package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Class representing an Verkuendung entity. This class is annotated with Lombok annotations for
 * generating getters, setters, constructors, and builder methods.
 */
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@Data
public class Verkuendung {

  private NormExpressionEli eli;

  private Instant importTimestamp;
}
