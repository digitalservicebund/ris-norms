package de.bund.digitalservice.ris.norms.adapter.output;

import de.bund.digitalservice.ris.norms.application.port.output.LoadProcedurePort;
import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ProcedureRepository implements LoadProcedurePort {
    @Override
    public Optional<Procedure> findById(UUID id) {
        return Optional.empty();
    }
}
