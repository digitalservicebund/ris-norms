package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.net.URI;
import java.util.Map;
import lombok.Getter;

/** Indicates that the requested verkuendung does not exist. */
@Getter
public class VerkuendungNotFoundException extends RuntimeException implements NormsAppException {

  private final String eli;

  public VerkuendungNotFoundException(final String eli) {
    super("Verkuendung for norm with eli %s does not exist".formatted(eli));
    this.eli = eli;
  }

  @Override
  public URI getType() {
    return URI.create("/errors/verkuendung-not-found");
  }

  @Override
  public String getTitle() {
    return "Verkuendung not found";
  }

  @Override
  public Map<String, Object> getProperties() {
    return Map.of("eli", getEli());
  }
}
