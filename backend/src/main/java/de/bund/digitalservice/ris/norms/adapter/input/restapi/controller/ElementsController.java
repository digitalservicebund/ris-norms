package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ElementsResponseEntrySchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.utils.NodeParser;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Controller for norm-related list actions. */
@RestController
@RequestMapping(
    "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/elements")
public class ElementsController {

  /** The types of elements that can be retrieved */
  public enum ElementType {
    article,
    preamble,
    preface
  }

  private final Map<ElementType, String> typesToXPaths = Map.ofEntries(
          Map.entry(ElementType.preface, "//act/preface"),
          Map.entry(ElementType.preamble, "//act/preamble"),
          Map.entry(ElementType.article, "//body/article")
  );

  private final LoadNormUseCase loadNormUseCase;

  public ElementsController(LoadNormUseCase loadNormUseCase) {
    this.loadNormUseCase = loadNormUseCase;
  }

  /**
   * Retrieves a list of elements inside a norm based on the ELI of the norm and the types of the
   * elements.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param agent DE: "Verkündungsblatt"
   * @param year DE "Verkündungsjahr"
   * @param naturalIdentifier DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @param types The type(s) of the elements that should be returned. Elements are returned in the
   *     order of the types, and then in the order of elements in the norm.
   * @return A {@link ResponseEntity} containing the list of elements.
   *     <p>Returns HTTP 200 (OK) if the norm is found. The list might be empty.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   *     <p>Returns HTTP 500 (Server error) if an unsupported type is provided.
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
      @RequestParam final ElementType[] type) {

    final String eli =
        buildEli(agent, year, naturalIdentifier, pointInTime, version, language, subtype);

    // Calculate the XPath based on the types via a Map defined above
    var combinedXPaths = String.join(
            "|",
            Arrays.stream(type)
              .map(typesToXPaths::get)
              .toList());

    var elements =
        loadNormUseCase.loadNorm(new LoadNormUseCase.Query(eli)).stream()
            .flatMap(norm -> NodeParser.getNodesFromExpression(combinedXPaths, norm.getDocument()).stream())
            .flatMap(node-> {
                    var nodeType = node.getNodeName().replace("akn:", "");
                    var eid = NodeParser.getValueFromExpression("./@eId", node);

                    return Stream.of((ElementsResponseEntrySchema)
                            ElementsResponseEntrySchema.builder()
                                    .title("dummy title")
                                    .eid(eid.orElseThrow())
                                    .type(nodeType)
                                    .build());
            })
            .toList();

// TODO Hannes: We need to discriminate between a non-existing norm and a norm with e.g. no articles
    if (elements.isEmpty()) return ResponseEntity.notFound().build();
    else return ResponseEntity.ok(elements);
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
