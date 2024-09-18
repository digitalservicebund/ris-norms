package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
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

  private static final String VALUE_ATTIBUTE = "value";

  public FRBRExpression(final Node node) {
    super(node);
  }

  /**
   * Retrieves the UUID of the previous version.
   *
   * @return the uuid
   */
  public Optional<UUID> getFRBRaliasPreviousVersionId() {
    return NodeParser
      .getValueFromExpression("./FRBRalias[@name='vorherige-version-id']/@value", getNode())
      .map(UUID::fromString);
  }

  /**
   * Sets the FRBRalias for the previous version id.
   *
   * @param uuid the new uuid
   */
  public void setFRBRaliasPreviousVersionId(final UUID uuid) {
    NodeParser
      .getNodeFromExpression("./FRBRalias[@name='vorherige-version-id']", getNode())
      .orElseGet(() -> {
        final Element newElement = NodeCreator.createElementWithEidAndGuid(
          "akn:FRBRalias",
          getNode()
        );
        newElement.setAttribute("name", "vorherige-version-id");
        newElement.setAttribute(VALUE_ATTIBUTE, uuid.toString());
        getNode().appendChild(newElement);
        return newElement;
      })
      .getAttributes()
      .getNamedItem(VALUE_ATTIBUTE)
      .setNodeValue(uuid.toString());
  }

  /**
   * Retrieves the UUID of the current version.
   *
   * @return the uuid
   */
  public UUID getFRBRaliasCurrentVersionId() {
    return UUID.fromString(
      NodeParser.getValueFromMandatoryNodeFromExpression(
        "./FRBRalias[@name='aktuelle-version-id']/@value",
        getNode()
      )
    );
  }

  /**
   * Sets the FRBRalias for the current version id.
   *
   * @param uuid the new uuid
   */
  public void setFRBRaliasCurrentVersionId(final UUID uuid) {
    NodeParser
      .getMandatoryNodeFromExpression("./FRBRalias[@name='aktuelle-version-id']", getNode())
      .getAttributes()
      .getNamedItem(VALUE_ATTIBUTE)
      .setNodeValue(uuid.toString());
  }

  /**
   * Retrieves the UUID of the next version.
   *
   * @return the uuid
   */
  public Optional<UUID> getFRBRaliasNextVersionId() {
    return NodeParser
      .getValueFromExpression("./FRBRalias[@name='nachfolgende-version-id']/@value", getNode())
      .map(UUID::fromString);
  }

  /**
   * Sets the FRBRalias for the next version id.
   *
   * @param uuid the new uuid
   */
  public void setFRBRaliasNextVersionId(final UUID uuid) {
    NodeParser
      .getNodeFromExpression("./FRBRalias[@name='nachfolgende-version-id']", getNode())
      .orElseGet(() -> {
        final Element nextVersionAlias = NodeCreator.createElementWithEidAndGuid(
          "akn:FRBRalias",
          getNode()
        );
        nextVersionAlias.setAttribute("name", "nachfolgende-version-id");
        nextVersionAlias.setAttribute(VALUE_ATTIBUTE, uuid.toString());
        getNode().appendChild(nextVersionAlias);
        return nextVersionAlias;
      })
      .getAttributes()
      .getNamedItem(VALUE_ATTIBUTE)
      .setNodeValue(uuid.toString());
  }

  /**
   * Deletes the FRBRalias with the next version id.
   *
   */
  public void deleteAliasNextVersionId() {
    Optional<Node> alias = NodeParser.getNodeFromExpression(
      "./FRBRalias[@name='nachfolgende-version-id']",
      getNode()
    );
    if (alias.isPresent()) {
      Node parent = alias.get().getParentNode();
      parent.removeChild(alias.get());
    }
  }
}
