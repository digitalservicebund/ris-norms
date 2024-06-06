package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlContentException;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.w3c.dom.Node;

class ModificationValidatorTest {
  private final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  private final LoadZf0Service loadZf0Service = mock(LoadZf0Service.class);
  private final ModificationValidator underTest =
      new ModificationValidator(loadNormPort, loadZf0Service);

  @Nested
  class noDestinationEli {

    @Test
    void emptyActiveModificationDestinationHref() {
      // given
      final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      amendingNorm
          .getNodeByEId("hauptteil-1_art-1")
          .get()
          .getAttributes()
          .getNamedItem("refersTo")
          .setTextContent("");

      // when
      Throwable thrown = catchThrowable(() -> underTest.destinationIsSet(amendingNorm));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): RefersTo is empty in article with eId hauptteil-1_art-1");
    }

    @Test
    void emptyArticleRefersTo() {
      // given
      final Norm amendingNorm =
          NormFixtures.loadFromDisk("NormWithEmptyActiveModificationDestinationHref.xml");

      // when
      Throwable thrown = catchThrowable(() -> underTest.destinationIsSet(amendingNorm));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): ActiveModification Destination Href is empty where textualMod eId is meta-1_analysis-1_activemod-1_textualmod-1");
    }

    @Test
    void brokenActiveModificationDestinationHref() {
      // given
      final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      amendingNorm
          .getMeta()
          .getAnalysis()
          .map(Analysis::getActiveModifications)
          .orElse(Collections.emptyList())
          .getFirst()
          .setDestinationHref("#THIS_IS_NOT_OK_A_HREF_IS_NEVER_RELATIVE");

      // when
      Throwable thrown = catchThrowable(() -> underTest.destinationIsSet(amendingNorm));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): ActiveModification Destination Href holds an empty (more general: invalid) Eli where textualMod eId is meta-1_analysis-1_activemod-1_textualmod-1");
    }

    @Test
    void emptyAffectedDocumentHref() {

      // given
      final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      amendingNorm.getArticles().getFirst().setAffectedDocumentEli("");

      // when
      Throwable thrown = catchThrowable(() -> underTest.destinationIsSet(amendingNorm));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): AffectedDocument href is empty in article with eId hauptteil-1_art-1");
    }
  }

  @Nested
  class oldTextExistsInZf0Norm {

    @Test
    void oldTextIsEmpty() {

      // given
      final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      final String amendingNormEli = amendingNorm.getEli();
      final Node modNode =
          amendingNorm
              .getNodeByEId(
                  "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1")
              .orElseThrow();
      final Mod mod = new Mod(modNode);
      mod.setOldText("");

      final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
      when(loadZf0Service.loadZf0(any(), any())).thenReturn(zf0Norm);
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(amendingNorm));

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.validateSubstitutionMod(amendingNormEli, mod));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): quotedText[1] (the old, to be replaced, text) is empty in article mod with eId hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1");
    }

    @Test
    void oldTextNotTheSameInZf0Norm() {

      // given
      final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      final String amendingNormEli = amendingNorm.getEli();
      final Node modNode =
          amendingNorm
              .getNodeByEId(
                  "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1")
              .orElseThrow();
      final Mod mod = new Mod(modNode);
      mod.setOldText("not the same text as in target law");

      final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
      when(loadZf0Service.loadZf0(any(), any())).thenReturn(zf0Norm);
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(amendingNorm));

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.validateSubstitutionMod(amendingNormEli, mod));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): The replacement text '§ 9 Abs. 1 Satz 2, Abs. 2' in the target law does not equal the replacement text 'not the same text as in target law' in the mod with eId hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1");
    }

    private static Stream<Arguments> provideParametersForModTargetHref() {
      return Stream.of(
          Arguments.of(
              "",
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): mod href is empty in article mod with eId hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"),
          Arguments.of(
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/",
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): The eId in mod href is empty in article with eId hauptteil-1_art-1"),
          Arguments.of(
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/",
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): The character range in mod href is empty in article with eId hauptteil-1_art-1"));
    }

    @ParameterizedTest
    @MethodSource("provideParametersForModTargetHref")
    void modTargetHref(String targetHref, String message) {

      // given
      final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      final String amendingNormEli = amendingNorm.getEli();
      final Node modNode =
          amendingNorm
              .getNodeByEId(
                  "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1")
              .orElseThrow();
      final Mod mod = new Mod(modNode);
      mod.setTargetHref(targetHref);
      final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
      when(loadZf0Service.loadZf0(any(), any())).thenReturn(zf0Norm);
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(amendingNorm));

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.validateSubstitutionMod(amendingNormEli, mod));

      // then
      assertThat(thrown).isInstanceOf(XmlContentException.class).hasMessageContaining(message);
    }

    @Test
    void moreThanOneNodeWithGivenDestEidExists() {
      // TODO repair test and activate again
      // given
      final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      final String amendingNormEli = amendingNorm.getEli();
      final Node modNode =
          amendingNorm
              .getNodeByEId(
                  "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1")
              .orElseThrow();
      final Mod mod = new Mod(modNode);
      final Norm zf0Norm =
          new Norm(
              XmlMapper.toDocument(
                  """
                          <?xml version="1.0" encoding="UTF-8"?>
                          <wrap>
                            <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1"
                                                GUID="4c69a6d2-8988-4581-bfa9-df9e8e24f321">
                                <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"
                                              GUID="f3805314-bbb6-4def-b82b-8b7f0b126197"
                                              value="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"/>
                            </akn:FRBRExpression>
                            <test eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">content</test>
                            <test2 eId="hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1">content</test2>
                          </wrap>
                          """));
      when(loadZf0Service.loadZf0(any(), any())).thenReturn(zf0Norm);
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(amendingNorm));

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.validateSubstitutionMod(amendingNormEli, mod));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): Too many elements with the same eId hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1.");
    }

    @Test
    void nodeWithGivenDestEidDoesNotExists() {
      // TODO repair test and activate again
      // given
      final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      final String amendingNormEli = amendingNorm.getEli();
      final Node modNode =
          amendingNorm
              .getNodeByEId(
                  "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1")
              .orElseThrow();
      final Mod mod = new Mod(modNode);
      final Norm zf0Norm = NormFixtures.loadFromDisk("SimpleNorm.xml");
      when(loadZf0Service.loadZf0(any(), any())).thenReturn(zf0Norm);
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(amendingNorm));

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.validateSubstitutionMod(amendingNormEli, mod));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): Couldn't load target eId (hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1) element in zf0 (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1) for mod with eId hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1");
    }

    @Test
    void validationSuccessful() {
      // given
      final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      final String amendingNormEli = amendingNorm.getEli();
      final Node modNode =
          amendingNorm
              .getNodeByEId(
                  "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1")
              .orElseThrow();
      final Mod mod = new Mod(modNode);
      final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
      when(loadZf0Service.loadZf0(any(), any())).thenReturn(zf0Norm);
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(amendingNorm));

      // when/then
      Assertions.assertDoesNotThrow(() -> underTest.validateSubstitutionMod(amendingNormEli, mod));
    }

    @Test
    void validationSuccessfulUntilEndOfParagraph() {
      // given
      final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      final Node modNode =
          amendingNorm
              .getNodeByEId(
                  "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1")
              .orElseThrow();
      final Mod mod = new Mod(modNode);
      final String amendingNormEli = amendingNorm.getEli();
      final String href =
          new Href.Builder()
              .setEli(amendingNormEli)
              .setEId("hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
              .setCharacterRange(new CharacterRange("9-112"))
              .buildAbsolute()
              .value();

      // 112 paragraph length
      amendingNorm
          .getMeta()
          .getAnalysis()
          .map(Analysis::getActiveModifications)
          .orElse(Collections.emptyList())
          .getFirst()
          .setDestinationHref(href);

      mod.setTargetHref(href);
      mod.setOldText(
          "§ 9 Abs. 1 Satz 2, Abs. 2 Kennezichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,");

      final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
      when(loadZf0Service.loadZf0(any(), any())).thenReturn(zf0Norm);
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(amendingNorm));

      // when/then
      Assertions.assertDoesNotThrow(() -> underTest.validateSubstitutionMod(amendingNormEli, mod));
    }

    // TODO add test for full paragraph

    @Test
    void throwsExceptionWhenCharacterRangeIsNotSet() {
      // given
      final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      final String amendingNormEli = amendingNorm.getEli();
      final Node modNode =
          amendingNorm
              .getNodeByEId(
                  "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1")
              .orElseThrow();
      final Mod mod = new Mod(modNode);
      mod.setTargetHref(
          new Href.Builder()
              .setEli(amendingNormEli)
              .setEId("hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
              .setCharacterRange(new CharacterRange(""))
              .buildInternalReference()
              .value());

      amendingNorm
          .getMeta()
          .getAnalysis()
          .map(Analysis::getActiveModifications)
          .orElse(Collections.emptyList())
          .forEach(
              textMod ->
                  textMod.setDestinationHref(
                      new Href.Builder()
                          .setEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                          .setEId(
                              "hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
                          .setCharacterRange(new CharacterRange(""))
                          .buildAbsolute()
                          .value()));

      final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
      when(loadZf0Service.loadZf0(any(), any())).thenReturn(zf0Norm);
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(amendingNorm));

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.validateSubstitutionMod(amendingNormEli, mod));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): The character range in mod href is empty in article with eId hauptteil-1_art-1");
    }

    private static Stream<Arguments> provideParametersForThrowsExceptionWhenCharacterRange() {
      return Stream.of(
          Arguments.of(
              "20-20.xml",
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): The character range in mod href is not valid in mod with eId hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1. Make sure start is smaller than end 20 < 20."),
          Arguments.of(
              "-20.xml",

              // TODO Add to log: "For norm with Eli"
              "The range (-20) given at mod with eId hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1 is not valid"),
          Arguments.of(
              "0-.xml",

              // TODO Add to log: "For norm with Eli"
              "The range (0-) given at mod with eId hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1 is not valid"),
          Arguments.of(
              "",
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): The character range in mod href is empty in article with eId hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"));
    }

    @ParameterizedTest
    @MethodSource("provideParametersForThrowsExceptionWhenCharacterRange")
    void throwsExceptionWhenCharacterRangeIsMalformed(String cr, String message) {
      // given
      final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      final String amendingNormEli = amendingNorm.getEli();
      final Node modNode =
          amendingNorm
              .getNodeByEId(
                  "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1")
              .orElseThrow();
      final Mod mod = new Mod(modNode);

      mod.setTargetHref(
          new Href.Builder()
              .setCharacterRange(new CharacterRange(cr))
              .setEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
              .setEId("hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
              .buildAbsolute()
              .value());

      amendingNorm
          .getMeta()
          .getAnalysis()
          .map(Analysis::getActiveModifications)
          .orElse(Collections.emptyList())
          .forEach(
              textMod ->
                  textMod.setDestinationHref(
                      new Href.Builder()
                          .setCharacterRange(new CharacterRange(cr))
                          .setEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                          .setEId(
                              "hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
                          .buildAbsolute()
                          .value()));

      final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
      when(loadZf0Service.loadZf0(any(), any())).thenReturn(zf0Norm);
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(amendingNorm));

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.validateSubstitutionMod(amendingNormEli, mod));

      // then
      assertThat(thrown).isInstanceOf(XmlContentException.class).hasMessageContaining(message);
    }

    @Test
    void ThrowsExceptionWhenCharacterRangeEndIsTooHigh() {
      // given
      final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      final String amendingNormEli = amendingNorm.getEli();
      final Node modNode =
          amendingNorm
              .getNodeByEId(
                  "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1")
              .orElseThrow();
      final Mod mod = new Mod(modNode);
      // 112 paragraph length
      amendingNorm
          .getMeta()
          .getAnalysis()
          .map(Analysis::getActiveModifications)
          .orElse(Collections.emptyList())
          .getFirst()
          .setDestinationHref(
              new Href.Builder()
                  .setEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                  .setEId("hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
                  .setCharacterRange(new CharacterRange("1-113"))
                  .buildAbsolute()
                  .value());

      mod.setTargetHref(
          new Href.Builder()
              .setEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
              .setEId("hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
              .setCharacterRange(new CharacterRange("9-113"))
              .buildAbsolute()
              .value());
      mod.setOldText(
          "§ 9 Abs. 1 Satz 2, Abs. 2 Kennezichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,");

      final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
      when(loadZf0Service.loadZf0(any(), any())).thenReturn(zf0Norm);
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(amendingNorm));

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.validateSubstitutionMod(amendingNormEli, mod));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): The character range in mod href is not valid (target paragraph is to short) in mod with eId hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1");
    }
  }

  @Test
  void destinationEliIsConsistent() {
    // given
    final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");

    // when/then
    Assertions.assertDoesNotThrow(() -> underTest.destinationEliIsConsistent(amendingNorm));
  }

  @Test
  void ThrowExceptionIfDestinationEliIsNotConsistent() {
    // given
    final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithInconsistentEli.xml");

    // when
    Throwable thrown = catchThrowable(() -> underTest.destinationEliIsConsistent(amendingNorm));

    // then
    assertThat(thrown)
        .isInstanceOf(XmlContentException.class)
        .hasMessageContaining(
            "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): Elis are not consistent");
  }

  @Test
  void destinationHrefIsConsistent() {
    // given
    final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");

    // when/then
    Assertions.assertDoesNotThrow(() -> underTest.destinationHrefIsConsistent(amendingNorm));
  }

  @Test
  void ThrowExceptionIfDestinationEidIsNotConsistent() {
    // given
    final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithInconsistentEid.xml");

    // when
    Throwable thrown = catchThrowable(() -> underTest.destinationHrefIsConsistent(amendingNorm));

    // then
    assertThat(thrown)
        .isInstanceOf(XmlContentException.class)
        .hasMessageContaining(
            "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): Eids are not consistent");
  }

  @Test
  void validateXmlConsistency() {
    // given
    final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
    final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
    when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(amendingNorm));
    when(loadZf0Service.loadZf0(any(), any())).thenReturn(zf0Norm);

    // when/then
    Assertions.assertDoesNotThrow(() -> underTest.validate(amendingNorm));
  }
}
