package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.*;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.utils.EidConsistencyGuardian;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;
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
    CreateAnnouncementUseCase,
    LoadNormExpressionsAffectedByVerkuendungUseCase {

  private final LoadAllAnnouncementsPort loadAllAnnouncementsPort;
  private final LoadAnnouncementByNormEliPort loadAnnouncementByNormEliPort;
  private final LoadNormPort loadNormPort;
  private final LoadNormByGuidPort loadNormByGuidPort;
  private final UpdateOrSaveAnnouncementPort updateOrSaveAnnouncementPort;
  private final BillToActService billToActService;
  private final LdmlDeValidator ldmlDeValidator;
  private final DeleteAnnouncementByNormEliPort deleteAnnouncementByNormEliPort;
  private final UpdateOrSaveNormPort updateOrSaveNormPort;

  public AnnouncementService(
    LoadAllAnnouncementsPort loadAllAnnouncementsPort,
    LoadAnnouncementByNormEliPort loadAnnouncementByNormEliPort,
    LoadNormPort loadNormPort,
    LoadNormByGuidPort loadNormByGuidPort,
    UpdateOrSaveAnnouncementPort updateOrSaveAnnouncementPort,
    BillToActService billToActService,
    LdmlDeValidator ldmlDeValidator,
    DeleteAnnouncementByNormEliPort deleteAnnouncementByNormEliPort,
    UpdateOrSaveNormPort updateOrSaveNormPort
  ) {
    this.loadAllAnnouncementsPort = loadAllAnnouncementsPort;
    this.loadAnnouncementByNormEliPort = loadAnnouncementByNormEliPort;
    this.loadNormPort = loadNormPort;
    this.loadNormByGuidPort = loadNormByGuidPort;
    this.updateOrSaveAnnouncementPort = updateOrSaveAnnouncementPort;
    this.billToActService = billToActService;
    this.ldmlDeValidator = ldmlDeValidator;
    this.deleteAnnouncementByNormEliPort = deleteAnnouncementByNormEliPort;
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
    var validatedRegelungstext = ldmlDeValidator.parseAndValidateRegelungstext(actString);
    ldmlDeValidator.validateSchematron(validatedRegelungstext);

    var norm = Norm.builder().dokumente(Set.of(new Regelungstext(document))).build();

    final boolean normFound = isNormRetrievableByEli(query.force(), norm);

    if (normFound && query.force()) {
      deleteAnnouncement(norm.getExpressionEli());
    } else if (
      loadNormByGuidPort.loadNormByGuid(new LoadNormByGuidPort.Command(norm.getGuid())).isPresent()
    ) {
      throw new NormWithGuidAlreadyExistsException(norm.getGuid());
    }
    runPreProcessing(norm);
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
    }
  }

  private Announcement saveNewAnnouncement(Norm norm) {
    var announcement = Announcement.builder().eli(norm.getExpressionEli()).build();
    updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Command(norm));
    return updateOrSaveAnnouncementPort.updateOrSaveAnnouncement(
      new UpdateOrSaveAnnouncementPort.Command(announcement)
    );
  }

  private void runPreProcessing(final Norm norm) {
    norm
      .getRegelungstexte()
      .forEach(dokument -> EidConsistencyGuardian.correctEids(dokument.getDocument()));
  }

  @Override
  public List<Norm> loadNormExpressionsAffectedByVerkuendung(
    LoadNormExpressionsAffectedByVerkuendungUseCase.Query query
  ) {
    var announcementNorm = loadNormPort
      .loadNorm(new LoadNormPort.Command(query.eli()))
      .orElseThrow(() -> new AnnouncementNotFoundException(query.eli().toString()));

    var affectedExpressionElis = announcementNorm
      .getRegelungstext1()
      .getMeta()
      .getProprietary()
      .flatMap(Proprietary::getCustomModsMetadata)
      .map(CustomModsMetadata::getAmendedNormExpressionElis)
      .orElse(List.of());

    return affectedExpressionElis
      .stream()
      .map(eli ->
        loadNormPort
          .loadNorm(new LoadNormPort.Command(eli))
          .orElseThrow(() -> new NormNotFoundException(eli.toString()))
      )
      .toList();
  }
}
