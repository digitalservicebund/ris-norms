package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.AmendingLawResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ArticleResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.AmendingLawResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ArticleResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ReleaseAmendingLawResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling amending laws in the REST API. This class is annotated with {@link
 * RestController} and {@link RequestMapping} to define the base URL for handling amending laws in
 * the API.
 */
@RestController
@RequestMapping("api/v1/amending-laws")
public class AmendingLawController {

  private final LoadAmendingLawUseCase loadAmendingLawUseCase;
  private final LoadAmendingLawXmlUseCase loadAmendingLawXmlUseCase;
  private final LoadAllAmendingLawsUseCase loadAllAmendingLawsUseCase;
  private final LoadArticlesUseCase loadArticlesUseCase;
  private final LoadArticleUseCase loadArticleUseCase;
  private final UpdateAmendingLawXmlUseCase updateAmendingLawXmlUseCase;
  private final ReleaseAmendingLawAndAllRelatedTargetLawsUseCase
      releaseAmendingLawAndAllRelatedTargetLawsUseCase;
  private final TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase;

  public AmendingLawController(
      LoadAmendingLawUseCase loadAmendingLawUseCase,
      LoadAmendingLawXmlUseCase loadAmendingLawXmlUseCase,
      LoadAllAmendingLawsUseCase loadAllAmendingLawsUseCase,
      LoadArticlesUseCase loadArticlesUseCase,
      LoadArticleUseCase loadArticleUseCase,
      UpdateAmendingLawXmlUseCase updateAmendingLawXmlUseCase,
      ReleaseAmendingLawAndAllRelatedTargetLawsUseCase
          releaseAmendingLawAndAllRelatedTargetLawsUseCase,
      TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase) {
    this.loadAmendingLawUseCase = loadAmendingLawUseCase;
    this.loadAmendingLawXmlUseCase = loadAmendingLawXmlUseCase;
    this.loadAllAmendingLawsUseCase = loadAllAmendingLawsUseCase;
    this.loadArticlesUseCase = loadArticlesUseCase;
    this.loadArticleUseCase = loadArticleUseCase;
    this.updateAmendingLawXmlUseCase = updateAmendingLawXmlUseCase;
    this.releaseAmendingLawAndAllRelatedTargetLawsUseCase =
        releaseAmendingLawAndAllRelatedTargetLawsUseCase;
    this.transformLegalDocMlToHtmlUseCase = transformLegalDocMlToHtmlUseCase;
  }

  /**
   * Retrieves all available amending laws
   *
   * @return A {@link ResponseEntity} containing the response schema for a list of amending laws
   *     <p>Returns HTTP 200 (OK) and a list of amending laws on successful execution.
   *     <p>If no law is found, the list is empty.
   */
  @GetMapping
  public ResponseEntity<List<AmendingLawResponseSchema>> getAllAmendingLaws() {
    final List<AmendingLaw> amendingLawWithArticles =
        loadAllAmendingLawsUseCase.loadAllAmendingLaws();
    final List<AmendingLawResponseSchema> responseSchemas =
        amendingLawWithArticles.stream().map(AmendingLawResponseMapper::fromUseCaseData).toList();
    return ResponseEntity.ok(responseSchemas);
  }

