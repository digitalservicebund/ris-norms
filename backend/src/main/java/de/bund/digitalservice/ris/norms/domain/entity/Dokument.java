package de.bund.digitalservice.ris.norms.domain.entity;

import static de.bund.digitalservice.ris.norms.utils.NodeParser.getElementsFromExpression;

import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentWorkEli;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Represents an abstract "Dokument" in LDML.de (which can be a "Rechtsetzungsdokument", a "Regelungstext" and a "Offene Struktur")
 */
@Getter
@AllArgsConstructor
public abstract class Dokument {

  private final Document document;

  /**
   * Returns the work Eli of the {@link Dokument}.
   *
   * @return The work Eli
   */
  public DokumentWorkEli getWorkEli() {
    return DokumentWorkEli.fromString(
      NodeParser.getValueFromMandatoryNodeFromExpression("//FRBRWork/FRBRthis/@value", document)
    );
  }

  /**
   * Returns the expression Eli of the {@link Dokument}.
   *
   * @return The expression Eliuuu
   */
  public DokumentExpressionEli getExpressionEli() {
    return DokumentExpressionEli.fromString(
      NodeParser.getValueFromMandatoryNodeFromExpression(
        "//FRBRExpression/FRBRthis/@value",
        document
      )
    );
  }

  /**
   * Returns the manifestation Eli of the {@link Dokument}.
   *
   * @return The manifestation Eli
   */
  public DokumentManifestationEli getManifestationEli() {
    return DokumentManifestationEli.fromString(
      NodeParser.getValueFromMandatoryNodeFromExpression(
        "//FRBRManifestation/FRBRthis/@value",
        document
      )
    );
  }

  /**
   * Returns the current version GUID as {@link UUID} from the {@link Dokument}.
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
   * Returns a {@link Meta} instance from a {@link Document} in a {@link Dokument}.
   *
   * @return the meta node as {@link Meta}
   */
  public Meta getMeta() {
    return new Meta(NodeParser.getMandatoryElementFromExpression("//act/meta", document));
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
    Dokument norm = (Dokument) object;
    return document.isEqualNode(norm.document);
  }

  @Override
  public int hashCode() {
    return hashNode(document);
  }

  /**
   * Custom hashCode implementation that uses the same semantic as Node::isEqualNode to determine the hash code so two
   * Dokumente that are equal also have the same hashCode. We ignore Node::getNodeType as it would be really odd for two
   * Nodes to only differ in it, and it is the slowest part to call.
   *
   * @param node the {@link Node} to create a hash for
   * @return a hash code for the node
   */
  private static int hashNode(Node node) {
    if (node == null) {
      return 1;
    }

    return Objects.hash(
      node.getNodeName(),
      node.getLocalName(),
      node.getNamespaceURI(),
      node.getPrefix(),
      node.getNodeValue(),
      hashNamedNodeMap(node.getAttributes()),
      hashNodeList(node.getChildNodes())
    );
  }

  private static int hashNodeList(NodeList nodeList) {
    if (nodeList == null || nodeList.getLength() == 0) {
      return 1;
    }

    int[] hashes = new int[nodeList.getLength()];

    for (int i = 0; i < nodeList.getLength(); i++) {
      hashes[i] = hashNode(nodeList.item(i));
    }

    return Arrays.hashCode(hashes);
  }

  private static int hashNamedNodeMap(NamedNodeMap nodeMap) {
    if (nodeMap == null || nodeMap.getLength() == 0) {
      return 1;
    }

    int[] hashes = new int[nodeMap.getLength()];

    for (int i = 0; i < nodeMap.getLength(); i++) {
      hashes[i] = hashNode(nodeMap.item(i));
    }

    return Arrays.hashCode(hashes);
  }

  /**
   * Abstract method so that child classes are forced to implement it, so that the deep-copy code in the Norm object is more readable
   *
   * @return a deep copy of this Dokument
   */
  public abstract Dokument copy();
}
