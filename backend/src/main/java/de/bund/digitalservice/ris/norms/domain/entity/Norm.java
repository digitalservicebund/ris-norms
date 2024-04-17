package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.exceptions.XmlProcessingException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.*;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import net.sf.saxon.TransformerFactoryImpl;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Represents an amending law entity with various attributes. This class is annotated with Lombok
 * annotations for generating builders, getters, toString, and constructors.
 */
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class Norm {

  @Getter private final Document document;

  /**
   * Returns an Eli as {@link String} from a {@link Document} in a {@link Norm}.
   *
   * @return An Eli
   */
  public Optional<String> getEli() {
    return getValueFromExpression("//FRBRExpression/FRBRthis/@value", document);
  }

  /**
   * Returns an GUID as {@link UUID} from a {@link Document} in a {@link Norm}.
   *
   * @return An GUID of the document
   */
  public Optional<UUID> getGuid() {
    return getValueFromExpression(
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
   * Returns an FRBRname as {@link String} from a {@link Document} in a {@link Norm}.
   *
   * @return The PrintAnnouncementGazette
   */
  public Optional<String> getFRBRname() {
    Optional<String> announcementGazetteRaw =
        getValueFromExpression("//FRBRWork/FRBRname/@value", document);

    return announcementGazetteRaw.map(
        s ->
            s.replace("bgbl-1", "BGBl. I")
                .replace("bgbl-2", "BGBl. II")
                .replace("bgbl-3", "BGBl. III"));
  }

  /**
   * Returns an AnnouncementPage as {@link String} from a {@link Document} in a {@link Norm}.
   *
   * @return The AnnouncementPage
   */
  public Optional<String> getFRBRnumber() {
    return getValueFromExpression("//FRBRWork/FRBRnumber/@value", document);
  }

  /**
   * Returns an PublicationDate as {@link LocalDate} from a {@link Document} in a {@link Norm}.
   *
   * @return The PublicationDate
   */
  public Optional<LocalDate> getPublicationDate() {
    return getValueFromExpression("//FRBRWork/FRBRdate/@date", document).map(LocalDate::parse);
  }

  /**
   * Returns the title as {@link String} from a {@link Document} in a {@link Norm}.
   *
   * @return The title
   */
  public Optional<String> getTitle() {
    return getValueFromExpression("//longTitle/*/docTitle", document);
  }

  /**
   * Returns a list of articles as {@link List} from a {@link Document} in a {@link Norm}.
   *
   * @return The list of articles
   */
  public List<NormArticle> getArticles() {
    final Optional<NodeList> allArticles = getNodesFromExpression("//body/article", document);
    if (allArticles.isEmpty()) {
      return List.of();
    }

    List<NormArticle> articles = new ArrayList<>();

    for (int i = 0; i < allArticles.get().getLength(); i++) {
      final Node articleNode = allArticles.get().item(i);
      final NamedNodeMap attributes = articleNode.getAttributes();
      final Optional<Node> guidNode = Optional.ofNullable(attributes.getNamedItem("GUID"));
      final Optional<String> guid = guidNode.map(Node::getNodeValue);

      final Optional<Node> eIdNode = Optional.ofNullable(attributes.getNamedItem("eId"));
      final Optional<String> eId = eIdNode.map(Node::getNodeValue);

      final Optional<String> heading;

      final Optional<String> enumeration;

      final Optional<String> affectedDocumentEli;

      if (guid.isPresent()) {
        final String ARTICLE_EXPRESSION = "//body/article[@GUID=" + "'" + guid.get() + "']";

        heading = getValueFromExpression(ARTICLE_EXPRESSION + "/heading/text()", document);
        // not(normalize-space() is needed to filter out whitespaces which occur due to inner nodes
        // like akn:marker
        enumeration =
            getValueFromExpression(
                ARTICLE_EXPRESSION + "/num/text()[not(normalize-space() = '')]", document);

        affectedDocumentEli =
            getValueFromExpression(ARTICLE_EXPRESSION + "//affectedDocument/@href", document);

      } else {
        heading = Optional.empty();
        enumeration = Optional.empty();
        affectedDocumentEli = Optional.empty();
      }

      NormArticle newArticle =
          NormArticle.builder()
              .guid(guid)
              .eid(eId)
              .enumeration(enumeration)
              .title(heading)
              .affectedDocumentEli(affectedDocumentEli)
              .build();
      articles.add(newArticle);
    }
    return articles;
  }

  private Optional<String> getValueFromExpression(String expression, Node xmlNode) {
    XPath xPath = XPathFactory.newInstance().newXPath();
    String result;
    try {
      result = (String) xPath.evaluate(expression, xmlNode, XPathConstants.STRING);
    } catch (XPathExpressionException | NoSuchElementException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }
    if (result.isEmpty()) return Optional.empty();

    return Optional.of(result);
  }

  private Optional<NodeList> getNodesFromExpression(String expression, Document xmlDocument) {
    XPath xPath = XPathFactory.newInstance().newXPath();
    NodeList result;
    try {
      result = (NodeList) xPath.evaluate(expression, xmlDocument, XPathConstants.NODESET);
    } catch (XPathExpressionException | NoSuchElementException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }
    return Optional.of(result);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Norm norm = (Norm) o;
    return document.isEqualNode(norm.document);
  }

  @Override
  public int hashCode() {
    var writer = new StringWriter();

    try {
      new TransformerFactoryImpl()
          .newTransformer()
          .transform(new DOMSource(document), new StreamResult(writer));
    } catch (TransformerException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }

    return Objects.hash(writer.toString());
  }
}
