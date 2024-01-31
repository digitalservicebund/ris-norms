package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.ProcedureDTO;
import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import de.bund.digitalservice.ris.norms.domain.value.ProcedureState;
import org.junit.jupiter.api.Test;

class ProcedureMapperTest {

  @Test
  void testMapToDomain() {
    // Given
    final ProcedureDTO procedureDTO = new ProcedureDTO();
    procedureDTO.setState("OPEN");
    procedureDTO.setEli("ELI");
    procedureDTO.setPrintAnnouncementGazette("GAZETTE");
    procedureDTO.setPrintAnnouncementYear("2022");
    procedureDTO.setPrintAnnouncementPage("PAGE");

    // When
    final Procedure resultProcedure = ProcedureMapper.mapToDomain(procedureDTO);

    // Then
    assertThat(resultProcedure.getState()).isEqualTo(ProcedureState.OPEN);
    assertThat(resultProcedure.getEli()).isEqualTo("ELI");
    assertThat(resultProcedure.getPrintAnnouncementGazette()).isEqualTo("GAZETTE");
    assertThat(resultProcedure.getPrintAnnouncementYear()).isEqualTo("2022");
    assertThat(resultProcedure.getPrintAnnouncementPage()).isEqualTo("PAGE");
  }
}
