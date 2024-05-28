package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.Optional;
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
        LoadElementHtmlAtDateFromNormUseCase {
  private final LoadNormPort loadNormPort;
  private final XsltTransformationService xsltTransformationService;
  private final TimeMachineService timeMachineService;

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
        .map(norm -> NodeParser.getNodeFromExpression(xPath, norm.getDocument()));
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
        .map(
            norm ->
                NodeParser.getNodeFromExpression(getXPathForEid(query.eid()), norm.getDocument()))
        .map(
            element ->
                new TransformLegalDocMlToHtmlUseCase.Query(XmlMapper.toString(element), false))
        .map(xsltTransformationService::transformLegalDocMlToHtml);
  }

  private String getXPathForEid(String eid) {
    return String.format("//*[@eId='%s']", eid);
  }
}
