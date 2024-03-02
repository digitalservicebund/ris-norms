package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.TargetLawResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.TargetLawResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetLawUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling target laws in the REST API. This class is annotated with {@link
 * RestController} and {@link RequestMapping} to define the base URL for handling target laws in the
 * API.
 */
@RestController
@RequestMapping("api/v1/target-laws")
public class TargetLawController {

  private final LoadTargetLawUseCase loadTargetLawUseCase;

  /**
   * Creates a {@code TargetLawController} instance with necessary dependencies.
   *
   * @param loadTargetLawUseCase the use case for loading a target law
   */
  public TargetLawController(LoadTargetLawUseCase loadTargetLawUseCase) {
    this.loadTargetLawUseCase = loadTargetLawUseCase;
  }

  /**
   * Retrieves a target law based on its expression ELI. The ELI's components are interpreted as
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
   *     <p>Returns HTTP 200 (OK) and the target law's data if found.
   *     <p>Returns HTTP 404 (Not Found) if the target law is not found.
   */
  @GetMapping(
      path =
          "/eli/bund/{printAnnouncementGazette}/{printAnnouncementYear}/{printAnnouncementPage}/{pointInTime}/{version}/{language}/{subtype}")
  public ResponseEntity<TargetLawResponseSchema> getAmendingLaw(
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
    final Optional<TargetLaw> targetLawOptional =
        loadTargetLawUseCase.loadTargetLaw(new LoadTargetLawUseCase.Query(eli));
    return targetLawOptional
        .map(TargetLawResponseMapper::fromUseCaseData)
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
