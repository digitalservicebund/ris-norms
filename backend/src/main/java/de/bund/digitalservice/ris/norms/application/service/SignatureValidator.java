package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.InvalidSignatureException;
import de.bund.digitalservice.ris.norms.application.exception.SignatureProcessingException;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * Service responsible for validating digital SHA256withRSA signatures using a public key certificate.
 * This is a fail-fast approach: if any error occurs while setting up the signature verification (such as an invalid
 * certificate, incorrect file path, or unsupported signature algorithm), the constructor will throw an exception.
 * This ensures that the service is fully configured and ready for use before any signature validation attempts.
 */
@Service
public class SignatureValidator {

  private final Signature signature;

  public SignatureValidator(
    @Value("${everkuendung.certificate.path}") Resource certificateResource
  ) {
    try {
      try (InputStream in = certificateResource.getInputStream()) {
        final CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        final X509Certificate certificate =
          (X509Certificate) certificateFactory.generateCertificate(in);

        certificate.checkValidity();

        final PublicKey publicKey = certificate.getPublicKey();

        this.signature = Signature.getInstance("SHA256withRSA");
        this.signature.initVerify(publicKey);
      }
    } catch (
      IOException | CertificateException | NoSuchAlgorithmException | InvalidKeyException e
    ) {
      throw new IllegalStateException("Signature validator misconfigured", e);
    }
  }

  /**
   * Validates that the given digital signature matches the provided data using the configured public key.
   *
   * @param data           the original data that was signed
   * @param signatureBytes the digital signature to verify
   *
   * @throws InvalidSignatureException      if the signature is valid in structure but does not match the data
   * @throws SignatureProcessingException   if an internal error occurs during the signature verification process
   */
  public void validate(byte[] data, byte[] signatureBytes) {
    try {
      signature.update(data);
      final boolean valid = signature.verify(signatureBytes);
      if (!valid) {
        throw new InvalidSignatureException();
      }
    } catch (SignatureException e) {
      throw new SignatureProcessingException();
    }
  }
}
