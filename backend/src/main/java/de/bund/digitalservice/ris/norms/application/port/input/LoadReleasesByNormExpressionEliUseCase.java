package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Release;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.util.List;

/**
 * Interface representing the use case for loading {@link Release}s of a specific norm expression.
 */
public interface LoadReleasesByNormExpressionEliUseCase {
  /**
   * Loads all {@link Release}s associated with the norm expression.
   *
   * @param options Options for loading the releases
   * @return A {@link List} of {@link Release} objects, which may be empty if no {@link
   *     Release}s are found.
   */
  List<Release> loadReleasesByNormExpressionEli(Options options);

  /**
   * A record representing the options for loading {@link Release}s of a specific norm expression.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the norm expression.
   */
  record Options(NormExpressionEli eli) {}
}
