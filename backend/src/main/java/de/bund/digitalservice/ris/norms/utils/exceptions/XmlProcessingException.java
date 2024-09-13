package de.bund.digitalservice.ris.norms.utils.exceptions;

/** This exception indicates that something went wrong while processing the xml e.g. parsing */
public class XmlProcessingException extends RuntimeException implements NormsAppException {

  public XmlProcessingException(String message, Exception e) {
    super(message, e);
  }
}
