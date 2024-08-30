package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.ValidationException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadZf0UseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Analysis;
import de.bund.digitalservice.ris.norms.domain.entity.Article;
import de.bund.digitalservice.ris.norms.domain.entity.Href;
import de.bund.digitalservice.ris.norms.domain.entity.Mod;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.TextualMod;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * Validator for a whole amending law. It checks if the destination is set and if both destination
 * href and i ELI are consistent. It also checks all the mods by using the {@link
 * SingleModValidator} on each single mod.
 */
@Component
public class AmendingLawValidator {

  private final LoadNormPort loadNormPort;
  private final LoadZf0Service loadZf0Service;
  private final SingleModValidator singleModValidator;

  public AmendingLawValidator(
    final LoadNormPort loadNormPort,
    final LoadZf0Service loadZf0Service,
    final SingleModValidator singleModValidator
  ) {
    this.loadNormPort = loadNormPort;
    this.loadZf0Service = loadZf0Service;
    this.singleModValidator = singleModValidator;
  }

  /**
   * Validates the given amending law norm.
   *
   * @param amendingLawNorm - the amending law in form of {@link Norm}
   * @throws ValidationException if a validation step fails
   */
  public void validate(final Norm amendingLawNorm) throws ValidationException {
    destinationIsSet(amendingLawNorm);
    destinationEliIsConsistent(amendingLawNorm);
    destinationHrefIsConsistent(amendingLawNorm);
    checkAllMods(amendingLawNorm);
  }

