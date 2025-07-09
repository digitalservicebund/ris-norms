package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormManifestationMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.BinaryFileRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.metadata.rahmen.RahmenMetadata;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser(roles = { Roles.NORMS_USER })
class ProprietaryControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DokumentRepository dokumentRepository;

  @Autowired
  private BinaryFileRepository binaryFileRepository;

  @Autowired
  private NormManifestationRepository normManifestationRepository;

  @AfterEach
  void cleanUp() {
    dokumentRepository.deleteAll();
    binaryFileRepository.deleteAll();
    normManifestationRepository.deleteAll();
  }

  @Nested
  class getProprietary {

    @Test
    void return404IfNormNotFound() throws Exception {
      // given no norm
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
      );

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary").accept(MediaType.APPLICATION_JSON_VALUE)
        )
        // then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail").value(
            "Norm with eli eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu does not exist"
          )
        )
        .andExpect(
          jsonPath("instance").value(
            "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/proprietary"
          )
        )
        .andExpect(jsonPath("eli").value("eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu"));
    }

    @Test
    void returnEmptyValuesIfNormHasNoProprietaryAtAll() throws Exception {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/rechtsetzungsdokument-1"
      );
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        ProprietaryControllerIntegrationTest.class,
        "regelungstext-without-proprietary",
        NormPublishState.UNPUBLISHED
      );

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary").accept(MediaType.APPLICATION_JSON_VALUE)
        )
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("fna").isEmpty())
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
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
      );
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        ProprietaryControllerIntegrationTest.class,
        "vereinsgesetz-with-invalid-proprietary-metadata",
        NormPublishState.UNPUBLISHED
      );

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary").accept(MediaType.APPLICATION_JSON_VALUE)
        )
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("fna").isEmpty())
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
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
      );
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
        NormPublishState.UNPUBLISHED
      );

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary").accept(MediaType.APPLICATION_JSON_VALUE)
        )
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("fna").value("754-28-1"))
        .andExpect(jsonPath("typ").value("gesetz"))
        .andExpect(jsonPath("subtyp").value("rechtsverordnung"))
        .andExpect(jsonPath("bezeichnungInVorlage").value("Bezeichnung gemäß Vorlage"))
        .andExpect(jsonPath("artDerNorm").value("SN,ÄN,ÜN"))
        .andExpect(jsonPath("staat").value("DEU"))
        .andExpect(jsonPath("beschliessendesOrgan").value("Bundestag"))
        .andExpect(jsonPath("qualifizierteMehrheit").value(true))
        .andExpect(jsonPath("ressort").value("Bundesministerium der Justiz"))
        .andExpect(jsonPath("organisationsEinheit").value("Aktuelle Organisationseinheit"));
    }
  }

  @Nested
  class updateProprietary {

    @Test
    void return404IfNormNotFound() throws Exception {
      // given
      final String eli =
        "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1";
      // when
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/proprietary", eli)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              "{\"fna\": \"new-fna\"," +
              "\"typ\": \"verordnung\"," +
              "\"subtyp\": \"new-subtyp\"," +
              "\"bezeichnungInVorlage\": \"new-bezeichnungInVorlage\"," +
              "\"artDerNorm\": \"SN,ÄN,ÜN\"," +
              "\"staat\": \"DEU\"," +
              "\"beschliessendesOrgan\": \"Bundestag\"," +
              "\"qualifizierteMehrheit\": true," +
              "\"ressort\": \"Bundesministerium der Justiz\"," +
              "\"organisationsEinheit\": \"Andere Organisationseinheit\"}"
            )
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail").value(
            "Norm with eli eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu does not exist"
          )
        )
        .andExpect(
          jsonPath("instance").value(
            "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/proprietary"
          )
        )
        .andExpect(jsonPath("eli").value("eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu"));
    }

    @Test
    void updatesAll() throws Exception {
      // given
      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1";
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
        NormPublishState.UNPUBLISHED
      );

      // when
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/proprietary", eli)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              "{\"fna\": \"new-fna\"," +
              "\"typ\": \"verordnung\"," +
              "\"subtyp\": \"new-subtyp\"," +
              "\"bezeichnungInVorlage\": \"new-bezeichnungInVorlage\"," +
              "\"artDerNorm\": \"ÄN,ÜN\"," +
              "\"staat\": \"DDR\"," +
              "\"beschliessendesOrgan\": \"LT\"," +
              "\"qualifizierteMehrheit\": false," +
              "\"organisationsEinheit\": \"Andere Organisationseinheit\"," +
              "\"ressort\": \"new ressort\"}"
            )
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("fna").value("new-fna"))
        .andExpect(jsonPath("typ").value("verordnung"))
        .andExpect(jsonPath("subtyp").value("new-subtyp"))
        .andExpect(jsonPath("bezeichnungInVorlage").value("new-bezeichnungInVorlage"))
        .andExpect(jsonPath("artDerNorm").value("ÄN,ÜN"))
        .andExpect(jsonPath("staat").value("DDR"))
        .andExpect(jsonPath("beschliessendesOrgan").value("LT"))
        .andExpect(jsonPath("qualifizierteMehrheit").value(false))
        .andExpect(jsonPath("organisationsEinheit").value("Andere Organisationseinheit"))
        .andExpect(jsonPath("ressort").value("new ressort"));

      final Norm normLoaded = NormManifestationMapper.mapToDomain(
        normManifestationRepository
          .findByManifestationEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2999-12-31")
          .get()
      );

      final RahmenMetadata metadata = normLoaded.getRahmenMetadata();
      assertThat(metadata.getFna()).contains("new-fna");
      assertThat(metadata.getTyp()).contains("verordnung");
      assertThat(metadata.getSubtyp()).contains("new-subtyp");
      assertThat(metadata.getBezeichnungInVorlage()).contains("new-bezeichnungInVorlage");
      assertThat(metadata.getArtDerNorm()).contains("ÄN,ÜN");
      assertThat(metadata.getStaat()).contains("DDR");
      assertThat(metadata.getBeschliessendesOrgan()).contains("LT");
      assertThat(metadata.getQualifizierteMehrheit()).contains(false);
      assertThat(metadata.getRessort()).contains("new ressort");
      assertThat(metadata.getOrganisationsEinheit()).contains("Andere Organisationseinheit");

      // also check that the metadata is set correctly in the proprietary elements
      final Proprietary proprietaryRegelungstext1 = normLoaded
        .getRegelungstext1()
        .getMeta()
        .getOrCreateProprietary();
      final Proprietary proprietaryRechtsetzungsdokument = normLoaded
        .getRechtsetzungsdokument()
        .getMeta()
        .getOrCreateProprietary();
      assertThat(proprietaryRechtsetzungsdokument.getMetadataValue(Metadata.FNA)).contains(
        "new-fna"
      );
      assertThat(proprietaryRegelungstext1.getMetadataValue(Metadata.TYP)).contains("verordnung");
      assertThat(proprietaryRegelungstext1.getMetadataValue(Metadata.SUBTYP)).contains(
        "new-subtyp"
      );
      assertThat(
        proprietaryRegelungstext1.getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE)
      ).contains("new-bezeichnungInVorlage");
      assertThat(proprietaryRegelungstext1.getMetadataValue(Metadata.ART_DER_NORM)).contains(
        "ÄN,ÜN"
      );
      assertThat(proprietaryRegelungstext1.getMetadataValue(Metadata.STAAT)).contains("DDR");
      assertThat(
        proprietaryRegelungstext1.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN)
      ).contains("LT");
      assertThat(
        proprietaryRegelungstext1.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR)
      ).contains("false");
      assertThat(
        proprietaryRechtsetzungsdokument.getRessort(LocalDate.parse("2019-11-22"))
      ).contains("new ressort");
      assertThat(
        proprietaryRegelungstext1.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)
      ).contains("Andere Organisationseinheit");
    }
  }

  @Nested
  class getProprietarySingleElement {

    @Test
    void return404IfNormNotFound() throws Exception {
      // given no norm
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
      );
      var eid = "art-z20";

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary/" + eid).accept(
            MediaType.APPLICATION_JSON_VALUE
          )
        )
        // then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/dokument-not-found"))
        .andExpect(jsonPath("title").value("Dokument not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail").value(
            "Document with eli eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1 does not exist"
          )
        )
        .andExpect(
          jsonPath("instance").value(
            "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/proprietary/art-z20"
          )
        )
        .andExpect(
          jsonPath("eli").value(
            "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
          )
        );
    }

    @Test
    void returnEmptyValuesIfNormHasNoProprietaryAtAll() throws Exception {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/rechtsetzungsdokument-1"
      );
      var eid = "art-z20";

      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        ProprietaryControllerIntegrationTest.class,
        "regelungstext-without-proprietary",
        NormPublishState.UNPUBLISHED
      );

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary/" + eid).accept(
            MediaType.APPLICATION_JSON_VALUE
          )
        )
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("artDerNorm").isEmpty());
    }

    @Test
    void returnEmptyValuesIfInvalidProprietaryDoesNotContainThem() throws Exception {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
      );
      var eid = "hauptteil-n1_abschnitt-n0_art-n1";

      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        ProprietaryControllerIntegrationTest.class,
        "vereinsgesetz-with-invalid-proprietary-metadata",
        NormPublishState.UNPUBLISHED
      );

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary/" + eid).accept(
            MediaType.APPLICATION_JSON_VALUE
          )
        )
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("artDerNorm").isEmpty());
    }

    @Test
    void returnProprietarySingleElement() throws Exception {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
      );
      var eid = "art-z20";

      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
          )
        )
      );

      // when
      mockMvc
        .perform(
          get("/api/v1/norms/" + eli + "/proprietary/" + eid).accept(
            MediaType.APPLICATION_JSON_VALUE
          )
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
        "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
      );
      var eid = "hauptteil-n1_abschnitt-n0_art-n1";

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
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail").value(
            "Norm with eli eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu does not exist"
          )
        )
        .andExpect(
          jsonPath("instance").value(
            "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/proprietary/hauptteil-n1_abschnitt-n0_art-n1"
          )
        )
        .andExpect(jsonPath("eli").value("eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu"));
    }

    @Test
    void createsProprietaryAndMetadatenDsAndEinzelelementAndSetsValue() throws Exception {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
      );
      var eid = "hauptteil-n1_abschnitt-n0_art-n1";

      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            ProprietaryControllerIntegrationTest.class,
            "regelungstext-without-proprietary/regelungstext-verkuendung-1.xml"
          )
        )
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
