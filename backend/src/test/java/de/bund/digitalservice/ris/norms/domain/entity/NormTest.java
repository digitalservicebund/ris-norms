package de.bund.digitalservice.ris.norms.domain.entity;

import static de.bund.digitalservice.ris.norms.utils.XmlMapper.toDocument;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFound;
import java.time.LocalDate;
import java.util.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

class NormTest {
  @Test
  void getEli() {
    // given
    Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");
    String expectedEli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";

    // when
    var actualEli = norm.getEli();

    // then
    assertThat(actualEli).isEqualTo(expectedEli);
  }

  @Test
  void getErrorWhenEliItDoesntExist() {
    // given
    String normString =
        """
                        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                    <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                       xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                           http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                       <akn:act name="regelungstext">
                       </akn:act>
                    </akn:akomaNtoso>
                      """
            .strip();

    Norm norm = new Norm(toDocument(normString));

    assertThrows(NoSuchElementException.class, norm::getEli);
  }

  @Test
  void getGuid() {
    // given
    String normString =
        """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                         <akn:act name="regelungstext">
                            <!-- Metadaten -->
                            <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                               <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                  <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                                     <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                                  </akn:FRBRExpression>
                              </akn:identification>
                            </akn:meta>
                         </akn:act>
                      </akn:akomaNtoso>
                    """;

    Norm norm = new Norm(toDocument(normString));

    // when
    UUID actualGuid = norm.getGuid();

    // then
    assertThat(actualGuid).isEqualTo(UUID.fromString("ba44d2ae-0e73-44ba-850a-932ab2fa553f"));
  }

  @Test
  void getTitle() {
    // given
    String normString =
        """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                         <akn:act name="regelungstext">
                            <!-- Dokumentenkopf Regelungstext -->
                            <akn:preface eId="einleitung-1" GUID="fc10e89f-fde4-44bf-aa98-b6bdea01f0ea">
                               <akn:longTitle eId="einleitung-1_doktitel-1" GUID="abbb08de-e7e2-40ab-aba0-079ce786e6d6">
                                  <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="3e7c2134-d82c-44ba-b50d-bad9790375a0">
                                     <akn:docStage eId="einleitung-1_doktitel-1_text-1_docstadium-1" GUID="3b355cab-ce10-45b5-9cde-cc618fbf491f" />
                                     <akn:docProponent eId="einleitung-1_doktitel-1_text-1_docproponent-1" GUID="c83abe1e-5fde-4e4e-a9b5-7293505ffeff" />
                                     <akn:docTitle
                                        eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="8c4eabab-9893-455e-b83b-c46f2453f2fb">Gesetz zur Regelung des öffentlichen Vereinsrechts</akn:docTitle>
                                     <akn:shortTitle eId="einleitung-1_doktitel-1_text-1_kurztitel-1" GUID="fdb8ed28-2e1f-4d81-b780-846fd9ecb716">( <akn:inline
                                           eId="einleitung-1_doktitel-1_text-1_kurztitel-1_inline-1" GUID="bdff7240-266e-4ff3-b311-60342bd1afa2" refersTo="amtliche-abkuerzung" name="attributsemantik-noch-undefiniert">Vereinsgesetz</akn:inline>)</akn:shortTitle>
                                  </akn:p>
                               </akn:longTitle>
                               <akn:block eId="einleitung-1_block-1" GUID="010d9df0-817a-49b6-a121-d0a1d412a3e3" name="attributsemantik-noch-undefiniert">
                                  <akn:date eId="einleitung-1_block-1_datum-1" GUID="28fafbe4-403d-4436-8d0d-7241cbbdade0" refersTo="ausfertigung-datum" date="1964-08-05">Vom 5. August 1964 </akn:date>
                               </akn:block>
                            </akn:preface>
                         </akn:act>
                      </akn:akomaNtoso>
                    """;

    Norm norm = new Norm(toDocument(normString));
    String expectedTitle = "Gesetz zur Regelung des öffentlichen Vereinsrechts";

    // when
    String actualTitle = norm.getTitle().get();

    // then
    assertThat(actualTitle).contains(expectedTitle);
  }

  @Test
  void getShortTitle() {
    // given
    String normString =
        """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                         <akn:act name="regelungstext">
                            <!-- Dokumentenkopf Regelungstext -->
                            <akn:preface eId="einleitung-1" GUID="fc10e89f-fde4-44bf-aa98-b6bdea01f0ea">
                               <akn:longTitle eId="einleitung-1_doktitel-1" GUID="abbb08de-e7e2-40ab-aba0-079ce786e6d6">
                                  <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="3e7c2134-d82c-44ba-b50d-bad9790375a0">
                                     <akn:shortTitle eId="einleitung-1_doktitel-1_text-1_kurztitel-1" GUID="fdb8ed28-2e1f-4d81-b780-846fd9ecb716">Vereinsgesetz</akn:shortTitle>
                                  </akn:p>
                               </akn:longTitle>
                            </akn:preface>
                         </akn:act>
                      </akn:akomaNtoso>
                    """;
    Norm norm = new Norm(toDocument(normString));

    // when
    var shortTitle = norm.getShortTitle();

    // then
    assertThat(shortTitle).contains("Vereinsgesetz");
  }

  @Test
  void getShortTitleWithoutParenthesis() {
    // given
    String normString =
        """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
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
    Norm norm = new Norm(toDocument(normString));

    // when
    var shortTitle = norm.getShortTitle();

    // then
    assertThat(shortTitle).contains("Vereinsgesetz");
  }

  @Test
  void getFRBRname() {
    // given
    String normString =
        """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                         <akn:act name="regelungstext">
                            <!-- Metadaten -->
                            <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                               <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                  <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
                                      <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="b82cc174-8fff-43bf-a434-5646de09e807" value="s593" />
                                      <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" value="bgbl-1" />
                                   </akn:FRBRWork>
                              </akn:identification>
                            </akn:meta>
                         </akn:act>
                      </akn:akomaNtoso>
                    """;

    Norm norm = new Norm(toDocument(normString));
    String expectedFRBRname = "BGBl. I";

    // when
    String actualFRBRname = norm.getMeta().getFRBRWork().getFRBRname().get();

    // then
    assertThat(actualFRBRname).contains(expectedFRBRname);
  }

  @Test
  void getFRBRnumber() {
    // given
    String normString =
        """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                         <akn:act name="regelungstext">
                            <!-- Metadaten -->
                            <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                               <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                  <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
                                      <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="b82cc174-8fff-43bf-a434-5646de09e807" value="s593" />
                                      <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" value="bgbl-1" />
                                   </akn:FRBRWork>
                              </akn:identification>
                            </akn:meta>
                         </akn:act>
                      </akn:akomaNtoso>
                    """;

    Norm norm = new Norm(toDocument(normString));
    String expectedFRBRname = "s593";

    // when
    String actualAnnouncementGazette = norm.getMeta().getFRBRWork().getFRBRnumber().get();

    // then
    assertThat(actualAnnouncementGazette).contains(expectedFRBRname);
  }

  @Test
  void getFRBRnameAlreadyProperlyFormatted() {
    // given
    String normString =
        """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
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

    Norm norm = new Norm(toDocument(normString));
    String expectedFRBRname = "BGBl. I";

    // when
    String actualAnnouncementGazette = norm.getMeta().getFRBRWork().getFRBRname().get();

    // then
    assertThat(actualAnnouncementGazette).contains(expectedFRBRname);
  }

