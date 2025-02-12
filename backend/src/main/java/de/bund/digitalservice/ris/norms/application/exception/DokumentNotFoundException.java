package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import lombok.Getter;

/** Indicates that the requested {@link Dokument} does not exist. */
@Getter
public class DokumentNotFoundException extends RuntimeException implements NormsAppException {

  private final String eli;

  public DokumentNotFoundException(final String eli) {
    super("Document with eli %s does not exist".formatted(eli));
    this.eli = eli;
  }
}
