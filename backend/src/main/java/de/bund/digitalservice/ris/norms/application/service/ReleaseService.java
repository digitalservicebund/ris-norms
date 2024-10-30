package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteQueuedReleasesPort;
import de.bund.digitalservice.ris.norms.application.port.output.SaveReleaseToAnnouncementPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.domain.entity.Release;
import de.bund.digitalservice.ris.norms.domain.entity.TextualMod;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports. It is annotated with {@link Service} to indicate that it's a service
 * component in the Spring context.
 */
@Service
public class ReleaseService implements ReleaseAnnouncementUseCase {

  private final LoadAnnouncementByNormEliUseCase loadAnnouncementByNormEliUseCase;
  private final UpdateOrSaveNormPort updateOrSaveNormPort;
  private final NormService normService;
  private final TimeMachineService timeMachineService;
  private final CreateNewVersionOfNormService createNewVersionOfNormService;
  private final DeleteNormPort deleteNormPort;
  private final SaveReleaseToAnnouncementPort saveReleaseToAnnouncementPort;
  private final DeleteQueuedReleasesPort deleteQueuedReleasesPort;
  private final LdmlDeValidator ldmlDeValidator;

  public ReleaseService(
    LoadAnnouncementByNormEliUseCase loadAnnouncementByNormEliUseCase,
    UpdateOrSaveNormPort updateOrSaveNormPort,
    NormService normService,
    TimeMachineService timeMachineService,
    CreateNewVersionOfNormService createNewVersionOfNormService,
    DeleteNormPort deleteNormPort,
    SaveReleaseToAnnouncementPort saveReleaseToAnnouncementPort,
    DeleteQueuedReleasesPort deleteQueuedReleasesPort,
    LdmlDeValidator ldmlDeValidator
  ) {
    this.loadAnnouncementByNormEliUseCase = loadAnnouncementByNormEliUseCase;
    this.updateOrSaveNormPort = updateOrSaveNormPort;
    this.normService = normService;
    this.timeMachineService = timeMachineService;
    this.createNewVersionOfNormService = createNewVersionOfNormService;
    this.deleteNormPort = deleteNormPort;
    this.saveReleaseToAnnouncementPort = saveReleaseToAnnouncementPort;
    this.deleteQueuedReleasesPort = deleteQueuedReleasesPort;
    this.ldmlDeValidator = ldmlDeValidator;
  }

