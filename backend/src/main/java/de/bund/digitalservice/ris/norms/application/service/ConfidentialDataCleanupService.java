package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import org.springframework.stereotype.Service;

/**
 * Service for removing data that should not be available to the public.
 */
@Service
public class ConfidentialDataCleanupService {

  /**
   * Cleans the confidential data of all documents associated with the given {@link Norm}.
   *
   * @param norm The {@link Norm} containing the documents to be cleaned.
   */
  public void clean(Norm norm) {
    norm
      .getDokumente()
      .forEach(dokument ->
        dokument.getMeta().getProprietary().ifPresent(this::removePrivateMetadata)
      );
  }

  private void removePrivateMetadata(final Proprietary proprietary) {
    // Organisationseinheit
    var matches = NodeParser.getNodesFromExpression(
      "//organisationsEinheit",
      proprietary.getElement()
    );

    matches.forEach(match -> match.getParentNode().removeChild(match));
  }
}
