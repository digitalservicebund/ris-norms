package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.exception.InvalidUpdateException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadRegelungstextUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadRegelungstextXmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.TransformLegalDocMlToHtmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateRegelungstextXmlUseCase;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SecurelessControllerTest(DokumentExpressionController.class)
class DokumentExpressionControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private LoadNormUseCase loadNormUseCase;

  @MockitoBean
  private LoadRegelungstextUseCase loadRegelungstextUseCase;

  @MockitoBean
  private LoadRegelungstextXmlUseCase loadRegelungstextXmlUseCase;

  @MockitoBean
  private UpdateRegelungstextXmlUseCase updateRegelungstextXmlUseCase;

  @MockitoBean
  private TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase;

  @Nested
  class getNormXml {

    @Test
    void itCallsLoadNormXmlAndReturnsNormXml() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-verkuendung-1";
      final String xml = "</target>";

      // When
      when(loadRegelungstextXmlUseCase.loadRegelungstextXml(any())).thenReturn(xml);

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms/{eli}", eli).accept(MediaType.APPLICATION_XML))
        .andExpect(status().isOk())
        .andExpect(content().string(xml));
    }
  }

  @Nested
  class updateNormRender {

    @Test
    void itCallsNormServiceAndUpdatesNorm() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-verkuendung-1";
      final String xml = "<akn:doc>new</akn:doc>";

      when(updateRegelungstextXmlUseCase.updateRegelungstextXml(any())).thenReturn(xml);

      // When // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}", eli)
            .accept(MediaType.APPLICATION_XML)
            .with(csrf())
            .contentType(MediaType.APPLICATION_XML)
            .content(xml)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
        .andExpect(content().string(xml));

      verify(updateRegelungstextXmlUseCase, times(1)).updateRegelungstextXml(
        argThat(query -> query.xml().equals(xml))
      );
    }

    @Test
    void itCallsNormServiceAndReturnsErrorMessage() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-verkuendung-1";
      final String xml = "<akn:doc>new</akn:doc>";

      when(updateRegelungstextXmlUseCase.updateRegelungstextXml(any())).thenThrow(
        new InvalidUpdateException("Error Message")
      );

      // When // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}", eli)
            .accept(MediaType.APPLICATION_XML)
            .with(csrf())
            .contentType(MediaType.APPLICATION_XML)
            .content(xml)
        )
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type").value("/errors/invalidate-update"))
        .andExpect(jsonPath("title").value("Invalid update operation"))
        .andExpect(jsonPath("status").value(422))
        .andExpect(jsonPath("detail").value("Error Message"))
        .andExpect(
          jsonPath("instance").value(
            "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-verkuendung-1"
          )
        );

      verify(updateRegelungstextXmlUseCase, times(1)).updateRegelungstextXml(
        argThat(query -> query.xml().equals(xml))
      );
    }

    @Test
    void itCallsNormServiceAndReturnsUnprocessableWhenNodeIsMissing() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-verkuendung-1";
      final String xml = "<akn:doc>new</akn:doc>";

      when(updateRegelungstextXmlUseCase.updateRegelungstextXml(any())).thenThrow(
        new MandatoryNodeNotFoundException("example-xpath", "example/eli")
      );

      // When
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}", eli)
            .accept(MediaType.APPLICATION_XML)
            .with(csrf())
            .contentType(MediaType.APPLICATION_XML)
            .content(xml)
        )
        .andExpect(status().isUnprocessableEntity());
    }
  }
}
