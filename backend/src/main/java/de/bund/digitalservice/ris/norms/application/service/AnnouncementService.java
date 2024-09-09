package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.ActiveModDestinationNormNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.AnnouncementNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.NormExistsAlreadyException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotAnActException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.NotAXmlFileException;
import de.bund.digitalservice.ris.norms.application.port.input.CreateAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAllAnnouncementsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAnnouncementByNormEliUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetNormsAffectedByAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadZf0UseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAllAnnouncementsPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAnnouncementByNormEliPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveAnnouncementPort;
import de.bund.digitalservice.ris.norms.domain.entity.Analysis;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.TextualMod;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
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
        LoadTargetNormsAffectedByAnnouncementUseCase,
        CreateAnnouncementUseCase {
  private final LoadAllAnnouncementsPort loadAllAnnouncementsPort;
  private final LoadAnnouncementByNormEliPort loadAnnouncementByNormEliPort;
  private final LoadNormPort loadNormPort;
  private final LoadZf0Service loadZf0Service;
  private final UpdateOrSaveAnnouncementPort updateOrSaveAnnouncementPort;

  public AnnouncementService(
      LoadAllAnnouncementsPort loadAllAnnouncementsPort,
      LoadAnnouncementByNormEliPort loadAnnouncementByNormEliPort,
      LoadNormPort loadNormPort,
      LoadZf0Service loadZf0Service,
      UpdateOrSaveAnnouncementPort updateOrSaveAnnouncementPort) {
    this.loadAllAnnouncementsPort = loadAllAnnouncementsPort;
    this.loadAnnouncementByNormEliPort = loadAnnouncementByNormEliPort;
    this.loadNormPort = loadNormPort;
    this.loadZf0Service = loadZf0Service;
    this.updateOrSaveAnnouncementPort = updateOrSaveAnnouncementPort;
  }

  @Override
  public List<Announcement> loadAllAnnouncements() {
    return loadAllAnnouncementsPort.loadAllAnnouncements();
  }

  @Override
  public Announcement loadAnnouncementByNormEli(LoadAnnouncementByNormEliUseCase.Query query) {
    return loadAnnouncementByNormEliPort
        .loadAnnouncementByNormEli(new LoadAnnouncementByNormEliPort.Command(query.eli()))
        .orElseThrow(() -> new AnnouncementNotFoundException(query.eli()));
  }

  @Override
  public List<Norm> loadTargetNormsAffectedByAnnouncement(
      LoadTargetNormsAffectedByAnnouncementUseCase.Query query) {
    final Norm amendingNorm =
        this.loadAnnouncementByNormEli(new LoadAnnouncementByNormEliUseCase.Query(query.eli()))
            .getNorm();

    return amendingNorm.getArticles().stream()
        .filter(
            article ->
                article.getRefersTo().isPresent()
                    && !article.getRefersTo().get().equals("geltungszeitregel"))
        .map(
            article -> {
              final Norm targetLaw =
                  loadNormPort
                      .loadNorm(new LoadNormPort.Command(article.getMandatoryAffectedDocumentEli()))
                      .orElseThrow(
                          () ->
                              new NormNotFoundException(article.getMandatoryAffectedDocumentEli()));
              return loadZf0Service.loadOrCreateZf0(
                  new LoadZf0UseCase.Query(amendingNorm, targetLaw, true));
            })
        .toList();
  }

  @Override
  public Announcement createAnnouncement(CreateAnnouncementUseCase.Query query) throws IOException {
    if (query.file().isEmpty() || !"text/xml".equals(query.file().getContentType())) {
      throw new NotAXmlFileException();
    }

    var xmlString = IOUtils.toString(query.file().getInputStream(), Charset.defaultCharset());

    var norm = Norm.builder().document(XmlMapper.toDocument(xmlString)).build();

    if (!norm.isAct()) {
      throw new NormNotAnActException();
    }

    var activeMods =
        norm.getMeta().getAnalysis().map(Analysis::getActiveModifications).orElseGet(List::of);
    var activeModDestinationElis =
        activeMods.stream()
            .map(TextualMod::getDestinationHref)
            .flatMap(Optional::stream)
            .map(Href::getEli)
            .flatMap(Optional::stream)
            .collect(Collectors.toSet());

    activeModDestinationElis.forEach(
        eli -> {
          if (loadNormPort.loadNorm(new LoadNormPort.Command(eli)).isEmpty()) {
            throw new ActiveModDestinationNormNotFoundException(eli);
          }
        });

    if (loadNormPort.loadNorm(new LoadNormPort.Command(norm.getEli())).isPresent()) {
      throw new NormExistsAlreadyException(norm.getEli());
    }

    var announcement = Announcement.builder().norm(norm).build();
    updateOrSaveAnnouncementPort.updateOrSaveAnnouncement(
        new UpdateOrSaveAnnouncementPort.Command(announcement));

    return announcement;
  }
}
