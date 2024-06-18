package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class ProprietaryControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private NormRepository normRepository;

  @AfterEach
  void cleanUp() {
    normRepository.deleteAll();
  }

  @Nested
  class getProprietaryAtDate {
    @Test
    void return404IfNormNotFound() throws Exception {
      // given no norm
      var eli = "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1";
      var atDateString = "2024-06-03";
      // when
      mockMvc
          .perform(
              get("/api/v1/norms/" + eli + "/proprietary/" + atDateString)
                  .accept(MediaType.APPLICATION_JSON_VALUE))
          // then
          .andExpect(status().isNotFound());
    }

    @Test
    void returnEmptyValuesIfNormHasNoProprietaryAtAll() throws Exception {
      // given
      var eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
      var atDateString = "2024-06-03";
      var norm = NormFixtures.loadFromDisk("NormWithoutProprietary.xml");
      normRepository.save(NormMapper.mapToDto(norm));

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
          .andExpect(jsonPath("qualifizierteMehrheit").isEmpty());
    }

    @Test
    void returnEmptyValuesIfInvalidProprietaryDoesNotContainThem() throws Exception {
      // given
      var eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
      var atDateString = "2024-06-03";
      var norm = NormFixtures.loadFromDisk("NormWithInvalidProprietary.xml");
      normRepository.save(NormMapper.mapToDto(norm));

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
          .andExpect(jsonPath("qualifizierteMehrheit").isEmpty());
    }

    @Test
    void returnProprietary() throws Exception {
      // given
      var eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
      var atDateString = "2024-06-03";
      var norm = NormFixtures.loadFromDisk("NormWithProprietary.xml");
      normRepository.save(NormMapper.mapToDto(norm));

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
          .andExpect(jsonPath("qualifizierteMehrheit").value(true));
    }
  }

  @Nested
  class updateProprietary {
    @Test
    void return404IfNormNotFound() throws Exception {
      // given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      // when
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
                          + "\"qualifizierteMehrheit\": true}"))
          .andExpect(status().isNotFound());
    }

    @Test
    void updatesAll() throws Exception {
      // One may wonder why this test does all at once: Integration tests are expensive.
      // given
      final String eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
      final LocalDate date = LocalDate.parse("1990-01-01");
      final Norm norm = NormFixtures.loadFromDisk("NormWithProprietary.xml");
      normRepository.save(NormMapper.mapToDto(norm));

      // when
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
                          + "\"artDerNorm\": \"ÄN,ÜN\","
                          + "\"normgeber\": \"DDR\","
                          + "\"beschliessendesOrgan\": \"LT\","
                          + "\"qualifizierteMehrheit\": false}"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("fna").value("new-fna"))
          .andExpect(jsonPath("art").value("new-art"))
          .andExpect(jsonPath("typ").value("new-typ"))
          .andExpect(jsonPath("subtyp").value("new-subtyp"))
          .andExpect(jsonPath("bezeichnungInVorlage").value("new-bezeichnungInVorlage"))
          .andExpect(jsonPath("artDerNorm").value("ÄN,ÜN"))
          .andExpect(jsonPath("normgeber").value("DDR"))
          .andExpect(jsonPath("beschliessendesOrgan").value("LT"))
          .andExpect(jsonPath("qualifizierteMehrheit").value(false));

      final Norm normLoaded = NormMapper.mapToDomain(normRepository.findByEli(eli).get());

      assertThat(normLoaded.getMeta().getOrCreateProprietary().getFna(date)).contains("new-fna");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getArt(date)).contains("new-art");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getTyp(date)).contains("new-typ");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getSubtyp(date))
          .contains("new-subtyp");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getBezeichnungInVorlage(date))
          .contains("new-bezeichnungInVorlage");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getArtDerNorm(date))
          .contains("ÄN,ÜN");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getNormgeber(date)).contains("DDR");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getBeschliessendesOrgan(date))
          .contains("LT");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getQualifizierteMehrheit(date))
          .contains(false);
    }

    @Test
    void doesNotUpdateAllFieldsNull() throws Exception {
      // given
      final String eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
      final LocalDate date = LocalDate.parse("1990-01-01");
      final Norm norm = NormFixtures.loadFromDisk("NormWithProprietary.xml");
      normRepository.save(NormMapper.mapToDto(norm));

      // when

      mockMvc
          .perform(
              put("/api/v1/norms/{eli}/proprietary/{date}", eli, date.toString())
                  .accept(MediaType.APPLICATION_JSON)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(
                      "{\"fna\": null,"
                          + "\"art\": null,"
                          + "\"typ\": null,"
                          + "\"subtyp\": null,"
                          + "\"bezeichnungInVorlage\": null,"
                          + "\"artDerNorm\": null,"
                          + "\"normgeber\": null,"
                          + "\"beschliessendesOrgan\": null}"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("fna").isEmpty())
          .andExpect(jsonPath("art").isEmpty())
          .andExpect(jsonPath("typ").isEmpty())
          .andExpect(jsonPath("subtyp").isEmpty())
          .andExpect(jsonPath("bezeichnungInVorlage").isEmpty())
          .andExpect(jsonPath("artDerNorm").isEmpty())
          .andExpect(jsonPath("normgeber").isEmpty())
          .andExpect(jsonPath("beschliessendesOrgan").isEmpty())
          .andExpect(jsonPath("qualifizierteMehrheit").doesNotExist());

      final Norm normLoaded = NormMapper.mapToDomain(normRepository.findByEli(eli).get());

      assertThat(normLoaded.getMeta().getOrCreateProprietary().getFna(date)).contains("");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getArt(date)).contains("");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getTyp(date)).contains("");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getSubtyp(date)).contains("");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getBezeichnungInVorlage(date))
          .contains("");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getArtDerNorm(date)).contains("");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getNormgeber(date)).contains("");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getBeschliessendesOrgan(date))
          .contains("");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getQualifizierteMehrheit(date))
          .isEmpty();
    }

    @Test
    void createsProprietaryAndMetadatenDsAndUpdatesFna() throws Exception {
      // given
      final String eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
      final LocalDate date = LocalDate.parse("1990-01-01");
      final Norm norm = NormFixtures.loadFromDisk("NormWithoutProprietary.xml");
      normRepository.save(NormMapper.mapToDto(norm));

      // when
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
                          + "\"qualifizierteMehrheit\": true}"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("fna").value("new-fna"))
          .andExpect(jsonPath("art").value("new-art"))
          .andExpect(jsonPath("typ").value("new-typ"))
          .andExpect(jsonPath("subtyp").value("new-subtyp"))
          .andExpect(jsonPath("bezeichnungInVorlage").value("new-bezeichnungInVorlage"))
          .andExpect(jsonPath("artDerNorm").value("SN,ÄN,ÜN"))
          .andExpect(jsonPath("normgeber").value("DEU"))
          .andExpect(jsonPath("beschliessendesOrgan").value("Bundestag"))
          .andExpect(jsonPath("qualifizierteMehrheit").value(true));

      final Norm normLoaded = NormMapper.mapToDomain(normRepository.findByEli(eli).get());

      assertThat(normLoaded.getMeta().getOrCreateProprietary().getFna(date)).contains("new-fna");
    }
  }
}
