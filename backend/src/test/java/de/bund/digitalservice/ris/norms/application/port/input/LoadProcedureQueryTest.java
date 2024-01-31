package de.bund.digitalservice.ris.norms.application.port.input;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class LoadProcedureQueryTest {

  @Test
  void canCreateQueryWithUUID() {
    // Given
    final UUID guid = UUID.randomUUID();

    // When
    final LoadProcedureUseCase.Query query = new LoadProcedureUseCase.Query(guid);

    // Then
    assertThat(query.uuid()).isEqualTo(guid);
  }
}
