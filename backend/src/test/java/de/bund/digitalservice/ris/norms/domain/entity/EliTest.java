package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EliTest {

  @ParameterizedTest
  @CsvSource(
    """
    eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1,eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1
    eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1.xml,eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1
    """
  )
  void itShouldCreateEliFromString(String eli, String expectedEli) {
    assertThat(new Eli(eli).getValue()).isEqualTo(expectedEli);
  }

  @Test
  void itShouldCreateEliFromParameters() {
    // Given // When
    var eli = new Eli("bgbl-1", "2017", "s419", "2017-03-15", "1", "deu", "regelungstext-1");

    // Then
    assertThat(eli.getValue())
      .isEqualTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1");
  }
}
