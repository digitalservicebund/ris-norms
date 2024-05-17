package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.UpdatePassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

/** Service for updating norms. */
@Service
public class UpdateNormService implements UpdatePassiveModificationsUseCase {
  /**
   * Remove passive modifications form the norm, who originate from the norm with the given source.
   *
   * @param norm the norm from which to remove passive modifications
   * @param sourceNormEli the eli which the removed passive modifications should have as a source
   */
  private void removePassiveModificationsThatStemFromSource(Norm norm, String sourceNormEli) {
    norm.getPassiveModifications().stream()
        .filter(
            passiveModification ->
                passiveModification.getSourceEli().equals(Optional.of(sourceNormEli)))
        .forEach(
            passiveModification -> {
              // delete the temporal group if it is not referenced by anything anymore
              var nodesUsingTemporalData =
                  NodeParser.getNodesFromExpression(
                      String.format(
                          "//*[@period='#%s']",
                          passiveModification.getForcePeriodEid().orElseThrow()),
                      norm.getDocument());
              if (nodesUsingTemporalData.size() == 1) {
                var temporalGroupNode =
                    NodeParser.getNodeFromExpression(
                        String.format(
                            "//act//temporalGroup[@eId='%s']",
                            passiveModification.getForcePeriodEid().orElseThrow()),
                        norm.getDocument());
                temporalGroupNode.getParentNode().removeChild(temporalGroupNode);

                // delete the event ref if it is not referenced by anything anymore
                var eventRefEId =
                    NodeParser.getValueFromExpression("./timeInterval/@start", temporalGroupNode)
                        .map(href -> href.replace("#", ""));
                var nodesUsingEventRef =
                    NodeParser.getNodesFromExpression(
                        String.format("//*[@refersTo='#%s']", eventRefEId.orElseThrow()),
                        norm.getDocument());
                if (nodesUsingEventRef.isEmpty()) {
                  var eventRefNode =
                      NodeParser.getNodeFromExpression(
                          String.format("//act//eventRef[@eId='%s']", eventRefEId.orElseThrow()),
                          norm.getDocument());
                  eventRefNode.getParentNode().removeChild(eventRefNode);
                }
              }

              final var node = passiveModification.getNode();
              node.getParentNode().removeChild(node);
            });
  }

  @Override
  public Norm updatePassiveModifications(Query query) {
    var norm = query.norm();

    // clean up existing passive modifications stemming from the amending norm
    removePassiveModificationsThatStemFromSource(norm, query.amendingNorm().getEli().orElseThrow());

    final var activeModificationsToAdd =
        query.amendingNorm().getActiveModifications().stream()
            .filter(
                activeModification -> activeModification.getDestinationEli().equals(norm.getEli()))
            .toList();

    // create temporal groups
    Map<String, String> amendingNormTemporalGroupEidsToNormTemporalGroupEids = new HashMap<>();
    activeModificationsToAdd.stream()
        .flatMap(activeModification -> activeModification.getForcePeriodEid().stream())
        .distinct()
        .forEach(
            forcePeriodEid -> {
              final var temporalGroup =
                  norm.addTimeBoundary(
                      LocalDate.parse(
                          query
                              .amendingNorm()
                              .getStartDateForTemporalGroup(forcePeriodEid)
                              .orElseThrow()));

              amendingNormTemporalGroupEidsToNormTemporalGroupEids.put(
                  forcePeriodEid, temporalGroup.getEid().orElseThrow());
            });

    // create the passive modifications
    query.amendingNorm().getActiveModifications().stream()
        .filter(activeModification -> activeModification.getDestinationEli().equals(norm.getEli()))
        .forEach(
            activeModification ->
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
                        .map(amendingNormTemporalGroupEidsToNormTemporalGroupEids::get)
                        .orElse(null)));

    return norm;
  }
}
