package de.bund.digitalservice.ris.norms.config;

import de.bund.digitalservice.ris.norms.adapter.output.s3.S3MockClient;
import de.bund.digitalservice.ris.norms.utils.ConditionalOnMissingProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * Configuration class for setting up mocked S3 clients for the portal prototype bucket for testing and local
 * development purposes.
 * <p>
 * The actual implementation is provided by {@link PortalPrototypeS3ClientConfig}
 */
@Slf4j
@Configuration
@ConditionalOnMissingProperty("otc.obs.prototype.access-key-id")
public class MockPortalPrototypeS3ClientConfig {

  /**
   * Creates a mock S3 client for the portal prototype client for local development and testing purposes.
   *
   * @return an instance of {@link S3MockClient} for local testing.
   */
  @Bean(name = "portalPrototypeS3Client")
  public S3Client portalPrototypeS3MockClient() {
    log.warn("Using mock S3Client for portal-prototype bucket");
    return new S3MockClient();
  }
}
