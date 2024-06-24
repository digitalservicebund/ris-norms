package de.bund.digitalservice.ris.norms.utils.exception;

/** This exception indicates that something went wrong while processing the xml e.g. parsing */
public class XmlProcessingException extends RuntimeException {
  public XmlProcessingException(final String message, final Exception e) {
    super(message, e);
  }
}
