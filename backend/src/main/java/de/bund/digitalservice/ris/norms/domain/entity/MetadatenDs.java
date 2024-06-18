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
  private static final String XPATH_TEMPLATE = "%s[@start='%s']";

  /** The list of all simple metadata. They consist of a single string property. */
  public enum SimpleMetadatum {
    FNA("./fna"),
    ART("./art"),
    TYP("./typ"),
    SUBTYP("./subtyp"),
    BEZEICHNUNG_IN_VORLAGE("./bezeichnungInVorlage"),
    ART_DER_NORM("./artDerNorm"),
    NORMGEBER("./normgeber");

    private final String xpath;

    SimpleMetadatum(final String xpath) {
      this.xpath = xpath;
    }
  }

  // TODO tbh I'd rather integrate metadata that have an attribute into SimpleMetadatum
  /**
   * The list of all complex metadata. They consist of a single string property, and they might have
   * attributes.
   */
  public enum ComplexMetadatum {
    BESCHLIESSENDES_ORGAN("beschliessendesOrgan", new String[] {"qualifizierteMehrheit"});

    private final String xpath;
    private final Map<String, String> attributeXpaths;

    ComplexMetadatum(final String nodeName, final String[] attributeNames) {
      this.xpath = String.format("./%s", nodeName);
      this.attributeXpaths = new HashMap<>();
      for (String attributeName : attributeNames) {
        this.attributeXpaths.put(attributeName, this.xpath + "@" + attributeName);
      }
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
    return getProprietaryValueAt(simpleMetadatum.xpath, date).map(ProprietaryValue::getValue);
  }

  /**
   * It returns the value for a xpath at a specific date. If no matching value @start or @end is
   * found, it will retrieve the value from the node with neither @start nor @end.
   *
   * @param complexMetadatum the enum metadatum
   * @param date the specific date
   * @return an optional value, if found.
   */
  public Optional<String> getComplexValueAt(
      final ComplexMetadatum complexMetadatum, final LocalDate date) {
    return getProprietaryValueAt(complexMetadatum.xpath, date).map(ProprietaryValue::getValue);
  }

  /**
   * It returns the attribute value for a given attributeName, a xpath and a specific date. If no
   * matching value @start or @end is found, it will retrieve the attribute value from the node with
   * neither @start nor @end.
   *
   * @param complexMetadatum the enum metadatum which has an attribute
   * @param date the specific date
   * @param attributeName the name of the attribute
   * @return an optional value, if found.
   */
  public Optional<String> getAttributeValueAt(
      final ComplexMetadatum complexMetadatum, final LocalDate date, final String attributeName) {
    var simpleProprietaryValueOptional = getProprietaryValueAt(complexMetadatum.xpath, date);
    if (simpleProprietaryValueOptional.isPresent()) {
      return simpleProprietaryValueOptional.get().getAttribute(attributeName);
    }
    return Optional.empty();
  }

  private Optional<ProprietaryValue> getProprietaryValueAt(
      final String xpath, final LocalDate date) {
    final List<ProprietaryValue> valuesWithoutStartAndEnd =
        getNodes(xpath).stream()
            .filter(f -> f.getStart().isEmpty() && f.getEnd().isEmpty())
            .toList();

    final List<ProprietaryValue> valuesWithStartOrAndEnd =
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
      return valuesWithStartOrAndEnd.stream().findFirst();
    } else {
      return valuesWithoutStartAndEnd.stream().findFirst();
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
   * @param simpleMetadatum - the simple enum metadatum
   * @param date - the specific date
   * @param newValue - the new value to update/create
   */
  public void setSimpleProprietaryMetadata(
      final SimpleMetadatum simpleMetadatum, final LocalDate date, final String newValue) {
    setProprietaryMetadata(simpleMetadatum.xpath, date, newValue);
  }

  /**
   * Creates or updates a metadata with complex value at the specific date. It sets the @start
   * attribute to the given date. If node not present, it will create a new one and set also
   * the @end attribute if there is a later fna node. The value would be the @start date of the next
   * closest FNA minus 1 day. Also, if there is a previous node it will set the @end attribute to 1
   * day before of the newly updated/created node. Finally, it will also set the @end attribute of
   * those nodes with neither @start nor @end attributes. Depending on the newValue it will either
   * remove all attributes (when null) or set them to the desired value.
   *
   * @param complexMetadatum - the complex enum metadatum
   * @param date - the specific date
   * @param newValue - the new value to update/create
   * @param attributes - the attributes as hashMap. Values are passed as Objects. Make sure to
   *     supply an Object with toString method.
   */
  public void setComplexProprietaryMetadata(
      final ComplexMetadatum complexMetadatum,
      final LocalDate date,
      final String newValue,
      final Map<String, Object> attributes) {

    setProprietaryMetadata(complexMetadatum.xpath, date, newValue);

    if (newValue == null || newValue.trim().isEmpty()) {
      removeAllComplexProprietaryMetadataAttributes(complexMetadatum, date, attributes);
    } else {
      setAllComplexProprietaryMetadataAttributes(complexMetadatum, date, attributes);
    }
  }

  private void setProprietaryMetadata(
      final String xpath, final LocalDate date, final String newValue) {
    NodeParser.getNodeFromExpression(String.format(XPATH_TEMPLATE, xpath, date.toString()), node)
        .ifPresentOrElse(
            fnaNode -> fnaNode.setTextContent(newValue),
            () -> {
              // 1. Check if we have later FNAs and get the next closest one
              final Optional<ProprietaryValue> nextNode =
                  getNodes(xpath).stream()
                      .filter(f -> f.getStart().isPresent() && f.getStart().get().isAfter(date))
                      .min(Comparator.comparing(f -> f.getStart().get()));

              // 2. Check if we have previous FNAs and get the next closest one
              final Optional<ProprietaryValue> previousNode =
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
              getNodes(xpath).stream()
                  .filter(f -> f.getStart().isEmpty() && f.getEnd().isEmpty())
                  .forEach(
                      value ->
                          ((Element) value.getNode())
                              .setAttribute("end", date.minusDays(1).toString()));
            });
  }

  private void removeAllComplexProprietaryMetadataAttributes(
      final ComplexMetadatum complexMetadatum,
      final LocalDate date,
      final Map<String, Object> attributes) {
    for (String attributeName : attributes.keySet()) {
      removeComplexProprietaryMetadataAttribute(complexMetadatum, date, attributeName);
    }
  }

  private void removeComplexProprietaryMetadataAttribute(
      final ComplexMetadatum complexMetadatum, final LocalDate date, final String attributeName) {
    NodeParser.getNodeFromExpression(
            String.format(XPATH_TEMPLATE, complexMetadatum.xpath, date.toString()), node)
        .ifPresent(
            nodeWithAttribute ->
                ((Element) nodeWithAttribute)
                    .removeAttribute(complexMetadatum.attributeXpaths.get(attributeName)));
  }

  private void setAllComplexProprietaryMetadataAttributes(
      final ComplexMetadatum complexMetadatum,
      final LocalDate date,
      final Map<String, Object> attributes) {
    for (Map.Entry<String, Object> entry : attributes.entrySet()) {
      setComplexProprietaryMetadataAttribute(
          complexMetadatum, date, entry.getKey(), entry.getValue());
    }
  }

  private void setComplexProprietaryMetadataAttribute(
      final ComplexMetadatum complexMetadatum,
      final LocalDate date,
      final String attributeName,
      final Object newAttributeValue) {
    NodeParser.getNodeFromExpression(
            String.format(XPATH_TEMPLATE, complexMetadatum.xpath, date.toString()), node)
        .ifPresent(
            selectedNode ->
                ((Element) selectedNode).setAttribute(attributeName, newAttributeValue.toString()));
  }

  /**
   * Retrieves all nodes of the specific metadatum.
   *
   * @param xpath of the node
   * @return list of all metadata as {@link ProprietaryValue}
   */
  public List<ProprietaryValue> getNodes(final String xpath) {
    return NodeParser.getNodesFromExpression(xpath, node).stream()
        .map(ProprietaryValue::new)
        .toList();
  }
}
