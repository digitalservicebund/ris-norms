package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.ApplyPassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadArticleHtmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.TransformLegalDocMlToHtmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/** Service for loading a norm's articles */
@Service
public class ArticleService implements LoadArticleHtmlUseCase {

  LoadNormPort loadNormPort;
  TimeMachineService timeMachineService;
  XsltTransformationService xsltTransformationService;

  public ArticleService(
      LoadNormPort loadNormPort,
      TimeMachineService timeMachineService,
      XsltTransformationService xsltTransformationService) {
    this.loadNormPort = loadNormPort;
    this.timeMachineService = timeMachineService;
    this.xsltTransformationService = xsltTransformationService;
  }

  @Override
  public Optional<String> loadArticleHtml(final Query query) {
    return loadNormPort
        .loadNorm(new LoadNormPort.Command(query.eli()))
        .map(
            norm -> {
              if (query.atIsoDate() == null)
                return norm; // no date given -> use the norm unchanged
              else {
                return timeMachineService.applyPassiveModifications(
                    new ApplyPassiveModificationsUseCase.Query(norm, query.atIsoDate()));
              }
            })
        .map(Norm::getArticles)
        .stream()
        .flatMap(List::stream)
        .filter(article -> article.getEid().isPresent() && article.getEid().get().equals(query.eid()))
        .findFirst()
        .map(article -> XmlMapper.toString(article.getNode()))
        .map(
            xml ->
                xsltTransformationService.transformLegalDocMlToHtml(
                    new TransformLegalDocMlToHtmlUseCase.Query(xml, false)));
  }
}
