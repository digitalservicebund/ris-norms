package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.constraints.UniqueTimeBoundariesDatesConstraint;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.TimeBoundaryMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.TimeBoundarySchema;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Controller for listing and managing time boundaries of a regelungstext. */
@RestController
@RequestMapping(
  "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/timeBoundaries"
)
public class TimeBoundaryController {

  private final LoadTimeBoundariesUseCase loadTimeBoundariesUseCase;
  private final UpdateTimeBoundariesUseCase updateTimeBoundariesUseCase;

  public TimeBoundaryController(
    LoadTimeBoundariesUseCase loadTimeBoundariesUseCase,
    UpdateTimeBoundariesUseCase updateTimeBoundariesUseCase
  ) {
    this.loadTimeBoundariesUseCase = loadTimeBoundariesUseCase;
    this.updateTimeBoundariesUseCase = updateTimeBoundariesUseCase;
  }

  /**
   * Retrieves time boundaries for a regelungstext based on its ELI.
   *
   * <p>The method constructs an ELI from the provided path variables, queries the use case to
   * retrieve time boundaries, and maps the resulting data to {@link TimeBoundarySchema}. If no data
   * is found, it returns an HTTP 404 Not Found status.
   *
   * @param eli Eli of the request
   * @return a {@link ResponseEntity} containing a list of {@link TimeBoundarySchema} or HTTP 404
   *     Not Found if no boundaries are available.
   */
  @GetMapping(produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<List<TimeBoundarySchema>> getTimeBoundaries(
    final DokumentExpressionEli eli
  ) {
    return ResponseEntity.ok(
      loadTimeBoundariesUseCase
        .loadTimeBoundariesFromRegelungstext(new LoadTimeBoundariesUseCase.Query(eli))
        .stream()
        .map(TimeBoundaryMapper::fromUseCaseData)
        .toList()
    );
  }

  /**
   * Updates time boundaries for a regelungstext based on its ELI.
   *
   * <p>The method constructs an ELI from the provided path variables, queries the use case to
   * update time boundaries, and maps the resulting data to {@link TimeBoundarySchema}. If no data
   * is found, it returns an HTTP 404 Not Found status.
   *
   * @param eli Eli of the request
   * @param timeBoundaries the time boundaries that should be updated ("GeltungszeitIntervall")
   * @return a {@link ResponseEntity} containing a list of {@link TimeBoundarySchema} or HTTP 404
   *     Not Found if no boundaries are available or a 400 if the update failed.
   */
  @PutMapping(consumes = { APPLICATION_JSON_VALUE }, produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<List<TimeBoundarySchema>> updateTimeBoundaries(
    final DokumentExpressionEli eli,
    @RequestBody @Valid @UniqueTimeBoundariesDatesConstraint @NotEmpty(
      message = "Change list must not be empty"
    ) @Size(max = 100, message = "A maximum of 100 time boundaries is supported") final List<
      TimeBoundarySchema
    > timeBoundaries
  ) {
    return ResponseEntity.ok(
      updateTimeBoundariesUseCase
        .updateTimeBoundariesOfRegelungstext(
          new UpdateTimeBoundariesUseCase.Query(
            eli,
            TimeBoundaryMapper.fromResponseSchema(timeBoundaries)
          )
        )
        .stream()
        .map(TimeBoundaryMapper::fromUseCaseData)
        .toList()
    );
  }
}
