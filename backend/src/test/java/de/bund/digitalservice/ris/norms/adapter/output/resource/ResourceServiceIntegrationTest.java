package de.bund.digitalservice.ris.norms.adapter.output.resource;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ResourceServiceIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private ResourceService resourceService;

  @Test
  void loadPortalPublishingAllowList() {
    var allowList = resourceService.loadPortalPublishingAllowListPort();

    assertThat(allowList).isNotNull().isNotEmpty().contains("ERPWiPlanG 2025");
  }
}
