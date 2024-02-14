package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ProcedureResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAllProceduresUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProcedureUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling procedures in the REST API. This class is annotated with {@link
 * RestController} and {@link RequestMapping} to define the base URL for handling procedures in the
 * API.
 */
@RestController
@RequestMapping("api/v1/norms/procedures")
public class ProcedureController {

  private final LoadProcedureUseCase loadProcedureUseCase;
  private final LoadAllProceduresUseCase loadAllProceduresUseCase;

  /**
   * Creates a {@code ProcedureController} instance with necessary dependencies.
   *
   * @param loadProcedureUseCase the use case for loading a single procedure
   * @param loadAllProceduresUseCase the use case for loading all procedures
   */
  public ProcedureController(
      LoadProcedureUseCase loadProcedureUseCase,
      LoadAllProceduresUseCase loadAllProceduresUseCase) {
    this.loadProcedureUseCase = loadProcedureUseCase;
    this.loadAllProceduresUseCase = loadAllProceduresUseCase;
  }

  /**
   * Retrieves a procedure based on the specified parameters.
   *
   * @param printAnnouncementGazette The print announcement gazette parameter.
   * @param printAnnouncementYear The print announcement year parameter.
   * @param printAnnouncementPage The print announcement page parameter.
   * @return A {@link ResponseEntity} containing the response schema for the retrieved procedure. If
   *     the procedure is found, returns HTTP 200 (OK) with the procedure data. If the procedure is
   *     not found, returns HTTP 404 (Not Found).
   */
  @GetMapping(
      path = "/eli/bund/{printAnnouncementGazette}/{printAnnouncementYear}/{printAnnouncementPage}")
  ResponseEntity<ProcedureResponseSchema> getProcedure(
      @PathVariable final String printAnnouncementGazette,
      @PathVariable final String printAnnouncementYear,
      @PathVariable final String printAnnouncementPage) {
    final String eli =
        "eli/bund/"
            + printAnnouncementGazette
            + "/"
            + printAnnouncementYear
            + "/"
            + printAnnouncementPage;
    final Optional<Procedure> optionalProcedure =
        loadProcedureUseCase.loadProcedure(new LoadProcedureUseCase.Query(eli));
    return optionalProcedure
        .map(ProcedureResponseSchema::fromUseCaseData)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<ProcedureResponseSchema>> getAllUnclosedProcedures() {
    List<Procedure> procedures = loadAllProceduresUseCase.loadAllUnclosedProcedures();
    List<ProcedureResponseSchema> responseSchemas =
        procedures.stream()
            .map(ProcedureResponseSchema::fromUseCaseData)
            .collect(Collectors.toList());
    return ResponseEntity.ok(responseSchemas);
  }
}
