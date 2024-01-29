package de.bund.digitalservice.ris.norms.adapter.output.database;

import de.bund.digitalservice.ris.norms.application.port.output.LoadProcedurePort;
import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ProcedureRepository implements LoadProcedurePort {

  @Override
  public Optional<Procedure> findById(Command command) {
    return Optional.empty();
  }
}
