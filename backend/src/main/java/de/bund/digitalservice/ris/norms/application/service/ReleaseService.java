package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAnnouncementPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateAnnouncementPort;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import java.time.Instant;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports. It is annotated with {@link Service} to indicate that it's a service
 * component in the Spring context.
 */
@Service
public class ReleaseService implements ReleaseAnnouncementUseCase {
  private final LoadAnnouncementPort loadAnnouncementPort;
  private final UpdateAnnouncementPort updateAnnouncementPort;

  public ReleaseService(
      LoadAnnouncementPort loadAnnouncementPort, UpdateAnnouncementPort updateAnnouncementPort) {
    this.loadAnnouncementPort = loadAnnouncementPort;
    this.updateAnnouncementPort = updateAnnouncementPort;
  }

  @Override
  public Optional<Announcement> releaseAnnouncement(ReleaseAnnouncementUseCase.Query query) {
    return loadAnnouncementPort
        .loadAnnouncement(new LoadAnnouncementPort.Command(query.eli()))
        .flatMap(
            announcement -> {
              announcement.setReleasedByDocumentalistAt(Instant.now());

              return updateAnnouncementPort.updateAnnouncement(
                  new UpdateAnnouncementPort.Command(announcement));
            });
  }
}
