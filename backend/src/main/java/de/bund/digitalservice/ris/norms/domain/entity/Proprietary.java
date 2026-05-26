package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.w3c.dom.Element;

/**
 * Encapsulates metadata that is found in the proprietary section of the document. This includes
 * both metadata that is proprietary to the LDML.de standard and to DigitalService.
 */
@Getter
@AllArgsConstructor
public class Proprietary {

  private final Element element;

  /**
   * Retrieves the text content of the XML node for the given {@link Metadata}.
   * @param metadata - the given {@link Metadata}
   * @return the value or an optional empty
   */
  public Optional<String> getMetadataValue(final Metadata metadata) {
    return getMetadataParent(metadata.getNamespace()).flatMap(e ->
      NodeParser.getValueFromExpression(metadata.getXpath(), e)
    );
  }

  /**
   * Gets the date the expression enters into force
   * @return the date of the entry into force
   */
  public Optional<LocalDate> getInkrafttreteDatum() {
    return getMetadataValue(Metadata.INKRAFT).map(LocalDate::parse);
  }

  /**
   * Gets the date the expression is no longer in force
   * @return the date of the expiry
   */
  public Optional<LocalDate> getAusserkrafttreteDatum() {
    return getMetadataValue(Metadata.AUSSERKRAFT).map(LocalDate::parse);
  }

  /**
   * Returns the metadata parent, if present
   * @param namespace of the metadata parent
   * @return an optional element
   */
  public Optional<Element> getMetadataParent(final Namespace namespace) {
    return NodeParser.getElementFromExpression("./" + getXpathExpression(namespace), element);
  }

  private String getXpathExpression(final Namespace namespace) {
    return "Q{" + namespace.getNamespaceUri() + "}legalDocML.de_metadaten";
  }
}
