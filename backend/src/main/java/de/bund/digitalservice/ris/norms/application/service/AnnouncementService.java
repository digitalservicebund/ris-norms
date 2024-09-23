package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.*;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.Analysis;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.TextualMod;
import de.bund.digitalservice.ris.norms.utils.EidConsistencyGuardian;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

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
  private final UpdateNormPort updateNormPort;
  private final ReferenceService referenceService;

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
    DeleteNormByGuidPort deleteNormByGuidPort,
    UpdateNormPort updateNormPort,
    ReferenceService referenceService
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
    this.updateNormPort = updateNormPort;
    this.referenceService = referenceService;
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
    validateFileIsXml(query.file());
    var document = convertFileToDocument(query.file());
    final String actString = billToActService.convert(document);

    // it throws an exception if the validation fails or the LDML.de Version is not 1.6
    // we can at the moment not use the resulting norm as it is namespace-aware and our xPaths are
    // not yet.
    var validatedNorm = ldmlDeValidator.parseAndValidate(actString);
    ldmlDeValidator.validateSchematron(validatedNorm);

    var norm = Norm.builder().document(document).build();

    var activeModDestinationElis = getActiveModDestinationElis(norm);

    validateTargetNormsExist(activeModDestinationElis, norm);

    final boolean normFound = isNormRetrievableByEli(query.force(), norm);

    if (normFound && query.force()) {
      deleteAnnouncement(norm);
      deleteTargetNormsZf0(activeModDestinationElis);
    } else if (
      loadNormByGuidPort.loadNormByGuid(new LoadNormByGuidPort.Command(norm.getGuid())).isPresent()
    ) {
      throw new NormWithGuidAlreadyExistsException(norm.getGuid());
    }
    runPreProcessing(norm, activeModDestinationElis);
    return saveNewAnnouncement(norm);
  }

  private void validateFileIsXml(MultipartFile file) {
    if (file == null || file.isEmpty() || !"text/xml".equals(file.getContentType())) {
      throw new NotAXmlFileException(
        file != null ? file.getOriginalFilename() : "null",
        file != null ? file.getContentType() : "null"
      );
    }
  }

  private Document convertFileToDocument(MultipartFile file) throws IOException {
    var xmlString = IOUtils.toString(file.getInputStream(), Charset.defaultCharset());
    var document = XmlMapper.toDocument(xmlString);

    if (!document.getDocumentElement().getTagName().equals("akn:akomaNtoso")) {
      throw new NotLdmlDeXmlFileException(file.getOriginalFilename());
    }
    return document;
  }

  private Set<String> getActiveModDestinationElis(Norm norm) {
    var activeMods = norm
      .getMeta()
      .getAnalysis()
      .map(Analysis::getActiveModifications)
      .orElseGet(List::of);
    return activeMods
      .stream()
      .map(TextualMod::getDestinationHref)
      .flatMap(Optional::stream)
      .map(Href::getEli)
      .flatMap(Optional::stream)
      .collect(Collectors.toSet());
  }

  private void validateTargetNormsExist(Set<String> activeModDestinationElis, Norm norm) {
    activeModDestinationElis.forEach(eli -> {
      if (loadNormPort.loadNorm(new LoadNormPort.Command(eli)).isEmpty()) {
        throw new ActiveModDestinationNormNotFoundException(norm.getEli(), eli);
      }
    });
  }

  private boolean isNormRetrievableByEli(boolean forceOverwrite, Norm norm) {
    final boolean normExists = loadNormPort
      .loadNorm(new LoadNormPort.Command(norm.getEli()))
      .isPresent();

    if (normExists && !forceOverwrite) {
      throw new NormExistsAlreadyException(norm.getEli());
    }
    return normExists;
  }

  private void deleteAnnouncement(Norm norm) {
    deleteAnnouncementByNormEliPort.deleteAnnouncementByNormEli(
      new DeleteAnnouncementByNormEliPort.Command(norm.getEli())
    );
  }

  private void deleteTargetNormsZf0(Set<String> activeModDestinationElis) {
    activeModDestinationElis.forEach(eli ->
      loadNormPort
        .loadNorm(new LoadNormPort.Command(eli))
        .ifPresent(targetNorm ->
          targetNorm
            .getMeta()
            .getFRBRExpression()
            .getFRBRaliasNextVersionId()
            .ifPresent(uuid -> {
              deleteNormByGuidPort.loadNormByGuid(new DeleteNormByGuidPort.Command(uuid));
              targetNorm.getMeta().getFRBRExpression().deleteAliasNextVersionId();
              updateNormPort.updateNorm(new UpdateNormPort.Command(targetNorm));
            })
        )
    );
  }

  private Announcement saveNewAnnouncement(Norm norm) {
    var announcement = Announcement.builder().norm(norm).build();
    updateOrSaveAnnouncementPort.updateOrSaveAnnouncement(
      new UpdateOrSaveAnnouncementPort.Command(announcement)
    );
    return announcement;
  }

  private void runPreProcessing(final Norm norm, final Set<String> activeModDestinationElis) {
    // 1. Eid-Correction
    EidConsistencyGuardian.correctEids(norm.getDocument());
    // 2. ZF0 creation
    activeModDestinationElis.forEach(eli ->
      loadNormPort
        .loadNorm(new LoadNormPort.Command(eli))
        .ifPresent(targetNorm ->
          loadZf0Service.loadOrCreateZf0(new LoadZf0UseCase.Query(norm, targetNorm, true))
        )
    );
    // 3. Reference recognition
    referenceService.findAndCreateReferences(norm);
  }
}
