package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.net.URI;
import java.util.Map;

/**
 * Something went wrong in the cryptographic operation (e.g. the Signature wasn’t properly initialized, or a provider error)
 */
public class SignatureProcessingException extends RuntimeException implements NormsAppException {

  public SignatureProcessingException() {
    super("The signature verification process failed.");
  }

  @Override
  public URI getType() {
    return URI.create(
      "/errors/normendokumentationspaket-import-failed/signature-processing-failed"
    );
  }

  @Override
  public String getTitle() {
    return "Verification of signature failed";
  }

  @Override
  public Map<String, Object> getProperties() {
    return Map.of();
  }
}
