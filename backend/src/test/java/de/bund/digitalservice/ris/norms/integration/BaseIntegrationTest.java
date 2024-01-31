package de.bund.digitalservice.ris.norms.integration;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers(disabledWithoutDocker = true)
@Tag("integration")
abstract class BaseIntegrationTest {

  @Container
  static PostgreSQLContainer<?> postgreSQLContainer =
      new PostgreSQLContainer<>("postgres:16")
          .withExposedPorts(5432)
          .withDatabaseName("postgres")
          .withUsername("postgres")
          .withPassword("pass");

  @DynamicPropertySource
  static void registerDynamicProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.username", () -> postgreSQLContainer.getUsername());
    registry.add("spring.datasource.password", () -> postgreSQLContainer.getPassword());
    registry.add(
        "spring.datasource.url",
        () ->
            String.format(
                "jdbc:postgresql://localhost:%d/postgres",
                postgreSQLContainer.getFirstMappedPort()));
    registry.add("database.database", () -> postgreSQLContainer.getDatabaseName());
  }
}
