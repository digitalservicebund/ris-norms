package de.bund.digitalservice.ris.norms.application.port.input;

/** Interface representing the use case for transforming a LegalDocML.de XML to HTML. */
public interface TransformLegalDocMlToHtmlUseCase {

  /**
   * Provides a html representation of a LegalDocML.de law.
   *
   * @param query The query specifying the XML of the law to be transformed to HTML.
   * @return The HTML representation.
   */
  String transformLegalDocMlToHtml(Query query) throws XmlTransformationException;

  /**
   * A record representing the query for transforming the XML representation of an law in
   * LegalDocML.de to HTML.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the law.
   * @param xml The XML representation of the law.
   */
  record Query(String eli, String xml) {}

  /** Exception for problems that happen when transforming an xml document. */
  class XmlTransformationException extends Exception {
    public XmlTransformationException(String message) {
      super(message);
    }
  }
}
