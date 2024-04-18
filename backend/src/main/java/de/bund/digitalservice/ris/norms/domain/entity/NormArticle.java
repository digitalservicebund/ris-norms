package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.exceptions.XmlProcessingException;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/**
 * Represents an article inside a law with various attributes. This class is annotated with Lombok
 * annotations for generating getters, setters, constructors, and builder methods.
 */
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class NormArticle {
  @Getter private final Node node;

  /**
   * Returns a GUID as {@link UUID} from a {@link Node} in a {@link Norm}.
   *
   * @return The GUID of the article
   */
  public Optional<String> getGuid() {
    return getValueFromExpression("./@GUID", this.node);
  }

  /**
   * Returns the enumeration as {@link String} from a {@link Node} in a {@link Norm}.
   *
   * @return The enumeration of the article
   */
  public Optional<String> getEnumeration() {
    // not(normalize-space() is needed to filter out whitespaces which occur due to inner nodes
    // like akn:marker
    return getValueFromExpression("./num/text()[not(normalize-space() = '')]", this.node);
  }

  /**
   * Returns the eId as {@link String} from a {@link Node} in a {@link Norm}.
   *
   * @return The eId of the article
   */
  public Optional<String> getEid() {
    return getValueFromExpression("./@eId", this.node);
  }

  /**
   * Returns the heading as {@link String} from a {@link Node} in a {@link Norm}.
   *
   * @return The heading of the article
   */
  public Optional<String> getHeading() {
    return getValueFromExpression("./heading/text()", this.node);
  }

  /**
   * Returns the ELI of the affected document as {@link String} from a {@link Node} in a {@link
   * Norm}.
   *
   * @return The ELI of the affected document of the article
   */
  public Optional<String> getAffectedDocumentEli() {
    return getValueFromExpression(".//affectedDocument/@href", this.node);
  }

  private Optional<String> getValueFromExpression(String expression, Node xmlNode) {
    XPath xPath = XPathFactory.newInstance().newXPath();
    String result;
    try {
      result = (String) xPath.evaluate(expression, xmlNode, XPathConstants.STRING);
    } catch (XPathExpressionException | NoSuchElementException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }
    if (result.isEmpty()) return Optional.empty();

    return Optional.of(result);
  }
}
