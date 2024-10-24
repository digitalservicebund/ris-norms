package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteQueuedNormsPort;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteUnpublishedNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateAnnouncementPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.TextualMod;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
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
  private final UpdateAnnouncementPort updateAnnouncementPort;
  private final UpdateOrSaveNormPort updateOrSaveNormPort;
  private final NormService normService;
  private final TimeMachineService timeMachineService;
  private final CreateNewVersionOfNormService createNewVersionOfNormService;
  private final DeleteQueuedNormsPort deleteQueuedNormsPort;
  private final DeleteUnpublishedNormPort deleteUnpublishedNormPort;

  public ReleaseService(
    LoadAnnouncementByNormEliUseCase loadAnnouncementByNormEliUseCase,
    UpdateAnnouncementPort updateAnnouncementPort,
    UpdateOrSaveNormPort updateOrSaveNormPort,
    NormService normService,
    TimeMachineService timeMachineService,
    CreateNewVersionOfNormService createNewVersionOfNormService,
    DeleteQueuedNormsPort deleteQueuedNormsPort,
    DeleteUnpublishedNormPort deleteUnpublishedNormPort
  ) {
    this.loadAnnouncementByNormEliUseCase = loadAnnouncementByNormEliUseCase;
    this.updateAnnouncementPort = updateAnnouncementPort;
    this.updateOrSaveNormPort = updateOrSaveNormPort;
    this.normService = normService;
    this.timeMachineService = timeMachineService;
    this.createNewVersionOfNormService = createNewVersionOfNormService;
    this.deleteQueuedNormsPort = deleteQueuedNormsPort;
    this.deleteUnpublishedNormPort = deleteUnpublishedNormPort;
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

    // Delete the files from a previous release of the same announcement if they are still queued for publishing.
    // This is to prevent residual files from remaining if some time boundaries changed and this release will create
    // different expressions.
    normsToPublish.forEach(norm ->
      deleteQueuedNormsPort.deleteQueuedForPublishNorms(
        new DeleteQueuedNormsPort.Command(norm.getWorkEli())
      )
    );

    allVersionsOfAllNormsToPublish.forEach(norm ->
      norm.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH)
    );
    allVersionsOfAllNormsToPublish.forEach(norm ->
      updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Command(norm))
    );

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
      deleteUnpublishedNormPort.deleteUnpublishedNorm(
        new DeleteUnpublishedNormPort.Command(norm.getManifestationEli())
      );
      updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Command(nextManifestationOfNorm));
    });

    announcement.setReleasedByDocumentalistAt(Instant.now());
    updateAnnouncementPort.updateAnnouncement(new UpdateAnnouncementPort.Command(announcement));
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
