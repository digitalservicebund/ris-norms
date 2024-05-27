package de.bund.digitalservice.ris.norms.application.service;

import static java.lang.Integer.parseInt;

import de.bund.digitalservice.ris.norms.adapter.output.database.service.DBService;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
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
    // TODO create new, other exception
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
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toSet());

    Set<String> activeModificationsDestinationElis =
        amendingLaw.getActiveModifications().stream()
            .map(TextualMod::getDestinationHref)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Href::getEli)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toSet());

    Set<String> aknModElis =
        amendingLaw.getArticles().stream()
            .map(Article::getMod)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Mod::getTargetHref)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Href::getEli)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toSet());

    if (!affectedDocumentElis.equals(activeModificationsDestinationElis)
        && !activeModificationsDestinationElis.equals(aknModElis))
      throw new XmlProcessingException("Elis are not consistent", null);
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
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toSet());

    Set<Href> aknModElis =
        amendingLaw.getArticles().stream()
            .map(Article::getMod)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Mod::getTargetHref)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toSet());
    if (!activeModificationsDestinationElis.equals(aknModElis))
      throw new XmlProcessingException("Eids are not consistent", null);
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
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();

    affectedDocumentElis.forEach(
        eli -> {
          Optional<Norm> norm = dbService.loadNorm(new LoadNormPort.Command(eli));
          if (norm.isEmpty()) {
            throw new XmlProcessingException(
                "Could not find a norm with the eli %s for the amending law %s"
                    .formatted(eli, amendingLaw.getEli()),
                null);
          }
        });
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
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();

    affectedDocumentElis.forEach(
        destination -> {
          if (destination.getEli().isPresent()) {
            Optional<Norm> targetNorm =
                dbService.loadNorm(new LoadNormPort.Command(destination.getEli().get()));
            if (destination.getEId().isPresent() && targetNorm.isPresent()) {
              List<Node> targetLawNode = targetNorm.get().getNodeByEid(destination.getEId().get());
              if (targetLawNode.size() > 1) {
                throw new XmlProcessingException(
                    "To many matching eIds for %s in target norm %s"
                        .formatted(
                            destination.getEId().orElse(""), targetNorm.get().getEli().orElse("")),
                    null);
              }
              if (targetLawNode.isEmpty()) {
                throw new XmlProcessingException("No matching eIds found", null);
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
              if (article.getEid().isEmpty())
                throw new XmlProcessingException("Article eId is empty.", null);
              if (article.getAffectedDocumentEli().isEmpty())
                throw new XmlProcessingException(
                    "AffectedDocument href is empty in article with eId %s"
                        .formatted(article.getEid().get()),
                    null);
              if (article.getMod().isEmpty())
                throw new XmlProcessingException(
                    "There is no mod in article with eId %s".formatted(article.getEid().get()),
                    null);
              if (article.getMod().get().getOldText().isEmpty())
                throw new XmlProcessingException(
                    "quotedText[1] (the old, to be replaced, text) is empty in article with eId %s"
                        .formatted(article.getEid().get()),
                    null);
              if (article.getMod().get().getTargetHref().isEmpty())
                throw new XmlProcessingException(
                    "mod href is empty in article with eId %s".formatted(article.getEid().get()),
                    null);
              if (article.getMod().get().getTargetEid().isEmpty())
                throw new XmlProcessingException(
                    "The eId in mod href is empty in article with eId %s"
                        .formatted(article.getEid().get()),
                    null);
              if (article.getMod().get().getTargetHref().get().getCharacterRange().isEmpty())
                throw new XmlProcessingException(
                    "The character range in mod href is empty in article with eId %s"
                        .formatted(article.getEid().get()),
                    null);

              Optional<Norm> normOptional =
                  dbService.loadNorm(
                      new LoadNormPort.Command(article.getAffectedDocumentEli().get()));

              // TODO this may obsoletes affectedDocumentsExists()
              if (normOptional.isEmpty())
                throw new XmlProcessingException(
                    "Couldn't load target law by Eli: The affectedDocument href may hold an invalid value in article with eId %s"
                        .formatted(article.getEid().get()),
                    null);

              if (normOptional
                  .get()
                  .getByEId(article.getMod().get().getTargetEid().get())
                  .isEmpty())
                throw new XmlProcessingException(
                    "Couldn't load target eId element in target law for article with eId %s"
                        .formatted(article.getEid().get()),
                    null);

              String paragraphText =
                  normOptional
                      .get()
                      .getByEId(article.getMod().get().getTargetEid().get())
                      .get()
                      .getTextContent();

              // TODO what if paragraphText is empty string?

              // TODO critical part
              paragraphText = StringUtils.normalizeSpace(paragraphText);

              // TODO move to a new CharacterRange class
              String[] range =
                  article.getMod().get().getTargetHref().get().getCharacterRange().get().split("-");
              // TODO test for that throw
              if (range.length != 2)
                throw new XmlProcessingException(
                    "The character range in mod href is not valid (no range given) in article with eId %s"
                        .formatted(article.getEid().get()),
                    null);

              // TODO parseInt could fail -> partly covered by modHasValidDestRangeForDestNode()
              int from = parseInt(range[0]);
              int to = parseInt(range[1]);

              // TODO test for that throw
              if (from >= to)
                throw new XmlProcessingException(
                    "The character range in mod href is not valid (%s >= %s) in article with eId %s"
                        .formatted(from, to, article.getEid().get()),
                    null);

              // TODO test for that throw
              // TODO add test when text to be replaced is at the end
              // TODO this is most probably not correct -> <=
              if (paragraphText.length() < to)
                throw new XmlProcessingException(
                    "The character range in mod href is not valid (target paragraph is to short) in article with eId %s"
                        .formatted(article.getEid().get()),
                    null);

              String textToBeReplaced = paragraphText.substring(from, to);

              // TODO test for that throw and improve error message
              if (!textToBeReplaced.equals(article.getMod().get().getOldText().get()))
                throw new XmlProcessingException("Error TBD 7", null);
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
      throw new XmlProcessingException(
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

  /**
   * Throws an error if any of the articles of the passed amendingLaw has an empty affected document
   * Eli. The error message contains a comma separated list of all article eIds, that are affected.
   *
   * @param amendingLaw the amending law to be checked
   */
  public void throwErrorNoDestinationSet(Norm amendingLaw) {
    // TODO need to test for the other two Elis, too.
    List<String> articleEidsWhereAffectedDocumentEliIsEmpty =
        amendingLaw.getArticles().stream()
            .filter(a -> a.getAffectedDocumentEli().isEmpty())
            .map(Article::getEid)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();

    if (!articleEidsWhereAffectedDocumentEliIsEmpty.isEmpty()) {
      throw new XmlProcessingException(
          "Some articles have empty affected document Elis. Here are the according article eIds: %s"
              .formatted(String.join(", ", articleEidsWhereAffectedDocumentEliIsEmpty)),
          null);
    }
  }
}
