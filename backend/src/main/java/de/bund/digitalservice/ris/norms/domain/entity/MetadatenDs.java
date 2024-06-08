package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/** Class representing the meta:legalDocML.de_metadaten_ds */
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class MetadatenDs {
  private final Node node;

  /**
   * Retrieves the FNA value at a specific date. It looks for the one value where @start attribute
   * is equals or before the given date and the @end attribute is equals or after the given date.
   *
   * @param date - the specific date
   * @return the optional fna value
   */
  public Optional<String> getFnaAt(final LocalDate date) {
    return getFnaNodes().stream()
        .filter(
            f -> f.getStart().map(start -> date.isEqual(start) || date.isAfter(start)).orElse(true))
        .filter(f -> f.getEnd().map(end -> date.isEqual(end) || date.isBefore(end)).orElse(true))
        .findFirst()
        .map(Fna::getValue);
  }

  /**
   * Creates or updates the fna at the specific date. It sets the @start attribute to the given
   * date. If node not present before, it will create a new one and set also the @end attribute if
   * there is a later fna node. The value would be the @start date of the next closest FNA minus 1
   * day. Also, if there is a previous node it will set the @end attribute to 1 day before of the
   * newly updated/created node.
   *
   * @param date - the specific date of the time boundary.
   * @param fna - the new fna
   */
  public void setFnaAt(final LocalDate date, final String fna) {
    NodeParser.getNodeFromExpression(String.format("./fna[@start='%s']", date.toString()), node)
        .ifPresentOrElse(
            fnaNode -> fnaNode.setTextContent(fna),
            () -> {
              // 1. Check if we have later FNAs and get the next closest one
              final Optional<Fna> nextFna =
                  getFnaNodes().stream()
                      .filter(f -> f.getStart().isPresent() && f.getStart().get().isAfter(date))
                      .min(Comparator.comparing(f -> f.getStart().get()));

              // 2. Check if we have previous FNAs and get the next closest one
              final Optional<Fna> previousFna =
                  getFnaNodes().stream()
                      .filter(f -> f.getStart().isPresent() && f.getStart().get().isBefore(date))
                      .max(Comparator.comparing(f -> f.getStart().get()));

              // 3. Create new meta:fna node with the @start value and optional @end
              final Element newFnaElement = NodeCreator.createElement("meta:fna", node);
              newFnaElement.setTextContent(fna);
              newFnaElement.setAttribute("start", date.toString());
              if (nextFna.isPresent() && nextFna.get().getStart().isPresent()) {
                final LocalDate nextStart = nextFna.get().getStart().get().minusDays(1);
                newFnaElement.setAttribute("end", nextStart.toString());
              }

              // 4. Then also set @end of a previous one, if present
              previousFna.ifPresent(
                  value ->
                      ((Element) value.getNode())
                          .setAttribute("end", date.minusDays(1).toString()));
            });
  }

  /**
   * Retrieves all meta:fna nodes
   *
   * @return list of {@link Fna}
   */
  public List<Fna> getFnaNodes() {
    return NodeParser.getNodesFromExpression("./fna", node).stream().map(Fna::new).toList();
  }
}
