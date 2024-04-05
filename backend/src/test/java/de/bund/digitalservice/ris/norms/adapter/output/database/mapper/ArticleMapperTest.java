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

  @Test
  void testMapToDomain() {
    // Given
    TimeMachineService timeMachineService = new TimeMachineService(new XmlDocumentService());
    final TargetLawDto targetLawDto =
        TargetLawDto.builder()
            .eli("123")
            .title("Test Law")
            .xml(timeMachineService.stringToXmlDocument("<?xml version=\"1.0\" encoding=\"UTF-8\"?><test>Test XML</test>"))
            .build();

    final TargetLawDto targetLawZf0Dto =
        TargetLawDto.builder()
            .eli("123")
            .title("Test Law")
            .xml(timeMachineService.stringToXmlDocument("<?xml version=\"1.0\" encoding=\"UTF-8\"?><test>Test XML ZF0</test>"))
            .build();

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
    assertEquals(
        targetLawDto.getXml(),
        timeMachineService.convertDocumentToString(article.getTargetLaw().getXml()));
    assertEquals(targetLawZf0Dto.getEli(), article.getTargetLawZf0().getEli());
    assertEquals(targetLawZf0Dto.getTitle(), article.getTargetLawZf0().getTitle());
    assertEquals(
        targetLawZf0Dto.getXml(),
        timeMachineService.convertDocumentToString(article.getTargetLawZf0().getXml()));
  }

  @Test
  void testMapToDto() {
    // Given
    TimeMachineService timeMachineService = new TimeMachineService(new XmlDocumentService());
    final TargetLaw targetLaw =
        TargetLaw.builder()
            .eli("456")
            .title("Another Test Law")
            .xml(
                timeMachineService.stringToXmlDocument(
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><test>Test XML</test>"))
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
    assertEquals(
        timeMachineService.convertDocumentToString(targetLaw.getXml()),
        articleDto.getTargetLawDto().getXml());
  }
}
