package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.net.URI;
import java.util.Map;

/** This exception indicates that a proposed change to a norm is not allowed */
public class InvalidUpdateException extends RuntimeException implements NormsAppException {

  public InvalidUpdateException(String message) {
    super(message);
  }

  @Override
  public URI getType() {
    return URI.create("/errors/invalidate-update");
  }

  @Override
  public String getTitle() {
    return "Invalid update operation";
  }

  @Override
  public Map<String, Object> getProperties() {
    return Map.of();
  }
}
