package de.bund.digitalservice.template;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Tag("integration")
class SecurityTxtIntegrationTest {

  @Autowired WebTestClient webTestClient;

  @Test
  void shouldExposeSecurityTxt() {
    webTestClient
        .get()
        .uri("/.well-known/security.txt")
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.TEXT_PLAIN)
        .expectBody()
        .consumeWith(response -> Assertions.assertThat(response.getResponseBody()).isNotEmpty());
  }
}
