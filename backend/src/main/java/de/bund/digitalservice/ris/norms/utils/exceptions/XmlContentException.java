package de.bund.digitalservice.ris.norms.utils.exceptions;

import lombok.extern.slf4j.Slf4j;

/**
 * This exception indicates that something is wrong with the content of the xml e.g. missing Eli or
 * invalid Mods
 */
@Slf4j
public class XmlContentException extends RuntimeException {
  public XmlContentException(String message, Exception e) {
    super(message, e);
    log.error("XmlContentException: {}", message);
  }
}
