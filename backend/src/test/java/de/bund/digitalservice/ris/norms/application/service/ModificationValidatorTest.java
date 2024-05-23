package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.service.DBService;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
import java.util.Objects;
import java.util.Optional;
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
    assertThat(thrown).isInstanceOf(XmlProcessingException.class);
  }

  @Test
  void noDestinationEli() {

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
        .isInstanceOf(XmlProcessingException.class)
        .hasMessageContaining(
            "Some articles have empty affected document Elis. Here are the according eIds: hauptteil-1_art-1");
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
        .isInstanceOf(XmlProcessingException.class)
        .hasMessageContaining(
            "Some textual modifications have broken destination ranges. Here are the according textualMod eIds: meta-1_analysis-1_activemod-1_textualmod-1");
  }
}
