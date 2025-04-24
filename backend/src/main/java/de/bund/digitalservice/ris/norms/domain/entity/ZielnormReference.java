package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.w3c.dom.Element;

/**
 * A norms:zielnorm-reference. The information about the zielnorm and geltungszeit of an amending command, see also: ADR-0015
 */
@Getter
@AllArgsConstructor
public class ZielnormReference {

  private final Element element;

  /**
   * The type of the reference
   * @return the type of reference
   */
  public String getTyp() {
    return NodeParser.getValueFromMandatoryNodeFromExpression("./typ/text()", getElement());
  }

  /**
   * The id of the geltungszeit
   * @return the id of the geltungszeit
   */
  public String getGeltungszeit() {
    return NodeParser.getValueFromMandatoryNodeFromExpression(
      "./geltungszeit/text()",
      getElement()
    );
  }

  /**
   * The eId of the associated amending command
   * @return the eId of the element that is creating the reference
   */
  public EId getEId() {
    return new EId(
      NodeParser.getValueFromMandatoryNodeFromExpression("./eid/text()", getElement())
    );
  }

  /**
   * The work eli of the norm that is targeted by the amending command
   * @return work eli of the zielnorm
   */
  public NormWorkEli getZielnorm() {
    return NormWorkEli.fromString(
      NodeParser.getValueFromMandatoryNodeFromExpression("./zielnorm/text()", getElement())
    );
  }
}
