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
    var dto = new VerkuendungImportProcessDto(
      UUID.randomUUID(),
      VerkuendungImportProcessDto.Status.ERROR,
      Instant.parse("2025-03-26T09:00:00Z"),
      Instant.parse("2025-03-26T10:00:00Z"),
      Instant.parse("2025-03-26T11:00:00Z"),
      List.of(dtoDetail)
    );

    // When
    var entity = VerkuendungImportProcessMapper.mapToDomain(dto);
    var entityDetail = entity.getDetail().getFirst();

    // Then
    assertThat(entity.getId()).isEqualTo(dto.getId());
    assertThat(entity.getStatus()).isEqualTo(VerkuendungImportProcess.Status.ERROR);
    assertThat(entity.getCreatedAt()).isEqualTo(dto.getCreatedAt());
    assertThat(entity.getStartedAt()).isEqualTo(dto.getStartedAt());
    assertThat(entity.getFinishedAt()).isEqualTo(dto.getFinishedAt());

    assertThat(entityDetail.getId()).isEqualTo(dtoDetail.getId());
    assertThat(entityDetail.getType()).isEqualTo(dtoDetail.getType());
    assertThat(entityDetail.getTitle()).isEqualTo(dtoDetail.getTitle());
    assertThat(entityDetail.getDetail()).isEqualTo(dtoDetail.getDetail());
  }

  @Test
  void itShouldMapToDomainWithEmptyDetails() {
    // Given
    var dto = new VerkuendungImportProcessDto(
      UUID.randomUUID(),
      VerkuendungImportProcessDto.Status.ERROR,
      Instant.parse("2025-03-26T09:00:00Z"),
      Instant.parse("2025-03-26T10:00:00Z"),
      Instant.parse("2025-03-26T11:00:00Z"),
      null
    );

    // When
    var entity = VerkuendungImportProcessMapper.mapToDomain(dto);

    // Then
    assertThat(entity.getId()).isEqualTo(dto.getId());
    assertThat(entity.getStatus()).isEqualTo(VerkuendungImportProcess.Status.ERROR);
    assertThat(entity.getCreatedAt()).isEqualTo(dto.getCreatedAt());
    assertThat(entity.getStartedAt()).isEqualTo(dto.getStartedAt());
    assertThat(entity.getFinishedAt()).isEqualTo(dto.getFinishedAt());
    assertThat(entity.getDetail()).isNull();
  }

  @Test
  void itShouldMapToDto() {
    // Given
    var entityDetail = VerkuendungImportProcessDetail
      .builder()
      .id(UUID.randomUUID())
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

    assertThat(dtoDetail.getId()).isEqualTo(entityDetail.getId());
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
      .build();

    // When
    var dto = VerkuendungImportProcessMapper.mapToDto(entity);

    // Then
    assertThat(dto.getId()).isEqualTo(entity.getId());
    assertThat(dto.getStatus()).isEqualTo(VerkuendungImportProcessDto.Status.ERROR);
    assertThat(dto.getCreatedAt()).isEqualTo(entity.getCreatedAt());
    assertThat(dto.getStartedAt()).isEqualTo(entity.getStartedAt());
    assertThat(dto.getFinishedAt()).isEqualTo(entity.getFinishedAt());
    assertThat(dto.getDetail()).isNull();
  }

  @Test
  void itMapsStatusesCorrectlyToDoamin() {
    // Given
    var createdDto = new VerkuendungImportProcessDto();
    createdDto.setStatus(VerkuendungImportProcessDto.Status.CREATED);

    var processingDto = new VerkuendungImportProcessDto();
    processingDto.setStatus(VerkuendungImportProcessDto.Status.PROCESSING);

    var errorDto = new VerkuendungImportProcessDto();
    errorDto.setStatus(VerkuendungImportProcessDto.Status.ERROR);

    var successDto = new VerkuendungImportProcessDto();
    successDto.setStatus(VerkuendungImportProcessDto.Status.SUCCESS);

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
