package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
class RenderingControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DokumentRepository dokumentRepository;

  @AfterEach
  void cleanUp() {
    dokumentRepository.deleteAll();
  }

  @Nested
  class getHtmlPreview {

    @Test
    void itReturnsRender() throws Exception {
      mockMvc
        .perform(
          post("/api/v1/renderings?atIsoDate=2024-01-01T00:00:00.0Z")
            .accept(MediaType.TEXT_HTML)
            .contentType(MediaType.APPLICATION_XML)
            .content(Fixtures.loadTextFromDisk("NormWithPassiveModifications.xml"))
        )
        .andExpect(status().isOk())
        .andExpect(
          xpath("//*[@data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1']")
            .string(containsString("§ 9 Abs. 1 Satz 2, Abs. 2"))
        );
    }
  }
}
