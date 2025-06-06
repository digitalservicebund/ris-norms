package de.bund.digitalservice.ris.norms.domain.entity.metadata.rahmen;

import de.bund.digitalservice.ris.norms.domain.entity.Meta;
import de.bund.digitalservice.ris.norms.domain.entity.Metadata;
import de.bund.digitalservice.ris.norms.domain.entity.Rechtsetzungsdokument;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Metadata for the "Rahmen" of a norm that is saved on a {@link Rechtsetzungsdokument}.
 */
public final class RechtsetzungsdokumentRahmenMetadata extends DokumentRahmenMetadata {

  public RechtsetzungsdokumentRahmenMetadata(@NonNull Meta meta) {
    super(meta);
  }

  public Optional<String> getFna() {
    return meta.getProprietary().flatMap(p -> p.getMetadataValue(Metadata.FNA));
  }

  /**
   * Sets the "Sachgebiet" metadatum
   * @param fna the new value for the metadatum or null to remove it
   */
  public void setFna(@Nullable String fna) {
    meta.getOrCreateProprietary().setMetadataValue(Metadata.FNA, fna);
  }

  /**
   * Gets the ressort metadatum
   * @return the value of the metadatum or null if it isn't set
   */
  public Optional<String> getRessort() {
    return meta
      .getProprietary()
      .flatMap(p -> p.getRessort(meta.getFRBRExpression().getEli().getPointInTime()));
  }

  /**
   * Sets the ressort metadatum
   * @param ressort the new value for the metadatum or null to remove it
   */
  public void setRessort(@Nullable String ressort) {
    meta
      .getOrCreateProprietary()
      .setRessort(ressort, meta.getFRBRExpression().getEli().getPointInTime());
  }
}
