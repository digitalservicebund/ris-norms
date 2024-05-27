package de.bund.digitalservice.ris.norms.utils.exceptions;

/**
 * This exception indicates that something is wrong with the content of the xml e.g. missing Eli or
 * invalid Mods
 */
public class XmlContentException extends RuntimeException {
  public XmlContentException(String message, Exception e) {
    super(message, e);
  }
}
