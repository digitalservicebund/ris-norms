package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.exceptions.InternalErrorExceptionHandler;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetLawUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetLawXmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.TimeMachineUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.TransformLegalDocMlToHtmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateTargetLawUseCase;
import de.bund.digitalservice.ris.norms.application.service.exceptions.XmlProcessingException;
import de.bund.digitalservice.ris.norms.config.SecurityConfig;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import de.bund.digitalservice.ris.norms.helper.MemoryAppender;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
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
@WebMvcTest(TargetLawController.class)
@Import(SecurityConfig.class)
class TargetLawControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private LoadTargetLawUseCase loadTargetLawUseCase;
  @MockBean private LoadTargetLawXmlUseCase loadTargetLawXmlUseCase;
  @MockBean private TimeMachineUseCase timeMachineUseCase;
  @MockBean private UpdateTargetLawUseCase updateTargetLawUseCase;
  @MockBean private TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase;

  @Nested
  class GetTargetLaw {
    @Test
    void itCallsloadTargetLawWithExpressionEliFromQuery() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      when(loadTargetLawUseCase.loadTargetLaw(any())).thenReturn(Optional.empty());

      // When
      mockMvc.perform(get("/api/v1/target-laws/{eli}", eli));

      // Then
      verify(loadTargetLawUseCase, times(1))
          .loadTargetLaw(argThat(query -> query.eli().equals(eli)));
    }

    @Test
    void itCallsTargetServiceAndReturnsTargetLaw() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String title = "Title vom Gesetz";
      final String xml = "<target></target>";

      // When
      final TargetLaw targetLaw = TargetLaw.builder().eli(eli).title(title).xml(xml).build();

      when(loadTargetLawUseCase.loadTargetLaw(any())).thenReturn(Optional.of(targetLaw));

      // When // Then
      mockMvc
          .perform(get("/api/v1/target-laws/{eli}", eli))
          .andExpect(status().isOk())
          .andExpect(jsonPath("eli").value(equalTo(eli)))
          .andExpect(jsonPath("title").value(equalTo(title)))
          .andExpect(jsonPath("xml").doesNotExist());
    }

    @Test
    void itCallsTargetLawServiceAndReturnsNotFound() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      when(loadTargetLawUseCase.loadTargetLaw(any())).thenReturn(Optional.empty());

      // When // Then
      mockMvc.perform(get("/api/v1/target-laws/{eli}", eli)).andExpect(status().isNotFound());
    }

    @Test
    void itCallsloadAmendingLawAndReturnsInternalError() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      when(loadTargetLawUseCase.loadTargetLaw(any()))
          .thenThrow(new RuntimeException("simulating internal server error"));

      // When // Then
      mockMvc.perform(get("/api/v1/target-laws/{eli}", eli)).andExpect(status().is5xxServerError());
    }
  }

  @Nested
  class GetTargetLawXml {
    @Test
    void itCallsTargetServiceAndReturnsTargetLawXml() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String xml = "<target></target>";

      when(loadTargetLawXmlUseCase.loadTargetLawXml(any())).thenReturn(Optional.of(xml));

      // When // Then
      mockMvc
          .perform(get("/api/v1/target-laws/{eli}", eli).accept(MediaType.APPLICATION_XML))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
          .andExpect(content().string(xml));
    }
  }

  @Nested
  class GetTargetLawHtml {
    @Test
    void itCallsTargetServiceAndReturnsTargetLawXml() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String xml = "<target></target>";
      final String html = "<div></div>";

      when(loadTargetLawXmlUseCase.loadTargetLawXml(any())).thenReturn(Optional.of(xml));
      when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(any())).thenReturn(html);

      // When // Then
      mockMvc
          .perform(get("/api/v1/target-laws/{eli}", eli).accept(MediaType.TEXT_HTML))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
          .andExpect(content().string(html));

      verify(transformLegalDocMlToHtmlUseCase, times(1))
          .transformLegalDocMlToHtml(argThat(query -> query.xml().equals(xml)));
    }

    @Test
    void itReturnsXmlTransformationExceptions() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String xml = "<target";

      when(loadTargetLawXmlUseCase.loadTargetLawXml(any())).thenReturn(Optional.of(xml));
      when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(any()))
          .thenThrow(new XmlProcessingException("XML Exception", new Exception()));

      // When // Then
      mockMvc
          .perform(get("/api/v1/target-laws/{eli}", eli).accept(MediaType.TEXT_HTML))
          .andExpect(status().is5xxServerError());
    }
  }

  @Nested
  class GetPreview {
    @Test
    void itReturnsInternalServerErrorWhenThereIsNoAmendingLaw() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";

      // When // Then
      mockMvc
          .perform(
              post("/api/v1/target-laws/{eli}/preview", eli)
                  .contentType(MediaType.APPLICATION_XML)
                  .accept(MediaType.APPLICATION_XML))
          .andExpect(status().isBadRequest());
    }

    @Test
    void itCallsPreviewAndTimeMachineServiceIsCalled() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String xml = "<target></target>";
      final String amendingLaw =
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?><amendingLaw><akn:mod>old</akn:mod></amendingLaw>";

      when(timeMachineUseCase.applyTimeMachine(any())).thenReturn(Optional.of(xml));

      // When // Then
      mockMvc
          .perform(
              post("/api/v1/target-laws/{eli}/preview", eli)
                  .content(amendingLaw)
                  .contentType(MediaType.APPLICATION_XML)
                  .accept(MediaType.APPLICATION_XML))
          .andExpect(status().isOk());

      verify(timeMachineUseCase, times(1))
          .applyTimeMachine(
              argThat(
                  query ->
                      query.amendingLawXml().equals(amendingLaw)
                          && query.targetLawEli().equals(eli)));
    }

    @Test
    void returnsHttps500OnModificationException() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String amendingLaw =
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?><amendingLaw><akn:mod>old</akn:mod></amendingLaw>";

      when(timeMachineUseCase.applyTimeMachine(any())).thenThrow(XmlProcessingException.class);

      // When // Then
      mockMvc
          .perform(
              post("/api/v1/target-laws/{eli}/preview", eli)
                  .content(amendingLaw)
                  .contentType(MediaType.APPLICATION_XML)
                  .accept(MediaType.APPLICATION_XML))
          .andExpect(status().is5xxServerError());
    }

    @Test
    void expectStacktraceWhenParsingExceptionOccurs() throws Exception {
      // Given
      MemoryAppender memoryAppender;

      Logger logger = (Logger) LoggerFactory.getLogger(InternalErrorExceptionHandler.class);
      memoryAppender = new MemoryAppender();
      memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
      logger.setLevel(Level.ALL);
      logger.addAppender(memoryAppender);
      memoryAppender.start();

      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String amendingLaw =
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?><amendingLaw><akn:mod>old</akn:mod></amendingLaw>";

      when(timeMachineUseCase.applyTimeMachine(any()))
          .thenThrow(new XmlProcessingException("message with individual cause", new Exception()));

      // When // Then
      mockMvc
          .perform(
              post("/api/v1/target-laws/{eli}/preview", eli)
                  .content(amendingLaw)
                  .contentType(MediaType.APPLICATION_XML))
          .andExpect(status().is5xxServerError())
          .andExpect(
              result ->
                  assertThat(memoryAppender.contains("message with individual cause", Level.ERROR))
                      .isTrue());
    }
  }

  @Nested
  class GetHtmlPreview {
    @Test
    void itReturnsInternalServerErrorWhenThereIsNoAmendingLaw() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";

      // When // Then
      mockMvc
          .perform(
              post("/api/v1/target-laws/{eli}/preview", eli)
                  .contentType(MediaType.APPLICATION_XML)
                  .accept(MediaType.TEXT_HTML))
          .andExpect(status().isBadRequest());
    }

    @Test
    void itCallsPreviewAndTimeMachineUseCaseAndTransformLegalDocMlToHtmlUseCaseAreCalled()
        throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String xml = "<target></target>";
      final String html = "<div></div>";
      final String amendingLaw =
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?><amendingLaw><akn:mod>old</akn:mod></amendingLaw>";

      when(timeMachineUseCase.applyTimeMachine(any())).thenReturn(Optional.of(xml));
      when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(any())).thenReturn(html);

      // When // Then
      mockMvc
          .perform(
              post("/api/v1/target-laws/{eli}/preview", eli)
                  .content(amendingLaw)
                  .contentType(MediaType.APPLICATION_XML)
                  .accept(MediaType.TEXT_HTML))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
          .andExpect(content().string(html));

      verify(timeMachineUseCase, times(1))
          .applyTimeMachine(
              argThat(
                  query ->
                      query.amendingLawXml().equals(amendingLaw)
                          && query.targetLawEli().equals(eli)));
      verify(transformLegalDocMlToHtmlUseCase, times(1))
          .transformLegalDocMlToHtml(argThat(query -> query.xml().equals(xml)));
    }
  }

  @Nested
  class UpdateTargetLaw {
    @Test
    void itSendsAnUpdatedTargetLawWhichIsSavedAndReturned() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String targetLawXml = "<target></target>";

      when(updateTargetLawUseCase.updateTargetLaw(any())).thenReturn(Optional.of(targetLawXml));

      // When // Then
      mockMvc
          .perform(
              put("/api/v1/target-laws/{eli}", eli)
                  .content(targetLawXml)
                  .contentType(MediaType.APPLICATION_XML))
          .andExpect(status().isOk());

      verify(updateTargetLawUseCase, times(1))
          .updateTargetLaw(
              argThat(
                  query ->
                      query.newTargetLawXml().equals(targetLawXml) && query.eli().equals(eli)));
    }

    @Test
    void itReturn404whenLawIsNotFound() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String targetLawXml = "<target></target>";

      when(updateTargetLawUseCase.updateTargetLaw(any())).thenReturn(Optional.empty());

      // When // Then
      mockMvc
          .perform(
              put("/api/v1/target-laws/{eli}", eli)
                  .content(targetLawXml)
                  .contentType(MediaType.APPLICATION_XML))
          .andExpect(status().isNotFound());

      verify(updateTargetLawUseCase, times(1))
          .updateTargetLaw(
              argThat(
                  query ->
                      query.newTargetLawXml().equals(targetLawXml) && query.eli().equals(eli)));
    }

    @Test
    void itReturnBadRequestWhenThereIsNoTargetLawXmlProvided() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";

      when(updateTargetLawUseCase.updateTargetLaw(any())).thenReturn(Optional.empty());

      // When // Then
      mockMvc
          .perform(put("/api/v1/target-laws/{eli}", eli).contentType(MediaType.APPLICATION_XML))
          .andExpect(status().isBadRequest());
    }
  }
}
