package de.bund.digitalservice.ris.norms.utils.exceptions;

import java.net.URI;
import java.util.Map;

/** This exception indicates that something went wrong while processing the xml e.g. parsing */
public class XmlProcessingException extends RuntimeException implements NormsAppException {

  public XmlProcessingException(String message, Exception e) {
    super(message, e);
  }

  @Override
  public URI getType() {
    return URI.create("/errors/xml-processing-error");
  }

  @Override
  public String getTitle() {
    return "XML processing error";
  }

  @Override
  public Map<String, Object> getProperties() {
    return Map.of();
  }
}
