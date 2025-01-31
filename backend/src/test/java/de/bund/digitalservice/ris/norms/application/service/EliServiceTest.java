package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class EliServiceTest {

  private final LoadNormUseCase loadNormUseCase = mock(LoadNormUseCase.class);
  private final EliService eliService = new EliService(loadNormUseCase);

  @Test
  void findNextExpressionEli() {
    when(loadNormUseCase.loadNorm(any())).thenThrow(new NormNotFoundException(""));

    var eli = eliService.findNextExpressionEli(
      NormWorkEli.fromString("eli/bund/bgbl-1/1990/s2954"),
      LocalDate.parse("2025-01-01"),
      "deu"
    );

    assertThat(eli).hasToString("eli/bund/bgbl-1/1990/s2954/2025-01-01/1/deu");
  }

  @Test
  void findNextExpressionEliVersion1AlreadyInUse() {
    when(loadNormUseCase.loadNorm(any()))
      .thenReturn(Fixtures.loadNormFromDisk("SimpleNorm.xml"))
      .thenThrow(new NormNotFoundException(""));

    var eli = eliService.findNextExpressionEli(
      NormWorkEli.fromString("eli/bund/bgbl-1/1990/s2954"),
      LocalDate.parse("2025-01-01"),
      "deu"
    );

    assertThat(eli).hasToString("eli/bund/bgbl-1/1990/s2954/2025-01-01/2/deu");
  }
}
