package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;

class LdmlDeEmptyElementRemoverTest {

  LdmlDeEmptyElementRemover ldmlDeEmptyElementRemover = new LdmlDeEmptyElementRemover();

  @Test
  void elementsWithoutEmptyElementsStayTheSame() {
    var element = Fixtures.loadRegelungstextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
    )
      .getDocument()
      .getDocumentElement();

    ldmlDeEmptyElementRemover.removeEmptyElements(element);

    assertThat(
      element.isEqualNode(
        Fixtures.loadRegelungstextFromDisk(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
        )
          .getDocument()
          .getDocumentElement()
      )
    ).isTrue();
  }

  @Test
  void itRemovesEmptyElements() {
    var element = XmlMapper.toElement(
      """
      <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
        eId="meta-n1_proprietary-n1"
        GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
        source="attributsemantik-noch-undefiniert">
          <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.8.1/">
              <ris:einzelelement href="#hauptteil-n1_abschnitt-n0_art-n1">
              </ris:einzelelement>
          </ris:legalDocML.de_metadaten>
      </akn:proprietary>
      """
    );

    ldmlDeEmptyElementRemover.removeEmptyElements(element);

    Diff diff = DiffBuilder.compare(element)
      .normalizeWhitespace()
      .withTest(
        XmlMapper.toElement(
          """
          <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/"
            eId="meta-n1_proprietary-n1"
            GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
            source="attributsemantik-noch-undefiniert">
          </akn:proprietary>
          """
        )
      )
      .build();

    assertThat(diff.hasDifferences()).isFalse();
  }
}
