package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static de.bund.digitalservice.ris.norms.utils.XmlMapper.toElement;
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
import java.util.Collections;
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
    void getZeitgrenzenReturnsCorrectDataInOrder() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";

      var customModsMetadata = new CustomModsMetadata(
        toElement(
          """
          <norms:legalDocML.de_metadaten xmlns:norms="http://MetadatenMods.LegalDocML.de/1.7.2/">
            <norms:geltungszeiten>
                <norms:geltungszeit id="gz-1" art="inkraft">2020-01-01</norms:geltungszeit>
                <norms:geltungszeit id="gz-2" art="ausserkraft">2024-01-01</norms:geltungszeit>
                <norms:geltungszeit id="gz-3" art="inkraft">2019-01-01</norms:geltungszeit>
                <norms:geltungszeit id="gz-4" art="ausserkraft">2025-01-01</norms:geltungszeit>
            </norms:geltungszeiten>
            <norms:zielnorm-references>
                 <norms:zielnorm-reference>
                     <norms:typ>Änderungsvorschrift</norms:typ>
                     <norms:geltungszeit>gz-1</norms:geltungszeit>
                     <norms:eid>hauptteil-1_art-1_abs-1_untergl-1_listenelem-1</norms:eid>
                     <norms:zielnorm>eli/bund/bgbl-1/2021/123</norms:zielnorm>
                 </norms:zielnorm-reference>
             </norms:zielnorm-references>
          </norms:legalDocML.de_metadaten>
          """
        )
      );

      when(loadZeitgrenzenUseCase.loadZeitgrenzenFromDokument(any())).thenReturn(
        customModsMetadata.getOrCreateGeltungszeiten().stream().toList()
      );

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms/{eli}/zeitgrenzen", eli).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(4)))
        .andExpect(jsonPath("$[0].id", is("gz-3")))
        .andExpect(jsonPath("$[0].date", is("2019-01-01")))
        .andExpect(jsonPath("$[0].art", is("INKRAFT")))
        .andExpect(jsonPath("$[0].inUse", is(false)))
        .andExpect(jsonPath("$[1].id", is("gz-1")))
        .andExpect(jsonPath("$[1].date", is("2020-01-01")))
        .andExpect(jsonPath("$[1].art", is("INKRAFT")))
        .andExpect(jsonPath("$[1].inUse", is(true)))
        .andExpect(jsonPath("$[2].id", is("gz-2")))
        .andExpect(jsonPath("$[2].date", is("2024-01-01")))
        .andExpect(jsonPath("$[2].art", is("AUSSERKRAFT")))
        .andExpect(jsonPath("$[2].inUse", is(false)))
        .andExpect(jsonPath("$[3].id", is("gz-4")))
        .andExpect(jsonPath("$[3].date", is("2025-01-01")))
        .andExpect(jsonPath("$[3].art", is("AUSSERKRAFT")))
        .andExpect(jsonPath("$[3].inUse", is(false)));

      verify(loadZeitgrenzenUseCase, times(1)).loadZeitgrenzenFromDokument(
        any(LoadZeitgrenzenUseCase.Query.class)
      );
    }

    @Test
    void getZeitgrenzenReturnsEmpty() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";

      when(loadZeitgrenzenUseCase.loadZeitgrenzenFromDokument(any())).thenReturn(
        Collections.emptyList()
      );

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms/{eli}/zeitgrenzen", eli).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", empty()));

      verify(loadZeitgrenzenUseCase, times(1)).loadZeitgrenzenFromDokument(
        any(LoadZeitgrenzenUseCase.Query.class)
      );
    }
  }

  @Nested
  class updateZeitgrenzen {

    @Test
    void updateZeitgrenzeReturnsSuccess() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";

      var customModsMetadata = new CustomModsMetadata(
        toElement(
          """
          <norms:legalDocML.de_metadaten xmlns:norms="http://MetadatenMods.LegalDocML.de/1.7.2/">
            <norms:geltungszeiten>
                <norms:geltungszeit id="gz-1" art="inkraft">2025-02-20</norms:geltungszeit>
                <norms:geltungszeit id="gz-2" art="ausserkraft">2023-05-01</norms:geltungszeit>
            </norms:geltungszeiten>
          </norms:legalDocML.de_metadaten>
          """
        )
      );

      when(updateZeitgrenzenUseCase.updateZeitgrenzenOfDokument(any())).thenReturn(
        customModsMetadata.getOrCreateGeltungszeiten().stream().toList()
      );

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
        .andExpect(jsonPath("$[0].id", is("gz-2")))
        .andExpect(jsonPath("$[0].date", is("2023-05-01")))
        .andExpect(jsonPath("$[0].art", is("AUSSERKRAFT")))
        .andExpect(jsonPath("$[0].inUse", is(false)))
        .andExpect(jsonPath("$[1].id", is("gz-1")))
        .andExpect(jsonPath("$[1].date", is("2025-02-20")))
        .andExpect(jsonPath("$[1].art", is("INKRAFT")))
        .andExpect(jsonPath("$[1].inUse", is(false)));

      verify(updateZeitgrenzenUseCase, times(1)).updateZeitgrenzenOfDokument(
        any(UpdateZeitgrenzenUseCase.Query.class)
      );
    }

    @Test
    void updateZeitgrenzenWithEmptyList() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";

      when(updateZeitgrenzenUseCase.updateZeitgrenzenOfDokument(any())).thenReturn(
        Collections.emptyList()
      );

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

      verify(updateZeitgrenzenUseCase, times(1)).updateZeitgrenzenOfDokument(
        any(UpdateZeitgrenzenUseCase.Query.class)
      );
    }
  }
}
