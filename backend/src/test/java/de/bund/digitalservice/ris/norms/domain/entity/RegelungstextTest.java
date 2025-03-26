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
  void getShortTitleWithoutParenthesis() {
    // given
    final String xml =
      """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                   http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
           <akn:act name="regelungstext">
              <!-- Dokumentenkopf Regelungstext -->
              <akn:preface eId="einleitung-1" GUID="fc10e89f-fde4-44bf-aa98-b6bdea01f0ea">
                 <akn:longTitle eId="einleitung-1_doktitel-1" GUID="abbb08de-e7e2-40ab-aba0-079ce786e6d6">
                    <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="3e7c2134-d82c-44ba-b50d-bad9790375a0">
                       <akn:shortTitle eId="einleitung-1_doktitel-1_text-1_kurztitel-1" GUID="fdb8ed28-2e1f-4d81-b780-846fd9ecb716">( <akn:inline
                             eId="einleitung-1_doktitel-1_text-1_kurztitel-1_inline-1" GUID="bdff7240-266e-4ff3-b311-60342bd1afa2" refersTo="amtliche-abkuerzung" name="attributsemantik-noch-undefiniert">Vereinsgesetz</akn:inline>)</akn:shortTitle>
                    </akn:p>
                 </akn:longTitle>
              </akn:preface>
           </akn:act>
        </akn:akomaNtoso>
      """;

    final var regelungstext = new Regelungstext(XmlMapper.toDocument(xml));

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
  void getFRBRnameAlreadyProperlyFormatted() {
    // given
    final String xml =
      """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                   http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
           <akn:act name="regelungstext">
              <!-- Metadaten -->
              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                 <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                    <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
                        <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="b82cc174-8fff-43bf-a434-5646de09e807" value="s593" />
                        <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" value="BGBl. I" />
                     </akn:FRBRWork>
                </akn:identification>
              </akn:meta>
           </akn:act>
        </akn:akomaNtoso>
      """;

    final var regelungstext = new Regelungstext(XmlMapper.toDocument(xml));
    final String expectedFRBRname = "BGBl. I";

    // when
    final String actualAnnouncementGazette = regelungstext
      .getMeta()
      .getFRBRWork()
      .getFRBRname()
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
    final String xml =
      """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                   http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
           <akn:act name="regelungstext">
              <!-- Metadaten -->
              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                 <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                 </akn:lifecycle>
                 <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                             <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                                <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                             </akn:temporalGroup>
                 </akn:temporalData>
              </akn:meta>
           </akn:act>
        </akn:akomaNtoso>
      """.strip();

    final var regelungstext = new Regelungstext(XmlMapper.toDocument(xml));
    final List<TimeBoundary> actualBoundaries = regelungstext.getTimeBoundaries();

    assertThat(actualBoundaries.getFirst().getEventRef().getDate())
      .contains(LocalDate.parse("2023-12-30"));

    assertThat(actualBoundaries.getFirst().getEventRefEid())
      .isEqualTo("meta-1_lebzykl-1_ereignis-2");
  }

  @Test
  void extractTimeBoundariesFromTemporalGroups() {
    final String xml =
      """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                   http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
           <akn:act name="regelungstext">
              <!-- Metadaten -->
              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                 <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                 </akn:lifecycle>
                 <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                             <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                                <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                             </akn:temporalGroup>
                 </akn:temporalData>
              </akn:meta>
           </akn:act>
        </akn:akomaNtoso>
      """.strip();

    final var regelungstext = new Regelungstext(XmlMapper.toDocument(xml));
    final List<TimeBoundary> actualBoundaries = regelungstext.getTimeBoundaries(
      regelungstext.getMeta().getTemporalData().getTemporalGroups()
    );

    assertThat(actualBoundaries.getFirst().getEventRef().getDate())
      .contains(LocalDate.parse("2023-12-30"));

    assertThat(actualBoundaries.getFirst().getEventRefEid())
      .isEqualTo("meta-1_lebzykl-1_ereignis-2");
  }

  @Test
  void getTimeBoundariesEmpty() {
    final String xml =
      """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                   http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
           <akn:act name="regelungstext">
              <!-- Metadaten -->
              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                 <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                 </akn:lifecycle>
                 <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                 </akn:temporalData>
              </akn:meta>
           </akn:act>
        </akn:akomaNtoso>
      """.strip();

    final var regelungstext = new Regelungstext(XmlMapper.toDocument(xml));
    final List<TimeBoundary> timeBoundaries = regelungstext.getTimeBoundaries();
    assertThat(timeBoundaries).isEmpty();
  }

  @Test
  void addTimeBoundary() {
    final String xml =
      """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                   http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
           <akn:act name="regelungstext">
              <!-- Metadaten -->
              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                 <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                 </akn:lifecycle>
                 <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                             <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                                <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                             </akn:temporalGroup>
                 </akn:temporalData>
              </akn:meta>
           </akn:act>
        </akn:akomaNtoso>
      """.strip();

    final var regelungstext = new Regelungstext(XmlMapper.toDocument(xml));
    regelungstext.addTimeBoundary(LocalDate.parse("2024-01-02"), EventRefType.GENERATION);
    final List<TimeBoundary> timeBoundaries = regelungstext.getTimeBoundaries();

    // old one still there
    assertThat(timeBoundaries.get(0).getEventRef().getDate())
      .contains(LocalDate.parse("2023-12-30"));

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
      .contains("ac311ee1-33d3-4b9b-a974-776e55a88396");
    assertThat(timeBoundaries.get(0).getTimeIntervalEid())
      .isEqualTo("meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1");
    assertThat(timeBoundaries.get(0).getTimeInterval().getElement().getAttribute("GUID"))
      .contains("ca9f53aa-d374-4bec-aca3-fff4e3485179");
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
    final String xml =
      """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                   http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
           <akn:act name="regelungstext">
              <!-- Metadaten -->
              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                 <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-3" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2024-01-02"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                 </akn:lifecycle>
                 <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                       <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                          <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                       </akn:temporalGroup>
                       <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-2" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                          <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-3" />
                       </akn:temporalGroup>
                 </akn:temporalData>
              </akn:meta>
           </akn:act>
        </akn:akomaNtoso>
      """.strip();

    final var regelungstext = new Regelungstext(XmlMapper.toDocument(xml));
    final TimeBoundaryChangeData timeBoundaryToDelete = new TimeBoundaryChangeData(
      "meta-1_lebzykl-1_ereignis-3",
      LocalDate.parse("2024-01-02")
    );

    regelungstext.deleteTimeBoundary(timeBoundaryToDelete);
    final List<TimeBoundary> timeBoundaries = regelungstext.getTimeBoundaries();

    // old one still there
    assertThat(timeBoundaries).hasSize(1);

    assertThat(timeBoundaries.get(0).getEventRef().getDate())
      .contains(LocalDate.parse("2023-12-30"));
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
      .contains("ac311ee1-33d3-4b9b-a974-776e55a88396");
    assertThat(timeBoundaries.get(0).getTimeIntervalEid())
      .isEqualTo("meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1");
    assertThat(timeBoundaries.get(0).getTimeInterval().getElement().getAttribute("GUID"))
      .contains("ca9f53aa-d374-4bec-aca3-fff4e3485179");
    assertThat(timeBoundaries.get(0).getTimeInterval().getElement().getAttribute("refersTo"))
      .contains("geltungszeit");
    assertThat(timeBoundaries.get(0).getTimeInterval().getElement().getAttribute("start"))
      .contains("#" + timeBoundaries.get(0).getEventRefEid());
  }

  @Test
  void getStartDateForTemporalGroup() {
    // given
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "NormWithMultiplePassiveModifications.xml"
    );

    // when
    final var date = regelungstext.getStartDateForTemporalGroup(
      new EId("meta-1_geltzeiten-1_geltungszeitgr-2")
    );

    // then
    assertThat(date).contains("2017-03-23");
  }

  @Test
  void getStartEventRefForTemporalGroup() {
    // given
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "NormWithMultiplePassiveModifications.xml"
    );

    // when
    final var eId = regelungstext.getStartEventRefForTemporalGroup(
      new EId("meta-1_geltzeiten-1_geltungszeitgr-2")
    );

    // then
    assertThat(eId).contains(new EId("meta-1_lebzykl-1_ereignis-4"));
  }

  @Test
  void getStartDateForEventRef() {
    // given
    final var regelungstext = Fixtures.loadRegelungstextFromDisk(
      "NormWithMultiplePassiveModifications.xml"
    );

    // when
    final var date = regelungstext.getStartDateForEventRef(new EId("meta-1_lebzykl-1_ereignis-3"));

    // then
    assertThat(date).contains("2017-03-15");
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
        "NormWithPassiveModifications.xml"
      );

      // when
      final var temporalData = regelungstext.getMeta().getTemporalData();

      // then
      assertThat(temporalData).isNotNull();
      assertThat(NodeParser.getValueFromExpression("@GUID", temporalData.getElement()))
        .contains("f866d5a3-98c8-4927-8cab-1630c5832f3c");
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
    final String xml =
      """
          <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
          <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                                    http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
          <akn:act name="regelungstext">
              <!-- Metadaten -->
              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                  <akn:analysis eId="meta-1_analysis-1" GUID="c0eb49c8-bf39-4a4a-b324-3b0feb88c1f1" source="attributsemantik-noch-undefiniert">
                      <akn:activeModifications eId="meta-1_analysis-1_activemod-1" GUID="cd241744-ace4-436c-a0e3-dc1ee8caf3ac">
                          <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-2" GUID="8992dd02-ab87-42e8-bee2-86b76f587f81" type="substitution">
                              <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-2_source-1" GUID="7537d65c-2a3b-440c-80ec-257073b1d1d3" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"/>
                                      <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-2_destination-1" GUID="83a4e169-ec57-4981-b191-84afe42130c8" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/art-20_abs-1/100-126.xml"/>
                              <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-2_gelzeitnachw-1" GUID="9180eb9f-9da2-4fa4-b57f-803d4ddcdbc9" period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
                          </akn:textualMod>
                      </akn:activeModifications>
                      <akn:activeModifications eId="meta-1_analysis-1_activemod-2" GUID="cd241744-ace4-436c-a0e3-dc1ee8caf3a2">
                          <akn:textualMod eId="meta-1_analysis-1_activemod-2_textualmod-1" GUID="8992dd02-ab87-42e8-bee2-86b76f587f81" type="substitution">
                              <akn:source eId="meta-1_analysis-1_activemod-2_textualmod-1_source-1" GUID="7537d65c-2a3b-440c-80ec-257073b1d1d3" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"/>
                                      <akn:destination eId="meta-1_analysis-1_activemod-2_textualmod-1_destination-1" GUID="83a4e169-ec57-4981-b191-84afe42130c8" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/art-20_abs-1/100-126.xml"/>
                              <akn:force eId="meta-1_analysis-1_activemod-2_textualmod-1_gelzeitnachw-1" GUID="9180eb9f-9da2-4fa4-b57f-803d4ddcdbc9" period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
                          </akn:textualMod>
                      </akn:activeModifications>
                  </akn:analysis>
              </akn:meta>
          </akn:act>
      </akn:akomaNtoso>
      """;

    final var regelungstext = new Regelungstext(XmlMapper.toDocument(xml));

    // when
    final Optional<Element> textualMod = regelungstext.getElementByEId(
      "meta-1_analysis-1_activemod-2_textualmod-1"
    );

    // then
    assertThat(textualMod).isPresent();
    assertThat(textualMod.get().getAttribute("GUID"))
      .contains("8992dd02-ab87-42e8-bee2-86b76f587f81");
  }
}
