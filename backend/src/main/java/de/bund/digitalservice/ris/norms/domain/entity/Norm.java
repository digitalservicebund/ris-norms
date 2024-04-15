package de.bund.digitalservice.ris.norms.domain.entity;

import java.util.Optional;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Document;

/**
 * Represents an amending law entity with various attributes. This class is annotated with Lombok
 * annotations for generating builders, getters, toString, and constructors.
 */
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class Norm {

  @Getter private Document document;

  /**
   * Returns an Eli as {@link String} from a {@link Document} in a {@link Norm}.
   *
   * @return An Eli
   */
  public Optional<String> getEli() throws XPathExpressionException {
    return getValueFromExpression("//FRBRExpression/FRBRthis/@value", document);
  }

  //  public String getPrintAnnouncementGazette() {
  //    return getNodeByXPath("//*[local-name()='mod']", amendingLaw);
  //  }
  //
  //  public String getDigitalAnnouncementMedium() {
  //    return digitalAnnouncementMedium;
  //  }
  //
  //  public LocalDate getPublicationDate() {
  //    return publicationDate;
  //  }
  //
  //  public String getPrintAnnouncementPage() {
  //    return printAnnouncementPage;
  //  }
  //
  //  public String getDigitalAnnouncementEdition() {
  //    return digitalAnnouncementEdition;
  //  }
  //
  //  public String getTitle() {
  //    return title;
  //  }
  //
  //  public Instant getReleasedAt() {
  //    return releasedAt;
  //  }
  //
  //  public List<Article> getArticles() {
  //    return articles;
  //  }

  private Optional<String> getValueFromExpression(String expression, Document xmlDocument)
      throws XPathExpressionException {

    XPath xPath = XPathFactory.newInstance().newXPath();
    String result = (String) xPath.evaluate(expression, xmlDocument, XPathConstants.STRING);
    if (result.isEmpty()) return Optional.empty();

    return Optional.of(result);
  }
}
