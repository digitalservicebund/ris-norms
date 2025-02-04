package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static de.bund.digitalservice.ris.norms.XPathMatcher.hasXPath;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.Set;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser
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
        .andExpect(
          jsonPath("$[0].affectedDocumentEli")
            .value("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
        )
        .andExpect(jsonPath("$[1]").exists())
        .andExpect(jsonPath("$[1].eid").value("hauptteil-1_art-2"))
        .andExpect(jsonPath("$[1].affectedDocumentEli").doesNotExist())
        .andExpect(jsonPath("$[1].affectedDocumentZf0Eli").doesNotExist())
        .andExpect(jsonPath("$[2]").doesNotExist());
    }

    @Test
    void itReturnsArticlesFilteredByAmendedAt() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithMultiplePassiveModifications.xml")
        )
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/articles?amendedAt=meta-1_lebzykl-1_ereignis-4"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").exists())
        .andExpect(jsonPath("$[0].eid").value("hauptteil-1_art-1"))
        .andExpect(jsonPath("$[1]").doesNotExist());
    }

    @Test
    void itReturnsArticlesFilteredByAmendedBy() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithPassiveModificationsInDifferentArticles.xml")
        )
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/articles?amendedBy=eli/bund/bgbl-1/2017/s815/1995-03-15/1/deu/regelungstext-1"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").exists())
        .andExpect(jsonPath("$[0].eid").value("hauptteil-1_art-2"))
        .andExpect(jsonPath("$[1]").doesNotExist());
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
    void itReturnsEmptyListIfAmendedByIsNotFound() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithMultiplePassiveModifications.xml")
        )
      );

      // When / Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/articles?amendedBy=eli/bund/DOES-NOT-EXIST/2017/s419/2017-03-15/1/deu/regelungstext-1&amendedAt=meta-1_lebzykl-1_ereignis-4"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").doesNotExist());
    }

    @Test
    void itReturnsEmptyListIfAmendedAtIsNotFound() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithMultiplePassiveModifications.xml")
        )
      );

      // When / Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/articles?amendedBy=eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1&amendedAt=DOES-NOT-EXIST"
          )
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
        .andExpect(jsonPath("eid").value("hauptteil-1_art-1"))
        .andExpect(
          jsonPath("affectedDocumentEli")
            .value("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
        );
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
    void itReturnsArticleRenderAtIsoDate() throws Exception {
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
            "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/articles/hauptteil-1_art-1?atIsoDate=2017-03-01T00:00:00.000Z"
          )
            .accept(MediaType.TEXT_HTML)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("§ 9 Absatz 1 Satz 2, Absatz 2 oder 3")))
        .andExpect(content().string(not(containsString("§ 9 Abs. 1 Satz 2, Abs. 2"))));
    }

    @Test
    void itReturnsServerErrorForInvalidIsoDate() throws Exception {
      // Given

      // When / Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/articles/hauptteil-1_art-20?atIsoDate=thisIsNotADate"
          )
            .accept(MediaType.TEXT_HTML)
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("type").value("/errors/parameter-binding-error"))
        .andExpect(jsonPath("title").value("Parameter Binding Error"))
        .andExpect(jsonPath("status").value(400))
        .andExpect(jsonPath("detail").value("Invalid request parameter: thisIsNotADate"))
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/articles/hauptteil-1_art-20"
            )
        );
    }

    @Test
    void itReturnsNotFoundIfNormDoesntExist() throws Exception {
      // Given
      // Nothing

      // When / Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/articles/hauptteil-1_art-20?atIsoDate=2017-03-01T00:00:00.000Z"
          )
            .accept(MediaType.TEXT_HTML)
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Norm with eli eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1 does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/articles/hauptteil-1_art-20"
            )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1")
        );
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
            "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/articles/hauptteil-1_art-9999?atIsoDate=2017-03-01T00:00:00.000Z"
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

    @Test
    void itThrowsValidationExceptionBecauseWithinTimeMachineMetaModDoesNotHaveSourceHref()
      throws Exception {
      // Given
      final Norm targetNorm = Norm
        .builder()
        .dokumente(
          Set.of(
            new Regelungstext(
              XmlMapper.toDocument(
                """
                     <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                    <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                       xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                           http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                   <akn:act name="regelungstext">

                      <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                         <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                            <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8a">
                               <akn:FRBRthis eId="meta-1_ident-1_frbrwork-1_frbrthis-1" GUID="f3805314-bbb6-4def-b82b-8b7f0b12619a" value="eli/bund/bgbl-1/1964/s593/regelungstext-1" />
                               <akn:FRBRuri eId="meta-1_ident-1_frbrwork-1_frbruri-1" GUID="f3805314-bbb6-4def-b82b-8b7f0b12619b" value="eli/bund/bgbl-1/1964/s593" />
                            </akn:FRBRWork>
                            <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                               <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="f3805314-bbb6-4def-b82b-8b7f0b126197" value="eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1" />
                               <akn:FRBRuri eId="meta-1_ident-1_frbrexpression-1_frbruri-1" GUID="f3805314-bbb6-4def-b82b-8b7f0b126199" value="eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu" />
                               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                            </akn:FRBRExpression>
                                <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1"
                                                     GUID="ea61dfec-d89c-442a-9f6d-cb65d8ed2dc3">
                                 <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"
                                               GUID="d74e4be8-c15d-4a9f-8ae6-781e522dc7a4"
                                                   value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23/regelungstext-1.xml"/>
                                 <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"
                                              GUID="6e12c94c-f206-4144-bedf-dcab30867f4c"
                                                  value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23/regelungstext-1.xml"/>
                                 <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"
                                               GUID="791a8124-d12e-45e1-9c80-5f0438e4d046"
                                               date="2022-08-23"
                                               name="generierung"/>
                                 <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"
                                                 GUID="f9d34cba-d819-4468-b6a7-4a3d76046a26"
                                                 href="recht.bund.de"/>
                                 <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"
                                                 GUID="dcf3aa47-de13-4ef6-9dce-1325a121fb4d"
                                                 value="xml"/>
                              </akn:FRBRManifestation>
                         </akn:identification>
                          <akn:lifecycle eId="meta-1_lebzykl-1" GUID="f551699c-7848-4a0d-ab11-1ef44f309504" source="attributsemantik-noch-undefiniert">
                                             <akn:eventRef eId="meta-1_lebzykl-1_ereignis-4" GUID="2f0febd4-edbe-4c02-9aca-827ee943ae28" date="2017-03-23" source="attributsemantik-noch-undefiniert" type="amendment" refersTo="inkrafttreten"/>
                                         </akn:lifecycle>
                                         <akn:analysis eId="meta-1_analysis-1" GUID="5a5d264e-431e-4dc1-b971-4bd81af8a0f4" source="attributsemantik-noch-undefiniert">
                                             <akn:passiveModifications eId="meta-1_analysis-1_pasmod-1" GUID="77aae58f-06c9-4189-af80-a5f3ada6432c">
                                                 <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-2" GUID="26b091d0-1bb9-4c83-b940-f6788b2922f2" type="substitution">
                                                     <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-2_source-1" GUID="a5e43d31-65e1-4d99-a1aa-fb4695a94cf5"/>
                                                             <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-2_destination-1" GUID="8c0418f1-b6fa-4110-8820-cf0db752c5bd" href="#hauptteil-1_art-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34"/>
                                                     <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-2_gelzeitnachw-1" GUID="e5962d3b-9bb8-4eb0-8d8f-131a5114fddb" period="#meta-1_geltzeiten-1_geltungszeitgr-2"/>
                                                 </akn:textualMod>
                                             </akn:passiveModifications>
                                         </akn:analysis>
                                         <akn:temporalData eId="meta-1_geltzeiten-1" GUID="2fcdfa3e-1460-4ef4-b22b-5ff4a897538f" source="attributsemantik-noch-undefiniert">
                                             <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-2" GUID="7af9337a-3727-424c-a3df-dee918a79b22">
                                                 <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1" GUID="826b00c9-1069-44fa-a5fd-a5676e56c2f1" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-4"/>
                                             </akn:temporalGroup>
                                         </akn:temporalData>
                      </akn:meta>
                   </akn:act>
                </akn:akomaNtoso>
                """
              )
            )
          )
        )
        .build();
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithMods.xml"))
      );
      dokumentRepository.save(DokumentMapper.mapToDto(targetNorm.getRegelungstext1()));

      // When / Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-20?atIsoDate=2017-03-23T00:00:00.000Z"
          )
            .accept(MediaType.TEXT_HTML)
        )
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type").value(equalTo("/errors/source-href-in-meta-mod-missing")))
        .andExpect(jsonPath("title").value(equalTo("Validation error")))
        .andExpect(jsonPath("status").value(equalTo(422)))
        .andExpect(
          jsonPath("detail")
            .value(
              equalTo(
                "Did not find source href for textual mod with eId meta-1_analysis-1_pasmod-1_textualmod-2"
              )
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              equalTo(
                "/api/v1/norms/eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-20"
              )
            )
        )
        .andExpect(jsonPath("eId").value(equalTo("meta-1_analysis-1_pasmod-1_textualmod-2")));
    }

    @Test
    void itThrowsMandatoryNodeNotFoundBecauseTemporalDataMissing() throws Exception {
      // Given
      final Norm targetNorm = Norm
        .builder()
        .dokumente(
          Set.of(
            new Regelungstext(
              XmlMapper.toDocument(
                """
                     <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                    <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                       xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                           http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                   <akn:act name="regelungstext">

                      <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                         <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                            <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8a">
                               <akn:FRBRthis eId="meta-1_ident-1_frbrwork-1_frbrthis-1" GUID="f3805314-bbb6-4def-b82b-8b7f0b12619a" value="eli/bund/bgbl-1/1964/s593/regelungstext-1" />
                               <akn:FRBRuri eId="meta-1_ident-1_frbrwork-1_frbruri-1" GUID="f3805314-bbb6-4def-b82b-8b7f0b12619b" value="eli/bund/bgbl-1/1964/s593" />
                            </akn:FRBRWork>
                            <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                               <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="f3805314-bbb6-4def-b82b-8b7f0b126197" value="eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1" />
                               <akn:FRBRuri eId="meta-1_ident-1_frbrexpression-1_frbruri-1" GUID="f3805314-bbb6-4def-b82b-8b7f0b126198" value="eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu" />
                               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                            </akn:FRBRExpression>
                                <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1"
                                                     GUID="ea61dfec-d89c-442a-9f6d-cb65d8ed2dc3">
                                 <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"
                                               GUID="d74e4be8-c15d-4a9f-8ae6-781e522dc7a4"
                                                   value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23/regelungstext-1.xml"/>
                                 <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"
                                              GUID="6e12c94c-f206-4144-bedf-dcab30867f4c"
                                                  value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23/regelungstext-1.xml"/>
                                 <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"
                                               GUID="791a8124-d12e-45e1-9c80-5f0438e4d046"
                                               date="2022-08-23"
                                               name="generierung"/>
                                 <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"
                                                 GUID="f9d34cba-d819-4468-b6a7-4a3d76046a26"
                                                 href="recht.bund.de"/>
                                 <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"
                                                 GUID="dcf3aa47-de13-4ef6-9dce-1325a121fb4d"
                                                 value="xml"/>
                              </akn:FRBRManifestation>
                         </akn:identification>
                          <akn:lifecycle eId="meta-1_lebzykl-1" GUID="f551699c-7848-4a0d-ab11-1ef44f309504" source="attributsemantik-noch-undefiniert">
                                             <akn:eventRef eId="meta-1_lebzykl-1_ereignis-4" GUID="2f0febd4-edbe-4c02-9aca-827ee943ae28" date="2017-03-23" source="attributsemantik-noch-undefiniert" type="amendment" refersTo="inkrafttreten"/>
                                         </akn:lifecycle>
                                         <akn:analysis eId="meta-1_analysis-1" GUID="5a5d264e-431e-4dc1-b971-4bd81af8a0f4" source="attributsemantik-noch-undefiniert">
                                             <akn:passiveModifications eId="meta-1_analysis-1_pasmod-1" GUID="77aae58f-06c9-4189-af80-a5f3ada6432c">
                                                 <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-2" GUID="26b091d0-1bb9-4c83-b940-f6788b2922f2" type="substitution">
                                                     <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-2_source-1" GUID="a5e43d31-65e1-4d99-a1aa-fb4695a94cf5"/>
                                                             <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-2_destination-1" GUID="8c0418f1-b6fa-4110-8820-cf0db752c5bd" href="#hauptteil-1_art-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34"/>
                                                     <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-2_gelzeitnachw-1" GUID="e5962d3b-9bb8-4eb0-8d8f-131a5114fddb" period="#meta-1_geltzeiten-1_geltungszeitgr-2"/>
                                                 </akn:textualMod>
                                             </akn:passiveModifications>
                                         </akn:analysis>
                      </akn:meta>
                   </akn:act>
                </akn:akomaNtoso>
                """
              )
            )
          )
        )
        .build();
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithMods.xml"))
      );
      dokumentRepository.save(DokumentMapper.mapToDto(targetNorm.getRegelungstext1()));

      // When / Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-20?atIsoDate=2017-03-23T00:00:00.000Z"
          )
            .accept(MediaType.TEXT_HTML)
        )
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type").value(equalTo("/errors/mandatory-node-not-found")))
        .andExpect(jsonPath("title").value(equalTo("Mandatory node not found")))
        .andExpect(jsonPath("status").value(equalTo(422)))
        .andExpect(
          jsonPath("detail")
            .value(
              equalTo(
                "Element with xpath './temporalData' not found in 'akn:meta' of norm 'eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1'"
              )
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              equalTo(
                "/api/v1/norms/eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-20"
              )
            )
        )
        .andExpect(jsonPath("xpath").value(equalTo("./temporalData")))
        .andExpect(
          jsonPath("eli")
            .value(equalTo("eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1"))
        )
        .andExpect(jsonPath("nodeName").value(equalTo("akn:meta")));
    }
  }
}
