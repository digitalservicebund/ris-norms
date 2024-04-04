package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.ArticleDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.TargetLawDto;
import de.bund.digitalservice.ris.norms.application.service.TimeMachineService;
import de.bund.digitalservice.ris.norms.application.service.XmlDocumentService;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import org.junit.jupiter.api.Test;

class ArticleMapperTest {

  final XmlDocumentService xmlDocumentService = new XmlDocumentService();
  final TimeMachineService timeMachineService = new TimeMachineService(xmlDocumentService);


  @Test
  void testMapToDomain() {
    // Given
    final TargetLawDto targetLawDto =
        TargetLawDto.builder().eli("123").title("Test Law").xml("<xml>Test XML</xml>").build();

    final TargetLawDto targetLawZf0Dto =
        TargetLawDto.builder().eli("123").title("Test Law").xml("<xml>Test XML ZF0</xml>").build();

    final ArticleDto articleDto =
        ArticleDto.builder()
            .enumeration("1")
            .title("Test Article")
            .eid("article-1")
            .targetLawDto(targetLawDto)
            .targetLawZf0Dto(targetLawZf0Dto)
            .build();

    // When
    final Article article = ArticleMapper.mapToDomain(articleDto);

    // Then
    assertNotNull(article);
    assertEquals(articleDto.getEnumeration(), article.getEnumeration());
    assertEquals(articleDto.getTitle(), article.getTitle());
    assertEquals(articleDto.getEid(), article.getEid());
    assertNotNull(article.getTargetLaw());
    assertEquals(targetLawDto.getEli(), article.getTargetLaw().getEli());
    assertEquals(targetLawDto.getTitle(), article.getTargetLaw().getTitle());
    assertEquals(targetLawDto.getXml(), article.getTargetLaw().getXml());
    assertEquals(targetLawZf0Dto.getEli(), article.getTargetLawZf0().getEli());
    assertEquals(targetLawZf0Dto.getTitle(), article.getTargetLawZf0().getTitle());
    assertEquals(targetLawZf0Dto.getXml(), article.getTargetLawZf0().getXml());
  }

  @Test
  void testMapToDto() {
    // Given
    final TargetLaw targetLaw =
        TargetLaw.builder()
            .eli("456")
            .title("Another Test Law")
            .xml(timeMachineService.stringToXmlDocument("<xml>Another Test XML</xml>"))
            .build();

    final Article article =
        Article.builder()
            .enumeration("2")
            .title("Another Test Article")
            .eid("article-2")
            .targetLaw(targetLaw)
            .build();

    // When
    final ArticleDto articleDto = ArticleMapper.mapToDto(article);

    // Then
    assertNotNull(articleDto);
    assertEquals(article.getEnumeration(), articleDto.getEnumeration());
    assertEquals(article.getTitle(), articleDto.getTitle());
    assertEquals(article.getEid(), articleDto.getEid());
    assertNotNull(articleDto.getTargetLawDto());
    assertEquals(targetLaw.getEli(), articleDto.getTargetLawDto().getEli());
    assertEquals(targetLaw.getTitle(), articleDto.getTargetLawDto().getTitle());
    assertEquals(targetLaw.getXml(), articleDto.getTargetLawDto().getXml());
  }
}
