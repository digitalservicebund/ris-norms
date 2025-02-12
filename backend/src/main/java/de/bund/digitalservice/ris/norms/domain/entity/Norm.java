package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentWorkEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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

  @Builder.Default
  private Set<BinaryFile> binaryFiles = new HashSet<>();

  public Norm(NormPublishState publishState, Set<Dokument> dokumente) {
    this.publishState = publishState;
    this.dokumente = dokumente;
    this.binaryFiles = new HashSet<>();
  }

  public Norm(Norm norm) {
    this.publishState = NormPublishState.UNPUBLISHED;
    this.dokumente =
    norm.getDokumente() != null
      ? norm.getDokumente().stream().map(Dokument::copy).collect(Collectors.toSet())
      : new HashSet<>();
    this.binaryFiles =
    norm.getBinaryFiles() != null
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
      : dokumente.stream().filter(d -> !(d instanceof Regelungstext)).collect(Collectors.toSet());
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
      .findFirst()
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

  @Deprecated(forRemoval = true)
  public Document getDocument() {
    return getRegelungstext1().getDocument();
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
   * @deprecated use {@link #getExpressionEli()} instead
   */
  @Deprecated(forRemoval = true)
  public DokumentExpressionEli getRegelungstext1ExpressionEli() {
    return getRegelungstext1().getExpressionEli();
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
    return getRegelungstext1().getManifestationEli().asNormEli();
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
  public Optional<String> getTitle() {
    return getRegelungstext1().getTitle();
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
   * Returns a {@link Meta} instance from a {@link Document} in a {@link Norm}.
   *
   * @return the meta node as {@link Meta}
   * @deprecated
   */
  @Deprecated(forRemoval = true)
  public Meta getMeta() {
    return getRegelungstext1().getMeta();
  }

  /**
   * @param temporalGroupEid EId of a temporal group
   * @return Start date of the temporal group
   * @deprecated
   */
  @Deprecated(forRemoval = true)
  public Optional<String> getStartDateForTemporalGroup(String temporalGroupEid) {
    return getRegelungstext1().getStartDateForTemporalGroup(temporalGroupEid);
  }

  /**
   * Adds one time boundary (Zeitgrenze) to the document. New eventRef node as child of lifecycle.
   * The temporalData node will get a new temporalGroup node as child, which will have a new
   * timeInterval node as child.
   *
   * @param date         the {@link LocalDate} for the new time boundary.
   * @param eventRefType the {@link EventRefType} for the new time boundary.
   * @return the newly created {@link TemporalGroup}
   * @deprecated
   */
  @Deprecated(forRemoval = true)
  public TemporalGroup addTimeBoundary(LocalDate date, EventRefType eventRefType) {
    return getRegelungstext1().addTimeBoundary(date, eventRefType);
  }

  /**
   * Deletes the element of the norm identified by the given eId.
   *
   * @param eId the eId of the element to delete
   * @return the deleted element or empty if nothing to delete was found
   * @deprecated
   */
  @Deprecated(forRemoval = true)
  public Optional<Element> deleteByEId(String eId) {
    return getRegelungstext1().deleteByEId(eId);
  }

  /**
   * Deletes the temporal group if it is not referenced anymore in the norm.
   *
   * @param eId the eId of the temporal group to delete
   * @return the deleted temporal group or empty if nothing was deleted
   * @deprecated
   */
  @Deprecated(forRemoval = true)
  public Optional<TemporalGroup> deleteTemporalGroupIfUnused(String eId) {
    return getRegelungstext1().deleteTemporalGroupIfUnused(eId);
  }

  /**
   * Deletes the event ref if it is not referenced anymore in the norm.
   *
   * @param eId the eId of the event ref to delete
   * @return the deleted temporal ref node or empty if nothing was deleted
   * @deprecated
   */
  @Deprecated(forRemoval = true)
  public Optional<Element> deleteEventRefIfUnused(String eId) {
    return getRegelungstext1().deleteEventRefIfUnused(eId);
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
