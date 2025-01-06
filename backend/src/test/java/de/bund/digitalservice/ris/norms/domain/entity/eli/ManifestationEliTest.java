package de.bund.digitalservice.ris.norms.domain.entity.eli;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ManifestationEliTest {

  @Nested
  class hasPointInTimeManifestation {

    @Test
    void itShouldBeTrueIfPointInTimeManifestationExists() {
      var eli = ManifestationEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/2021-03-03/regelungstext-1.xml"
      );
      assertThat(eli.hasPointInTimeManifestation()).isTrue();
    }

    @Test
    void itShouldBeFalseIfPointInTimeManifestationDoesNotExists() {
      var eli = ManifestationEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1.xml"
      );
      assertThat(eli.hasPointInTimeManifestation()).isFalse();
    }
  }

  @Nested
  class toString {

    @Test
    void itShouldBeCorrectWithPointInTimeManifestation() {
      var eli = ManifestationEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/2021-03-03/regelungstext-1.xml"
      );
      assertThat(eli)
        .hasToString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/2021-03-03/regelungstext-1.xml");
    }

    @Test
    void itShouldBeCorrectWithoutPointInTimeManifestation() {
      var eli = ManifestationEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1.xml"
      );
      assertThat(eli).hasToString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1.xml");
    }
  }

  @Nested
  class toUri {

    @Test
    void itShouldBeCorrectWithPointInTimeManifestation() {
      var eli = ManifestationEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/2021-03-03/regelungstext-1.xml"
      );
      assertThat(eli.toUri())
        .hasToString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/2021-03-03/regelungstext-1.xml");
    }

    @Test
    void itShouldBeCorrectWithoutPointInTimeManifestation() {
      var eli = ManifestationEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1.xml"
      );
      assertThat(eli.toUri())
        .hasToString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1.xml");
    }
  }

  @Nested
  class fromString {

    @Test
    void itShouldBeCorrectWithPointInTimeManifestation() {
      var eli = ManifestationEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/2021-03-03/regelungstext-1.xml"
      );
      assertThat(eli.getAgent()).isEqualTo("bgbl-1");
      assertThat(eli.getYear()).isEqualTo("2021");
      assertThat(eli.getNaturalIdentifier()).isEqualTo("s4");
      assertThat(eli.getPointInTime()).isEqualTo("2021-03-01");
      assertThat(eli.getVersion()).isEqualTo(1);
      assertThat(eli.getLanguage()).isEqualTo("deu");
      assertThat(eli.getPointInTimeManifestation()).isEqualTo("2021-03-03");
      assertThat(eli.getSubtype()).isEqualTo("regelungstext-1");
      assertThat(eli.getFormat()).isEqualTo("xml");
    }

    @Test
    void itShouldBeCorrectWithoutPointInTimeManifestation() {
      var eli = ManifestationEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1.xml"
      );
      assertThat(eli.getAgent()).isEqualTo("bgbl-1");
      assertThat(eli.getYear()).isEqualTo("2021");
      assertThat(eli.getNaturalIdentifier()).isEqualTo("s4");
      assertThat(eli.getPointInTime()).isEqualTo("2021-03-01");
      assertThat(eli.getVersion()).isEqualTo(1);
      assertThat(eli.getLanguage()).isEqualTo("deu");
      assertThat(eli.getPointInTimeManifestation()).isNull();
      assertThat(eli.getSubtype()).isEqualTo("regelungstext-1");
      assertThat(eli.getFormat()).isEqualTo("xml");
    }
  }

  @Nested
  class withoutPointInTimeManifestation {

    @Test
    void itShouldRemovePointInTimeManifestation() {
      var eli = ManifestationEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/2021-03-03/regelungstext-1.xml"
      );
      assertThat(eli.withoutPointInTimeManifestation())
        .hasToString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1.xml");
    }
  }

  @Nested
  class asExpressionEli {

    @Test
    void itShouldConvertToAExpressionEli() {
      var eli = ManifestationEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/2021-03-03/regelungstext-1.xml"
      );
      assertThat(eli.asExpressionEli())
        .hasToString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1");
    }
  }

  @Nested
  class asWorkEli {

    @Test
    void itShouldConvertToAExpressionEli() {
      var eli = ManifestationEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/2021-03-03/regelungstext-1.xml"
      );
      assertThat(eli.asWorkEli()).hasToString("eli/bund/bgbl-1/2021/s4/regelungstext-1");
    }
  }

  @Nested
  class fromExpressionEli {

    @Test
    void itShouldCreateFromExpressionEliAndPointInTimeManifestation() {
      var eli = ManifestationEli.fromExpressionEli(
        ExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1"),
        LocalDate.parse("2022-01-01"),
        "xml"
      );
      assertThat(eli)
        .hasToString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/2022-01-01/regelungstext-1.xml");
    }
  }
}
