package de.bund.digitalservice.ris.norms.domain.entity.metadata.rahmen;

import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.jspecify.annotations.Nullable;

/**
 * Metadata for the "Rahmen" of a norm.
 * <p>
 *   The metadata is directly queried from and updated in the correct places of
 *   the metadata elements within the {@link Dokument}e of the {@link Norm}.
 * </p>
 */
public class RahmenMetadata {

  private final List<DokumentRahmenMetadata> dokumentRahmenMetadata;

  public RahmenMetadata(List<DokumentRahmenMetadata> dokumentRahmenMetadata) {
    this.dokumentRahmenMetadata = dokumentRahmenMetadata;
  }

  public Optional<String> getFna() {
    return getRegelungstextRahmenMetadata().findAny().flatMap(RegelungstextRahmenMetadata::getFna);
  }

  public void setFna(@Nullable String fna) {
    getRegelungstextRahmenMetadata().forEach(m -> m.setFna(fna));
  }

  public Optional<String> getTyp() {
    return getRegelungstextRahmenMetadata().findAny().flatMap(RegelungstextRahmenMetadata::getTyp);
  }

  public void setTyp(@Nullable String typ) {
    getRegelungstextRahmenMetadata().forEach(m -> m.setTyp(typ));
  }

  public Optional<String> getSubtyp() {
    return getRegelungstextRahmenMetadata()
      .findAny()
      .flatMap(RegelungstextRahmenMetadata::getSubtyp);
  }

  public void setSubtyp(@Nullable String subtyp) {
    getRegelungstextRahmenMetadata().forEach(m -> m.setSubtyp(subtyp));
  }

  public Optional<String> getBezeichnungInVorlage() {
    return getRegelungstextRahmenMetadata()
      .findAny()
      .flatMap(RegelungstextRahmenMetadata::getBezeichnungInVorlage);
  }

  public void setBezeichnungInVorlage(@Nullable String bezeichnungInVorlage) {
    getRegelungstextRahmenMetadata().forEach(m -> m.setBezeichnungInVorlage(bezeichnungInVorlage));
  }

  public Optional<String> getArtDerNorm() {
    return getRegelungstextRahmenMetadata()
      .findAny()
      .flatMap(RegelungstextRahmenMetadata::getArtDerNorm);
  }

  public void setArtDerNorm(@Nullable String artDerNorm) {
    getRegelungstextRahmenMetadata().forEach(m -> m.setArtDerNorm(artDerNorm));
  }

  public Optional<String> getStaat() {
    return getRegelungstextRahmenMetadata()
      .findAny()
      .flatMap(RegelungstextRahmenMetadata::getStaat);
  }

  public void setStaat(@Nullable String staat) {
    getRegelungstextRahmenMetadata().forEach(m -> m.setStaat(staat));
  }

  public Optional<String> getBeschliessendesOrgan() {
    return getRegelungstextRahmenMetadata()
      .findAny()
      .flatMap(RegelungstextRahmenMetadata::getBeschliessendesOrgan);
  }

  public void setBeschliessendesOrgan(@Nullable String beschliessendesOrgan) {
    getRegelungstextRahmenMetadata().forEach(m -> m.setBeschliessendesOrgan(beschliessendesOrgan));
  }

  public Optional<Boolean> getQualifizierteMehrheit() {
    return getRegelungstextRahmenMetadata()
      .findAny()
      .flatMap(RegelungstextRahmenMetadata::getQualifizierteMehrheit);
  }

  /**
   * Set the "Normgeber - Beschlussf. qual. Mehrheit" metadatum
   * @param qualifizierteMehrheit the new value for the metadatum or null to remove it
   */
  public void setQualifizierterMehrheit(@Nullable Boolean qualifizierteMehrheit) {
    getRegelungstextRahmenMetadata()
      .forEach(m -> m.setQualifizierterMehrheit(qualifizierteMehrheit));
  }

  public Optional<String> getRessort() {
    return getRegelungstextRahmenMetadata()
      .findAny()
      .flatMap(RegelungstextRahmenMetadata::getRessort);
  }

  public void setRessort(@Nullable String ressort) {
    getRegelungstextRahmenMetadata().forEach(m -> m.setRessort(ressort));
  }

  public Optional<String> getOrganisationsEinheit() {
    return getRegelungstextRahmenMetadata()
      .findAny()
      .flatMap(RegelungstextRahmenMetadata::getOrganisationsEinheit);
  }

  public void setOrganisationsEinheit(@Nullable String organisationsEinheit) {
    getRegelungstextRahmenMetadata().forEach(m -> m.setOrganisationsEinheit(organisationsEinheit));
  }

  private Stream<RegelungstextRahmenMetadata> getRegelungstextRahmenMetadata() {
    return dokumentRahmenMetadata
      .stream()
      .filter(RegelungstextRahmenMetadata.class::isInstance)
      .map(RegelungstextRahmenMetadata.class::cast);
  }
}
