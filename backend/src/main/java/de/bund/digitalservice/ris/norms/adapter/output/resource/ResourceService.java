package de.bund.digitalservice.ris.norms.adapter.output.resource;

import de.bund.digitalservice.ris.norms.application.port.output.LoadPortalPublishingAllowListPort;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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

  private final List<String> publishingAllowlist;

  public ResourceService(
    @Value(
      "classpath:portal-prototype-publishing-allowlist.txt"
    ) Resource portalPrototypePublishingAllowlist
  ) throws IOException {
    this.publishingAllowlist =
    new BufferedReader(
      new InputStreamReader(
        portalPrototypePublishingAllowlist.getInputStream(),
        StandardCharsets.UTF_8
      )
    )
      .lines()
      .toList();
  }

  @Override
  public List<String> loadPortalPublishingAllowListPort() {
    return this.publishingAllowlist;
  }
}
