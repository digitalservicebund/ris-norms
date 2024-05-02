package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.port.input.TimeMachineUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TimeMachineServiceTest {
  final NormService normService = mock(NormService.class);

  final TimeMachineService timeMachineService =
      new TimeMachineService(new XmlDocumentService(), normService);

  @Nested
  class applyTimeMachine {
    @Test
    void itAppliesTimeMachine() {

      // Given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";

      String amendingLawString =
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?><amendingLaw><akn:mod>In <akn:ref"
              + " href=\"eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml\">paragraph 2</akn:ref> "
              + "<akn:quotedText>old</akn:quotedText> with <akn:quotedText>new</akn:quotedText>.</akn:mod></amendingLaw>";
      String targetLawString =
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?><targetLaw><akn:p eId=\"two\">old text</akn:p></targetLaw>";

      when(normService.loadNormXml(any())).thenReturn(Optional.of(targetLawString));

      // When
      var xml =
          timeMachineService.applyTimeMachine(new TimeMachineUseCase.Query(eli, amendingLawString));

      // Then
      verify(normService, times(1))
          .loadNormXml(argThat(argument -> Objects.equals(argument.eli(), eli)));
      assertThat(xml)
          .isPresent()
          .contains(
              "<?xml version=\"1.0\" encoding=\"UTF-8\"?><targetLaw><akn:p eId=\"two\">new text</akn:p></targetLaw>");
    }
  }

  @Nested
  class applyPassiveModifications {
    @Test
    void returnUnchangedIfNoPassiveModifications() {
      // given
      final var norm =
          Norm.builder()
              .document(
                  XmlMapper.toDocument(
                      """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                         <akn:act name="regelungstext">
                            <!-- Metadaten -->
                            <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                               <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                  <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
                                      <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="b82cc174-8fff-43bf-a434-5646de09e807" value="s593" />
                                      <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" value="BGBl. I" />
                                      <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung" />
                                   </akn:FRBRWork>
                              </akn:identification>
                            </akn:meta>
                         </akn:act>
                      </akn:akomaNtoso>
                      """))
              .build();

      // when
      Norm result = timeMachineService.applyPassiveModifications(norm, Instant.MAX);

      // then
      assertThat(result).isEqualTo(norm);
    }

    @Test
    void applyOnePassiveModification() {
      // given
      final var norm = NormFixtures.normWithPassiveModifications();

      final var amendingLaw = NormFixtures.normWithMods();

      when(normService.loadNorm(any())).thenReturn(Optional.of(amendingLaw));

      // when
      Norm result = timeMachineService.applyPassiveModifications(norm, Instant.MAX);

      // then
      var changedNodeValue =
          NodeParser.getValueFromExpression(
              "//*[@eId=\"hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1\"]",
              result.getDocument());
      assertThat(changedNodeValue).isPresent();
      assertThat(changedNodeValue.get())
          .isEqualToIgnoringWhitespace(
              "entgegen § 9 Absatz 1 Satz 2, Absatz 2 oder 3 Kennezichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,");
    }

    @Test
    void applyPassiveModificationsInCorrectOrder() {
      // given
      final var norm = NormFixtures.normWithMultiplePassiveModifications();

      final var amendingLaw = NormFixtures.normWithMultipleMods();

      when(normService.loadNorm(any())).thenReturn(Optional.of(amendingLaw));

      // when
      Norm result = timeMachineService.applyPassiveModifications(norm, Instant.MAX);

      // then
      var changedNodeValue =
          NodeParser.getValueFromExpression(
              "//*[@eId=\"hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1\"]",
              result.getDocument());
      assertThat(changedNodeValue).isPresent();
      assertThat(changedNodeValue.get())
          .isEqualToIgnoringWhitespace(
              "entgegen § 9 Absatz 1 Satz 2, Absatz 2, 3 oder 4 Kennezichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,");
    }

    @Test
    void applyPassiveModificationsBeforeDate() {
      // given
      final var norm = NormFixtures.normWithMultiplePassiveModifications();

      final var amendingLaw = NormFixtures.normWithMultipleMods();

      when(normService.loadNorm(any())).thenReturn(Optional.of(amendingLaw));

      // when
      Norm result =
          timeMachineService.applyPassiveModifications(
              norm, Instant.parse("2017-03-01T00:00:00.000Z"));

      // then
      var changedNodeValue =
          NodeParser.getValueFromExpression(
              "//*[@eId=\"hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1\"]",
              result.getDocument());
      assertThat(changedNodeValue).isPresent();
      assertThat(changedNodeValue.get())
          .isEqualToIgnoringWhitespace(
              "entgegen § 9 Absatz 1 Satz 2, Absatz 2 oder 3 Kennezichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,");
    }
  }

  @Nested
  class applyAmendingLawOnTargetLaw {

    @Test
    void oldTextIsReplacedByNewText() {
      // given
      String amendingLawString =
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?><amendingLaw><akn:mod>In <akn:ref"
              + " href=\"eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml\">paragraph 2</akn:ref> "
              + "<akn:quotedText>old</akn:quotedText> with <akn:quotedText>new</akn:quotedText>.</akn:mod></amendingLaw>";
      String targetLawString =
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?><targetLaw><akn:p eId=\"two\">old text</akn:p></targetLaw>";

      // when
      String result = timeMachineService.apply(amendingLawString, targetLawString);

      // then
      assertThat(result)
          .isEqualTo(
              "<?xml version=\"1.0\" encoding=\"UTF-8\"?><targetLaw><akn:p eId=\"two\">new text</akn:p></targetLaw>");
    }

    @Test
    void XmlProcessingExceptionIsThrownWhenAmendingLawXmlIsInvalid() {
      // given
      String amendingLawString = "SomeRandomText";
      String targetLawString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><targetLaw></targetLaw>";

      // when
      Throwable thrown =
          catchThrowable(() -> timeMachineService.apply(amendingLawString, targetLawString));

      // then
      assertThat(thrown).isInstanceOf(XmlProcessingException.class);
    }

    @Test
    void XmlProcessingExceptionIsThrownWhenTargetLawXmlIsInvalid() {
      // given
      String amendingLawString =
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?><amendingLaw></amendingLaw>";

      String targetLawString = "randomString";

      // when
      Throwable thrown =
          catchThrowable(() -> timeMachineService.apply(amendingLawString, targetLawString));

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

      // when
      Throwable thrown =
          catchThrowable(() -> timeMachineService.apply(amendingLawXmlText, targetLawXmlText));

      // then
      assertThat(thrown).isInstanceOf(XmlProcessingException.class);
    }

    @Test
    void throwModificationExceptionIfAmendingLawHasNoModifications() {
      // given
      final String amendingLaw = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><amending/>";
      final String targetLaw = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><target/>";

      // when
      Throwable thrown = catchThrowable(() -> timeMachineService.apply(amendingLaw, targetLaw));

      // then
      assertThat(thrown).isInstanceOf(XmlProcessingException.class);
    }

    @Test
    void targetLawToContainTheNewTextInPlaceOfTheOldOne() {
      // given
      final String amendingLawXmlText =
          """
             <?xml version="1.0" encoding="UTF-8"?>
             <akn:body>
                 <akn:mod>
               In <akn:ref href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml">paragraph 2</akn:ref> replace <akn:quotedText>old</akn:quotedText> with
               <akn:quotedText>new</akn:quotedText>.
                 </akn:mod>

                 "old" -> "new"

          </akn:body>
             """
              .strip();
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
          <?xml version="1.0" encoding="UTF-8"?>
          <akn:body>
              <akn:p eId="one">old text</akn:p>
              <akn:p eId="two">new text</akn:p>
            </akn:body>
          """
              .replaceAll("[\\n ]", "");

      // when applying the TimeMachine
      final String resultingLaw = timeMachineService.apply(amendingLawXmlText, targetLawXmlText);

      // the result contains the new text in place of the old text
      assertThat(resultingLaw.replaceAll("[\\n ]", "")).isEqualTo(expectedResultingLawXmlText);
    }
  }
}
