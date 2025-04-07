package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.net.URI;
import java.util.Map;
import lombok.Getter;

/** Indicates that the requested announcement does not exist. */
@Getter
public class AnnouncementNotFoundException extends RuntimeException implements NormsAppException {

  private final String eli;

  public AnnouncementNotFoundException(final String eli) {
    super("Announcement for norm with eli %s does not exist".formatted(eli));
    this.eli = eli;
  }

  @Override
  public URI getType() {
    return URI.create("/errors/announcement-not-found");
  }

  @Override
  public String getTitle() {
    return "Announcement not found";
  }

  @Override
  public Map<String, Object> getProperties() {
    return Map.of("eli", getEli());
  }
}
