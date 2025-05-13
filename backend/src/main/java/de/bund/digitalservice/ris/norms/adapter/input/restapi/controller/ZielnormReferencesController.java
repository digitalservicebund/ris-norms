package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ZielnormReferenceMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ZeitgrenzeResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ZielnormReferenceSchema;
import de.bund.digitalservice.ris.norms.application.port.input.DeleteZielnormReferencesUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadZielnormReferencesUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateZielnormReferencesUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.EId;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller for listing and managing references to zielnormen that will be changed by the {@link Norm}. */
@RestController
@RequestMapping(
  "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/zielnorm-references"
)
public class ZielnormReferencesController {

  private final LoadZielnormReferencesUseCase loadZielnormReferencesUseCase;
  private final UpdateZielnormReferencesUseCase updateZielnormReferencesUseCase;
  private final DeleteZielnormReferencesUseCase deleteZielnormReferencesUseCase;

  public ZielnormReferencesController(
    LoadZielnormReferencesUseCase loadZielnormReferencesUseCase,
    UpdateZielnormReferencesUseCase updateZielnormReferencesUseCase,
    DeleteZielnormReferencesUseCase deleteZielnormReferencesUseCase
  ) {
    this.loadZielnormReferencesUseCase = loadZielnormReferencesUseCase;
    this.updateZielnormReferencesUseCase = updateZielnormReferencesUseCase;
    this.deleteZielnormReferencesUseCase = deleteZielnormReferencesUseCase;
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

  /**
   * Updates the provided references (creates references with new eIds, updates once with existing eIds and does not change any other references) and returns all zielnormen references.
   *
   * @param references List of references to update or create
   * @param eli Eli of the norm whose zielnorm-references should be updated
   * @return all zielnormen-references of the norm
   */
  @PostMapping(produces = { APPLICATION_JSON_VALUE }, consumes = { APPLICATION_JSON_VALUE })
  public ResponseEntity<List<ZielnormReferenceSchema>> updateReferences(
    final NormExpressionEli eli,
    @RequestBody @Valid final List<ZielnormReferenceSchema> references
  ) {
    return ResponseEntity.ok(
      updateZielnormReferencesUseCase
        .updateZielnormReferences(
          new UpdateZielnormReferencesUseCase.Query(
            eli,
            references.stream().map(ZielnormReferenceMapper::toUseCaseData).toList()
          )
        )
        .stream()
        .map(ZielnormReferenceMapper::fromUseCaseData)
        .toList()
    );
  }

  /**
   * Deletes all references related to the eIds and returns all remaining zielnormen references.
   *
   * @param referenceEIds List of eIds whose references should be deleted
   * @param eli Eli of the norm whose zielnorm-references should be deleted
   * @return all (remaining) zielnormen-references of the norm
   */
  @DeleteMapping(produces = { APPLICATION_JSON_VALUE }, consumes = { APPLICATION_JSON_VALUE })
  public ResponseEntity<List<ZielnormReferenceSchema>> deleteReferences(
    final NormExpressionEli eli,
    @RequestBody final List<String> referenceEIds
  ) {
    return ResponseEntity.ok(
      deleteZielnormReferencesUseCase
        .deleteZielnormReferences(
          new DeleteZielnormReferencesUseCase.Query(
            eli,
            referenceEIds.stream().map(EId::new).toList()
          )
        )
        .stream()
        .map(ZielnormReferenceMapper::fromUseCaseData)
        .toList()
    );
  }
}
