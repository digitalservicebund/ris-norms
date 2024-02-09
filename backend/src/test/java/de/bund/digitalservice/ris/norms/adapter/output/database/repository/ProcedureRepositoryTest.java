package de.bund.digitalservice.ris.norms.adapter.output.database.repository;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.ProcedureDTO;
import de.bund.digitalservice.ris.norms.domain.value.ProcedureState;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProcedureRepositoryTest {
  @Autowired private ProcedureRepository procedureRepository;

  @Test
  void findAllOpenProcedures() {
    // Given
    final ProcedureDTO procedureOpen =
        saveOneProcedure("eli/bgbl-1/2024/123", ProcedureState.OPEN.name());
    saveOneProcedure("eli/bgbl-1/2024/456", ProcedureState.CLOSE.name());

    // when
    Collection<ProcedureDTO> found = procedureRepository.findAllUnclosedProcedures();

    // then
    assertThat(found.size()).isEqualTo(1);
    assertThat(found).contains(procedureOpen);
  }

  @Test
  void findAllInProgressProcedures() {
    // Given
    final ProcedureDTO procedureInProgress =
        saveOneProcedure("eli/bgbl-1/2024/123", ProcedureState.PROGRESS.name());
    saveOneProcedure("eli/bgbl-1/2024/456", ProcedureState.CLOSE.name());

    // when
    Collection<ProcedureDTO> found = procedureRepository.findAllUnclosedProcedures();

    // then
    assertThat(found.size()).isEqualTo(1);
    assertThat(found).contains(procedureInProgress);
  }

  @Test
  void rejectClosedOrInProgressProcedures() {
    // Given
    final ProcedureDTO procedureOpen =
        saveOneProcedure("eli/bgbl-1/2024/123", ProcedureState.CLOSE.name());
    saveOneProcedure("eli/bgbl-1/2024/456", ProcedureState.CLOSE.name());

    // when
    Collection<ProcedureDTO> found = procedureRepository.findAllUnclosedProcedures();

    // then
    assertThat(found.size()).isEqualTo(0);
  }

  private ProcedureDTO saveOneProcedure(String eli, String state) {
    final ProcedureDTO procedure =
        ProcedureDTO.builder()
            .state(state)
            .eli(eli)
            .printAnnouncementGazette("bgbl-1")
            .printAnnouncementYear("2024")
            .printAnnouncementPage("123")
            .build();
    procedureRepository.save(procedure);
    return procedure;
  }
}
