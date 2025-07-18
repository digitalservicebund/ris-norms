package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * A norms:zielnorm-reference. The information about the zielnorm and
 * geltungszeit of an amending command, see also: ADR-0015
 */
@Getter
@AllArgsConstructor
public class ZielnormReference {

  public static final Namespace NAMESPACE = Namespace.METADATEN_NORMS_APPLICATION_MODS;
  public static final String TAG_NAME = "zielnorm-reference";

  private final Element element;

  /**
   * Creates a new norms:zielnorm-reference element and appends it to the given
   * node.
   *
   * @param parentNode the node under which a new {@link ZielnormReference} should be created.
   * @param typ the typ of the reference
   * @param geltungszeit the id of the geltungszeitregel
   * @param eId the eId of the element containing the Änderungsbefehl
   * @param zielnorm the ELI of the zielnorm changed by the Änderungsbefehl
   * @param newWork whether the Zielnorm reference is referring to a new work
   * @return the newly created {@link ZielnormReference}
   */
  public static ZielnormReference createAndAppend(
    Node parentNode,
    String typ,
    Zeitgrenze.Id geltungszeit,
    EId eId,
    NormWorkEli zielnorm,
    boolean newWork
  ) {
    final var element = NodeCreator.createElement(NAMESPACE, TAG_NAME, parentNode);
    NodeCreator.createElement(NAMESPACE, "typ", element).setTextContent(typ);
    NodeCreator.createElement(NAMESPACE, "geltungszeit", element).setTextContent(
      geltungszeit.toString()
    );
    NodeCreator.createElement(NAMESPACE, "eid", element).setTextContent(eId.toString());

    var zielnormElement = NodeCreator.createElement(NAMESPACE, "zielnorm", element);
    zielnormElement.setTextContent(zielnorm.toString());
    if (newWork) {
      zielnormElement.setAttribute("new-work", "true");
    }

    return new ZielnormReference(element);
  }

  /**
   * The type of the reference
   *
   * @return the type of reference
   */
  public String getTyp() {
    return NodeParser.getValueFromMandatoryNodeFromExpression("./typ/text()", getElement());
  }

  /**
   * Set a new value for the typ
   *
   * @param typ the typ of the reference
   */
  public void setTyp(String typ) {
    NodeParser.getMandatoryNodeFromExpression("./typ/text()", getElement()).setTextContent(typ);
  }

  /**
   * The id of the geltungszeit
   *
   * @return the id of the geltungszeit
   */
  public Zeitgrenze.Id getGeltungszeit() {
    return new Zeitgrenze.Id(
      NodeParser.getValueFromMandatoryNodeFromExpression("./geltungszeit/text()", getElement())
    );
  }

  /**
   * Set a new value for the geltungszeit
   *
   * @param geltungszeit the id of the geltungszeitregel
   */
  public void setGeltungszeit(Zeitgrenze.Id geltungszeit) {
    NodeParser.getMandatoryNodeFromExpression("./geltungszeit/text()", getElement()).setTextContent(
      geltungszeit.toString()
    );
  }

  /**
   * The eId of the associated amending command
   *
   * @return the eId of the element that is creating the reference
   */
  public EId getEId() {
    return new EId(
      NodeParser.getValueFromMandatoryNodeFromExpression("./eid/text()", getElement())
    );
  }

  /**
   * The work eli of the norm that is targeted by the amending command
   *
   * @return work eli of the zielnorm
   */
  public NormWorkEli getZielnorm() {
    return NormWorkEli.fromString(
      NodeParser.getValueFromMandatoryNodeFromExpression("./zielnorm/text()", getElement())
    );
  }

  /**
   * Set a new value for the zielnorm
   *
   * @param zielnorm the ELI of the zielnorm
   */
  public void setZielnorm(NormWorkEli zielnorm) {
    NodeParser.getMandatoryNodeFromExpression("./zielnorm/text()", getElement()).setTextContent(
      zielnorm.toString()
    );
  }

  /**
   * Whether this Zielnorm reference is referring to a work that hasn't been
   * created yet.
   *
   * @return true if referring to a new work
   */
  public boolean isNewWork() {
    var newWork = NodeParser.getValueFromExpression("./zielnorm/@new-work", getElement());
    return newWork.isPresent() && newWork.get().equals("true");
  }

  /**
   * Set whether this Zielnorm reference is referring to a work that hasn't been
   * created yet.
   *
   * @param newWork true if referring to a new work
   */
  public void setNewWork(boolean newWork) {
    var zielnormElement = NodeParser.getMandatoryElementFromExpression("./zielnorm", element);
    if (newWork) {
      zielnormElement.setAttribute("new-work", "true");
    } else {
      zielnormElement.removeAttribute("new-work");
    }
  }
}
