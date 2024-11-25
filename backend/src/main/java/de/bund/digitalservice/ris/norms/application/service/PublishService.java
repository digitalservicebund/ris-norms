package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.exceptions.StorageException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

  private final LoadNormIdsByPublishStatePort loadNormIdsByPublishStatePort;
  private final PublishPublicNormPort publishPublicNormPort;
  private final PublishPrivateNormPort publishPrivateNormPort;
  private final UpdateOrSaveNormPort updateOrSaveNormPort;
  private final DeletePublicNormPort deletePublicNormPort;
  private final DeletePrivateNormPort deletePrivateNormPort;
  private final LoadNormByIdPort loadNormByIdPort;
  private final LoadMigrationLogByDatePort loadMigrationLogByDatePort;
  private final DeleteAllPublicNormsPort deleteAllPublicNormsPort;
  private final DeleteAllPrivateNormsPort deleteAllPrivateNormsPort;
  private final PublishChangelogsPort publishChangelogsPort;

  public PublishService(
    LoadNormIdsByPublishStatePort loadNormIdsByPublishStatePort,
    PublishPublicNormPort publishPublicNormPort,
    PublishPrivateNormPort publishPrivateNormPort,
    UpdateOrSaveNormPort updateOrSaveNormPort,
    DeletePublicNormPort deletePublicNormPort,
    DeletePrivateNormPort deletePrivateNormPort,
    LoadNormByIdPort loadNormByIdPort,
    LoadMigrationLogByDatePort loadMigrationLogByDatePort,
    DeleteAllPublicNormsPort deleteAllPublicNormsPort,
    DeleteAllPrivateNormsPort deleteAllPrivateNormsPort,
    PublishChangelogsPort publishChangelogsPort
  ) {
    this.loadNormIdsByPublishStatePort = loadNormIdsByPublishStatePort;
    this.publishPublicNormPort = publishPublicNormPort;
    this.publishPrivateNormPort = publishPrivateNormPort;
    this.updateOrSaveNormPort = updateOrSaveNormPort;
    this.deletePublicNormPort = deletePublicNormPort;
    this.deletePrivateNormPort = deletePrivateNormPort;
    this.loadNormByIdPort = loadNormByIdPort;
    this.loadMigrationLogByDatePort = loadMigrationLogByDatePort;
    this.deleteAllPublicNormsPort = deleteAllPublicNormsPort;
    this.deleteAllPrivateNormsPort = deleteAllPrivateNormsPort;
    this.publishChangelogsPort = publishChangelogsPort;
  }

  @Override
  public void processQueuedFilesForPublish() {
    loadMigrationLogByDatePort
      .loadMigrationLogByDate(new LoadMigrationLogByDatePort.Command(LocalDate.now()))
      .ifPresent(migrationLog -> {
        log.info(
          "Migration log found with timestamp {}. Deleting all norms in both buckets",
          migrationLog
            .getCreatedAt()
            .atOffset(ZoneOffset.UTC)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        if (migrationLog.getSize() > 0) {
          deleteAllPublicNormsPort.deleteAllPublicNorms();
          deleteAllPrivateNormsPort.deleteAllPrivateNorms();
          log.info("Deleted all norms in both buckets");
        } else {
          log.info("Migration log has a size of 0");
        }
      });

    List<UUID> normIds = loadNormIdsByPublishStatePort.loadNormIdsByPublishState(
      new LoadNormIdsByPublishStatePort.Command(NormPublishState.QUEUED_FOR_PUBLISH)
    );

    log.info("Found {} norms that are queued for publishing", normIds.size());

    normIds.forEach(publishId -> {
      log.info("Processing norm with id {}", publishId);
      Optional<Norm> norm = loadNormByIdPort.loadNormById(new LoadNormByIdPort.Command(publishId));
      norm.ifPresent(this::processNorm);
      if (norm.isEmpty()) {
        log.error("Norm with id {} not found", publishId);
      }
    });
    publishChangelogsPort.publishChangelogs();
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
    normToBeReleased.getMeta().getProprietary().ifPresent(this::removePrivateMetadata);
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
      proprietary.getNode()
    );

    matches.forEach(match -> match.getParentNode().removeChild(match));
  }
}
