package de.bund.digitalservice.ris.norms.domain.entity;

import java.util.Optional;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/** Class representing an active modification. */
@Getter
@SuperBuilder(toBuilder = true)
public class ActiveModification extends Modification {

  /**
   * Constructor for ActiveModification.
   *
   * @param node the Node object to initialize
   */
  public ActiveModification(Node node) {
    super(node);
  }

  /**
   * Returns the source eli as {@link String}.
   *
   * @return The source eli of the active modification
   */
  public Optional<String> getDestinationEli() {
    return this.getDestinationHref().map(Href::new).flatMap(Href::getEli);
  }

  /**
   * Returns the destination eid as {@link String}.
   *
   * @return The destination eid of the active modification
   */
  public Optional<String> getDestinationEid() {
    return this.getDestinationHref().map(Href::new).flatMap(Href::getEId);
  }

  /**
   * Returns the destination character range as {@link String}.
   *
   * @return The destination character range of the active modification
   */
  public Optional<String> getDestinationCharacterRange() {
    return this.getDestinationHref().map(Href::new).flatMap(Href::getCharacterRange);
  }

  /**
   * Returns the source eid as {@link String}.
   *
   * @return The source eid of the active modification
   */
  public Optional<String> getSourceEid() {
    return this.getSourceHref().map(Href::new).flatMap(Href::getEId);
  }
}
