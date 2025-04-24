package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.net.URI;
import java.util.Map;
import lombok.Getter;

/** The given XML file is not a LDML.de file */
@Getter
public class NotLdmlDeXmlFileException extends RuntimeException implements NormsAppException {

  private final String fileName;

  public NotLdmlDeXmlFileException(String fileName) {
    super("The XML file %s is not a LDML.de XML file.".formatted(fileName));
    this.fileName = fileName;
  }

  @Override
  public URI getType() {
    return URI.create("/errors/not-a-ldml-de-xml-file");
  }

  @Override
  public String getTitle() {
    return "The provided XML file is not a LDML.de XML file";
  }

  @Override
  public Map<String, Object> getProperties() {
    return Map.of("fileName", fileName);
  }
}
