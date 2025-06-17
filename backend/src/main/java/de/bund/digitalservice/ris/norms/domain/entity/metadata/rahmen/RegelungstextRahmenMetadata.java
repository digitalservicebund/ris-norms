package de.bund.digitalservice.ris.norms.domain.entity.metadata.rahmen;

import de.bund.digitalservice.ris.norms.domain.entity.Meta;
import de.bund.digitalservice.ris.norms.domain.entity.Metadata;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Metadata for the "Rahmen" of a norm that are saved in a {@link Regelungstext}.
 */
public final class RegelungstextRahmenMetadata extends DokumentRahmenMetadata {

  public RegelungstextRahmenMetadata(@NonNull Meta meta) {
    super(meta);
  }

  public Optional<String> getTyp() {
    return meta.getProprietary().flatMap(p -> p.getMetadataValue(Metadata.TYP));
  }

  public void setTyp(@Nullable String typ) {
    meta.getOrCreateProprietary().setMetadataValue(Metadata.TYP, typ);
  }

  public Optional<String> getSubtyp() {
    return meta.getProprietary().flatMap(p -> p.getMetadataValue(Metadata.SUBTYP));
  }

  public void setSubtyp(@Nullable String subtyp) {
    meta.getOrCreateProprietary().setMetadataValue(Metadata.SUBTYP, subtyp);
  }

  public Optional<String> getBezeichnungInVorlage() {
    return meta.getProprietary().flatMap(p -> p.getMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE));
  }

  /**
   * Set the "Bezeichnung gemäß Vorlage" Metadata
   * @param bezeichnungInVorlage the new value for the metadatum or null to remove it
   */
  public void setBezeichnungInVorlage(@Nullable String bezeichnungInVorlage) {
    meta
      .getOrCreateProprietary()
      .setMetadataValue(Metadata.BEZEICHNUNG_IN_VORLAGE, bezeichnungInVorlage);
  }

  public Optional<String> getArtDerNorm() {
    return meta.getProprietary().flatMap(p -> p.getMetadataValue(Metadata.ART_DER_NORM));
  }

  /**
   * Set the "Art der Norm" metadatum
   * @param artDerNorm the new value for the metadatum or null to remove it
   */
  public void setArtDerNorm(@Nullable String artDerNorm) {
    meta.getOrCreateProprietary().setMetadataValue(Metadata.ART_DER_NORM, artDerNorm);
  }

  public Optional<String> getStaat() {
    return meta.getProprietary().flatMap(p -> p.getMetadataValue(Metadata.STAAT));
  }

  /**
   * Set the "Normgeber - Staat" metadatum
   * @param staat the new value for the metadatum or null to remove it
   */
  public void setStaat(@Nullable String staat) {
    meta.getOrCreateProprietary().setMetadataValue(Metadata.STAAT, staat);
  }

  public Optional<String> getBeschliessendesOrgan() {
    return meta.getProprietary().flatMap(p -> p.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN));
  }

  /**
   * Set the "Normgeber - beschließendes Organ" metadatum
   * @param beschliessendesOrgan the new value for the metadatum or null to remove it
   */
  public void setBeschliessendesOrgan(@Nullable String beschliessendesOrgan) {
    meta
      .getOrCreateProprietary()
      .setMetadataValue(Metadata.BESCHLIESSENDES_ORGAN, beschliessendesOrgan);
  }

  public Optional<Boolean> getQualifizierteMehrheit() {
    return meta
      .getProprietary()
      .flatMap(p -> p.getMetadataValue(Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR))
      .map(Boolean::parseBoolean);
  }

  /**
   * Set the "Normgeber - Beschlussf. qual. Mehrheit" metadatum
   * @param qualifizierteMehrheit the new value for the metadatum or null to remove it
   */
  public void setQualifizierterMehrheit(@Nullable Boolean qualifizierteMehrheit) {
    meta
      .getOrCreateProprietary()
      .setMetadataValue(
        Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR,
        qualifizierteMehrheit != null ? qualifizierteMehrheit.toString() : ""
      );
  }

  public Optional<String> getOrganisationsEinheit() {
    return meta.getProprietary().flatMap(p -> p.getMetadataValue(Metadata.ORGANISATIONS_EINHEIT));
  }

  /**
   * Sets the "Federführung - Organisationseinheit" metadatum
   * @param organisationsEinheit the new value for the metadatum or null to remove it
   */
  public void setOrganisationsEinheit(@Nullable String organisationsEinheit) {
    meta
      .getOrCreateProprietary()
      .setMetadataValue(Metadata.ORGANISATIONS_EINHEIT, organisationsEinheit);
  }
}