  @Test
  void getPublishingDate() {
    // given
    String normString =
        """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                         <akn:act name="regelungstext">
                            <!-- Metadaten -->
                            <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                               <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                  <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
                                      <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="b82cc174-8fff-43bf-a434-5646de09e807" value="s593" />
                                      <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" value="BGBl. I" />
                                      <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung" />
                                   </akn:FRBRWork>
                              </akn:identification>
                            </akn:meta>
                         </akn:act>
                      </akn:akomaNtoso>
                    """;

    Norm norm = new Norm(toDocument(normString));
    // when
    String actualFBRDateVerkuendung = norm.getMeta().getFRBRWork().getFBRDate();

    // then
    assertThat(actualFBRDateVerkuendung).isEqualTo("1964-08-05");
  }

  @Test
  void getMeta() {
    // given
    String normString =
        """
                  <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                  <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                     xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                         http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                     <akn:act name="regelungstext">
                        <!-- Metadaten -->
                        <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                                   <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                      <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                                         <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                                         <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                                         <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                                         <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                                         <akn:FRBRdate eId="meta-1_ident-1_frbrexpression-1_frbrdate-1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung" />
                                      </akn:FRBRExpression>
                                  </akn:identification>
                        </akn:meta>
                     </akn:act>
                  </akn:akomaNtoso>
                """;

    Norm norm = new Norm(toDocument(normString));

    // when
    final Meta meta = norm.getMeta();

    // then
    assertThat(meta).isNotNull();
  }

  @Test
  void getMetaNotFound() {
    // given
    String normString =
        """
              <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
          <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                 http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
             <akn:act name="regelungstext">
             </akn:act>
          </akn:akomaNtoso>
            """
            .strip();

    Norm norm = new Norm(toDocument(normString));

    assertThrows(MandatoryNodeNotFound.class, norm::getMeta);
  }

  @Test
  void getAllArticles() {
    // given
    String normString =
        """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                         <akn:act name="regelungstext">
                            <akn:body eId="hauptteil-1" GUID="0B4A8E1F-65EF-4B7C-9E22-E83BA6B73CD8">
                               <!-- Artikel 1 : Hauptänderung -->
                               <akn:article eId="hauptteil-1_art-1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#geltungszeitgr-1" refersTo="hauptaenderung">
                                  <akn:num eId="hauptteil-1_art-1_bezeichnung-1" GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">
                                     <akn:marker eId="hauptteil-1_art-1_bezeichnung-1_zaehlbez-1" GUID="81c9c481-9427-4f03-9f51-099aa9b2201e" name="1" />Artikel 1</akn:num>
                                  <akn:heading eId="hauptteil-1_art-1_überschrift-1" GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes</akn:heading>
                                  <!-- Absatz (1) -->
                                  <akn:paragraph eId="hauptteil-1_art-1_abs-1" GUID="48ead358-f901-41d3-a135-e372d5ef97b1">
                                     <akn:num eId="hauptteil-1_art-1_abs-1_bezeichnung-1" GUID="ef3a32d2-df20-4978-914b-cd6288872208">
                                        <akn:marker eId="hauptteil-1_art-1_abs-1_bezeichnung-1_zaehlbez-1" GUID="eab5a7e7-b649-4c23-b495-648b8ec71843" name="1" />
                                     </akn:num>
                                     <akn:list eId="hauptteil-1_art-1_abs-1_untergl-1" GUID="41675622-ed62-46e3-869f-94d99908b010">
                                        <akn:intro eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1" GUID="5d6fc824-7926-43b4-b243-b0096da183f9">
                                           <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1" GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5"> Das <akn:affectedDocument eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1" GUID="88b3b9f3-e4a8-49c6-9320-b5b42150bcc5"
                                                 href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1">Vereinsgesetz vom 5. August 1964 (BGBl. I S. 593), das zuletzt durch … geändert worden ist</akn:affectedDocument>, wird wie folgt geändert:</akn:p>
                                        </akn:intro>
                                     </akn:list>
                                  </akn:paragraph>
                               </akn:article>
                                <!-- Artikel 3: Geltungszeitregel-->
                                <akn:article eId="hauptteil-1_art-3" GUID="aaae12b5-0c74-4e51-a286-d6051ff5d21b" period="#geltungszeitgr-1" refersTo="geltungszeitregel">
                                   <akn:num eId="hauptteil-1_art-3_bezeichnung-1" GUID="1bc12642-f00c-4b55-8388-5e8870e6e706">
                                      <akn:marker eId="hauptteil-1_art-3_bezeichnung-1_zaehlbez-1" GUID="7bbcdd71-a27b-4932-91b7-6c18356ed3e5" name="3" />Artikel 3</akn:num>
                                   <akn:heading eId="hauptteil-1_art-3_überschrift-1" GUID="59a7dc28-e095-4da6-ba78-278a0d69a3fd">Inkrafttreten</akn:heading>
                                   <!-- Absatz (1) -->
                                   <akn:paragraph eId="hauptteil-1_art-3_abs-1" GUID="0b1590b0-8945-44a0-bf44-ebfb7d8c3bd8">
                                      <akn:num eId="hauptteil-1_art-3_abs-1_bezeichnung-1" GUID="c46a1cbc-f823-4a18-9b0c-0f131244a58e">
                                         <akn:marker eId="hauptteil-1_art-3_abs-1_bezeichnung-1_zaehlbez-1" GUID="1d41fa26-e36a-4d03-8f4a-4790d3184944" name="1" />
                                      </akn:num>
                                      <akn:content eId="hauptteil-1_art-3_abs-1_inhalt-1" GUID="3e004e1f-f1bc-42a7-8042-2c1d77df81aa">
                                         <akn:p eId="hauptteil-1_art-3_abs-1_inhalt-1_text-1" GUID="52406e40-b866-410c-b097-af69e6248f58"> Dieses Gesetz tritt <akn:date eId="hauptteil-1_art-3_abs-1_inhalt-1_text-1_datum-1" GUID="2ee89811-5368-46e0-acf8-a598506cc956" date="2017-03-16" refersTo="inkrafttreten-datum">am Tag
                                            nach der Verkündung</akn:date> in Kraft. </akn:p>
                                      </akn:content>
                                   </akn:paragraph>
                                </akn:article>
                            </akn:body>
                         </akn:act>
                      </akn:akomaNtoso>
                    """;

    Norm norm = new Norm(toDocument(normString));
    var expectedNumberOfArticles = 2;
    var firstExpectedHeading = "Änderung des Vereinsgesetzes";
    var secondExpectedHeading = "Inkrafttreten";

    // when
    List<Article> actualArticles = norm.getArticles();

    // then
    assertThat(actualArticles).hasSize(expectedNumberOfArticles);
    assertThat(actualArticles.getFirst().getHeading()).contains(firstExpectedHeading);
    assertThat(actualArticles.getFirst().getEnumeration()).contains("1");
    assertThat(actualArticles.get(0).getEid()).contains("hauptteil-1_art-1");
    assertThat(actualArticles.get(0).getAffectedDocumentEli())
        .contains("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1");

    assertThat(actualArticles.get(1).getHeading()).contains(secondExpectedHeading);
    assertThat(actualArticles.get(1).getEnumeration()).contains("3");
    assertThat(actualArticles.get(1).getEid()).contains("hauptteil-1_art-3");
    assertThat(actualArticles.get(1).getAffectedDocumentEli()).isNotPresent();
  }

