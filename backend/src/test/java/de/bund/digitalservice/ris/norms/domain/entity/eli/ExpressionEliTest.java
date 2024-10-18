package de.bund.digitalservice.ris.norms.domain.entity.eli;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ExpressionEliTest {

  @Test
  void fromString() {
    var eli = ExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1");
    assertThat(eli.getAgent()).isEqualTo("bgbl-1");
    assertThat(eli.getYear()).isEqualTo("2021");
    assertThat(eli.getNaturalIdentifier()).isEqualTo("s4");
    assertThat(eli.getPointInTime()).isEqualTo("2021-03-01");
    assertThat(eli.getVersion()).isEqualTo(1);
    assertThat(eli.getLanguage()).isEqualTo("deu");
    assertThat(eli.getSubtype()).isEqualTo("regelungstext-1");
  }

  @Test
  void fromWorkEli() {
    var eli = ExpressionEli.fromWorkEli(
      WorkEli.fromString("eli/bund/bgbl-1/2021/s4/regelungstext-1"),
      LocalDate.parse("2021-03-01"),
      1,
      "deu"
    );
    assertThat(eli).hasToString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1");
  }

  @Test
  void testToString() {
    var eli = ExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1");
    assertThat(eli).hasToString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1");
  }

  @Test
  void toUri() {
    var eli = ExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1");
    assertThat(eli.toUri()).hasToString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu");
  }

  @Test
  void asWorkEli() {
    var eli = ExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1");
    assertThat(eli.asWorkEli()).hasToString("eli/bund/bgbl-1/2021/s4/regelungstext-1");
  }
}
