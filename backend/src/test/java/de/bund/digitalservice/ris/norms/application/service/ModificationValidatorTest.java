package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlContentException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.w3c.dom.Node;

class ModificationValidatorTest {

  private final ModificationValidator underTest = new ModificationValidator();

  @Nested
  class noDestinationEli {

    @Test
    void emptyActiveModificationDestinationHref() {
      // given
      final Norm amendingNorm =
          NormFixtures.loadFromDisk("NormWithEmptyActiveModificationDestinationHref.xml");

      // when
      Throwable thrown = catchThrowable(() -> underTest.throwErrorNoDestinationSet(amendingNorm));

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
          .getActiveModifications()
          .getFirst()
          .setDestinationHref("#THIS_IS_NOT_OK_A_HREF_IS_NEVER_RELATIVE");

      // when
      Throwable thrown = catchThrowable(() -> underTest.throwErrorNoDestinationSet(amendingNorm));

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
      Throwable thrown = catchThrowable(() -> underTest.throwErrorNoDestinationSet(amendingNorm));

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
    void itWorksWithoutException() {
      // given
      final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      // 112 paragraph length
      amendingNorm
          .getActiveModifications()
          .getFirst()
          .setDestinationHref(
              new Href.Builder()
                  .setEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                  .setEId("hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
                  .setCharacterRange(new CharacterRange("1-112"))
                  .buildAbsolute()
                  .value());

      amendingNorm
          .getMods()
          .forEach(
              mod -> {
                mod.setTargetHref(
                    new Href.Builder()
                        .setEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                        .setEId("hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
                        .setCharacterRange(new CharacterRange("9-112"))
                        .buildAbsolute()
                        .value());
                mod.setOldText(
                    "§ 9 Abs. 1 Satz 2, Abs. 2 Kennezichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,");
              });

      final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // when/then
      Assertions.assertDoesNotThrow(() -> underTest.oldTextExistsInZf0Norm(amendingNorm, zf0Norm));
    }

    @Test
    void articleEIdIsEmpty() {

      // given
      var amendingNormXml =
          """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/"
                                      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                                      xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                          <akn:act name="regelungstext">
                              <!-- Metadaten -->
                                  <akn:meta eId="meta-1" GUID="7e5837c8-b967-45be-924b-c95956c4aa94">
                                      <akn:identification eId="meta-1_ident-1"
                                                          GUID="be8ecb75-0f1a-4209-b3a4-17d55bdffb47"
                                                          source="attributsemantik-noch-undefiniert">
                                          <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1"
                                                              GUID="4c69a6d2-8988-4581-bfa9-df9e8e24f321">
                                              <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"
                                                            GUID="f3805314-bbb6-4def-b82b-8b7f0b126197"
                                                            value="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"/>
                                          </akn:FRBRExpression>
                                      </akn:identification>
                                  </akn:meta>
                              <akn:body eId="hauptteil-1" GUID="0B4A8E1F-65EF-4B7C-9E22-E83BA6B73CD8">
                                  <!-- Artikel 1 : Hauptänderung -->
                                  <akn:article eId=""
                                               GUID="cdbfc728-a070-42d9-ba2f-357945afef06"
                                               period="#geltungszeitgr-1"
                                               refersTo="hauptaenderung">
                                  </akn:article>
                              </akn:body>
                          </akn:act>
                      </akn:akomaNtoso>
                      """;

      Norm amendingNorm = new Norm(XmlMapper.toDocument(amendingNormXml));
      final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInZf0Norm(amendingNorm, zf0Norm));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): Article eId is empty.");
    }

    @Test
    void noModInArticle() {

      // given
      final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      Node mod =
          amendingNorm
              .getByEId(
                  "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1")
              .get();
      mod.getParentNode().removeChild(mod);

      final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInZf0Norm(amendingNorm, zf0Norm));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): There is no mod in article with eId hauptteil-1_art-1");
    }

