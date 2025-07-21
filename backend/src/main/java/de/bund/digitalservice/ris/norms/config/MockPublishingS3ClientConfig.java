package de.bund.digitalservice.ris.norms.config;

import de.bund.digitalservice.ris.norms.adapter.output.s3.S3MockClient;
import de.bund.digitalservice.ris.norms.utils.ConditionalOnMissingProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * Configuration class for setting up mocked S3 clients for private and public buckets for testing and local development
 * purposes.
 * <p>
 * The actual implementation is provided by {@link PublishingS3ClientConfig}
 */
@Slf4j
@Configuration
@ConditionalOnMissingProperty(value = "otc.obs.public.access-key-id")
@ConditionalOnProperty("publish.enabled")
public class MockPublishingS3ClientConfig {

  /**
   * Creates a mock S3 client for the private client for local development and testing purposes.
   *
   * @return an instance of {@link S3MockClient} for local testing.
   */
  @Bean(name = "privateS3Client")
  public S3Client privateS3MockClient() {
    log.warn("Using mock S3Client for private bucket used for publishing");
    return new S3MockClient();
  }

  /**
   * Creates a mock S3 client for the public client for local development and testing purposes.
   *
   * @return an instance of {@link S3MockClient} for local testing.
   */
  @Bean(name = "publicS3Client")
  public S3Client publicS3MockClient() {
    log.warn("Using mock S3Client for public bucket used for publishing");
    return new S3MockClient();
  }
}
