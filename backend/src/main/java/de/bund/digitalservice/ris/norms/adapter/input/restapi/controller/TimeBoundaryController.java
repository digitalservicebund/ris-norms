package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.TimeBoundaryResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.TimeBoundaryResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.*;
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

  public TimeBoundaryController(LoadTimeBoundariesUseCase loadTimeBoundariesUseCase) {
    this.loadTimeBoundariesUseCase = loadTimeBoundariesUseCase;
  }

  /**
   * Retrieves time boundaries for a norm based on its ELI.
   *
   * <p>The method constructs an ELI from the provided path variables, queries the use case to
   * retrieve time boundaries, and maps the resulting data to {@link TimeBoundaryResponseSchema}. If
   * no data is found, it returns an HTTP 404 Not Found status.
   *
   * @param agent the publishing body ("Verkündungsblatt")
   * @param year the year of announcement ("Verkündungsjahr")
   * @param naturalIdentifier the natural identifier, typically page number or announcement number
   *     ("Seitenzahl / Verkündungsnummer")
   * @param pointInTime the version date of the document ("Versionsdatum")
   * @param version the version number of the document ("Versionsnummer")
   * @param language the language of the document ("Sprache")
   * @param subtype the type of document ("Dokumentenart")
   * @return a {@link ResponseEntity} containing a list of {@link TimeBoundaryResponseSchema} or
   *     HTTP 404 Not Found if no boundaries are available.
   */
  @GetMapping(produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<List<TimeBoundaryResponseSchema>> getTimeBoundaries(
      @PathVariable final String agent,
      @PathVariable final String year,
      @PathVariable final String naturalIdentifier,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype) {
    final String eli =
        buildEli(agent, year, naturalIdentifier, pointInTime, version, language, subtype);

    List<TimeBoundaryResponseSchema> result =
        loadTimeBoundariesUseCase
            .loadTimeBoundariesOfNorm(new LoadTimeBoundariesUseCase.Query(eli))
            .stream()
            .map(TimeBoundaryResponseMapper::fromUseCaseData)
            .toList();
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
