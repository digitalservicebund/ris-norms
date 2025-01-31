package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ArticleResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ArticleResponseSchema;
import de.bund.digitalservice.ris.norms.application.exception.ArticleNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import java.time.Instant;
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
  "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/articles"
)
public class ArticleController {

  private final LoadRegelungstextUseCase loadRegelungstextUseCase;
  private final LoadArticlesFromDokumentUseCase loadArticlesFromDokumentUseCase;
  private final LoadSpecificArticlesXmlFromNormUseCase loadSpecificArticlesXmlFromNormUseCase;
  private final TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase;
  private final LoadArticleHtmlUseCase loadArticleHtmlUseCase;

  public ArticleController(
    LoadRegelungstextUseCase loadRegelungstextUseCase1,
    LoadArticlesFromDokumentUseCase loadArticlesFromDokumentUseCase,
    LoadSpecificArticlesXmlFromNormUseCase loadSpecificArticlesXmlFromNormUseCase,
    TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase,
    LoadArticleHtmlUseCase loadArticleHtmlUseCase
  ) {
    this.loadRegelungstextUseCase = loadRegelungstextUseCase1;
    this.loadArticlesFromDokumentUseCase = loadArticlesFromDokumentUseCase;
    this.loadSpecificArticlesXmlFromNormUseCase = loadSpecificArticlesXmlFromNormUseCase;
    this.transformLegalDocMlToHtmlUseCase = transformLegalDocMlToHtmlUseCase;
    this.loadArticleHtmlUseCase = loadArticleHtmlUseCase;
  }

  /**
   * Retrieves all articles of a norm based on its expression ELI. The ELI's components are
   * interpreted as query parameters.
   *
   * @param eli Eli of the request
   * @param amendedBy Filter the articles to articles amended by the given norm. Must be the eli of
   *     the amending norm.
   * @param amendedAt Filter the articles to articles amended at the given livecycle event. Must be
   *     the eid of the livecycle event.
   * @return A {@link ResponseEntity} containing the retrieved norm.
   *     <p>Returns HTTP 200 (OK) and the norm if found.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   */
  @GetMapping(produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<List<ArticleResponseSchema>> getArticles(
    final DokumentExpressionEli eli,
    @RequestParam final Optional<DokumentExpressionEli> amendedBy,
    @RequestParam final Optional<String> amendedAt
  ) {
    final var query = new LoadArticlesFromDokumentUseCase.Query(
      eli,
      amendedBy.orElse(null),
      amendedAt.orElse(null)
    );

    final var articlesWithZf0 = loadArticlesFromDokumentUseCase
      .loadArticlesFromDokument(query)
      .stream()
      .map(ArticleResponseMapper::fromNormArticle)
      .toList();

    return ResponseEntity.ok(articlesWithZf0);
  }

  /**
   * Retrieves articles of a specific type within a norm as HTML. The norm is identified by its
   * expression ELI. The ELI's components are interpreted as query parameters.
   *
   * @param eli ELI of the norm
   * @param refersTo DE: "Artikeltyp" - The articles are filtered to only include articles of this
   *     type.
   * @return A {@link ResponseEntity} containing the retrieved norm's articles as html.
   *     <p>Returns HTTP 200 (OK) and the articles as HTML if found.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found or the norm doesn't contain
   *     articles of that type
   */
  @GetMapping(produces = { TEXT_HTML_VALUE })
  public ResponseEntity<String> getArticlesRender(
    final DokumentExpressionEli eli,
    @RequestParam(required = false, name = "refersTo") final String refersTo
  ) {
    String articles = loadSpecificArticlesXmlFromNormUseCase
      .loadSpecificArticlesXmlFromNorm(
        new LoadSpecificArticlesXmlFromNormUseCase.Query(eli, refersTo)
      )
      .stream()
      .map(xml ->
        this.transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(
            new TransformLegalDocMlToHtmlUseCase.Query(xml, false, false)
          ) +
        "\n"
      )
      .reduce("", String::concat);

    String divWrapped = "<div>\n" + articles + "</div>\n";

    return ResponseEntity.ok(divWrapped);
  }

  /**
   * Retrieves an article of a norm based on the norms expression ELI and the articles eid. The
   * ELI's components are interpreted as query parameters.
   *
   * @param eli Eli of the request
   * @param eid eid of the article
   * @return A {@link ResponseEntity} containing the retrieved norm.
   *     <p>Returns HTTP 200 (OK) and the norm if found.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   */
  @GetMapping(path = "/{eid}", produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<ArticleResponseSchema> getArticle(
    final DokumentExpressionEli eli,
    @PathVariable final String eid
  ) {
    final var regelungstext = loadRegelungstextUseCase.loadRegelungstext(
      new LoadRegelungstextUseCase.Query(eli)
    );

    // The response type is richer than the domain "Norm" type, hence the separate mapper
    return regelungstext
      .getArticles()
      .stream()
      .filter(article -> article.getEid().equals(eid))
      .findFirst()
      .map(ArticleResponseMapper::fromNormArticle)
      .map(ResponseEntity::ok)
      .orElseThrow(() -> new ArticleNotFoundException(eli.toString(), eid));
  }

  /**
   * Retrieves an article of a norm based on the norms expression ELI and the articles eid. The
   * ELI's components are interpreted as query parameters.
   *
   * @param eli Eli of the request
   * @param eid eid of the article
   * @param atIsoDate ISO date string indicating which modifications should be applied before the
   *     HTML gets rendered and returned.
   * @return A {@link ResponseEntity} containing the retrieved article as rendered HTML.
   *     <p>Returns HTTP 200 (OK) and the rendered HTML if found.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   */
  @GetMapping(path = "/{eid}", produces = { TEXT_HTML_VALUE })
  public ResponseEntity<String> getArticleRender(
    final DokumentExpressionEli eli,
    @PathVariable final String eid,
    @RequestParam Optional<Instant> atIsoDate
  ) {
    var query = new LoadArticleHtmlUseCase.Query(eli, eid, atIsoDate.orElse(null));
    var articleHtml = loadArticleHtmlUseCase.loadArticleHtml(query);
    return ResponseEntity.ok(articleHtml);
  }
}
