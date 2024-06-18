package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/** Class representing simple metadata within meta:legalDocML.de_metadaten_ds */
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class SimpleProprietaryValue {
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
   * Retrieves the value of the start attribute, which is mandatory
   *
   * @return the start value in {@link LocalDate}
   */
  public Optional<LocalDate> getStart() {
    return NodeParser.getValueFromExpression("./@start", node).map(LocalDate::parse);
  }

  /**
   * Retrieves the value of the end attribute, which is optional
   *
   * @return the optional value of end in {@link LocalDate}
   */
  public Optional<LocalDate> getEnd() {
    return NodeParser.getValueFromExpression("./@end", node)
        .map(
            m -> {
              if (m.equals("unbestimmt")) {
                return LocalDate.MAX;
              } else {
                return LocalDate.parse(m);
              }
            });
  }
}
