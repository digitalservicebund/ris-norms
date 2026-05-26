package de.bund.digitalservice.ris.norms.domain.entity.eli;

/**
 * A european legislation identifier for a dokument of a norm.
 */
public sealed interface DokumentEli
  permits DokumentWorkEli, DokumentExpressionEli, DokumentManifestationEli {
  /**
   * Create a {@link NormEli} that contains the parts of this eli
   *
   * @return a norm eli
   */
  NormEli asNormEli();
}
