package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.NormResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.NormResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.UpdateModRequestSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.UpdateModResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.UpdateModsRequestSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.UpdateModsResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for norm-related actions.
 *
 * Path parameters represent the eli of the expression of a norm and can be used to create an
 * {@link DokumentExpressionEli}: agent - DE: "Verkündungsblatt" year - DE "Verkündungsjahr"
 * naturalIdentifier - DE: "Seitenzahl / Verkündungsnummer" pointInTime - DE: "Versionsdatum"
 * version - DE: "Versionsnummer" language - DE: "Sprache" subtype - DE: "Dokumentenart"
 */
@RestController
@RequestMapping(
  "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}"
)
public class NormExpressionController {

  private final LoadNormUseCase loadNormUseCase;
  private final LoadRegelungstextUseCase loadRegelungstextUseCase;
  private final LoadRegelungstextXmlUseCase loadRegelungstextXmlUseCase;
  private final UpdateRegelungstextXmlUseCase updateRegelungstextXmlUseCase;
  private final TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase;
  private final ApplyPassiveModificationsUseCase applyPassiveModificationsUseCase;

  public NormExpressionController(
    LoadNormUseCase loadNormUseCase,
    LoadRegelungstextUseCase loadRegelungstextUseCase,
    LoadRegelungstextXmlUseCase loadRegelungstextXmlUseCase,
    UpdateRegelungstextXmlUseCase updateRegelungstextXmlUseCase,
    TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase,
    ApplyPassiveModificationsUseCase applyPassiveModificationsUseCase
  ) {
    this.loadNormUseCase = loadNormUseCase;
    this.loadRegelungstextUseCase = loadRegelungstextUseCase;
    this.loadRegelungstextXmlUseCase = loadRegelungstextXmlUseCase;
    this.updateRegelungstextXmlUseCase = updateRegelungstextXmlUseCase;
    this.transformLegalDocMlToHtmlUseCase = transformLegalDocMlToHtmlUseCase;
    this.applyPassiveModificationsUseCase = applyPassiveModificationsUseCase;
  }

  /**
   * Retrieves a norm based on its expression ELI.
   *
   * @param eli Eli of the request
   * @return A {@link ResponseEntity} containing the retrieved norm.
   *     <p>Returns HTTP 200 (OK) and the norm if found.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   */
  @GetMapping(produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<NormResponseSchema> getNorm(final DokumentExpressionEli eli) {
    var norm = loadNormUseCase.loadNorm(new LoadNormUseCase.Query(eli.asNormEli()));
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
      loadRegelungstextXmlUseCase.loadRegelungstextXml(new LoadRegelungstextXmlUseCase.Query(eli))
    );
  }

  /**
   * Retrieves a regelungstext's html render based on its expression ELI.
   *
   * @param eli Eli of the request
   * @param showMetadata Boolean indicating whether to include metadata in the HTML response.
   * @param atIsoDate ISO date string indicating which modifications should be applied before the
   *     HTML gets rendered and returned.
   * @return A {@link ResponseEntity} containing the retrieved regelungstext as rendered html.
   *     <p>Returns HTTP 200 (OK) and the regelungstext as rendered html.
   *     <p>Returns HTTP 404 (Not Found) if the regelungstext is not found.
   */
  @GetMapping(produces = { TEXT_HTML_VALUE })
  public ResponseEntity<String> getNormRender(
    final DokumentExpressionEli eli,
    @RequestParam(defaultValue = "false") boolean showMetadata,
    @RequestParam Optional<Instant> atIsoDate
  ) {
    if (atIsoDate.isPresent()) {
      var regelungstext = loadRegelungstextUseCase.loadRegelungstext(
        new LoadRegelungstextUseCase.Query(eli)
      );
      regelungstext =
      applyPassiveModificationsUseCase.applyPassiveModifications(
        new ApplyPassiveModificationsUseCase.Query(regelungstext, atIsoDate.get())
      );

      return ResponseEntity.ok(
        this.transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(
            new TransformLegalDocMlToHtmlUseCase.Query(
              XmlMapper.toString(regelungstext.getDocument()),
              showMetadata,
              false
            )
          )
      );
    }

    var normXml = loadRegelungstextXmlUseCase.loadRegelungstextXml(
      new LoadRegelungstextXmlUseCase.Query(eli)
    );
    var legalDocHtml =
      this.transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(
          new TransformLegalDocMlToHtmlUseCase.Query(normXml, showMetadata, false)
        );

    return ResponseEntity.ok(legalDocHtml);
  }

