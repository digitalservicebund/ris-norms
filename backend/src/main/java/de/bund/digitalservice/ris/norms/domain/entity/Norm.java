package de.bund.digitalservice.ris.norms.domain.entity;

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
   * Returns an Eli as {@link String} from a {@link Document} in a {@link Norm}.
   *
   * @return An Eli
   */
  public Optional<String> getEli() {
    return NodeParser.getValueFromExpression("//FRBRExpression/FRBRthis/@value", document);
  }

  /**
   * Returns an GUID as {@link UUID} from a {@link Document} in a {@link Norm}.
   *
   * @return An GUID of the document
   */
  public Optional<UUID> getGuid() {
    return NodeParser.getValueFromExpression(
            "//FRBRExpression/FRBRalias[@name='aktuelle-version-id']/@value", document)
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
   * Returns a FRBRname as {@link String} from a {@link Document} in a {@link Norm}.
   *
   * @return The FRBRname
   */
  public Optional<String> getFRBRname() {
    Optional<String> fRBRname =
        NodeParser.getValueFromExpression("//FRBRWork/FRBRname/@value", document);

    return fRBRname.map(
        s ->
            s.replace("bgbl-1", "BGBl. I")
                .replace("bgbl-2", "BGBl. II")
                .replace("banz-at", "BAnz AT"));
  }

  /**
   * Returns a FRBRnumber as {@link String} from a {@link Document} in a {@link Norm}.
   *
   * @return The FRBRnumber
   */
  public Optional<String> getFRBRnumber() {
    return NodeParser.getValueFromExpression("//FRBRWork/FRBRnumber/@value", document);
  }

  /**
   * Returns a FBRDateVerkuendun as {@link LocalDate} from a {@link Document} in a {@link Norm}.
   *
   * @return The FBRDateVerkuendun
   */
  public Optional<LocalDate> getFBRDateVerkuendung() {
    return NodeParser.getValueFromExpression("//FRBRWork/FRBRdate/@date", document)
        .map(LocalDate::parse);
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
   * Returns the short title as {@link String} from a {@link Document} in a {@link Norm}.
   *
   * @return The short title
   */
  public Optional<String> getFna() {
    return NodeParser.getValueFromExpression(
        "//meta/proprietary/legalDocML.de_metadaten/fna", document);
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
    List<Node> timeIntervalNodes =
        NodeParser.getNodesFromExpression("//temporalData/temporalGroup/timeInterval", document);

    return timeIntervalNodes.stream()
        .map(
            node -> {
              String eIdEventRef =
                  node.getAttributes().getNamedItem("start").getNodeValue().replace("#", "");
              String eventRefNodeExpression =
                  String.format("//lifecycle/eventRef[@eId='%s']", eIdEventRef);
              Node eventRefNode =
                  NodeParser.getNodeFromExpression(eventRefNodeExpression, document);

              return (TimeBoundary)
                  TimeBoundary.builder().timeIntervalNode(node).eventRefNode(eventRefNode).build();
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
    Node temporalData = NodeParser.getNodeFromExpression("//meta/temporalData", document);
    List<Node> temporalGroups = NodeParser.nodeListToList(temporalData.getChildNodes());

    return temporalGroups.stream()
        .filter(node -> "akn:temporalGroup".equals(node.getNodeName()))
        .map(node -> node.getAttributes().getNamedItem("eId").getNodeValue())
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
        .map(value -> value.replaceFirst("^#", ""));
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
    final Node livecycle = getTimeBoundaries().getLast().getEventRefNode().getParentNode();
    final Element eventRef = createElementWithEidAndGuid("akn:eventRef", "ereignis", livecycle);
    eventRef.setAttribute("date", date.toString());
    eventRef.setAttribute("source", "attributsemantik-noch-undefiniert");
    eventRef.setAttribute("type", eventRefType.getValue());
    eventRef.setAttribute("refersTo", "inkrafttreten");
    livecycle.appendChild(eventRef);

    // Create new temporalGroup node
    final Node temporalData = NodeParser.getNodeFromExpression("//meta/temporalData", document);
    final Element temporalGroup =
        createElementWithEidAndGuid("akn:temporalGroup", "geltungszeitgr", temporalData);
    temporalData.appendChild(temporalGroup);

    // Create new timeInterval node
    final Element timeInterval =
        createElementWithEidAndGuid("akn:timeInterval", "gelzeitintervall", temporalGroup);
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
   * @return the deleted element
   */
  public Node deleteByEId(String eId) {
    var node =
        NodeParser.getNodeFromExpression(String.format("//*[@eId='%s']", eId), this.getDocument());
    return node.getParentNode().removeChild(node);
  }

  /**
   * Deletes the temporal group if it is not referenced anymore in the norm.
   *
   * @param eId the eId of the temporal group to delete
   * @return the deleted temporal group node or empty if nothing was deleted
   */
  public Optional<TemporalGroup> deleteTemporalGroupIfUnused(String eId) {
    final var nodesUsingTemporalData =
        NodeParser.getNodesFromExpression(String.format("//*[@period='#%s']", eId), getDocument());

    if (!nodesUsingTemporalData.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(new TemporalGroup(deleteByEId(eId)));
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

    return Optional.of(deleteByEId(eId));
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
    Node timeIntervalNode = NodeParser.getNodeFromExpression(timeIntervalNodeExpression, document);
    Node temporalGroupNode = timeIntervalNode.getParentNode();
    Node temporalDataNode = temporalGroupNode.getParentNode();
    temporalDataNode.removeChild(temporalGroupNode);
  }

  /**
   * Create a new element with both an eId and a GUID. The new element still needs to appended to
   * the parent node.
   *
   * @param tagName the tag name of the new element
   * @param eidPartName the name for the last part of the eid for the new element
   * @param parentNode the element of which this newly created element should be a child
   * @return the newly created element
   */
  public Element createElementWithEidAndGuid(String tagName, String eidPartName, Node parentNode) {
    var newElement = getDocument().createElement(tagName);
    newElement.setAttribute("eId", calculateNextPossibleEid(parentNode, eidPartName));
    newElement.setAttribute("GUID", UUID.randomUUID().toString());
    parentNode.appendChild(newElement);
    return newElement;
  }

  /**
   * Gets the akn:analysis element of the norm, or creates it if it does not yet exist.
   *
   * @return the akn:analysis element of the norm
   */
  public Node getOrCreateAnalysisNode() {
    final var metaNode = NodeParser.getNodeFromExpression("//act/meta", getDocument());

    return Optional.ofNullable(NodeParser.getNodeFromExpression("analysis", metaNode))
        .orElseGet(
            () -> {
              final var newElement =
                  createElementWithEidAndGuid("akn:analysis", "analysis", metaNode);
              newElement.setAttribute("source", "attributsemantik-noch-undefiniert");
              return newElement;
            });
  }

  /**
   * Gets the akn:passiveModifications element of the norm, or creates it if it does not yet exist.
   *
   * @return the akn:passiveModifications element of the norm
   */
  public Node getOrCreatePassiveModificationsNode() {
    final var analysisNode = getOrCreateAnalysisNode();
    return Optional.ofNullable(
            NodeParser.getNodeFromExpression("passiveModifications", analysisNode))
        .orElseGet(
            () -> createElementWithEidAndGuid("akn:passiveModifications", "pasmod", analysisNode));
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
        createElementWithEidAndGuid("akn:textualMod", "textualmod", passiveModificationsNode);
    textualMod.setAttribute("type", type);
    passiveModificationsNode.appendChild(textualMod);

    var source = createElementWithEidAndGuid("akn:source", "source", textualMod);
    source.setAttribute("href", sourceHref);
    textualMod.appendChild(source);

    var destination = createElementWithEidAndGuid("akn:destination", "destination", textualMod);
    destination.setAttribute("href", destinationHref);
    textualMod.appendChild(destination);

    var force = createElementWithEidAndGuid("akn:force", "gelzeitnachw", textualMod);
    force.setAttribute("period", periodHref);
    textualMod.appendChild(force);

    return new TextualMod(textualMod);
  }

  /**
   * Calculates the next possible eId for the given eIdPartType and parent node.
   *
   * @param parentNode The parent node under which this new eId should be used
   * @param eidPartType The name of the new part of the eId
   * @return The new eId created from the parent node eId, the eidPartType and the next available
   *     position
   */
  public static String calculateNextPossibleEid(Node parentNode, String eidPartType) {
    var lastPosition =
        NodeParser.nodeListToList(parentNode.getChildNodes()).stream()
            .flatMap(node -> NodeParser.getValueFromExpression("@eId", node).stream())
            .map(EId::new)
            .map(eId -> eId.getParts().getLast())
            .filter(eIdPart -> eIdPart.getType().equals(eidPartType))
            .map(EIdPart::getPosition)
            .map(Integer::parseInt)
            .max(Comparator.comparingInt(Integer::intValue))
            .orElse(0);
    var newEidPart = new EIdPart(eidPartType, String.valueOf(lastPosition + 1));

    return NodeParser.getValueFromExpression("@eId", parentNode)
        .map(EId::new)
        .map(parendEId -> parendEId.addPart(newEidPart))
        .map(EId::value)
        .orElseThrow();
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
