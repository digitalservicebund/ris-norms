package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentWorkEli;
import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

class RegelungstextTest {

  @Test
  void getWorkEli() {
    // given
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );

    // when
    final var actualEli = regelungstext.getWorkEli();

    // then
    assertThat(actualEli)
      .isEqualTo(DokumentWorkEli.fromString("eli/bund/bgbl-1/1964/s593/regelungstext-1"));
  }

  @Test
  void getExpressionEli() {
    // given
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );
    DokumentExpressionEli expectedEli = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
    );

    // when
    final var actualEli = regelungstext.getExpressionEli();

    // then
    assertThat(actualEli).isEqualTo(expectedEli);
  }

  @Test
  void getManifestationEli() {
    // given
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );
    DokumentManifestationEli expectedEli = DokumentManifestationEli.fromString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );

    // when
    final var actualEli = regelungstext.getManifestationEli();

    // then
    assertThat(actualEli).isEqualTo(expectedEli);
  }

  @Test
  void getErrorWhenEliDoesntExist() {
    // given
    final String xml =
      """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                     <akn:act name="regelungstext">
                     </akn:act>
                  </akn:akomaNtoso>
      """.strip();

    final var regelungstext = new Regelungstext(XmlMapper.toDocument(xml));

    assertThatThrownBy(regelungstext::getExpressionEli)
      .isInstanceOf(MandatoryNodeNotFoundException.class);
  }

  @Test
  void getGuid() {
    // given
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );

    // when
    final UUID actualGuid = regelungstext.getGuid();

    // then
    assertThat(actualGuid).isEqualTo(UUID.fromString("d04791fc-dcdc-47e6-aefb-bc2f7aaee151"));
  }

  @Test
  void getTitle() {
    // given
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );
    final String expectedTitle = "Gesetz zur Regelung des öffentlichen Vereinsrechts";

    // when
    final String actualTitle = regelungstext.getTitle().get();

    // then
    assertThat(actualTitle).contains(expectedTitle);
  }

  @Test
  void getShortTitle() {
    // given
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );

    // when
    final var shortTitle = regelungstext.getShortTitle();

    // then
    assertThat(shortTitle).contains("Vereinsgesetz");
  }

  @Test
  void getDateAusfertigung() {
    // given
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );

    // when
    final Optional<String> dateAusfertigung = regelungstext.getDateAusfertigung();

    // then
    assertThat(dateAusfertigung).isNotNull().contains("1964-08-05");
  }

  @Test
  void getFRBRname() {
    // given
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );
    final String expectedFRBRname = "BGBl. I";

    // when
    final String actualFRBRname = regelungstext.getMeta().getFRBRWork().getFRBRname().get();

    // then
    assertThat(actualFRBRname).contains(expectedFRBRname);
  }

  @Test
  void getFRBRnumber() {
    // given
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );
    final String expectedFRBRname = "s593";

    // when
    final String actualAnnouncementGazette = regelungstext
      .getMeta()
      .getFRBRWork()
      .getFRBRnumber()
      .get();

    // then
    assertThat(actualAnnouncementGazette).contains(expectedFRBRname);
  }

  @Test
  void getPublishingDate() {
    // given
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );

    // when
    final String actualFBRDateVerkuendung = regelungstext.getMeta().getFRBRWork().getFBRDate();

    // then
    assertThat(actualFBRDateVerkuendung).isEqualTo("1964-08-05");
  }

  @Test
  void getMeta() {
    // given
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );

    // when
    final Meta meta = regelungstext.getMeta();

    // then
    assertThat(meta).isNotNull();
  }

  @Test
  void getMetaNotFound() {
    // given
    final String xml =
      """
          <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
          <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                 http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
         <akn:act name="regelungstext">
         </akn:act>
      </akn:akomaNtoso>
      """.strip();

    final var regelungstext = new Regelungstext(XmlMapper.toDocument(xml));

    assertThatThrownBy(regelungstext::getMeta).isInstanceOf(MandatoryNodeNotFoundException.class);
  }

  @Test
  void getArticles() {
    // given
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
    );
    final var expectedNumberOfArticles = 2;
    final var firstExpectedHeading = "Änderung des Vereinsgesetzes";
    final var secondExpectedHeading = "Inkrafttreten";

    // when
    final List<Article> actualArticles = regelungstext.getArticles();

    // then
    assertThat(actualArticles).hasSize(expectedNumberOfArticles);
    assertThat(actualArticles.getFirst().getHeading()).contains(firstExpectedHeading);
    assertThat(actualArticles.getFirst().getEnumeration()).contains("Artikel 1");
    assertThat(actualArticles.get(0).getEid()).hasToString("hauptteil-1_art-1");
    assertThat(actualArticles.get(0).getAffectedDocumentEli())
      .contains(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
        )
      );

    assertThat(actualArticles.get(1).getHeading()).contains(secondExpectedHeading);
    assertThat(actualArticles.get(1).getEnumeration()).contains("Artikel 3");
    assertThat(actualArticles.get(1).getEid()).hasToString("hauptteil-1_art-2");
    assertThat(actualArticles.get(1).getAffectedDocumentEli()).isNotPresent();
  }

  @Test
  void returnsEmptyListIfNoArticlesAreFound() {
    // given
    final String xml =
      """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                   http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
           <akn:act name="regelungstext">
              <akn:body eId="hauptteil-1" GUID="0B4A8E1F-65EF-4B7C-9E22-E83BA6B73CD8">
              </akn:body>
           </akn:act>
        </akn:akomaNtoso>
      """;

    final var regelungstext = new Regelungstext(XmlMapper.toDocument(xml));

    // when
    final List<Article> actualArticles = regelungstext.getArticles();

    // then
    assertThat(actualArticles).isEmpty();
  }

  @Test
  void equalsShouldEqualWithSameXml() {
    // given
    final var regelungstext1 = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );
    final var regelungstext2 = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );

    // then
    assertThat(regelungstext1).isEqualTo(regelungstext2);
  }

  @Test
  void equalsShouldNotEqualWithDifferentXml() {
    // given
    final var regelungstext1 = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );
    final var regelungstext2 = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/2017-03-15/regelungstext-1.xml"
    );

    // then
    assertThat(regelungstext1).isNotEqualTo(regelungstext2);
  }

  @Test
  void hashCodeShouldBeTheSameWithSameXml() {
    // given
    final var regelungstext1 = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );
    final var regelungstext2 = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );

    // then
    assertThat(regelungstext1.hashCode()).hasSameHashCodeAs(regelungstext2.hashCode());
  }

  @Test
  void hashCodeShouldBeDifferentWithDifferentXml() {
    // given
    final var regelungstext1 = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );
    final var regelungstext2 = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/2017-03-15/regelungstext-1.xml"
    );

    // then
    assertThat(regelungstext1.hashCode()).isNotEqualTo(regelungstext2.hashCode());
  }

  @Test
  void extractTimeBoundariesFromXml() {
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );
    final List<TimeBoundary> actualBoundaries = regelungstext.getTimeBoundaries();

    assertThat(actualBoundaries.getFirst().getEventRef().getDate())
      .contains(LocalDate.parse("1964-09-21"));

    assertThat(actualBoundaries.getFirst().getEventRefEid())
      .isEqualTo("meta-1_lebzykl-1_ereignis-2");
  }

  @Test
  void extractTimeBoundariesFromTemporalGroups() {
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );
    final List<TimeBoundary> actualBoundaries = regelungstext.getTimeBoundaries(
      regelungstext.getMeta().getTemporalData().getTemporalGroups()
    );

    assertThat(actualBoundaries.getFirst().getEventRef().getDate())
      .contains(LocalDate.parse("1964-09-21"));

    assertThat(actualBoundaries.getFirst().getEventRefEid())
      .isEqualTo("meta-1_lebzykl-1_ereignis-2");
  }

  @Test
  void getTimeBoundariesEmpty() {
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      RegelungstextTest.class,
      "vereinsgesetz-without-inkrafttreten-time-boundary.xml"
    );
    final List<TimeBoundary> timeBoundaries = regelungstext.getTimeBoundaries();
    assertThat(timeBoundaries).isEmpty();
  }

  @Test
  void addTimeBoundary() {
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );
    regelungstext.addTimeBoundary(LocalDate.parse("2024-01-02"), EventRefType.GENERATION);
    final List<TimeBoundary> timeBoundaries = regelungstext.getTimeBoundaries();

    // old one still there
    assertThat(timeBoundaries.get(0).getEventRef().getDate())
      .contains(LocalDate.parse("1964-09-21"));

    assertThat(timeBoundaries.get(0).getEventRefEid()).isEqualTo("meta-1_lebzykl-1_ereignis-2");
    assertThat(
      timeBoundaries
        .get(0)
        .getTimeInterval()
        .getElement()
        .getParentNode()
        .getAttributes()
        .getNamedItem("eId")
        .getNodeValue()
    )
      .contains("meta-1_geltzeiten-1_geltungszeitgr-1");
    assertThat(
      timeBoundaries
        .get(0)
        .getTimeInterval()
        .getElement()
        .getParentNode()
        .getAttributes()
        .getNamedItem("GUID")
        .getNodeValue()
    )
      .contains("2b728878-4b15-4b14-89cd-ecc270202a81");
    assertThat(timeBoundaries.get(0).getTimeIntervalEid())
      .isEqualTo("meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1");
    assertThat(timeBoundaries.get(0).getTimeInterval().getElement().getAttribute("GUID"))
      .contains("10819a15-54fe-4924-a469-4a097f9c0e41");
    assertThat(timeBoundaries.get(0).getTimeInterval().getElement().getAttribute("refersTo"))
      .contains("geltungszeit");
    assertThat(timeBoundaries.get(0).getTimeInterval().getElement().getAttribute("start"))
      .contains("#" + timeBoundaries.get(0).getEventRefEid());
    // new one added
    assertThat(timeBoundaries.get(1).getEventRef().getDate())
      .contains(LocalDate.parse("2024-01-02"));

    assertThat(timeBoundaries.get(1).getEventRefEid()).isEqualTo("meta-1_lebzykl-1_ereignis-3");
    assertThat(
      timeBoundaries
        .get(1)
        .getTimeInterval()
        .getElement()
        .getParentNode()
        .getAttributes()
        .getNamedItem("eId")
        .getNodeValue()
    )
      .contains("meta-1_geltzeiten-1_geltungszeitgr-2");
    assertThat(
      timeBoundaries
        .get(1)
        .getTimeInterval()
        .getElement()
        .getParentNode()
        .getAttributes()
        .getNamedItem("GUID")
        .getNodeValue()
    )
      .isNotEmpty();
    assertThat(timeBoundaries.get(1).getTimeIntervalEid())
      .isEqualTo("meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1");
    assertThat(timeBoundaries.get(1).getTimeInterval().getElement().getAttribute("GUID"))
      .isNotEmpty();
    assertThat(timeBoundaries.get(1).getTimeInterval().getElement().getAttribute("refersTo"))
      .contains("geltungszeit");
    assertThat(timeBoundaries.get(1).getTimeInterval().getElement().getAttribute("start"))
      .contains("#" + timeBoundaries.get(1).getEventRefEid());
  }

  @Test
  void deleteTimeBoundary() {
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );
    final TimeBoundaryChangeData timeBoundaryToDelete = new TimeBoundaryChangeData(
      "meta-1_lebzykl-1_ereignis-2",
      LocalDate.parse("1964-09-21")
    );

    regelungstext.deleteTimeBoundary(timeBoundaryToDelete);
    final List<TimeBoundary> timeBoundaries = regelungstext.getTimeBoundaries();

    assertThat(timeBoundaries).isEmpty();
  }

  @Test
  void getStartDateForTemporalGroup() {
    // given
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );

    // when
    final var date = regelungstext.getStartDateForTemporalGroup(
      new EId("meta-1_geltzeiten-1_geltungszeitgr-1")
    );

    // then
    assertThat(date).contains("1964-09-21");
  }

  @Test
  void getStartEventRefForTemporalGroup() {
    // given
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );

    // when
    final var eId = regelungstext.getStartEventRefForTemporalGroup(
      new EId("meta-1_geltzeiten-1_geltungszeitgr-1")
    );

    // then
    assertThat(eId).contains(new EId("meta-1_lebzykl-1_ereignis-2"));
  }

  @Test
  void getStartDateForEventRef() {
    // given
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );

    // when
    final var date = regelungstext.getStartDateForEventRef(new EId("meta-1_lebzykl-1_ereignis-1"));

    // then
    assertThat(date).contains("1964-08-05");
  }

  @Nested
  class createElementWithEidAndGuid {

    @Test
    void itShouldCreatesANewElement() {
      // given
      final var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      final Node parentNode = NodeParser
        .getNodeFromExpression("//act/meta", regelungstext.getDocument())
        .orElseThrow();

      // when
      final Node createdNode = NodeCreator.createElementWithEidAndGuid("akn:analysis", parentNode);

      // then
      assertThat(
        NodeParser.getNodeFromExpression("//act/meta/analysis", regelungstext.getDocument())
      )
        .contains(createdNode);
      assertThat(NodeParser.getValueFromExpression("@eId", createdNode))
        .contains("meta-1_analysis-1");
    }
  }

  @Nested
  class getOrCreateTemporalDataNode {

    @Test
    void itShouldCreatesTheTemporalDataNodeIfItDoesNotExist() {
      // given
      final var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );

      // when
      final var temporalData = regelungstext.getMeta().getOrCreateTemporalData();

      // then
      assertThat(temporalData).isNotNull();
      assertThat(
        NodeParser.getNodeFromExpression("//act//temporalData", regelungstext.getDocument())
      )
        .contains(temporalData.getElement());
    }

    @Test
    void itShouldFindTheTemporalDataNodeIfItExist() {
      // given
      final var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );

      // when
      final var temporalData = regelungstext.getMeta().getTemporalData();

      // then
      assertThat(temporalData).isNotNull();
      assertThat(NodeParser.getValueFromExpression("@GUID", temporalData.getElement()))
        .contains("0b03ee18-0131-47ec-bd46-519d60209cc7");
    }
  }

  @Nested
  class deleteByEId {

    @Test
    void itShouldDeleteTheNodeOfTheEId() {
      // given
      final var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );

      // when
      regelungstext.deleteByEId("meta-1_ident-1_frbrexpression-1_frbrthis-1");
      regelungstext.deleteByEId("meta-1_ident-1_frbrmanifestation-1_frbrthis-1");

      // then
      assertThatThrownBy(regelungstext::getExpressionEli)
        .isInstanceOf(MandatoryNodeNotFoundException.class);
    }
  }

  @Nested
  class deleteTemporalGroupIfUnused {

    @Test
    void itShouldDeleteTemporalGroupIfUnused() {
      // given
      final var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );
      // delete the mod so the temporal group is unused
      regelungstext.deleteByEId("meta-1_analysis-1_activemod-1");

      // when
      regelungstext.deleteTemporalGroupIfUnused("meta-1_geltzeiten-1_geltungszeitgr-1");

      // then
      assertThat(regelungstext.getElementByEId("meta-1_analysis-1_activemod-1")).isEmpty();
    }

    @Test
    void itShouldNotDeleteTemporalGroupIfUsed() {
      // given
      final var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );

      // when
      regelungstext.deleteTemporalGroupIfUnused("meta-1_geltzeiten-1_geltungszeitgr-1");

      // then
      assertThat(regelungstext.getElementByEId("meta-1_geltzeiten-1_geltungszeitgr-1")).isPresent();
    }
  }

  @Nested
  class deleteEventRefIfUnused {

    @Test
    void itShouldDeleteEventRefIfUnused() {
      // given
      final var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );
      // delete the mod so the temporal group is unused
      regelungstext.deleteByEId("meta-1_analysis-1_activemod-1");

      // when
      regelungstext.deleteEventRefIfUnused("meta-1_lebzykl-1_ereignis-1");

      // then
      assertThat(
        NodeParser.getNodeFromExpression(
          "//*[@eId='meta-1_lebzykl-1_ereignis-1']",
          regelungstext.getDocument()
        )
      )
        .isEmpty();
    }

    @Test
    void itShouldNotDeleteEventRefIfUsed() {
      // given
      final var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );

      assertThat(
        NodeParser.getNodeFromExpression(
          "//*[@eId='meta-1_lebzykl-1_ereignis-2']",
          regelungstext.getDocument()
        )
      )
        .isNotNull();
      // when
      regelungstext.deleteEventRefIfUnused("meta-1_lebzykl-1_ereignis-2");

      // then
      assertThat(
        NodeParser.getNodeFromExpression(
          "//*[@eId='meta-1_lebzykl-1_ereignis-2']",
          regelungstext.getDocument()
        )
      )
        .isNotNull();
    }
  }

  @Test
  void getElementByEId() {
    // given
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );

    // when
    final Optional<Element> element = regelungstext.getElementByEId("einleitung-1_block-1_datum-1");

    // then
    assertThat(element).isPresent();
    assertThat(element.get().getAttribute("GUID")).contains("28fafbe4-403d-4436-8d0d-7241cbbdade0");
  }
}
