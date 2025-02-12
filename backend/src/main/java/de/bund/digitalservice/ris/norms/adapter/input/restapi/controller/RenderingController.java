package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.PreviewRequestSchema;
import de.bund.digitalservice.ris.norms.application.port.input.TransformLegalDocMlToHtmlUseCase;
import java.time.Instant;
import java.util.Optional;
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
   * Retrieves the HTML rendering of a norm.
   *
   * @param previewRequestSchema The Request schema for rendering a norm. It includes the norm xml
   *     and any additional norms that should be used instead of the saved once for rendering the
   *     norm.
   * @param showMetadata A boolean indicating whether metadata should be included in the rendering.
   * @param snippet A boolean indicating whether the XML passed is just a snippet.
   * @param atIsoDate ISO date string indicating which modifications should be applied before the
   *     HTML gets rendered and returned. If no date is provided the current date is used.
   * @return A {@link ResponseEntity} containing the HTML rendering of the law document.
   */
  @PostMapping(consumes = { APPLICATION_JSON_VALUE }, produces = { TEXT_HTML_VALUE })
  public ResponseEntity<String> getHtmlPreview(
    @RequestBody final PreviewRequestSchema previewRequestSchema,
    @RequestParam(defaultValue = "false") boolean showMetadata,
    @RequestParam(defaultValue = "false") boolean snippet,
    @RequestParam Optional<Instant> atIsoDate
  ) {
    return ResponseEntity.ok(
      this.transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(
          new TransformLegalDocMlToHtmlUseCase.Query(
            previewRequestSchema.getRegelungstext(),
            showMetadata,
            snippet
          )
        )
    );
  }

  /**
   * Retrieves the XML preview of a regelungstext.
   *
   * @param previewRequestSchema The Request schema for rendering a regelungstext. It includes the regelungstext xml
   *     and any additional regelungstexts that should be used instead of the saved once for rendering the
   *     regelungstext.
   * @param atIsoDate ISO date string indicating which modifications should be applied before the
   *     HTML gets rendered and returned. If no date is provided the current date is used.
   * @return A {@link ResponseEntity} containing the HTML rendering of the law document.
   * @deprecated
   */
  @PostMapping(consumes = { APPLICATION_JSON_VALUE }, produces = { APPLICATION_XML_VALUE })
  @Deprecated(forRemoval = true)
  public ResponseEntity<String> getXMLPreview(
    @RequestBody final PreviewRequestSchema previewRequestSchema,
    @RequestParam Optional<Instant> atIsoDate
  ) {
    return ResponseEntity.ok(previewRequestSchema.getRegelungstext());
  }
}
