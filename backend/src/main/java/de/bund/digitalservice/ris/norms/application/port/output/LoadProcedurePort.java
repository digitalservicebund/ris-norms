package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import java.util.Optional;

public interface LoadProcedurePort {

  Optional<Procedure> loadProcedureByEli(final Command command);

  record Command(String eli) {}
}
