package de.bund.digitalservice.ris.norms.config;

import de.bund.digitalservice.ris.norms.adapter.output.s3.S3MockClient;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * Configuration class for setting up Amazon S3 clients for the portal prototype bucket.
 * This class defines beans for the S3 clients based on the active Spring profile. For local development and tests a mock client is used for both buckets.
 *
 * <p>The configuration properties for the S3 clients are loaded from the application properties file.</p>
 */
@Profile({ "production", "local", "local-docker" })
@Configuration
public class PortalPrototypeOtcObsConfig {

  @Value("${otc.obs.endpoint}")
  private String endpoint;

  // Portal prototype bucket
  @Value("${otc.obs.portal-prototype.access-key-id}")
  private String portalPrototypeAccessKeyId;

  @Value("${otc.obs.portal-prototype.secret-access-key}")
  private String portalPrototypeSecretAccessKey;

  /**
   * Creates a {@link S3Client} for accessing the portal-prototype S3 bucket.
   *
   * <p>This bean is only available in the "production" profile.</p>
   *
   * @return an instance of {@link S3Client} configured for the portal-prototype bucket.
   * @throws URISyntaxException if the endpoint URI is malformed.
   */
  @Bean(name = "portalPrototypeS3Client")
  @Profile({ "production" })
  public S3Client portalPrototypeS3Client() throws URISyntaxException {
    return createS3Client(portalPrototypeAccessKeyId, portalPrototypeSecretAccessKey);
  }

  /**
   * Creates a mock S3 client for the portal prototype client for local development and testing purposes.
   *
   * <p>This bean is available when the active profile is not "staging", "uat", or "production".</p>
   *
   * @return an instance of {@link S3MockClient} for local testing.
   */
  @Bean(name = "portalPrototypeS3Client")
  @Profile("!staging & !uat & !production")
  public S3Client portalPrototypeS3MockClient() {
    return new S3MockClient();
  }

  private S3Client createS3Client(final String accessKeyId, final String secretAccessKey)
    throws URISyntaxException {
    return S3Client
      .builder()
      .credentialsProvider(
        StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretAccessKey))
      )
      .endpointOverride(new URI(endpoint))
      .region(Region.of("eu-de"))
      .build();
  }
}
