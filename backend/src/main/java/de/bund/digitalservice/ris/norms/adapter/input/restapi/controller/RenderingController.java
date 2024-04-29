package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.application.port.input.TransformLegalDocMlToHtmlUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for rendering HTML of norm documents. This controller provides endpoints for
 * transforming XML representations of norms into HTML.
 */
@RestController
@RequestMapping("/api/v1/renderings")
public class RenderingController {

  private final TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase;

  public RenderingController(TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase) {
    this.transformLegalDocMlToHtmlUseCase = transformLegalDocMlToHtmlUseCase;
  }

  /**
   * Retrieves the HTML rendering of a law document.
   *
   * @param xml The XML content of the law document to be rendered.
   * @param showMetadata A boolean indicating whether metadata should be included in the rendering.
   * @return A {@link ResponseEntity} containing the HTML rendering of the law document.
   */
  @PostMapping(
      consumes = {APPLICATION_XML_VALUE},
      produces = {TEXT_HTML_VALUE})
  public ResponseEntity<String> getHtmlPreview(
      @RequestBody final String xml, @RequestParam boolean showMetadata) {

    var html =
        transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(
            new TransformLegalDocMlToHtmlUseCase.Query(xml, showMetadata));
    return ResponseEntity.ok(html);
  }
}
