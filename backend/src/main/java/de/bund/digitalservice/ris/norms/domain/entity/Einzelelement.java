package de.bund.digitalservice.ris.norms.domain.entity;

import lombok.Builder;
import lombok.Getter;
import org.w3c.dom.Node;

/** Class representing the meta:legalDocML.de_metadaten_ds/meta:einzelelement */
@Getter
public class Einzelelement extends Metadaten<Einzelelement.Metadata> {

  @Builder
  public Einzelelement(final Node node) {
    super(node, "start", "end");
  }

  /**
   * The list of all simple metadata within the meta:legalDocML.de_metadaten_ds block. They consist
   * of a single string property.
   */
  public enum Metadata implements MetadataInterface {
    ART_DER_NORM("./artDerNorm");

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
