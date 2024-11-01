package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.exceptions.StorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service responsible for publishing {@link Norm} to both a private and public storage locations. All necessary steps for
 * preparing the data before publishing it are also contained within this service. Only if publishing to both public and
 * private locations succeeds, the publish state of the respective {@link Norm} will get the {@link NormPublishState} updated to "PUBLISHED"
 */
@Service
@Slf4j
public class PublishService implements PublishNormUseCase {

  private final LoadNormsByPublishStatePort loadNormsByPublishStatePort;
  private final PublishPublicNormPort publishPublicNormPort;
  private final PublishPrivateNormPort publishPrivateNormPort;
  private final UpdateOrSaveNormPort updateOrSaveNormPort;
  private final DeletePublicNormPort deletePublicNormPort;
  private final DeletePrivateNormPort deletePrivateNormPort;

  public PublishService(
    LoadNormsByPublishStatePort loadNormsByPublishStatePort,
    PublishPublicNormPort publishPublicNormPort,
    PublishPrivateNormPort publishPrivateNormPort,
    UpdateOrSaveNormPort updateOrSaveNormPort,
    DeletePublicNormPort deletePublicNormPort,
    DeletePrivateNormPort deletePrivateNormPort
  ) {
    this.loadNormsByPublishStatePort = loadNormsByPublishStatePort;
    this.publishPublicNormPort = publishPublicNormPort;
    this.publishPrivateNormPort = publishPrivateNormPort;
    this.updateOrSaveNormPort = updateOrSaveNormPort;
    this.deletePublicNormPort = deletePublicNormPort;
    this.deletePrivateNormPort = deletePrivateNormPort;
  }

  @Override
  public void processQueuedFilesForPublish() {
    loadNormsByPublishStatePort
      .loadNormsByPublishState(
        new LoadNormsByPublishStatePort.Command(NormPublishState.QUEUED_FOR_PUBLISH)
      )
      .forEach(norm -> {
        prepareForPublish(norm);
        boolean isPublicPublished = false;
        boolean isPrivatePublished = false;
        try {
          // Try to publish publicly
          publishPublicNormPort.publishPublicNorm(new PublishPublicNormPort.Command(norm));
          isPublicPublished = true;
          // Only if public publish succeeds, try private publish
          publishPrivateNormPort.publishPrivateNorm(new PublishPrivateNormPort.Command(norm));
          isPrivatePublished = true;
          // If both succeeds, then update publish state
          norm.setPublishState(NormPublishState.PUBLISHED);
          updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Command(norm));
          log.info("Published norm: {}", norm.getManifestationEli().toString());
        } catch (final Exception e) {
          log.error(e.getMessage());
          // Rollback logic based on what succeeded (also for the case that DB update failed)
          if (isPublicPublished) {
            rollbackPublicPublish(norm);
          }
          if (isPublicPublished && isPrivatePublished) {
            rollbackPrivatePublish(norm);
          }
        }
      });
  }

  /**
   * Takes a norm and makes any changes needed in preparation for publishing it.
   *
   * @param normToBeReleased Norm that will be released
   */
  public void prepareForPublish(final Norm normToBeReleased) {
    normToBeReleased.getMeta().getProprietary().ifPresent(this::removePrivateMetadata);
  }

  private void rollbackPublicPublish(Norm norm) {
    try {
      deletePublicNormPort.deletePublicNorm(new DeletePublicNormPort.Command(norm));
    } catch (final StorageException e) {
      log.error(e.getMessage());
    }
  }

  private void rollbackPrivatePublish(Norm norm) {
    try {
      deletePrivateNormPort.deletePrivateNorm(new DeletePrivateNormPort.Command(norm));
    } catch (StorageException e) {
      log.error(e.getMessage());
    }
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
