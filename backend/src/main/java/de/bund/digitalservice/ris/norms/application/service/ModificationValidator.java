package de.bund.digitalservice.ris.norms.application.service;

import static java.lang.Integer.parseInt;

import de.bund.digitalservice.ris.norms.adapter.output.database.service.DBService;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlContentException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

/** */
@Service
@Slf4j
public class ModificationValidator {

  private final DBService dbService;

  public ModificationValidator(DBService dbService) {
    // TODO use specific port
    this.dbService = dbService;
  }

  /**
   * @param amendingLaw the amending law to be checked
   */
  public void validate(Norm amendingLaw) {
    throwErrorNoDestinationSet(amendingLaw);
    destinationEliIsConsistent(amendingLaw);
    destinationHrefIsConsistent(amendingLaw);
    affectedDocumentsExists(amendingLaw);
    nodeWithGivenDestEidExists(amendingLaw);

    destNodeHasTextOnlyContent(amendingLaw);
    modHasValidDestRangeForDestNode(amendingLaw);
    oldTextExistsInTargetLaw(amendingLaw);
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
    return a.getEid().orElseThrow(() -> new XmlContentException("Article eId id empty.", null));
  }

  private Mod getArticleMod(Article a, String articleEId) {
    return a.getMod()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "Empty aknMod for article with eId %s".formatted(articleEId), null));
  }

  private Href getModTargetHref(Mod m, String articleEId) {
    return m.getTargetHref()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "aknMod href is empty in article with eId %s".formatted(articleEId), null));
  }

  private String getHrefEli(Href h, String articleEId) {
    return h.getEli()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "The Eli in aknMod href is empty in article with eId %s".formatted(articleEId),
                    null));
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
                            // TODO add unit test for this throw
                            new XmlContentException(
                                "ActiveModification Destination Href holds an empty Eli where textualMod eId is %s"
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
   * Checks whether an reference to a target law in an amending law is consistent in
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
   * Checks whether an reference to a target law in an amending law is consistent in
   * akn:activeModifications - destination and akn:mod
   *
   * @param amendingLaw the amending law to be checked
   */
  public void destinationHrefIsConsistent(Norm amendingLaw) {
    // TODO rename variables
    Set<Href> activeModificationsDestinationElis =
        amendingLaw.getActiveModifications().stream()
            .map(TextualMod::getDestinationHref)
            .flatMap(Optional::stream)
            .collect(Collectors.toSet());

    Set<Href> aknModElis =
        amendingLaw.getArticles().stream()
            .map(Article::getMod)
            .flatMap(Optional::stream)
            .map(Mod::getTargetHref)
            .flatMap(Optional::stream)
            .collect(Collectors.toSet());
    if (!activeModificationsDestinationElis.equals(aknModElis))
      throw new XmlContentException("Eids are not consistent", null);
  }

  /**
   * Checks if the target law referenced from a modification command exists
   *
   * @param amendingLaw the amending law to be checked
   */
  public void affectedDocumentsExists(Norm amendingLaw) {
    // TODO maybe obsolete due to oldTextExistsInTargetLaw()
    // TODO not needed as already checked by NormService beforehand
    List<String> affectedDocumentElis =
        amendingLaw.getArticles().stream()
            .map(Article::getAffectedDocumentEli)
            .flatMap(Optional::stream)
            .toList();

    affectedDocumentElis.forEach(
        eli ->
            dbService
                .loadNorm(new LoadNormPort.Command(eli))
                .orElseThrow(
                    () ->
                        new XmlContentException(
                            "Could not find a norm with the eli %s for the amending law %s"
                                .formatted(eli, amendingLaw.getEli()),
                            null)));
  }

  /**
   * Checks whether an eid that is referenced in an amending law exists in the target law
   *
   * @param amendingLaw the amending law to be checked
   */
  public void nodeWithGivenDestEidExists(Norm amendingLaw) {
    // TODO log error or throw exception when eId is empty
    List<Href> affectedDocumentElis =
        amendingLaw.getActiveModifications().stream()
            .map(TextualMod::getDestinationHref)
            .flatMap(Optional::stream)
            .toList();

    affectedDocumentElis.forEach(
        destination -> {
          if (destination.getEli().isPresent()) {
            Optional<Norm> targetNorm =
                dbService.loadNorm(new LoadNormPort.Command(destination.getEli().get()));
            if (destination.getEId().isPresent() && targetNorm.isPresent()) {
              List<Node> targetLawNode = targetNorm.get().getNodeByEid(destination.getEId().get());
              if (targetLawNode.size() > 1) {
                throw new XmlContentException(
                    "To many matching eIds for %s in target norm %s"
                        .formatted(
                            destination.getEId().orElse(""), targetNorm.get().getEli().orElse("")),
                    null);
              }
              if (targetLawNode.isEmpty()) {
                throw new XmlContentException("No matching eIds found", null);
              }
              log.info("EId found");
            }
          }
        });
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
              String articleEId =
                  article
                      .getEid()
                      .orElseThrow(() -> new XmlContentException("Article eId is empty.", null));
              String affectedDocumentEli =
                  article
                      .getAffectedDocumentEli()
                      .orElseThrow(
                          () ->
                              new XmlContentException(
                                  "AffectedDocument href is empty in article with eId %s"
                                      .formatted(articleEId),
                                  null));
              Mod mod =
                  article
                      .getMod()
                      .orElseThrow(
                          () ->
                              new XmlContentException(
                                  "There is no mod in article with eId %s".formatted(articleEId),
                                  null));
              String oldText =
                  mod.getOldText()
                      .orElseThrow(
                          () ->
                              new XmlContentException(
                                  "quotedText[1] (the old, to be replaced, text) is empty in article with eId %s"
                                      .formatted(articleEId),
                                  null));
              Href targetHref =
                  mod.getTargetHref()
                      .orElseThrow(
                          () ->
                              new XmlContentException(
                                  "mod href is empty in article with eId %s".formatted(articleEId),
                                  null));
              String targetHrefEId =
                  targetHref
                      .getEId()
                      .orElseThrow(
                          () ->
                              new XmlContentException(
                                  "The eId in mod href is empty in article with eId %s"
                                      .formatted(articleEId),
                                  null));
              String characterRange =
                  targetHref
                      .getCharacterRange()
                      .orElseThrow(
                          () ->
                              new XmlContentException(
                                  "The character range in mod href is empty in article with eId %s"
                                      .formatted(articleEId),
                                  null));

              // TODO this may obsoletes affectedDocumentsExists()
              Norm targetLaw =
                  dbService
                      .loadNorm(new LoadNormPort.Command(affectedDocumentEli))
                      .orElseThrow(
                          () ->
                              new XmlContentException(
                                  "Couldn't load target law by Eli: The affectedDocument href may hold an invalid value in article with eId %s"
                                      .formatted(articleEId),
                                  null));

              // TODO put here nodeWithGivenDestEidExists() to check if there is exactly 1 node with
              // that eId

              Node targetNode =
                  targetLaw
                      .getByEId(targetHrefEId)
                      .orElseThrow(
                          () ->
                              new XmlContentException(
                                  "Couldn't load target eId element in target law for article with eId %s"
                                      .formatted(articleEId),
                                  null));

              // normalizeSpace removes double spaces and new lines
              String paragraphText = StringUtils.normalizeSpace(targetNode.getTextContent());

              // TODO what if paragraphText is empty string?

              // TODO move to a new CharacterRange class
              String[] range = characterRange.split("-");
              // TODO test for that throw
              if (range.length != 2)
                throw new XmlContentException(
                    "The character range in mod href is not valid (no range given) in article with eId %s"
                        .formatted(articleEId),
                    null);

              // TODO parseInt could fail -> partly covered by modHasValidDestRangeForDestNode()
              int from = parseInt(range[0]);
              int to = parseInt(range[1]);

              // TODO test for that throw
              if (from >= to)
                throw new XmlContentException(
                    "The character range in mod href is not valid (%s >= %s) in article with eId %s"
                        .formatted(from, to, articleEId),
                    null);

              // TODO test for that throw
              // TODO add test when text to be replaced is at the end
              // TODO this is most probably not correct -> <=
              if (paragraphText.length() < to)
                throw new XmlContentException(
                    "The character range in mod href is not valid (target paragraph is to short) in article with eId %s"
                        .formatted(articleEId),
                    null);

              String textToBeReplaced = paragraphText.substring(from, to);

              // TODO test for that throw and improve error message
              if (!textToBeReplaced.equals(oldText))
                throw new XmlContentException("Error TBD 7", null);
            });
  }

  /**
   * Throws an error if any of the textualModifications of the passed amendingLaw has a broken
   * destination href character range. The error message contains a comma separated list of all
   * textualMod eIds, that are affected. Pre-assumption: For this to work, the textualMod needs to
   * have a valid eId.
   *
   * @param amendingLaw the amending law to be checked
   */
  public void modHasValidDestRangeForDestNode(Norm amendingLaw) {
    final String regex = "^\\d*-\\d*$";
    final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

    // TODO also check article -> mod
    List<String> textualModEidsWhereDestRangeIsBroken =
        amendingLaw.getActiveModifications().stream()
            .filter(
                tm ->
                    (tm.getDestinationHref().isEmpty()
                            || tm.getDestinationHref().get().getCharacterRange().isEmpty()
                            || !pattern
                                .matcher(tm.getDestinationHref().get().getCharacterRange().get())
                                .hasMatch())
                        && tm.getEid().isPresent())
            .map(am -> am.getEid().get())
            .toList();

    if (!textualModEidsWhereDestRangeIsBroken.isEmpty()) {
      throw new XmlContentException(
          "Some textual modifications have broken destination ranges. Here are the according textualMod eIds: %s"
              .formatted(String.join(", ", textualModEidsWhereDestRangeIsBroken)),
          null);
    }
  }

  /**
   * @param amendingLaw the amending law to be checked
   */
  public void destNodeHasTextOnlyContent(Norm amendingLaw) {
    // TODO no "<>" exists
  }
}
