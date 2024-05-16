package de.bund.digitalservice.ris.norms.domain.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/** Represents a part of a LDML.de eId. */
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode
public class EIdPart {
  private final String value;

  @Override
  public String toString() {
    return value;
  }

  public EIdPart(String type, String position) {
    this.value = type + "-" + position;
  }

  public String getType() {
    return value.split("-")[0];
  }

  public String getPosition() {
    return value.split("-")[1];
  }
}
