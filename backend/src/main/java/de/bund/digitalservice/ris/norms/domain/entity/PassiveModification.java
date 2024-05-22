package de.bund.digitalservice.ris.norms.domain.entity;

import java.util.Optional;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/** Class representing a passive modification. */
@Getter
@SuperBuilder(toBuilder = true)
public class PassiveModification extends Modification {

  /**
   * Constructor for PassiveModification.
   *
   * @param node the Node object to initialize
   */
  public PassiveModification(Node node) {
    super(node);
  }

  /**
   * Returns the source eli as {@link String}.
   *
   * @return The source eli of the passive modification
   */
  public Optional<String> getSourceEli() {
    return this.getSourceHref().flatMap(Href::getEli);
  }

  /**
   * Returns the source eid as {@link String}.
   *
   * @return The source eid of the passive modification
   */
  public Optional<String> getSourceEid() {
    return this.getSourceHref().flatMap(Href::getEId);
  }
}
