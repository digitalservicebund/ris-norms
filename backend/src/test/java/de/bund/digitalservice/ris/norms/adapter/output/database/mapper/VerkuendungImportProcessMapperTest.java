package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungImportProcessDto;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class VerkuendungImportProcessMapperTest {

  @Test
  void itShouldMapToDomain() {
    // Given
    var dto = VerkuendungImportProcessDto.builder()
      .id(UUID.randomUUID())
      .status(VerkuendungImportProcessDto.Status.ERROR)
      .createdAt(Instant.parse("2025-03-26T09:00:00Z"))
      .startedAt(Instant.parse("2025-03-26T10:00:00Z"))
      .finishedAt(Instant.parse("2025-03-26T11:00:00Z"))
      .details(
        """
        {
          "type": "/errors/job-run-failed",
          "title": "Tried to import a Normendokumentationspacket the max amount of times but failed",
          "detail": "detail message",
          "additionalProperty": "some-value"
        }
        """
      )
      .build();
    // When
    var entity = VerkuendungImportProcessMapper.mapToDomain(dto);

    // Then
    assertThat(entity.getId()).isEqualTo(dto.getId());

    assertThat(entity.getStatus()).isEqualTo(VerkuendungImportProcess.Status.ERROR);
    assertThat(entity.getCreatedAt()).isEqualTo(dto.getCreatedAt());
    assertThat(entity.getStartedAt()).isEqualTo(dto.getStartedAt());
    assertThat(entity.getFinishedAt()).isEqualTo(dto.getFinishedAt());
    assertThat(entity.getDetail()).contains("/errors/job-run-failed");
  }

  @Test
  void itShouldMapToDomainWithEmptyDetails() {
    // Given
    var dto = VerkuendungImportProcessDto.builder()
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
    assertThat(entity.getDetail()).isNull();
  }

  @Test
  void itShouldMapToDto() {
    // Given
    var entity = VerkuendungImportProcess.builder()
      .id(UUID.randomUUID())
      .status(VerkuendungImportProcess.Status.ERROR)
      .createdAt(Instant.parse("2025-03-26T09:00:00Z"))
      .startedAt(Instant.parse("2025-03-26T10:00:00Z"))
      .finishedAt(Instant.parse("2025-03-26T11:00:00Z"))
      .detail(
        """
        {
          "type": "/errors/job-run-failed",
          "title": "Tried to import a Normendokumentationspacket the max amount of times but failed",
          "detail": "detail message",
          "additionalProperty": "some-value"
        }
        """
      )
      .build();

    // When
    var dto = VerkuendungImportProcessMapper.mapToDto(entity);

    // Then
    assertThat(dto.getId()).isEqualTo(entity.getId());
    assertThat(dto.getStatus()).isEqualTo(VerkuendungImportProcessDto.Status.ERROR);
    assertThat(dto.getCreatedAt()).isEqualTo(entity.getCreatedAt());
    assertThat(dto.getStartedAt()).isEqualTo(entity.getStartedAt());
    assertThat(dto.getFinishedAt()).isEqualTo(entity.getFinishedAt());

    assertThat(dto.getDetails()).contains("/errors/job-run-failed");
  }

  @Test
  void itShouldMapToDtoWithEmptyDetails() {
    // Given
    var entity = VerkuendungImportProcess.builder()
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
    assertThat(dto.getDetails()).isNull();
  }

  @Test
  void itMapsStatusesCorrectlyToDoamin() {
    // Given
    var createdDto = VerkuendungImportProcessDto.builder()
      .status(VerkuendungImportProcessDto.Status.CREATED)
      .build();

    var processingDto = VerkuendungImportProcessDto.builder()
      .status(VerkuendungImportProcessDto.Status.PROCESSING)
      .build();

    var errorDto = VerkuendungImportProcessDto.builder()
      .status(VerkuendungImportProcessDto.Status.ERROR)
      .build();

    var successDto = VerkuendungImportProcessDto.builder()
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
    var createdEntity = VerkuendungImportProcess.builder()
      .status(VerkuendungImportProcess.Status.CREATED)
      .build();

    var processingEntity = VerkuendungImportProcess.builder()
      .status(VerkuendungImportProcess.Status.PROCESSING)
      .build();

    var errorEntity = VerkuendungImportProcess.builder()
      .status(VerkuendungImportProcess.Status.ERROR)
      .build();

    var successEntity = VerkuendungImportProcess.builder()
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
