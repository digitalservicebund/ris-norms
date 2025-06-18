package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.bund.digitalservice.ris.norms.application.port.output.LoadNormExpressionElisPort;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class EliServiceTest {

  private final LoadNormExpressionElisPort loadNormExpressionElisPort = mock(
    LoadNormExpressionElisPort.class
  );
  private final EliService eliService = new EliService(loadNormExpressionElisPort);

  @Test
  void findNextExpressionEli() {
    when(loadNormExpressionElisPort.loadNormExpressionElis(any())).thenReturn(
      List.of(NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1900-08-05/1/deu"))
    );

    var eli = eliService.findNextExpressionEli(
      NormWorkEli.fromString("eli/bund/bgbl-1/1990/s2954"),
      LocalDate.parse("2025-01-01"),
      "deu"
    );

    assertThat(eli).hasToString("eli/bund/bgbl-1/1990/s2954/2025-01-01/1/deu");
  }

  @Test
  void findNextExpressionEliVersionIfOneIsAlreadyInUse() {
    when(loadNormExpressionElisPort.loadNormExpressionElis(any())).thenReturn(
      List.of(
        NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1900-08-05/1/deu"),
        NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2025-01-01/4/deu")
      )
    );

    var eli = eliService.findNextExpressionEli(
      NormWorkEli.fromString("eli/bund/bgbl-1/1990/s2954"),
      LocalDate.parse("2025-01-01"),
      "deu"
    );

    assertThat(eli).hasToString("eli/bund/bgbl-1/1990/s2954/2025-01-01/5/deu");
  }
}
