package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Element;

/** Class representing simple metadata within ris:legalDocML.de_metadaten */
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class SimpleProprietary {

  private final Element element;

  /**
   * Retrieves the node value.
   *
   * @return the node value
   */
  public String getValue() {
    return element.getTextContent();
  }

  /**
   * Retrieves the value of the optional @start attribute
   *
   * @return the optional value of @start in {@link LocalDate}
   */
  public Optional<LocalDate> getStart() {
    return NodeParser
      .getValueFromExpression("./@start", element)
      .map(LocalDate::parse)
      .or(this::getAb);
  }

  /**
   * Retrieves the value of the optional @ab attribute
   *
   * @return the optional value of @ab in {@link LocalDate}
   */
  public Optional<LocalDate> getAb() {
    return NodeParser.getValueFromExpression("./@ab", element).map(LocalDate::parse);
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
      .getValueFromExpression("./@%s".formatted(attributeName), element)
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
    return NodeParser.getValueFromExpression("./@%s".formatted(attributeName), element);
  }

  /**
   * Sets the attribute to the new value.
   *
   * @param attributeName the name of the attribute
   * @param newValue the new value to be set
   */
  public void setAttribute(final String attributeName, final String newValue) {
    ((Element) element).setAttribute(attributeName, newValue);
  }

  /**
   * Removes an attribute
   *
   * @param attributeName the name of the attribute
   */
  public void removeAttribute(final String attributeName) {
    ((Element) element).removeAttribute(attributeName);
  }
}
