package de.bund.digitalservice.template

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SecurityTxtIntegrationTest(
    @Autowired val webTestClient: WebTestClient,
) {
    fun getSecurityTxt() = webTestClient.get().uri("/.well-known/security.txt").exchange()

    @Test
    fun `should expose security txt at well known location`() {
        getSecurityTxt()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.TEXT_PLAIN)
            .expectBody().consumeWith { response ->
                Assertions.assertThat(response.responseBody).isNotEmpty
            }
    }
}
