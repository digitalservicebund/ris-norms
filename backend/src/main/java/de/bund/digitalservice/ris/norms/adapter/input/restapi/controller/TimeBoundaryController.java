package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.TimeBoundaryMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.TimeBoundarySchema;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Controller for norm-related actions. */
@RestController
@RequestMapping(
    "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/timeBoundaries")
public class TimeBoundaryController {

  private final LoadTimeBoundariesUseCase loadTimeBoundariesUseCase;
  private final UpdateTimeBoundariesUseCase updateTimeBoundariesUseCase;

  public TimeBoundaryController(
      LoadTimeBoundariesUseCase loadTimeBoundariesUseCase,
      UpdateTimeBoundariesUseCase updateTimeBoundariesUseCase) {

    this.loadTimeBoundariesUseCase = loadTimeBoundariesUseCase;
    this.updateTimeBoundariesUseCase = updateTimeBoundariesUseCase;
  }

  /**
   * Retrieves time boundaries for a norm based on its ELI.
   *
   * <p>The method constructs an ELI from the provided path variables, queries the use case to
   * retrieve time boundaries, and maps the resulting data to {@link TimeBoundarySchema}. If no data
   * is found, it returns an HTTP 404 Not Found status.
   *
   * @param agent the publishing body ("Verkündungsblatt")
   * @param year the year of announcement ("Verkündungsjahr")
   * @param naturalIdentifier the natural identifier, typically page number or announcement number
   *     ("Seitenzahl / Verkündungsnummer")
   * @param pointInTime the version date of the document ("Versionsdatum")
   * @param version the version number of the document ("Versionsnummer")
   * @param language the language of the document ("Sprache")
   * @param subtype the type of document ("Dokumentenart")
   * @return a {@link ResponseEntity} containing a list of {@link TimeBoundarySchema} or HTTP 404
   *     Not Found if no boundaries are available.
   */
  @GetMapping(produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<List<TimeBoundarySchema>> getTimeBoundaries(
      @PathVariable final String agent,
      @PathVariable final String year,
      @PathVariable final String naturalIdentifier,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype) {
    final String eli =
        buildEli(agent, year, naturalIdentifier, pointInTime, version, language, subtype);

    List<TimeBoundarySchema> result =
        loadTimeBoundariesUseCase
            .loadTimeBoundariesOfNorm(new LoadTimeBoundariesUseCase.Query(eli))
            .stream()
            .map(TimeBoundaryMapper::fromUseCaseData)
            .toList();

    // Assumptions: According to spec there must always be a temporalData with one temporalGroup
    // having 1
    //              temporalInterval in a ReglungstextVerkuendungsfassung
    return (result.isEmpty()) ? ResponseEntity.notFound().build() : ResponseEntity.ok(result);
  }

  /**
   * Updates time boundaries for a norm based on its ELI.
   *
   * <p>The method constructs an ELI from the provided path variables, queries the use case to
   * update time boundaries, and maps the resulting data to {@link TimeBoundarySchema}. If no data
   * is found, it returns an HTTP 404 Not Found status.
   *
   * @param agent the publishing body ("Verkündungsblatt")
   * @param year the year of announcement ("Verkündungsjahr")
   * @param naturalIdentifier the natural identifier, typically page number or announcement number
   *     ("Seitenzahl / Verkündungsnummer")
   * @param pointInTime the version date of the document ("Versionsdatum")
   * @param version the version number of the document ("Versionsnummer")
   * @param language the language of the document ("Sprache")
   * @param subtype the type of document ("Dokumentenart")
   * @param timeBoundaries the time boundaries that should be updated ("GeltungszeitIntervall")
   * @return a {@link ResponseEntity} containing a list of {@link TimeBoundarySchema} or HTTP 404
   *     Not Found if no boundaries are available or a 400 if the update failed.
   */
  @PutMapping(
      consumes = {APPLICATION_JSON_VALUE},
      produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<List<TimeBoundarySchema>> updateTimeBoundaries(
      @PathVariable final String agent,
      @PathVariable final String year,
      @PathVariable final String naturalIdentifier,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype,
      @RequestBody @Valid @NotEmpty(message = "Change list must not be empty")
          final List<TimeBoundarySchema> timeBoundaries) {

    final String eli =
        buildEli(agent, year, naturalIdentifier, pointInTime, version, language, subtype);

    List<TimeBoundarySchema> result =
        updateTimeBoundariesUseCase
            .updateTimeBoundariesOfNorm(
                new UpdateTimeBoundariesUseCase.Query(
                    eli, TimeBoundaryMapper.fromResponseSchema(timeBoundaries)))
            .stream()
            .map(TimeBoundaryMapper::fromUseCaseData)
            .toList();

    // Assumptions: According to spec there must always be a temporalData with one temporalGroup
    // having 1
    //              temporalInterval in a ReglungstextVerkuendungsfassung
    return (result.isEmpty()) ? ResponseEntity.notFound().build() : ResponseEntity.ok(result);
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
