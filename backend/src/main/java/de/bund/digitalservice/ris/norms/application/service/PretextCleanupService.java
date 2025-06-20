package de.bund.digitalservice.ris.norms.application.service;

import static de.bund.digitalservice.ris.norms.domain.entity.Namespace.*;

import de.bund.digitalservice.ris.norms.domain.entity.Namespace;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Service for removing metadata that should not be published when doing a pre-text publication
 */
@Service
@Slf4j
public class PretextCleanupService {

  private static final Set<Namespace> ALLOWED_METADATA = Set.of(
    METADATEN_RECHTSETZUNGSDOKUMENT,
    METADATEN_REGELUNGSTEXT,
    METADATEN_BUNDESREGIERUNG
  );

  /**
   * Cleans the metadata of all documents associated with the given {@link Norm}.
   *
   * @param norm The {@link Norm} containing the documents to be cleaned.
   */
  public void clean(Norm norm) {
    norm
      .getDokumente()
      .forEach(dokument -> {
        Optional<Proprietary> proprietaryOpt = dokument.getMeta().getProprietary();
        if (proprietaryOpt.isEmpty()) {
          return;
        }

        final NodeList childNodes = proprietaryOpt.get().getElement().getChildNodes();

        // Iterate backwards to safely remove elements while iterating
        for (int i = childNodes.getLength() - 1; i >= 0; i--) {
          final Node childNode = childNodes.item(i);

          // Skip non-element nodes
          if (childNode.getNodeType() != Node.ELEMENT_NODE) {
            continue;
          }

          final Element childElement = (Element) childNode;
          final String namespaceUri = childElement.getNamespaceURI();

          try {
            final Namespace currentNamespace = Namespace.getByUri(namespaceUri);
            if (!ALLOWED_METADATA.contains(currentNamespace)) childElement
              .getParentNode()
              .removeChild(childElement);
          } catch (IllegalArgumentException e) {
            // Unknown namespaces should be removed
            log.warn(
              "Clean-up metadata for pre-text publication. Namespace URI '{}' does not exist and XML node was removed in '{}'",
              namespaceUri,
              norm.getManifestationEli()
            );
            childElement.getParentNode().removeChild(childElement);
          }
        }
      });
  }
}
