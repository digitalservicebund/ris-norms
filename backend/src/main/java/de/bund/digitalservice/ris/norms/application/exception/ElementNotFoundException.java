package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import lombok.Getter;

/** Indicates that an element was not found based on the provided parameters. */
@Getter
public class ElementNotFoundException extends RuntimeException implements NormsAppException {

  private final String eli;
  private final String eid;

  public ElementNotFoundException(final String eli, final String eid) {
    super("Element with eid %s does not exist in norm with eli %s".formatted(eli, eid));
    this.eli = eli;
    this.eid = eid;
  }
}
