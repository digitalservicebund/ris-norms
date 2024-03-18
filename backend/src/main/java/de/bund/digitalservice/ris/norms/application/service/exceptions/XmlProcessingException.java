package de.bund.digitalservice.ris.norms.application.service.exceptions;

/** This exception indicates that something went wrong while processing the xml e.g. parsing */
public class XmlProcessingException extends RuntimeException {
  public XmlProcessingException(String message, Exception e) {
    super(message, e);
  }
}
