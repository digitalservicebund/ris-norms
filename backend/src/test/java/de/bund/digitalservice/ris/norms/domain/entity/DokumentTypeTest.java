package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class DokumentTypeTest {

  @Nested
  class getByOntologyUri {

    @Test
    void regelungstextVerkuendung() {
      assertThat(
        DokumentType.getByOntologyUri(
          "/akn/ontology/de/concept/documenttype/bund/regelungstext-verkuendung"
        )
      ).isEqualTo(DokumentType.REGELUNGSTEXT_VERKUENDUNG);
    }

    @ParameterizedTest
    @EnumSource(DokumentType.class)
    void findsItself(DokumentType dokumentType) {
      assertThat(DokumentType.getByOntologyUri(dokumentType.ontologyUri)).isEqualTo(dokumentType);
    }

    @Test
    void unknownDokumentTypeThrows() {
      assertThatThrownBy(() ->
        DokumentType.getByOntologyUri("/akn/ontology/de/concept/documenttype/unknown-dokumenttype")
      ).isInstanceOf(IllegalArgumentException.class);
    }
  }
}
