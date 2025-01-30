package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.*;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.utils.EidConsistencyGuardian;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
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
    LoadAllAnnouncementsUseCase, LoadAnnouncementByNormEliUseCase, CreateAnnouncementUseCase {

  private final LoadAllAnnouncementsPort loadAllAnnouncementsPort;
  private final LoadAnnouncementByNormEliPort loadAnnouncementByNormEliPort;
  private final LoadNormPort loadNormPort;
  private final LoadNormByGuidPort loadNormByGuidPort;
  private final CreateZf0Service createZf0Service;
  private final UpdateOrSaveAnnouncementPort updateOrSaveAnnouncementPort;
  private final BillToActService billToActService;
  private final LdmlDeValidator ldmlDeValidator;
  private final DeleteAnnouncementByNormEliPort deleteAnnouncementByNormEliPort;
  private final DeleteNormPort deleteNormPort;
  private final ReferenceService referenceService;
  private final UpdateOrSaveNormPort updateOrSaveNormPort;

  public AnnouncementService(
    LoadAllAnnouncementsPort loadAllAnnouncementsPort,
    LoadAnnouncementByNormEliPort loadAnnouncementByNormEliPort,
    LoadNormPort loadNormPort,
    LoadNormByGuidPort loadNormByGuidPort,
    CreateZf0Service createZf0Service,
    UpdateOrSaveAnnouncementPort updateOrSaveAnnouncementPort,
    BillToActService billToActService,
    LdmlDeValidator ldmlDeValidator,
    DeleteAnnouncementByNormEliPort deleteAnnouncementByNormEliPort,
    DeleteNormPort deleteNormPort,
    ReferenceService referenceService,
    UpdateOrSaveNormPort updateOrSaveNormPort
  ) {
    this.loadAllAnnouncementsPort = loadAllAnnouncementsPort;
    this.loadAnnouncementByNormEliPort = loadAnnouncementByNormEliPort;
    this.loadNormPort = loadNormPort;
    this.loadNormByGuidPort = loadNormByGuidPort;
    this.createZf0Service = createZf0Service;
    this.updateOrSaveAnnouncementPort = updateOrSaveAnnouncementPort;
    this.billToActService = billToActService;
    this.ldmlDeValidator = ldmlDeValidator;
    this.deleteAnnouncementByNormEliPort = deleteAnnouncementByNormEliPort;
    this.deleteNormPort = deleteNormPort;
    this.referenceService = referenceService;
    this.updateOrSaveNormPort = updateOrSaveNormPort;
  }

  @Override
  public List<Announcement> loadAllAnnouncements() {
    return loadAllAnnouncementsPort.loadAllAnnouncements();
  }

  @Override
  public Announcement loadAnnouncementByNormEli(LoadAnnouncementByNormEliUseCase.Query query) {
    return loadAnnouncementByNormEliPort
      .loadAnnouncementByNormEli(new LoadAnnouncementByNormEliPort.Command(query.eli()))
      .orElseThrow(() -> new AnnouncementNotFoundException(query.eli().toString()));
  }

  @Override
  public Announcement createAnnouncement(CreateAnnouncementUseCase.Query query) throws IOException {
    validateFileIsXml(query.file());
    var document = convertFileToDocument(query.file());
    final String actString = billToActService.convert(document);

    // it throws an exception if the validation fails or the LDML.de Version is not 1.7.2
    // we can at the moment not use the resulting norm as it is namespace-aware and our xPaths are
    // not yet.
    var validatedNorm = ldmlDeValidator.parseAndValidate(actString);
    ldmlDeValidator.validateSchematron(validatedNorm);

    var norm = Norm.builder().regelungstexte(Set.of(new Regelungstext(document))).build();

    var activeModDestinationElis = getActiveModDestinationElis(norm);

    validateTargetNormsExist(activeModDestinationElis, norm);

    final boolean normFound = isNormRetrievableByEli(query.force(), norm);

    if (normFound && query.force()) {
      deleteAnnouncement(norm.getNormExpressionEli());
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

  private Set<DokumentExpressionEli> getActiveModDestinationElis(Norm norm) {
    var activeMods = norm
      .getMeta()
      .getAnalysis()
      .map(Analysis::getActiveModifications)
      .orElseGet(List::of);
    return activeMods
      .stream()
      .map(TextualMod::getDestinationHref)
      .flatMap(Optional::stream)
      .map(Href::getExpressionEli)
      .flatMap(Optional::stream)
      .collect(Collectors.toSet());
  }

  private void validateTargetNormsExist(
    Set<DokumentExpressionEli> activeModDestinationElis,
    Norm norm
  ) {
    activeModDestinationElis.forEach(eli -> {
      if (loadNormPort.loadNorm(new LoadNormPort.Command(eli)).isEmpty()) {
        throw new ActiveModDestinationNormNotFoundException(
          norm.getExpressionEli().toString(),
          eli.toString()
        );
      }
    });
  }

  private boolean isNormRetrievableByEli(boolean forceOverwrite, Norm norm) {
    final boolean normExists = loadNormPort
      .loadNorm(new LoadNormPort.Command(norm.getExpressionEli()))
      .isPresent();

    if (normExists && !forceOverwrite) {
      throw new NormExistsAlreadyException(norm.getExpressionEli().toString());
    }
    return normExists;
  }

  private void deleteAnnouncement(NormExpressionEli expressionEli) {
    var announcement = loadAnnouncementByNormEliPort.loadAnnouncementByNormEli(
      new LoadAnnouncementByNormEliPort.Command(expressionEli)
    );

    if (announcement.isPresent()) {
      deleteAnnouncementByNormEliPort.deleteAnnouncementByNormEli(
        new DeleteAnnouncementByNormEliPort.Command(expressionEli)
      );
      announcement
        .get()
        .getReleases()
        .stream()
        .map(Release::getPublishedNorms)
        .flatMap(Collection::stream)
        .forEach(norm ->
          deleteNormPort.deleteNorm(
            new DeleteNormPort.Command(
              norm.getNormManifestationEli(),
              NormPublishState.QUEUED_FOR_PUBLISH
            )
          )
        );
    }

    Optional<Norm> normToDelete = loadNormPort.loadNorm(new LoadNormPort.Command(expressionEli));

    if (normToDelete.isPresent()) {
      var activeModDestinationElis = getActiveModDestinationElis(normToDelete.get());
      deleteTargetNormsZf0(activeModDestinationElis);
    }
  }

  private void deleteTargetNormsZf0(Set<DokumentExpressionEli> activeModDestinationElis) {
    activeModDestinationElis.forEach(expressionEli ->
      loadNormPort
        .loadNorm(new LoadNormPort.Command(expressionEli))
        .ifPresent(targetNorm ->
          deleteNormPort.deleteNorm(
            new DeleteNormPort.Command(
              targetNorm.getNormManifestationEli(),
              NormPublishState.UNPUBLISHED
            )
          )
        )
    );
  }

  private Announcement saveNewAnnouncement(Norm norm) {
    var announcement = Announcement.builder().eli(norm.getNormExpressionEli()).build();
    updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Command(norm));
    updateOrSaveAnnouncementPort.updateOrSaveAnnouncement(
      new UpdateOrSaveAnnouncementPort.Command(announcement)
    );
    return announcement;
  }

  private void runPreProcessing(
    final Norm norm,
    final Set<DokumentExpressionEli> activeModDestinationElis
  ) {
    // 1. Eid-Correction
    EidConsistencyGuardian.correctEids(norm.getDocument());
    // 2. ZF0 creation
    activeModDestinationElis.forEach(eli ->
      loadNormPort
        .loadNorm(new LoadNormPort.Command(eli))
        .ifPresent(targetNorm ->
          createZf0Service.createZf0(new CreateZf0UseCase.Query(norm, targetNorm, true))
        )
    );
    // 3. Reference recognition
    referenceService.findAndCreateReferences(norm);
  }
}
