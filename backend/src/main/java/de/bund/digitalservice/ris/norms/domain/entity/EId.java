package de.bund.digitalservice.ris.norms.domain.entity;

import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/** Represents an LDML.de eId. */
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode
public class EId {
  private final String eId;

  public List<EIdPart> getParts() {
    return Arrays.stream(eId.split("_")).map(EIdPart::new).toList();
  }
}
