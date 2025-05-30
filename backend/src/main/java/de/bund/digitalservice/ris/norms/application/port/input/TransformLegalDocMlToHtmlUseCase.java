package de.bund.digitalservice.ris.norms.application.port.input;

/** Interface representing the use case for transforming a LegalDocML.de XML to HTML. */
public interface TransformLegalDocMlToHtmlUseCase {
  /**
   * Provides a html representation of a LegalDocML.de law.
   *
   * @param options The options specifying the XML of the law to be transformed to HTML.
   * @return The HTML representation.
   */
  String transformLegalDocMlToHtml(Options options);

  /**
   * A record representing the options for transforming the XML representation of an law in
   * LegalDocML.de to HTML.
   *
   * @param xml The XML representation of the law.
   * @param showMetadata Should the metadata section be included in the HTML?
   * @param snippet if the XML passed is just a snippet of a norm
   */
  record Options(String xml, boolean showMetadata, boolean snippet) {}
}
