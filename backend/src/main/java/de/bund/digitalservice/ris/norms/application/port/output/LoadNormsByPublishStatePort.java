package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import java.util.List;

/**
 * Interface representing a port for loading all {@link Norm}s based their publish state.
 */
public interface LoadNormsByPublishStatePort {
  /**
   * Loads all {@link Norm}s based on its publish state.
   *
   * @param command The command specifying the publish state
   * @return An list of all {@link Norm}s with that publish state
   */
  List<Norm> loadNormsByPublishState(final Command command);

  /**
   * A record representing the command for loading a norm by its publish state.
   *
   * @param publishState the state of the publish
   */
  record Command(NormPublishState publishState) {}
}
