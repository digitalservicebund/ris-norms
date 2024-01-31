package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.value.ProcedureState;
import org.junit.jupiter.api.Test;

class ProcedureTest {

  @Test
  void canCreateSimpleProcedure() {

    final Procedure procedure =
        Procedure.builder()
            .state(ProcedureState.OPEN)
            .eli("someEli")
            .printAnnouncementGazette("someGazette")
            .printAnnouncementYear("2024")
            .printAnnouncementPage("page123")
            .build();

    assertThat(procedure.getState()).isEqualTo(ProcedureState.OPEN);
    assertThat(procedure.getEli()).isEqualTo("someEli");
    assertThat(procedure.getPrintAnnouncementGazette()).isEqualTo("someGazette");
    assertThat(procedure.getPrintAnnouncementYear()).isEqualTo("2024");
    assertThat(procedure.getPrintAnnouncementPage()).isEqualTo("page123");
  }
}
