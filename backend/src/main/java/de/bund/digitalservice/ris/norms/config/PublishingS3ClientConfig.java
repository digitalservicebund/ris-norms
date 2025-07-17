package de.bund.digitalservice.ris.norms.config;

import java.net.URISyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * Configuration class for setting up Amazon S3 clients for private and public buckets.
 *
 * <p>The configuration properties for the S3 clients are loaded from the application properties file.</p>
 *
 * For local development or testing the {@link MockPublishingS3ClientConfig} can be used instead.
 */
@Slf4j
@Configuration
@ConditionalOnProperty(value = "otc.obs.public.access-key-id")
public class PublishingS3ClientConfig {

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

  public PublishingS3ClientConfig() {
    log.info("Using normal S3Client for public and private buckets used for publishing");
  }

  /**
   * Creates a {@link S3Client} for accessing the private S3 bucket.
   *
   * @return an instance of {@link S3Client} configured for the private bucket.
   * @throws URISyntaxException if the endpoint URI is malformed.
   */
  @Bean(name = "privateS3Client")
  public S3Client privateS3Client() throws URISyntaxException {
    return S3ClientFactory.create(privateAccessKeyId, privateSecretAccessKey, endpoint);
  }

  /**
   * Creates a {@link S3Client} for accessing the public S3 bucket.
   *
   * @return an instance of {@link S3Client} configured for the public bucket.
   * @throws URISyntaxException if the endpoint URI is malformed.
   */
  @Bean(name = "publicS3Client")
  public S3Client publicS3Client() throws URISyntaxException {
    return S3ClientFactory.create(publicAccessKeyId, publicSecretAccessKey, endpoint);
  }
}