  @Test
  void getAllArticlesEvenWhenNestedInSections() {
    // given
    String normString =
        """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                         <akn:act name="regelungstext">
                            <akn:body eId="hauptteil-1" GUID="0B4A8E1F-65EF-4B7C-9E22-E83BA6B73CD8">
                              <akn:section GUID="851e76da-dea2-4d9b-a85a-a90c31358e6e" eId="hauptteil-1_abschnitt-erster">
                                 <akn:num GUID="15376a15-dcf4-4c11-ad20-f09b134db7f9" eId="hauptteil-1_abschnitt-erster_bezeichnung-1">
                                    <akn:marker GUID="f26cff1f-7bba-46a0-8c1b-f23bce5c35aa" eId="hauptteil-1_abschnitt-erster_bezeichnung-1_zaehlbez-1" name="erster"/>Erster
                                    Abschnitt </akn:num>
                                 <akn:heading GUID="a803190c-9626-421f-9fd4-a904f9e572dd" eId="hauptteil-1_abschnitt-erster_überschrift-1">
                                    Zusammenarbeit, Aufgaben der Verfassungsschutzbehörden
                                 </akn:heading>
                                 <!-- Artikel 1 : Hauptänderung -->
                                 <akn:article eId="hauptteil-1_abschnitt-erster_art-1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#geltungszeitgr-1" refersTo="hauptaenderung">
                                    <akn:num eId="hauptteil-1_abschnitt-erster_art-1_bezeichnung-1" GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">
                                       <akn:marker eId="hauptteil-1_abschnitt-erster_art-1_bezeichnung-1_zaehlbez-1" GUID="81c9c481-9427-4f03-9f51-099aa9b2201e" name="1" />Artikel 1</akn:num>
                                    <akn:heading eId="hauptteil-1_abschnitt-erster_art-1_überschrift-1" GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes</akn:heading>
                                    <!-- Absatz (1) -->
                                    <akn:paragraph eId="hauptteil-1_abschnitt-erster_art-1_abs-1" GUID="48ead358-f901-41d3-a135-e372d5ef97b1">
                                       <akn:num eId="hauptteil-1_abschnitt-erster_art-1_abs-1_bezeichnung-1" GUID="ef3a32d2-df20-4978-914b-cd6288872208">
                                          <akn:marker eId="hauptteil-1_abschnitt-erster_art-1_abs-1_bezeichnung-1_zaehlbez-1" GUID="eab5a7e7-b649-4c23-b495-648b8ec71843" name="1" />
                                       </akn:num>
                                       <akn:list eId="hauptteil-1_abschnitt-erster_art-1_abs-1_untergl-1" GUID="41675622-ed62-46e3-869f-94d99908b010">
                                          <akn:intro eId="hauptteil-1_abschnitt-erster_art-1_abs-1_untergl-1_intro-1" GUID="5d6fc824-7926-43b4-b243-b0096da183f9">
                                             <akn:p eId="hauptteil-1_abschnitt-erster_art-1_abs-1_untergl-1_intro-1_text-1" GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5"> Das <akn:affectedDocument eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1" GUID="88b3b9f3-e4a8-49c6-9320-b5b42150bcc5"
                                                   href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1">Vereinsgesetz vom 5. August 1964 (BGBl. I S. 593), das zuletzt durch … geändert worden ist</akn:affectedDocument>, wird wie folgt geändert:</akn:p>
                                          </akn:intro>
                                       </akn:list>
                                    </akn:paragraph>
                                 </akn:article>
                                 <!-- Artikel 3: Geltungszeitregel-->
                                 <akn:article eId="hauptteil-1_abschnitt-erster_art-3" GUID="aaae12b5-0c74-4e51-a286-d6051ff5d21b" period="#geltungszeitgr-1" refersTo="geltungszeitregel">
                                    <akn:num eId="hauptteil-1_abschnitt-erster_art-3_bezeichnung-1" GUID="1bc12642-f00c-4b55-8388-5e8870e6e706">
                                       <akn:marker eId="hauptteil-1_abschnitt-erster_art-3_bezeichnung-1_zaehlbez-1" GUID="7bbcdd71-a27b-4932-91b7-6c18356ed3e5" name="3" />Artikel 3</akn:num>
                                    <akn:heading eId="hauptteil-1_abschnitt-erster_art-3_überschrift-1" GUID="59a7dc28-e095-4da6-ba78-278a0d69a3fd">Inkrafttreten</akn:heading>
                                    <!-- Absatz (1) -->
                                    <akn:paragraph eId="hauptteil-1_abschnitt-erster_art-3_abs-1" GUID="0b1590b0-8945-44a0-bf44-ebfb7d8c3bd8">
                                       <akn:num eId="hauptteil-1_abschnitt-erster_art-3_abs-1_bezeichnung-1" GUID="c46a1cbc-f823-4a18-9b0c-0f131244a58e">
                                          <akn:marker eId="hauptteil-1_abschnitt-erster_art-3_abs-1_bezeichnung-1_zaehlbez-1" GUID="1d41fa26-e36a-4d03-8f4a-4790d3184944" name="1" />
                                       </akn:num>
                                       <akn:content eId="hauptteil-1_abschnitt-erster_art-3_abs-1_inhalt-1" GUID="3e004e1f-f1bc-42a7-8042-2c1d77df81aa">
                                          <akn:p eId="hauptteil-1_abschnitt-erster_art-3_abs-1_inhalt-1_text-1" GUID="52406e40-b866-410c-b097-af69e6248f58"> Dieses Gesetz tritt <akn:date eId="hauptteil-1_art-3_abs-1_inhalt-1_text-1_datum-1" GUID="2ee89811-5368-46e0-acf8-a598506cc956" date="2017-03-16" refersTo="inkrafttreten-datum">am Tag
                                             nach der Verkündung</akn:date> in Kraft. </akn:p>
                                       </akn:content>
                                    </akn:paragraph>
                                 </akn:article>
                               </akn:section>
                            </akn:body>
                         </akn:act>
                      </akn:akomaNtoso>
                    """;

    Norm norm = new Norm(toDocument(normString));
    var expectedNumberOfArticles = 2;
    var firstExpectedHeading = "Änderung des Vereinsgesetzes";
    var secondExpectedHeading = "Inkrafttreten";

    // when
    List<Article> actualArticles = norm.getArticles();

    // then
    assertThat(actualArticles).hasSize(expectedNumberOfArticles);
    assertThat(actualArticles.getFirst().getHeading()).contains(firstExpectedHeading);
    assertThat(actualArticles.getFirst().getEnumeration()).contains("1");
    assertThat(actualArticles.get(0).getEid()).contains("hauptteil-1_abschnitt-erster_art-1");
    assertThat(actualArticles.get(0).getAffectedDocumentEli())
        .contains("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1");

    assertThat(actualArticles.get(1).getHeading()).contains(secondExpectedHeading);
    assertThat(actualArticles.get(1).getEnumeration()).contains("3");
    assertThat(actualArticles.get(1).getEid()).contains("hauptteil-1_abschnitt-erster_art-3");
    assertThat(actualArticles.get(1).getAffectedDocumentEli()).isNotPresent();
  }

