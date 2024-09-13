package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/** Class representing simple metadata within meta:legalDocML.de_metadaten_ds */
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class SimpleProprietary {

  private final Node node;

  /**
   * Retrieves the node value.
   *
   * @return the node value
   */
  public String getValue() {
    return node.getTextContent();
  }

  /**
   * Retrieves the value of the optional @start attribute
   *
   * @return the optional value of @start in {@link LocalDate}
   */
  public Optional<LocalDate> getStart() {
    return NodeParser
      .getValueFromExpression("./@start", node)
      .map(LocalDate::parse)
      .or(this::getAb);
  }

  /**
   * Retrieves the value of the optional @ab attribute
   *
   * @return the optional value of @ab in {@link LocalDate}
   */
  public Optional<LocalDate> getAb() {
    return NodeParser.getValueFromExpression("./@ab", node).map(LocalDate::parse);
  }

  /**
   * Retrieves the value of the optional @end attribute
   *
   * @return the optional value of @end in {@link LocalDate}
   */
  public Optional<LocalDate> getEnd() {
    return getEndOrBis("end").or(this::getBis);
  }

  /**
   * Retrieves the value of the optional @bis attribute
   *
   * @return the optional value of @bis in {@link LocalDate}
   */
  public Optional<LocalDate> getBis() {
    return getEndOrBis("bis");
  }

  private Optional<LocalDate> getEndOrBis(final String attributeName) {
    return NodeParser
      .getValueFromExpression("./@%s".formatted(attributeName), node)
      .map(m -> {
        if (m.equals("unbestimmt")) {
          return LocalDate.MAX;
        } else {
          return LocalDate.parse(m);
        }
      });
  }

  /**
   * Retrieves the value of an attribute, which is mandatory
   *
   * @param attributeName Name of the attribute for which you want to retrieve the value
   * @return String value of the requested attribute
   */
  public Optional<String> getAttribute(final String attributeName) {
    return NodeParser.getValueFromExpression("./@%s".formatted(attributeName), node);
  }

  /**
   * Sets the attribute to the new value.
   *
   * @param attributeName the name of the attribute
   * @param newValue the new value to be set
   */
  public void setAttribute(final String attributeName, final String newValue) {
    ((Element) node).setAttribute(attributeName, newValue);
  }

  /**
   * Removes an attribute
   *
   * @param attributeName the name of the attribute
   */
  public void removeAttribute(final String attributeName) {
    ((Element) node).removeAttribute(attributeName);
  }
}
