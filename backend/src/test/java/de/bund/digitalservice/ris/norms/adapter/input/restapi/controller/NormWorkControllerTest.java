package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadExpressionsOfNormWorkUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SecurelessControllerTest(NormWorkController.class)
class NormWorkControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private LoadNormUseCase loadNormUseCase;

  @MockitoBean
  private LoadExpressionsOfNormWorkUseCase loadExpressionsOfNormWorkUseCase;

  @Nested
  class getNorm {

    @Test
    void itReturnsNotFoundWhenNormDoesNotExist() throws Exception {
      when(loadNormUseCase.loadNorm(any())).thenThrow(
        new NormNotFoundException(NormWorkEli.fromString("eli/bund/bgbl-1/2024/4"))
      );

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms/eli/bund/bgbl-1/2024/4").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().contentTypeCompatibleWith("application/problem+json"))
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("eli").value("eli/bund/bgbl-1/2024/4"));
    }

    @Test
    void itReturnsNorms() throws Exception {
      when(loadNormUseCase.loadNorm(any())).thenReturn(
        Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
      );

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms/eli/bund/bgbl-1/1964/s593").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("eli").value("eli/bund/bgbl-1/1964/s593"))
        .andExpect(jsonPath("title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts"));

      verify(loadNormUseCase).loadNorm(
          assertArg(arg -> {
            assertThat(arg).isInstanceOf(LoadNormUseCase.EliOptions.class);
            assertThat(((LoadNormUseCase.EliOptions) arg).eli()).hasToString(
              "eli/bund/bgbl-1/1964/s593"
            );
          })
        );
    }
  }

  @Nested
  class getExpressions {

    @Test
    void itReturnsEmptyListWhenNormDoesNotExist() throws Exception {
      when(loadExpressionsOfNormWorkUseCase.loadExpressionsOfNormWork(any())).thenReturn(List.of());

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/2024/4/expressions").accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(content().string("[]"));
    }

    @Test
    void itReturnsExpressions() throws Exception {
      when(loadExpressionsOfNormWorkUseCase.loadExpressionsOfNormWork(any())).thenReturn(
        List.of(
          new LoadExpressionsOfNormWorkUseCase.Result(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/2/deu"),
            false
          ),
          new LoadExpressionsOfNormWorkUseCase.Result(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"),
            true
          )
        )
      );

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/expressions").accept(
            MediaType.APPLICATION_JSON
          )
        )
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("[0].eli").value("eli/bund/bgbl-1/1964/s593/1964-08-05/2/deu"))
        .andExpect(jsonPath("[0].gegenstandslos").value(false))
        .andExpect(jsonPath("[1].eli").value("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"))
        .andExpect(jsonPath("[1].gegenstandslos").value(true));

      verify(loadExpressionsOfNormWorkUseCase).loadExpressionsOfNormWork(
        new LoadExpressionsOfNormWorkUseCase.Options(
          NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593")
        )
      );
    }
  }
}
