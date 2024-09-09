package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;

/** The given file is not a xml file, while a xml file was expected. */
public class NotAXmlFileException extends RuntimeException implements NormsAppException {
  public NotAXmlFileException() {
    super("The file is not a xml file");
  }
}
