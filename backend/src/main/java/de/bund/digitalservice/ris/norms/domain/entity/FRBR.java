package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Class representing a generalization of FRBR elements. It cannot be instantiated itself because it
 * needs a custom implementation of a level.
 */
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class FRBR {

  private final Node node;

  /**
   * Returns the Eli as {@link String} from the FRBRThis of the specific FRBR level.
   *
   * @return An Eli
   */
  public String getEli() {
    return NodeParser.getValueFromMandatoryNodeFromExpression("./FRBRthis/@value", node);
  }

  /**
   * Updates the Eli of a Norm
   *
   * @param eli - the new ELI
   */
  public void setEli(final String eli) {
    NodeParser
      .getMandatoryNodeFromExpression("./FRBRthis", node)
      .getAttributes()
      .getNamedItem("value")
      .setNodeValue(eli);
  }

  /**
   * Returns a FBRDate as {@link LocalDate} from the FRBR level.
   *
   * @return The FBRDate
   */
  public String getFBRDate() {
    return NodeParser.getValueFromMandatoryNodeFromExpression("./FRBRdate/@date", node);
  }

  /**
   * Updates both the date and the name attibutes of FBRDate
   *
   * @param date - the new date
   * @param name - the new name
   */
  public void setFBRDate(final String date, final String name) {
    final NamedNodeMap attributes = NodeParser
      .getMandatoryNodeFromExpression("./FRBRdate", node)
      .getAttributes();
    attributes.getNamedItem("date").setNodeValue(date);
    attributes.getNamedItem("name").setNodeValue(name);
  }
}
