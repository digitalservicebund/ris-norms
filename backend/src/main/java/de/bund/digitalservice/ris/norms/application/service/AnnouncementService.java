package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadAllAnnouncementsUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAllAnnouncementsPort;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports. It is annotated with {@link Service} to indicate that it's a service
 * component in the Spring context.
 */
@Service
public class AnnouncementService implements LoadAllAnnouncementsUseCase {
  private final LoadAllAnnouncementsPort loadAllAnnouncementsPort;

  public AnnouncementService(LoadAllAnnouncementsPort loadAllAnnouncementsPort) {
    this.loadAllAnnouncementsPort = loadAllAnnouncementsPort;
  }

  @Override
  public List<Announcement> loadAllAnnouncements() {
    return loadAllAnnouncementsPort.loadAllAnnouncements();
  }
}
