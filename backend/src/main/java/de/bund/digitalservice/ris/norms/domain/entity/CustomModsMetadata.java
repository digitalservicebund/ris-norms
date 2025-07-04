package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Optional;
import lombok.Getter;
import org.w3c.dom.Element;

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
   * Get the collection norm expressions that have been amended by this norm
   * @return all norm expression elis amended by this norm or empty if none exist
   */
  public Optional<AmendedNormExpressions> getAmendedNormExpressions() {
    return NodeParser.getElementFromExpression(
      "./%s".formatted(AmendedNormExpressions.TAG_NAME),
      getElement()
    ).map(AmendedNormExpressions::new);
  }

  /**
   * Get the collection norm expressions that have been amended by this norm
   * @return all norm expression elis amended by this norm or a Set that can be used to add new once
   */
  public AmendedNormExpressions getOrCreateAmendedNormExpressions() {
    return getAmendedNormExpressions().orElseGet(() ->
      AmendedNormExpressions.createAndAppend(getElement())
    );
  }

  /**
   * Get the collection of {@link Zeitgrenze}n
   * @return the collection of {@link Zeitgrenze}n or empty if none exist.
   */
  public Optional<Geltungszeiten> getGeltungszeiten() {
    return NodeParser.getElementFromExpression("./geltungszeiten", getElement()).map(
      geltungszeitenElement -> new Geltungszeiten(geltungszeitenElement, this::isZeitgrenzeInUse)
    );
  }

  /**
   * Get the collection of {@link Zeitgrenze}n or creates it if it doesn't exist yet.
   * @return the collection of {@link Zeitgrenze}n
   */
  public Geltungszeiten getOrCreateGeltungszeiten() {
    return getGeltungszeiten().orElseGet(() ->
      Geltungszeiten.createAndAppend(this.getElement(), this::isZeitgrenzeInUse)
    );
  }

  /**
   * Remove the {@link Geltungszeiten} element
   */
  public void removeGeltungszeiten() {
    getGeltungszeiten().ifPresent(geltungszeiten ->
      getElement().removeChild(geltungszeiten.getElement())
    );
  }

  /**
   * Is the given {@link Zeitgrenze} currently in use?
   * @param zeitgrenze the {@link Zeitgrenze} to check
   * @return true if it is in use, false otherwise
   */
  public boolean isZeitgrenzeInUse(Zeitgrenze zeitgrenze) {
    return getZielnormenReferences()
      .stream()
      .flatMap(ZielnormReferences::stream)
      .anyMatch(zielnormReference ->
        zielnormReference.getGeltungszeit().equals(zeitgrenze.getId())
      );
  }

  /**
   * Get the {@link ZielnormReferences} element, if it exists.
   * @return {@link ZielnormReferences} or empty if none exist
   */
  public Optional<ZielnormReferences> getZielnormenReferences() {
    return NodeParser.getElementFromExpression("./zielnorm-references", getElement()).map(
      ZielnormReferences::new
    );
  }

  /**
   * Get the {@link ZielnormReferences} element or create a new one if no such element exists
   * @return the {@link ZielnormReferences} of the norm
   */
  public ZielnormReferences getOrCreateZielnormenReferences() {
    return getZielnormenReferences().orElseGet(() ->
      ZielnormReferences.createAndAppend(getElement())
    );
  }

  /**
   * Remove the {@link ZielnormReferences} element
   */
  public void removeZielnormenReferences() {
    getZielnormenReferences().ifPresent(zielnormReferences ->
      getElement().removeChild(zielnormReferences.getElement())
    );
  }
}
