package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ElementResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ElementResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Controller for retrieving a norm's elements. */
@RestController
@RequestMapping(
  "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/elements"
)
public class ElementController {

  private final LoadElementUseCase loadElementUseCase;
  private final LoadElementHtmlUseCase loadElementHtmlUseCase;

  public ElementController(
    LoadElementUseCase loadElementUseCase,
    LoadElementHtmlUseCase loadElementHtmlUseCase
  ) {
    this.loadElementUseCase = loadElementUseCase;
    this.loadElementHtmlUseCase = loadElementHtmlUseCase;
  }

  /**
   * Retrieves a norm's element's HTML preview
   *
   * @param eli Eli of the request
   * @param eid EID of the element to return
   * @return A {@link ResponseEntity} containing the HTML.
   *     <p>Returns HTTP 400 (Bad Request) if ELI or EID can not be found
   */
  @GetMapping(path = "/{eid}", produces = { TEXT_HTML_VALUE })
  public ResponseEntity<String> getElementHtmlPreview(
    final DokumentExpressionEli eli,
    @PathVariable final EId eid
  ) {
    var elementHtml = loadElementHtmlUseCase.loadElementHtml(
      new LoadElementHtmlUseCase.Options(eli, eid)
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
    @PathVariable final EId eid
  ) {
    var element = loadElementUseCase.loadElement(new LoadElementUseCase.Options(eli, eid));

    return ResponseEntity.ok(ElementResponseMapper.fromElementNode(element));
  }
}
