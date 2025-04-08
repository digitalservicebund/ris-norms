package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.net.URI;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;

/**
 * A norm with the given GUID already exists in the system and no two norms can have the same GUID.
 */
@Getter
public class NormWithGuidAlreadyExistsException
  extends RuntimeException
  implements NormsAppException {

  private final UUID guid;

  public NormWithGuidAlreadyExistsException(final UUID guid) {
    super("A norm with the GUID %s already exists.".formatted(guid));
    this.guid = guid;
  }

  @Override
  public URI getType() {
    return URI.create("/errors/norm-with-guid-exists-already");
  }

  @Override
  public String getTitle() {
    return "Norm with GUID exists already";
  }

  @Override
  public Map<String, Object> getProperties() {
    return Map.of("guid", guid);
  }
}
