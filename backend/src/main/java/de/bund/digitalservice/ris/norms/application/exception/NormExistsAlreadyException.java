package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.net.URI;
import java.util.Map;
import lombok.Getter;

/**
 * A norm with the given eli already exists in the system and no two norms can have the same eli.
 */
@Getter
public class NormExistsAlreadyException extends RuntimeException implements NormsAppException {

  private final String eli;

  public NormExistsAlreadyException(String eli) {
    super("A norm with the eli %s already exists.".formatted(eli));
    this.eli = eli;
  }

  @Override
  public URI getType() {
    return URI.create("/errors/norm-with-eli-exists-already");
  }

  @Override
  public String getTitle() {
    return "Norm with ELI exists already";
  }

  @Override
  public Map<String, Object> getProperties() {
    return Map.of("eli", getEli());
  }
}
