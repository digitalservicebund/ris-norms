package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.exceptions.StorageException;
import java.util.List;
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
    final List<Norm> norms = loadNormsByPublishStatePort.loadNormsByPublishState(
      new LoadNormsByPublishStatePort.Command(NormPublishState.QUEUED_FOR_PUBLISH)
    );

    log.info("Loaded {} norms for publishing", norms.size());
    norms.forEach(norm -> {
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
        log.error("Norm {} could not be published", norm.getManifestationEli().toString());
        log.error(e.getMessage(), e);
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
      log.info(
        "Deleted public norm on rollback strategy: {}",
        norm.getManifestationEli().toString()
      );
    } catch (final StorageException e) {
      log.error(e.getMessage(), e);
    }
  }

  private void rollbackPrivatePublish(Norm norm) {
    try {
      deletePrivateNormPort.deletePrivateNorm(new DeletePrivateNormPort.Command(norm));
      log.info(
        "Deleted privated norm on rollback strategy: {}",
        norm.getManifestationEli().toString()
      );
    } catch (StorageException e) {
      log.error(e.getMessage(), e);
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