  @Test
  void returnsEmptyListIfNoArticlesAreFound() {
    // given
    String normString =
        """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                         <akn:act name="regelungstext">
                            <akn:body eId="hauptteil-1" GUID="0B4A8E1F-65EF-4B7C-9E22-E83BA6B73CD8">
                            </akn:body>
                         </akn:act>
                      </akn:akomaNtoso>
                    """;

    Norm norm = new Norm(toDocument(normString));

    // when
    List<Article> actualArticles = norm.getArticles();

    // then
    assertThat(actualArticles).isEmpty();
  }

  @Test
  void equalsShouldEqualWithSameXml() {
    // given
    String xml =
        """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                         <akn:act name="regelungstext">
                            <!-- Metadaten -->
                            <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                               <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                  <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
                                      <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="b82cc174-8fff-43bf-a434-5646de09e807" value="s593" />
                                      <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" value="BGBl. I" />
                                      <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung" />
                                   </akn:FRBRWork>
                              </akn:identification>
                            </akn:meta>
                         </akn:act>
                      </akn:akomaNtoso>
                    """;

    Norm norm1 = new Norm(toDocument(xml));
    Norm norm2 = new Norm(toDocument(xml));

    // then
    assertThat(norm1).isEqualTo(norm2);
  }

  @Test
  void equalsShouldNotEqualWithDifferentXml() {
    // given
    String xml1 =
        """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                         <akn:act name="regelungstext">
                            <!-- Metadaten -->
                            <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                               <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                  <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
                                      <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="b82cc174-8fff-43bf-a434-5646de09e807" value="s593" />
                                      <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" value="BGBl. I" />
                                      <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung" />
                                   </akn:FRBRWork>
                              </akn:identification>
                            </akn:meta>
                         </akn:act>
                      </akn:akomaNtoso>
                    """;

    String xml2 =
        """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                         <akn:act name="regelungstext">
                            <!-- Metadaten -->
                            <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                               <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                  <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
                                      <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="b82cc174-8fff-43bf-a434-5646de09e807" value="s593" />
                                      <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" value="BGBl. I" />
                                      <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-06" name="verkuendungsfassung" />
                                   </akn:FRBRWork>
                              </akn:identification>
                            </akn:meta>
                         </akn:act>
                      </akn:akomaNtoso>
                    """;

    Norm norm1 = new Norm(toDocument(xml1));
    Norm norm2 = new Norm(toDocument(xml2));

    // then
    assertThat(norm1).isNotEqualTo(norm2);
  }

  @Test
  void hashCodeShouldBeTheSameWithSameXml() {
    // given
    String xml =
        """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                         <akn:act name="regelungstext">
                            <!-- Metadaten -->
                            <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                               <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                  <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
                                      <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="b82cc174-8fff-43bf-a434-5646de09e807" value="s593" />
                                      <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" value="BGBl. I" />
                                      <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung" />
                                   </akn:FRBRWork>
                              </akn:identification>
                            </akn:meta>
                         </akn:act>
                      </akn:akomaNtoso>
                    """;

    Norm norm1 = new Norm(toDocument(xml));
    Norm norm2 = new Norm(toDocument(xml));

    // then
    assertThat(norm1.hashCode()).hasSameHashCodeAs(norm2.hashCode());
  }

