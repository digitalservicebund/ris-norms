package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormEli;
import java.util.UUID;

/**
 * Interface representing the use case for loading a {@link Norm}.
 */
public interface LoadNormUseCase {
  /**
   * Retrieves a norm based on the provided options.
   *
   * @param options The options containing the ELI (European Legislation Identifier) or GUID of the norm.
   * @return The loaded {@link Norm}
   * @throws NormNotFoundException if {@link Norm} could not be found
   */
  Norm loadNorm(Options options);

  /**
   * The options for loading a norm.
   */
  sealed interface Options permits EliOptions, GuidOptions {}

  /**
   * The options for loading a norm based on an eli.
   * @param eli the eli to identify the norm
   */
  record EliOptions(NormEli eli) implements Options {}

  /**
   * The options for loading a norm based on a guid.
   * @param guid the guid to identify the norm expression
   */
  record GuidOptions(UUID guid) implements Options {}
}
