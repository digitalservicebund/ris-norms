package de.bund.digitalservice.ris.norms.domain.entity.eli;

import java.net.URI;

/**
 * A european legislation identifier for a dokument of a norm.
 */
public sealed interface DokumentEli
  permits DokumentWorkEli, DokumentExpressionEli, DokumentManifestationEli {
  /**
   * Create the URI for the eli to be used in e.g. href attributes.
   *
   * @return the URI for the eli
   */
  URI toUri();
}
