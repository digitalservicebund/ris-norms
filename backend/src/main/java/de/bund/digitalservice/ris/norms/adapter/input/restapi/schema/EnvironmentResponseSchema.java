package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

/**
 * Information about the environment that can be shared with the frontend.
 *
 * @param name         Name of the environment
 * @param authClientId Client ID that should be used when authenticating
 * @param authUrl      URL of the authentication service
 * @param authRealm    Realm that should be used when authenticating
 */
public record EnvironmentResponseSchema(
  String name,
  String authClientId,
  String authUrl,
  String authRealm
) {}
