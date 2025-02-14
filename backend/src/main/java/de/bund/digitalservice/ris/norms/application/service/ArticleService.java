package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.ArticleNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.RegelungstextNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadRegelungstextPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

/** Service for loading a norm's articles */
@Service
public class ArticleService
  implements
    LoadArticleHtmlUseCase,
    LoadArticlesFromDokumentUseCase,
    LoadSpecificArticlesXmlFromDokumentUseCase {

  LoadRegelungstextPort loadRegelungstextPort;
  XsltTransformationService xsltTransformationService;

  public ArticleService(
    LoadRegelungstextPort loadRegelungstextPort,
    XsltTransformationService xsltTransformationService
  ) {
    this.loadRegelungstextPort = loadRegelungstextPort;
    this.xsltTransformationService = xsltTransformationService;
  }

  @Override
  public String loadArticleHtml(final LoadArticleHtmlUseCase.Query query) {
    var regelungstext = loadRegelungstextPort
      .loadRegelungstext(new LoadRegelungstextPort.Command(query.eli()))
      .orElseThrow(() -> new NormNotFoundException(query.eli().toString()));

    return regelungstext
      .getArticles()
      .stream()
      .filter(article -> article.getEid().equals(query.eid()))
      .findFirst()
      .map(article -> XmlMapper.toString(article.getElement()))
      .map(xml ->
        xsltTransformationService.transformLegalDocMlToHtml(
          new TransformLegalDocMlToHtmlUseCase.Query(xml, false, false)
        )
      )
      .orElseThrow(() -> new ArticleNotFoundException(query.eli().toString(), query.eid()));
  }

  @Override
  public List<Article> loadArticlesFromDokument(final LoadArticlesFromDokumentUseCase.Query query) {
    final var regelungstext = loadRegelungstextPort
      .loadRegelungstext(new LoadRegelungstextPort.Command(query.eli()))
      .orElseThrow(() -> new RegelungstextNotFoundException(query.eli().toString()));

    return regelungstext.getArticles();
  }

  @Override
  public List<String> loadSpecificArticlesXmlFromDokument(
    LoadSpecificArticlesXmlFromDokumentUseCase.Query query
  ) {
    List<Article> articles = loadRegelungstextPort
      .loadRegelungstext(new LoadRegelungstextPort.Command(query.eli()))
      .orElseThrow(() -> new NormNotFoundException(query.eli().toString()))
      .getArticles();

    if (query.refersTo() != null) {
      articles =
      articles
        .stream()
        .filter(a -> Objects.equals(a.getRefersTo().orElse(""), query.refersTo()))
        .toList();
    }

    if (articles.isEmpty()) {
      throw new ArticleOfTypeNotFoundException(query.eli().toString(), query.refersTo());
    }

    return articles.stream().map(a -> XmlMapper.toString(a.getElement())).toList();
  }
}
