package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetLawUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetLawXmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.TimeMachineUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateTargetLawUseCase;
import de.bund.digitalservice.ris.norms.application.service.exceptions.ModificationException;
import de.bund.digitalservice.ris.norms.config.SecurityConfig;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import java.util.Optional;
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
@WebMvcTest(TargetLawController.class)
@Import(SecurityConfig.class)
class TargetLawControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private LoadTargetLawUseCase loadTargetLawUseCase;
  @MockBean private LoadTargetLawXmlUseCase loadTargetLawXmlUseCase;
  @MockBean private TimeMachineUseCase timeMachineUseCase;
  @MockBean private UpdateTargetLawUseCase updateTargetLawUseCase;

  @Test
  void itCallsloadTargetLawWithExpressionEliFromQuery() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
    when(loadTargetLawUseCase.loadTargetLaw(any())).thenReturn(Optional.empty());

    // When
    mockMvc.perform(get("/api/v1/target-laws/{eli}", eli));

    // Then
    verify(loadTargetLawUseCase, times(1)).loadTargetLaw(argThat(query -> query.eli().equals(eli)));
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
        .andExpect(content().string(xml));
  }

  @Test
  void itReturnsInternalServerErrorWhenThereIsNoAmendingLaw() throws Exception {
    // Given
    final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";

    // When // Then
    mockMvc
        .perform(
            post("/api/v1/target-laws/{eli}/preview", eli).contentType(MediaType.APPLICATION_XML))
        .andExpect(status().is5xxServerError());
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
                .contentType(MediaType.APPLICATION_XML))
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

    when(timeMachineUseCase.applyTimeMachine(any())).thenThrow(ModificationException.class);

    // When // Then
    mockMvc
        .perform(
            post("/api/v1/target-laws/{eli}/preview", eli)
                .content(amendingLaw)
                .contentType(MediaType.APPLICATION_XML))
        .andExpect(status().is5xxServerError());
  }

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
                query -> query.newTargetLawXml().equals(targetLawXml) && query.eli().equals(eli)));
  }
}
