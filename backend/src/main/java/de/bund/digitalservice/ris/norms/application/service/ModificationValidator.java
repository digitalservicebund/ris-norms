package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlContentException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

/** */
@Service
@Slf4j
public class ModificationValidator {
  // TODO rename ModificationValidatorService?

  /**
   * @param amendingNormEli the amending norm eli
   * @param zf0Norm the target law needed to look up the old, to replace text
   * @param mod the amending norm mod to be checked
   */
  public void validate(String amendingNormEli, Norm zf0Norm, Mod mod) {
    //    throwErrorNoDestinationSet(amendingNorm);
    //    destinationEliIsConsistent(amendingNorm);
    //    destinationHrefIsConsistent(amendingNorm);
    oldTextExistsInZf0Norm(amendingNormEli, zf0Norm, mod);
  }

  /**
   * Throws an error if any of the articles of the passed amendingNorm has an empty affected
   * document Eli. The error message contains a comma separated list of all article eIds, that are
   * affected.
   *
   * @param amendingNorm the amending law to be checked
   */
  public void throwErrorNoDestinationSet(Norm amendingNorm) {
    validateActiveModificationsEli(amendingNorm);
    validateArticlesEli(amendingNorm);
  }

  private void validateActiveModificationsEli(Norm amendingNorm) {
    amendingNorm
        .getActiveModifications()
        .forEach(
            tm ->
                tm.getDestinationHref()
                    .orElseThrow(
                        () -> {
                          String amendingNormEli = amendingNorm.getEli();
                          return new XmlContentException(
                              "For norm with Eli (%s): ActiveModification Destination Href is empty where textualMod eId is %s"
                                  .formatted(
                                      amendingNormEli, getTextualModEId(amendingNormEli, tm)),
                              null);
                        })
                    .getEli()
                    .orElseThrow(
                        () -> {
                          String amendingNormEli = amendingNorm.getEli();
                          return new XmlContentException(
                              "For norm with Eli (%s): ActiveModification Destination Href holds an empty (more general: invalid) Eli where textualMod eId is %s"
                                  .formatted(
                                      amendingNormEli, getTextualModEId(amendingNormEli, tm)),
                              null);
                        }));
  }

  private void validateArticlesEli(Norm amendingNorm) {
    amendingNorm.getArticles().stream()
        .filter(
            article -> {
              String articleRefersTo =
                  getArticleRefersTo(
                      amendingNorm.getEli(),
                      article,
                      getArticleEId(amendingNorm.getEli(), article));
              return Objects.equals(articleRefersTo, "hauptaenderung");
            })
        .forEach(
            article -> {
              String amendingNormEli = amendingNorm.getEli();
              String articleEId = getArticleEId(amendingNormEli, article);
              validateAknModEli(amendingNormEli, article, articleEId);
              validateAffectedDocumentEli(amendingNormEli, article, articleEId);
            });
  }

  private void validateAknModEli(String amendingNormEli, Article article, String articleEId) {
    getHrefEli(
        amendingNormEli,
        getModTargetHref(
            amendingNormEli, getArticleMod(amendingNormEli, article, articleEId), articleEId),
        "The Eli in aknMod href is empty in article with eId %s".formatted(articleEId));
  }

  private void validateAffectedDocumentEli(
      String amendingNormEli, Article article, String articleEId) {
    getArticleAffectedDocumentEli(amendingNormEli, article, articleEId);
  }

  /**
   * Checks whether a reference to a target law in an amending law is consistent in
   * akn:activeModifications - destination and akn:mod
   *
   * @param amendingNorm the amending law to be checked
   */
  public void destinationEliIsConsistent(Norm amendingNorm) {
    Set<String> affectedDocumentElis =
        amendingNorm.getArticles().stream()
            .map(Article::getAffectedDocumentEli)
            .flatMap(Optional::stream)
            .collect(Collectors.toSet());

    Set<String> activeModificationsDestinationElis =
        amendingNorm.getActiveModifications().stream()
            .map(TextualMod::getDestinationHref)
            .flatMap(Optional::stream)
            .map(Href::getEli)
            .flatMap(Optional::stream)
            .collect(Collectors.toSet());

    Set<String> aknModElis =
        amendingNorm.getArticles().stream()
            .map(Article::getMod)
            .flatMap(Optional::stream)
            .map(Mod::getTargetHref)
            .flatMap(Optional::stream)
            .map(Href::getEli)
            .flatMap(Optional::stream)
            .collect(Collectors.toSet());

    if (!affectedDocumentElis.equals(activeModificationsDestinationElis)
        && !activeModificationsDestinationElis.equals(aknModElis))
      throw new XmlContentException(
          "For norm with Eli (%s): Elis are not consistent".formatted(amendingNorm.getEli()), null);
  }

