package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.service.ProprietaryService;
import de.bund.digitalservice.ris.norms.config.SecurityConfig;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormNotFoundException;
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
@WebMvcTest(ProprietaryController.class)
@Import(SecurityConfig.class)
class ProprietaryControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ProprietaryService proprietaryService;

  @Nested
  class getProprietary {

    @Test
    void returns404IfNormNotFound() throws Exception {
      // given
      var eli = "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1";
      when(proprietaryService.loadProprietaryFromNorm(
              new LoadProprietaryFromNormUseCase.Query(eli)))
          .thenThrow(new NormNotFoundException(eli));
      // when
      mockMvc
          .perform(
              get("/api/v1/norms/" + eli + "/proprietary").accept(MediaType.APPLICATION_JSON_VALUE))
          // then
          .andExpect(status().isNotFound());
    }

    @Test
    void returnsProprietaryResponseSchema() throws Exception {
      // given
      var eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
      var normWithProprietary = NormFixtures.loadFromDisk("NormWithProprietary.xml");
      var proprietary = normWithProprietary.getMeta().getOrCreateProprietary();
      when(proprietaryService.loadProprietaryFromNorm(
              new LoadProprietaryFromNormUseCase.Query(eli)))
          .thenReturn(proprietary);

      // when
      mockMvc
          .perform(
              get("/api/v1/norms/" + eli + "/proprietary").accept(MediaType.APPLICATION_JSON_VALUE))
          // then
          .andExpect(status().isOk())
          .andExpect(jsonPath("fna.value").value("754-28-1"));
    }

    @Test
    void returnsEmptyValuesIfSpecificProprietaryDataIsNotFound() throws Exception {
      // given
      var eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
      var normWithInvalidProprietary = NormFixtures.loadFromDisk("NormWithInvalidProprietary.xml");
      var proprietary = normWithInvalidProprietary.getMeta().getOrCreateProprietary();
      when(proprietaryService.loadProprietaryFromNorm(
              new LoadProprietaryFromNormUseCase.Query(eli)))
          .thenReturn(proprietary);

      // when
      mockMvc
          .perform(
              get("/api/v1/norms/" + eli + "/proprietary").accept(MediaType.APPLICATION_JSON_VALUE))
          // then
          .andExpect(status().isOk())
          .andExpect(jsonPath("fna").exists())
          .andExpect(jsonPath("fna.value").doesNotExist());
    }

    @Test
    void returnsEmptyValuesIfProprietaryDoesNotExist() throws Exception {
      // given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      var normWithInvalidProprietary = NormFixtures.loadFromDisk("SimpleNorm.xml");
      var proprietary = normWithInvalidProprietary.getMeta().getOrCreateProprietary();
      when(proprietaryService.loadProprietaryFromNorm(
              new LoadProprietaryFromNormUseCase.Query(eli)))
          .thenReturn(proprietary);

      // when
      mockMvc
          .perform(
              get("/api/v1/norms/" + eli + "/proprietary").accept(MediaType.APPLICATION_JSON_VALUE))
          // then
          .andExpect(status().isOk())
          .andExpect(jsonPath("fna").exists())
          .andExpect(jsonPath("fna.value").doesNotExist());
    }
  }

  @Nested
  class getProprietaryAtDate {
    @Test
    void returns404IfNormNotFound() throws Exception {
      // given
      var eli = "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1";
      var atDateString = "2024-06-03";
      when(proprietaryService.loadProprietaryFromNorm(
              new LoadProprietaryFromNormUseCase.Query(eli)))
          .thenThrow(new NormNotFoundException(eli));
      // when
      mockMvc
          .perform(
              get("/api/v1/norms/" + eli + "/proprietary/" + atDateString)
                  .accept(MediaType.APPLICATION_JSON_VALUE))
          // then
          .andExpect(status().isNotFound());
    }

    @Test
    void returnsProprietaryResponseSchema() throws Exception {
      // given
      var eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
      var atDateString = "2024-06-03";
      var normWithProprietary = NormFixtures.loadFromDisk("NormWithProprietary.xml");
      var proprietary = normWithProprietary.getMeta().getOrCreateProprietary();
      when(proprietaryService.loadProprietaryFromNorm(
              new LoadProprietaryFromNormUseCase.Query(eli)))
          .thenReturn(proprietary);

      // when
      mockMvc
          .perform(
              get("/api/v1/norms/" + eli + "/proprietary/" + atDateString)
                  .accept(MediaType.APPLICATION_JSON_VALUE))
          // then
          .andExpect(status().isOk())
          .andExpect(jsonPath("fna.value").value("754-28-1"));
    }

    @Test
    void returnsEmptyValuesIfSpecificProprietaryDataIsNotFound() throws Exception {
      // given
      var eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
      var atDateString = "2024-06-03";
      var normWithInvalidProprietary = NormFixtures.loadFromDisk("NormWithInvalidProprietary.xml");
      var proprietary = normWithInvalidProprietary.getMeta().getOrCreateProprietary();
      when(proprietaryService.loadProprietaryFromNorm(
              new LoadProprietaryFromNormUseCase.Query(eli)))
          .thenReturn(proprietary);

      // when
      mockMvc
          .perform(
              get("/api/v1/norms/" + eli + "/proprietary/" + atDateString)
                  .accept(MediaType.APPLICATION_JSON_VALUE))
          // then
          .andExpect(status().isOk())
          .andExpect(jsonPath("fna").exists())
          .andExpect(jsonPath("fna.value").doesNotExist())
          .andExpect(jsonPath("art").exists())
          .andExpect(jsonPath("art.value").doesNotExist())
          .andExpect(jsonPath("typ").exists())
          .andExpect(jsonPath("typ.value").doesNotExist())
          .andExpect(jsonPath("subtyp").exists())
          .andExpect(jsonPath("subtyp.value").doesNotExist());
    }

    @Test
    void returnsEmptyValuesIfProprietaryDoesNotExistAtAll() throws Exception {
      // given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      var atDateString = "2024-06-03";
      var normWithInvalidProprietary = NormFixtures.loadFromDisk("SimpleNorm.xml");
      var proprietary = normWithInvalidProprietary.getMeta().getOrCreateProprietary();
      when(proprietaryService.loadProprietaryFromNorm(
              new LoadProprietaryFromNormUseCase.Query(eli)))
          .thenReturn(proprietary);

      // when
      mockMvc
          .perform(
              get("/api/v1/norms/" + eli + "/proprietary/" + atDateString)
                  .accept(MediaType.APPLICATION_JSON_VALUE))
          // then
          .andExpect(status().isOk())
          .andExpect(jsonPath("fna").exists())
          .andExpect(jsonPath("fna.value").doesNotExist())
          .andExpect(jsonPath("art").exists())
          .andExpect(jsonPath("art.value").doesNotExist())
          .andExpect(jsonPath("typ").exists())
          .andExpect(jsonPath("typ.value").doesNotExist())
          .andExpect(jsonPath("subtyp").exists())
          .andExpect(jsonPath("subtyp.value").doesNotExist());
    }

    @Test
    void returnsProprietaryMetadata() throws Exception {
      // given
      var eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
      var dateBeforeAnyUpdate = "1970-01-01";
      var proprietary =
          Proprietary.builder()
              .node(
                  XmlMapper.toNode(
                      """
              <akn:proprietary eId="meta-1_proprietary-1"
                                           GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                           source="attributsemantik-noch-undefiniert">
                              <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.6/">
                                  <meta:art>rechtsetzungsdokument</meta:art>
                                  <meta:typ>sonstige-bekanntmachung</meta:typ>
                                  <meta:subtyp>Bekanntmachung vor einer Neufassung</meta:subtyp>
                                  <meta:fna>754-28-1</meta:fna>
                              </meta:legalDocML.de_metadaten>
                              <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                                  <meta:fna start="1990-01-01" end="1994-12-31">111-11-1</meta:fna>
                                  <meta:fna start="1995-01-01" end="2000-12-31">222-22-2</meta:fna>
                                  <meta:fna start="2001-01-01">333-33-3</meta:fna>
                                  <!-- the values and combinations of art, typ and subtyp below are invalid in real LDML.de-->
                                  <meta:art start="1990-01-01" end="1994-12-31">art update 1</meta:art>
                                  <meta:art start="1995-01-01" end="2000-12-31">art update 2</meta:art>
                                  <meta:art start="2001-01-01">art update 3</meta:art>
                                  <meta:typ start="1990-01-01" end="1994-12-31">typ update 1</meta:typ>
                                  <meta:typ start="1995-01-01" end="2000-12-31">typ update 2</meta:typ>
                                  <meta:typ start="2001-01-01">typ update 3</meta:typ>
                                  <meta:subtyp start="1990-01-01" end="1994-12-31">subtyp update 1</meta:subtyp>
                                  <meta:subtyp start="1995-01-01" end="2000-12-31">subtyp update 2</meta:subtyp>
                                  <meta:subtyp start="2001-01-01">subtyp update 3</meta:subtyp>
                              </meta:legalDocML.de_metadaten_ds>
                          </akn:proprietary>
              """))
              .build();

      when(proprietaryService.loadProprietaryFromNorm(
              new LoadProprietaryFromNormUseCase.Query(eli)))
          .thenReturn(proprietary);
      // when
      mockMvc
          .perform(
              get("/api/v1/norms/" + eli + "/proprietary/" + dateBeforeAnyUpdate)
                  .accept(MediaType.APPLICATION_JSON_VALUE))
          // then
          .andExpect(status().isOk())
          .andExpect(jsonPath("fna").exists())
          .andExpect(jsonPath("fna.value").value("754-28-1"))
          .andExpect(jsonPath("art").exists())
          .andExpect(jsonPath("art.value").value("rechtsetzungsdokument"))
          .andExpect(jsonPath("typ").exists())
          .andExpect(jsonPath("typ.value").value("sonstige-bekanntmachung"))
          .andExpect(jsonPath("subtyp").exists())
          .andExpect(jsonPath("subtyp.value").value("Bekanntmachung vor einer Neufassung"));
      ;
    }
  }
}
