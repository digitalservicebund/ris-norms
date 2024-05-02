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
import org.w3c.dom.NodeList;

/**
 * Represents an amending law entity with various attributes. This class is annotated with Lombok
 * annotations for generating builders, getters, toString, and constructors.
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
    final NodeList allArticles = NodeParser.getNodesFromExpression("//body//article", document);
    if (allArticles.getLength() == 0) {
      return List.of();
    }

    List<Article> articles = new ArrayList<>();

    for (int i = 0; i < allArticles.getLength(); i++) {
      final Node articleNode = allArticles.item(i);
      Article newArticle = Article.builder().node(articleNode).build();
      articles.add(newArticle);
    }
    return articles;
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
    NodeList timeIntervalNodes =
        NodeParser.getNodesFromExpression("//temporalData/temporalGroup/timeInterval", document);

    if (timeIntervalNodes.getLength() == 0) {
      return List.of();
    }

    List<TimeBoundary> timeBoundaries = new ArrayList<>();

    for (int i = 0; i < timeIntervalNodes.getLength(); i++) {
      Node timeIntervalNode = timeIntervalNodes.item(i);
      String eIdEventRef =
          timeIntervalNode.getAttributes().getNamedItem("start").getNodeValue().replace("#", "");
      String eventRefNodeExpression = String.format("//lifecycle/eventRef[@eId='%s']", eIdEventRef);
      Node eventRefNode = NodeParser.getNodeFromExpression(eventRefNodeExpression, document);

      TimeBoundary timeBoundary =
          TimeBoundary.builder()
              .timeIntervalNode(timeIntervalNode)
              .eventRefNode(eventRefNode)
              .build();
      timeBoundaries.add(timeBoundary);
    }

    return timeBoundaries;
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
    NodeList temporalGroups = temporalData.getChildNodes();
    List<String> temporalGroupIds = new ArrayList<>();
    for (int i = 0; i < temporalGroups.getLength(); i++) {
      Node temporalGroupNode = temporalGroups.item(i);
      if ("akn:temporalGroup".equals(temporalGroupNode.getNodeName())) {
        temporalGroupIds.add(temporalGroupNode.getAttributes().getNamedItem("eId").getNodeValue());
      }
    }
    return temporalGroupIds;
  }

  /**
   * Adds one time boundary (Zeitgrenze) to the document. New eventRef node as child of lifecycle.
   * The temporalData node will get a new temporalGroup node as child, which will have a new
   * timeInterval node as child.
   *
   * @param timeBoundaryToAdd a {@link TimeBoundaryChangeData} containing a date and eid (null in
   *     this case).
   */
  public void addTimeBoundary(TimeBoundaryChangeData timeBoundaryToAdd) {

    // Calculate next possible eventRefEid
    String nextPossibleEventRefEid = calculateNextPossibleEid(getEventRefEids());

    // Create new eventRef node
    Element eventRef = document.createElement("akn:eventRef");
    eventRef.setAttribute("eId", nextPossibleEventRefEid);
    eventRef.setAttribute("GUID", UUID.randomUUID().toString());
    eventRef.setAttribute("date", timeBoundaryToAdd.date().toString());
    eventRef.setAttribute("source", "attributsemantik-noch-undefiniert");
    eventRef.setAttribute("type", "generation");
    eventRef.setAttribute("refersTo", "inkrafttreten");

    // Append new eventRef node to lifecycle node
    getTimeBoundaries().getLast().getEventRefNode().getParentNode().appendChild(eventRef);

    // Calculate next possible temporalGroup Eid
    String nextPossibleTemporalGroupEid = calculateNextPossibleEid(getTemporalGroupEids());

    // Create new temporalGroup node
    Element temporalGroup = document.createElement("akn:temporalGroup");
    temporalGroup.setAttribute("eId", nextPossibleTemporalGroupEid);
    temporalGroup.setAttribute("GUID", UUID.randomUUID().toString());

    // Create new timeInterval node
    Element timeInterval = document.createElement("akn:timeInterval");
    timeInterval.setAttribute("eId", nextPossibleTemporalGroupEid + "_gelzeitintervall-1");
    timeInterval.setAttribute("GUID", UUID.randomUUID().toString());
    timeInterval.setAttribute("refersTo", "geltungszeit");
    timeInterval.setAttribute("start", "#" + nextPossibleEventRefEid);

    // Append new timeInterval node to new temporalGroup node
    temporalGroup.appendChild(timeInterval);

    // Append new temporalGroup node to temporalData node
    NodeParser.getNodeFromExpression("//meta/temporalData", document).appendChild(temporalGroup);
  }

  /**
   * Deletes one time boundary (Zeitgrenze) from the document.
   *
   * @param timeBoundaryToDelete
   */
  public void deleteTimeBoundary(TimeBoundaryChangeData timeBoundaryToDelete) {
    // delete eventRef node
    String eventRefNodeExpression =
        String.format("//lifecycle/eventRef[@eId='%s']", timeBoundaryToDelete.eid());
    Node eventRefNode = NodeParser.getNodeFromExpression(eventRefNodeExpression, document);
    eventRefNode.getParentNode().removeChild(eventRefNode);

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
   * Calculates the next possible eId out of a list of eIds
   *
   * @param eIds List of identifiers within a document
   *     "meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1"
   * @return the next possible eid
   */
  public static String calculateNextPossibleEid(List<String> eIds) {
    if (eIds.isEmpty()) throw new IllegalArgumentException("eIds is empty");

    String eventRefStringFirst = eIds.getFirst();
    String eventRefBase = eventRefStringFirst.substring(0, eventRefStringFirst.lastIndexOf('-'));

    boolean allMatch =
        eIds.stream()
            .map(eventRefString -> eventRefString.substring(0, eventRefString.lastIndexOf('-')))
            .allMatch(eventRefBase::equals);
    if (!allMatch) throw new IllegalArgumentException("Not all eId-bases are equal");

    final String lastNumberAsString =
        Arrays.stream(eIds.stream().sorted().toList().getLast().split("-")).toList().getLast();

    int nextId = Integer.parseInt(lastNumberAsString) + 1;

    return eventRefBase + "-" + nextId;
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
