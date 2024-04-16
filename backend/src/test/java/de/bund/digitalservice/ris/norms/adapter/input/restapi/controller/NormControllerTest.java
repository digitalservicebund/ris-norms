package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.port.input.LoadNormXmlUseCase;
import de.bund.digitalservice.ris.norms.config.SecurityConfig;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
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
@WebMvcTest(NormController.class)
@Import(SecurityConfig.class)
class NormControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private LoadNormXmlUseCase loadNormXmlUseCase;

  @Nested
  class getNormXml {

    @Test
    void itCallsLoadNormXmlAndReturnsNormXml() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String xml = "<target></target>";

      // When
      when(loadNormXmlUseCase.loadNormXml(any())).thenReturn(Optional.of(xml));

      // When // Then
      mockMvc
          .perform(get("/api/v1/norms/{eli}", eli).accept(MediaType.APPLICATION_XML))
          .andExpect(status().isOk())
          .andExpect(content().string(xml));
    }
  }
}
