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
      "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
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
          "Regelungstext with eli eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1 does not exist"
        )
      )
      .andExpect(
        jsonPath("instance").value(
          "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/toc"
        )
      )
      .andExpect(
        jsonPath("eli").value(
          "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
        )
      );
  }

  @Test
  void returnsToc() throws Exception {
    // given
    var eli = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2024/17/2024-01-24/1/deu/regelungstext-verkuendung-1"
    );

    dokumentRepository.save(
      DokumentMapper.mapToDto(
        Fixtures.loadRegelungstextFromDisk(
          "eli/bund/bgbl-1/2024/17/2024-01-24/1/deu/2024-01-24/regelungstext-verkuendung-1.xml"
        )
      )
    );

    dokumentRepository.save(
      DokumentMapper.mapToDto(
        Fixtures.loadRegelungstextFromDisk(
          "eli/bund/bgbl-1/2024/17/2024-01-24/1/deu/2024-01-24/regelungstext-verkuendung-2.xml"
        )
      )
    );

    // when
    mockMvc
      .perform(get("/api/v1/norms/" + eli + "/toc").accept(MediaType.APPLICATION_JSON_VALUE))
      // then
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].id").value("art-z"))
      .andExpect(jsonPath("$[0].marker").value(""))
      .andExpect(
        jsonPath("$[0].heading").value("Soldatinnen- und Soldatengleichstellungsgesetz (SGleiG)")
      )
      .andExpect(jsonPath("$[0].type").value("article"))
      .andExpect(jsonPath("$[0].hasEingebundeneStammform").value("true"))
      .andExpect(jsonPath("$[0].children").isEmpty())
      .andExpect(jsonPath("$[1].id").value("art-z2"))
      .andExpect(jsonPath("$[1].marker").value("Artikel 2"))
      .andExpect(jsonPath("$[1].heading").value("Änderung des Bundesgleichstellungsgesetzes"))
      .andExpect(jsonPath("$[1].type").value("article"))
      .andExpect(jsonPath("$[1].children").isEmpty())
      .andExpect(jsonPath("$[1].hasEingebundeneStammform").value("false"))
      .andExpect(jsonPath("$[2].marker").value("Artikel 3"))
      .andExpect(jsonPath("$[2].heading").value("Änderung des Soldatengesetzes"))
      .andExpect(jsonPath("$[2].type").value("article"))
      .andExpect(jsonPath("$[2].children").isEmpty())
      .andExpect(jsonPath("$[2].hasEingebundeneStammform").value("false"))
      .andExpect(jsonPath("$[3].marker").value("Artikel 4"))
      .andExpect(
        jsonPath("$[3].heading").value(
          "Änderung der Gleichstellungsbeauftragten-Wahlverordnung Soldatinnen"
        )
      )
      .andExpect(jsonPath("$[3].type").value("article"))
      .andExpect(jsonPath("$[3].children").isEmpty())
      .andExpect(jsonPath("$[3].hasEingebundeneStammform").value("false"))
      .andExpect(jsonPath("$[4].marker").value("Artikel 5"))
      .andExpect(jsonPath("$[4].heading").value("Änderung der Soldaten-Haushaltshilfen-Verordnung"))
      .andExpect(jsonPath("$[4].type").value("article"))
      .andExpect(jsonPath("$[4].children").isEmpty())
      .andExpect(jsonPath("$[4].hasEingebundeneStammform").value("false"))
      .andExpect(jsonPath("$[5].marker").value("Artikel 6"))
      .andExpect(jsonPath("$[5].heading").value("Änderung des Beamtenversorgungsgesetzes"))
      .andExpect(jsonPath("$[5].type").value("article"))
      .andExpect(jsonPath("$[5].children").isEmpty())
      .andExpect(jsonPath("$[5].hasEingebundeneStammform").value("false"))
      .andExpect(jsonPath("$[6].marker").value("Artikel 7"))
      .andExpect(jsonPath("$[6].heading").value("Änderung des Soldatenversorgungsgesetzes"))
      .andExpect(jsonPath("$[6].type").value("article"))
      .andExpect(jsonPath("$[6].children").isEmpty())
      .andExpect(jsonPath("$[6].hasEingebundeneStammform").value("false"))
      .andExpect(jsonPath("$[7].marker").value("Artikel 8"))
      .andExpect(jsonPath("$[7].heading").value("Änderung des Soldatenversorgungsgesetzes"))
      .andExpect(jsonPath("$[7].type").value("article"))
      .andExpect(jsonPath("$[7].children").isEmpty())
      .andExpect(jsonPath("$[7].hasEingebundeneStammform").value("false"))
      .andExpect(jsonPath("$[8].marker").value("Artikel 9"))
      .andExpect(jsonPath("$[8].heading").value("Änderung des Unterhaltssicherungsgesetzes"))
      .andExpect(jsonPath("$[8].type").value("article"))
      .andExpect(jsonPath("$[8].children").isEmpty())
      .andExpect(jsonPath("$[8].hasEingebundeneStammform").value("false"))
      .andExpect(jsonPath("$[9].marker").value("Artikel 10"))
      .andExpect(jsonPath("$[9].heading").value("Inkrafttreten, Außerkrafttreten"))
      .andExpect(jsonPath("$[9].type").value("article"))
      .andExpect(jsonPath("$[9].children").isEmpty())
      .andExpect(jsonPath("$[9].hasEingebundeneStammform").value("false"));
  }
}
