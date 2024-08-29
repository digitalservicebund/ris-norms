package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.AnnouncementNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAnnouncementByNormEliPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateAnnouncementPort;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import java.time.Instant;
import org.springframework.stereotype.Service;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports. It is annotated with {@link Service} to indicate that it's a service
 * component in the Spring context.
 */
@Service
public class ReleaseService implements ReleaseAnnouncementUseCase {
  private final LoadAnnouncementByNormEliPort loadAnnouncementByNormEliPort;
  private final UpdateAnnouncementPort updateAnnouncementPort;

  public ReleaseService(
      LoadAnnouncementByNormEliPort loadAnnouncementByNormEliPort,
      UpdateAnnouncementPort updateAnnouncementPort) {
    this.loadAnnouncementByNormEliPort = loadAnnouncementByNormEliPort;
    this.updateAnnouncementPort = updateAnnouncementPort;
  }

  @Override
  public Announcement releaseAnnouncement(ReleaseAnnouncementUseCase.Query query) {
    final Announcement announcement =
        loadAnnouncementByNormEliPort
            .loadAnnouncementByNormEli(new LoadAnnouncementByNormEliPort.Command(query.eli()))
            .orElseThrow(() -> new AnnouncementNotFoundException(query.eli()));

    announcement.setReleasedByDocumentalistAt(Instant.now());

    updateAnnouncementPort.updateAnnouncement(new UpdateAnnouncementPort.Command(announcement));
    return announcement;
  }
}
