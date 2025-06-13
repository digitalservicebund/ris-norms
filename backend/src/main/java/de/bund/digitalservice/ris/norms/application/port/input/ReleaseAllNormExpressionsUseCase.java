package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Release;
import de.bund.digitalservice.ris.norms.domain.entity.ReleaseType;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import org.jspecify.annotations.NullMarked;

/**
 * Interface representing the use case for releasing a {@link Norm}.
 */
@NullMarked
public interface ReleaseAllNormExpressionsUseCase {
  /**
   * Releases all expressions of a {@link Norm} based on the provided options.
   *
   * @param options The options specifying the {@link Norm} to be released.
   * @return The created {@link Release}.
   */
  Release release(Options options);

  /**
   * A record representing the options for releasing all expressions of a {@link Norm}.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the {@link Norm}.
   * @param releaseType The type of the release.
   */
  record Options(NormWorkEli eli, ReleaseType releaseType) {}
}
