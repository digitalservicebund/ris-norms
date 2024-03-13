package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.service.exceptions.ModificationException;
import de.bund.digitalservice.ris.norms.application.service.exceptions.XmlProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.w3c.dom.Node;

class TimeMachineTest {

  @Test
  public void allRelevantMethodsAreCalled() {
    // given
    final XmlDocumentExtractor xmlDocumentExtractor = mock(XmlDocumentExtractor.class);
    final TimeMachine timeMachine = new TimeMachine(xmlDocumentExtractor);

    String amendingLawString =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?><amendingLaw><akn:mod>old</akn:mod></amendingLaw>";
    String targetLawString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><targetLaw></targetLaw>";
    Node firstModificationNodeInAmendingLaw = mock(Node.class);
    Node targetNode = mock(Node.class);
    XmlDocumentExtractor.ReplacementPair replacementPair =
        new XmlDocumentExtractor.ReplacementPair("old", "new");

    when(xmlDocumentExtractor.getFirstModification(any()))
        .thenReturn(firstModificationNodeInAmendingLaw);
    when(xmlDocumentExtractor.getReplacementText(any())).thenReturn(replacementPair);
    when(xmlDocumentExtractor.findTargetLawNodeToBeModified(any(), any())).thenReturn(targetNode);
    when(targetNode.getTextContent()).thenReturn("old");

    // when
    timeMachine.apply(amendingLawString, targetLawString);

    // then
    verify(xmlDocumentExtractor, times(1)).getFirstModification(any());
    verify(xmlDocumentExtractor, times(1)).getReplacementText(any());
    verify(xmlDocumentExtractor, times(1)).findTargetLawNodeToBeModified(any(), any());
  }

  @Test
  public void oldTextIsReplacedByNewText() {
    // given
    final XmlDocumentExtractor xmlDocumentExtractor = new XmlDocumentExtractor();
    final TimeMachine timeMachine = new TimeMachine(xmlDocumentExtractor);

    String amendingLawString =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?><amendingLaw><akn:mod>In <akn:ref"
            + " href=\"eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml\">paragraph 2</akn:ref> "
            + "<akn:quotedText>old</akn:quotedText> with <akn:quotedText>new</akn:quotedText>.</akn:mod></amendingLaw>";
    String targetLawString =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?><targetLaw><akn:p eId=\"two\">old text</akn:p></targetLaw>";

    // when
    String result = timeMachine.apply(amendingLawString, targetLawString);

    // then
    assertThat(result)
        .isEqualTo(
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><targetLaw><akn:p eId=\"two\">new text</akn:p></targetLaw>");
  }

  @Test
  public void XmlProcessingExceptionIsThrownWhenAmendingLawXmlIsInvalid() {
    // given
    final XmlDocumentExtractor xmlDocumentExtractor = mock(XmlDocumentExtractor.class);
    final TimeMachine timeMachine = new TimeMachine(xmlDocumentExtractor);
    String amendingLawString = "SomeRandomText";
    String targetLawString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><targetLaw></targetLaw>";

    // when
    Throwable thrown = catchThrowable(() -> timeMachine.apply(amendingLawString, targetLawString));

    // then
    assertThat(thrown).isInstanceOf(XmlProcessingException.class);
  }

