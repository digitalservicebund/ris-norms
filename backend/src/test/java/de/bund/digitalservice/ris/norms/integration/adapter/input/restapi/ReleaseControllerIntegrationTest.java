package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.NormManifestationDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.BinaryFileRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.service.NormDBService;
import de.bund.digitalservice.ris.norms.domain.entity.*;
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
class ReleaseControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DokumentRepository dokumentRepository;

  @Autowired
  private NormManifestationRepository normManifestationRepository;

  @Autowired
  private BinaryFileRepository binaryFileRepository;

  @Autowired
  private NormDBService normDBService;

  @AfterEach
  void cleanUp() {
    dokumentRepository.deleteAll();
    binaryFileRepository.deleteAll();
    normManifestationRepository.deleteAll();
  }

  @Nested
  class postReleases {

    @Test
    void itDoesNotReleaseBecauseNormNotFound() throws Exception {
      // given no norm is stored in the database

      // when
      mockMvc
        .perform(
          post("/api/v1/norms/eli/bund/bgbl-1/2023/413/releases")
            .content(
              """
              {"releaseType": "praetext"}
              """
            )
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
        // then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail").value("Norm with eli eli/bund/bgbl-1/2023/413 does not exist")
        )
        .andExpect(jsonPath("instance").value("/api/v1/norms/eli/bund/bgbl-1/2023/413/releases"))
        .andExpect(jsonPath("eli").value("eli/bund/bgbl-1/2023/413"));
    }

    @Test
    void itReleaseANormExpression() throws Exception {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23",
        NormPublishState.UNPUBLISHED
      );

      // When // Then
      mockMvc
        .perform(
          post("/api/v1/norms/eli/bund/bgbl-1/2017/s419/releases")
            .content(
              """
              {"releaseType": "praetext"}
              """
            )
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("normWorkEli", equalTo("eli/bund/bgbl-1/2017/s419")))
        .andExpect(
          jsonPath(
            "title",
            equalTo("Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes")
          )
        )
        .andExpect(jsonPath("shortTitle", equalTo("")))
        .andExpect(jsonPath("expressions", hasSize(1)))
        .andExpect(
          jsonPath(
            "expressions[0].normExpressionEli",
            equalTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu")
          )
        )
        .andExpect(jsonPath("expressions[0].isGegenstandslos", equalTo(false)))
        .andExpect(jsonPath("expressions[0].currentStatus", equalTo("PRAETEXT_RELEASED")));

      var publishedManifestationOfAmendingNorm = normManifestationRepository.findByManifestationEli(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/%s".formatted(LocalDate.now().toString())
      );
      assertThat(publishedManifestationOfAmendingNorm).isPresent();
      assertThat(publishedManifestationOfAmendingNorm.get().getPublishState()).isEqualTo(
        NormPublishState.QUEUED_FOR_PUBLISH
      );

      var newUnpublishedManifestationOfAmendingNorm =
        normManifestationRepository.findByManifestationEli(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2999-12-31"
        );
      assertThat(newUnpublishedManifestationOfAmendingNorm).isPresent();
      assertThat(newUnpublishedManifestationOfAmendingNorm.get().getPublishState()).isEqualTo(
        NormPublishState.UNPUBLISHED
      );

      // 1 queued for publish norm (1 rechtsetzungsdokument and 1 reglungstext) + 1 newly created manifestations (1 rechtsetzungsdokument and 1 reglungstext) for further work. The original amending norm should no longer exist
      assertThat(dokumentRepository.findAll()).hasSize(4);
      assertThat(normManifestationRepository.findAll()).hasSize(2);
    }

    @Test
    void releasingANormASecondTimeCreatesTheSameFilesAndCleansUpOldRelease() throws Exception {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23",
        NormPublishState.UNPUBLISHED
      );

      // When // Then
      mockMvc
        .perform(
          post("/api/v1/norms/eli/bund/bgbl-1/2017/s419/releases")
            .content(
              """
              {"releaseType": "praetext"}
              """
            )
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());

      // release norm a second time
      mockMvc
        .perform(
          post("/api/v1/norms/eli/bund/bgbl-1/2017/s419/releases")
            .content(
              """
              {"releaseType": "praetext"}
              """
            )
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());

      var publishedManifestationOfAmendingNorm = normManifestationRepository.findByManifestationEli(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/%s".formatted(LocalDate.now().toString())
      );
      assertThat(publishedManifestationOfAmendingNorm).isPresent();
      assertThat(publishedManifestationOfAmendingNorm.get().getPublishState()).isEqualTo(
        NormPublishState.QUEUED_FOR_PUBLISH
      );

      var newUnpublishedManifestationOfAmendingNorm =
        normManifestationRepository.findByManifestationEli(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2999-12-31"
        );
      assertThat(newUnpublishedManifestationOfAmendingNorm).isPresent();
      assertThat(newUnpublishedManifestationOfAmendingNorm.get().getPublishState()).isEqualTo(
        NormPublishState.UNPUBLISHED
      );

      // 1 queued for publish norm (1 rechtsetzungsdokument and 1 reglungstext) + 1 newly created manifestations (1 rechtsetzungsdokument and 1 reglungstext) for further work. The original amending norm should no longer exist
      assertThat(dokumentRepository.findAll()).hasSize(4);
    }

    @Test
    void failsWhenTryingToReleaseAnXsdInvalidNorm() throws Exception {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        ReleaseControllerIntegrationTest.class,
        "vereinsgesetz-xsd-invalid",
        NormPublishState.UNPUBLISHED
      );

      // When // Then
      // Request is refused
      mockMvc
        .perform(
          post("/api/v1/norms/eli/bund/bgbl-1/1964/s593/releases")
            .content(
              """
              {"releaseType": "praetext"}
              """
            )
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/ldml-de-not-valid")));

      // Content of the DB should be unchanged & unpublished
      assertThat(dokumentRepository.findAll()).hasSize(2);
      assertThat(
        dokumentRepository.findByEliDokumentManifestation(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
        )
      ).isNotEmpty();

      assertThat(
        normManifestationRepository.findByManifestationEli(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
        )
      )
        .isNotEmpty()
        .map(NormManifestationDto::getPublishState)
        .contains(NormPublishState.UNPUBLISHED);
    }

    @Test
    void failsWhenTryingToReleaseASchematronInvalidNorm() throws Exception {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        ReleaseControllerIntegrationTest.class,
        "vereinsgesetz-schematron-invalid",
        NormPublishState.UNPUBLISHED
      );

      // When // Then
      // Request is refused
      mockMvc
        .perform(
          post("/api/v1/norms/eli/bund/bgbl-1/1964/s593/releases")
            .content(
              """
              {"releaseType": "praetext"}
              """
            )
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/ldml-de-not-schematron-valid")));

      // Content of the DB should be unchanged = 1 sample norms, unpublished
      assertThat(dokumentRepository.findAll()).hasSize(2);
      assertThat(
        dokumentRepository.findByEliDokumentManifestation(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
        )
      ).isNotEmpty();
      assertThat(
        normManifestationRepository.findByManifestationEli(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
        )
      )
        .isNotEmpty()
        .map(NormManifestationDto::getPublishState)
        .contains(NormPublishState.UNPUBLISHED);
    }
  }

  @Nested
  class getZielnormExpressionsStatus {

    @Test
    void itReturnsStatusForOneExpression() throws Exception {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
        NormPublishState.UNPUBLISHED
      );

      //when
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/releases").accept(MediaType.APPLICATION_JSON)
        )
        //then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.normWorkEli").value("eli/bund/bgbl-1/1964/s593"))
        .andExpect(jsonPath("$.title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts"))
        .andExpect(jsonPath("$.shortTitle").value("Vereinsgesetz"))
        .andExpect(jsonPath("$.expressions").isArray())
        .andExpect(
          jsonPath("$.expressions[0].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"
          )
        )
        .andExpect(jsonPath("$.expressions[0].isGegenstandslos").value(false))
        .andExpect(jsonPath("$.expressions[0].currentStatus").value("PRAETEXT_RELEASED"));
    }

    @Test
    void itDoesNotReturnStatusIfNoUnpublished() throws Exception {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
        NormPublishState.QUEUED_FOR_PUBLISH
      );

      //when
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/releases").accept(MediaType.APPLICATION_JSON)
        )
        //then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.normWorkEli").value("eli/bund/bgbl-1/1964/s593"))
        .andExpect(jsonPath("$.title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts"))
        .andExpect(jsonPath("$.shortTitle").value("Vereinsgesetz"))
        .andExpect(jsonPath("$.expressions").isEmpty());
    }

    @Test
    void itReturns404WhenNormDoesntExist() throws Exception {
      //when
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/1111/s593/releases").accept(MediaType.APPLICATION_JSON)
        )
        //then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail").value("Norm with eli eli/bund/bgbl-1/1111/s593 does not exist")
        )
        .andExpect(jsonPath("instance").value("/api/v1/norms/eli/bund/bgbl-1/1111/s593/releases"))
        .andExpect(jsonPath("eli").value("eli/bund/bgbl-1/1111/s593"));
    }
  }
}
