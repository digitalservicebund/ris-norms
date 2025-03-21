package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SecurelessControllerTest(EnvironmentController.class)
@TestPropertySource(
  properties = {
    "sentry.environment=test",
    "frontend.auth.clientId=test-client",
    "frontend.auth.realm=test-realm",
    "frontend.auth.url=http://test.url",
  }
)
class EnvironmentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Nested
  class getEnvironment {

    @Test
    void itReturnsTheConfigurationOfTheEnvironment() throws Exception {
      // When / Then
      mockMvc
        .perform(get("/environment"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("name").value("test"))
        .andExpect(jsonPath("authClientId").value("test-client"))
        .andExpect(jsonPath("authRealm").value("test-realm"))
        .andExpect(jsonPath("authUrl").value("http://test.url"));
    }
  }
}
