package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class EliServiceTest {

  private final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  private final EliService eliService = new EliService(loadNormPort);

  @Test
  void findNextExpressionEli() {
    when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());

    var eli = eliService.findNextExpressionEli(
      NormWorkEli.fromString("eli/bund/bgbl-1/1990/s2954"),
      LocalDate.parse("2025-01-01"),
      "deu"
    );

    assertThat(eli).hasToString("eli/bund/bgbl-1/1990/s2954/2025-01-01/1/deu");
  }

  @Test
  void findNextExpressionEliVersion1AlreadyInUse() {
    when(loadNormPort.loadNorm(any()))
      .thenReturn(
        Optional.of(
          Fixtures.loadNormFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      )
      .thenReturn(Optional.empty());

    var eli = eliService.findNextExpressionEli(
      NormWorkEli.fromString("eli/bund/bgbl-1/1990/s2954"),
      LocalDate.parse("2025-01-01"),
      "deu"
    );

    assertThat(eli).hasToString("eli/bund/bgbl-1/1990/s2954/2025-01-01/2/deu");
  }
}
