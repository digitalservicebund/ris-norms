package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.LdmlDeNotValidException;
import de.bund.digitalservice.ris.norms.application.port.input.PublishNormsToPortalPrototypeUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteAllPublishedDokumentePort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormManifestationElisByPublishStatePort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadPortalPublishingAllowListPort;
import de.bund.digitalservice.ris.norms.application.port.output.PublishChangelogPort;
import de.bund.digitalservice.ris.norms.application.port.output.PublishNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * Service for publishing norms to the portal prototype
 */
@Service
@Slf4j
@ConditionalOnProperty("publish.portal-prototype.enabled")
public class PortalPrototypePublishService implements PublishNormsToPortalPrototypeUseCase {

  private final LoadNormManifestationElisByPublishStatePort loadNormManifestationElisByPublishStatePort;
  private final PublishNormPort publishNormPort;
  private final DeleteAllPublishedDokumentePort deleteAllPublishedDokumentePort;
  private final PublishChangelogPort publishChangelogPort;
  private final LoadNormPort loadNormPort;
  private final LoadPortalPublishingAllowListPort loadPortalPublishingAllowListPort;
  private final ConfidentialDataCleanupService confidentialDataCleanupService;
  private final PrototypeCleanupService prototypeCleanupService;
  private final LdmlDeValidator ldmlDeValidator;

  public PortalPrototypePublishService(
    LoadNormManifestationElisByPublishStatePort loadNormManifestationElisByPublishStatePort,
    @Qualifier("portalPrototype") PublishNormPort publishNormPort,
    @Qualifier("portalPrototype") DeleteAllPublishedDokumentePort deleteAllPublishedDokumentePort,
    @Qualifier("portalPrototype") PublishChangelogPort publishChangelogsPort,
    LoadNormPort loadNormPort,
    LoadPortalPublishingAllowListPort loadPortalPublishingAllowListPort,
    ConfidentialDataCleanupService confidentialDataCleanupService,
    PrototypeCleanupService prototypeCleanupService,
    LdmlDeValidator ldmlDeValidator
  ) {
    this.loadNormManifestationElisByPublishStatePort = loadNormManifestationElisByPublishStatePort;
    this.publishNormPort = publishNormPort;
    this.deleteAllPublishedDokumentePort = deleteAllPublishedDokumentePort;
    this.publishChangelogPort = publishChangelogsPort;
    this.loadNormPort = loadNormPort;
    this.loadPortalPublishingAllowListPort = loadPortalPublishingAllowListPort;
    this.confidentialDataCleanupService = confidentialDataCleanupService;
    this.prototypeCleanupService = prototypeCleanupService;
    this.ldmlDeValidator = ldmlDeValidator;
    log.info("PortalPrototypePublishService initialized");
  }

  @Override
  public void publishNormsToPortalPrototype() {
    final Instant startOfProcessing = Instant.now();

    var queuedNormElis =
      loadNormManifestationElisByPublishStatePort.loadNormManifestationElisByPublishState(
        new LoadNormManifestationElisByPublishStatePort.Options(NormPublishState.QUEUED_FOR_PUBLISH)
      );
    log.info("Currently {} norms are in state QUEUED_FOR_PUBLISHED", queuedNormElis.size());

    var successfullyPublishedNormCount = queuedNormElis
      .stream()
      .map(eli -> {
        log.info("Processing norm with manifestation eli {}", eli);
        Optional<Norm> optionalNorm = loadNormPort.loadNorm(new LoadNormPort.Options(eli));

        if (optionalNorm.isEmpty()) {
          log.error("Norm with manifestation eli {} not found", eli);
          return false;
        }

        var norm = optionalNorm.get();

        if (!isNormAllowedToBePublished(norm)) {
          return false;
        }

        prototypeCleanupService.clean(norm);

        confidentialDataCleanupService.clean(norm);

        try {
          ldmlDeValidator.validateXSDSchema(norm);
        } catch (final LdmlDeNotValidException e) {
          log.error(
            "Norm {} could not be published to portal-prototype (schema validation failed)",
            norm.getManifestationEli().toString()
          );
          for (LdmlDeNotValidException.ValidationError error : e.getErrors()) {
            log.error(
              "Validation error: Type={}, Line={}, Column={}, Detail={}",
              error.type(),
              error.lineNumber(),
              error.columnNumber(),
              error.detail()
            );
          }
          log.error(e.getMessage(), e);
          return false;
        }

        try {
          publishNormPort.publishNorm(new PublishNormPort.Options(norm));
        } catch (final Exception e) {
          log.error(
            "Norm {} could not be published to portal-prototype (publishing failed)",
            norm.getManifestationEli().toString()
          );
          log.error(e.getMessage(), e);
          return false;
        }

        log.info("Published norm to portal-prototype: {}", norm.getManifestationEli().toString());
        return true;
      })
      .filter(successfullyPublished -> successfullyPublished)
      .count();

    log.info("Published {} norms to portal-prototype", successfullyPublishedNormCount);

    if (successfullyPublishedNormCount > 0) {
      log.info("Deleting all old dokumente from portal-prototype bucket");
      deleteAllPublishedDokumentePort.deleteAllPublishedDokumente(
        new DeleteAllPublishedDokumentePort.Options(startOfProcessing)
      );
      log.info("Deleted all old dokumente from portal-prototype bucket");

      publishChangelogPort.publishChangelogs(new PublishChangelogPort.Options(false));
    }
    log.info("Publish job for portal prototype successfully completed.");
  }

  private boolean isNormAllowedToBePublished(Norm norm) {
    var amtlicheAbkuerzung = norm.getShortTitle();

    if (amtlicheAbkuerzung.isEmpty()) {
      log.info(
        "Norm {} could not be published to portal-prototype (amtliche-abkuerzung is empty)",
        norm.getManifestationEli().toString()
      );
      return false;
    }

    if (
      !loadPortalPublishingAllowListPort
        .loadPortalPublishingAllowListPort()
        .contains(amtlicheAbkuerzung.get())
    ) {
      log.info(
        "Norm {} could not be published to portal-prototype (amtliche-abkuerzung {} is not allowed to be published)",
        norm.getManifestationEli().toString(),
        amtlicheAbkuerzung.orElse("")
      );
      return false;
    }

    if (!norm.isInkraftAt(LocalDate.now())) {
      log.info(
        "Norm {} could not be published to portal-prototype (it is not inkraft)",
        norm.getManifestationEli().toString()
      );
      return false;
    }

    return true;
  }
}
