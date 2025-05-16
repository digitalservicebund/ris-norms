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
 * This class defines beans for the S3 clients based on the active Spring profile. For local development and tests a mock client is used for both buckets.
 *
 * <p>The configuration properties for the S3 clients are loaded from the application properties file.</p>
 */
@Configuration
public class OtcObsConfig {

  @Value("${otc.obs.endpoint}")
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

  // Bucket for storing packages from E-Verkündung
  @Value("${otc.obs.everkuendung.access-key-id}")
  private String eVerkuendungAccessKeyId;

  @Value("${otc.obs.everkuendung.secret-access-key}")
  private String eVerkuendungSecretAccessKey;

  /**
   * Creates a {@link S3Client} for accessing the private S3 bucket.
   *
   * <p>This bean is only available in the "staging", "uat", and "production" profiles.</p>
   *
   * @return an instance of {@link S3Client} configured for the private bucket.
   * @throws URISyntaxException if the endpoint URI is malformed.
   */
  @Bean(name = "privateS3Client")
  @Profile({ "staging", "uat", "production" })
  public S3Client privateS3Client() throws URISyntaxException {
    return createS3Client(privateAccessKeyId, privateSecretAccessKey);
  }

  /**
   * Creates a {@link S3Client} for accessing the public S3 bucket.
   *
   * <p>This bean is only available in the "staging", "uat", and "production" profiles.</p>
   *
   * @return an instance of {@link S3Client} configured for the public bucket.
   * @throws URISyntaxException if the endpoint URI is malformed.
   */
  @Bean(name = "publicS3Client")
  @Profile({ "staging", "uat", "production" })
  public S3Client publicS3Client() throws URISyntaxException {
    return createS3Client(publicAccessKeyId, publicSecretAccessKey);
  }

  /**
   * Creates a {@link S3Client} for accessing the eVerkündungs S3 bucket.
   *
   * <p>This bean is only available in the "staging", "uat", and "production" profiles.</p>
   *
   * @return an instance of {@link S3Client} configured for the eVerkündungs bucket.
   * @throws URISyntaxException if the endpoint URI is malformed.
   */
  @Bean(name = "eVerkuendungS3Client")
  @Profile({ "staging", "uat", "production" })
  public S3Client eVerkuendungS3Client() throws URISyntaxException {
    return createS3Client(eVerkuendungAccessKeyId, eVerkuendungSecretAccessKey);
  }

  /**
   * Creates a mock S3 client for the private client for local development and testing purposes.
   *
   * <p>This bean is available when the active profile is not "staging", "uat", or "production".</p>
   *
   * @return an instance of {@link S3MockClient} for local testing.
   */
  @Bean(name = "privateS3Client")
  @Profile("!staging & !uat & !production")
  public S3Client privateS3MockClient() {
    return new S3MockClient();
  }

  /**
   * Creates a mock S3 client for the public client for local development and testing purposes.
   *
   * <p>This bean is available when the active profile is not "staging", "uat", or "production".</p>
   *
   * @return an instance of {@link S3MockClient} for local testing.
   */
  @Bean(name = "publicS3Client")
  @Profile("!staging & !uat & !production")
  public S3Client publicS3MockClient() {
    return new S3MockClient();
  }

  /**
   * Creates a mock S3 client for the eVerkuendung client for local development and testing purposes.
   *
   * <p>This bean is available when the active profile is not "staging", "uat", or "production".</p>
   *
   * @return an instance of {@link S3MockClient} for local testing.
   */
  @Bean(name = "eVerkuendungS3Client")
  @Profile("!staging & !uat & !production")
  public S3Client eVerkuendungS3MockClient() {
    return new S3MockClient();
  }

  private S3Client createS3Client(final String accessKeyId, final String secretAccessKey)
    throws URISyntaxException {
    return S3Client.builder()
      .credentialsProvider(
        StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretAccessKey))
      )
      .endpointOverride(new URI(endpoint))
      .region(Region.of("eu-de"))
      .build();
  }
}
