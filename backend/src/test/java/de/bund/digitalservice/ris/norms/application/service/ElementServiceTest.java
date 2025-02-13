package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.exception.ElementNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.RegelungstextNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadRegelungstextPort;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ElementServiceTest {

  final LoadRegelungstextPort loadRegelungstextPort = mock(LoadRegelungstextPort.class);
  final XsltTransformationService xsltTransformationService = mock(XsltTransformationService.class);

  final ElementService service = new ElementService(
    loadRegelungstextPort,
    xsltTransformationService
  );

  @Nested
  class loadElement {

    @Test
    void itLoadsAnElement() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2000/s1/1970-01-01/1/deu/regelungstext-1"
      );
      var eid = "meta-1";

      var normXml =
        """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
        <akn:akomaNtoso
              xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd">
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

      var regelungstext = new Regelungstext(XmlMapper.toDocument(normXml));
      when(loadRegelungstextPort.loadRegelungstext(new LoadRegelungstextPort.Command(eli)))
        .thenReturn(Optional.of(regelungstext));

      // When
      var element = service.loadElement(new LoadElementUseCase.Query(eli, eid));

      // Then
      assertThat(NodeParser.getValueFromExpression("./@eId", element)).contains(eid);

      verify(loadRegelungstextPort).loadRegelungstext(new LoadRegelungstextPort.Command(eli));
    }

    @Test
    void itThrowsIfDokumentIsNotFound() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/notfound/2000/s1/1970-01-01/1/deu/regelungstext-1"
      );
      var eid = "meta-1";
      var query = new LoadElementUseCase.Query(eli, eid);

      when(loadRegelungstextPort.loadRegelungstext(new LoadRegelungstextPort.Command(eli)))
        .thenReturn(Optional.empty());

      // When / Then
      assertThatThrownBy(() -> service.loadElement(query))
        .isInstanceOf(RegelungstextNotFoundException.class);

      verify(loadRegelungstextPort).loadRegelungstext(new LoadRegelungstextPort.Command(eli));
    }

    @Test
    void itThrowsIfNoElementIsFound() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2000/s1/1970-01-01/1/deu/regelungstext-1"
      );
      var eid = "meta-1000";
      var query = new LoadElementUseCase.Query(eli, eid);

      var normXml =
        """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
        <akn:akomaNtoso
              xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd">
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

      var regelungstext = new Regelungstext(XmlMapper.toDocument(normXml));
      when(loadRegelungstextPort.loadRegelungstext(new LoadRegelungstextPort.Command(eli)))
        .thenReturn(Optional.of(regelungstext));

      // When / Then
      assertThatThrownBy(() -> service.loadElement(query))
        .isInstanceOf(ElementNotFoundException.class);

      verify(loadRegelungstextPort).loadRegelungstext(new LoadRegelungstextPort.Command(eli));
    }
  }

  @Nested
  class loadElementHtml {

    @Test
    void itLoadsTheElementHtml() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2000/s1/1970-01-01/1/deu/regelungstext-1"
      );
      var eid = "meta-1";

      var normXml =
        """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
        <akn:akomaNtoso
              xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd">
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

      var regelungstext = new Regelungstext(XmlMapper.toDocument(normXml));
      when(loadRegelungstextPort.loadRegelungstext(new LoadRegelungstextPort.Command(eli)))
        .thenReturn(Optional.of(regelungstext));
      when(xsltTransformationService.transformLegalDocMlToHtml(any())).thenReturn("<div></div>");

      // When
      var html = service.loadElementHtml(new LoadElementHtmlUseCase.Query(eli, eid));

      // Then
      assertThat(html).contains("<div></div>");

      verify(loadRegelungstextPort).loadRegelungstext(new LoadRegelungstextPort.Command(eli));
      verify(xsltTransformationService, times(1)).transformLegalDocMlToHtml(any());
    }

    @Test
    void itThrowsIfNoRegelungstextIsFound() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/notfound/2000/s1/1970-01-01/1/deu/regelungstext-1"
      );
      var eid = "meta-1";
      var query = new LoadElementHtmlUseCase.Query(eli, eid);

      when(loadRegelungstextPort.loadRegelungstext(new LoadRegelungstextPort.Command(eli)))
        .thenReturn(Optional.empty());

      // When / Then
      assertThatThrownBy(() -> service.loadElementHtml(query))
        .isInstanceOf(RegelungstextNotFoundException.class);

      verify(loadRegelungstextPort).loadRegelungstext(new LoadRegelungstextPort.Command(eli));
    }

    @Test
    void itThrowsIfNoElementIsFound() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2000/s1/1970-01-01/1/deu/regelungstext-1"
      );
      var eid = "meta-1000";
      var query = new LoadElementHtmlUseCase.Query(eli, eid);

      var normXml =
        """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
        <akn:akomaNtoso
              xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd">
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

      var regelungstext = new Regelungstext(XmlMapper.toDocument(normXml));
      when(loadRegelungstextPort.loadRegelungstext(new LoadRegelungstextPort.Command(eli)))
        .thenReturn(Optional.of(regelungstext));
      when(xsltTransformationService.transformLegalDocMlToHtml(any())).thenReturn("<div></div>");

      // When / Then
      assertThatThrownBy(() -> service.loadElementHtml(query))
        .isInstanceOf(ElementNotFoundException.class);

      verify(loadRegelungstextPort).loadRegelungstext(new LoadRegelungstextPort.Command(eli));
    }
  }

  @Nested
  class loadElementsByType {

    @Test
    void returnsAllSupportedTypes() {
      // Given
      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "NormWithPrefacePreambleAndConclusions.xml"
      );
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.of(regelungstext));

      // When
      var elements = service.loadElementsByType(
        new LoadElementsByTypeUseCase.Query(
          DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/2017/s815/1995-03-15/1/deu/regelungstext-1"
          ),
          List.of("preface", "preamble", "article", "conclusions")
        )
      );

      // Then
      assertThat(elements).hasSize(5);
      assertThat(elements.getFirst().getNodeName()).isEqualTo("akn:preface");
      assertThat(elements.get(1).getNodeName()).isEqualTo("akn:preamble");
      assertThat(elements.get(2).getNodeName()).isEqualTo("akn:article");
      assertThat(elements.get(3).getNodeName()).isEqualTo("akn:article");
      assertThat(elements.get(4).getNodeName()).isEqualTo("akn:conclusions");

      verify(loadRegelungstextPort)
        .loadRegelungstext(
          argThat(command ->
            command
              .eli()
              .equals(
                DokumentExpressionEli.fromString(
                  "eli/bund/bgbl-1/2017/s815/1995-03-15/1/deu/regelungstext-1"
                )
              )
          )
        );
    }

    @Test
    void returnsASubsetOfTypes() {
      // Given
      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "NormWithPrefacePreambleAndConclusions.xml"
      );
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.of(regelungstext));

      // When
      var elements = service.loadElementsByType(
        new LoadElementsByTypeUseCase.Query(new DokumentExpressionEli(), List.of("article"))
      );

      // Then
      assertThat(elements).hasSize(2);
      assertThat(elements.getFirst().getNodeName()).isEqualTo("akn:article");
      assertThat(elements.get(1).getNodeName()).isEqualTo("akn:article");
    }

    @Test
    void returnsEmptyListIfTypeIsNotInNorm() {
      // Given
      var regelungstext = Fixtures.loadRegelungstextFromDisk("NormWithMultipleMods.xml");
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.of(regelungstext));

      // When
      var elements = service.loadElementsByType(
        new LoadElementsByTypeUseCase.Query(new DokumentExpressionEli(), List.of("preface"))
      );

      // Then
      assertThat(elements).isEmpty();
    }

    @Test
    void returnsEmptyListIfTypesAreEmpty() {
      // Given
      var regelungstext = Fixtures.loadRegelungstextFromDisk("NormWithMultipleMods.xml");
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.of(regelungstext));

      // When
      var elements = service.loadElementsByType(
        new LoadElementsByTypeUseCase.Query(new DokumentExpressionEli(), List.of())
      );

      // Then
      assertThat(elements).isEmpty();
    }

    @Test
    void throwsWhenAttemptingToLoadUnsupportedType() {
      // Given
      var regelungstext = Fixtures.loadRegelungstextFromDisk("NormWithMultipleMods.xml");
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.of(regelungstext));

      // When / Then
      assertThatThrownBy(() ->
          service.loadElementsByType(
            new LoadElementsByTypeUseCase.Query(
              DokumentExpressionEli.fromString(
                "eli/bund/bgbl-1/2017/s815/1995-03-15/1/deu/regelungstext-1"
              ),
              List.of("invalid_type")
            )
          )
        )
        .isInstanceOf(LoadElementsByTypeUseCase.UnsupportedElementTypeException.class);
    }

    @Test
    void throwsWhenNormDoesNotExist() {
      // Given
      // Nothing given -> Loading should fail

      LoadElementsByTypeUseCase.Query query = new LoadElementsByTypeUseCase.Query(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2017/s815/1995-03-15/1/deu/regelungstext-1"
        ),
        List.of("article")
      );

      // When / Then
      assertThatThrownBy(() -> service.loadElementsByType(query))
        .isInstanceOf(RegelungstextNotFoundException.class);
    }

    @Test
    void filtersReturnedElementsByAmendingNorm() {
      // Given
      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "NormWithPassiveModificationsInDifferentArticles.xml"
      );
      var eli = regelungstext.getExpressionEli();
      when(loadRegelungstextPort.loadRegelungstext(new LoadRegelungstextPort.Command(eli)))
        .thenReturn(Optional.of(regelungstext));

      // When
      var elements = service.loadElementsByType(
        new LoadElementsByTypeUseCase.Query(
          eli,
          List.of("preface", "preamble", "article", "conclusions"),
          DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/2017/s815/1995-03-15/1/deu/regelungstext-1"
          )
        )
      );

      // Then
      assertThat(elements).hasSize(1);
      assertThat(elements.getFirst().getNodeName()).isEqualTo("akn:article");
    }

    @Test
    void returnsEmptyListIfNoElementIsAffectedByTheAmendingNorm() {
      // Given
      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "NormWithMultiplePassiveModifications.xml"
      );
      var eli = regelungstext.getExpressionEli();
      when(
        loadRegelungstextPort.loadRegelungstext(
          new LoadRegelungstextPort.Command(
            DokumentExpressionEli.fromString(
              "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1"
            )
          )
        )
      )
        .thenReturn(Optional.of(regelungstext));

      // When
      var elements = service.loadElementsByType(
        new LoadElementsByTypeUseCase.Query(
          eli,
          List.of("preface", "preamble", "article", "conclusions"),
          DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/1000/1/1000-01-01/1/deu/regelungstext-1"
          )
        )
      );

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
        .isInstanceOf(LoadElementsByTypeUseCase.UnsupportedElementTypeException.class);
    }
  }
}
