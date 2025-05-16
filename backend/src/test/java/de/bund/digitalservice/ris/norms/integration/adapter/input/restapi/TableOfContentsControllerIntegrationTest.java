package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Roles;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser(roles = { Roles.NORMS_USER })
class TableOfContentsControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DokumentRepository dokumentRepository;

  @AfterEach
  void cleanUp() {
    dokumentRepository.deleteAll();
  }

  @Test
  void return404IfRegelungstextNotFound() throws Exception {
    // given no norm
    var eli = DokumentExpressionEli.fromString(
      "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1"
    );

    // when
    mockMvc
      .perform(get("/api/v1/norms/" + eli + "/toc").accept(MediaType.APPLICATION_JSON_VALUE))
      // then
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("type").value("/errors/regelungstext-not-found"))
      .andExpect(jsonPath("title").value("Regelungstext not found"))
      .andExpect(jsonPath("status").value(404))
      .andExpect(
        jsonPath("detail").value(
          "Regelungstext with eli eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1 does not exist"
        )
      )
      .andExpect(
        jsonPath("instance").value(
          "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/toc"
        )
      )
      .andExpect(
        jsonPath("eli").value(
          "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1"
        )
      );
  }

  @Test
  void returnsToc() throws Exception {
    // given
    var eli = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
    );
    dokumentRepository.save(
      DokumentMapper.mapToDto(
        Fixtures.loadRegelungstextFromDisk(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
        )
      )
    );

    // when
    mockMvc
      .perform(get("/api/v1/norms/" + eli + "/toc").accept(MediaType.APPLICATION_JSON_VALUE))
      // then
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].id").value("hauptteil-1_art-1"))
      .andExpect(jsonPath("$[0].marker").value("§ 20"))
      .andExpect(jsonPath("$[0].heading").isEmpty())
      .andExpect(jsonPath("$[0].type").value("article"))
      .andExpect(jsonPath("$[0].children").isEmpty())
      .andExpect(jsonPath("$[1].id").value("hauptteil-1_art-2"))
      .andExpect(jsonPath("$[1].marker").value("Artikel 34"))
      .andExpect(jsonPath("$[1].heading").value("Inkrafttreten"))
      .andExpect(jsonPath("$[1].type").value("article"))
      .andExpect(jsonPath("$[1].children").isEmpty());
  }
}
