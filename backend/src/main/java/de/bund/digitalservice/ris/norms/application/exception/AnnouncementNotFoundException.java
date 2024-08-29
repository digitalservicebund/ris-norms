package de.bund.digitalservice.ris.norms.application.exception;

/** Indicates that the requested norm does not exist. */
public class AnnouncementNotFoundException extends RuntimeException {

  public AnnouncementNotFoundException(final String eli) {
    super("Announcement with eli %s does not exist".formatted(eli));
  }
}
