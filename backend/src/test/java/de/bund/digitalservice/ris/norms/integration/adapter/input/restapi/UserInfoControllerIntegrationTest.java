package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

class UserInfoControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  MockMvc mvc;

  @Test
  void getUserInfo() throws Exception {
    mvc
      .perform(get("/api/v1/me").with(user("user")).accept(MediaType.APPLICATION_JSON))
      // then
      .andExpect(status().isOk())
      .andExpect(jsonPath("name").value("user"));
  }
}
