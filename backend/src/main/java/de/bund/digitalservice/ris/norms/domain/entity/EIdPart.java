package de.bund.digitalservice.ris.norms.domain.entity;

/**
 * Represents a part of a LDML.de eId.
 *
 * @param value the part of the eId as {@link String}
 */
public record EIdPart(String value) {
  public EIdPart(String type, String position) {
    this(type + "-" + position);
  }

  public String getType() {
    return value.split("-")[0];
  }

  public String getPosition() {
    return value.split("-")[1];
  }

  @Override
  public String toString() {
    return value;
  }
}
