package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ElementsResponseEntrySchema;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Controller for norm-related list actions. */
@RestController
@RequestMapping(
    "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/elements")
public class ElementsController {
  /**
   * Retrieves a list of elements inside a norm based on the ELI of the norm and the types of the
   * elements.
   *
   * @param type The type(s) of the elements that should be returned. Elements are returned in the
   *     order of the types, and then in the order of elements in the norm.
   * @return A {@link ResponseEntity} containing the list of elements.
   *     <p>Returns HTTP 200 (OK) if the norm is found. The list might be empty.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   *     <p>Returns HTTP 500 (Server error) if an unsupported type is provided.
   */
  @GetMapping(produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<List<ElementsResponseEntrySchema>> getList(
      @RequestParam final String type) {
    var entry =
        ElementsResponseEntrySchema.builder()
            .title("dummy title")
            .eid("dummy eid")
            .type("dummy type")
            .build();
    return ResponseEntity.ok(List.of(entry));
  }
}
