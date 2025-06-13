package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class ReleaseService
  implements ReleaseAllNormExpressionsUseCase, LoadReleasesByNormExpressionEliUseCase {

  private final UpdateOrSaveNormPort updateOrSaveNormPort;
  private final NormService normService;
  private final CreateNewVersionOfNormService createNewVersionOfNormService;
  private final DeleteNormPort deleteNormPort;
  private final LdmlDeValidator ldmlDeValidator;
  private final LoadReleasesByNormExpressionEliPort loadReleasesByNormExpressionEliPort;
  private final LoadNormExpressionElisPort loadNormExpressionElisPort;
  private final LdmlDeElementSorter ldmlDeElementSorter;

  public ReleaseService(
    UpdateOrSaveNormPort updateOrSaveNormPort,
    NormService normService,
    CreateNewVersionOfNormService createNewVersionOfNormService,
    DeleteNormPort deleteNormPort,
    LdmlDeValidator ldmlDeValidator,
    LoadReleasesByNormExpressionEliPort loadReleasesByNormExpressionEliPort,
    LoadNormExpressionElisPort loadNormExpressionElisPort,
    LdmlDeElementSorter ldmlDeElementSorter
  ) {
    this.updateOrSaveNormPort = updateOrSaveNormPort;
    this.normService = normService;
    this.createNewVersionOfNormService = createNewVersionOfNormService;
    this.deleteNormPort = deleteNormPort;
    this.ldmlDeValidator = ldmlDeValidator;
    this.loadReleasesByNormExpressionEliPort = loadReleasesByNormExpressionEliPort;
    this.loadNormExpressionElisPort = loadNormExpressionElisPort;
    this.ldmlDeElementSorter = ldmlDeElementSorter;
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
  public Release release(ReleaseAllNormExpressionsUseCase.@NonNull Options options) {
    // all expression elis of the norm to publish
    List<NormExpressionEli> allExpressionElis = loadNormExpressionElisPort.loadNormExpressionElis(
      new LoadNormExpressionElisPort.Options(options.eli())
    );

    List<Norm> manifestationsToPublish = allExpressionElis
      .stream()
      // we assume the latest expression is the working copy
      .map(expressionEli -> normService.loadNorm(new LoadNormUseCase.EliOptions(expressionEli)))
      .filter(norm -> NormPublishState.UNPUBLISHED.equals(norm.getPublishState()))
      .toList();

    manifestationsToPublish.forEach(manifestationToPublish -> {
      final NormManifestationEli oldManifestationEli = manifestationToPublish.getManifestationEli();
      setNewStandDerBearbeitung(options.releaseType(), manifestationToPublish);
      //TODO next iteration: Generate new GUID for FRBRExpression/FRBRalias/aktuelle-version-id/@value and set it as nachfolgende-version-id in the previous expression
      final Norm workingCopy = createNewVersionOfNormService.createNewManifestation(
        manifestationToPublish,
        LocalDate.of(2999, 12, 31)
      );
      ldmlDeElementSorter.sortElements(
        workingCopy.getRegelungstext1().getDocument().getDocumentElement()
      );
      ldmlDeValidator.validateXSDSchema(workingCopy);
      ldmlDeValidator.validateSchematron(workingCopy);
      updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Options(workingCopy));

      if (manifestationToPublish.getReleaseType() == ReleaseType.PRAETEXT_RELEASED) {} // TODO Clean metadata
      setManifestationDateToCurrentDate(manifestationToPublish);
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
        oldManifestationEli
          .getPointInTimeManifestation()
          .isBefore(workingCopy.getManifestationEli().getPointInTimeManifestation());
      if (newManifestationWasCreatedOnSave) {
        deleteNormPort.deleteNorm(
          new DeleteNormPort.Options(oldManifestationEli, NormPublishState.UNPUBLISHED)
        );
      }
    });

    return Release.builder()
      .publishedNorms(manifestationsToPublish)
      .releasedAt(Instant.now())
      .build();
  }

  // TODO this is just a copy from CreateNewVersionOfNormService might this be moved to Norm?
  private void setManifestationDateToCurrentDate(Norm manifestationToPublish) {
    var newManifestationEli = NormManifestationEli.fromExpressionEli(
      manifestationToPublish.getExpressionEli(),
      LocalDate.now()
    );
    manifestationToPublish
      .getDokumente()
      .forEach(dokument ->
        setNewManifestationMetadata(
          dokument,
          DokumentManifestationEli.fromNormEli(
            newManifestationEli,
            dokument.getManifestationEli().getSubtype(),
            dokument.getManifestationEli().getFormat()
          )
        )
      );
  }

  /**
   * Sets the metadata for a new manifestation based on the eli.
   * @param dokument a dokument of the new manifestation
   * @param manifestationEli the new eli for the manifestation
   */
  private void setNewManifestationMetadata(
    Dokument dokument,
    DokumentManifestationEli manifestationEli
  ) {
    var manifestation = dokument.getMeta().getFRBRManifestation();
    manifestation.setEli(manifestationEli);
    manifestation.setURI(manifestationEli.toUri());
    manifestation.setFBRDate(
      manifestationEli.getPointInTimeManifestation().format(DateTimeFormatter.ISO_LOCAL_DATE),
      "generierung"
    );
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

    // in all cases set to VOLLDOKUMENTATION_RELEASED
    if (targetReleaseType == ReleaseType.VOLLDOKUMENTATION_RELEASED) {
      norm.setReleaseType(ReleaseType.VOLLDOKUMENTATION_RELEASED);
    }
  }

  @Override
  public List<Release> loadReleasesByNormExpressionEli(
    LoadReleasesByNormExpressionEliUseCase.Options options
  ) {
    return loadReleasesByNormExpressionEliPort.loadReleasesByNormExpressionEli(
      new LoadReleasesByNormExpressionEliPort.Options(options.eli())
    );
  }
}
