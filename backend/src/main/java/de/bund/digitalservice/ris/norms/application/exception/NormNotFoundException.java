package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.net.URI;
import java.util.Map;
import lombok.Getter;

/** Indicates that the requested norm does not exist. */
@Getter
public class NormNotFoundException extends RuntimeException implements NormsAppException {

  private final String eli;

  public NormNotFoundException(final String eli) {
    super("Norm with eli %s does not exist".formatted(eli));
    this.eli = eli;
  }

  @Override
  public URI getType() {
    return URI.create("/errors/norm-not-found");
  }

  @Override
  public String getTitle() {
    return "Norm not found";
  }

  @Override
  public Map<String, Object> getProperties() {
    return Map.of("eli", eli);
  }
}
