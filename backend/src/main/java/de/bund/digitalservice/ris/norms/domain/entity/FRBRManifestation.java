package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import lombok.Getter;
import org.w3c.dom.Element;

/** Class representing the akn:FRBRManifestation */
@Getter
public class FRBRManifestation extends FRBR {

  public FRBRManifestation(final Element element) {
    super(element);
  }

  /**
   * Returns the Eli as {@link String} from the FRBRThis of the specific FRBR level.
   *
   * @return An Eli
   */
  public DokumentManifestationEli getEli() {
    return DokumentManifestationEli.fromString(
      NodeParser.getValueFromMandatoryNodeFromExpression("./FRBRthis/@value", this.getElement())
    );
  }

  /**
   * Updates the Eli of a Norm
   *
   * @param eli - the new ELI
   */
  public void setEli(final DokumentManifestationEli eli) {
    NodeParser
      .getMandatoryElementFromExpression("./FRBRthis", this.getElement())
      .setAttribute("value", eli.toString());
  }
}
