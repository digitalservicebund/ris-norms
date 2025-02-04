package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.ValidationException;
import de.bund.digitalservice.ris.norms.application.port.input.ApplyPassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.utils.EidConsistencyGuardian;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Namespace for business Logics related to "time machine" functionality, i.e. to applying LDML.de
 * "modifications" to LDML.de files. For details on LDML.de modifications, cf. <a href=
 * "https://gitlab.opencode.de/bmi/e-gesetzgebung/ldml_de/-/tree/main/Spezifikation?ref_type=heads">LDML-details</a>
 */
@Service
@Slf4j
public class TimeMachineService implements ApplyPassiveModificationsUseCase {

  private final LoadNormPort loadNormPort;

  public TimeMachineService(final LoadNormPort loadNormPort) {
    this.loadNormPort = loadNormPort;
  }

  /**
   * Applies the passive modifications of the regelungstext. Only applies "aenderungsbefehl-ersetzen".
   *
   * @param query An ApplyPassiveModificationsUsecase.Query containing the norm and a date
   * @return the regelungstext with the applied passive modifications that are in effect before the date
   */
  public Regelungstext applyPassiveModifications(ApplyPassiveModificationsUseCase.Query query) {
    var regelungstext = new Regelungstext(query.regelungstext()); // create a copy of the norm to work on
    var date = query.date();
    var customRegelungstexts = query
      .customRegelungstexte()
      .stream()
      .collect(
        Collectors.toMap(
          Regelungstext::getExpressionEli,
          customRegelungstext -> customRegelungstext
        )
      );

    var actualDate = date.equals(Instant.MAX) ? Instant.MAX : date.plus(Duration.ofDays(1));

    try {
      regelungstext.getMeta();
    } catch (final MandatoryNodeNotFoundException e) {
      return regelungstext;
    }

    regelungstext
      .getMeta()
      .getAnalysis()
      .map(analysis -> analysis.getPassiveModifications().stream())
      .orElse(Stream.empty())
      .filter((TextualMod passiveModification) -> {
        final var startDate = passiveModification
          .getForcePeriodEid()
          .flatMap(regelungstext::getStartDateForTemporalGroup)
          .map(dateString -> Instant.parse(dateString + "T00:00:00.000Z"));

        // when no start date exists we do not want to apply the mod
        return startDate.isPresent() && startDate.get().isBefore(actualDate);
      })
      .sorted(
        Comparator.comparing((TextualMod passiveModification) ->
          // We already filtered out empty Optionals, so safe retrieving directly
          passiveModification
            .getForcePeriodEid()
            .flatMap(regelungstext::getStartDateForTemporalGroup)
            .map(dateString -> Instant.parse(dateString + "T00:00:00.000Z"))
            .orElse(Instant.EPOCH) // This is just a fallback
        )
      )
      .flatMap((TextualMod passiveModification) -> {
        var sourceEli = passiveModification
          .getSourceHref()
          .flatMap(Href::getExpressionEli)
          .orElseThrow(() ->
            new ValidationException(
              ValidationException.ErrorType.SOURCE_HREF_IN_META_MOD_MISSING,
              Pair.of(ValidationException.FieldName.EID, passiveModification.getEid())
            )
          );

        Regelungstext amendingLaw;
        if (customRegelungstexts.containsKey(sourceEli)) {
          amendingLaw = customRegelungstexts.get(sourceEli);
        } else {
          amendingLaw =
          loadNormPort
            .loadNorm(new LoadNormPort.Command(sourceEli))
            .orElseThrow(() -> new NormNotFoundException(sourceEli.toString()))
            .getRegelungstext1();
        }

        var sourceEid = passiveModification.getSourceHref().flatMap(Href::getEId);
        return amendingLaw
          .getMods()
          .stream()
          .filter(mod -> mod.getEid().equals(sourceEid.get()))
          .map(mod -> new ModData(passiveModification, mod));
      })
      .forEach(modData -> applyMod(modData, regelungstext));

    EidConsistencyGuardian.correctEids(regelungstext.getDocument());

    return regelungstext;
  }

  record ModData(TextualMod passiveModification, Mod mod) {}

