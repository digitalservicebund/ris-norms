package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AmendingLawDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.ArticleDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.TargetLawDto;
import de.bund.digitalservice.ris.norms.application.service.TimeMachineService;
import de.bund.digitalservice.ris.norms.application.service.XmlDocumentService;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class AmendingLawMapperTest {

  final XmlDocumentService xmlDocumentService = new XmlDocumentService();
  final TimeMachineService timeMachineService = new TimeMachineService(xmlDocumentService);


  @Test
  void testMapToDomain() {
    // Given
    final LocalDate now = LocalDate.now();

    final TargetLawDto targetLawDto =
        TargetLawDto.builder()
            .eli("target law eli")
            .title("target law title")
            .xml("<test></test>")
            .build();

    final TargetLawDto targetLawZf0Dto =
        TargetLawDto.builder()
            .eli("target law eli zf0")
            .title("target law title zf0")
            .xml("<test>zf0</test>")
            .build();

    final List<ArticleDto> articles = new ArrayList<>();
    articles.add(
        ArticleDto.builder()
            .enumeration("1234")
            .eid("eid")
            .title("title")
            .targetLawDto(targetLawDto)
            .targetLawZf0Dto(targetLawZf0Dto)
            .build());

    final AmendingLawDto amendingLawDTO = new AmendingLawDto();
    amendingLawDTO.setEli("ELI");
    amendingLawDTO.setPrintAnnouncementGazette("GAZETTE");
    amendingLawDTO.setDigitalAnnouncementMedium("MEDIUM");
    amendingLawDTO.setPublicationDate(now);
    amendingLawDTO.setPrintAnnouncementPage("PAGE");
    amendingLawDTO.setDigitalAnnouncementEdition("EDITION");
    amendingLawDTO.setTitle("TITLE");
    amendingLawDTO.setArticleDtos(articles);

    // When
    final AmendingLaw amendingLaw = AmendingLawMapper.mapToDomainWithArticles(amendingLawDTO);

    // Then
    assertThat(amendingLaw.getEli()).isEqualTo("ELI");
    assertThat(amendingLaw.getPrintAnnouncementGazette()).isEqualTo("GAZETTE");
    assertThat(amendingLaw.getDigitalAnnouncementMedium()).isEqualTo("MEDIUM");
    assertThat(amendingLaw.getPublicationDate()).isEqualTo(now);
    assertThat(amendingLaw.getPrintAnnouncementPage()).isEqualTo("PAGE");
    assertThat(amendingLaw.getDigitalAnnouncementEdition()).isEqualTo("EDITION");
    assertThat(amendingLaw.getTitle()).isEqualTo("TITLE");
    final Article article = amendingLaw.getArticles().getFirst();
    assertThat(article.getEnumeration()).isEqualTo("1234");
    assertThat(article.getEid()).isEqualTo("eid");
    assertThat(article.getTitle()).isEqualTo("title");
    assertThat(article.getTargetLaw().getEli()).isEqualTo("target law eli");
    assertThat(article.getTargetLaw().getTitle()).isEqualTo("target law title");
    assertThat(article.getTargetLaw().getXml()).isEqualTo("<test></test>");
  }

  @Test
  void testMapToDto() {
    // Given
    final LocalDate now = LocalDate.now();

    final TargetLaw targetLaw =
        TargetLaw.builder()
            .eli("target law eli")
            .title("target law title")
            .xml(timeMachineService.stringToXmlDocument("<test></test>"))
            .build();

    final TargetLaw targetLawZf0 =
        TargetLaw.builder()
            .eli("target law zf0 eli")
            .title("target law zf0 title")
            .xml(timeMachineService.stringToXmlDocument("<test>zf0</test>"))
            .build();

    final List<Article> articles = new ArrayList<>();
    articles.add(new Article("1234", "eid", "title", targetLaw, targetLawZf0));

    final AmendingLaw amendingLaw =
        AmendingLaw.builder()
            .eli("ELI")
            .printAnnouncementGazette("GAZETTE")
            .digitalAnnouncementMedium("MEDIUM")
            .publicationDate(now)
            .printAnnouncementPage("PAGE")
            .digitalAnnouncementEdition("EDITION")
            .title("TITLE")
            .articles(articles)
            .build();

    // When
    final AmendingLawDto resultAmendingLawDto = AmendingLawMapper.mapToDto(amendingLaw);

    // Then
    assertThat(resultAmendingLawDto.getEli()).isEqualTo("ELI");
    assertThat(resultAmendingLawDto.getPrintAnnouncementGazette()).isEqualTo("GAZETTE");
    assertThat(resultAmendingLawDto.getDigitalAnnouncementMedium()).isEqualTo("MEDIUM");
    assertThat(resultAmendingLawDto.getPublicationDate()).isEqualTo(now);
    assertThat(resultAmendingLawDto.getPrintAnnouncementPage()).isEqualTo("PAGE");
    assertThat(resultAmendingLawDto.getTitle()).isEqualTo("TITLE");
    assertThat(resultAmendingLawDto.getArticleDtos().getFirst().getTitle()).isEqualTo("title");
    final ArticleDto articleDto = resultAmendingLawDto.getArticleDtos().getFirst();
    assertThat(articleDto.getEnumeration()).isEqualTo("1234");
    assertThat(articleDto.getEid()).isEqualTo("eid");
    assertThat(articleDto.getTitle()).isEqualTo("title");

    assertThat(articleDto.getTargetLawDto().getEli()).isEqualTo("target law eli");
    assertThat(articleDto.getTargetLawDto().getTitle()).isEqualTo("target law title");
    assertThat(articleDto.getTargetLawDto().getXml()).isEqualTo("<test></test>");

    assertThat(articleDto.getTargetLawZf0Dto().getEli()).isEqualTo("target law zf0 eli");
    assertThat(articleDto.getTargetLawZf0Dto().getTitle()).isEqualTo("target law zf0 title");
    assertThat(articleDto.getTargetLawZf0Dto().getXml()).isEqualTo("<test>zf0</test>");
  }
}
