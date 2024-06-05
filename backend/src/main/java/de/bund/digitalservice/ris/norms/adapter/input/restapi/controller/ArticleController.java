package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ArticleResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ArticleResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.domain.entity.Analysis;
import de.bund.digitalservice.ris.norms.domain.entity.Eli;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.TextualMod;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Controller for norm-related actions. */
@RestController
@RequestMapping(
    "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/articles")
public class ArticleController {

  private final LoadNormUseCase loadNormUseCase;
  private final LoadSpecificArticleXmlFromNormUseCase loadSpecificArticleXmlFromNormUseCase;
  private final TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase;
  private final LoadArticleHtmlUseCase loadArticleHtmlUseCase;
  private final LoadZf0UseCase loadZf0UseCase;

  public ArticleController(
      LoadNormUseCase loadNormUseCase,
      LoadSpecificArticleXmlFromNormUseCase loadSpecificArticleXmlFromNormUseCase,
      TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase,
      LoadArticleHtmlUseCase loadArticleHtmlUseCase,
      LoadZf0UseCase loadZf0UseCase) {
    this.loadNormUseCase = loadNormUseCase;
    this.loadSpecificArticleXmlFromNormUseCase = loadSpecificArticleXmlFromNormUseCase;
    this.transformLegalDocMlToHtmlUseCase = transformLegalDocMlToHtmlUseCase;
    this.loadArticleHtmlUseCase = loadArticleHtmlUseCase;
    this.loadZf0UseCase = loadZf0UseCase;
  }

