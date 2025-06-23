package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;

/**
 * Port for updating only the publish state of a norm
 */
public interface UpdateNormPublishStatePort {
  /**
   * Updates a {@link Norm}s publish state based on the provided data in the options.
   * @param options The options specifying the update.
   */
  void updateNormPublishState(final UpdateNormPublishStatePort.Options options);

  /**
   * A record representing the options for updating a norms publish state.
   * @param normManifestationEli the eli to identify the norm
   * @param publishState the new publish state
   */
  record Options(NormManifestationEli normManifestationEli, NormPublishState publishState) {}
}
