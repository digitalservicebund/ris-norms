package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadZf0UseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Analysis;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Mod;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.TextualMod;
import de.bund.digitalservice.ris.norms.utils.exceptions.ValidationException;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlContentException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Validator for a whole amending law. It checks if the destination is set and if both destination
 * href and i ELI are consistent. It also checks all the mods by using the {@link
 * ModificationValidator} on each single mod.
 */
public class AmendingLawValidator {

  private final LoadNormPort loadNormPort;
  private final LoadZf0Service loadZf0Service;
  private final ModificationValidator modificationValidator;

  public AmendingLawValidator(
      final LoadNormPort loadNormPort,
      final LoadZf0Service loadZf0Service,
      final ModificationValidator modificationValidator) {
    this.loadNormPort = loadNormPort;
    this.loadZf0Service = loadZf0Service;
    this.modificationValidator = modificationValidator;
  }

  /**
   * Checks an amending law xml for consistency errors.
   *
   * @param amendingNorm the amending norm to be checked
   */
  public void validate(Norm amendingNorm) {
    destinationIsSet(amendingNorm);
    destinationEliIsConsistent(amendingNorm);
    destinationHrefIsConsistent(amendingNorm);
    checkAllMods(amendingNorm);
  }

  private void checkAllMods(Norm amendingNorm) {
    final List<Article> articles =
        amendingNorm.getArticles().stream()
            .filter(
                article -> {
                  String articleRefersTo = article.getRefersToOrThrow();
                  return Objects.equals(articleRefersTo, "hauptaenderung");
                })
            .toList();

    final List<Mod> mods = articles.stream().map(Article::getMods).flatMap(List::stream).toList();

    mods.forEach(
        mod -> {
          final String targetNormEli =
              mod.getTargetHref()
                  .orElseThrow(() -> new ValidationException("Target href missing"))
                  .getEli()
                  .orElseThrow(() -> new ValidationException("Target eli missing"));
          final Norm targetNorm =
              loadNormPort
                  .loadNorm(new LoadNormPort.Command(targetNormEli))
                  .orElseThrow(
                      () ->
                          new ValidationException(
                              String.format("Target law with eli %s missing", targetNormEli)));
          final Norm zf0Norm =
              loadZf0Service.loadZf0(new LoadZf0UseCase.Query(amendingNorm, targetNorm));
          modificationValidator.validateSubstitutionMod(zf0Norm, mod);
        });
  }

  /**
   * Throws an error if any of the articles of the passed amendingNorm has an empty affected
   * document Eli. The error message contains a comma separated list of all article eIds, that are
   * affected.
   *
   * @param amendingNorm the amending law to be checked
   */
  public void destinationIsSet(Norm amendingNorm) {
    validateActiveModificationsEli(amendingNorm);
    validateArticlesEli(amendingNorm);
  }

  private void validateActiveModificationsEli(Norm amendingNorm) {
    amendingNorm
        .getMeta()
        .getAnalysis()
        .map(Analysis::getActiveModifications)
        .orElse(Collections.emptyList())
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
              String articleRefersTo = article.getRefersToOrThrow();
              return Objects.equals(articleRefersTo, "hauptaenderung");
            })
        .forEach(
            article -> {
              String amendingNormEli = amendingNorm.getEli();
              String articleEId = article.getEidOrThrow();
              validateAknModEli(amendingNormEli, article, articleEId);
              validateAffectedDocumentEli(amendingNormEli, article, articleEId);
            });
  }

  private void validateAknModEli(String amendingNormEli, Article article, String articleEId) {
    List<Mod> mods = article.getModsOrThrow();
    mods.forEach(
        mod -> {
          Optional<String> eli = getModTargetHref(amendingNormEli, mod, articleEId).getEli();
          if (eli.isEmpty()) {
            throw new XmlContentException(
                "For norm with Eli (%s): The Eli in aknMod href is empty in article with eId %s"
                    .formatted(amendingNormEli, articleEId),
                null);
          }
        });
  }

  private void validateAffectedDocumentEli(
      String amendingNormEli, Article article, String articleEId) {
    if (article.getAffectedDocumentEli().isEmpty()) {
      throw new XmlContentException(
          "For norm with Eli (%s): AffectedDocument href is empty in article with eId %s"
              .formatted(amendingNormEli, articleEId),
          null);
    }
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
        amendingNorm
            .getMeta()
            .getAnalysis()
            .map(Analysis::getActiveModifications)
            .orElse(Collections.emptyList())
            .stream()
            .map(TextualMod::getDestinationHref)
            .flatMap(Optional::stream)
            .map(Href::getEli)
            .flatMap(Optional::stream)
            .collect(Collectors.toSet());

    Set<String> aknModElis =
        amendingNorm.getArticles().stream()
            .map(Article::getMods)
            .flatMap(List::stream)
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
        amendingNorm
            .getMeta()
            .getAnalysis()
            .map(Analysis::getActiveModifications)
            .orElse(Collections.emptyList())
            .stream()
            .map(TextualMod::getDestinationHref)
            .flatMap(Optional::stream)
            .collect(Collectors.toSet());

    Set<Href> aknModHrefs =
        amendingNorm.getArticles().stream()
            .map(Article::getMods)
            .flatMap(List::stream)
            .map(Mod::getTargetHref)
            .flatMap(Optional::stream)
            .collect(Collectors.toSet());
    if (!activeModificationsDestinationHrefs.equals(aknModHrefs))
      throw new XmlContentException(
          "For norm with Eli (%s): Eids are not consistent".formatted(amendingNorm.getEli()), null);
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

  private String getTextualModEId(String eli, TextualMod textualMod) {
    return textualMod
        .getEid()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): TextualMod eId empty.".formatted(eli), null));
  }
}