  @Test
  void hashCodeShouldBeDifferentWithDifferentXml() {
    // given
    String xml1 =
        """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                         <akn:act name="regelungstext">
                            <!-- Metadaten -->
                            <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                               <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                  <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
                                      <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="b82cc174-8fff-43bf-a434-5646de09e807" value="s593" />
                                      <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" value="BGBl. I" />
                                      <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung" />
                                   </akn:FRBRWork>
                              </akn:identification>
                            </akn:meta>
                         </akn:act>
                      </akn:akomaNtoso>
                    """;

    String xml2 =
        """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                         <akn:act name="regelungstext">
                            <!-- Metadaten -->
                            <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                               <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                  <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
                                      <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="b82cc174-8fff-43bf-a434-5646de09e807" value="s593" />
                                      <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" value="BGBl. I" />
                                      <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-06" name="verkuendungsfassung" />
                                   </akn:FRBRWork>
                              </akn:identification>
                            </akn:meta>
                         </akn:act>
                      </akn:akomaNtoso>
                    """;

    Norm norm1 = new Norm(toDocument(xml1));
    Norm norm2 = new Norm(toDocument(xml2));

    // then
    assertThat(norm1.hashCode()).isNotEqualTo(norm2.hashCode());
  }

  @Test
  void extractTimeBoundariesFromXml() {
    String xml =
        """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
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
                    """
            .strip();

    Norm norm = new Norm(toDocument(xml));

    List<TimeBoundary> actualBoundaries = norm.getTimeBoundaries();

    assertThat(actualBoundaries.getFirst().getEventRef().getDate())
        .contains(LocalDate.parse("2023-12-30"));
    assertThat(actualBoundaries.getFirst().getEventRefEid().get())
        .contains("meta-1_lebzykl-1_ereignis-2");
  }

  @Test
  void getTimeBoundariesEmpty() {
    String xml =
        """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
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
                    """
            .strip();

    Norm norm = new Norm(toDocument(xml));
    List<TimeBoundary> timeBoundaries = norm.getTimeBoundaries();
    assertThat(timeBoundaries).isEmpty();
  }

  @Test
  void addTimeBoundary() {
    String xml =
        """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
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
                    """
            .strip();

    Norm norm = new Norm(toDocument(xml));
    norm.addTimeBoundary(LocalDate.parse("2024-01-02"), EventRefType.GENERATION);

    List<TimeBoundary> timeBoundaries = norm.getTimeBoundaries();

    // old one still there
    assertThat(timeBoundaries.get(0).getEventRef().getDate())
        .contains(LocalDate.parse("2023-12-30"));
    assertThat(timeBoundaries.get(0).getEventRefEid().get())
        .contains("meta-1_lebzykl-1_ereignis-2");
    assertThat(
            timeBoundaries
                .get(0)
                .getTimeInterval()
                .getNode()
                .getParentNode()
                .getAttributes()
                .getNamedItem("eId")
                .getNodeValue())
        .contains("meta-1_geltzeiten-1_geltungszeitgr-1");
    assertThat(
            timeBoundaries
                .get(0)
                .getTimeInterval()
                .getNode()
                .getParentNode()
                .getAttributes()
                .getNamedItem("GUID")
                .getNodeValue())
        .contains("ac311ee1-33d3-4b9b-a974-776e55a88396");
    assertThat(timeBoundaries.get(0).getTimeIntervalEid().get())
        .contains("meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1");
    assertThat(
            timeBoundaries
                .get(0)
                .getTimeInterval()
                .getNode()
                .getAttributes()
                .getNamedItem("GUID")
                .getNodeValue())
        .contains("ca9f53aa-d374-4bec-aca3-fff4e3485179");
    assertThat(
            timeBoundaries
                .get(0)
                .getTimeInterval()
                .getNode()
                .getAttributes()
                .getNamedItem("refersTo")
                .getNodeValue())
        .contains("geltungszeit");
    assertThat(
            timeBoundaries
                .get(0)
                .getTimeInterval()
                .getNode()
                .getAttributes()
                .getNamedItem("start")
                .getNodeValue())
        .contains("#" + timeBoundaries.get(0).getEventRefEid().get());

    // new one added
    assertThat(timeBoundaries.get(1).getEventRef().getDate())
        .contains(LocalDate.parse("2024-01-02"));
    assertThat(timeBoundaries.get(1).getEventRefEid().get())
        .contains("meta-1_lebzykl-1_ereignis-3");
    assertThat(
            timeBoundaries
                .get(1)
                .getTimeInterval()
                .getNode()
                .getParentNode()
                .getAttributes()
                .getNamedItem("eId")
                .getNodeValue())
        .contains("meta-1_geltzeiten-1_geltungszeitgr-2");
    assertThat(
            timeBoundaries
                .get(1)
                .getTimeInterval()
                .getNode()
                .getParentNode()
                .getAttributes()
                .getNamedItem("GUID")
                .getNodeValue())
        .isNotEmpty();
    assertThat(timeBoundaries.get(1).getTimeIntervalEid().get())
        .contains("meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1");
    assertThat(
            timeBoundaries
                .get(1)
                .getTimeInterval()
                .getNode()
                .getAttributes()
                .getNamedItem("GUID")
                .getNodeValue())
        .isNotEmpty();
    assertThat(
            timeBoundaries
                .get(1)
                .getTimeInterval()
                .getNode()
                .getAttributes()
                .getNamedItem("refersTo")
                .getNodeValue())
        .contains("geltungszeit");
    assertThat(
            timeBoundaries
                .get(1)
                .getTimeInterval()
                .getNode()
                .getAttributes()
                .getNamedItem("start")
                .getNodeValue())
        .contains("#" + timeBoundaries.get(1).getEventRefEid().get());
  }

  @Test
  void deleteTimeBoundary() {
    String xml =
        """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                             http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
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
                    """
            .strip();

    Norm norm = new Norm(toDocument(xml));

    TimeBoundaryChangeData timeBoundaryToDelete =
        new TimeBoundaryChangeData("meta-1_lebzykl-1_ereignis-3", LocalDate.parse("2024-01-02"));
    norm.deleteTimeBoundary(timeBoundaryToDelete);

    List<TimeBoundary> timeBoundaries = norm.getTimeBoundaries();

    // old one still there
    assertThat(timeBoundaries).hasSize(1);
    assertThat(timeBoundaries.get(0).getEventRef().getDate())
        .contains(LocalDate.parse("2023-12-30"));
    assertThat(timeBoundaries.get(0).getEventRefEid().get())
        .contains("meta-1_lebzykl-1_ereignis-2");
    assertThat(
            timeBoundaries
                .get(0)
                .getTimeInterval()
                .getNode()
                .getParentNode()
                .getAttributes()
                .getNamedItem("eId")
                .getNodeValue())
        .contains("meta-1_geltzeiten-1_geltungszeitgr-1");
    assertThat(
            timeBoundaries
                .get(0)
                .getTimeInterval()
                .getNode()
                .getParentNode()
                .getAttributes()
                .getNamedItem("GUID")
                .getNodeValue())
        .contains("ac311ee1-33d3-4b9b-a974-776e55a88396");
    assertThat(timeBoundaries.get(0).getTimeIntervalEid().get())
        .contains("meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1");
    assertThat(
            timeBoundaries
                .get(0)
                .getTimeInterval()
                .getNode()
                .getAttributes()
                .getNamedItem("GUID")
                .getNodeValue())
        .contains("ca9f53aa-d374-4bec-aca3-fff4e3485179");
    assertThat(
            timeBoundaries
                .get(0)
                .getTimeInterval()
                .getNode()
                .getAttributes()
                .getNamedItem("refersTo")
                .getNodeValue())
        .contains("geltungszeit");
    assertThat(
            timeBoundaries
                .get(0)
                .getTimeInterval()
                .getNode()
                .getAttributes()
                .getNamedItem("start")
                .getNodeValue())
        .contains("#" + timeBoundaries.get(0).getEventRefEid().get());
  }

