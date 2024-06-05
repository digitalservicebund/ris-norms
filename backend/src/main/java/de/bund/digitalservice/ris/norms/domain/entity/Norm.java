package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
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

  /**
   * Returns an Eli as {@link String} from a {@link Document} in a {@link Norm}. It tries to extract
   * it first from the expression level, otherwise it tries to extract it from the manifestation
   * level.
   *
   * @return An Eli
   */
  public String getEli() {
    return NodeParser.getValueFromExpression("//FRBRExpression/FRBRthis/@value", document)
        .orElseGet(
            () ->
                NodeParser.getValueFromExpression("//FRBRManifestation/FRBRthis/@value", document)
                    .map(m -> m.replace(".xml", ""))
                    .orElseThrow());
  }

  /**
   * Returns an GUID as {@link UUID} from a {@link Document} in a {@link Norm}.
   *
   * @return An GUID of the document
   */
  public UUID getGuid() {
    var guid =
        NodeParser.getValueFromExpression(
                "//FRBRExpression/FRBRalias[@name='aktuelle-version-id']/@value", document)
            .orElseThrow();

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
    return NodeParser.getValueFromExpression(
            "//longTitle/*/shortTitle/*[@refersTo=\"amtliche-abkuerzung\"]", document)
        .or(() -> NodeParser.getValueFromExpression("//longTitle/*/shortTitle", document));
  }

  /**
   * Returns the FNA as {@link String} of a {@link Norm}.
   *
   * @return The FNA
   * @deprecated Use {@link #getProprietary()} instead.
   */
  @Deprecated(forRemoval = true)
  public Optional<String> getFna() {
    return NodeParser.getValueFromExpression(
        "//meta/proprietary/legalDocML.de_metadaten/fna", document);
  }

  /**
   * Returns the proprietary metadata of the {@link Norm}.
   *
   * @return Proprietary metadata.
   */
  public Proprietary getProprietary() {
    return NodeParser.getNodeFromExpression("//meta/proprietary", document)
        .map(Proprietary::new)
        .orElse(
            Proprietary.builder().node(XmlMapper.toNode("<proprietary></proprietary>")).build());
  }

  /**
   * Returns a {@link Meta} instance from a {@link Document} in a {@link Norm}.
   *
   * @return the meta node as {@link Meta}
   */
  public Meta getMeta() {
    return new Meta(NodeParser.getMandatoryNodeFromExpression("//act/meta", document));
  }

  /**
   * Returns a list of articles as {@link List} from a {@link Document} in a {@link Norm}.
   *
   * @return The list of articles
   */
  public List<Article> getArticles() {
    return NodeParser.getNodesFromExpression("//body//article", document).stream()
        .map(Article::new)
        .toList();
  }

  /**
   * Returns the GUID of the next version as {@link UUID} from a {@link Document} in a {@link Norm}.
   *
   * @return The GUID of the next version of the norm.
   */
  public Optional<UUID> getNextVersionGuid() {
    return NodeParser.getValueFromExpression(
            "//FRBRExpression/FRBRalias[@name='nachfolgende-version-id']/@value", document)
        .flatMap(
            guid -> {
              try {
                return Optional.of(UUID.fromString(guid));
              } catch (IllegalArgumentException e) {
                return Optional.empty();
              }
            });
  }

  /**
   * Extracts a list of time boundaries (Zeitgrenzen) from the document.
   *
   * @return a list of {@link TimeBoundary} containing dates and event IDs.
   */
  public List<TimeBoundary> getTimeBoundaries() {
    final List<TemporalGroup> temporalGroups = getMeta().getTemporalData().getTemporalGroups();
    return temporalGroups.stream()
        .map(
            temporalGroup -> {
              final TimeInterval timeInterval = temporalGroup.getTimeInterval();
              final String eventRefEId = timeInterval.getEventRefEId().orElseThrow();
              final EventRef eventRef =
                  getMeta().getLifecycle().getEventRefs().stream()
                      .filter(f -> Objects.equals(f.getEid().value(), eventRefEId))
                      .findFirst()
                      .orElseThrow();
              return (TimeBoundary)
                  TimeBoundary.builder()
                      .temporalGroup(temporalGroup)
                      .timeInterval(timeInterval)
                      .eventRef(eventRef)
                      .build();
            })
        .toList();
  }

  /**
   * Extracts a list of passive modifications from the document.
   *
   * @return a list of passive modifications.
   */
  public List<TextualMod> getPassiveModifications() {
    return NodeParser.getNodesFromExpression("//passiveModifications/textualMod", document).stream()
        .map(TextualMod::new)
        .toList();
  }

  /**
   * Extracts a list of active modifications from the document.
   *
   * @return a list of active modifications.
   */
  public List<TextualMod> getActiveModifications() {
    return NodeParser.getNodesFromExpression("//activeModifications/textualMod", document).stream()
        .map(TextualMod::new)
        .toList();
  }

  /**
   * Extracts a list of {@link Mod}s from the document.
   *
   * @return a list of {@link Mod}s
   */
  public List<Mod> getMods() {
    return NodeParser.getNodesFromExpression("//body//mod", document).stream()
        .map(Mod::new)
        .toList();
  }

  /**
   * @return List of Strings with all eIds of all eventRef nodes where a related timeInterval exists
   */
  public List<String> getEventRefEids() {
    return getTimeBoundaries().stream()
        .map(TimeBoundary::getEventRefEid)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();
  }

  /**
   * @return List of Strings with all existing eIds of all temporalGroup nodes
   */
  public List<String> getTemporalGroupEids() {
    return getMeta().getTemporalData().getTemporalGroups().stream()
        .map(
            temporalGroup ->
                temporalGroup.getNode().getAttributes().getNamedItem("eId").getNodeValue())
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
  public Optional<String> getStartEventRefForTemporalGroup(String temporalGroupEid) {
    return NodeParser.getValueFromExpression(
            String.format(
                "//meta/temporalData/temporalGroup[@eId='%s']/timeInterval/@start",
                temporalGroupEid),
            this.document)
        .map(Href::new)
        .flatMap(Href::getEId);
  }

  /**
   * @param eId EId of an event ref
   * @return Start date of the event ref
   */
  public Optional<String> getStartDateForEventRef(String eId) {
    return NodeParser.getValueFromExpression(
        String.format("//meta/lifecycle/eventRef[@eId='%s']/@date", eId), this.document);
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
    final Node livecycle = getTimeBoundaries().getLast().getEventRef().getNode().getParentNode();
    final Element eventRef =
        NodeCreator.createElementWithEidAndGuid("akn:eventRef", "ereignis", livecycle);
    eventRef.setAttribute("date", date.toString());
    eventRef.setAttribute("source", "attributsemantik-noch-undefiniert");
    eventRef.setAttribute("type", eventRefType.getValue());
    eventRef.setAttribute("refersTo", "inkrafttreten");
    livecycle.appendChild(eventRef);

    // Create new temporalGroup node
    final TemporalData temporalData = getMeta().getTemporalData();
    final Element temporalGroup =
        NodeCreator.createElementWithEidAndGuid(
            "akn:temporalGroup", "geltungszeitgr", temporalData.getNode());
    temporalData.getNode().appendChild(temporalGroup);

    // Create new timeInterval node
    final Element timeInterval =
        NodeCreator.createElementWithEidAndGuid(
            "akn:timeInterval", "gelzeitintervall", temporalGroup);
    timeInterval.setAttribute("refersTo", "geltungszeit");
    final var eventRefEId = eventRef.getAttribute("eId");
    timeInterval.setAttribute(
        "start", new Href.Builder().setEId(eventRefEId).buildRelative().value());
    temporalGroup.appendChild(timeInterval);

    return new TemporalGroup(temporalGroup);
  }

  /**
   * Deletes the element of the norm identified by the given eId.
   *
   * @param eId the eId of the element to delete
   * @return the deleted element or empty if nothing to delete was found
   */
  public Optional<Node> deleteByEId(String eId) {
    return NodeParser.getNodeFromExpression(
            String.format("//*[@eId='%s']", eId), this.getDocument())
        .map(node -> node.getParentNode().removeChild(node));
  }

  /**
   * Deletes the temporal group if it is not referenced anymore in the norm.
   *
   * @param eId the eId of the temporal group to delete
   * @return the deleted temporal group or empty if nothing was deleted
   */
  public Optional<TemporalGroup> deleteTemporalGroupIfUnused(String eId) {
    final var nodesUsingTemporalData =
        NodeParser.getNodesFromExpression(String.format("//*[@period='#%s']", eId), getDocument());

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
  public Optional<Node> deleteEventRefIfUnused(String eId) {
    final var nodesUsingTemporalData =
        NodeParser.getNodesFromExpression(
            String.format("//*[@start='#%s' or @end='#%s']", eId, eId), getDocument());

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
    String timeIntervalNodeExpression =
        String.format(
            "//temporalData/temporalGroup/timeInterval[@start='#%s']", timeBoundaryToDelete.eid());
    Optional<Node> timeIntervalNode =
        NodeParser.getNodeFromExpression(timeIntervalNodeExpression, document);

    if (timeIntervalNode.isEmpty()) {
      return;
    }

    Node temporalGroupNode = timeIntervalNode.get().getParentNode();
    Node temporalDataNode = temporalGroupNode.getParentNode();
    temporalDataNode.removeChild(temporalGroupNode);
  }

  /**
   * Gets the akn:passiveModifications element of the norm, or creates it if it does not yet exist.
   *
   * @return the akn:passiveModifications element of the norm
   */
  public Node getOrCreatePassiveModificationsNode() {
    return NodeParser.getNodeFromExpression("//meta/analysis/passiveModifications", getDocument())
        .orElseGet(
            () ->
                NodeCreator.createElementWithEidAndGuid(
                    "akn:passiveModifications",
                    "pasmod",
                    getMeta().getOrCreateAnalysis().getNode()));
  }

  /**
   * Create a new passive modification element
   *
   * @param type the type of the textual mod (this is different from the @refersTo property of an
   *     akn:mod)
   * @param sourceHref the href our the source of the textual mod
   * @param destinationHref the href our the destination of the textual mod
   * @param periodHref the href to the geltungszeitgruppe of the textual mod
   * @return the newly create passive modification
   */
  public TextualMod addPassiveModification(
      String type, String sourceHref, String destinationHref, String periodHref) {
    var passiveModificationsNode = getOrCreatePassiveModificationsNode();

    var textualMod =
        NodeCreator.createElementWithEidAndGuid(
            "akn:textualMod", "textualmod", passiveModificationsNode);
    textualMod.setAttribute("type", type);
    passiveModificationsNode.appendChild(textualMod);

    var source = NodeCreator.createElementWithEidAndGuid("akn:source", "source", textualMod);
    source.setAttribute("href", sourceHref);
    textualMod.appendChild(source);

    var destination =
        NodeCreator.createElementWithEidAndGuid("akn:destination", "destination", textualMod);
    destination.setAttribute("href", destinationHref);
    textualMod.appendChild(destination);

    var force = NodeCreator.createElementWithEidAndGuid("akn:force", "gelzeitnachw", textualMod);
    force.setAttribute("period", periodHref);
    textualMod.appendChild(force);

    return new TextualMod(textualMod);
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
