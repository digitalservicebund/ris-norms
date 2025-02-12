package de.bund.digitalservice.ris.norms.integration;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

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
@SpringBootTest(
  webEnvironment = WebEnvironment.RANDOM_PORT,
  properties = {
    "spring.flyway.locations=classpath:db/migration",
    "local.file-storage=" + BaseIntegrationTest.LOCAL_STORAGE_PATH,
  }
)
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@Testcontainers(disabledWithoutDocker = true)
@Tag("integration")
public abstract class BaseIntegrationTest {

  protected static final KeycloakContainer keycloak;
  protected static final String LOCAL_STORAGE_PATH = ".local-storage-integration-test=";

  @Container
  static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14")
    .withDatabaseName("postgres")
    .withUsername("postgres")
    .withPassword("pass");

  static {
    keycloak =
    new KeycloakContainer()
      .withRealmImportFile("de/bund/digitalservice/ris/norms/keycloak/realm-export.json");
    keycloak.start();
  }

  @DynamicPropertySource
  static void registerDynamicProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    registry.add(
      "spring.datasource.url",
      () ->
        "jdbc:postgresql://localhost:%d/%s".formatted(
            postgreSQLContainer.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT),
            postgreSQLContainer.getDatabaseName()
          )
    );

    registry.add(
      "spring.security.oauth2.resourceserver.jwt.issuer-uri",
      () -> keycloak.getAuthServerUrl() + "/realms/testing"
    );
  }
}
