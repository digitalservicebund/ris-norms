package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormEli;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.net.URI;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;

/** Indicates that the requested norm does not exist. */
@Getter
public class NormNotFoundException extends RuntimeException implements NormsAppException {

  private final String eli;
  private final String guid;

  public NormNotFoundException(final NormEli eli) {
    super("Norm with eli %s does not exist".formatted(eli));
    this.eli = eli.toString();
    this.guid = null;
  }

  public NormNotFoundException(final DokumentEli eli) {
    super("Norm with eli %s does not exist".formatted(eli));
    this.eli = eli.toString();
    this.guid = null;
  }

  public NormNotFoundException(final UUID guid) {
    super("Norm with guid %s does not exist".formatted(guid));
    this.guid = guid.toString();
    this.eli = null;
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
    if (guid != null) {
      return Map.of("guid", guid);
    }

    return Map.of("eli", eli);
  }
}
