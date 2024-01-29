package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.adapter.output.ProcedureRepository;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProcedureUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ProcedureService implements LoadProcedureUseCase {

  private final ProcedureRepository procedureRepository;

  public ProcedureService(ProcedureRepository procedureRepository) {
    this.procedureRepository = procedureRepository;
  }

  @Override
  public Procedure loadProcedure(UUID uuid) {
    return procedureRepository.findById(uuid).orElse(null);
  }
}
