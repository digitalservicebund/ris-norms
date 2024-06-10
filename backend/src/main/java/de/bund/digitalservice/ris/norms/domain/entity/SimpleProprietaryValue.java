package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/** Class meta:fna within meta:legalDocML.de_metadaten_ds */
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class SimpleProprietaryValue implements Comparable<SimpleProprietaryValue> {
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
    return NodeParser.getValueFromExpression("./@end", node).map(LocalDate::parse);
  }

  /**
   * Compares two FNAs, so they can be sorted by their start dates. Currently, only the start date
   * is considered when comparing. It is assumed that there is no overlap in validity between two
   * FNAs.
   *
   * @param other the element to be compared
   * @return Comparison result
   */
  @Override
  public int compareTo(SimpleProprietaryValue other) {
    if (this.getStart().isPresent() && other.getStart().isPresent()) {
      return this.getStart().get().compareTo(other.getStart().get());
    } else if (this.getStart().isEmpty() && other.getStart().isPresent()) return -1;
    else if (this.getStart().isPresent() && other.getStart().isEmpty()) return 1;
    else return 0;
  }
}
