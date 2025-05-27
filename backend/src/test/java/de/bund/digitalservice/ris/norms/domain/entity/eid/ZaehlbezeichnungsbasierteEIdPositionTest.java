package de.bund.digitalservice.ris.norms.domain.entity.eid;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ZaehlbezeichnungsbasierteEIdPositionTest {

  @Nested
  class findEIdPosition {

    @Test
    void artikel() {
      var node = XmlMapper.toElement(
        """
        <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8/">
            <akn:num>Artikel 1</akn:num>
        </akn:article>
        """
      );

      assertThat(ZaehlbezeichnungsbasierteEIdPosition.findEIdPosition(node))
        .isNotNull()
        .hasToString("z1");
    }

    @Test
    void parenthesis() {
      var node = XmlMapper.toElement(
        """
        <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8/">
            <akn:num>(1)</akn:num>
        </akn:article>
        """
      );

      assertThat(ZaehlbezeichnungsbasierteEIdPosition.findEIdPosition(node))
        .isNotNull()
        .hasToString("z1");
    }

    @Test
    void emptyNum() {
      var node = XmlMapper.toElement(
        """
        <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8/">
            <akn:num></akn:num>
        </akn:article>
        """
      );

      assertThat(ZaehlbezeichnungsbasierteEIdPosition.findEIdPosition(node))
        .isNotNull()
        .hasToString("z");
    }

    @Test
    void missingNum() {
      var node = XmlMapper.toElement(
        """
        <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8/"></akn:article>
        """
      );

      assertThat(ZaehlbezeichnungsbasierteEIdPosition.findEIdPosition(node))
        .isNotNull()
        .hasToString("z");
    }

    @Test
    void complexContent() {
      var node = XmlMapper.toElement(
        """
        <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8/">
            <akn:num>Artik. 1-1?</akn:num>
        </akn:article>
        """
      );

      assertThat(ZaehlbezeichnungsbasierteEIdPosition.findEIdPosition(node))
        .isNotNull()
        .hasToString(
          // xpath based encoding, similar to the one done by the xsd-schema
          "z" +
          NodeParser.getValueFromExpression(
            "lower-case(encode-for-uri(translate(replace(replace(normalize-space(lower-case(./num/text())), '(§ )|(art\\. )|(art )|(artikel )', ''), '(\\()(\\d+[a-z]*)(\\))', '$2'), '-_.', '~~~')))",
            node
          ).get()
        )
        .hasToString("zartik~%201~1%3f");
    }
  }
}
