package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Metadata;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser
public class ProprietaryControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DokumentRepository dokumentRepository;

  @AfterEach
  void cleanUp() {
    dokumentRepository.deleteAll();
  }

  @Nested
  class getProprietary {

    @Test
    void return404IfNormNotFound() throws Exception {
      // given no norm
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary").accept(MediaType.APPLICATION_JSON_VALUE)
        )
        // then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/dokument-not-found"))
        .andExpect(jsonPath("title").value("Dokument not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Document with eli eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1 does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/proprietary"
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
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1"
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithoutProprietary.xml"))
      );

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary").accept(MediaType.APPLICATION_JSON_VALUE)
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
        .andExpect(jsonPath("organisationsEinheit").isEmpty())
        .andExpect(jsonPath("ressort").isEmpty());
    }

    @Test
    void returnEmptyValuesIfInvalidProprietaryDoesNotContainThem() throws Exception {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1"
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithInvalidProprietary.xml")
        )
      );

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary").accept(MediaType.APPLICATION_JSON_VALUE)
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
        .andExpect(jsonPath("organisationsEinheit").isEmpty())
        .andExpect(jsonPath("ressort").isEmpty());
    }

    @Test
    void returnProprietary() throws Exception {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1"
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithProprietary.xml"))
      );

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary").accept(MediaType.APPLICATION_JSON_VALUE)
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
        .andExpect(jsonPath("ressort").value("Bundesministerium des Innern und für Heimat"))
        .andExpect(jsonPath("organisationsEinheit").value("Organisationseinheit"))
        .andExpect(jsonPath("ressort").value("Bundesministerium des Innern und für Heimat"));
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
          put("/api/v1/norms/{eli}/proprietary", eli)
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
              "\"ressort\": \"Bundesministerium der Justiz\"," +
              "\"organisationsEinheit\": \"Andere Organisationseinheit\"," +
              "\"ressort\": \"new ressort\"}"
            )
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/dokument-not-found"))
        .andExpect(jsonPath("title").value("Dokument not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Document with eli eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1 does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/proprietary"
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

      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithProprietary.xml"))
      );

      // when
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/proprietary", eli)
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
              "\"ressort\": \"Bundesministerium der Magie\"," +
              "\"organisationsEinheit\": \"Andere Organisationseinheit\"," +
              "\"ressort\": \"new ressort\"}"
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
        .andExpect(jsonPath("organisationsEinheit").value("Andere Organisationseinheit"))
        .andExpect(jsonPath("ressort").value("new ressort"));

      final Regelungstext regelungstextLoaded = (Regelungstext) DokumentMapper.mapToDomain(
        dokumentRepository
          .findFirstByEliDokumentExpressionOrderByEliDokumentManifestationDesc(eli)
          .get()
      );

      final Proprietary proprietary = regelungstextLoaded.getMeta().getOrCreateProprietary();
      assertThat(proprietary.getMetadataValue(Metadata.FNA)).contains("new-fna");
      assertThat(proprietary.getMetadataValue(Metadata.ART)).contains("new-art");
      assertThat(proprietary.getMetadataValue(Metadata.TYP)).contains("new-typ");
      assertThat(proprietary.getMetadataValue(Metadata.SUBTYP)).contains("new-subtyp");
      assertThat(proprietary.getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE))
        .contains("new-bezeichnungInVorlage");
      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM)).contains("ÄN,ÜN");
      assertThat(proprietary.getMetadataValue(Metadata.STAAT)).contains("DDR");
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN)).contains("LT");
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR))
        .contains("false");
      assertThat(proprietary.getRessort(LocalDate.parse("2019-11-22"))).contains("new ressort");
      assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT))
        .contains("Andere Organisationseinheit");
    }

    @Test
    void doesResetAllFieldsBySendingNull() throws Exception {
      // given
      final String eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";

      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithProprietary.xml"))
      );

      // when

      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/proprietary", eli)
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
              "\"ressort\": null," +
              "\"organisationsEinheit\": null}"
            )
        )
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
        .andExpect(jsonPath("ressort").isEmpty())
        .andExpect(jsonPath("organisationsEinheit").isEmpty());

      final Regelungstext regelungstextLoaded = (Regelungstext) DokumentMapper.mapToDomain(
        dokumentRepository
          .findFirstByEliDokumentExpressionOrderByEliDokumentManifestationDesc(eli)
          .get()
      );

      final Proprietary proprietary = regelungstextLoaded.getMeta().getOrCreateProprietary();
      assertThat(proprietary.getMetadataValue(Metadata.FNA)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.ART)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.TYP)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.SUBTYP)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.STAAT)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR)).isEmpty();
      assertThat(proprietary.getRessort(LocalDate.parse("2019-11-22"))).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)).isEmpty();
    }

    @Test
    void doesResetAllFieldsBySendingEmptyString() throws Exception {
      // given
      final String eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";

      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithProprietary.xml"))
      );

      // when

      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/proprietary", eli)
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
              "\"qualifizierteMehrheit\": null," +
              "\"ressort\": \"\"," +
              "\"organisationsEinheit\": \"\"}"
            )
        )
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
        .andExpect(jsonPath("ressort").isEmpty())
        .andExpect(jsonPath("organisationsEinheit").isEmpty());

      final Regelungstext regelungstextLoaded = (Regelungstext) DokumentMapper.mapToDomain(
        dokumentRepository
          .findFirstByEliDokumentExpressionOrderByEliDokumentManifestationDesc(eli)
          .get()
      );

      final Proprietary proprietary = regelungstextLoaded.getMeta().getOrCreateProprietary();
      assertThat(proprietary.getMetadataValue(Metadata.FNA)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.ART)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.TYP)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.SUBTYP)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.STAAT)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR)).isEmpty();
      assertThat(proprietary.getRessort(LocalDate.parse("2019-11-22"))).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)).isEmpty();
    }

    @Test
    void doesRemoveQualifizierteMehrheitFromBeschliessendesOrganWhenNull() throws Exception {
      // given
      final String eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";

      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithProprietaryAndMultipleTimeBoundaries.xml")
        )
      );

      // when

      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/proprietary", eli)
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

      final Regelungstext regelungstextLoaded = (Regelungstext) DokumentMapper.mapToDomain(
        dokumentRepository
          .findFirstByEliDokumentExpressionOrderByEliDokumentManifestationDesc(eli)
          .get()
      );

      final Proprietary proprietary = regelungstextLoaded.getMeta().getOrCreateProprietary();
      assertThat(proprietary.getMetadataValue(Metadata.FNA)).contains("new-fna");
      assertThat(proprietary.getMetadataValue(Metadata.ART)).contains("new-art");
      assertThat(proprietary.getMetadataValue(Metadata.TYP)).contains("new-typ");
      assertThat(proprietary.getMetadataValue(Metadata.SUBTYP)).contains("new-subtyp");
      assertThat(proprietary.getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE))
        .contains("new-bezeichnungInVorlage");
      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM)).contains("ÄN,ÜN");
      assertThat(proprietary.getMetadataValue(Metadata.STAAT)).contains("DDR");
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR)).isEmpty();
      assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT))
        .contains("Andere Organisationseinheit");
    }

    @Test
    void createsProprietaryAndMetadatenDsAndUpdatesFna() throws Exception {
      // given
      final String eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";

      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithoutProprietary.xml"))
      );

      // when
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/proprietary", eli)
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

      final Regelungstext regelungstextLoaded = (Regelungstext) DokumentMapper.mapToDomain(
        dokumentRepository
          .findFirstByEliDokumentExpressionOrderByEliDokumentManifestationDesc(eli)
          .get()
      );

      final Proprietary proprietary = regelungstextLoaded.getMeta().getOrCreateProprietary();
      assertThat(proprietary.getMetadataValue(Metadata.FNA)).contains("new-fna");
      assertThat(proprietary.getMetadataValue(Metadata.ART)).contains("new-art");
      assertThat(proprietary.getMetadataValue(Metadata.TYP)).contains("new-typ");
      assertThat(proprietary.getMetadataValue(Metadata.SUBTYP)).contains("new-subtyp");
      assertThat(proprietary.getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE))
        .contains("new-bezeichnungInVorlage");
      assertThat(proprietary.getMetadataValue(Metadata.ART_DER_NORM)).contains("SN,ÄN,ÜN");
      assertThat(proprietary.getMetadataValue(Metadata.STAAT)).contains("DEU");
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN))
        .contains("Bundestag");
      assertThat(proprietary.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR))
        .contains("true");
      assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT))
        .contains("Organisationseinheit");
    }
  }

  @Nested
  class getProprietarySingleElement {

    @Test
    void return404IfNormNotFound() throws Exception {
      // given no norm
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      var eid = "hauptteil-1_abschnitt-0_art-1";

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary/" + eid)
            .accept(MediaType.APPLICATION_JSON_VALUE)
        )
        // then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/dokument-not-found"))
        .andExpect(jsonPath("title").value("Dokument not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Document with eli eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1 does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/proprietary/hauptteil-1_abschnitt-0_art-1"
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
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1"
      );
      var eid = "hauptteil-1_abschnitt-0_art-1";

      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithoutProprietary.xml"))
      );

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary/" + eid)
            .accept(MediaType.APPLICATION_JSON_VALUE)
        )
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("artDerNorm").isEmpty());
    }

    @Test
    void returnEmptyValuesIfInvalidProprietaryDoesNotContainThem() throws Exception {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1"
      );
      var eid = "hauptteil-1_abschnitt-0_art-1";

      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithInvalidProprietary.xml")
        )
      );

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary/" + eid)
            .accept(MediaType.APPLICATION_JSON_VALUE)
        )
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("artDerNorm").isEmpty());
    }

    @Test
    void returnProprietarySingleElement() throws Exception {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1"
      );
      var eid = "hauptteil-1_abschnitt-0_art-1";

      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithProprietary.xml"))
      );

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary/" + eid)
            .accept(MediaType.APPLICATION_JSON_VALUE)
        )
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("artDerNorm").value("SN"));
    }
  }

  @Nested
  class updateProprietarySingleElement {

    @Test
    void return404IfNormNotFound() throws Exception {
      // given no norm
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      var eid = "hauptteil-1_abschnitt-0_art-1";

      // when
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/proprietary/{eid}", eli, eid)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"artDerNorm\": \"SN\"}")
        )
        // then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/dokument-not-found"))
        .andExpect(jsonPath("title").value("Dokument not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Document with eli eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1 does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/proprietary/hauptteil-1_abschnitt-0_art-1"
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
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1"
      );
      var eid = "hauptteil-1_abschnitt-0_art-1";

      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithoutProprietary.xml"))
      );

      // when
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/proprietary/{eid}", eli, eid)
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
