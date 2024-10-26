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
 * Configuration class for setting up Amazon S3 clients for private and public buckets.
 * This class defines beans for the S3 clients based on the active Spring profile.
 *
 * <p>The configuration properties for the S3 clients are loaded from the application properties file.</p>
 */
@Configuration
public class OtcObsConfig {

  @Value("${otc.obs.endpoint:https://obs.eu-de.otc.t-systems.com}")
  private String endpoint;

  // Private bucket
  @Value("${otc.obs.private.access-key-id}")
  private String privateAccessKeyId;

  @Value("${otc.obs.private.secret-access-key}")
  private String privateSecretAccessKey;

  // Public bucket
  @Value("${otc.obs.public.access-key-id}")
  private String publicAccessKeyId;

  @Value("${otc.obs.public.secret-access-key}")
  private String publicSecretAccessKey;

  /**
   * Creates a {@link S3Client} for accessing the private S3 bucket.
   *
   * <p>This bean is only available in the "staging", "uat", and "production" profiles.</p>
   *
   * @return an instance of {@link S3Client} configured for the private bucket.
   * @throws URISyntaxException if the endpoint URI is malformed.
   */
  @Bean
  @Profile({ "staging", "uat", "production" })
  public S3Client privateS3Client() throws URISyntaxException {
    return S3Client
      .builder()
      .credentialsProvider(
        StaticCredentialsProvider.create(
          AwsBasicCredentials.create(privateAccessKeyId, privateSecretAccessKey)
        )
      )
      .endpointOverride(new URI(endpoint))
      .region(Region.of("eu-de"))
      .build();
  }

  /**
   * Creates a {@link S3Client} for accessing the public S3 bucket.
   *
   * <p>This bean is only available in the "staging", "uat", and "production" profiles.</p>
   *
   * @return an instance of {@link S3Client} configured for the public bucket.
   * @throws URISyntaxException if the endpoint URI is malformed.
   */
  @Bean
  @Profile({ "staging", "uat", "production" })
  public S3Client publicS3Client() throws URISyntaxException {
    return S3Client
      .builder()
      .credentialsProvider(
        StaticCredentialsProvider.create(
          AwsBasicCredentials.create(publicAccessKeyId, publicSecretAccessKey)
        )
      )
      .endpointOverride(new URI(endpoint))
      .region(Region.of("eu-de"))
      .build();
  }

  /**
   * Creates a mock S3 client for testing purposes.
   *
   * <p>This bean is available when the active profile is not "staging", "uat", or "production".</p>
   *
   * @return an instance of {@link S3MockClient} for local testing.
   */
  @Bean
  @Profile({ "!staging & !uat & !production" })
  public S3Client amazonS3Mock() {
    return new S3MockClient();
  }
}
