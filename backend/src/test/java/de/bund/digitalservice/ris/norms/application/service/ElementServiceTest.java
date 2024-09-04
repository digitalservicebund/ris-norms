package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.exception.ElementNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ElementServiceTest {
  final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  final XsltTransformationService xsltTransformationService = mock(XsltTransformationService.class);
  final TimeMachineService timeMachineService = mock(TimeMachineService.class);

  final ElementService service =
      new ElementService(loadNormPort, xsltTransformationService, timeMachineService);

  @Nested
  class loadElementFromNorm {
    @Test
    void itLoadsAnElementFromANorm() {
      // Given
      var eli = "eli/bund/bgbl-1/2000/s1/1970-01-01/1/deu/regelungstext-1";
      var eid = "meta-1";

      var normXml =
          """
              <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
              <akn:akomaNtoso
                xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd">
                <akn:act name="regelungstext">
                  <!-- Metadaten -->
                  <akn:meta eId="meta-1" GUID="000">
                    <akn:identification eId="meta-1_ident-1" GUID="000" source="attributsemantik-noch-undefiniert">
                      <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="000">
                        <akn:FRBRthis
                          eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"
                          GUID="000"
                          value="eli/bund/bgbl-1/2000/s1/1970-01-01/1/deu/regelungstext-1"
                        />
                      </akn:FRBRExpression>
                    </akn:identification>
                  </akn:meta>
                </akn:act>
              </akn:akomaNtoso>
              """;

      var norm = new Norm(XmlMapper.toDocument(normXml));
      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli))).thenReturn(Optional.of(norm));

      // When
      var element = service.loadElementFromNorm(new LoadElementFromNormUseCase.Query(eli, eid));

      // Then
      assertThat(NodeParser.getValueFromExpression("./@eId", element)).contains(eid);

      verify(loadNormPort).loadNorm(new LoadNormPort.Command(eli));
    }

    @Test
    void itThrowsIfNormIsNotFound() {
      // Given
      var eli = "eli/bund/notfound/2000/s1/1970-01-01/1/deu/regelungstext-1";
      var eid = "meta-1";
      var query = new LoadElementFromNormUseCase.Query(eli, eid);

      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli))).thenReturn(Optional.empty());

      // When / Then
      assertThatThrownBy(() -> service.loadElementFromNorm(query))
          .isInstanceOf(NormNotFoundException.class);

      verify(loadNormPort).loadNorm(new LoadNormPort.Command(eli));
    }

    @Test
    void itThrowsIfNoElementIsFound() {
      // Given
      var eli = "eli/bund/bgbl-1/2000/s1/1970-01-01/1/deu/regelungstext-1";
      var eid = "meta-1000";
      var query = new LoadElementFromNormUseCase.Query(eli, eid);

      var normXml =
          """
              <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
              <akn:akomaNtoso
                xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd">
                <akn:act name="regelungstext">
                  <!-- Metadaten -->
                  <akn:meta eId="meta-1" GUID="000">
                    <akn:identification eId="meta-1_ident-1" GUID="000" source="attributsemantik-noch-undefiniert">
                      <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="000">
                        <akn:FRBRthis
                          eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"
                          GUID="000"
                          value="eli/bund/bgbl-1/2000/s1/1970-01-01/1/deu/regelungstext-1"
                        />
                      </akn:FRBRExpression>
                    </akn:identification>
                  </akn:meta>
                </akn:act>
              </akn:akomaNtoso>
              """;

      var norm = new Norm(XmlMapper.toDocument(normXml));
      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli))).thenReturn(Optional.of(norm));

      // When / Then
      assertThatThrownBy(() -> service.loadElementFromNorm(query))
          .isInstanceOf(ElementNotFoundException.class);

      verify(loadNormPort).loadNorm(new LoadNormPort.Command(eli));
    }
  }

  @Nested
  class loadElementHtmlFromNorm {
    @Test
    void itLoadsTheElementHtmlFromANorm() {
      // Given
      var eli = "eli/bund/bgbl-1/2000/s1/1970-01-01/1/deu/regelungstext-1";
      var eid = "meta-1";

      var normXml =
          """
              <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
              <akn:akomaNtoso
                xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd">
                <akn:act name="regelungstext">
                  <!-- Metadaten -->
                  <akn:meta eId="meta-1" GUID="000">
                    <akn:identification eId="meta-1_ident-1" GUID="000" source="attributsemantik-noch-undefiniert">
                      <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="000">
                        <akn:FRBRthis
                          eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"
                          GUID="000"
                          value="eli/bund/bgbl-1/2000/s1/1970-01-01/1/deu/regelungstext-1"
                        />
                      </akn:FRBRExpression>
                    </akn:identification>
                  </akn:meta>
                </akn:act>
              </akn:akomaNtoso>
              """;

      var norm = new Norm(XmlMapper.toDocument(normXml));
      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli))).thenReturn(Optional.of(norm));
      when(xsltTransformationService.transformLegalDocMlToHtml(any())).thenReturn("<div></div>");

      // When
      var html =
          service.loadElementHtmlFromNorm(new LoadElementHtmlFromNormUseCase.Query(eli, eid));

      // Then
      assertThat(html).contains("<div></div>");

      verify(loadNormPort).loadNorm(new LoadNormPort.Command(eli));
      verify(xsltTransformationService, times(1)).transformLegalDocMlToHtml(any());
    }

    @Test
    void itThrowsIfNoNormIsFound() {
      // Given
      var eli = "eli/bund/notfound/2000/s1/1970-01-01/1/deu/regelungstext-1";
      var eid = "meta-1";
      var query = new LoadElementHtmlFromNormUseCase.Query(eli, eid);

      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli))).thenReturn(Optional.empty());

      // When / Then
      assertThatThrownBy(() -> service.loadElementHtmlFromNorm(query))
          .isInstanceOf(NormNotFoundException.class);

      verify(loadNormPort).loadNorm(new LoadNormPort.Command(eli));
    }

    @Test
    void itThrowsIfNoElementIsFound() {
      // Given
      var eli = "eli/bund/bgbl-1/2000/s1/1970-01-01/1/deu/regelungstext-1";
      var eid = "meta-1000";
      var query = new LoadElementHtmlFromNormUseCase.Query(eli, eid);

      var normXml =
          """
              <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
              <akn:akomaNtoso
                xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd">
                <akn:act name="regelungstext">
                  <!-- Metadaten -->
                  <akn:meta eId="meta-1" GUID="000">
                    <akn:identification eId="meta-1_ident-1" GUID="000" source="attributsemantik-noch-undefiniert">
                      <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="000">
                        <akn:FRBRthis
                          eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"
                          GUID="000"
                          value="eli/bund/bgbl-1/2000/s1/1970-01-01/1/deu/regelungstext-1"
                        />
                      </akn:FRBRExpression>
                    </akn:identification>
                  </akn:meta>
                </akn:act>
              </akn:akomaNtoso>
              """;

      var norm = new Norm(XmlMapper.toDocument(normXml));
      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli))).thenReturn(Optional.of(norm));
      when(xsltTransformationService.transformLegalDocMlToHtml(any())).thenReturn("<div></div>");

      // When / Then
      assertThatThrownBy(() -> service.loadElementHtmlFromNorm(query))
          .isInstanceOf(ElementNotFoundException.class);

      verify(loadNormPort).loadNorm(new LoadNormPort.Command(eli));
    }
  }

  @Nested
  class loadElementHtmlAtDateFromNorm {
    @Test
    void itLoadsTheElementHtmlAtTheDateFromTheNorm() {
      // Given
      var eli = "eli/bund/bgbl-1/2000/s1/1970-01-01/1/deu/regelungstext-1";
      var eid = "meta-1";
      var date = Instant.parse("2099-12-31T00:00:00.00Z");

      var normXml =
          """
              <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
              <akn:akomaNtoso
                xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd">
                <akn:act name="regelungstext">
                  <!-- Metadaten -->
                  <akn:meta eId="meta-1" GUID="000">
                    <akn:identification eId="meta-1_ident-1" GUID="000" source="attributsemantik-noch-undefiniert">
                      <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="000">
                        <akn:FRBRthis
                          eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"
                          GUID="000"
                          value="eli/bund/bgbl-1/2000/s1/1970-01-01/1/deu/regelungstext-1"
                        />
                      </akn:FRBRExpression>
                    </akn:identification>
                  </akn:meta>
                </akn:act>
              </akn:akomaNtoso>
              """;

      var norm = new Norm(XmlMapper.toDocument(normXml));
      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli))).thenReturn(Optional.of(norm));
      when(timeMachineService.applyPassiveModifications(
              new ApplyPassiveModificationsUseCase.Query(norm, date)))
          .thenReturn(norm);
      when(xsltTransformationService.transformLegalDocMlToHtml(any())).thenReturn("<div></div>");

      // When
      var html =
          service.loadElementHtmlAtDateFromNorm(
              new LoadElementHtmlAtDateFromNormUseCase.Query(eli, eid, date));

      // Then
      assertThat(html).contains("<div></div>");
      verify(loadNormPort).loadNorm(new LoadNormPort.Command(eli));
      verify(timeMachineService)
          .applyPassiveModifications(new ApplyPassiveModificationsUseCase.Query(norm, date));
      verify(xsltTransformationService, times(1)).transformLegalDocMlToHtml(any());
    }

    @Test
    void itThrowsIfNormIsNotFound() {
      // Given
      var eli = "eli/bund/notfound/2000/s1/1970-01-01/1/deu/regelungstext-1";
      var eid = "meta-1";
      var date = Instant.parse("2099-12-31T00:00:00.00Z");
      var query = new LoadElementHtmlAtDateFromNormUseCase.Query(eli, eid, date);

      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli))).thenReturn(Optional.empty());

      // When / Then
      assertThatThrownBy(() -> service.loadElementHtmlAtDateFromNorm(query))
          .isInstanceOf(NormNotFoundException.class);

      verify(loadNormPort).loadNorm(new LoadNormPort.Command(eli));
    }

    @Test
    void itThrowsIfElementIsNotFound() {
      // Given
      var eli = "eli/bund/bgbl-1/2000/s1/1970-01-01/1/deu/regelungstext-1";
      var eid = "meta-1000";
      var date = Instant.parse("2099-12-31T00:00:00.00Z");
      var query = new LoadElementHtmlAtDateFromNormUseCase.Query(eli, eid, date);

      var normXml =
          """
              <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
              <akn:akomaNtoso
                xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd">
                <akn:act name="regelungstext">
                  <!-- Metadaten -->
                  <akn:meta eId="meta-1" GUID="000">
                    <akn:identification eId="meta-1_ident-1" GUID="000" source="attributsemantik-noch-undefiniert">
                      <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="000">
                        <akn:FRBRthis
                          eId="meta-1_ident-1_frbrexpression-1_frbrthis-1"
                          GUID="000"
                          value="eli/bund/bgbl-1/2000/s1/1970-01-01/1/deu/regelungstext-1"
                        />
                      </akn:FRBRExpression>
                    </akn:identification>
                  </akn:meta>
                </akn:act>
              </akn:akomaNtoso>
              """;

      var norm = new Norm(XmlMapper.toDocument(normXml));
      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli))).thenReturn(Optional.of(norm));
      when(timeMachineService.applyPassiveModifications(
              new ApplyPassiveModificationsUseCase.Query(norm, date)))
          .thenReturn(norm);
      when(xsltTransformationService.transformLegalDocMlToHtml(any())).thenReturn("<div></div>");

      // When / Then
      assertThatThrownBy(() -> service.loadElementHtmlAtDateFromNorm(query))
          .isInstanceOf(ElementNotFoundException.class);

      verify(loadNormPort).loadNorm(new LoadNormPort.Command(eli));
      verify(timeMachineService)
          .applyPassiveModifications(new ApplyPassiveModificationsUseCase.Query(norm, date));
    }
  }

  @Nested
  class loadElementsByTypeFromNorm {
    @Test
    void returnsAllSupportedTypesFromANorm() {
      // Given
      var norm = NormFixtures.loadFromDisk("NormWithPrefacePreambleAndConclusions.xml");
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      var elements =
          service.loadElementsByTypeFromNorm(
              new LoadElementsByTypeFromNormUseCase.Query(
                  "fake/eli", List.of("preface", "preamble", "article", "conclusions")));

      // Then
      assertThat(elements).hasSize(5);
      assertThat(elements.getFirst().getNodeName()).isEqualTo("akn:preface");
      assertThat(elements.get(1).getNodeName()).isEqualTo("akn:preamble");
      assertThat(elements.get(2).getNodeName()).isEqualTo("akn:article");
      assertThat(elements.get(3).getNodeName()).isEqualTo("akn:article");
      assertThat(elements.get(4).getNodeName()).isEqualTo("akn:conclusions");

      verify(loadNormPort).loadNorm(new LoadNormPort.Command("fake/eli"));
    }

    @Test
    void returnsASubsetOfTypesFromANorm() {
      // Given
      var norm = NormFixtures.loadFromDisk("NormWithPrefacePreambleAndConclusions.xml");
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      var elements =
          service.loadElementsByTypeFromNorm(
              new LoadElementsByTypeFromNormUseCase.Query("fake/eli", List.of("article")));

      // Then
      assertThat(elements).hasSize(2);
      assertThat(elements.getFirst().getNodeName()).isEqualTo("akn:article");
      assertThat(elements.get(1).getNodeName()).isEqualTo("akn:article");
    }

    @Test
    void returnsEmptyListIfTypeIsNotInNorm() {
      // Given
      var norm = NormFixtures.loadFromDisk("NormWithMultipleMods.xml");
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      var elements =
          service.loadElementsByTypeFromNorm(
              new LoadElementsByTypeFromNormUseCase.Query("fake/eli", List.of("preface")));

      // Then
      assertThat(elements).isEmpty();
    }

    @Test
    void returnsEmptyListIfTypesAreEmpty() {
      // Given
      var norm = NormFixtures.loadFromDisk("NormWithMultipleMods.xml");
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      var elements =
          service.loadElementsByTypeFromNorm(
              new LoadElementsByTypeFromNormUseCase.Query("fake/eli", List.of()));

      // Then
      assertThat(elements).isEmpty();
    }

    @Test
    void throwsWhenAttemptingToLoadUnsupportedType() {
      // Given
      var norm = NormFixtures.loadFromDisk("NormWithMultipleMods.xml");
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When / Then
      assertThatThrownBy(
              () ->
                  service.loadElementsByTypeFromNorm(
                      new LoadElementsByTypeFromNormUseCase.Query(
                          "fake/eli", List.of("invalid_type"))))
          .isInstanceOf(LoadElementsByTypeFromNormUseCase.UnsupportedElementTypeException.class);
    }

    @Test
    void throwsWhenNormDoesNotExist() {
      // Given
      // Nothing given -> Loading should fail

      LoadElementsByTypeFromNormUseCase.Query query =
          new LoadElementsByTypeFromNormUseCase.Query("fake/eli", List.of("article"));

      // When / Then
      assertThatThrownBy(() -> service.loadElementsByTypeFromNorm(query))
          .isInstanceOf(NormNotFoundException.class);
    }

    @Test
    void filtersReturnedElementsByAmendingNorm() {
      // Given
      var targetNorm =
          NormFixtures.loadFromDisk("NormWithPassiveModificationsInDifferentArticles.xml");
      var targetNormEli = targetNorm.getEli();
      when(loadNormPort.loadNorm(new LoadNormPort.Command(targetNormEli)))
          .thenReturn(Optional.of(targetNorm));

      // When
      var elements =
          service.loadElementsByTypeFromNorm(
              new LoadElementsByTypeFromNormUseCase.Query(
                  targetNormEli,
                  List.of("preface", "preamble", "article", "conclusions"),
                  "eli/bund/bgbl-1/2017/s815/1995-03-15/1/deu/regelungstext-1"));

      // Then
      assertThat(elements).hasSize(1);
      assertThat(elements.getFirst().getNodeName()).isEqualTo("akn:article");
    }

    @Test
    void returnsEmptyListIfNoElementIsAffectedByTheAmendingNorm() {
      // Given
      var targetNorm = NormFixtures.loadFromDisk("NormWithMultiplePassiveModifications.xml");
      var targetNormEli = targetNorm.getEli();
      when(loadNormPort.loadNorm(
              new LoadNormPort.Command(
                  "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1")))
          .thenReturn(Optional.of(targetNorm));

      // When
      var elements =
          service.loadElementsByTypeFromNorm(
              new LoadElementsByTypeFromNormUseCase.Query(
                  targetNormEli,
                  List.of("preface", "preamble", "article", "conclusions"),
                  "fake/eli"));

      // Then
      assertThat(elements).isEmpty();
    }
  }

  @Nested
  class elementType {
    @Test
    void returnsTheEnumValueForSupportedTypes() {
      // When
      var type = ElementService.ElementType.fromLabel("article");

      // Then
      assertThat(type).isEqualTo(ElementService.ElementType.ARTICLE);
    }

    @Test
    void throwsWhenAttemptingToGetTheValueForAnUnsupportedType() {
      // When / Then
      assertThatThrownBy(() -> ElementService.ElementType.fromLabel("invalid_type"))
          .isInstanceOf(LoadElementsByTypeFromNormUseCase.UnsupportedElementTypeException.class);
    }
  }
}
