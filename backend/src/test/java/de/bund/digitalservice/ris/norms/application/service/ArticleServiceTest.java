package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import de.bund.digitalservice.ris.norms.application.exception.ArticleNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadArticleHtmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadArticlesFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadSpecificArticlesXmlFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.Objects;
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
      var query = new LoadArticleHtmlUseCase.Query(eli, eid);
      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli))).thenReturn(Optional.empty());

      // when
      assertThatThrownBy(() -> articleService.loadArticleHtml(query))

          // then
          .isInstanceOf(NormNotFoundException.class);
    }

    @Test
    void throwsIfArticleNotFound() {
      // given
      var norm = NormFixtures.loadFromDisk("SimpleNorm.xml");
      var eli = "eli/bund/DOES_NOT_EXIST/2000/s1/1970-01-01/1/deu/regelungstext-1";
      var eid = "NOT_IN_NORM";
      var query = new LoadArticleHtmlUseCase.Query(eli, eid);
      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli))).thenReturn(Optional.of(norm));
      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli))).thenReturn(Optional.of(norm));

      // when
      assertThatThrownBy(() -> articleService.loadArticleHtml(query))

          // then
          .isInstanceOf(ArticleNotFoundException.class);
    }
  }

  @Nested
  class loadArticlesFromNorm {
    @Test
    void itReturnsArticlesFromNorm() {
      // Given
      final var eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final var norm = NormFixtures.loadFromDisk("NormWithMultiplePassiveModifications.xml");
      final var query = new LoadArticlesFromNormUseCase.Query(eli);

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      final var articles = articleService.loadArticlesFromNorm(query);

      // Then
      assertThat(articles).hasSize(2);
      assertThat(articles.get(0).getEid()).contains("hauptteil-1_para-20");
      assertThat(articles.get(1).getEid()).contains("hauptteil-1_para-1");
    }

    @Test
    void itFiltersArticlesByAmendedBy() {
      // Given
      final var eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final var amendedBy = "eli/bund/bgbl-1/2017/s815/1995-03-15/1/deu/regelungstext-1";
      final String amendedAt = null;
      final var norm =
          NormFixtures.loadFromDisk("NormWithPassiveModificationsInDifferentArticles.xml");
      final var query = new LoadArticlesFromNormUseCase.Query(eli, amendedBy, amendedAt);

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      final var articles = articleService.loadArticlesFromNorm(query);

      // Then
      assertThat(articles).hasSize(1);
      assertThat(articles.getFirst().getEid()).contains("hauptteil-1_para-1");
    }

    @Test
    void itFiltersArticlesByAmendedAt() {
      // Given
      final var eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String amendedBy = null;
      final var amendedAt = "meta-1_lebzykl-1_ereignis-4";
      final var norm = NormFixtures.loadFromDisk("NormWithMultiplePassiveModifications.xml");
      final var query = new LoadArticlesFromNormUseCase.Query(eli, amendedBy, amendedAt);

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      final var articles = articleService.loadArticlesFromNorm(query);

      // Then
      assertThat(articles).hasSize(1);
      assertThat(articles.getFirst().getEid()).contains("hauptteil-1_para-20");
    }

    @Test
    void itFiltersArticlesByAmendedByAndAmendedAt() {
      // Given
      final var eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final var amendedBy = "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1";
      final var amendedAt = "meta-1_lebzykl-1_ereignis-4";
      final var norm = NormFixtures.loadFromDisk("NormWithMultiplePassiveModifications.xml");
      final var query = new LoadArticlesFromNormUseCase.Query(eli, amendedBy, amendedAt);

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      final var articles = articleService.loadArticlesFromNorm(query);

      // Then
      assertThat(articles).hasSize(1);
      assertThat(articles.getFirst().getEid()).contains("hauptteil-1_para-20");
    }

    @Test
    void itThrowsWhenTheNormIsNotFound() {
      // Given
      final var eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final var query = new LoadArticlesFromNormUseCase.Query(eli);

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());

      // When / Then
      assertThatThrownBy(() -> articleService.loadArticlesFromNorm(query))
          .isInstanceOf(NormNotFoundException.class);
    }

    @Test
    void itReturnsEmptyListWhenTheNormHasNoArticles() {
      // Given
      final var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      final var norm = NormFixtures.loadFromDisk("SimpleNorm.xml");
      final var query = new LoadArticlesFromNormUseCase.Query(eli);

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      final var articles = articleService.loadArticlesFromNorm(query);

      // Then
      assertThat(articles).isEmpty();
    }

    @Test
    void itReturnsEmptyListWhenAmendedByIsNotFound() {
      // Given
      final var eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final var amendedBy = "eli/bund/DOES-NOT-EXIST/2017/s419/2017-03-15/1/deu/regelungstext-1";
      final var amendedAt = "meta-1_lebzykl-1_ereignis-4";
      final var norm = NormFixtures.loadFromDisk("NormWithMultiplePassiveModifications.xml");
      final var query = new LoadArticlesFromNormUseCase.Query(eli, amendedBy, amendedAt);

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      final var articles = articleService.loadArticlesFromNorm(query);

      // Then
      assertThat(articles).isEmpty();
    }

    @Test
    void itReturnsEmptyListWhenAmendedAtIsNotFound() {
      // Given
      final var eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final var amendedBy = "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1";
      final var amendedAt = "DOES-NOT-EXIST";
      final var norm = NormFixtures.loadFromDisk("NormWithMultiplePassiveModifications.xml");
      final var query = new LoadArticlesFromNormUseCase.Query(eli, amendedBy, amendedAt);

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      final var articles = articleService.loadArticlesFromNorm(query);

      // Then
      assertThat(articles).isEmpty();
    }

    @Test
    void itReturnsEmptyListWhenNormHasNoPassiveMods() {
      // Given
      final var eli = "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1";
      final var amendedBy = "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1";
      final var amendedAt = "meta-1_lebzykl-1_ereignis-4";
      final var norm = NormFixtures.loadFromDisk("NormWithMods.xml");
      final var query = new LoadArticlesFromNormUseCase.Query(eli, amendedBy, amendedAt);

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      final var articles = articleService.loadArticlesFromNorm(query);

      // Then
      assertThat(articles).isEmpty();
    }
  }

  @Nested
  class loadSpecificArticlesXmlFromNorm {

    @Test
    void loadAllArticles() {
      // Given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";

      var norm =
          Norm.builder()
              .document(
                  XmlMapper.toDocument(
                      """
                        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                           xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                               http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                           <akn:act name="regelungstext">
                              <akn:body eId="hauptteil-1" GUID="0B4A8E1F-65EF-4B7C-9E22-E83BA6B73CD8">
                                       <!-- Artikel 1 : Hauptänderung -->
                                       <akn:article eId="hauptteil-1_art-1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#geltungszeitgr-1" refersTo="hauptaenderung">
                                          Some Text
                                       </akn:article>

                                       <!-- Artikel 3: Geltungszeitregel-->
                                       <akn:article eId="hauptteil-1_art-3" GUID="aaae12b5-0c74-4e51-a286-d6051ff5d21b" period="#geltungszeitgr-1" refersTo="geltungszeitregel">
                                          More Text
                                       </akn:article>
                                    </akn:body>
                           </akn:act>
                        </akn:akomaNtoso>
                      """))
              .build();
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      var xmls =
          articleService.loadSpecificArticlesXmlFromNorm(
              new LoadSpecificArticlesXmlFromNormUseCase.Query(eli, null));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      assertThat(xmls).isNotEmpty();
      assertThat(xmls.getFirst()).contains("hauptteil-1_art-1");
      assertThat(xmls.get(1)).contains("hauptteil-1_art-3");
    }

    @Test
    void itCallsLoadNormAndThrowsIfNotFound() {
      // Given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      var query = new LoadSpecificArticlesXmlFromNormUseCase.Query(eli, "geltungszeitregel");
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());

      // When
      assertThatThrownBy(() -> articleService.loadSpecificArticlesXmlFromNorm(query))

          // Then
          .isInstanceOf(NormNotFoundException.class);

      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
    }

    @Test
    void loadSpecificArticles() {
      // Given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";

      var norm =
          Norm.builder()
              .document(
                  XmlMapper.toDocument(
                      """
                        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                           xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                               http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                           <akn:act name="regelungstext">
                              <akn:body eId="hauptteil-1" GUID="0B4A8E1F-65EF-4B7C-9E22-E83BA6B73CD8">
                                       <!-- Artikel 1 : Hauptänderung -->
                                       <akn:article eId="hauptteil-1_art-1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#geltungszeitgr-1" refersTo="hauptaenderung">
                                          Some Text
                                       </akn:article>

                                       <!-- Artikel 3: Geltungszeitregel-->
                                       <akn:article eId="hauptteil-1_art-3" GUID="aaae12b5-0c74-4e51-a286-d6051ff5d21b" period="#geltungszeitgr-1" refersTo="geltungszeitregel">
                                          More Text
                                       </akn:article>
                                    </akn:body>
                           </akn:act>
                        </akn:akomaNtoso>
                      """))
              .build();
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      var xmls =
          articleService.loadSpecificArticlesXmlFromNorm(
              new LoadSpecificArticlesXmlFromNormUseCase.Query(eli, "geltungszeitregel"));

      // Then
      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      assertThat(xmls).isNotEmpty();
      assertThat(xmls.getFirst()).contains("hauptteil-1_art-3");
    }

    @Test
    void itThrowsWhenNoArticlesOfTypeAreFoundInNorm() {
      // Given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      var query = new LoadSpecificArticlesXmlFromNormUseCase.Query(eli, "geltungszeitregel");

      var norm =
          Norm.builder()
              .document(
                  XmlMapper.toDocument(
                      """
                        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                           xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                               http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                           <akn:act name="regelungstext">
                              <akn:body eId="hauptteil-1" GUID="0B4A8E1F-65EF-4B7C-9E22-E83BA6B73CD8">
                                       <!-- Artikel 1 : Hauptänderung -->
                                       <akn:article eId="hauptteil-1_art-1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#geltungszeitgr-1" refersTo="hauptaenderung">
                                          Some Text
                                       </akn:article>
                                    </akn:body>
                           </akn:act>
                        </akn:akomaNtoso>
                      """))
              .build();
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      assertThatThrownBy(() -> articleService.loadSpecificArticlesXmlFromNorm(query))

          // Then
          .isInstanceOf(
              LoadSpecificArticlesXmlFromNormUseCase.ArticleOfTypeNotFoundException.class);

      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
    }

    @Test
    void itThrowsWhenTheNormHasNoArticles() {
      // Given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      var query = new LoadSpecificArticlesXmlFromNormUseCase.Query(eli, "geltungszeitregel");

      var norm =
          Norm.builder()
              .document(
                  XmlMapper.toDocument(
                      """
                        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                           xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                               http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                           <akn:act name="regelungstext">
                              <akn:body eId="hauptteil-1" GUID="0B4A8E1F-65EF-4B7C-9E22-E83BA6B73CD8">
                              </akn:body>
                           </akn:act>
                        </akn:akomaNtoso>
                      """))
              .build();
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      assertThatThrownBy(() -> articleService.loadSpecificArticlesXmlFromNorm(query))

          // Then
          .isInstanceOf(
              LoadSpecificArticlesXmlFromNormUseCase.ArticleOfTypeNotFoundException.class);

      verify(loadNormPort, times(1))
          .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
    }
  }
}
