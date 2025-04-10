package de.bund.digitalservice.ris.norms.integration.adapter.output.database;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungImportProcessDetailDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungImportProcessDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.VerkuendungImportProcessMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.VerkuendungImportProcessesRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.service.VerkuedungImportProcessDBService;
import de.bund.digitalservice.ris.norms.application.port.output.LoadVerkuendungImportProcessPort;
import de.bund.digitalservice.ris.norms.application.port.output.SaveVerkuendungImportProcessPort;
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
        .id(UUID.randomUUID())
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
      var verkuendungsDetail = new VerkuendungImportProcessDetailDto(
        null,
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
        .detail(List.of(verkuendungsDetail))
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

      assertThat(detail.getType()).isEqualTo(verkuendungsDetail.getType());
      assertThat(detail.getTitle()).isEqualTo(verkuendungsDetail.getTitle());
      assertThat(detail.getDetail()).isEqualTo(verkuendungsDetail.getDetail());
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
  class saveVerkuendungImportProcess {

    @Test
    void itSavesAnInitialProcessStart() {
      // Given
      SaveVerkuendungImportProcessPort.Command command =
        new SaveVerkuendungImportProcessPort.Command(
          UUID.randomUUID(),
          VerkuendungImportProcess.Status.CREATED
        );
      // When

      var resultProcess = verkuedungImportProcessDBService.saveOrUpdateVerkuendungImportProcess(
        command
      );

      // Then
      assertThat(resultProcess).isNotNull();
      assertThat(resultProcess.getStatus()).isEqualTo(VerkuendungImportProcess.Status.CREATED);
      assertThat(resultProcess.getCreatedAt())
        .isBetween(Instant.now().minusSeconds(30), Instant.now());
      assertThat(resultProcess.getStartedAt()).isNull();
      assertThat(resultProcess.getFinishedAt()).isNull();
      assertThat(resultProcess.getDetail()).isEmpty();
    }

    @Test
    void itUpdatesAProcessWithNewDetails() {
      // Given
      // Initial process with no details
      var initialProcess = VerkuendungImportProcess
        .builder()
        .id(UUID.randomUUID())
        .status(VerkuendungImportProcess.Status.PROCESSING)
        .createdAt(Instant.parse("2025-03-26T09:00:00Z"))
        .startedAt(Instant.parse("2025-03-26T10:00:00Z"))
        .finishedAt(null)
        .detail(List.of())
        .build();

      var initialDto = VerkuendungImportProcessMapper.mapToDto(initialProcess);
      var savedProcess = verkuendungImportProcessesRepository.save(initialDto);

      // Update information with new details
      SaveVerkuendungImportProcessPort.Command command =
        new SaveVerkuendungImportProcessPort.Command(
          savedProcess.getId(),
          VerkuendungImportProcess.Status.ERROR,
          List.of(
            new VerkuendungImportProcessDetail("type", "title", "detail"),
            new VerkuendungImportProcessDetail("type2", "title2", "detail2")
          )
        );

      // When
      var resultProcess = verkuedungImportProcessDBService.saveOrUpdateVerkuendungImportProcess(
        command
      );

      var reloadedFromDb = verkuendungImportProcessesRepository
        .findById(resultProcess.getId())
        .get();

      // Then
      assertThat(reloadedFromDb).isNotNull();
      assertThat(reloadedFromDb.getStatus()).isEqualTo(VerkuendungImportProcessDto.Status.ERROR);
      assertThat(reloadedFromDb.getCreatedAt()).isEqualTo(initialProcess.getCreatedAt());
      assertThat(reloadedFromDb.getStartedAt()).isEqualTo(initialProcess.getStartedAt());
      assertThat(reloadedFromDb.getFinishedAt())
        .isBetween(Instant.now().minusSeconds(30), Instant.now());
      assertThat(reloadedFromDb.getDetail()).hasSize(2);
      assertThat(reloadedFromDb.getDetail().get(0).getDetail()).isEqualTo("detail");
      assertThat(reloadedFromDb.getDetail().get(1).getDetail()).isEqualTo("detail2");
    }

    @Test
    void DetailsAreNotDuplicated() {
      // Given
      // Initial process with no details
      var initialProcess = VerkuendungImportProcessDto
        .builder()
        .id(UUID.randomUUID())
        .status(VerkuendungImportProcessDto.Status.PROCESSING)
        .createdAt(Instant.parse("2025-03-25T09:00:00Z"))
        .startedAt(Instant.parse("2025-03-25T10:00:00Z"))
        .finishedAt(null)
        .detail(
          List.of(
            new VerkuendungImportProcessDetailDto(null, "type", "title", "detail"),
            new VerkuendungImportProcessDetailDto(null, "type2", "title2", "detail2")
          )
        )
        .build();

      var savedProcess = verkuendungImportProcessesRepository.save(initialProcess);

      // Update information with new details
      SaveVerkuendungImportProcessPort.Command command =
        new SaveVerkuendungImportProcessPort.Command(
          savedProcess.getId(),
          VerkuendungImportProcess.Status.ERROR,
          List.of(
            new VerkuendungImportProcessDetail("type3", "title3", "detail3"),
            new VerkuendungImportProcessDetail("type", "title", "detail")
          )
        );

      // When
      var resultProcess = verkuedungImportProcessDBService.saveOrUpdateVerkuendungImportProcess(
        command
      );

      // Then
      assertThat(resultProcess).isNotNull();
      assertThat(resultProcess.getStatus()).isEqualTo(VerkuendungImportProcess.Status.ERROR);
      assertThat(resultProcess.getCreatedAt()).isEqualTo(initialProcess.getCreatedAt());
      assertThat(resultProcess.getStartedAt()).isEqualTo(initialProcess.getStartedAt());
      assertThat(resultProcess.getFinishedAt())
        .isBetween(Instant.now().minusSeconds(30), Instant.now());
      assertThat(resultProcess.getDetail()).hasSize(3);
      assertThat(resultProcess.getDetail().get(0).getDetail()).isEqualTo("detail");
      assertThat(resultProcess.getDetail().get(1).getDetail()).isEqualTo("detail2");
      assertThat(resultProcess.getDetail().get(2).getDetail()).isEqualTo("detail3");
    }
  }
}