    @Test
    void oldTextIsEmpty() {

      // given
      final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      amendingNorm.getMods().getFirst().setOldText("");

      final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInZf0Norm(amendingNorm, zf0Norm));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): quotedText[1] (the old, to be replaced, text) is empty in article with eId hauptteil-1_art-1");
    }

    @Test
    void oldTextNotTheSameInZf0Norm() {

      // given
      final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      amendingNorm.getMods().forEach(mod -> mod.setOldText("not the same text as in target law"));

      final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInZf0Norm(amendingNorm, zf0Norm));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): The replacement text '§ 9 Abs. 1 Satz 2, Abs. 2' in the target law does not equal the replacement text 'not the same text as in target law' in the article with eId hauptteil-1_art-1");
    }

    private static Stream<Arguments> provideParametersForModTargetHref() {
      return Stream.of(
          Arguments.of(
              "",
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): mod href is empty in article with eId hauptteil-1_art-1"),
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
      amendingNorm.getMods().forEach(mod -> mod.setTargetHref(targetHref));

      final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInZf0Norm(amendingNorm, zf0Norm));

      // then
      assertThat(thrown).isInstanceOf(XmlContentException.class).hasMessageContaining(message);
    }

    @Disabled
    @Test
    void moreThanOneNodeWithGivenDestEidExists() {
      // TODO repair test and activate again
      // given
      final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
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

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInZf0Norm(amendingNorm, zf0Norm));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): Too many elements with the same eId hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1.");
    }

    @Disabled
    @Test
    void nodeWithGivenDestEidDoesNotExists() {
      // TODO repair test and activate again
      // given
      final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      final Norm zf0Norm = NormFixtures.loadFromDisk("SimpleNorm.xml");

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInZf0Norm(amendingNorm, zf0Norm));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): Couldn't load target eId (hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1) element in target law (eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1) for article with eId hauptteil-1_art-1");
    }

    @Test
    void doesNotThrow() {
      // given
      final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // when/then
      Assertions.assertDoesNotThrow(() -> underTest.oldTextExistsInZf0Norm(amendingNorm, zf0Norm));
    }

    @Test
    void throwsExceptionWhenCharacterRangeIsNotSet() {
      // given
      final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      amendingNorm
          .getMods()
          .forEach(
              mod ->
                  mod.setTargetHref(
                      new Href.Builder()
                          .setEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                          .setEId(
                              "hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
                          .setCharacterRange(new CharacterRange(""))
                          .buildInternalReference()
                          .value()));

      amendingNorm
          .getActiveModifications()
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

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInZf0Norm(amendingNorm, zf0Norm));

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
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): The character range in mod href is not valid in article with eId hauptteil-1_art-1. Make sure start is smaller than end 20 < 20."),
          Arguments.of(
              "-20.xml",

              // TODO Add to log: "For norm with Eli"
              "The range (-20) given at article with eId hauptteil-1_art-1 is not valid"),
          Arguments.of(
              "0-.xml",

              // TODO Add to log: "For norm with Eli"
              "The range (0-) given at article with eId hauptteil-1_art-1 is not valid"));
    }

    @ParameterizedTest
    @MethodSource("provideParametersForThrowsExceptionWhenCharacterRange")
    void throwsExceptionWhenCharacterRange(String cr, String message) {
      // given
      final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      amendingNorm
          .getMods()
          .forEach(
              mod ->
                  mod.setTargetHref(
                      new Href.Builder()
                          .setCharacterRange(new CharacterRange(cr))
                          .setEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                          .setEId(
                              "hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
                          .buildAbsolute()
                          .value()));

      amendingNorm
          .getActiveModifications()
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

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInZf0Norm(amendingNorm, zf0Norm));

      // then
      assertThat(thrown).isInstanceOf(XmlContentException.class).hasMessageContaining(message);
    }

    @Test
    void ThrowsExceptionWhenCharacterRangeEndIsTooHigh() {
      // given
      final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      // 112 paragraph length
      amendingNorm
          .getActiveModifications()
          .getFirst()
          .setDestinationHref(
              new Href.Builder()
                  .setEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                  .setEId("hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
                  .setCharacterRange(new CharacterRange("1-113"))
                  .buildAbsolute()
                  .value());

      amendingNorm
          .getMods()
          .forEach(
              mod -> {
                mod.setTargetHref(
                    new Href.Builder()
                        .setEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                        .setEId("hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
                        .setCharacterRange(new CharacterRange("9-113"))
                        .buildAbsolute()
                        .value());
                mod.setOldText(
                    "§ 9 Abs. 1 Satz 2, Abs. 2 Kennezichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet,");
              });

      final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInZf0Norm(amendingNorm, zf0Norm));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): The character range in mod href is not valid (target paragraph is to short) in article with eId hauptteil-1_art-1");
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
}
