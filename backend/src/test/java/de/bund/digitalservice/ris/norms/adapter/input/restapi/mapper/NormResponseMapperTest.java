package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.NormResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;

class NormResponseMapperTest {

  @Test
  void canMapPrintAnnouncedNorm() {
    // Given
    var norm = Norm
      .builder()
      .document(
        XmlMapper.toDocument(
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
                      <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                         <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                         <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                         <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                         <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                      </akn:FRBRExpression>
                  </akn:identification>
                                  <akn:proprietary eId="meta-1_proprietary-1" GUID="cbeef40f-ddc7-4ea5-9d4d-c0077844b58f" source="attributsemantik-noch-undefiniert">
                                    <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.6/">
                                      <meta:typ>gesetz</meta:typ>
                                      <meta:form>stammform</meta:form>
                                      <meta:fassung>verkuendungsfassung</meta:fassung>
                                      <meta:art>regelungstext</meta:art>
                                      <meta:initiant>nicht-vorhanden</meta:initiant>
                                      <meta:bearbeitendeInstitution>nicht-vorhanden</meta:bearbeitendeInstitution>
                                      <!-- Die vorliegende Angabe von meta:fna stellt einen beliebigen, exemplarischen Fundstellennachweis dar und besitzt keine fachliche Korrektheit. -->
                                      <meta:fna>754-28-1</meta:fna>
                                      <!-- Die vorliegende Angabe von meta:gesta besitzt keine fachliche Korrektheit. -->
                                      <meta:gesta>nicht-vorhanden</meta:gesta>
                                      <!-- Die vorliegenden Angaben von meta:federfuehrung besitzen keine fachliche Korrektheit. -->
                                      <meta:federfuehrung>
                                        <meta:federfuehrend ab="2002-12-01" bis="unbestimmt">Bundesministerium des Innern und für Heimat</meta:federfuehrend>
                                        <meta:federfuehrend ab="2002-10-01" bis="2002-12-01">Bundesministerium der Justiz</meta:federfuehrend>
                                      </meta:federfuehrung>
                                    </meta:legalDocML.de_metadaten>
                                  </akn:proprietary>
                </akn:meta>

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
          """
        )
      )
      .build();

    // When
    final NormResponseSchema result = NormResponseMapper.fromUseCaseData(norm);

    // Then
    assertThat(result.getEli())
      .isEqualTo("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1");
    assertThat(result.getTitle()).isEqualTo("Gesetz zur Regelung des öffentlichen Vereinsrechts");
    assertThat(result.getShortTitle()).isEqualTo("Vereinsgesetz");
    assertThat(result.getFrbrName()).isEqualTo("BGBl. I");
    assertThat(result.getFrbrNumber()).isEqualTo("s593");
    assertThat(result.getFrbrDateVerkuendung()).isEqualTo("1964-08-05");
    assertThat(result.getFna()).isEqualTo("754-28-1");
  }

  @Test
  void canMapDigitallyAnnouncedNorm() {
    // Given
    var norm = Norm
      .builder()
      .document(
        XmlMapper.toDocument(
          """
          <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
          <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                 http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
             <akn:act name="regelungstext">
                <!-- Metadaten -->
                <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                   <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                      <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="d4f77434-7c1f-4496-917a-262a82a7070c">
                         <akn:FRBRthis eId="meta-1_ident-1_frbrwork-1_frbrthis-1" GUID="ad47b5be-0012-447a-90db-71438b38650e" value="eli/bund/bgbl-1/2023/413/regelungstext-1" />
                         <akn:FRBRuri eId="meta-1_ident-1_frbrwork-1_frbruri-1" GUID="a739e012-fe1d-4411-91b8-58e0de76fc28" value="eli/bund/bgbl-1/2023/413" />
                         <akn:FRBRalias eId="meta-1_ident-1_frbrwork-1_frbralias-1" GUID="42ae272d-4b4a-4d25-9965-79e76c741b5b" name="übergreifende-id" value="c53036e4-14ac-420f-b01a-bae05a0a9756" />
                         <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="525ff48c-a66e-45f6-b036-884935f7ba7d" date="2023-12-29" name="verkuendungsfassung" />
                         <akn:FRBRauthor eId="meta-1_ident-1_frbrwork-1_frbrauthor-1" GUID="27fa3047-26e1-4c59-8701-76dd34043d71" href="recht.bund.de/institution/bundesregierung" />
                         <akn:FRBRcountry eId="meta-1_ident-1_frbrwork-1_frbrcountry-1" GUID="fa3d22d4-4f01-4486-9d45-c1edcf50729e" value="de" />
                         <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="565c2f06-c2c9-4a27-aeb3-ca34199ce08c" value="413" />
                         <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="7219aecc-e1eb-49a1-abf5-bba8a8be721c" value="bgbl-1" />
                         <akn:FRBRsubtype eId="meta-1_ident-1_frbrwork-1_frbrsubtype-1" GUID="c5bc9d46-575f-4808-90e8-a354a227d701" value="regelungstext-1" />
                      </akn:FRBRWork>
                      <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                         <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="f3805314-bbb6-4def-b82b-8b7f0b126197" value="eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1"/>
                         <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                         <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                         <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                      </akn:FRBRExpression>
                  </akn:identification>
                </akn:meta>

                <akn:preface eId="einleitung-1" GUID="4554f060-e4ef-43a3-b71f-f30aa25769d6">
                   <akn:longTitle eId="einleitung-1_doktitel-1" GUID="185fcdbe-04f8-4b17-ac7c-2208c7f2f9df">
                      <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="a9694e02-330d-40e3-b0d1-50b2059f020c">
                         <akn:docStage eId="einleitung-1_doktitel-1_text-1_docstadium-1" GUID="884b29f7-584f-41e2-9329-d8780d33a3d7">Verkündungsfassung</akn:docStage>
                         <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="e08874b2-05a8-4d6e-9d78-7be24380c54b">Gesetz zum ersten Teil der Reform des Nachrichtendienstrechts</akn:docTitle>
                      </akn:p>
                   </akn:longTitle>
                   <akn:block eId="einleitung-1_block-1" GUID="a0973d49-d628-42f7-a1da-b004bc980a44" name="attributsemantik-noch-undefiniert">
                      <akn:date eId="einleitung-1_block-1_datum-1" GUID="f20d437a-3058-4747-8b8b-9b1e06c17273" refersTo="ausfertigung-datum" date="2023-12-29">Vom
                         29.12.2023</akn:date>
                   </akn:block>
                </akn:preface>
             </akn:act>
          </akn:akomaNtoso>
          """
        )
      )
      .build();

    // When
    final NormResponseSchema result = NormResponseMapper.fromUseCaseData(norm);

    // Then
    assertThat(result.getEli())
      .isEqualTo("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1");
    assertThat(result.getTitle())
      .isEqualTo("Gesetz zum ersten Teil der Reform des Nachrichtendienstrechts");
    assertThat(result.getFrbrName()).isEqualTo("BGBl. I");
    assertThat(result.getFrbrNumber()).isEqualTo("413");
    assertThat(result.getFrbrDateVerkuendung()).isEqualTo("2023-12-29");
  }
}
