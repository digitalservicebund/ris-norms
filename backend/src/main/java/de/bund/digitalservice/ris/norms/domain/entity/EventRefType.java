package de.bund.digitalservice.ris.norms.domain.entity;

import lombok.Getter;

/**
 * Values for the @type property of an akn:eventRef element. See: LDML.de 9.2.36.2
 * typeLiterals.ereignisReferenz
 */
@Getter
public enum EventRefType {
  GENERATION("generation"),
  AMENDMENT("amendment"),
  REPEAL("repeal");

  final String value;

  EventRefType(String value) {
    this.value = value;
  }
}
