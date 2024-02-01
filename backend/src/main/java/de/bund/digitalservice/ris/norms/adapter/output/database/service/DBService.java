package de.bund.digitalservice.ris.norms.adapter.output.database.service;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.ProcedureMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.ProcedureRepository;
import de.bund.digitalservice.ris.norms.application.port.output.LoadProcedurePort;
import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DBService implements LoadProcedurePort {

  private final ProcedureRepository procedureRepository;

  public DBService(ProcedureRepository procedureRepository) {
    this.procedureRepository = procedureRepository;
  }

  @Override
  public Optional<Procedure> loadProcedureByEli(Command command) {
    return procedureRepository.findByEli(command.eli()).map(ProcedureMapper::mapToDomain);
  }
}
