package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.ManifestationEli;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/** Class representing the akn:FRBRManifestation */
@Getter
@SuperBuilder(toBuilder = true)
public class FRBRManifestation extends FRBR {

  public FRBRManifestation(final Node node) {
    super(node);
  }

  /**
   * Returns the Eli as {@link String} from the FRBRThis of the specific FRBR level.
   *
   * @return An Eli
   */
  public ManifestationEli getEli() {
    return ManifestationEli.fromString(
      NodeParser.getValueFromMandatoryNodeFromExpression("./FRBRthis/@value", this.getNode())
    );
  }

  /**
   * Updates the Eli of a Norm
   *
   * @param eli - the new ELI
   */
  public void setEli(final ManifestationEli eli) {
    NodeParser
      .getMandatoryNodeFromExpression("./FRBRthis", this.getNode())
      .getAttributes()
      .getNamedItem("value")
      .setNodeValue(eli.toString());
  }
}
