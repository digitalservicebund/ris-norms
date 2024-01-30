package de.bund.digitalservice.ris.norms.adapter.input.restapi;

import de.bund.digitalservice.ris.norms.application.port.input.LoadProcedureUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/procedures")
public class ProcedureController {

  private final LoadProcedureUseCase loadProcedureUseCase;

  public ProcedureController(LoadProcedureUseCase loadProcedureUseCase) {
    this.loadProcedureUseCase = loadProcedureUseCase;
  }

  @GetMapping
  Procedure getProcedure(UUID uuid) {
    return loadProcedureUseCase.loadProcedure(new LoadProcedureUseCase.Query(uuid)).orElse(null);
  }
}
