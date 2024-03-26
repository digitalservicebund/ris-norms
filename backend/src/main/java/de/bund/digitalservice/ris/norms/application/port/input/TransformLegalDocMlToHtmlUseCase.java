package de.bund.digitalservice.ris.norms.application.port.input;

/** Interface representing the use case for transforming a LegalDocML.de XML to HTML. */
public interface TransformLegalDocMlToHtmlUseCase {

  /**
   * Provides a html representation of a LegalDocML.de law.
   *
   * @param query The query specifying the XML of the law to be transformed to HTML.
   * @return The HTML representation.
   */
  String transformLegalDocMlToHtml(Query query);

  /**
   * A record representing the query for transforming the XML representation of an law in
   * LegalDocML.de to HTML.
   *
   * @param xml The XML representation of the law.
   * @param showMetadata Should the metadata section be included in the HTML?
   */
  record Query(String xml, boolean showMetadata) {}
}
