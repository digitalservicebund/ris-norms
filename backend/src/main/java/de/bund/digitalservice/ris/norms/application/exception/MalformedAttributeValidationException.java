package de.bund.digitalservice.ris.norms.application.exception;

import lombok.Getter;

/** This exception indicates that there was a validation error. */
@Getter
public class MalformedAttributeValidationException extends RuntimeException
    implements NormsAppException {

  private final String eli;
  private final String eId;
  private final String attributeName;
  private final String attributeValue;

  public MalformedAttributeValidationException(
      String eli, String eId, String attributeName, String attributeValue) {
    super(
        "In the norm %s the element with eId %s has a malformed attribute value %s on attribute %s"
            .formatted(eli, eId, attributeValue, attributeName));

    this.eli = eli;
    this.eId = eId;
    this.attributeName = attributeName;
    this.attributeValue = attributeValue;
  }
}
