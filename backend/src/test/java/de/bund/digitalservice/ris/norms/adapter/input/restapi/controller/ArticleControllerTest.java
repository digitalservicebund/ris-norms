package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SecurelessControllerTest(ArticleController.class)
class ArticleControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private LoadRegelungstextUseCase loadRegelungstextUseCase;

  @MockitoBean
  private LoadArticlesFromDokumentUseCase loadArticlesFromDokumentUseCase;

  @MockitoBean
  private LoadSpecificArticlesXmlFromDokumentUseCase loadSpecificArticlesXmlFromDokumentUseCase;

  @MockitoBean
  private TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase;

  @MockitoBean
  private LoadArticleHtmlUseCase loadArticleHtmlUseCase;

  @Nested
  class getArticles {

    @Test
    void itReturnsArticles() throws Exception {
      // Given
      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-verkuendung-1.xml"
      );

      when(loadArticlesFromDokumentUseCase.loadArticlesFromDokument(any())).thenReturn(
        regelungstext.getArticles()
      );

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1/articles"
          ).accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").exists())
        .andExpect(jsonPath("$[0].eid").value("art-z1"))
        .andExpect(jsonPath("$[1]").exists())
        .andExpect(jsonPath("$[1].eid").value("art-z3"))
        .andExpect(jsonPath("$[2]").doesNotExist());

      verify(loadArticlesFromDokumentUseCase, times(1)).loadArticlesFromDokument(
        argThat(argument ->
          Objects.equals(
            argument.eli(),
            DokumentExpressionEli.fromString(
              "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1"
            )
          )
        )
      );
    }

    @Test
    void itReturnsUnprocessableEntityWhenMandatoryNodeIsMissing() throws Exception {
      // Given
      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
      );

      when(loadRegelungstextUseCase.loadRegelungstext(any())).thenReturn(regelungstext);

      when(loadArticlesFromDokumentUseCase.loadArticlesFromDokument(any())).thenThrow(
        new MandatoryNodeNotFoundException("example-xpath", "example/eli")
      );

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/articles?amendedBy=eli/bund/bgbl-1/2017/s815/1995-03-15/1/deu/regelungstext-verkuendung-1"
          ).accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isUnprocessableEntity());
    }
  }

  @Nested
  class getArticlesRender {

    @Test
    void itCallsNormServiceAndReturnsNormRender() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-verkuendung-1";
      final String xml = "<akn:doc></akn:doc>";
      final String html = "<div></div>";

      when(
        loadSpecificArticlesXmlFromDokumentUseCase.loadSpecificArticlesXmlFromDokument(any())
      ).thenReturn(List.of(xml));
      when(transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(any())).thenReturn(html);

      // When
      mockMvc
        .perform(
          get("/api/v1/norms/{eli}/articles?refersTo=something", eli).accept(MediaType.TEXT_HTML)
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
        .andExpect(content().string("<div>\n" + html + "\n</div>\n"));

      verify(transformLegalDocMlToHtmlUseCase, times(1)).transformLegalDocMlToHtml(
        argThat(query -> query.xml().equals(xml))
      );
    }
  }

  @Nested
  class getArticle {

    @Test
    void itReturnsArticle() throws Exception {
      // Given
      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-verkuendung-1.xml"
      );

      when(loadRegelungstextUseCase.loadRegelungstext(any())).thenReturn(regelungstext);

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1/articles/art-z1"
          ).accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("eid").value("art-z1"));

      verify(loadRegelungstextUseCase, times(1)).loadRegelungstext(
        argThat(argument ->
          Objects.equals(
            argument.eli(),
            DokumentExpressionEli.fromString(
              "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1"
            )
          )
        )
      );
    }

    @Test
    void itReturnsNothingIfArticleDoesNotExists() throws Exception {
      // Given
      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
      );

      // When
      when(loadRegelungstextUseCase.loadRegelungstext(any())).thenReturn(regelungstext);

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/articles/art-z4523"
          ).accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isNotFound());

      verify(loadRegelungstextUseCase, times(1)).loadRegelungstext(
        argThat(argument ->
          Objects.equals(
            argument.eli(),
            DokumentExpressionEli.fromString(
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
            )
          )
        )
      );
    }
  }

  @Nested
  class getArticleRender {

    @Test
    void itReturnsArticleRender() throws Exception {
      // Given
      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
      );

      // When
      when(loadRegelungstextUseCase.loadRegelungstext(any())).thenReturn(regelungstext);
      when(loadArticleHtmlUseCase.loadArticleHtml(any())).thenReturn("<div></div>");

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/articles/art-z1"
          ).accept(MediaType.TEXT_HTML)
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(content().string("<div></div>"));
    }
  }
}
