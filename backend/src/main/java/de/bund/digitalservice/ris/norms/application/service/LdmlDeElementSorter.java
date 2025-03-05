package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.LdmlDeElementSortingException;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Service for fixing the order of nodes within a LegalDocML.de element based on the xsd-Schema. Only sorts nodes which
 * do not allow mixed-content.
 */
@Service
public class LdmlDeElementSorter {

  private final List<Document> schemas;
  private final Logger logger = LoggerFactory.getLogger(LdmlDeElementSorter.class);

  // General idea:
  // 1. search for the element-name in the schema and try to figure out which schema-type it is
  // 2. lookup the definition of that schema-type and figure out the sequence the elements should have
  // 3. reorder the child elements based on this sequence
  // 4. do the same for all child elements

  // Within the LegalDocMl.de schemas all sequences (that do not allow mixed content) are simple. As in, they only
  // consist of xs:element and max. one xs:group which is also the last part of the sequence. Therefore, we can model
  // these sequences as a simple lists that shown in which order the elements must appear.

  public LdmlDeElementSorter(XsdSchemaService schemaService) {
    this.schemas = schemaService.loadLdmlDeSchemaDocuments();
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

  private void sortChildElements(Element element) {
    var elementOrder = findChildElementOrder(element);

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

  private List<String> findChildElementOrder(Element element) {
    return findTypeForElement(element).map(this::findElementOrderForType).orElse(List.of());
  }

  private Optional<String> findTypeForElement(Element element) {
    var tagName = element.getLocalName();
    var types = findPossibleTypesForTagName(tagName);

    if (types.isEmpty()) {
      // If we can find no nested xs:element with the tagName as a name that have a @type there should be no
      // xs:complexType for this element. For some elements a global xs:element definition exists. This does not have a
      // @type attribute but can specify a sequence. To handle this case we return the tag name as the schema-type.
      return Optional.of(tagName);
    }

    if (types.size() == 1) {
      return Optional.of(types.iterator().next());
    }

    // There are a bunch of cases where more than one schema-type exists for a tag-name. This tries to figure out which
    // one is the correct one for the given element.

    // the FRBR elements all do not have any child elements. Therefore, we can ignore them.
    if (tagName.startsWith("FRBR")) {
      return Optional.empty();
    }

    return switch (tagName) {
      // There are two types for p, block and signature elements but as all allow mixed content there is nothing to sort
      // for us.
      case "p", "block", "signature" -> Optional.empty();
      // There are two types for formula elements but both allow the exact same content, so just default to one.
      case "formula" -> Optional.of("schlussformel");
      // There are two types for blockContainer elements. Both can have different sequences.
      case "blockContainer" -> {
        if (
          element.getParentNode().getLocalName().equals("quotedStructure") ||
          element.getParentNode().getLocalName().equals("preamble")
        ) {
          yield Optional.of("verzeichniscontainer");
        }

        if (element.getParentNode().getLocalName().equals("conclusions")) {
          yield Optional.of("signaturblock");
        }

        logger.debug(
          "Skip ordering elements: Unable to determine which type of blockContainer element is used."
        );
        yield Optional.empty();
      }
      // There are two types for intro elements. Both can have different sequences.
      case "intro" -> {
        if (element.getParentNode().getLocalName().equals("citations")) {
          yield Optional.of("ermaechtigungsnormEingangssatz");
        }

        if (element.getParentNode().getLocalName().equals("list")) {
          yield Optional.of("textVorUntergliederung");
        }

        logger.debug(
          "Skip ordering elements: Unable to determine which type of intro element is used."
        );
        yield Optional.empty();
      }
      // There are two types for wrapUp elements. Both can have different sequences.
      case "wrapUp" -> {
        if (element.getParentNode().getLocalName().equals("citations")) {
          yield Optional.of("ermaechtigungsnormSchlusssatz");
        }

        if (element.getParentNode().getLocalName().equals("list")) {
          yield Optional.of("textNachUntergliederung");
        }

        logger.debug(
          "Skip ordering elements: Unable to determine which type of wrapUp element is used."
        );
        yield Optional.empty();
      }
      default -> throw new LdmlDeElementSortingException(
        "Too many possible types for %s (%s) in schema".formatted(tagName, String.join(", ", types))
      );
    };
  }

  // Cache of possible types for every tag name. Helps a lot with the performance.
  private final Map<String, Set<String>> typesByTag = new HashMap<>();

  /**
   * Finds the possible schema-types that an element tag name could represent.
   */
  private Set<String> findPossibleTypesForTagName(String tagName) {
    if (typesByTag.containsKey(tagName)) {
      return typesByTag.get(tagName);
    }

    var types = schemas
      .stream()
      .map(schema ->
        NodeParser
          .getNodesFromExpression("//element[@name = \"%s\"]/@type".formatted(tagName), schema)
          .stream()
          .map(Node::getNodeValue)
          .collect(Collectors.toSet())
      )
      .filter(Predicate.not(Set::isEmpty))
      .findFirst()
      .orElse(Set.of());

    typesByTag.put(tagName, types);
    return types;
  }

  private final Map<String, List<String>> elementOrderByType = new HashMap<>();

  private List<String> findElementOrderForType(String typeName) {
    if (elementOrderByType.containsKey(typeName)) {
      return elementOrderByType.get(typeName);
    }

    var schemaType = findDefinitionForSchemaType(typeName);

    if (schemaType.isEmpty()) {
      elementOrderByType.put(typeName, List.of());
      return List.of();
    }

    // Skip sorting for mixed content as there can be text nodes in between the sequence which we therefore cannot
    // reliably reorder
    if (schemaType.get().getAttribute("mixed").equals("true")) {
      elementOrderByType.put(typeName, List.of());
      return List.of();
    }

    var elements = NodeParser.getElementsFromExpression("./sequence/*", schemaType.get());

    var elementOrder = elements
      .stream()
      .flatMap(element ->
        switch (element.getTagName()) {
          case "xs:element" -> Stream.of(element.getAttribute("name"));
          case "xs:group" -> findElementOrderForType(element.getAttribute("ref")).stream();
          case "xs:choice" -> Stream.empty();
          default -> throw new LdmlDeElementSortingException(
            "Unexpected element in schema sequence: %s".formatted(element.getTagName())
          );
        }
      )
      .toList();
    elementOrderByType.put(typeName, elementOrder);
    return elementOrder;
  }

  /**
   * Finds the element that holds the definition of a schema-type.
   */
  private Optional<Element> findDefinitionForSchemaType(String typeName) {
    return schemas
      .stream()
      .map(schema ->
        NodeParser.getElementFromExpression(
          "/schema/(complexType | group | element)[@name = \"%s\"]".formatted(typeName),
          schema
        )
      )
      .filter(Optional::isPresent)
      .map(Optional::get)
      .findFirst();
  }
}
