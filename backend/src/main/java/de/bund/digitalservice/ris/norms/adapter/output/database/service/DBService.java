package de.bund.digitalservice.ris.norms.adapter.output.database.service;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.ProcedureMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.ProcedureRepository;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAllProceduresPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadProcedurePort;
import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Service class for interacting with the database and implementing {@link LoadProcedurePort}. This
 * class is annotated with {@link Service} to indicate that it's a service component in the Spring
 * context.
 */
@Service
public class DBService implements LoadProcedurePort, LoadAllProceduresPort {

  private final ProcedureRepository procedureRepository;

  public DBService(ProcedureRepository procedureRepository) {
    this.procedureRepository = procedureRepository;
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Procedure> loadProcedureByEli(Command command) {
    return procedureRepository.findByEli(command.eli()).map(ProcedureMapper::mapToDomain);
  }

  @Override
  public List<Procedure> loadAllProcedures() {
    return procedureRepository.findAll().stream()
        .map(ProcedureMapper::mapToDomain)
        .collect(Collectors.toList());
  }
}