  /**
   * Checks whether a reference to a target law in an amending law is consistent in
   * akn:activeModifications - destination and akn:mod
   *
   * @param amendingNorm the amending law to be checked
   */
  public void destinationHrefIsConsistent(Norm amendingNorm) {
    Set<Href> activeModificationsDestinationHrefs =
        amendingNorm.getActiveModifications().stream()
            .map(TextualMod::getDestinationHref)
            .flatMap(Optional::stream)
            .collect(Collectors.toSet());

    Set<Href> aknModHrefs =
        amendingNorm.getArticles().stream()
            .map(Article::getMod)
            .flatMap(Optional::stream)
            .map(Mod::getTargetHref)
            .flatMap(Optional::stream)
            .collect(Collectors.toSet());
    if (!activeModificationsDestinationHrefs.equals(aknModHrefs))
      throw new XmlContentException(
          "For norm with Eli (%s): Eids are not consistent".formatted(amendingNorm.getEli()), null);
  }

  /**
   * Checks if the text to be replaced is present in the target law
   *
   * @param amendingNormEli the amending Norm Eli
   * @param zf0Norm the target law needed to look up the old, to replace text
   * @param mod the amending law mod to be checked
   */
  public void oldTextExistsInZf0Norm(String amendingNormEli, Norm zf0Norm, Mod mod) {
    if (!mod.usesQuotedText()) {
      throw new XmlContentException("TODO 2", null);
    }

    // TODO compose message
    // TODO put to getter
    // Test for that throw
    String modEId = mod.getEid().orElseThrow(() -> new XmlContentException("TODO 1", null));

    Href articleModTargetHref = getModTargetHref(amendingNormEli, mod, modEId);

    String articleModTargetHrefEId =
        getHrefEId(
            amendingNormEli,
            articleModTargetHref,
            "The eId in mod href is empty in article with eId %s".formatted(modEId));

    Node zf0TargetNode =
        getTargetNodeFromZF0Norm(
            amendingNormEli, zf0Norm, articleModTargetHrefEId, amendingNormEli, modEId);

    // normalizeSpace removes double spaces and new lines
    String targetParagraphText = StringUtils.normalizeSpace(zf0TargetNode.getTextContent());

    String amendingNormOldText =
        getModOldText(
            amendingNormEli,
            mod,
            "quotedText[1] (the old, to be replaced, text) is empty in article mod with eId %s"
                .formatted(modEId));

    validateQuotedText(
        amendingNormEli,
        amendingNormOldText,
        targetParagraphText,
        modEId,
        articleModTargetHref,
        "The character range in mod href is empty in article with eId %s".formatted(modEId));
  }

