package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadAllAnnouncementsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAnnouncementByNormEliUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNextVersionOfNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetNormsAffectedByAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAllAnnouncementsPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAnnouncementByNormEliPort;
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
public class AnnouncementByNormEliService
    implements LoadAllAnnouncementsUseCase,
        LoadAnnouncementByNormEliUseCase,
        LoadTargetNormsAffectedByAnnouncementUseCase {
  private final LoadAllAnnouncementsPort loadAllAnnouncementsPort;
  private final LoadAnnouncementByNormEliPort loadAnnouncementByNormEliPort;
  private final NormService normService;

  public AnnouncementByNormEliService(
      LoadAllAnnouncementsPort loadAllAnnouncementsPort,
      LoadAnnouncementByNormEliPort loadAnnouncementByNormEliPort,
      NormService normService) {
    this.loadAllAnnouncementsPort = loadAllAnnouncementsPort;
    this.loadAnnouncementByNormEliPort = loadAnnouncementByNormEliPort;
    this.normService = normService;
  }

  @Override
  public List<Announcement> loadAllAnnouncements() {
    return loadAllAnnouncementsPort.loadAllAnnouncements();
  }

  @Override
  public Optional<Announcement> loadAnnouncementByNormEli(
      LoadAnnouncementByNormEliUseCase.Query query) {
    return loadAnnouncementByNormEliPort.loadAnnouncementByNormEli(
        new LoadAnnouncementByNormEliPort.Command(query.eli()));
  }

  @Override
  public List<Norm> loadTargetNormsAffectedByAnnouncement(
      LoadTargetNormsAffectedByAnnouncementUseCase.Query query) {
    return this.loadAnnouncementByNormEli(new LoadAnnouncementByNormEliUseCase.Query(query.eli()))
        .stream()
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
