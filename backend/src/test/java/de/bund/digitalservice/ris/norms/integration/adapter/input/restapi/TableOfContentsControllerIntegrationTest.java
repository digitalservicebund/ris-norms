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
        jsonPath("detail")
          .value(
            "Regelungstext with eli eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1 does not exist"
          )
      )
      .andExpect(
        jsonPath("instance")
          .value(
            "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/toc"
          )
      )
      .andExpect(
        jsonPath("eli")
          .value("eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1")
      );
  }

  @Test
  void returnsToc() throws Exception {
    // given
    var eli = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
    );
    dokumentRepository.save(
      DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithGliederung.xml"))
    );

    // when
    mockMvc
      .perform(get("/api/v1/norms/" + eli + "/toc").accept(MediaType.APPLICATION_JSON_VALUE))
      // then
      .andExpect(status().isOk())
      // Verify Title 1 exists inside Subsection 1
      .andExpect(
        jsonPath("$[0].children[0].children[0].children[0].children[0].children[0].id")
          .value("hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1_titel-1")
      )
      .andExpect(
        jsonPath("$[0].children[0].children[0].children[0].children[0].children[0].marker")
          .value("Titel 1")
      )
      .andExpect(
        jsonPath("$[0].children[0].children[0].children[0].children[0].children[0].heading")
          .value("Überschrift Titel")
      )
      .andExpect(
        jsonPath("$[0].children[0].children[0].children[0].children[0].children[0].type")
          .value("title")
      )
      .andExpect(
        jsonPath(
          "$[0].children[0].children[0].children[0].children[0].children[0].children.length()"
        )
          .value(1)
      ) // Title contains one subtitle
      // Verify Subtitle 1 inside Title 1
      .andExpect(
        jsonPath("$[0].children[0].children[0].children[0].children[0].children[0].children[0].id")
          .value("hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1_titel-1_utitel-1")
      )
      .andExpect(
        jsonPath(
          "$[0].children[0].children[0].children[0].children[0].children[0].children[0].marker"
        )
          .value("Untertitel 1")
      )
      .andExpect(
        jsonPath(
          "$[0].children[0].children[0].children[0].children[0].children[0].children[0].heading"
        )
          .value("Überschrift Untertitel")
      )
      .andExpect(
        jsonPath(
          "$[0].children[0].children[0].children[0].children[0].children[0].children[0].type"
        )
          .value("subtitle")
      )
      .andExpect(
        jsonPath(
          "$[0].children[0].children[0].children[0].children[0].children[0].children[0].children.length()"
        )
          .value(2)
      ) // Subtitle contains two articles
      // Verify First Article inside Subtitle
      .andExpect(
        jsonPath(
          "$[0].children[0].children[0].children[0].children[0].children[0].children[0].children[0].id"
        )
          .value(
            "hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1_titel-1_utitel-1_art-1"
          )
      )
      .andExpect(
        jsonPath(
          "$[0].children[0].children[0].children[0].children[0].children[0].children[0].children[0].marker"
        )
          .value("§ 1")
      )
      .andExpect(
        jsonPath(
          "$[0].children[0].children[0].children[0].children[0].children[0].children[0].children[0].heading"
        )
          .value("Anwendungsbereich")
      )
      .andExpect(
        jsonPath(
          "$[0].children[0].children[0].children[0].children[0].children[0].children[0].children[0].type"
        )
          .value("article")
      )
      .andExpect(
        jsonPath(
          "$[0].children[0].children[0].children[0].children[0].children[0].children[0].children[0].children"
        )
          .isEmpty()
      ) // Articles have NO children
      // Verify Second Article inside Subtitle
      .andExpect(
        jsonPath(
          "$[0].children[0].children[0].children[0].children[0].children[0].children[0].children[1].id"
        )
          .value(
            "hauptteil-1_buch-1_teil-1_kapitel-1_abschnitt-1_uabschnitt-1_titel-1_utitel-1_art-2"
          )
      )
      .andExpect(
        jsonPath(
          "$[0].children[0].children[0].children[0].children[0].children[0].children[0].children[1].marker"
        )
          .value("§ 2")
      )
      .andExpect(
        jsonPath(
          "$[0].children[0].children[0].children[0].children[0].children[0].children[0].children[1].heading"
        )
          .value("Paragrafenüberschrift")
      )
      .andExpect(
        jsonPath(
          "$[0].children[0].children[0].children[0].children[0].children[0].children[0].children[1].type"
        )
          .value("article")
      )
      .andExpect(
        jsonPath(
          "$[0].children[0].children[0].children[0].children[0].children[0].children[0].children[1].children"
        )
          .isEmpty()
      ); // Articles have NO children
  }
}
