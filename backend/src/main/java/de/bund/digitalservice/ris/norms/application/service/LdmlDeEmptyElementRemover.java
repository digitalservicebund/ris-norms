package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.domain.entity.Geltungszeiten;
import de.bund.digitalservice.ris.norms.domain.entity.Namespace;
import de.bund.digitalservice.ris.norms.domain.entity.ZielnormReferences;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Service for removing elements within a LegalDocML.de element that are empty and should only exist if they have child
 * elements.
 */
@Service
public class LdmlDeEmptyElementRemover {

  private static final Map<Namespace, Set<String>> ELEMENTS_TO_REMOVE_IF_EMPTY = Set.of(
    new ElementType(Geltungszeiten.NAMESPACE, Geltungszeiten.TAG_NAME),
    new ElementType(ZielnormReferences.NAMESPACE, ZielnormReferences.TAG_NAME),
    new ElementType(Namespace.METADATEN_RIS, "einzelelement")
  )
    .stream()
    .collect(
      Collectors.toMap(
        ElementType::namespace,
        a -> {
          var set = new HashSet<String>();
          set.add(a.tagName);
          return set;
        },
        (a, b) -> {
          a.addAll(b);
          return a;
        }
      )
    );

  /**
   * Removes empty elements.
   * @param element the element in which the empty elements should be removed.
   */
  public void removeEmptyElements(Element element) {
    NodeParser.nodeListToList(element.getChildNodes()).forEach(child -> {
        if (child.getNodeType() == Node.ELEMENT_NODE) {
          removeEmptyElements((Element) child);
        }
      });

    if (!hasChildElements(element)) {
      var elementsToRemoveOfNamespace = ELEMENTS_TO_REMOVE_IF_EMPTY.getOrDefault(
        Namespace.getByUri(element.getNamespaceURI()),
        Set.of()
      );

      if (elementsToRemoveOfNamespace.contains(element.getLocalName())) {
        element.getParentNode().removeChild(element);
      }
    }
  }

  private boolean hasChildElements(Element element) {
    var childNodes = element.getChildNodes();

    for (int i = 0; i < childNodes.getLength(); i++) {
      if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
        return true;
      }
    }

    return false;
  }

  record ElementType(Namespace namespace, String tagName) {}
}
