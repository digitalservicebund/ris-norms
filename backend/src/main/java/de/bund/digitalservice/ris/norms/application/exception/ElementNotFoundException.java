package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.application.service.ElementService;

/** Indicates that an element was not found based on the provided parameters. */
public class ElementNotFoundException extends RuntimeException {
  public ElementNotFoundException(
      final String eli, final String eid, final ElementService.ElementType type) {
    super(
        "Element of type %s with eid %s does not exist in norm with eli %s"
            .formatted(type.toString(), eli, eid));
  }

  public ElementNotFoundException(final String eli, final String eid) {
    super("Element with eid %s does not exist in norm with eli %s".formatted(eli, eid));
  }
}
