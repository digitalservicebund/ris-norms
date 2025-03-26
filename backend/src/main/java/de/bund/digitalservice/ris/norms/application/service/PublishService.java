package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.MigrationJobException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import de.bund.digitalservice.ris.norms.utils.exceptions.StorageException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
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
  private final PublishNormPort publishNormPort;
  private final PublishNormPort publishPrivateNormPort;
  private final UpdateOrSaveNormPort updateOrSaveNormPort;
  private final DeletePublishedNormPort deletePublishedNormPort;
  private final DeletePublishedNormPort deletePrivateNormPort;
  private final LoadLastMigrationLogPort loadLastMigrationLogPort;
  private final DeleteAllPublishedDokumentePort deleteAllPublishedDokumentePort;
  private final DeleteAllPublishedDokumentePort deleteAllPrivateDokumentePort;
  private final PublishChangelogPort publishPublicChangelogsPort;
  private final PublishChangelogPort publishPrivateChangelogsPort;
  private final CompleteMigrationLogPort updateMigrationLogPort;
  private final ConfidentialDataCleanupService confidentialDataCleanupService;

  public PublishService(
    LoadNormManifestationElisByPublishStatePort loadNormManifestationElisByPublishStatePort,
    LoadNormPort loadNormPort,
    @Qualifier("public") PublishNormPort publishNormPort,
    @Qualifier("private") PublishNormPort publishPrivateNormPort,
    UpdateOrSaveNormPort updateOrSaveNormPort,
    @Qualifier("public") DeletePublishedNormPort deletePublishedNormPort,
    @Qualifier("private") DeletePublishedNormPort deletePrivateNormPort,
    LoadLastMigrationLogPort loadLastMigrationLogPort,
    @Qualifier("public") DeleteAllPublishedDokumentePort deleteAllPublishedDokumentePort,
    @Qualifier("private") DeleteAllPublishedDokumentePort deleteAllPrivateDokumentePort,
    @Qualifier("public") PublishChangelogPort publishPublicChangelogsPort,
    @Qualifier("private") PublishChangelogPort publishPrivateChangelogsPort,
    CompleteMigrationLogPort updateMigrationLogPort,
    ConfidentialDataCleanupService confidentialDataCleanupService
  ) {
    this.loadNormManifestationElisByPublishStatePort = loadNormManifestationElisByPublishStatePort;
    this.loadNormPort = loadNormPort;
    this.publishNormPort = publishNormPort;
    this.publishPrivateNormPort = publishPrivateNormPort;
    this.updateOrSaveNormPort = updateOrSaveNormPort;
    this.deletePublishedNormPort = deletePublishedNormPort;
    this.deletePrivateNormPort = deletePrivateNormPort;
    this.loadLastMigrationLogPort = loadLastMigrationLogPort;
    this.deleteAllPublishedDokumentePort = deleteAllPublishedDokumentePort;
    this.deleteAllPrivateDokumentePort = deleteAllPrivateDokumentePort;
    this.publishPublicChangelogsPort = publishPublicChangelogsPort;
    this.publishPrivateChangelogsPort = publishPrivateChangelogsPort;
    this.updateMigrationLogPort = updateMigrationLogPort;
    this.confidentialDataCleanupService = confidentialDataCleanupService;
  }

  @Override
  public void processQueuedFilesForPublish() {
    var lastMigrationLog = loadLastMigrationLogPort
      .loadLastMigrationLog()
      .filter(migrationLog -> !migrationLog.isCompleted());
    lastMigrationLog.ifPresent(log -> {
      if (migrationLogIsRelevant(log) && log.getSize() <= 0) {
        throw new MigrationJobException();
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

    lastMigrationLog.ifPresent(migrationLog -> {
      if (migrationLogIsRelevant(migrationLog)) {
        log.info(
          "Migration log found with timestamp {} (UTC) and {} dokumente.",
          formatMigrationLogTimestamp(migrationLog.getCreatedAt()),
          migrationLog.getSize()
        );
        log.info("Deleting all old dokumente in both buckets");
        deleteAllPublishedDokumentePort.deleteAllPublishedDokumente(
          new DeleteAllPublishedDokumentePort.Command(migrationLog.getCreatedAt())
        );
        deleteAllPrivateDokumentePort.deleteAllPublishedDokumente(
          new DeleteAllPublishedDokumentePort.Command(migrationLog.getCreatedAt())
        );
        log.info("Deleted all dokumente in both buckets");
        updateMigrationLogPort.completeMigrationLog(
          new CompleteMigrationLogPort.Command(migrationLog.getId())
        );
        log.info(
          "Marked migration log with timestamp {} (UTC) as completed.",
          formatMigrationLogTimestamp(migrationLog.getCreatedAt())
        );
      }
    });
    publishPublicChangelogsPort.publishChangelogs(new PublishChangelogPort.Command(false));
    publishPrivateChangelogsPort.publishChangelogs(new PublishChangelogPort.Command(false));
    log.info("Publish job successfully completed.");
  }

  private boolean migrationLogIsRelevant(final MigrationLog migrationLog) {
    final LocalDate createdAtDate = migrationLog
      .getCreatedAt()
      .atZone(ZoneId.systemDefault())
      .toLocalDate();
    final LocalDate today = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
    final LocalDate yesterday = today.minusDays(1);
    return (
      createdAtDate.equals(today) || createdAtDate.equals(yesterday) || !migrationLog.isCompleted()
    );
  }

  private void processNorm(Norm norm) {
    prepareForPublish(norm);
    boolean isPublicPublished = false;
    boolean isPrivatePublished = false;
    try {
      // Try to publish publicly
      publishNormPort.publishNorm(new PublishNormPort.Command(norm));
      isPublicPublished = true;
      // Only if public publish succeeds, try private publish
      publishPrivateNormPort.publishNorm(new PublishNormPort.Command(norm));
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
  private void prepareForPublish(final Norm normToBeReleased) {
    confidentialDataCleanupService.clean(normToBeReleased);
  }

  private void rollbackPublicPublish(Norm norm) {
    try {
      deletePublishedNormPort.deletePublishedNorm(new DeletePublishedNormPort.Command(norm));
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
      deletePrivateNormPort.deletePublishedNorm(new DeletePublishedNormPort.Command(norm));
      log.info(
        "Deleted private norm on rollback strategy: {}",
        norm.getManifestationEli().toString()
      );
    } catch (StorageException e) {
      log.error(e.getMessage(), e);
    }
  }

  private String formatMigrationLogTimestamp(Instant timestamp) {
    return timestamp
      .atOffset(ZoneOffset.UTC)
      .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }
}
