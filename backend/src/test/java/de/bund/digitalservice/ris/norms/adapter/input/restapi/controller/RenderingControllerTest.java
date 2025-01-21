package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import de.bund.digitalservice.ris.norms.application.port.input.ApplyPassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.TransformLegalDocMlToHtmlUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
import java.time.Instant;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalMatchers;
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

  @MockitoBean
  private ApplyPassiveModificationsUseCase applyPassiveModificationsUseCase;

  @Nested
  class getHtmlPreview {

    @Test
    void itThrowsXmlProcessingException() throws Exception {
      when(applyPassiveModificationsUseCase.applyPassiveModifications(any()))
        .thenReturn(
          new Regelungstext(XmlMapper.toDocument("<law>applied-passive-modification</law>"))
        );
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
      when(applyPassiveModificationsUseCase.applyPassiveModifications(any()))
        .thenReturn(
          new Regelungstext(XmlMapper.toDocument("<law>applied-passive-modification</law>"))
        );
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

      verify(applyPassiveModificationsUseCase, times(1))
        .applyPassiveModifications(
          argThat(query ->
            query
              .regelungstext()
              .getDocument()
              .getFirstChild()
              .getTextContent()
              .equals("original-law")
          )
        );
      verify(transformLegalDocMlToHtmlUseCase, times(1))
        .transformLegalDocMlToHtml(
          new TransformLegalDocMlToHtmlUseCase.Query(
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><law>applied-passive-modification</law>",
            true,
            false
          )
        );
    }

    @Test
    void getHtmlPreviewWithShowMetadataFalse() throws Exception {
      when(applyPassiveModificationsUseCase.applyPassiveModifications(any()))
        .thenReturn(
          new Regelungstext(XmlMapper.toDocument("<law>applied-passive-modification</law>"))
        );
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

      verify(applyPassiveModificationsUseCase, times(1))
        .applyPassiveModifications(
          argThat(query ->
            query
              .regelungstext()
              .getDocument()
              .getFirstChild()
              .getTextContent()
              .equals("original-law")
          )
        );
      verify(transformLegalDocMlToHtmlUseCase, times(1))
        .transformLegalDocMlToHtml(
          new TransformLegalDocMlToHtmlUseCase.Query(
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><law>applied-passive-modification</law>",
            false,
            false
          )
        );
    }

    @Test
    void getHtmlPreviewForDate() throws Exception {
      when(applyPassiveModificationsUseCase.applyPassiveModifications(any()))
        .thenReturn(
          new Regelungstext(XmlMapper.toDocument("<law>applied-passive-modification</law>"))
        );
      when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(any()))
        .thenReturn("<html></html>");

      mockMvc
        .perform(
          post("/api/v1/renderings")
            .queryParam("showMetadata", "true")
            .queryParam("atIsoDate", "2024-01-01T00:00:00.0Z")
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
          new TransformLegalDocMlToHtmlUseCase.Query(
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><law>applied-passive-modification</law>",
            true,
            false
          )
        );
      verify(applyPassiveModificationsUseCase, times(1))
        .applyPassiveModifications(
          AdditionalMatchers.and(
            argThat(query -> query.date().equals(Instant.parse("2024-01-01T00:00:00.0Z"))),
            AdditionalMatchers.and(
              argThat(query -> query.customRegelungstexte().isEmpty()),
              argThat(query ->
                query
                  .regelungstext()
                  .getDocument()
                  .getFirstChild()
                  .getTextContent()
                  .equals("original-law")
              )
            )
          )
        );
    }

    @Test
    void getHtmlPreviewForCurrentDate() throws Exception {
      when(applyPassiveModificationsUseCase.applyPassiveModifications(any()))
        .thenReturn(
          new Regelungstext(XmlMapper.toDocument("<law>applied-passive-modification</law>"))
        );
      when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(any()))
        .thenReturn("<html></html>");

      Instant instantBefore = Instant.now();

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
        .andExpect(status().isOk())
        .andExpect(content().string("<html></html>"));

      verify(applyPassiveModificationsUseCase, times(1))
        .applyPassiveModifications(
          argThat(query ->
            query.date().isAfter(instantBefore) && query.date().isBefore(Instant.now())
          )
        );
    }

    @Test
    void getHtmlPreviewForDateWithCustomRegelungstexte() throws Exception {
      when(applyPassiveModificationsUseCase.applyPassiveModifications(any()))
        .thenReturn(
          new Regelungstext(XmlMapper.toDocument("<law>applied-passive-modification</law>"))
        );
      when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(any()))
        .thenReturn("<html></html>");

      mockMvc
        .perform(
          post("/api/v1/renderings")
            .queryParam("showMetadata", "false")
            .queryParam("atIsoDate", "2024-01-01T00:00:00.0Z")
            .accept(MediaType.TEXT_HTML)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              """
              {
                "regelungstext": "<law>original-law</law>",
                "customRegelungstexte": ["<law>amending-law</law>"]
              }
              """
            )
        )
        .andExpect(status().isOk())
        .andExpect(content().string("<html></html>"));

      verify(applyPassiveModificationsUseCase, times(1))
        .applyPassiveModifications(
          argThat(query ->
            query
              .customRegelungstexte()
              .iterator()
              .next()
              .getDocument()
              .getFirstChild()
              .getTextContent()
              .equals("amending-law")
          )
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

      verify(applyPassiveModificationsUseCase, times(0)).applyPassiveModifications(any());
      verify(transformLegalDocMlToHtmlUseCase, times(1))
        .transformLegalDocMlToHtml(
          new TransformLegalDocMlToHtmlUseCase.Query("<law>original-law</law>", false, true)
        );
    }
  }

  @Nested
  class getXmlPreview {

    @Test
    void getXmlPreviewForCurrentDate() throws Exception {
      when(applyPassiveModificationsUseCase.applyPassiveModifications(any()))
        .thenReturn(
          new Regelungstext(XmlMapper.toDocument("<law>applied-passive-modification</law>"))
        );

      Instant instantBefore = Instant.now();

      mockMvc
        .perform(
          post("/api/v1/renderings")
            .accept(MediaType.APPLICATION_XML_VALUE)
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
        .andExpect(
          content()
            .string(
              "<?xml version=\"1.0\" encoding=\"UTF-8\"?><law>applied-passive-modification</law>"
            )
        );

      verify(applyPassiveModificationsUseCase, times(1))
        .applyPassiveModifications(
          AdditionalMatchers.and(
            argThat(query ->
              query.date().isAfter(instantBefore) && query.date().isBefore(Instant.now())
            ),
            AdditionalMatchers.and(
              argThat(query -> query.customRegelungstexte().isEmpty()),
              argThat(query ->
                query
                  .regelungstext()
                  .getDocument()
                  .getFirstChild()
                  .getTextContent()
                  .equals("original-law")
              )
            )
          )
        );
    }

    @Test
    void getXmlPreviewForDate() throws Exception {
      when(applyPassiveModificationsUseCase.applyPassiveModifications(any()))
        .thenReturn(
          new Regelungstext(XmlMapper.toDocument("<law>applied-passive-modification</law>"))
        );

      mockMvc
        .perform(
          post("/api/v1/renderings")
            .queryParam("atIsoDate", "2024-01-01T00:00:00.0Z")
            .accept(MediaType.APPLICATION_XML_VALUE)
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
        .andExpect(
          content()
            .string(
              "<?xml version=\"1.0\" encoding=\"UTF-8\"?><law>applied-passive-modification</law>"
            )
        );

      verify(applyPassiveModificationsUseCase, times(1))
        .applyPassiveModifications(
          AdditionalMatchers.and(
            argThat(query -> query.date().equals(Instant.parse("2024-01-01T00:00:00.0Z"))),
            AdditionalMatchers.and(
              argThat(query -> query.customRegelungstexte().isEmpty()),
              argThat(query ->
                query
                  .regelungstext()
                  .getDocument()
                  .getFirstChild()
                  .getTextContent()
                  .equals("original-law")
              )
            )
          )
        );
    }

    @Test
    void getXmlPreviewForDateWithCustomRegelungstexte() throws Exception {
      when(applyPassiveModificationsUseCase.applyPassiveModifications(any()))
        .thenReturn(
          new Regelungstext(XmlMapper.toDocument("<law>applied-passive-modification</law>"))
        );

      mockMvc
        .perform(
          post("/api/v1/renderings")
            .queryParam("atIsoDate", "2024-01-01T00:00:00.0Z")
            .accept(MediaType.APPLICATION_XML_VALUE)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              """
                  {
                    "regelungstext": "<law>original-law</law>",
                    "customRegelungstexte": ["<law>amending-law</law>"]
                  }
              """
            )
        )
        .andExpect(status().isOk())
        .andExpect(
          content()
            .string(
              "<?xml version=\"1.0\" encoding=\"UTF-8\"?><law>applied-passive-modification</law>"
            )
        );

      verify(applyPassiveModificationsUseCase, times(1))
        .applyPassiveModifications(
          argThat(query ->
            query
              .customRegelungstexte()
              .iterator()
              .next()
              .getDocument()
              .getFirstChild()
              .getTextContent()
              .equals("amending-law")
          )
        );
    }
  }
}
