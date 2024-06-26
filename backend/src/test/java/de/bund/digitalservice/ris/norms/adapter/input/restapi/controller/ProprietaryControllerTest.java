package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.config.SecurityConfig;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
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

  @MockBean private LoadProprietaryFromNormUseCase loadProprietaryFromNormUseCase;
  @MockBean private UpdateProprietaryFromNormUseCase updateProprietaryFromNormUseCase;

  @Nested
  class getProprietaryAtDate {
    @Test
    void returns404IfNormNotFound() throws Exception {
      // given
      var eli = "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1";
      var atDateString = "2024-06-03";
      when(loadProprietaryFromNormUseCase.loadProprietaryFromNorm(
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
      when(loadProprietaryFromNormUseCase.loadProprietaryFromNorm(
              new LoadProprietaryFromNormUseCase.Query(eli)))
          .thenReturn(proprietary);

      // when
      mockMvc
          .perform(
              get("/api/v1/norms/" + eli + "/proprietary/" + atDateString)
                  .accept(MediaType.APPLICATION_JSON_VALUE))
          // then
          .andExpect(status().isOk())
          .andExpect(jsonPath("fna").value("754-28-1"))
          .andExpect(jsonPath("art").value("rechtsetzungsdokument"))
          .andExpect(jsonPath("typ").value("gesetz"))
          .andExpect(jsonPath("subtyp").value("rechtsverordnung"))
          .andExpect(jsonPath("bezeichnungInVorlage").value("Bezeichnung gemäß Vorlage"))
          .andExpect(jsonPath("artDerNorm").value("SN,ÄN,ÜN"))
          .andExpect(jsonPath("normgeber").value("DEU"))
          .andExpect(jsonPath("beschliessendesOrgan").value("Bundestag"))
          .andExpect(jsonPath("qualifizierteMehrheit").value(true))
          .andExpect(jsonPath("organisationsEinheit").value("Organisationseinheit"));
    }

    @Test
    void returnsEmptyValuesIfSpecificProprietaryDataIsNotFound() throws Exception {
      // given
      var eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
      var atDateString = "2024-06-03";
      var normWithInvalidProprietary = NormFixtures.loadFromDisk("NormWithInvalidProprietary.xml");
      var proprietary = normWithInvalidProprietary.getMeta().getOrCreateProprietary();
      when(loadProprietaryFromNormUseCase.loadProprietaryFromNorm(
              new LoadProprietaryFromNormUseCase.Query(eli)))
          .thenReturn(proprietary);

      // when
      mockMvc
          .perform(
              get("/api/v1/norms/" + eli + "/proprietary/" + atDateString)
                  .accept(MediaType.APPLICATION_JSON_VALUE))
          // then
          .andExpect(status().isOk())
          .andExpect(jsonPath("fna").isEmpty())
          .andExpect(jsonPath("art").isEmpty())
          .andExpect(jsonPath("typ").isEmpty())
          .andExpect(jsonPath("subtyp").isEmpty())
          .andExpect(jsonPath("bezeichnungInVorlage").isEmpty())
          .andExpect(jsonPath("artDerNorm").isEmpty())
          .andExpect(jsonPath("normgeber").isEmpty())
          .andExpect(jsonPath("beschliessendesOrgan").isEmpty())
          .andExpect(jsonPath("qualifizierteMehrheit").isEmpty())
          .andExpect(jsonPath("organisationsEinheit").isEmpty());
    }

    @Test
    void returnsEmptyValuesIfProprietaryDoesNotExist() throws Exception {
      // given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      var atDateString = "2024-06-03";
      var normWithInvalidProprietary = NormFixtures.loadFromDisk("SimpleNorm.xml");
      var proprietary = normWithInvalidProprietary.getMeta().getOrCreateProprietary();
      when(loadProprietaryFromNormUseCase.loadProprietaryFromNorm(
              new LoadProprietaryFromNormUseCase.Query(eli)))
          .thenReturn(proprietary);

      // when
      mockMvc
          .perform(
              get("/api/v1/norms/" + eli + "/proprietary/" + atDateString)
                  .accept(MediaType.APPLICATION_JSON_VALUE))
          // then
          .andExpect(status().isOk())
          .andExpect(jsonPath("fna").isEmpty())
          .andExpect(jsonPath("art").isEmpty())
          .andExpect(jsonPath("typ").isEmpty())
          .andExpect(jsonPath("subtyp").isEmpty())
          .andExpect(jsonPath("bezeichnungInVorlage").isEmpty())
          .andExpect(jsonPath("artDerNorm").isEmpty())
          .andExpect(jsonPath("normgeber").isEmpty())
          .andExpect(jsonPath("beschliessendesOrgan").isEmpty())
          .andExpect(jsonPath("qualifizierteMehrheit").isEmpty())
          .andExpect(jsonPath("organisationsEinheit").isEmpty());
    }
  }

  @Nested
  class updateProprietaryAtDate {

    @Test
    void updatesProprietarySuccess() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final LocalDate date = LocalDate.parse("1990-01-01");

      final Proprietary proprietary =
          Proprietary.builder()
              .node(
                  XmlMapper.toNode(
                      """
                                                            <akn:proprietary eId="meta-1_proprietary-1" GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c" source="attributsemantik-noch-undefiniert">

                                                                <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                                                                    <meta:fna start="1990-01-01" end="1994-12-31">new-fna</meta:fna>
                                                                    <meta:art start="1990-01-01" end="1994-12-31">new-art</meta:art>
                                                                    <meta:typ start="1990-01-01" end="1994-12-31">new-typ</meta:typ>
                                                                    <meta:subtyp start="1990-01-01" end="1994-12-31">new-subtyp</meta:subtyp>
                                                                    <meta:bezeichnungInVorlage start="1990-01-01" end="1994-12-31">new-bezeichnungInVorlage</meta:bezeichnungInVorlage>
                                                                    <meta:artDerNorm start="1990-01-01" end="1994-12-31">SN,ÄN,ÜN</meta:artDerNorm>
                                                                    <meta:normgeber start="1990-01-01" end="1994-12-31">DEU</meta:normgeber>
                                                                    <meta:beschliessendesOrgan start="1990-01-01" end="1994-12-31" qualifizierteMehrheit="true">Bundestag</meta:beschliessendesOrgan>
                                                                    <meta:organisationsEinheit start="1990-01-01" end="1994-12-31">Andere Organisationseinheit</meta:organisationsEinheit>
                                                                </meta:legalDocML.de_metadaten_ds>
                                                            </akn:proprietary>
                                                            """))
              .build();

      when(updateProprietaryFromNormUseCase.updateProprietaryFromNorm(any()))
          .thenReturn(proprietary);

      // When // Then
      mockMvc
          .perform(
              put("/api/v1/norms/{eli}/proprietary/{date}", eli, date.toString())
                  .accept(MediaType.APPLICATION_JSON)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(
                      "{\"fna\": \"new-fna\","
                          + "\"art\": \"new-art\","
                          + "\"typ\": \"new-typ\","
                          + "\"subtyp\": \"new-subtyp\","
                          + "\"bezeichnungInVorlage\": \"new-bezeichnungInVorlage\","
                          + "\"artDerNorm\": \"SN,ÄN,ÜN\","
                          + "\"normgeber\": \"DEU\","
                          + "\"beschliessendesOrgan\": \"Bundestag\","
                          + "\"qualifizierteMehrheit\": true,"
                          + "\"organisationsEinheit\": \"Andere Organisationseinheit\"}"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("fna").value("new-fna"))
          .andExpect(jsonPath("art").value("new-art"))
          .andExpect(jsonPath("typ").value("new-typ"))
          .andExpect(jsonPath("subtyp").value("new-subtyp"))
          .andExpect(jsonPath("bezeichnungInVorlage").value("new-bezeichnungInVorlage"))
          .andExpect(jsonPath("artDerNorm").value("SN,ÄN,ÜN"))
          .andExpect(jsonPath("normgeber").value("DEU"))
          .andExpect(jsonPath("beschliessendesOrgan").value("Bundestag"))
          .andExpect(jsonPath("qualifizierteMehrheit").value(true))
          .andExpect(jsonPath("organisationsEinheit").value("Andere Organisationseinheit"));

      verify(updateProprietaryFromNormUseCase, times(1))
          .updateProprietaryFromNorm(
              argThat(
                  query ->
                      query
                              .eli()
                              .equals("eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1")
                          && query.atDate().isEqual(date)
                          && query.metadata().fna().equals("new-fna")
                          && query.metadata().art().equals("new-art")
                          && query.metadata().typ().equals("new-typ")
                          && query.metadata().subtyp().equals("new-subtyp")
                          && query
                              .metadata()
                              .bezeichnungInVorlage()
                              .equals("new-bezeichnungInVorlage")
                          && query.metadata().artDerNorm().equals("SN,ÄN,ÜN")
                          && query.metadata().normgeber().equals("DEU")
                          && query.metadata().beschliessendesOrgan().equals("Bundestag")
                          && query.metadata().qualifizierterMehrheit().equals(true)
                          && query
                              .metadata()
                              .organisationsEinheit()
                              .equals("Andere Organisationseinheit")));
    }

    @Test
    void itReturnsNotFoundIfNormIsNotFound() throws Exception {
      // given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";

      when(updateProprietaryFromNormUseCase.updateProprietaryFromNorm(any()))
          .thenThrow(new NormNotFoundException("Norm not found"));

      // When // Then
      mockMvc
          .perform(
              put("/api/v1/norms/{eli}/proprietary/{date}", eli, "1990-01-01")
                  .accept(MediaType.APPLICATION_JSON)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(
                      "{\"fna\": \"new-fna\","
                          + "\"art\": \"new-art\","
                          + "\"typ\": \"new-typ\","
                          + "\"subtyp\": \"new-subtyp\","
                          + "\"bezeichnungInVorlage\": \"new-bezeichnungInVorlage\","
                          + "\"artDerNorm\": \"SN,ÄN,ÜN\","
                          + "\"normgeber\": \"DEU\","
                          + "\"beschliessendesOrgan\": \"Bundestag\","
                          + "\"qualifizierteMehrheit\": true,"
                          + "\"organisationsEinheit\": \"Andere Organisationseinheit\"}"))
          .andExpect(status().isNotFound());
    }
  }
}
