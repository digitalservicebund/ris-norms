package de.bund.digitalservice.ris.norms.integration.adapter.output.database;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.ProcedureMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.ProcedureRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.service.DBService;
import de.bund.digitalservice.ris.norms.application.port.output.LoadProcedurePort;
import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import de.bund.digitalservice.ris.norms.domain.value.ProcedureState;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DBServiceIntegrationTest extends BaseIntegrationTest {

  @Autowired private DBService dbService;

  @Autowired private ProcedureRepository procedureRepository;

  @AfterEach
  void cleanUp() {
    procedureRepository.deleteAll();
  }

  @Test
  void itFindsProcudeOnDB() {
    // Given
    final String eli = "eli/bgbl-1/2024/123";
    final Procedure procedure =
        Procedure.builder()
            .state(ProcedureState.OPEN)
            .eli(eli)
            .printAnnouncementGazette("bgbl-1")
            .printAnnouncementYear("2024")
            .printAnnouncementPage("123")
            .build();
    procedureRepository.save(ProcedureMapper.mapToDto(procedure));

    // When
    final Optional<Procedure> procedureLoaded =
        dbService.loadProcedureByEli(new LoadProcedurePort.Command(eli));

    // Then
    assertThat(procedureLoaded).isPresent().contains(procedure);
  }

  @Test
  void itDoesNotFindProcedureOnDb() {
    // Given
    final String eli = "eli/bgbl-1/2024/123";

    // When
    final Optional<Procedure> procedureLoaded =
        dbService.loadProcedureByEli(new LoadProcedurePort.Command(eli));

    // Then
    assertThat(procedureLoaded).isNotPresent();
  }

  @Test
  void itLoadsAllProceduresFromDB() {
    // Given
    final Procedure procedure1 =
        Procedure.builder()
            .state(ProcedureState.OPEN)
            .eli("eli/bgbl-1/2024/123")
            .printAnnouncementGazette("bgbl-1")
            .printAnnouncementYear("2024")
            .printAnnouncementPage("123")
            .build();
    final Procedure procedure2 =
        Procedure.builder()
            .state(ProcedureState.OPEN)
            .eli("eli/bgbl-2/2025/456")
            .printAnnouncementGazette("bgbl-2")
            .printAnnouncementYear("2025")
            .printAnnouncementPage("456")
            .build();
    procedureRepository.saveAll(
        List.of(ProcedureMapper.mapToDto(procedure1), ProcedureMapper.mapToDto(procedure2)));

    // When
    List<Procedure> proceduresLoaded = dbService.loadAllProcedures();

    // Then
    assertThat(proceduresLoaded).hasSize(3);
    assertThat(proceduresLoaded)
        .extracting(Procedure::getEli)
        .containsExactlyInAnyOrder(
            "eli/bund/bgbl-1/2024/123", procedure1.getEli(), procedure2.getEli());
  }
}
