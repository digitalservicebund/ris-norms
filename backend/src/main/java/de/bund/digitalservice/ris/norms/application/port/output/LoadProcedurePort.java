package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Procedure;

import java.util.Optional;
import java.util.UUID;

public interface LoadProcedurePort {

    Optional<Procedure> findById(UUID id);
}
