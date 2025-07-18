package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SecurelessControllerTest(NormExpressionController.class)
class NormExpressionControllerTest {

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
  class getNorms {

    @Test
    void itReturnsNorm() throws Exception {
      // Given
      var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");

      // When
      when(loadNormUseCase.loadNorm(any())).thenReturn(norm);

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu").accept(
            MediaType.APPLICATION_JSON
          )
        )
        .andExpect(status().isOk())
        .andExpect(
          jsonPath("eli").value(
            equalTo("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1")
          )
        )
        .andExpect(
          jsonPath("title").value(equalTo("Gesetz zur Regelung des öffentlichen Vereinsrechts"))
        )
        .andExpect(jsonPath("frbrNumber").value(equalTo("s593")))
        .andExpect(jsonPath("frbrName").value(equalTo("BGBl. I")))
        .andExpect(jsonPath("frbrDateVerkuendung").value(equalTo("1964-08-05")));

      verify(loadNormUseCase, times(1)).loadNorm(
        new LoadNormUseCase.EliOptions(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );
    }

    @Test
    void itReturnsErrorResponse() throws Exception {
      // Given

      // When
      when(loadNormUseCase.loadNorm(any())).thenThrow(
        new NormNotFoundException(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu").accept(
            MediaType.APPLICATION_JSON
          )
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value(equalTo("/errors/norm-not-found")))
        .andExpect(jsonPath("title").value(equalTo("Norm not found")))
        .andExpect(jsonPath("status").value(equalTo(404)))
        .andExpect(
          jsonPath("detail").value(
            equalTo("Norm with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu does not exist")
          )
        )
        .andExpect(
          jsonPath("instance").value(
            equalTo("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
          )
        )
        .andExpect(jsonPath("eli").value(equalTo("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")));
    }
  }

  @Nested
  class getNormRender {

    @Test
    void itCallsNormServiceAndReturnsNormRender() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu";
      final String xml = "<akn:doc></akn:doc>";
      final String html = "<div></div>";

      when(loadRegelungstextXmlUseCase.loadRegelungstextXml(any())).thenReturn(xml);
      when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(any())).thenReturn(html);

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms/{eli}", eli).accept(MediaType.TEXT_HTML))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
        .andExpect(content().string(html));

      verify(transformLegalDocMlToHtmlUseCase, times(1)).transformLegalDocMlToHtml(
        argThat(query -> query.xml().equals(xml) && !query.showMetadata())
      );
    }

    @Test
    void itCallsNormServiceAndReturnsNormRenderWithMetadata() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu";
      final String xml = "<akn:doc></akn:doc>";
      final String html = "<div></div>";

      when(loadRegelungstextXmlUseCase.loadRegelungstextXml(any())).thenReturn(xml);
      when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(any())).thenReturn(html);

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms/{eli}?showMetadata=true", eli).accept(MediaType.TEXT_HTML))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
        .andExpect(content().string(html));

      verify(transformLegalDocMlToHtmlUseCase, times(1)).transformLegalDocMlToHtml(
        argThat(query -> query.xml().equals(xml) && query.showMetadata())
      );
    }
  }

  @Nested
  class checkIfExceptionHandlerWorks {

    @Test
    void itReturnsErrorResponseOnNoSuchElementException() throws Exception {
      // Given

      // When
      when(loadNormUseCase.loadNorm(any())).thenThrow(new NoSuchElementException("ValueNotFound"));

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu").accept(
            MediaType.APPLICATION_JSON
          )
        )
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type").value(equalTo("/errors/necessary-value-missing")))
        .andExpect(jsonPath("title").value(equalTo("Unprocessable Entity")))
        .andExpect(jsonPath("status").value(equalTo(422)))
        .andExpect(jsonPath("detail").value(equalTo("ValueNotFound")))
        .andExpect(
          jsonPath("instance").value(
            equalTo("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
          )
        );
    }
  }
}
