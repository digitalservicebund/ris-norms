package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
import org.junit.jupiter.api.Test;

class RegelungstextTest {

  @Test
  void getShortTitle() {
    // given
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
    );

    // when
    final var shortTitle = regelungstext.getShortTitle();

    // then
    assertThat(shortTitle).contains("Vereinsgesetz");
  }

  @Test
  void getMeta() {
    // given
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
    );

    // when
    final Meta meta = regelungstext.getMeta();

    // then
    assertThat(meta).isNotNull();
  }

  @Test
  void getMetaNotFound() {
    // given
    final String xml = """
      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.2/">
         <akn:act name="regelungstext">
         </akn:act>
      </akn:akomaNtoso>
      """.strip();

    final var regelungstext = new Regelungstext(XmlMapper.toDocument(xml));

    assertThatThrownBy(regelungstext::getMeta).isInstanceOf(MandatoryNodeNotFoundException.class);
  }

  @Test
  void equalsShouldEqualWithSameXml() {
    // given
    final var regelungstext1 = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
    );
    final var regelungstext2 = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
    );

    // then
    assertThat(regelungstext1).isEqualTo(regelungstext2);
  }

  @Test
  void equalsShouldNotEqualWithDifferentXml() {
    // given
    final var regelungstext1 = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
    );
    final var regelungstext2 = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu/2017-03-15/regelungstext-verkuendung-1.xml"
    );

    // then
    assertThat(regelungstext1).isNotEqualTo(regelungstext2);
  }

  @Test
  void hashCodeShouldBeTheSameWithSameXml() {
    // given
    final var regelungstext1 = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
    );
    final var regelungstext2 = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
    );

    // then
    assertThat(regelungstext1.hashCode()).hasSameHashCodeAs(regelungstext2.hashCode());
  }

  @Test
  void hashCodeShouldBeDifferentWithDifferentXml() {
    // given
    final var regelungstext1 = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
    );
    final var regelungstext2 = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu/2017-03-15/regelungstext-verkuendung-1.xml"
    );

    // then
    assertThat(regelungstext1.hashCode()).isNotEqualTo(regelungstext2.hashCode());
  }
}
