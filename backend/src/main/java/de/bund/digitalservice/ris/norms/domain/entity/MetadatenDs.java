package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.util.*;
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
   * The list of all attribute. They consist of the enum {@link SimpleMetadatum} they belong to and
   * its name.
   */
  public enum Attribute {
    QUALIFIZIERTE_MEHRHEIT(SimpleMetadatum.BESCHLIESSENDES_ORGAN, "qualifizierteMehrheit");

    private final SimpleMetadatum simpleMetadatum;
    private final String name;

    Attribute(final SimpleMetadatum simpleMetadatum, final String name) {
      this.simpleMetadatum = simpleMetadatum;
      this.name = name;
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
  public Optional<String> getSimpleMetadatum(
      final SimpleMetadatum simpleMetadatum, final LocalDate date) {
    return getSimpleNodeAt(simpleMetadatum, date).map(SimpleProprietary::getValue);
  }

  /**
   * It returns the value of the attribute by retrieving first the belonging metadatum by using
   * getSimpleMetadatum
   *
   * @param attribute the attribute to be retrieved
   * @param date the specific date
   * @return an optional string value of the attribute, if found
   */
  public Optional<String> getAttributeOfSimpleMetadatumAt(
      final Attribute attribute, final LocalDate date) {
    return getSimpleNodeAt(attribute.simpleMetadatum, date)
        .flatMap(m -> m.getAttribute(attribute.name));
  }

  /**
   * Creates or updates a metadata with simple value at the specific date. It sets the @start
   * attribute to the given date. If node not present, it will create a new one and set also
   * the @end attribute if there is a later fna node. The value would be the @start date of the next
   * closest FNA minus 1 day. Also, if there is a previous node it will set the @end attribute to 1
   * day before of the newly updated/created node. Finally, it will also set the @end attribute of
   * those nodes with neither @start nor @end attributes.
   *
   * @param simpleMetadatum - the simple enum metadatum
   * @param date - the specific date
   * @param newValue - the new value to update/create
   */
  public void setSimpleMetadatum(
      final SimpleMetadatum simpleMetadatum, final LocalDate date, final String newValue) {
    NodeParser.getNodeFromExpression(
            String.format("%s[@start='%s']", simpleMetadatum.xpath, date.toString()), node)
        .ifPresentOrElse(
            fnaNode -> fnaNode.setTextContent(newValue),
            () -> {
              // 1. Check if we have later FNAs and get the next closest one
              final Optional<SimpleProprietary> nextNode =
                  getNodes(simpleMetadatum.xpath).stream()
                      .filter(f -> f.getStart().isPresent() && f.getStart().get().isAfter(date))
                      .min(Comparator.comparing(f -> f.getStart().get()));

              // 2. Check if we have previous FNAs and get the next closest one
              final Optional<SimpleProprietary> previousNode =
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
              } else {
                newFnaElement.setAttribute("end", "unbestimmt");
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
   * Creates or updates an attribute of a simple node with the new passed value.
   *
   * @param attribute the attribute to be created/updated
   * @param date the specific date
   * @param newValue the new value
   */
  public void setAttributeOfSimpleMetadatum(
      final Attribute attribute, final LocalDate date, final String newValue) {
    getSimpleNodeAt(attribute.simpleMetadatum, date)
        .ifPresent(
            m -> {
              if (m.getValue().isEmpty()) {
                m.removeAttribute(attribute.name);
              } else {
                m.setAttribute(attribute.name, newValue);
              }
            });
  }

  /**
   * Retrieves all nodes of the specific metadatum.
   *
   * @param xpath of the node
   * @return list of all metadata as {@link SimpleProprietary}
   */
  public List<SimpleProprietary> getNodes(final String xpath) {
    return NodeParser.getNodesFromExpression(xpath, node).stream()
        .map(SimpleProprietary::new)
        .toList();
  }

  private Optional<SimpleProprietary> getSimpleNodeAt(
      final SimpleMetadatum simpleMetadatum, final LocalDate date) {
    final List<SimpleProprietary> valuesWithoutStartAndEnd =
        getNodes(simpleMetadatum.xpath).stream()
            .filter(f -> f.getStart().isEmpty() && f.getEnd().isEmpty())
            .toList();

    final List<SimpleProprietary> valuesWithStartOrAndEnd =
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
      return valuesWithStartOrAndEnd.stream().findFirst();
    } else {
      return valuesWithoutStartAndEnd.stream().findFirst();
    }
  }
}
