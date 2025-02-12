package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import de.bund.digitalservice.ris.norms.application.port.input.TransformLegalDocMlToHtmlUseCase;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Not using SpringBootTest annotation to avoid needing a database connection. Therefore, manually
 * setting up the {@code mockMvc} including the ControllerAdvice
 */
@WithMockUser
@WebMvcTest(
  controllers = RenderingController.class,
  excludeAutoConfiguration = OAuth2ClientAutoConfiguration.class
)
class RenderingControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase;

  @Nested
  class getHtmlPreview {

    @Test
    void itThrowsXmlProcessingException() throws Exception {
      when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(any()))
        .thenThrow(new XmlProcessingException("Error message", null));

      mockMvc
        .perform(
          post("/api/v1/renderings")
            .accept(MediaType.TEXT_HTML)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              """

                                    {
                "regelungstext": "<law>original-law</law>"
              }
              """
            )
        )
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("type").value("/errors/xml-processing-error"))
        .andExpect(jsonPath("title").value("XML processing error"))
        .andExpect(jsonPath("status").value(500))
        .andExpect(jsonPath("detail").value("Error message"))
        .andExpect(jsonPath("instance").value("/api/v1/renderings"));
    }

    @Test
    void getHtmlPreviewWithShowMetadataTrue() throws Exception {
      when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(any()))
        .thenReturn("<html></html>");

      mockMvc
        .perform(
          post("/api/v1/renderings")
            .queryParam("showMetadata", "true")
            .accept(MediaType.TEXT_HTML)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              """

                                    {
                "regelungstext": "<law>original-law</law>"
              }
              """
            )
        )
        .andExpect(status().isOk())
        .andExpect(content().string("<html></html>"));

      verify(transformLegalDocMlToHtmlUseCase, times(1))
        .transformLegalDocMlToHtml(
          new TransformLegalDocMlToHtmlUseCase.Query("<law>original-law</law>", true, false)
        );
    }

    @Test
    void getHtmlPreviewWithShowMetadataFalse() throws Exception {
      when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(any()))
        .thenReturn("<html></html>");

      mockMvc
        .perform(
          post("/api/v1/renderings")
            .queryParam("showMetadata", "false")
            .accept(MediaType.TEXT_HTML)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              """
              {
                "regelungstext": "<law>original-law</law>"
              }
              """
            )
        )
        .andExpect(status().isOk())
        .andExpect(content().string("<html></html>"));

      verify(transformLegalDocMlToHtmlUseCase, times(1))
        .transformLegalDocMlToHtml(
          new TransformLegalDocMlToHtmlUseCase.Query("<law>original-law</law>", false, false)
        );
    }

    @Test
    void getHtmlPreviewWithSnippetTrue() throws Exception {
      when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(any()))
        .thenReturn("<html></html>");
      mockMvc
        .perform(
          post("/api/v1/renderings")
            .queryParam("snippet", "true")
            .accept(MediaType.TEXT_HTML)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              """
              {
                 "regelungstext": "<law>original-law</law>"
               }
               """
            )
        )
        .andExpect(status().isOk())
        .andExpect(content().string("<html></html>"));

      verify(transformLegalDocMlToHtmlUseCase, times(1))
        .transformLegalDocMlToHtml(
          new TransformLegalDocMlToHtmlUseCase.Query("<law>original-law</law>", false, true)
        );
    }
  }
}
