package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

import de.bund.digitalservice.ris.norms.application.exception.ValidationException;
import de.bund.digitalservice.ris.norms.domain.entity.Analysis;
import de.bund.digitalservice.ris.norms.domain.entity.CharacterRange;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Mod;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.domain.entity.TextualMod;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFound;
import java.util.Collections;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.w3c.dom.Node;

class SingleModValidatorTest {

  private final SingleModValidator underTest = new SingleModValidator();

  @Test
  void oldTextIsEmpty() {

    // given
    final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
    final Node modNode =
        amendingNorm
            .getNodeByEId(
                "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1")
            .orElseThrow();
    final Mod mod = new Mod(modNode);
    mod.setOldText("");

    final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

    // when
    Throwable thrown = catchThrowable(() -> underTest.validate(zf0Norm, mod));

    // then
    assertThat(thrown)
        .isInstanceOf(MandatoryNodeNotFound.class)
        .hasMessageContaining(
            "Element with xpath 'normalize-space(./quotedText[1])' not found in 'akn:mod' of norm 'eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1'");
  }

  @Test
  void oldTextNotTheSameInZf0Norm() {

    // given
    final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
    final Node modNode =
        amendingNorm
            .getNodeByEId(
                "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1")
            .orElseThrow();
    final Mod mod = new Mod(modNode);
    mod.setOldText("not the same text as in target law");

    final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

    // when
    Throwable thrown = catchThrowable(() -> underTest.validate(zf0Norm, mod));

    // then
    assertThat(thrown)
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining(
            "The character range 9-34 of passive mod with eId meta-1_analysis-1_pasmod-1_textualmod-2 within ZF0 norm with eli eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1 does not resolve to the targeted text to be replaced.");
  }

  @Test
  void nodeWithGivenDestEidDoesNotExists() {
    // given
    final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
    final Node modNode =
        amendingNorm
            .getNodeByEId(
                "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1")
            .orElseThrow();
    final Mod mod = new Mod(modNode);
    final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
    final TextualMod passiveMod =
        zf0Norm.getMeta().getAnalysis().orElseThrow().getPassiveModifications().getFirst();
    passiveMod.setDestinationHref(
        "#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1");

    // when
    Throwable thrown = catchThrowable(() -> underTest.validate(zf0Norm, mod));

    // then
    assertThat(thrown)
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining(
            "Target node with eid hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1 not present");
  }

  @Test
  void validationSuccessful() {
    // given
    final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
    final Node modNode =
        amendingNorm
            .getNodeByEId(
                "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1")
            .orElseThrow();
    final Mod mod = new Mod(modNode);
    final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

    // when/then
    Assertions.assertDoesNotThrow(() -> underTest.validate(zf0Norm, mod));
  }

