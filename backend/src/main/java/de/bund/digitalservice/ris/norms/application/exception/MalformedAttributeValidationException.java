package de.bund.digitalservice.ris.norms.application.exception;

import lombok.Getter;

/** This exception indicates that there was a validation error with a malformed attribute */
@Getter
public class MalformedAttributeValidationException extends RuntimeException
    implements NormsAppException {

  private final String eli;
  private final String eId;
  private final String attributeName;
  private final String attributeValue;

  public MalformedAttributeValidationException(
      final String eli, final String eId, final String attributeName, final String attributeValue) {
    super(
        "In the norm %s the element with eId %s has the attribute %s with a malformed value %s"
            .formatted(eli, eId, attributeName, attributeValue));

    this.eli = eli;
    this.eId = eId;
    this.attributeName = attributeName;
    this.attributeValue = attributeValue;
  }
}
