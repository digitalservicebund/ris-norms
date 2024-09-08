package de.bund.digitalservice.ris.norms.domain.entity;

import lombok.Getter;

/** Enum with a list of attributes of the XML nodes in LDML.de */
@Getter
public enum Attribute {
  REFERS_TO("refersTo"),
  HREF("href"),
  EID("eId");

  private final String value;

  Attribute(String value) {
    this.value = value;
  }
}
