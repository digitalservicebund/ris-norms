package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/** Class representing the meta:legalDocML.de_metadaten_ds */
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class MetadatenDs {
  private final Node node;

  /**
   * Retrieves the FNA value at a specific date. It looks for the one value which a @start attribute
   * >= date and @end attribute <= date.
   *
   * @param date - the specific date
   * @return the optional fna value
   */
  public Optional<String> getFnaAt(final LocalDate date) {
    return NodeParser.getNodesFromExpression("./fna", node).stream()
        .map(Fna::new)
        .filter(
            f -> f.getStart().map(start -> date.isEqual(start) || date.isAfter(start)).orElse(true))
        .filter(f -> f.getEnd().map(end -> date.isEqual(end) || date.isBefore(end)).orElse(true))
        .findFirst()
        .map(Fna::getValue);
  }
}
