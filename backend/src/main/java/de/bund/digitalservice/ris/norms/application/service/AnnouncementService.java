package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadAllAnnouncementsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNextVersionOfNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetNormsAffectedByAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAllAnnouncementsPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAnnouncementPort;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports. It is annotated with {@link Service} to indicate that it's a service
 * component in the Spring context.
 */
@Service
public class AnnouncementService
    implements LoadAllAnnouncementsUseCase,
        LoadAnnouncementUseCase,
        LoadTargetNormsAffectedByAnnouncementUseCase {
  private final LoadAllAnnouncementsPort loadAllAnnouncementsPort;
  private final LoadAnnouncementPort loadAnnouncementPort;
  private final NormService normService;

  public AnnouncementService(
      LoadAllAnnouncementsPort loadAllAnnouncementsPort,
      LoadAnnouncementPort loadAnnouncementPort,
      NormService normService) {
    this.loadAllAnnouncementsPort = loadAllAnnouncementsPort;
    this.loadAnnouncementPort = loadAnnouncementPort;
    this.normService = normService;
  }

  @Override
  public List<Announcement> loadAllAnnouncements() {
    return loadAllAnnouncementsPort.loadAllAnnouncements();
  }

  @Override
  public Optional<Announcement> loadAnnouncement(LoadAnnouncementUseCase.Query query) {
    return loadAnnouncementPort.loadAnnouncement(new LoadAnnouncementPort.Command(query.eli()));
  }

  @Override
  public List<Norm> loadTargetNormsAffectedByAnnouncement(
      LoadTargetNormsAffectedByAnnouncementUseCase.Query query) {
    return this.loadAnnouncement(new LoadAnnouncementUseCase.Query(query.eli())).stream()
        .map(Announcement::getNorm)
        .flatMap(norm -> norm.getArticles().stream())
        .flatMap(article -> article.getAffectedDocumentEli().stream())
        .flatMap(
            affectedDocumentEli ->
                normService
                    .loadNextVersionOfNorm(
                        new LoadNextVersionOfNormUseCase.Query(affectedDocumentEli))
                    .stream())
        .toList();
  }
}
