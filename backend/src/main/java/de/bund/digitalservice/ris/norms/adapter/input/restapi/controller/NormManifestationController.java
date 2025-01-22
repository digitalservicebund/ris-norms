package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

import de.bund.digitalservice.ris.norms.application.port.input.LoadRegelungstextXmlUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{pointInTimeManifestation}/{subtype}.xml"
)
public class NormManifestationController {

  private final LoadRegelungstextXmlUseCase loadRegelungstextXmlUseCase;

  public NormManifestationController(LoadRegelungstextXmlUseCase loadRegelungstextXmlUseCase) {
    this.loadRegelungstextXmlUseCase = loadRegelungstextXmlUseCase;
  }

  /**
   * Retrieves the XML of a norm based on its manifestation ELI.
   *
   * @param eli Eli of the request
   * @return A {@link ResponseEntity} containing the retrieved norm as XML.
   *     <p>Returns HTTP 200 (OK) and the norm as XML.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   */
  @GetMapping(produces = { APPLICATION_XML_VALUE })
  public ResponseEntity<String> getNormManifestationXml(final DokumentManifestationEli eli) {
    var norm = loadRegelungstextXmlUseCase.loadRegelungstextXml(
      new LoadRegelungstextXmlUseCase.Query(eli)
    );
    return ResponseEntity.ok(norm);
  }
}
