package de.bund.digitalservice.template

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HealthEndpointIntegrationTest(
    @Autowired val webTestClient: WebTestClient,
) {
    @Test
    fun `should expose health endpoint`() {
        webTestClient.get().uri("/actuator/health").exchange().expectStatus().isOk()
    }
}
