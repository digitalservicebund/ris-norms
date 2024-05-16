package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.config.SecurityConfig;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Not using SpringBootTest annotation to avoid needing a database connection. Using @Import to load
 * the {@link SecurityConfig} in order to avoid http 401 Unauthorised
 */
@WebMvcTest(ElementsController.class)
@Import(SecurityConfig.class)
class ElementsControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private LoadNormUseCase loadNormUseCase;

  @Test
  void itReturnsPrefacePreambleArticleAndConclusionDataInElementsResponseEntrySchema()
      throws Exception {
    // given
    var norm = NormFixtures.loadFromDisk("NormWithPrefacePreambleAndConclusions.xml");
    when(loadNormUseCase.loadNorm(any())).thenReturn(Optional.of(norm));

    var url =
        "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements"
            + "?type=preface"
            + "&type=preamble"
            + "&type=article"
            + "&type=conclusions";

    // when
    mockMvc
        .perform(get(url))
        // then
        .andExpect(status().isOk())
        // preface
        .andExpect(jsonPath("$[0]").exists())
        .andExpect(jsonPath("$[0].title").value("Dokumentenkopf"))
        .andExpect(jsonPath("$[0].eid").value("einleitung-1"))
        .andExpect(jsonPath("$[0].type").value("preface"))
        // preamble
        .andExpect(jsonPath("$[1].title").value("Eingangsformel"))
        .andExpect(jsonPath("$[1].eid").value("preambel-1"))
        .andExpect(jsonPath("$[1].type").value("preamble"))
        // articles
        .andExpect(jsonPath("$[2].title").value("Artikel 1 Änderung des Vereinsgesetzes"))
        .andExpect(jsonPath("$[2].eid").value("hauptteil-1_art-1"))
        .andExpect(jsonPath("$[2].type").value("article"))
        .andExpect(jsonPath("$[3].title").value("Artikel 3 Inkrafttreten"))
        .andExpect(jsonPath("$[3].eid").value("hauptteil-1_art-3"))
        .andExpect(jsonPath("$[3].type").value("article"))
        // conclusion
        .andExpect(jsonPath("$[4].title").value("Schlussteil"))
        .andExpect(jsonPath("$[4].eid").value("schluss-1"))
        .andExpect(jsonPath("$[4].type").value("conclusions"));
  }
}
