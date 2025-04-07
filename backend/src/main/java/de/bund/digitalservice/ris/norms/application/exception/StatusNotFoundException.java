package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.net.URI;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;

/** Indicates that the requested status does not exist. */
@Getter
public class StatusNotFoundException extends RuntimeException implements NormsAppException {

  private final UUID processId;

  public StatusNotFoundException(UUID processId) {
    this.processId = processId;
  }

  @Override
  public URI getType() {
    return URI.create("/errors/process-id-not-found");
  }

  @Override
  public String getTitle() {
    return "Could not find process with id " + processId;
  }

  @Override
  public Map<String, Object> getProperties() {
    return Map.of();
  }
}
