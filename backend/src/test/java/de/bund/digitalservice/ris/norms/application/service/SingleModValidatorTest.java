package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

import de.bund.digitalservice.ris.norms.application.exception.ValidationException;
import de.bund.digitalservice.ris.norms.domain.entity.Analysis;
import de.bund.digitalservice.ris.norms.domain.entity.CharacterRange;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Mod;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.domain.entity.TextualMod;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
import java.util.Collections;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.w3c.dom.Element;

class SingleModValidatorTest {

  private final SingleModValidator underTest = new SingleModValidator();

  @Nested
  class quotedText {

    @Test
    void oldTextIsEmpty() {
      // given
      final Regelungstext amendingNorm = Fixtures.loadRegelungstextFromDisk("NormWithMods.xml");
      final Element modNode = amendingNorm
        .getElementByEId(
          "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"
        )
        .orElseThrow();
      final Mod mod = new Mod(modNode);
      mod.setOldText("");

      final Regelungstext zf0Norm = Fixtures.loadRegelungstextFromDisk(
        "NormWithPassiveModifications.xml"
      );

      // when
      Throwable thrown = catchThrowable(() -> underTest.validate(zf0Norm, mod));

      // then
      assertThat(thrown)
        .isInstanceOf(MandatoryNodeNotFoundException.class)
        .hasMessageContaining(
          "Element with xpath 'normalize-space(./quotedText[1])' not found in 'akn:mod' of norm 'eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1'"
        );
    }

    @Test
    void oldTextNotTheSameInZf0Norm() {
      // given
      final Regelungstext amendingNorm = Fixtures.loadRegelungstextFromDisk("NormWithMods.xml");
      final Element modNode = amendingNorm
        .getElementByEId(
          "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"
        )
        .orElseThrow();
      final Mod mod = new Mod(modNode);
      mod.setOldText("not the same text as in target law");

      final Regelungstext zf0Norm = Fixtures.loadRegelungstextFromDisk(
        "NormWithPassiveModifications.xml"
      );

      // when
      Throwable thrown = catchThrowable(() -> underTest.validate(zf0Norm, mod));

      // then
      assertThat(thrown)
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining(
          "The character range 9-34 of passive mod with eId meta-1_analysis-1_pasmod-1_textualmod-1 within ZF0 norm with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1 does not resolve to the targeted text to be replaced."
        );
    }

    @Test
    void nodeWithGivenDestEidDoesNotExists() {
      // given
      final Regelungstext amendingNorm = Fixtures.loadRegelungstextFromDisk("NormWithMods.xml");
      final Element modNode = amendingNorm
        .getElementByEId(
          "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"
        )
        .orElseThrow();
      final Mod mod = new Mod(modNode);
      final Regelungstext zf0Norm = Fixtures.loadRegelungstextFromDisk(
        "NormWithPassiveModifications.xml"
      );
      final TextualMod passiveMod = zf0Norm
        .getMeta()
        .getAnalysis()
        .orElseThrow()
        .getPassiveModifications()
        .getFirst();
      passiveMod.setDestinationHref(
        new Href("#hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1")
      );

      // when
      Throwable thrown = catchThrowable(() -> underTest.validate(zf0Norm, mod));

      // then
      assertThat(thrown)
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining(
          "Target node with eid hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1 not present"
        );
    }

    @Test
    void validationSuccessful() {
      // given
      final Regelungstext amendingNorm = Fixtures.loadRegelungstextFromDisk("NormWithMods.xml");
      final Element modNode = amendingNorm
        .getElementByEId(
          "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"
        )
        .orElseThrow();
      final Mod mod = new Mod(modNode);
      final Regelungstext zf0Norm = Fixtures.loadRegelungstextFromDisk(
        "NormWithPassiveModifications.xml"
      );

      // when/then
      assertThatCode(() -> underTest.validate(zf0Norm, mod)).doesNotThrowAnyException();
    }

