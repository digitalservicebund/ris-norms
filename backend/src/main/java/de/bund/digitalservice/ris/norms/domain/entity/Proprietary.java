package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
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

/**
 * Encapsulates metadata that is found in the proprietary section of the document. This includes
 * both metadata that is proprietary to the LDML.de standard and to DigitalService.
 */
@Getter
@AllArgsConstructor
public class Proprietary {

  private final Element element;

  /**
   * Retrieves the text content of the XML node for the given {@link Metadata}.
   * @param metadata - the given {@link Metadata}
   * @return the value or an optional empty
   */
  public Optional<String> getMetadataValue(final Metadata metadata) {
    return getMetadataParent(metadata.getNamespace()).flatMap(e ->
      NodeParser.getValueFromExpression(metadata.getXpath(), e)
    );
  }

  /**
   * Retrieves the text content of the XML node for the given {@link Metadata} assigned to the single element identified with the eId.
   * @param metadata - the given {@link Metadata}
   * @param eId - of the single element
   * @return the value or an optional empty
   */
  public Optional<String> getMetadataValue(final Metadata metadata, final EId eId) {
    return getMetadataParent(Namespace.METADATEN_RIS)
      .flatMap(parent ->
        NodeParser.getNodeFromExpression(
          "./einzelelement[@href='#%s']".formatted(eId.toString()),
          parent
        )
      )
      .flatMap(einzelElement ->
        NodeParser.getValueFromExpression(metadata.getXpath(), einzelElement)
      );
  }

  /**
   * Retrieves the text content of the XML node for FEDERFUEHRUNG at a specific date.
   *
   * @param date - the reference date
   * @return the value or an optional empty
   */
  public Optional<String> getRessort(final LocalDate date) {
    return findRessortElementByDate(date).map(m -> m.getTextContent().trim());
  }

  /**
   * Updates the text content or attribute value of the XML node for the given {@link Metadata}.
   * If the node is not present, it creates it anew (taking into account nested nodes). If value passed is empty, remove node.
   * @param metadata - the given {@link Metadata}
   * @param newValue - the new value for the text content or attribute value
   */
  public void setMetadataValue(final Metadata metadata, final String newValue) {
    final Element metadataNode = getOrCreateNode(
      getOrCreateMetadataParent(metadata.getNamespace()),
      metadata
    );
    if (metadata.isAttribute()) {
      if (StringUtils.isNotEmpty(newValue) && !newValue.equals("null")) {
        metadataNode.setAttribute(metadata.getTag(), newValue);
      } else {
        metadataNode.removeAttribute(metadata.getTag());
      }
    } else {
      if (StringUtils.isNotEmpty(newValue)) {
        metadataNode.setTextContent(newValue);
      } else {
        metadataNode.getParentNode().removeChild(metadataNode);
      }
    }
  }

  /**
   * Creates or updates the text content of the XML node for the given {@link Metadata} inside the single element identified by the eId.
   * If value passed is empty, remove node.
   *
   * @param metadata - the given {@link Metadata}
   * @param eId - the ID of the single element
   * @param newValue - the new value to be set
   */
  public void setMetadataValue(final Metadata metadata, final EId eId, final String newValue) {
    final Element parent = getOrCreateMetadataParent(metadata.getNamespace());
    final Element einzelElement = getOrCreateEinzelElement(parent, eId);
    final Element metadataNode = getOrCreateNode(einzelElement, metadata);
    if (metadata.isAttribute()) {
      if (StringUtils.isNotEmpty(newValue) && !newValue.equals("null")) {
        metadataNode.setAttribute(metadata.getTag(), newValue);
      } else {
        metadataNode.removeAttribute(metadata.getTag());
      }
    } else {
      if (StringUtils.isNotEmpty(newValue)) {
        metadataNode.setTextContent(newValue);
      } else {
        einzelElement.removeChild(metadataNode);
      }
    }
  }

