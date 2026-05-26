package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Represents an abstract "Dokument" in LDML.de
 */
@Getter
@AllArgsConstructor
public abstract sealed class Dokument
  permits Bekanntmachung, OffeneStruktur, Rechtsetzungsdokument, Regelungstext {

  private final Document document;

  /**
   * Returns the manifestation Eli of the {@link Dokument}.
   *
   * @return The manifestation Eli
   */
  public DokumentManifestationEli getManifestationEli() {
    return DokumentManifestationEli.fromString(
      NodeParser.getValueFromMandatoryNodeFromExpression(
        "//FRBRManifestation/FRBRthis/@value",
        document
      )
    );
  }

  /**
   * Returns a {@link Meta} instance from a {@link Document} in a {@link Dokument}.
   *
   * @return the meta node as {@link Meta}
   */
  public Meta getMeta() {
    return new Meta(NodeParser.getMandatoryElementFromExpression("//meta", document));
  }

  /**
   * Returns the element of the norm identified by the given eId.
   *
   * @param eId the eId of the element to return
   * @return the selected element
   */
  private Optional<Element> getElementByEId(String eId) {
    return NodeParser.getElementFromExpression(
      String.format("//*[@eId='%s']", eId),
      this.getDocument()
    );
  }

  /**
   * Deletes the element of the norm identified by the given eId.
   *
   * @param eId the eId of the element to delete
   */
  public void deleteByEId(String eId) {
    var node = getElementByEId(eId);
    node.ifPresent(n -> n.getParentNode().removeChild(n));
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    Dokument norm = (Dokument) object;
    return document.isEqualNode(norm.document);
  }

  @Override
  public int hashCode() {
    return hashNode(document);
  }

  /**
   * Custom hashCode implementation that uses the same semantic as Node::isEqualNode to determine the hash code so two
   * Dokumente that are equal also have the same hashCode. We ignore Node::getNodeType as it would be really odd for two
   * Nodes to only differ in it, and it is the slowest part to call.
   *
   * @param node the {@link Node} to create a hash for
   * @return a hash code for the node
   */
  private static int hashNode(Node node) {
    if (node == null) {
      return 1;
    }

    return Objects.hash(
      node.getNodeName(),
      node.getLocalName(),
      node.getNamespaceURI(),
      node.getPrefix(),
      node.getNodeValue(),
      hashNamedNodeMap(node.getAttributes()),
      hashNodeList(node.getChildNodes())
    );
  }

  private static int hashNodeList(NodeList nodeList) {
    if (nodeList == null || nodeList.getLength() == 0) {
      return 1;
    }

    int[] hashes = new int[nodeList.getLength()];

    for (int i = 0; i < nodeList.getLength(); i++) {
      hashes[i] = hashNode(nodeList.item(i));
    }

    return Arrays.hashCode(hashes);
  }

  private static int hashNamedNodeMap(NamedNodeMap nodeMap) {
    if (nodeMap == null || nodeMap.getLength() == 0) {
      return 1;
    }

    int[] hashes = new int[nodeMap.getLength()];

    for (int i = 0; i < nodeMap.getLength(); i++) {
      hashes[i] = hashNode(nodeMap.item(i));
    }

    return Arrays.hashCode(hashes);
  }
}
