package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.bund.digitalservice.ris.norms.application.port.output.CheckNormExistencePort;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class EliServiceTest {

  private final CheckNormExistencePort checkNormExistencePort = mock(CheckNormExistencePort.class);
  private final EliService eliService = new EliService(checkNormExistencePort);

  @Test
  void findNextExpressionEli() {
    when(checkNormExistencePort.checkNormExistence(any())).thenReturn(false);

    var eli = eliService.findNextExpressionEli(
      NormWorkEli.fromString("eli/bund/bgbl-1/1990/s2954"),
      LocalDate.parse("2025-01-01"),
      "deu"
    );

    assertThat(eli).hasToString("eli/bund/bgbl-1/1990/s2954/2025-01-01/1/deu");
  }

  @Test
  void findNextExpressionEliVersion1AlreadyInUse() {
    when(checkNormExistencePort.checkNormExistence(any())).thenReturn(true).thenReturn(false);

    var eli = eliService.findNextExpressionEli(
      NormWorkEli.fromString("eli/bund/bgbl-1/1990/s2954"),
      LocalDate.parse("2025-01-01"),
      "deu"
    );

    assertThat(eli).hasToString("eli/bund/bgbl-1/1990/s2954/2025-01-01/2/deu");
  }
}
