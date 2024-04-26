package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.application.port.input.TransformLegalDocMlToHtmlUseCase;
import de.bund.digitalservice.ris.norms.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Not using SpringBootTest annotation to avoid needing a database connection. Therefore, manually
 * setting up the {@code mockMvc} including the ControllerAdvice
 */
@WebMvcTest(RenderingController.class)
@Import(SecurityConfig.class)
class RenderingControllerTest {
  @Autowired private MockMvc mockMvc;
  @MockBean private TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase;

  @Test
  void getHtmlPreviewWithShowMetadataTrue() throws Exception {
    String xml = "<law></law>";
    String html = "<html></html>";
    boolean showMetadata = true;

    when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(
            new TransformLegalDocMlToHtmlUseCase.Query(xml, showMetadata)))
        .thenReturn(html);

    mockMvc
        .perform(
            post("/api/v1/renderings")
                .queryParam("showMetadata", String.valueOf(showMetadata))
                .contentType(MediaType.APPLICATION_XML)
                .content(xml))
        .andExpect(status().isOk())
        .andExpect(content().string(html));

    verify(transformLegalDocMlToHtmlUseCase, times(1))
        .transformLegalDocMlToHtml(new TransformLegalDocMlToHtmlUseCase.Query(xml, true));
  }

  @Test
  void getHtmlPreviewWithShowMetadataFalse() throws Exception {
    String xml = "<law></law>";
    String html = "<html></html>";
    boolean showMetadata = false;

    when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(
            new TransformLegalDocMlToHtmlUseCase.Query(xml, showMetadata)))
        .thenReturn(html);

    mockMvc
        .perform(
            post("/api/v1/renderings")
                .queryParam("showMetadata", String.valueOf(showMetadata))
                .contentType(MediaType.APPLICATION_XML)
                .content(xml))
        .andExpect(status().isOk())
        .andExpect(content().string(html));

    verify(transformLegalDocMlToHtmlUseCase, times(1))
        .transformLegalDocMlToHtml(new TransformLegalDocMlToHtmlUseCase.Query(xml, false));
  }
}
