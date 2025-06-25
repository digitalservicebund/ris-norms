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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * Service responsible for publishing {@link Norm} to both a private and public storage locations. All necessary steps for
 * preparing the data before publishing it are also contained within this service. Only if publishing to both public and
 * private locations succeeds, the publish state of the respective {@link Norm} will get the {@link NormPublishState} updated to "PUBLISHED"
 */
@Service
@Slf4j
@ConditionalOnProperty("publish.enabled")
public class PublishService implements PublishNormUseCase {

  private final LoadNormManifestationElisByPublishStatePort loadNormManifestationElisByPublishStatePort;
  private final LoadNormPort loadNormPort;
  private final PublishNormPort publishNormPort;
  private final PublishNormPort publishPrivateNormPort;
  private final DeletePublishedNormPort deletePublishedNormPort;
  private final DeletePublishedNormPort deletePrivateNormPort;
  private final LoadLastMigrationLogPort loadLastMigrationLogPort;
  private final DeleteAllPublishedDokumentePort deleteAllPublishedDokumentePort;
  private final DeleteAllPublishedDokumentePort deleteAllPrivateDokumentePort;
  private final PublishChangelogPort publishPublicChangelogsPort;
  private final PublishChangelogPort publishPrivateChangelogsPort;
  private final CompleteMigrationLogPort updateMigrationLogPort;
  private final ConfidentialDataCleanupService confidentialDataCleanupService;
  private final UpdateNormPublishStatePort updateNormPublishStatePort;

  public PublishService(
    LoadNormManifestationElisByPublishStatePort loadNormManifestationElisByPublishStatePort,
    LoadNormPort loadNormPort,
    @Qualifier("public") PublishNormPort publishNormPort,
    @Qualifier("private") PublishNormPort publishPrivateNormPort,
    @Qualifier("public") DeletePublishedNormPort deletePublishedNormPort,
    @Qualifier("private") DeletePublishedNormPort deletePrivateNormPort,
    LoadLastMigrationLogPort loadLastMigrationLogPort,
    @Qualifier("public") DeleteAllPublishedDokumentePort deleteAllPublishedDokumentePort,
    @Qualifier("private") DeleteAllPublishedDokumentePort deleteAllPrivateDokumentePort,
    @Qualifier("public") PublishChangelogPort publishPublicChangelogsPort,
    @Qualifier("private") PublishChangelogPort publishPrivateChangelogsPort,
    CompleteMigrationLogPort updateMigrationLogPort,
    ConfidentialDataCleanupService confidentialDataCleanupService,
    UpdateNormPublishStatePort updateNormPublishStatePort
  ) {
    this.loadNormManifestationElisByPublishStatePort = loadNormManifestationElisByPublishStatePort;
    this.loadNormPort = loadNormPort;
    this.publishNormPort = publishNormPort;
    this.publishPrivateNormPort = publishPrivateNormPort;
    this.deletePublishedNormPort = deletePublishedNormPort;
    this.deletePrivateNormPort = deletePrivateNormPort;
    this.loadLastMigrationLogPort = loadLastMigrationLogPort;
    this.deleteAllPublishedDokumentePort = deleteAllPublishedDokumentePort;
    this.deleteAllPrivateDokumentePort = deleteAllPrivateDokumentePort;
    this.publishPublicChangelogsPort = publishPublicChangelogsPort;
    this.publishPrivateChangelogsPort = publishPrivateChangelogsPort;
    this.updateMigrationLogPort = updateMigrationLogPort;
    this.confidentialDataCleanupService = confidentialDataCleanupService;
    this.updateNormPublishStatePort = updateNormPublishStatePort;
  }

  @Override
  public void processQueuedFilesForPublish() {
    var lastMigrationLog = loadLastMigrationLogPort
      .loadLastMigrationLog()
      .filter(migrationLog -> !migrationLog.isCompleted());
    lastMigrationLog.ifPresent(log -> {
      if (migrationLogIsRelevant(log) && log.getXmlSize() <= 0) {
        throw new MigrationJobException();
      }
    });
    List<NormManifestationEli> manifestationElis =
      loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(
        new LoadNormManifestationElisByPublishStatePort.Options(NormPublishState.QUEUED_FOR_PUBLISH)
      );

    log.info("Found {} norms that are queued for publishing", manifestationElis.size());

    manifestationElis.forEach(manifestationEli -> {
      log.info("Processing norm with manifestation eli {}", manifestationEli);
      Optional<Norm> norm = loadNormPort.loadNorm(new LoadNormPort.Options(manifestationEli));
      log.debug("Finished looking for norm with manifestation eli {}", manifestationEli);
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
          migrationLog.getXmlSize()
        );
        log.info("Deleting all old dokumente in both buckets");
        deleteAllPublishedDokumentePort.deleteAllPublishedDokumente(
          new DeleteAllPublishedDokumentePort.Options(migrationLog.getCreatedAt())
        );
        deleteAllPrivateDokumentePort.deleteAllPublishedDokumente(
          new DeleteAllPublishedDokumentePort.Options(migrationLog.getCreatedAt())
        );
        log.info("Deleted all dokumente in both buckets");
        updateMigrationLogPort.completeMigrationLog(
          new CompleteMigrationLogPort.Options(migrationLog.getId())
        );
        log.info(
          "Marked migration log with timestamp {} (UTC) as completed.",
          formatMigrationLogTimestamp(migrationLog.getCreatedAt())
        );
      }
    });
    publishPublicChangelogsPort.publishChangelogs(new PublishChangelogPort.Options(false));
    publishPrivateChangelogsPort.publishChangelogs(new PublishChangelogPort.Options(false));
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
    log.debug("Preparing norm with manifestation eli {} for publish", norm.getManifestationEli());
    prepareForPublish(norm);
    boolean isPublicPublished = false;
    boolean isPrivatePublished = false;
    try {
      // Try to publish publicly
      log.debug(
        "Publishing norm with manifestation eli {} to public bucket",
        norm.getManifestationEli()
      );
      publishNormPort.publishNorm(new PublishNormPort.Options(norm));
      isPublicPublished = true;
      // Only if public publish succeeds, try private publish
      log.debug(
        "Publishing norm with manifestation eli {} to private bucket",
        norm.getManifestationEli()
      );
      publishPrivateNormPort.publishNorm(new PublishNormPort.Options(norm));
      isPrivatePublished = true;
      // If both succeed, update the publish state
      log.debug(
        "Saving updated norm state to database for norm with manifestation eli {}",
        norm.getManifestationEli()
      );
      updateNormPublishStatePort.updateNormPublishState(
        new UpdateNormPublishStatePort.Options(
          norm.getManifestationEli(),
          NormPublishState.PUBLISHED
        )
      );
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
      deletePublishedNormPort.deletePublishedNorm(new DeletePublishedNormPort.Options(norm));
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
      deletePrivateNormPort.deletePublishedNorm(new DeletePublishedNormPort.Options(norm));
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
