package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.MigrationLogDto;
import de.bund.digitalservice.ris.norms.domain.entity.MigrationLog;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class MigrationLogMapperTest {

  @Test
  void itShouldMapToDomain() {
    // Given
    var dtoObj = MigrationLogDto
      .builder()
      .id(UUID.randomUUID())
      .size(5)
      .createdAt(Instant.parse("2025-03-03T15:00:00.0Z"))
      .completed(false)
      .build();

    // When
    var domainObj = MigrationLogMapper.mapToDomain(dtoObj);

    // Then
    assertThat(domainObj.getId()).isEqualTo(dtoObj.getId());
    assertThat(domainObj.getSize()).isEqualTo(dtoObj.getSize());
    assertThat(domainObj.getCreatedAt()).isEqualTo(dtoObj.getCreatedAt());
    assertThat(domainObj.isCompleted()).isEqualTo(dtoObj.isCompleted());
  }

  @Test
  void itShouldMapToDtos() {
    // Given
    var domainObj = MigrationLog
      .builder()
      .id(UUID.randomUUID())
      .size(5)
      .createdAt(Instant.parse("2025-03-03T15:00:00.0Z"))
      .completed(false)
      .build();

    // When
    var dtoObj = MigrationLogMapper.mapToDto(domainObj);

    // Then
    assertThat(dtoObj.getId()).isEqualTo(domainObj.getId());
    assertThat(dtoObj.getSize()).isEqualTo(domainObj.getSize());
    assertThat(dtoObj.getCreatedAt()).isEqualTo(domainObj.getCreatedAt());
    assertThat(dtoObj.isCompleted()).isEqualTo(domainObj.isCompleted());
  }
}
