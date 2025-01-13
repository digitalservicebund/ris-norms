package de.bund.digitalservice.ris.norms.domain.entity;

import static de.bund.digitalservice.ris.norms.utils.NodeParser.getElementsFromExpression;

import de.bund.digitalservice.ris.norms.domain.entity.eli.ExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.WorkEli;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Represents an LDML.de norm. This class is annotated with Lombok annotations for generating
 * builders, getters, toString, and constructors.
 */
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class Norm {

  private final Document document;

  public Norm(Norm norm) {
    this.document = (Document) norm.getDocument().cloneNode(true);
    this.publishState = NormPublishState.UNPUBLISHED;
  }

  @Setter
  @Builder.Default
  private NormPublishState publishState = NormPublishState.UNPUBLISHED;

  /**
   * Returns the work Eli of the {@link Norm}.
   *
   * @return The work Eli
   */
  public WorkEli getWorkEli() {
    return WorkEli.fromString(
      NodeParser.getValueFromMandatoryNodeFromExpression("//FRBRWork/FRBRthis/@value", document)
    );
  }

  /**
   * Returns the expression Eli of the {@link Norm}.
   *
   * @return The expression Eli
   */
  public ExpressionEli getExpressionEli() {
    return ExpressionEli.fromString(
      NodeParser.getValueFromMandatoryNodeFromExpression(
        "//FRBRExpression/FRBRthis/@value",
        document
      )
    );
  }

  /**
   * Returns the manifestation Eli of the {@link Norm}.
   *
   * @return The manifestation Eli
   */
  public ManifestationEli getManifestationEli() {
    return ManifestationEli.fromString(
      NodeParser.getValueFromMandatoryNodeFromExpression(
        "//FRBRManifestation/FRBRthis/@value",
        document
      )
    );
  }

  /**
   * Returns the current version GUID as {@link UUID} from the {@link Norm}.
   *
   * @return An GUID of the norm (of the expression level)
   */
  public UUID getGuid() {
    var guid = NodeParser.getValueFromMandatoryNodeFromExpression(
      "//FRBRExpression/FRBRalias[@name='aktuelle-version-id']/@value",
      document
    );

    return UUID.fromString(guid);
  }

  /**
   * Returns the title as {@link String} from a {@link Document} in a {@link Norm}.
   *
   * @return The title
   */
  public Optional<String> getTitle() {
    return NodeParser.getValueFromExpression("//longTitle/*/docTitle", document);
  }

  /**
   * Returns the short title as {@link String} from a {@link Document} in a {@link Norm}.
   *
   * @return The short title
   */
  public Optional<String> getShortTitle() {
    return NodeParser
      .getValueFromExpression(
        "//longTitle/*/shortTitle/*[@refersTo=\"amtliche-abkuerzung\"]",
        document
      )
      .or(() -> NodeParser.getValueFromExpression("//longTitle/*/shortTitle", document));
  }

  /**
   * Returns a {@link Meta} instance from a {@link Document} in a {@link Norm}.
   *
   * @return the meta node as {@link Meta}
   */
  public Meta getMeta() {
    return new Meta(NodeParser.getMandatoryElementFromExpression("//act/meta", document));
  }

  /**
   * Returns a list of articles as {@link List} from a {@link Document} in a {@link Norm}. It
   * filters out articles within akn:mod
   *
   * @return The list of articles
   */
  public List<Article> getArticles() {
    return getElementsFromExpression("//body//article[not(ancestor-or-self::mod)]", document)
      .stream()
      .map(Article::new)
      .toList();
  }

  /**
   * Extracts a list of {@link Mod}s from the document.
   *
   * @return a list of {@link Mod}s
   */
  public List<Mod> getMods() {
    return getElementsFromExpression("//body//mod", document).stream().map(Mod::new).toList();
  }

  /**
   * Extracts a list of time boundaries (Zeitgrenzen) from the document.
   *
   * @return a list of {@link TimeBoundary} containing dates and event IDs.
   */
  public List<TimeBoundary> getTimeBoundaries() {
    final List<TemporalGroup> temporalGroups = getMeta().getTemporalData().getTemporalGroups();
    return getTimeBoundaries(temporalGroups);
  }

  /**
   * * Extracts a list of time boundaries (Zeitgrenzen) from the document of a pre-filtered given
   * list of temporal groups.
   *
   * @param temporalGroups - the pre-filtered listed of temporal groups
   * @return a list of {@link TimeBoundary} containing dates and event IDs.
   */
  public List<TimeBoundary> getTimeBoundaries(final List<TemporalGroup> temporalGroups) {
    return temporalGroups
      .stream()
      .map(temporalGroup -> {
        final TimeInterval timeInterval = temporalGroup.getTimeInterval();
        final String eventRefEId = timeInterval.getEventRefEId().orElseThrow();
        final EventRef eventRef = getMeta()
          .getLifecycle()
          .getEventRefs()
          .stream()
          .filter(f -> Objects.equals(f.getEid().value(), eventRefEId))
          .findFirst()
          .orElseThrow();
        return (TimeBoundary) TimeBoundary
          .builder()
          .temporalGroup(temporalGroup)
          .timeInterval(timeInterval)
          .eventRef(eventRef)
          .build();
      })
      .toList();
  }

  /**
   * @param temporalGroupEid EId of a temporal group
   * @return Start date of the temporal group
   */
  public Optional<String> getStartDateForTemporalGroup(String temporalGroupEid) {
    return getStartEventRefForTemporalGroup(temporalGroupEid)
      .flatMap(this::getStartDateForEventRef);
  }

  /**
   * @param temporalGroupEid EId of a temporal group
   * @return eid of the event ref of the start of the temporal group
   */
  public Optional<String> getStartEventRefForTemporalGroup(final String temporalGroupEid) {
    return getMeta()
      .getTemporalData()
      .getTemporalGroups()
      .stream()
      .filter(temporalGroup -> temporalGroup.getEid().equals(temporalGroupEid))
      .findFirst()
      .flatMap(m -> m.getTimeInterval().getEventRefEId());
  }

  /**
   * @param eId EId of an event ref
   * @return Start date of the event ref
   */
  public Optional<String> getStartDateForEventRef(String eId) {
    return getMeta()
      .getLifecycle()
      .getEventRefs()
      .stream()
      .filter(eventRef -> Objects.equals(eventRef.getEid().value(), eId))
      .findFirst()
      .flatMap(EventRef::getDate)
      .map(LocalDate::toString);
  }

  /**
   * Adds one time boundary (Zeitgrenze) to the document. New eventRef node as child of lifecycle.
   * The temporalData node will get a new temporalGroup node as child, which will have a new
   * timeInterval node as child.
   *
   * @param date the {@link LocalDate} for the new time boundary.
   * @param eventRefType the {@link EventRefType} for the new time boundary.
   * @return the newly created {@link TemporalGroup}
   */
  public TemporalGroup addTimeBoundary(LocalDate date, EventRefType eventRefType) {
    // Create new eventRef node
    final EventRef eventRef = getMeta().getLifecycle().addEventRef();
    eventRef.setDate(date.toString());
    eventRef.setRefersTo("inkrafttreten");
    eventRef.setType(eventRefType.getValue());

    final TemporalGroup temporalGroup = getMeta().getTemporalData().addTemporalGroup();
    final TimeInterval timeInterval = temporalGroup.getOrCreateTimeInterval();
    timeInterval.setStart(
      new Href.Builder().setEId(eventRef.getEid().value()).buildInternalReference()
    );
    timeInterval.setRefersTo("geltungszeit");

    return temporalGroup;
  }

  /**
   * Searches for a given eId and returns the number of matches.
   *
   * @param eId the eId of the element to search for.
   * @return the number of nodes for a given eId.
   */
  public int getNumberOfNodesWithEid(String eId) {
    return getElementsFromExpression("//*[@eId='%s']".formatted(eId), document).size();
  }

  /**
   * Returns the element of the norm identified by the given eId.
   *
   * @param eId the eId of the element to return
   * @return the selected element
   */
  public Optional<Element> getElementByEId(String eId) {
    return NodeParser.getElementFromExpression(
      String.format("//*[@eId='%s']", eId),
      this.getDocument()
    );
  }

  /**
   * Deletes the element of the norm identified by the given eId.
   *
   * @param eId the eId of the element to delete
   * @return the deleted element or empty if nothing to delete was found
   */
  public Optional<Element> deleteByEId(String eId) {
    var node = getElementByEId(eId);

    node.ifPresent(n -> n.getParentNode().removeChild(n));

    return node;
  }

  /**
   * Deletes the temporal group if it is not referenced anymore in the norm.
   *
   * @param eId the eId of the temporal group to delete
   * @return the deleted temporal group or empty if nothing was deleted
   */
  public Optional<TemporalGroup> deleteTemporalGroupIfUnused(String eId) {
    final var nodesUsingTemporalData = getElementsFromExpression(
      String.format("//*[@period='#%s']", eId),
      getDocument()
    );

    if (!nodesUsingTemporalData.isEmpty()) {
      return Optional.empty();
    }

    return deleteByEId(eId).map(TemporalGroup::new);
  }

  /**
   * Deletes the event ref if it is not referenced anymore in the norm.
   *
   * @param eId the eId of the event ref to delete
   * @return the deleted temporal ref node or empty if nothing was deleted
   */
  public Optional<Element> deleteEventRefIfUnused(String eId) {
    final var nodesUsingTemporalData = getElementsFromExpression(
      String.format("//*[@start='#%s' or @end='#%s']", eId, eId),
      getDocument()
    );

    if (!nodesUsingTemporalData.isEmpty()) {
      return Optional.empty();
    }

    return deleteByEId(eId);
  }

  /**
   * Deletes one time boundary (Zeitgrenze) from the document.
   *
   * @param timeBoundaryToDelete the time boundary that should be deleted from the xml
   */
  public void deleteTimeBoundary(TimeBoundaryChangeData timeBoundaryToDelete) {
    // delete eventRef node
    deleteByEId(timeBoundaryToDelete.eid());

    // delete temporalGroup node and its timeInterval node children
    String timeIntervalNodeExpression = String.format(
      "//temporalData/temporalGroup/timeInterval[@start='#%s']",
      timeBoundaryToDelete.eid()
    );
    Optional<Element> timeIntervalNode = NodeParser.getElementFromExpression(
      timeIntervalNodeExpression,
      document
    );

    if (timeIntervalNode.isEmpty()) {
      return;
    }

    Node temporalGroupNode = timeIntervalNode.get().getParentNode();
    Node temporalDataNode = temporalGroupNode.getParentNode();
    temporalDataNode.removeChild(temporalGroupNode);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    Norm norm = (Norm) object;
    return document.isEqualNode(norm.document);
  }

  @Override
  public int hashCode() {
    return Objects.hash(XmlMapper.toString(document));
  }
}
