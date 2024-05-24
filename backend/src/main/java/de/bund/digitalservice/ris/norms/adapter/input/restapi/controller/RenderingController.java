package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.PreviewRequestSchema;
import de.bund.digitalservice.ris.norms.application.port.input.ApplyPassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.TransformLegalDocMlToHtmlUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
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
  private final ApplyPassiveModificationsUseCase applyPassiveModificationsUseCase;

  public RenderingController(
      TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase,
      ApplyPassiveModificationsUseCase applyPassiveModificationsUseCase) {
    this.transformLegalDocMlToHtmlUseCase = transformLegalDocMlToHtmlUseCase;
    this.applyPassiveModificationsUseCase = applyPassiveModificationsUseCase;
  }

  /**
   * Retrieves the HTML rendering of a norm.
   *
   * @param previewRequestSchema The Request schema for rendering a norm. It includes the norm xml
   *     and any additional norms that should be used instead of the saved once for rendering the
   *     norm.
   * @param showMetadata A boolean indicating whether metadata should be included in the rendering.
   * @param atIsoDate ISO date string indicating which modifications should be applied before the
   *     HTML gets rendered and returned. If no date is provided the current date is used.
   * @return A {@link ResponseEntity} containing the HTML rendering of the law document.
   */
  @PostMapping(
      consumes = {APPLICATION_JSON_VALUE},
      produces = {TEXT_HTML_VALUE})
  public ResponseEntity<String> getHtmlPreview(
      @RequestBody final PreviewRequestSchema previewRequestSchema,
      @RequestParam(defaultValue = "false") boolean showMetadata,
      @RequestParam Optional<Instant> atIsoDate) {
    final var norm =
        Norm.builder().document(XmlMapper.toDocument(previewRequestSchema.getNorm())).build();
    final Map<String, Norm> customNorms =
        previewRequestSchema.getCustomNorms().entrySet().stream()
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey,
                    entry ->
                        Norm.builder().document(XmlMapper.toDocument(entry.getValue())).build()));

    var normWithAppliedModifications =
        applyPassiveModificationsUseCase.applyPassiveModifications(
            new ApplyPassiveModificationsUseCase.Query(
                norm, atIsoDate.orElse(Instant.now()), customNorms));

    return ResponseEntity.ok(
        this.transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(
            new TransformLegalDocMlToHtmlUseCase.Query(
                XmlMapper.toString(normWithAppliedModifications.getDocument()), showMetadata)));
  }
}
