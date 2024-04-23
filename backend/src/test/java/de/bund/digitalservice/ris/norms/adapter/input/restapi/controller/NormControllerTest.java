package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.config.SecurityConfig;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.TimeBoundary;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import java.util.List;
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
  @MockBean private LoadSpecificArticleXmlFromNormUseCase loadSpecificArticleXmlFromNormUseCase;
  @MockBean private UpdateNormXmlUseCase updateNormXmlUseCase;
  @MockBean private TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase;
  @MockBean private LoadTimeBoundariesUseCase loadTimeBoundariesUseCase;
  @MockBean private TimeMachineUseCase timeMachineUseCase;

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
                            eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="8c4eabab-9893-455e-b83b-c46f2453f2fb">Gesetz zur Regelungs des öffenltichen Vereinsrechts</akn:docTitle>
                      </akn:p>
                   </akn:longTitle>
                </akn:preface>
             </akn:act>
          </akn:akomaNtoso>
          """))
              .build();

      // When
      when(loadNormUseCase.loadNorm(any())).thenReturn(Optional.of(norm));

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
                  .value(equalTo("Gesetz zur Regelungs des öffenltichen Vereinsrechts")))
          .andExpect(jsonPath("frbrNumber").value(equalTo("s593")))
          .andExpect(jsonPath("frbrName").value(equalTo("BGBl. I")))
          .andExpect(jsonPath("publicationDate").value(equalTo("1964-08-05")));
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
      when(loadNormXmlUseCase.loadNormXml(any())).thenReturn(Optional.of(xml));

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

      when(loadNormXmlUseCase.loadNormXml(any())).thenReturn(Optional.of(xml));
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

      when(loadNormXmlUseCase.loadNormXml(any())).thenReturn(Optional.of(xml));
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
  }

  @Nested
  class getArticlesRender {

    @Test
    void itCallsNormServiceAndReturnsNormRender() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String xml = "<akn:doc></akn:doc>";
      final String html = "<div></div>";

      when(loadSpecificArticleXmlFromNormUseCase.loadSpecificArticles(any()))
          .thenReturn(List.of(xml));
      when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(any())).thenReturn(html);

      // When // Then
      mockMvc
          .perform(
              get("/api/v1/norms/{eli}/articles?refersTo=something", eli)
                  .accept(MediaType.TEXT_HTML))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
          .andExpect(content().string(html));

      verify(transformLegalDocMlToHtmlUseCase, times(1))
          .transformLegalDocMlToHtml(argThat(query -> query.xml().equals(xml)));
    }
  }

  @Nested
  class updateNormRender {

    @Test
    void itCallsNormServiceAndUpdatesNorm() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String xml = "<akn:doc>new</akn:doc>";

      when(updateNormXmlUseCase.updateNormXml(any())).thenReturn(Optional.of(xml));

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

    @Test
    void itCallsNormServiceAndReturnsErrorMessage() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String xml = "<akn:doc>new</akn:doc>";

      when(updateNormXmlUseCase.updateNormXml(any()))
          .thenThrow(new UpdateNormXmlUseCase.InvalidUpdateException("Error Message"));

      // When // Then
      mockMvc
          .perform(
              put("/api/v1/norms/{eli}", eli)
                  .accept(MediaType.APPLICATION_XML)
                  .contentType(MediaType.APPLICATION_XML)
                  .content(xml))
          .andExpect(status().isBadRequest())
          .andExpect(content().string("Error Message"));

      verify(updateNormXmlUseCase, times(1))
          .updateNormXml(argThat(query -> query.xml().equals(xml)));
    }

    @Test
    void itCallsNormServiceAndReturnsNotFound() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String xml = "<akn:doc>new</akn:doc>";

      when(updateNormXmlUseCase.updateNormXml(any())).thenReturn(Optional.empty());

      // When // Then
      mockMvc
          .perform(
              put("/api/v1/norms/{eli}", eli)
                  .accept(MediaType.APPLICATION_XML)
                  .contentType(MediaType.APPLICATION_XML)
                  .content(xml))
          .andExpect(status().isNotFound());

      verify(updateNormXmlUseCase, times(1))
          .updateNormXml(argThat(query -> query.xml().equals(xml)));
    }
  }

  @Nested
  class getPreview {

    @Test
    void itReturnsPreview() throws Exception {
      // Given
      when(timeMachineUseCase.applyTimeMachine(any())).thenReturn(Optional.of("<xml>result</xml>"));

      // When // Then
      mockMvc
          .perform(
              post("/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/preview")
                  .contentType(MediaType.APPLICATION_XML)
                  .content("<xml>amending-law</xml>")
                  .accept(MediaType.APPLICATION_XML))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
          .andExpect(content().string("<xml>result</xml>"));

      verify(timeMachineUseCase, times(1))
          .applyTimeMachine(
              argThat(
                  query ->
                      query
                              .targetLawEli()
                              .equals("eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1")
                          && query.amendingLawXml().equals("<xml>amending-law</xml>")));
    }
  }

  @Nested
  class getHtmlPreview {

    @Test
    void itReturnsPreview() throws Exception {
      // Given
      when(timeMachineUseCase.applyTimeMachine(any())).thenReturn(Optional.of("<xml>result</xml>"));
      when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(any()))
          .thenReturn("<html></html>");

      // When // Then
      mockMvc
          .perform(
              post("/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/preview")
                  .contentType(MediaType.APPLICATION_XML)
                  .content("<xml>amending-law</xml>")
                  .accept(MediaType.TEXT_HTML))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
          .andExpect(content().string("<html></html>"));

      verify(timeMachineUseCase, times(1))
          .applyTimeMachine(
              argThat(
                  query ->
                      query
                              .targetLawEli()
                              .equals("eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1")
                          && query.amendingLawXml().equals("<xml>amending-law</xml>")));
      verify(transformLegalDocMlToHtmlUseCase, times(1))
          .transformLegalDocMlToHtml(argThat(query -> query.xml().equals("<xml>result</xml>")));
    }
  }

  @Nested
  class TimeBoundaries {

    @Test
    void getTimeBoundariesReturnsCorrectData() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";

      List<TimeBoundary> timeBoundaries =
          List.of(
              new TimeBoundary(LocalDate.parse("2023-12-29"), "meta-1_lebzykl-1_ereignis-1"),
              new TimeBoundary(LocalDate.parse("2023-12-30"), "meta-1_lebzykl-1_ereignis-2"));

      when(loadTimeBoundariesUseCase.loadTimeBoundariesOfNorm(
              new LoadTimeBoundariesUseCase.Query(eli)))
          .thenReturn(timeBoundaries);

      // When // Then
      mockMvc
          .perform(
              get("/api/v1/norms/{eli}/timeBoundaries", eli).accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(2)))
          .andExpect(jsonPath("$[0].date", is("2023-12-29")))
          .andExpect(jsonPath("$[0].eid", is("meta-1_lebzykl-1_ereignis-1")))
          .andExpect(jsonPath("$[1].date", is("2023-12-30")))
          .andExpect(jsonPath("$[1].eid", is("meta-1_lebzykl-1_ereignis-2")));

      verify(loadTimeBoundariesUseCase, times(1))
          .loadTimeBoundariesOfNorm(any(LoadTimeBoundariesUseCase.Query.class));
    }
  }
}
