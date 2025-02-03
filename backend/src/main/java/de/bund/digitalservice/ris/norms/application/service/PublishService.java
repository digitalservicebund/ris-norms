package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.MigrationJobException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.exceptions.StorageException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service responsible for publishing {@link Norm} to both a private and public storage locations. All necessary steps for
 * preparing the data before publishing it are also contained within this service. Only if publishing to both public and
 * private locations succeeds, the publish state of the respective {@link Norm} will get the {@link NormPublishState} updated to "PUBLISHED"
 */
@Service
@Slf4j
public class PublishService implements PublishNormUseCase {

  private final LoadNormManifestationElisByPublishStatePort loadNormManifestationElisByPublishStatePort;
  private final LoadNormPort loadNormPort;
  private final PublishPublicNormPort publishPublicNormPort;
  private final PublishPrivateNormPort publishPrivateNormPort;
  private final UpdateOrSaveNormPort updateOrSaveNormPort;
  private final DeletePublicNormPort deletePublicNormPort;
  private final DeletePrivateNormPort deletePrivateNormPort;
  private final LoadLastMigrationLogPort loadLastMigrationLogPort;
  private final DeleteAllPublicNormsPort deleteAllPublicNormsPort;
  private final DeleteAllPrivateNormsPort deleteAllPrivateNormsPort;
  private final PublishChangelogsPort publishChangelogsPort;

  public PublishService(
    LoadNormManifestationElisByPublishStatePort loadNormManifestationElisByPublishStatePort,
    LoadNormPort loadNormPort,
    PublishPublicNormPort publishPublicNormPort,
    PublishPrivateNormPort publishPrivateNormPort,
    UpdateOrSaveNormPort updateOrSaveNormPort,
    DeletePublicNormPort deletePublicNormPort,
    DeletePrivateNormPort deletePrivateNormPort,
    LoadLastMigrationLogPort loadLastMigrationLogPort,
    DeleteAllPublicNormsPort deleteAllPublicNormsPort,
    DeleteAllPrivateNormsPort deleteAllPrivateNormsPort,
    PublishChangelogsPort publishChangelogsPort
  ) {
    this.loadNormManifestationElisByPublishStatePort = loadNormManifestationElisByPublishStatePort;
    this.loadNormPort = loadNormPort;
    this.publishPublicNormPort = publishPublicNormPort;
    this.publishPrivateNormPort = publishPrivateNormPort;
    this.updateOrSaveNormPort = updateOrSaveNormPort;
    this.deletePublicNormPort = deletePublicNormPort;
    this.deletePrivateNormPort = deletePrivateNormPort;
    this.loadLastMigrationLogPort = loadLastMigrationLogPort;
    this.deleteAllPublicNormsPort = deleteAllPublicNormsPort;
    this.deleteAllPrivateNormsPort = deleteAllPrivateNormsPort;
    this.publishChangelogsPort = publishChangelogsPort;
  }

  @Override
  public void processQueuedFilesForPublish() {
    loadLastMigrationLogPort
      .loadLastMigrationLog()
      .ifPresent(migrationLog -> {
        final LocalDate createdAtDate = migrationLog
          .getCreatedAt()
          .atZone(ZoneId.systemDefault())
          .toLocalDate();
        final LocalDate today = LocalDate.now();
        final LocalDate yesterday = today.minusDays(1);
        if (createdAtDate.equals(today) || createdAtDate.equals(yesterday)) {
          log.info(
            "Migration log found with timestamp {} (UTC). Deleting all norms in both buckets",
            migrationLog
              .getCreatedAt()
              .atOffset(ZoneOffset.UTC)
              .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
          );
          if (migrationLog.getSize() <= 0) {
            throw new MigrationJobException();
          }
          deleteAllPublicNormsPort.deleteAllPublicNorms();
          deleteAllPrivateNormsPort.deleteAllPrivateNorms();
          log.info("Deleted all norms in both buckets");
        }
      });

    List<NormManifestationEli> manifestationElis =
      loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(
        new LoadNormManifestationElisByPublishStatePort.Command(NormPublishState.QUEUED_FOR_PUBLISH)
      );

    log.info("Found {} norms that are queued for publishing", manifestationElis.size());

    manifestationElis.forEach(manifestationEli -> {
      log.info("Processing norm with manifestation eli {}", manifestationEli);
      Optional<Norm> norm = loadNormPort.loadNorm(new LoadNormPort.Command(manifestationEli));
      norm.ifPresent(this::processNorm);
      if (norm.isEmpty()) {
        log.error("Norm with manifestation eli {} not found", manifestationEli);
      }
    });
    publishChangelogsPort.publishChangelogs(new PublishChangelogsPort.Command(true));
    log.info("Publish job successfully completed.");
  }

  private void processNorm(Norm norm) {
    prepareForPublish(norm);
    boolean isPublicPublished = false;
    boolean isPrivatePublished = false;
    try {
      // Try to publish publicly
      publishPublicNormPort.publishPublicNorm(new PublishPublicNormPort.Command(norm));
      isPublicPublished = true;
      // Only if public publish succeeds, try private publish
      publishPrivateNormPort.publishPrivateNorm(new PublishPrivateNormPort.Command(norm));
      isPrivatePublished = true;
      // If both succeed, update the publish state
      norm.setPublishState(NormPublishState.PUBLISHED);
      updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Command(norm));
      log.info("Published norm: {}", norm.getManifestationEli().toString());
    } catch (final Exception e) {
      log.error("Norm {} could not be published", norm.getManifestationEli().toString());
      log.error(e.getMessage(), e);
      // Rollback logic based on what succeeded
      if (isPublicPublished) {
        rollbackPublicPublish(norm);
      }
      if (isPublicPublished && isPrivatePublished) {
        rollbackPrivatePublish(norm);
      }
    }
  }

  /**
   * Takes a norm and makes any changes needed in preparation for publishing it.
   *
   * @param normToBeReleased Norm that will be released
   */
  public void prepareForPublish(final Norm normToBeReleased) {
    normToBeReleased
      .getRegelungstexte()
      .forEach(regelungstext ->
        regelungstext.getMeta().getProprietary().ifPresent(this::removePrivateMetadata)
      );
  }

  private void rollbackPublicPublish(Norm norm) {
    try {
      deletePublicNormPort.deletePublicNorm(new DeletePublicNormPort.Command(norm));
      log.info(
        "Deleted public norm on rollback strategy: {}",
        norm.getManifestationEli().toString()
      );
    } catch (final StorageException e) {
      log.error(e.getMessage(), e);
    }
  }

  private void rollbackPrivatePublish(Norm norm) {
    try {
      deletePrivateNormPort.deletePrivateNorm(new DeletePrivateNormPort.Command(norm));
      log.info(
        "Deleted privated norm on rollback strategy: {}",
        norm.getManifestationEli().toString()
      );
    } catch (StorageException e) {
      log.error(e.getMessage(), e);
    }
  }

  private void removePrivateMetadata(final Proprietary proprietary) {
    // Organisationseinheit
    var matches = NodeParser.getNodesFromExpression(
      "//organisationsEinheit",
      proprietary.getElement()
    );

    matches.forEach(match -> match.getParentNode().removeChild(match));
  }
}
