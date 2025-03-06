package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.PublishNormsToPortalPrototypeUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service for publishing norms to the portal prototype
 */
@Service
@Slf4j
public class PortalPrototypePublishService implements PublishNormsToPortalPrototypeUseCase {

  @Override
  public void publishNormsToPortalPrototype() {
    log.info("Publishing norms to port prototype - not yet implemented");
  }
}
