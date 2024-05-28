package de.bund.digitalservice.ris.norms.domain.entity;

import java.util.Arrays;
import java.util.List;

/**
 * Represents an LDML.de eId.
 *
 * @param value the eId as {@link String}
 */
public record EId(String value) {
  public List<EIdPart> getParts() {
    return Arrays.stream(value.split("_")).map(EIdPart::new).toList();
  }

  /**
   * Creates a new eId that includes all the parts of the current eId and the new part at the end.
   *
   * @param part the new part to append to the eId
   * @return a new eId
   */
  public EId addPart(EIdPart part) {
    return new EId(value + "_" + part);
  }

  @Override
  public String toString() {
    return value;
  }
}
