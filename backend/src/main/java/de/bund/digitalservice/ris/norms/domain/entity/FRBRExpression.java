package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Optional;
import java.util.UUID;
import lombok.Getter;
import org.w3c.dom.Element;

/** Class representing the akn:FRBRExpression */
@Getter
public class FRBRExpression extends FRBR {

  public FRBRExpression(final Element element) {
    super(element);
  }

  /**
   * Returns the Eli as {@link String} from the FRBRThis of the specific FRBR level.
   *
   * @return An Eli
   */
  public DokumentExpressionEli getEli() {
    return DokumentExpressionEli.fromString(
      NodeParser.getValueFromMandatoryNodeFromExpression("./FRBRthis/@value", this.getElement())
    );
  }

  /**
   * Updates the Eli of a Norm
   *
   * @param eli - the new ELI
   */
  public void setEli(final DokumentExpressionEli eli) {
    NodeParser.getMandatoryElementFromExpression("./FRBRthis", this.getElement()).setAttribute(
      VALUE_ATTIBUTE,
      eli.toString()
    );
  }

  /**
   * Returns the Version as {@link Integer} from the FRBRversionNumber.
   *
   * @return The version of the norm
   */
  public Optional<Integer> getFRBRVersionNumber() {
    return NodeParser.getValueFromExpression("./FRBRversionNumber/@value", this.getElement()).map(
      Integer::parseInt
    );
  }

  /**
   * Sets the FRBRversionNumber.
   *
   * @param version the new version
   */
  public void setFRBRVersionNumber(final Integer version) {
    NodeParser.getMandatoryElementFromExpression(
      "./FRBRversionNumber",
      this.getElement()
    ).setAttribute(VALUE_ATTIBUTE, version.toString());
  }

  /**
   * Retrieves the UUID of the previous version.
   *
   * @return the uuid
   */
  public Optional<UUID> getFRBRaliasPreviousVersionId() {
    return NodeParser.getValueFromExpression(
      "./FRBRalias[@name='vorherige-version-id']/@value",
      getElement()
    ).map(UUID::fromString);
  }

  /**
   * Sets the FRBRalias for the previous version id.
   *
   * @param uuid the new uuid
   */
  public void setFRBRaliasPreviousVersionId(final UUID uuid) {
    NodeParser.getElementFromExpression("./FRBRalias[@name='vorherige-version-id']", getElement())
      .orElseGet(() -> {
        final Element newElement = NodeCreator.createElementWithEidAndGuid(
          "akn:FRBRalias",
          getElement()
        );
        newElement.setAttribute("name", "vorherige-version-id");
        newElement.setAttribute(VALUE_ATTIBUTE, uuid.toString());

        // FRBR metadata needs to be in the correct order, so we're inserting it before the author, which is the
        // element that has to follow the aliases in a valid document.
        getElement().insertBefore(newElement, getFRBRAuthorNode());

        return newElement;
      })
      .setAttribute(VALUE_ATTIBUTE, uuid.toString());
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
        getElement()
      )
    );
  }

  /**
   * Sets the FRBRalias for the current version id.
   *
   * @param uuid the new uuid
   */
  public void setFRBRaliasCurrentVersionId(final UUID uuid) {
    NodeParser.getMandatoryElementFromExpression(
      "./FRBRalias[@name='aktuelle-version-id']",
      getElement()
    ).setAttribute(VALUE_ATTIBUTE, uuid.toString());
  }

  /**
   * Retrieves the UUID of the next version.
   *
   * @return the uuid
   */
  public Optional<UUID> getFRBRaliasNextVersionId() {
    return NodeParser.getValueFromExpression(
      "./FRBRalias[@name='nachfolgende-version-id']/@value",
      getElement()
    ).map(UUID::fromString);
  }

  /**
   * Sets the FRBRalias for the next version id.
   *
   * @param uuid the new uuid
   */
  public void setFRBRaliasNextVersionId(final UUID uuid) {
    NodeParser.getElementFromExpression(
      "./FRBRalias[@name='nachfolgende-version-id']",
      getElement()
    )
      .orElseGet(() -> {
        final Element nextVersionAlias = NodeCreator.createElementWithEidAndGuid(
          "akn:FRBRalias",
          getElement()
        );
        nextVersionAlias.setAttribute("name", "nachfolgende-version-id");
        nextVersionAlias.setAttribute(VALUE_ATTIBUTE, uuid.toString());

        // FRBR metadata needs to be in the correct order, so we're inserting it before the author, which is the
        // element that has to follow the aliases in a valid document.
        getElement().insertBefore(nextVersionAlias, getFRBRAuthorNode());

        return nextVersionAlias;
      })
      .setAttribute(VALUE_ATTIBUTE, uuid.toString());
  }

  /**
   * Deletes the FRBRalias with the next version id.
   *
   */
  public void deleteAliasNextVersionId() {
    NodeParser.getElementFromExpression(
      "./FRBRalias[@name='nachfolgende-version-id']",
      getElement()
    ).ifPresent(node -> node.getParentNode().removeChild(node));
  }

  /**
   * Returns a FRBRlanguage as {@link String}.
   *
   * @return The FRBRlanguage
   */
  public Optional<String> getFRBRlanguage() {
    return NodeParser.getValueFromExpression("./FRBRlanguage/@language", getElement());
  }

  private Element getFRBRAuthorNode() {
    return NodeParser.getMandatoryElementFromExpression("./FRBRauthor", getElement());
  }

  /**
   * Set the value of the FRBRauthor element (this contains the URI of the author of the document)
   * @param author the uri identifying the author of the document
   */
  public void setFRBRAuthor(final String author) {
    getFRBRAuthorNode().setAttribute("href", author);
  }
}
