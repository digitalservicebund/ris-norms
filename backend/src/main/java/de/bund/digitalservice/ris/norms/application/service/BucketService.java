package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.io.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

/**
 * Service class responsible for uploading XML documents to private and public S3 buckets.
 * This service interacts with the AWS S3 service using configured S3 clients for each bucket.
 */
@Service
@Profile({ "staging", "uat", "production" })
public class BucketService {

  @Value("${otc.obs.private.bucket-name}")
  private String privateBucketName;

  @Value("${otc.obs.public.bucket-name}")
  private String publicBucketName;

  private final S3Client privateS3Client;
  private final S3Client publicS3Client;

  public BucketService(
    @Qualifier("privateS3Client") S3Client privateS3Client,
    @Qualifier("publicS3Client") S3Client publicS3Client
  ) {
    this.privateS3Client = privateS3Client;
    this.publicS3Client = publicS3Client;
  }

  /**
   * Uploads a given Norm document to the private S3 bucket.
   *
   * @param norm the Norm object containing the document to upload
   */
  public void uploadToPrivateBucket(final Norm norm) {
    uploadToBucket(privateS3Client, privateBucketName, norm);
  }

  /**
   * Uploads a given Norm document to the public S3 bucket.
   *
   * @param norm the Norm object containing the document to upload
   */
  public void uploadToPublicBucket(final Norm norm) {
    uploadToBucket(publicS3Client, publicBucketName, norm);
  }

  private void uploadToBucket(final S3Client s3Client, final String bucketName, final Norm norm) {
    try {
      InputStream inputStream = convertDocumentToInputStream(norm.getDocument());
      PutObjectRequest request = PutObjectRequest
        .builder()
        .bucket(bucketName)
        .key(norm.getManifestationEli().toString())
        .build();
      s3Client.putObject(
        request,
        RequestBody.fromInputStream(inputStream, inputStream.available())
      );
    } catch (final TransformerException | IOException e) {
      // TODO
      e.printStackTrace();
    }
  }

  private InputStream convertDocumentToInputStream(final Document document)
    throws TransformerException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Transformer transformer = TransformerFactory.newInstance().newTransformer();
    transformer.transform(new DOMSource(document), new StreamResult(outputStream));
    return new ByteArrayInputStream(outputStream.toByteArray());
  }
}
