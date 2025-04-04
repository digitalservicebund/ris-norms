package de.bund.digitalservice.ris.norms.adapter.output.database.service;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungImportProcessDetailDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungImportProcessDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.VerkuendungImportProcessMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.VerkuendungImportProcessesRepository;
import de.bund.digitalservice.ris.norms.application.port.output.LoadVerkuendungImportProcessPort;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcessDetail;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * DBService for {@link VerkuendungImportProcess} / {@link VerkuendungImportProcessDto} and {@link VerkuendungImportProcessDetail} / {@link VerkuendungImportProcessDetailDto}
 */
@Service
public class VerkuedungImportProcessDBService
  extends DBService
  implements LoadVerkuendungImportProcessPort {

  private final VerkuendungImportProcessesRepository verkuendungImportProcessesRepository;

  public VerkuedungImportProcessDBService(
    VerkuendungImportProcessesRepository verkuendungImportProcessesRepository
  ) {
    this.verkuendungImportProcessesRepository = verkuendungImportProcessesRepository;
  }

  @Override
  public Optional<VerkuendungImportProcess> loadVerkuendungImportProcess(
    LoadVerkuendungImportProcessPort.Command command
  ) {
    return verkuendungImportProcessesRepository
      .findById(command.id())
      .map(VerkuendungImportProcessMapper::mapToDomain);
  }
}
