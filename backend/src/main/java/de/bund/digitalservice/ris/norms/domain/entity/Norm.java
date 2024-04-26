package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
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
    final NodeList allArticles = NodeParser.getNodesFromExpression("//body/article", document);
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
   * Adds a list of time boundaries (Zeitgrenzen) to the document.
   *
   * @param timeBoundaryToAdd a {@link TimeBoundaryChangeData} containing dates and event IDs.
   */
  public void addTimeBoundary(TimeBoundaryChangeData timeBoundaryToAdd) {
    List<String> eventRefIds =
        getTimeBoundaries().stream()
            .map(TimeBoundary::getEventRefEid)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();

    String eventRefString = eventRefIds.getFirst();
    String eventRefBase = eventRefString.substring(0, eventRefString.lastIndexOf('-'));
    Integer nextPossibleId = calculateNextPossibleId(eventRefIds);

    TimeBoundary lastTimeBoundary = getTimeBoundaries().getLast();

    Node lifecycle = lastTimeBoundary.getEventRefNode().getParentNode();

    // Create new element
    Element eventRef = document.createElement("akn:eventRef");

    // Set attributes
    eventRef.setAttribute("eId", eventRefBase + nextPossibleId);
    eventRef.setAttribute("GUID", UUID.randomUUID().toString());
    eventRef.setAttribute("date", timeBoundaryToAdd.date().toString());
    eventRef.setAttribute("source", "attributsemantik-noch-undefiniert");
    eventRef.setAttribute("type", "generation");
    eventRef.setAttribute("refersTo", "inkrafttreten");

    // Append the element to the document
    lifecycle.appendChild(eventRef);
  }

  private static @NotNull Integer calculateNextPossibleId(List<String> eids) {
    final String lastNumberAsString =
        Arrays.stream(eids.stream().sorted().toList().getLast().split("-")).toList().getLast();

    return Integer.parseInt(lastNumberAsString) + 1;
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
