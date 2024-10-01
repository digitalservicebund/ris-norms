package de.bund.digitalservice.ris.norms.domain.entity.eli;

import java.net.URI;

/**
 * A european legislation identifier.
 */
public sealed interface Eli permits WorkEli, ExpressionEli, ManifestationEli {
  /**
   * Create the URI for the eli to be used in e.g. href attributes.
   *
   * @return the URI for the eli
   */
  URI toUri();
}
