package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ArticleResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import org.junit.jupiter.api.Test;

class ArticleResponseMapperTest {

  @Test
  void canMapSimpleResponseSchema() {
    // Given
    final TargetLaw targetLaw1 =
        TargetLaw.builder()
            .eli("target-law-eli-1")
            .title("target law article 1")
            .xml("<target>1</target>")
            .build();

    final Article article1 =
        Article.builder()
            .enumeration("1")
            .eid("eid-article-1")
            .title("article title 1")
            .targetLaw(targetLaw1)
            .build();

    // When
    final ArticleResponseSchema resultArticle = ArticleResponseMapper.fromUseCaseData(article1);

    // Then
    assertThat(resultArticle.getEid()).isEqualTo("eid-article-1");
    assertThat(resultArticle.getEnumeration()).isEqualTo("1");
    assertThat(resultArticle.getTitle()).isEqualTo("article title 1");
    assertThat(resultArticle.getAffectedDocumentEli()).isEqualTo("target-law-eli-1");
  }
}
