package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

class NormControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private NormRepository normRepository;

  @AfterEach
  void cleanUp() {
    normRepository.deleteAll();
  }

  @Nested
  class GetNormByEli {

    @Test
    void itCallsNormsServiceAndReturnsNorm() throws Exception {
      // Given
      var norm =
          Norm.builder()
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
                                 """))
              .build();
      normRepository.save(NormMapper.mapToDto(norm));

      // When // Then
      mockMvc
          .perform(
              get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                  .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(
              jsonPath("eli").value("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"))
          .andExpect(jsonPath("title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts"))
          .andExpect(jsonPath("frbrName").value("BGBl. I"))
          .andExpect(jsonPath("frbrNumber").value("s593"))
          .andExpect(jsonPath("frbrDateVerkuendung").value("1964-08-05"));
    }

    @Test
    void itCallsNormsServiceAndReturnsNormXml() throws Exception {
      // Given
      final String xml =
          """
               <?xml version="1.0" encoding="UTF-8"?><?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?><akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                        http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                 <akn:act name="regelungstext">
                    <!-- Metadaten -->
                    <akn:meta GUID="82a65581-0ea7-4525-9190-35ff86c977af" eId="meta-1">
                       <akn:identification GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" eId="meta-1_ident-1" source="attributsemantik-noch-undefiniert">
                          <akn:FRBRExpression GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d" eId="meta-1_ident-1_frbrexpression-1">
                             <akn:FRBRthis GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"/>
                             <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                             <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                             <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                          </akn:FRBRExpression>
                      </akn:identification>
                    </akn:meta>
                 </akn:act>
               </akn:akomaNtoso>""";

      // When
      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
      normRepository.save(NormMapper.mapToDto(norm));

      // When // Then
      mockMvc
          .perform(
              get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                  .accept(MediaType.APPLICATION_XML))
          .andExpect(status().isOk())
          .andExpect(content().string(xml));
    }

    @Test
    void itCallsNormServiceAndReturnsNormRender() throws Exception {
      // Given
      final String xml =
          """
               <?xml version="1.0" encoding="UTF-8"?><?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?><akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                        http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                 <akn:act name="regelungstext">
                    <!-- Metadaten -->
                    <akn:meta GUID="82a65581-0ea7-4525-9190-35ff86c977af" eId="meta-1">
                       <akn:identification GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" eId="meta-1_ident-1" source="attributsemantik-noch-undefiniert">
                          <akn:FRBRExpression GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d" eId="meta-1_ident-1_frbrexpression-1">
                             <akn:FRBRthis GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"/>
                             <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                             <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                             <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                          </akn:FRBRExpression>
                      </akn:identification>
                    </akn:meta>
                    <akn:preface eId="einleitung-1" GUID="4554f060-e4ef-43a3-b71f-f30aa25769d6">
                       <akn:longTitle eId="einleitung-1_doktitel-1" GUID="185fcdbe-04f8-4b17-ac7c-2208c7f2f9df">
                          <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="a9694e02-330d-40e3-b0d1-50b2059f020c">
                             <akn:docStage eId="einleitung-1_doktitel-1_text-1_docstadium-1" GUID="884b29f7-584f-41e2-9329-d8780d33a3d7">Referentenentwurf</akn:docStage>
                             <akn:docProponent eId="einleitung-1_doktitel-1_text-1_docproponent-1" GUID="20095250-c44a-45a5-b7e3-2b49366ff5a8"> des Bundesministeriums des
                             Innern</akn:docProponent>
                             <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="e08874b2-05a8-4d6e-9d78-7be24380c54b">Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes</akn:docTitle>
                          </akn:p>
                       </akn:longTitle>
                       <akn:block eId="einleitung-1_block-1" GUID="a0973d49-d628-42f7-a1da-b004bc980a44" name="attributsemantik-noch-undefiniert">
                          <akn:date eId="einleitung-1_block-1_datum-1" GUID="f20d437a-3058-4747-8b8b-9b1e06c17273" refersTo="ausfertigung-datum" date="1900-01-01">Vom 01.01.1900</akn:date>
                       </akn:block>
                    </akn:preface>
                 </akn:act>
               </akn:akomaNtoso>""";

      // When
      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
      normRepository.save(NormMapper.mapToDto(norm));

      // When // Then
      mockMvc
          .perform(
              get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                  .accept(MediaType.TEXT_HTML))
          .andExpect(status().isOk())
          .andExpect(
              content()
                  .node(
                      hasXPath(
                          "//h1//*[@data-eId=\"einleitung-1_doktitel-1_text-1_doctitel-1\"]",
                          equalTo(
                              "Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes"))));
    }
  }

  @Nested
  class GetNormRenderWithQueryParameters {

    @Test
    void itCallsNormServiceAndReturnsNormRenderWithMetadata() throws Exception {
      // Given
      final String xml =
          """
               <?xml version="1.0" encoding="UTF-8"?><?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?><akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                        http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                 <akn:act name="regelungstext">
                    <!-- Metadaten -->
                    <akn:meta GUID="82a65581-0ea7-4525-9190-35ff86c977af" eId="meta-1">
                       <akn:identification GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" eId="meta-1_ident-1" source="attributsemantik-noch-undefiniert">
                          <akn:FRBRExpression GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d" eId="meta-1_ident-1_frbrexpression-1">
                             <akn:FRBRthis GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"/>
                             <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                             <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                             <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                          </akn:FRBRExpression>
                      </akn:identification>
                    </akn:meta>
                    <akn:preface eId="einleitung-1" GUID="4554f060-e4ef-43a3-b71f-f30aa25769d6">
                       <akn:longTitle eId="einleitung-1_doktitel-1" GUID="185fcdbe-04f8-4b17-ac7c-2208c7f2f9df">
                          <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="a9694e02-330d-40e3-b0d1-50b2059f020c">
                             <akn:docStage eId="einleitung-1_doktitel-1_text-1_docstadium-1" GUID="884b29f7-584f-41e2-9329-d8780d33a3d7">Referentenentwurf</akn:docStage>
                             <akn:docProponent eId="einleitung-1_doktitel-1_text-1_docproponent-1" GUID="20095250-c44a-45a5-b7e3-2b49366ff5a8"> des Bundesministeriums des
                             Innern</akn:docProponent>
                             <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="e08874b2-05a8-4d6e-9d78-7be24380c54b">Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes</akn:docTitle>
                          </akn:p>
                       </akn:longTitle>
                       <akn:block eId="einleitung-1_block-1" GUID="a0973d49-d628-42f7-a1da-b004bc980a44" name="attributsemantik-noch-undefiniert">
                          <akn:date eId="einleitung-1_block-1_datum-1" GUID="f20d437a-3058-4747-8b8b-9b1e06c17273" refersTo="ausfertigung-datum" date="1900-01-01">Vom 01.01.1900</akn:date>
                       </akn:block>
                    </akn:preface>
                 </akn:act>
               </akn:akomaNtoso>""";

      // When
      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
      normRepository.save(NormMapper.mapToDto(norm));

      // When // Then
      mockMvc
          .perform(
              get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1?showMetadata=true")
                  .accept(MediaType.TEXT_HTML))
          .andExpect(status().isOk())
          .andExpect(
              content()
                  .node(
                      hasXPath(
                          "//h1//*[@data-eId=\"einleitung-1_doktitel-1_text-1_doctitel-1\"]",
                          equalTo(
                              "Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes"))))
          .andExpect(
              content()
                  .node(
                      hasXPath(
                          "//section[@class=\"metadata\"]//td[text()=\"Amtliche Langüberschrift\"]/following-sibling::td/text()",
                          equalTo(
                              "Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes"))));
    }

    @Test
    void itReturnsBadRequestWhenRenderingAtInvalidIsoDate() throws Exception {
      // Given
      final String xml =
          """
             <?xml version="1.0" encoding="UTF-8"?><?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?><akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                        http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
               <akn:act name="regelungstext">
                  <!-- Metadaten -->
                  <akn:meta GUID="82a65581-0ea7-4525-9190-35ff86c977af" eId="meta-1">
                     <akn:identification GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" eId="meta-1_ident-1" source="attributsemantik-noch-undefiniert">
                        <akn:FRBRExpression GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d" eId="meta-1_ident-1_frbrexpression-1">
                           <akn:FRBRthis GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"/>
                           <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                           <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                           <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                        </akn:FRBRExpression>
                    </akn:identification>
                  </akn:meta>
                  <akn:preface eId="einleitung-1" GUID="4554f060-e4ef-43a3-b71f-f30aa25769d6">
                     <akn:longTitle eId="einleitung-1_doktitel-1" GUID="185fcdbe-04f8-4b17-ac7c-2208c7f2f9df">
                        <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="a9694e02-330d-40e3-b0d1-50b2059f020c">
                           <akn:docStage eId="einleitung-1_doktitel-1_text-1_docstadium-1" GUID="884b29f7-584f-41e2-9329-d8780d33a3d7">Referentenentwurf</akn:docStage>
                           <akn:docProponent eId="einleitung-1_doktitel-1_text-1_docproponent-1" GUID="20095250-c44a-45a5-b7e3-2b49366ff5a8"> des Bundesministeriums des
                           Innern</akn:docProponent>
                           <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="e08874b2-05a8-4d6e-9d78-7be24380c54b">Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes</akn:docTitle>
                        </akn:p>
                     </akn:longTitle>
                     <akn:block eId="einleitung-1_block-1" GUID="a0973d49-d628-42f7-a1da-b004bc980a44" name="attributsemantik-noch-undefiniert">
                        <akn:date eId="einleitung-1_block-1_datum-1" GUID="f20d437a-3058-4747-8b8b-9b1e06c17273" refersTo="ausfertigung-datum" date="1900-01-01">Vom 01.01.1900</akn:date>
                     </akn:block>
                  </akn:preface>
               </akn:act>
             </akn:akomaNtoso>""";

      // When
      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
      normRepository.save(NormMapper.mapToDto(norm));

      // When // Then
      mockMvc
          .perform(
              get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1?atIsoDate=NOT_A_DATE")
                  .accept(MediaType.TEXT_HTML))
          .andExpect(status().isBadRequest());
    }

    @Test
    void itReturnsHtmlWhenRenderingAtValidTimeBoundary() throws Exception {
      // Given
      final String xml =
          """
             <?xml version="1.0" encoding="UTF-8"?><?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?><akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                        http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
               <akn:act name="regelungstext">
                  <!-- Metadaten -->
                  <akn:meta GUID="82a65581-0ea7-4525-9190-35ff86c977af" eId="meta-1">
                     <akn:identification GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" eId="meta-1_ident-1" source="attributsemantik-noch-undefiniert">
                        <akn:FRBRExpression GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d" eId="meta-1_ident-1_frbrexpression-1">
                           <akn:FRBRthis GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"/>
                           <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                           <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                           <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                        </akn:FRBRExpression>
                    </akn:identification>
                  </akn:meta>
                  <akn:preface eId="einleitung-1" GUID="4554f060-e4ef-43a3-b71f-f30aa25769d6">
                     <akn:longTitle eId="einleitung-1_doktitel-1" GUID="185fcdbe-04f8-4b17-ac7c-2208c7f2f9df">
                        <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="a9694e02-330d-40e3-b0d1-50b2059f020c">
                           <akn:docStage eId="einleitung-1_doktitel-1_text-1_docstadium-1" GUID="884b29f7-584f-41e2-9329-d8780d33a3d7">Referentenentwurf</akn:docStage>
                           <akn:docProponent eId="einleitung-1_doktitel-1_text-1_docproponent-1" GUID="20095250-c44a-45a5-b7e3-2b49366ff5a8"> des Bundesministeriums des
                           Innern</akn:docProponent>
                           <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="e08874b2-05a8-4d6e-9d78-7be24380c54b">Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes</akn:docTitle>
                        </akn:p>
                     </akn:longTitle>
                     <akn:block eId="einleitung-1_block-1" GUID="a0973d49-d628-42f7-a1da-b004bc980a44" name="attributsemantik-noch-undefiniert">
                        <akn:date eId="einleitung-1_block-1_datum-1" GUID="f20d437a-3058-4747-8b8b-9b1e06c17273" refersTo="ausfertigung-datum" date="1900-01-01">Vom 01.01.1900</akn:date>
                     </akn:block>
                  </akn:preface>
               </akn:act>
             </akn:akomaNtoso>""";

      // When
      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
      normRepository.save(NormMapper.mapToDto(norm));

      // When // Then
      mockMvc
          .perform(
              get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1?atIsoDate=2024-04-25T14:37:14.434Z")
                  .accept(MediaType.TEXT_HTML))
          .andExpect(status().isOk())
          .andExpect(content().node(hasXPath("//div")));
    }
  }

  @Nested
  class PutNormByEli {
    @Test
    void itCallsNormServiceAndUpdatesNorm() throws Exception {
      // Given
      var oldXml =
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
                              <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                              <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                              <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                           </akn:FRBRExpression>
                       </akn:identification>
                     </akn:meta>
                     <akn:preface eId="einleitung-1" GUID="4554f060-e4ef-43a3-b71f-f30aa25769d6">
                         <akn:longTitle eId="einleitung-1_doktitel-1" GUID="185fcdbe-04f8-4b17-ac7c-2208c7f2f9df">
                            <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="a9694e02-330d-40e3-b0d1-50b2059f020c">
                               <akn:docStage eId="einleitung-1_doktitel-1_text-1_docstadium-1" GUID="884b29f7-584f-41e2-9329-d8780d33a3d7">Referentenentwurf</akn:docStage>
                               <akn:docProponent eId="einleitung-1_doktitel-1_text-1_docproponent-1" GUID="20095250-c44a-45a5-b7e3-2b49366ff5a8"> des Bundesministeriums des
                               Innern</akn:docProponent>
                               <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="e08874b2-05a8-4d6e-9d78-7be24380c54b">Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes</akn:docTitle>
                            </akn:p>
                         </akn:longTitle>
                         <akn:block eId="einleitung-1_block-1" GUID="a0973d49-d628-42f7-a1da-b004bc980a44" name="attributsemantik-noch-undefiniert">
                            <akn:date eId="einleitung-1_block-1_datum-1" GUID="f20d437a-3058-4747-8b8b-9b1e06c17273" refersTo="ausfertigung-datum" date="1900-01-01">Vom 01.01.1900</akn:date>
                         </akn:block>
                      </akn:preface>
                  </akn:act>
               </akn:akomaNtoso>
               """;
      var newXml =
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
                              <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                              <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                              <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                           </akn:FRBRExpression>
                       </akn:identification>
                     </akn:meta>
                     <akn:preface eId="einleitung-1" GUID="4554f060-e4ef-43a3-b71f-f30aa25769d6">
                         <akn:longTitle eId="einleitung-1_doktitel-1" GUID="185fcdbe-04f8-4b17-ac7c-2208c7f2f9df">
                            <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="a9694e02-330d-40e3-b0d1-50b2059f020c">
                               <akn:docStage eId="einleitung-1_doktitel-1_text-1_docstadium-1" GUID="884b29f7-584f-41e2-9329-d8780d33a3d7">Referentenentwurf</akn:docStage>
                               <akn:docProponent eId="einleitung-1_doktitel-1_text-1_docproponent-1" GUID="20095250-c44a-45a5-b7e3-2b49366ff5a8"> des Bundesministeriums des
                               Innern</akn:docProponent>
                               <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="e08874b2-05a8-4d6e-9d78-7be24380c54b">Entwurf eines Dritten Gesetzes zur Änderung des Vereinsgesetzes</akn:docTitle>
                            </akn:p>
                         </akn:longTitle>
                         <akn:block eId="einleitung-1_block-1" GUID="a0973d49-d628-42f7-a1da-b004bc980a44" name="attributsemantik-noch-undefiniert">
                            <akn:date eId="einleitung-1_block-1_datum-1" GUID="f20d437a-3058-4747-8b8b-9b1e06c17273" refersTo="ausfertigung-datum" date="1900-01-01">Vom 01.01.1900</akn:date>
                         </akn:block>
                      </akn:preface>
                  </akn:act>
               </akn:akomaNtoso>
               """;

      // When
      var norm = Norm.builder().document(XmlMapper.toDocument(oldXml)).build();
      normRepository.save(NormMapper.mapToDto(norm));

      // When // Then
      mockMvc
          .perform(
              put("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                  .accept(MediaType.APPLICATION_XML)
                  .contentType(MediaType.APPLICATION_XML)
                  .content(newXml))
          .andExpect(status().isOk())
          .andExpect(
              content()
                  .node(
                      hasXPath(
                          "//*[@eId=\"einleitung-1_doktitel-1_text-1_doctitel-1\"]",
                          equalTo(
                              "Entwurf eines Dritten Gesetzes zur Änderung des Vereinsgesetzes"))));
    }
  }

  @Nested
  class GetNormPreview {

    @Test
    void itAppliesTimeMachine() throws Exception {
      // Given
      final String xml =
          """
               <?xml version="1.0" encoding="UTF-8"?><?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?><akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                        http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                 <akn:act name="regelungstext">
                    <!-- Metadaten -->
                    <akn:meta GUID="82a65581-0ea7-4525-9190-35ff86c977af" eId="meta-1">
                       <akn:identification GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" eId="meta-1_ident-1" source="attributsemantik-noch-undefiniert">
                          <akn:FRBRExpression GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d" eId="meta-1_ident-1_frbrexpression-1">
                             <akn:FRBRthis GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"/>
                             <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                             <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                             <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                          </akn:FRBRExpression>
                      </akn:identification>
                    </akn:meta>
                    <akn:body>
                       <akn:p eId="one">old text</akn:p>
                       <akn:p eId="two">old text</akn:p>
                   </akn:body>
                 </akn:act>
               </akn:akomaNtoso>""";

      // When
      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
      normRepository.save(NormMapper.mapToDto(norm));

      var amendingNormXml =
          """
               <?xml version="1.0" encoding="UTF-8"?><?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?><akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                        http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                 <akn:body>
                    <akn:mod>
                      In <akn:ref href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml">paragraph 2</akn:ref> replace <akn:quotedText>old</akn:quotedText> with <akn:quotedText>new</akn:quotedText>.
                    </akn:mod>
                    "old" -> "new"
                 </akn:body>
               </akn:akomaNtoso>""";

      // When // Then
      mockMvc
          .perform(
              post("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/preview")
                  .contentType(MediaType.APPLICATION_XML)
                  .content(amendingNormXml)
                  .accept(MediaType.APPLICATION_XML))
          .andExpect(status().isOk())
          .andExpect(
              xpath(
                      "//akn:p[@eId=\"one\"]",
                      Map.of("akn", "http://Inhaltsdaten.LegalDocML.de/1.6/"))
                  .string("old text"))
          .andExpect(
              xpath(
                      "//akn:p[@eId=\"two\"]",
                      Map.of("akn", "http://Inhaltsdaten.LegalDocML.de/1.6/"))
                  .string("new text"));
    }

    @Test
    void itAppliesTimeMachineAndRendersHtml() throws Exception {
      // Given
      final String xml =
          """
               <?xml version="1.0" encoding="UTF-8"?><?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?><akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                        http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                 <akn:act name="regelungstext">
                    <!-- Metadaten -->
                    <akn:meta GUID="82a65581-0ea7-4525-9190-35ff86c977af" eId="meta-1">
                       <akn:identification GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" eId="meta-1_ident-1" source="attributsemantik-noch-undefiniert">
                          <akn:FRBRExpression GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d" eId="meta-1_ident-1_frbrexpression-1">
                             <akn:FRBRthis GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"/>
                             <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                             <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                             <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                          </akn:FRBRExpression>
                      </akn:identification>
                    </akn:meta>
                    <akn:body>
                       <akn:p eId="one">old text</akn:p>
                       <akn:p eId="two">old text</akn:p>
                   </akn:body>
                 </akn:act>
               </akn:akomaNtoso>""";

      // When
      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
      normRepository.save(NormMapper.mapToDto(norm));

      var amendingNormXml =
          """
               <?xml version="1.0" encoding="UTF-8"?><?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?><akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                        http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                 <akn:body>
                    <akn:mod>
                      In <akn:ref href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml">paragraph 2</akn:ref> replace <akn:quotedText>old</akn:quotedText> with <akn:quotedText>new</akn:quotedText>.
                    </akn:mod>
                    "old" -> "new"
                 </akn:body>
               </akn:akomaNtoso>""";

      // When // Then
      mockMvc
          .perform(
              post("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/preview")
                  .contentType(MediaType.APPLICATION_XML)
                  .content(amendingNormXml)
                  .accept(MediaType.TEXT_HTML))
          .andExpect(status().isOk())
          .andExpect(xpath("//*[@data-eId=\"one\"]").string("old text"))
          .andExpect(xpath("//*[@data-eId=\"two\"]").string("new text"));
    }
  }

  @Nested
  class GetNormTimeBoundaries {

    @Test
    void itExtractsAndReturnsTimeBoundariesFromNorm() throws Exception {
      // Given
      final String xml =
          """
               <?xml version="1.0" encoding="UTF-8"?><?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?><akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                        http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                 <akn:act name="regelungstext">
                    <!-- Metadaten -->
                    <akn:meta GUID="82a65581-0ea7-4525-9190-35ff86c977af" eId="meta-1">
                       <akn:identification GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" eId="meta-1_ident-1" source="attributsemantik-noch-undefiniert">
                          <akn:FRBRExpression GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d" eId="meta-1_ident-1_frbrexpression-1">
                             <akn:FRBRthis GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"/>
                             <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                             <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                             <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                          </akn:FRBRExpression>
                      </akn:identification>
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
                    <akn:body>
                       <akn:p eId="one">old text</akn:p>
                       <akn:p eId="two">old text</akn:p>
                   </akn:body>
                 </akn:act>
               </akn:akomaNtoso>""";

      // When
      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
      normRepository.save(NormMapper.mapToDto(norm));

      // When // Then
      mockMvc
          .perform(
              get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/timeBoundaries")
                  .accept(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(1)))
          .andExpect(jsonPath("$[0].date", is("2023-12-30")));
    }

    @Test
    void itExtractsAndReturnsTimeBoundariesFromNormWhenDateIsEmpty() throws Exception {
      // Given
      final String xml =
          """
               <?xml version="1.0" encoding="UTF-8"?><?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?><akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                        http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                 <akn:act name="regelungstext">
                    <!-- Metadaten -->
                    <akn:meta GUID="82a65581-0ea7-4525-9190-35ff86c977af" eId="meta-1">
                       <akn:identification GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" eId="meta-1_ident-1" source="attributsemantik-noch-undefiniert">
                          <akn:FRBRExpression GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d" eId="meta-1_ident-1_frbrexpression-1">
                             <akn:FRBRthis GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"/>
                             <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                             <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                             <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                          </akn:FRBRExpression>
                      </akn:identification>
                      <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                           <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29"
                               source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                           <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date=""
                               source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                      </akn:lifecycle>
                      <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                                          <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                                             <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                                          </akn:temporalGroup>
                       </akn:temporalData>
                    </akn:meta>
                    <akn:body>
                       <akn:p eId="one">old text</akn:p>
                       <akn:p eId="two">old text</akn:p>
                   </akn:body>
                 </akn:act>
               </akn:akomaNtoso>""";

      // When
      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
      normRepository.save(NormMapper.mapToDto(norm));

      // When // Then
      mockMvc
          .perform(
              get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/timeBoundaries")
                  .accept(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(1)))
          .andExpect(jsonPath("$[0].date", is(nullValue())));
    }
  }

  @Nested
  class UpdateMod {

    @Test
    void itUpdatesAMod() throws Exception {

      // Given
      var oldAmendingLawXml =
          """
              <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
              <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                                        http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                  <akn:act name="regelungstext">
                      <!-- Metadaten -->
                      <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                          <akn:identification>
                              <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4c69a6d2-8988-4581-bfa9-df9e8e24f321">
                                  <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="f3805314-bbb6-4def-b82b-8b7f0b126197" value="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"/>
                                  <akn:FRBRuri eId="meta-1_ident-1_frbrexpression-1_frbruri-1" GUID="5a2c4542-56cc-4c70-8b80-e2041b5b75e1" value="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"/>
                                  <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                                  <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                                  <akn:FRBRauthor eId="meta-1_ident-1_frbrexpression-1_frbrauthor-1" GUID="9063f5e7-c3c5-4ab4-8e15-459b11d7a9f2" href="recht.bund.de/institution/bundesregierung"/>
                                  <akn:FRBRdate eId="meta-1_ident-1_frbrexpression-1_frbrdate-1" GUID="1e8f33a8-d124-48c3-a864-7968701816ee" date="2017-03-15" name="verkuendung"/>
                                  <akn:FRBRlanguage eId="meta-1_ident-1_frbrexpression-1_frbrlanguage-1" GUID="9c61581b-ce24-4589-8db8-533262149b90" language="deu"/>
                                  <akn:FRBRversionNumber eId="meta-1_ident-1_frbrexpression-1_frbrersionnumber-1" GUID="de475d52-7263-4c05-8014-e92a7785b784" value="1"/>
                              </akn:FRBRExpression>
                          </akn:identification>
                          <akn:analysis eId="meta-1_analysis-1" GUID="c0eb49c8-bf39-4a4a-b324-3b0feb88c1f1" source="attributsemantik-noch-undefiniert">
                              <akn:activeModifications eId="meta-1_analysis-1_activemod-1" GUID="cd241744-ace4-436c-a0e3-dc1ee8caf3ac">
                                  <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-2" GUID="8992dd02-ab87-42e8-bee2-86b76f587f81" type="substitution">
                                      <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-2_source-1" GUID="7537d65c-2a3b-440c-80ec-257073b1d1d3" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"/>
                                      <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-2_destination-1" GUID="83a4e169-ec57-4981-b191-84afe42130c8" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml"/>
                                      <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-2_gelzeitnachw-1" GUID="9180eb9f-9da2-4fa4-b57f-803d4ddcdbc9" period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
                                  </akn:textualMod>
                              </akn:activeModifications>
                          </akn:analysis>
                      </akn:meta>
                      <akn:body>
                          <akn:mod eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1" GUID="148c2f06-6e33-4af8-9f4a-3da67c888510" refersTo="aenderungsbefehl-ersetzen">In <akn:ref eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_ref-1" GUID="61d3036a-d7d9-4fa5-b181-c3345caa3206" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml">§ 20 Absatz 1 Satz 2</akn:ref> wird
                                            die Angabe <akn:quotedText eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-1" GUID="694459c4-ef66-4f87-bb78-a332054a2216" startQuote="„" endQuote="“">§ 9 Abs. 1 Satz 2, Abs. 2</akn:quotedText> durch die
                                            Wörter <akn:quotedText eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-2" GUID="dd25bdb6-4ef4-4ef5-808c-27579b6ae196" startQuote="„" endQuote="“">§ 9 Absatz 1 Satz 2, Absatz 2 oder 3</akn:quotedText>
                                            ersetzt.</akn:mod>
                      </akn:body>
                  </akn:act>
              </akn:akomaNtoso>
                   """;

      // When
      var norm = Norm.builder().document(XmlMapper.toDocument(oldAmendingLawXml)).build();
      normRepository.save(NormMapper.mapToDto(norm));

      final String amendingLawEli = "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1";
      final String modEid =
          "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1";

      // When // Then
      mockMvc
          .perform(
              put("/api/v1/norms/" + amendingLawEli + "/mod/" + modEid)
                  .accept(MediaType.APPLICATION_XML)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(
                      "{\"refersTo\": \"aenderungsbefehl-ersetzen\", \"timeBoundaryEid\": \"new-time-boundary-eid\", \"destinationHref\": \"new-destination-href\", \"oldText\": \"old text\", \"newText\": \"new test text\"}"))
          .andExpect(status().isOk())
          .andExpect(
              xpath("//activeModifications/textualMod/destination/@href")
                  .string("new-destination-href"))
          .andExpect(
              xpath("//activeModifications/textualMod/force/@period")
                  .string("new-time-boundary-eid"))
          .andExpect(xpath("//body/mod/ref/@href").string("new-destination-href"))
          .andExpect(xpath("//body/mod/quotedText[2]").string("new test text"));
    }
  }
}
