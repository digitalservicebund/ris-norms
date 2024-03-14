package de.bund.digitalservice.ris.norms.integration;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/**
 * This class is a base class for integration tests which require a PostgreSQL database
 * testcontainer. The usage of {@code @DirtiesContext} needed because {@code @SpringBootTest} is
 * reusing Spring context between tests, meaning there is a common Hikari Pool between tests. But
 * testcontainer is killing previous container and creating new one. SpringBootTest is not aware of
 * that change what results in a new postgres container with the same Hikari Pool as the previous
 * test, which is pointing to the already used an unavailable port. Using {@code @DirtiesContext}
 * tells the test framework to close and recreate the context.
 */
@DirtiesContext
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@Testcontainers(disabledWithoutDocker = true)
@Tag("integration")
public abstract class BaseIntegrationTest {

  @Container
  static final PostgreSQLContainer<?> postgreSQLContainer =
      new PostgreSQLContainer<>("postgres:14")
          .withDatabaseName("postgres")
          .withUsername("postgres")
          .withPassword("pass");

  @Container
  static final GenericContainer<?> redis =
      new GenericContainer<>(DockerImageName.parse("cgr.dev/chainguard/redis"))
          .withExposedPorts(6379);

  @DynamicPropertySource
  static void registerDynamicProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    registry.add(
        "spring.datasource.url",
        () ->
            String.format(
                "jdbc:postgresql://localhost:%d/%s",
                postgreSQLContainer.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT),
                postgreSQLContainer.getDatabaseName()));

    registry.add("spring.data.redis.host", redis::getHost);
    registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379).toString());
    // Unsetting default values from application.yaml
    registry.add("spring.data.redis.username", () -> "");
    registry.add("spring.data.redis.password", () -> "");
  }
}
