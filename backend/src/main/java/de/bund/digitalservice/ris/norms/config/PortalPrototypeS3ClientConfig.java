package de.bund.digitalservice.ris.norms.config;

import java.net.URISyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * Configuration class for setting up Amazon S3 clients for the portal prototype bucket.
 *
 * <p>The configuration properties for the S3 clients are loaded from the application properties file.</p>
 *
 * For local development or testing the {@link MockPortalPrototypeS3ClientConfig} can be used instead.
 */
@Slf4j
@Configuration
@ConditionalOnProperty(value = "otc.obs.prototype.access-key-id")
@ConditionalOnProperty("publish.portal-prototype.enabled")
public class PortalPrototypeS3ClientConfig {

  @Value("${otc.obs.endpoint}")
  private String endpoint;

  // Portal prototype bucket
  @Value("${otc.obs.prototype.access-key-id}")
  private String portalPrototypeAccessKeyId;

  @Value("${otc.obs.prototype.secret-access-key}")
  private String portalPrototypeSecretAccessKey;

  public PortalPrototypeS3ClientConfig() {
    log.info("Using normal S3Client for portal-prototype bucket");
  }

  /**
   * Creates a {@link S3Client} for accessing the portal-prototype S3 bucket.
   *
   * @return an instance of {@link S3Client} configured for the portal-prototype bucket.
   * @throws URISyntaxException if the endpoint URI is malformed.
   */
  @Bean(name = "portalPrototypeS3Client")
  public S3Client portalPrototypeS3Client() throws URISyntaxException {
    return S3ClientFactory.create(
      portalPrototypeAccessKeyId,
      portalPrototypeSecretAccessKey,
      endpoint
    );
  }
}
