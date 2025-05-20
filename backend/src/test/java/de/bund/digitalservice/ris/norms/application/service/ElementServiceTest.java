package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.exception.ElementNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.RegelungstextNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadRegelungstextPort;
import de.bund.digitalservice.ris.norms.domain.entity.EId;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
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
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      var eid = new EId("meta-1");

      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      when(
        loadRegelungstextPort.loadRegelungstext(new LoadRegelungstextPort.Command(eli))
      ).thenReturn(Optional.of(regelungstext));

      // When
      var element = service.loadElement(new LoadElementUseCase.Options(eli, eid));

      // Then
      assertThat(NodeParser.getValueFromExpression("./@eId", element)).contains(eid.toString());

      verify(loadRegelungstextPort).loadRegelungstext(new LoadRegelungstextPort.Command(eli));
    }

    @Test
    void itThrowsIfDokumentIsNotFound() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/notfound/2000/s1/1970-01-01/1/deu/regelungstext-1"
      );
      var eid = new EId("meta-1");
      var query = new LoadElementUseCase.Options(eli, eid);

      when(
        loadRegelungstextPort.loadRegelungstext(new LoadRegelungstextPort.Command(eli))
      ).thenReturn(Optional.empty());

      // When / Then
      assertThatThrownBy(() -> service.loadElement(query)).isInstanceOf(
        RegelungstextNotFoundException.class
      );

      verify(loadRegelungstextPort).loadRegelungstext(new LoadRegelungstextPort.Command(eli));
    }

    @Test
    void itThrowsIfNoElementIsFound() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      var eid = new EId("meta-1000");
      var query = new LoadElementUseCase.Options(eli, eid);

      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      when(
        loadRegelungstextPort.loadRegelungstext(new LoadRegelungstextPort.Command(eli))
      ).thenReturn(Optional.of(regelungstext));

      // When / Then
      assertThatThrownBy(() -> service.loadElement(query)).isInstanceOf(
        ElementNotFoundException.class
      );

      verify(loadRegelungstextPort).loadRegelungstext(new LoadRegelungstextPort.Command(eli));
    }
  }

  @Nested
  class loadElementHtml {

    @Test
    void itLoadsTheElementHtml() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      var eid = new EId("meta-1");

      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      when(
        loadRegelungstextPort.loadRegelungstext(new LoadRegelungstextPort.Command(eli))
      ).thenReturn(Optional.of(regelungstext));
      when(xsltTransformationService.transformLegalDocMlToHtml(any())).thenReturn("<div></div>");

      // When
      var html = service.loadElementHtml(new LoadElementHtmlUseCase.Options(eli, eid));

      // Then
      assertThat(html).contains("<div></div>");

      verify(loadRegelungstextPort).loadRegelungstext(new LoadRegelungstextPort.Command(eli));
      verify(xsltTransformationService, times(1)).transformLegalDocMlToHtml(any());
    }

    @Test
    void itThrowsIfNoRegelungstextIsFound() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      var eid = new EId("meta-1");
      var query = new LoadElementHtmlUseCase.Options(eli, eid);

      when(
        loadRegelungstextPort.loadRegelungstext(new LoadRegelungstextPort.Command(eli))
      ).thenReturn(Optional.empty());

      // When / Then
      assertThatThrownBy(() -> service.loadElementHtml(query)).isInstanceOf(
        RegelungstextNotFoundException.class
      );

      verify(loadRegelungstextPort).loadRegelungstext(new LoadRegelungstextPort.Command(eli));
    }

    @Test
    void itThrowsIfNoElementIsFound() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      var eid = new EId("meta-1000");
      var query = new LoadElementHtmlUseCase.Options(eli, eid);

      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      when(
        loadRegelungstextPort.loadRegelungstext(new LoadRegelungstextPort.Command(eli))
      ).thenReturn(Optional.of(regelungstext));
      when(xsltTransformationService.transformLegalDocMlToHtml(any())).thenReturn("<div></div>");

      // When / Then
      assertThatThrownBy(() -> service.loadElementHtml(query)).isInstanceOf(
        ElementNotFoundException.class
      );

      verify(loadRegelungstextPort).loadRegelungstext(new LoadRegelungstextPort.Command(eli));
    }
  }
}
