package de.bund.digitalservice.ris.norms.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

class HealthEndpointIntegrationTest extends BaseIntegrationTest {

  @Test
  void actuatorHealthShouldBeReachable(@Autowired MockMvc mvc) throws Exception {
    mvc.perform(get("/actuator/health")).andExpect(status().isOk());
  }
}
