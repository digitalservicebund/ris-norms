package de.bund.digitalservice.ris.norms.domain.entity.eli;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class NormExpressionEliTest {

  @Test
  void fromString() {
    var eli = NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu");
    assertThat(eli.getAgent()).isEqualTo("bgbl-1");
    assertThat(eli.getYear()).isEqualTo("2021");
    assertThat(eli.getNaturalIdentifier()).isEqualTo("s4");
    assertThat(eli.getPointInTime()).isEqualTo("2021-03-01");
    assertThat(eli.getVersion()).isEqualTo(1);
    assertThat(eli.getLanguage()).isEqualTo("deu");
  }

  @Test
  void fromWorkEli() {
    var eli = NormExpressionEli.fromWorkEli(
      NormWorkEli.fromString("eli/bund/bgbl-1/2021/s4"),
      LocalDate.parse("2021-03-01"),
      1,
      "deu"
    );
    assertThat(eli).hasToString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu");
  }

  @Test
  void testToString() {
    var eli = NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu");
    assertThat(eli).hasToString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu");
  }

  @Test
  void toUri() {
    var eli = NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu");
    assertThat(eli.toUri()).hasToString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu");
  }

  @Test
  void asWorkEli() {
    var eli = NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu");
    assertThat(eli.asWorkEli()).hasToString("eli/bund/bgbl-1/2021/s4");
  }
}
