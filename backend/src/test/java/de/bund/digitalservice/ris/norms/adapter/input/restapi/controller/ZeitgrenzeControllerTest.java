package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.application.port.input.LoadZeitgrenzenUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateZeitgrenzenUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SecurelessControllerTest(ZeitgrenzenController.class)
class ZeitgrenzeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private LoadZeitgrenzenUseCase loadZeitgrenzenUseCase;

  @MockitoBean
  private UpdateZeitgrenzenUseCase updateZeitgrenzenUseCase;

  @Nested
  class getZeitgrenzen {

    @Test
    void getZeitgrenzenReturnsCorrectData() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";

      final List<Zeitgrenze> zeitgrenzen = List.of(
        Zeitgrenze
          .builder()
          .id(new Zeitgrenze.Id("gz-1"))
          .art(Zeitgrenze.Art.INKRAFT)
          .date(LocalDate.parse("2025-02-20"))
          .build(),
        Zeitgrenze
          .builder()
          .id(new Zeitgrenze.Id("gz-2"))
          .art(Zeitgrenze.Art.AUSSERKRAFT)
          .date(LocalDate.parse("2023-05-01"))
          .build()
      );

      when(loadZeitgrenzenUseCase.loadZeitgrenzenFromDokument(any())).thenReturn(zeitgrenzen);

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms/{eli}/zeitgrenzen", eli).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id", is("gz-1")))
        .andExpect(jsonPath("$[0].date", is("2025-02-20")))
        .andExpect(jsonPath("$[0].art", is("INKRAFT")))
        .andExpect(jsonPath("$[1].id", is("gz-2")))
        .andExpect(jsonPath("$[1].date", is("2023-05-01")))
        .andExpect(jsonPath("$[1].art", is("AUSSERKRAFT")));

      verify(loadZeitgrenzenUseCase, times(1))
        .loadZeitgrenzenFromDokument(any(LoadZeitgrenzenUseCase.Query.class));
    }

    @Test
    void getZeitgrenzenReturnsEmpty() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";

      when(loadZeitgrenzenUseCase.loadZeitgrenzenFromDokument(any()))
        .thenReturn(Collections.emptyList());

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms/{eli}/zeitgrenzen", eli).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", empty()));

      verify(loadZeitgrenzenUseCase, times(1))
        .loadZeitgrenzenFromDokument(any(LoadZeitgrenzenUseCase.Query.class));
    }
  }

  @Nested
  class updateZeitgrenzen {

    @Test
    void updateZeitgrenzeReturnsSuccess() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";

      final List<Zeitgrenze> zeitgrenzen = List.of(
        Zeitgrenze
          .builder()
          .id(new Zeitgrenze.Id("gz-1"))
          .art(Zeitgrenze.Art.INKRAFT)
          .date(LocalDate.parse("2025-02-20"))
          .build(),
        Zeitgrenze
          .builder()
          .id(new Zeitgrenze.Id("gz-2"))
          .art(Zeitgrenze.Art.AUSSERKRAFT)
          .date(LocalDate.parse("2023-05-01"))
          .build()
      );

      when(updateZeitgrenzenUseCase.updateZeitgrenzenOfDokument(any())).thenReturn(zeitgrenzen);

      // When // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/zeitgrenzen", eli)
            .accept(MediaType.APPLICATION_JSON)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              "[{\"date\": \"2025-02-20\", \"art\": \"INKRAFT\"},{\"date\": \"2023-05-01\", \"art\": \"AUSSERKRAFT\"}]"
            )
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id", is("gz-1")))
        .andExpect(jsonPath("$[0].date", is("2025-02-20")))
        .andExpect(jsonPath("$[0].art", is("INKRAFT")))
        .andExpect(jsonPath("$[1].id", is("gz-2")))
        .andExpect(jsonPath("$[1].date", is("2023-05-01")))
        .andExpect(jsonPath("$[1].art", is("AUSSERKRAFT")));

      verify(updateZeitgrenzenUseCase, times(1))
        .updateZeitgrenzenOfDokument(any(UpdateZeitgrenzenUseCase.Query.class));
    }

    @Test
    void updateZeitgrenzenWithEmptyList() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";

      when(updateZeitgrenzenUseCase.updateZeitgrenzenOfDokument(any()))
        .thenReturn(Collections.emptyList());

      // When // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/zeitgrenzen", eli)
            .accept(MediaType.APPLICATION_JSON)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content("[]")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", empty()));

      verify(updateZeitgrenzenUseCase, times(1))
        .updateZeitgrenzenOfDokument(any(UpdateZeitgrenzenUseCase.Query.class));
    }
  }
}
