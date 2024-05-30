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
              "ActiveModification Destination Href is empty where textualMod eId is meta-1_analysis-1_activemod-1_textualmod-1");
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
              "ActiveModification Destination Href holds an empty (more general: invalid) Eli where textualMod eId is meta-1_analysis-1_activemod-1_textualmod-1");
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
              "AffectedDocument href is empty in article with eId hauptteil-1_art-1");
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
          .hasMessageContaining("Article eId is empty.");
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
          .hasMessageContaining("There is no mod in article with eId hauptteil-1_art-1");
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
              "quotedText[1] (the old, to be replaced, text) is empty in article with eId hauptteil-1_art-1");
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
              "The replacement text '§ 9 Abs. 1 Satz 2, Abs. 2' in the target law does not equal the replacement text 'not the same text as in target law' in the article with eId hauptteil-1_art-1");
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
          .hasMessageContaining("mod href is empty in article with eId hauptteil-1_art-1");
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
              "The eId in mod href is empty in article with eId hauptteil-1_art-1");
    }

    @Test
    void characterRangeIsEmpty() {

      // given
      // TODO is this okay or shall this be in a separate file?
      var amendingLawXml =
          """
                          <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                          <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/"
                                          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                                          xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                              <akn:act name="regelungstext">
                                  <akn:body eId="hauptteil-1" GUID="0B4A8E1F-65EF-4B7C-9E22-E83BA6B73CD8">
                                      <!-- Artikel 1 : Hauptänderung -->
                                      <akn:article eId="hauptteil-1_art-1"
                                                   GUID="cdbfc728-a070-42d9-ba2f-357945afef06"
                                                   period="#geltungszeitgr-1"
                                                   refersTo="hauptaenderung">
                                          <akn:num eId="hauptteil-1_art-1_bezeichnung-1"
                                                   GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">
                                              <akn:marker eId="hauptteil-1_art-1_bezeichnung-1_zaehlbez-1"
                                                          GUID="81c9c481-9427-4f03-9f51-099aa9b2201e"
                                                          name="1"/>Artikel 1
                                          </akn:num>
                                          <akn:heading eId="hauptteil-1_art-1_überschrift-1"
                                                       GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes
                                          </akn:heading>
                                          <!-- Absatz (1) -->
                                          <akn:paragraph eId="hauptteil-1_art-1_abs-1"
                                                         GUID="48ead358-f901-41d3-a135-e372d5ef97b1">
                                              <akn:num eId="hauptteil-1_art-1_abs-1_bezeichnung-1"
                                                       GUID="ef3a32d2-df20-4978-914b-cd6288872208">
                                                  <akn:marker eId="hauptteil-1_art-1_abs-1_bezeichnung-1_zaehlbez-1"
                                                              GUID="eab5a7e7-b649-4c23-b495-648b8ec71843"
                                                              name="1"/>
                                              </akn:num>
                                              <akn:list eId="hauptteil-1_art-1_abs-1_untergl-1"
                                                        GUID="41675622-ed62-46e3-869f-94d99908b010">
                                                  <akn:intro eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1"
                                                             GUID="5d6fc824-7926-43b4-b243-b0096da183f9">
                                                      <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1"
                                                             GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5">Das <akn:affectedDocument
                                                              eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1"
                                                              GUID="88b3b9f3-e4a8-49c6-9320-b5b42150bcc5"
                                                              href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1">Vereinsgesetz vom
                                                          5. August 1964 (BGBl. I S. 593), das zuletzt
                                                          durch … geändert worden ist</akn:affectedDocument>, wird wie folgt geändert:
                                                      </akn:p>
                                                  </akn:intro>
                                                  <!-- Nummer 2 -->
                                                  <akn:point eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2"
                                                             GUID="b5fa1383-f26a-4904-a638-f48711fbcf2d">
                                                      <akn:num eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1"
                                                               GUID="6f0f92b3-1a51-440c-9137-b44ab9d990ac">
                                                          <akn:marker
                                                                  eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1_zaehlbez-1"
                                                                  GUID="5d7d54f0-8a4e-4d8f-b5d0-93d0ca393e82"
                                                                  name="2"/>2.
                                                      </akn:num>
                                                      <akn:content eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1"
                                                                   GUID="6cb14ab5-3a7f-45f4-9e85-00ac2fb0fe5e">
                                                          <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1"
                                                                 GUID="db3fbe0f-b758-4cc4-b528-a723cacad94a">
                                                              <akn:mod
                                                                      eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"
                                                                      GUID="148c2f06-6e33-4af8-9f4a-3da67c888510"
                                                                      refersTo="aenderungsbefehl-ersetzen">In <akn:ref
                                                                      eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_ref-1"
                                                                      GUID="61d3036a-d7d9-4fa5-b181-c3345caa3206"
                                                                      href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/hauptteil-1_para-20_abs-1/">
                                                                  § 20 Absatz 1 Satz 2
                                                              </akn:ref> wird
                                                                  die Angabe <akn:quotedText
                                                                          eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-1"
                                                                          GUID="694459c4-ef66-4f87-bb78-a332054a2216"
                                                                          startQuote="„"
                                                                          endQuote="“">§ 9 Abs. 1 Satz 2, Abs. 2
                                                                  </akn:quotedText> durch die
                                                                  Wörter
                                                                  <akn:quotedText
                                                                          eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-2"
                                                                          GUID="dd25bdb6-4ef4-4ef5-808c-27579b6ae196"
                                                                          startQuote="„"
                                                                          endQuote="“">§ 9 Absatz 1 Satz 2, Absatz 2 oder 3
                                                                  </akn:quotedText>
                                                                  ersetzt.
                                                              </akn:mod>
                                                          </akn:p>
                                                      </akn:content>
                                                  </akn:point>
                                              </akn:list>
                                          </akn:paragraph>
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
              "The character range in mod href is empty in article with eId hauptteil-1_art-1");
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
              "To many matching eIds (hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1) for article hauptteil-1_art-1 in target norm eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1");
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
              "Couldn't load target eId (hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1) element in target law (eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1) for article with eId hauptteil-1_art-1");
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
                          .setCharacterRange(new CharacterRange(""))
                          .buildInternalReference()
                          .value()));

      amendingLaw
          .getActiveModifications()
          .forEach(
              textMod ->
                  textMod.setDestinationHref(
                      new Href.Builder()
                          .setCharacterRange(new CharacterRange(""))
                          .buildInternalReference()
                          .value()));

      final Norm targetLaw = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // when
      Throwable thrown =
          catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw, targetLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "The character range in mod href is empty in article with eId hauptteil-1_art-1");
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
              "The character range in mod href is not valid in article with eId hauptteil-1_art-1. Make sure start is smaller than end 20 < 20.");
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
              "The character range in mod href is not valid (target paragraph is to short) in article with eId hauptteil-1_art-1");
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
        .hasMessageContaining("Elis are not consistent");
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
        .hasMessageContaining("Eids are not consistent");
  }
}
