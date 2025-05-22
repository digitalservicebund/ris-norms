package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;

/**
 * Interface representing a port for deleting a {@link Norm} based on the ELI (European Legislation
 * Identifier).
 */
public interface DeleteNormPort {
  /**
   * Delete a {@link Norm} based on the provided ELI specified in the options.
   *
   * @param options The options specifying the ELI to identify the norm to be deleted.
   */
  void deleteNorm(final Options options);

  /**
   * A record representing the options for loading a norm.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the norm.
   * @param publishState The publish-state that the norm that should be deleted must currently have. If it differs from the current state no norms are deleted.
   */
  record Options(NormManifestationEli eli, NormPublishState publishState) {}
}
