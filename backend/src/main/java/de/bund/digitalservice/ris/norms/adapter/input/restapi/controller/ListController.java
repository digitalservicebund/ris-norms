package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ListResponseEntrySchema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/** Controller for norm-related list actions. */
@RestController
@RequestMapping(
        "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/list")
public class ListController {
    @GetMapping(produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ListResponseEntrySchema>> getList(){
        var entry = ListResponseEntrySchema.builder()
                .title("dummy title")
                .eid("dummy eid")
                .type("dummy type")
                .build();
        return ResponseEntity.ok(List.of(entry));
    }
}
