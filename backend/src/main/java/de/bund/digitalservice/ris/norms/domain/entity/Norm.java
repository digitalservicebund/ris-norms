package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.*;
import de.bund.digitalservice.ris.norms.domain.entity.metadata.rahmen.RahmenMetadata;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Document;

/**
 * Represents a Norm containing {@link Dokument}s (which can be either {@link Regelungstext} or {@link OffeneStruktur}) and {@link BinaryFile}s.
 * It also maintains a publish state defined by {@link NormPublishState}.
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class Norm {

  @Builder.Default
  private NormPublishState publishState = NormPublishState.UNPUBLISHED;

  private Set<Dokument> dokumente;

  public static final LocalDate WORKING_COPY_DATE = LocalDate.of(2999, 12, 31);

  @Builder.Default
  private Set<BinaryFile> binaryFiles = new HashSet<>();

  public Norm(NormPublishState publishState, Set<Dokument> dokumente) {
    this.publishState = publishState;
    this.dokumente = dokumente;
    this.binaryFiles = new HashSet<>();
  }

  public Norm(Norm norm) {
    this.publishState = NormPublishState.UNPUBLISHED;
    this.dokumente = norm.getDokumente() != null
      ? norm.getDokumente().stream().map(Dokument::copy).collect(Collectors.toSet())
      : new HashSet<>();
    this.binaryFiles = norm.getBinaryFiles() != null
      ? norm
          .getBinaryFiles()
          .stream()
          .map(b -> new BinaryFile(b.getDokumentManifestationEli(), b.getContent()))
          .collect(Collectors.toSet())
      : new HashSet<>();
  }

  /**
   * Returns all Regelungstext objects from the dokumente set.
   *
   * @return a set of Regelungstext objects, or an empty set if none exist
   */
  public Set<Regelungstext> getRegelungstexte() {
    return dokumente
      .stream()
      .filter(Regelungstext.class::isInstance)
      .map(Regelungstext.class::cast)
      .collect(Collectors.toSet());
  }

  /**
   * Returns all Bekanntmachung objects from the dokumente set.
   *
   * @return a set of Bekanntmachung objects, or an empty set if none exist
   */
  public Set<Bekanntmachung> getBekanntmachungen() {
    return dokumente
      .stream()
      .filter(Bekanntmachung.class::isInstance)
      .map(Bekanntmachung.class::cast)
      .collect(Collectors.toSet());
  }

  /**
   * Returns all Regelungstext objects from the dokumente set.
   *
   * @return a set of Regelungstext objects, or an empty set if none exist
   */
  public Set<OffeneStruktur> getOffenestrukturen() {
    return dokumente
      .stream()
      .filter(OffeneStruktur.class::isInstance)
      .map(OffeneStruktur.class::cast)
      .collect(Collectors.toSet());
  }

  /**
   * Returns the Rechtsetzungsdokument.
   *
   * @return the {@link Rechtsetzungsdokument}, or an empty set if none exist
   */
  public Rechtsetzungsdokument getRechtsetzungsdokument() {
    return dokumente
      .stream()
      .filter(Rechtsetzungsdokument.class::isInstance)
      .map(Rechtsetzungsdokument.class::cast)
      .findFirst()
      .orElseThrow(() -> new RuntimeException("Missing Rechtsetzungsdokument"));
  }

  /**
   * Replaces the current {@link Regelungstext} objects in {@link #dokumente} with the provided set.
   * <p>
   * If the underlying {@code dokumente} set is immutable (for example, created via {@code Set.of(...)}),
   * this method creates a new mutable {@link HashSet} containing all non-{@link Regelungstext} elements,
   * then adds the provided {@code regelungstexte} to it.
   * </p>
   *
   * @param regelungstexte the new set of {@link Regelungstext} objects to set
   */
  public void setRegelungstexte(final Set<Regelungstext> regelungstexte) {
    final Set<Dokument> newDokumente = (dokumente == null)
      ? new HashSet<>()
      : dokumente
          .stream()
          .filter(d -> !(d instanceof Regelungstext))
          .collect(Collectors.toSet());
    newDokumente.addAll(regelungstexte);
    dokumente = newDokumente;
  }

  /**
   * Returns the first "Regelungstext".
   *
   * @return the "Regelungstext"
   */
  public Regelungstext getRegelungstext1() {
    return dokumente
      .stream()
      .filter(Regelungstext.class::isInstance)
      .map(Regelungstext.class::cast)
      .min(Comparator.comparing(Dokument::getManifestationEli))
      .orElseThrow();
  }

  /**
   * Get a specific regelungstext of this norm by its ELI
   * @param dokumentEli the eli of the dokument
   * @return the {@link Regelungstext} or empty if none of the {@link Regelungstext}e match
   */
  public Optional<Regelungstext> getRegelungstextByEli(DokumentEli dokumentEli) {
    return getRegelungstexte()
      .stream()
      .filter(regelungstext ->
        switch (dokumentEli) {
          case DokumentManifestationEli manifestationEli -> regelungstext
            .getManifestationEli()
            .equals(manifestationEli);
          case DokumentExpressionEli expressionEli -> regelungstext
            .getExpressionEli()
            .equals(expressionEli);
          case DokumentWorkEli workEli -> regelungstext.getWorkEli().equals(workEli);
        }
      )
      .findFirst();
  }

  /**
   * Get a specific dokument of this norm by its ELI
   * @param dokumentEli the eli of the dokument
   * @return the {@link Dokument} or empty if none of the {@link Dokument}e match
   */
  public Optional<Dokument> getDokumentByEli(DokumentEli dokumentEli) {
    return getDokumente()
      .stream()
      .filter(dokument ->
        switch (dokumentEli) {
          case DokumentManifestationEli manifestationEli -> dokument
            .getManifestationEli()
            .equals(manifestationEli);
          case DokumentExpressionEli expressionEli -> dokument
            .getExpressionEli()
            .equals(expressionEli);
          case DokumentWorkEli workEli -> dokument.getWorkEli().equals(workEli);
        }
      )
      .findFirst();
  }

  /**
   * Returns the work Eli of the {@link Norm}.
   *
   * @return The work Eli
   */
  public NormWorkEli getWorkEli() {
    return getRegelungstext1().getWorkEli().asNormEli();
  }

  /**
   * Returns the expression Eli of the {@link Norm}.
   *
   * @return The expression Eli
   */
  public NormExpressionEli getExpressionEli() {
    return getRegelungstext1().getExpressionEli().asNormEli();
  }

  /**
   * Returns the manifestation Eli of the {@link Norm}.
   *
   * @return The manifestation Eli
   */
  public NormManifestationEli getManifestationEli() {
    return dokumente
      .stream()
      .findFirst()
      .map(Dokument::getManifestationEli)
      .orElseThrow()
      .asNormEli();
  }

  public void setManifestationDateTo(LocalDate manifestationDate) {
    dokumente.forEach(dokument -> dokument.setManifestationDateTo(manifestationDate));
  }

  /**
   * Returns the current version GUID as {@link UUID} from the {@link Norm}.
   *
   * @return An GUID of the norm (of the expression level)
   */
  public UUID getGuid() {
    return getRegelungstext1().getGuid();
  }

  /**
   * Returns the title as {@link String} from a {@link Document} in a {@link Norm}.
   *
   * @return The title
   */
  public Optional<String> getLongTitle() {
    return getRegelungstext1().getLongTitle();
  }

  /**
   * Returns the short title as {@link String} from a {@link Document} in a {@link Norm}.
   *
   * @return The short title
   */
  public Optional<String> getShortTitle() {
    return getRegelungstext1().getShortTitle();
  }

  /**
   * Returns if the norm is in force at the given date
   *
   * @param date the date to check
   * @return true if the norm is currently in force
   */
  public boolean isInkraftAt(LocalDate date) {
    var inkrafttreteDatum = getRegelungstext1()
      .getMeta()
      .getProprietary()
      .flatMap(Proprietary::getInkrafttreteDatum);
    var ausserkrafttreteDatum = getRegelungstext1()
      .getMeta()
      .getProprietary()
      .flatMap(Proprietary::getAusserkrafttreteDatum);

    return (
      inkrafttreteDatum.map(d -> !d.isAfter(date)).orElse(false) &&
      ausserkrafttreteDatum.map(d -> !d.isBefore(date)).orElse(true)
    );
  }

  /**
   * Is this norm gegenstandlos? This information/metadata is ONLY present in the <i>rechtsetzungsdokument</i>
   *
   * @return true if it is.
   */
  public boolean isGegenstandlos() {
    return getRechtsetzungsdokument()
      .getMeta()
      .getProprietary()
      .flatMap(Proprietary::getGegenstandlos)
      .isPresent();
  }

  /**
   * Set the norm as gegenstandslos
   *
   * @param date - date to set as gegenstandslos with
   */
  public void setGegenstandlos(final String date) {
    final Proprietary proprietary = getRechtsetzungsdokument().getMeta().getOrCreateProprietary();
    final Gegenstandlos gegenstandlos = proprietary.getOrCreateGegenstandlos();
    gegenstandlos.setSinceDate(date);
  }

  public RahmenMetadata getRahmenMetadata() {
    return new RahmenMetadata(getDokumente().stream().map(Dokument::getRahmenMetadata).toList());
  }

  /**
   * Set the release type of the norm
   *
   * @param releaseType - the release type to set
   */
  public void setReleaseType(ReleaseType releaseType) {
    getRegelungstext1()
      .getMeta()
      .getOrCreateProprietary()
      .setMetadataValue(Metadata.STAND, releaseType.getValue());
  }

  /**
   * Get the release type of the norm
   *
   * @return the release type
   */
  public ReleaseType getReleaseType() {
    return ReleaseType.fromString(
      getRegelungstext1()
        .getMeta()
        .getOrCreateProprietary()
        .getMetadataValue(Metadata.STAND)
        .orElse("")
    );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Norm norm = (Norm) o;
    return (
      Objects.equals(dokumente, norm.dokumente) && Objects.equals(binaryFiles, norm.binaryFiles)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(dokumente, binaryFiles);
  }
}
