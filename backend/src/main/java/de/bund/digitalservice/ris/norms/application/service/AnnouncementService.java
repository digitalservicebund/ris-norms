package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadAllAnnouncementsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAnnouncementByNormEliUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetNormsAffectedByAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadZf0UseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAllAnnouncementsPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAnnouncementByNormEliPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
        LoadAnnouncementByNormEliUseCase,
        LoadTargetNormsAffectedByAnnouncementUseCase {
  private final LoadAllAnnouncementsPort loadAllAnnouncementsPort;
  private final LoadAnnouncementByNormEliPort loadAnnouncementByNormEliPort;
  private final LoadNormPort loadNormPort;
  private final LoadZf0Service loadZf0Service;

  public AnnouncementService(
      LoadAllAnnouncementsPort loadAllAnnouncementsPort,
      LoadAnnouncementByNormEliPort loadAnnouncementByNormEliPort,
      LoadNormPort loadNormPort,
      LoadZf0Service loadZf0Service) {
    this.loadAllAnnouncementsPort = loadAllAnnouncementsPort;
    this.loadAnnouncementByNormEliPort = loadAnnouncementByNormEliPort;
    this.loadNormPort = loadNormPort;
    this.loadZf0Service = loadZf0Service;
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
    final Optional<Norm> optionalAmendingNorm =
        this.loadAnnouncementByNormEli(new LoadAnnouncementByNormEliUseCase.Query(query.eli()))
            .map(Announcement::getNorm);

    return optionalAmendingNorm
        .flatMap(
            m -> {
              List<Norm> norms =
                  m.getArticles().stream()
                      .map(
                          a -> {
                            final Optional<String> optionalTargetLawEli =
                                a.getAffectedDocumentEli();
                            final Optional<Norm> optionalTargetLaw =
                                optionalTargetLawEli.flatMap(
                                    targetLawEli ->
                                        loadNormPort.loadNorm(
                                            new LoadNormPort.Command(targetLawEli)));
                            return optionalTargetLaw
                                .map(
                                    targetLaw ->
                                        loadZf0Service.loadOrCreateZf0(
                                            new LoadZf0UseCase.Query(
                                                optionalAmendingNorm.get(), targetLaw, true)))
                                .orElse(null);
                          })
                      .filter(Objects::nonNull)
                      .toList();
              return Optional.of(norms);
            })
        .orElse(Collections.emptyList());
  }
}
