package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.port.input.LoadZielnormReferencesUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.ZielnormReference;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SecurelessControllerTest(ZielnormReferencesController.class)
class ZielnormReferencesControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private LoadZielnormReferencesUseCase loadZielnormReferencesUseCase;

  @Nested
  class getReferences {

    @Test
    void itReturnsReferences() throws Exception {
      when(loadZielnormReferencesUseCase.loadZielnormReferences(any()))
        .thenReturn(
          List.of(
            new ZielnormReference(
              XmlMapper.toElement(
                """
                <norms:zielnorm-reference xmlns:norms='http://MetadatenMods.LegalDocML.de/1.7.2/'>
                   <norms:typ>Änderungsvorschrift</norms:typ>
                   <norms:geltungszeit>gz-1</norms:geltungszeit>
                   <norms:eid>hauptteil-1_art-1_abs-1_untergl-1_listenelem-1</norms:eid>
                   <norms:zielnorm>eli/bund/bgbl-1/2021/123</norms:zielnorm>
                </norms:zielnorm-reference>
                """
              )
            )
          )
        );

      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/zielnorm-references")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").exists())
        .andExpect(jsonPath("$[0].typ").value(equalTo("Änderungsvorschrift")))
        .andExpect(jsonPath("$[0].geltungszeit").value(equalTo("gz-1")))
        .andExpect(
          jsonPath("$[0].eId").value(equalTo("hauptteil-1_art-1_abs-1_untergl-1_listenelem-1"))
        )
        .andExpect(jsonPath("$[0].zielnorm").value(equalTo("eli/bund/bgbl-1/2021/123")));

      verify(loadZielnormReferencesUseCase, times(1))
        .loadZielnormReferences(
          assertArg(arg -> {
            assertThat(arg.eli()).hasToString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu");
          })
        );
    }
  }
}