  /**
   * Retrieves all articles of a norm based on its expression ELI. The ELI's components are
   * interpreted as query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param eli Eli of the request
   * @param amendedBy Filter the articles to articles amended by the given norm. Must be the eli of
   *     the amending norm. Requires amendedAt.
   * @param amendedAt Filter the articles to articles amended at the given livecycle event. Must be
   *     the eid of the livecycle event. Requires amendedBy.
   * @return A {@link ResponseEntity} containing the retrieved norm.
   *     <p>Returns HTTP 200 (OK) and the norm if found.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   */
  @GetMapping(produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<List<ArticleResponseSchema>> getArticles(
      final Eli eli,
      @RequestParam final Optional<String> amendedBy,
      @RequestParam final Optional<String> amendedAt) {
    final var optionalNorm = loadNormUseCase.loadNorm(new LoadNormUseCase.Query(eli.getValue()));

    // amendedAt and amendedBy refer to passive modifications (i.e. amended at a specific
    // date or by a specific change law). So we collect a list of all passive modifications
    // matching both criteria.

    var passiveModificationsAmendedAtOrBy =
        optionalNorm
            .flatMap(n -> n.getMeta().getAnalysis().map(Analysis::getPassiveModifications))
            .orElse(Collections.emptyList())
            .stream()
            .filter(
                passiveModification -> {
                  if (amendedBy.isEmpty()) return true;
                  else
                    return passiveModification
                        .getSourceHref()
                        .flatMap(Href::getEli)
                        .equals(amendedBy);
                })
            .filter(
                passiveModification -> {
                  if (amendedAt.isEmpty()) return true;
                  else
                    return optionalNorm
                        .get()
                        .getStartEventRefForTemporalGroup(
                            passiveModification.getForcePeriodEid().orElseThrow())
                        .equals(amendedAt);
                })
            .toList();

    return ResponseEntity.ok(
        optionalNorm.map(Norm::getArticles).stream()
            .flatMap(List::stream)
            .filter(
                article -> {
                  // If we don't filter by anything related to passive modifications,
                  // return all articles.
                  if (amendedBy.isEmpty() && amendedAt.isEmpty()) {
                    return true;
                  }

                  // If we filter by amendedAt or amendedBy: Those properties are found
                  // in the passive modifications we already collected above. What's left
                  // now is to only return the articles that are going to be modified by
                  // those passive modifications.
                  return passiveModificationsAmendedAtOrBy.stream()
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
                })
            .map(
                article -> {
                  var targetLawZf0 =
                      article
                          .getAffectedDocumentEli()
                          .flatMap(
                              eliTargetLaw ->
                                  loadNormUseCase.loadNorm(new LoadNormUseCase.Query(eliTargetLaw)))
                          .map(
                              targetLaw ->
                                  loadZf0UseCase.loadZf0(
                                      new LoadZf0UseCase.Query(
                                          optionalNorm.get(), targetLaw, true)))
                          .orElse(null);
                  return ArticleResponseMapper.fromNormArticle(article, targetLawZf0);
                })
            .toList());
  }

  /**
   * Retrieves a norm's articles as html based on its expression ELI. The ELI's components are
   * interpreted as query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param eli Eli of the request
   * @param refersTo DE: "Artikeltyp" - The articles are filtered to only include articles of this
   *     type.
   * @return A {@link ResponseEntity} containing the retrieved norm's articles as html.
   *     <p>Returns HTTP 200 (OK) and the norm if found.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   */
  @GetMapping(produces = {TEXT_HTML_VALUE})
  public ResponseEntity<String> getArticlesRender(
      final Eli eli, @RequestParam(required = false, name = "refersTo") final String refersTo) {
    String articles =
        loadSpecificArticleXmlFromNormUseCase
            .loadSpecificArticles(
                new LoadSpecificArticleXmlFromNormUseCase.Query(eli.getValue(), refersTo))
            .stream()
            .map(
                xml ->
                    this.transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(
                            new TransformLegalDocMlToHtmlUseCase.Query(xml, false))
                        + "\n")
            .reduce("", String::concat);

    String divWrapped = "<div>\n" + articles + "</div>\n";

    return (articles.isEmpty()) ? ResponseEntity.notFound().build() : ResponseEntity.ok(divWrapped);
  }

  /**
   * Retrieves an article of a norm based on the norms expression ELI and the articles eid. The
   * ELI's components are interpreted as query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param eli Eli of the request
   * @param eid eid of the article
   * @return A {@link ResponseEntity} containing the retrieved norm.
   *     <p>Returns HTTP 200 (OK) and the norm if found.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   */
  @GetMapping(
      path = "/{eid}",
      produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<ArticleResponseSchema> getArticle(
      final Eli eli, @PathVariable final String eid) {
    var optionalNorm = loadNormUseCase.loadNorm(new LoadNormUseCase.Query(eli.getValue()));
    var optionalArticle =
        optionalNorm.map(Norm::getArticles).stream()
            .flatMap(List::stream)
            .filter(article -> article.getEid().isPresent() && article.getEid().get().equals(eid))
            .findFirst();

    if (optionalArticle.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    var article = optionalArticle.get();
    var targetLawZf0 =
        article
            .getAffectedDocumentEli()
            .flatMap(
                eliTargetLaw -> loadNormUseCase.loadNorm(new LoadNormUseCase.Query(eliTargetLaw)))
            .map(
                targetLaw ->
                    loadZf0UseCase.loadZf0(
                        new LoadZf0UseCase.Query(optionalNorm.get(), targetLaw, true)))
            .orElse(null);

    // The response type is richer than the domain "Norm" type, hence the separate mapper
    return ResponseEntity.ok(ArticleResponseMapper.fromNormArticle(article, targetLawZf0));
  }

  /**
   * Retrieves an article of a norm based on the norms expression ELI and the articles eid. The
   * ELI's components are interpreted as query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param eli Eli of the request
   * @param eid eid of the article
   * @param atIsoDate ISO date string indicating which modifications should be applied before the
   *     HTML gets rendered and returned.
   * @return A {@link ResponseEntity} containing the retrieved article as rendered HTML.
   *     <p>Returns HTTP 200 (OK) and the rendered HTML if found.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   */
  @GetMapping(
      path = "/{eid}",
      produces = {TEXT_HTML_VALUE})
  public ResponseEntity<String> getArticleRender(
      final Eli eli, @PathVariable final String eid, @RequestParam Optional<Instant> atIsoDate) {
    return loadArticleHtmlUseCase
        .loadArticleHtml(
            new LoadArticleHtmlUseCase.Query(eli.getValue(), eid, atIsoDate.orElse(null)))
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