  /**
   * Updates the text content the XML node for FEDERFUEHRUNG.It takes the "ab" and "bis" attributes into consideration.
   * @param newValue - the new value for the ressort
   * @param date - the date of the ressort
   */
  public void setRessort(final String newValue, final LocalDate date) {
    final Element parent = getOrCreateMetadataParent(Metadata.FEDERFUEHRUNG.getNamespace());

    // 1. Check if an element already exists for this date
    NodeParser.getNodeFromExpression(
      String.format("%s[@ab='%s']", Metadata.FEDERFUEHRUNG.getXpath(), date.toString()),
      parent
    ).ifPresentOrElse(
      nodeFound -> {
        if (StringUtils.isNotEmpty(newValue)) {
          nodeFound.setTextContent(newValue);
        } else {
          nodeFound.getParentNode().removeChild(nodeFound);
        }
      },
      () -> {
        if (StringUtils.isNotEmpty(newValue)) {
          // 2. Get the previous and next nodes
          final List<Element> federfuehrendNodes = NodeParser.getElementsFromExpression(
            Metadata.FEDERFUEHRUNG.getXpath(),
            parent
          );

          // Find the closest previous and next nodes based on @ab attribute
          final Optional<Element> previousNode = federfuehrendNodes
            .stream()
            .filter(f -> LocalDate.parse(f.getAttribute("ab")).isBefore(date))
            .max(Comparator.comparing(f -> LocalDate.parse(f.getAttribute("ab"))));

          final Optional<Element> nextNode = federfuehrendNodes
            .stream()
            .filter(f -> LocalDate.parse(f.getAttribute("ab")).isAfter(date))
            .min(Comparator.comparing(f -> LocalDate.parse(f.getAttribute("ab"))));

          // 3. Create the new <breg:federfuehrend> element
          final Element federfuehrungNode = NodeParser.getElementFromExpression(
            "./federfuehrung",
            parent
          ).orElseGet(() ->
            NodeCreator.createElement(
              Metadata.FEDERFUEHRUNG.getNamespace(),
              "federfuehrung",
              parent
            )
          );
          final Element newElement = NodeCreator.createElement(
            Metadata.FEDERFUEHRUNG.getNamespace(),
            Metadata.FEDERFUEHRUNG.getTag(),
            federfuehrungNode
          );
          newElement.setTextContent(newValue);
          newElement.setAttribute("ab", date.toString());

          // Set "bis" attribute based on the next node
          if (nextNode.isPresent()) {
            final LocalDate nextStart = LocalDate.parse(
              nextNode.get().getAttribute("ab")
            ).minusDays(1);
            newElement.setAttribute("bis", nextStart.toString());
          } else {
            newElement.setAttribute("bis", "unbestimmt");
          }
          // Set "bis" of the previous node to (date - 1) if exists
          previousNode.ifPresent(value -> value.setAttribute("bis", date.minusDays(1).toString()));
        }
      }
    );
  }

  /**
   * Gets the date the expression enters into force
   * @return the date of the entry into force
   */
  public Optional<LocalDate> getInkrafttreteDatum() {
    return getMetadataValue(Metadata.INKRAFT).map(LocalDate::parse);
  }

  /**
   * Gets the date the expression is no longer in force
   * @return the date of the expiry
   */
  public Optional<LocalDate> getAusserkrafttreteDatum() {
    return getMetadataValue(Metadata.AUSSERKRAFT).map(LocalDate::parse);
  }

  /**
   * Get the gegenstandlos metadata element (if it exists). If it exists it implies that the norm is gegenstandslos.
   * @return the {@link Gegenstandlos} element or empty if the norm is not gegenstandslos
   */
  public Optional<Gegenstandlos> getGegenstandlos() {
    return getMetadataParent(Gegenstandlos.NAMESPACE).flatMap(metadataElement ->
      NodeParser.getElementFromExpression(
        "./%s".formatted(Gegenstandlos.TAG_NAME),
        metadataElement
      ).map(Gegenstandlos::new)
    );
  }

  /**
   * Get the gegenstandlos metadata element (if it exists). If it does not exist it creates a new one.
   * @return the {@link Gegenstandlos} element
   */
  public Gegenstandlos getOrCreateGegenstandlos() {
    return getGegenstandlos().orElseGet(() -> {
      final Element metadataParent = getOrCreateMetadataParent(Gegenstandlos.NAMESPACE);
      final Gegenstandlos gegenstandlos = new Gegenstandlos(
        NodeCreator.createElement(Gegenstandlos.NAMESPACE, Gegenstandlos.TAG_NAME, metadataParent)
      );
      gegenstandlos.setSinceDate(LocalDate.now().toString());
      return gegenstandlos;
    });
  }

