package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.exception.StatusNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormendokumentationspacketProcessingStatusUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadVerkuendungImportProcessPort;
import de.bund.digitalservice.ris.norms.application.port.output.SaveNormendokumentationspaketPort;
import de.bund.digitalservice.ris.norms.application.port.output.SaveVerkuendungImportProcessPort;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.jobrunr.scheduling.JobScheduler;
import org.junit.jupiter.api.Test;

class VerkuendungsImportServiceTest {

  private final SaveNormendokumentationspaketPort saveNormendokumentationspaketPort = mock(
    SaveNormendokumentationspaketPort.class
  );
  private final SaveVerkuendungImportProcessPort saveVerkuendungImportProcessPort = mock(
    SaveVerkuendungImportProcessPort.class
  );
  private final LoadVerkuendungImportProcessPort loadVerkuendungImportProcessPort = mock(
    LoadVerkuendungImportProcessPort.class
  );
  private final JobScheduler jobScheduler = mock(JobScheduler.class);

  private final VerkuendungsImportService verkuendungsImportService = new VerkuendungsImportService(
    saveNormendokumentationspaketPort,
    saveVerkuendungImportProcessPort,
    loadVerkuendungImportProcessPort,
    jobScheduler
  );

  @Test
  void getStatus() {
    // given
    UUID uuid = UUID.randomUUID();
    VerkuendungImportProcess verkuendungImportProcess = VerkuendungImportProcess
      .builder()
      .id(uuid)
      .status(VerkuendungImportProcess.Status.CREATED)
      .build();
    when(loadVerkuendungImportProcessPort.loadVerkuendungImportProcess(any()))
      .thenReturn(Optional.of(verkuendungImportProcess));

    //when
    var result = verkuendungsImportService.getStatus(
      new LoadNormendokumentationspacketProcessingStatusUseCase.Query(uuid)
    );

    //then
    verify(loadVerkuendungImportProcessPort).loadVerkuendungImportProcess(any());
    assertThat(result.getId()).isEqualTo(uuid);
    assertThat(result.getStatus()).isEqualTo(VerkuendungImportProcess.Status.CREATED);
    assertThat(result.getCreatedAt()).isNull();
    assertThat(result.getStartedAt()).isNull();
    assertThat(result.getFinishedAt()).isNull();
    assertThat(result.getDetail()).isInstanceOf(List.class);
  }

  @Test
  void getStatusThrowsException() {
    // given
    when(loadVerkuendungImportProcessPort.loadVerkuendungImportProcess(any()))
      .thenReturn(Optional.empty());

    // when
    LoadNormendokumentationspacketProcessingStatusUseCase.Query query =
      new LoadNormendokumentationspacketProcessingStatusUseCase.Query(UUID.randomUUID());
    assertThatThrownBy(() -> verkuendungsImportService.getStatus(query))
      // then
      .isInstanceOf(StatusNotFoundException.class);
  }
}
