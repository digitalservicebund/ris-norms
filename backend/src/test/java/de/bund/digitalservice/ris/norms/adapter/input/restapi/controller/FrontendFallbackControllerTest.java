package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser
@WebMvcTest(
  controllers = FrontendFallbackController.class,
  excludeAutoConfiguration = OAuth2ClientAutoConfiguration.class
)
class FrontendFallbackControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void itShouldRedirectToTheFrontendForRoot() throws Exception {
    mockMvc
      .perform(get("/"))
      .andExpect(status().is3xxRedirection())
      .andExpect(redirectedUrl("/app/"));
  }

  @Test
  void itShouldServeTheFrontendWhenRequestingApp() throws Exception {
    mockMvc
      .perform(get("/app/"))
      .andExpect(status().is2xxSuccessful())
      .andExpect(forwardedUrl("/app/index.html"));
  }

  @Test
  void itShouldServeTheFrontendWhenRequestingAPathUnderneathApp() throws Exception {
    mockMvc
      .perform(get("/app/amending-laws"))
      .andExpect(status().is2xxSuccessful())
      .andExpect(forwardedUrl("/app/index.html"));
  }

  @Test
  void itShouldServeAssets() throws Exception {
    // If we had our frontend as static files in the backend this would be a 200 OK,
    // but we don't when our unit tests run so its an error. Doesn't matter for the
    // purpose of this test, the important part is that it's not handled by the
    // fallback controller.
    mockMvc.perform(get("/app/favicon.svg")).andExpect(status().is5xxServerError());
  }
}
