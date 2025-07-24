package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports. It is annotated with {@link Service} to indicate that it's a service
 * component in the Spring context.
 */
@Slf4j
@Service
public class ReleaseService implements ReleaseAllNormExpressionsUseCase {

  private final UpdateOrSaveNormPort updateOrSaveNormPort;
  private final NormService normService;
  private final DeleteNormPort deleteNormPort;
  private final LdmlDeValidator ldmlDeValidator;
  private final LoadNormExpressionElisPort loadNormExpressionElisPort;
  private final LdmlDeElementSorter ldmlDeElementSorter;
  private final PretextCleanupService pretextCleanupService;

  public ReleaseService(
    UpdateOrSaveNormPort updateOrSaveNormPort,
    NormService normService,
    DeleteNormPort deleteNormPort,
    LdmlDeValidator ldmlDeValidator,
    LoadNormExpressionElisPort loadNormExpressionElisPort,
    LdmlDeElementSorter ldmlDeElementSorter,
    PretextCleanupService pretextCleanupService
  ) {
    this.updateOrSaveNormPort = updateOrSaveNormPort;
    this.normService = normService;
    this.deleteNormPort = deleteNormPort;
    this.ldmlDeValidator = ldmlDeValidator;
    this.loadNormExpressionElisPort = loadNormExpressionElisPort;
    this.ldmlDeElementSorter = ldmlDeElementSorter;
    this.pretextCleanupService = pretextCleanupService;
  }

  /**
   * Queue the expression of the given norm for publishing.
   * <p></p>
   * Previous releases of the same norm of the current date are replaced by this release.
   * <p></p>
   * NOTE: This is currently not taking care of updating the "nachfolgende-version-id".
   *
   * @param options The options specifying the {@link Verkuendung} to be loaded.
   * @return The information about the Verkuendung published.
   */
  @Override
  @Transactional
  public @NonNull List<Norm> release(@NonNull Options options) {
    // all expression elis of the norm to publish
    List<NormExpressionEli> allExpressionElis = loadNormExpressionElisPort.loadNormExpressionElis(
      new LoadNormExpressionElisPort.Options(options.eli())
    );

    List<Norm> manifestationsToPublish = allExpressionElis
      .stream()
      // we assume the latest manifestation is the working copy
      .map(expressionEli -> normService.loadNorm(new LoadNormUseCase.EliOptions(expressionEli)))
      .filter(norm -> NormPublishState.UNPUBLISHED.equals(norm.getPublishState()))
      .toList();

    log.info(
      "Manifestations to release: {}",
      manifestationsToPublish
        .stream()
        .map(Norm::getManifestationEli)
        .map(NormManifestationEli::toString)
        .collect(Collectors.joining())
    );

    List<Norm> releasedNorms = new ArrayList<>();
    manifestationsToPublish.forEach(manifestationToPublish -> {
      final NormManifestationEli oldManifestationEli = manifestationToPublish.getManifestationEli();
      final ReleaseType effectiveReleaseType = effectiveReleaseType(
        options.releaseType(),
        manifestationToPublish
      );

      log.info("Releasing {} as {}", oldManifestationEli, effectiveReleaseType);

      manifestationToPublish.setReleaseType(effectiveReleaseType);

      // copy that still includes the stuff the pretext cleanup might remove
      var uncleanedCopy = new Norm(manifestationToPublish);
      if (effectiveReleaseType == ReleaseType.PRAETEXT_RELEASED) {
        pretextCleanupService.clean(manifestationToPublish);
      }

      manifestationToPublish.setManifestationDateTo(LocalDate.now());

      ldmlDeElementSorter.sortElements(
        manifestationToPublish.getRegelungstext1().getDocument().getDocumentElement()
      );
      ldmlDeValidator.validateXSDSchema(manifestationToPublish);
      ldmlDeValidator.validateSchematron(manifestationToPublish);
      manifestationToPublish.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);

      final Norm updatedNorm = updateOrSaveNormPort.updateOrSave(
        new UpdateOrSaveNormPort.Options(manifestationToPublish)
      );

      if (effectiveReleaseType == ReleaseType.PRAETEXT_RELEASED) {
        // save a new working copy that still includes the stuff removed by the pretext cleanup (so the information
        // isn't lost)
        var newWorkingCopy = normService.updateNorm(uncleanedCopy);
        // add the working copy to the released norm list as it includes the release type information which is removed
        // in the pretext cleanup
        releasedNorms.add(newWorkingCopy);
      } else {
        // Remove the working copy as it has 1:1 the same information as the just published one. A new working copy will
        // be created when further things are changed.
        deleteNormPort.deleteNorm(
          new DeleteNormPort.Options(
            uncleanedCopy.getManifestationEli(),
            NormPublishState.UNPUBLISHED
          )
        );
        releasedNorms.add(updatedNorm);
      }
    });

    return releasedNorms;
  }

  private static ReleaseType effectiveReleaseType(ReleaseType targetReleaseType, Norm norm) {
    return switch (targetReleaseType) {
      case VOLLDOKUMENTATION_RELEASED -> ReleaseType.VOLLDOKUMENTATION_RELEASED;
      case PRAETEXT_RELEASED -> switch (norm.getReleaseType()) {
        case VOLLDOKUMENTATION_RELEASED -> ReleaseType.VOLLDOKUMENTATION_RELEASED;
        case PRAETEXT_RELEASED, NOT_RELEASED -> ReleaseType.PRAETEXT_RELEASED;
      };
      case NOT_RELEASED -> throw new IllegalStateException("Cannot release a norm as NOT_RELEASED");
    };
  }
}
