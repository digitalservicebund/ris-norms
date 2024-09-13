package de.bund.digitalservice.ris.norms.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.web.servlet.MockMvc;

class SessionIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Test
  void sessionShouldBePersistedToRedis(@Autowired MockMvc mvc) throws Exception {
    final Set<String> keysBefore = redisTemplate.keys("*");
    assertThat(keysBefore).isEmpty();

    mvc.perform(get("/actuator/health")).andExpect(status().isOk());

    final Set<String> keyAfter = redisTemplate.keys("*");
    assertThat(keyAfter).hasSize(1);
  }

  @Test
  void setCookieHeaderShouldBePresentInResponse(@Autowired MockMvc mvc) throws Exception {
    final HttpServletResponse response = mvc
      .perform(get("/actuator/health"))
      .andExpect(status().isOk())
      .andReturn()
      .getResponse();

    final String setCookieHeader = response.getHeader("Set-Cookie");
    assertThat(setCookieHeader)
      .isNotNull()
      .contains("SESSION=")
      .contains("; Max-Age=43200")
      .contains("; Expires=")
      .contains("; HttpOnly")
      .contains("; SameSite=Lax");
  }
}
