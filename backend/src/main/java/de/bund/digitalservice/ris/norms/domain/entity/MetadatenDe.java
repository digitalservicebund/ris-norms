package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import org.w3c.dom.Node;

/** Class representing the meta:legalDocML.de_metadaten */
@Getter
public class MetadatenDe extends Metadaten<MetadatenDe.Metadata> {

  @Builder
  public MetadatenDe(final Node node) {
    super(node, "ab", "bis", Namespace.METADATEN);
  }

  /**
   * The list of all simple metadata within the meta:legalDocML.de_metadaten block. Single and
   * nested properties.
   */
  public enum Metadata implements MetadataInterface {
    FNA("./fna"),
    ART("./art"),
    TYP("./typ"),
    GESTA("./gesta"),
    FASSUNG("./fassung");

    private final String xpath;

    Metadata(final String xpath) {
      this.xpath = xpath;
    }

    @Override
    public String getXpath() {
      return this.xpath;
    }
  }

  /**
   * Returns the FNA ("Fundstellennachweis A") of the norm.
   *
   * @return FNA or empty if it doesn't exist.
   */
  public Optional<String> getFna() {
    return NodeParser.getValueFromExpression(Metadata.FNA.xpath, getNode());
  }

  /**
   * Sets the FNA ("Fundstellennachweis A") of the norm.
   *
   * @param fna the new FNA
   */
  public void setFna(final String fna) {
    var fnaNode = NodeParser
      .getNodeFromExpression(Metadata.FNA.xpath, getNode())
      .orElseGet(() -> NodeCreator.createElement(getNamespace(), "fna", getNode()));

    fnaNode.setTextContent(fna);
  }

  /**
   * Returns the Art ("Art der Norm") of the norm.
   *
   * @return Art or empty if it doesn't exist.
   */
  public Optional<String> getArt() {
    return NodeParser.getValueFromExpression(Metadata.ART.xpath, getNode());
  }

  /**
   * Returns the type ("Typ des Dokuments") of the document.
   *
   * @return Typ or empty if it doesn't exist.
   */
  public Optional<String> getTyp() {
    return NodeParser.getValueFromExpression(Metadata.TYP.xpath, getNode());
  }

  /**
   * Returns the type GESTA of the document.
   *
   * @return Gesta or empty if it doesn't exist.
   */
  public Optional<String> getGesta() {
    return NodeParser.getValueFromExpression(Metadata.GESTA.xpath, getNode());
  }

  /**
   * Sets the GESTA of the norm.
   *
   * @param gesta the new gesta value
   */
  public void setGesta(final String gesta) {
    var node = NodeParser
      .getNodeFromExpression(Metadata.GESTA.xpath, getNode())
      .orElseGet(() -> NodeCreator.createElement(getNamespace(), "gesta", getNode()));

    node.setTextContent(gesta);
  }

  /**
   * Returns the fassung of the document.
   *
   * @return Fassung or empty if it doesn't exist.
   */
  public Optional<String> getFassung() {
    return NodeParser.getValueFromExpression(Metadata.FASSUNG.xpath, getNode());
  }

  /**
   * Sets the fassung of the norm.
   *
   * @param fassung the new fassung value
   */
  public void setFassung(final String fassung) {
    var node = NodeParser
      .getNodeFromExpression(Metadata.FASSUNG.xpath, getNode())
      .orElseGet(() -> NodeCreator.createElement(getNamespace(), "fassung", getNode()));

    node.setTextContent(fassung);
  }
}
