package de.bund.digitalservice.ris.norms.config;

import java.net.URI;
import java.net.URISyntaxException;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.checksums.RequestChecksumCalculation;
import software.amazon.awssdk.core.checksums.ResponseChecksumValidation;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * Utility class for creating preconfigured {@link S3Client} instances for any S3-compatible object storage.
 *
 * <p>This class centralizes S3 client creation logic and applies common configuration such as:
 * <ul>
 *   <li>Static credentials with access key and secret</li>
 *   <li>Custom endpoint override</li>
 *   <li>Checksum calculation and validation</li>
 *   <li>Region set to "eu-de"</li>
 * </ul>
 *
 * <p>Intended for use in Spring configuration classes that need to declare S3 clients as beans.
 */
public class S3ClientFactory {

  private S3ClientFactory() {
    // Should not be instantiated as an object
  }

  /**
   * Creates a new {@link S3Client} instance using the provided credentials and endpoint.
   *
   * @param accessKeyId the access key ID for authentication
   * @param secretAccessKey the secret access key for authentication
   * @param endpoint the OTC S3-compatible endpoint to use
   * @return a configured {@link S3Client} instance
   * @throws URISyntaxException if the endpoint string is not a valid URI
   */
  public static S3Client create(
    final String accessKeyId,
    final String secretAccessKey,
    final String endpoint
  ) throws URISyntaxException {
    return S3Client.builder()
      .credentialsProvider(
        StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretAccessKey))
      )
      .endpointOverride(new URI(endpoint))
      .region(Region.of("eu-de"))
      .responseChecksumValidation(ResponseChecksumValidation.WHEN_REQUIRED)
      .requestChecksumCalculation(RequestChecksumCalculation.WHEN_REQUIRED)
      .build();
  }
}
