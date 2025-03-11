package de.bund.digitalservice.ris.norms.adapter.output.resource;

import de.bund.digitalservice.ris.norms.application.port.output.LoadPortalPublishingAllowListPort;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * Service for loading data from classpath resources.
 */
@Service
@Log4j2
public class ResourceService implements LoadPortalPublishingAllowListPort {

  private final List<String> publishingAllowlist = new ArrayList<>();

  public ResourceService(
    @Value(
      "classpath:/portal-prototype-publishing-allowlist.txt"
    ) Resource portalPrototypePublishingAllowlist
  ) {
    try {
      this.publishingAllowlist.addAll(
          Files.readAllLines(portalPrototypePublishingAllowlist.getFile().toPath())
        );
    } catch (IOException e) {
      log.error(
        "Could not read portal prototype publishing allowlist. Keep the list of allowed norms empty.",
        e
      );
      this.publishingAllowlist.clear();
    }
  }

  @Override
  public List<String> loadPortalPublishingAllowListPort() {
    return this.publishingAllowlist;
  }
}
