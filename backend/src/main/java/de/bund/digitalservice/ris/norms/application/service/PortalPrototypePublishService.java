package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.PublishNormsToPortalPrototypeUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteAllPublishedDokumentePort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormManifestationElisByPublishStatePort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.PublishChangelogPort;
import de.bund.digitalservice.ris.norms.application.port.output.PublishNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import java.time.Instant;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Service for publishing norms to the portal prototype
 */
@Profile({ "production", "local", "local-docker" })
@Service
@Slf4j
public class PortalPrototypePublishService implements PublishNormsToPortalPrototypeUseCase {

  private final LoadNormManifestationElisByPublishStatePort loadNormManifestationElisByPublishStatePort;
  private final PublishNormPort publishNormPort;
  private final DeleteAllPublishedDokumentePort deleteAllPublishedDokumentePort;
  private final PublishChangelogPort publishChangelogPort;
  private final LoadNormPort loadNormPort;

  public PortalPrototypePublishService(
    LoadNormManifestationElisByPublishStatePort loadNormManifestationElisByPublishStatePort,
    @Qualifier("portalPrototype") PublishNormPort publishNormPort,
    @Qualifier("portalPrototype") DeleteAllPublishedDokumentePort deleteAllPublishedDokumentePort,
    @Qualifier("portalPrototype") PublishChangelogPort publishChangelogsPort,
    LoadNormPort loadNormPort
  ) {
    this.loadNormManifestationElisByPublishStatePort = loadNormManifestationElisByPublishStatePort;
    this.publishNormPort = publishNormPort;
    this.deleteAllPublishedDokumentePort = deleteAllPublishedDokumentePort;
    this.publishChangelogPort = publishChangelogsPort;
    this.loadNormPort = loadNormPort;
  }

  @Override
  public void publishNormsToPortalPrototype() {
    final Instant startOfProcessing = Instant.now();

    var publishedNormElis =
      loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(
        new LoadNormManifestationElisByPublishStatePort.Command(NormPublishState.PUBLISHED)
      );

    log.info("Currently {} norms are in state PUBLISHED", publishedNormElis.size());

    publishedNormElis.forEach(eli -> {
      log.info("Processing norm with manifestation eli {}", eli);
      Optional<Norm> norm = loadNormPort.loadNorm(new LoadNormPort.Command(eli));

      if (norm.isEmpty()) {
        log.error("Norm with manifestation eli {} not found", eli);
        return;
      }

      // TODO: (Malte Laukötter, 2025-03-07) check if norm is allowed to be published

      // TODO: (Malte Laukötter, 2025-03-07) clean up norm
      // TODO: (Malte Laukötter, 2025-03-07) also run the cleanup from the normal PublishService

      try {
        publishNormPort.publishNorm(new PublishNormPort.Command(norm.get()));
        log.info(
          "Published norm to portal-prototype: {}",
          norm.get().getManifestationEli().toString()
        );
      } catch (final Exception e) {
        log.error(
          "Norm {} could not be published to portal-prototype",
          norm.get().getManifestationEli().toString()
        );
        log.error(e.getMessage(), e);
      }
    });

    log.info("Deleting all old dokumente from portal-prototype bucket");
    deleteAllPublishedDokumentePort.deleteAllPublishedDokumente(
      new DeleteAllPublishedDokumentePort.Command(startOfProcessing)
    );
    log.info("Deleted all old dokumente from portal-prototype  bucket");

    publishChangelogPort.publishChangelogs(new PublishChangelogPort.Command(false));

    log.info("Publish job for portal prototype successfully completed.");
  }
}
