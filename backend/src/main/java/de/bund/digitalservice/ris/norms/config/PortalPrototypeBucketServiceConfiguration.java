package de.bund.digitalservice.ris.norms.config;

import de.bund.digitalservice.ris.norms.adapter.output.s3.BucketService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * This provides the {@link BucketService}s for the public and private S3Buckets.
 *
 * <p>Configuration:</p>
 * <ul>
 *   <li>The bucket names are configured through application properties:
 *       <ul>
 *         <li>{@code otc.obs.portal-prototype.bucket-name} for the private S3 bucket.</li>
 *       </ul>
 *   </li>
 *   <li>S3 clients are injected with qualifiers {@code privateS3Client} and {@code publicS3Client}.</li>
 * </ul>
 */
@Configuration
@Profile({ "production", "local", "local-docker" })
public class PortalPrototypeBucketServiceConfiguration {

  @Value("${otc.obs.portal-prototype.bucket-name}")
  private String bucketName;

  private final S3Client s3Client;

  public PortalPrototypeBucketServiceConfiguration(
    @Qualifier("portalPrototypeS3Client") S3Client s3Client
  ) {
    this.s3Client = s3Client;
  }

  @Bean(name = "portalPrototype")
  public BucketService portalPrototypeBucketService() {
    return new BucketService(s3Client, bucketName);
  }
}
