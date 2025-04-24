package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ZielnormReferenceMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ZeitgrenzeResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ZielnormReferenceSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadZielnormReferencesUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller for listing and managing references to zielnormen that will be changed by the {@link Norm}. */
@RestController
@RequestMapping(
  "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/zielnorm-references"
)
public class ZielnormReferencesController {

  private final LoadZielnormReferencesUseCase loadZielnormReferencesUseCase;

  public ZielnormReferencesController(LoadZielnormReferencesUseCase loadZielnormReferencesUseCase) {
    this.loadZielnormReferencesUseCase = loadZielnormReferencesUseCase;
  }

  /**
   * Retrieves zielnorm references for a {@link Norm} based on its ELI.
   *
   * @param eli Eli of the request
   * @return a {@link ResponseEntity} containing a list of {@link ZeitgrenzeResponseSchema}
   */
  @GetMapping(produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<List<ZielnormReferenceSchema>> getReferences(final NormExpressionEli eli) {
    return ResponseEntity.ok(
      loadZielnormReferencesUseCase
        .loadZielnormReferences(new LoadZielnormReferencesUseCase.Query(eli))
        .stream()
        .map(ZielnormReferenceMapper::fromUseCaseData)
        .toList()
    );
  }
}
