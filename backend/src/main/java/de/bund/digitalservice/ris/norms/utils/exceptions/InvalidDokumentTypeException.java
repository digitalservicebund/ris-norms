package de.bund.digitalservice.ris.norms.utils.exceptions;

import java.net.URI;
import java.util.Map;

/**
 * A Dokument is of an invalid or unsupported Dokument Type
 */
public class InvalidDokumentTypeException extends RuntimeException implements NormsAppException {

  private final String dokumentName;

  public InvalidDokumentTypeException(String dokumentName) {
    super("Dokument " + dokumentName + " is not of a supported dokument type.");
    this.dokumentName = dokumentName;
  }

  @Override
  public URI getType() {
    return URI.create("/errors/invalid-dokument-type");
  }

  @Override
  public String getTitle() {
    return "Invalid Dokument Type";
  }

  @Override
  public Map<String, Object> getProperties() {
    return Map.of("dokumentName", dokumentName);
  }
}
