package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.adapter.output.database.repository.BinaryFileRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.Roles;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser(roles = { Roles.NORMS_USER })
class NormWorkControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DokumentRepository dokumentRepository;

  @Autowired
  private NormManifestationRepository normManifestationRepository;

  @Autowired
  private BinaryFileRepository binaryFileRepository;

  @AfterEach
  void cleanUp() {
    dokumentRepository.deleteAll();
    binaryFileRepository.deleteAll();
    normManifestationRepository.deleteAll();
  }

  @Nested
  class getNorm {

    @Test
    void itShowsWorkInformation() throws Exception {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
        NormPublishState.UNPUBLISHED
      );

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms/eli/bund/bgbl-1/1964/s593").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("eli").value("eli/bund/bgbl-1/1964/s593"))
        .andExpect(jsonPath("title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts"));
    }
  }

  @Nested
  class getExpressions {

    @Test
    void itShowsExpressions() throws Exception {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu/2017-03-15",
        NormPublishState.UNPUBLISHED
      );
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        NormWorkControllerIntegrationTest.class,
        "vereinsgesetz-gegenstandlos",
        NormPublishState.UNPUBLISHED
      );

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/expressions").accept(
            MediaType.APPLICATION_JSON
          )
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("[0].eli").value("eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu"))
        .andExpect(jsonPath("[0].gegenstandslos").value(false))
        .andExpect(jsonPath("[1].eli").value("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"))
        .andExpect(jsonPath("[1].gegenstandslos").value(true))
        .andExpect(jsonPath("[2]").doesNotExist());
    }

    @Test
    void itOnlyLooksAtLatestManifestation() throws Exception {
      // Given
      Fixtures.loadAndSaveNormFixture(
        // old manifestation
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        NormWorkControllerIntegrationTest.class,
        "vereinsgesetz-gegenstandlos",
        NormPublishState.UNPUBLISHED
      );
      Fixtures.loadAndSaveNormFixture(
        // new manifestation
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2017-03-15",
        NormPublishState.UNPUBLISHED
      );

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/expressions").accept(
            MediaType.APPLICATION_JSON
          )
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("[0].eli").value("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"))
        .andExpect(jsonPath("[0].gegenstandslos").value(false))
        .andExpect(jsonPath("[1]").doesNotExist());
    }
  }
}