    @Test
    void validationSuccessfulUntilEndOfParagraph() {
      // given
      final Regelungstext amendingNorm = Fixtures.loadRegelungstextFromDisk("NormWithMods.xml");
      final Element modNode = amendingNorm
        .getElementByEId(
          "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"
        )
        .orElseThrow();
      final Mod mod = new Mod(modNode);
      final DokumentExpressionEli amendingNormEli = amendingNorm.getExpressionEli();
      final Href href = new Href.Builder()
        .setEli(amendingNormEli)
        .setEId("hauptteil-1_art-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
        .setCharacterRange(new CharacterRange("9-112"))
        .buildAbsolute();

      // 112 paragraph length
      amendingNorm
        .getMeta()
        .getAnalysis()
        .map(Analysis::getActiveModifications)
        .orElse(Collections.emptyList())
        .getFirst()
        .setDestinationHref(href);

      mod.setTargetRefHref(href);
      mod.setOldText("§ 9 Abs. 1 Satz 2, Abs. 2");

      final Regelungstext zf0Norm = Fixtures.loadRegelungstextFromDisk(
        "NormWithPassiveModifications.xml"
      );

      // when/then
      assertThatCode(() -> underTest.validate(zf0Norm, mod)).doesNotThrowAnyException();
    }

    @Test
    void validationSuccessfulStartingAt0() {
      // given
      final Regelungstext amendingNorm = Fixtures.loadRegelungstextFromDisk("NormWithMods.xml");
      final Element modNode = amendingNorm
        .getElementByEId(
          "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"
        )
        .orElseThrow();
      final Mod mod = new Mod(modNode);
      final DokumentExpressionEli amendingNormEli = amendingNorm.getExpressionEli();
      final Href href = new Href.Builder()
        .setEli(amendingNormEli)
        .setEId("hauptteil-1_art-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
        .setCharacterRange(new CharacterRange("0-34"))
        .buildAbsolute();

      // 112 paragraph length
      amendingNorm
        .getMeta()
        .getAnalysis()
        .map(Analysis::getActiveModifications)
        .orElse(Collections.emptyList())
        .getFirst()
        .setDestinationHref(href);

      mod.setTargetRefHref(href);
      mod.setOldText("§ 9 Abs. 1 Satz 2, Abs. 2");

      final Regelungstext zf0Norm = Fixtures.loadRegelungstextFromDisk(
        "NormWithPassiveModifications.xml"
      );

      // when/then
      assertThatCode(() -> underTest.validate(zf0Norm, mod)).doesNotThrowAnyException();
    }

    @Test
    void throwsExceptionWhenCharacterRangeIsNotSet() {
      // given
      final Regelungstext amendingNorm = Fixtures.loadRegelungstextFromDisk("NormWithMods.xml");
      final DokumentExpressionEli amendingNormEli = amendingNorm.getExpressionEli();
      final Element modNode = amendingNorm
        .getElementByEId(
          "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"
        )
        .orElseThrow();
      final Mod mod = new Mod(modNode);
      mod.setTargetRefHref(
        new Href.Builder()
          .setEli(amendingNormEli)
          .setEId("hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
          .setCharacterRange(new CharacterRange(""))
          .buildInternalReference()
      );

      final Regelungstext zf0Norm = Fixtures.loadRegelungstextFromDisk(
        "NormWithPassiveModifications.xml"
      );
      final TextualMod passiveMod = zf0Norm
        .getMeta()
        .getAnalysis()
        .orElseThrow()
        .getPassiveModifications()
        .getFirst();
      passiveMod.setDestinationHref(
        new Href.Builder()
          .setEId("hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
          .setCharacterRange(new CharacterRange(""))
          .buildInternalReference()
      );

      // when
      Throwable thrown = catchThrowable(() -> underTest.validate(zf0Norm, mod));

      // then
      assertThat(thrown)
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining(
          "In the destination href with value #hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/ of passive mod with eId meta-1_analysis-1_pasmod-1_textualmod-1 within ZF0 norm with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1, the character range not present."
        );
    }

    private static Stream<Arguments> provideParametersForThrowsExceptionWhenCharacterRange() {
      return Stream.of(
        Arguments.of(
          "20-20.xml",
          "The character range 20-20.xml of passive mod with eId meta-1_analysis-1_pasmod-1_textualmod-1 within ZF0 norm with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1 has invalid format."
        ),
        Arguments.of(
          "-20.xml",
          "The character range -20.xml of passive mod with eId meta-1_analysis-1_pasmod-1_textualmod-1 within ZF0 norm with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1 has invalid format."
        ),
        Arguments.of(
          "0-.xml",
          "The character range 0-.xml of passive mod with eId meta-1_analysis-1_pasmod-1_textualmod-1 within ZF0 norm with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1 has invalid format."
        ),
        Arguments.of(
          "",
          "In the destination href with value #hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/ of passive mod with eId meta-1_analysis-1_pasmod-1_textualmod-1 within ZF0 norm with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1, the character range not present."
        )
      );
    }

    @ParameterizedTest
    @MethodSource("provideParametersForThrowsExceptionWhenCharacterRange")
    void throwsExceptionWhenCharacterRangeIsMalformed(String cr, String message) {
      // given
      final Regelungstext amendingNorm = Fixtures.loadRegelungstextFromDisk("NormWithMods.xml");
      final Element modNode = amendingNorm
        .getElementByEId(
          "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"
        )
        .orElseThrow();
      final Mod mod = new Mod(modNode);

      final Regelungstext zf0Norm = Fixtures.loadRegelungstextFromDisk(
        "NormWithPassiveModifications.xml"
      );
      final TextualMod passiveMod = zf0Norm
        .getMeta()
        .getAnalysis()
        .orElseThrow()
        .getPassiveModifications()
        .getFirst();
      passiveMod.setDestinationHref(
        new Href.Builder()
          .setEId("hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
          .setCharacterRange(new CharacterRange(cr))
          .buildInternalReference()
      );

      // when
      Throwable thrown = catchThrowable(() -> underTest.validate(zf0Norm, mod));

      // then
      assertThat(thrown).isInstanceOf(ValidationException.class).hasMessageContaining(message);
    }

    @Test
    void ThrowsExceptionWhenCharacterRangeEndIsTooHigh() {
      // given
      final Regelungstext amendingNorm = Fixtures.loadRegelungstextFromDisk("NormWithMods.xml");
      final Element modNode = amendingNorm
        .getElementByEId(
          "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"
        )
        .orElseThrow();
      final Mod mod = new Mod(modNode);

      final Regelungstext zf0Norm = Fixtures.loadRegelungstextFromDisk(
        "NormWithPassiveModifications.xml"
      );
      final TextualMod passiveMod = zf0Norm
        .getMeta()
        .getAnalysis()
        .orElseThrow()
        .getPassiveModifications()
        .getFirst();
      passiveMod.setDestinationHref(
        new Href.Builder()
          .setEId("hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
          .setCharacterRange(new CharacterRange("9-1113"))
          .buildInternalReference()
      );

      // when
      Throwable thrown = catchThrowable(() -> underTest.validate(zf0Norm, mod));

      // then
      assertThat(thrown)
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining(
          "The character range 9-1113 of passive mod with eId meta-1_analysis-1_pasmod-1_textualmod-1 within ZF0 norm with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1 is not within the target node."
        );
    }
  }

  @Nested
  class quotedStructure {

    @Test
    void validationSuccessNoUpTo() {
      // given
      final Regelungstext amendingNorm = Fixtures.loadRegelungstextFromDisk(
        "NormWithQuotedStructureMods.xml"
      );
      final Element modNode = amendingNorm
        .getElementByEId(
          "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"
        )
        .orElseThrow();
      final Mod mod = new Mod(modNode);
      final Regelungstext zf0Norm = Fixtures.loadRegelungstextFromDisk(
        "NormWithPassiveModsQuotedStructure.xml"
      );

      // when then
      assertThatCode(() -> underTest.validate(zf0Norm, mod)).doesNotThrowAnyException();
    }

    @Test
    void validationSuccessWithUpTo() {
      // given
      final Regelungstext amendingNorm = Fixtures.loadRegelungstextFromDisk(
        "NormWithQuotedStructureModsAndUpTo.xml"
      );
      final Element modNode = amendingNorm
        .getElementByEId(
          "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"
        )
        .orElseThrow();
      final Mod mod = new Mod(modNode);
      final Regelungstext zf0Norm = Fixtures.loadRegelungstextFromDisk(
        "NormWithPassiveModsQuotedStructureAndUpTo.xml"
      );

      // when then
      assertThatCode(() -> underTest.validate(zf0Norm, mod)).doesNotThrowAnyException();
    }

    @Test
    void upToNodeNotPresent() {
      // given
      final Regelungstext amendingNorm = Fixtures.loadRegelungstextFromDisk(
        "NormWithQuotedStructureModsAndUpTo.xml"
      );
      final Element modNode = amendingNorm
        .getElementByEId(
          "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"
        )
        .orElseThrow();
      final Mod mod = new Mod(modNode);
      final Regelungstext zf0Norm = Fixtures.loadRegelungstextFromDisk(
        "NormWithPassiveModsQuotedStructureAndUpTo.xml"
      );

      final TextualMod passiveMod = zf0Norm
        .getMeta()
        .getAnalysis()
        .orElseThrow()
        .getPassiveModifications()
        .getFirst();
      passiveMod.setDestinationUpTo(new Href("#not-present-href"));

      // when
      Throwable thrown = catchThrowable(() -> underTest.validate(zf0Norm, mod));

      // then
      assertThat(thrown)
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining(
          "Target upTo node with eid not-present-href not present in ZF0 norm with eli eli/bund/bgbl-1/1999/66/2002-02-20/1/deu/regelungstext-1."
        );
    }

    @Test
    void upToNodeAndTargetNodeNotSiblings() {
      // given
      final Regelungstext amendingNorm = Fixtures.loadRegelungstextFromDisk(
        "NormWithQuotedStructureModsAndUpTo.xml"
      );
      final Element modNode = amendingNorm
        .getElementByEId(
          "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"
        )
        .orElseThrow();
      final Mod mod = new Mod(modNode);
      final Regelungstext zf0Norm = Fixtures.loadRegelungstextFromDisk(
        "NormWithPassiveModsQuotedStructureAndUpTo.xml"
      );

      final TextualMod passiveMod = zf0Norm
        .getMeta()
        .getAnalysis()
        .orElseThrow()
        .getPassiveModifications()
        .getFirst();
      passiveMod.setDestinationUpTo(
        new Href("#hauptteil-1_art-2_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-2")
      );

      // when
      Throwable thrown = catchThrowable(() -> underTest.validate(zf0Norm, mod));

      // then
      assertThat(thrown)
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining(
          "Target node with eid hauptteil-1_art-2_abs-1 and target upTo node with eid hauptteil-1_art-2_abs-3_untergl-1_listenelem-1_untergl-1_listenelem-2 are not siblings in ZF0 norm with eli eli/bund/bgbl-1/1999/66/2002-02-20/1/deu/regelungstext-1."
        );
    }

    @Test
    void upToNodeBeforeTargetNode() {
      // given
      final Regelungstext amendingNorm = Fixtures.loadRegelungstextFromDisk(
        "NormWithQuotedStructureModsAndUpTo.xml"
      );
      final Element modNode = amendingNorm
        .getElementByEId(
          "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"
        )
        .orElseThrow();
      final Mod mod = new Mod(modNode);
      final Regelungstext zf0Norm = Fixtures.loadRegelungstextFromDisk(
        "NormWithPassiveModsQuotedStructureAndUpTo.xml"
      );

      final TextualMod passiveMod = zf0Norm
        .getMeta()
        .getAnalysis()
        .orElseThrow()
        .getPassiveModifications()
        .getFirst();
      passiveMod.setDestinationHref(new Href("#hauptteil-1_art-2_abs-3"));
      passiveMod.setDestinationUpTo(new Href("#hauptteil-1_art-2_abs-1"));

      // when
      Throwable thrown = catchThrowable(() -> underTest.validate(zf0Norm, mod));

      // then
      assertThat(thrown)
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining(
          "Target node with eid hauptteil-1_art-2_abs-3 does not appear before target upTo node with eid hauptteil-1_art-2_abs-1 in ZF0 norm with eli eli/bund/bgbl-1/1999/66/2002-02-20/1/deu/regelungstext-1."
        );
    }
  }
}
