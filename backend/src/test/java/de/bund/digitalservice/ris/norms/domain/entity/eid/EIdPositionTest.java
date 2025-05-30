package de.bund.digitalservice.ris.norms.domain.entity.eid;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class EIdPositionTest {

  @Nested
  class findEIdPosition {

    @Test
    void article() {
      var node = XmlMapper.toElement(
        """
        <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8/">
            <akn:num>Artikel 2</akn:num>
        </akn:article>
        """
      );

      assertThat(EIdPosition.findEIdPosition(node, EIdPartType.ART)).isNotNull().hasToString("z2");
    }

    @Test
    void paragraph() {
      var node = XmlMapper.toElement(
        """
        <akn:paragraph xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8/">
            <akn:num>(2)</akn:num>
        </akn:paragraph>
        """
      );

      assertThat(EIdPosition.findEIdPosition(node, EIdPartType.ABS)).isNotNull().hasToString("z2");
    }

    @Test
    void articleWithRefersTo() {
      var node = XmlMapper.toElement(
        """
        <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8/">
            <akn:num refersTo="ordinale-zaehlung-eid" />
        </akn:article>
        """
      );
      assertThat(EIdPosition.findEIdPosition(node, EIdPartType.ART)).isNotNull().hasToString("n1");
    }

    @Test
    void paragraphWithRefersTo() {
      var node = XmlMapper.toElement(
        """
        <akn:paragraph xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8/">
            <akn:num refersTo="ordinale-zaehlung-eid" />
        </akn:paragraph>
        """
      );
      assertThat(EIdPosition.findEIdPosition(node, EIdPartType.ABS)).isNotNull().hasToString("n1");
    }

    @Test
    void ordinalEIdPosition() {
      var node = XmlMapper.toElement(
        "<akn:mod xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.8/\" />"
      );

      assertThat(EIdPosition.findEIdPosition(node, EIdPartType.AENDBEFEHL))
        .isNotNull()
        .hasToString("n1");
    }
  }
}
