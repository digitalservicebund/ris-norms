package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateAnnouncementPort;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.Instant;
import org.springframework.stereotype.Service;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports. It is annotated with {@link Service} to indicate that it's a service
 * component in the Spring context.
 */
@Service
public class ReleaseService implements ReleaseAnnouncementUseCase {

  private final LoadAnnouncementByNormEliUseCase loadAnnouncementByNormEliUseCase;
  private final UpdateAnnouncementPort updateAnnouncementPort;

  public ReleaseService(
    LoadAnnouncementByNormEliUseCase loadAnnouncementByNormEliUseCase,
    UpdateAnnouncementPort updateAnnouncementPort
  ) {
    this.loadAnnouncementByNormEliUseCase = loadAnnouncementByNormEliUseCase;
    this.updateAnnouncementPort = updateAnnouncementPort;
  }

  @Override
  public Announcement releaseAnnouncement(ReleaseAnnouncementUseCase.Query query) {
    var announcement = loadAnnouncementByNormEliUseCase.loadAnnouncementByNormEli(
      new LoadAnnouncementByNormEliUseCase.Query(query.eli())
    );
    announcement.setReleasedByDocumentalistAt(Instant.now());

    updateAnnouncementPort.updateAnnouncement(new UpdateAnnouncementPort.Command(announcement));
    return announcement;
  }

  /**
   * Takes a norm and makes any changes needed in preparation for publishing it.
   *
   * @param normToBeReleased Norm that will be released
   */
  public void prepareForRelease(final Norm normToBeReleased) {
    normToBeReleased.getMeta().getProprietary().ifPresent(this::removePrivateMetadata);
  }

  private void removePrivateMetadata(final Proprietary proprietary) {
    // Organisationseinheit
    var matches = NodeParser.getNodesFromExpression(
      "//organisationsEinheit",
      proprietary.getNode()
    );

    matches.forEach(match -> match.getParentNode().removeChild(match));
  }
}
