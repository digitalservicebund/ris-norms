package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Release;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;

/**
 * Interface representing the use case for releasing a new expression of a {@link Norm}.
 */
public interface ReleaseNormExpressionUseCase {
  /**
   * Releases a new expression of a {@link Norm} based on the provided options.
   *
   * @param options The options specifying the {@link Norm} to be released.
   * @return The created {@link Release}.
   */
  Release releaseNormExpression(Options options);

  /**
   * A record representing the options for releasing a new expression of a {@link Norm}.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the {@link Norm}.
   */
  record Options(NormExpressionEli eli) {}
}
