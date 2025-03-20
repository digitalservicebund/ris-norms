package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.ReleaseDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.ReleaseRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.Roles;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
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
  private ReleaseRepository releaseRepository;

  @AfterEach
  void cleanUp() {
    releaseRepository.deleteAll();
    dokumentRepository.deleteAll();
    normManifestationRepository.deleteAll();
  }

  @Nested
  class getReleases {

    @Test
    void itDoesReturnNoReleasesIfNoneFound() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/releases")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("[0]").doesNotExist());
    }

    @Test
    void itReturnsRelease() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
          )
        )
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithPassiveModifications.xml")
        )
      );

      var amendingNorm = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23")
        .orElseThrow();
      var affectedNorm = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
        .orElseThrow();
      var affectedNormZf0 = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23")
        .orElseThrow();

      releaseRepository.save(
        ReleaseDto
          .builder()
          .releasedAt(Instant.parse("2024-01-02T10:20:30.0Z"))
          .norms(List.of(amendingNorm, affectedNorm, affectedNormZf0))
          .build()
      );

      // When
      mockMvc
        .perform(
          get("/api/v1/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/releases")
            .accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("[0]").exists())
        .andExpect(jsonPath("[1]").doesNotExist())
        .andExpect(jsonPath("[0].releaseAt", equalTo("2024-01-02T10:20:30Z")))
        .andExpect(jsonPath("[0].norms[3]").doesNotExist())
        .andExpect(
          jsonPath(
            "[0].norms",
            containsInAnyOrder(
              "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml",
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml",
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23/regelungstext-1.xml"
            )
          )
        );
    }
  }

  @Nested
  class postReleases {

    @Test
    void itDoesNotReleaseBecauseNormNotFound() throws Exception {
      // given no norm is stored in the database

      // when
      mockMvc
        .perform(
          post("/api/v1/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/releases")
            .accept(MediaType.APPLICATION_JSON)
        )
        // then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value("Norm with eli eli/bund/bgbl-1/2023/413/2023-12-29/1/deu does not exist")
        )
        .andExpect(
          jsonPath("instance").value("/api/v1/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/releases")
        )
        .andExpect(jsonPath("eli").value("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu"));
    }

    @Test
    void itReleaseANormExpression() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
          )
        )
      );

      // When // Then
      mockMvc
        .perform(
          post("/api/v1/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/releases")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("releaseAt").exists())
        .andExpect(jsonPath("norms[1]").doesNotExist())
        .andExpect(
          jsonPath(
            "norms",
            containsInAnyOrder(
              "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/%s/regelungstext-1.xml".formatted(
                  LocalDate.now().toString()
                )
            )
          )
        );

      var publishedManifestationOfAmendingNorm = normManifestationRepository.findByManifestationEli(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/%s".formatted(LocalDate.now().toString())
      );
      assertThat(publishedManifestationOfAmendingNorm).isPresent();
      assertThat(publishedManifestationOfAmendingNorm.get().getPublishState())
        .isEqualTo(NormPublishState.QUEUED_FOR_PUBLISH);

      var newUnpublishedManifestationOfAmendingNorm =
        normManifestationRepository.findByManifestationEli(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/%s".formatted(
              LocalDate.now().plusDays(1).toString()
            )
        );
      assertThat(newUnpublishedManifestationOfAmendingNorm).isPresent();
      assertThat(newUnpublishedManifestationOfAmendingNorm.get().getPublishState())
        .isEqualTo(NormPublishState.UNPUBLISHED);

      // 1 queued for publish norms + 1 newly created manifestations for further work. The original amending norm should no longer exist
      assertThat(dokumentRepository.findAll()).hasSize(2);
      assertThat(normManifestationRepository.findAll()).hasSize(2);
    }

    @Test
    void releasingANormASecondTimeCreatesTheSameFilesAndCleansUpOldRelease() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
          )
        )
      );

      // When // Then
      mockMvc
        .perform(
          post("/api/v1/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/releases")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());

      // release norm a second time
      mockMvc
        .perform(
          post("/api/v1/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/releases")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());

      var publishedManifestationOfAmendingNorm = normManifestationRepository.findByManifestationEli(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/%s".formatted(LocalDate.now().toString())
      );
      assertThat(publishedManifestationOfAmendingNorm).isPresent();
      assertThat(publishedManifestationOfAmendingNorm.get().getPublishState())
        .isEqualTo(NormPublishState.QUEUED_FOR_PUBLISH);

      var newUnpublishedManifestationOfAmendingNorm =
        normManifestationRepository.findByManifestationEli(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/%s".formatted(
              LocalDate.now().plusDays(1).toString()
            )
        );
      assertThat(newUnpublishedManifestationOfAmendingNorm).isPresent();
      assertThat(newUnpublishedManifestationOfAmendingNorm.get().getPublishState())
        .isEqualTo(NormPublishState.UNPUBLISHED);

      // 1 queued for publish norms + 1 newly created manifestations for further work. The original amending norm should no longer exist
      assertThat(dokumentRepository.findAll()).hasSize(2);
    }

    @Test
    void failsWhenTryingToReleaseAnXsdInvalidNorm() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithModsXsdInvalid.xml"))
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithPassiveModifications.xml")
        )
      );

      var affectedNormZf0 = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23")
        .orElseThrow();
      affectedNormZf0.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);
      normManifestationRepository.save(affectedNormZf0);

      // When // Then
      // Request is refused
      mockMvc
        .perform(
          post("/api/v1/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/releases")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/ldml-de-not-valid")));

      // Content of the DB should be unchanged = 3 sample norms, all unpublished
      assertThat(dokumentRepository.findAll()).hasSize(3);
      assertThat(
        dokumentRepository.findByEliDokumentManifestation(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
        )
      )
        .isNotEmpty();
      assertThat(
        dokumentRepository.findByEliDokumentManifestation(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
        )
      )
        .isNotEmpty();

      var originalNormWithPassiveMods = normManifestationRepository.findByManifestationEli(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23"
      );

      assertThat(originalNormWithPassiveMods).isNotEmpty();
      assertThat(originalNormWithPassiveMods.get().getPublishState())
        .isEqualTo(NormPublishState.QUEUED_FOR_PUBLISH);
    }

    @Test
    void failsWhenTryingToReleaseASchematronInvalidNorm() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithModsSchematronInvalid.xml")
        )
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithPassiveModifications.xml")
        )
      );

      // When // Then
      // Request is refused
      mockMvc
        .perform(
          post("/api/v1/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/releases")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/ldml-de-not-schematron-valid")));

      // Content of the DB should be unchanged = 3 sample norms, all unpublished
      assertThat(dokumentRepository.findAll()).hasSize(3);
      assertThat(
        dokumentRepository.findByEliDokumentManifestation(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
        )
      )
        .isNotEmpty();
      assertThat(
        dokumentRepository.findByEliDokumentManifestation(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
        )
      )
        .isNotEmpty();
      assertThat(
        dokumentRepository.findByEliDokumentManifestation(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23/regelungstext-1.xml"
        )
      )
        .isNotEmpty();
    }
  }
}
