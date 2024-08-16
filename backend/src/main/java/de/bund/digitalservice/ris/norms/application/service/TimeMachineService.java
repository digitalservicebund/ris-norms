package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.ValidationException;
import de.bund.digitalservice.ris.norms.application.port.input.ApplyPassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.utils.EidConsistencyGuardian;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
import io.micrometer.common.util.StringUtils;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * Namespace for business Logics related to "time machine" functionality, i.e. to applying LDML.de
 * "modifications" to LDML.de files. For details on LDML.de modifications, cf. <a href=
 * "https://gitlab.opencode.de/bmi/e-gesetzgebung/ldml_de/-/tree/main/Spezifikation?ref_type=heads">LDML-details</a>
 */
@Service
@Slf4j
public class TimeMachineService implements ApplyPassiveModificationsUseCase {

  private final NormService normService;

  public TimeMachineService(NormService normService) {
    this.normService = normService;
  }

  /**
   * Applies the passive modifications of the norm. Only applies "aenderungsbefehl-ersetzen".
   *
   * @param query An ApplyPassiveModificationsUsecase.Query containing the norm and a date
   * @return the Norm with the applied passive modifications that are in effect before the date
   */
  public Norm applyPassiveModifications(ApplyPassiveModificationsUseCase.Query query) {

    var norm = query.norm();
    var date = query.date();
    var customNorms =
        query.customNorms().stream()
            .collect(Collectors.toMap(Norm::getEli, customNorm -> customNorm));

    var actualDate = date.equals(Instant.MAX) ? Instant.MAX : date.plus(Duration.ofDays(1));

    try {
      norm.getMeta();
    } catch (final MandatoryNodeNotFoundException e) {
      return norm;
    }

    norm.getMeta()
        .getAnalysis()
        .map(analysis -> analysis.getPassiveModifications().stream())
        .orElse(Stream.empty())
        .filter(
            (TextualMod passiveModification) -> {
              final var startDate =
                  passiveModification
                      .getForcePeriodEid()
                      .flatMap(norm::getStartDateForTemporalGroup)
                      .map(dateString -> Instant.parse(dateString + "T00:00:00.000Z"));

              // when no start date exists we do not want to apply the mod
              return startDate.isPresent() && startDate.get().isBefore(actualDate);
            })
        .sorted(
            Comparator.comparing(
                (TextualMod passiveModification) ->
                    passiveModification
                        .getForcePeriodEid()
                        .flatMap(norm::getStartDateForTemporalGroup)
                        .orElseThrow(
                            () ->
                                new ValidationException(
                                    "Did not find a start date for textual mod with eId %s"
                                        .formatted(passiveModification.getEid())))))
        .flatMap(
            (TextualMod passiveModification) -> {
              var sourceEli =
                  passiveModification
                      .getSourceHref()
                      .flatMap(Href::getEli)
                      .orElseThrow(
                          () ->
                              new ValidationException(
                                  "Did not find source href for textual mod with eId %s"
                                      .formatted(passiveModification.getEid())));

              Norm amendingLaw;
              if (customNorms.containsKey(sourceEli)) {
                amendingLaw = customNorms.get(sourceEli);
              } else {
                amendingLaw =
                    normService
                        .loadNorm(new LoadNormUseCase.Query(sourceEli))
                        .orElseThrow(() -> new NormNotFoundException(sourceEli));
              }

              var sourceEid = passiveModification.getSourceHref().flatMap(Href::getEId);
              return amendingLaw.getMods().stream()
                  .filter(mod -> mod.getEid().equals(sourceEid))
                  .map(
                      mod ->
                          new ModData(
                              passiveModification.getDestinationHref(),
                              passiveModification.getDestinationUpTo(),
                              mod));
            })
        .forEach(
            modData -> {
              if (modData.targetHref().isEmpty() || modData.targetHref().get().getEId().isEmpty()) {
                return;
              }
              final var targetEid = modData.targetHref().get().getEId().get();
              final var targetNode =
                  NodeParser.getNodeFromExpression(
                      String.format("//*[@eId='%s']", targetEid), norm.getDocument());

              if (targetNode.isEmpty()) {
                return;
              }
              if (modData.mod().getSecondQuotedText().isPresent())
                applyQuotedText(modData, targetNode.get());
              if (modData.mod().getQuotedStructure().isPresent())
                applyQuotedStructure(modData, targetNode.get(), norm);
            });

    return norm;
  }

  record ModData(Optional<Href> targetHref, Optional<Href> targetUpToRef, Mod mod) {}

