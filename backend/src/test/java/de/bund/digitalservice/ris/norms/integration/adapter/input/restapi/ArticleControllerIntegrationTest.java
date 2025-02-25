package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static de.bund.digitalservice.ris.norms.XPathMatcher.hasXPath;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
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
  private NormManifestationRepository normManifestationRepository;

  @AfterEach
  void cleanUp() {
    dokumentRepository.deleteAll();
    normManifestationRepository.deleteAll();
  }

  @Nested
  class getArticles {

    @Test
    void itReturnsArticles() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithMods.xml"))
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithoutPassiveModifications.xml")
        )
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithPassiveModifications.xml")
        )
      );

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").exists())
        .andExpect(jsonPath("$[0].eid").value("hauptteil-1_art-1"))
        .andExpect(jsonPath("$[1]").exists())
        .andExpect(jsonPath("$[1].eid").value("hauptteil-1_art-2"))
        .andExpect(jsonPath("$[2]").doesNotExist());
    }

    @Test
    void itReturnsEmptyListWhenTheNormHasNoArticles() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml"))
      );

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/articles")
            .accept(MediaType.APPLICATION_JSON)
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
          get("/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/articles")
            .accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/regelungstext-not-found"))
        .andExpect(jsonPath("title").value("Regelungstext not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Regelungstext with eli eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1 does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/articles"
            )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1")
        );
    }
  }

  @Nested
  class getArticlesRender {

    @Test
    void itReturnsTheXmlOfArticles() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithMods.xml"))
      );

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles")
            .accept(MediaType.TEXT_HTML_VALUE)
        )
        .andExpect(status().isOk())
        .andExpectAll(
          content()
            .node(
              hasXPath(
                "//span[@data-eId=\"hauptteil-1_art-1_überschrift-1\"]",
                equalTo("Änderung des Vereinsgesetzes")
              )
            ),
          content()
            .node(
              hasXPath(
                "//span/@data-href",
                equalTo("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
              )
            ),
          content()
            .node(
              hasXPath(
                "//span[@data-eId=\"hauptteil-1_art-2_überschrift-1\"]",
                equalTo("Inkrafttreten")
              )
            )
        );
    }

    @Test
    void itReturnsTheXmlOfTheArticleInkrafttreten() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithMods.xml"))
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles?refersTo=geltungszeitregel"
          )
            .accept(MediaType.TEXT_HTML_VALUE)
        )
        .andExpect(status().isOk())
        .andExpectAll(
          content()
            .node(
              IsNot.not(
                hasXPath(
                  "//span[@data-eId=\"hauptteil-1_art-1_überschrift-1\"]",
                  equalTo("Änderung des Vereinsgesetzes")
                )
              )
            ),
          content()
            .node(
              IsNot.not(
                hasXPath(
                  "//span/@data-href",
                  equalTo("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
                )
              )
            ),
          content()
            .node(
              hasXPath(
                "//span[@data-eId=\"hauptteil-1_art-2_überschrift-1\"]",
                equalTo("Inkrafttreten")
              )
            )
        );
    }

    @Test
    void itReturnsNotFoundIfTheNormDoesntExist() throws Exception {
      // Given
      // Nothing saved to the repository

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/articles")
            .accept(MediaType.TEXT_HTML_VALUE)
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Norm with eli eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1 does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/articles"
            )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1")
        );
    }

    @Test
    void itReturnsNotFoundIfNoArticleOfTypeExist() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithoutPassiveModifications.xml")
        )
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/articles?refersTo=geltungszeitregel"
          )
            .accept(MediaType.TEXT_HTML_VALUE)
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/article-of-type-not-found"))
        .andExpect(jsonPath("title").value("Article of specific type not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Dokument with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1 does not contain articles of type geltungszeitregel"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/articles"
            )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
        )
        .andExpect(jsonPath("articleType").value("geltungszeitregel"));
    }

    @Test
    void itReturnsNotFoundIfTheNormHasNoArticles() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml"))
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/articles?refersTo=geltungszeitregel"
          )
            .accept(MediaType.TEXT_HTML_VALUE)
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/article-of-type-not-found"))
        .andExpect(jsonPath("title").value("Article of specific type not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Dokument with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1 does not contain articles of type geltungszeitregel"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/articles"
            )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
        )
        .andExpect(jsonPath("articleType").value("geltungszeitregel"));
    }
  }

  @Nested
  class getArticle {

    @Test
    void itReturnsArticle() throws Exception {
      // Given

      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithMods.xml"))
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithoutPassiveModifications.xml")
        )
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithPassiveModifications.xml")
        )
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("eid").value("hauptteil-1_art-1"));
    }

    @Test
    void itReturnsNothingIfNormDoesNotExist() throws Exception {
      // Given
      // Nothing

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/articles/hauptteil-1_art-1"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/regelungstext-not-found"))
        .andExpect(jsonPath("title").value("Regelungstext not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Regelungstext with eli eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1 does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/articles/hauptteil-1_art-1"
            )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1")
        );
    }

    @Test
    void itReturnsNothingIfArticleDoesNotExist() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithMods.xml"))
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-9999"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/article-not-found"))
        .andExpect(jsonPath("title").value("Article not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Article with eid hauptteil-1_art-9999 does not exist in norm with eli eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-9999"
            )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1")
        )
        .andExpect(jsonPath("eid").value("hauptteil-1_art-9999"));
    }
  }

  @Nested
  class getArticleRender {

    @Test
    void itReturnsArticleRender() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithMods.xml"))
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1"
          )
            .accept(MediaType.TEXT_HTML)
        )
        .andExpect(status().isOk())
        .andExpect(
          xpath("//span[@data-eId=\"hauptteil-1_art-1_überschrift-1\"]")
            .string(equalToCompressingWhiteSpace("Änderung des Vereinsgesetzes"))
        )
        .andExpect(xpath("//span[@data-eId=\"hauptteil-1_art-3_überschrift-1\"]").doesNotExist());
    }

    @Test
    void itReturnsNotFoundIfArticleDoesntExist() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithMultipleMods.xml"))
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithMultiplePassiveModifications.xml")
        )
      );

      // When / Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/articles/hauptteil-1_art-9999"
          )
            .accept(MediaType.TEXT_HTML)
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/article-not-found"))
        .andExpect(jsonPath("title").value("Article not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Article with eid hauptteil-1_art-9999 does not exist in norm with eli eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/articles/hauptteil-1_art-9999"
            )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1")
        )
        .andExpect(jsonPath("eid").value("hauptteil-1_art-9999"));
    }
  }
}
