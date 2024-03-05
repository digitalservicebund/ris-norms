package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.exceptions.InternalErrorExceptionHandler;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetLawUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetLawXmlUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Not using SpringBootTest annotation to avoid needing a database connection. Therefore, manually
 * setting up the {@code mockMvc} including the ControllerAdvice
 */
@ExtendWith(SpringExtension.class)
class TargetLawControllerTest {

  private MockMvc mockMvc;

  @MockBean private LoadTargetLawUseCase loadTargetLawUseCase;
  @MockBean private LoadTargetLawXmlUseCase loadTargetLawXmlUseCase;

  @BeforeEach
  public void setUp() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(
                new TargetLawController(loadTargetLawUseCase, loadTargetLawXmlUseCase))
            .setControllerAdvice(new InternalErrorExceptionHandler())
            .build();
  }

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
}
