package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.port.input.LoadRegelungstextXmlUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SecurelessControllerTest(NormManifestationController.class)
public class NormManifestationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private LoadRegelungstextXmlUseCase loadRegelungstextXmlUseCase;

  @Nested
  class getNormManifestationXml {

    @Test
    void itReturnsNorm() throws Exception {
      // Given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml";

      var xml = Fixtures.loadTextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );

      // When
      when(loadRegelungstextXmlUseCase.loadRegelungstextXml(any())).thenReturn(xml);

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms/{manifestationEli}", eli).accept(MediaType.APPLICATION_XML))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML));

      verify(loadRegelungstextXmlUseCase, times(1))
        .loadRegelungstextXml(
          new LoadRegelungstextXmlUseCase.Query(DokumentManifestationEli.fromString(eli))
        );
    }
  }
}
