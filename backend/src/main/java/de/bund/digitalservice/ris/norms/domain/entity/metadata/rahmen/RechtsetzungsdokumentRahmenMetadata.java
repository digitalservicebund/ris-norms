package de.bund.digitalservice.ris.norms.domain.entity.metadata.rahmen;

import de.bund.digitalservice.ris.norms.domain.entity.Meta;
import de.bund.digitalservice.ris.norms.domain.entity.Rechtsetzungsdokument;
import org.jspecify.annotations.NonNull;

/**
 * Metadata for the "Rahmen" of a norm that is saved on a {@link Rechtsetzungsdokument}.
 */
public final class RechtsetzungsdokumentRahmenMetadata extends DokumentRahmenMetadata {

  public RechtsetzungsdokumentRahmenMetadata(@NonNull Meta meta) {
    super(meta);
  }
}
