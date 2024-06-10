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
  class getProprietary {

    @Test
    void return404IfNormNotFound() throws Exception {
      // given no norm
      // when
      mockMvc
          .perform(
              get("/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/proprietary")
                  .accept(MediaType.APPLICATION_JSON_VALUE))
          // then
          .andExpect(status().isNotFound());
    }

    @Test
    void returnEmptyValuesIfNormHasNoProprietary() throws Exception {
      // given
      var eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
      var norm = NormFixtures.loadFromDisk("NormWithoutProprietary.xml");
      normRepository.save(NormMapper.mapToDto(norm));

      // when
      mockMvc
          .perform(
              get("/api/v1/norms/" + eli + "/proprietary").accept(MediaType.APPLICATION_JSON_VALUE))
          // then
          .andExpect(status().isOk())
          .andExpect(jsonPath("fna").isEmpty());
    }

    @Test
    void returnEmptyValuesForInvalidProprietary() throws Exception {
      // given
      var eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
      var norm = NormFixtures.loadFromDisk("NormWithInvalidProprietary.xml");
      normRepository.save(NormMapper.mapToDto(norm));

      // when
      mockMvc
          .perform(
              get("/api/v1/norms/" + eli + "/proprietary").accept(MediaType.APPLICATION_JSON_VALUE))
          // then
          .andExpect(status().isOk())
          .andExpect(jsonPath("fna").isEmpty());
    }

    @Test
    void returnProprietary() throws Exception {
      // given
      var eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
      var norm = NormFixtures.loadFromDisk("NormWithProprietary.xml");
      normRepository.save(NormMapper.mapToDto(norm));

      // when
      mockMvc
          .perform(
              get("/api/v1/norms/" + eli + "/proprietary").accept(MediaType.APPLICATION_JSON_VALUE))
          // then
          .andExpect(status().isOk())
          .andExpect(jsonPath("fna").value("754-28-1"));
    }
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
          .andExpect(jsonPath("subtyp").isEmpty());
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
          .andExpect(jsonPath("subtyp").isEmpty());
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
          .andExpect(jsonPath("fna").value("754-28-1"));
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
                  .content("{\"fna\": \"new-fna\",\"art\": null,\"typ\": null,\"subtyp\": null}"))
          .andExpect(status().isNotFound());
    }

    @Test
    void updatesFna() throws Exception {
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
                  .content("{\"fna\": \"new-fna\",\"art\": null,\"typ\": null,\"subtyp\": null}"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("fna").value("new-fna"));

      final Norm normLoaded = NormMapper.mapToDomain(normRepository.findByEli(eli).get());

      assertThat(normLoaded.getMeta().getOrCreateProprietary().getFna(date)).contains("new-fna");
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
                  .content("{\"fna\": \"new-fna\",\"art\": null,\"typ\": null,\"subtyp\": null}"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("fna").value("new-fna"));

      final Norm normLoaded = NormMapper.mapToDomain(normRepository.findByEli(eli).get());

      assertThat(normLoaded.getMeta().getOrCreateProprietary().getFna(date)).contains("new-fna");
    }
  }
}
