package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadProcedureUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadProcedurePort;
import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import org.springframework.stereotype.Service;

@Service
public class ProcedureService implements LoadProcedureUseCase {

  private final LoadProcedurePort loadProcedurePort;

  public ProcedureService(LoadProcedurePort loadProcedurePort) {
    this.loadProcedurePort = loadProcedurePort;
  }

  @Override
  public Procedure loadProcedure(final Query query) {
    return loadProcedurePort.findById(new LoadProcedurePort.Command(query.id())).orElse(null);
  }
}
