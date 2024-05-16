package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static de.bund.digitalservice.ris.norms.utils.EliBuilder.buildEli;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ElementsResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ElementsResponseEntrySchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.PassiveModification;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/** Controller for retrieving a norm's elements. */
@RestController
@RequestMapping(
    "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/elements")
public class ElementsController {

  /** The types of elements that can be retrieved */
  public enum ElementType {
    ARTICLE,
    CONCLUSIONS,
    PREAMBLE,
    PREFACE
  }

  private final Map<ElementType, String> xPathsForTypeNodes =
      Map.ofEntries(
          Map.entry(ElementType.PREFACE, "//act/preface"),
          Map.entry(ElementType.PREAMBLE, "//act/preamble"),
          Map.entry(ElementType.ARTICLE, "//body/article"),
          Map.entry(ElementType.CONCLUSIONS, "//act/conclusions"));

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
   * @param type The type(s) of the elements that should be returned. Elements are returned in the
   *     order of the types, and then in the order of elements in the norm.
   * @param amendedBy Only the elements modified by the norm of the given ELI will be returned.
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
      @RequestParam final ElementType[] type,
      @RequestParam final Optional<String> amendedBy) {

    final String eli =
        buildEli(agent, year, naturalIdentifier, pointInTime, version, language, subtype);

    // check targetNorm
    var targetNorm = loadNormUseCase.loadNorm(new LoadNormUseCase.Query(eli));
    if (targetNorm.isEmpty()) return ResponseEntity.notFound().build();

    // get elements and return
    return ResponseEntity.ok(
        getElementsResponseEntrySchemas(type, amendedBy, targetNorm.get(), eli));
  }

  private @NotNull List<ElementsResponseEntrySchema> getElementsResponseEntrySchemas(
      ElementType[] type, Optional<String> amendedBy, Norm targetNorm, String eli) {
    // Calculate the XPath based on the types via a Map defined above
    var combinedXPaths =
        String.join("|", Arrays.stream(type).map(xPathsForTypeNodes::get).toList());

    // Source EIDs from passive mods
    var passiveModsSourceEids =
        targetNorm.getPassiveModifications().stream()
            .filter(
                passiveMod -> {
                  if (amendedBy.isEmpty()) return true;

                  return passiveMod.getSourceEli().orElseThrow().equals(amendedBy.get());
                })
            .map(PassiveModification::getDestinationEid)
            .flatMap(Optional::stream)
            .toList();

    // get matching elements
    return loadNormUseCase.loadNorm(new LoadNormUseCase.Query(eli)).stream()
        .flatMap( // fetch nodes by type
            norm -> NodeParser.getNodesFromExpression(combinedXPaths, norm.getDocument()).stream())
        .flatMap(node -> Stream.of(ElementsResponseMapper.fromElementNode(node)))
        .filter( // filter by "amendedBy")
            element -> {
              // no amending law -> all elements are fine
              if (amendedBy.isEmpty()) return true;

              return passiveModsSourceEids.stream()
                  .anyMatch(modEid -> modEid.contains(element.getEid()));
            })
        .toList();
  }
}

@Component
class MyEnumConverter implements Converter<String, ElementsController.ElementType> {

  @Override
  public ElementsController.ElementType convert(String value) {
    return ElementsController.ElementType.valueOf(value.toUpperCase());
  }
}