  private void validateEachArticle(
      Norm zf0Norm,
      String amendingNormEli,
      String zf0NormEli,
      Article article,
      List<TextualMod> passiveMods) {
    String articleEId = getArticleEId(amendingNormEli, article);
    Mod articleMod = getArticleMod(amendingNormEli, article, articleEId);
    Href articleModTargetHref = getModTargetHref(amendingNormEli, articleMod, articleEId);
    String articleModTargetHrefEId =
        getHrefEId(
            amendingNormEli,
            articleModTargetHref,
            "The eId in mod href is empty in article with eId %s".formatted(articleEId));

    TextualMod passiveMod = getTextualModMatching(passiveMods, articleModTargetHrefEId);

    // TODO test for that throw
    String passiveModEId = getTextualModEId(zf0NormEli, passiveMod);

    // Check akn:source
    Href passiveModSourceHref = getTextualModSourceHref(zf0NormEli, passiveMod, passiveModEId);

    // TODO compose message
    // TODO test for that throw
    String passiveModSourceHrefEli = getHrefEli(zf0NormEli, passiveModSourceHref, "TBD 2");

    // TODO compose message
    // TODO test for that throw
    if (!Objects.equals(amendingNormEli, passiveModSourceHrefEli))
      throw new XmlContentException("TBD 3", null);

    // TODO compose message
    // TODO test for that throw
    String passiveModSourceHrefEId = getHrefEId(zf0NormEli, passiveModSourceHref, "TBD 4");

    // TODO move to getter
    String articleModEId =
        articleMod.getEid().orElseThrow(() -> new XmlContentException("TBD 5", null));

    // TODO compose message
    // TODO test for that throw
    if (!Objects.equals(articleModEId, passiveModSourceHrefEId))
      throw new XmlContentException("TBD 6", null);

    // Check akn:destination
    Href passiveModDestinationHref =
        getTextualModDestinationHref(zf0NormEli, passiveMod, passiveModSourceHrefEId);

    // TODO test for that throw
    String passiveModDestinationHrefEId =
        getHrefEId(
            zf0NormEli,
            passiveModDestinationHref,
            "PassiveModification TextualMod Destination Href holds an empty (more general: invalid) eId where TextualMod eId is %s"
                .formatted(passiveModEId));

    // TODO articleModTargetHrefEId == passiveModDestinationHrefEId?

    // Check akn:force
    // TODO check timeBoundaries

    // TODO fix test
    // TODO test for that throw
    validateNumberOfNodesWithEid(zf0NormEli, zf0Norm, passiveModDestinationHrefEId);

    String affectedDocumentEli =
        getArticleAffectedDocumentEli(amendingNormEli, article, articleEId);
    Node targetNode =
        getTargetNodeFromZF0Norm(
            amendingNormEli, zf0Norm, articleModTargetHrefEId, affectedDocumentEli, articleEId);

    // normalizeSpace removes double spaces and new lines
    String targetParagraphText = StringUtils.normalizeSpace(targetNode.getTextContent());

    if (articleMod.usesQuotedText()) {
      String amendingNormOldText =
          getModOldText(
              amendingNormEli,
              articleMod,
              "quotedText[1] (the old, to be replaced, text) is empty in article with eId %s"
                  .formatted(articleEId));

      validateQuotedText(
          amendingNormEli,
          amendingNormOldText,
          targetParagraphText,
          articleEId,
          articleModTargetHref,
          "The character range in mod href is empty in article with eId %s".formatted(articleEId));
    }
  }

  private void validateNumberOfNodesWithEid(String eli, Norm norm, String eId) {
    if (norm.getNumberOfNodesWithEid(eId) > 1) {
      throw new XmlContentException(
          "For norm with Eli (%s): Too many elements with the same eId %s.".formatted(eli, eId),
          null);
    }
  }

  private void validateQuotedText(
      String eli,
      String amendingNormOldText,
      String targetParagraphText,
      String modEId,
      Href href,
      String message) {

    // TODO test for that throw
    CharacterRange characterRange = getHrefCharacterRange(eli, href, message);

    int modStart = getCharacterRangeStart(characterRange, modEId);
    int modEnd = getCharacterRangeEnd(characterRange, modEId);

    validateStartIsBeforeEnd(eli, characterRange, modStart, modEnd, modEId);
    checkIfReplacementEndIsWithinText(eli, targetParagraphText, modEnd, modEId);

    String zf0NormOldText = targetParagraphText.substring(modStart, modEnd);
    validateQuotedTextEquals(eli, zf0NormOldText, amendingNormOldText, modEId);
  }

  private void validateStartIsBeforeEnd(
      String amendingNormEli, CharacterRange cr, int start, int end, String modEId) {
    if (!cr.isEndGreaterStart())
      throw new XmlContentException(
          "For norm with Eli (%s): The character range in mod href is not valid in mod with eId %s. Make sure start is smaller than end %s < %s."
              .formatted(amendingNormEli, modEId, start, end),
          null);
  }

  private void checkIfReplacementEndIsWithinText(
      String amendingNormEli, String targetParagraphText, int modEnd, String modEId) {
    // counting is null based e.g. 0 is the position of the first character; spaces are counted
    // see
    // https://gitlab.opencode.de/bmi/e-gesetzgebung/ldml_de/-/blob/1.6/Spezifikation/LegalDocML.de_1.6.pdf?ref_type=tags page 85
    if (targetParagraphText.length() < modEnd) {
      throw new XmlContentException(
          "For norm with Eli (%s): The character range in mod href is not valid (target paragraph is to short) in mod with eId %s"
              .formatted(amendingNormEli, modEId),
          null);
    }
  }

