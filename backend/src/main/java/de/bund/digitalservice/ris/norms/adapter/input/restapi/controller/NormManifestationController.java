package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

import de.bund.digitalservice.ris.norms.application.port.input.LoadNormXmlUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ManifestationEli;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for norm-related actions.
 *
 * Path parameters represent the eli of the expression of a norm and can be used to create an
 * {@link ExpressionEli}: agent - DE: "Verkündungsblatt" year - DE "Verkündungsjahr"
 * naturalIdentifier - DE: "Seitenzahl / Verkündungsnummer" pointInTime - DE: "Versionsdatum"
 * version - DE: "Versionsnummer" language - DE: "Sprache" subtype - DE: "Dokumentenart"
 */
@RestController
@RequestMapping(
  "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{pointInTimeManifestation}/{subtype}.xml"
)
public class NormManifestationController {

  private final LoadNormXmlUseCase loadNormXmlUseCase;

  public NormManifestationController(LoadNormXmlUseCase loadNormXmlUseCase) {
    this.loadNormXmlUseCase = loadNormXmlUseCase;
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
  public ResponseEntity<String> getNormManifestationXml(final ManifestationEli eli) {
    var norm = loadNormXmlUseCase.loadNormXml(new LoadNormXmlUseCase.Query(eli));
    return ResponseEntity.ok(norm);
  }
}
