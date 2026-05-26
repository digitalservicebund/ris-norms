package de.bund.digitalservice.ris.norms.domain.entity.eli;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NormManifestationEliTest {

  @Nested
  class hasPointInTimeManifestation {

    @Test
    void itShouldBeTrueIfPointInTimeManifestationExists() {
      var eli = NormManifestationEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/2021-03-03"
      );
      assertThat(eli.hasPointInTimeManifestation()).isTrue();
    }

    @Test
    void itShouldBeFalseIfPointInTimeManifestationDoesNotExists() {
      var eli = NormManifestationEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu");
      assertThat(eli.hasPointInTimeManifestation()).isFalse();
    }
  }

  @Nested
  class toString {

    @Test
    void itShouldBeCorrectWithPointInTimeManifestation() {
      var eli = NormManifestationEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/2021-03-03"
      );
      assertThat(eli).hasToString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/2021-03-03");
    }

    @Test
    void itShouldBeCorrectWithoutPointInTimeManifestation() {
      var eli = NormManifestationEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu");
      assertThat(eli).hasToString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu");
    }
  }

  @Nested
  class fromString {

    @Test
    void itShouldBeCorrectWithPointInTimeManifestation() {
      var eli = NormManifestationEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/2021-03-03"
      );
      assertThat(eli.getAgent()).isEqualTo("bgbl-1");
      assertThat(eli.getYear()).isEqualTo("2021");
      assertThat(eli.getNaturalIdentifier()).isEqualTo("s4");
      assertThat(eli.getPointInTime()).isEqualTo("2021-03-01");
      assertThat(eli.getVersion()).isEqualTo(1);
      assertThat(eli.getLanguage()).isEqualTo("deu");
      assertThat(eli.getPointInTimeManifestation()).isEqualTo("2021-03-03");
    }

    @Test
    void itShouldBeCorrectWithoutPointInTimeManifestation() {
      var eli = NormManifestationEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu");
      assertThat(eli.getAgent()).isEqualTo("bgbl-1");
      assertThat(eli.getYear()).isEqualTo("2021");
      assertThat(eli.getNaturalIdentifier()).isEqualTo("s4");
      assertThat(eli.getPointInTime()).isEqualTo("2021-03-01");
      assertThat(eli.getVersion()).isEqualTo(1);
      assertThat(eli.getLanguage()).isEqualTo("deu");
      assertThat(eli.getPointInTimeManifestation()).isNull();
    }
  }

  @Nested
  class asExpressionEli {

    @Test
    void itShouldConvertToAExpressionEli() {
      var eli = NormManifestationEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/2021-03-03"
      );
      assertThat(eli.asExpressionEli()).hasToString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu");
    }
  }
}
