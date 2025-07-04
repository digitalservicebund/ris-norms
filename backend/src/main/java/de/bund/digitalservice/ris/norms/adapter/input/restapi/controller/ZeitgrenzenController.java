package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.constraints.UniqueZeitgrenzeDateArtConstraint;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ZeitgrenzeMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ZeitgrenzeRequestSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ZeitgrenzeResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadZeitgrenzenUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateZeitgrenzenUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.Zeitgrenze;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.Comparator;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Controller for listing and managing time boundaries of a {@link Dokument}. */
@RestController
@RequestMapping(
  "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/zeitgrenzen"
)
public class ZeitgrenzenController {

  private final LoadZeitgrenzenUseCase loadZeitgrenzenUseCase;
  private final UpdateZeitgrenzenUseCase updateZeitgrenzenUseCase;

  public ZeitgrenzenController(
    LoadZeitgrenzenUseCase loadZeitgrenzenUseCase,
    UpdateZeitgrenzenUseCase updateZeitgrenzenUseCase
  ) {
    this.loadZeitgrenzenUseCase = loadZeitgrenzenUseCase;
    this.updateZeitgrenzenUseCase = updateZeitgrenzenUseCase;
  }

  /**
   * Retrieves time boundaries for a {@link Dokument} based on its ELI.
   *
   * @param eli Eli of the request
   * @return a {@link ResponseEntity} containing a list of {@link ZeitgrenzeResponseSchema} or HTTP 404
   *     Not Found if the {@link Dokument} was not found.
   */
  @GetMapping(produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<List<ZeitgrenzeResponseSchema>> getZeitgrenzen(
    final DokumentExpressionEli eli
  ) {
    return ResponseEntity.ok(
      loadZeitgrenzenUseCase
        .loadZeitgrenzen(new LoadZeitgrenzenUseCase.Options(eli.asNormEli()))
        .stream()
        .sorted(Comparator.comparing(Zeitgrenze::getDate))
        .map(ZeitgrenzeMapper::fromUseCaseData)
        .toList()
    );
  }

  /**
   * Updates time boundaries for a {@link Dokument} based on its ELI.
   *
   * @param eli Eli of the request
   * @param zeitgrenzen the time boundaries that should be updated
   * @return a {@link ResponseEntity} containing a list of {@link ZeitgrenzeResponseSchema} or HTTP 404
   *     Not Found if the {@link Dokument} was not found.
   */
  @PutMapping(consumes = { APPLICATION_JSON_VALUE }, produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<List<ZeitgrenzeResponseSchema>> updateZeitgrenzen(
    final DokumentExpressionEli eli,
    @RequestBody @Valid @UniqueZeitgrenzeDateArtConstraint @Size(
      max = 100,
      message = "A maximum of 100 time boundaries is supported"
    ) final List<ZeitgrenzeRequestSchema> zeitgrenzen
  ) {
    return ResponseEntity.ok(
      updateZeitgrenzenUseCase
        .updateZeitgrenzen(
          new UpdateZeitgrenzenUseCase.Options(
            eli.asNormEli(),
            ZeitgrenzeMapper.fromRequestSchema(zeitgrenzen)
          )
        )
        .stream()
        .sorted(Comparator.comparing(Zeitgrenze::getDate))
        .map(ZeitgrenzeMapper::fromUseCaseData)
        .toList()
    );
  }
}
