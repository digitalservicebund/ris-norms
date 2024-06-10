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
    return NodeParser.getValueFromExpression("./@end", node).map(LocalDate::parse);
  }

  /**
   * Compares two FNAs, so they can be sorted by their start dates. It is assumed that there is no
   * overlap in validity between two values.
   *
   * @param o1 the first element to be compared
   * @param o2 the second element to be compared
   * @return Comparison result
   */
  public static int compareByStartDate(
      final SimpleProprietaryValue o1, final SimpleProprietaryValue o2) {
    if (o1.getStart().isPresent() && o2.getStart().isPresent()) {
      return o1.getStart().get().compareTo(o2.getStart().get());
    } else if (o1.getStart().isEmpty() && o2.getStart().isPresent()) return -1;
    else if (o1.getStart().isPresent() && o2.getStart().isEmpty()) return 1;
    else return 0;
  }
}
