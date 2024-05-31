package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlContentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

class ModificationValidatorTest {

  private final ModificationValidator underTest = new ModificationValidator();

  @Nested
  class noDestinationEli {

    @Test
    void emptyActiveModificationDestinationHref() {
      // given
      final Norm amendingLaw =
          NormFixtures.loadFromDisk("NormWithEmptyActiveModificationDestinationHref.xml");

      // when
      Throwable thrown = catchThrowable(() -> underTest.throwErrorNoDestinationSet(amendingLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For amendingLaw with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): ActiveModification Destination Href is empty where textualMod eId is meta-1_analysis-1_activemod-1_textualmod-1");
    }

    @Test
    void brokenActiveModificationDestinationHref() {
      // given
      final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      amendingLaw
          .getActiveModifications()
          .getFirst()
          .setDestinationHref("#THIS_IS_NOT_OK_A_HREF_IS_NEVER_RELATIVE");

      // when
      Throwable thrown = catchThrowable(() -> underTest.throwErrorNoDestinationSet(amendingLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For amendingLaw with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): ActiveModification Destination Href holds an empty (more general: invalid) Eli where textualMod eId is meta-1_analysis-1_activemod-1_textualmod-1");
    }

    @Test
    void emptyAffectedDocumentHref() {

      // given
      final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      amendingLaw.getArticles().getFirst().setAffectedDocumentEli("");

      // when
      Throwable thrown = catchThrowable(() -> underTest.throwErrorNoDestinationSet(amendingLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For amendingLaw with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): AffectedDocument href is empty in article with eId hauptteil-1_art-1");
    }
  }

  @Nested
  class oldTextExistsInTargetLaw {

    @Test
    void itWorksWithoutException() {
      // given
      final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      // 112 paragraph length
      amendingLaw
          .getActiveModifications()
          .getFirst()
          .setDestinationHref(
              new Href.Builder()
                  .setEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                  .setEId("hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
                  .setCharacterRange(new CharacterRange("1-112"))
                  .buildAbsolute()
                  .value());

      amendingLaw
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

      final Norm targetLaw = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // when/then
      Assertions.assertDoesNotThrow(
          () -> underTest.oldTextExistsInTargetLaw(amendingLaw, targetLaw));
    }

    @Test
    void articleEIdIsEmpty() {

      // given
      var amendingLawXml =
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

      Norm amendingLaw = new Norm(XmlMapper.toDocument(amendingLawXml));
      final Norm targetLaw = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw, targetLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For amendingLaw with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): Article eId is empty.");
    }

    @Test
    void noModInArticle() {

      // given
      final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      Node mod =
          amendingLaw
              .getByEId(
                  "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1")
              .get();
      mod.getParentNode().removeChild(mod);

      final Norm targetLaw = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw, targetLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For amendingLaw with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): There is no mod in article with eId hauptteil-1_art-1");
    }

    @Test
    void oldTextIsEmpty() {

      // given
      final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      amendingLaw.getMods().getFirst().setOldText("");

      final Norm targetLaw = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw, targetLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For amendingLaw with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): quotedText[1] (the old, to be replaced, text) is empty in article with eId hauptteil-1_art-1");
    }

    @Test
    void oldTextNotTheSameInTargetLaw() {

      // given
      final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      amendingLaw.getMods().forEach(mod -> mod.setOldText("not the same text as in target law"));

      final Norm targetLaw = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw, targetLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For amendingLaw with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): The replacement text '§ 9 Abs. 1 Satz 2, Abs. 2' in the target law does not equal the replacement text 'not the same text as in target law' in the article with eId hauptteil-1_art-1");
    }

    @Test
    void modTargetHrefIsEmpty() {

      // given
      final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      amendingLaw.getMods().forEach(mod -> mod.setTargetHref(""));

      final Norm targetLaw = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw, targetLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For amendingLaw with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): mod href is empty in article with eId hauptteil-1_art-1");
    }

