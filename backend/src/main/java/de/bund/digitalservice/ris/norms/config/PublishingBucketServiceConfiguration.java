package de.bund.digitalservice.ris.norms.config;

import de.bund.digitalservice.ris.norms.adapter.output.s3.BucketService;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * This provides the {@link BucketService}s for the public and private S3Buckets.
 *
 * <p>Configuration:</p>
 * <ul>
 *   <li>The bucket names are configured through application properties:
 *       <ul>
 *         <li>{@code otc.obs.private.bucket-name} for the private S3 bucket.</li>
 *         <li>{@code otc.obs.public.bucket-name} for the public S3 bucket.</li>
 *       </ul>
 *   </li>
 *   <li>S3 clients are injected with qualifiers {@code privateS3Client} and {@code publicS3Client}.</li>
 * </ul>
 */
@Configuration
@NullMarked
@ConditionalOnProperty("publish.enabled")
public class PublishingBucketServiceConfiguration {

  private final String privateBucketName;
  private final String publicBucketName;
  private final S3Client privateS3Client;
  private final S3Client publicS3Client;

  public PublishingBucketServiceConfiguration(
    @Value("${otc.obs.private.bucket-name}") String privateBucketName,
    @Value("${otc.obs.public.bucket-name}") String publicBucketName,
    @Qualifier("privateS3Client") S3Client privateS3Client,
    @Qualifier("publicS3Client") S3Client publicS3Client
  ) {
    this.privateBucketName = privateBucketName;
    this.publicBucketName = publicBucketName;
    this.privateS3Client = privateS3Client;
    this.publicS3Client = publicS3Client;
  }

  @Bean(name = "public")
  public BucketService publicBucketService() {
    return new BucketService(publicS3Client, publicBucketName);
  }

  @Bean(name = "private")
  public BucketService privateBucketService() {
    return new BucketService(privateS3Client, privateBucketName);
  }
}
