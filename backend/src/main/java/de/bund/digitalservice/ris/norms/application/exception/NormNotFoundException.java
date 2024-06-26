package de.bund.digitalservice.ris.norms.application.exception;

import java.util.UUID;

/** Indicates that the requested norm does not exist. */
public class NormNotFoundException extends RuntimeException {
  public NormNotFoundException(final String eli) {
    super("Norm with eli %s does not exist".formatted(eli));
  }

  public NormNotFoundException(final UUID guid) {
    super("Norm with GUID %s does not exist".formatted(guid));
  }
}