    @Test
    void targetHrefEidIsEmpty() {

      // given
      final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      amendingLaw
          .getMods()
          .forEach(
              mod ->
                  mod.setTargetHref("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/"));

      final Norm targetLaw = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw, targetLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For amendingLaw with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): The eId in mod href is empty in article with eId hauptteil-1_art-1");
    }

    @Test
    void modHrefCharacterRangeIsEmpty() {
      // given
      final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      amendingLaw
          .getMods()
          .forEach(
              mod ->
                  mod.setTargetHref(
                      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/hauptteil-1_para-20_abs-1/"));

      final Norm targetLaw = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw, targetLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For amendingLaw with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): The character range in mod href is empty in article with eId hauptteil-1_art-1");
    }

    @Test
    void moreThanOneNodeWithGivenDestEidExists() {
      // given
      final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      final Norm targetLaw =
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
          catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw, targetLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For amendingLaw with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): To many matching eIds (hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1) for article hauptteil-1_art-1 in target norm eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1");
    }

    @Test
    void nodeWithGivenDestEidDoesNotExists() {
      // given
      final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      final Norm targetLaw = NormFixtures.loadFromDisk("SimpleNorm.xml");

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw, targetLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For amendingLaw with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): Couldn't load target eId (hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1) element in target law (eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1) for article with eId hauptteil-1_art-1");
    }

    @Test
    void doesNotThrow() {
      // given
      final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      final Norm targetLaw = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // when/then
      Assertions.assertDoesNotThrow(
          () -> underTest.oldTextExistsInTargetLaw(amendingLaw, targetLaw));
    }

    @Test
    void throwsExceptionWhenCharacterRangeIsNotSet() {
      // given
      final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      amendingLaw
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

      amendingLaw
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

      final Norm targetLaw = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw, targetLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For amendingLaw with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): The character range in mod href is empty in article with eId hauptteil-1_art-1");
    }

    @Test
    void throwsExceptionWhenCharacterRangeStartEqualsEnd() {
      // given
      final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      amendingLaw
          .getMods()
          .forEach(
              mod ->
                  mod.setTargetHref(
                      new Href.Builder()
                          .setCharacterRange(new CharacterRange("20-20.xml"))
                          .setEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                          .setEId(
                              "hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
                          .buildAbsolute()
                          .value()));

      amendingLaw
          .getActiveModifications()
          .forEach(
              textMod ->
                  textMod.setDestinationHref(
                      new Href.Builder()
                          .setCharacterRange(new CharacterRange("20-20.xml"))
                          .setEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                          .setEId(
                              "hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
                          .buildAbsolute()
                          .value()));

      final Norm targetLaw = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw, targetLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For amendingLaw with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): The character range in mod href is not valid in article with eId hauptteil-1_art-1. Make sure start is smaller than end 20 < 20.");
    }

    @Test
    void throwsExceptionWhenCharacterRangeStartIsNotSet() {
      // given
      final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      amendingLaw
          .getMods()
          .forEach(
              mod ->
                  mod.setTargetHref(
                      new Href.Builder()
                          .setCharacterRange(new CharacterRange("-20.xml"))
                          .setEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                          .setEId(
                              "hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
                          .buildAbsolute()
                          .value()));

      amendingLaw
          .getActiveModifications()
          .forEach(
              textMod ->
                  textMod.setDestinationHref(
                      new Href.Builder()
                          .setCharacterRange(new CharacterRange("-20.xml"))
                          .setEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                          .setEId(
                              "hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
                          .buildAbsolute()
                          .value()));

      final Norm targetLaw = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw, targetLaw));

      // then
      // TODO Add to log: "For amendingLaw with Eli
      // (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1):"
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "The range (-20) given at article with eId hauptteil-1_art-1 is not valid");
    }

    @Test
    void throwsExceptionWhenCharacterRangeEndIsNotSet() {
      // given
      final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      amendingLaw
          .getMods()
          .forEach(
              mod ->
                  mod.setTargetHref(
                      new Href.Builder()
                          .setCharacterRange(new CharacterRange("0-.xml"))
                          .setEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                          .setEId(
                              "hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
                          .buildAbsolute()
                          .value()));

      amendingLaw
          .getActiveModifications()
          .forEach(
              textMod ->
                  textMod.setDestinationHref(
                      new Href.Builder()
                          .setCharacterRange(new CharacterRange("0-.xml"))
                          .setEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                          .setEId(
                              "hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
                          .buildAbsolute()
                          .value()));

      final Norm targetLaw = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw, targetLaw));

      // then
      // TODO Add to log: "For amendingLaw with Eli
      // (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1):"
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "The range (0-) given at article with eId hauptteil-1_art-1 is not valid");
    }

    @Test
    void ThrowsExceptionWhenCharacterRangeEndIsTooHigh() {
      // given
      final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      // 112 paragraph length
      amendingLaw
          .getActiveModifications()
          .getFirst()
          .setDestinationHref(
              new Href.Builder()
                  .setEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                  .setEId("hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
                  .setCharacterRange(new CharacterRange("1-113"))
                  .buildAbsolute()
                  .value());

      amendingLaw
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

      final Norm targetLaw = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw, targetLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "For amendingLaw with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): The character range in mod href is not valid (target paragraph is to short) in article with eId hauptteil-1_art-1");
    }
  }

  @Test
  void destinationEliIsConsistent() {
    // given
    final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");

    // when/then
    Assertions.assertDoesNotThrow(() -> underTest.destinationEliIsConsistent(amendingLaw));
  }

  @Test
  void ThrowExceptionIfDestinationEliIsNotConsistent() {
    // given
    final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithInconsistentEli.xml");

    // when
    Throwable thrown = catchThrowable(() -> underTest.destinationEliIsConsistent(amendingLaw));

    // then
    assertThat(thrown)
        .isInstanceOf(XmlContentException.class)
        .hasMessageContaining(
            "For amendingLaw with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): Elis are not consistent");
  }

  @Test
  void destinationHrefIsConsistent() {
    // given
    final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");

    // when/then
    Assertions.assertDoesNotThrow(() -> underTest.destinationHrefIsConsistent(amendingLaw));
  }

  @Test
  void ThrowExceptionIfDestinationEidIsNotConsistent() {
    // given
    final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithInconsistentEid.xml");

    // when
    Throwable thrown = catchThrowable(() -> underTest.destinationHrefIsConsistent(amendingLaw));

    // then
    assertThat(thrown)
        .isInstanceOf(XmlContentException.class)
        .hasMessageContaining(
            "For amendingLaw with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): Eids are not consistent");
  }
}
