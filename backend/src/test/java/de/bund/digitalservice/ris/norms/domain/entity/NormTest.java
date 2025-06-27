package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentWorkEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import java.util.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class NormTest {

  @Test
  void getExpressionEli() {
    // given
    Norm norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
    NormExpressionEli expectedEli = NormExpressionEli.fromString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"
    );

    // when
    var actualEli = norm.getExpressionEli();

    // then
    assertThat(actualEli).isEqualTo(expectedEli);
  }

  @Test
  void getWorkEli() {
    // given
    Norm norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");

    // when
    var actualEli = norm.getWorkEli();

    // then
    assertThat(actualEli).isEqualTo(NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"));
  }

  @Test
  void getManifestationEli() {
    // given
    Norm norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
    NormManifestationEli expectedEli = NormManifestationEli.fromString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
    );

    // when
    var actualEli = norm.getManifestationEli();

    // then
    assertThat(actualEli).isEqualTo(expectedEli);
  }

  @Test
  void getManifestationEliNoRegelungsText() {
    // given
    Norm norm = Norm.builder()
      .dokumente(
        Set.of(
          Fixtures.loadOffeneStrukturFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/anlage-regelungstext-1.xml"
          )
        )
      )
      .build();
    NormManifestationEli expectedEli = NormManifestationEli.fromString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
    );

    // when
    var actualEli = norm.getManifestationEli();

    // then
    assertThat(actualEli).isEqualTo(expectedEli);
  }

  @Test
  void getGuid() {
    // given
    var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");

    // when
    UUID actualGuid = norm.getGuid();

    // then
    assertThat(actualGuid).isEqualTo(UUID.fromString("d04791fc-dcdc-47e6-aefb-bc2f7aaee151"));
  }

  @Test
  void getTitle() {
    // given
    var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
    String expectedTitle = "Gesetz zur Regelung des öffentlichen Vereinsrechts";

    // when
    String actualTitle = norm.getTitle().get();

    // then
    assertThat(actualTitle).contains(expectedTitle);
  }

  @Test
  void getShortTitle() {
    // given
    var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");

    // when
    var shortTitle = norm.getShortTitle();

    // then
    assertThat(shortTitle).contains("Vereinsgesetz");
  }

  @Test
  void equalsShouldEqualWithSameXml() {
    // given
    var norm1 = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
    var norm2 = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");

    // then
    assertThat(norm1).isEqualTo(norm2);
  }

  @Test
  void equalsShouldNotEqualWithDifferentXml() {
    // given
    var norm1 = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
    var norm2 = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu/2017-03-15");

    // then
    assertThat(norm1).isNotEqualTo(norm2);
  }

  @Test
  void hashCodeShouldBeTheSameWithSameXml() {
    // given
    var norm1 = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
    var norm2 = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");

    // then
    assertThat(norm1.hashCode()).hasSameHashCodeAs(norm2.hashCode());
  }

  @Test
  void shouldBeUnpublishedByDefaultWhenCreatedUsingBuild() {
    // Given
    final Norm norm = Norm.builder()
      .dokumente(
        Set.of(
          new Regelungstext(
            XmlMapper.toDocument(
              Fixtures.loadTextFromDisk(
                "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
              )
            )
          )
        )
      )
      .build();

    // Then
    assertThat(norm.getPublishState()).isEqualTo(NormPublishState.UNPUBLISHED);
  }

  @Test
  void hashCodeShouldBeDifferentWithDifferentXml() {
    // given
    var norm1 = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
    var norm2 = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu/2017-03-15");

    // then
    assertThat(norm1.hashCode()).isNotEqualTo(norm2.hashCode());
  }

  @Nested
  class getRegelungstextByEli {

    @Test
    void itReturnsRegelungstext1ByExpressionEli() {
      // Given
      Regelungstext regelungstext1 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
      );
      Regelungstext regelungstext2 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-2.xml"
      );
      Norm norm = new Norm(NormPublishState.UNPUBLISHED, Set.of(regelungstext1, regelungstext2));

      // When
      var loadedRegelungstext = norm.getRegelungstextByEli(regelungstext1.getExpressionEli());

      // Then
      assertThat(loadedRegelungstext).contains(regelungstext1);
    }

    @Test
    void itReturnsRegelungstext2ByManifestationEli() {
      // Given
      Regelungstext regelungstext1 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
      );
      Regelungstext regelungstext2 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-2.xml"
      );
      Norm norm = new Norm(NormPublishState.UNPUBLISHED, Set.of(regelungstext1, regelungstext2));

      // When
      var loadedRegelungstext = norm.getRegelungstextByEli(regelungstext2.getManifestationEli());

      // Then
      assertThat(loadedRegelungstext).contains(regelungstext2);
    }

    @Test
    void itReturnsRegelungstext1ByWorkEli() {
      // Given
      Regelungstext regelungstext1 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
      );
      Regelungstext regelungstext2 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-2.xml"
      );
      Norm norm = new Norm(NormPublishState.UNPUBLISHED, Set.of(regelungstext1, regelungstext2));

      // When
      var loadedRegelungstext = norm.getRegelungstextByEli(regelungstext1.getWorkEli());

      // Then
      assertThat(loadedRegelungstext).contains(regelungstext1);
    }

    @Test
    void itReturnsEmptyForUnknownEli() {
      // Given
      Regelungstext regelungstext1 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
      );
      Regelungstext regelungstext2 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-2.xml"
      );
      Norm norm = new Norm(NormPublishState.UNPUBLISHED, Set.of(regelungstext1, regelungstext2));

      // When
      var loadedRegelungstext = norm.getRegelungstextByEli(
        DokumentWorkEli.fromString("eli/bund/bgbl-1/2022/23/regelungstext-verkuendung-3")
      );

      // Then
      assertThat(loadedRegelungstext).isEmpty();
    }
  }

  @Nested
  class isInkraftAt {

    @ParameterizedTest
    @CsvSource(
      {
        "1962-01-01,false", // Before inkrafttreteDatum
        "1964-08-05,true", // Exactly on inkrafttreteDatum
        "2020-01-01,true", // Well after inkrafttreteDatum
        "2030-01-01,true", // Exactly on ausserkrafttreteDatum
        "2030-01-02,false", // After ausserkrafttreteDatum
      }
    )
    void itEvaluatesCorrectlyBasedOnDate(String inputDate, boolean expectedResult) {
      Norm norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
      );

      assertThat(norm.isInkraftAt(LocalDate.parse(inputDate))).isEqualTo(expectedResult);
    }
  }

  @Nested
  class getOffenestrukturen {

    @Test
    void itReturnsOffenestrukturen() {
      // Given
      Regelungstext regelungstext1 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
      );
      OffeneStruktur offeneStruktur1 = Fixtures.loadOffeneStrukturFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/anlage-regelungstext-1.xml"
      );
      Norm norm = new Norm(NormPublishState.UNPUBLISHED, Set.of(regelungstext1, offeneStruktur1));

      // When
      var offenestrukturen = norm.getOffenestrukturen();

      // Then
      assertThat(offenestrukturen).hasSize(1).contains(offeneStruktur1);
    }
  }

  @Nested
  class isGegenstandlos {

    @Test
    void itReturnsFalseForNonGegenstandsloseNorm() {
      Norm norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
      );

      assertThat(norm.isGegenstandlos()).isFalse();
    }

    @Test
    void itReturnsTrueForGegenstandsloseNorm() {
      Norm norm = Fixtures.loadNormFromDisk(NormTest.class, "vereinsgesetz-gegenstandlos");

      assertThat(norm.isGegenstandlos()).isTrue();
    }
  }

  @Nested
  class setGegenstandlos {

    @Test
    void setGegenstandlosCreate() {
      Norm norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
      );

      assertThat(norm.isGegenstandlos()).isFalse();

      norm.setGegenstandlos("2010-01-01");

      assertThat(norm.isGegenstandlos()).isTrue();

      final Optional<Gegenstandlos> gegenstandlos = norm
        .getRechtsetzungsdokument()
        .getMeta()
        .getOrCreateProprietary()
        .getGegenstandlos();
      assertThat(gegenstandlos)
        .isPresent()
        .get()
        .extracting(Gegenstandlos::getSinceDate)
        .isEqualTo(LocalDate.parse("2010-01-01"));
    }

    @Test
    void getGegenstandlosModify() {
      Norm norm = Fixtures.loadNormFromDisk(NormTest.class, "vereinsgesetz-gegenstandlos");
      final Optional<Gegenstandlos> gegenstandlos = norm
        .getRechtsetzungsdokument()
        .getMeta()
        .getOrCreateProprietary()
        .getGegenstandlos();
      assertThat(gegenstandlos)
        .isPresent()
        .get()
        .extracting(Gegenstandlos::getSinceDate)
        .isNotEqualTo(LocalDate.parse("2010-01-01"));

      assertThat(norm.isGegenstandlos()).isTrue();

      norm.setGegenstandlos("2010-01-01");

      assertThat(gegenstandlos)
        .isPresent()
        .get()
        .extracting(Gegenstandlos::getSinceDate)
        .isEqualTo(LocalDate.parse("2010-01-01"));
    }
  }
}