  /**
   * Updates the XML representation of an amending law based on its expression ELI.
   *
   * @param eli Eli of the request
   * @param xml - the XML representation of the amending law
   * @return A {@link ResponseEntity} without any content if 204 or error response if 404.
   *     <p>Returns HTTP 200 (OK) with the saved XML as response payload.
   *     <p>Returns HTTP 404 (Not Found) if the amending law is not found.
   */
  @PutMapping(consumes = { APPLICATION_XML_VALUE }, produces = { APPLICATION_XML_VALUE })
  public ResponseEntity<String> updateAmendingLaw(
    final DokumentExpressionEli eli,
    @RequestBody String xml
  ) {
    var updatedAmendingLaw = updateRegelungstextXmlUseCase.updateRegelungstextXml(
      new UpdateRegelungstextXmlUseCase.Query(eli, xml)
    );

    return ResponseEntity.ok(updatedAmendingLaw);
  }

  /**
   * Update an amending command of an amending law and consecutively creates/updates the ZF0 the
   * affected document.
   *
   * @param eli Eli of the request
   * @param eid the eId of the akn:mod within the amending law
   * @param updateModRequestSchema the new data about the akn:mod element
   * @param dryRun Should the save operation only be previewed and not actually persisted?
   * @return A {@link ResponseEntity} containing the updated xml of the amending law.
   *     <p>Returns HTTP 200 (OK) if both amending law and zf0 successfully uddated.
   *     <p>Returns HTTP 404 (Not Found) if amending law, target law or node within target law not
   *     found.
   * @deprecated
   */
  @PutMapping(
    path = "/mods/{eid}",
    consumes = { APPLICATION_JSON_VALUE },
    produces = { APPLICATION_JSON_VALUE }
  )
  @Deprecated(forRemoval = true)
  public ResponseEntity<UpdateModResponseSchema> updateMod(
    final DokumentExpressionEli eli,
    @PathVariable final String eid,
    @RequestBody @Valid final UpdateModRequestSchema updateModRequestSchema,
    @RequestParam(defaultValue = "false") final Boolean dryRun
  ) {
    return ResponseEntity
      .status(
        HttpStatusCode.valueOf(
          501 // Not implemented
        )
      )
      .build();
  }

  /**
   * Update multiple akn:mod elements of an amending law and consecutively creates/updates all ZF0
   * of all affected documents.
   *
   * @param eli Eli of the request
   * @param updateModsRequestSchema the eids and changes for the amending commands to update
   * @param dryRun Should the save operation only be previewed and not actually persisted?
   * @return A {@link ResponseEntity} containing the updated xmls of the norms.
   *     <p>Returns HTTP 200 (OK) if the amending law and all zf0 successfully updated.
   *     <p>Returns HTTP 404 (Not Found) if amending law, target law or node within target law not
   *     found.
   * @deprecated
   */
  @PatchMapping(
    path = "/mods",
    consumes = { APPLICATION_JSON_VALUE },
    produces = { APPLICATION_JSON_VALUE }
  )
  @Deprecated(forRemoval = true)
  public ResponseEntity<UpdateModsResponseSchema> updateMods(
    final DokumentExpressionEli eli,
    @RequestBody @Valid @NotEmpty final Map<
      String,
      UpdateModsRequestSchema.ModUpdate
    > updateModsRequestSchema,
    @RequestParam(defaultValue = "false") final Boolean dryRun
  ) {
    return ResponseEntity
      .status(
        HttpStatusCode.valueOf(
          501 // Not implemented
        )
      )
      .build();
  }
}
