package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.WorkEli;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Optional;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Element;

/** Class representing the akn:FRBRWork */
@Getter
@SuperBuilder(toBuilder = true)
public class FRBRWork extends FRBR {

  public FRBRWork(final Element element) {
    super(element);
  }

  /**
   * Returns the Eli as {@link String} from the FRBRThis of the specific FRBR level.
   *
   * @return An Eli
   */
  public WorkEli getEli() {
    return WorkEli.fromString(
      NodeParser.getValueFromMandatoryNodeFromExpression("./FRBRthis/@value", this.getElement())
    );
  }

  /**
   * Updates the Eli of a Norm
   *
   * @param eli - the new ELI
   */
  public void setEli(final WorkEli eli) {
    NodeParser
      .getMandatoryElementFromExpression("./FRBRthis", this.getElement())
      .setAttribute("value", eli.toString());
  }

  /**
   * Returns a FRBRname as {@link String} from a the FRBRWork in a {@link Norm}.
   *
   * @return The FRBRname
   */
  public Optional<String> getFRBRname() {
    Optional<String> fRBRname = NodeParser.getValueFromExpression(
      "./FRBRname/@value",
      getElement()
    );

    return fRBRname.map(s ->
      s.replace("bgbl-1", "BGBl. I").replace("bgbl-2", "BGBl. II").replace("banz-at", "BAnz AT")
    );
  }

  /**
   * Set the value of the FRBRname element (this contains the agent publishing the norm)
   * @param name the name of the agent
   */
  public void setFRBRName(final String name) {
    final Element fRBRName = NodeParser.getMandatoryElementFromExpression(
      "./FRBRname",
      getElement()
    );
    fRBRName.setAttribute("value", name);
  }

  /**
   * Set the value of the FRBRauthor element (this contains the URI of the author of the document)
   * @param author the uri identifying the author of the document
   */
  public void setFRBRAuthor(final String author) {
    final Element fRBRAuthor = NodeParser.getMandatoryElementFromExpression(
      "./FRBRauthor",
      getElement()
    );
    fRBRAuthor.setAttribute("href", author);
  }

  /**
   * Returns a FRBRnumber as {@link String} from the FRBRWork in a {@link Norm}.
   *
   * @return The FRBRnumber
   */
  public Optional<String> getFRBRnumber() {
    return NodeParser.getValueFromExpression("./FRBRnumber/@value", getElement());
  }
}
