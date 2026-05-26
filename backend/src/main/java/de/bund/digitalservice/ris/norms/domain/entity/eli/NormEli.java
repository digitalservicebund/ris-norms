package de.bund.digitalservice.ris.norms.domain.entity.eli;

/**
 * A european legislation identifier for a norm.
 */
public sealed interface NormEli permits NormWorkEli, NormExpressionEli, NormManifestationEli {}
