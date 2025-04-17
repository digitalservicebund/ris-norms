package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Release;

/**
 * Interface representing a port for saving a {@link Release}.
 */
public interface SaveReleasePort {
  /**
   * Saves a {@link Release}.
   *
   * @param command The command specifying the release.
   * @return the {@link Release}.
   */
  Release saveRelease(final Command command);

  /**
   * A record representing the command for saving a release.
   *
   * @param release The release to be saved to the verkuendung
   */
  record Command(Release release) {}
}
