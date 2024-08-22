package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CharacterRangeTest {

  @Nested
  class isEndGreaterEqualsStart {
    @Test
    void itShouldDetectStartIsGreaterThanEnd() {
      // given
      var characterRange = new CharacterRange("200-100");

      // when // then
      assertThat(characterRange.isEndGreaterStart()).isFalse();
    }

    @Test
    void itShouldDetectValidRange() {
      // given
      var characterRange = new CharacterRange("100-101");

      // when // then
      assertThat(characterRange.isEndGreaterStart()).isTrue();
    }
  }

  @Nested
  class getStart {
    @Test
    void itShouldReturnStart() {
      // given //when
      var characterRangeStart = new CharacterRange("100-200").getStart();

      // then
      assertThat(characterRangeStart).isEqualTo(100);
    }

    @Test
    void itShouldThrowAnError() {
      // given //when
      boolean isValid = new CharacterRange("-200").isValidCharacterRange();

      // then
      assertThat(isValid).isFalse();
    }
  }

  @Nested
  class getEnd {
    @Test
    void itShouldReturnEnd() {
      // given //when
      var characterRangeEnd = new CharacterRange("100-200").getEnd();

      // then
      assertThat(characterRangeEnd).isEqualTo(200);
    }

    @Test
    void itShouldThrowAnError() {
      // given //when
      boolean isValid = new CharacterRange("100-").isValidCharacterRange();

      // then
      assertThat(isValid).isFalse();
    }

    @Test
    void itShouldDetectInvalidRangesOne() {
      // given //when
      boolean isValid = new CharacterRange("1").isValidCharacterRange();

      // then
      assertThat(isValid).isFalse();
    }

    @Test
    void itShouldDetectValidRangeLargerNumbers() {
      // given //when
      boolean isValid = new CharacterRange("2500-3000").isValidCharacterRange();

      // then
      assertThat(isValid).isTrue();
    }
  }

  @Nested
  class findTextInNode {
    @Test
    void itShouldReturnTextOfASimpleTextNode() {
      // given //when
      var textInRange =
          new CharacterRange("9-13")
              .findTextInNode(
                  XmlMapper.toNode(
                      """
                          <akn:p xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="text-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2">A simple text of which we want to find a part.</akn:p>
                          """));

      // then
      assertThat(textInRange).isEqualTo("text");
    }

    @Test
    void itShouldReturnTextOfANestedNode() {
      // given //when
      var textInRange =
          new CharacterRange("9-13")
              .findTextInNode(
                  XmlMapper.toNode(
                      """
                          <akn:p xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="text-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2">A simple <akn:ref eId="text-1_ref-1">text</akn:ref> of which we want to find a part.</akn:p>
                          """));

      // then
      assertThat(textInRange).isEqualTo("text");
    }

    @Test
    void itShouldReturnTextSpanningMultipleNodes() {
      // given //when
      var textInRange =
          new CharacterRange("2-16")
              .findTextInNode(
                  XmlMapper.toNode(
                      """
                          <akn:p xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="text-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2">A simple <akn:ref eId="text-1_ref-1">text</akn:ref> of which we want to find a part.</akn:p>
                          """));

      // then
      assertThat(textInRange).isEqualTo("simple text of");
    }
  }

  @Nested
  class getNodesInRange {
    @Test
    void itShouldFindRangeInAText() {
      // given //when
      var node =
          XmlMapper.toNode(
              """
                  <akn:p xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="text-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179">A simple text of which we want to find a part.</akn:p>
                  """);
      var nodesInRange = new CharacterRange("9-13").getNodesInRange(node);

      // then
      assertThat(nodesInRange).hasSize(1);
      assertThat(nodesInRange.getFirst().getTextContent()).isEqualTo("text");

      nodesInRange.getFirst().setTextContent("foo");

      assertThat(XmlMapper.toString(node))
          .isEqualToIgnoringWhitespace(
              """
                  <?xml version="1.0" encoding="UTF-8"?><akn:p xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" eId="text-1">A simple foo of which we want to find a part.</akn:p>
                  """);
    }

    @Test
    void itShouldFindRangeInAValidatedNormIgnoringIndentation() {
      // given //when
      var norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml", true);

      var n2 =
          NodeParser.getNodeFromExpression(
              "//*[@eId='hauptteil-1_para-20_abs-1_untergl-1_listenelem-1_inhalt-1']",
              norm.getDocument());

      var nodesInRange = new CharacterRange("101-118").getNodesInRange(n2.get());

      // then
      assertThat(nodesInRange).hasSize(1);
      var firstNode = nodesInRange.getFirst();

      assertThat(firstNode.getTextContent()).isEqualTo("organisatorischen");
    }

    @Test
    void itShouldFindRangeInANestedNode() {
      // given //when
      var node =
          XmlMapper.toNode(
              """
                  <akn:p xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="text-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179">A simple <akn:ref eId="text-1_ref-1">text</akn:ref> of which we want to find a part.</akn:p>
                  """);
      var nodesInRange = new CharacterRange("9-13").getNodesInRange(node);

      // then
      assertThat(nodesInRange).hasSize(1);
      var firstNode = nodesInRange.getFirst();

      assertThat(firstNode.getTextContent()).isEqualTo("text");
      firstNode
          .getParentNode()
          .replaceChild(firstNode.getOwnerDocument().createTextNode("foo"), firstNode);

      assertThat(XmlMapper.toString(node))
          .isEqualToIgnoringWhitespace(
              """
                  <?xml version="1.0" encoding="UTF-8"?><akn:p xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" eId="text-1">A simple foo of which we want to find a part.</akn:p>
                  """);
    }

    @Test
    void itShouldFindRangeSpanningMultipleNodes() {
      // given //when
      var document =
          XmlMapper.toNode(
              """
                  <akn:p xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="text-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179">A simple <akn:ref eId="text-1_ref-1">text</akn:ref> of which we want to find a part.</akn:p>
                  """);
      var nodesInRange = new CharacterRange("2-16").getNodesInRange(document);

      // then
      assertThat(nodesInRange).hasSize(3);
      assertThat(nodesInRange.getFirst().getTextContent()).isEqualTo("simple ");
      assertThat(nodesInRange.get(1).getTextContent()).isEqualTo("text");
      assertThat(nodesInRange.get(2).getTextContent()).isEqualTo(" of");

      var firstNode = nodesInRange.getFirst();
      firstNode
          .getParentNode()
          .insertBefore(firstNode.getOwnerDocument().createTextNode("new text"), firstNode);
      nodesInRange.forEach(node -> node.getParentNode().removeChild(node));

      assertThat(XmlMapper.toString(document))
          .isEqualToIgnoringWhitespace(
              """
                  <?xml version="1.0" encoding="UTF-8"?><akn:p xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" eId="text-1">A new text which we want to find a part.</akn:p>
                  """);
    }
  }

  @Nested
  class Builder {
    @Test
    void itShouldCreateCharacterCount() {
      // given // when
      var characterRange = new CharacterRange.Builder().start(100).end(200).build();

      // then
      assertThat(characterRange).hasToString("100-200");
    }
  }
}
