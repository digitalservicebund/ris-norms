package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller for norm-related list actions. */
@RestController
@RequestMapping(
        "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/list")
public class ListController {
    @GetMapping
    public ResponseEntity getList(){
        return ResponseEntity.ok().build();
    }
}
