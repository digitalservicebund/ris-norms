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
   * @param zf0Norm the target law needed to look up the old, to replace text
   * @param amendingNorm the amending law to be checked
   */
  public void validate(Norm zf0Norm, Norm amendingNorm) {
    throwErrorNoDestinationSet(amendingNorm);
    destinationEliIsConsistent(amendingNorm);
    destinationHrefIsConsistent(amendingNorm);
    characterRangeIsConsistent(amendingNorm, zf0Norm);
    oldTextExistsInZf0Norm(amendingNorm, zf0Norm);
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
   * The character range is noted in three places: (1) in amending law activeModifications
   * destination href, (2) in amending law content mod href and (3) in zf0 passiveModifications
   * destination href. All 3 places need to be equal. This method checks for equality for all of
   * them and throws an exception otherwise.
   *
   * @param amendingNorm the amending norm to be checked
   * @param zf0Norm the target zf0 norm to be checked
   */
  public void characterRangeIsConsistent(Norm amendingNorm, Norm zf0Norm) {
    // TODO implement logic
    // TODO provide test
  }

  /**
   * Checks if the text to be replaced is present in the target law
   *
   * @param amendingNorm the amending law to be checked
   * @param zf0Norm the target law needed to look up the old, to replace text
   */
  public void oldTextExistsInZf0Norm(Norm amendingNorm, Norm zf0Norm) {
    List<Article> articles =
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
            .toList();

    List<TextualMod> passiveMods = zf0Norm.getPassiveModifications().stream().toList();

    // TODO compose message
    // TODO test for that throw
    if (articles.size() != passiveMods.size()) throw new XmlContentException("TBD 1", null);

    String amendingNormEli = amendingNorm.getEli();
    String zf0NormEli = zf0Norm.getEli();
    articles.forEach(
        article -> validateEachArticle(zf0Norm, amendingNormEli, zf0NormEli, article, passiveMods));
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
        getTargetNodeFromAmendingNorm(
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
      String articleEId,
      Href href,
      String message) {

    // TODO test for that throw
    CharacterRange characterRange = getHrefCharacterRange(eli, href, message);

    int modStart = getCharacterRangeStart(characterRange, articleEId);
    int modEnd = getCharacterRangeEnd(characterRange, articleEId);

    validateStartIsBeforeEnd(eli, characterRange, modStart, modEnd, articleEId);
    checkIfReplacementEndIsWithinText(eli, targetParagraphText, modEnd, articleEId);

    String zf0NormOldText = targetParagraphText.substring(modStart, modEnd);
    validateQuotedTextEquals(eli, zf0NormOldText, amendingNormOldText, articleEId);
  }

  private void validateStartIsBeforeEnd(
      String amendingNormEli, CharacterRange cr, int start, int end, String articleEId) {
    if (!cr.isEndGreaterStart())
      throw new XmlContentException(
          "For norm with Eli (%s): The character range in mod href is not valid in article with eId %s. Make sure start is smaller than end %s < %s."
              .formatted(amendingNormEli, articleEId, start, end),
          null);
  }

  private void checkIfReplacementEndIsWithinText(
      String amendingNormEli, String targetParagraphText, int modEnd, String articleEId) {
    // counting is null based e.g. 0 is the position of the first character; spaces are counted
    // see
    // https://gitlab.opencode.de/bmi/e-gesetzgebung/ldml_de/-/blob/1.6/Spezifikation/LegalDocML.de_1.6.pdf?ref_type=tags page 85
    if (targetParagraphText.length() < modEnd) {
      throw new XmlContentException(
          "For norm with Eli (%s): The character range in mod href is not valid (target paragraph is to short) in article with eId %s"
              .formatted(amendingNormEli, articleEId),
          null);
    }
  }

  private void validateQuotedTextEquals(
      String amendingNormEli,
      String zf0NormOldText,
      String amendingNormOldText,
      String articleEId) {
    if (!zf0NormOldText.equals(amendingNormOldText))
      throw new XmlContentException(
          "For norm with Eli (%s): The replacement text '%s' in the target law does not equal the replacement text '%s' in the article with eId %s"
              .formatted(amendingNormEli, zf0NormOldText, amendingNormOldText, articleEId),
          null);
  }

  private Node getTargetNodeFromAmendingNorm(
      String amendingNormEli,
      Norm zf0Norm,
      String targetHrefEId,
      String affectedDocumentEli,
      String articleEId) {
    return zf0Norm
        .getByEId(targetHrefEId)
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): Couldn't load target eId (%s) element in target law (%s) for article with eId %s"
                        .formatted(amendingNormEli, targetHrefEId, affectedDocumentEli, articleEId),
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
    return m.getOldText()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): %s".formatted(eli, message), null));
  }

  private Href getModTargetHref(String eli, Mod m, String articleEId) {
    return m.getTargetHref()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): mod href is empty in article with eId %s"
                        .formatted(eli, articleEId),
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

  private int getCharacterRangeStart(CharacterRange cr, String articleEId) {
    return cr.getStart(articleEId);
  }

  private int getCharacterRangeEnd(CharacterRange cr, String articleEId) {
    return cr.getEnd(articleEId);
  }
}