  private void applyQuotedText(final ModData modData, Node targetNode) {
    final Mod mod = modData.mod();
    if (mod.getSecondQuotedText().isEmpty()
        || mod.getOldText().isEmpty()
        || mod.getNewText().isEmpty()) return;
    String oldText = mod.getOldText().get();
    String newText = mod.getNewText().get();

    if (mod.containsRef()) {
      final Node secondQuotedTextNode = mod.getSecondQuotedText().get();

      // Get the target node's current text content and remove unnecessary whitespaces, for the
      // character counting from mod to work
      final String targetText = targetNode.getTextContent().trim().replaceAll("\\s+", " ");
      if (targetText.isEmpty()) return;

      // Get the start and end index of the old text, within the cleaned old text of the target node
      final int startIndex = targetText.indexOf(oldText);
      final int endIndex = startIndex + oldText.length();

      // Split the text into three parts: before, replacement, after
      final String beforeText = targetText.substring(0, startIndex);
      final String afterText = targetText.substring(endIndex + 1);

      // Clear the target node's content
      while (targetNode.hasChildNodes()) {
        targetNode.removeChild(targetNode.getFirstChild());
      }

      // Import beforeText
      if (StringUtils.isNotEmpty(beforeText)) {
        final Text beforeTextNode = targetNode.getOwnerDocument().createTextNode(beforeText);
        targetNode.appendChild(beforeTextNode);
      }

      // Import content of quotedText from amending law (which include akn:refs) using importNode
      // because of different documents
      NodeParser.nodeListToList(secondQuotedTextNode.getChildNodes())
          .forEach(
              child -> {
                final Node importedChild = targetNode.getOwnerDocument().importNode(child, true);
                targetNode.appendChild(importedChild);
              });

      // Import afterText
      if (StringUtils.isNotEmpty(afterText)) {
        final Text afterTextNode = targetNode.getOwnerDocument().createTextNode(afterText);
        targetNode.appendChild(afterTextNode);
      }

      // Correct eids of akn:ref
      final String quotedTextEid = EId.fromMandatoryNode(mod.getSecondQuotedText().get()).value();
      final String targetParentNodeEid = EId.fromMandatoryNode(targetNode).value();
      EidConsistencyGuardian.correctRootParentEid(targetNode, quotedTextEid, targetParentNodeEid);
    } else {
      final String modifiedTextContent = targetNode.getTextContent().replaceFirst(oldText, newText);
      targetNode.setTextContent(modifiedTextContent);
    }
  }

  private void applyQuotedStructure(
      final ModData modData, final Node targetNode, final Norm targetZf0Norm) {
    final Mod mod = modData.mod();
    if (mod.getQuotedStructure().isEmpty()) return;

    Optional<Node> upToTargetNode = Optional.empty();
    if (modData.targetUpToRef().isPresent()) {
      if (modData.targetUpToRef().get().getEId().isEmpty()) {
        return;
      } else {
        final var upToTargetNodeEid = modData.targetUpToRef().get().getEId().get();
        upToTargetNode =
            NodeParser.getNodeFromExpression(
                String.format("//*[@eId='%s']", upToTargetNodeEid), targetZf0Norm.getDocument());
        if (upToTargetNode.isEmpty()) {
          return;
        }
      }
    }

    final List<Node> newQuotedStructureContent =
        NodeParser.nodeListToList(mod.getQuotedStructure().get().getChildNodes());

    final Node newChildFragment = targetNode.getOwnerDocument().createDocumentFragment();
    newQuotedStructureContent.forEach(
        node -> {
          Node importedChild = targetNode.getOwnerDocument().importNode(node, true);
          newChildFragment.appendChild(importedChild);
        });

    // Collect nodes to be replaced
    final List<Node> nodesToReplace = new ArrayList<>();
    nodesToReplace.add(targetNode);
    Node currentNode = targetNode.getNextSibling();
    while (upToTargetNode.isPresent() && currentNode != null) {
      nodesToReplace.add(currentNode);
      if (currentNode == upToTargetNode.get()) {
        currentNode = currentNode.getNextSibling();
        break;
      }
      currentNode = currentNode.getNextSibling();
    }
    // Delete nodes that should be replaced
    final Node parentNode = targetNode.getParentNode();
    nodesToReplace.forEach(parentNode::removeChild);
    // Insert fragment with new nodes
    parentNode.insertBefore(newChildFragment, currentNode);

    final String quotedStructureEid = EId.fromMandatoryNode(mod.getQuotedStructure().get()).value();
    final String targetParentNodeEid = EId.fromMandatoryNode(parentNode).value();
    EidConsistencyGuardian.correctRootParentEid(
        parentNode, quotedStructureEid, targetParentNodeEid);
  }
}
