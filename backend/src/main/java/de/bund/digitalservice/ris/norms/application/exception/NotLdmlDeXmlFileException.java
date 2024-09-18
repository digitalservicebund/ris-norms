package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import lombok.Getter;

/** The given XML file is not a LDML.de file */
@Getter
public class NotLdmlDeXmlFileException extends RuntimeException implements NormsAppException {

  private final String fileName;

  public NotLdmlDeXmlFileException(String fileName) {
    super("The xml file %s is not a LDML.de xml file.".formatted(fileName));
    this.fileName = fileName;
  }
}
