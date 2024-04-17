package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.port.input.LoadNormXmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.TransformLegalDocMlToHtmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateNormXmlUseCase;
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
  @MockBean private UpdateNormXmlUseCase updateNormXmlUseCase;
  @MockBean private TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase;

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

  @Nested
  class getNormRender {

    @Test
    void itCallsNormServiceAndReturnsNormRender() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String xml = "<akn:doc></akn:doc>";
      final String html = "<div></div>";

      when(loadNormXmlUseCase.loadNormXml(any())).thenReturn(Optional.of(xml));
      when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(any())).thenReturn(html);

      // When // Then
      mockMvc
          .perform(get("/api/v1/norms/{eli}", eli).accept(MediaType.TEXT_HTML))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
          .andExpect(content().string(html));

      verify(transformLegalDocMlToHtmlUseCase, times(1))
          .transformLegalDocMlToHtml(argThat(query -> query.xml().equals(xml)));
    }
  }

  @Nested
  class updateNormRender {

    @Test
    void itCallsNormServiceAndUpdatesNorm() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String xml = "<akn:doc>new</akn:doc>";

      when(updateNormXmlUseCase.updateNormXml(any())).thenReturn(Optional.of(xml));

      // When // Then
      mockMvc
          .perform(
              put("/api/v1/norms/{eli}", eli)
                  .accept(MediaType.APPLICATION_XML)
                  .contentType(MediaType.APPLICATION_XML)
                  .content(xml))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
          .andExpect(content().string(xml));

      verify(updateNormXmlUseCase, times(1))
          .updateNormXml(argThat(query -> query.xml().equals(xml)));
    }

    @Test
    void itCallsNormServiceAndReturnsErrorMessage() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String xml = "<akn:doc>new</akn:doc>";

      when(updateNormXmlUseCase.updateNormXml(any()))
          .thenThrow(new UpdateNormXmlUseCase.InvalidUpdateException("Error Message"));

      // When // Then
      mockMvc
          .perform(
              put("/api/v1/norms/{eli}", eli)
                  .accept(MediaType.APPLICATION_XML)
                  .contentType(MediaType.APPLICATION_XML)
                  .content(xml))
          .andExpect(status().isBadRequest())
          .andExpect(content().string("Error Message"));

      verify(updateNormXmlUseCase, times(1))
          .updateNormXml(argThat(query -> query.xml().equals(xml)));
    }

    @Test
    void itCallsNormServiceAndReturnsNotFound() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String xml = "<akn:doc>new</akn:doc>";

      when(updateNormXmlUseCase.updateNormXml(any())).thenReturn(Optional.empty());

      // When // Then
      mockMvc
          .perform(
              put("/api/v1/norms/{eli}", eli)
                  .accept(MediaType.APPLICATION_XML)
                  .contentType(MediaType.APPLICATION_XML)
                  .content(xml))
          .andExpect(status().isNotFound());

      verify(updateNormXmlUseCase, times(1))
          .updateNormXml(argThat(query -> query.xml().equals(xml)));
    }
  }
}
