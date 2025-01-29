package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import java.util.List;

/**
 * Interface representing a port for loading the {@link NormManifestationEli} of all {@link Norm} based their publish state.
 */
public interface LoadNormManifestationElisByPublishStatePort {
  /**
   * Loads all {@link Norm} manifestation elis based on its publish state.
   *
   * @param command The command specifying the publish state
   * @return An list of all manifestation elis of norms with that publish state
   */
  List<NormManifestationEli> loadNormManifestationElisByPublishState(final Command command);

  /**
   * A record representing the command for loading a norm manifestation elis by its publish state in a paginated way
   *
   * @param publishState the state of the publish, indicating whether the norm is queued for publishing, published, etc.
   */
  record Command(NormPublishState publishState) {}
}
