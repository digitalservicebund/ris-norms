package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.adapter.output.ProcedureRepository;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProcedureUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadProcedurePort;
import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ProcedureService implements LoadProcedureUseCase {

  private final LoadProcedurePort loadProcedurePort;

  public ProcedureService(ProcedureRepository loadProcedurePort) {
    this.loadProcedurePort = loadProcedurePort;
  }

  @Override
  public Procedure loadProcedure(UUID uuid) {
    return loadProcedurePort.findById(uuid).orElse(null);
  }
}
