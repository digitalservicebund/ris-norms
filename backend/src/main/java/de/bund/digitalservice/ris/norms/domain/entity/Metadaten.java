package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

interface MetadataInterface {
  String getXpath();
}

/**
 * Abstract class with common functionalities between meta:legalDocML.de_metadaten_ds and
 * meta:legalDocML.de_metadaten
 *
 * @param <T> the concrete enum class implementing {@link MetadataInterface}
 */
@Getter
@AllArgsConstructor
public abstract class Metadaten<T extends MetadataInterface> {

  private final Node node;
  private final String startAttribute;
  private final String endAttribute;

  /**
   * It returns the value for a xpath at a specific date. If no matching value @start or @end is
   * found, it will retrieve the value from the node with neither @start nor @end.
   *
   * @param simpleMetadatum the enum metadatum
   * @param date the specific date
   * @return an optional value, if found.
   */
  public Optional<String> getSimpleMetadatum(final T simpleMetadatum, final LocalDate date) {
    return getSimpleNodeAt(simpleMetadatum, date).map(SimpleProprietary::getValue);
  }

  /**
   * Creates or updates a metadata following the xpath with simple value at the specific date. It
   * sets the @start attribute to the given date. If node not present, it will create a new one and
   * set also the @end attribute if there is a later fna node. The value would be the @start date of
   * the next closest FNA minus 1 day. Also, if there is a previous node it will set the @end
   * attribute to 1 day before of the newly updated/created node. Finally, it will also set the @end
   * attribute of those nodes with neither @start nor @end attributes.
   *
   * @param simpleMetadatum - the simple enum metadatum
   * @param date - the specific date
   * @param newValue - the new value to update/create
   */
  public void updateSimpleMetadatum(
    final T simpleMetadatum,
    final LocalDate date,
    final String newValue
  ) {
    NodeParser
      .getNodeFromExpression(
        String.format(
          "%s[@%s='%s']",
          simpleMetadatum.getXpath(),
          getStartAttribute(),
          date.toString()
        ),
        node
      )
      .ifPresentOrElse(
        nodeFound -> {
          if (StringUtils.isNotEmpty(newValue)) {
            nodeFound.setTextContent(newValue);
          } else {
            nodeFound.getParentNode().removeChild(nodeFound);
          }
        },
        () -> {
          if (StringUtils.isNotEmpty(newValue)) {
            // 1. Before updating creating new node, get next previous node and next later node
            final Optional<SimpleProprietary> previousNode = getNodes(simpleMetadatum.getXpath())
              .stream()
              .filter(f -> f.getStart().isPresent() && f.getStart().get().isBefore(date))
              .max(Comparator.comparing(f -> f.getStart().get()));
            final Optional<SimpleProprietary> nextNode = getNodes(simpleMetadatum.getXpath())
              .stream()
              .filter(f -> f.getStart().isPresent() && f.getStart().get().isAfter(date))
              .min(Comparator.comparing(f -> f.getStart().get()));

            // 2. Create new meta:fna node with the @start value and @end and set @start of next
            // one
            createNewNode(simpleMetadatum, newValue, date, nextNode.orElse(null));

            // 3. Then also set @end of a previous one, if present
            previousNode.ifPresent(value ->
              ((Element) value.getNode()).setAttribute(
                  getEndAttribute(),
                  date.minusDays(1).toString()
                )
            );

            // 4. And set @end of nodes without @start and @end to one day before given date
            getNodes(simpleMetadatum.getXpath())
              .stream()
              .filter(f -> f.getStart().isEmpty() && f.getEnd().isEmpty())
              .forEach(value ->
                ((Element) value.getNode()).setAttribute(
                    getEndAttribute(),
                    date.minusDays(1).toString()
                  )
              );
          }
        }
      );
  }

  /**
   * Retrieves all nodes of the specific metadatum.
   *
   * @param xpath of the node
   * @return list of all metadata as {@link SimpleProprietary}
   */
  public List<SimpleProprietary> getNodes(final String xpath) {
    return NodeParser
      .getNodesFromExpression(xpath, node)
      .stream()
      .map(SimpleProprietary::new)
      .toList();
  }

  protected Optional<SimpleProprietary> getSimpleNodeAt(
    final T simpleMetadatum,
    final LocalDate date
  ) {
    final List<SimpleProprietary> valuesWithoutStartAndEnd = getNodes(simpleMetadatum.getXpath())
      .stream()
      .filter(f -> f.getStart().isEmpty() && f.getEnd().isEmpty())
      .toList();

    final List<SimpleProprietary> valuesWithStartOrAndEnd = getNodes(simpleMetadatum.getXpath())
      .stream()
      .filter(f -> f.getStart().isPresent() || f.getEnd().isPresent())
      .filter(f ->
        f.getStart().map(start -> date.isEqual(start) || date.isAfter(start)).orElse(true)
      )
      .filter(f -> f.getEnd().map(end -> date.isEqual(end) || date.isBefore(end)).orElse(true))
      .toList();
    if (!valuesWithStartOrAndEnd.isEmpty()) {
      return valuesWithStartOrAndEnd.stream().findFirst();
    } else {
      return valuesWithoutStartAndEnd.stream().findFirst();
    }
  }

  private void createNewNode(
    final T simpleMetadatum,
    final String newValue,
    final LocalDate date,
    final SimpleProprietary nextNode
  ) {
    Node parentNode = node;
    final String[] pathElements = simpleMetadatum.getXpath().replace("./", "").split("/");

    for (int i = 0; i < pathElements.length; i++) {
      final String elementName = pathElements[i];
      final boolean isLastElement = i == pathElements.length - 1;

      if (isLastElement) {
        // Create and set the new element
        final Element newElement = NodeCreator.createElement(
          String.format("meta:%s", elementName),
          parentNode
        );
        newElement.setTextContent(newValue);
        newElement.setAttribute(getStartAttribute(), date.toString());

        if (nextNode != null && nextNode.getStart().isPresent()) {
          final LocalDate nextStart = nextNode.getStart().get().minusDays(1);
          newElement.setAttribute(getEndAttribute(), nextStart.toString());
        } else {
          newElement.setAttribute(getEndAttribute(), "unbestimmt");
        }
      } else {
        // Get or create child node
        final Optional<Node> childNode = NodeParser.getNodeFromExpression(
          "./%s".formatted(elementName),
          parentNode
        );
        if (childNode.isPresent()) {
          parentNode = childNode.get();
        } else {
          parentNode = NodeCreator.createElement(String.format("meta:%s", elementName), parentNode);
        }
      }
    }
  }
}
