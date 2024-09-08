package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import lombok.Getter;

/** Indicates that the requested announcement does not exist. */
@Getter
public class AnnouncementNotFoundException extends RuntimeException implements NormsAppException {

  private final String eli;

  public AnnouncementNotFoundException(final String eli) {
    super("Announcement for norm with eli %s does not exist".formatted(eli));
    this.eli = eli;
  }
}
