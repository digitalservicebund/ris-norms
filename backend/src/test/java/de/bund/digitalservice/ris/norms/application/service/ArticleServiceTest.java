package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.bund.digitalservice.ris.norms.application.exception.ArticleNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadArticleHtmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ArticleServiceTest {

  final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  final TimeMachineService timeMachineService = mock(TimeMachineService.class);
  final XsltTransformationService xsltTransformationService = mock(XsltTransformationService.class);
  final ArticleService articleService =
      new ArticleService(loadNormPort, timeMachineService, xsltTransformationService);

  @Nested
  class loadArticleHtml {

    @Test
    void returnResult() {
      // given
      var eli = "eli/bund/bgbl-1/2000/s1/1970-01-01/1/deu/regelungstext-1";
      var norm = NormFixtures.loadFromDisk("NormWithMods.xml");
      var eid = "hauptteil-1_art-1";
      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli))).thenReturn(Optional.of(norm));
      when(timeMachineService.applyPassiveModifications(any())).thenReturn(norm);
      when(xsltTransformationService.transformLegalDocMlToHtml(any())).thenReturn("<div></div>");
      // when
      var result = articleService.loadArticleHtml(new LoadArticleHtmlUseCase.Query(eli, eid));
      // then
      assertThat(result).isNotNull();
    }

    @Test
    void throwsIfNormNotFound() {
      // given
      var eli = "eli/bund/DOES_NOT_EXIST/2000/s1/1970-01-01/1/deu/regelungstext-1";
      var eid = "meta-1";
      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli))).thenReturn(Optional.empty());
      // when
      assertThatThrownBy(
              () -> articleService.loadArticleHtml(new LoadArticleHtmlUseCase.Query(eli, eid)))
          // then
          .isInstanceOf(NormNotFoundException.class);
    }

    @Test
    void throwsIfArticleNotFound() {
      // given
      var norm = NormFixtures.loadFromDisk("SimpleNorm.xml");
      var eli = "eli/bund/DOES_NOT_EXIST/2000/s1/1970-01-01/1/deu/regelungstext-1";
      var eid = "NOT_IN_NORM";
      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli))).thenReturn(Optional.of(norm));
      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli))).thenReturn(Optional.of(norm));
      // when
      assertThatThrownBy(
              () -> articleService.loadArticleHtml(new LoadArticleHtmlUseCase.Query(eli, eid)))
          // then
          .isInstanceOf(ArticleNotFoundException.class);
    }
  }
}
