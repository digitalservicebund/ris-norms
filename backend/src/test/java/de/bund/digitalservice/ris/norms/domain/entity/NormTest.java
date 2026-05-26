package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import java.util.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class NormTest {

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
}