  /**
   * Gets the custom norms-application-only mods metadata.
   * @return {@link Optional} with the {@link CustomModsMetadata}
   */
  public Optional<CustomModsMetadata> getCustomModsMetadata() {
    return NodeParser.getElementFromExpression(
      "./" +
        getXpathExpression(Namespace.METADATEN_RIS) +
        "/" +
        getXpathExpression(Namespace.METADATEN_NORMS_APPLICATION_MODS),
      element
    ).map(CustomModsMetadata::new);
  }

  /**
   * Gets the custom norms-application-only mods metadata, if not present yet, it creates the node and return the custom object.
   *
   * @return The retrieved/created object of {@link CustomModsMetadata}
   */
  public CustomModsMetadata getOrCreateCustomModsMetadata() {
    return this.getCustomModsMetadata().orElseGet(() -> {
      final Element risMetadaten = getOrCreateMetadataParent(Namespace.METADATEN_RIS);
      final var newElement = NodeCreator.createElement(
        CustomModsMetadata.NAMESPACE,
        CustomModsMetadata.TAG_NAME,
        risMetadaten
      );
      return new CustomModsMetadata(newElement);
    });
  }

  /**
   * Deletes a metadata parent if it has no childs
   *
   * @param namespace - the namespace of the metadata parent
   */
  public void removeMetadataParentIfEmpty(final Namespace namespace) {
    getMetadataParent(namespace).ifPresent(metadataParent -> {
      if (!metadataParent.hasChildNodes()) {
        element.removeChild(metadataParent);
      }
    });
  }

  /**
   * Returns the metadata parent, if present
   * @param namespace of the metadata parent
   * @return an optional element
   */
  public Optional<Element> getMetadataParent(final Namespace namespace) {
    return NodeParser.getElementFromExpression("./" + getXpathExpression(namespace), element);
  }

  private String getXpathExpression(final Namespace namespace) {
    return "Q{" + namespace.getNamespaceUri() + "}legalDocML.de_metadaten";
  }

  private Element getOrCreateMetadataParent(final Namespace namespace) {
    return NodeParser.getElementFromExpression(
      "./" + getXpathExpression(namespace),
      element
    ).orElseGet(() -> NodeCreator.createElement(namespace, "legalDocML.de_metadaten", element));
  }

  private Element getOrCreateNode(final Element parent, final Metadata metadata) {
    final String[] parts = metadata.getXpath().replace("./", "").split("/");
    Element current = parent;
    for (String part : parts) {
      if (part.startsWith("@")) {
        // Check if it’s an attribute (e.g., "./beschliessendesOrgan/@qualifizierteMehrheit")
        return current; // Stop here and return parent of attribute
      }
      // Find or create the element
      Optional<Element> child = NodeParser.getElementFromExpression("./" + part, current);
      final Element finalCurrent = current;
      current = child.orElseGet(() ->
        NodeCreator.createElement(metadata.getNamespace(), part, finalCurrent)
      );
    }
    return current;
  }

  private Element getOrCreateEinzelElement(final Element parent, final EId eId) {
    return NodeParser.getElementFromExpression(
      "./einzelelement[@href='#%s']".formatted(eId.toString()),
      parent
    ).orElseGet(() -> {
      Element einzelElement = NodeCreator.createElement(
        Namespace.METADATEN_RIS,
        "einzelelement",
        parent
      );
      einzelElement.setAttribute("href", "#" + eId);
      return einzelElement;
    });
  }

  private Optional<Element> findRessortElementByDate(final LocalDate date) {
    return getMetadataParent(Metadata.FEDERFUEHRUNG.getNamespace()).flatMap(parent ->
      NodeParser.getElementsFromExpression(Metadata.FEDERFUEHRUNG.getXpath(), parent)
        .stream()
        .filter(child -> {
          final String startAttr = child.getAttribute("ab");
          final String endAttr = child.getAttribute("bis");
          final LocalDate startDate = LocalDate.parse(startAttr);
          final LocalDate endDate = endAttr.equals("unbestimmt")
            ? LocalDate.MAX
            : LocalDate.parse(endAttr);
          return (
            (date.equals(startDate) || date.isAfter(startDate)) &&
            (date.equals(endDate) || date.isBefore(endDate))
          );
        })
        .findFirst()
    );
  }
}
