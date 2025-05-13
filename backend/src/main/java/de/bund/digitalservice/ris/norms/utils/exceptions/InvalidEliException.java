package de.bund.digitalservice.ris.norms.utils.exceptions;

import java.net.URI;
import java.util.Map;

/**
 * Exception for when parsing an eli fails as it is not a valid eli of that eli type
 */
public class InvalidEliException extends RuntimeException implements NormsAppException {

  final String eli;
  final String eliType;

  public InvalidEliException(Class<?> eliClass, String eli) {
    super("Invalid %s: \"%s\"".formatted(eliClass.getSimpleName(), eli));
    this.eli = eli;
    this.eliType = eliClass.getSimpleName();
  }

  @Override
  public URI getType() {
    return URI.create("/errors/invalid-eli");
  }

  @Override
  public String getTitle() {
    return "Invalid eli";
  }

  @Override
  public Map<String, Object> getProperties() {
    return Map.of("eli", eli, "eliType", eliType);
  }
}
