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
    final TargetLaw targetLaw =
        TargetLaw.builder()
            .eli("target-law-eli")
            .title("target law article")
            .xml("<target></target>")
            .build();

    final TargetLaw targetLawZf0 =
        TargetLaw.builder()
            .eli("target-law-zf0-eli")
            .title("target law zf0 article")
            .xml("<target>zf0</target>")
            .build();

    final Article article1 =
        Article.builder()
            .enumeration("1")
            .eid("eid-article-1")
            .title("article title 1")
            .targetLaw(targetLaw)
            .targetLawZf0(targetLawZf0)
            .build();

    // When
    final ArticleResponseSchema resultArticle = ArticleResponseMapper.fromUseCaseData(article1);

    // Then
    assertThat(resultArticle.getEid()).isEqualTo("eid-article-1");
    assertThat(resultArticle.getEnumeration()).isEqualTo("1");
    assertThat(resultArticle.getTitle()).isEqualTo("article title 1");
    assertThat(resultArticle.getAffectedDocumentEli()).isEqualTo("target-law-eli");
    assertThat(resultArticle.getAffectedDocumentZf0Eli()).isEqualTo("target-law-zf0-eli");
  }
}
