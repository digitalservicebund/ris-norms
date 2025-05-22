package de.bund.digitalservice.ris.norms.integration.adapter.output.database;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungImportProcessDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.VerkuendungImportProcessMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.VerkuendungImportProcessesRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.service.VerkuedungImportProcessDBService;
import de.bund.digitalservice.ris.norms.application.port.input.ProcessNormendokumentationspaketUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadVerkuendungImportProcessPort;
import de.bund.digitalservice.ris.norms.application.port.output.SaveVerkuendungImportProcessPort;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.time.Instant;
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
      var dto = VerkuendungImportProcessDto.builder()
        .id(UUID.randomUUID())
        .status(VerkuendungImportProcessDto.Status.ERROR)
        .createdAt(Instant.parse("2025-03-26T09:00:00Z"))
        .startedAt(Instant.parse("2025-03-26T10:00:00Z"))
        .finishedAt(Instant.parse("2025-03-26T11:00:00Z"))
        .details(null)
        .build();

      var saved = verkuendungImportProcessesRepository.save(dto);

      // When
      var resultOptional = verkuedungImportProcessDBService.loadVerkuendungImportProcess(
        new LoadVerkuendungImportProcessPort.Options(saved.getId())
      );

      // Then
      assertThat(resultOptional).isPresent();
      assertThat(resultOptional.get().getStatus()).isEqualTo(VerkuendungImportProcess.Status.ERROR);
      assertThat(resultOptional.get().getCreatedAt()).isEqualTo(dto.getCreatedAt());
      assertThat(resultOptional.get().getStartedAt()).isEqualTo(dto.getStartedAt());
      assertThat(resultOptional.get().getFinishedAt()).isEqualTo(dto.getFinishedAt());
      assertThat(resultOptional.get().getDetail()).isNull();
    }

    @Test
    void itLoadsAProcessWithDetails() {
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

      var saved = verkuendungImportProcessesRepository.save(dto);

      // When
      var resultOptional = verkuedungImportProcessDBService.loadVerkuendungImportProcess(
        new LoadVerkuendungImportProcessPort.Options(saved.getId())
      );

      // Then
      assertThat(resultOptional).isPresent();
      assertThat(resultOptional.get().getStatus()).isEqualTo(VerkuendungImportProcess.Status.ERROR);
      assertThat(resultOptional.get().getCreatedAt()).isEqualTo(dto.getCreatedAt());
      assertThat(resultOptional.get().getStartedAt()).isEqualTo(dto.getStartedAt());
      assertThat(resultOptional.get().getFinishedAt()).isEqualTo(dto.getFinishedAt());
      assertThat(resultOptional.get().getDetail()).contains("/errors/job-run-failed");
    }

    @Test
    void itReturnsEmptyIfTheProcessDoesntExist() {
      // Given
      // Nothing

      // When
      var resultOptional = verkuedungImportProcessDBService.loadVerkuendungImportProcess(
        new LoadVerkuendungImportProcessPort.Options(UUID.randomUUID())
      );

      // Then
      assertThat(resultOptional).isEmpty();
    }
  }

  @Nested
  class saveVerkuendungImportProcess {

    @Test
    void itSavesAnInitialProcessStart() {
      // Given
      SaveVerkuendungImportProcessPort.Options options =
        new SaveVerkuendungImportProcessPort.Options(
          UUID.randomUUID(),
          VerkuendungImportProcess.Status.CREATED
        );
      // When

      var resultProcess = verkuedungImportProcessDBService.saveOrUpdateVerkuendungImportProcess(
        options
      );

      // Then
      assertThat(resultProcess).isNotNull();
      assertThat(resultProcess.getStatus()).isEqualTo(VerkuendungImportProcess.Status.CREATED);
      assertThat(resultProcess.getCreatedAt()).isBetween(
        Instant.now().minusSeconds(30),
        Instant.now()
      );
      assertThat(resultProcess.getStartedAt()).isNull();
      assertThat(resultProcess.getFinishedAt()).isNull();
      assertThat(resultProcess.getDetail()).isNull();
    }

    @Test
    void itUpdatesAProcessWithNewDetails() {
      // Given
      // Initial process with no details
      var initialProcess = VerkuendungImportProcess.builder()
        .id(UUID.randomUUID())
        .status(VerkuendungImportProcess.Status.PROCESSING)
        .createdAt(Instant.parse("2025-03-26T09:00:00Z"))
        .startedAt(Instant.parse("2025-03-26T10:00:00Z"))
        .finishedAt(null)
        .build();

      var initialDto = VerkuendungImportProcessMapper.mapToDto(initialProcess);
      var savedProcess = verkuendungImportProcessesRepository.save(initialDto);

      // Update information with new details
      SaveVerkuendungImportProcessPort.Options options =
        new SaveVerkuendungImportProcessPort.Options(
          savedProcess.getId(),
          VerkuendungImportProcess.Status.ERROR,
          new ProcessNormendokumentationspaketUseCase.NotAZipFileException()
        );

      // When
      var resultProcess = verkuedungImportProcessDBService.saveOrUpdateVerkuendungImportProcess(
        options
      );

      var reloadedFromDb = verkuendungImportProcessesRepository
        .findById(resultProcess.getId())
        .get();

      // Then
      assertThat(reloadedFromDb).isNotNull();
      assertThat(reloadedFromDb.getStatus()).isEqualTo(VerkuendungImportProcessDto.Status.ERROR);
      assertThat(reloadedFromDb.getCreatedAt()).isEqualTo(initialProcess.getCreatedAt());
      assertThat(reloadedFromDb.getStartedAt()).isEqualTo(initialProcess.getStartedAt());
      assertThat(reloadedFromDb.getFinishedAt()).isBetween(
        Instant.now().minusSeconds(30),
        Instant.now()
      );
      assertThat(reloadedFromDb.getDetails()).contains(
        "/errors/normendokumentationspaket-import-failed/not-a-zip-file"
      );
    }
  }
}
