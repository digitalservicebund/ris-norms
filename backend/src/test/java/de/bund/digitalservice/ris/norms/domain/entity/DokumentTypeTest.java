package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.bund.digitalservice.ris.norms.utils.exceptions.InvalidDokumentTypeException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.FieldSource;

class DokumentTypeTest {

  @ParameterizedTest
  @EnumSource(DokumentType.class)
  void ontologyUriEndsWithFilename(DokumentType dokumentType) {
    assertThat(dokumentType.ontologyUri).endsWith(dokumentType.fileName);
  }

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

  @Nested
  class getByFileName {

    @ParameterizedTest
    @FieldSource
    void findsDokumentTypes(String fileName, DokumentType dokumentType) {
      assertThat(DokumentType.getByFileName(fileName)).isEqualTo(dokumentType);
    }

    static List<Arguments> findsDokumentTypes = Arrays.asList(
      Arguments.arguments(
        "regelungstext-verkuendung-1.xml",
        DokumentType.REGELUNGSTEXT_VERKUENDUNG
      ),
      Arguments.arguments(
        "regelungstext-verkuendung-4.xml",
        DokumentType.REGELUNGSTEXT_VERKUENDUNG
      ),
      Arguments.arguments("anlage-regelungstext-4.xml", DokumentType.ANLAGE_REGELUNGSTEXT),
      Arguments.arguments("rechtsetzungsdokument-1.xml", DokumentType.RECHTSETZUNGSDOKUMENT),
      Arguments.arguments("bekanntmachungstext-1.xml", DokumentType.BEKANNTMACHUNGSTEXT)
    );

    @Test
    void unknownFilenameThrows() {
      assertThatThrownBy(() ->
        DokumentType.getByFileName("unknown-dokumenttype-2.xml")
      ).isInstanceOf(InvalidDokumentTypeException.class);
    }
  }
}