  private void checkAllMods(Norm amendingNorm) {
    final List<Article> articles = amendingNorm
      .getArticles()
      .stream()
      .filter(article -> {
        String articleRefersTo = article
          .getRefersTo()
          .orElseThrow(() ->
            new ValidationException(
              "For norm with Eli (%s): RefersTo is empty in article with eId %s".formatted(
                  amendingNorm.getEli(),
                  article.getEid()
                )
            )
          );
        return Objects.equals(articleRefersTo, "hauptaenderung");
      })
      .toList();

    final List<Mod> mods = articles.stream().map(Article::getMods).flatMap(List::stream).toList();

    mods.forEach(mod -> {
      final String targetNormEli = mod
        .getTargetRefHref()
        .orElseThrow(() -> new ValidationException("Target href missing"))
        .getEli()
        .orElseThrow(() -> new ValidationException("Target eli missing"));
      final Norm targetNorm = loadNormPort
        .loadNorm(new LoadNormPort.Command(targetNormEli))
        .orElseThrow(() ->
          new ValidationException(String.format("Target law with eli %s missing", targetNormEli))
        );
      final Norm zf0Norm = loadZf0Service.loadOrCreateZf0(
        new LoadZf0UseCase.Query(amendingNorm, targetNorm)
      );
      singleModValidator.validate(zf0Norm, mod);
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
      .forEach(tm ->
        tm
          .getDestinationHref()
          .orElseThrow(() -> {
            String amendingNormEli = amendingNorm.getEli();
            return new ValidationException(
              "For norm with Eli (%s): ActiveModification Destination Href is empty where textualMod eId is %s".formatted(
                  amendingNormEli,
                  getTextualModEId(amendingNormEli, tm)
                )
            );
          })
          .getEli()
          .orElseThrow(() -> {
            String amendingNormEli = amendingNorm.getEli();
            return new ValidationException(
              "For norm with Eli (%s): ActiveModification Destination Href holds an empty (more general: invalid) Eli where textualMod eId is %s".formatted(
                  amendingNormEli,
                  getTextualModEId(amendingNormEli, tm)
                )
            );
          })
      );
  }

  private void validateArticlesEli(Norm amendingNorm) {
    amendingNorm
      .getArticles()
      .stream()
      .filter(article -> {
        String articleRefersTo;
        try {
          articleRefersTo =
          article
            .getRefersTo()
            .orElseThrow(() ->
              new ValidationException(
                "For norm with Eli (%s): RefersTo is empty in article with eId %s".formatted(
                    amendingNorm.getEli(),
                    article.getEid().get()
                  )
              )
            );
        } catch (NullPointerException e) {
          throw new ValidationException(e.getMessage());
        }

        return Objects.equals(articleRefersTo, "hauptaenderung");
      })
      .forEach(article -> {
        String amendingNormEli = amendingNorm.getEli();
        String articleEId = article.getMandatoryEid();
        validateAknModEli(amendingNormEli, article, articleEId);
        validateAffectedDocumentEli(amendingNormEli, article, articleEId);
      });
  }

  private void validateAknModEli(String amendingNormEli, Article article, String articleEId) {
    List<Mod> mods = article.getMods();
    mods.forEach(mod -> {
      Optional<String> eli = getModTargetHref(amendingNormEli, mod, articleEId).getEli();
      if (eli.isEmpty()) {
        throw new ValidationException(
          "For norm with Eli (%s): The Eli in aknMod href is empty in article with eId %s".formatted(
              amendingNormEli,
              articleEId
            )
        );
      }
    });
  }

  private void validateAffectedDocumentEli(
    String amendingNormEli,
    Article article,
    String articleEId
  ) {
    if (article.getAffectedDocumentEli().isEmpty()) {
      throw new ValidationException(
        "For norm with Eli (%s): AffectedDocument href is empty in article with eId %s".formatted(
            amendingNormEli,
            articleEId
          )
      );
    }
  }

  /**
   * Checks whether a reference to a target law in an amending law is consistent in
   * akn:activeModifications - destination and akn:mod
   *
   * @param amendingNorm the amending law to be checked
   */
  public void destinationEliIsConsistent(Norm amendingNorm) {
    Set<String> affectedDocumentElis = amendingNorm
      .getArticles()
      .stream()
      .map(Article::getAffectedDocumentEli)
      .flatMap(Optional::stream)
      .collect(Collectors.toSet());

    Set<String> activeModificationsDestinationElis = amendingNorm
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

    Set<String> aknModElis = amendingNorm
      .getArticles()
      .stream()
      .map(Article::getMods)
      .flatMap(List::stream)
      .map(Mod::getTargetRefHref)
      .flatMap(Optional::stream)
      .map(Href::getEli)
      .flatMap(Optional::stream)
      .collect(Collectors.toSet());

    if (
      !affectedDocumentElis.equals(activeModificationsDestinationElis) &&
      !activeModificationsDestinationElis.equals(aknModElis)
    ) throw new ValidationException(
      "For norm with Eli (%s): Elis are not consistent".formatted(amendingNorm.getEli())
    );
  }

  /**
   * Checks whether a reference to a target law in an amending law is consistent in
   * akn:activeModifications - destination and akn:mod
   *
   * @param amendingNorm the amending law to be checked
   */
  public void destinationHrefIsConsistent(Norm amendingNorm) {
    Set<Href> activeModificationsDestinationHrefs = amendingNorm
      .getMeta()
      .getAnalysis()
      .map(Analysis::getActiveModifications)
      .orElse(Collections.emptyList())
      .stream()
      .map(TextualMod::getDestinationHref)
      .flatMap(Optional::stream)
      .collect(Collectors.toSet());

    Set<Href> aknModHrefs = amendingNorm
      .getArticles()
      .stream()
      .map(Article::getMods)
      .flatMap(List::stream)
      .map(Mod::getTargetRefHref)
      .flatMap(Optional::stream)
      .collect(Collectors.toSet());
    if (!activeModificationsDestinationHrefs.equals(aknModHrefs)) throw new ValidationException(
      "For norm with Eli (%s): Eids are not consistent".formatted(amendingNorm.getEli())
    );
  }

  private Href getModTargetHref(String eli, Mod m, String modEId) {
    return m
      .getTargetRefHref()
      .orElseThrow(() ->
        new ValidationException(
          "For norm with Eli (%s): mod href is empty in article mod with eId %s".formatted(
              eli,
              modEId
            )
        )
      );
  }

  private String getTextualModEId(String eli, TextualMod textualMod) {
    return textualMod
      .getEid()
      .orElseThrow(() ->
        new ValidationException("For norm with Eli (%s): TextualMod eId empty.".formatted(eli))
      );
  }
}
