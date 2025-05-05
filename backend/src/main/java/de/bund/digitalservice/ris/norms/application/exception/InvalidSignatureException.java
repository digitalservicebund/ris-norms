package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.net.URI;
import java.util.Map;

/**
 * The signature was not valid, meaning there was a signature mismatch. Client side error.
 */
public class InvalidSignatureException extends RuntimeException implements NormsAppException {

  public InvalidSignatureException() {
    super("The uploaded signature is not valid");
  }

  @Override
  public URI getType() {
    return URI.create("/errors/normendokumentationspaket-import-failed/signature-not-valid");
  }

  @Override
  public String getTitle() {
    return "The uploaded signature is not valid";
  }

  @Override
  public Map<String, Object> getProperties() {
    return Map.of();
  }
}
