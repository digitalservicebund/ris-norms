package de.bund.digitalservice.ris.norms.application.service;

import static java.lang.Integer.parseInt;

import de.bund.digitalservice.ris.norms.adapter.output.database.service.DBService;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

/** */
@Service
public class ModificationValidator {

  private final DBService dbService;

  public ModificationValidator(DBService dbService) {
    this.dbService = dbService;
  }

  /**
   * @param amendingLaw the amending law to be checked
   */
  public void validate(Norm amendingLaw) {
    throwErrorNoDestinationSet(amendingLaw);
    affectedDocumentsExists(amendingLaw);
    nodeWithGivenDestEidExists(amendingLaw);

    destNodeHasTextOnlyContent(amendingLaw);
    modHasValidDestRangeForDestNode(amendingLaw);
    oldTextExistsInTargetLaw(amendingLaw);
  }

  /**
   * Checks if the target law referenced from a modification command exists
   *
   * @param amendingLaw the amending law to be checked
   */
  public void affectedDocumentsExists(Norm amendingLaw) {
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
   * Checks if the text to be replaced is present in the target law
   *
   * @param amendingLaw the amending law to be checked
   */
  public void oldTextExistsInTargetLaw(Norm amendingLaw) {
    // TODO implement unit test
    amendingLaw
        .getArticles()
        .forEach(
            article -> {
              if (article.getAffectedDocumentEli().isEmpty())
                throw new XmlProcessingException("Error TBD 1", null);
              if (article.getMod().getOldText().isEmpty())
                throw new XmlProcessingException("Error TBD 2", null);
              if (article.getMod().getTargetEid().isEmpty())
                throw new XmlProcessingException("Error TBD 3", null);
              if (article.getMod().getTargetHref().isEmpty())
                throw new XmlProcessingException("Error TBD 4", null);
              if (article.getMod().getTargetHref().get().getCharacterRange().isEmpty())
                throw new XmlProcessingException("Error TBD 5", null);

              Optional<Norm> normOptional =
                  dbService.loadNorm(
                      new LoadNormPort.Command(article.getAffectedDocumentEli().get()));

              if (normOptional.isEmpty()) throw new XmlProcessingException("Error TBD 6", null);

              String paragraphText =
                  normOptional
                      .get()
                      .getByEId(article.getMod().getTargetEid().get())
                      .getTextContent();

              String range = article.getMod().getTargetHref().get().getCharacterRange().get();
              int from = parseInt(range.split("-")[0]);
              int to = parseInt(range.split("-")[1]);

              String textToBeReplaced = paragraphText.substring(from, to);

              if (!textToBeReplaced.equals(paragraphText))
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

  /**
   * @param amendingLaw the amending law to be checked
   */
  public void nodeWithGivenDestEidExists(Norm amendingLaw) {
    //    List<String> affectedDocumentEids =
    //        amendingLaw.getActiveModifications().stream()
    //            .map(ActiveModification::getDestinationEid)
    //            .filter(Optional::isPresent)
    //            .map(Optional::get)
    //            .toList();
    // TODO
  }
}