  @Test
  public void XmlProcessingExceptionIsThrownWhenTargetLawXmlIsInvalid() {
    // given
    final XmlDocumentExtractor xmlDocumentExtractor = mock(XmlDocumentExtractor.class);
    final TimeMachine timeMachine = new TimeMachine(xmlDocumentExtractor);
    String amendingLawString =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?><amendingLaw></amendingLaw>";

    String targetLawString = "randomString";

    // when
    Throwable thrown = catchThrowable(() -> timeMachine.apply(amendingLawString, targetLawString));

    // then
    assertThat(thrown).isInstanceOf(XmlProcessingException.class);
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        """
      only one quotedText

      <akn:mod>
        In <akn:ref
 href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml">paragraph
 2</akn:ref> replace with <akn:quotedText>new</akn:quotedText>.
      </akn:mod>
    """,
        """
      eId THREE not found in target law

      <akn:mod>
        In <akn:ref
 href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/THREE/9-34.xml">paragraph
 2</akn:ref> replace <akn:quotedText>old</akn:quotedText> with
 <akn:quotedText>new</akn:quotedText>.
      </akn:mod>
    """,
        """
      no href attribute in "ref" tag

      <akn:mod>
        In <akn:ref>paragraph 2</akn:ref> replace <akn:quotedText>old</akn:quotedText> with
 <akn:quotedText>new</akn:quotedText>.
      </akn:mod>
    """,
        """
      can't get eId from href

      <akn:mod>
        In <akn:ref href="invalid-eli-href">paragraph 2</akn:ref> replace
 <akn:quotedText>old</akn:quotedText> with <akn:quotedText>new</akn:quotedText>.
      </akn:mod>
    """
      })
  void throwModificationExceptionOnMissingParts(String modificationNodeText) {
    // given
    final XmlDocumentExtractor xmlDocumentExtractor = new XmlDocumentExtractor();
    final TimeMachine timeMachine = new TimeMachine(xmlDocumentExtractor);

    final String amendingLawXmlText =
        """
        <?xml version="1.0" encoding="UTF-8"?>
        <akn:body>
        """
            + modificationNodeText
            + """

          </akn:body>
        """;
    final String targetLawXmlText =
        """
        <?xml version="1.0" encoding="UTF-8"?>
        <akn:body>
            <akn:p eId="one">old text</akn:p>
            <akn:p eId="two">old text</akn:p>
          </akn:body>
        """;

    // when/then
    assertThatExceptionOfType(ModificationException.class)
        .isThrownBy(() -> timeMachine.apply(amendingLawXmlText, targetLawXmlText));
  }

  @Test
  void throwModificationExceptionIfAmendingLawHasNoModifications() {
    // given
    final XmlDocumentExtractor xmlDocumentExtractor = new XmlDocumentExtractor();
    final TimeMachine timeMachine = new TimeMachine(xmlDocumentExtractor);

    final String amendingLaw = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><amending/>";
    final String targetLaw = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><target/>";
    // when/then
    assertThatExceptionOfType(ModificationException.class)
        .isThrownBy(() -> timeMachine.apply(amendingLaw, targetLaw));
  }

  @Test
  void targetLawToContainTheNewTextInPlaceOfTheOldOne() {
    // given
    final XmlDocumentExtractor xmlDocumentExtractor = new XmlDocumentExtractor();
    final TimeMachine timeMachine = new TimeMachine(xmlDocumentExtractor);
    final String amendingLawXmlText =
        """
          <?xml version="1.0" encoding="UTF-8"?>
          <akn:body>
              <akn:mod>
               In <akn:ref
   href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml">paragraph
   2</akn:ref> replace <akn:quotedText>old</akn:quotedText> with
   <akn:quotedText>new</akn:quotedText>.
              </akn:mod>

              "old" -> "new"

            </akn:body>
          """
            .trim();
    final String targetLawXmlText =
        """
          <?xml version="1.0" encoding="UTF-8"?>
          <akn:body>
              <akn:p eId="one">old text</akn:p>
              <akn:p eId="two">old text</akn:p>
            </akn:body>
          """;

    final String expectedResultingLawXmlText =
        """
          <?xml version="1.0" encoding="UTF-8" standalone="no"?>
          <akn:body>
              <akn:p eId="one">old text</akn:p>
              <akn:p eId="two">new text</akn:p>
            </akn:body>
          """
            .replaceAll("[\\n ]", "");

    // when applying the TimeMachine
    final String resultingLaw = timeMachine.apply(amendingLawXmlText, targetLawXmlText);

    // the result contains the new text in place of the old text
    assertThat(resultingLaw.replaceAll("[\\n ]", "")).isEqualTo(expectedResultingLawXmlText);
  }
}
