package de.bund.digitalservice.ris.norms.domain.entity;

import lombok.Builder;
import lombok.Getter;
import org.w3c.dom.Node;

/** Class representing the meta:legalDocML.de_metadaten for Bundesregierung */
@Getter
public class MetadatenBund extends Metadaten<MetadatenBund.Metadata> {

  @Builder
  public MetadatenBund(final Node node) {
    super(node, "ab", "bis", Namespace.METADATEN_BUNDESREGIERUNG);
  }

  /**
   * The list of all simple metadata within the meta:legalDocML.de_metadaten (Bund) block.
   */
  public enum Metadata implements MetadataInterface {
    RESSORT("./federfuehrung/federfuehrend");

    private final String xpath;

    Metadata(final String xpath) {
      this.xpath = xpath;
    }

    @Override
    public String getXpath() {
      return this.xpath;
    }
  }
}
