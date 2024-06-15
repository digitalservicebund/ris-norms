package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.domain.entity.CharacterRange;
import de.bund.digitalservice.ris.norms.domain.entity.Mod;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.TextualMod;
import de.bund.digitalservice.ris.norms.utils.exceptions.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

/** */
@Service
public class ModificationValidator {

  /**
   * Checks if a substitution mod is consistent
   *
   * @param zf0Norm the zf0Norm
   * @param activeMod the active mod of the amending law
   */
  public void validateSubstitutionMod(final Norm zf0Norm, final Mod activeMod) {

    final String modEId = activeMod.getMandatoryEid();

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
                        "Target node with eid %s not present".formatted(targetNodeEid)));

    if (activeMod.usesQuotedText()) {
      validateQuotedText(
          affectedPassiveMod,
          StringUtils.normalizeSpace(activeMod.getMandatoryOldText()),
          StringUtils.normalizeSpace(zf0TargetedNode.getTextContent()));
    }
    // other case <akn:quotedStructure>
  }

  private void validateQuotedText(
      final TextualMod passiveMode, String amendingNormOldText, String targetParagraphOldText) {

    final CharacterRange characterRange =
        passiveMode
            .getDestinationHref()
            .orElseThrow()
            .getCharacterRange()
            .orElseThrow(() -> new ValidationException("Character range not present"));

    if (!characterRange.isValidCharacterRange())
      throw new ValidationException(
          "The character range (%s) is not valid".formatted(characterRange));

    final int modStart = characterRange.getStart();
    final int modEnd = characterRange.getEnd();

    // counting is null based e.g. 0 is the position of the first character; spaces are counted see
    // https://gitlab.opencode.de/bmi/e-gesetzgebung/ldml_de/-/blob/1.6/Spezifikation/LegalDocML.de_1.6.pdf?ref_type=tags page 85
    if (targetParagraphOldText.length() < modEnd)
      throw new ValidationException("The character range not within length of target node content");

    if (!targetParagraphOldText.substring(modStart, modEnd).equals(amendingNormOldText))
      throw new ValidationException(
          "The character range does not resolve to the targeted text to be replaced");
  }
}
