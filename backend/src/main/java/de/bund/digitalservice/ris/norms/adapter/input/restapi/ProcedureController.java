package de.bund.digitalservice.ris.norms.adapter.input.restapi;

import de.bund.digitalservice.ris.norms.application.port.input.LoadProcedureUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import java.util.UUID;
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

  @GetMapping(path = "/{uuid}")
  Procedure getProcedure(@PathVariable final UUID uuid) {
    return loadProcedureUseCase.loadProcedure(new LoadProcedureUseCase.Query(uuid)).orElse(null);
  }
}
