package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;

/**
 * Interface representing a port for figuring out if a {@link Norm} exists.
 */
public interface CheckNormExistencePort {
  /**
   * Figures out if a norm based on the provided ELI exists.
   * For a {@link NormWorkEli} it checks if any expression of that work exists.
   * For a {@link NormExpressionEli} if any manifestation of that expression exists.
   * And for a {@link NormManifestationEli} if that specific manifestation exists.
   *
   * @param options The options specifying the ELI to identify the norm.
   * @return True if a norm for that eli exists, or false otherwise.
   */
  Boolean checkNormExistence(final Options options);

  /**
   * A record representing the options for identifying the norm.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the norm.
   */
  record Options(NormEli eli) {}
}
