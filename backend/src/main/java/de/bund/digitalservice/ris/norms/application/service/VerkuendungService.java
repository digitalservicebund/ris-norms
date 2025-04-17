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
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports. It is annotated with {@link Service} to indicate that it's a service
 * component in the Spring context.
 */
@Service
@Slf4j
public class VerkuendungService
  implements
    LoadAllVerkuendungenUseCase,
    CreateVerkuendungUseCase,
    LoadNormExpressionsAffectedByVerkuendungUseCase,
    LoadVerkuendungUseCase {

  private final LoadAllVerkuendungenPort loadAllVerkuendungenPort;
  private final LoadVerkuendungByNormEliPort loadVerkuendungByNormEliPort;
  private final LoadNormPort loadNormPort;
  private final LoadNormByGuidPort loadNormByGuidPort;
  private final UpdateOrSaveVerkuendungPort updateOrSaveVerkuendungPort;
  private final LdmlDeValidator ldmlDeValidator;
  private final DeleteVerkuendungByNormEliPort deleteVerkuendungByNormEliPort;
  private final UpdateOrSaveNormPort updateOrSaveNormPort;

  public VerkuendungService(
    LoadAllVerkuendungenPort loadAllVerkuendungenPort,
    LoadVerkuendungByNormEliPort loadVerkuendungByNormEliPort,
    LoadNormPort loadNormPort,
    LoadNormByGuidPort loadNormByGuidPort,
    UpdateOrSaveVerkuendungPort updateOrSaveVerkuendungPort,
    LdmlDeValidator ldmlDeValidator,
    DeleteVerkuendungByNormEliPort deleteVerkuendungByNormEliPort,
    UpdateOrSaveNormPort updateOrSaveNormPort
  ) {
    this.loadAllVerkuendungenPort = loadAllVerkuendungenPort;
    this.loadVerkuendungByNormEliPort = loadVerkuendungByNormEliPort;
    this.loadNormPort = loadNormPort;
    this.loadNormByGuidPort = loadNormByGuidPort;
    this.updateOrSaveVerkuendungPort = updateOrSaveVerkuendungPort;
    this.ldmlDeValidator = ldmlDeValidator;
    this.deleteVerkuendungByNormEliPort = deleteVerkuendungByNormEliPort;
    this.updateOrSaveNormPort = updateOrSaveNormPort;
  }

  @Override
  public List<Verkuendung> loadAllVerkuendungen() {
    return loadAllVerkuendungenPort.loadAllVerkuendungen();
  }

  @Override
  public Verkuendung loadVerkuendung(LoadVerkuendungUseCase.Query query) {
    return loadVerkuendungByNormEliPort
      .loadVerkuendungByNormEli(new LoadVerkuendungByNormEliPort.Command(query.eli()))
      .orElseThrow(() -> new VerkuendungNotFoundException(query.eli().toString()));
  }

  @Override
  public Verkuendung createVerkuendung(CreateVerkuendungUseCase.Query query) throws IOException {
    validateFileIsXml(query.file());
    var regelungstextString = IOUtils.toString(
      query.file().getInputStream(),
      Charset.defaultCharset()
    );
    var regelungstextDocument = XmlMapper.toDocument(regelungstextString);

    if (!regelungstextDocument.getDocumentElement().getTagName().equals("akn:akomaNtoso")) {
      throw new NotLdmlDeXmlFileException(query.file().getOriginalFilename());
    }

    // it throws an exception if the validation fails or the LDML.de Version is not 1.7.2
    // we can at the moment not use the resulting norm as it is namespace-aware and our xPaths are
    // not yet.
    var validatedRegelungstext = ldmlDeValidator.parseAndValidateRegelungstext(regelungstextString);
    ldmlDeValidator.validateSchematron(validatedRegelungstext);

    var norm = Norm.builder().dokumente(Set.of(new Regelungstext(regelungstextDocument))).build();

    final boolean normFound = isNormRetrievableByEli(query.force(), norm);

    if (normFound && query.force()) {
      deleteVerkuendung(norm.getExpressionEli());
    } else if (
      loadNormByGuidPort.loadNormByGuid(new LoadNormByGuidPort.Command(norm.getGuid())).isPresent()
    ) {
      throw new NormWithGuidAlreadyExistsException(norm.getGuid());
    }
    runPreProcessing(norm);
    return saveNewVerkuendung(norm);
  }

  private void validateFileIsXml(MultipartFile file) {
    if (file == null || file.isEmpty() || !"text/xml".equals(file.getContentType())) {
      throw new NotAXmlFileException(
        file != null ? file.getOriginalFilename() : "null",
        file != null ? file.getContentType() : "null"
      );
    }
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

  private void deleteVerkuendung(NormExpressionEli expressionEli) {
    var verkuendung = loadVerkuendungByNormEliPort.loadVerkuendungByNormEli(
      new LoadVerkuendungByNormEliPort.Command(expressionEli)
    );

    if (verkuendung.isPresent()) {
      deleteVerkuendungByNormEliPort.deleteVerkuendungByNormEli(
        new DeleteVerkuendungByNormEliPort.Command(expressionEli)
      );
    }
  }

  private Verkuendung saveNewVerkuendung(Norm norm) {
    var verkuendung = Verkuendung.builder().eli(norm.getExpressionEli()).build();
    updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Command(norm));
    return updateOrSaveVerkuendungPort.updateOrSaveVerkuendung(
      new UpdateOrSaveVerkuendungPort.Command(verkuendung)
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
    var verkuendungNorm = loadNormPort
      .loadNorm(new LoadNormPort.Command(query.eli()))
      .orElseThrow(() -> new VerkuendungNotFoundException(query.eli().toString()));

    var affectedExpressionElis = verkuendungNorm
      .getRegelungstext1()
      .getMeta()
      .getProprietary()
      .flatMap(Proprietary::getCustomModsMetadata)
      .map(CustomModsMetadata::getAmendedNormExpressionElis)
      .orElse(List.of());

    return affectedExpressionElis
      .stream()
      .map(eli -> {
        var norm = loadNormPort.loadNorm(new LoadNormPort.Command(eli));
        if (norm.isEmpty()) {
          log.warn(
            "Norm with ELI {} could not be loaded when collecting expressions affected by Verkuendung with ELI {}",
            eli,
            query.eli()
          );
        }
        return norm;
      })
      .flatMap(Optional::stream)
      .toList();
  }
}
