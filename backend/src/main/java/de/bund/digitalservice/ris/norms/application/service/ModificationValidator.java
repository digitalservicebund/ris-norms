package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlContentException;
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

  private final LoadNormPort loadNormPort;

  public ModificationValidator(LoadNormPort loadNormPort) {

    this.loadNormPort = loadNormPort;
  }

  /**
   * @param amendingLaw the amending law to be checked
   */
  public void validate(Norm amendingLaw) {
    throwErrorNoDestinationSet(amendingLaw);
    destinationEliIsConsistent(amendingLaw);
    destinationHrefIsConsistent(amendingLaw);
    oldTextExistsInTargetLaw(amendingLaw);
    destNodeHasTextOnlyContent(amendingLaw);
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
                                "ActiveModification Destination Href is empty where textualMod eId is %s"
                                    .formatted(getTextualModEId(tm)),
                                null))
                    .getEli()
                    .orElseThrow(
                        () ->
                            new XmlContentException(
                                "ActiveModification Destination Href holds an empty (more general: invalid) Eli where textualMod eId is %s"
                                    .formatted(getTextualModEId(tm)),
                                null)));
  }

  private void validateArticlesEli(Norm amendingLaw) {
    amendingLaw
        .getArticles()
        .forEach(
            article -> {
              String articleEId = getArticleEId(article);
              validateAknModEli(article, articleEId);
              validateAffectedDocumentEli(article, articleEId);
            });
  }

  private void validateAknModEli(Article article, String articleEId) {
    getHrefEli(getModTargetHref(getArticleMod(article, articleEId), articleEId), articleEId);
  }

  private void validateAffectedDocumentEli(Article article, String articleEId) {
    getArticleAffectedDocumentEli(article, articleEId);
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
      throw new XmlContentException("Elis are not consistent", null);
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
      throw new XmlContentException("Eids are not consistent", null);
  }

  /**
   * Checks if the text to be replaced is present in the target law
   *
   * @param amendingLaw the amending law to be checked
   */
  public void oldTextExistsInTargetLaw(Norm amendingLaw) {
    amendingLaw
        .getArticles()
        .forEach(
            article -> {
              String articleEId = getArticleEId(article);
              String affectedDocumentEli = getArticleAffectedDocumentEli(article, articleEId);
              Mod mod = getArticleMod(article, articleEId);
              String oldText = getModOldText(mod, articleEId);
              Href targetHref = getModTargetHref(mod, articleEId);
              String targetHrefEId = getHrefEid(targetHref, articleEId);
              CharacterRange characterRange = getHrefCharacterRange(targetHref, articleEId);
              Norm targetLaw = getTargetLaw(affectedDocumentEli, articleEId);

              validateNumberOfNodesWithEid(
                  articleEId, targetLaw, targetHrefEId, affectedDocumentEli);

              Node targetNode =
                  getTargetNode(targetLaw, targetHrefEId, affectedDocumentEli, articleEId);

              // normalizeSpace removes double spaces and new lines
              String paragraphText = StringUtils.normalizeSpace(targetNode.getTextContent());

              int start = getCharacterRangeStart(characterRange, articleEId);
              int end = getCharacterRangeEnd(characterRange, articleEId);

              validateCharacterRange(characterRange, start, end, articleEId);
              validateParagraphTextLength(paragraphText, end, articleEId);

              String textToBeReplaced = paragraphText.substring(start, end);
              validateParagraphEquals(textToBeReplaced, oldText, articleEId);
            });
  }

  private void validateNumberOfNodesWithEid(
      String articleEId, Norm targetLaw, String targetHrefEId, String affectedDocumentEli) {
    int numberOfNodesWithEid = targetLaw.getNumberOfNodesWithEid(targetHrefEId);
    if (numberOfNodesWithEid > 1) {
      throw new XmlContentException(
          "To many matching eIds (%s) for article %s in target norm %s"
              .formatted(targetHrefEId, articleEId, affectedDocumentEli),
          null);
    }
  }

  private void validateCharacterRange(CharacterRange cr, int start, int end, String articleEId) {
    if (!cr.isEndGreaterStart())
      throw new XmlContentException(
          "The character range in mod href is not valid in article with eId %s. Make sure start is smaller than end %s < %s."
              .formatted(articleEId, start, end),
          null);
  }

  private void validateParagraphTextLength(String paragraphText, int end, String articleEId) {
    // TODO test for that throw
    // TODO add test when text to be replaced is at the end
    // TODO this is most probably not correct -> <=
    if (paragraphText.length() < end)
      throw new XmlContentException(
          "The character range in mod href is not valid (target paragraph is to short) in article with eId %s"
              .formatted(articleEId),
          null);
  }

  private void validateParagraphEquals(String textToBeReplaced, String oldText, String articleEId) {
    // TODO test for that throw
    if (!textToBeReplaced.equals(oldText))
      throw new XmlContentException(
          "The to be replaced text does not equal the old text in article with eId %s"
              .formatted(articleEId),
          null);
  }

  /**
   * @param amendingLaw the amending law to be checked
   */
  public void destNodeHasTextOnlyContent(Norm amendingLaw) {
    // TODO no "<>" exists
  }

  private Node getTargetNode(
      Norm targetLaw, String targetHrefEId, String affectedDocumentEli, String articleEId) {
    return targetLaw
        .getByEId(targetHrefEId)
        .orElseThrow(
            () ->
                new XmlContentException(
                    "Couldn't load target eId (%s) element in target law (%s) for article with eId %s"
                        .formatted(targetHrefEId, affectedDocumentEli, articleEId),
                    null));
  }

  private Norm getTargetLaw(String affectedDocumentEli, String articleEId) {
    return loadNormPort
        .loadNorm(new LoadNormPort.Command(affectedDocumentEli))
        .orElseThrow(
            () ->
                new XmlContentException(
                    "Couldn't load target law by Eli: The affectedDocument href may hold an invalid value in article with eId %s"
                        .formatted(articleEId),
                    null));
  }

  private String getTextualModEId(TextualMod tm) {
    return tm.getEid().orElseThrow(() -> new XmlContentException("TextualMod eId id empty.", null));
  }

  private String getArticleAffectedDocumentEli(Article a, String articleEId) {
    return a.getAffectedDocumentEli()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "AffectedDocument href is empty in article with eId %s".formatted(articleEId),
                    null));
  }

  private String getArticleEId(Article a) {
    return a.getEid().orElseThrow(() -> new XmlContentException("Article eId is empty.", null));
  }

  private Mod getArticleMod(Article a, String articleEId) {
    return a.getMod()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "There is no mod in article with eId %s".formatted(articleEId), null));
  }

  private String getModOldText(Mod m, String articleEId) {
    return m.getOldText()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "quotedText[1] (the old, to be replaced, text) is empty in article with eId %s"
                        .formatted(articleEId),
                    null));
  }

  private Href getModTargetHref(Mod m, String articleEId) {
    return m.getTargetHref()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "mod href is empty in article with eId %s".formatted(articleEId), null));
  }

  private CharacterRange getHrefCharacterRange(Href h, String articleEId) {
    return h.getCharacterRange()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "The character range in mod href is empty in article with eId %s"
                        .formatted(articleEId),
                    null));
  }

  private String getHrefEid(Href h, String articleEId) {
    return h.getEId()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "The eId in mod href is empty in article with eId %s".formatted(articleEId),
                    null));
  }

  private String getHrefEli(Href h, String articleEId) {
    return h.getEli()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "The Eli in aknMod href is empty in article with eId %s".formatted(articleEId),
                    null));
  }

  private int getCharacterRangeStart(CharacterRange cr, String articleEId) {
    return cr.getStart(articleEId);
  }

  private int getCharacterRangeEnd(CharacterRange cr, String articleEId) {
    return cr.getEnd(articleEId);
  }
}
