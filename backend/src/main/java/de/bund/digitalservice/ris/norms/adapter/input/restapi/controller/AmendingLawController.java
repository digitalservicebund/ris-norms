package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.AmendingLawResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.AmendingLawIncludingArticlesResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.AmendingLawResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAllAmendingLawsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAmendingLawUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling amending laws in the REST API. This class is annotated with {@link
 * RestController} and {@link RequestMapping} to define the base URL for handling amending laws in
 * the API.
 */
@RestController
@RequestMapping("api/v1/amendinglaw")
public class AmendingLawController {

  private final LoadAmendingLawUseCase loadAmendingLawUseCase;
  private final LoadAllAmendingLawsUseCase loadAllAmendingLawsUseCase;

  /**
   * Creates a {@code AmendingLawController} instance with necessary dependencies.
   *
   * @param loadAmendingLawUseCase the use case for loading a single amending law
   * @param loadAllAmendingLawsUseCase the use case for loading all amending laws
   */
  public AmendingLawController(
      LoadAmendingLawUseCase loadAmendingLawUseCase,
      LoadAllAmendingLawsUseCase loadAllAmendingLawsUseCase) {
    this.loadAmendingLawUseCase = loadAmendingLawUseCase;
    this.loadAllAmendingLawsUseCase = loadAllAmendingLawsUseCase;
  }

  /**
   * Retrieves an amending law based on its expression ELI. The ELI's components are interpreted as
   * query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf.
   * https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24)
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
          "/eli/bund/{printAnnouncementGazette}/{printAnnouncementYear}/{printAnnouncementPage}/{pointInTime}/{version}/{language}/{subtype}")
  public ResponseEntity<AmendingLawIncludingArticlesResponseSchema> returnAmendingLaw(
      @PathVariable final String printAnnouncementGazette,
      @PathVariable final String printAnnouncementYear,
      @PathVariable final String printAnnouncementPage,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype) {
    final String eli =
        "eli/bund/"
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
    final Optional<AmendingLaw> optionalAmendingLaw =
        loadAmendingLawUseCase.loadAmendingLaw(new LoadAmendingLawUseCase.Query(eli));
    return optionalAmendingLaw
        .map(AmendingLawResponseMapper::fromUseCaseData)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Retrieves all available amending laws
   *
   * @return A {@link ResponseEntity} containing the response schema for a list of amending laws
   *     <p>Returns HTTP 200 (OK) and a list of amending laws on successful exection.
   *     <p>If no law is found, the list is empty.
   */
  @GetMapping
  public ResponseEntity<List<AmendingLawResponseSchema>> returnAllAmendingLaws() {
    List<AmendingLaw> amendingLawWithArticles = loadAllAmendingLawsUseCase.loadAllAmendingLaws();
    List<AmendingLawResponseSchema> responseSchemas =
        amendingLawWithArticles.stream()
            .map(AmendingLawResponseMapper::fromUseCaseDataWithoutArticles)
            .toList();
    return ResponseEntity.ok(responseSchemas);
  }
}
