package de.bund.digitalservice.ris.norms.integration.adapter.output.database;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungImportProcessDetailDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungImportProcessDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.VerkuendungImportProcessesRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.service.VerkuedungImportProcessDBService;
import de.bund.digitalservice.ris.norms.application.port.output.LoadVerkuendungImportProcessPort;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class VerkuendungImportProcessDBServiceIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private VerkuedungImportProcessDBService verkuedungImportProcessDBService;

  @Autowired
  private VerkuendungImportProcessesRepository verkuendungImportProcessesRepository;

  @AfterEach
  void cleanUp() {
    verkuendungImportProcessesRepository.deleteAll();
  }

  @Nested
  class loadVerkuendungImportProcess {

    @Test
    void itLoadsAProcessWithoutDetails() {
      // Given
      var dto = VerkuendungImportProcessDto
        .builder()
        .id(null)
        .status(VerkuendungImportProcessDto.Status.ERROR)
        .createdAt(Instant.parse("2025-03-26T09:00:00Z"))
        .startedAt(Instant.parse("2025-03-26T10:00:00Z"))
        .finishedAt(Instant.parse("2025-03-26T11:00:00Z"))
        .detail(List.of())
        .build();

      var saved = verkuendungImportProcessesRepository.save(dto);

      // When
      var resultOptional = verkuedungImportProcessDBService.loadVerkuendungImportProcess(
        new LoadVerkuendungImportProcessPort.Command(saved.getId())
      );

      // Then
      assertThat(resultOptional).isPresent();
      assertThat(resultOptional.get().getStatus()).isEqualTo(VerkuendungImportProcess.Status.ERROR);
      assertThat(resultOptional.get().getCreatedAt()).isEqualTo(dto.getCreatedAt());
      assertThat(resultOptional.get().getStartedAt()).isEqualTo(dto.getStartedAt());
      assertThat(resultOptional.get().getFinishedAt()).isEqualTo(dto.getFinishedAt());
      assertThat(resultOptional.get().getDetail()).isEmpty();
    }

    @Test
    void itLoadsAProcessWithDetails() {
      // Given
      var dtoDetail = new VerkuendungImportProcessDetailDto(
        null,
        "/example/type",
        "example title",
        "example detail"
      );
      var dto = VerkuendungImportProcessDto
        .builder()
        .id(null)
        .status(VerkuendungImportProcessDto.Status.ERROR)
        .createdAt(Instant.parse("2025-03-26T09:00:00Z"))
        .startedAt(Instant.parse("2025-03-26T10:00:00Z"))
        .finishedAt(Instant.parse("2025-03-26T11:00:00Z"))
        .detail(List.of(dtoDetail))
        .build();

      var saved = verkuendungImportProcessesRepository.save(dto);

      // When
      var resultOptional = verkuedungImportProcessDBService.loadVerkuendungImportProcess(
        new LoadVerkuendungImportProcessPort.Command(saved.getId())
      );
      var detail = resultOptional.get().getDetail().getFirst();

      // Then
      assertThat(resultOptional).isPresent();
      assertThat(resultOptional.get().getStatus()).isEqualTo(VerkuendungImportProcess.Status.ERROR);
      assertThat(resultOptional.get().getCreatedAt()).isEqualTo(dto.getCreatedAt());
      assertThat(resultOptional.get().getStartedAt()).isEqualTo(dto.getStartedAt());
      assertThat(resultOptional.get().getFinishedAt()).isEqualTo(dto.getFinishedAt());
      assertThat(resultOptional.get().getDetail()).hasSize(1);

      assertThat(detail.getType()).isEqualTo(dtoDetail.getType());
      assertThat(detail.getTitle()).isEqualTo(dtoDetail.getTitle());
      assertThat(detail.getDetail()).isEqualTo(dtoDetail.getDetail());
    }

    @Test
    void itReturnsEmptyIfTheProcessDoesntExist() {
      // Given
      // Nothing

      // When
      var resultOptional = verkuedungImportProcessDBService.loadVerkuendungImportProcess(
        new LoadVerkuendungImportProcessPort.Command(UUID.randomUUID())
      );

      // Then
      assertThat(resultOptional).isEmpty();
    }
  }
}