  @Test
  void calculateNextPossibleEid() {
    // given
    Node parentNode =
        XmlMapper.toNode(
            """
                            <akn:temporalData eId="meta-1_geltzeiten-1"
                                                          GUID="2fcdfa3e-1460-4ef4-b22b-5ff4a897538f"
                                                          source="attributsemantik-noch-undefiniert">
                                <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1"
                                                   GUID="7b13adb9-ef62-43c4-bf1b-155561edf89b">
                                </akn:temporalGroup>
                                <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-2"
                                                   GUID="7af9337a-3727-424c-a3df-dee918a79b22">
                                </akn:temporalGroup>
                            </akn:temporalData>
                            """);

    // when
    final String nextPossibleEid =
        NodeCreator.calculateNextPossibleEid(parentNode, "geltungszeitgr");

    // then
    assertThat(nextPossibleEid).contains("meta-1_geltzeiten-1_geltungszeitgr-3");
  }

  @Test
  void calculateNextPossibleEidWhenNoChildNodeOfTypeExists() {
    // given
    Node parentNode =
        XmlMapper.toNode(
            """
                            <akn:temporalData eId="meta-1_geltzeiten-1"
                                                          GUID="2fcdfa3e-1460-4ef4-b22b-5ff4a897538f"
                                                          source="attributsemantik-noch-undefiniert">
                            </akn:temporalData>
                            """);

    // when
    final String nextPossibleEid =
        NodeCreator.calculateNextPossibleEid(parentNode, "geltungszeitgr");

    // then
    assertThat(nextPossibleEid).contains("meta-1_geltzeiten-1_geltungszeitgr-1");
  }

  @Test
  void getMods() {
    // given
    Norm norm = NormFixtures.loadFromDisk("NormWithMods.xml");

    // when
    var mods = norm.getMods();

    // then
    assertThat(mods).hasSize(1);
    assertThat(mods.getFirst().getEid())
        .contains("hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1");
  }

  @Test
  void getStartDateForTemporalGroup() {
    // given
    Norm norm = NormFixtures.loadFromDisk("NormWithMultiplePassiveModifications.xml");

    // when
    var date = norm.getStartDateForTemporalGroup("meta-1_geltzeiten-1_geltungszeitgr-2");

    // then
    assertThat(date).contains("2017-03-23");
  }

  @Test
  void getStartEventRefForTemporalGroup() {
    // given
    Norm norm = NormFixtures.loadFromDisk("NormWithMultiplePassiveModifications.xml");

    // when
    var eId = norm.getStartEventRefForTemporalGroup("meta-1_geltzeiten-1_geltungszeitgr-2");

    // then
    assertThat(eId).contains("meta-1_lebzykl-1_ereignis-4");
  }

  @Test
  void getStartDateForEventRef() {
    // given
    Norm norm = NormFixtures.loadFromDisk("NormWithMultiplePassiveModifications.xml");

    // when
    var date = norm.getStartDateForEventRef("meta-1_lebzykl-1_ereignis-3");

    // then
    assertThat(date).contains("2017-03-15");
  }

  @Nested
  class createElementWithEidAndGuid {
    @Test
    void itShouldCreatesANewElement() {
      // given
      Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");
      Node parentNode =
          NodeParser.getNodeFromExpression("//act/meta", norm.getDocument()).orElseThrow();

      // when
      final Node createdNode =
          NodeCreator.createElementWithEidAndGuid("akn:analysis", "analysis", parentNode);

      // then
      assertThat(NodeParser.getNodeFromExpression("//act/meta/analysis", norm.getDocument()))
          .contains(createdNode);
      assertThat(NodeParser.getValueFromExpression("@eId", createdNode))
          .contains("meta-1_analysis-1");
    }
  }

  @Nested
  class getOrCreateAnalysisNode {
    @Test
    void itShouldCreatesTheAnalysisNodeIfItDoesNotExist() {
      // given
      final Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");

      // when
      final var analysis = norm.getMeta().getOrCreateAnalysis();

      // then
      assertThat(analysis).isNotNull();
      assertThat(NodeParser.getNodeFromExpression("//act/meta/analysis", norm.getDocument()))
          .contains(analysis.getNode());
    }
  }

  @Nested
  class getOrCreateTemporalDataNode {
    @Test
    void itShouldCreatesTheTemporalDataNodeIfItDoesNotExist() {
      // given
      final Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");

      // when
      final var temporalData = norm.getMeta().getOrCreateTemporalDataNode();

      // then
      assertThat(temporalData).isNotNull();
      assertThat(NodeParser.getNodeFromExpression("//act//temporalData", norm.getDocument()))
          .contains(temporalData.getNode());
    }

    @Test
    void itShouldFindTheTemporalDataNodeIfItExist() {
      // given
      final Norm norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");

      // when
      final var temporalData = norm.getMeta().getTemporalData();

      // then
      assertThat(temporalData).isNotNull();
      assertThat(NodeParser.getValueFromExpression("@GUID", temporalData.getNode()))
          .contains("2fcdfa3e-1460-4ef4-b22b-5ff4a897538f");
    }
  }

  @Nested
  class deleteByEId {
    @Test
    void itShouldDeleteTheNodeOfTheEId() {
      // given
      Norm norm = NormFixtures.loadFromDisk("SimpleNorm.xml");

      // when
      norm.deleteByEId("meta-1_ident-1_frbrexpression-1_frbrthis-1");

      // then
      assertThrows(NoSuchElementException.class, norm::getEli);
    }
  }

