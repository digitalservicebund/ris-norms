package de.bund.digitalservice.ris.norms.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

class OidcIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mvc;

  @Test
  void noAccessIfNotLoggedIn() throws Exception {
    // when
    mvc
      .perform(get("/api/v1/announcements").accept(MediaType.APPLICATION_JSON))
      // then
      .andExpect(status().isUnauthorized());
  }

  @Test
  void shouldServeFrontendWhenLoggedOut() throws Exception {
    // when
    mvc
      .perform(get("/amending-laws").accept(MediaType.TEXT_HTML))
      // then
      .andExpect(status().is2xxSuccessful());
  }
}
