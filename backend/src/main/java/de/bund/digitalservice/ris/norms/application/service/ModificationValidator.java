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

  /**
   * @param amendingLaw the amending law to be checked
   * @param targetLaw the target law needed to look up the old, to replace text
   */
  public void validate(Norm amendingLaw, Norm targetLaw) {
    throwErrorNoDestinationSet(amendingLaw);
    destinationEliIsConsistent(amendingLaw);
    destinationHrefIsConsistent(amendingLaw);
    oldTextExistsInTargetLaw(amendingLaw, targetLaw);
  }

  /**
   * Throws an error if any of the articles of the passed amendingLaw has an empty affected document
   * Eli. The error message contains a comma separated list of all article eIds, that are affected.
   *
   * @param amendingLaw the amending law to be checked
   */
  public void throwErrorNoDestinationSet(Norm amendingLaw) {
    validateActiveModificationsEli(amendingLaw);
    validateArticlesEli(amendingLaw);
  }

  private void validateActiveModificationsEli(Norm amendingLaw) {
    amendingLaw
        .getActiveModifications()
        .forEach(
            tm ->
                tm.getDestinationHref()
                    .orElseThrow(
                        () ->
                            new XmlContentException(
                                "For amendingLaw with Eli (%s): ActiveModification Destination Href is empty where textualMod eId is %s"
                                    .formatted(amendingLaw.getEli(), getTextualModEId(tm)),
                                null))
                    .getEli()
                    .orElseThrow(
                        () ->
                            new XmlContentException(
                                "For amendingLaw with Eli (%s): ActiveModification Destination Href holds an empty (more general: invalid) Eli where textualMod eId is %s"
                                    .formatted(amendingLaw.getEli(), getTextualModEId(tm)),
                                null)));
  }

  private void validateArticlesEli(Norm amendingLaw) {
    amendingLaw.getArticles().stream()
        .filter(
            article -> {
              String articleRefersTo =
                  getArticleRefersTo(
                      amendingLaw.getEli(), article, getArticleEId(amendingLaw.getEli(), article));
              return Objects.equals(articleRefersTo, "hauptaenderung");
            })
        .forEach(
            article -> {
              String amendingLawEli = amendingLaw.getEli();
              String articleEId = getArticleEId(amendingLawEli, article);
              validateAknModEli(amendingLawEli, article, articleEId);
              validateAffectedDocumentEli(amendingLawEli, article, articleEId);
            });
  }

  private void validateAknModEli(String amendingLawEli, Article article, String articleEId) {
    getHrefEli(
        amendingLawEli,
        getModTargetHref(
            amendingLawEli, getArticleMod(amendingLawEli, article, articleEId), articleEId),
        articleEId);
  }

  private void validateAffectedDocumentEli(
      String amendingLawEli, Article article, String articleEId) {
    getArticleAffectedDocumentEli(amendingLawEli, article, articleEId);
  }

  /**
   * Checks whether a reference to a target law in an amending law is consistent in
   * akn:activeModifications - destination and akn:mod
   *
   * @param amendingLaw the amending law to be checked
   */
  public void destinationEliIsConsistent(Norm amendingLaw) {
    Set<String> affectedDocumentElis =
        amendingLaw.getArticles().stream()
            .map(Article::getAffectedDocumentEli)
            .flatMap(Optional::stream)
            .collect(Collectors.toSet());

    Set<String> activeModificationsDestinationElis =
        amendingLaw.getActiveModifications().stream()
            .map(TextualMod::getDestinationHref)
            .flatMap(Optional::stream)
            .map(Href::getEli)
            .flatMap(Optional::stream)
            .collect(Collectors.toSet());

    Set<String> aknModElis =
        amendingLaw.getArticles().stream()
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
          "For amendingLaw with Eli (%s): Elis are not consistent".formatted(amendingLaw.getEli()),
          null);
  }

  /**
   * Checks whether a reference to a target law in an amending law is consistent in
   * akn:activeModifications - destination and akn:mod
   *
   * @param amendingLaw the amending law to be checked
   */
  public void destinationHrefIsConsistent(Norm amendingLaw) {
    Set<Href> activeModificationsDestinationHrefs =
        amendingLaw.getActiveModifications().stream()
            .map(TextualMod::getDestinationHref)
            .flatMap(Optional::stream)
            .collect(Collectors.toSet());

    Set<Href> aknModHrefs =
        amendingLaw.getArticles().stream()
            .map(Article::getMod)
            .flatMap(Optional::stream)
            .map(Mod::getTargetHref)
            .flatMap(Optional::stream)
            .collect(Collectors.toSet());
    if (!activeModificationsDestinationHrefs.equals(aknModHrefs))
      throw new XmlContentException(
          "For amendingLaw with Eli (%s): Eids are not consistent".formatted(amendingLaw.getEli()),
          null);
  }

  /**
   * Checks if the text to be replaced is present in the target law
   *
   * @param amendingLaw the amending law to be checked
   * @param targetLaw the target law needed to look up the old, to replace text
   */
  public void oldTextExistsInTargetLaw(Norm amendingLaw, Norm targetLaw) {
    amendingLaw.getArticles().stream()
        .filter(
            article -> {
              String articleRefersTo =
                  getArticleRefersTo(
                      amendingLaw.getEli(), article, getArticleEId(amendingLaw.getEli(), article));
              return Objects.equals(articleRefersTo, "hauptaenderung");
            })
        .forEach(
            article -> {
              String amendingLawEli = amendingLaw.getEli();
              String articleEId = getArticleEId(amendingLawEli, article);
              String affectedDocumentEli =
                  getArticleAffectedDocumentEli(amendingLawEli, article, articleEId);
              Mod mod = getArticleMod(amendingLawEli, article, articleEId);
              String amendingLawOldText = getModOldText(amendingLawEli, mod, articleEId);
              Href targetHref = getModTargetHref(amendingLawEli, mod, articleEId);
              String targetHrefEId = getHrefEid(amendingLawEli, targetHref, articleEId);

              validateNumberOfNodesWithEid(
                  amendingLawEli, articleEId, targetLaw, targetHrefEId, affectedDocumentEli);

              Node targetNode =
                  getTargetNode(
                      amendingLawEli, targetLaw, targetHrefEId, affectedDocumentEli, articleEId);

              // normalizeSpace removes double spaces and new lines
              String targetParagraphText = StringUtils.normalizeSpace(targetNode.getTextContent());

              if (mod.usesQuotedText()) {
                CharacterRange characterRange =
                    getHrefCharacterRange(amendingLawEli, targetHref, articleEId);
                int modStart = getCharacterRangeStart(characterRange, articleEId);
                int modEnd = getCharacterRangeEnd(characterRange, articleEId);

                validateStartIsBeforeEnd(
                    amendingLawEli, characterRange, modStart, modEnd, articleEId);
                checkIfReplacementEndIsWithinText(
                    amendingLawEli, targetParagraphText, modEnd, articleEId);

                String targetLawOldText = targetParagraphText.substring(modStart, modEnd);
                validateQuotedTextEquals(
                    amendingLawEli, targetLawOldText, amendingLawOldText, articleEId);
              }
            });
  }

  private void validateNumberOfNodesWithEid(
      String amendingLawEli,
      String articleEId,
      Norm targetLaw,
      String targetHrefEId,
      String affectedDocumentEli) {
    int numberOfNodesWithEid = targetLaw.getNumberOfNodesWithEid(targetHrefEId);
    if (numberOfNodesWithEid > 1) {
      throw new XmlContentException(
          "For amendingLaw with Eli (%s): To many matching eIds (%s) for article %s in target norm %s"
              .formatted(amendingLawEli, targetHrefEId, articleEId, affectedDocumentEli),
          null);
    }
  }

  private void validateStartIsBeforeEnd(
      String amendingLawEli, CharacterRange cr, int start, int end, String articleEId) {
    if (!cr.isEndGreaterStart())
      throw new XmlContentException(
          "For amendingLaw with Eli (%s): The character range in mod href is not valid in article with eId %s. Make sure start is smaller than end %s < %s."
              .formatted(amendingLawEli, articleEId, start, end),
          null);
  }

  private void checkIfReplacementEndIsWithinText(
      String amendingLawEli, String targetParagraphText, int modEnd, String articleEId) {
    // counting is null based e.g. 0 is the position of the first character; spaces are counted
    // see
    // https://gitlab.opencode.de/bmi/e-gesetzgebung/ldml_de/-/blob/1.6/Spezifikation/LegalDocML.de_1.6.pdf?ref_type=tags page 85
    if (targetParagraphText.length() < modEnd) {
      throw new XmlContentException(
          "For amendingLaw with Eli (%s): The character range in mod href is not valid (target paragraph is to short) in article with eId %s"
              .formatted(amendingLawEli, articleEId),
          null);
    }
  }

  private void validateQuotedTextEquals(
      String amendingLawEli,
      String targetLawOldText,
      String amendingLawOldText,
      String articleEId) {
    if (!targetLawOldText.equals(amendingLawOldText))
      throw new XmlContentException(
          "For amendingLaw with Eli (%s): The replacement text '%s' in the target law does not equal the replacement text '%s' in the article with eId %s"
              .formatted(amendingLawEli, targetLawOldText, amendingLawOldText, articleEId),
          null);
  }

  private Node getTargetNode(
      String amendingLawEli,
      Norm targetLaw,
      String targetHrefEId,
      String affectedDocumentEli,
      String articleEId) {
    return targetLaw
        .getByEId(targetHrefEId)
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For amendingLaw with Eli (%s): Couldn't load target eId (%s) element in target law (%s) for article with eId %s"
                        .formatted(amendingLawEli, targetHrefEId, affectedDocumentEli, articleEId),
                    null));
  }

  private String getTextualModEId(TextualMod tm) {
    return tm.getEid()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For amendingLaw with Eli (%s): TextualMod eId id empty.", null));
  }

  private String getArticleRefersTo(String amendingLawEli, Article a, String articleEId) {
    // TODO test for that throw
    return a.getRefersTo()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For amendingLaw with Eli (%s): RefersTo is empty in article with eId %s"
                        .formatted(amendingLawEli, articleEId),
                    null));
  }

  private String getArticleAffectedDocumentEli(
      String amendingLawEli, Article a, String articleEId) {
    return a.getAffectedDocumentEli()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For amendingLaw with Eli (%s): AffectedDocument href is empty in article with eId %s"
                        .formatted(amendingLawEli, articleEId),
                    null));
  }

  private String getArticleEId(String amendingLawEli, Article a) {
    return a.getEid()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For amendingLaw with Eli (%s): Article eId is empty."
                        .formatted(amendingLawEli),
                    null));
  }

  private Mod getArticleMod(String amendingLawEli, Article a, String articleEId) {
    return a.getMod()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For amendingLaw with Eli (%s): There is no mod in article with eId %s"
                        .formatted(amendingLawEli, articleEId),
                    null));
  }

  private String getModOldText(String amendingLawEli, Mod m, String articleEId) {
    return m.getOldText()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For amendingLaw with Eli (%s): quotedText[1] (the old, to be replaced, text) is empty in article with eId %s"
                        .formatted(amendingLawEli, articleEId),
                    null));
  }

  private Href getModTargetHref(String amendingLawEli, Mod m, String articleEId) {
    return m.getTargetHref()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For amendingLaw with Eli (%s): mod href is empty in article with eId %s"
                        .formatted(amendingLawEli, articleEId),
                    null));
  }

  private CharacterRange getHrefCharacterRange(String amendingLawEli, Href h, String articleEId) {
    return h.getCharacterRange()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For amendingLaw with Eli (%s): The character range in mod href is empty in article with eId %s"
                        .formatted(amendingLawEli, articleEId),
                    null));
  }

  private String getHrefEid(String amendingLawEli, Href h, String articleEId) {
    return h.getEId()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For amendingLaw with Eli (%s): The eId in mod href is empty in article with eId %s"
                        .formatted(amendingLawEli, articleEId),
                    null));
  }

  private String getHrefEli(String amendingLawEli, Href h, String articleEId) {
    return h.getEli()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For amendingLaw with Eli (%s): The Eli in aknMod href is empty in article with eId %s"
                        .formatted(amendingLawEli, articleEId),
                    null));
  }

  private int getCharacterRangeStart(CharacterRange cr, String articleEId) {
    return cr.getStart(articleEId);
  }

  private int getCharacterRangeEnd(CharacterRange cr, String articleEId) {
    return cr.getEnd(articleEId);
  }
}
