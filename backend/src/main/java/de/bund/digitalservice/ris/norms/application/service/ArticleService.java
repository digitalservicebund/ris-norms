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
  public String loadArticleHtml(final LoadArticleHtmlUseCase.Options options) {
    var regelungstext = loadRegelungstextPort
      .loadRegelungstext(new LoadRegelungstextPort.Options(options.eli()))
      .orElseThrow(() -> new NormNotFoundException(options.eli()));

    return regelungstext
      .getArticles()
      .stream()
      .filter(article -> article.getEid().equals(options.eid()))
      .findFirst()
      .map(article -> XmlMapper.toString(article.getElement()))
      .map(xml ->
        xsltTransformationService.transformLegalDocMlToHtml(
          new TransformLegalDocMlToHtmlUseCase.Options(xml, false, false)
        )
      )
      .orElseThrow(() ->
        new ArticleNotFoundException(options.eli().toString(), options.eid().toString())
      );
  }

  @Override
  public List<Article> loadArticlesFromDokument(
    final LoadArticlesFromDokumentUseCase.Options options
  ) {
    final var regelungstext = loadRegelungstextPort
      .loadRegelungstext(new LoadRegelungstextPort.Options(options.eli()))
      .orElseThrow(() -> new RegelungstextNotFoundException(options.eli().toString()));

    return regelungstext.getArticles();
  }

  @Override
  public List<String> loadSpecificArticlesXmlFromDokument(
    LoadSpecificArticlesXmlFromDokumentUseCase.Options options
  ) {
    List<Article> articles = loadRegelungstextPort
      .loadRegelungstext(new LoadRegelungstextPort.Options(options.eli()))
      .orElseThrow(() -> new NormNotFoundException(options.eli()))
      .getArticles();

    if (options.refersTo() != null) {
      articles = articles
        .stream()
        .filter(a -> Objects.equals(a.getRefersTo().orElse(""), options.refersTo()))
        .toList();
    }

    if (articles.isEmpty()) {
      throw new ArticleOfTypeNotFoundException(options.eli().toString(), options.refersTo());
    }

    return articles.stream().map(a -> XmlMapper.toString(a.getElement())).toList();
  }
}
