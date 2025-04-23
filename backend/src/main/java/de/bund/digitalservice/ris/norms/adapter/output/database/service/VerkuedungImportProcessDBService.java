package de.bund.digitalservice.ris.norms.adapter.output.database.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungImportProcessDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.VerkuendungImportProcessMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.VerkuendungImportProcessesRepository;
import de.bund.digitalservice.ris.norms.application.port.output.LoadVerkuendungImportProcessPort;
import de.bund.digitalservice.ris.norms.application.port.output.SaveVerkuendungImportProcessPort;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import java.time.Instant;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * DBService for {@link VerkuendungImportProcess} / {@link VerkuendungImportProcessDto}
 */
@Slf4j
@Service
public class VerkuedungImportProcessDBService
  extends DBService
  implements LoadVerkuendungImportProcessPort, SaveVerkuendungImportProcessPort {

  private final VerkuendungImportProcessesRepository verkuendungImportProcessesRepository;
  private final ObjectMapper objectMapper;

  public VerkuedungImportProcessDBService(
    VerkuendungImportProcessesRepository verkuendungImportProcessesRepository,
    ObjectMapper objectMapper
  ) {
    this.verkuendungImportProcessesRepository = verkuendungImportProcessesRepository;
    this.objectMapper = objectMapper;
  }

  @Override
  public Optional<VerkuendungImportProcess> loadVerkuendungImportProcess(
    LoadVerkuendungImportProcessPort.Command command
  ) {
    return verkuendungImportProcessesRepository
      .findById(command.id())
      .map(VerkuendungImportProcessMapper::mapToDomain);
  }

  @Override
  public VerkuendungImportProcess saveOrUpdateVerkuendungImportProcess(
    SaveVerkuendungImportProcessPort.Command command
  ) {
    final Optional<VerkuendungImportProcessDto> optionalExistingVerkuendungImportProcessDto =
      verkuendungImportProcessesRepository.findById(command.id());

    if (optionalExistingVerkuendungImportProcessDto.isPresent()) {
      var existingVerkuendungImportProcessDto = optionalExistingVerkuendungImportProcessDto.get();
      updateProcessDto(command, existingVerkuendungImportProcessDto);
      return VerkuendungImportProcessMapper.mapToDomain(existingVerkuendungImportProcessDto);
    } else {
      VerkuendungImportProcessDto createdVerkuendungImportProcessDto =
        new VerkuendungImportProcessDto();
      createdVerkuendungImportProcessDto.setId(command.id());
      updateProcessDto(command, createdVerkuendungImportProcessDto);
      return VerkuendungImportProcessMapper.mapToDomain(
        verkuendungImportProcessesRepository.save(createdVerkuendungImportProcessDto)
      );
    }
  }

  private void updateProcessDto(
    SaveVerkuendungImportProcessPort.Command command,
    VerkuendungImportProcessDto verkuendungImportProcessDto
  ) {
    final VerkuendungImportProcessDto.Status status = VerkuendungImportProcessMapper.mapStatusToDto(
      command.status()
    );
    verkuendungImportProcessDto.setStatus(status);

    switch (status) {
      case CREATED -> verkuendungImportProcessDto.setCreatedAt(Instant.now());
      case PROCESSING -> verkuendungImportProcessDto.setStartedAt(Instant.now());
      case SUCCESS -> verkuendungImportProcessDto.setFinishedAt(Instant.now());
      case ERROR -> {
        verkuendungImportProcessDto.setFinishedAt(Instant.now());
        try {
          var details = objectMapper.writeValueAsString(command.details());
          verkuendungImportProcessDto.setDetails(details);
        } catch (JsonProcessingException e) {
          log.error("Could not serialize verkuendungImportProcessDto details", e);
        }
      }
      default -> log.error("Unhandled status {}", status);
    }
  }
}
