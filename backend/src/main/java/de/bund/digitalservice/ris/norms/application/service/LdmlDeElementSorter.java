package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Service for fixing the order of nodes within a LegalDocML.de element based on the xsd-Schema.
 */
public class LdmlDeElementSorter {

  private final List<Document> schemas;

  // All sequences (without mixed content) are simple. As in, they only consist of xs:element and max. one xs:group
  // which is also the last part of the sequence. Therefore, we can represent these sequences as a simple lists of
  // elements.

  public LdmlDeElementSorter(
    @Value(
      "classpath:/LegalDocML.de/1.7.2/schema/legalDocML.de-baukasten.xsd"
    ) Resource baukastenXsdSchema,
    @Value(
      "classpath:/LegalDocML.de/1.7.2/schema/legalDocML.de-metadaten.xsd"
    ) Resource metadatenXsdSchema,
    @Value(
      "classpath:/LegalDocML.de/1.7.2/schema/legalDocML.de-regelungstextverkuendungsfassung.xsd"
    ) Resource regelungstextverkuendungsfassungXsdSchema
  ) {
    this.schemas =
    List.of(
      getSchemaFor(baukastenXsdSchema),
      getSchemaFor(metadatenXsdSchema),
      getSchemaFor(regelungstextverkuendungsfassungXsdSchema)
    );
  }

  /**
   * Sorts all nodes of the given element based on the XSD-Schema.
   * @param element the element of which the order should be fixed.
   */
  public void sortElements(Element element) {
    sortChildElements(element);

    NodeParser
      .nodeListToList(element.getChildNodes())
      .forEach(child -> {
        if (child.getNodeType() == Node.ELEMENT_NODE) {
          sortElements((Element) child);
        }
      });
  }

  private Set<String> findTypesInAllSchemas(String tagName) {
    for (Document schema : schemas) {
      var types = NodeParser
        .getNodesFromExpression("//element[@name = \"%s\"]/@type".formatted(tagName), schema)
        .stream()
        .map(Node::getNodeValue)
        .collect(Collectors.toSet());
      if (!types.isEmpty()) {
        return types;
      }
    }

    return Set.of();
  }

  private Optional<Element> findDefinitionInAllSchemas(String typeName) {
    for (Document schema : schemas) {
      var typeDefinition = NodeParser.getElementFromExpression(
        "/schema/(complexType | group | element)[@name = \"%s\"]".formatted(typeName),
        schema
      );

      if (typeDefinition.isPresent()) {
        return typeDefinition;
      }
    }

    return Optional.empty();
  }

  private Optional<String> findTypeForElement(Element element) {
    var tagName = element.getLocalName();
    var types = findTypesInAllSchemas(tagName);

    if (types.size() > 1) {
      // the FRBR elements are the only once that use different complex types for the same akn element. But as all of them have an empty sequence as only child we can ignore them.
      if (tagName.startsWith("FRBR")) {
        return Optional.empty();
      }

      // There are two types for p elements (p and ortUndDatum) but as both allow mixed content there is nothing to sort for us.
      if (tagName.equals("p")) {
        return Optional.empty();
      }

      // There are two types for block elements (block and datumContainer) but as both allow mixed content there is nothing to sort for us.
      if (tagName.equals("block")) {
        return Optional.empty();
      }

      // There are two types for formula elements (schlussformel and eingangsformel) but both allow the exact same content.
      if (tagName.equals("formula")) {
        return Optional.of("schlussformel");
      }

      // There are two types for blockContainer elements. Both can have different sequences.
      if (tagName.equals("blockContainer")) {
        if (
          element.getParentNode().getLocalName().equals("quotedStructure") ||
          element.getParentNode().getLocalName().equals("preamble")
        ) {
          return Optional.of("verzeichniscontainer");
        }

        if (element.getParentNode().getLocalName().equals("conclusions")) {
          return Optional.of("signaturblock");
        }
      }

      // There are two types for intro elements. Both can have different sequences.
      if (tagName.equals("intro")) {
        if (element.getParentNode().getLocalName().equals("citations")) {
          return Optional.of("ermaechtigungsnormEingangssatz");
        }

        if (element.getParentNode().getLocalName().equals("list")) {
          return Optional.of("textVorUntergliederung");
        }
      }

      // There are two types for wrapUp elements. Both can have different sequences.
      if (tagName.equals("wrapUp")) {
        if (element.getParentNode().getLocalName().equals("citations")) {
          return Optional.of("ermaechtigungsnormSchlusssatz");
        }

        if (element.getParentNode().getLocalName().equals("list")) {
          return Optional.of("textNachUntergliederung");
        }
      }
      // There are two types for signature elements but as both allow mixed content there is nothing to sort for us
      if (tagName.equals("signature")) {
        return Optional.empty();
      }

      throw new RuntimeException(
        "Too many possible types for %s (%s)".formatted(tagName, String.join(", ", types))
      );
    }

    if (types.isEmpty()) {
      // If we can find no nested xs:element with the tagName as a name that have a @type there should be no
      // xs:complexType for this element. For some elements a global xs:element definition exists. This does not have a
      // @type attribute but can specify a sequence. To handle this case we return the tag name as type.
      return Optional.of(tagName);
    }

    return Optional.of(types.iterator().next());
  }

  private void sortChildElements(Element element) {
    var type = findTypeForElement(element);

    if (type.isEmpty()) {
      return;
    }

    var elementOrder = getElementOrderForType(type.get());

    if (elementOrder.isEmpty()) {
      return;
    }

    var childNodes = NodeParser.nodeListToList(element.getChildNodes());
    childNodes.forEach(element::removeChild);

    // keep non-element nodes (e.g. comments). We just place them at the beginning because that's simple.
    childNodes
      .stream()
      .filter(childNode -> childNode.getNodeType() != Node.ELEMENT_NODE)
      .forEach(element::appendChild);

    childNodes
      .stream()
      .filter(childNode -> childNode.getNodeType() == Node.ELEMENT_NODE)
      .sorted(
        Comparator.comparing(childNode -> {
          var index = elementOrder.indexOf(childNode.getLocalName());

          // elements which are not part of the ordered elements always need to be at the end
          if (index == -1) {
            return Integer.MAX_VALUE;
          }

          return index;
        })
      )
      .forEach(element::appendChild);
  }

  private List<String> getElementOrderForType(String typeName) {
    var complexType = findDefinitionInAllSchemas(typeName);

    if (complexType.isEmpty()) {
      return List.of();
    }

    // Skip sorting for mixed content as there can be text nodes in between the sequence which we therefore cannot
    // reliably reorder
    if (complexType.get().getAttribute("mixed").equals("true")) {
      return List.of();
    }

    var elements = NodeParser.getElementsFromExpression("./sequence/*", complexType.get());

    return elements
      .stream()
      .flatMap(element -> {
        if (Objects.equals(element.getTagName(), "xs:element")) {
          return Stream.of(element.getAttribute("name"));
        }
        if (Objects.equals(element.getTagName(), "xs:group")) {
          return getElementOrderForType(element.getAttribute("ref")).stream();
        }
        if (Objects.equals(element.getTagName(), "xs:choice")) {
          return Stream.empty();
        }
        throw new RuntimeException("Unknown tag: " + element.getTagName());
      })
      .toList();
  }

  // Helper method to load a Schema from a Resource.
  private Document getSchemaFor(Resource schemaResource) {
    try {
      return XmlMapper.toDocument(schemaResource.getContentAsString(StandardCharsets.UTF_8));
    } catch (IOException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }
  }
}
