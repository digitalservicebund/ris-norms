package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.List;
import java.util.function.Predicate;
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

    norm
      .getDokumente()
      .forEach(dokument -> {
        dokument
          .getMeta()
          .getProprietary()
          .ifPresent(proprietary -> {
            assertThat(proprietary.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT)).isEmpty();
            assertThat(proprietary.getMetadataValue(Metadata.INKRAFT)).isEmpty();
            assertThat(proprietary.getMetadataValue(Metadata.AUSSERKRAFT)).isEmpty();
          });
      });
  }

  @Test
  void cleanRegularAndBundesregierungMetadata() {
    final Norm norm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
    );

    prototypeCleanupService.clean(norm);
    norm
      .getDokumente()
      .forEach(dokument -> {
        dokument
          .getMeta()
          .getProprietary()
          .ifPresent(proprietary -> {
            List<Node> proprietyChildNodes = NodeParser.nodeListToList(
              proprietary.getElement().getChildNodes()
            )
              .stream()
              .filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
              .toList();

            assertThat(proprietyChildNodes).hasSize(1);
            assertThat(proprietyChildNodes.getFirst().getNamespaceURI()).isEqualTo(
              Namespace.METADATEN_RIS.getNamespaceUri()
            );
          });
      });
  }

  @Test
  void cleanNotesMetadata() {
    final Norm norm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
    );

    prototypeCleanupService.clean(norm);
    norm
      .getDokumente()
      .forEach(dokument -> {
        List<Node> notes = NodeParser.nodeListToList(
          dokument.getMeta().getElement().getElementsByTagName("akn:note")
        )
          .stream()
          .filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
          .toList();

        assertThat(notes).hasSize(1);
        assertThat(
          notes.getFirst().getAttributes().getNamedItem("refersTo").getNodeValue()
        ).isEqualTo("kommentierende-fussnote");
      });
  }

  @Test
  void cleanNotesMetadataDeleteNotes() {
    final Norm norm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
    );
    norm
      .getDokumente()
      .forEach(dokument -> {
        dokument.deleteByEId("meta-n1_editfnote-n1");
      });

    prototypeCleanupService.clean(norm);
    norm
      .getDokumente()
      .forEach(dokument -> {
        List<Node> notes = NodeParser.nodeListToList(
          dokument.getMeta().getElement().getElementsByTagName("akn:note")
        )
          .stream()
          .filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
          .toList();

        assertThat(notes).isEmpty();
        assertThat(
          NodeParser.getNodesFromExpression("//notes", dokument.getMeta().getElement())
        ).isEmpty();
      });
  }

  @Test
  void cleanZeitgrenzenMetadata() {
    final Norm norm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
    );

    prototypeCleanupService.clean(norm);

    norm
      .getDokumente()
      .stream()
      .filter(Predicate.not(Rechtsetzungsdokument.class::isInstance)) // only this document type can't have lifecycle
      .forEach(dokument -> {
        List<Node> eventRefs = NodeParser.nodeListToList(
          dokument.getDocument().getElementsByTagName("akn:eventRef")
        );
        assertThat(eventRefs).hasSize(2);

        for (Node eventRef : eventRefs) {
          Element event = (Element) eventRef;
          String date = event.getAttribute("date");

          assertThat(date).isEqualTo("1001-01-01");
        }
      });
  }
}
