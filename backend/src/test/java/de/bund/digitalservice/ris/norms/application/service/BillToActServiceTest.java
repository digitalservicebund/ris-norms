package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

class BillToActServiceTest {

  final BillToActService underTest = new BillToActService();

  @Test
  void itChangesNothing() {
    // given
    final String xmlString = Fixtures.loadTextFromDisk(
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
    );

    // when
    String result = underTest.convert(XmlMapper.toDocument(xmlString));

    // then
    final Diff diff = DiffBuilder
      .compare(Input.from(xmlString))
      .withTest(Input.from(result))
      .build();
    assertThat(diff.hasDifferences()).isFalse();
  }

  @Test
  void convert() {
    // given
    final String xmlString = Fixtures.loadTextFromDisk("ReglungstextEntwurfsfassung.xml");
    final String expectedResult = Fixtures.loadTextFromDisk("ReglungstextVerkuendungsfassung.xml");

    // when
    String result = underTest.convert(XmlMapper.toDocument(xmlString));

    // then
    final Diff diff = DiffBuilder
      .compare(Input.from(expectedResult))
      .withTest(Input.from(result))
      .normalizeWhitespace()
      .withAttributeFilter(attribute ->
        !attribute.getName().equals("GUID") &&
        !attribute.getOwnerElement().getNodeName().equals("akn:FRBRalias")
      )
      .build();
    System.out.println(diff.fullDescription());
    assertThat(diff.hasDifferences()).isFalse();
  }
}
