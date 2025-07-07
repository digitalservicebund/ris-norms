package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.NormResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.NormResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadRegelungstextXmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.TransformLegalDocMlToHtmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateRegelungstextXmlUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.DokumentType;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for {@link Dokument}-related actions.
 * <p></p>
 * Path parameters represent the eli of the expression of a dokument and can be used to create a {@link DokumentExpressionEli}
 */
@RestController
@RequestMapping(
  "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}"
)
public class DokumentExpressionController {

  private final LoadNormUseCase loadNormUseCase;
  private final LoadRegelungstextXmlUseCase loadRegelungstextXmlUseCase;
  private final UpdateRegelungstextXmlUseCase updateRegelungstextXmlUseCase;
  private final TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase;

  public DokumentExpressionController(
    LoadNormUseCase loadNormUseCase,
    LoadRegelungstextXmlUseCase loadRegelungstextXmlUseCase,
    UpdateRegelungstextXmlUseCase updateRegelungstextXmlUseCase,
    TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase
  ) {
    this.loadNormUseCase = loadNormUseCase;
    this.loadRegelungstextXmlUseCase = loadRegelungstextXmlUseCase;
    this.updateRegelungstextXmlUseCase = updateRegelungstextXmlUseCase;
    this.transformLegalDocMlToHtmlUseCase = transformLegalDocMlToHtmlUseCase;
  }

  /**
   * Retrieves a norm based on its expression ELI.
   *
   * @param eli Eli of the request
   * @return A {@link ResponseEntity} containing the retrieved norm.
   *     <p>Returns HTTP 200 (OK) and the norm if found.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   * @deprecated use {@link NormExpressionController#getNorm(NormExpressionEli)} instead
   */
  @Deprecated(forRemoval = true)
  @GetMapping(produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<NormResponseSchema> getNorm(final NormExpressionEli eli) {
    var norm = loadNormUseCase.loadNorm(new LoadNormUseCase.EliOptions(eli));
    return ResponseEntity.ok(NormResponseMapper.fromUseCaseData(norm));
  }

  /**
   * Retrieves a norm's xml based on its expression ELI.
   *
   * @param eli Eli of the request
   * @return A {@link ResponseEntity} containing the retrieved norm's xml.
   *     <p>Returns HTTP 200 (OK) and the norm's xml if found.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   */
  @GetMapping(produces = { APPLICATION_XML_VALUE })
  public ResponseEntity<String> getNormXml(final DokumentExpressionEli eli) {
    return ResponseEntity.ok(
      loadRegelungstextXmlUseCase.loadRegelungstextXml(new LoadRegelungstextXmlUseCase.Options(eli))
    );
  }

  /**
   * Retrieves a regelungstext's html render based on its expression ELI.
   *
   * @param eli Eli of the request
   * @param showMetadata Boolean indicating whether to include metadata in the HTML response.
   * @return A {@link ResponseEntity} containing the retrieved regelungstext as rendered html.
   *     <p>Returns HTTP 200 (OK) and the regelungstext as rendered html.
   *     <p>Returns HTTP 404 (Not Found) if the regelungstext is not found.
   * @deprecated use {@link NormExpressionController#getNormRender(NormExpressionEli, boolean)} instead
   */
  @GetMapping(produces = { TEXT_HTML_VALUE })
  public ResponseEntity<String> getNormRender(
    final NormExpressionEli eli,
    @RequestParam(defaultValue = "false") boolean showMetadata
  ) {
    var normXml = loadRegelungstextXmlUseCase.loadRegelungstextXml(
      new LoadRegelungstextXmlUseCase.Options(
        DokumentExpressionEli.fromNormEli(eli, DokumentType.REGELUNGSTEXT_VERKUENDUNG, 1)
      )
    );
    var legalDocHtml = this.transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(
      new TransformLegalDocMlToHtmlUseCase.Options(normXml, showMetadata, false)
    );

    return ResponseEntity.ok(legalDocHtml);
  }

  /**
   * Updates the XML representation of a Regelungstext based on its expression ELI.
   *
   * @param eli Eli of the request
   * @param xml - the XML representation of the Regelungstext
   * @return A {@link ResponseEntity} without any content if 204 or error response if 404.
   *     <p>Returns HTTP 200 (OK) with the saved XML as response payload.
   *     <p>Returns HTTP 404 (Not Found) if the Regelungstext is not found.
   */
  @PutMapping(consumes = { APPLICATION_XML_VALUE }, produces = { APPLICATION_XML_VALUE })
  public ResponseEntity<String> updateRegelungstext(
    final DokumentExpressionEli eli,
    @RequestBody String xml
  ) {
    var updatedRegelungstext = updateRegelungstextXmlUseCase.updateRegelungstextXml(
      new UpdateRegelungstextXmlUseCase.Options(eli, xml)
    );

    return ResponseEntity.ok(updatedRegelungstext);
  }
}
