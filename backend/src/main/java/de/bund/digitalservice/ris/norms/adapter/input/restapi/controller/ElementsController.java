package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ElementsResponseEntrySchema;

import java.util.List;
import java.util.Optional;

import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

enum ElementType {
    article,
}

/**
 * Controller for norm-related list actions.
 */
@RestController
@RequestMapping(
        "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/elements")
public class ElementsController {

    private final LoadNormUseCase loadNormUseCase;

    public ElementsController(
            LoadNormUseCase loadNormUseCase
    ) {
        this.loadNormUseCase = loadNormUseCase;
    }

    /**
     * Retrieves a list of elements inside a norm based on the ELI of the norm and the types of the
     * elements.
     *
     * @param type The type(s) of the elements that should be returned. Elements are returned in the
     *             order of the types, and then in the order of elements in the norm.
     * @return A {@link ResponseEntity} containing the list of elements.
     * <p>Returns HTTP 200 (OK) if the norm is found. The list might be empty.
     * <p>Returns HTTP 404 (Not Found) if the norm is not found.
     * <p>Returns HTTP 500 (Server error) if an unsupported type is provided.
     */
    @GetMapping(produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ElementsResponseEntrySchema>> getList(
            @PathVariable final String agent,
            @PathVariable final String year,
            @PathVariable final String naturalIdentifier,
            @PathVariable final String pointInTime,
            @PathVariable final String version,
            @PathVariable final String language,
            @PathVariable final String subtype,
            @RequestParam final ElementType type) {

        // TODO Hannes: Where to put this?
        // get norm
        final String eli =
                buildEli(agent, year, naturalIdentifier, pointInTime, version, language, subtype);

        var elements = loadNormUseCase.loadNorm(new LoadNormUseCase.Query(eli)).stream()
                .flatMap(norm -> NodeParser.getNodesFromExpression("//body//article", norm.getDocument()).stream())
                .flatMap(node -> NodeParser.getValueFromExpression("./@eId", node).stream())
                .map(eid ->(ElementsResponseEntrySchema) ElementsResponseEntrySchema.builder()
                        .title("dummy title")
                        .eid(eid)
                        .type("article")
                        .build())
                .toList();

        if (elements.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(elements);
    }

    // TODO: Hannes: This is repeated across many controllers and should be refactored
    @NotNull
    private static String buildEli(
            String agent,
            String year,
            String naturalIdentifier,
            String pointInTime,
            String version,
            String language,
            String subtype) {
        return "eli/bund/"
                + agent
                + "/"
                + year
                + "/"
                + naturalIdentifier
                + "/"
                + pointInTime
                + "/"
                + version
                + "/"
                + language
                + "/"
                + subtype;
    }
}
