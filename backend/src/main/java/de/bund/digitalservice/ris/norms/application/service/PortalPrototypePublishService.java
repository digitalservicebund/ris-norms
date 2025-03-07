package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.PublishNormsToPortalPrototypeUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteAllPublishedDokumentePort;
import de.bund.digitalservice.ris.norms.application.port.output.DeletePublishedNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormManifestationElisByPublishStatePort;
import de.bund.digitalservice.ris.norms.application.port.output.PublishChangelogPort;
import de.bund.digitalservice.ris.norms.application.port.output.PublishNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
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
  private final DeletePublishedNormPort deletePublishedNormPort;
  private final DeleteAllPublishedDokumentePort deleteAllPublishedDokumentePort;
  private final PublishChangelogPort publishChangelogPort;

  public PortalPrototypePublishService(
    LoadNormManifestationElisByPublishStatePort loadNormManifestationElisByPublishStatePort,
    @Qualifier("portalPrototype") PublishNormPort publishNormPort,
    @Qualifier("portalPrototype") DeletePublishedNormPort deletePublishedNormPort,
    @Qualifier("portalPrototype") DeleteAllPublishedDokumentePort deleteAllPublishedDokumentePort,
    @Qualifier("portalPrototype") PublishChangelogPort publishChangelogsPort
  ) {
    this.loadNormManifestationElisByPublishStatePort = loadNormManifestationElisByPublishStatePort;
    this.publishNormPort = publishNormPort;
    this.deletePublishedNormPort = deletePublishedNormPort;
    this.deleteAllPublishedDokumentePort = deleteAllPublishedDokumentePort;
    this.publishChangelogPort = publishChangelogsPort;
  }

  @Override
  public void publishNormsToPortalPrototype() {
    var publishedNormElis =
      loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(
        new LoadNormManifestationElisByPublishStatePort.Command(NormPublishState.PUBLISHED)
      );

    log.info("Currently {} norms are in state PUBLISHED", publishedNormElis.size());

    publishedNormElis.forEach(eli -> {
      // TODO: (Malte Laukötter, 2025-03-06) implement
    });

    log.info("Publishing norms to port prototype - not yet implemented");
  }
}
