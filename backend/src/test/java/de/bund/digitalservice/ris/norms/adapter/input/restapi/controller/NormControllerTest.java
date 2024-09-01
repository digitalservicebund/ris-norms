package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.config.SecurityConfig;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Not using SpringBootTest annotation to avoid needing a database connection. Using @Import to load
 * the {@link SecurityConfig} in order to avoid http 401 Unauthorised
 */
@WebMvcTest(NormController.class)
@Import(SecurityConfig.class)
class NormControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private LoadNormUseCase loadNormUseCase;
  @MockBean private LoadNormXmlUseCase loadNormXmlUseCase;
  @MockBean private UpdateNormXmlUseCase updateNormXmlUseCase;
  @MockBean private TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase;
  @MockBean private ApplyPassiveModificationsUseCase applyPassiveModificationsUseCase;
  @MockBean private UpdateModsUseCase updateModsUseCase;
  @MockBean private UpdateModUseCase updateModUseCase;

  @Nested
  class getNorm {

    @Test
    void itReturnsNorm() throws Exception {
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
                                                           <akn:docTitle
                                                              eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="8c4eabab-9893-455e-b83b-c46f2453f2fb">Gesetz zur Regelung des öffentlichen Vereinsrechts</akn:docTitle>
                                                        </akn:p>
                                                     </akn:longTitle>
                                                  </akn:preface>
                                               </akn:act>
                                            </akn:akomaNtoso>
                                            """))
              .build();

      // When
      when(loadNormUseCase.loadNorm(any())).thenReturn(norm);

      // When // Then
      mockMvc
          .perform(
              get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                  .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(
              jsonPath("eli")
                  .value(equalTo("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")))
          .andExpect(
              jsonPath("title")
                  .value(equalTo("Gesetz zur Regelung des öffentlichen Vereinsrechts")))
          .andExpect(jsonPath("frbrNumber").value(equalTo("s593")))
          .andExpect(jsonPath("frbrName").value(equalTo("BGBl. I")))
          .andExpect(jsonPath("frbrDateVerkuendung").value(equalTo("1964-08-05")));

      verify(loadNormUseCase, times(1))
          .loadNorm(
              argThat(
                  query ->
                      query
                          .eli()
                          .equals("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")));
    }
  }

  @Nested
  class getNormXml {

    @Test
    void itCallsLoadNormXmlAndReturnsNormXml() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String xml = "<target></target>";

      // When
      when(loadNormXmlUseCase.loadNormXml(any())).thenReturn(xml);

      // When // Then
      mockMvc
          .perform(get("/api/v1/norms/{eli}", eli).accept(MediaType.APPLICATION_XML))
          .andExpect(status().isOk())
          .andExpect(content().string(xml));
    }
  }

  @Nested
  class getNormRender {

    @Test
    void itCallsNormServiceAndReturnsNormRender() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String xml = "<akn:doc></akn:doc>";
      final String html = "<div></div>";

      when(loadNormXmlUseCase.loadNormXml(any())).thenReturn(xml);
      when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(any())).thenReturn(html);

      // When // Then
      mockMvc
          .perform(get("/api/v1/norms/{eli}", eli).accept(MediaType.TEXT_HTML))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
          .andExpect(content().string(html));

      verify(transformLegalDocMlToHtmlUseCase, times(1))
          .transformLegalDocMlToHtml(
              argThat(query -> query.xml().equals(xml) && !query.showMetadata()));
    }

    @Test
    void itCallsNormServiceAndReturnsNormRenderWithMetadata() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String xml = "<akn:doc></akn:doc>";
      final String html = "<div></div>";

      when(loadNormXmlUseCase.loadNormXml(any())).thenReturn(xml);
      when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(any())).thenReturn(html);

      // When // Then
      mockMvc
          .perform(get("/api/v1/norms/{eli}?showMetadata=true", eli).accept(MediaType.TEXT_HTML))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
          .andExpect(content().string(html));

      verify(transformLegalDocMlToHtmlUseCase, times(1))
          .transformLegalDocMlToHtml(
              argThat(query -> query.xml().equals(xml) && query.showMetadata()));
    }

    @Test
    void itCallsNormServiceAndReturnsNormRenderWithAtIsoDate() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String html = "<div></div>";

      when(loadNormUseCase.loadNorm(any())).thenReturn(NormFixtures.loadFromDisk("SimpleNorm.xml"));
      when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(any())).thenReturn(html);
      when(applyPassiveModificationsUseCase.applyPassiveModifications(any()))
          .thenReturn(NormFixtures.loadFromDisk("SimpleNorm.xml"));

      // When // Then
      mockMvc
          .perform(
              get("/api/v1/norms/{eli}?atIsoDate=2024-04-03T00:00:00.000Z", eli)
                  .accept(MediaType.TEXT_HTML))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
          .andExpect(content().string(html));

      verify(applyPassiveModificationsUseCase, times(1)).applyPassiveModifications(any());
    }
  }

  @Nested
  class updateNormRender {

    @Test
    void itCallsNormServiceAndUpdatesNorm() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String xml = "<akn:doc>new</akn:doc>";

      when(updateNormXmlUseCase.updateNormXml(any())).thenReturn(xml);

      // When // Then
      mockMvc
          .perform(
              put("/api/v1/norms/{eli}", eli)
                  .accept(MediaType.APPLICATION_XML)
                  .contentType(MediaType.APPLICATION_XML)
                  .content(xml))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
          .andExpect(content().string(xml));

      verify(updateNormXmlUseCase, times(1))
          .updateNormXml(argThat(query -> query.xml().equals(xml)));
    }
  }

  @Nested
  class updateMod {

    @Test
    void itCallsUpdateModUseCaseAndReturnsXml() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String modEid = "mod-eid-1";
      final String amendingNormXml = "<law></law>";
      final String targetNormZf0Xml = "<target-norm-xml></target-norm-xml>";

      // When
      when(updateModUseCase.updateMod(any()))
          .thenReturn(Optional.of(new UpdateModUseCase.Result(amendingNormXml, targetNormZf0Xml)));

      // When // Then
      mockMvc
          .perform(
              put("/api/v1/norms/" + eli + "/mods/" + modEid)
                  .accept(MediaType.APPLICATION_JSON)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(
                      "{\"refersTo\": \"aenderungsbefehl-ersetzen\", \"timeBoundaryEid\": \"new-time-boundary-eid\", \"destinationHref\": \"new-destination-href\", \"newContent\": \"new test text\"}"))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("amendingNormXml").value(amendingNormXml))
          .andExpect(jsonPath("targetNormZf0Xml").value(targetNormZf0Xml));

      verify(updateModUseCase, times(1)).updateMod(argThat(query -> !query.dryRun()));
    }

    @Test
    void itCallsUpdateModUseCaseWithDryRunAndReturnsXml() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String modEid = "mod-eid-1";
      final String amendingNormXml = "<law></law>";
      final String targetNormZf0Xml = "<target-norm-xml></target-norm-xml>";

      // When
      when(updateModUseCase.updateMod(any()))
          .thenReturn(Optional.of(new UpdateModUseCase.Result(amendingNormXml, targetNormZf0Xml)));

      // When // Then
      mockMvc
          .perform(
              put("/api/v1/norms/" + eli + "/mods/" + modEid + "?dryRun=true")
                  .accept(MediaType.APPLICATION_JSON)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(
                      "{\"refersTo\": \"aenderungsbefehl-ersetzen\", \"timeBoundaryEid\": \"new-time-boundary-eid\", \"destinationHref\": \"new-destination-href\", \"newContent\": \"new test text\"}"))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("amendingNormXml").value(amendingNormXml))
          .andExpect(jsonPath("targetNormZf0Xml").value(targetNormZf0Xml));

      verify(updateModUseCase, times(1)).updateMod(argThat(UpdateModUseCase.Query::dryRun));
    }
  }

  @Nested
  class updateMods {

    @Test
    void itCallsUpdateModsUseCaseAndReturnsXml() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String amendingNormXml = "<law></law>";
      final String targetNormZf0Xml = "<target-norm-xml></target-norm-xml>";

      // When
      when(updateModsUseCase.updateMods(any()))
          .thenReturn(Optional.of(new UpdateModsUseCase.Result(amendingNormXml, targetNormZf0Xml)));

      // When // Then
      mockMvc
          .perform(
              patch("/api/v1/norms/" + eli + "/mods")
                  .accept(MediaType.APPLICATION_JSON)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(
                      "{\"mod-eid-1\": {\"timeBoundaryEid\": \"new-time-boundary-eid\"},\n"
                          + "\"mod-eid-2\": {\"timeBoundaryEid\": \"new-time-boundary-eid\"}}"))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("amendingNormXml").value(amendingNormXml))
          .andExpect(jsonPath("targetNormZf0Xml").value(targetNormZf0Xml));

      verify(updateModsUseCase, times(1)).updateMods(argThat(query -> !query.dryRun()));
    }

    @Test
    void itCallsUpdateModsUseCaseWithDryRunAndReturnsXml() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String amendingNormXml = "<law></law>";
      final String targetNormZf0Xml = "<target-norm-xml></target-norm-xml>";

      // When
      when(updateModsUseCase.updateMods(any()))
          .thenReturn(Optional.of(new UpdateModsUseCase.Result(amendingNormXml, targetNormZf0Xml)));

      // When // Then
      mockMvc
          .perform(
              patch("/api/v1/norms/" + eli + "/mods?dryRun=true")
                  .accept(MediaType.APPLICATION_JSON)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("{\"mod-eid-1\": {\"timeBoundaryEid\": \"new-time-boundary-eid\"}}"))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("amendingNormXml").value(amendingNormXml))
          .andExpect(jsonPath("targetNormZf0Xml").value(targetNormZf0Xml));

      verify(updateModsUseCase, times(1)).updateMods(argThat(UpdateModsUseCase.Query::dryRun));
    }
  }
}
