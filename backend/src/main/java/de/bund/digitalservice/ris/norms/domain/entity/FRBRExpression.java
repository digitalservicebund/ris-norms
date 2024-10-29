package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.ExpressionEli;
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
   * Returns the Eli as {@link String} from the FRBRThis of the specific FRBR level.
   *
   * @return An Eli
   */
  public ExpressionEli getEli() {
    return ExpressionEli.fromString(
      NodeParser.getValueFromMandatoryNodeFromExpression("./FRBRthis/@value", this.getNode())
    );
  }

  /**
   * Updates the Eli of a Norm
   *
   * @param eli - the new ELI
   */
  public void setEli(final ExpressionEli eli) {
    NodeParser
      .getMandatoryNodeFromExpression("./FRBRthis", this.getNode())
      .getAttributes()
      .getNamedItem(VALUE_ATTIBUTE)
      .setNodeValue(eli.toString());
  }

  /**
   * Returns the Version as {@link Integer} from the FRBRversionNumber.
   *
   * @return The version of the norm
   */
  public Optional<Integer> getFRBRVersionNumber() {
    return NodeParser
      .getValueFromExpression("./FRBRversionNumber/@value", this.getNode())
      .map(Integer::parseInt);
  }

  /**
   * Sets the FRBRversionNumber.
   *
   * @param version the new version
   */
  public void setFRBRVersionNumber(final Integer version) {
    NodeParser
      .getMandatoryNodeFromExpression("./FRBRversionNumber", this.getNode())
      .getAttributes()
      .getNamedItem(VALUE_ATTIBUTE)
      .setNodeValue(version.toString());
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

        // FRBR metadata needs to be in the correct order, so we're inserting it before the author, which is the
        // element that has to follow the aliases in a valid document.
        var author = NodeParser.getMandatoryNodeFromExpression("./FRBRauthor", getNode());
        getNode().insertBefore(nextVersionAlias, author);

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
    NodeParser
      .getNodeFromExpression("./FRBRalias[@name='nachfolgende-version-id']", getNode())
      .ifPresent(node -> node.getParentNode().removeChild(node));
  }
}
