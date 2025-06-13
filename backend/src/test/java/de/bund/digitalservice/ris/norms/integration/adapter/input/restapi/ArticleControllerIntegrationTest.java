package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static de.bund.digitalservice.ris.norms.XPathMatcher.hasXPath;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.repository.BinaryFileRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.Roles;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser(roles = { Roles.NORMS_USER })
class ArticleControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DokumentRepository dokumentRepository;

  @Autowired
  private BinaryFileRepository binaryFileRepository;

  @Autowired
  private NormManifestationRepository normManifestationRepository;

  @AfterEach
  void cleanUp() {
    dokumentRepository.deleteAll();
    binaryFileRepository.deleteAll();
    normManifestationRepository.deleteAll();
  }

  @Nested
  class getArticles {

    @Test
    void itReturnsArticles() throws Exception {
      // Give
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23",
        NormPublishState.UNPUBLISHED
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-verkuendung-1/articles"
          ).accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").exists())
        .andExpect(jsonPath("$[0].eid").value("art-z1"))
        .andExpect(jsonPath("$[1]").exists())
        .andExpect(jsonPath("$[1].eid").value("art-z3"))
        .andExpect(jsonPath("$[2]").doesNotExist());
    }

    @Test
    void itReturnsEmptyListWhenTheNormHasNoArticles() throws Exception {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        ArticleControllerIntegrationTest.class,
        "vereinsgesetz-without-articles",
        NormPublishState.UNPUBLISHED
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/articles"
          ).accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").doesNotExist());
    }

    @Test
    void itReturnsNotFoundIfTheRegelungstextIsNotFound() throws Exception {
      // Given
      // Nothing

      // When / Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-verkuendung-1/articles"
          ).accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/regelungstext-not-found"))
        .andExpect(jsonPath("title").value("Regelungstext not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail").value(
            "Regelungstext with eli eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-verkuendung-1 does not exist"
          )
        )
        .andExpect(
          jsonPath("instance").value(
            "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-verkuendung-1/articles"
          )
        )
        .andExpect(
          jsonPath("eli").value(
            "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-verkuendung-1"
          )
        );
    }
  }

  @Nested
  class getArticlesRender {

    @Test
    void itReturnsTheXmlOfArticles() throws Exception {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23",
        NormPublishState.UNPUBLISHED
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-verkuendung-1/articles"
          ).accept(MediaType.TEXT_HTML_VALUE)
        )
        .andExpect(status().isOk())
        .andExpectAll(
          content()
            .node(
              hasXPath(
                "//span[@data-eId=\"art-z1_überschrift-n1\"]",
                equalTo("Änderung des Vereinsgesetzes")
              )
            ),
          content()
            .node(
              hasXPath(
                "//span/@data-href",
                equalTo("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1")
              )
            ),
          content()
            .node(hasXPath("//span[@data-eId=\"art-z3_überschrift-n1\"]", equalTo("Inkrafttreten")))
        );
    }

    @Test
    void itReturnsTheXmlOfTheGeltungszeitregelArticles() throws Exception {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/2024/10/2024-01-18/1/deu/2024-01-18",
        NormPublishState.UNPUBLISHED
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2024/10/2024-01-18/1/deu/regelungstext-verkuendung-1/articles?refersTo=geltungszeitregel&refersTo=geltungszeitregel-inkrafttreten&refersTo=geltungszeitregel-ausserkrafttreten"
          ).accept(MediaType.TEXT_HTML_VALUE)
        )
        .andExpect(status().isOk())
        .andExpectAll(
          content()
            .node(
              IsNot.not(
                hasXPath(
                  "//span[@data-eId=\"art-z1_überschrift-n1\"]",
                  equalTo("Änderung des Vereinsgesetzes")
                )
              )
            ),
          content()
            .node(
              IsNot.not(
                hasXPath(
                  "//span/@data-href",
                  equalTo("eli/bund/bgbl-1/2024/10/2024-01-18/1/deu/regelungstext-verkuendung-1")
                )
              )
            ),
          content()
            .node(
              hasXPath("//span[@data-eId=\"art-z3_überschrift-n1\"]", equalTo("Außerkrafttreten"))
            ),
          content()
            .node(hasXPath("//span[@data-eId=\"art-z4_überschrift-n1\"]", equalTo("Inkrafttreten")))
        );
    }

    @Test
    void itReturnsNotFoundIfTheNormDoesntExist() throws Exception {
      // Given
      // Nothing saved to the repository

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1/articles"
          ).accept(MediaType.TEXT_HTML_VALUE)
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail").value(
            "Norm with eli eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1 does not exist"
          )
        )
        .andExpect(
          jsonPath("instance").value(
            "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1/articles"
          )
        )
        .andExpect(
          jsonPath("eli").value(
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1"
          )
        );
    }

    @Test
    void itReturnsNotFoundIfNoArticleOfTypeExist() throws Exception {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        ArticleControllerIntegrationTest.class,
        "vereinsgestz-without-geltungszeit-article",
        NormPublishState.UNPUBLISHED
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/articles?refersTo=geltungszeitregel&refersTo=geltungszeitregel-inkrafttreten&refersTo=geltungszeitregel-ausserkrafttreten"
          ).accept(MediaType.TEXT_HTML_VALUE)
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/no-articles-of-types-found"))
        .andExpect(jsonPath("title").value("No articles of specific types found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail").value(
            "Dokument with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1 does not contain articles of any of the types geltungszeitregel, geltungszeitregel-inkrafttreten, geltungszeitregel-ausserkrafttreten"
          )
        )
        .andExpect(
          jsonPath("instance").value(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/articles"
          )
        )
        .andExpect(
          jsonPath("eli").value(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
          )
        )
        .andExpect(jsonPath("articleTypes[0]").value("geltungszeitregel"))
        .andExpect(jsonPath("articleTypes[1]").value("geltungszeitregel-inkrafttreten"))
        .andExpect(jsonPath("articleTypes[2]").value("geltungszeitregel-ausserkrafttreten"));
    }

    @Test
    void itReturnsNotFoundIfTheNormHasNoArticles() throws Exception {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        ArticleControllerIntegrationTest.class,
        "vereinsgesetz-without-articles",
        NormPublishState.UNPUBLISHED
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/articles?refersTo=geltungszeitregel&refersTo=geltungszeitregel-inkrafttreten&refersTo=geltungszeitregel-ausserkrafttreten"
          ).accept(MediaType.TEXT_HTML_VALUE)
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/no-articles-of-types-found"))
        .andExpect(jsonPath("title").value("No articles of specific types found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail").value(
            "Dokument with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1 does not contain articles of any of the types geltungszeitregel, geltungszeitregel-inkrafttreten, geltungszeitregel-ausserkrafttreten"
          )
        )
        .andExpect(
          jsonPath("instance").value(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/articles"
          )
        )
        .andExpect(
          jsonPath("eli").value(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
          )
        )
        .andExpect(jsonPath("articleTypes[0]").value("geltungszeitregel"));
    }
  }

  @Nested
  class getArticle {

    @Test
    void itReturnsArticle() throws Exception {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23",
        NormPublishState.UNPUBLISHED
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-verkuendung-1/articles/art-z1"
          ).accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("eid").value("art-z1"));
    }

    @Test
    void itReturnsNothingIfNormDoesNotExist() throws Exception {
      // Given
      // Nothing

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1/articles/art-z1"
          ).accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/regelungstext-not-found"))
        .andExpect(jsonPath("title").value("Regelungstext not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail").value(
            "Regelungstext with eli eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1 does not exist"
          )
        )
        .andExpect(
          jsonPath("instance").value(
            "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1/articles/art-z1"
          )
        )
        .andExpect(
          jsonPath("eli").value(
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1"
          )
        );
    }

    @Test
    void itReturnsNothingIfArticleDoesNotExist() throws Exception {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23",
        NormPublishState.UNPUBLISHED
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-verkuendung-1/articles/art-z9999"
          ).accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/article-not-found"))
        .andExpect(jsonPath("title").value("Article not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail").value(
            "Article with eid art-z9999 does not exist in norm with eli eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-verkuendung-1"
          )
        )
        .andExpect(
          jsonPath("instance").value(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-verkuendung-1/articles/art-z9999"
          )
        )
        .andExpect(
          jsonPath("eli").value(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-verkuendung-1"
          )
        )
        .andExpect(jsonPath("eid").value("art-z9999"));
    }
  }

  @Nested
  class getArticleRender {

    @Test
    void itReturnsArticleRender() throws Exception {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23",
        NormPublishState.UNPUBLISHED
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-verkuendung-1/articles/art-z1"
          ).accept(MediaType.TEXT_HTML)
        )
        .andExpect(status().isOk())
        .andExpect(
          xpath("//span[@data-eId=\"art-z1_überschrift-n1\"]").string(
            equalToCompressingWhiteSpace("Änderung des Vereinsgesetzes")
          )
        )
        .andExpect(xpath("//span[@data-eId=\"hauptteil-1_art-3_überschrift-1\"]").doesNotExist());
    }

    @Test
    void itReturnsNotFoundIfArticleDoesntExist() throws Exception {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
        NormPublishState.UNPUBLISHED
      );

      // When / Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/articles/art-z9999"
          ).accept(MediaType.TEXT_HTML)
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/article-not-found"))
        .andExpect(jsonPath("title").value("Article not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail").value(
            "Article with eid art-z9999 does not exist in norm with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
          )
        )
        .andExpect(
          jsonPath("instance").value(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1/articles/art-z9999"
          )
        )
        .andExpect(
          jsonPath("eli").value(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
          )
        )
        .andExpect(jsonPath("eid").value("art-z9999"));
    }
  }
}
