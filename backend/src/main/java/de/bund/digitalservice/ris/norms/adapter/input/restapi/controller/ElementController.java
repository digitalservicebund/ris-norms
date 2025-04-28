package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ElementResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ElementResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.domain.entity.EId;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

/** Controller for retrieving a norm's elements. */
@RestController
@RequestMapping(
  "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/elements"
)
public class ElementController {

  private final LoadElementUseCase loadElementUseCase;
  private final LoadElementHtmlUseCase loadElementHtmlUseCase;
  private final LoadElementXmlUseCase loadElementXmlUseCase;

  public ElementController(
    LoadElementUseCase loadElementUseCase,
    LoadElementHtmlUseCase loadElementHtmlUseCase,
    LoadElementXmlUseCase loadElementXmlUseCase
  ) {
    this.loadElementUseCase = loadElementUseCase;
    this.loadElementHtmlUseCase = loadElementHtmlUseCase;
    this.loadElementXmlUseCase = loadElementXmlUseCase;
  }

  /**
   * Retrieves a norm's element's HTML preview
   *
   * @param eli Eli of the request
   * @param eid EID of the element to return
   * @return A {@link ResponseEntity} containing the HTML.
   */
  @GetMapping(path = "/{eid}", produces = { TEXT_HTML_VALUE })
  public ResponseEntity<String> getElementHtmlPreview(
    final DokumentExpressionEli eli,
    @PathVariable final EId eid
  ) {
    var elementHtml = loadElementHtmlUseCase.loadElementHtml(
      new LoadElementHtmlUseCase.Query(eli, eid)
    );

    return ResponseEntity.ok(elementHtml);
  }

  /**
   * Retrieves a norm's element's information
   *
   * @param eli Eli of the request
   * @param eid EID of the element to return
   * @return A {@link ResponseEntity} containing the element's information.
   */
  @GetMapping(path = "/{eid}", produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<ElementResponseSchema> getElementInfo(
    final DokumentExpressionEli eli,
    @PathVariable final EId eid
  ) {
    var element = loadElementUseCase.loadElement(new LoadElementUseCase.Query(eli, eid));

    return ResponseEntity.ok(ElementResponseMapper.fromElementNode(element));
  }

  /**
   * Retrieves a norm's element's XML.
   *
   * @param eli Eli of the request
   * @param eid EID of the element to return
   * @return A {@link ResponseEntity} containing the HTML.
   */
  @GetMapping(path = "/{eid}", produces = { APPLICATION_XML_VALUE })
  public ResponseEntity<String> getElementXml(
    final DokumentExpressionEli eli,
    @PathVariable final EId eid
  ) {
    var elementXml = loadElementXmlUseCase.loadElementXml(
      new LoadElementXmlUseCase.Query(eli, eid)
    );

    return ResponseEntity.ok(elementXml);
  }
}
