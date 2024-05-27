package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.ApplyPassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadArticleHtmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.TransformLegalDocMlToHtmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class ArticleService implements LoadArticleHtmlUseCase {

  LoadNormPort loadNormPort;
  TimeMachineService timeMachineService;
  XsltTransformationService xsltTransformationService;

  private ArticleService(
      LoadNormPort loadNormPort,
      TimeMachineService timeMachineService,
      XsltTransformationService xsltTransformationService) {
    this.loadNormPort = loadNormPort;
    this.timeMachineService = timeMachineService;
    this.xsltTransformationService = xsltTransformationService;
  }

  // TODO Hannes: Untested
  @Override
  public Optional<String> loadArticleHtml(final Query query) {
    return loadNormPort
        .loadNorm(new LoadNormPort.Command(query.normEli()))
        .map(
            norm -> {
              if (query.atIsoDate().isEmpty())
                return norm; // no date given -> use the norm unchanged
              else {
                return timeMachineService.applyPassiveModifications(
                    new ApplyPassiveModificationsUseCase.Query(
                        norm, Instant.parse(query.atIsoDate().get())));
              }
            })
        .map(Norm::getArticles)
        .stream()
        .flatMap(List::stream)
        // TODO: Hannes split into separate filter steps
        .filter(
            article ->
                article.getEid().isPresent() && article.getEid().get().equals(query.articleEid()))
        .findFirst()
        .map(article -> XmlMapper.toString(article.getNode()))
        .map(
            xml ->
                xsltTransformationService.transformLegalDocMlToHtml(
                    new TransformLegalDocMlToHtmlUseCase.Query(xml, false)));
  }
}
