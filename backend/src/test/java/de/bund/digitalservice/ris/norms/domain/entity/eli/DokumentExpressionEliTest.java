package de.bund.digitalservice.ris.norms.domain.entity.eli;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class DokumentExpressionEliTest {

  @Test
  void fromString() {
    var eli = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1"
    );
    assertThat(eli.getAgent()).isEqualTo("bgbl-1");
    assertThat(eli.getYear()).isEqualTo("2021");
    assertThat(eli.getNaturalIdentifier()).isEqualTo("s4");
    assertThat(eli.getPointInTime()).isEqualTo("2021-03-01");
    assertThat(eli.getVersion()).isEqualTo(1);
    assertThat(eli.getLanguage()).isEqualTo("deu");
    assertThat(eli.getSubtype()).isEqualTo("regelungstext-verkuendung-1");
  }

  @Test
  void fromWorkEli() {
    var eli = DokumentExpressionEli.fromWorkEli(
      DokumentWorkEli.fromString("eli/bund/bgbl-1/2021/s4/regelungstext-verkuendung-1"),
      LocalDate.parse("2021-03-01"),
      1,
      "deu"
    );
    assertThat(eli).hasToString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1"
    );
  }

  @Test
  void fromNormEli() {
    var eli = DokumentExpressionEli.fromNormEli(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
      "regelungstext-verkuendung-1"
    );
    assertThat(eli).hasToString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1"
    );
  }

  @Test
  void testToString() {
    var eli = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1"
    );
    assertThat(eli).hasToString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1"
    );
  }

  @Test
  void toUri() {
    var eli = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1"
    );
    assertThat(eli.toUri()).hasToString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu");
  }

  @Test
  void asWorkEli() {
    var eli = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1"
    );
    assertThat(eli.asWorkEli()).hasToString("eli/bund/bgbl-1/2021/s4/regelungstext-verkuendung-1");
  }

  @Test
  void asNormEli() {
    var eli = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1"
    );
    assertThat(eli.asNormEli()).hasToString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu");
  }
}
