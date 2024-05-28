package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.Instant;
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
      assertThat(element).isPresent();
      assertThat(NodeParser.getValueFromExpression("./@eId", element.get())).contains(eid);

      verify(loadNormPort).loadNorm(new LoadNormPort.Command(eli));
    }

    @Test
    void itReturnsEmptyOptionalIfNoNormIsFound() {
      // Given
      var eli = "eli/bund/notfound/2000/s1/1970-01-01/1/deu/regelungstext-1";
      var eid = "meta-1";

      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli))).thenReturn(Optional.empty());

      // When
      var element = service.loadElementFromNorm(new LoadElementFromNormUseCase.Query(eli, eid));

      // Then
      assertThat(element).isEmpty();

      verify(loadNormPort).loadNorm(new LoadNormPort.Command(eli));
    }

    @Test
    void itReturnsEmptyOptionalIfNoElementIsFound() {
      // Given
      var eli = "eli/bund/bgbl-1/2000/s1/1970-01-01/1/deu/regelungstext-1";
      var eid = "meta-1000";

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
      assertThat(element).isEmpty();

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
    void itReturnsEmptyOptionalIfNoNormIsFound() {
      // Given
      var eli = "eli/bund/notfound/2000/s1/1970-01-01/1/deu/regelungstext-1";
      var eid = "meta-1";

      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli))).thenReturn(Optional.empty());

      // When
      var element =
          service.loadElementHtmlFromNorm(new LoadElementHtmlFromNormUseCase.Query(eli, eid));

      // Then
      assertThat(element).isEmpty();

      verify(loadNormPort).loadNorm(new LoadNormPort.Command(eli));
    }

    @Test
    void itReturnsEmptyOptionalIfNoElementIsFound() {
      // Given
      var eli = "eli/bund/bgbl-1/2000/s1/1970-01-01/1/deu/regelungstext-1";
      var eid = "meta-1000";

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
      assertThat(html).isEmpty();

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
    void itReturnsEmptyOptionalIfNormIsNotFound() {
      // Given
      var eli = "eli/bund/notfound/2000/s1/1970-01-01/1/deu/regelungstext-1";
      var eid = "meta-1";
      var date = Instant.parse("2099-12-31T00:00:00.00Z");

      when(loadNormPort.loadNorm(new LoadNormPort.Command(eli))).thenReturn(Optional.empty());

      // When
      var html =
          service.loadElementHtmlAtDateFromNorm(
              new LoadElementHtmlAtDateFromNormUseCase.Query(eli, eid, date));

      // Then
      assertThat(html).isEmpty();
      verify(loadNormPort).loadNorm(new LoadNormPort.Command(eli));
    }

    @Test
    void itReturnsEmptyOptionalIfElementIsNotFound() {
      // Given
      var eli = "eli/bund/bgbl-1/2000/s1/1970-01-01/1/deu/regelungstext-1";
      var eid = "meta-1000";
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
      assertThat(html).isEmpty();
      verify(loadNormPort).loadNorm(new LoadNormPort.Command(eli));
      verify(timeMachineService)
          .applyPassiveModifications(new ApplyPassiveModificationsUseCase.Query(norm, date));
    }
  }
}
