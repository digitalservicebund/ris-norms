package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.ElementNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Analysis;
import de.bund.digitalservice.ris.norms.domain.entity.EId;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.TextualMod;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

/**
 * Implements various operations related to getting generic elements from a {@link Norm}. Note that
 * this is necessarily vague because we allow to work with any element that can be identified by an
 * eId. This can be many different things. If you need something more specific (e.g. specifically
 * related to articles), you should use a more specific service.
 */
@Service
public class ElementService
    implements LoadElementFromNormUseCase,
        LoadElementHtmlFromNormUseCase,
        LoadElementHtmlAtDateFromNormUseCase,
        LoadElementsByTypeFromNormUseCase {
  private final LoadNormPort loadNormPort;
  private final XsltTransformationService xsltTransformationService;
  private final TimeMachineService timeMachineService;

  /** The types of elements that can be retrieved from a norm. */
  public enum ElementType {
    ARTICLE("article", "//body//article"),
    BOOK("book", "//body//book"),
    CHAPTER("chapter", "//body//chapter"),
    CONCLUSIONS("conclusions", "//act/conclusions"),
    PART("part", "//body//part"),
    PREAMBLE("preamble", "//act/preamble"),
    PREFACE("preface", "//act/preface"),
    SECTION("section", "//body//section"),
    SUBSECTION("subsection", "//body//subsection"),
    SUBTITLE("subtitle", "//body//subtitle"),
    TITLE("title", "//body//title");

    /** The name of the element. */
    public final String label;

    /** The XPath used for finding elements of that type in a norm. */
    public final String xPath;

    ElementType(String label, String xPath) {
      this.label = label;
      this.xPath = xPath;
    }

    /**
     * Infers the enum value based on the provided label.
     *
     * @param label Label to infer the enum value from.
     * @return Enum value for that label
     */
    public static ElementType fromLabel(String label) {
      for (ElementType type : values()) {
        if (type.label.equals(label)) {
          return type;
        }
      }

      throw new UnsupportedElementTypeException(label + " is not supported");
    }
  }

  public ElementService(
      LoadNormPort loadNormPort,
      XsltTransformationService xsltTransformationService,
      TimeMachineService timeMachineService) {
    this.loadNormPort = loadNormPort;
    this.xsltTransformationService = xsltTransformationService;
    this.timeMachineService = timeMachineService;
  }

  @Override
  public Node loadElementFromNorm(final LoadElementFromNormUseCase.Query query) {
    final var xPath = getXPathForEid(query.eid());

    final var norm =
        loadNormPort
            .loadNorm(new LoadNormPort.Command(query.eli()))
            .orElseThrow(() -> new NormNotFoundException(query.eli()));

    return NodeParser.getNodeFromExpression(xPath, norm.getDocument())
        .orElseThrow(() -> new ElementNotFoundException(query.eli(), query.eid()));
  }

  @Override
  public String loadElementHtmlFromNorm(final LoadElementHtmlFromNormUseCase.Query query) {
    final var normXml =
        XmlMapper.toString(
            loadElementFromNorm(new LoadElementFromNormUseCase.Query(query.eli(), query.eid())));

    return xsltTransformationService.transformLegalDocMlToHtml(
        new TransformLegalDocMlToHtmlUseCase.Query(normXml, false, false));
  }

  @Override
  public String loadElementHtmlAtDateFromNorm(
      final LoadElementHtmlAtDateFromNormUseCase.Query query) {
    var norm =
        loadNormPort
            .loadNorm(new LoadNormPort.Command(query.eli()))
            .orElseThrow(() -> new NormNotFoundException(query.eli()));

    norm =
        timeMachineService.applyPassiveModifications(
            new ApplyPassiveModificationsUseCase.Query(norm, query.atDate()));

    final var element =
        NodeParser.getNodeFromExpression(getXPathForEid(query.eid()), norm.getDocument())
            .orElseThrow(() -> new ElementNotFoundException(query.eli(), query.eid()));

    return xsltTransformationService.transformLegalDocMlToHtml(
        new TransformLegalDocMlToHtmlUseCase.Query(XmlMapper.toString(element), false, false));
  }

  @Override
  public List<Node> loadElementsByTypeFromNorm(LoadElementsByTypeFromNormUseCase.Query query) {
    // No need to do anything if no types are requested
    if (query.elementType().isEmpty()) return List.of();

    final var combinedXPaths =
        String.join(
            "|",
            query.elementType().stream().map(label -> ElementType.fromLabel(label).xPath).toList());

    final var norm =
        loadNormPort
            .loadNorm(new LoadNormPort.Command(query.eli()))
            .orElseThrow(() -> new NormNotFoundException(query.eli()));

    // Source EIDs from passive mods
    final var passiveModsDestinationEids =
        getDestinationEidsFromPassiveMods(
            norm.getMeta()
                .getAnalysis()
                .map(Analysis::getPassiveModifications)
                .orElse(Collections.emptyList()),
            query.amendedBy());

    return NodeParser.getNodesFromExpression(combinedXPaths, norm.getDocument()).stream()
        .filter( // filter by "amendedBy")
            element -> {
              // no amending law -> all elements are fine
              if (query.amendedBy() == null) return true;

              var eId = EId.fromNode(element).value();
              return passiveModsDestinationEids.stream().anyMatch(modEid -> modEid.contains(eId));
            })
        .toList();
  }

  private String getXPathForEid(String eid) {
    return String.format("//*[@eId='%s']", eid);
  }

  private List<String> getDestinationEidsFromPassiveMods(
      List<TextualMod> mods, @Nullable String amendedBy) {
    return mods.stream()
        .filter(
            passiveMod -> {
              if (amendedBy == null) return true;

              return passiveMod
                  .getSourceHref()
                  .flatMap(Href::getEli)
                  .orElseThrow()
                  .equals(amendedBy);
            })
        .map(TextualMod::getDestinationHref)
        .flatMap(Optional::stream)
        .map(Href::getEId)
        .flatMap(Optional::stream)
        .toList();
  }
}
