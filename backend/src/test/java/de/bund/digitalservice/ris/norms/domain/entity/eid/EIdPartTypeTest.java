package de.bund.digitalservice.ris.norms.domain.entity.eid;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EIdPartTypeTest {

  @ParameterizedTest
  @CsvSource(
    {
      "<akn:mod xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.8.1/\" />,ändbefehl",
      "<akn:list xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.8.1/\" />,untergl",
      "<akn:li xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.8.1/\" />,listenelem",
      "<akn:point xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.8.1/\" />,listenelem",
      "<akn:passiveModifications xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.8.1/\" />,pasmod",
      "<akn:article xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.8.1/\" />,art",
    }
  )
  void itShouldGetTheCorrectEIdPartTypeForAknElements(String xml, String eIdPartName) {
    var node = XmlMapper.toElement(xml);
    // when
    var partType = EIdPartType.forAknElement(node);
    // then
    assertThat(partType).hasValueSatisfying(part ->
      assertThat(part.getName()).isEqualTo(eIdPartName)
    );
  }
}
