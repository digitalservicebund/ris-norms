package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlContentException;
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
    oldTextExistsInZf0Norm(amendingNorm, zf0Norm);
    validatePassiveModificationsEId(amendingNorm, zf0Norm);
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
        articleEId);
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
   * @param amendingNorm the amending law to be checked
   * @param zf0Norm the target law needed to look up the old, to replace text
   */
  public void oldTextExistsInZf0Norm(Norm amendingNorm, Norm zf0Norm) {
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
              String affectedDocumentEli =
                  getArticleAffectedDocumentEli(amendingNormEli, article, articleEId);
              Mod mod = getArticleMod(amendingNormEli, article, articleEId);
              String amendingNormOldText = getModOldText(amendingNormEli, mod, articleEId);
              Href targetHref = getModTargetHref(amendingNormEli, mod, articleEId);
              String targetHrefEId = getHrefEid(amendingNormEli, targetHref, articleEId);

              validateNumberOfNodesWithEid(
                  amendingNormEli, articleEId, zf0Norm, targetHrefEId, affectedDocumentEli);

              Node targetNode =
                  getTargetNodeFromAmendingNorm(
                      amendingNormEli, zf0Norm, targetHrefEId, affectedDocumentEli, articleEId);

              // normalizeSpace removes double spaces and new lines
              String targetParagraphText = StringUtils.normalizeSpace(targetNode.getTextContent());

              if (mod.usesQuotedText()) {
                CharacterRange characterRange =
                    getHrefCharacterRange(
                        amendingNormEli,
                        targetHref,
                        "The character range in mod href is empty in article with eId %s"
                            .formatted(articleEId));
                validateQuotedText(
                    amendingNormEli,
                    amendingNormOldText,
                    targetParagraphText,
                    characterRange,
                    articleEId);
              }
            });
  }

  /**
   * Checks all passive modifications for ZF0 norm, whether the destination href eId is present.
   *
   * @param amendingNorm the amending norm to be checked
   * @param zf0Norm the target norm to be checked
   */
  public void validatePassiveModificationsEId(Norm amendingNorm, Norm zf0Norm) {
    String zf0NormEli = zf0Norm.getEli();
    zf0Norm
        .getPassiveModifications()
        .forEach(
            tm -> {
              // we are doing the same thing as in oldTextExistsInZf0Norm() but backwards

              // TODO move to getter and test for that throw
              String textualModEId =
                  tm.getEid().orElseThrow(() -> new XmlContentException("TBD", null));

              // Check akn:source
              Href sourceHref = getTextualModSourceHref(zf0NormEli, tm, textualModEId);
              String amendingNormEli =
                  sourceHref.getEli().orElseThrow(() -> new XmlContentException("TBD", null));

              if (!Objects.equals(amendingNorm.getEli(), amendingNormEli))
                throw new XmlContentException("TBD", null);

              String sourceModEId =
                  sourceHref.getEId().orElseThrow(() -> new XmlContentException("TBD", null));
              Mod sourceMod =
                  amendingNorm.getMods().stream()
                      .filter(
                          m ->
                              m.getEid().isPresent()
                                  && Objects.equals(m.getEid().get(), (sourceModEId)))
                      .findFirst()
                      .orElseThrow(() -> new XmlContentException("TBD", null));
              String amendingNormOldText =
                  sourceMod.getOldText().orElseThrow(() -> new XmlContentException("TBD", null));

              // Check akn:destination
              Href destinationHref =
                  tm.getDestinationHref()
                      .orElseThrow(
                          () ->
                              // TODO test for that throw
                              new XmlContentException(
                                  "For norm with Eli (%s): PassiveModification Destination Href is empty where textualMod eId is %s"
                                      .formatted(zf0NormEli, getTextualModEId(zf0NormEli, tm)),
                                  null));
              String destinationHrefEId =
                  destinationHref
                      .getEId()
                      .orElseThrow(
                          () ->
                              // TODO test for that throw
                              new XmlContentException(
                                  "For norm with Eli (%s): PassiveModification Destination Href holds an empty (more general: invalid) eId where textualMod eId is %s"
                                      .formatted(zf0NormEli, getTextualModEId(zf0NormEli, tm)),
                                  null));

              Node targetNode =
                  getTargetNodeFromZf0Norm(
                      zf0Norm, destinationHrefEId, getTextualModEId(zf0NormEli, tm));

              // normalizeSpace removes double spaces and new lines
              String targetParagraphText = StringUtils.normalizeSpace(targetNode.getTextContent());

              // TODO compare oldText with substring of  targetParagraphText
              if (sourceMod.usesQuotedText()) {
                CharacterRange characterRange =
                    getHrefCharacterRange(
                        zf0NormEli,
                        destinationHref,
                        "The character range in passiveModifications textualMod destination href is empty where eId is %s"
                            .formatted(destinationHrefEId));

                // TODO articleEId is not a valid argument anymore
                String articleEId = "TBD";

                validateQuotedText(
                    amendingNormEli,
                    amendingNormOldText,
                    targetParagraphText,
                    characterRange,
                    articleEId);
              }

              // Check akn:force
              // TODO check timeBoundaries
            });
  }

  private void validateQuotedText(
      String amendingNormEli,
      String amendingNormOldText,
      String targetParagraphText,
      CharacterRange characterRange,
      String articleEId) {
    int modStart = getCharacterRangeStart(characterRange, articleEId);
    int modEnd = getCharacterRangeEnd(characterRange, articleEId);

    validateStartIsBeforeEnd(amendingNormEli, characterRange, modStart, modEnd, articleEId);
    checkIfReplacementEndIsWithinText(amendingNormEli, targetParagraphText, modEnd, articleEId);

    String zf0NormOldText = targetParagraphText.substring(modStart, modEnd);
    validateQuotedTextEquals(amendingNormEli, zf0NormOldText, amendingNormOldText, articleEId);
  }

  private void validateNumberOfNodesWithEid(
      String amendingNormEli,
      String articleEId,
      Norm zf0Norm,
      String targetHrefEId,
      String affectedDocumentEli) {
    int numberOfNodesWithEid = zf0Norm.getNumberOfNodesWithEid(targetHrefEId);
    if (numberOfNodesWithEid > 1) {
      throw new XmlContentException(
          "For norm with Eli (%s): To many matching eIds (%s) for article %s in target norm %s"
              .formatted(amendingNormEli, targetHrefEId, articleEId, affectedDocumentEli),
          null);
    }
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

  private Node getTargetNodeFromZf0Norm(Norm zf0Norm, String targetHrefEId, String textualModEId) {
    return zf0Norm
        .getByEId(targetHrefEId)
        .orElseThrow(
            () ->
                // TODO test for that throw
                new XmlContentException(
                    "For norm with Eli (%s): Couldn't load target eId (%s) element for passiveModifications textualMod with eId %s"
                        .formatted(zf0Norm.getEli(), targetHrefEId, textualModEId),
                    null));
  }

  private String getTextualModEId(String eli, TextualMod tm) {
    return tm.getEid()
        .orElseThrow(
            () -> // TODO repair test
            new XmlContentException(
                    "For norm with Eli (%s): TextualMod eId empty.".formatted(eli), null));
  }

  private String getArticleRefersTo(String amendingNormEli, Article a, String articleEId) {
    // TODO test for that throw
    return a.getRefersTo()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): RefersTo is empty in article with eId %s"
                        .formatted(amendingNormEli, articleEId),
                    null));
  }

  private String getArticleAffectedDocumentEli(
      String amendingNormEli, Article a, String articleEId) {
    return a.getAffectedDocumentEli()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): AffectedDocument href is empty in article with eId %s"
                        .formatted(amendingNormEli, articleEId),
                    null));
  }

  private String getArticleEId(String amendingNormEli, Article a) {
    return a.getEid()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): Article eId is empty.".formatted(amendingNormEli),
                    null));
  }

  private Mod getArticleMod(String amendingNormEli, Article a, String articleEId) {
    return a.getMod()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): There is no mod in article with eId %s"
                        .formatted(amendingNormEli, articleEId),
                    null));
  }

  private String getModOldText(String amendingNormEli, Mod m, String articleEId) {
    return m.getOldText()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): quotedText[1] (the old, to be replaced, text) is empty in article with eId %s"
                        .formatted(amendingNormEli, articleEId),
                    null));
  }

  private Href getModTargetHref(String amendingNormEli, Mod m, String articleEId) {
    return m.getTargetHref()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): mod href is empty in article with eId %s"
                        .formatted(amendingNormEli, articleEId),
                    null));
  }

  private Href getTextualModSourceHref(String zf0NormEli, TextualMod tm, String textualModEId) {
    // TODO test for that throw
    return tm.getSourceHref()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): textualMod source href is empty where textualMod eId is %s"
                        .formatted(zf0NormEli, textualModEId),
                    null));
  }

  private CharacterRange getHrefCharacterRange(String eli, Href h, String message) {
    return h.getCharacterRange()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): %s".formatted(eli, message), null));
  }

  private String getHrefEid(String amendingNormEli, Href h, String articleEId) {
    return h.getEId()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): The eId in mod href is empty in article with eId %s"
                        .formatted(amendingNormEli, articleEId),
                    null));
  }

  private void getHrefEli(String amendingNormEli, Href h, String articleEId) {
    if (h.getEli().isEmpty()) {
      throw new XmlContentException(
          "For norm with Eli (%s): The Eli in aknMod href is empty in article with eId %s"
              .formatted(amendingNormEli, articleEId),
          null);
    }
  }

  private int getCharacterRangeStart(CharacterRange cr, String articleEId) {
    return cr.getStart(articleEId);
  }

  private int getCharacterRangeEnd(CharacterRange cr, String articleEId) {
    return cr.getEnd(articleEId);
  }
}
