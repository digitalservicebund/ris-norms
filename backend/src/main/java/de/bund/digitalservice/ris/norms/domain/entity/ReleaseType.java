package de.bund.digitalservice.ris.norms.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

/**
 * Enum representing the type of a release.
 */
@Getter
public enum ReleaseType {
  NOT_RELEASED(""),
  PRAETEXT_RELEASED("praetext"),
  VOLLDOKUMENTATION_RELEASED("volldokumentation");

  final String value;

  ReleaseType(String value) {
    this.value = value;
  }

  /**
   * Get the enum constant for the given value.
   *
   * @param value the value to get the enum constant for.
   * @return the enum constant for the given value.
   */
  @JsonCreator
  public static ReleaseType fromString(String value) {
    for (ReleaseType releaseType : ReleaseType.values()) {
      if (releaseType.value.equals(value)) {
        return releaseType;
      }
    }
    throw new IllegalArgumentException("No enum constant for ReleaseType with value " + value);
  }
}
