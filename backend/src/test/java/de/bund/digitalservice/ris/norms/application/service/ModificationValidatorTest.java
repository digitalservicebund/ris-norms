package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.service.DBService;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlContentException;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ModificationValidatorTest {
  private final DBService dbService = mock(DBService.class);
  private final ModificationValidator underTest = new ModificationValidator(dbService);

  @Test
  void allAffectedDocumentsExist() {
    // given
    final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
    when(dbService.loadNorm(any()))
        .thenReturn(
            Optional.of(
                new Norm(
                    XmlMapper.toDocument(
                        "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n <test>content</test>"))));

    // when
    underTest.affectedDocumentsExists(amendingLaw);

    // then
    verify(dbService, times(1))
        .loadNorm(
            argThat(
                argument ->
                    Objects.equals(
                        argument.eli(),
                        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")));
  }

  @Test
  void normDoesNotExist() {
    // given
    final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
    when(dbService.loadNorm(any())).thenReturn(Optional.empty());

    // when
    Throwable thrown = catchThrowable(() -> underTest.affectedDocumentsExists(amendingLaw));

    // then
    assertThat(thrown).isInstanceOf(XmlContentException.class);
  }

  @Test
  void nodeWithGivenDestEidExists() {
    // given
    final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
    final Norm targetLaw = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");
    when(dbService.loadNorm(any())).thenReturn(Optional.of(targetLaw));

    // when/then
    Assertions.assertDoesNotThrow(() -> underTest.nodeWithGivenDestEidExists(amendingLaw));
  }

  @Test
  void nodeWithGivenDestEidDoesNotExists() {
    // given
    final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
    final Norm targetLaw = NormFixtures.loadFromDisk("SimpleNorm.xml");
    when(dbService.loadNorm(any())).thenReturn(Optional.of(targetLaw));

    // when
    Throwable thrown = catchThrowable(() -> underTest.nodeWithGivenDestEidExists(amendingLaw));

    // then
    assertThat(thrown)
        .isInstanceOf(XmlContentException.class)
        .hasMessageContaining("No matching eIds found");
  }

  @Test
  void moreThanOneNodeWithGivenDestEidExists() {
    // given
    final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
    when(dbService.loadNorm(any()))
        .thenReturn(
            Optional.of(
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
    """))));

    // when
    Throwable thrown = catchThrowable(() -> underTest.nodeWithGivenDestEidExists(amendingLaw));

    // then
    assertThat(thrown)
        .isInstanceOf(XmlContentException.class)
        .hasMessageContaining(
            "To many matching eIds for hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1 in target norm eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1");
  }

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
    void emptyAffectedDocumentHref() {

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
                                     <akn:article eId="hauptteil-1_art-1"
                                                  GUID="cdbfc728-a070-42d9-ba2f-357945afef06"
                                                  period="#geltungszeitgr-1"
                                                  refersTo="hauptaenderung">
                                         <akn:paragraph eId="hauptteil-1_art-1_abs-1"
                                                        GUID="48ead358-f901-41d3-a135-e372d5ef97b1">
                                             <akn:list eId="hauptteil-1_art-1_abs-1_untergl-1"
                                                       GUID="41675622-ed62-46e3-869f-94d99908b010">
                                                 <akn:intro eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1"
                                                            GUID="5d6fc824-7926-43b4-b243-b0096da183f9">
                                                     <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1"
                                                            GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5">Das <akn:affectedDocument
                                                             eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1"
                                                             GUID="88b3b9f3-e4a8-49c6-9320-b5b42150bcc5"
                                                             href="">Vereinsgesetz vom
                                                         5. August 1964 (BGBl. I S. 593), das zuletzt
                                                         durch … geändert worden ist</akn:affectedDocument>, wird wie folgt geändert:
                                                     </akn:p>
                                                 </akn:intro>
                                             </akn:list>
                                         </akn:paragraph>
                                     </akn:article>
                                 </akn:body>
                             </akn:act>
                         </akn:akomaNtoso>
                      """;

      Norm amendingLaw = new Norm(XmlMapper.toDocument(amendingLawXml));

      // when
      Throwable thrown = catchThrowable(() -> underTest.throwErrorNoDestinationSet(amendingLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining("Empty aknMod for article with eId hauptteil-1_art-1");
    }
  }

  @Test
  void validDestinationRange() {

    // given
    // TODO is this okay or shall this be in a separate file?
    var amendingLawXml =
        """
              <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
              <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/"
                              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                              xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                  <akn:act name="regelungstext">
                      <!-- Metadaten -->
                      <akn:meta eId="meta-1" GUID="7e5837c8-b967-45be-924b-c95956c4aa94">
                          <akn:analysis eId="meta-1_analysis-1" GUID="c0eb49c8-bf39-4a4a-b324-3b0feb88c1f1"
                                        source="attributsemantik-noch-undefiniert">
                              <akn:activeModifications eId="meta-1_analysis-1_activemod-1"
                                                       GUID="cd241744-ace4-436c-a0e3-dc1ee8caf3ac">
                                  <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-1"
                                                  GUID="2e5533d3-d0e3-43ba-aa1a-5859d108eb46" type="substitution">
                                      <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-1_source-1"
                                                  GUID="8b3e1841-5d63-4400-96ae-214f6ee28db6"
                                                  href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"/>
                                      <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-1_destination-1"
                                                       GUID="94c1e417-e849-4269-8320-9f0173b39626"
                                                       href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/hauptteil-1_para-20_abs-1/THIS_DOES_NOT_WORK.xml"/>
                                      <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-1_gelzeitnachw-1"
                                                 GUID="6f5eabe9-1102-4d29-9d25-a44643354519"
                                                 period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
                                  </akn:textualMod>
                              </akn:activeModifications>
                          </akn:analysis>
                      </akn:meta>
                  </akn:act>
              </akn:akomaNtoso>
            """;

    Norm amendingLaw = new Norm(XmlMapper.toDocument(amendingLawXml));

    // when
    Throwable thrown = catchThrowable(() -> underTest.modHasValidDestRangeForDestNode(amendingLaw));

    // then
    assertThat(thrown)
        .isInstanceOf(XmlContentException.class)
        .hasMessageContaining(
            "Some textual modifications have broken destination ranges. Here are the according textualMod eIds: meta-1_analysis-1_activemod-1_textualmod-1");
  }

  @Nested
  class oldTextExistsInTargetLaw {

    @Test
    void articleEIdIsEmpty() {

      // given
      // TODO is this okay or shall this be in a separate file?
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

      // when
      Throwable thrown = catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining("Article eId is empty.");
    }

    @Test
    void affectedDocumentEliIsEmpty() {

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
                                <akn:paragraph eId="hauptteil-1_art-1_abs-1"
                                               GUID="48ead358-f901-41d3-a135-e372d5ef97b1">
                                    <akn:list eId="hauptteil-1_art-1_abs-1_untergl-1"
                                              GUID="41675622-ed62-46e3-869f-94d99908b010">
                                        <akn:intro eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1"
                                                   GUID="5d6fc824-7926-43b4-b243-b0096da183f9">
                                            <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1"
                                                   GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5">Das <akn:affectedDocument
                                                    eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1"
                                                    GUID="88b3b9f3-e4a8-49c6-9320-b5b42150bcc5"
                                                    href="">Vereinsgesetz vom
                                                5. August 1964 (BGBl. I S. 593), das zuletzt
                                                durch … geändert worden ist</akn:affectedDocument>, wird wie folgt geändert:
                                            </akn:p>
                                        </akn:intro>
                                    </akn:list>
                                </akn:paragraph>
                            </akn:article>
                        </akn:body>
                    </akn:act>
                </akn:akomaNtoso>
              """;

      Norm amendingLaw = new Norm(XmlMapper.toDocument(amendingLawXml));

      // when
      Throwable thrown = catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "AffectedDocument href is empty in article with eId hauptteil-1_art-1");
    }

    @Test
    void noModInArticle() {

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
                                          </akn:list>
                                      </akn:paragraph>
                                  </akn:article>
                              </akn:body>
                          </akn:act>
                      </akn:akomaNtoso>
                  """;

      Norm amendingLaw = new Norm(XmlMapper.toDocument(amendingLawXml));

      // when
      Throwable thrown = catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining("There is no mod in article with eId hauptteil-1_art-1");
    }

    @Test
    void oldTextIsEmpty() {

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
                                                                  href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/hauptteil-1_para-20_abs-1/100-126.xml">
                                                              § 20 Absatz 1 Satz 2
                                                          </akn:ref> wird
                                                              die Angabe <akn:quotedText
                                                                      eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-1"
                                                                      GUID="694459c4-ef66-4f87-bb78-a332054a2216"
                                                                      startQuote="„"
                                                                      endQuote="“"></akn:quotedText> durch die
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

      // when
      Throwable thrown = catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "quotedText[1] (the old, to be replaced, text) is empty in article with eId hauptteil-1_art-1");
    }

    @Test
    void targetHrefIsEmpty() {

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
                                                                  href="">
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

      // when
      Throwable thrown = catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining("mod href is empty in article with eId hauptteil-1_art-1");
    }

    @Test
    void targetHrefEidIsEmpty() {

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
                                                                  href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/">
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

      // when
      Throwable thrown = catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw));

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

      // when
      Throwable thrown = catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "The character range in mod href is empty in article with eId hauptteil-1_art-1");
    }

    @Test
    void normDoesNotExist() {
      // given
      final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      when(dbService.loadNorm(any())).thenReturn(Optional.empty());

      // when
      Throwable thrown = catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "Couldn't load target law by Eli: The affectedDocument href may hold an invalid value in article with eId hauptteil-1_art-1");
    }

    @Test
    void nodeWithGivenDestEidDoesNotExist() {
      // given
      final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      when(dbService.loadNorm(any()))
          .thenReturn(
              Optional.of(
                  new Norm(
                      XmlMapper.toDocument(
                          "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n <test>content</test>"))));

      // when
      Throwable thrown = catchThrowable(() -> underTest.oldTextExistsInTargetLaw(amendingLaw));

      // then
      assertThat(thrown)
          .isInstanceOf(XmlContentException.class)
          .hasMessageContaining(
              "Couldn't load target eId element in target law for article with eId hauptteil-1_art-1");
    }

    @Test
    void doesNotThrow() {
      // given
      final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
      final Norm targetLaw = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
      when(dbService.loadNorm(any())).thenReturn(Optional.of(targetLaw));

      // when/then
      Assertions.assertDoesNotThrow(() -> underTest.oldTextExistsInTargetLaw(amendingLaw));
    }
  }

  @Test
  void destinationEliIsConsistent() {
    // given
    final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithMods.xml");
    final Norm targetLaw = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");
    when(dbService.loadNorm(any())).thenReturn(Optional.of(targetLaw));

    // when/then
    Assertions.assertDoesNotThrow(() -> underTest.destinationEliIsConsistent(amendingLaw));
  }

  @Test
  void ThrowExceptionIfDestinationEliIsNotConsistent() {
    // given
    final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithInconsistentEli.xml");
    final Norm targetLaw = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");
    when(dbService.loadNorm(any())).thenReturn(Optional.of(targetLaw));

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
    final Norm targetLaw = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");
    when(dbService.loadNorm(any())).thenReturn(Optional.of(targetLaw));

    // when/then
    Assertions.assertDoesNotThrow(() -> underTest.destinationHrefIsConsistent(amendingLaw));
  }

  @Test
  void ThrowExceptionIfDestinationEidIsNotConsistent() {
    // given
    // TODO rename and fix typo in xml name -> NormWithInconsistentEid
    final Norm amendingLaw = NormFixtures.loadFromDisk("NormWithInconsistenEid.xml");
    final Norm targetLaw = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");
    when(dbService.loadNorm(any())).thenReturn(Optional.of(targetLaw));

    // when
    Throwable thrown = catchThrowable(() -> underTest.destinationHrefIsConsistent(amendingLaw));

    // then
    assertThat(thrown)
        .isInstanceOf(XmlContentException.class)
        .hasMessageContaining("Eids are not consistent");
  }
}
