package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.domain.entity.AmendedNormExpressions;
import de.bund.digitalservice.ris.norms.domain.entity.CustomModsMetadata;
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
    new ElementType(Namespace.INHALTSDATEN, "proprietary"),
    new ElementType(Namespace.INHALTSDATEN, "analysis"),
    new ElementType(Namespace.INHALTSDATEN, "activeModifications"),
    new ElementType(Namespace.INHALTSDATEN, "passiveModifications"),
    new ElementType(Namespace.INHALTSDATEN, "notes"),
    new ElementType(Namespace.INHALTSDATEN, "lifecycle"),
    new ElementType(Namespace.INHALTSDATEN, "components"),
    new ElementType(Namespace.METADATEN_BUNDESREGIERUNG, "legalDocML.de_metadaten"),
    new ElementType(Namespace.METADATEN_BUNDESREGIERUNG, "federfuehrung"),
    new ElementType(Namespace.METADATEN_RIS, "legalDocML.de_metadaten"),
    new ElementType(Namespace.METADATEN_RIS, "einzelelement"),
    new ElementType(CustomModsMetadata.NAMESPACE, CustomModsMetadata.TAG_NAME),
    new ElementType(ZielnormReferences.NAMESPACE, ZielnormReferences.TAG_NAME),
    new ElementType(Geltungszeiten.NAMESPACE, Geltungszeiten.TAG_NAME),
    new ElementType(AmendedNormExpressions.NAMESPACE, AmendedNormExpressions.TAG_NAME)
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