  @Nested
  class deleteTemporalGroupIfUnused {
    @Test
    void itShouldDeleteTemporalGroupIfUnused() {
      // given
      Norm norm = NormFixtures.loadFromDisk("NormWithMods.xml");
      // delete the mod so the temporal group is unused
      norm.deleteByEId("meta-1_analysis-1_activemod-1");

      // when
      norm.deleteTemporalGroupIfUnused("meta-1_geltzeiten-1_geltungszeitgr-1");

      // then
      assertThat(norm.getNodeByEId("meta-1_analysis-1_activemod-1")).isEmpty();
    }

    @Test
    void itShouldNotDeleteTemporalGroupIfUsed() {
      // given
      Norm norm = NormFixtures.loadFromDisk("NormWithMods.xml");

      // when
      norm.deleteTemporalGroupIfUnused("meta-1_geltzeiten-1_geltungszeitgr-1");

      // then
      assertThat(norm.getNodeByEId("meta-1_geltzeiten-1_geltungszeitgr-1")).isPresent();
    }
  }

  @Nested
  class deleteEventRefIfUnused {
    @Test
    void itShouldDeleteEventRefIfUnused() {
      // given
      Norm norm = NormFixtures.loadFromDisk("NormWithMods.xml");
      // delete the mod so the temporal group is unused
      norm.deleteByEId("meta-1_analysis-1_activemod-1");

      // when
      norm.deleteEventRefIfUnused("meta-1_lebzykl-1_ereignis-1");

      // then
      assertThat(
              NodeParser.getNodeFromExpression(
                  "//*[@eId='meta-1_lebzykl-1_ereignis-1']", norm.getDocument()))
          .isEmpty();
    }

    @Test
    void itShouldNotDeleteEventRefIfUsed() {
      // given
      Norm norm = NormFixtures.loadFromDisk("NormWithMods.xml");

      assertThat(
              NodeParser.getNodeFromExpression(
                  "//*[@eId='meta-1_lebzykl-1_ereignis-2']", norm.getDocument()))
          .isNotNull();
      // when
      norm.deleteEventRefIfUnused("meta-1_lebzykl-1_ereignis-2");

      // then
      assertThat(
              NodeParser.getNodeFromExpression(
                  "//*[@eId='meta-1_lebzykl-1_ereignis-2']", norm.getDocument()))
          .isNotNull();
    }
  }

  @Test
  void getNodeByEId() {
    // given
    String normString =
        """
                        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                    <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                                              http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                        <akn:act name="regelungstext">
                            <!-- Metadaten -->
                            <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                                <akn:analysis eId="meta-1_analysis-1" GUID="c0eb49c8-bf39-4a4a-b324-3b0feb88c1f1" source="attributsemantik-noch-undefiniert">
                                    <akn:activeModifications eId="meta-1_analysis-1_activemod-1" GUID="cd241744-ace4-436c-a0e3-dc1ee8caf3ac">
                                        <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-2" GUID="8992dd02-ab87-42e8-bee2-86b76f587f81" type="substitution">
                                            <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-2_source-1" GUID="7537d65c-2a3b-440c-80ec-257073b1d1d3" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"/>
                                            <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-2_destination-1" GUID="83a4e169-ec57-4981-b191-84afe42130c8" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml"/>
                                            <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-2_gelzeitnachw-1" GUID="9180eb9f-9da2-4fa4-b57f-803d4ddcdbc9" period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
                                        </akn:textualMod>
                                    </akn:activeModifications>
                                    <akn:activeModifications eId="meta-1_analysis-1_activemod-2" GUID="cd241744-ace4-436c-a0e3-dc1ee8caf3a2">
                                        <akn:textualMod eId="meta-1_analysis-1_activemod-2_textualmod-1" GUID="8992dd02-ab87-42e8-bee2-86b76f587f81" type="substitution">
                                            <akn:source eId="meta-1_analysis-1_activemod-2_textualmod-1_source-1" GUID="7537d65c-2a3b-440c-80ec-257073b1d1d3" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"/>
                                            <akn:destination eId="meta-1_analysis-1_activemod-2_textualmod-1_destination-1" GUID="83a4e169-ec57-4981-b191-84afe42130c8" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml"/>
                                            <akn:force eId="meta-1_analysis-1_activemod-2_textualmod-1_gelzeitnachw-1" GUID="9180eb9f-9da2-4fa4-b57f-803d4ddcdbc9" period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
                                        </akn:textualMod>
                                    </akn:activeModifications>
                                </akn:analysis>
                            </akn:meta>
                        </akn:act>
                    </akn:akomaNtoso>
                    """;

    Norm norm = new Norm(toDocument(normString));

    // when
    final Optional<Node> textualMod =
        norm.getNodeByEId("meta-1_analysis-1_activemod-2_textualmod-1");

    // then
    assertThat(textualMod).isPresent();
    assertThat(textualMod.get().getAttributes().getNamedItem("GUID").getNodeValue())
        .contains("8992dd02-ab87-42e8-bee2-86b76f587f81");
  }

  @Nested
  class getNumberOfNodesWithEid {

    @Test
    void getNumberOfNodesWithEidReturnsZero() {
      // given
      String normString =
          """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                                                http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                          <akn:act name="regelungstext">
                              <!-- Metadaten -->
                              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                                  <akn:analysis eId="meta-1_analysis-1" GUID="c0eb49c8-bf39-4a4a-b324-3b0feb88c1f1" source="attributsemantik-noch-undefiniert">
                                      <akn:activeModifications eId="meta-1_analysis-1_activemod-1" GUID="cd241744-ace4-436c-a0e3-dc1ee8caf3ac">
                                          <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-1" GUID="8992dd02-ab87-42e8-bee2-86b76f587f81" type="substitution">
                                              <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-1_source-1" GUID="7537d65c-2a3b-440c-80ec-257073b1d1d3" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"/>
                                              <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-1_destination-1" GUID="83a4e169-ec57-4981-b191-84afe42130c8" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml"/>
                                              <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-1_gelzeitnachw-1" GUID="9180eb9f-9da2-4fa4-b57f-803d4ddcdbc9" period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
                                          </akn:textualMod>
                                      </akn:activeModifications>
                                      <akn:activeModifications eId="meta-1_analysis-1_activemod-2" GUID="cd241744-ace4-436c-a0e3-dc1ee8caf3a2">
                                          <akn:textualMod eId="meta-1_analysis-1_activemod-2_textualmod-1" GUID="8992dd02-ab87-42e8-bee2-86b76f587f81" type="substitution">
                                              <akn:source eId="meta-1_analysis-1_activemod-2_textualmod-1_source-1" GUID="7537d65c-2a3b-440c-80ec-257073b1d1d3" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-2"/>
                                              <akn:destination eId="meta-1_analysis-1_activemod-2_textualmod-1_destination-1" GUID="83a4e169-ec57-4981-b191-84afe42130c8" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml"/>
                                              <akn:force eId="meta-1_analysis-1_activemod-2_textualmod-1_gelzeitnachw-1" GUID="9180eb9f-9da2-4fa4-b57f-803d4ddcdbc9" period="#meta-1_geltzeiten-1_geltungszeitgr-2"/>
                                          </akn:textualMod>
                                      </akn:activeModifications>
                                  </akn:analysis>
                              </akn:meta>
                          </akn:act>
                      </akn:akomaNtoso>
                      """;

      Norm norm = new Norm(toDocument(normString));

      // when
      final int textualMod =
          norm.getNumberOfNodesWithEid("meta-1_analysis-1_activemod-3_textualmod-1");

      // then
      assertThat(textualMod).isZero();
    }

