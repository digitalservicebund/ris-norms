package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
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
          post("/api/v1/renderings")
            .accept(MediaType.TEXT_HTML)
            .contentType(MediaType.APPLICATION_XML)
            .content(
              Fixtures.loadTextFromDisk(
                "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
              )
            )
        )
        .andExpect(status().isOk())
        .andExpect(
          xpath("//*[@data-eId='art-z20_abs-z1_inhalt-n1_liste-n1_listenelem-n2_text-n1']").string(
            containsString("§ 9 Abs. 1 Satz 2, Abs. 2")
          )
        );
    }
  }
}
