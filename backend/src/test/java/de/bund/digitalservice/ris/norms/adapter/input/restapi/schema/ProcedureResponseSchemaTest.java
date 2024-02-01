package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.Procedure;
import de.bund.digitalservice.ris.norms.domain.value.ProcedureState;
import org.junit.jupiter.api.Test;

class ProcedureResponseSchemaTest {

  @Test
  void canCreateSimpleProcedureResponseSchema() {

    final Procedure procedure =
        Procedure.builder()
            .state(ProcedureState.OPEN)
            .eli("someEli")
            .printAnnouncementGazette("someGazette")
            .printAnnouncementYear("2024")
            .printAnnouncementPage("page123")
            .build();

    final ProcedureResponseSchema procedureResponseSchema =
        ProcedureResponseSchema.fromUseCaseData(procedure);

    assertThat(procedureResponseSchema.getState()).isEqualTo("OPEN");
    assertThat(procedureResponseSchema.getEli()).isEqualTo("someEli");
    assertThat(procedureResponseSchema.getPrintAnnouncementGazette()).isEqualTo("someGazette");
    assertThat(procedureResponseSchema.getPrintAnnouncementYear()).isEqualTo("2024");
    assertThat(procedureResponseSchema.getPrintAnnouncementPage()).isEqualTo("page123");
  }
}
