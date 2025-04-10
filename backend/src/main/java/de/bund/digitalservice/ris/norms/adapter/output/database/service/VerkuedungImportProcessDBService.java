package de.bund.digitalservice.ris.norms.adapter.output.database.service;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungImportProcessDetailDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungImportProcessDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.VerkuendungImportProcessMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.VerkuendungImportProcessesRepository;
import de.bund.digitalservice.ris.norms.application.port.output.LoadVerkuendungImportProcessPort;
import de.bund.digitalservice.ris.norms.application.port.output.SaveVerkuendungImportProcessPort;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcessDetail;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * DBService for {@link VerkuendungImportProcess} / {@link VerkuendungImportProcessDto} and {@link
 * VerkuendungImportProcessDetail} / {@link VerkuendungImportProcessDetailDto}
 */
@Slf4j
@Service
public class VerkuedungImportProcessDBService
  extends DBService
  implements LoadVerkuendungImportProcessPort, SaveVerkuendungImportProcessPort {

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

  private static void updateProcessDto(
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

        final List<VerkuendungImportProcessDetailDto> incomingDetails = command
          .details()
          .stream()
          .map(VerkuendungImportProcessMapper::mapDetailToDto)
          .toList();

        final Set<String> existingDetailSet = verkuendungImportProcessDto
          .getDetail()
          .stream()
          .map(VerkuendungImportProcessDetailDto::getDetail)
          .collect(Collectors.toSet());

        for (VerkuendungImportProcessDetailDto incomingDetail : incomingDetails) {
          if (!existingDetailSet.contains(incomingDetail.getDetail())) {
            final VerkuendungImportProcessDetailDto safeDetail = VerkuendungImportProcessDetailDto
              .builder()
              .type(incomingDetail.getType())
              .title(incomingDetail.getTitle())
              .detail(incomingDetail.getDetail())
              .build();
            verkuendungImportProcessDto.getDetail().add(safeDetail);
          }
        }
      }
      default -> log.error("Unhandled status {}", status);
    }
  }
}
