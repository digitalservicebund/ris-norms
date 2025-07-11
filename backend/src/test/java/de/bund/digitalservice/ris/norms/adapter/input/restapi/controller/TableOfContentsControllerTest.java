package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.application.exception.RegelungstextNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
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
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-verkuendung-1"
    );
    final TableOfContentsItem childItem = new TableOfContentsItem(
      new EId("child-n1"),
      "child-marker",
      "child-heading",
      "child-type",
      false,
      Collections.emptyList()
    );
    final TableOfContentsItem childItemWithStammform = new TableOfContentsItem(
      new EId("child-n2"),
      "child-marker",
      "child-stammform-heading",
      "child-type",
      true,
      Collections.emptyList()
    );
    final TableOfContentsItem parentItem = new TableOfContentsItem(
      new EId("parent-n1"),
      "parent-marker",
      "parent-heading",
      "parent-type",
      false,
      List.of(childItem, childItemWithStammform)
    );
    when(loadTocFromRegelungstextUseCase.loadTocFromRegelungstext(any())).thenReturn(
      List.of(parentItem)
    );

    // When // Then
    mockMvc
      .perform(get("/api/v1/norms/" + eli + "/toc").accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].id").value("parent-n1"))
      .andExpect(jsonPath("$[0].marker").value("parent-marker"))
      .andExpect(jsonPath("$[0].heading").value("parent-heading"))
      .andExpect(jsonPath("$[0].type").value("parent-type"))
      .andExpect(jsonPath("$[0].hasEingebundeneStammform").value("false"))
      .andExpect(jsonPath("$[0].children[0].id").value("child-n1"))
      .andExpect(jsonPath("$[0].children[0].marker").value("child-marker"))
      .andExpect(jsonPath("$[0].children[0].heading").value("child-heading"))
      .andExpect(jsonPath("$[0].children[0].type").value("child-type"))
      .andExpect(jsonPath("$[0].children[0].hasEingebundeneStammform").value("false"))
      .andExpect(jsonPath("$[0].children[1].id").value("child-n2"))
      .andExpect(jsonPath("$[0].children[1].marker").value("child-marker"))
      .andExpect(jsonPath("$[0].children[1].heading").value("child-stammform-heading"))
      .andExpect(jsonPath("$[0].children[1].type").value("child-type"))
      .andExpect(jsonPath("$[0].children[1].hasEingebundeneStammform").value("true"));
  }

  @Test
  void itReturnsEmptyListWhenNoTableOfContentsExists() throws Exception {
    // Given
    var eli = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-verkuendung-1"
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
      "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
    );

    when(loadTocFromRegelungstextUseCase.loadTocFromRegelungstext(any())).thenThrow(
      new RegelungstextNotFoundException(eli.toString())
    );

    // when
    mockMvc
      .perform(get("/api/v1/norms/" + eli + "/toc").accept(MediaType.APPLICATION_JSON_VALUE))
      // then
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("type").value("/errors/regelungstext-not-found"))
      .andExpect(jsonPath("title").value("Regelungstext not found"))
      .andExpect(jsonPath("status").value(404))
      .andExpect(
        jsonPath("detail").value(
          "Regelungstext with eli eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1 does not exist"
        )
      )
      .andExpect(
        jsonPath("instance").value(
          "/api/v1/norms/eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/toc"
        )
      )
      .andExpect(
        jsonPath("eli").value(
          "eli/bund/NONEXISTENT_NORM/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
        )
      );
  }
}
