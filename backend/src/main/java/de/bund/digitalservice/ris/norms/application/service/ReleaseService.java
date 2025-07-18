package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports. It is annotated with {@link Service} to indicate that it's a service
 * component in the Spring context.
 */
@Service
public class ReleaseService implements ReleaseAllNormExpressionsUseCase {

  private final UpdateOrSaveNormPort updateOrSaveNormPort;
  private final NormService normService;
  private final CreateNewVersionOfNormService createNewVersionOfNormService;
  private final DeleteNormPort deleteNormPort;
  private final LdmlDeValidator ldmlDeValidator;
  private final LoadNormExpressionElisPort loadNormExpressionElisPort;
  private final LdmlDeElementSorter ldmlDeElementSorter;
  private final PretextCleanupService pretextCleanupService;

  public ReleaseService(
    UpdateOrSaveNormPort updateOrSaveNormPort,
    NormService normService,
    CreateNewVersionOfNormService createNewVersionOfNormService,
    DeleteNormPort deleteNormPort,
    LdmlDeValidator ldmlDeValidator,
    LoadNormExpressionElisPort loadNormExpressionElisPort,
    LdmlDeElementSorter ldmlDeElementSorter,
    PretextCleanupService pretextCleanupService
  ) {
    this.updateOrSaveNormPort = updateOrSaveNormPort;
    this.normService = normService;
    this.createNewVersionOfNormService = createNewVersionOfNormService;
    this.deleteNormPort = deleteNormPort;
    this.ldmlDeValidator = ldmlDeValidator;
    this.loadNormExpressionElisPort = loadNormExpressionElisPort;
    this.ldmlDeElementSorter = ldmlDeElementSorter;
    this.pretextCleanupService = pretextCleanupService;
  }

  /**
   * Queue the expression of the given norm for publishing.
   * <p></p>
   * Previous releases of the same norm of the current date are replaced by this release. Also, new
   * unpublished manifestations are created for the norm to enable further work on the expression.
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

    List<Norm> workingCopies = new ArrayList<>();
    manifestationsToPublish.forEach(manifestationToPublish -> {
      final NormManifestationEli oldManifestationEli = manifestationToPublish.getManifestationEli();
      setNewStandDerBearbeitung(options.releaseType(), manifestationToPublish);
      final Norm workingCopy = createNewVersionOfNormService.createNewManifestation(
        manifestationToPublish,
        Norm.WORKING_COPY_DATE
      );
      ldmlDeElementSorter.sortElements(
        workingCopy.getRegelungstext1().getDocument().getDocumentElement()
      );
      ldmlDeValidator.validateXSDSchema(workingCopy);
      ldmlDeValidator.validateSchematron(workingCopy);
      updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Options(workingCopy));
      workingCopies.add(workingCopy);

      if (manifestationToPublish.getReleaseType() == ReleaseType.PRAETEXT_RELEASED) {
        pretextCleanupService.clean(manifestationToPublish);
      }
      manifestationToPublish.setManifestationDateTo(LocalDate.now());
      ldmlDeElementSorter.sortElements(
        manifestationToPublish.getRegelungstext1().getDocument().getDocumentElement()
      );
      ldmlDeValidator.validateXSDSchema(manifestationToPublish);
      ldmlDeValidator.validateSchematron(manifestationToPublish);
      manifestationToPublish.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);
      // The manifestationToPublish might create a new point-in-time-manifestation thus a new norm might be created.
      final Norm updatedNorm = updateOrSaveNormPort.updateOrSave(
        new UpdateOrSaveNormPort.Options(manifestationToPublish)
      );
      final boolean newManifestationWasCreatedOnSave =
        !updatedNorm.getManifestationEli().equals(oldManifestationEli) &&
        // Do not delete the working copy
        !Norm.WORKING_COPY_DATE.isEqual(oldManifestationEli.getPointInTimeManifestation());
      if (newManifestationWasCreatedOnSave) {
        deleteNormPort.deleteNorm(
          new DeleteNormPort.Options(oldManifestationEli, NormPublishState.UNPUBLISHED)
        );
      }
    });

    return workingCopies;
  }

  private static void setNewStandDerBearbeitung(ReleaseType targetReleaseType, Norm norm) {
    ReleaseType currentReleaseType = norm.getReleaseType();

    // PRAETEXT_RELEASED and VOLLDOKUMENTATION_RELEASED are not relevant as they remain unchanged.
    if (
      targetReleaseType == ReleaseType.PRAETEXT_RELEASED &&
      currentReleaseType == ReleaseType.NOT_RELEASED
    ) {
      norm.setReleaseType(ReleaseType.PRAETEXT_RELEASED);
    }

    if (targetReleaseType == ReleaseType.VOLLDOKUMENTATION_RELEASED) {
      // in all cases set to VOLLDOKUMENTATION_RELEASED
      norm.setReleaseType(ReleaseType.VOLLDOKUMENTATION_RELEASED);
    }
  }
}
