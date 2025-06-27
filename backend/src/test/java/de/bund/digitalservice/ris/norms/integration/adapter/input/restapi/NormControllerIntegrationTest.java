package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
class NormControllerIntegrationTest extends BaseIntegrationTest {

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
  class getNorms {

    @Test
    void itShowsEachWorkOnlyOnce() throws Exception {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
        NormPublishState.UNPUBLISHED
      );
      // same expression, different manifestation
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2017-03-15",
        NormPublishState.UNPUBLISHED
      );
      // same work, different expression
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu/2017-03-15",
        NormPublishState.UNPUBLISHED
      );

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("content[0].eli").value("eli/bund/bgbl-1/1964/s593"))
        .andExpect(
          jsonPath("content[0].title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts")
        )
        .andExpect(jsonPath("content[1]").doesNotExist())
        .andExpect(jsonPath("page.size").value(20))
        .andExpect(jsonPath("page.number").value(0))
        .andExpect(jsonPath("page.totalElements").value(1))
        .andExpect(jsonPath("page.totalPages").value(1));
    }

    @Test
    void itShowsAllWorksInTheDBAndPaginates() throws Exception {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
        NormPublishState.UNPUBLISHED
      );
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23",
        NormPublishState.UNPUBLISHED
      );
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/2021/s818/2021-04-16/1/deu/2021-04-16",
        NormPublishState.UNPUBLISHED
      );
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/2024/10/2024-01-18/1/deu/2024-01-18",
        NormPublishState.UNPUBLISHED
      );

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms?size=2&page=0").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("content[0].eli").value("eli/bund/bgbl-1/1964/s593"))
        .andExpect(jsonPath("content[1].eli").value("eli/bund/bgbl-1/2017/s419"))
        .andExpect(jsonPath("content[2]").doesNotExist())
        .andExpect(jsonPath("page.size").value(2))
        .andExpect(jsonPath("page.number").value(0))
        .andExpect(jsonPath("page.totalElements").value(4))
        .andExpect(jsonPath("page.totalPages").value(2));
      mockMvc
        .perform(get("/api/v1/norms?size=2&page=1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("content[0].eli").value("eli/bund/bgbl-1/2021/s818"))
        .andExpect(jsonPath("content[1].eli").value("eli/bund/bgbl-1/2024/10"))
        .andExpect(jsonPath("content[2]").doesNotExist())
        .andExpect(jsonPath("page.size").value(2))
        .andExpect(jsonPath("page.number").value(1))
        .andExpect(jsonPath("page.totalElements").value(4))
        .andExpect(jsonPath("page.totalPages").value(2));
    }
  }
}
