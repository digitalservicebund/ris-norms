package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Service responsible for cleaning up all {@link Dokument}s contained in a {@link Norm} since in
 * the prototype not all available data can be published due to legal obligations
 */
@Service
public class PrototypeCleanupService {

  private static final Set<String> ALLOWED_METADATA = Set.of("standangabe", "vollzitat");

  /**
   * Cleans the metadata and lifecycle events of all documents associated with the given {@link
   * Norm}.
   *
   * <p>This method iterates through each {@link Dokument} in the provided {@link Norm} and applies
   * a series of metadata-cleaning operations. Specifically, it:
   *
   * <ul>
   *   <li>Removes metadata from RIS-specific elements.
   *   <li>Deletes regular metadata.
   *   <li>Deletes metadata associated with the German federal government.
   *   <li>Removes specific notes related to amendments.
   *   <li>Modifies lifecycle events so the real date of the event is hidden.
   * </ul>
   *
   * @param norm The {@link Norm} containing the documents to be cleaned.
   */
  public void clean(Norm norm) {
    Set<Dokument> dokumente = norm.getDokumente();
    for (Dokument dokument : dokumente) {
      cleanRisMetadata(dokument);
      cleanRegularMetaData(dokument);
      cleanBundesRegierungMetaData(dokument);
      cleanNotes(dokument);
      cleanLifecycleEvents(dokument);
    }
  }

  private void cleanRisMetadata(Dokument dokument) {
    Optional<Element> dsProprietary = NodeParser.getElementFromExpression(
      "//Q{" + Namespace.METADATEN_RIS.getNamespaceUri() + "}legalDocML.de_metadaten",
      dokument.getDocument()
    );
    if (dsProprietary.isEmpty()) return;

    var nodesToDelete = NodeParser.getNodesFromExpression(
      createXpathAllDsProprietaryExceptAllowedOnes(),
      dsProprietary.get()
    );
    for (Node node : nodesToDelete) {
      node.getParentNode().removeChild(node);
    }
  }

  private void cleanRegularMetaData(Dokument dokument) {
    deleteMetaData(dokument, Namespace.METADATEN.getNamespaceUri());
  }

  private void cleanBundesRegierungMetaData(Dokument dokument) {
    deleteMetaData(dokument, Namespace.METADATEN_BUNDESREGIERUNG.getNamespaceUri());
  }

  private void cleanNotes(Dokument dokument) {
    Element metadataElement = dokument.getMeta().getElement();
    String query = "./notes/note[@refersTo=\"aenderungsfussnote\"]";
    var nodesToDelete = NodeParser.getNodesFromExpression(query, metadataElement);
    for (Node node : nodesToDelete) {
      node.getParentNode().removeChild(node);
    }
  }

  private void cleanLifecycleEvents(Dokument dokument) {
    Element metadataElement = dokument.getMeta().getElement();
    String query = "//eventRef";

    var nodesToChange = NodeParser.getElementsFromExpression(query, metadataElement);
    for (Element element : nodesToChange) {
      element.setAttribute("date", "1001-01-01");
    }
  }

  private void deleteMetaData(Dokument dokument, String namespaceUri) {
    NodeParser
      .getElementFromExpression(
        "//Q{" + namespaceUri + "}legalDocML.de_metadaten",
        dokument.getDocument()
      )
      .ifPresent(element -> element.getParentNode().removeChild(element));
  }

  private static String createXpathAllDsProprietaryExceptAllowedOnes() {
    String shouldStayConcat = ALLOWED_METADATA
      .stream()
      .map(s -> "self::" + s) // Prefix each element with "self::"
      .collect(Collectors.joining(" or "));
    return "./*[not(" + shouldStayConcat + ")]";
  }
}