  private void validateQuotedTextEquals(
      String amendingNormEli, String zf0NormOldText, String amendingNormOldText, String modEId) {
    if (!zf0NormOldText.equals(amendingNormOldText))
      throw new XmlContentException(
          "For norm with Eli (%s): The replacement text '%s' in the target law does not equal the replacement text '%s' in the mod with eId %s"
              .formatted(amendingNormEli, zf0NormOldText, amendingNormOldText, modEId),
          null);
  }

  private Node getTargetNodeFromZF0Norm(
      String amendingNormEli,
      Norm zf0Norm,
      String targetHrefEId,
      String affectedDocumentEli,
      String modEId) {

    validateNumberOfNodesWithEid(zf0Norm.getEli(), zf0Norm, targetHrefEId);

    return zf0Norm
        .getByEId(targetHrefEId)
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): Couldn't load target eId (%s) element in zf0 (%s) for mod with eId %s"
                        .formatted(amendingNormEli, targetHrefEId, affectedDocumentEli, modEId),
                    null));
  }

  private String getArticleRefersTo(String eli, Article a, String articleEId) {
    // TODO test for that throw
    return a.getRefersTo()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): RefersTo is empty in article with eId %s"
                        .formatted(eli, articleEId),
                    null));
  }

  private String getArticleAffectedDocumentEli(String eli, Article a, String articleEId) {
    return a.getAffectedDocumentEli()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): AffectedDocument href is empty in article with eId %s"
                        .formatted(eli, articleEId),
                    null));
  }

  private String getArticleEId(String eli, Article a) {
    return a.getEid()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): Article eId is empty.".formatted(eli), null));
  }

  private Mod getArticleMod(String eli, Article a, String articleEId) {
    return a.getMod()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): There is no mod in article with eId %s"
                        .formatted(eli, articleEId),
                    null));
  }

  private String getModOldText(String eli, Mod m, String message) {
    // TODO check is test in place?
    // TODO this is not normalized?
    return m.getOldText()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): %s".formatted(eli, message), null));
  }

  private Href getModTargetHref(String eli, Mod m, String modEId) {
    return m.getTargetHref()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): mod href is empty in article mod with eId %s"
                        .formatted(eli, modEId),
                    null));
  }

  private TextualMod getTextualModMatching(
      List<TextualMod> passiveMods, String articleModTargetHrefEId) {
    List<TextualMod> passiveModsMatching =
        passiveMods.stream()
            .filter(
                pm -> {
                  Href destinationHref =
                      pm.getDestinationHref()
                          .orElseThrow(() -> new XmlContentException("TBD 8", null));
                  String destinationHrefEId =
                      destinationHref
                          .getEId()
                          .orElseThrow(() -> new XmlContentException("TBD 9", null));
                  return Objects.equals(destinationHrefEId, articleModTargetHrefEId);
                })
            .toList();

    if (passiveModsMatching.size() > 1) throw new XmlContentException("TBD 10", null);
    if (passiveModsMatching.isEmpty()) throw new XmlContentException("TBD 11", null);
    return passiveModsMatching.getFirst();
  }

  private String getTextualModEId(String eli, TextualMod tm) {
    return tm.getEid()
        .orElseThrow(
            () -> // TODO repair test
            new XmlContentException(
                    "For norm with Eli (%s): TextualMod eId empty.".formatted(eli), null));
  }

  private Href getTextualModSourceHref(String eli, TextualMod tm, String textualModEId) {
    // TODO test for that throw
    return tm.getSourceHref()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): PassiveModification TextualMod Source Href is empty where textualMod eId is %s"
                        .formatted(eli, textualModEId),
                    null));
  }

  private Href getTextualModDestinationHref(String eli, TextualMod tm, String textualModEId) {
    // TODO test for that throw
    return tm.getDestinationHref()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): PassiveModification TextualMod Destination Href is empty where textualMod eId is %s"
                        .formatted(eli, textualModEId),
                    null));
  }

  private CharacterRange getHrefCharacterRange(String eli, Href h, String message) {
    return h.getCharacterRange()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): %s".formatted(eli, message), null));
  }

  private String getHrefEId(String eli, Href h, String message) {
    return h.getEId()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): %s".formatted(eli, message), null));
  }

  private String getHrefEli(String eli, Href h, String message) {
    return h.getEli()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): %s".formatted(eli, message), null));
  }

  private int getCharacterRangeStart(CharacterRange cr, String modEId) {
    return cr.getStart(modEId);
  }

  private int getCharacterRangeEnd(CharacterRange cr, String modEId) {
    return cr.getEnd(modEId);
  }
}
