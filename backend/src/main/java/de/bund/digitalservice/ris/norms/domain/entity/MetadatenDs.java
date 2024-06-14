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

  /** The list of all simple metadata. They consist of a single string property. */
  public enum SimpleMetadatum {
    FNA("./fna"),
    ART("./art"),
    TYP("./typ"),
    SUBTYP("./subtyp"),
    BEZEICHNUNG_IN_VORLAGE("./bezeichnungInVorlage"),
    ART_DER_NORM("./artDerNorm"),
    NORMGEBER("./normgeber"),
    BESCHLIESSENDES_ORGAN("./beschliessendesOrgan");

    private final String xpath;

    SimpleMetadatum(final String xpath) {
      this.xpath = xpath;
    }
  }

  /**
   * It returns the value for a xpath at a specific date. If no matching value @start or @end is
   * found, it will retrieve the value from the node with neither @start nor @end.
   *
   * @param simpleMetadatum the enum metadatum
   * @param date the specific date
   * @return an optional value, if found.
   */
  public Optional<String> getSimpleValueAt(
      final SimpleMetadatum simpleMetadatum, final LocalDate date) {
    final List<SimpleProprietaryValue> valuesWithoutStartAndEnd =
        getNodes(simpleMetadatum.xpath).stream()
            .filter(f -> f.getStart().isEmpty() && f.getEnd().isEmpty())
            .toList();

    final List<SimpleProprietaryValue> valuesWithStartOrAndEnd =
        getNodes(simpleMetadatum.xpath).stream()
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
   * attribute to the given date. If node not present, it will create a new one and set also
   * the @end attribute if there is a later fna node. The value would be the @start date of the next
   * closest FNA minus 1 day. Also, if there is a previous node it will set the @end attribute to 1
   * day before of the newly updated/created node. Finally, it will also set the @end attribute of
   * those nodes with neither @start nor @end attributes.
   *
   * @param simpleMetadatum - the enum metadatum
   * @param date - the specific date
   * @param newValue - the new value to update/create
   */
  public void setSimpleProprietaryMetadata(
      final SimpleMetadatum simpleMetadatum, final LocalDate date, final String newValue) {
    NodeParser.getNodeFromExpression(
            String.format("%s[@start='%s']", simpleMetadatum.xpath, date.toString()), node)
        .ifPresentOrElse(
            fnaNode -> fnaNode.setTextContent(newValue),
            () -> {
              // 1. Check if we have later FNAs and get the next closest one
              final Optional<SimpleProprietaryValue> nextNode =
                  getNodes(simpleMetadatum.xpath).stream()
                      .filter(f -> f.getStart().isPresent() && f.getStart().get().isAfter(date))
                      .min(Comparator.comparing(f -> f.getStart().get()));

              // 2. Check if we have previous FNAs and get the next closest one
              final Optional<SimpleProprietaryValue> previousNode =
                  getNodes(simpleMetadatum.xpath).stream()
                      .filter(f -> f.getStart().isPresent() && f.getStart().get().isBefore(date))
                      .max(Comparator.comparing(f -> f.getStart().get()));

              // 3. Create new meta:fna node with the @start value and optional @end
              final Element newFnaElement =
                  NodeCreator.createElement(
                      String.format("meta:%s", simpleMetadatum.xpath.replace("./", "")), node);
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
              getNodes(simpleMetadatum.xpath).stream()
                  .filter(f -> f.getStart().isEmpty() && f.getEnd().isEmpty())
                  .forEach(
                      value ->
                          ((Element) value.getNode())
                              .setAttribute("end", date.minusDays(1).toString()));
            });
  }

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
}
