package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.PublishNormsToPortalPrototypeUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteAllPublishedDokumentePort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormManifestationElisByPublishStatePort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.PublishChangelogPort;
import de.bund.digitalservice.ris.norms.application.port.output.PublishNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
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

  private final List<String> publishingAllowlist = new ArrayList<>();

  public PortalPrototypePublishService(
    LoadNormManifestationElisByPublishStatePort loadNormManifestationElisByPublishStatePort,
    @Qualifier("portalPrototype") PublishNormPort publishNormPort,
    @Qualifier("portalPrototype") DeleteAllPublishedDokumentePort deleteAllPublishedDokumentePort,
    @Qualifier("portalPrototype") PublishChangelogPort publishChangelogsPort,
    LoadNormPort loadNormPort,
    @Value(
      "classpath:/portal-prototype-publishing-allowlist.txt"
    ) Resource portalPrototypePublishingAllowlist
  ) {
    this.loadNormManifestationElisByPublishStatePort = loadNormManifestationElisByPublishStatePort;
    this.publishNormPort = publishNormPort;
    this.deleteAllPublishedDokumentePort = deleteAllPublishedDokumentePort;
    this.publishChangelogPort = publishChangelogsPort;
    this.loadNormPort = loadNormPort;

    try {
      this.publishingAllowlist.addAll(
          Files.readAllLines(portalPrototypePublishingAllowlist.getFile().toPath())
        );
    } catch (IOException e) {
      log.error(
        "Could not read portal prototype publishing allowlist. Do not allow publishing of any norms.",
        e
      );
      this.publishingAllowlist.clear();
    }
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

      if (!isNormAllowedToBePublished(norm.get())) {
        log.info(
          "Norm with manifestation eli {} is not allowed to be published to the prototype",
          eli
        );
        return;
      }

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

  private boolean isNormAllowedToBePublished(Norm norm) {
    var amtlicheAbkuerzung = norm.getShortTitle();

    return (
      amtlicheAbkuerzung.isPresent() &&
      publishingAllowlist.contains(amtlicheAbkuerzung.get()) &&
      norm.isInkraftAt(LocalDate.now())
    );
  }
}
