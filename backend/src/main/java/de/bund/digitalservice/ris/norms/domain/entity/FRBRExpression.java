package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFound;
import java.util.Optional;
import java.util.UUID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/** Class representing the akn:FRBRExpression */
@Getter
@SuperBuilder(toBuilder = true)
public class FRBRExpression extends FRBR {

  public FRBRExpression(final Node node, final String normEli) {
    super(node, normEli, FRBRExpression.class.getSimpleName());
  }

  /**
   * Retrieves the UUID of the previous version.
   *
   * @return the uuid
   */
  public Optional<UUID> getFRBRaliasPreviousVersionId() {
    return NodeParser.getNodeFromExpression("./FRBRalias[@name='vorherige-version-id']", getNode())
        .map(m -> m.getAttributes().getNamedItem("value").getNodeValue())
        .map(UUID::fromString);
  }

  /**
   * Sets the FRBRalias for the previous version id.
   *
   * @param uuid the new uuid
   */
  public void setFRBRaliasPreviousVersionId(final UUID uuid) {
    NodeParser.getNodeFromExpression("./FRBRalias[@name='vorherige-version-id']", getNode())
        .orElseGet(
            () -> {
              final Element newElement =
                  getNode().getOwnerDocument().createElement("akn:FRBRalias");
              newElement.setAttribute("eId", "meta-1_ident-1_frbrexpression-1_frbralias-1");
              newElement.setAttribute("GUID", UUID.randomUUID().toString());
              newElement.setAttribute("name", "vorherige-version-id");
              newElement.setAttribute("value", uuid.toString());
              getNode().appendChild(newElement);
              return newElement;
            })
        .getAttributes()
        .getNamedItem("value")
        .setNodeValue(uuid.toString());
  }

  /**
   * Retrieves the UUID of the current version.
   *
   * @return the uuid
   */
  public UUID getFRBRaliasCurrentVersionId() {
    final String xpath = "./FRBRalias[@name='aktuelle-version-id']";
    return NodeParser.getNodeFromExpression(xpath, getNode())
        .map(m -> m.getAttributes().getNamedItem("value").getNodeValue())
        .map(UUID::fromString)
        .orElseThrow(() -> new MandatoryNodeNotFound(xpath, getNodeName(), getNormEli()));
  }

  /**
   * Sets the FRBRalias for the current version id.
   *
   * @param uuid the new uuid
   */
  public void setFRBRaliasCurrentVersionId(final UUID uuid) {
    NodeParser.getNodeFromExpression("./FRBRalias[@name='aktuelle-version-id']", getNode())
        .orElseThrow()
        .getAttributes()
        .getNamedItem("value")
        .setNodeValue(uuid.toString());
  }

  /**
   * Retrieves the UUID of the next version.
   *
   * @return the uuid
   */
  public UUID getFRBRaliasNextVersionId() {
    final String xpath = "./FRBRalias[@name='nachfolgende-version-id']";
    return NodeParser.getNodeFromExpression(xpath, getNode())
        .map(m -> m.getAttributes().getNamedItem("value").getNodeValue())
        .map(UUID::fromString)
        .orElseThrow(() -> new MandatoryNodeNotFound(xpath, getNodeName(), getNormEli()));
  }

  /**
   * Sets the FRBRalias for the next version id.
   *
   * @param uuid the new uuid
   */
  public void setFRBRaliasNextVersionId(final UUID uuid) {
    NodeParser.getNodeFromExpression("./FRBRalias[@name='nachfolgende-version-id']", getNode())
        .orElseThrow()
        .getAttributes()
        .getNamedItem("value")
        .setNodeValue(uuid.toString());
  }
}
