package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

class ReferenceServiceTest {

  final ReferenceService service = new ReferenceService();

  @Test
  void itDoesNotLookForReferencesBecauseQuotedTextContainReferencesAlready() {
    // Given
    final Norm norm = Norm
      .builder()
      .regelungstexte(
        Set.of(
          new Regelungstext(
            XmlMapper.toDocument(
              """
              <?xml version="1.0" encoding="UTF-8"?><?xml-model href="../../../schema/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                  <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                                  xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.1/ ../../../schema/legalDocML.de-metadaten.xsd                       http://Inhaltsdaten.LegalDocML.de/1.7.1/ ../../../schema/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                  <akn:act name="regelungstext">
                      <akn:body GUID="0B4A8E1F-65EF-4B7C-9E22-E83BA6B73CD8" eId="hauptteil-1">
                          <akn:article GUID="cdbfc728-a070-42d9-ba2f-357945afef06" eId="hauptteil-1_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1" refersTo="hauptaenderung">
                              <akn:num GUID="25a9acae-7463-4490-bc3f-8258b629d7e9" eId="hauptteil-1_art-1_bezeichnung-1">
                                      Artikel 1</akn:num>
                              <akn:heading GUID="92827aa8-8118-4207-9f93-589345f0bab6" eId="hauptteil-1_art-1_überschrift-1">Änderung des
                                  Bundesverfassungsschutzgesetzes</akn:heading>
                              <akn:paragraph eId="hauptteil-1_art-1_abs-1" GUID="48ead358-f901-41d3-a135-e372d5ef97b1">
                                  <akn:num eId="hauptteil-1_art-1_abs-1_bezeichnung-1" GUID="ef3a32d2-df20-4978-914b-cd6288872208">

                                  </akn:num>
                                  <akn:list eId="hauptteil-1_art-1_abs-1_untergl-1" GUID="41675622-ed62-46e3-869f-94d99908b010">
                                      <akn:intro eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1" GUID="5d6fc824-7926-43b4-b243-b0096da183f9">
                                          <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1" GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5"> Das <akn:affectedDocument eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1" GUID="88b3b9f3-e4a8-49c6-9320-b5b42150bcc5"
                                                                                                                                                                               href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1">Vereinsgesetz vom 5. August 1964 (BGBl. I S. 593), das zuletzt durch … geändert worden ist</akn:affectedDocument>, wird wie folgt geändert:</akn:p>
                                      </akn:intro>
                                      <akn:point eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2" GUID="b5fa1383-f26a-4904-a638-f48711fbcf2d">
                                          <akn:num eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_bezeichnung-1" GUID="6f0f92b3-1a51-440c-9137-b44ab9d990ac">
                                                  2.</akn:num>
                                          <akn:content eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1" GUID="6cb14ab5-3a7f-45f4-9e85-00ac2fb0fe5e">
                                              <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1" GUID="db3fbe0f-b758-4cc4-b528-a723cacad94a">
                                                  <akn:mod eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1" GUID="148c2f06-6e33-4af8-9f4a-3da67c888510" refersTo="aenderungsbefehl-ersetzen">In <akn:ref eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_ref-1"
                                                                                                                                                                                                                                                 GUID="61d3036a-d7d9-4fa5-b181-c3345caa3206" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/hauptteil-1_art-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/0-0.xml">§ 20 Absatz 1 Satz 2</akn:ref> wird die Angabe <akn:quotedText
                                                          eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-1" GUID="694459c4-ef66-4f87-bb78-a332054a2216" startQuote="„" endQuote="“">§ 9 Abs. 1 Satz 2, Abs. 2</akn:quotedText> durch die Wörter <akn:quotedText
                                                          eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-2" GUID="dd25bdb6-4ef4-4ef5-808c-27579b6ae196" startQuote="„" endQuote="“">§ 9 Absatz 1 <akn:ref>Satz 2</akn:ref>, Absatz 2 oder 3</akn:quotedText> ersetzt.</akn:mod>
                                              </akn:p>
                                          </akn:content>
                                      </akn:point>
                                  </akn:list>
                              </akn:paragraph>
                          </akn:article>
                      </akn:body>
                  </akn:act>
              </akn:akomaNtoso>
                    """
            )
          )
        )
      )
      .build();

    // When
    final String result = service.findAndCreateReferences(norm);

    // Then
    final Diff diff = DiffBuilder
      .compare(Input.from(XmlMapper.toDocument(result)))
      .withTest(Input.from(norm.getDocument()))
      .build();
    assertThat(diff.hasDifferences()).isFalse();
  }

  @Test
  void itDoesNotLookForReferencesBecauseQuotedStructureContainReferencesAlready() {
    // Given
    final Norm norm = Fixtures.loadNormFromDisk("NormWithReferencesFound.xml");

    // When
    final String result = service.findAndCreateReferences(norm);

    // Then
    final Diff diff = DiffBuilder
      .compare(Input.from(XmlMapper.toDocument(result)))
      .withTest(Input.from(norm.getDocument()))
      .build();
    assertThat(diff.hasDifferences()).isFalse();
  }

  @Test
  void ifFindsAndCreatesReferences() {
    // Given
    final Norm norm = Fixtures.loadNormFromDisk("NormWithReferencesToFind.xml");

    // When
    final String result = service.findAndCreateReferences(norm);

    // Then
    final Norm expectedUpdatedNorm = Fixtures.loadNormFromDisk("NormWithReferencesFound.xml");
    final Diff diff = DiffBuilder
      .compare(Input.from(XmlMapper.toDocument(result)))
      .ignoreElementContentWhitespace()
      .withTest(Input.from(expectedUpdatedNorm.getDocument()))
      .withAttributeFilter(attribute -> !attribute.getName().equals("GUID"))
      .build();
    assertThat(diff.hasDifferences()).isFalse();
  }

  @Test
  void itDoesNotFindReferencesInNum() {
    // Given
    final Norm norm = Fixtures.loadNormFromDisk("NormWithReferencesInNumToSkip.xml");
    // When
    final String result = service.findAndCreateReferences(norm);

    // Then
    final Norm sameNormReload = Fixtures.loadNormFromDisk("NormWithReferencesInNumToSkip.xml");
    final Diff diff = DiffBuilder
      .compare(Input.from(XmlMapper.toDocument(result)))
      .withTest(Input.from(sameNormReload.getDocument()))
      .ignoreWhitespace()
      .withAttributeFilter(attribute -> !attribute.getName().equals("GUID"))
      .build();
    assertThat(diff.hasDifferences()).isFalse();
  }
}
