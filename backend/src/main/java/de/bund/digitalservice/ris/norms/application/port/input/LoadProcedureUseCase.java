package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import java.util.Optional;
import java.util.UUID;

public interface LoadProcedureUseCase {

  Optional<Procedure> loadProcedure(Query query);

  record Query(UUID id) {}
}