  private void applyMod(final ModData modData, final Regelungstext targetZf0Norm) {
    if (
      modData.passiveModification().getDestinationHref().isEmpty() ||
      modData.passiveModification().getDestinationHref().get().getEId().isEmpty()
    ) {
      return;
    }

    final var targetEid = modData.passiveModification().getDestinationHref().get().getEId().get();
    final var targetNode = NodeParser.getElementFromExpression(
      String.format("//*[@eId='%s']", targetEid),
      targetZf0Norm.getDocument()
    );

    if (targetNode.isEmpty()) {
      return;
    }

    if (modData.mod().getSecondQuotedText().isPresent()) {
      try {
        applyQuotedText(modData, targetNode.get());
      } catch (IllegalArgumentException | IndexOutOfBoundsException exception) {
        log.info(
          "Could not apply quoted text mod (%s)".formatted(modData.mod().getEid()),
          exception
        );
      }
    } else if (modData.mod().getQuotedStructure().isPresent()) {
      applyQuotedStructure(modData, targetNode.get(), targetZf0Norm);
    }

    if (targetZf0Norm.getMeta().getAnalysis().isPresent()) {
      targetZf0Norm
        .getMeta()
        .getAnalysis()
        .get()
        .deletePassiveModification(modData.passiveModification());
    }
  }

  private void applyQuotedText(final ModData modData, Node targetNode)
    throws IllegalArgumentException, IndexOutOfBoundsException {
    final Mod mod = modData.mod();
    final Node secondQuotedTextNode = mod
      .getSecondQuotedText()
      .orElseThrow(() -> new IllegalArgumentException("Second quoted text (new text) is empty."));
    final var targetHref = modData
      .passiveModification()
      .getDestinationHref()
      .orElseThrow(() -> new IllegalArgumentException("Target href is empty."));
    final var characterRange = targetHref
      .getCharacterRange()
      .orElseThrow(() ->
        new IllegalArgumentException(
          "Destination has empty character range (%s)".formatted(targetHref)
        )
      );
    final var oldText = mod
      .getOldText()
      .orElseThrow(() -> new IllegalArgumentException("Old text is empty."));

    if (!Objects.equals(characterRange.findTextInNode(targetNode), oldText)) {
      throw new IllegalArgumentException(
        "Old text (%s) is not the same as the text of the character range (%s). Text of the node: %s".formatted(
            oldText,
            characterRange.findTextInNode(targetNode),
            targetNode.getTextContent()
          )
      );
    }

    final var nodesToBeReplaced = characterRange.getNodesInRange(targetNode);

    final var newChildFragment = targetNode.getOwnerDocument().createDocumentFragment();

    // Import content of quotedText from amending law (which include akn:refs) using importNode
    // because of different documents
    NodeParser
      .nodeListToList(secondQuotedTextNode.getChildNodes())
      .forEach(child -> {
        final Node importedChild = targetNode.getOwnerDocument().importNode(child, true);
        newChildFragment.appendChild(importedChild);
      });

    replaceNodes(nodesToBeReplaced, newChildFragment);
  }

  private void applyQuotedStructure(
    final ModData modData,
    final Node targetNode,
    final Regelungstext targetZf0Norm
  ) {
    final Mod mod = modData.mod();
    if (mod.getQuotedStructure().isEmpty()) return;

    Optional<Element> upToTargetNode = Optional.empty();
    if (modData.passiveModification().getDestinationUpTo().isPresent()) {
      if (modData.passiveModification().getDestinationUpTo().get().getEId().isEmpty()) {
        return;
      } else {
        final var upToTargetNodeEid = modData
          .passiveModification()
          .getDestinationUpTo()
          .get()
          .getEId()
          .get();
        upToTargetNode =
        NodeParser.getElementFromExpression(
          String.format("//*[@eId='%s']", upToTargetNodeEid),
          targetZf0Norm.getDocument()
        );
        if (upToTargetNode.isEmpty()) {
          return;
        }
      }
    }

    final List<Node> newQuotedStructureContent = NodeParser.nodeListToList(
      mod.getQuotedStructure().get().getChildNodes()
    );

    final Node newChildFragment = targetNode.getOwnerDocument().createDocumentFragment();
    newQuotedStructureContent.forEach(node -> {
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
        break;
      }
      currentNode = currentNode.getNextSibling();
    }

    replaceNodes(nodesToReplace, newChildFragment);
  }

  private void replaceNodes(final List<Node> targetNodes, final Node newNode) {
    if (targetNodes.isEmpty()) {
      return;
    }

    final var parentNode = targetNodes.getFirst().getParentNode();

    if (targetNodes.stream().anyMatch(node -> node.getParentNode() != parentNode)) {
      throw new IllegalArgumentException("Not all target nodes are siblings.");
    }

    final var firstNode = targetNodes.getFirst();
    parentNode.insertBefore(newNode, firstNode);
    targetNodes.forEach(parentNode::removeChild);
  }
}
