package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ExpressionEli;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class ProprietaryControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private NormRepository normRepository;

  @AfterEach
  void cleanUp() {
    normRepository.deleteAll();
  }

  @Nested
  class getProprietaryAtDate {

    @Test
    void return404IfNormNotFound() throws Exception {
      // given no norm
      var eli = ExpressionEli.fromString(
        "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      var atDateString = "2024-06-03";
      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary/" + atDateString)
            .accept(MediaType.APPLICATION_JSON_VALUE)
        )
        // then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Norm with eli eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1 does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/proprietary/2024-06-03"
            )
        )
        .andExpect(
          jsonPath("eli")
            .value("eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1")
        );
    }

    @Test
    void returnEmptyValuesIfNormHasNoProprietaryAtAll() throws Exception {
      // given
      var eli = ExpressionEli.fromString(
        "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1"
      );
      var atDateString = "2024-06-03";
      var norm = NormFixtures.loadFromDisk("NormWithoutProprietary.xml");
      normRepository.save(NormMapper.mapToDto(norm));

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary/" + atDateString)
            .accept(MediaType.APPLICATION_JSON_VALUE)
        )
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("fna").isEmpty())
        .andExpect(jsonPath("art").isEmpty())
        .andExpect(jsonPath("typ").isEmpty())
        .andExpect(jsonPath("subtyp").isEmpty())
        .andExpect(jsonPath("bezeichnungInVorlage").isEmpty())
        .andExpect(jsonPath("artDerNorm").isEmpty())
        .andExpect(jsonPath("staat").isEmpty())
        .andExpect(jsonPath("beschliessendesOrgan").isEmpty())
        .andExpect(jsonPath("qualifizierteMehrheit").isEmpty())
        .andExpect(jsonPath("organisationsEinheit").isEmpty());
    }

    @Test
    void returnEmptyValuesIfInvalidProprietaryDoesNotContainThem() throws Exception {
      // given
      var eli = ExpressionEli.fromString(
        "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1"
      );
      var atDateString = "2024-06-03";
      var norm = NormFixtures.loadFromDisk("NormWithInvalidProprietary.xml");
      normRepository.save(NormMapper.mapToDto(norm));

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary/" + atDateString)
            .accept(MediaType.APPLICATION_JSON_VALUE)
        )
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("fna").isEmpty())
        .andExpect(jsonPath("art").isEmpty())
        .andExpect(jsonPath("typ").isEmpty())
        .andExpect(jsonPath("subtyp").isEmpty())
        .andExpect(jsonPath("bezeichnungInVorlage").isEmpty())
        .andExpect(jsonPath("artDerNorm").isEmpty())
        .andExpect(jsonPath("staat").isEmpty())
        .andExpect(jsonPath("beschliessendesOrgan").isEmpty())
        .andExpect(jsonPath("qualifizierteMehrheit").isEmpty())
        .andExpect(jsonPath("organisationsEinheit").isEmpty());
    }

    @Test
    void returnProprietary() throws Exception {
      // given
      var eli = ExpressionEli.fromString(
        "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1"
      );
      var atDateString = "2024-06-03";
      var norm = NormFixtures.loadFromDisk("NormWithProprietary.xml");
      normRepository.save(NormMapper.mapToDto(norm));

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary/" + atDateString)
            .accept(MediaType.APPLICATION_JSON_VALUE)
        )
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("fna").value("754-28-1"))
        .andExpect(jsonPath("art").value("rechtsetzungsdokument"))
        .andExpect(jsonPath("typ").value("gesetz"))
        .andExpect(jsonPath("subtyp").value("rechtsverordnung"))
        .andExpect(jsonPath("bezeichnungInVorlage").value("Bezeichnung gemäß Vorlage"))
        .andExpect(jsonPath("artDerNorm").value("SN,ÄN,ÜN"))
        .andExpect(jsonPath("staat").value("DEU"))
        .andExpect(jsonPath("beschliessendesOrgan").value("Bundestag"))
        .andExpect(jsonPath("qualifizierteMehrheit").value(true))
        .andExpect(jsonPath("organisationsEinheit").value("Organisationseinheit"));
    }
  }

  @Nested
  class updateProprietary {

    @Test
    void return404IfNormNotFound() throws Exception {
      // given
      final String eli = "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1";
      // when
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/proprietary/{date}", eli, "1990-01-01")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              "{\"fna\": \"new-fna\"," +
              "\"art\": \"new-art\"," +
              "\"typ\": \"new-typ\"," +
              "\"subtyp\": \"new-subtyp\"," +
              "\"bezeichnungInVorlage\": \"new-bezeichnungInVorlage\"," +
              "\"artDerNorm\": \"SN,ÄN,ÜN\"," +
              "\"staat\": \"DEU\"," +
              "\"beschliessendesOrgan\": \"Bundestag\"," +
              "\"qualifizierteMehrheit\": true," +
              "\"organisationsEinheit\": \"Andere Organisationseinheit\"}"
            )
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Norm with eli eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1 does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/proprietary/1990-01-01"
            )
        )
        .andExpect(
          jsonPath("eli")
            .value("eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1")
        );
    }

    @Test
    void updatesAll() throws Exception {
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
              "{\"fna\": \"new-fna\"," +
              "\"art\": \"new-art\"," +
              "\"typ\": \"new-typ\"," +
              "\"subtyp\": \"new-subtyp\"," +
              "\"bezeichnungInVorlage\": \"new-bezeichnungInVorlage\"," +
              "\"artDerNorm\": \"ÄN,ÜN\"," +
              "\"staat\": \"DDR\"," +
              "\"beschliessendesOrgan\": \"LT\"," +
              "\"qualifizierteMehrheit\": false," +
              "\"organisationsEinheit\": \"Andere Organisationseinheit\"}"
            )
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("fna").value("new-fna"))
        .andExpect(jsonPath("art").value("new-art"))
        .andExpect(jsonPath("typ").value("new-typ"))
        .andExpect(jsonPath("subtyp").value("new-subtyp"))
        .andExpect(jsonPath("bezeichnungInVorlage").value("new-bezeichnungInVorlage"))
        .andExpect(jsonPath("artDerNorm").value("ÄN,ÜN"))
        .andExpect(jsonPath("staat").value("DDR"))
        .andExpect(jsonPath("beschliessendesOrgan").value("LT"))
        .andExpect(jsonPath("qualifizierteMehrheit").value(false))
        .andExpect(jsonPath("organisationsEinheit").value("Andere Organisationseinheit"));

      final Norm normLoaded = NormMapper.mapToDomain(
        normRepository.findFirstByEliExpressionOrderByEliManifestation(eli).get()
      );

      assertThat(normLoaded.getMeta().getOrCreateProprietary().getFna(date)).contains("new-fna");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getArt(date)).contains("new-art");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getTyp(date)).contains("new-typ");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getSubtyp(date))
        .contains("new-subtyp");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getBezeichnungInVorlage(date))
        .contains("new-bezeichnungInVorlage");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getArtDerNorm(date))
        .contains("ÄN,ÜN");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getStaat(date)).contains("DDR");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getBeschliessendesOrgan(date))
        .contains("LT");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getQualifizierteMehrheit(date))
        .contains(false);
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getOrganisationsEinheit(date))
        .contains("Andere Organisationseinheit");
    }

    @Test
    void doesResetAllFieldsBySendingNullAndGetSomeDefaults() throws Exception {
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
              "{\"fna\": null," +
              "\"art\": null," +
              "\"typ\": null," +
              "\"subtyp\": null," +
              "\"bezeichnungInVorlage\": null," +
              "\"artDerNorm\": null," +
              "\"staat\": null," +
              "\"beschliessendesOrgan\": null," +
              "\"qualifizierteMehrheit\": null," +
              "\"organisationsEinheit\": null}"
            )
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("fna").value("754-28-1"))
        .andExpect(jsonPath("art").value("rechtsetzungsdokument"))
        .andExpect(jsonPath("typ").value("gesetz"))
        .andExpect(jsonPath("subtyp").isEmpty())
        .andExpect(jsonPath("bezeichnungInVorlage").isEmpty())
        .andExpect(jsonPath("artDerNorm").isEmpty())
        .andExpect(jsonPath("staat").isEmpty())
        .andExpect(jsonPath("beschliessendesOrgan").isEmpty())
        .andExpect(jsonPath("qualifizierteMehrheit").isEmpty())
        .andExpect(jsonPath("organisationsEinheit").isEmpty());

      final Norm normLoaded = NormMapper.mapToDomain(
        normRepository.findFirstByEliExpressionOrderByEliManifestation(eli).get()
      );

      assertThat(normLoaded.getMeta().getOrCreateProprietary().getFna(date)).contains("754-28-1");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getArt(date))
        .contains("rechtsetzungsdokument");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getTyp(date)).contains("gesetz");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getSubtyp(date)).isEmpty();
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getBezeichnungInVorlage(date))
        .isEmpty();
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getArtDerNorm(date)).isEmpty();
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getStaat(date)).isEmpty();
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getBeschliessendesOrgan(date))
        .isEmpty();
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getQualifizierteMehrheit(date))
        .isEmpty();
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getOrganisationsEinheit(date))
        .isEmpty();
    }

    @Test
    void doesResetAllFieldsBySendingEmptyStringAndGetSomeDefaults() throws Exception {
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
              "{\"fna\": \"\"," +
              "\"art\": \"\"," +
              "\"typ\": \"\"," +
              "\"subtyp\": \"\"," +
              "\"bezeichnungInVorlage\": \"\"," +
              "\"artDerNorm\": \"\"," +
              "\"staat\": \"\"," +
              "\"beschliessendesOrgan\": \"\"," +
              "\"qualifizierteMehrheit\": false," +
              "\"organisationsEinheit\": \"\"}"
            )
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("fna").value("754-28-1"))
        .andExpect(jsonPath("art").value("rechtsetzungsdokument"))
        .andExpect(jsonPath("typ").value("gesetz"))
        .andExpect(jsonPath("subtyp").isEmpty())
        .andExpect(jsonPath("bezeichnungInVorlage").isEmpty())
        .andExpect(jsonPath("artDerNorm").isEmpty())
        .andExpect(jsonPath("staat").isEmpty())
        .andExpect(jsonPath("beschliessendesOrgan").isEmpty())
        .andExpect(jsonPath("qualifizierteMehrheit").isEmpty())
        .andExpect(jsonPath("organisationsEinheit").isEmpty());

      final Norm normLoaded = NormMapper.mapToDomain(
        normRepository.findFirstByEliExpressionOrderByEliManifestation(eli).get()
      );

      assertThat(normLoaded.getMeta().getOrCreateProprietary().getFna(date)).contains("754-28-1");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getArt(date))
        .contains("rechtsetzungsdokument");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getTyp(date)).contains("gesetz");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getSubtyp(date)).isEmpty();
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getBezeichnungInVorlage(date))
        .isEmpty();
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getArtDerNorm(date)).isEmpty();
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getStaat(date)).isEmpty();
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getBeschliessendesOrgan(date))
        .isEmpty();
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getQualifizierteMehrheit(date))
        .isEmpty();
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getOrganisationsEinheit(date))
        .isEmpty();
    }

    @Test
    void doesRemoveQualifizierteMehrheitFromBeschliessendesOrganWhenNull() throws Exception {
      // given
      final String eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
      final LocalDate date = LocalDate.parse("2003-01-01");
      final Norm norm = NormFixtures.loadFromDisk(
        "NormWithProprietaryAndMultipleTimeBoundaries.xml"
      );
      normRepository.save(NormMapper.mapToDto(norm));

      // when

      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/proprietary/{date}", eli, date.toString())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              "{\"fna\": \"new-fna\"," +
              "\"art\": \"new-art\"," + // no change
              // no change
              "\"typ\": \"new-typ\"," + // no change
              "\"subtyp\": \"new-subtyp\"," + // no change
              "\"bezeichnungInVorlage\": \"new-bezeichnungInVorlage\"," + // no change
              "\"artDerNorm\": \"ÄN,ÜN\"," + // no change
              "\"staat\": \"DDR\"," + // no change
              "\"beschliessendesOrgan\": \"\"," + // this will remove the...
              "\"qualifizierteMehrheit\": null," + // ...qualifizierteMehrheit attr
              "\"organisationsEinheit\": \"Andere Organisationseinheit\"}"
            )
        ) // no
        // change
        .andExpect(status().isOk())
        .andExpect(jsonPath("fna").value("new-fna"))
        .andExpect(jsonPath("art").value("new-art"))
        .andExpect(jsonPath("typ").value("new-typ"))
        .andExpect(jsonPath("subtyp").value("new-subtyp"))
        .andExpect(jsonPath("bezeichnungInVorlage").value("new-bezeichnungInVorlage"))
        .andExpect(jsonPath("artDerNorm").value("ÄN,ÜN"))
        .andExpect(jsonPath("staat").value("DDR"))
        .andExpect(jsonPath("beschliessendesOrgan").isEmpty())
        .andExpect(jsonPath("qualifizierteMehrheit").isEmpty()) // meaning json "qualifizierteMehrheit":null
        .andExpect(jsonPath("organisationsEinheit").value("Andere Organisationseinheit"));

      final Norm normLoaded = NormMapper.mapToDomain(
        normRepository.findFirstByEliExpressionOrderByEliManifestation(eli).get()
      );

      assertThat(normLoaded.getMeta().getOrCreateProprietary().getFna(date)).contains("new-fna");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getArt(date)).contains("new-art");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getTyp(date)).contains("new-typ");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getSubtyp(date))
        .contains("new-subtyp");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getBezeichnungInVorlage(date))
        .contains("new-bezeichnungInVorlage");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getArtDerNorm(date))
        .contains("ÄN,ÜN");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getStaat(date)).contains("DDR");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getBeschliessendesOrgan(date))
        .isEmpty();
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getQualifizierteMehrheit(date))
        .isEmpty();
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getOrganisationsEinheit(date))
        .contains("Andere Organisationseinheit");
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
              "{\"fna\": \"new-fna\"," +
              "\"art\": \"new-art\"," +
              "\"typ\": \"new-typ\"," +
              "\"subtyp\": \"new-subtyp\"," +
              "\"bezeichnungInVorlage\": \"new-bezeichnungInVorlage\"," +
              "\"artDerNorm\": \"SN,ÄN,ÜN\"," +
              "\"staat\": \"DEU\"," +
              "\"beschliessendesOrgan\": \"Bundestag\"," +
              "\"qualifizierteMehrheit\": true," +
              "\"organisationsEinheit\": \"Organisationseinheit\"}"
            )
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("fna").value("new-fna"))
        .andExpect(jsonPath("art").value("new-art"))
        .andExpect(jsonPath("typ").value("new-typ"))
        .andExpect(jsonPath("subtyp").value("new-subtyp"))
        .andExpect(jsonPath("bezeichnungInVorlage").value("new-bezeichnungInVorlage"))
        .andExpect(jsonPath("artDerNorm").value("SN,ÄN,ÜN"))
        .andExpect(jsonPath("staat").value("DEU"))
        .andExpect(jsonPath("beschliessendesOrgan").value("Bundestag"))
        .andExpect(jsonPath("qualifizierteMehrheit").value(true))
        .andExpect(jsonPath("organisationsEinheit").value("Organisationseinheit"));

      final Norm normLoaded = NormMapper.mapToDomain(
        normRepository.findFirstByEliExpressionOrderByEliManifestation(eli).get()
      );

      assertThat(normLoaded.getMeta().getOrCreateProprietary().getFna(date)).contains("new-fna");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getArt(date)).contains("new-art");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getTyp(date)).contains("new-typ");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getSubtyp(date))
        .contains("new-subtyp");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getBezeichnungInVorlage(date))
        .contains("new-bezeichnungInVorlage");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getArtDerNorm(date))
        .contains("SN,ÄN,ÜN");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getStaat(date)).contains("DEU");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getBeschliessendesOrgan(date))
        .contains("Bundestag");
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getQualifizierteMehrheit(date))
        .contains(true);
      assertThat(normLoaded.getMeta().getOrCreateProprietary().getOrganisationsEinheit(date))
        .contains("Organisationseinheit");
    }
  }

  @Nested
  class getProprietaryEinzelelementAtDate {

    @Test
    void return404IfNormNotFound() throws Exception {
      // given no norm
      var eli = ExpressionEli.fromString(
        "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      var eid = "hauptteil-1_abschnitt-0_para-1";
      var atDateString = "2024-06-03";
      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary/" + eid + "/" + atDateString)
            .accept(MediaType.APPLICATION_JSON_VALUE)
        )
        // then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Norm with eli eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1 does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/proprietary/hauptteil-1_abschnitt-0_para-1/2024-06-03"
            )
        )
        .andExpect(
          jsonPath("eli")
            .value("eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1")
        );
    }

    @Test
    void returnEmptyValuesIfNormHasNoProprietaryAtAll() throws Exception {
      // given
      var eli = ExpressionEli.fromString(
        "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1"
      );
      var eid = "hauptteil-1_abschnitt-0_para-1";
      var atDateString = "2024-06-03";
      var norm = NormFixtures.loadFromDisk("NormWithoutProprietary.xml");
      normRepository.save(NormMapper.mapToDto(norm));

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary/" + eid + "/" + atDateString)
            .accept(MediaType.APPLICATION_JSON_VALUE)
        )
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("artDerNorm").isEmpty());
    }

    @Test
    void returnEmptyValuesIfInvalidProprietaryDoesNotContainThem() throws Exception {
      // given
      var eli = ExpressionEli.fromString(
        "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1"
      );
      var eid = "hauptteil-1_abschnitt-0_para-1";
      var atDateString = "2024-06-03";
      var norm = NormFixtures.loadFromDisk("NormWithInvalidProprietary.xml");
      normRepository.save(NormMapper.mapToDto(norm));

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary/" + eid + "/" + atDateString)
            .accept(MediaType.APPLICATION_JSON_VALUE)
        )
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("artDerNorm").isEmpty());
    }

    @Test
    void returnProprietaryEinzelelement() throws Exception {
      // given
      var eli = ExpressionEli.fromString(
        "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1"
      );
      var eid = "hauptteil-1_abschnitt-0_para-1";
      var atDateString = "2024-06-03";
      var norm = NormFixtures.loadFromDisk("NormWithProprietary.xml");
      normRepository.save(NormMapper.mapToDto(norm));

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary/" + eid + "/" + atDateString)
            .accept(MediaType.APPLICATION_JSON_VALUE)
        )
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("artDerNorm").value("SN"));
    }
  }

  @Nested
  class updateProprietaryEinzelelementAtDate {

    @Test
    void return404IfNormNotFound() throws Exception {
      // given no norm
      var eli = ExpressionEli.fromString(
        "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      var eid = "hauptteil-1_abschnitt-0_para-1";
      var atDateString = "2024-06-03";
      // when
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/proprietary/{eid}/{atDateString}", eli, eid, atDateString)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"artDerNorm\": \"SN\"}")
        )
        // then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Norm with eli eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1 does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/proprietary/hauptteil-1_abschnitt-0_para-1/2024-06-03"
            )
        )
        .andExpect(
          jsonPath("eli")
            .value("eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1")
        );
    }

    @Test
    void createsProprietaryAndMetadatenDsAndEinzelelementAndSetsValue() throws Exception {
      // given
      var eli = ExpressionEli.fromString(
        "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1"
      );
      var eid = "hauptteil-1_abschnitt-0_para-1";
      var atDateString = "2024-06-03";
      var norm = NormFixtures.loadFromDisk("NormWithoutProprietary.xml");
      normRepository.save(NormMapper.mapToDto(norm));

      // when
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/proprietary/{eid}/{atDateString}", eli, eid, atDateString)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"artDerNorm\": \"SN\"}")
        )
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("artDerNorm").value("SN"));
    }
  }
}
