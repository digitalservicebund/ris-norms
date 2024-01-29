package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import java.util.UUID;

public interface LoadProcedureUseCase {

  // TODO define command for the input parameters?
  Procedure loadProcedure(UUID uuid);
}
