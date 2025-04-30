package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Class representing the norms-application-only metadata
 */
@Getter
public class CustomModsMetadata {

  private final Element element;

  public CustomModsMetadata(Element element) {
    this.element = element;
  }

  /**
   * Get a list of all the norm expressions that have been amended bfy this norm
   *
   * @return all norm expression elis amended by this norm
   */
  public List<NormExpressionEli> getAmendedNormExpressionElis() {
    return NodeParser
      .getNodesFromExpression("./amended-norm-expressions/norm-expression/text()", element)
      .stream()
      .map(Node::getNodeValue)
      .map(NormExpressionEli::fromString)
      .toList();
  }

  /**
   * Get a list of the time boundaries including mandatory "id" and "art" attributes. Current does not support "unbestimmt" cases
   *
   * @return a list of {@link Zeitgrenze}
   */
  public List<Zeitgrenze> getZeitgrenzen() {
    return getGeltungszeiten().stream().flatMap(Geltungszeiten::stream).toList();
  }

  /**
   * Get the collection of {@link Zeitgrenze}n
   * @return the collection of {@link Zeitgrenze}n or empty if none exist.
   */
  public Optional<Geltungszeiten> getGeltungszeiten() {
    return NodeParser
      .getElementFromExpression("./geltungszeiten", getElement())
      .map(geltungszeitenElement -> new Geltungszeiten(geltungszeitenElement, this));
  }

  /**
   * Get the collection of {@link Zeitgrenze}n or creates it if it doesn't exist yet.
   * @return the collection of {@link Zeitgrenze}n
   */
  public Geltungszeiten getOrCreateGeltungszeiten() {
    return getGeltungszeiten().orElseGet(() -> Geltungszeiten.createAndAppend(this));
  }

  /**
   * Get the {@link ZielnormReferences} element, if it exists.
   * @return {@link ZielnormReferences} or empty if none exist
   */
  public Optional<ZielnormReferences> getZielnormenReferences() {
    return NodeParser
      .getElementFromExpression("./zielnorm-references", getElement())
      .map(ZielnormReferences::new);
  }

  /**
   * Get the {@link ZielnormReferences} element or create a new one if no such element exists
   * @return the {@link ZielnormReferences} of the norm
   */
  public ZielnormReferences getOrCreateZielnormenReferences() {
    return getZielnormenReferences()
      .orElseGet(() -> ZielnormReferences.createAndAppend(getElement()));
  }

  /**
   * Remove the {@link ZielnormReferences} element
   */
  public void removeZielnormenReferences() {
    getZielnormenReferences()
      .ifPresent(zielnormReferences -> getElement().removeChild(zielnormReferences.getElement()));
  }
}
