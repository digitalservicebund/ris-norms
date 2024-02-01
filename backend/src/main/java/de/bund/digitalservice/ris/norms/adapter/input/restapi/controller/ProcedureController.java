package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ProcedureResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProcedureUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/norms/procedures")
public class ProcedureController {

  private final LoadProcedureUseCase loadProcedureUseCase;

  public ProcedureController(LoadProcedureUseCase loadProcedureUseCase) {
    this.loadProcedureUseCase = loadProcedureUseCase;
  }

  @GetMapping(
      path = "/eli/{printAnnouncementGazette}/{printAnnouncementYear}/{printAnnouncementPage}")
  ResponseEntity<ProcedureResponseSchema> getProcedure(
      @PathVariable final String printAnnouncementGazette,
      @PathVariable final String printAnnouncementYear,
      @PathVariable final String printAnnouncementPage) {
    final String eli =
        "eli/"
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
}
