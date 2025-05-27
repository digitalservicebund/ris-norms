package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import de.bund.digitalservice.ris.norms.application.exception.ArticleNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.RegelungstextNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadArticleHtmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadArticlesFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadSpecificArticlesXmlFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadRegelungstextPort;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ArticleServiceTest {

  final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  final LoadRegelungstextPort loadRegelungstextPort = mock(LoadRegelungstextPort.class);
  final XsltTransformationService xsltTransformationService = mock(XsltTransformationService.class);
  final ArticleService articleService = new ArticleService(
    loadRegelungstextPort,
    xsltTransformationService
  );

  @Nested
  class loadArticleHtml {

    @Test
    void returnResult() {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2000/s1/1970-01-01/1/deu/regelungstext-1"
      );
      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );
      var eid = new EId("hauptteil-1_art-1");
      when(
        loadRegelungstextPort.loadRegelungstext(new LoadRegelungstextPort.Options(eli))
      ).thenReturn(Optional.of(regelungstext));
      when(xsltTransformationService.transformLegalDocMlToHtml(any())).thenReturn("<div></div>");

      // when
      var result = articleService.loadArticleHtml(new LoadArticleHtmlUseCase.Options(eli, eid));

      // then
      assertThat(result).isNotNull();
    }

    @Test
    void throwsIfNormNotFound() {
      // given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/DOES_NOT_EXIST/2000/s1/1970-01-01/1/deu/regelungstext-1"
      );
      var eid = new EId("meta-1");
      var query = new LoadArticleHtmlUseCase.Options(eli, eid);
      when(
        loadRegelungstextPort.loadRegelungstext(new LoadRegelungstextPort.Options(eli))
      ).thenReturn(Optional.empty());

      // when
      assertThatThrownBy(() -> articleService.loadArticleHtml(query)).isInstanceOf(
        // then
        NormNotFoundException.class
      );
    }

    @Test
    void throwsIfArticleNotFound() {
      // given
      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/DOES_NOT_EXIST/2000/s1/1970-01-01/1/deu/regelungstext-1"
      );
      var eid = new EId("eid-1_not-1_in-1_norm-1");
      var query = new LoadArticleHtmlUseCase.Options(eli, eid);
      when(
        loadRegelungstextPort.loadRegelungstext(new LoadRegelungstextPort.Options(eli))
      ).thenReturn(Optional.of(regelungstext));
      when(
        loadRegelungstextPort.loadRegelungstext(new LoadRegelungstextPort.Options(eli))
      ).thenReturn(Optional.of(regelungstext));

      // when
      assertThatThrownBy(() -> articleService.loadArticleHtml(query)).isInstanceOf(
        // then
        ArticleNotFoundException.class
      );
    }
  }

  @Nested
  class loadArticlesFromDokument {

    @Test
    void itReturnsArticlesFromNorm() {
      // Given
      final var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      final var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      final var query = new LoadArticlesFromDokumentUseCase.Options(eli);

      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.of(regelungstext));

      // When
      final var articles = articleService.loadArticlesFromDokument(query);

      // Then
      assertThat(articles).hasSize(2);
      assertThat(articles.get(0).getEid()).hasToString("hauptteil-1_art-1");
      assertThat(articles.get(1).getEid()).hasToString("hauptteil-1_art-2");
    }

    @Test
    void itThrowsWhenTheDokumentIsNotFound() {
      // Given
      final var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1"
      );
      final var query = new LoadArticlesFromDokumentUseCase.Options(eli);

      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.empty());

      // When / Then
      assertThatThrownBy(() -> articleService.loadArticlesFromDokument(query)).isInstanceOf(
        RegelungstextNotFoundException.class
      );
    }

    @Test
    void itReturnsEmptyListWhenTheNormHasNoArticles() {
      // Given
      final var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      final var regelungstext = Fixtures.loadRegelungstextFromDisk(
        ArticleServiceTest.class,
        "vereinsgesetz-without-articles/regelungstext-verkuendung-1.xml"
      );
      final var query = new LoadArticlesFromDokumentUseCase.Options(eli);

      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.of(regelungstext));

      // When
      final var articles = articleService.loadArticlesFromDokument(query);

      // Then
      assertThat(articles).isEmpty();
    }
  }

  @Nested
  class loadSpecificArticlesXmlFromDokument {

    @Test
    void loadAllArticles() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
      );
      final var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );

      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.of(regelungstext));

      // When
      var xmls = articleService.loadSpecificArticlesXmlFromDokument(
        new LoadSpecificArticlesXmlFromDokumentUseCase.Options(eli, null)
      );

      // Then
      verify(loadRegelungstextPort, times(1)).loadRegelungstext(
        argThat(argument -> Objects.equals(argument.eli(), eli))
      );
      assertThat(xmls).isNotEmpty();
      assertThat(xmls.getFirst()).contains("hauptteil-1_art-1");
      assertThat(xmls.get(1)).contains("hauptteil-1_art-2");
    }

    @Test
    void itCallsLoadDokumentAndThrowsIfNotFound() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      var query = new LoadSpecificArticlesXmlFromDokumentUseCase.Options(eli, "geltungszeitregel");
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.empty());

      // When
      assertThatThrownBy(() ->
        articleService.loadSpecificArticlesXmlFromDokument(query)
      ).isInstanceOf(NormNotFoundException.class); // Then

      verify(loadRegelungstextPort, times(1)).loadRegelungstext(
        argThat(argument -> Objects.equals(argument.eli(), eli))
      );
    }

    @Test
    void loadSpecificArticles() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
      );
      final var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );

      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.of(regelungstext));

      // When
      var xmls = articleService.loadSpecificArticlesXmlFromDokument(
        new LoadSpecificArticlesXmlFromDokumentUseCase.Options(eli, "geltungszeitregel")
      );

      // Then
      verify(loadRegelungstextPort, times(1)).loadRegelungstext(
        argThat(argument -> Objects.equals(argument.eli(), eli))
      );
      assertThat(xmls).isNotEmpty();
      assertThat(xmls.getFirst()).contains("hauptteil-1_art-2");
    }

    @Test
    void itThrowsWhenNoArticlesOfTypeAreFound() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      var query = new LoadSpecificArticlesXmlFromDokumentUseCase.Options(eli, "geltungszeitregel");

      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        ArticleServiceTest.class,
        "regelungstext-without-geltungszeitregel/regelungstext-verkuendung-1.xml"
      );
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.of(regelungstext));

      // When
      assertThatThrownBy(() ->
        articleService.loadSpecificArticlesXmlFromDokument(query)
      ).isInstanceOf(
        // Then
        LoadSpecificArticlesXmlFromDokumentUseCase.ArticleOfTypeNotFoundException.class
      );

      verify(loadRegelungstextPort, times(1)).loadRegelungstext(
        argThat(argument -> Objects.equals(argument.eli(), eli))
      );
    }

    @Test
    void itThrowsWhenTheDokumentHasNoArticles() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      var query = new LoadSpecificArticlesXmlFromDokumentUseCase.Options(eli, "geltungszeitregel");
      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        ArticleServiceTest.class,
        "vereinsgesetz-without-articles/regelungstext-verkuendung-1.xml"
      );

      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.of(regelungstext));

      // When
      assertThatThrownBy(() ->
        articleService.loadSpecificArticlesXmlFromDokument(query)
      ).isInstanceOf(
        // Then
        LoadSpecificArticlesXmlFromDokumentUseCase.ArticleOfTypeNotFoundException.class
      );

      verify(loadRegelungstextPort, times(1)).loadRegelungstext(
        argThat(argument -> Objects.equals(argument.eli(), eli))
      );
    }
  }
}
