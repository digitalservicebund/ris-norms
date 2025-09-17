package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.domain.entity.AmendedNormExpressions;
import de.bund.digitalservice.ris.norms.domain.entity.CustomModsMetadata;
import de.bund.digitalservice.ris.norms.domain.entity.Geltungszeiten;
import de.bund.digitalservice.ris.norms.domain.entity.Namespace;
import de.bund.digitalservice.ris.norms.domain.entity.ZielnormReferences;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Service for removing elements within a LegalDocML.de element that are empty and should only exist if they have child
 * elements.
 */
@Service
public class LdmlDeEmptyElementRemover {

  private static final Map<Namespace, Set<String>> ELEMENTS_TO_REMOVE_IF_EMPTY = Map.of(
    Namespace.INHALTSDATEN,
    Set.of(
      "proprietary",
      "analysis",
      "activeModifications",
      "passiveModifications",
      "notes",
      "lifecycle",
      "components"
    ),
    Namespace.METADATEN_BUNDESREGIERUNG,
    Set.of("legalDocML.de_metadaten", "federfuehrung"),
    Namespace.METADATEN_RIS,
    Set.of("legalDocML.de_metadaten", "einzelelement"),
    Namespace.METADATEN_NORMS_APPLICATION_MODS,
    Set.of(
      CustomModsMetadata.TAG_NAME,
      ZielnormReferences.TAG_NAME,
      Geltungszeiten.TAG_NAME,
      AmendedNormExpressions.TAG_NAME
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
}
