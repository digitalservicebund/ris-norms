package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.UpdatePassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import org.springframework.stereotype.Service;

/** Service for updating norms. */
@Service
public class UpdateNormService implements UpdatePassiveModificationsUseCase {
  @Override
  public Norm updatePassiveModifications(Query query) {
    var norm = query.norm();

    norm.getPassiveModifications().stream()
        .filter(
            passiveModification ->
                passiveModification.getSourceEli().equals(query.amendingNorm().getEli()))
        .forEach(
            passiveModification -> {
              final var node = passiveModification.getNode();
              node.getParentNode().removeChild(node);
              // todo: we also need to clean the temporal data
            });

    query.amendingNorm().getActiveModifications().stream()
        .filter(activeModification -> activeModification.getDestinationEli().equals(norm.getEli()))
        .forEach(
            activeModification -> {
              norm.addPassiveModification(
                  activeModification.getType().orElseThrow(),
                  query.amendingNorm().getEli().orElseThrow()
                      + "/"
                      + activeModification.getSourceEid().orElseThrow()
                      + ".xml",
                  "#"
                      + activeModification.getDestinationEid().orElseThrow()
                      + "/"
                      + activeModification.getDestinationCharacterRange().orElseThrow(),
                  activeModification
                      .getForcePeriodEid()
                      .orElseThrow() // todo this needs to be adjusted to the correct period eid
                  // (and the temporal data needs to be updated)
                  );
            });

    return norm;
  }
}
