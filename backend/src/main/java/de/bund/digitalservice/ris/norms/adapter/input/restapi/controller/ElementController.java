package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static de.bund.digitalservice.ris.norms.utils.EliBuilder.buildEli;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ElementResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ElementResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.ApplyPassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.TransformLegalDocMlToHtmlUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.TextualMod;
import de.bund.digitalservice.ris.norms.utils.EliBuilder;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Node;

/**
 * Custom enum converter for converting the string values of the element types enum to lowercase.
 * This allows us to use a nicer lowercase spelling in the API requests while adhering to the naming
 * convention of UPPERCASE enums in Java code.
 */
@Component
class LowercaseEnumConverter implements Converter<String, ElementController.ElementType> {
  @Override
  public ElementController.ElementType convert(String value) {
    return ElementController.ElementType.valueOf(value.toUpperCase());
  }
}

/** Controller for retrieving a norm's elements. */
@RestController
@RequestMapping(
    "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/elements")
public class ElementController {

  /** The types of elements that can be retrieved. */
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
          // TODO: Hannes - Test new XPath of article
          Map.entry(ElementType.ARTICLE, "//body//article"),
          Map.entry(ElementType.CONCLUSIONS, "//act/conclusions"));

  private final LoadNormUseCase loadNormUseCase;
  private final TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase;
  private final ApplyPassiveModificationsUseCase applyPassiveModificationsUseCase;

  public ElementController(
      LoadNormUseCase loadNormUseCase,
      TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase,
      ApplyPassiveModificationsUseCase applyPassiveModificationsUseCase) {
    this.loadNormUseCase = loadNormUseCase;
    this.transformLegalDocMlToHtmlUseCase = transformLegalDocMlToHtmlUseCase;
    this.applyPassiveModificationsUseCase = applyPassiveModificationsUseCase;
  }

  /**
   * Retrieves a norm's element's HTML preview
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a href=
   * "https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param agent DE: "Verkündungsblatt"
   * @param year DE "Verkündungsjahr"
   * @param naturalIdentifier DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @param eid EID of the element to return
   * @param atIsoDate Render the version of the law valid at the given date (with passive changes
   *     applied up to that date)
   * @return A {@link ResponseEntity} containing the HTML.
   *     <p>Returns HTTP 400 (Bad Request) if ELI or EID can not be found
   */
  @GetMapping(
      path = "/{eid}",
      produces = {TEXT_HTML_VALUE})
  public ResponseEntity<String> getElementHtmlPreview(
      @PathVariable final String agent,
      @PathVariable final String year,
      @PathVariable final String naturalIdentifier,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype,
      @PathVariable final String eid,
      @RequestParam Optional<String> atIsoDate) {

    var eli =
        EliBuilder.buildEli(
            agent, year, naturalIdentifier, pointInTime, version, language, subtype);

    if (atIsoDate.isPresent()) {
      try {
        DateTimeFormatter.ISO_DATE_TIME.parse(atIsoDate.get());
      } catch (Exception e) {
        return ResponseEntity.badRequest().build();
      }

      return loadNormUseCase
          .loadNorm(new LoadNormUseCase.Query(eli))
          .map(
              norm ->
                  applyPassiveModificationsUseCase.applyPassiveModifications(
                      new ApplyPassiveModificationsUseCase.Query(
                          norm, Instant.parse(atIsoDate.get()))))
          .map(
              norm -> {
                var elementXpath = String.format("//*[@eId='%s']", eid);
                return NodeParser.getNodeFromExpression(elementXpath, norm.getDocument());
              })
          .map(
              element ->
                  transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(
                      new TransformLegalDocMlToHtmlUseCase.Query(
                          XmlMapper.toString(element), false)))
          .map(ResponseEntity::ok)
          .orElse(ResponseEntity.notFound().build());
    }

    return loadNormUseCase
        .loadNorm(new LoadNormUseCase.Query(eli))
        .map(
            norm -> {
              var elementXpath = String.format("//*[@eId='%s']", eid);
              return NodeParser.getNodeFromExpression(elementXpath, norm.getDocument());
            })
        .map(
            element ->
                transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(
                    new TransformLegalDocMlToHtmlUseCase.Query(XmlMapper.toString(element), false)))
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Retrieves a norm's element's information
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a href=
   * "https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param agent DE: "Verkündungsblatt"
   * @param year DE "Verkündungsjahr"
   * @param naturalIdentifier DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @param eid EID of the element to return
   * @return A {@link ResponseEntity} containing the element's information.
   *     <p>Returns HTTP 400 (Bad Request) if ELI or EID can not be found
   */
  @GetMapping(
      path = "/{eid}",
      produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<ElementResponseSchema> getElementInfo(
      @PathVariable final String agent,
      @PathVariable final String year,
      @PathVariable final String naturalIdentifier,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype,
      @PathVariable final String eid) {

    var eli =
        EliBuilder.buildEli(
            agent, year, naturalIdentifier, pointInTime, version, language, subtype);

    return loadNormUseCase
        .loadNorm(new LoadNormUseCase.Query(eli))
        .map(
            norm -> {
              var elementXpath = String.format("//*[@eId='%s']", eid);
              return NodeParser.getNodeFromExpression(elementXpath, norm.getDocument());
            })
        .map(ElementResponseMapper::fromElementNode)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
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
  public ResponseEntity<List<ElementResponseSchema>> getList(
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
        getElements(type, amendedBy, targetNorm.get(), eli).stream()
            .map(ElementResponseMapper::fromElementNode)
            .toList());
  }

  private @NotNull List<Node> getElements(
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

                  return passiveMod
                      .getSourceHref()
                      .flatMap(Href::getEli)
                      .orElseThrow()
                      .equals(amendedBy.get());
                })
            .map(TextualMod::getDestinationHref)
            .flatMap(Optional::stream)
            .map(Href::getEId)
            .flatMap(Optional::stream)
            .toList();

    // get matching elements
    return loadNormUseCase.loadNorm(new LoadNormUseCase.Query(eli)).stream()
        .flatMap( // fetch nodes by type
            norm -> NodeParser.getNodesFromExpression(combinedXPaths, norm.getDocument()).stream())
        .filter( // filter by "amendedBy")
            element -> {
              // no amending law -> all elements are fine
              if (amendedBy.isEmpty()) return true;

              var eId = NodeParser.getValueFromExpression("./@eId", element).orElseThrow();
              return passiveModsSourceEids.stream().anyMatch(modEid -> modEid.contains(eId));
            })
        .toList();
  }
}
