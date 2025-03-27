package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungImportProcessDetailDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungImportProcessDto;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcessDetail;

/**
 * Converts background process information for newly uploaded Verkündungen between
 * the DTOs and domain entities.
 */
public class VerkuendungImportProcessMapper {

  private VerkuendungImportProcessMapper() {}

  /**
   * Maps a {@link VerkuendungImportProcessDto} to the corresponding domain
   * entity.
   *
   * @param verkuendungImportProcessDto The input that should be converted
   * @return A new {@link VerkuendungImportProcess} based on the DTO
   */
  public static VerkuendungImportProcess mapToDomain(
    final VerkuendungImportProcessDto verkuendungImportProcessDto
  ) {
    var detail = verkuendungImportProcessDto.getDetail() == null
      ? null
      : verkuendungImportProcessDto
        .getDetail()
        .stream()
        .map(VerkuendungImportProcessMapper::mapDetailToDomain)
        .toList();

    return VerkuendungImportProcess
      .builder()
      .id(verkuendungImportProcessDto.getId())
      .status(mapStatusToDomain(verkuendungImportProcessDto.getStatus()))
      .createdAt(verkuendungImportProcessDto.getCreatedAt())
      .startedAt(verkuendungImportProcessDto.getStartedAt())
      .finishedAt(verkuendungImportProcessDto.getFinishedAt())
      .detail(detail)
      .build();
  }

  /**
   * Maps a {@link VerkuendungImportProcess} to the corresponding DTO.
   *
   * @param verkuendungImportProcess The input that should be converted
   * @return A new {@link VerkuendungImportProcessDto} based on the domain
   *         entity.
   */
  public static VerkuendungImportProcessDto mapToDto(
    final VerkuendungImportProcess verkuendungImportProcess
  ) {
    var detail = verkuendungImportProcess.getDetail() == null
      ? null
      : verkuendungImportProcess
        .getDetail()
        .stream()
        .map(VerkuendungImportProcessMapper::mapDetailToDto)
        .toList();

    return new VerkuendungImportProcessDto(
      verkuendungImportProcess.getId(),
      mapStatusToDto(verkuendungImportProcess.getStatus()),
      verkuendungImportProcess.getCreatedAt(),
      verkuendungImportProcess.getStartedAt(),
      verkuendungImportProcess.getFinishedAt(),
      detail
    );
  }

  private static VerkuendungImportProcessDetail mapDetailToDomain(
    final VerkuendungImportProcessDetailDto verkuendungImportProcessDetailDto
  ) {
    return VerkuendungImportProcessDetail
      .builder()
      .id(verkuendungImportProcessDetailDto.getId())
      .type(verkuendungImportProcessDetailDto.getType())
      .title(verkuendungImportProcessDetailDto.getTitle())
      .detail(verkuendungImportProcessDetailDto.getDetail())
      .build();
  }

  private static VerkuendungImportProcessDetailDto mapDetailToDto(
    final VerkuendungImportProcessDetail verkuendungImportProcessDetail
  ) {
    return new VerkuendungImportProcessDetailDto(
      verkuendungImportProcessDetail.getId(),
      verkuendungImportProcessDetail.getType(),
      verkuendungImportProcessDetail.getTitle(),
      verkuendungImportProcessDetail.getDetail()
    );
  }

  private static VerkuendungImportProcess.Status mapStatusToDomain(
    final VerkuendungImportProcessDto.Status status
  ) {
    switch (status) {
      case VerkuendungImportProcessDto.Status.CREATED:
        return VerkuendungImportProcess.Status.CREATED;
      case VerkuendungImportProcessDto.Status.PROCESSING:
        return VerkuendungImportProcess.Status.PROCESSING;
      case VerkuendungImportProcessDto.Status.ERROR:
        return VerkuendungImportProcess.Status.ERROR;
      case VerkuendungImportProcessDto.Status.SUCCESS:
        return VerkuendungImportProcess.Status.SUCCESS;
      default:
        throw new IllegalArgumentException(
          "Cannot map unknown status %s to domain entity".formatted(status)
        );
    }
  }

  private static VerkuendungImportProcessDto.Status mapStatusToDto(
    final VerkuendungImportProcess.Status status
  ) {
    switch (status) {
      case VerkuendungImportProcess.Status.CREATED:
        return VerkuendungImportProcessDto.Status.CREATED;
      case VerkuendungImportProcess.Status.PROCESSING:
        return VerkuendungImportProcessDto.Status.PROCESSING;
      case VerkuendungImportProcess.Status.ERROR:
        return VerkuendungImportProcessDto.Status.ERROR;
      case VerkuendungImportProcess.Status.SUCCESS:
        return VerkuendungImportProcessDto.Status.SUCCESS;
      default:
        throw new IllegalArgumentException("Cannot map unknown status %s to DTO".formatted(status));
    }
  }
}
