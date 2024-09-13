package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.domain.entity.Eli;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller for references actions. */
@RestController
@RequestMapping(
  "/api/v1/references/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}"
)
public class ReferenceController {

  private final ReferenceRecognitionUseCase referenceRecognitionUseCase;

  public ReferenceController(ReferenceRecognitionUseCase referenceRecognitionUseCase) {
    this.referenceRecognitionUseCase = referenceRecognitionUseCase;
  }

  /**
   * Runs the regex pattern recognition for references in the norm with the given ELI
   *
   * @param eli of the norm on which to run the reference pattern recognition
   * @return A {@link ResponseEntity} containing the updated norm's xml.
   *     <p>Returns HTTP 200 (OK) and the updated norm's xml if found.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   */
  @PostMapping(produces = { APPLICATION_XML_VALUE })
  public ResponseEntity<String> findReferencesInNorm(final Eli eli) {
    return ResponseEntity.ok(
      referenceRecognitionUseCase.findAndCreateReferences(
        new ReferenceRecognitionUseCase.Query(eli.getValue())
      )
    );
  }
}