    @Test
    void getNumberOfNodesWithEidReturnsOne() {
      // given
      String normString =
          """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                                                http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                          <akn:act name="regelungstext">
                              <!-- Metadaten -->
                              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                                  <akn:analysis eId="meta-1_analysis-1" GUID="c0eb49c8-bf39-4a4a-b324-3b0feb88c1f1" source="attributsemantik-noch-undefiniert">
                                      <akn:activeModifications eId="meta-1_analysis-1_activemod-1" GUID="cd241744-ace4-436c-a0e3-dc1ee8caf3ac">
                                          <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-1" GUID="8992dd02-ab87-42e8-bee2-86b76f587f81" type="substitution">
                                              <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-1_source-1" GUID="7537d65c-2a3b-440c-80ec-257073b1d1d3" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"/>
                                              <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-1_destination-1" GUID="83a4e169-ec57-4981-b191-84afe42130c8" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml"/>
                                              <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-1_gelzeitnachw-1" GUID="9180eb9f-9da2-4fa4-b57f-803d4ddcdbc9" period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
                                          </akn:textualMod>
                                      </akn:activeModifications>
                                      <akn:activeModifications eId="meta-1_analysis-1_activemod-2" GUID="cd241744-ace4-436c-a0e3-dc1ee8caf3a2">
                                          <akn:textualMod eId="meta-1_analysis-1_activemod-2_textualmod-1" GUID="8992dd02-ab87-42e8-bee2-86b76f587f81" type="substitution">
                                              <akn:source eId="meta-1_analysis-1_activemod-2_textualmod-1_source-1" GUID="7537d65c-2a3b-440c-80ec-257073b1d1d3" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-2"/>
                                              <akn:destination eId="meta-1_analysis-1_activemod-2_textualmod-1_destination-1" GUID="83a4e169-ec57-4981-b191-84afe42130c8" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml"/>
                                              <akn:force eId="meta-1_analysis-1_activemod-2_textualmod-1_gelzeitnachw-1" GUID="9180eb9f-9da2-4fa4-b57f-803d4ddcdbc9" period="#meta-1_geltzeiten-1_geltungszeitgr-2"/>
                                          </akn:textualMod>
                                      </akn:activeModifications>
                                  </akn:analysis>
                              </akn:meta>
                          </akn:act>
                      </akn:akomaNtoso>
                      """;

      Norm norm = new Norm(toDocument(normString));

      // when
      final int textualMod =
          norm.getNumberOfNodesWithEid("meta-1_analysis-1_activemod-2_textualmod-1");

      // then
      assertThat(textualMod).isEqualTo(1);
    }

    @Test
    void getNumberOfNodesWithEidReturnsTwo() {
      // given
      String normString =
          """
                      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                                                http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                          <akn:act name="regelungstext">
                              <!-- Metadaten -->
                              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                                  <akn:analysis eId="meta-1_analysis-1" GUID="c0eb49c8-bf39-4a4a-b324-3b0feb88c1f1" source="attributsemantik-noch-undefiniert">
                                      <akn:activeModifications eId="meta-1_analysis-1_activemod-1" GUID="cd241744-ace4-436c-a0e3-dc1ee8caf3ac">
                                          <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-1" GUID="8992dd02-ab87-42e8-bee2-86b76f587f81" type="substitution">
                                              <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-1_source-1" GUID="7537d65c-2a3b-440c-80ec-257073b1d1d3" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"/>
                                              <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-1_destination-1" GUID="83a4e169-ec57-4981-b191-84afe42130c8" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml"/>
                                              <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-1_gelzeitnachw-1" GUID="9180eb9f-9da2-4fa4-b57f-803d4ddcdbc9" period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
                                          </akn:textualMod>
                                      </akn:activeModifications>
                                      <akn:activeModifications eId="meta-1_analysis-1_activemod-1" GUID="cd241744-ace4-436c-a0e3-dc1ee8caf3a2">
                                          <akn:textualMod eId="meta-1_analysis-1_activemod-2_textualmod-1" GUID="8992dd02-ab87-42e8-bee2-86b76f587f81" type="substitution">
                                              <akn:source eId="meta-1_analysis-1_activemod-2_textualmod-1_source-1" GUID="7537d65c-2a3b-440c-80ec-257073b1d1d3" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-2"/>
                                              <akn:destination eId="meta-1_analysis-1_activemod-2_textualmod-1_destination-1" GUID="83a4e169-ec57-4981-b191-84afe42130c8" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml"/>
                                              <akn:force eId="meta-1_analysis-1_activemod-2_textualmod-1_gelzeitnachw-1" GUID="9180eb9f-9da2-4fa4-b57f-803d4ddcdbc9" period="#meta-1_geltzeiten-1_geltungszeitgr-2"/>
                                          </akn:textualMod>
                                      </akn:activeModifications>
                                  </akn:analysis>
                              </akn:meta>
                          </akn:act>
                      </akn:akomaNtoso>
                      """;

      Norm norm = new Norm(toDocument(normString));

      // when
      final int textualMod = norm.getNumberOfNodesWithEid("meta-1_analysis-1_activemod-1");

      // then
      assertThat(textualMod).isEqualTo(2);
    }
  }
}
