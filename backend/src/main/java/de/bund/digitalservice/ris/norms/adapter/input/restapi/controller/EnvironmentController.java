package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.EnvironmentResponseSchema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for environment-related information that the frontend can use
 * to configure itself at runtime. Since this is a technical endpoint similar
 * to /actuator and not related to our domain, it is not prefixed with /api.
 */
@RestController
@RequestMapping("/environment")
public class EnvironmentController {

  @Value("${sentry.environment:local}")
  private String environmentName;

  @Value("${frontend.auth.clientId:ris-norms-local}")
  private String frontendAuthClientId;

  @Value("${frontend.auth.realm:ris}")
  private String frontendAuthRealm;

  @Value("${frontend.auth.url:http://localhost:8443}")
  private String frontendAuthUrl;

  /**
   * Returns information and configuration details for the current environment.
   *
   * @return A {@link ResponseEntity} containing the environment details.
   */
  @GetMapping(produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<EnvironmentResponseSchema> getEnvironment() {
    var info = new EnvironmentResponseSchema(
      environmentName,
      frontendAuthClientId,
      frontendAuthUrl,
      frontendAuthRealm
    );

    return ResponseEntity.ok(info);
  }
}
