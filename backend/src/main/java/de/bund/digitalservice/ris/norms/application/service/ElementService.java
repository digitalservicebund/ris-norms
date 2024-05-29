package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.EId;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.TextualMod;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.List;
import java.util.Map;
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
    ARTICLE("article"),
    CONCLUSIONS("conclusions"),
    PREAMBLE("preamble"),
    PREFACE("preface");

    public final String label;

    ElementType(String label) {
      this.label = label;
    }

    /**
     * Infers the enum value based on the provided label.
     *
     * @param label Label to infer the enum value from.
     * @return Enum value for that label
     * @throws LoadElementsByTypeFromNormUseCase.UnsupportedElementTypeException If no value exists
     *     for that label.
     */
    public static ElementType fromLabel(String label) throws UnsupportedElementTypeException {
      for (ElementType type : values()) {
        if (type.label.equals(label)) {
          return type;
        }
      }

      throw new UnsupportedElementTypeException(label + " is not supported");
    }
  }

  private final Map<ElementType, String> xPathsForTypes =
      Map.ofEntries(
          Map.entry(ElementType.PREFACE, "//act/preface"),
          Map.entry(ElementType.PREAMBLE, "//act/preamble"),
          Map.entry(ElementType.ARTICLE, "//body//article"),
          Map.entry(ElementType.CONCLUSIONS, "//act/conclusions"));

  public ElementService(
      LoadNormPort loadNormPort,
      XsltTransformationService xsltTransformationService,
      TimeMachineService timeMachineService) {
    this.loadNormPort = loadNormPort;
    this.xsltTransformationService = xsltTransformationService;
    this.timeMachineService = timeMachineService;
  }

  @Override
  public Optional<Node> loadElementFromNorm(final LoadElementFromNormUseCase.Query query) {
    var xPath = getXPathForEid(query.eid());

    return loadNormPort
        .loadNorm(new LoadNormPort.Command(query.eli()))
        .flatMap(norm -> NodeParser.getNodeFromExpression(xPath, norm.getDocument()));
  }

  @Override
  public Optional<String> loadElementHtmlFromNorm(
      final LoadElementHtmlFromNormUseCase.Query query) {
    return loadElementFromNorm(new LoadElementFromNormUseCase.Query(query.eli(), query.eid()))
        .map(XmlMapper::toString)
        .map(rawXml -> new TransformLegalDocMlToHtmlUseCase.Query(rawXml, false))
        .map(xsltTransformationService::transformLegalDocMlToHtml);
  }

  @Override
  public Optional<String> loadElementHtmlAtDateFromNorm(
      final LoadElementHtmlAtDateFromNormUseCase.Query query) {
    return loadNormPort
        .loadNorm(new LoadNormPort.Command(query.eli()))
        .map(norm -> new ApplyPassiveModificationsUseCase.Query(norm, query.atDate()))
        .map(timeMachineService::applyPassiveModifications)
        .flatMap(
            norm ->
                NodeParser.getNodeFromExpression(getXPathForEid(query.eid()), norm.getDocument()))
        .map(
            element ->
                new TransformLegalDocMlToHtmlUseCase.Query(XmlMapper.toString(element), false))
        .map(xsltTransformationService::transformLegalDocMlToHtml);
  }

  @Override
  public List<Node> loadElementsByTypeFromNorm(LoadElementsByTypeFromNormUseCase.Query query)
      throws UnsupportedElementTypeException, NormNotFoundException {
    // No need to do anything if no types are requested
    if (query.elementType().isEmpty()) return List.of();

    var combinedXPaths =
        String.join(
            "|",
            query.elementType().stream()
                .map(ElementType::fromLabel)
                .map(xPathsForTypes::get)
                .toList());

    var norm =
        loadNormPort
            .loadNorm(new LoadNormPort.Command(query.eli()))
            .orElseThrow(() -> new NormNotFoundException(query.eli() + " does not exist"));

    // Source EIDs from passive mods
    var passiveModsDestinationEids =
        getDestinationEidsFromPassiveMods(norm.getPassiveModifications(), query.amendedBy());

    return NodeParser.getNodesFromExpression(combinedXPaths, norm.getDocument()).stream()
        .filter( // filter by "amendedBy")
            element -> {
              // no amending law -> all elements are fine
              if (query.amendedBy() == null) return true;

              var eId = EId.fromNode(element).map(EId::value).orElseThrow();
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
