package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser
public class NormManifestationControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DokumentRepository dokumentRepository;

  @AfterEach
  void cleanUp() {
    dokumentRepository.deleteAll();
  }

  @Nested
  class getNormManifestationXml {

    @Test
    void itCallsNormServiceAndReturnsNormXml() throws Exception {
      // Given
      dokumentRepository.saveAll(NormMapper.mapToDtos(Fixtures.loadNormFromDisk("SimpleNorm.xml")));
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml";

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms/{eli}", eli).accept(MediaType.APPLICATION_XML))
        .andExpect(status().isOk())
        .andExpect(
          xpath("//*[@eId='meta-1_ident-1_frbrmanifestation-1_frbrthis-1']/@value").string(eli)
        );
    }

    @Test
    void itReturnsNotFoundIfManifestationDoesntExist() throws Exception {
      // Given
      // No norm
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml";

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms/{eli}", eli).accept(MediaType.APPLICATION_XML))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Norm with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
            )
        )
        .andExpect(jsonPath("eli").value(eli));
    }

    @Test
    void itReturnsNotFoundIfTypeIsNotXml() throws Exception {
      // Given
      dokumentRepository.saveAll(NormMapper.mapToDtos(Fixtures.loadNormFromDisk("SimpleNorm.xml")));
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.pdf";

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms/{eli}", eli))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("type").value("/errors/no-static-resource"))
        .andExpect(jsonPath("title").value("No static resource found"))
        .andExpect(jsonPath("status").value(500))
        .andExpect(
          jsonPath("detail")
            .value(
              "No static resource api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.pdf."
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.pdf"
            )
        );
    }
  }
}
