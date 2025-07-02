package de.bund.digitalservice.ris.norms.domain.entity.eid;

/**
 * Represents a part of a LDML.de eId.
 *
 * @param value the part of the eId as {@link String}
 */
public record EIdPart(String value) {
  public EIdPart(String type, EIdPosition position) {
    this(type + "-" + position.toString());
  }

  public String getType() {
    return value.split("-")[0];
  }

  /**
   * Get the position information
   * @return the position information
   */
  public EIdPosition getPosition() {
    var position = value.split("-")[1];

    if (position.startsWith(OrdinalEIdPosition.PREFIX)) {
      return OrdinalEIdPosition.fromString(position);
    }

    if (position.startsWith(ZaehlbezeichnungsbasierteEIdPosition.PREFIX)) {
      return ZaehlbezeichnungsbasierteEIdPosition.fromString(position);
    }

    throw new IllegalStateException(
      "EId position doesn't start with one of the expected prefixes (either n or z): %s".formatted(
        position
      )
    );
  }

  @Override
  public String toString() {
    return value;
  }
}
