package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static de.bund.digitalservice.ris.norms.XPathMatcher.hasXPath;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser
class NormExpressionControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DokumentRepository dokumentRepository;

  @Autowired
  private NormManifestationRepository normManifestationRepository;

  @AfterEach
  void cleanUp() {
    dokumentRepository.deleteAll();
    normManifestationRepository.deleteAll();
  }

  @Nested
  class GetNormByEli {

    // Note: Error case missing

    @Test
    void itCallsNormsServiceAndReturnsNorm() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("Vereinsgesetz.xml"))
      );

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
        )
        .andExpect(jsonPath("title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts"))
        .andExpect(jsonPath("frbrName").value("BGBl. I"))
        .andExpect(jsonPath("frbrNumber").value("s593"))
        .andExpect(jsonPath("frbrDateVerkuendung").value("1964-08-05"));
    }

    @Test
    void itCallsNormsServiceAndReturnsNormXml() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("Vereinsgesetz.xml"))
      );

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
            .accept(MediaType.APPLICATION_XML)
        )
        .andExpect(status().isOk())
        .andExpect(
          xpath("//*[@eId='einleitung-1_doktitel-1_text-1_doctitel-1']")
            .string("Gesetz zur Regelung des öffentlichen Vereinsrechts")
        );
    }

    @Test
    void itCallsNormServiceAndReturnsNormRender() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("Vereinsgesetz_2017_s419_2017-03-15.xml")
        )
      );

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1")
            .accept(MediaType.TEXT_HTML)
        )
        .andExpect(status().isOk())
        .andExpect(
          content()
            .node(
              hasXPath(
                "//h1//*[@data-eId=\"einleitung-1_doktitel-1_text-1_doctitel-1\"]",
                equalTo("Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes")
              )
            )
        );
    }
  }

  @Nested
  class GetNormRenderWithQueryParameters {

    // Note: Error case missing

    @Test
    void itCallsNormServiceAndReturnsNormRenderWithMetadata() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithMods.xml"))
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1?showMetadata=true"
          )
            .accept(MediaType.TEXT_HTML)
        )
        .andExpect(status().isOk())
        .andExpect(
          content()
            .node(
              hasXPath(
                "//h1//*[@data-eId=\"einleitung-1_doktitel-1_text-1_doctitel-1\"]",
                equalTo("Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes")
              )
            )
        )
        .andExpect(
          content()
            .node(
              hasXPath(
                "//section[@class=\"metadata\"]//td[text()=\"Amtliche Langüberschrift\"]/following-sibling::td/text()",
                equalTo("Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes")
              )
            )
        );
    }

    @Test
    void itReturnsBadRequestWhenRenderingAtInvalidIsoDate() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml"))
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1?atIsoDate=NOT_A_DATE"
          )
            .accept(MediaType.TEXT_HTML)
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("type").value("/errors/parameter-binding-error"))
        .andExpect(jsonPath("title").value("Parameter Binding Error"))
        .andExpect(jsonPath("status").value(400))
        .andExpect(jsonPath("detail").value("Invalid request parameter: NOT_A_DATE"))
        .andExpect(
          jsonPath("instance")
            .value("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
        );
    }

    @Test
    void itReturnsHtmlWhenRenderingAtValidTimeBoundary() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml"))
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1?atIsoDate=2024-04-25T14:37:14.434Z"
          )
            .accept(MediaType.TEXT_HTML)
        )
        .andExpect(status().isOk())
        .andExpect(content().node(hasXPath("//div")));
    }
  }

  @Nested
  class PutNormByEli {

    @Test
    void itReturnsNotFound() throws Exception {
      // Given no norm in database
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String xml =
        "<akn:doc xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.7.2/\">new</akn:doc>";

      // When
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}", eli)
            .accept(MediaType.APPLICATION_XML)
            .contentType(MediaType.APPLICATION_XML)
            .content(xml)
        )
        // Then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value("Norm with eli eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu does not exist")
        )
        .andExpect(
          jsonPath("instance")
            .value("/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1")
        )
        .andExpect(jsonPath("eli").value("eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu"));
    }

    @Test
    void itThrowsInvalidUpdateExceptionBecauseEliChanged() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml"))
      );
      var newXml =
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
                       <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/CHANGED" />
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

      // When // Then
      mockMvc
        .perform(
          put("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
            .accept(MediaType.APPLICATION_XML)
            .contentType(MediaType.APPLICATION_XML)
            .content(newXml)
        )
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type").value("/errors/invalidate-update"))
        .andExpect(jsonPath("title").value("Invalid update operation"))
        .andExpect(jsonPath("status").value(422))
        .andExpect(jsonPath("detail").value("Changing the ELI is not supported."))
        .andExpect(
          jsonPath("instance")
            .value("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
        );
    }

    @Test
    void itThrowsInvalidUpdateExceptionBecauseGuidChanged() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml"))
      );
      var newXml =
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
                       <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                       <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6123"/>
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

      // When // Then
      mockMvc
        .perform(
          put("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
            .accept(MediaType.APPLICATION_XML)
            .contentType(MediaType.APPLICATION_XML)
            .content(newXml)
        )
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type").value("/errors/invalidate-update"))
        .andExpect(jsonPath("title").value("Invalid update operation"))
        .andExpect(jsonPath("status").value(422))
        .andExpect(jsonPath("detail").value("Changing the GUID is not supported."))
        .andExpect(
          jsonPath("instance")
            .value("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
        );
    }

    @Test
    void itCallsNormServiceAndUpdatesNorm() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml"))
      );
      var newXml =
        """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                   http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
           <akn:act name="regelungstext">
              <!-- Metadaten -->
              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                 <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                    <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8e">
                       <akn:FRBRthis eId="meta-1_ident-1_frbrwork-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c70" value="eli/bund/bgbl-1/1964/s593/regelungstext-1" />
                       <akn:FRBRuri eId="meta-1_ident-1_frbrwork-1_frbruri-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c71" value="eli/bund/bgbl-1/1964/s593" />
                    </akn:FRBRWork>
                    <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                       <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                       <akn:FRBRuri eId="meta-1_ident-1_frbrexpression-1_frbruri-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c79" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu" />
                       <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                           <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="77167d15-511d-4927-adf3-3c8b0464423c"/>
                       <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                    </akn:FRBRExpression>
                         <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1"
                                                GUID="bd2375e5-3e81-435d-a4f8-159d8572c46b">
                            <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"
                                          GUID="9dcc818e-3ed8-4414-b562-342bd5f405b3"
                                              value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"/>
                            <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"
                                          GUID="9dcc818e-3ed8-4414-b562-342bd5f405b4"
                                              value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"/>
                            <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"
                                          GUID="f3288a2a-3511-454e-ada1-9de8c33f6dbe"
                                          date="1964-08-05"
                                          name="generierung"/>
                            <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"
                                            GUID="634de2b4-5d14-4144-9e8e-80548da73b73"
                                            value="xml"/>
                         </akn:FRBRManifestation>
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

      // When // Then
      mockMvc
        .perform(
          put("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
            .accept(MediaType.APPLICATION_XML)
            .contentType(MediaType.APPLICATION_XML)
            .content(newXml)
        )
        .andExpect(status().isOk())
        .andExpect(
          content()
            .node(
              hasXPath(
                "//*[@eId=\"einleitung-1_doktitel-1_text-1_doctitel-1\"]",
                equalTo("Entwurf eines Dritten Gesetzes zur Änderung des Vereinsgesetzes")
              )
            )
        );
    }
  }

  @Nested
  class GetNormTimeBoundaries {

    // Note: Error case missing

    @Test
    void itExtractsAndReturnsTimeBoundariesFromNorm() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithMods.xml"))
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/timeBoundaries"
          )
            .accept(MediaType.APPLICATION_JSON_VALUE)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].date", is("2023-12-30")))
        .andExpect(jsonPath("$[1].date", is("2023-12-30")));
    }

    @Test
    void itExtractsAndReturnsTimeBoundariesFromNormWhenDateIsEmpty() throws Exception {
      // Given
      final String xml =
        """
        <?xml version="1.0" encoding="UTF-8"?><?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?><akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                        http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
            <akn:act name="regelungstext">
               <!-- Metadaten -->
               <akn:meta GUID="82a65581-0ea7-4525-9190-35ff86c977af" eId="meta-1">
                  <akn:identification GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" eId="meta-1_ident-1" source="attributsemantik-noch-undefiniert">
                      <akn:FRBRWork GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8e" eId="meta-1_ident-1_frbrwork-1">
                        <akn:FRBRthis GUID="c01334e2-f12b-4055-ac82-15ac03c74c70" eId="meta-1_ident-1_frbrwork-1_frbrthis-1" value="eli/bund/bgbl-1/1964/s593/regelungstext-1"/>
                        <akn:FRBRuri GUID="c01334e2-f12b-4055-ac82-15ac03c74c71" eId="meta-1_ident-1_frbrwork-1_frbruri-1" value="eli/bund/bgbl-1/1964/s593"/>
                     </akn:FRBRWork>
                     <akn:FRBRExpression GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d" eId="meta-1_ident-1_frbrexpression-1">
                        <akn:FRBRthis GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"/>
                        <akn:FRBRuri GUID="c01334e2-f12b-4055-ac82-15ac03c74c79" eId="meta-1_ident-1_frbrexpression-1_frbruri-1" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"/>
                        <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                        <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                        <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                     </akn:FRBRExpression>
                          <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1"
                                                 GUID="ea61dfec-d89c-442a-9f6d-cb65d8ed2dc3">
                             <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"
                                           GUID="d74e4be8-c15d-4a9f-8ae6-781e522dc7a4"
                                               value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23/regelungstext-1.xml"/>
                             <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"
                                          GUID="6e12c94c-f206-4144-bedf-dcab30867f4c"
                                              value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23/regelungstext-1.xml"/>
                             <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"
                                           GUID="791a8124-d12e-45e1-9c80-5f0438e4d046"
                                           date="2022-08-23"
                                           name="generierung"/>
                             <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"
                                             GUID="f9d34cba-d819-4468-b6a7-4a3d76046a26"
                                             href="recht.bund.de"/>
                             <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"
                                             GUID="dcf3aa47-de13-4ef6-9dce-1325a121fb4d"
                                             value="xml"/>
                          </akn:FRBRManifestation>
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
      dokumentRepository.save(
        DokumentMapper.mapToDto(new Regelungstext(XmlMapper.toDocument(xml)))
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/timeBoundaries"
          )
            .accept(MediaType.APPLICATION_JSON_VALUE)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].date", is(nullValue())));
    }
  }
}
