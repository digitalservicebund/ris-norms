package de.bund.digitalservice.ris.norms.domain.entity;

import java.time.Instant;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * A release of norms to the bundesrechtsdatenbank.
 */
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@Data
public class Release {

  private Instant releasedAt;
  private Collection<Norm> publishedNorms;
}
