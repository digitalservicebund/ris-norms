package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Class representing the norms-application-only metadata
 */
@Getter
public class CustomModsMetadata {

  private final Element element;

  public CustomModsMetadata(Element element) {
    this.element = element;
  }

  /**
   * Get a list of all the norm expressions that have been amended bfy this norm
   *
   * @return all norm expression elis amended by this norm
   */
  public List<NormExpressionEli> getAmendedNormExpressionElis() {
    return NodeParser
      .getNodesFromExpression("./amended-norm-expressions/norm-expression/text()", element)
      .stream()
      .map(Node::getNodeValue)
      .map(NormExpressionEli::fromString)
      .toList();
  }

  /**
   * Get a list of the time boundaries including mandatory "id" and "art" attributes. Current does not support "unbestimmt" cases
   *
   * @return a list of {@link Zeitgrenze}
   */
  public List<Zeitgrenze> getZeitgrenzen() {
    return NodeParser
      .getNodesFromExpression("./geltungszeiten/geltungszeit", element)
      .stream()
      .map(node -> {
        final Node idNode = node.getAttributes().getNamedItem("id");
        if (idNode == null) {
          throw new IllegalArgumentException(
            "Missing required attribute 'id' in <geltungszeit> node."
          );
        }
        final String idAttr = idNode.getNodeValue();
        final Node artNode = node.getAttributes().getNamedItem("art");
        if (artNode == null) {
          throw new IllegalArgumentException(
            "Missing required attribute 'art' in <geltungszeit> node with id: " + idAttr
          );
        }
        final String artAttr = artNode.getNodeValue();
        final String textContent = node.getTextContent().trim();
        if (textContent.isEmpty()) {
          throw new IllegalArgumentException(
            "Missing text content in <geltungszeit> node with id: " + idAttr
          );
        }
        final LocalDate date;
        try {
          date = LocalDate.parse(textContent);
        } catch (final DateTimeParseException e) {
          throw new IllegalArgumentException(
            "Invalid date format in <geltungszeit>: '" + textContent + "'",
            e
          );
        }
        final Zeitgrenze.Art art;
        try {
          art = Zeitgrenze.Art.valueOf(artAttr.toUpperCase());
        } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException("Unknown art value: '" + artAttr + "'", e);
        }
        return Zeitgrenze.builder().id(idAttr).date(date).art(art).build();
      })
      .collect(Collectors.toList());
  }

  /**
   * Replaces the already existing list of time boundaries with the new passed list, sorting first by date and creating id accordingly.
   *
   * @param zeitgrenzen - the list of {@link Zeitgrenze}
   *
   * @return the created list of {@link Zeitgrenze} including the ids
   */
  public List<Zeitgrenze> updateZeitgrenzen(final List<Zeitgrenze> zeitgrenzen) {
    final Optional<Node> geltunsZeitenNode = NodeParser.getNodeFromExpression(
      "./geltungszeiten",
      element
    );
    final Element geltungszeitenElement;
    if (geltunsZeitenNode.isEmpty()) {
      geltungszeitenElement = NodeCreator.createElement("geltungszeiten", element);
    } else {
      geltungszeitenElement = (Element) geltunsZeitenNode.get();
      while (geltungszeitenElement.hasChildNodes()) {
        geltungszeitenElement.removeChild(geltungszeitenElement.getFirstChild());
      }
    }

    if (zeitgrenzen != null && !zeitgrenzen.isEmpty()) {
      final List<Zeitgrenze> sortedZeitgrenzen = zeitgrenzen
        .stream()
        .sorted(Comparator.comparing(Zeitgrenze::getDate))
        .toList();
      for (int i = 0; i < sortedZeitgrenzen.size(); i++) {
        final Zeitgrenze zeitgrenze = sortedZeitgrenzen.get(i);
        final String generatedId = "gz-" + (i + 1);

        final Element geltungszeit = NodeCreator.createElement(
          "geltungszeit",
          geltungszeitenElement
        );
        geltungszeit.setAttribute("id", generatedId);
        geltungszeit.setAttribute("art", zeitgrenze.getArt().name().toLowerCase());
        geltungszeit.setTextContent(zeitgrenze.getDate().toString());
      }
    } else {
      geltungszeitenElement.getParentNode().removeChild(geltungszeitenElement);
    }
    return getZeitgrenzen();
  }

  public List<ZielnormReference> getZielnormenReferences() {
    return NodeParser
      .getElementsFromExpression("./zielnorm-references/zielnorm-reference", getElement())
      .stream()
      .map(ZielnormReference::new)
      .toList();
  }
}
