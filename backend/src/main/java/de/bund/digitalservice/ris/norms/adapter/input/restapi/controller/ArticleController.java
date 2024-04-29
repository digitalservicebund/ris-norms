package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ArticleResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ArticleResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNextVersionOfNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadSpecificArticleXmlFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.TransformLegalDocMlToHtmlUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.List;
import org.jetbrains.annotations.NotNull;
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
  private final LoadNextVersionOfNormUseCase loadNextVersionOfNormUseCase;
  private final LoadSpecificArticleXmlFromNormUseCase loadSpecificArticleXmlFromNormUseCase;
  private final TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase;

  public ArticleController(
      LoadNormUseCase loadNormUseCase,
      LoadNextVersionOfNormUseCase loadNextVersionOfNormUseCase,
      LoadSpecificArticleXmlFromNormUseCase loadSpecificArticleXmlFromNormUseCase,
      TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase) {
    this.loadNormUseCase = loadNormUseCase;
    this.loadNextVersionOfNormUseCase = loadNextVersionOfNormUseCase;
    this.loadSpecificArticleXmlFromNormUseCase = loadSpecificArticleXmlFromNormUseCase;
    this.transformLegalDocMlToHtmlUseCase = transformLegalDocMlToHtmlUseCase;
  }

  /**
   * Retrieves all articles of a norm based on its expression ELI. The ELI's components are
   * interpreted as query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param agent DE: "Verkündungsblatt"
   * @param year DE "Verkündungsjahr"
   * @param naturalIdentifier DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @return A {@link ResponseEntity} containing the retrieved norm.
   *     <p>Returns HTTP 200 (OK) and the norm if found.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   */
  @GetMapping(produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<List<ArticleResponseSchema>> getArticles(
      @PathVariable final String agent,
      @PathVariable final String year,
      @PathVariable final String naturalIdentifier,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype) {
    final String eli =
        buildEli(agent, year, naturalIdentifier, pointInTime, version, language, subtype);

    return ResponseEntity.ok(
        loadNormUseCase.loadNorm(new LoadNormUseCase.Query(eli)).map(Norm::getArticles).stream()
            .flatMap(List::stream)
            .map(
                article -> {
                  var targetLawZf0 =
                      article
                          .getAffectedDocumentEli()
                          .flatMap(
                              affectedDocumentEli ->
                                  loadNextVersionOfNormUseCase.loadNextVersionOfNorm(
                                      new LoadNextVersionOfNormUseCase.Query(affectedDocumentEli)))
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
   * @param agent DE: "Verkündungsblatt"
   * @param year DE "Verkündungsjahr"
   * @param naturalIdentifier DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @param refersTo DE: "Artikeltyp" - The articles are filtered to only include articles of this
   *     type.
   * @return A {@link ResponseEntity} containing the retrieved norm's articles as html.
   *     <p>Returns HTTP 200 (OK) and the norm if found.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   */
  @GetMapping(produces = {TEXT_HTML_VALUE})
  public ResponseEntity<String> getArticlesRender(
      @PathVariable final String agent,
      @PathVariable final String year,
      @PathVariable final String naturalIdentifier,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype,
      @RequestParam(required = false, name = "refersTo") final String refersTo) {
    final String eli =
        buildEli(agent, year, naturalIdentifier, pointInTime, version, language, subtype);

    String articles =
        loadSpecificArticleXmlFromNormUseCase
            .loadSpecificArticles(new LoadSpecificArticleXmlFromNormUseCase.Query(eli, refersTo))
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
   * @param agent DE: "Verkündungsblatt"
   * @param year DE "Verkündungsjahr"
   * @param naturalIdentifier DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @param eid eid of the article
   * @return A {@link ResponseEntity} containing the retrieved norm.
   *     <p>Returns HTTP 200 (OK) and the norm if found.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   */
  @GetMapping(
      path = "/{eid}",
      produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<ArticleResponseSchema> getArticle(
      @PathVariable final String agent,
      @PathVariable final String year,
      @PathVariable final String naturalIdentifier,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype,
      @PathVariable final String eid) {
    final String eli =
        buildEli(agent, year, naturalIdentifier, pointInTime, version, language, subtype);

    var optionalArticle =
        loadNormUseCase.loadNorm(new LoadNormUseCase.Query(eli)).map(Norm::getArticles).stream()
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
                affectedDocumentEli ->
                    loadNextVersionOfNormUseCase.loadNextVersionOfNorm(
                        new LoadNextVersionOfNormUseCase.Query(affectedDocumentEli)))
            .orElse(null);

    return ResponseEntity.ok(ArticleResponseMapper.fromNormArticle(article, targetLawZf0));
  }

  @NotNull
  private static String buildEli(
      String agent,
      String year,
      String naturalIdentifier,
      String pointInTime,
      String version,
      String language,
      String subtype) {
    return "eli/bund/"
        + agent
        + "/"
        + year
        + "/"
        + naturalIdentifier
        + "/"
        + pointInTime
        + "/"
        + version
        + "/"
        + language
        + "/"
        + subtype;
  }
}
