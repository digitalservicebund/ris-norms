package de.bund.digitalservice.ris.norms.domain.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

  @Builder.Default
  private List<Norm> publishedNorms = new ArrayList<>();
}
