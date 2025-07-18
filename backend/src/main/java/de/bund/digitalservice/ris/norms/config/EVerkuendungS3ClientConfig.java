package de.bund.digitalservice.ris.norms.config;

import java.net.URISyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * Configuration class for setting up Amazon S3 clients for the e-Verkündung bucket.
 *
 * <p>The configuration properties for the S3 clients are loaded from the application properties file.</p>
 *
 * For local development or testing the {@link MockEVerkuendungS3ClientConfig} can be used instead.
 */
@Slf4j
@Configuration
@ConditionalOnProperty(value = "otc.obs.everkuendung.access-key-id")
public class EVerkuendungS3ClientConfig {

  @Value("${otc.obs.endpoint}")
  private String endpoint;

  // Bucket for storing packages from E-Verkündung
  @Value("${otc.obs.everkuendung.access-key-id}")
  private String eVerkuendungAccessKeyId;

  @Value("${otc.obs.everkuendung.secret-access-key}")
  private String eVerkuendungSecretAccessKey;

  public EVerkuendungS3ClientConfig() {
    log.info("Using normal S3Client for e-verkuendung bucket");
  }

  /**
   * Creates a {@link S3Client} for accessing the eVerkündungs S3 bucket.
   *
   * @return an instance of {@link S3Client} configured for the eVerkündungs bucket.
   * @throws URISyntaxException if the endpoint URI is malformed.
   */
  @Bean(name = "eVerkuendungS3Client")
  public S3Client eVerkuendungS3Client() throws URISyntaxException {
    return S3ClientFactory.create(eVerkuendungAccessKeyId, eVerkuendungSecretAccessKey, endpoint);
  }
}
