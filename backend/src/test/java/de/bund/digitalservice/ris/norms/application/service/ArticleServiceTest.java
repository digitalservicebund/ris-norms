package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadArticleHtmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ArticleServiceTest {

    final LoadNormPort loadNormPort = mock(LoadNormPort.class);
    final TimeMachineService timeMachineService = mock(TimeMachineService.class);
    final XsltTransformationService xsltTransformationService = mock(XsltTransformationService.class);
    final ArticleService articleService = new ArticleService(loadNormPort, timeMachineService, xsltTransformationService);

    @Test
    void returnResult() {
        // given
        var eli = "eli/bund/bgbl-1/2000/s1/1970-01-01/1/deu/regelungstext-1";
        var eid = "meta-1";
        // when
        var result = articleService.loadArticleHtml(new LoadArticleHtmlUseCase.Query(eli,eid, Optional.empty()));
        // then
        assertThat(result.isPresent());
    }

    @Test
    void returnEmptyIfNormNotFound() {
        // given
        var eli = "eli/bund/DOES_NOT_EXIST/2000/s1/1970-01-01/1/deu/regelungstext-1";
        var eid = "meta-1";
        when(loadNormPort.loadNorm(new LoadNormPort.Command(eli))).thenReturn(Optional.empty());
        // when
        var result = articleService.loadArticleHtml(new LoadArticleHtmlUseCase.Query(eli,eid, Optional.empty()));
        // then
        assertThat(result.isEmpty());
    }

    @Test
    void returnEmptyIfArticleNotFound(){
        // given
        var norm = NormFixtures.loadFromDisk("SimpleNorm.xml");
        var eli = "eli/bund/DOES_NOT_EXIST/2000/s1/1970-01-01/1/deu/regelungstext-1";
        var eid = "NOT_IN_NORM";
        when(loadNormPort.loadNorm(new LoadNormPort.Command(eli))).thenReturn(Optional.of(norm));
        when(loadNormPort.loadNorm(new LoadNormPort.Command(eli))).thenReturn(Optional.of(norm));
        // when
        var result = articleService.loadArticleHtml(new LoadArticleHtmlUseCase.Query(eli,eid, Optional.empty()));
        // then
        assertThat(result.isEmpty());
    }
}
