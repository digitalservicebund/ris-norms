package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAmendingLawPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAnnouncementPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateAmendingLawPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateAnnouncementPort;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import java.time.Instant;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports. It is annotated with {@link Service} to indicate that it's a service
 * component in the Spring context.
 */
@Service
public class ReleaseService implements ReleaseAmendingLawAndAllRelatedTargetLawsUseCase {
  private final LoadAmendingLawPort loadAmendingLawPort;
  private final LoadAnnouncementPort loadAnnouncementPort;
  private final UpdateAmendingLawPort updateAmendingLawPort;
  private final UpdateAnnouncementPort updateAnnouncementPort;

  public ReleaseService(
      LoadAmendingLawPort loadAmendingLawPort,
      LoadAnnouncementPort loadAnnouncementPort,
      UpdateAmendingLawPort updateAmendingLawPort,
      UpdateAnnouncementPort updateAnnouncementPort) {
    this.loadAmendingLawPort = loadAmendingLawPort;
    this.loadAnnouncementPort = loadAnnouncementPort;
    this.updateAmendingLawPort = updateAmendingLawPort;
    this.updateAnnouncementPort = updateAnnouncementPort;
  }

  @Override
  public Optional<AmendingLaw> releaseAmendingLaw(Query query) {
    var announcementOptional =
        loadAnnouncementPort.loadAnnouncement(new LoadAnnouncementPort.Command(query.eli()));
    announcementOptional.ifPresent(
        announcement -> {
          announcement.setReleasedByDocumentalistAt(Instant.now());

          updateAnnouncementPort.updateAnnouncement(
              new UpdateAnnouncementPort.Command(announcement));
        });

    var amendingLawOptional =
        loadAmendingLawPort.loadAmendingLawByEli(new LoadAmendingLawPort.Command(query.eli()));
    return amendingLawOptional.flatMap(
        amendingLaw -> {
          amendingLaw.setReleasedAt(Instant.now());

          return updateAmendingLawPort.updateAmendingLaw(
              new UpdateAmendingLawPort.Command(amendingLaw));
        });
  }
}
