package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import java.util.Optional;

public interface LoadProcedureUseCase {

  Optional<Procedure> loadProcedure(Query query);

  record Query(String eli) {}
}
