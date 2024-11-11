package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import java.util.List;
import java.util.UUID;

/**
 * Interface representing a port for loading all {@link Norm}s based their publish state.
 */
public interface LoadNormIdsByPublishStatePort {
  /**
   * Loads all {@link Norm} ids based on its publish state.
   *
   * @param command The command specifying the publish state
   * @return An list of all {@link java.util.UUID}s of norms with that publish state
   */
  List<UUID> loadNormIdsByPublishState(final Command command);

  /**
   * A record representing the command for loading a norm id by its publish state in a paginated way
   *
   * @param publishState the state of the publish, indicating whether the norm is queued for publishing, published, etc.
   */
  record Command(NormPublishState publishState) {}
}
