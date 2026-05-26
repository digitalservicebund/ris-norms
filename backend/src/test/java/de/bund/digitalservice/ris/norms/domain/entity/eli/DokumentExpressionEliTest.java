package de.bund.digitalservice.ris.norms.domain.entity.eli;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

class DokumentExpressionEliTest {

  @Test
  void asNormEli() {
    var eli = new DokumentExpressionEli(
      "bgbl-1",
      "2021",
      "s4",
      LocalDate.parse("2021-03-01", DateTimeFormatter.ISO_LOCAL_DATE),
      1,
      "deu",
      "regelungstext-verkuendung-1"
    );
    assertThat(eli.asNormEli()).hasToString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu");
  }
}
