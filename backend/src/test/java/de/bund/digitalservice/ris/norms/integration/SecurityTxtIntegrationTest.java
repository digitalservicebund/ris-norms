package de.bund.digitalservice.ris.norms.integration;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

class SecurityTxtIntegrationTest extends BaseIntegrationTest {

  @Test
  void shouldExposeSecurityTxt(@Autowired MockMvc mvc) throws Exception {
    mvc.perform(get("/.well-known/security.txt"))
        .andExpectAll(
            status().isOk(),
            header().stringValues("Content-Type", MediaType.TEXT_PLAIN.toString()),
            content().string(containsString("Contact: ")));
  }
}
