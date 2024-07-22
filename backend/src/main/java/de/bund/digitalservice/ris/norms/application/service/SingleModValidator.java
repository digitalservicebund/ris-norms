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
          StringUtils.normalizeSpace(zf0TargetedNode.getTextContent()));
    }
    if (activeMod.usesQuotedStructure()) {
      validateQuotedStructure(zf0NormEli, affectedPassiveMod, zf0Norm);
    }
  }

  private void validateQuotedText(
      final String zf0NormEli,
      final TextualMod passivemod,
      String amendingNormOldText,
      String targetParagraphOldText)
      throws ValidationException {

    final Href destinationHref = passivemod.getDestinationHref().orElseThrow();
    final String passiveModEid = passivemod.getEid().orElseThrow();
    final CharacterRange characterRange =
        destinationHref
            .getCharacterRange()
            .orElseThrow(
                () ->
                    new ValidationException(
                        "Destination href with value %s of passive mod with eId %s within ZF0 norm with eli %s not present."
                            .formatted(destinationHref, passiveModEid, zf0NormEli)));

    if (!characterRange.isValidCharacterRange())
      throw new ValidationException(
          "The character range %s of passive mod with eId %s within ZF0 norm with eli %s has invalid format."
              .formatted(characterRange, passiveModEid, zf0NormEli));

    final int modStart = characterRange.getStart();
    final int modEnd = characterRange.getEnd();

    // counting is null based e.g. 0 is the position of the first character; spaces are counted see
    // https://gitlab.opencode.de/bmi/e-gesetzgebung/ldml_de/-/blob/1.6/Spezifikation/LegalDocML.de_1.6.pdf?ref_type=tags page 85
    if (targetParagraphOldText.length() < modEnd)
      throw new ValidationException(
          "The character range %s of passive mod with eId %s within ZF0 norm with eli %s is not within length of target node content."
              .formatted(characterRange, passiveModEid, zf0NormEli));

    if (!targetParagraphOldText.substring(modStart, modEnd).equals(amendingNormOldText))
      throw new ValidationException(
          "The character range %s of passive mod with eId %s within ZF0 norm with eli %s does not resolve to the targeted text to be replaced."
              .formatted(characterRange, passiveModEid, zf0NormEli));
  }

  private void validateQuotedStructure(
      final String zf0NormEli, final TextualMod affectedPassiveMod, final Norm zf0Norm)
      throws ValidationException {

    // TODO check if destinationUpTo resolves to existing node

    // TODO check if href node and upTo node are siblings from same parent node
  }
}
