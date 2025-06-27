package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.port.input.LoadNormWorksUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SecurelessControllerTest(NormController.class)
class NormControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private LoadNormWorksUseCase loadNormWorksUseCase;

  @Nested
  class getNorm {

    @Test
    void itReturnsEmptyWhenNoNormsExist() throws Exception {
      when(loadNormWorksUseCase.loadNormWorks(any())).thenReturn(Page.empty());

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("content").isArray())
        .andExpect(jsonPath("content").isEmpty())
        .andExpect(jsonPath("page.size").value(0))
        .andExpect(jsonPath("page.number").value(0))
        .andExpect(jsonPath("page.totalElements").value(0))
        .andExpect(jsonPath("page.totalPages").value(1));
    }

    @Test
    void itReturnsNorms() throws Exception {
      when(loadNormWorksUseCase.loadNormWorks(any())).thenReturn(
        new PageImpl<>(
          List.of(
            Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"),
            Fixtures.loadNormFromDisk("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23")
          ),
          Pageable.ofSize(10).withPage(2),
          2
        )
      );

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms?size=10&page=2").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("content").isArray())
        .andExpect(jsonPath("content[0].eli").value("eli/bund/bgbl-1/1964/s593"))
        .andExpect(
          jsonPath("content[0].title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts")
        )
        .andExpect(jsonPath("content[1].eli").value("eli/bund/bgbl-1/2017/s419"))
        .andExpect(
          jsonPath("content[1].title").value(
            "Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes"
          )
        )
        .andExpect(jsonPath("content[2]").doesNotExist())
        .andExpect(jsonPath("page.size").value(10))
        .andExpect(jsonPath("page.number").value(2))
        .andExpect(jsonPath("page.totalElements").value(22))
        .andExpect(jsonPath("page.totalPages").value(3));

      verify(loadNormWorksUseCase).loadNormWorks(
        assertArg(arg -> {
          assertThat(arg.pageable().getPageNumber()).isEqualTo(2);
          assertThat(arg.pageable().getPageSize()).isEqualTo(10);
        })
      );
    }
  }
}
