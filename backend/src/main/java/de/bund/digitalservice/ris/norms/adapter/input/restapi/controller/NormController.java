package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.NormResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.NormResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Controller for norm-related actions. */
@RestController
@RequestMapping(
    "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}")
public class NormController {

  private final LoadNormUseCase loadNormUseCase;
  private final LoadNormXmlUseCase loadNormXmlUseCase;
  private final UpdateNormXmlUseCase updateNormXmlUseCase;
  private final TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase;
  private final TimeMachineUseCase timeMachineUseCase;

  public NormController(
      LoadNormUseCase loadNormUseCase,
      LoadNormXmlUseCase loadNormXmlUseCase,
      UpdateNormXmlUseCase updateNormXmlUseCase,
      TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase,
      TimeMachineUseCase timeMachineUseCase) {
    this.loadNormUseCase = loadNormUseCase;
    this.loadNormXmlUseCase = loadNormXmlUseCase;
    this.updateNormXmlUseCase = updateNormXmlUseCase;
    this.transformLegalDocMlToHtmlUseCase = transformLegalDocMlToHtmlUseCase;
    this.timeMachineUseCase = timeMachineUseCase;
  }

  /**
   * Retrieves a norm based on its expression ELI. The ELI's components are interpreted as query
   * parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param agent DE: "Verkündungsblatt"
   * @param year DE "Verkündungsjahr"
   * @param naturalIdentifier DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @return A {@link ResponseEntity} containing the retrieved norm.
   *     <p>Returns HTTP 200 (OK) and the norm if found.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   */
  @GetMapping(produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<NormResponseSchema> getNorm(
      @PathVariable final String agent,
      @PathVariable final String year,
      @PathVariable final String naturalIdentifier,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype) {
    final String eli =
        buildEli(agent, year, naturalIdentifier, pointInTime, version, language, subtype);

    return loadNormUseCase
        .loadNorm(new LoadNormUseCase.Query(eli))
        .map(NormResponseMapper::fromUseCaseData)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Retrieves a norm's xml based on its expression ELI. The ELI's components are interpreted as
   * query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param agent DE: "Verkündungsblatt"
   * @param year DE "Verkündungsjahr"
   * @param naturalIdentifier DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @return A {@link ResponseEntity} containing the retrieved norm's xml.
   *     <p>Returns HTTP 200 (OK) and the norm's xml if found.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   */
  @GetMapping(produces = {APPLICATION_XML_VALUE})
  public ResponseEntity<String> getNormXml(
      @PathVariable final String agent,
      @PathVariable final String year,
      @PathVariable final String naturalIdentifier,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype) {
    final String eli =
        buildEli(agent, year, naturalIdentifier, pointInTime, version, language, subtype);

    return loadNormXmlUseCase
        .loadNormXml(new LoadNormXmlUseCase.Query(eli))
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Retrieves a norm's html render based on its expression ELI. The ELI's components are
   * interpreted as query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param agent DE: "Verkündungsblatt"
   * @param year DE "Verkündungsjahr"
   * @param naturalIdentifier DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @param showMetadata Boolean indicating whether to include metadata in the HTML response.
   * @param atTimeBoundary ISO date as string indicating which modifications should be applied
   *     before returning the HTML rendering.
   * @return A {@link ResponseEntity} containing the retrieved norm as rendered html.
   *     <p>Returns HTTP 200 (OK) and the norm as rendered html.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   */
  @GetMapping(produces = {TEXT_HTML_VALUE})
  public ResponseEntity<String> getNormRender(
      @PathVariable final String agent,
      @PathVariable final String year,
      @PathVariable final String naturalIdentifier,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype,
      @RequestParam(defaultValue = "false") boolean showMetadata,
      // TODO: Should this be "atDate" ?
      @RequestParam Optional<String> atTimeBoundary) {

    if (atTimeBoundary.isPresent()) return ResponseEntity.badRequest().build();

    final String eli =
        buildEli(agent, year, naturalIdentifier, pointInTime, version, language, subtype);

    return loadNormXmlUseCase
        .loadNormXml(new LoadNormXmlUseCase.Query(eli))
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
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param agent DE: "Verkündungsblatt"
   * @param year DE "Verkündungsjahr"
   * @param naturalIdentifier DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @param xml - the XML representation of the amending law
   * @return A {@link ResponseEntity} without any content if 204 or error response if 404.
   *     <p>Returns HTTP 200 (OK) with the saved XML as response payload.
   *     <p>Returns HTTP 404 (Not Found) if the amending law is not found.
   */
  @PutMapping(
      consumes = {APPLICATION_XML_VALUE},
      produces = {APPLICATION_XML_VALUE})
  public ResponseEntity<String> updateAmendingLaw(
      @PathVariable final String agent,
      @PathVariable final String year,
      @PathVariable final String naturalIdentifier,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype,
      @RequestBody String xml) {
    final String eli =
        buildEli(agent, year, naturalIdentifier, pointInTime, version, language, subtype);

    try {
      return updateNormXmlUseCase
          .updateNormXml(new UpdateNormXmlUseCase.Query(eli, xml))
          .map(ResponseEntity::ok)
          .orElseGet(() -> ResponseEntity.notFound().build());
    } catch (UpdateNormXmlUseCase.InvalidUpdateException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  /**
   * Retrieves the xml preview of a norm after an amending law is applied.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param agent DE: "Verkündungsblatt"
   * @param year DE "Verkündungsjahr"
   * @param naturalIdentifier DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @param amendingLaw DE: Änderungsgesetz (as XML)
   * @return A {@link ResponseEntity} containing the retrieved preview.
   *     <p>Returns HTTP 400 (Bad Request) if the amending law is missing in the request.
   */
  @PostMapping(
      path = "/preview",
      consumes = {APPLICATION_XML_VALUE},
      produces = {APPLICATION_XML_VALUE})
  public ResponseEntity<String> getPreview(
      @PathVariable final String agent,
      @PathVariable final String year,
      @PathVariable final String naturalIdentifier,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype,
      @RequestBody final String amendingLaw) {

    final String eli =
        buildEli(agent, year, naturalIdentifier, pointInTime, version, language, subtype);

    Optional<String> targetLawPreview =
        timeMachineUseCase.applyTimeMachine(new TimeMachineUseCase.Query(eli, amendingLaw));
    return targetLawPreview
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Retrieves the html preview of a norm after an amending law is applied.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param agent DE: "Verkündungsblatt"
   * @param year DE "Verkündungsjahr"
   * @param naturalIdentifier DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @param amendingLaw DE: Änderungsgesetz (as XML)
   * @return A {@link ResponseEntity} containing the retrieved preview.
   *     <p>Returns HTTP 400 (Bad Request) if the amending law is missing in the request.
   */
  @PostMapping(
      path = "/preview",
      consumes = {APPLICATION_XML_VALUE},
      produces = {TEXT_HTML_VALUE})
  public ResponseEntity<String> getHtmlPreview(
      @PathVariable final String agent,
      @PathVariable final String year,
      @PathVariable final String naturalIdentifier,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype,
      @RequestBody final String amendingLaw) {
    final String eli =
        buildEli(agent, year, naturalIdentifier, pointInTime, version, language, subtype);

    return timeMachineUseCase
        .applyTimeMachine(new TimeMachineUseCase.Query(eli, amendingLaw))
        .map(
            xml -> {
              var html =
                  this.transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(
                      new TransformLegalDocMlToHtmlUseCase.Query(xml, false));
              return ResponseEntity.ok(html);
            })
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @NotNull
  private static String buildEli(
      String agent,
      String year,
      String naturalIdentifier,
      String pointInTime,
      String version,
      String language,
      String subtype) {
    return "eli/bund/"
        + agent
        + "/"
        + year
        + "/"
        + naturalIdentifier
        + "/"
        + pointInTime
        + "/"
        + version
        + "/"
        + language
        + "/"
        + subtype;
  }
}
