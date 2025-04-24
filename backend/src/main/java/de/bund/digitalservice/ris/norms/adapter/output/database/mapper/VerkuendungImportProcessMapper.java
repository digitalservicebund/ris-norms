package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungImportProcessDto;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;

/**
 * Converts background process information for newly uploaded Verkündungen between the DTOs and
 * domain entities.
 */
public class VerkuendungImportProcessMapper {

  private VerkuendungImportProcessMapper() {}

  /**
   * Maps a {@link VerkuendungImportProcessDto} to the corresponding domain entity.
   *
   * @param verkuendungImportProcessDto The input that should be converted
   * @return A new {@link VerkuendungImportProcess} based on the DTO
   */
  public static VerkuendungImportProcess mapToDomain(
    final VerkuendungImportProcessDto verkuendungImportProcessDto
  ) {
    return VerkuendungImportProcess
      .builder()
      .id(verkuendungImportProcessDto.getId())
      .status(mapStatusToDomain(verkuendungImportProcessDto.getStatus()))
      .createdAt(verkuendungImportProcessDto.getCreatedAt())
      .startedAt(verkuendungImportProcessDto.getStartedAt())
      .finishedAt(verkuendungImportProcessDto.getFinishedAt())
      .detail(verkuendungImportProcessDto.getDetails())
      .build();
  }

  /**
   * Maps a {@link VerkuendungImportProcess} to the corresponding DTO.
   *
   * @param verkuendungImportProcess The input that should be converted
   * @return A new {@link VerkuendungImportProcessDto} based on the domain entity.
   */
  public static VerkuendungImportProcessDto mapToDto(
    final VerkuendungImportProcess verkuendungImportProcess
  ) {
    return VerkuendungImportProcessDto
      .builder()
      .id(verkuendungImportProcess.getId())
      .status(mapStatusToDto(verkuendungImportProcess.getStatus()))
      .createdAt(verkuendungImportProcess.getCreatedAt())
      .startedAt(verkuendungImportProcess.getStartedAt())
      .finishedAt(verkuendungImportProcess.getFinishedAt())
      .details(verkuendungImportProcess.getDetail())
      .build();
  }

  private static VerkuendungImportProcess.Status mapStatusToDomain(
    final VerkuendungImportProcessDto.Status status
  ) {
    return switch (status) {
      case VerkuendungImportProcessDto.Status.CREATED -> VerkuendungImportProcess.Status.CREATED;
      case VerkuendungImportProcessDto.Status.PROCESSING -> VerkuendungImportProcess.Status.PROCESSING;
      case VerkuendungImportProcessDto.Status.ERROR -> VerkuendungImportProcess.Status.ERROR;
      case VerkuendungImportProcessDto.Status.SUCCESS -> VerkuendungImportProcess.Status.SUCCESS;
    };
  }

  /**
   * Maps a status
   *
   * @param status to map
   * @return mapped status
   */
  public static VerkuendungImportProcessDto.Status mapStatusToDto(
    final VerkuendungImportProcess.Status status
  ) {
    return switch (status) {
      case VerkuendungImportProcess.Status.CREATED -> VerkuendungImportProcessDto.Status.CREATED;
      case VerkuendungImportProcess.Status.PROCESSING -> VerkuendungImportProcessDto.Status.PROCESSING;
      case VerkuendungImportProcess.Status.ERROR -> VerkuendungImportProcessDto.Status.ERROR;
      case VerkuendungImportProcess.Status.SUCCESS -> VerkuendungImportProcessDto.Status.SUCCESS;
    };
  }
}
