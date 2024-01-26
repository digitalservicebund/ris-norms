package de.bund.digitalservice.ris.norms;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Tag("integration")
class SecurityTxtIntegrationTest {

  @Test
  void shouldExposeSecurityTxt(@Autowired MockMvc mvc) throws Exception {
    mvc.perform(get("/.well-known/security.txt"))
        .andExpectAll(
            status().isOk(),
            header().stringValues("Content-Type", MediaType.TEXT_PLAIN.toString()),
            content().string(containsString("Contact: ")));
  }
}
