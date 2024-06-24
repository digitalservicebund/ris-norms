package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.NormResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.UpdateModResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.NormResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.UpdateModRequestSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.UpdateModResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.domain.entity.Eli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import jakarta.validation.Valid;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for norm-related actions.
 *
 * <p>Path Parameters, they represent the eli of a norm and can be used to create an {@link Eli}:
 * agent - DE: "Verkündungsblatt" year - DE "Verkündungsjahr" naturalIdentifier - DE: "Seitenzahl /
 * Verkündungsnummer" pointInTime - DE: "Versionsdatum" version - DE: "Versionsnummer" language -
 * DE: "Sprache" subtype - DE: "Dokumentenart"
 */
@RestController
@RequestMapping(
    "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}")
public class NormController {

  private final LoadNormUseCase loadNormUseCase;
  private final LoadNormXmlUseCase loadNormXmlUseCase;
  private final UpdateNormXmlUseCase updateNormXmlUseCase;
  private final TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase;
  private final ApplyPassiveModificationsUseCase applyPassiveModificationsUseCase;
  private final UpdateModUseCase updateModUseCase;

  public NormController(
      LoadNormUseCase loadNormUseCase,
      LoadNormXmlUseCase loadNormXmlUseCase,
      UpdateNormXmlUseCase updateNormXmlUseCase,
      TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase,
      ApplyPassiveModificationsUseCase applyPassiveModificationsUseCase,
      UpdateModUseCase updateModUseCase) {
    this.loadNormUseCase = loadNormUseCase;
    this.loadNormXmlUseCase = loadNormXmlUseCase;
    this.updateNormXmlUseCase = updateNormXmlUseCase;
    this.transformLegalDocMlToHtmlUseCase = transformLegalDocMlToHtmlUseCase;
    this.applyPassiveModificationsUseCase = applyPassiveModificationsUseCase;
    this.updateModUseCase = updateModUseCase;
  }

  /**
   * Retrieves a norm based on its expression ELI. The ELI's components are interpreted as query
   * parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a href=
   * "https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param eli Eli of the request
   * @return A {@link ResponseEntity} containing the retrieved norm.
   *     <p>Returns HTTP 200 (OK) and the norm if found.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   */
  @GetMapping(produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<NormResponseSchema> getNorm(final Eli eli) {
    return loadNormUseCase
        .loadNorm(new LoadNormUseCase.Query(eli.getValue()))
        .map(NormResponseMapper::fromUseCaseData)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Retrieves a norm's xml based on its expression ELI. The ELI's components are interpreted as
   * query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a href=
   * "https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param eli Eli of the request
   * @return A {@link ResponseEntity} containing the retrieved norm's xml.
   *     <p>Returns HTTP 200 (OK) and the norm's xml if found.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   */
  @GetMapping(produces = {APPLICATION_XML_VALUE})
  public ResponseEntity<String> getNormXml(final Eli eli) {
    return loadNormXmlUseCase
        .loadNormXml(new LoadNormXmlUseCase.Query(eli.getValue()))
        .map(
            norm ->
                this.transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(
                    new TransformLegalDocMlToHtmlUseCase.Query(norm, true)))
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Retrieves a norm's html render based on its expression ELI. The ELI's components are
   * interpreted as query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a href=
   * "https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param eli Eli of the request
   * @param showMetadata Boolean indicating whether to include metadata in the HTML response.
   * @param atIsoDate ISO date string indicating which modifications should be applied before the
   *     HTML gets rendered and returned.
   * @return A {@link ResponseEntity} containing the retrieved norm as rendered html.
   *     <p>Returns HTTP 200 (OK) and the norm as rendered html.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   */
  @GetMapping(produces = {TEXT_HTML_VALUE})
  public ResponseEntity<String> getNormRender(
      final Eli eli,
      @RequestParam(defaultValue = "false") boolean showMetadata,
      @RequestParam Optional<String> atIsoDate) {
    if (atIsoDate.isPresent()) {
      try {
        DateTimeFormatter.ISO_DATE_TIME.parse(atIsoDate.get());
      } catch (Exception e) {
        return ResponseEntity.badRequest().build();
      }

      return loadNormUseCase
          .loadNorm(new LoadNormUseCase.Query(eli.getValue()))
          .map(
              norm ->
                  applyPassiveModificationsUseCase.applyPassiveModifications(
                      new ApplyPassiveModificationsUseCase.Query(
                          norm, Instant.parse(atIsoDate.get()))))
          .map(
              norm ->
                  ResponseEntity.ok(
                      this.transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(
                          new TransformLegalDocMlToHtmlUseCase.Query(
                              XmlMapper.toString(norm.getDocument()), showMetadata))))
          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    return loadNormXmlUseCase
        .loadNormXml(new LoadNormXmlUseCase.Query(eli.getValue()))
        .map(
            xml ->
                ResponseEntity.ok(
                    this.transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(
                        new TransformLegalDocMlToHtmlUseCase.Query(xml, showMetadata))))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Updates the XML representation of an amending law based on its expression ELI. The ELI's
   * components are interpreted as query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a href=
   * "https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param eli Eli of the request
   * @param xml - the XML representation of the amending law
   * @return A {@link ResponseEntity} without any content if 204 or error response if 404.
   *     <p>Returns HTTP 200 (OK) with the saved XML as response payload.
   *     <p>Returns HTTP 404 (Not Found) if the amending law is not found.
   */
  @PutMapping(
      consumes = {APPLICATION_XML_VALUE},
      produces = {APPLICATION_XML_VALUE})
  public ResponseEntity<String> updateAmendingLaw(final Eli eli, @RequestBody String xml) {
    try {
      return updateNormXmlUseCase
          .updateNormXml(new UpdateNormXmlUseCase.Query(eli.getValue(), xml))
          .map(ResponseEntity::ok)
          .orElseGet(() -> ResponseEntity.notFound().build());
    } catch (UpdateNormXmlUseCase.InvalidUpdateException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  /**
   * Update an amending command of an amending law and consecutively creates/updates all ZF0 of all
   * affected documents.
   *
   * @param eli Eli of the request
   * @param eid the eId of the akn:mod within the amending law
   * @param updateModRequestSchema the amending command to update
   * @param dryRun Should the save operation only be previewed and not actually persisted?
   * @return A {@link ResponseEntity} containing the updated xml of the amending law.
   *     <p>Returns HTTP 200 (OK) if both amending law and zf0 successfully uddated.
   *     <p>Returns HTTP 404 (Not Found) if amending law, target law or node within target law not
   *     found.
   */
  @PutMapping(
      path = "/mods/{eid}",
      consumes = {APPLICATION_JSON_VALUE},
      produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<UpdateModResponseSchema> updateMod(
      final Eli eli,
      @PathVariable final String eid,
      @RequestBody @Valid final UpdateModRequestSchema updateModRequestSchema,
      @RequestParam(defaultValue = "false") final Boolean dryRun) {

    return updateModUseCase
        .updateMod(
            new UpdateModUseCase.Query(
                eli.getValue(),
                eid,
                updateModRequestSchema.getRefersTo(),
                updateModRequestSchema.getTimeBoundaryEid(),
                updateModRequestSchema.getDestinationHref(),
                updateModRequestSchema.getNewText(),
                dryRun))
        .map(UpdateModResponseMapper::fromResult)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
