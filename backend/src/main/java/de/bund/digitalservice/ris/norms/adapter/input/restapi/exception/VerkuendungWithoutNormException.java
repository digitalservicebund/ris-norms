package de.bund.digitalservice.ris.norms.adapter.input.restapi.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.net.URI;
import java.util.Map;
import lombok.Getter;

/** Indicates that the requested Verkuendung does not have the linked norm. */
@Getter
public class VerkuendungWithoutNormException extends RuntimeException implements NormsAppException {

  private final String eli;

  public VerkuendungWithoutNormException(final String eli) {
    super("Verkuendung with eli %s has no norm present".formatted(eli));
    this.eli = eli;
  }

  @Override
  public URI getType() {
    return URI.create("/errors/verkuendung-without-norm");
  }

  @Override
  public String getTitle() {
    return "Verkuendung without norm";
  }

  @Override
  public Map<String, Object> getProperties() {
    return Map.of("eli", eli);
  }
}
