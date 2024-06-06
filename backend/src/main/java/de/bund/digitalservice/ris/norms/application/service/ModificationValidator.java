package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlContentException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

/** */
@Service
public class ModificationValidator {
  private final LoadNormPort loadNormPort;
  private final LoadZf0Service loadZf0Service;

  public ModificationValidator(LoadNormPort loadNormPort, LoadZf0Service loadZf0Service) {
    this.loadNormPort = loadNormPort;
    this.loadZf0Service = loadZf0Service;
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
    String amendingNormEli = amendingNorm.getEli();
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

    List<Mod> mods = articles.stream().map(Article::getMods).flatMap(List::stream).toList();

    mods.forEach(mod -> validateSubstitutionMod(amendingNormEli, mod));
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
    List<Mod> mods = getArticleMod(amendingNormEli, article, articleEId);
    mods.forEach(mod -> getModTargetHref(amendingNormEli, mod, articleEId));
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

  /**
   * Checks if a substitution mod is consistent
   *
   * @param amendingLawEli the amending Norm Eli
   * @param mod the amending law mod to be checked
   */
  public void validateSubstitutionMod(String amendingLawEli, Mod mod) {
    if (mod.usesQuotedText()) {
      validateQuotedTextSubstitutions(amendingLawEli, mod);
    }
    // other case <akn:quotedStructure>
  }

  /**
   * Checks if the text to be replaced is present in the target law
   *
   * @param amendingNormEli the amending Norm Eli
   * @param mod the amending law mod to be checked
   */
  private void validateQuotedTextSubstitutions(String amendingNormEli, Mod mod) {
    String modEId = getModEId(mod);
    Href articleModTargetHref = getModTargetHref(amendingNormEli, mod, modEId);
    Norm amendingLaw =
        loadNormPort
            .loadNorm(new LoadNormPort.Command(amendingNormEli))
            .orElseThrow(() -> new XmlContentException("Could not load norm", null));
    Norm zf0Norm = loadZf0Service.loadZf0(amendingLaw, mod);

    String articleModTargetHrefEId =
        getHrefEId(
            amendingNormEli,
            articleModTargetHref,
            "The eId in mod href is empty in article with eId %s".formatted(modEId));

    Node zf0TargetNode =
        getTargetNodeFromZF0Norm(
            amendingNormEli, zf0Norm, articleModTargetHrefEId, amendingNormEli, modEId);

    // normalizeSpace removes double spaces and new lines
    String targetParagraphOldText = StringUtils.normalizeSpace(zf0TargetNode.getTextContent());

    String amendingNormOldText =
        getModOldText(
            amendingNormEli,
            mod,
            "quotedText[1] (the old, to be replaced, text) is empty in article mod with eId %s"
                .formatted(modEId));

    validateQuotedText(
        amendingNormEli,
        amendingNormOldText,
        targetParagraphOldText,
        modEId,
        articleModTargetHref,
        "The character range in mod href is empty in article with eId %s".formatted(modEId));
  }

  private void validateQuotedText(
      String eli,
      String amendingNormOldText,
      String targetParagraphOldText,
      String modEId,
      Href href,
      String message) {

    CharacterRange characterRange = getHrefCharacterRange(eli, href, message);

    int modStart = getCharacterRangeStart(characterRange, modEId);
    int modEnd = getCharacterRangeEnd(characterRange, modEId);

    validateStartIsBeforeEnd(eli, characterRange, modStart, modEnd, modEId);
    checkIfReplacementEndIsWithinText(eli, targetParagraphOldText, modEnd, modEId);

    String zf0NormOldText = targetParagraphOldText.substring(modStart, modEnd);
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
        .getNodeByEId(targetHrefEId)
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): Couldn't load target eId (%s) element in zf0 (%s) for mod with eId %s"
                        .formatted(amendingNormEli, targetHrefEId, affectedDocumentEli, modEId),
                    null));
  }

  private void validateNumberOfNodesWithEid(String eli, Norm norm, String eId) {
    if (norm.getNumberOfNodesWithEid(eId) > 1) {
      throw new XmlContentException(
          "For norm with Eli (%s): Too many elements with the same eId %s.".formatted(eli, eId),
          null);
    }
  }

  private String getArticleRefersTo(String eli, Article a, String articleEId) {
    return a.getRefersTo()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): RefersTo is empty in article with eId %s"
                        .formatted(eli, articleEId),
                    null));
  }

  private void getArticleAffectedDocumentEli(String eli, Article a, String articleEId) {
    if (a.getAffectedDocumentEli().isEmpty()) {
      throw new XmlContentException(
          "For norm with Eli (%s): AffectedDocument href is empty in article with eId %s"
              .formatted(eli, articleEId),
          null);
    }
  }

  private String getArticleEId(String eli, Article a) {
    return a.getEid()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "For norm with Eli (%s): Article eId is empty.".formatted(eli), null));
  }

  private List<Mod> getArticleMod(String eli, Article a, String articleEId) {
    List<Mod> modsInArticle = a.getMods();
    if (modsInArticle.isEmpty()) {
      throw new XmlContentException(
          "For norm with Eli (%s): There is no mod in article with eId %s"
              .formatted(eli, articleEId),
          null);
    } else return modsInArticle;
  }

  private String getModEId(Mod mod) {
    return mod.getEid()
        .orElseThrow(
            () ->
                new XmlContentException(
                    "Eid in mod %s is empty".formatted(XmlMapper.toString(mod.getNode())), null));
  }

  private String getModOldText(String eli, Mod mod, String message) {
    String oldText =
        mod.getOldText()
            .orElseThrow(
                () ->
                    new XmlContentException(
                        "For norm with Eli (%s): %s".formatted(eli, message), null));
    return StringUtils.normalizeSpace(oldText);
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

  private int getCharacterRangeStart(CharacterRange cr, String modEId) {
    return cr.getStart(modEId);
  }

  private int getCharacterRangeEnd(CharacterRange cr, String modEId) {
    return cr.getEnd(modEId);
  }
}
