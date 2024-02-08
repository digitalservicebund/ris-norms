package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadAllProceduresUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProcedureUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAllProceduresPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadProcedurePort;
import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class implementing the {@link LoadProcedureUseCase}. This class is responsible for
 * loading procedures by delegating the operation to the injected {@link LoadProcedurePort}. It is
 * annotated with {@link Service} to indicate that it's a service component in the Spring context.
 */
@Service
public class ProcedureService implements LoadProcedureUseCase, LoadAllProceduresUseCase {

  private final LoadProcedurePort loadProcedurePort;
  private final LoadAllProceduresPort loadAllProceduresPort;

  /**
   * Constructs a new {@link ProcedureService} instance.
   *
   * @param loadProcedurePort The port for loading individual procedures.
   * @param loadAllProceduresPort The port for loading all procedures.
   */
  public ProcedureService(
      LoadProcedurePort loadProcedurePort, LoadAllProceduresPort loadAllProceduresPort) {
    this.loadProcedurePort = loadProcedurePort;
    this.loadAllProceduresPort = loadAllProceduresPort;
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Procedure> loadProcedure(final Query query) {
    return loadProcedurePort.loadProcedureByEli(new LoadProcedurePort.Command(query.eli()));
  }

  @Override
  public List<Procedure> loadAllProcedures() {
    return loadAllProceduresPort.loadAllProcedures();
  }
}
