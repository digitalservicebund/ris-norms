package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * A single norm expression that is amended by the Verkündung it belong to
 * See also: ADR-0020
 */
@AllArgsConstructor
public class NormExpression {

  public static final Namespace NAMESPACE = Namespace.METADATEN_NORMS_APPLICATION_MODS;
  public static final String TAG_NAME = "norm-expression";
  public static final String ATTRIBUTE_NAME_CREATED_BY_ZEITGRENZE = "created-by-zeitgrenze";
  public static final String ATTRIBUTE_NAME_CREATED_BY_REPLACING_EXISTING =
    "created-by-replacing-existing-expression";

  private final Element element;

  /**
   * Creates a new norms:norm-expression element and appends it to the parent {@link Node} of norms:amended-norm-expressions
   * @param parentNode the element that should contain the new {@link NormExpression}
   * @param expression the expression eli that will be placed into the text content of the new node
   * @param createdByZeitgrenze the attribute indicating if the expression was created because of a zeitgrenze
   * @param createdByReplacingExisting the attribute indicating if the expression was created replacing an existing one
   * @return the newly created {@link NormExpression}
   */
  public static NormExpression createAndAppend(
    Node parentNode,
    NormExpressionEli expression,
    boolean createdByZeitgrenze,
    boolean createdByReplacingExisting
  ) {
    final var element = NodeCreator.createElement(NAMESPACE, TAG_NAME, parentNode);

    final var normExpression = new NormExpression(element);

    normExpression.setCreatedByZeitgrenze(createdByZeitgrenze);
    normExpression.setCreatedByReplacingExisting(createdByReplacingExisting);

    normExpression.setNormExpressionEli(expression);

    return normExpression;
  }

  /**
   * Retrieves expression eli of the {@link NormExpression} which is within the text content of the node.
   * @return the parsed {@link NormExpression}
   */
  public NormExpressionEli getNormExpressionEli() {
    final String textContent = element.getTextContent().trim();
    if (textContent.isEmpty()) {
      throw new IllegalArgumentException("Missing text content in <norm-expression> node");
    }
    return NormExpressionEli.fromString(element.getTextContent());
  }

  private void setNormExpressionEli(NormExpressionEli normExpressionEli) {
    element.setTextContent(normExpressionEli.toString());
  }

  /**
   * Retrieves the value of the attribute {@link #ATTRIBUTE_NAME_CREATED_BY_ZEITGRENZE} of the node.
   * @return the parsed true/false value
   */
  public boolean getCreatedByZeitgrenze() {
    return Boolean.parseBoolean(element.getAttribute(ATTRIBUTE_NAME_CREATED_BY_ZEITGRENZE));
  }

  /**
   * Updates the attribute  {@link #ATTRIBUTE_NAME_CREATED_BY_ZEITGRENZE}
   * @param value true/false
   */
  public void setCreatedByZeitgrenze(boolean value) {
    element.setAttribute(ATTRIBUTE_NAME_CREATED_BY_ZEITGRENZE, String.valueOf(value));
  }

  /**
   * Retrieves the value of the attribute {@link #ATTRIBUTE_NAME_CREATED_BY_REPLACING_EXISTING} of the node.
   * @return the parsed true/false value
   */
  public boolean getCreatedByReplacingExistingExpression() {
    return Boolean.parseBoolean(element.getAttribute(ATTRIBUTE_NAME_CREATED_BY_REPLACING_EXISTING));
  }

  /**
   * Updates the attribute  {@link #ATTRIBUTE_NAME_CREATED_BY_REPLACING_EXISTING}
   * @param value true/false
   */
  public void setCreatedByReplacingExisting(boolean value) {
    element.setAttribute(ATTRIBUTE_NAME_CREATED_BY_REPLACING_EXISTING, String.valueOf(value));
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    NormExpression that = (NormExpression) obj;
    return (
      this.getCreatedByZeitgrenze() == that.getCreatedByZeitgrenze() &&
      this.getCreatedByReplacingExistingExpression() ==
      that.getCreatedByReplacingExistingExpression() &&
      this.getNormExpressionEli().equals(that.getNormExpressionEli())
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      getNormExpressionEli(),
      getCreatedByZeitgrenze(),
      getCreatedByReplacingExistingExpression()
    );
  }
}
