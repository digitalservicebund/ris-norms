package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ElementResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ElementResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import java.time.Instant;
import java.util.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Controller for retrieving a norm's elements. */
@RestController
@RequestMapping(
  "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/elements"
)
public class ElementController {

  private final LoadElementFromNormUseCase loadElementFromNormUseCase;
  private final LoadElementHtmlFromNormUseCase loadElementHtmlFromNormUseCase;
  private final LoadElementHtmlAtDateFromNormUseCase loadElementHtmlAtDateFromNormUseCase;
  private final LoadElementsByTypeFromNormUseCase loadElementsByTypeFromNormUseCase;

  public ElementController(
    LoadElementFromNormUseCase loadElementFromNormUseCase,
    LoadElementHtmlFromNormUseCase loadElementHtmlFromNormUseCase,
    LoadElementHtmlAtDateFromNormUseCase loadElementHtmlAtDateFromNormUseCase,
    LoadElementsByTypeFromNormUseCase loadElementsByTypeFromNormUseCase
  ) {
    this.loadElementFromNormUseCase = loadElementFromNormUseCase;
    this.loadElementHtmlFromNormUseCase = loadElementHtmlFromNormUseCase;
    this.loadElementHtmlAtDateFromNormUseCase = loadElementHtmlAtDateFromNormUseCase;
    this.loadElementsByTypeFromNormUseCase = loadElementsByTypeFromNormUseCase;
  }

  /**
   * Retrieves a norm's element's HTML preview
   *
   * @param eli Eli of the request
   * @param eid EID of the element to return
   * @param atIsoDate Render the version of the law valid at the given date (with passive changes
   *     applied up to that date)
   * @return A {@link ResponseEntity} containing the HTML.
   *     <p>Returns HTTP 400 (Bad Request) if ELI or EID can not be found
   */
  @GetMapping(path = "/{eid}", produces = { TEXT_HTML_VALUE })
  public ResponseEntity<String> getElementHtmlPreview(
    final DokumentExpressionEli eli,
    @PathVariable final String eid,
    @RequestParam Optional<Instant> atIsoDate
  ) {
    var elementHtml = atIsoDate
      .map(date ->
        loadElementHtmlAtDateFromNormUseCase.loadElementHtmlAtDateFromNorm(
          new LoadElementHtmlAtDateFromNormUseCase.Query(eli, eid, date)
        )
      )
      .orElseGet(() ->
        loadElementHtmlFromNormUseCase.loadElementHtmlFromNorm(
          new LoadElementHtmlFromNormUseCase.Query(eli, eid)
        )
      );

    return ResponseEntity.ok(elementHtml);
  }

  /**
   * Retrieves a norm's element's information
   *
   * @param eli Eli of the request
   * @param eid EID of the element to return
   * @return A {@link ResponseEntity} containing the element's information.
   *     <p>Returns HTTP 400 (Bad Request) if ELI or EID can not be found
   */
  @GetMapping(path = "/{eid}", produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<ElementResponseSchema> getElementInfo(
    final DokumentExpressionEli eli,
    @PathVariable final String eid
  ) {
    var element = loadElementFromNormUseCase.loadElementFromNorm(
      new LoadElementFromNormUseCase.Query(eli, eid)
    );

    return ResponseEntity.ok(ElementResponseMapper.fromElementNode(element));
  }

  /**
   * Retrieves a list of elements inside a norm based on the ELI of the norm and the types of the
   * elements.
   *
   * @param eli Eli of the request
   * @param type The type(s) of the elements that should be returned. Elements are returned in the
   *     order of the types, and then in the order of elements in the norm.
   * @param amendedBy Only the elements modified by the norm of the given ELI will be returned.
   * @return A {@link ResponseEntity} containing the list of elements.
   *     <p>Returns HTTP 200 (OK) if the norm is found. The list might be empty.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   *     <p>Returns HTTP 500 (Server error) if an unsupported type is provided.
   */
  @GetMapping(produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<List<ElementResponseSchema>> getElementList(
    final DokumentExpressionEli eli,
    @RequestParam final String[] type,
    @RequestParam final Optional<DokumentExpressionEli> amendedBy
  ) {
    List<ElementResponseSchema> elements = loadElementsByTypeFromNormUseCase
      .loadElementsByTypeFromNorm(
        new LoadElementsByTypeFromNormUseCase.Query(
          eli,
          Arrays.asList(type),
          amendedBy.orElse(null)
        )
      )
      .stream()
      .map(ElementResponseMapper::fromElementNode)
      .toList();

    return ResponseEntity.ok(elements);
  }
}
