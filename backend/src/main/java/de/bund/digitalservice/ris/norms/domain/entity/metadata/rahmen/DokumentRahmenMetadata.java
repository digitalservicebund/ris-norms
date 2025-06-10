package de.bund.digitalservice.ris.norms.domain.entity.metadata.rahmen;

import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.Meta;
import org.jspecify.annotations.NonNull;

/**
 * Metadata for the "Rahmen" of a norm that is stored in all {@link Dokument}e.
 *
 * <p>
 *   Currently doesn't provide any metadata on its own. It is still needed so we can provide a
 *   {@link DokumentRahmenMetadata} object for {@link Dokument}e that do not provide parts of the {@link RahmenMetadata}
 *   to keep the API the same for all {@link Dokument}e.
 * </p>
 */
public sealed class DokumentRahmenMetadata
  permits RechtsetzungsdokumentRahmenMetadata, RegelungstextRahmenMetadata {

  protected final Meta meta;

  public DokumentRahmenMetadata(@NonNull Meta meta) {
    this.meta = meta;
  }
}
