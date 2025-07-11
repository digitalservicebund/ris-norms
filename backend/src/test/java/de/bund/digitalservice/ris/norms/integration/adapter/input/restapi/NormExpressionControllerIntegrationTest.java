package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static de.bund.digitalservice.ris.norms.XPathMatcher.hasXPath;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
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
class NormExpressionControllerIntegrationTest extends BaseIntegrationTest {

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
  class GetNormByEli {

    // Note: Error case missing

    @Test
    void itCallsNormsServiceAndReturnsNorm() throws Exception {
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
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu").accept(
            MediaType.APPLICATION_JSON
          )
        )
        .andExpect(status().isOk())
        .andExpect(
          jsonPath("eli").value(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
          )
        )
        .andExpect(jsonPath("title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts"))
        .andExpect(jsonPath("frbrName").value("BGBl. I"))
        .andExpect(jsonPath("frbrNumber").value("s593"))
        .andExpect(jsonPath("frbrDateVerkuendung").value("1964-08-05"));
    }

    @Test
    void itCallsNormServiceAndReturnsNormRender() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-verkuendung-1.xml"
          )
        )
      );

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu").accept(
            MediaType.TEXT_HTML
          )
        )
        .andExpect(status().isOk())
        .andExpect(
          content().node(
            hasXPath(
              "//h1//*[@data-eId=\"einleitung-n1_doktitel-n1_text-n1_doctitel-n1\"]",
              equalTo("Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes")
            )
          )
        );
    }
  }

  @Nested
  class GetNormRenderWithQueryParameters {

    // Note: Error case missing

    @Test
    void itCallsNormServiceAndReturnsNormRenderWithMetadata() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-verkuendung-1.xml"
          )
        )
      );

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu?showMetadata=true").accept(
            MediaType.TEXT_HTML
          )
        )
        .andExpect(status().isOk())
        .andExpect(
          content().node(
            hasXPath(
              "//h1//*[@data-eId=\"einleitung-n1_doktitel-n1_text-n1_doctitel-n1\"]",
              equalTo("Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes")
            )
          )
        )
        .andExpect(
          content().node(
            hasXPath(
              "//section[@class=\"metadata\"]//td[text()=\"Amtliche Langüberschrift\"]/following-sibling::td/text()",
              equalTo("Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes")
            )
          )
        );
    }
  }
}
