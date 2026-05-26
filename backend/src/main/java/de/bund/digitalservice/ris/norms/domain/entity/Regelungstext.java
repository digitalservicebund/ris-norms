package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Optional;
import lombok.Getter;
import org.w3c.dom.Document;

/**
 * Represents the "Regelungstext" of a norm in LDML.de.
 */
@Getter
public non-sealed class Regelungstext extends Dokument {

  public Regelungstext(Regelungstext regelungstext) {
    this((Document) regelungstext.getDocument().cloneNode(true));
  }

  public Regelungstext(Document document) {
    super(document);
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
}
