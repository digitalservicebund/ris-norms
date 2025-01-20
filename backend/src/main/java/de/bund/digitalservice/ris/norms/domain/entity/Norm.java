package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
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
 * Represents an LDML.de norm. This class is annotated with Lombok annotations for generating
 * builders, getters, toString, and constructors.
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class Norm {

  @Builder.Default
  private NormPublishState publishState = NormPublishState.UNPUBLISHED;

  private Set<Regelungstext> regelungstexte;

  public Norm(Norm norm) {
    this.regelungstexte =
    norm.getRegelungstexte().stream().map(Regelungstext::new).collect(Collectors.toSet());
    this.publishState = NormPublishState.UNPUBLISHED;
  }

  /**
   * Returns the first "Regelungstext".
   *
   * @return the "Regelungstext"
   */
  public Regelungstext getRegelungstext1() {
    return regelungstexte.iterator().next();
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
   * @deprecated use {@link #getNormExpressionEli()} instead
   */
  @Deprecated(forRemoval = true)
  public DokumentExpressionEli getExpressionEli() {
    return getRegelungstext1().getExpressionEli();
  }

  /**
   * Returns the expression Eli of the {@link Norm}.
   *
   * @return The expression Eli
   */
  // TODO: (Malte Laukötter, 2025-01-17) rename to getExpressionEli once the deprecated method is removed.
  public NormExpressionEli getNormExpressionEli() {
    return getExpressionEli().asNormEli();
  }

  /**
   * Returns the manifestation Eli of the {@link Norm}.
   *
   * @return The manifestation Eli
   * @deprecated use {@link #getNormManifestationEli()} instead
   */
  @Deprecated(forRemoval = true)
  public DokumentManifestationEli getManifestationEli() {
    return getRegelungstext1().getManifestationEli();
  }

  /**
   * Returns the expression Eli of the {@link Norm}.
   *
   * @return The expression Eli
   */
  // TODO: (Malte Laukötter, 2025-01-17) rename to getManifestationEli once the deprecated method is removed.
  public NormManifestationEli getNormManifestationEli() {
    return getManifestationEli().asNormEli();
  }

  /**
   * Returns the current version GUID as {@link UUID} from the {@link Norm}.
   *
   * @return An GUID of the norm (of the expression level)
   * @deprecated
   */
  @Deprecated(forRemoval = true)
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
   * Returns a list of articles as {@link List} from a {@link Document} in a {@link Norm}. It
   * filters out articles within akn:mod
   *
   * @return The list of articles
   * @deprecated
   */
  @Deprecated(forRemoval = true)
  public List<Article> getArticles() {
    return getRegelungstext1().getArticles();
  }

  /**
   * Returns a {@link List} of all target norms {@link DokumentExpressionEli}s of an amending {@link Norm}.
   *
   * @return The list of target norm elis
   */
  public List<DokumentExpressionEli> getTargetLawElis() {
    return getArticles()
      .stream()
      .filter(article -> !article.isGeltungszeitregel())
      .map(Article::getMandatoryAffectedDocumentEli)
      .toList();
  }

  /**
   * Extracts a list of {@link Mod}s from the document.
   *
   * @return a list of {@link Mod}s
   * @deprecated
   */
  @Deprecated(forRemoval = true)
  public List<Mod> getMods() {
    return getRegelungstext1().getMods();
  }

  /**
   * Extracts a list of time boundaries (Zeitgrenzen) from the document.
   *
   * @return a list of {@link TimeBoundary} containing dates and event IDs.
   * @deprecated
   */
  @Deprecated(forRemoval = true)
  public List<TimeBoundary> getTimeBoundaries() {
    return getRegelungstext1().getTimeBoundaries();
  }

  /**
   * * Extracts a list of time boundaries (Zeitgrenzen) from the document of a pre-filtered given
   * list of temporal groups.
   *
   * @param temporalGroups - the pre-filtered listed of temporal groups
   * @return a list of {@link TimeBoundary} containing dates and event IDs.
   * @deprecated
   */
  @Deprecated(forRemoval = true)
  public List<TimeBoundary> getTimeBoundaries(final List<TemporalGroup> temporalGroups) {
    return getRegelungstext1().getTimeBoundaries(temporalGroups);
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
   * @param temporalGroupEid EId of a temporal group
   * @return eid of the event ref of the start of the temporal group
   * @deprecated
   */
  @Deprecated(forRemoval = true)
  public Optional<String> getStartEventRefForTemporalGroup(final String temporalGroupEid) {
    return getRegelungstext1().getStartEventRefForTemporalGroup(temporalGroupEid);
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
   * Searches for a given eId and returns the number of matches.
   *
   * @param eId the eId of the element to search for.
   * @return the number of nodes for a given eId.
   * @deprecated
   */
  @Deprecated(forRemoval = true)
  public int getNumberOfNodesWithEid(String eId) {
    return getRegelungstext1().getNumberOfNodesWithEid(eId);
  }

  /**
   * Returns the element of the norm identified by the given eId.
   *
   * @param eId the eId of the element to return
   * @return the selected element
   * @deprecated
   */
  @Deprecated(forRemoval = true)
  public Optional<Element> getElementByEId(String eId) {
    return getRegelungstext1().getElementByEId(eId);
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

  /**
   * Deletes one time boundary (Zeitgrenze) from the document.
   *
   * @param timeBoundaryToDelete the time boundary that should be deleted from the xml
   * @deprecated
   */
  @Deprecated(forRemoval = true)
  public void deleteTimeBoundary(TimeBoundaryChangeData timeBoundaryToDelete) {
    getRegelungstext1().deleteTimeBoundary(timeBoundaryToDelete);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    Norm norm = (Norm) object;
    return regelungstexte.containsAll(norm.regelungstexte);
  }

  @Override
  public int hashCode() {
    return Objects.hash(regelungstexte);
  }
}
