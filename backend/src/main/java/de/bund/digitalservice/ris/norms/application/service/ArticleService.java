package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.ArticleNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
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
    implements LoadArticleHtmlUseCase,
        LoadArticlesFromNormUseCase,
        LoadSpecificArticlesXmlFromNormUseCase {

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
  public String loadArticleHtml(final LoadArticleHtmlUseCase.Query query) {
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

  @Override
  public List<Article> loadArticlesFromNorm(final LoadArticlesFromNormUseCase.Query query) {
    final var amendedAt = query.amendedAt();
    final var amendedBy = query.amendedBy();

    final var norm =
        loadNormPort
            .loadNorm(new LoadNormPort.Command(query.eli()))
            .orElseThrow(() -> new NormNotFoundException(query.eli()));

    List<Article> articles = norm.getArticles();

    // Filter list of articles by passive mods if at least one filter is specified
    if (amendedBy != null || amendedAt != null) {
      var filterPassiveMods = getPassiveModsAmendingByOrAt(norm, amendedBy, amendedAt);
      var passiveModFilter = createPassiveModFilter(filterPassiveMods);
      articles = articles.stream().filter(passiveModFilter).toList();
    }

    return articles;
  }

  private List<TextualMod> getPassiveModsAmendingByOrAt(
      final Norm fromNorm, final String amendingBy, final String amendingAt) {
    if (amendingBy == null && amendingAt == null) return List.of();

    return fromNorm
        .getMeta()
        .getAnalysis()
        .map(Analysis::getPassiveModifications)
        .orElse(Collections.emptyList())
        .stream()
        .filter(
            passiveModification -> {
              if (amendingBy == null) return true;

              return passiveModification
                  .getSourceHref()
                  .flatMap(Href::getEli)
                  .map(sourceEli -> sourceEli.equals(amendingBy))
                  .orElse(false);
            })
        .filter(
            passiveModification -> {
              if (amendingAt == null) return true;

              return passiveModification
                  .getForcePeriodEid()
                  .flatMap(fromNorm::getStartEventRefForTemporalGroup)
                  .map(startEventRef -> startEventRef.equals(amendingAt))
                  .orElse(false);
            })
        .toList();
  }

  @Override
  public List<String> loadSpecificArticlesXmlFromNorm(
      LoadSpecificArticlesXmlFromNormUseCase.Query query) {
    List<Article> articles =
        loadNormPort
            .loadNorm(new LoadNormPort.Command(query.eli()))
            .orElseThrow(() -> new NormNotFoundException(query.eli()))
            .getArticles();

    if (query.refersTo() != null) {
      articles =
          articles.stream()
              .filter(a -> Objects.equals(a.getRefersTo().orElse(""), query.refersTo()))
              .toList();
    }

    if (articles.isEmpty()) {
      throw new ArticleOfTypeNotFoundException(query.eli(), query.refersTo());
    }

    return articles.stream().map(a -> XmlMapper.toString(a.getNode())).toList();
  }

  private Predicate<Article> createPassiveModFilter(final List<TextualMod> mods) {
    return article ->
        // If we filter by amendedAt or amendedBy: Those properties are found
        // in the passive modifications we already collected above. What's left
        // now is to only return the articles that are going to be modified by
        // those passive modifications.
        mods.stream()
            .map(TextualMod::getDestinationHref)
            .flatMap(Optional::stream)
            .map(Href::getEId)
            .flatMap(Optional::stream)
            .anyMatch(
                destinationEid ->
                    // Modifications can be either on the article itself or anywhere
                    // inside the article, hence the "contains" rather than exact
                    // matching.
                    destinationEid.contains(article.getEid().orElseThrow()));
  }
}
