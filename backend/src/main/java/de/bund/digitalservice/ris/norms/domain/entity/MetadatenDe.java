package de.bund.digitalservice.ris.norms.domain.entity;

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
    super(node, "ab", "bis");
  }

  /**
   * The list of all simple metadata within the meta:legalDocML.de_metadaten block. Single and
   * nested properties.
   */
  public enum Metadata implements MetadataInterface {
    FNA("./fna"),
    ART("./art"),
    TYP("./typ"),
    FEDERFUEHRUNG("./federfuehrung/federfuehrend");

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
}
