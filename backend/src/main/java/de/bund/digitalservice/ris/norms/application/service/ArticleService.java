package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.ArticleNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.RegelungstextNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadRegelungstextPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import org.springframework.stereotype.Service;

/** Service for loading a norm's articles */
@Service
public class ArticleService
  implements
    LoadArticleHtmlUseCase,
    LoadArticlesFromDokumentUseCase,
    LoadSpecificArticlesXmlFromDokumentUseCase {

  LoadRegelungstextPort loadRegelungstextPort;
  TimeMachineService timeMachineService;
  XsltTransformationService xsltTransformationService;

  public ArticleService(
    LoadRegelungstextPort loadRegelungstextPort,
    TimeMachineService timeMachineService,
    XsltTransformationService xsltTransformationService
  ) {
    this.loadRegelungstextPort = loadRegelungstextPort;
    this.timeMachineService = timeMachineService;
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
    final var amendedAt = query.amendedAt();
    final var amendedBy = query.amendedBy();

    final var regelungstext = loadRegelungstextPort
      .loadRegelungstext(new LoadRegelungstextPort.Command(query.eli()))
      .orElseThrow(() -> new RegelungstextNotFoundException(query.eli().toString()));

    List<Article> articles = regelungstext.getArticles();

    // Filter list of articles by passive mods if at least one filter is specified
    if (amendedBy != null || amendedAt != null) {
      var filterPassiveMods = getPassiveModsAmendingByOrAt(regelungstext, amendedBy, amendedAt);
      var passiveModFilter = createPassiveModFilter(filterPassiveMods);
      articles = articles.stream().filter(passiveModFilter).toList();
    }

    return articles;
  }

  private List<TextualMod> getPassiveModsAmendingByOrAt(
    final Regelungstext fromRegelungstext,
    final DokumentExpressionEli amendingBy,
    final String amendingAt
  ) {
    if (amendingBy == null && amendingAt == null) return List.of();

    return fromRegelungstext
      .getMeta()
      .getAnalysis()
      .map(Analysis::getPassiveModifications)
      .orElse(Collections.emptyList())
      .stream()
      .filter(passiveModification -> {
        if (amendingBy == null) return true;

        return passiveModification
          .getSourceHref()
          .flatMap(Href::getExpressionEli)
          .map(sourceEli -> sourceEli.equals(amendingBy))
          .orElse(false);
      })
      .filter(passiveModification -> {
        if (amendingAt == null) return true;

        return passiveModification
          .getForcePeriodEid()
          .flatMap(fromRegelungstext::getStartEventRefForTemporalGroup)
          .map(startEventRef -> startEventRef.equals(amendingAt))
          .orElse(false);
      })
      .toList();
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

  private Predicate<Article> createPassiveModFilter(final List<TextualMod> mods) {
    return article ->
      // If we filter by amendedAt or amendedBy: Those properties are found
      // in the passive modifications we already collected above. What's left
      // now is to only return the articles that are going to be modified by
      // those passive modifications.
      mods
        .stream()
        .map(TextualMod::getDestinationHref)
        .flatMap(Optional::stream)
        .map(Href::getEId)
        .flatMap(Optional::stream)
        .anyMatch(destinationEid ->
          // Modifications can be either on the article itself or anywhere
          // inside the article, hence the "contains" rather than exact
          // matching.
          destinationEid.contains(article.getEid())
        );
  }
}
