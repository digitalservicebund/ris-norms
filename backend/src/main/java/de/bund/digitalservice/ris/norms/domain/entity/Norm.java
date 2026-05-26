package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
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
