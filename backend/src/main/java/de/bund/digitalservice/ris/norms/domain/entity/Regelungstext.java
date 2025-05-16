package de.bund.digitalservice.ris.norms.domain.entity;

import static de.bund.digitalservice.ris.norms.utils.NodeParser.getElementsFromExpression;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import org.w3c.dom.Document;

/**
 * Represents the "Regelungstext" of a norm in LDML.de.
 */
@Getter
public non-sealed class Regelungstext extends Dokument {

  @Override
  public Dokument copy() {
    return new Regelungstext(this);
  }

  public Regelungstext(Regelungstext regelungstext) {
    this((Document) regelungstext.getDocument().cloneNode(true));
  }

  public Regelungstext(Document document) {
    super(document);
  }

  /**
   * Returns the title as {@link String} from a {@link Document} in a {@link Regelungstext}.
   *
   * @return The title
   */
  public Optional<String> getTitle() {
    return NodeParser.getValueFromExpression("//longTitle/*/docTitle", getDocument());
  }

  /**
   * Returns the short title as {@link String} from a {@link Document} in a {@link Regelungstext}.
   *
   * @return The short title
   */
  public Optional<String> getShortTitle() {
    return NodeParser.getValueFromExpression(
      "//longTitle/*/shortTitle/*[@refersTo=\"amtliche-abkuerzung\"]",
      getDocument()
    ).or(() -> NodeParser.getValueFromExpression("//longTitle/*/shortTitle", getDocument()));
  }

  /**
   * Returns the "Ausfertigungsdatum" as a {@link String} from the preface (to our current knowledge only logically belonging to Regelungstext)
   *
   * @return The date value
   */
  public Optional<String> getDateAusfertigung() {
    return NodeParser.getValueFromExpression(
      "//preface/block/date[@refersTo='ausfertigung-datum']/@date",
      getDocument()
    );
  }

  /**
   * Returns a list of articles as {@link List} from a {@link Document} in a {@link Regelungstext}. It
   * filters out articles within akn:mod
   *
   * @return The list of articles
   */
  public List<Article> getArticles() {
    return getElementsFromExpression("//body//article[not(ancestor-or-self::mod)]", getDocument())
      .stream()
      .map(Article::new)
      .toList();
  }
}
