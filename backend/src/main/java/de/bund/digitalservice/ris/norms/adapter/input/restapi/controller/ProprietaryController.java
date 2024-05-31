package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ProprietaryResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Retrieve proprietary data of a {@link Norm}. */
@RestController
@RequestMapping(
    "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/proprietary")
// "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/proprietary"
public class ProprietaryController {
  @GetMapping(produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<ProprietaryResponseSchema> getProprietary() {
    return ResponseEntity.notFound().build();
  }
}
