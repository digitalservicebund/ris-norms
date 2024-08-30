package de.bund.digitalservice.ris.norms.application.exception;

/** Indicates that the requested announcement does not exist. */
public class AnnouncementNotFoundException extends RuntimeException {

  public AnnouncementNotFoundException(final String eli) {
    super("Announcement for norm with eli %s does not exist".formatted(eli));
  }
}
