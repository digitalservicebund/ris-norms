package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Release;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.util.List;

/**
 * Interface representing a port for loading {@link Release}s of a specific norm expression.
 */
public interface LoadReleasesByNormExpressionEliPort {
  /**
   * Loads {@link Release}s of a specific norm expression.
   *
   * @param options The options specifying the ELI to identify the norm.
   * @return A {@link List} of {@link Release}s, which may be empty if no {@link Release} is found.
   */
  List<Release> loadReleasesByNormExpressionEli(final Options options);

  /**
   * A record representing the options for loading releases of a norm.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the norm.
   */
  record Options(NormExpressionEli eli) {}
}
