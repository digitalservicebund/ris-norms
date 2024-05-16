package de.bund.digitalservice.ris.norms.domain.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/** Represents a part of a LDML.de eId. */
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode
public class EIdPart {
  private final String eIdPart;

  public String getType() {
    return eIdPart.split("-")[0];
  }

  public String getPosition() {
    return eIdPart.split("-")[1];
  }
}
