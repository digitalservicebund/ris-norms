package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungImportProcessDetailDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungImportProcessDto;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcessDetail;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class VerkuendungImportProcessMapperTest {

  @Test
  void itShouldMapToDomain() {
    // Given
    var dtoDetail = new VerkuendungImportProcessDetailDto(
      UUID.randomUUID(),
      "/example/type",
      "example title",
      "example detail"
    );
    var dto = VerkuendungImportProcessDto
      .builder()
      .id(UUID.randomUUID())
      .status(VerkuendungImportProcessDto.Status.ERROR)
      .createdAt(Instant.parse("2025-03-26T09:00:00Z"))
      .startedAt(Instant.parse("2025-03-26T10:00:00Z"))
      .finishedAt(Instant.parse("2025-03-26T11:00:00Z"))
      .detail(List.of(dtoDetail))
      .build();
    // When
    var entity = VerkuendungImportProcessMapper.mapToDomain(dto);

    var entityDetail = entity.getDetail().getFirst();
    // Then
    assertThat(entity.getId()).isEqualTo(dto.getId());

    assertThat(entity.getStatus()).isEqualTo(VerkuendungImportProcess.Status.ERROR);
    assertThat(entity.getCreatedAt()).isEqualTo(dto.getCreatedAt());
    assertThat(entity.getStartedAt()).isEqualTo(dto.getStartedAt());
    assertThat(entity.getFinishedAt()).isEqualTo(dto.getFinishedAt());

    assertThat(entityDetail.getType()).isEqualTo(dtoDetail.getType());
    assertThat(entityDetail.getTitle()).isEqualTo(dtoDetail.getTitle());
    assertThat(entityDetail.getDetail()).isEqualTo(dtoDetail.getDetail());
  }

  @Test
  void itShouldMapToDomainWithEmptyDetails() {
    // Given
    var dto = VerkuendungImportProcessDto
      .builder()
      .id(UUID.randomUUID())
      .status(VerkuendungImportProcessDto.Status.ERROR)
      .createdAt(Instant.parse("2025-03-26T09:00:00Z"))
      .startedAt(Instant.parse("2025-03-26T10:00:00Z"))
      .finishedAt(Instant.parse("2025-03-26T11:00:00Z"))
      .build();

    // When
    var entity = VerkuendungImportProcessMapper.mapToDomain(dto);

    // Then
    assertThat(entity.getId()).isEqualTo(dto.getId());
    assertThat(entity.getStatus()).isEqualTo(VerkuendungImportProcess.Status.ERROR);
    assertThat(entity.getCreatedAt()).isEqualTo(dto.getCreatedAt());
    assertThat(entity.getStartedAt()).isEqualTo(dto.getStartedAt());
    assertThat(entity.getFinishedAt()).isEqualTo(dto.getFinishedAt());
    assertThat(entity.getDetail()).isEmpty();
  }

  @Test
  void itShouldMapToDto() {
    // Given
    var entityDetail = VerkuendungImportProcessDetail
      .builder()
      .type("/example/type")
      .title("example title")
      .detail("example detail")
      .build();
    var entity = VerkuendungImportProcess
      .builder()
      .id(UUID.randomUUID())
      .status(VerkuendungImportProcess.Status.ERROR)
      .createdAt(Instant.parse("2025-03-26T09:00:00Z"))
      .startedAt(Instant.parse("2025-03-26T10:00:00Z"))
      .finishedAt(Instant.parse("2025-03-26T11:00:00Z"))
      .detail(List.of(entityDetail))
      .build();

    // When
    var dto = VerkuendungImportProcessMapper.mapToDto(entity);
    var dtoDetail = dto.getDetail().getFirst();

    // Then
    assertThat(dto.getId()).isEqualTo(entity.getId());
    assertThat(dto.getStatus()).isEqualTo(VerkuendungImportProcessDto.Status.ERROR);
    assertThat(dto.getCreatedAt()).isEqualTo(entity.getCreatedAt());
    assertThat(dto.getStartedAt()).isEqualTo(entity.getStartedAt());
    assertThat(dto.getFinishedAt()).isEqualTo(entity.getFinishedAt());

    assertThat(dtoDetail.getType()).isEqualTo(entityDetail.getType());
    assertThat(dtoDetail.getTitle()).isEqualTo(entityDetail.getTitle());
    assertThat(dtoDetail.getDetail()).isEqualTo(entityDetail.getDetail());
  }

  @Test
  void itShouldMapToDtoWithEmptyDetails() {
    // Given
    var entity = VerkuendungImportProcess
      .builder()
      .id(UUID.randomUUID())
      .status(VerkuendungImportProcess.Status.ERROR)
      .createdAt(Instant.parse("2025-03-26T09:00:00Z"))
      .startedAt(Instant.parse("2025-03-26T10:00:00Z"))
      .finishedAt(Instant.parse("2025-03-26T11:00:00Z"))
      .detail(List.of())
      .build();

    // When
    var dto = VerkuendungImportProcessMapper.mapToDto(entity);

    // Then
    assertThat(dto.getId()).isEqualTo(entity.getId());
    assertThat(dto.getStatus()).isEqualTo(VerkuendungImportProcessDto.Status.ERROR);
    assertThat(dto.getCreatedAt()).isEqualTo(entity.getCreatedAt());
    assertThat(dto.getStartedAt()).isEqualTo(entity.getStartedAt());
    assertThat(dto.getFinishedAt()).isEqualTo(entity.getFinishedAt());
    assertThat(dto.getDetail()).isEmpty();
  }

  @Test
  void itMapsStatusesCorrectlyToDoamin() {
    // Given
    var createdDto = VerkuendungImportProcessDto
      .builder()
      .status(VerkuendungImportProcessDto.Status.CREATED)
      .build();

    var processingDto = VerkuendungImportProcessDto
      .builder()
      .status(VerkuendungImportProcessDto.Status.PROCESSING)
      .build();

    var errorDto = VerkuendungImportProcessDto
      .builder()
      .status(VerkuendungImportProcessDto.Status.ERROR)
      .build();

    var successDto = VerkuendungImportProcessDto
      .builder()
      .status(VerkuendungImportProcessDto.Status.SUCCESS)
      .build();

    // When
    var createdEntity = VerkuendungImportProcessMapper.mapToDomain(createdDto);
    var processingEntity = VerkuendungImportProcessMapper.mapToDomain(processingDto);
    var errorEntity = VerkuendungImportProcessMapper.mapToDomain(errorDto);
    var successEntity = VerkuendungImportProcessMapper.mapToDomain(successDto);

    // Then
    assertThat(createdEntity.getStatus()).isEqualTo(VerkuendungImportProcess.Status.CREATED);
    assertThat(processingEntity.getStatus()).isEqualTo(VerkuendungImportProcess.Status.PROCESSING);
    assertThat(errorEntity.getStatus()).isEqualTo(VerkuendungImportProcess.Status.ERROR);
    assertThat(successEntity.getStatus()).isEqualTo(VerkuendungImportProcess.Status.SUCCESS);
  }

  @Test
  void itMapsStatusesCorrectlyToDto() {
    // Given
    var createdEntity = VerkuendungImportProcess
      .builder()
      .status(VerkuendungImportProcess.Status.CREATED)
      .build();

    var processingEntity = VerkuendungImportProcess
      .builder()
      .status(VerkuendungImportProcess.Status.PROCESSING)
      .build();

    var errorEntity = VerkuendungImportProcess
      .builder()
      .status(VerkuendungImportProcess.Status.ERROR)
      .build();

    var successEntity = VerkuendungImportProcess
      .builder()
      .status(VerkuendungImportProcess.Status.SUCCESS)
      .build();

    // When
    var createdDto = VerkuendungImportProcessMapper.mapToDto(createdEntity);
    var processingDto = VerkuendungImportProcessMapper.mapToDto(processingEntity);
    var errorDto = VerkuendungImportProcessMapper.mapToDto(errorEntity);
    var successDto = VerkuendungImportProcessMapper.mapToDto(successEntity);

    // Then
    assertThat(createdDto.getStatus()).isEqualTo(VerkuendungImportProcessDto.Status.CREATED);
    assertThat(processingDto.getStatus()).isEqualTo(VerkuendungImportProcessDto.Status.PROCESSING);
    assertThat(errorDto.getStatus()).isEqualTo(VerkuendungImportProcessDto.Status.ERROR);
    assertThat(successDto.getStatus()).isEqualTo(VerkuendungImportProcessDto.Status.SUCCESS);
  }
}
