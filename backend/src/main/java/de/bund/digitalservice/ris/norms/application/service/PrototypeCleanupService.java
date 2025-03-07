package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.Namespace;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Service
class PrototypeCleanupService {

  private static final Set<String> SHOULD_STAY = Set.of("entryIntoForce", "expiry", "standangabe");

  public Norm clean(Norm norm) {
    Set<Dokument> dokumente = norm.getDokumente();
    for (Dokument dokument : dokumente) {
      cleanRisMetadata(dokument);
    }

    return norm;
  }

  private void cleanRisMetadata(Dokument dokument) {
    Optional<Element> dsProprietary = NodeParser.getElementFromExpression(
      "//Q{" + Namespace.METADATEN_RIS.getNamespaceUri() + "}legalDocML.de_metadaten",
      dokument.getDocument()
    );
    if (dsProprietary.isPresent()) {
      String query = createXpathAllDsProprietaryExceptShouldStay();
      var nodesToDelete = NodeParser.getNodesFromExpression(query, dsProprietary.get());
      for (Node node : nodesToDelete) {
        node.getParentNode().removeChild(node);
      }
    }
  }

  private static String createXpathAllDsProprietaryExceptShouldStay() {
    String shouldStayConcat = SHOULD_STAY
      .stream()
      .map(s -> "self::" + s) // Prefix each element with "self::"
      .reduce((a, b) -> a + " or " + b) // Concatenate with " or "
      .orElse("");
    return "./*[not(" + shouldStayConcat + ")]";
  }
}
