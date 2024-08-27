package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.ArticleNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.ApplyPassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadArticleHtmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.TransformLegalDocMlToHtmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
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
  public String loadArticleHtml(final Query query) {
    var norm =
        loadNormPort
            .loadNorm(new LoadNormPort.Command(query.eli()))
            .orElseThrow(() -> new NormNotFoundException(query.eli()));

    if (query.atIsoDate() != null) {
      norm =
          timeMachineService.applyPassiveModifications(
              new ApplyPassiveModificationsUseCase.Query(norm, query.atIsoDate()));
    }

    return norm.getArticles().stream()
        .filter(
            article -> article.getEid().isPresent() && article.getEid().get().equals(query.eid()))
        .findFirst()
        .map(article -> XmlMapper.toString(article.getNode()))
        .map(
            xml ->
                xsltTransformationService.transformLegalDocMlToHtml(
                    new TransformLegalDocMlToHtmlUseCase.Query(xml, false, false)))
        .orElseThrow(() -> new ArticleNotFoundException(query.eli(), query.eid()));
  }
}
