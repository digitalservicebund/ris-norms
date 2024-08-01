package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.config.SecurityConfig;
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
@WebMvcTest(ReferenceController.class)
@Import(SecurityConfig.class)
class ReferenceControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ReferenceRecognitionUseCase referenceRecognitionUseCase;

  @Test
  void itReturnsXml() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
    final String xml = "<target></target>";
    // When
    when(referenceRecognitionUseCase.findAndCreateReferences(any())).thenReturn(xml);

    // When // Then
    mockMvc
        .perform(post("/api/v1/references/{eli}", eli).accept(MediaType.APPLICATION_XML))
        .andExpect(status().isOk())
        .andExpect(content().string(xml));
  }

  @Test
  void itThrowsNormNotFoundException() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
    // When
    when(referenceRecognitionUseCase.findAndCreateReferences(any()))
        .thenThrow(new NormNotFoundException("Norm not found"));

    // When // Then
    mockMvc
        .perform(post("/api/v1/references/{eli}", eli).accept(MediaType.APPLICATION_XML))
        .andExpect(status().isNotFound());
  }
}
