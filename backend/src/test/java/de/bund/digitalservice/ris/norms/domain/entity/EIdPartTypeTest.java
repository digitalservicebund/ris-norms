package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EIdPartTypeTest {

  @ParameterizedTest
  @CsvSource(
    {
      "<akn:mod xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.7/\" />,ändbefehl",
      "<akn:list xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.7/\" />,untergl",
      "<akn:li xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.7/\" />,listenelem",
      "<akn:point xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.7/\" />,listenelem",
      "<akn:passiveModifications xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.7/\" />,pasmod",
        "<akn:article xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.7/\" />,art",
    }
  )
  void itShouldGetTheCorrectEIdPartTypeForAknElements(String xml, String eIdPartName) {
    var node = XmlMapper.toNode(xml);
    // when
    var partType = EIdPartType.forAknElement(node);
    // then
    assertThat(partType)
      .hasValueSatisfying(part -> assertThat(part.getName()).isEqualTo(eIdPartName));
  }
}
