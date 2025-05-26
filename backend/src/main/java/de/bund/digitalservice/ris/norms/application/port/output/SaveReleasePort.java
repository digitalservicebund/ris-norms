package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Release;

/**
 * Interface representing a port for saving a {@link Release}.
 */
public interface SaveReleasePort {
  /**
   * Saves a {@link Release}.
   *
   * @param options The options specifying the release.
   * @return the {@link Release}.
   */
  Release saveRelease(final Options options);

  /**
   * A record representing the options for saving a release.
   *
   * @param release The release to be saved to the verkuendung
   */
  record Options(Release release) {}
}
