package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteQueuedReleasesPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadReleasesByNormExpressionEliPort;
import de.bund.digitalservice.ris.norms.application.port.output.SaveReleasePort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.Release;
import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports. It is annotated with {@link Service} to indicate that it's a service
 * component in the Spring context.
 */
@Service
public class ReleaseService
  implements ReleaseNormExpressionUseCase, LoadReleasesByNormExpressionEliUseCase {

  private final UpdateOrSaveNormPort updateOrSaveNormPort;
  private final NormService normService;
  private final CreateNewVersionOfNormService createNewVersionOfNormService;
  private final DeleteNormPort deleteNormPort;
  private final SaveReleasePort saveReleasePort;
  private final DeleteQueuedReleasesPort deleteQueuedReleasesPort;
  private final LdmlDeValidator ldmlDeValidator;
  private final LoadReleasesByNormExpressionEliPort loadReleasesByNormExpressionEliPort;

  public ReleaseService(
    UpdateOrSaveNormPort updateOrSaveNormPort,
    NormService normService,
    CreateNewVersionOfNormService createNewVersionOfNormService,
    DeleteNormPort deleteNormPort,
    SaveReleasePort saveReleasePort,
    DeleteQueuedReleasesPort deleteQueuedReleasesPort,
    LdmlDeValidator ldmlDeValidator,
    LoadReleasesByNormExpressionEliPort loadReleasesByNormExpressionEliPort
  ) {
    this.updateOrSaveNormPort = updateOrSaveNormPort;
    this.normService = normService;
    this.createNewVersionOfNormService = createNewVersionOfNormService;
    this.deleteNormPort = deleteNormPort;
    this.saveReleasePort = saveReleasePort;
    this.deleteQueuedReleasesPort = deleteQueuedReleasesPort;
    this.ldmlDeValidator = ldmlDeValidator;
    this.loadReleasesByNormExpressionEliPort = loadReleasesByNormExpressionEliPort;
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
  public Release releaseNormExpression(ReleaseNormExpressionUseCase.Options options) {
    var normToPublish = normService.loadNorm(new LoadNormUseCase.EliOptions(options.eli()));

    // Delete the files from a previous release of the same norm if they are still queued for publishing.
    var deletedReleases = deleteQueuedReleasesPort.deleteQueuedReleases(
      new DeleteQueuedReleasesPort.Options(options.eli())
    );
    deletedReleases
      .stream()
      .flatMap(release -> release.getPublishedNorms().stream())
      .forEach(queuedNorm ->
        deleteNormPort.deleteNorm(
          new DeleteNormPort.Options(
            queuedNorm.getManifestationEli(),
            NormPublishState.QUEUED_FOR_PUBLISH
          )
        )
      );

    // We need to publish the manifestation of the norm with the correct "nachfolgende-version-id", therefore we
    // use the manifestation created from createNewExpression of the next time boundary if it exists
    Norm manifestationToPublish = createNewVersionOfNormService.createNewManifestation(
      normToPublish
    );

    // Validate all resulting versions
    ldmlDeValidator.validateXSDSchema(manifestationToPublish);
    ldmlDeValidator.validateSchematron(manifestationToPublish);

    manifestationToPublish.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);
    updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Options(manifestationToPublish));

    // We need the new manifestations to use tomorrow's date so they do have a different eli to the once just
    // published. When they are published the manifestation eli will be updated again anyway so the date is not really
    // important as long as it is the newest date of any manifestation of the same expression.
    var nextManifestationOfNorm = createNewVersionOfNormService.createNewManifestation(
      normToPublish,
      LocalDate.now().plusDays(1)
    );
    // delete the old unpublished manifestation so no residual data remains in the database
    deleteNormPort.deleteNorm(
      new DeleteNormPort.Options(normToPublish.getManifestationEli(), NormPublishState.UNPUBLISHED)
    );
    updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Options(nextManifestationOfNorm));

    var release = Release.builder()
      .publishedNorms(List.of(manifestationToPublish))
      .releasedAt(Instant.now())
      .build();

    return saveReleasePort.saveRelease(new SaveReleasePort.Options(release));
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
