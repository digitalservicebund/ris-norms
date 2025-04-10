package de.bund.digitalservice.ris.norms.integration.adapter.output.database;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungImportProcessDetailDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungImportProcessDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.VerkuendungImportProcessesRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.service.VerkuedungImportProcessDBService;
import de.bund.digitalservice.ris.norms.application.port.output.LoadVerkuendungImportProcessPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateVerkuendungImportProcessPort;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcessDetail;
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

  @Nested
  class updateVerkuendungImportProcess {

    @Test
    void itUpdateAProcessWithNewInformation() {
      // Given
      var dto = VerkuendungImportProcessDto
        .builder()
        .id(null)
        .status(VerkuendungImportProcessDto.Status.CREATED)
        .createdAt(Instant.parse("2025-03-26T09:00:00Z"))
        .detail(List.of())
        .build();

      var saved = verkuendungImportProcessesRepository.save(dto);
      var process = verkuedungImportProcessDBService
        .loadVerkuendungImportProcess(new LoadVerkuendungImportProcessPort.Command(saved.getId()))
        .orElseThrow();

      // When
      process.setStatus(VerkuendungImportProcess.Status.ERROR);
      process.setStartedAt(Instant.parse("2025-03-26T10:00:00Z"));
      process.setFinishedAt(Instant.parse("2025-03-26T11:00:00Z"));
      process.setDetail(
        List.of(
          VerkuendungImportProcessDetail
            .builder()
            .type("/error/processing-error")
            .title("Processing Error")
            .detail("{ \"fileName\": \"bla.zip\" }")
            .build()
        )
      );
      verkuedungImportProcessDBService.updateVerkuendungImportProcess(
        new UpdateVerkuendungImportProcessPort.Command(process)
      );

      // When
      var resultOptional = verkuedungImportProcessDBService.loadVerkuendungImportProcess(
        new LoadVerkuendungImportProcessPort.Command(saved.getId())
      );
      assertThat(resultOptional).isPresent();
      assertThat(resultOptional.get().getStatus()).isEqualTo(VerkuendungImportProcess.Status.ERROR);
      assertThat(resultOptional.get().getCreatedAt())
        .isEqualTo(Instant.parse("2025-03-26T09:00:00Z"));
      assertThat(resultOptional.get().getStartedAt())
        .isEqualTo(Instant.parse("2025-03-26T10:00:00Z"));
      assertThat(resultOptional.get().getFinishedAt())
        .isEqualTo(Instant.parse("2025-03-26T11:00:00Z"));
      assertThat(resultOptional.get().getDetail()).hasSize(1);
      assertThat(resultOptional.get().getDetail().get(0).getType())
        .isEqualTo("/error/processing-error");
      assertThat(resultOptional.get().getDetail().get(0).getTitle()).isEqualTo("Processing Error");
      assertThat(resultOptional.get().getDetail().get(0).getDetail())
        .isEqualTo("{ \"fileName\": \"bla.zip\" }");
    }
  }
}
