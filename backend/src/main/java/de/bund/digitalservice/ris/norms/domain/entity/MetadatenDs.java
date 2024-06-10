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
   * Retrieves all nodes of the specific metadatum.
   *
   * @param xpath of the node
   * @return list of all metadata as {@link SimpleProprietaryValue}
   */
  public List<SimpleProprietaryValue> getNodes(final String xpath) {
    return NodeParser.getNodesFromExpression(xpath, node).stream()
        .map(SimpleProprietaryValue::new)
        .toList();
  }

  /**
   * Retrieves the FNA value at a specific date. It looks for the one value where @start attribute
   * is equals or before the given date and the @end attribute is equals or after the given date.
   *
   * @param date the specific date
   * @return the optional fna value
   */
  public Optional<String> getFnaAt(final LocalDate date) {
    return getSimpleValueAt("./fna", date);
  }

  /**
   * Updates/Creates the new fna value at a specific date.
   *
   * @param date the specific date.
   * @param fna the new fna value.
   */
  public void setFnaAt(final LocalDate date, final String fna) {
    this.setSimpleValueAt("./fna", date, fna);
  }

  /**
   * Retrieves the Art value at a specific date. It looks for the one value where @start attribute
   * is equals or before the given date and the @end attribute is equals or after the given date.
   *
   * @param date the specific date
   * @return Art or empty if it doesn't exist.
   */
  public Optional<String> getArtAt(final LocalDate date) {
    return getSimpleValueAt("./art", date);
  }

  /**
   * Updates/Creates the new art value at a specific date.
   *
   * @param date the specific date.
   * @param art the new art value.
   */
  public void setArtAt(final LocalDate date, final String art) {
    this.setSimpleValueAt("./art", date, art);
  }

  /**
   * Retrieves the Typ value at a specific date. It looks for the one value where @start attribute
   * is equals or before the given date and the @end attribute is equals or after the given date.
   *
   * @param date the specific date
   * @return Typ or empty if it doesn't exist.
   */
  public Optional<String> getTypAt(final LocalDate date) {
    return getSimpleValueAt("./typ", date);
  }

  /**
   * Updates/Creates the new typ value at a specific date.
   *
   * @param date the specific date.
   * @param typ the new art value.
   */
  public void setTypAt(final LocalDate date, final String typ) {
    this.setSimpleValueAt("./typ", date, typ);
  }

  public Optional<String> getSubtypAt(final LocalDate date) {
    return this.getSimpleValueAt("./subtyp", date);
  }

  /**
   * Updates/Creates the new subtyp value at a specific date.
   *
   * @param date the specific date.
   * @param subtyp the new subtyp value.
   */
  public void setSubtypAt(final LocalDate date, final String subtyp) {
    this.setSimpleValueAt("./subtyp", date, subtyp);
  }

  /**
   * It returns the value for a xpath at a specific date. If no matching value @start or @end is
   * found, it will retrieve the value from the node with neither @start nor @end.
   *
   * @param xpath the xpath of the node
   * @param date the specific date
   * @return an optional value, if found.
   */
  private Optional<String> getSimpleValueAt(final String xpath, final LocalDate date) {
    final List<SimpleProprietaryValue> valuesWithoutStartAndEnd =
        getNodes(xpath).stream()
            .filter(f -> f.getStart().isEmpty() && f.getEnd().isEmpty())
            .toList();

    final List<SimpleProprietaryValue> valuesWithStartOrAndEnd =
        getNodes(xpath).stream()
            .filter(f -> f.getStart().isPresent() || f.getEnd().isPresent())
            .filter(
                f ->
                    f.getStart()
                        .map(start -> date.isEqual(start) || date.isAfter(start))
                        .orElse(true))
            .filter(
                f -> f.getEnd().map(end -> date.isEqual(end) || date.isBefore(end)).orElse(true))
            .toList();
    if (!valuesWithStartOrAndEnd.isEmpty()) {
      return valuesWithStartOrAndEnd.stream().findFirst().map(SimpleProprietaryValue::getValue);
    } else {
      return valuesWithoutStartAndEnd.stream().findFirst().map(SimpleProprietaryValue::getValue);
    }
  }

  /**
   * Creates or updates a metadata with simple value at the specific date. It sets the @start
   * attribute to the given date. If node not present before, it will create a new one and set also
   * the @end attribute if there is a later fna node. The value would be the @start date of the next
   * closest FNA minus 1 day. Also, if there is a previous node it will set the @end attribute to 1
   * day before of the newly updated/created node.
   *
   * @param xpath the xpath of the node
   * @param date the specific date
   * @param newValue the new value
   */
  private void setSimpleValueAt(final String xpath, final LocalDate date, final String newValue) {
    NodeParser.getNodeFromExpression(String.format("%s[@start='%s']", xpath, date.toString()), node)
        .ifPresentOrElse(
            fnaNode -> fnaNode.setTextContent(newValue),
            () -> {
              // 1. Check if we have later FNAs and get the next closest one
              final Optional<SimpleProprietaryValue> nextNode =
                  getNodes(xpath).stream()
                      .filter(f -> f.getStart().isPresent() && f.getStart().get().isAfter(date))
                      .min(Comparator.comparing(f -> f.getStart().get()));

              // 2. Check if we have previous FNAs and get the next closest one
              final Optional<SimpleProprietaryValue> previousNode =
                  getNodes(xpath).stream()
                      .filter(f -> f.getStart().isPresent() && f.getStart().get().isBefore(date))
                      .max(Comparator.comparing(f -> f.getStart().get()));

              // 3. Create new meta:fna node with the @start value and optional @end
              final Element newFnaElement =
                  NodeCreator.createElement(
                      String.format("meta:%s", xpath.replace("./", "")), node);
              newFnaElement.setTextContent(newValue);
              newFnaElement.setAttribute("start", date.toString());
              if (nextNode.isPresent() && nextNode.get().getStart().isPresent()) {
                final LocalDate nextStart = nextNode.get().getStart().get().minusDays(1);
                newFnaElement.setAttribute("end", nextStart.toString());
              }

              // 4. Then also set @end of a previous one, if present
              previousNode.ifPresent(
                  value ->
                      ((Element) value.getNode())
                          .setAttribute("end", date.minusDays(1).toString()));

              // 5. And finally set @end of nodes without @start and @end to one day before given
              // date
              getNodes(xpath).stream()
                  .filter(f -> f.getStart().isEmpty() && f.getEnd().isEmpty())
                  .forEach(
                      value ->
                          ((Element) value.getNode())
                              .setAttribute("end", date.minusDays(1).toString()));
            });
  }
}
