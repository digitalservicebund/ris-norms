package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
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
class ElementControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DokumentRepository dokumentRepository;

  @AfterEach
  void cleanUp() {
    dokumentRepository.deleteAll();
  }

  @Nested
  class getElementHtmlPreview {

    @Test
    void returnsNotFoundIfRegelungstextNotFoundByEli() throws Exception {
      // Given
      // Nothing

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/elements/hauptteil-1_art-3"
          )
            .accept(MediaType.TEXT_HTML)
        )
        // Then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/regelungstext-not-found"))
        .andExpect(jsonPath("title").value("Regelungstext not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Regelungstext with eli eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1 does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/elements/hauptteil-1_art-3"
            )
        )
        .andExpect(
          jsonPath("eli")
            .value("eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1")
        );
    }

    @Test
    void returnsNotFoundIfElementNotFoundByEid() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithMultipleMods.xml"))
      );

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/NONEXISTENT_EID"
          )
            .accept(MediaType.TEXT_HTML)
        )
        // Then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/element-not-found"))
        .andExpect(jsonPath("title").value("Element not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Element with eid NONEXISTENT_EID does not exist in norm with eli eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/NONEXISTENT_EID"
            )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1")
        )
        .andExpect(jsonPath("eid").value("NONEXISTENT_EID"));
    }

    @Test
    void returnsElementRenderedAsHtml() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithMultipleMods.xml"))
      );

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/hauptteil-1_art-1"
          )
            .accept(MediaType.TEXT_HTML)
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Änderung des Vereinsgesetzes")));
    }

    @Test
    void returnsBadRequestIfAtIsoDateIsInvalid() throws Exception {
      // Given
      // Nothing

      // When / Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/elements/hauptteil-1_art-20?atIsoDate=INVALID"
          )
            .accept(MediaType.TEXT_HTML)
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("type").value("/errors/parameter-binding-error"))
        .andExpect(jsonPath("title").value("Parameter Binding Error"))
        .andExpect(jsonPath("status").value(400))
        .andExpect(jsonPath("detail").value("Invalid request parameter: INVALID"))
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/elements/hauptteil-1_art-20"
            )
        );
    }
  }

  @Nested
  class getElementInfo {

    @Test
    void returnsNotFoundIfRegelungstextNotFoundByEli() throws Exception {
      // Given
      // Nothing

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/elements/hauptteil-1_art-3"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/regelungstext-not-found"))
        .andExpect(jsonPath("title").value("Regelungstext not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Regelungstext with eli eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1 does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/elements/hauptteil-1_art-3"
            )
        )
        .andExpect(
          jsonPath("eli")
            .value("eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1")
        );
    }

    @Test
    void returnsNotFoundIfElementNotFoundByEid() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithMultipleMods.xml"))
      );

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/NONEXISTENT_EID"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/element-not-found"))
        .andExpect(jsonPath("title").value("Element not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Element with eid NONEXISTENT_EID does not exist in norm with eli eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/NONEXISTENT_EID"
            )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1")
        )
        .andExpect(jsonPath("eid").value("NONEXISTENT_EID"));
    }

    @Test
    void returnElementEidTitleAndType() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithMultipleMods.xml"))
      );

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/hauptteil-1_art-1"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("eid").value("hauptteil-1_art-1"))
        .andExpect(jsonPath("type").value("article"))
        .andExpect(jsonPath("title").value("Artikel 1 Änderung des Vereinsgesetzes"));
    }
  }
}