  /**
   * Retrieves an amending law based on its expression ELI. The ELI's components are interpreted as
   * query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param printAnnouncementGazette DE: "Verkündungsblatt"
   * @param printAnnouncementYear DE "Verkündungsjahr"
   * @param printAnnouncementPage DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @return A {@link ResponseEntity} containing the retrieved amending law.
   *     <p>Returns HTTP 200 (OK) and the amending law's data if found.
   *     <p>Returns HTTP 404 (Not Found) if the amending law is not found.
   */
  @GetMapping(
      path =
          "/eli/bund/{printAnnouncementGazette}/{printAnnouncementYear}/{printAnnouncementPage}/{pointInTime}/{version}/{language}/{subtype}",
      produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<AmendingLawResponseSchema> getAmendingLaw(
      @PathVariable final String printAnnouncementGazette,
      @PathVariable final String printAnnouncementYear,
      @PathVariable final String printAnnouncementPage,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype) {
    final String eli =
        buildEli(
            printAnnouncementGazette,
            printAnnouncementYear,
            printAnnouncementPage,
            pointInTime,
            version,
            language,
            subtype);
    final Optional<AmendingLaw> optionalAmendingLaw =
        loadAmendingLawUseCase.loadAmendingLaw(new LoadAmendingLawUseCase.Query(eli));
    return optionalAmendingLaw
        .map(AmendingLawResponseMapper::fromUseCaseData)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Updates the XML representation of an amending law based on its expression ELI. The ELI's
   * components are interpreted as query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param printAnnouncementGazette DE: "Verkündungsblatt"
   * @param printAnnouncementYear DE "Verkündungsjahr"
   * @param printAnnouncementPage DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @param xml - the XML representation of the amending law
   * @return A {@link ResponseEntity} without any content if 204 or error response if 404.
   *     <p>Returns HTTP 200 (OK) with the saved XML as response payload.
   *     <p>Returns HTTP 404 (Not Found) if the amending law is not found.
   */
  @PutMapping(
      path =
          "/eli/bund/{printAnnouncementGazette}/{printAnnouncementYear}/{printAnnouncementPage}/{pointInTime}/{version}/{language}/{subtype}",
      consumes = {APPLICATION_XML_VALUE},
      produces = {APPLICATION_XML_VALUE})
  public ResponseEntity<String> updateAmendingLaw(
      @PathVariable final String printAnnouncementGazette,
      @PathVariable final String printAnnouncementYear,
      @PathVariable final String printAnnouncementPage,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype,
      @RequestBody String xml) {
    final String eli =
        buildEli(
            printAnnouncementGazette,
            printAnnouncementYear,
            printAnnouncementPage,
            pointInTime,
            version,
            language,
            subtype);

    return updateAmendingLawXmlUseCase
        .updateAmendingLawXml(new UpdateAmendingLawXmlUseCase.Query(eli, xml))
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Retrieves the xml representation of an amending law based on its expression ELI. The ELI's
   * components are interpreted as query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param printAnnouncementGazette DE: "Verkündungsblatt"
   * @param printAnnouncementYear DE "Verkündungsjahr"
   * @param printAnnouncementPage DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @return A {@link ResponseEntity} containing the retrieved amending law.
   *     <p>Returns HTTP 200 (OK) and the amending law's data if found.
   *     <p>Returns HTTP 404 (Not Found) if the amending law is not found.
   */
  @GetMapping(
      path =
          "/eli/bund/{printAnnouncementGazette}/{printAnnouncementYear}/{printAnnouncementPage}/{pointInTime}/{version}/{language}/{subtype}",
      produces = {APPLICATION_XML_VALUE})
  public ResponseEntity<String> getAmendingLawXml(
      @PathVariable final String printAnnouncementGazette,
      @PathVariable final String printAnnouncementYear,
      @PathVariable final String printAnnouncementPage,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype) {
    final String eli =
        buildEli(
            printAnnouncementGazette,
            printAnnouncementYear,
            printAnnouncementPage,
            pointInTime,
            version,
            language,
            subtype);
    final Optional<String> optionalAmendingLaw =
        loadAmendingLawXmlUseCase.loadAmendingLawXml(new LoadAmendingLawXmlUseCase.Query(eli));
    return optionalAmendingLaw
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Retrieves the html render of an amending law based on its expression ELI. The ELI's components
   * are interpreted as query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param printAnnouncementGazette DE: "Verkündungsblatt"
   * @param printAnnouncementYear DE "Verkündungsjahr"
   * @param printAnnouncementPage DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @return A {@link ResponseEntity} containing the retrieved amending law as rendered html.
   *     <p>Returns HTTP 200 (OK) and the amending law as rendered html if found.
   *     <p>Returns HTTP 404 (Not Found) if the amending law is not found.
   */
  @GetMapping(
      path =
          "/eli/bund/{printAnnouncementGazette}/{printAnnouncementYear}/{printAnnouncementPage}/{pointInTime}/{version}/{language}/{subtype}",
      produces = {TEXT_HTML_VALUE})
  public ResponseEntity<String> getAmendingLawRenderedHtml(
      @PathVariable final String printAnnouncementGazette,
      @PathVariable final String printAnnouncementYear,
      @PathVariable final String printAnnouncementPage,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype) {
    final String eli =
        buildEli(
            printAnnouncementGazette,
            printAnnouncementYear,
            printAnnouncementPage,
            pointInTime,
            version,
            language,
            subtype);
    final Optional<String> optionalAmendingLaw =
        loadAmendingLawXmlUseCase.loadAmendingLawXml(new LoadAmendingLawXmlUseCase.Query(eli));
    return optionalAmendingLaw
        .map(
            xml -> {
              var html =
                  this.transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(
                      new TransformLegalDocMlToHtmlUseCase.Query(xml, false));
              return ResponseEntity.ok(html);
            })
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Retrieves the articles of an amending law based on the expression ELI of the amending law. The
   * ELI's components are interpreted as query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param printAnnouncementGazette DE: "Verkündungsblatt"
   * @param printAnnouncementYear DE "Verkündungsjahr"
   * @param printAnnouncementPage DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @return A {@link ResponseEntity} containing the response schema for a list of articles
   *     <p>Returns HTTP 200 (OK) and a list of articles on successful execution.
   *     <p>If no law is found, the list is empty.
   */
  @GetMapping(
      path =
          "/eli/bund/{printAnnouncementGazette}/{printAnnouncementYear}/{printAnnouncementPage}/{pointInTime}/{version}/{language}/{subtype}/articles")
  public ResponseEntity<List<ArticleResponseSchema>> getArticlesOfAmendingLaw(
      @PathVariable final String printAnnouncementGazette,
      @PathVariable final String printAnnouncementYear,
      @PathVariable final String printAnnouncementPage,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype) {
    final String eli =
        buildEli(
            printAnnouncementGazette,
            printAnnouncementYear,
            printAnnouncementPage,
            pointInTime,
            version,
            language,
            subtype);
    final List<Article> articles =
        loadArticlesUseCase.loadArticlesOfAmendingLaw(new LoadArticlesUseCase.Query(eli));
    final List<ArticleResponseSchema> responseSchemas =
        articles.stream().map(ArticleResponseMapper::fromUseCaseData).toList();
    return ResponseEntity.ok(responseSchemas);
  }

  /**
   * Retrieves one article of an amending law based on the expression ELI of the amending law and
   * the eid of the article. The ELI's components are interpreted as query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param printAnnouncementGazette DE: "Verkündungsblatt"
   * @param printAnnouncementYear DE "Verkündungsjahr"
   * @param printAnnouncementPage DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @param eid the expression identifier of the article withing the amending law
   * @return A {@link ResponseEntity} containing the response schema for a list of articles
   *     <p>Returns HTTP 200 (OK) and a list of articles on successful execution.
   *     <p>If no law is found, the list is empty.
   */
  @GetMapping(
      path =
          "/eli/bund/{printAnnouncementGazette}/{printAnnouncementYear}/{printAnnouncementPage}/{pointInTime}/{version}/{language}/{subtype}/articles/{eid}")
  public ResponseEntity<ArticleResponseSchema> getArticleOfAmendingLawByEid(
      @PathVariable final String printAnnouncementGazette,
      @PathVariable final String printAnnouncementYear,
      @PathVariable final String printAnnouncementPage,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype,
      @PathVariable final String eid) {
    final String eli =
        buildEli(
            printAnnouncementGazette,
            printAnnouncementYear,
            printAnnouncementPage,
            pointInTime,
            version,
            language,
            subtype);
    final Optional<Article> articleOptional =
        loadArticleUseCase.loadArticle(new LoadArticleUseCase.Query(eli, eid));
    return articleOptional
        .map(ArticleResponseMapper::fromUseCaseData)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Releases an amending law based on its expression ELI. The ELI's components are interpreted as
   * query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param printAnnouncementGazette DE: "Verkündungsblatt"
   * @param printAnnouncementYear DE "Verkündungsjahr"
   * @param printAnnouncementPage DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @return A {@link ResponseEntity} containing the elis of the affected target laws.
   *     <p>Returns HTTP 200 (OK) and the amending law's data if found.
   *     <p>Returns HTTP 404 (Not Found) if the amending law respectively it's target laws are not
   *     found.
   */
  @PutMapping(
      path =
          "/eli/bund/{printAnnouncementGazette}/{printAnnouncementYear}/{printAnnouncementPage}/{pointInTime}/{version}/{language}/{subtype}/release",
      produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<ReleaseAmendingLawResponseSchema> releaseAmendingLaw(
      @PathVariable final String printAnnouncementGazette,
      @PathVariable final String printAnnouncementYear,
      @PathVariable final String printAnnouncementPage,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype) {
    final String eli =
        buildEli(
            printAnnouncementGazette,
            printAnnouncementYear,
            printAnnouncementPage,
            pointInTime,
            version,
            language,
            subtype);
    final Optional<AmendingLaw> amendingLawReleasedOptional =
        releaseAmendingLawAndAllRelatedTargetLawsUseCase.releaseAmendingLaw(
            new ReleaseAmendingLawAndAllRelatedTargetLawsUseCase.Query(eli));

    return amendingLawReleasedOptional
        .map(
            amendingLawReleased ->
                new ReleaseAmendingLawResponseSchema(
                    amendingLawReleased.getReleasedAt(),
                    amendingLawReleased.getEli(),
                    amendingLawReleased.getArticles().stream()
                        .map(article -> article.getTargetLawZf0().getEli())
                        .toList()))
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Gets an released amending law based on its expression ELI. The ELI's components are interpreted
   * as query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param printAnnouncementGazette DE: "Verkündungsblatt"
   * @param printAnnouncementYear DE "Verkündungsjahr"
   * @param printAnnouncementPage DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @return A {@link ResponseEntity} containing the elis of the affected target laws.
   *     <p>Returns HTTP 200 (OK) and the amending law's data if found.
   *     <p>Returns HTTP 404 (Not Found) if the amending law respectively it's target laws are not
   *     found.
   */
  @GetMapping(
      path =
          "/eli/bund/{printAnnouncementGazette}/{printAnnouncementYear}/{printAnnouncementPage}/{pointInTime}/{version}/{language}/{subtype}/release",
      produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<ReleaseAmendingLawResponseSchema> getReleasedAmendingLaw(
      @PathVariable final String printAnnouncementGazette,
      @PathVariable final String printAnnouncementYear,
      @PathVariable final String printAnnouncementPage,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype) {
    final String eli =
        buildEli(
            printAnnouncementGazette,
            printAnnouncementYear,
            printAnnouncementPage,
            pointInTime,
            version,
            language,
            subtype);
    final Optional<AmendingLaw> amendingLawReleasedOptional =
        loadAmendingLawUseCase.loadAmendingLaw(new LoadAmendingLawUseCase.Query(eli));

    return amendingLawReleasedOptional
        .map(
            amendingLawReleased ->
                new ReleaseAmendingLawResponseSchema(
                    amendingLawReleased.getReleasedAt(),
                    amendingLawReleased.getEli(),
                    amendingLawReleased.getArticles().stream()
                        .map(article -> article.getTargetLawZf0().getEli())
                        .toList()))
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @NotNull
  private static String buildEli(
      String printAnnouncementGazette,
      String printAnnouncementYear,
      String printAnnouncementPage,
      String pointInTime,
      String version,
      String language,
      String subtype) {
    return "eli/bund/"
        + printAnnouncementGazette
        + "/"
        + printAnnouncementYear
        + "/"
        + printAnnouncementPage
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
