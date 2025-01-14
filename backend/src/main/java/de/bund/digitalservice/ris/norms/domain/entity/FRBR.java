package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.Eli;
import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.net.URI;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.w3c.dom.Element;

/**
 * Class representing a generalization of FRBR elements. It cannot be instantiated itself because it
 * needs a custom implementation of a level.
 */
@Getter
@AllArgsConstructor
public abstract class FRBR {

  private final Element element;
  private static final String VALUE_ATTIBUTE = "value";

  /**
   * Returns the Eli as {@link String} from the FRBRThis of the specific FRBR level.
   *
   * @return An Eli
   */
  public abstract Eli getEli();

  /**
   * Returns the URI as {@link String} from the FRBRuri of the specific FRBR level.
   *
   * @return An URI
   */
  public URI getURI() {
    return URI.create(
      NodeParser.getValueFromMandatoryNodeFromExpression("./FRBRuri/@value", element)
    );
  }

  /**
   * Updates the URI of a Norm
   *
   * @param uri - the new URI
   */
  public void setURI(final URI uri) {
    var optionalFRBRuri = NodeParser.getElementFromExpression("./FRBRuri", element);

    if (optionalFRBRuri.isEmpty()) {
      var newFRBRuri = NodeCreator.createElement(Namespace.INHALTSDATEN, "FRBRuri", element);
      newFRBRuri.setAttribute("GUID", UUID.randomUUID().toString());
      newFRBRuri.setAttribute(VALUE_ATTIBUTE, uri.toString());
      return;
    }

    optionalFRBRuri.get().setAttribute(VALUE_ATTIBUTE, uri.toString());
  }

  /**
   * Returns a FBRDate as {@link LocalDate} from the FRBR level.
   *
   * @return The FBRDate
   */
  public String getFBRDate() {
    return NodeParser.getValueFromMandatoryNodeFromExpression("./FRBRdate/@date", element);
  }

  /**
   * Updates both the date and the name attibutes of FBRDate
   *
   * @param date - the new date
   * @param name - the new name
   */
  public void setFBRDate(final String date, final String name) {
    final Element dateElement = NodeParser.getMandatoryElementFromExpression(
      "./FRBRdate",
      this.element
    );
    dateElement.setAttribute("date", date);
    dateElement.setAttribute("name", name);
  }
}
