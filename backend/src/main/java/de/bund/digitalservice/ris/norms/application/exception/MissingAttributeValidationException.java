package de.bund.digitalservice.ris.norms.application.exception;

import lombok.Getter;

/** This exception indicates that there was a validation error. */
@Getter
public class MissingAttributeValidationException extends RuntimeException
    implements NormsAppException {

  private final String eli;
  private final String eId;
  private final String attributeName;

  public MissingAttributeValidationException(String eli, String eId, String attributeName) {
    super(
        "In the norm %s the element with eId %s is missing the attribute %s"
            .formatted(eli, eId, attributeName));

    this.eli = eli;
    this.eId = eId;
    this.attributeName = attributeName;
  }
}
