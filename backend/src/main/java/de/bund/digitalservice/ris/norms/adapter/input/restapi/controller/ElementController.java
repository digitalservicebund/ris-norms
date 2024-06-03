package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static de.bund.digitalservice.ris.norms.utils.EliBuilder.buildEli;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ElementResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ElementResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.utils.EliBuilder;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormNotFoundException;
import java.time.Instant;
import java.util.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Controller for retrieving a norm's elements. */
@RestController
@RequestMapping(
    "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/elements")
public class ElementController {
  private final LoadElementFromNormUseCase loadElementFromNormUseCase;
  private final LoadElementHtmlFromNormUseCase loadElementHtmlFromNormUseCase;
  private final LoadElementHtmlAtDateFromNormUseCase loadElementHtmlAtDateFromNormUseCase;
  private final LoadElementsByTypeFromNormUseCase loadElementsByTypeFromNormUseCase;

  public ElementController(
      LoadElementFromNormUseCase loadElementFromNormUseCase,
      LoadElementHtmlFromNormUseCase loadElementHtmlFromNormUseCase,
      LoadElementHtmlAtDateFromNormUseCase loadElementHtmlAtDateFromNormUseCase,
      LoadElementsByTypeFromNormUseCase loadElementsByTypeFromNormUseCase) {
    this.loadElementFromNormUseCase = loadElementFromNormUseCase;
    this.loadElementHtmlFromNormUseCase = loadElementHtmlFromNormUseCase;
    this.loadElementHtmlAtDateFromNormUseCase = loadElementHtmlAtDateFromNormUseCase;
    this.loadElementsByTypeFromNormUseCase = loadElementsByTypeFromNormUseCase;
  }

  /**
   * Retrieves a norm's element's HTML preview
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a href=
   * "https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param agent DE: "Verkündungsblatt"
   * @param year DE "Verkündungsjahr"
   * @param naturalIdentifier DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @param eid EID of the element to return
   * @param atIsoDate Render the version of the law valid at the given date (with passive changes
   *     applied up to that date)
   * @return A {@link ResponseEntity} containing the HTML.
   *     <p>Returns HTTP 400 (Bad Request) if ELI or EID can not be found
   */
  @GetMapping(
      path = "/{eid}",
      produces = {TEXT_HTML_VALUE})
  public ResponseEntity<String> getElementHtmlPreview(
      @PathVariable final String agent,
      @PathVariable final String year,
      @PathVariable final String naturalIdentifier,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype,
      @PathVariable final String eid,
      @RequestParam Optional<Instant> atIsoDate) {

    var eli =
        EliBuilder.buildEli(
            agent, year, naturalIdentifier, pointInTime, version, language, subtype);

    return atIsoDate
        .map(
            date ->
                loadElementHtmlAtDateFromNormUseCase.loadElementHtmlAtDateFromNorm(
                    new LoadElementHtmlAtDateFromNormUseCase.Query(eli, eid, date)))
        .orElseGet(
            () ->
                loadElementHtmlFromNormUseCase.loadElementHtmlFromNorm(
                    new LoadElementHtmlFromNormUseCase.Query(eli, eid)))
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Retrieves a norm's element's information
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a href=
   * "https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param agent DE: "Verkündungsblatt"
   * @param year DE "Verkündungsjahr"
   * @param naturalIdentifier DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @param eid EID of the element to return
   * @return A {@link ResponseEntity} containing the element's information.
   *     <p>Returns HTTP 400 (Bad Request) if ELI or EID can not be found
   */
  @GetMapping(
      path = "/{eid}",
      produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<ElementResponseSchema> getElementInfo(
      @PathVariable final String agent,
      @PathVariable final String year,
      @PathVariable final String naturalIdentifier,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype,
      @PathVariable final String eid) {

    var eli =
        EliBuilder.buildEli(
            agent, year, naturalIdentifier, pointInTime, version, language, subtype);

    return loadElementFromNormUseCase
        .loadElementFromNorm(new LoadElementFromNormUseCase.Query(eli, eid))
        .map(ElementResponseMapper::fromElementNode)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Retrieves a list of elements inside a norm based on the ELI of the norm and the types of the
   * elements.
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
   * @param type The type(s) of the elements that should be returned. Elements are returned in the
   *     order of the types, and then in the order of elements in the norm.
   * @param amendedBy Only the elements modified by the norm of the given ELI will be returned.
   * @return A {@link ResponseEntity} containing the list of elements.
   *     <p>Returns HTTP 200 (OK) if the norm is found. The list might be empty.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   *     <p>Returns HTTP 500 (Server error) if an unsupported type is provided.
   */
  @GetMapping(produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<List<ElementResponseSchema>> getElementList(
      @PathVariable final String agent,
      @PathVariable final String year,
      @PathVariable final String naturalIdentifier,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype,
      @RequestParam final String[] type,
      @RequestParam final Optional<String> amendedBy) {

    final String eli =
        buildEli(agent, year, naturalIdentifier, pointInTime, version, language, subtype);

    try {
      List<ElementResponseSchema> elements =
          loadElementsByTypeFromNormUseCase
              .loadElementsByTypeFromNorm(
                  new LoadElementsByTypeFromNormUseCase.Query(
                      eli, Arrays.asList(type), amendedBy.orElse(null)))
              .stream()
              .map(ElementResponseMapper::fromElementNode)
              .toList();

      return ResponseEntity.ok(elements);
    } catch (NormNotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (LoadElementsByTypeFromNormUseCase.UnsupportedElementTypeException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
