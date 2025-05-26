package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Metadata;
import de.bund.digitalservice.ris.norms.domain.entity.Namespace;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

class PrototypeCleanupServiceTest {

  private final PrototypeCleanupService prototypeCleanupService = new PrototypeCleanupService();

  @Test
  void cleanRisMetadata() {
    final Norm norm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
    );

    prototypeCleanupService.clean(norm);
    assertThat(
      norm.getRegelungstext1().getMeta().getProprietary().get().getMetadataValue(Metadata.SUBTYP)
    ).isEmpty();
    assertThat(
      norm
        .getRegelungstext1()
        .getMeta()
        .getProprietary()
        .get()
        .getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE)
    ).isEmpty();
    assertThat(
      norm
        .getRegelungstext1()
        .getMeta()
        .getProprietary()
        .get()
        .getMetadataValue(Metadata.ART_DER_NORM)
    ).isEmpty();
    assertThat(
      norm.getRegelungstext1().getMeta().getProprietary().get().getMetadataValue(Metadata.NORMGEBER)
    ).isEmpty();
    assertThat(
      norm.getRegelungstext1().getMeta().getProprietary().get().getMetadataValue(Metadata.SUBTYP)
    ).isEmpty();
    assertThat(
      norm
        .getRegelungstext1()
        .getMeta()
        .getProprietary()
        .get()
        .getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE)
    ).isEmpty();
    assertThat(
      norm.getRegelungstext1().getMeta().getProprietary().get().getMetadataValue(Metadata.NORMGEBER)
    ).isEmpty();
    assertThat(
      norm
        .getRegelungstext1()
        .getMeta()
        .getProprietary()
        .get()
        .getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN)
    ).isEmpty();
    assertThat(
      norm
        .getRegelungstext1()
        .getMeta()
        .getProprietary()
        .get()
        .getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)
    ).isEmpty();
    assertThat(
      norm.getRegelungstext1().getMeta().getProprietary().get().getMetadataValue(Metadata.INKRAFT)
    ).isEmpty();
    assertThat(
      norm
        .getRegelungstext1()
        .getMeta()
        .getProprietary()
        .get()
        .getMetadataValue(Metadata.AUSSERKRAFT)
    ).isEmpty();
    assertThat(
      norm
        .getRegelungstext1()
        .getMeta()
        .getProprietary()
        .get()
        .getMetadataValue(Metadata.STANDANGABE)
    ).contains("zuletzt geändert durch Art. 2 Abs. 2 G v.7.11.2024 I Nr. 351");
    assertThat(
      norm.getRegelungstext1().getMeta().getProprietary().get().getMetadataValue(Metadata.VOLLZITAT)
    ).contains("Gesetz vom 5. August 1964 (BGBl. 1964 I S. 593)");
  }

  @Test
  void cleanRegularAndBundesregierungMetadata() {
    final Norm norm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
    );

    prototypeCleanupService.clean(norm);
    List<Node> proprietyChildNodes = NodeParser.nodeListToList(
      norm.getRegelungstext1().getMeta().getProprietary().get().getElement().getChildNodes()
    )
      .stream()
      .filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
      .toList();

    assertThat(proprietyChildNodes).hasSize(1);
    assertThat(proprietyChildNodes.getFirst().getNamespaceURI()).isEqualTo(
      Namespace.METADATEN_RIS.getNamespaceUri()
    );
  }

  @Test
  void cleanNotesMetadata() {
    final Norm norm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
    );

    prototypeCleanupService.clean(norm);
    List<Node> notes = NodeParser.nodeListToList(
      norm.getRegelungstext1().getMeta().getElement().getElementsByTagName("akn:note")
    )
      .stream()
      .filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
      .toList();

    assertThat(notes).hasSize(1);
    assertThat(notes.getFirst().getAttributes().getNamedItem("refersTo").getNodeValue()).isEqualTo(
      "kommentierende-fussnote"
    );
  }

  @Test
  void cleanNotesMetadataDeleteNotes() {
    final Norm norm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
    );
    norm.getRegelungstext1().deleteByEId("meta-1_editfnote-1");

    prototypeCleanupService.clean(norm);
    List<Node> notes = NodeParser.nodeListToList(
      norm.getRegelungstext1().getMeta().getElement().getElementsByTagName("akn:note")
    )
      .stream()
      .filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
      .toList();

    assertThat(notes).isEmpty();
    assertThat(
      NodeParser.getNodesFromExpression("//notes", norm.getRegelungstext1().getMeta().getElement())
    ).isEmpty();
  }

  @Test
  void cleanZeitgrenzenMetadata() {
    final Norm norm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
    );

    prototypeCleanupService.clean(norm);

    List<Node> eventRefs = NodeParser.nodeListToList(
      norm.getRegelungstext1().getDocument().getElementsByTagName("akn:eventRef")
    );
    assertThat(eventRefs).hasSize(2);

    for (Node eventRef : eventRefs) {
      Element event = (Element) eventRef;
      String date = event.getAttribute("date");

      assertThat(date).isEqualTo("1001-01-01");
    }
  }
}
