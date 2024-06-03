package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.service.ProprietaryService;
import de.bund.digitalservice.ris.norms.config.SecurityConfig;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Not using SpringBootTest annotation to avoid needing a database connection. Using @Import to load
 * the {@link SecurityConfig} in order to avoid http 401 Unauthorised
 */
@WebMvcTest(ProprietaryController.class)
@Import(SecurityConfig.class)
class ProprietaryControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ProprietaryService proprietaryService;

  @Test
  void returns404IfNormNotFound() throws Exception {
    // given
    var eli = "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1";
    when(proprietaryService.loadProprietaryFromNorm(new LoadProprietaryFromNormUseCase.Query(eli)))
        .thenThrow(new NormNotFoundException(eli));
    // when
    mockMvc
        .perform(
            get("/api/v1/norms/" + eli + "/proprietary").accept(MediaType.APPLICATION_JSON_VALUE))
        // then
        .andExpect(status().isNotFound());
  }

  @Test
  void returnsProprietaryResponseSchema() throws Exception {
    // given
    var eli = "eli/bund/bgbl-1/2002/s1181/2019-11-22/1/deu/rechtsetzungsdokument-1";
    var norm = NormFixtures.loadFromDisk("NormWithProprietary.xml");
    var proprietary = norm.getProprietary();
    when(proprietaryService.loadProprietaryFromNorm(new LoadProprietaryFromNormUseCase.Query(eli)))
        .thenReturn(proprietary);

    // when
    mockMvc
        .perform(
            get("/api/v1/norms/" + eli + "/proprietary").accept(MediaType.APPLICATION_JSON_VALUE))
        // then
        .andExpect(status().isOk());

    // response content (expect via jsonPath)
    // returns proprietary (all metadata tested for correct value)

    // returns fallback if not found
  }
}