  /**
   * Queue all norms that are affected by the given announcement for publishing.
   * This includes generating a new manifestation of the amending norm, and new expressions for each time boundary of
   * every target norm. It also creates a new manifestation for the current expression of the target norm so it contains
   * the information about the future changes and the correct "nachfolgende-version-id".
   * <p></p>
   * Previous releases of the same announcement of the current date are replaced by this release. Also, new
   * unpublished manifestations are created for the amending norm and the current expression of the target norms to
   * enable further work on the announcement.
   * <p></p>
   * NOTE: This is currently always creating new expressions for every time boundary (so what is necessary for the
   * prätext-abgabe). When only metadata has changed to the previous abgabe no new expressions should be generated. This
   * is not handled by this method yet.
   *
   * @param query The query specifying the {@link Announcement} to be loaded.
   * @return The information about the announcement published.
   */
  @Override
  @Transactional
  public Announcement releaseAnnouncement(ReleaseAnnouncementUseCase.Query query) {
    var announcement = loadAnnouncementByNormEliUseCase.loadAnnouncementByNormEli(
      new LoadAnnouncementByNormEliUseCase.Query(query.eli())
    );

    // find all norms to publish
    Set<Norm> normsToPublish = new HashSet<>();
    normsToPublish.add(normService.loadNorm(new LoadNormUseCase.Query(announcement.getEli())));

    for (Norm norm : normsToPublish) {
      var analysis = norm.getMeta().getAnalysis();
      if (analysis.isEmpty()) {
        break;
      }

      analysis
        .get()
        .getActiveModifications()
        .stream()
        .map(TextualMod::getDestinationHref)
        .flatMap(Optional::stream)
        .map(Href::getExpressionEli)
        .flatMap(Optional::stream)
        .map(eli -> normService.loadNorm(new LoadNormUseCase.Query(eli)))
        .forEach(normsToPublish::add);
    }

    // Delete the files from a previous release of the same announcement if they are still queued for publishing.
    // This is to prevent residual files from remaining if some time boundaries changed and this release will create
    // different expressions.
    var deletedReleases = deleteQueuedReleasesPort.deleteQueuedReleases(
      new DeleteQueuedReleasesPort.Command(announcement.getEli())
    );
    deletedReleases
      .stream()
      .flatMap(release -> release.getPublishedNorms().stream())
      .forEach(queuedNorm ->
        deleteNormPort.deleteNorm(
          new DeleteNormPort.Command(
            queuedNorm.getManifestationEli(),
            NormPublishState.QUEUED_FOR_PUBLISH
          )
        )
      );

    // generate all future versions for all norms that will be published
    Set<Norm> allVersionsOfAllNormsToPublish = new HashSet<>();
    for (Norm norm : normsToPublish) {
      // We need to publish the manifestation of the norm with the correct "nachfolgende-version-id", therefore we
      // use the manifestation created from createNewExpression of the next time boundary if it exists
      Norm latestNormExpression = createNewVersionOfNormService.createNewManifestation(norm);

      var dates = norm
        .getMeta()
        .getAnalysis()
        .stream()
        .flatMap(analysis -> analysis.getPassiveModifications().stream())
        .flatMap(textualMod -> textualMod.getForcePeriodEid().stream())
        .map(norm::getStartDateForTemporalGroup)
        .flatMap(Optional::stream)
        .map(LocalDate::parse)
        .distinct()
        .sorted();

      for (LocalDate date : dates.toList()) {
        var result = createNewVersionOfNormService.createNewExpression(latestNormExpression, date);
        allVersionsOfAllNormsToPublish.add(result.newManifestationOfOldExpression());

        latestNormExpression =
        timeMachineService.applyPassiveModifications(
          new ApplyPassiveModificationsUseCase.Query(
            result.newExpression(),
            date.atStartOfDay(ZoneId.systemDefault()).toInstant()
          )
        );
      }

      allVersionsOfAllNormsToPublish.add(latestNormExpression);
    }

    // Validate all resulting versions
    allVersionsOfAllNormsToPublish.forEach(norm -> {
      ldmlDeValidator.parseAndValidate(XmlMapper.toString(norm.getDocument()));
      ldmlDeValidator.validateSchematron(norm);
    });

    allVersionsOfAllNormsToPublish.forEach(norm -> {
      norm.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);
      updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Command(norm));
    });

    // Create new manifestations for the expressions that are directly targeted by this announcement (so not the once
    // only created by the time-machine, but all the other once) so the newest manifestation is still unpublished and
    // can be edited further
    normsToPublish.forEach(norm -> {
      // We need the new manifestations to use tomorrow's date so they do have a different eli to the once just
      // published. When they are published the manifestation eli will be updated again anyway so the date is not really
      // important as long as it is the newest date of any manifestation of the same expression.
      var nextManifestationOfNorm = createNewVersionOfNormService.createNewManifestation(
        norm,
        LocalDate.now().plusDays(1)
      );
      // delete the old unpublished manifestation so no residual data remains in the database
      deleteNormPort.deleteNorm(
        new DeleteNormPort.Command(norm.getManifestationEli(), NormPublishState.UNPUBLISHED)
      );
      updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Command(nextManifestationOfNorm));
    });

    var release = Release
      .builder()
      .publishedNorms(allVersionsOfAllNormsToPublish.stream().toList())
      .releasedAt(Instant.now())
      .build();
    announcement.addRelease(release);

    saveReleaseToAnnouncementPort.saveReleaseToAnnouncement(
      new SaveReleaseToAnnouncementPort.Command(release, announcement)
    );

    return announcement;
  }

  /**
   * Takes a norm and makes any changes needed in preparation for publishing it.
   *
   * @param normToBeReleased Norm that will be released
   */
  public void prepareForRelease(final Norm normToBeReleased) {
    normToBeReleased.getMeta().getProprietary().ifPresent(this::removePrivateMetadata);
  }

  private void removePrivateMetadata(final Proprietary proprietary) {
    // Organisationseinheit
    var matches = NodeParser.getNodesFromExpression(
      "//organisationsEinheit",
      proprietary.getNode()
    );

    matches.forEach(match -> match.getParentNode().removeChild(match));
  }
}
