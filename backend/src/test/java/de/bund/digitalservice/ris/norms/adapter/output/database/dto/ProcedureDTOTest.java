package de.bund.digitalservice.ris.norms.adapter.output.database.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class ProcedureDTOTest {

  @Test
  void testProcedureDTO() {
    // Given
    final UUID id = UUID.randomUUID();
    final String state = "someState";
    final String eli = "someEli";
    final String printAnnouncementGazette = "someGazette";
    final String printAnnouncementYear = "2022";
    final String printAnnouncementPage = "page123";

    // When
    final ProcedureDTO procedureDTO =
        ProcedureDTO.builder()
            .id(id)
            .state(state)
            .eli(eli)
            .printAnnouncementGazette(printAnnouncementGazette)
            .printAnnouncementYear(printAnnouncementYear)
            .printAnnouncementPage(printAnnouncementPage)
            .build();

    // Then
    assertThat(procedureDTO.getId()).isEqualTo(id);
    assertThat(procedureDTO.getState()).isEqualTo(state);
    assertThat(procedureDTO.getEli()).isEqualTo(eli);
    assertThat(procedureDTO.getPrintAnnouncementGazette()).isEqualTo(printAnnouncementGazette);
    assertThat(procedureDTO.getPrintAnnouncementYear()).isEqualTo(printAnnouncementYear);
    assertThat(procedureDTO.getPrintAnnouncementPage()).isEqualTo(printAnnouncementPage);
  }
}
