package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.ValidationException;
import de.bund.digitalservice.ris.norms.domain.entity.CharacterRange;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Mod;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.TextualMod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

/**
 * A custom validator for validating single mods that are being updated on the amending law. This
 * validator uses the ZF0 version of the target law targeted by the mod
 */
@Service
public class SingleModValidator {

  /**
   * Validates the given ZF0 norm using the one mod of the amending law being modified.
   *
   * @param zf0Norm - the ZF0 {@link Norm}
   * @param activeMod - the {@link Mod} that is being modified.
   * @throws ValidationException if a validation step fails
   */
  public void validate(final Norm zf0Norm, final Mod activeMod) throws ValidationException {

    final String modEId = activeMod.getMandatoryEid();
    final String zf0NormEli = zf0Norm.getEli();

    final TextualMod affectedPassiveMod =
        zf0Norm.getMeta().getOrCreateAnalysis().getPassiveModifications().stream()
            .filter(f -> f.getSourceHref().orElseThrow().getEId().orElseThrow().equals(modEId))
            .findFirst()
            .orElseThrow();

    final String targetNodeEid =
        affectedPassiveMod.getDestinationHref().orElseThrow().getEId().orElseThrow();
    final Node zf0TargetedNode =
        zf0Norm
            .getNodeByEId(targetNodeEid)
            .orElseThrow(
                () ->
                    new ValidationException(
                        "Target node with eid %s not present in ZF0 norm with eli %s."
                            .formatted(targetNodeEid, zf0NormEli)));
    if (activeMod.usesQuotedText()) {
      validateQuotedText(
          zf0NormEli,
          affectedPassiveMod,
          StringUtils.normalizeSpace(activeMod.getMandatoryOldText()),
          zf0TargetedNode);
    }
    if (activeMod.usesQuotedStructure()) {
      validateQuotedStructure(affectedPassiveMod, zf0Norm, targetNodeEid, zf0TargetedNode);
    }
  }

  private void validateQuotedText(
      final String zf0NormEli,
      final TextualMod passivemod,
      String amendingNormOldText,
      Node targetNode)
      throws ValidationException {

    final Href destinationHref = passivemod.getDestinationHref().orElseThrow();
    final String passiveModEid = passivemod.getEid().orElseThrow();
    final CharacterRange characterRange =
        destinationHref
            .getCharacterRange()
            .orElseThrow(
                () ->
                    new ValidationException(
                        "In the destination href with value %s of passive mod with eId %s within ZF0 norm with eli %s, the character range not present."
                            .formatted(destinationHref, passiveModEid, zf0NormEli)));

    if (!characterRange.isValidCharacterRange())
      throw new ValidationException(
          "The character range %s of passive mod with eId %s within ZF0 norm with eli %s has invalid format."
              .formatted(characterRange, passiveModEid, zf0NormEli));

    try {
      if (!characterRange.findTextInNode(targetNode).equals(amendingNormOldText))
        throw new ValidationException(
            "The character range %s of passive mod with eId %s within ZF0 norm with eli %s does not resolve to the targeted text to be replaced."
                .formatted(characterRange, passiveModEid, zf0NormEli));
    } catch (IndexOutOfBoundsException exception) {
      throw new ValidationException(
          "The character range %s of passive mod with eId %s within ZF0 norm with eli %s is not within the target node."
              .formatted(characterRange, passiveModEid, zf0NormEli));
    }
  }

  private void validateQuotedStructure(
      final TextualMod affectedPassiveMod,
      final Norm zf0Norm,
      final String targetNodeEid,
      final Node targetNode)
      throws ValidationException {

    affectedPassiveMod
        .getDestinationUpTo()
        .ifPresent(
            upToHref -> {
              final String targetUpToNodeEid = upToHref.getEId().orElseThrow();

              final Node zf0TargetedUpToNode =
                  zf0Norm
                      .getNodeByEId(targetUpToNodeEid)
                      .orElseThrow(
                          () ->
                              new ValidationException(
                                  "Target upTo node with eid %s not present in ZF0 norm with eli %s."
                                      .formatted(targetUpToNodeEid, zf0Norm.getEli())));

              if (targetNode.getParentNode() != zf0TargetedUpToNode.getParentNode()) {
                throw new ValidationException(
                    "Target node with eid %s and target upTo node with eid %s are not siblings in ZF0 norm with eli %s."
                        .formatted(targetNodeEid, targetUpToNodeEid, zf0Norm.getEli()));
              }

              if ((targetNode.compareDocumentPosition(zf0TargetedUpToNode)
                      & Node.DOCUMENT_POSITION_FOLLOWING)
                  == 0) {
                throw new ValidationException(
                    "Target node with eid %s does not appear before target upTo node with eid %s in ZF0 norm with eli %s."
                        .formatted(targetNodeEid, targetUpToNodeEid, zf0Norm.getEli()));
              }
            });
  }
}
