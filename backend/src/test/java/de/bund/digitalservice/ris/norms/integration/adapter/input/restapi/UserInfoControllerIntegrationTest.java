package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class UserInfoControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    OAuth2User oAuth2User = new DefaultOAuth2User(
      Collections.singletonList(() -> "ROLE_USER"),
      Map.of("name", "testuser"),
      "name"
    );
    SecurityContextHolder
      .getContext()
      .setAuthentication(
        new OAuth2AuthenticationToken(
          oAuth2User,
          Collections.singletonList(() -> "ROLE_USER"),
          "client-id"
        )
      );
  }

  @Test
  void getUserInfo_ShouldReturnUserInfo() throws Exception {
    mockMvc
      .perform(get("/api/v1/me"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("testuser"));
  }
}
