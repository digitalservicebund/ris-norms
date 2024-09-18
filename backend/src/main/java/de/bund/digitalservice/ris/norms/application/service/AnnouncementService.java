package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.*;
import de.bund.digitalservice.ris.norms.application.port.input.CreateAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAllAnnouncementsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAnnouncementByNormEliUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetNormsAffectedByAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadZf0UseCase;
import de.bund.digitalservice.ris.norms.application.port.output.*;
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
import java.util.UUID;
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
  implements
    LoadAllAnnouncementsUseCase,
    LoadAnnouncementByNormEliUseCase,
    LoadTargetNormsAffectedByAnnouncementUseCase,
    CreateAnnouncementUseCase {

  private final LoadAllAnnouncementsPort loadAllAnnouncementsPort;
  private final LoadAnnouncementByNormEliPort loadAnnouncementByNormEliPort;
  private final LoadNormPort loadNormPort;
  private final LoadNormByGuidPort loadNormByGuidPort;
  private final LoadZf0Service loadZf0Service;
  private final UpdateOrSaveAnnouncementPort updateOrSaveAnnouncementPort;
  private final BillToActService billToActService;
  private final LdmlDeValidator ldmlDeValidator;
  private final DeleteAnnouncementByNormEliPort deleteAnnouncementByNormEliPort;
  private final DeleteNormByGuidPort deleteNormByGuidPort;

  public AnnouncementService(
    LoadAllAnnouncementsPort loadAllAnnouncementsPort,
    LoadAnnouncementByNormEliPort loadAnnouncementByNormEliPort,
    LoadNormPort loadNormPort,
    LoadNormByGuidPort loadNormByGuidPort,
    LoadZf0Service loadZf0Service,
    UpdateOrSaveAnnouncementPort updateOrSaveAnnouncementPort,
    BillToActService billToActService,
    LdmlDeValidator ldmlDeValidator,
    DeleteAnnouncementByNormEliPort deleteAnnouncementByNormEliPort,
    DeleteNormByGuidPort deleteNormByGuidPort
  ) {
    this.loadAllAnnouncementsPort = loadAllAnnouncementsPort;
    this.loadAnnouncementByNormEliPort = loadAnnouncementByNormEliPort;
    this.loadNormPort = loadNormPort;
    this.loadNormByGuidPort = loadNormByGuidPort;
    this.loadZf0Service = loadZf0Service;
    this.updateOrSaveAnnouncementPort = updateOrSaveAnnouncementPort;
    this.billToActService = billToActService;
    this.ldmlDeValidator = ldmlDeValidator;
    this.deleteAnnouncementByNormEliPort = deleteAnnouncementByNormEliPort;
    this.deleteNormByGuidPort = deleteNormByGuidPort;
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
    LoadTargetNormsAffectedByAnnouncementUseCase.Query query
  ) {
    final Norm amendingNorm =
      this.loadAnnouncementByNormEli(new LoadAnnouncementByNormEliUseCase.Query(query.eli()))
        .getNorm();

    return amendingNorm
      .getArticles()
      .stream()
      .filter(article ->
        article.getRefersTo().isPresent() &&
        !article.getRefersTo().get().equals("geltungszeitregel")
      )
      .map(article -> {
        final Norm targetLaw = loadNormPort
          .loadNorm(new LoadNormPort.Command(article.getMandatoryAffectedDocumentEli()))
          .orElseThrow(() -> new NormNotFoundException(article.getMandatoryAffectedDocumentEli()));
        return loadZf0Service.loadOrCreateZf0(
          new LoadZf0UseCase.Query(amendingNorm, targetLaw, true)
        );
      })
      .toList();
  }

  @Override
  public Announcement createAnnouncement(CreateAnnouncementUseCase.Query query) throws IOException {
    if (query.file().isEmpty() || !"text/xml".equals(query.file().getContentType())) {
      throw new NotAXmlFileException(
        query.file().getOriginalFilename(),
        query.file().getContentType()
      );
    }

    var xmlString = IOUtils.toString(query.file().getInputStream(), Charset.defaultCharset());
    var document = XmlMapper.toDocument(xmlString);

    if (!document.getDocumentElement().getTagName().equals("akn:akomaNtoso")) {
      throw new NotLdmlDeXmlFileException(query.file().getOriginalFilename());
    }
    final String actString = billToActService.convert(document);

    // it throws an exception if the validation fails or the LDML.de Version is not 1.6
    // we can at the moment not use the resulting norm as it is namespace-aware and our xPaths are
    // not yet.
    var validatedNorm = ldmlDeValidator.parseAndValidate(actString);
    ldmlDeValidator.validateSchematron(validatedNorm);

    var norm = Norm.builder().document(XmlMapper.toDocument(xmlString)).build();

    var activeMods = norm
      .getMeta()
      .getAnalysis()
      .map(Analysis::getActiveModifications)
      .orElseGet(List::of);
    var activeModDestinationElis = activeMods
      .stream()
      .map(TextualMod::getDestinationHref)
      .flatMap(Optional::stream)
      .map(Href::getEli)
      .flatMap(Optional::stream)
      .collect(Collectors.toSet());

    activeModDestinationElis.forEach(eli -> {
      if (loadNormPort.loadNorm(new LoadNormPort.Command(eli)).isEmpty()) {
        throw new ActiveModDestinationNormNotFoundException(norm.getEli(), eli);
      }
    });

    final boolean normExists = loadNormPort
      .loadNorm(new LoadNormPort.Command(norm.getEli()))
      .isPresent();

    if (normExists && !query.force()) {
      throw new NormExistsAlreadyException(norm.getEli());
    }

    if (normExists) {
      deleteAnnouncementByNormEliPort.deleteAnnouncementByNormEli(
        new DeleteAnnouncementByNormEliPort.Command(norm.getEli())
      );
      activeModDestinationElis.forEach(eli ->
        loadNormPort
          .loadNorm(new LoadNormPort.Command(eli))
          .ifPresent(targetNorm -> {
            final UUID uuid = targetNorm.getMeta().getFRBRExpression().getFRBRaliasNextVersionId();
            deleteNormByGuidPort.loadNormByGuid(new DeleteNormByGuidPort.Command(uuid));
          })
      );
    } else if (
      loadNormByGuidPort.loadNormByGuid(new LoadNormByGuidPort.Command(norm.getGuid())).isPresent()
    ) {
      throw new NormWithGuidAlreadyExistsException(norm.getGuid());
    }

    var announcement = Announcement.builder().norm(norm).build();
    updateOrSaveAnnouncementPort.updateOrSaveAnnouncement(
      new UpdateOrSaveAnnouncementPort.Command(announcement)
    );

    return announcement;
  }
}
