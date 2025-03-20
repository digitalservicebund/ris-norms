package de.bund.digitalservice.ris.norms.domain.entity;

import static de.bund.digitalservice.ris.norms.utils.XmlMapper.toDocument;
import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentWorkEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import java.util.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NormTest {

  @Test
  void getExpressionEli() {
    // given
    Norm norm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );
    NormExpressionEli expectedEli = NormExpressionEli.fromString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"
    );

    // when
    var actualEli = norm.getExpressionEli();

    // then
    assertThat(actualEli).isEqualTo(expectedEli);
  }

  @Test
  void getWorkEli() {
    // given
    Norm norm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );

    // when
    var actualEli = norm.getWorkEli();

    // then
    assertThat(actualEli).isEqualTo(NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"));
  }

  @Test
  void getManifestationEli() {
    // given
    Norm norm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );
    NormManifestationEli expectedEli = NormManifestationEli.fromString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
    );

    // when
    var actualEli = norm.getManifestationEli();

    // then
    assertThat(actualEli).isEqualTo(expectedEli);
  }

  @Test
  void getManifestationEliNoRegelungsText() {
    // given
    Norm norm = Fixtures.loadNormFromDisk("OffeneStruktur.xml");
    NormManifestationEli expectedEli = NormManifestationEli.fromString(
      "eli/bund/bgbl-1/2020/s3092/2020-12-23/1/deu/2022-08-23"
    );

    // when
    var actualEli = norm.getManifestationEli();

    // then
    assertThat(actualEli).isEqualTo(expectedEli);
  }

  @Test
  void getGuid() {
    // given
    String normString =
      """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                   http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
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

    Norm norm = Norm
      .builder()
      .dokumente(Set.of(new Regelungstext(XmlMapper.toDocument(normString))))
      .build();

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
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                   http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
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

    Norm norm = Norm
      .builder()
      .dokumente(Set.of(new Regelungstext(XmlMapper.toDocument(normString))))
      .build();
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
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                   http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
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
    Norm norm = Norm
      .builder()
      .dokumente(Set.of(new Regelungstext(XmlMapper.toDocument(normString))))
      .build();

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
    Norm norm = Norm
      .builder()
      .dokumente(Set.of(new Regelungstext(XmlMapper.toDocument(normString))))
      .build();

    // when
    var shortTitle = norm.getShortTitle();

    // then
    assertThat(shortTitle).contains("Vereinsgesetz");
  }

  @Test
  void equalsShouldEqualWithSameXml() {
    // given
    String xml =
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
                        <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung" />
                     </akn:FRBRWork>
                </akn:identification>
              </akn:meta>
           </akn:act>
        </akn:akomaNtoso>
      """;

    Norm norm1 = Norm.builder().dokumente(Set.of(new Regelungstext(toDocument(xml)))).build();
    Norm norm2 = Norm.builder().dokumente(Set.of(new Regelungstext(toDocument(xml)))).build();

    // then
    assertThat(norm1).isEqualTo(norm2);
  }

  @Test
  void equalsShouldNotEqualWithDifferentXml() {
    // given
    String xml1 =
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
                        <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-06" name="verkuendungsfassung" />
                     </akn:FRBRWork>
                </akn:identification>
              </akn:meta>
           </akn:act>
        </akn:akomaNtoso>
      """;

    Norm norm1 = Norm.builder().dokumente(Set.of(new Regelungstext(toDocument(xml1)))).build();
    Norm norm2 = Norm.builder().dokumente(Set.of(new Regelungstext(toDocument(xml2)))).build();
    // then
    assertThat(norm1).isNotEqualTo(norm2);
  }

  @Test
  void hashCodeShouldBeTheSameWithSameXml() {
    // given
    String xml =
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
                        <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung" />
                     </akn:FRBRWork>
                </akn:identification>
              </akn:meta>
           </akn:act>
        </akn:akomaNtoso>
      """;

    Norm norm1 = Norm.builder().dokumente(Set.of(new Regelungstext(toDocument(xml)))).build();
    Norm norm2 = Norm.builder().dokumente(Set.of(new Regelungstext(toDocument(xml)))).build();
    // then
    assertThat(norm1.hashCode()).hasSameHashCodeAs(norm2.hashCode());
  }

  @Test
  void shouldBeUnpublishedByDefaultWhenCreatedUsingBuild() {
    // Given
    final Norm norm = Norm
      .builder()
      .dokumente(
        Set.of(
          new Regelungstext(
            XmlMapper.toDocument(
              Fixtures.loadTextFromDisk(
                "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
              )
            )
          )
        )
      )
      .build();

    // Then
    assertThat(norm.getPublishState()).isEqualTo(NormPublishState.UNPUBLISHED);
  }

  @Test
  void hashCodeShouldBeDifferentWithDifferentXml() {
    // given
    String xml1 =
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
                        <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-06" name="verkuendungsfassung" />
                     </akn:FRBRWork>
                </akn:identification>
              </akn:meta>
           </akn:act>
        </akn:akomaNtoso>
      """;

    Norm norm1 = Norm.builder().dokumente(Set.of(new Regelungstext(toDocument(xml1)))).build();
    Norm norm2 = Norm.builder().dokumente(Set.of(new Regelungstext(toDocument(xml2)))).build();
    // then
    assertThat(norm1.hashCode()).isNotEqualTo(norm2.hashCode());
  }

  @Nested
  class getRegelungstextByEli {

    @Test
    void itReturnsRegelungstext1ByExpressionEli() {
      // Given
      Regelungstext regelungstext1 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      Regelungstext regelungstext2 = Fixtures.loadRegelungstextFromDisk("SimpleRegelungstext2.xml");
      Norm norm = new Norm(NormPublishState.UNPUBLISHED, Set.of(regelungstext1, regelungstext2));

      // When
      var loadedRegelungstext = norm.getRegelungstextByEli(regelungstext1.getExpressionEli());

      // Then
      assertThat(loadedRegelungstext).contains(regelungstext1);
    }

    @Test
    void itReturnsRegelungstext2ByManifestationEli() {
      // Given
      Regelungstext regelungstext1 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      Regelungstext regelungstext2 = Fixtures.loadRegelungstextFromDisk("SimpleRegelungstext2.xml");
      Norm norm = new Norm(NormPublishState.UNPUBLISHED, Set.of(regelungstext1, regelungstext2));

      // When
      var loadedRegelungstext = norm.getRegelungstextByEli(regelungstext2.getManifestationEli());

      // Then
      assertThat(loadedRegelungstext).contains(regelungstext2);
    }

    @Test
    void itReturnsRegelungstext1ByWorkEli() {
      // Given
      Regelungstext regelungstext1 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      Regelungstext regelungstext2 = Fixtures.loadRegelungstextFromDisk("SimpleRegelungstext2.xml");
      Norm norm = new Norm(NormPublishState.UNPUBLISHED, Set.of(regelungstext1, regelungstext2));

      // When
      var loadedRegelungstext = norm.getRegelungstextByEli(regelungstext1.getWorkEli());

      // Then
      assertThat(loadedRegelungstext).contains(regelungstext1);
    }

    @Test
    void itReturnsEmptyForUnknownEli() {
      // Given
      Regelungstext regelungstext1 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      Regelungstext regelungstext2 = Fixtures.loadRegelungstextFromDisk("SimpleRegelungstext2.xml");
      Norm norm = new Norm(NormPublishState.UNPUBLISHED, Set.of(regelungstext1, regelungstext2));

      // When
      var loadedRegelungstext = norm.getRegelungstextByEli(
        DokumentWorkEli.fromString("eli/bund/bgbl-1/2022/23/regelungstext-3")
      );

      // Then
      assertThat(loadedRegelungstext).isEmpty();
    }
  }

  @Nested
  class isInkraftAt {

    @Test
    void itReturnsTrueIfTheDateIsAfterInkrafttreteDatum() {
      // Given
      Norm norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );

      // When // Then
      assertThat(norm.isInkraftAt(LocalDate.parse("2020-01-01"))).isTrue();
    }

    @Test
    void itReturnsTrueIfTheDateIsBeforeInkrafttreteDatum() {
      // Given
      Norm norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );

      // When // Then
      assertThat(norm.isInkraftAt(LocalDate.parse("1962-01-01"))).isFalse();
    }

    @Test
    void itReturnsFalseIfTheDateIsAfterAusserkrafttreteDatum() {
      // Given
      Norm norm = Fixtures.loadNormFromDisk("NormToBeReleased.xml");

      // When // Then
      assertThat(norm.isInkraftAt(LocalDate.parse("2030-01-01"))).isFalse();
    }
  }

  @Nested
  class getOffenestrukturen {

    @Test
    void itReturnsOffenestrukturen() {
      // Given
      Regelungstext regelungstext1 = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      OffeneStruktur offeneStruktur1 = Fixtures.loadOffeneStrukturFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/offenestruktur-1.xml"
      );
      Norm norm = new Norm(NormPublishState.UNPUBLISHED, Set.of(regelungstext1, offeneStruktur1));

      // When
      var offenestrukturen = norm.getOffenestrukturen();

      // Then
      assertThat(offenestrukturen).hasSize(1).contains(offeneStruktur1);
    }
  }
}