  @Test
  void validationSuccessfulEvenWithManySpacesAndLineBreaks() {
    // given
    final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
    final Node modNode =
        amendingNorm
            .getNodeByEId(
                "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1")
            .orElseThrow();
    final Mod mod = new Mod(modNode);
    final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
    Node zf0TargetNode =
        zf0Norm
            .getNodeByEId("hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
            .orElseThrow();

    // sets quotedText[1] in amending norm: imagine a xml formatting script did the following
    // (note the new lines)
    mod.setOldText(
        """
                                                               §             9
Abs.
                                  1
                                   Satz 2,
                                                 Abs. 2
                                                               """);

    // and ZF0 experienced something similar
    zf0TargetNode.setTextContent(
        """
              entgegen          § 9          Abs.

        1          Satz 2,
                                                 Abs.           2

                                                  Kennezichen eines verbotenen Vereins
                    oder einer Ersatzorganisation verwendet,""");

    // when/then: both are equal due to usage of StringUtils.normalizeSpace()
    Assertions.assertDoesNotThrow(() -> underTest.validate(zf0Norm, mod));
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
    mod.setOldText("§ 9 Abs. 1 Satz 2, Abs. 2");

    final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

    // when/then
    Assertions.assertDoesNotThrow(() -> underTest.validate(zf0Norm, mod));
  }

  @Test
  void validationSuccessfulStartingAt0() {
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
            .setCharacterRange(new CharacterRange("0-34"))
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
    mod.setOldText("§ 9 Abs. 1 Satz 2, Abs. 2");

    final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

    // when/then
    Assertions.assertDoesNotThrow(() -> underTest.validate(zf0Norm, mod));
  }

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

    final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
    final TextualMod passiveMod =
        zf0Norm.getMeta().getAnalysis().orElseThrow().getPassiveModifications().getFirst();
    passiveMod.setDestinationHref(
        new Href.Builder()
            .setEId("hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
            .setCharacterRange(new CharacterRange(""))
            .buildInternalReference()
            .value());

    // when
    Throwable thrown = catchThrowable(() -> underTest.validate(zf0Norm, mod));

    // then
    assertThat(thrown)
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining(
            "Destination href with value #hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/ of passive mod with eId meta-1_analysis-1_pasmod-1_textualmod-2 within ZF0 norm with eli eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1 not present.");
  }

  private static Stream<Arguments> provideParametersForThrowsExceptionWhenCharacterRange() {
    return Stream.of(
        Arguments.of(
            "20-20.xml",
            "The character range 20-20.xml of passive mod with eId meta-1_analysis-1_pasmod-1_textualmod-2 within ZF0 norm with eli eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1 has invalid format."),
        Arguments.of(
            "-20.xml",
            "The character range -20.xml of passive mod with eId meta-1_analysis-1_pasmod-1_textualmod-2 within ZF0 norm with eli eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1 has invalid format."),
        Arguments.of(
            "0-.xml",
            "The character range 0-.xml of passive mod with eId meta-1_analysis-1_pasmod-1_textualmod-2 within ZF0 norm with eli eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1 has invalid format."),
        Arguments.of(
            "",
            "Destination href with value #hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/ of passive mod with eId meta-1_analysis-1_pasmod-1_textualmod-2 within ZF0 norm with eli eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1 not present."));
  }

  @ParameterizedTest
  @MethodSource("provideParametersForThrowsExceptionWhenCharacterRange")
  void throwsExceptionWhenCharacterRangeIsMalformed(String cr, String message) {
    // given
    final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
    final Node modNode =
        amendingNorm
            .getNodeByEId(
                "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1")
            .orElseThrow();
    final Mod mod = new Mod(modNode);

    final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
    final TextualMod passiveMod =
        zf0Norm.getMeta().getAnalysis().orElseThrow().getPassiveModifications().getFirst();
    passiveMod.setDestinationHref(
        new Href.Builder()
            .setEId("hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
            .setCharacterRange(new CharacterRange(cr))
            .buildInternalReference()
            .value());

    // when
    Throwable thrown = catchThrowable(() -> underTest.validate(zf0Norm, mod));

    // then
    assertThat(thrown).isInstanceOf(ValidationException.class).hasMessageContaining(message);
  }

  @Test
  void ThrowsExceptionWhenCharacterRangeEndIsTooHigh() {
    // given
    final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
    final Node modNode =
        amendingNorm
            .getNodeByEId(
                "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1")
            .orElseThrow();
    final Mod mod = new Mod(modNode);

    final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
    final TextualMod passiveMod =
        zf0Norm.getMeta().getAnalysis().orElseThrow().getPassiveModifications().getFirst();
    passiveMod.setDestinationHref(
        new Href.Builder()
            .setEId("hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
            .setCharacterRange(new CharacterRange("9-113"))
            .buildInternalReference()
            .value());

    // when
    Throwable thrown = catchThrowable(() -> underTest.validate(zf0Norm, mod));

    // then
    assertThat(thrown)
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining(
            "The character range 9-113 of passive mod with eId meta-1_analysis-1_pasmod-1_textualmod-2 within ZF0 norm with eli eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1 is not within length of target node content.");
  }
}
