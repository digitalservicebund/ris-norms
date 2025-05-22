package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import java.util.List;

/**
 * Interface representing a port for loading the {@link NormExpressionEli}s of all expressions of a norm based on the {@link NormWorkEli}.
 */
public interface LoadNormExpressionElisPort {
  /**
   * Loads the {@link NormExpressionEli}s of all expressions of a norm.
   *
   * @param options The options specifying the ELI to identify the norm whose expression should be loaded.
   * @return A {@link List} containing the {@link NormExpressionEli}s.
   */
  List<NormExpressionEli> loadNormExpressionElis(final Options options);

  /**
   * A record representing the options for loading the {@link NormExpressionEli}s of all expressions of a norm.
   *
   * @param eli The work-level ELI (European Legislation Identifier) used to identify the norm.
   */
  record Options(NormWorkEli eli) {}
}
