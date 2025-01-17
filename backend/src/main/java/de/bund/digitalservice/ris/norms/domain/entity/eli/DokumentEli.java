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

  /**
   * Create a {@link NormEli} that contains the parts of this eli
   *
   * @return a norm eli
   */
  NormEli asNormEli();
}
