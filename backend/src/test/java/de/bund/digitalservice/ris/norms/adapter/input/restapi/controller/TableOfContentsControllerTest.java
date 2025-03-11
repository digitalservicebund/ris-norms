package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.application.exception.RegelungstextNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SecurelessControllerTest(TableOfContentsController.class)
class TableOfContentsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private LoadTocFromRegelungstextUseCase loadTocFromRegelungstextUseCase;

  @Test
  void itReturnsTableOfContents() throws Exception {
    // Given
    var eli = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
    );
    final TableOfContentsItem childItem = new TableOfContentsItem(
      "child-id",
      "child-marker",
      "child-heading",
      "child-type",
      Collections.emptyList()
    );
    final TableOfContentsItem parentItem = new TableOfContentsItem(
      "parent-id",
      "parent-marker",
      "parent-heading",
      "parent-type",
      List.of(childItem)
    );
    when(loadTocFromRegelungstextUseCase.loadTocFromRegelungstext(any()))
      .thenReturn(List.of(parentItem));

    // When // Then
    mockMvc
      .perform(get("/api/v1/norms/" + eli + "/toc").accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].id").value("parent-id"))
      .andExpect(jsonPath("$[0].marker").value("parent-marker"))
      .andExpect(jsonPath("$[0].heading").value("parent-heading"))
      .andExpect(jsonPath("$[0].type").value("parent-type"))
      .andExpect(jsonPath("$[0].children[0].id").value("child-id"))
      .andExpect(jsonPath("$[0].children[0].marker").value("child-marker"))
      .andExpect(jsonPath("$[0].children[0].heading").value("child-heading"))
      .andExpect(jsonPath("$[0].children[0].type").value("child-type"));
  }

  @Test
  void itReturnsEmptyListWhenNoTableOfContentsExists() throws Exception {
    // Given
    var eli = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
    );
    when(loadTocFromRegelungstextUseCase.loadTocFromRegelungstext(any())).thenReturn(List.of());

    // When // Then
    mockMvc
      .perform(get("/api/v1/norms/" + eli + "/toc").accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isEmpty());
  }

  @Test
  void return404IfRegelungstextNotFound() throws Exception {
    // given no norm
    var eli = DokumentExpressionEli.fromString(
      "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1"
    );

    when(loadTocFromRegelungstextUseCase.loadTocFromRegelungstext(any()))
      .thenThrow(new RegelungstextNotFoundException(eli.toString()));

    // when
    mockMvc
      .perform(get("/api/v1/norms/" + eli + "/toc").accept(MediaType.APPLICATION_JSON_VALUE))
      // then
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("type").value("/errors/regelungstext-not-found"))
      .andExpect(jsonPath("title").value("Regelungstext not found"))
      .andExpect(jsonPath("status").value(404))
      .andExpect(
        jsonPath("detail")
          .value(
            "Regelungstext with eli eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1 does not exist"
          )
      )
      .andExpect(
        jsonPath("instance")
          .value(
            "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1/toc"
          )
      )
      .andExpect(
        jsonPath("eli")
          .value("eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-1")
      );
  }
}
