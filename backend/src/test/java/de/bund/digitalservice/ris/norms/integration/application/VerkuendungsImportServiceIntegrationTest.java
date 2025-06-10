package de.bund.digitalservice.ris.norms.integration.application;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungImportProcessDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.BinaryFileRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.VerkuendungImportProcessesRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.VerkuendungRepository;
import de.bund.digitalservice.ris.norms.application.port.input.ProcessNormendokumentationspaketUseCase;
import de.bund.digitalservice.ris.norms.application.service.VerkuendungsImportService;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.integration.BaseS3MockIntegrationTest;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

class VerkuendungsImportServiceIntegrationTest extends BaseS3MockIntegrationTest {

  @Autowired
  private VerkuendungsImportService verkuendungsImportService;

  @Autowired
  private VerkuendungImportProcessesRepository verkuendungImportProcessesRepository;

  @Autowired
  private DokumentRepository dokumentRepository;

  @Autowired
  private BinaryFileRepository binaryFileRepository;

  @Autowired
  private VerkuendungRepository verkuendungRepository;

  @Autowired
  private NormManifestationRepository normManifestationRepository;

  @BeforeEach
  void beforeEach() {
    verkuendungRepository.deleteAll();
    verkuendungImportProcessesRepository.deleteAll();
    dokumentRepository.deleteAll();
    binaryFileRepository.deleteAll();
    normManifestationRepository.deleteAll();
  }

  @Test
  void processNormendokumentationspaket()
    throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {
    var processId = UUID.randomUUID();
    storeFolderAsNormendokumentationsPaket(processId, "verkuendung-valid");

    verkuendungImportProcessesRepository.save(
      new VerkuendungImportProcessDto(
        processId,
        VerkuendungImportProcessDto.Status.CREATED,
        Instant.now(),
        null,
        null,
        null
      )
    );

    verkuendungsImportService.processNormendokumentationspaket(
      new ProcessNormendokumentationspaketUseCase.ProcessOptions(processId)
    );

    var finishedProcess = verkuendungImportProcessesRepository.findById(processId);
    assertThat(finishedProcess).isPresent();
    assertThat(finishedProcess.get().getStatus()).isEqualTo(
      VerkuendungImportProcessDto.Status.SUCCESS
    );

    assertThat(dokumentRepository.findAll()).hasSize(2);
    assertThat(binaryFileRepository.findAll()).hasSize(1);
    assertThat(verkuendungRepository.findAll())
      .hasSize(1)
      .anySatisfy(verkuendungDto ->
        assertThat(verkuendungDto.getEliNormExpression()).isEqualTo(
          "eli/bund/bgbl-1/2024/107/2024-03-27/1/deu"
        )
      );
  }

  @Test
  void processNormendokumentationspaketWithBekanntmachung()
    throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {
    var processId = UUID.randomUUID();
    storeFolderAsNormendokumentationsPaket(processId, "verkuendung-valid-bekanntmachung");

    verkuendungImportProcessesRepository.save(
      new VerkuendungImportProcessDto(
        processId,
        VerkuendungImportProcessDto.Status.CREATED,
        Instant.now(),
        null,
        null,
        null
      )
    );

    verkuendungsImportService.processNormendokumentationspaket(
      new ProcessNormendokumentationspaketUseCase.ProcessOptions(processId)
    );

    var finishedProcess = verkuendungImportProcessesRepository.findById(processId);
    assertThat(finishedProcess).isPresent();
    assertThat(finishedProcess.get().getStatus()).isEqualTo(
      VerkuendungImportProcessDto.Status.SUCCESS
    );

    assertThat(dokumentRepository.findAll()).hasSize(3);
    assertThat(binaryFileRepository.findAll()).isEmpty();
    assertThat(verkuendungRepository.findAll())
      .hasSize(1)
      .anySatisfy(verkuendungDto ->
        assertThat(verkuendungDto.getEliNormExpression()).isEqualTo(
          "eli/bund/bgbl-1/2004/s1673/2004-07-16/1/deu"
        )
      );
  }

  @Test
  void processNormendokumentationspaketWithExternesDokument()
    throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {
    var processId = UUID.randomUUID();
    storeFolderAsNormendokumentationsPaket(processId, "verkuendung-valid-with-pdf-teildokument");

    verkuendungImportProcessesRepository.save(
      new VerkuendungImportProcessDto(
        processId,
        VerkuendungImportProcessDto.Status.CREATED,
        Instant.now(),
        null,
        null,
        null
      )
    );

    verkuendungsImportService.processNormendokumentationspaket(
      new ProcessNormendokumentationspaketUseCase.ProcessOptions(processId)
    );

    var finishedProcess = verkuendungImportProcessesRepository.findById(processId);
    assertThat(finishedProcess).isPresent();
    assertThat(finishedProcess.get().getStatus()).isEqualTo(
      VerkuendungImportProcessDto.Status.SUCCESS
    );

    assertThat(dokumentRepository.findAll()).hasSize(2);
    assertThat(binaryFileRepository.findAll()).hasSize(1);
    assertThat(verkuendungRepository.findAll())
      .hasSize(1)
      .anySatisfy(verkuendungDto ->
        assertThat(verkuendungDto.getEliNormExpression()).isEqualTo(
          "eli/bund/bgbl-1/2024/107/2024-03-27/1/deu"
        )
      );
  }

  @Test
  void processNormendokumentationspaketWithInvalidSchematron()
    throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {
    var processId = UUID.randomUUID();
    storeFolderAsNormendokumentationsPaket(processId, "verkuendung-invalid-schematron");

    verkuendungImportProcessesRepository.save(
      new VerkuendungImportProcessDto(
        processId,
        VerkuendungImportProcessDto.Status.CREATED,
        Instant.now(),
        null,
        null,
        null
      )
    );

    verkuendungsImportService.processNormendokumentationspaket(
      new ProcessNormendokumentationspaketUseCase.ProcessOptions(processId)
    );

    var finishedProcess = verkuendungImportProcessesRepository.findById(processId);
    assertThat(finishedProcess).isPresent();
    assertThat(finishedProcess.get().getStatus()).isEqualTo(
      VerkuendungImportProcessDto.Status.ERROR
    );
    assertThat(finishedProcess.get().getDetails()).contains("/errors/ldml-de-not-schematron-valid");
    assertThat(finishedProcess.get().getDetails()).contains(
      "/errors/ldml-de-not-schematron-valid/failed-assert/SCH-00071-005"
    );

    assertThat(dokumentRepository.findAll()).isEmpty();
    assertThat(binaryFileRepository.findAll()).isEmpty();
    assertThat(verkuendungRepository.findAll()).isEmpty();
  }

  @Test
  void processNormendokumentationspaketWithUnsupportedFiletype()
    throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {
    var processId = UUID.randomUUID();
    storeFolderAsNormendokumentationsPaket(
      processId,
      "verkuendung-with-missing-referenced-dokument"
    );

    verkuendungImportProcessesRepository.save(
      new VerkuendungImportProcessDto(
        processId,
        VerkuendungImportProcessDto.Status.CREATED,
        Instant.now(),
        null,
        null,
        null
      )
    );

    verkuendungsImportService.processNormendokumentationspaket(
      new ProcessNormendokumentationspaketUseCase.ProcessOptions(processId)
    );

    var finishedProcess = verkuendungImportProcessesRepository.findById(processId);
    assertThat(finishedProcess).isPresent();
    assertThat(finishedProcess.get().getStatus()).isEqualTo(
      VerkuendungImportProcessDto.Status.ERROR
    );
    assertThat(finishedProcess.get().getDetails()).contains(
      "/errors/normendokumentationspaket-import-failed/missing-referenced-dokument"
    );
    assertThat(finishedProcess.get().getDetails()).contains(
      "\"Referenced Dokument regelungstext-1.xml not found.\""
    );

    assertThat(dokumentRepository.findAll()).isEmpty();
    assertThat(binaryFileRepository.findAll()).isEmpty();
  }

  @Test
  void processNormendokumentationspaketWithInvalidSignature()
    throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {
    var processId = UUID.randomUUID();
    storeFolderAsNormendokumentationsPaket(
      processId,
      "verkuendung-valid",
      loadPrivateKey(new ClassPathResource("certs/test-private-key-invalid.pem"))
    );

    verkuendungImportProcessesRepository.save(
      new VerkuendungImportProcessDto(
        processId,
        VerkuendungImportProcessDto.Status.CREATED,
        Instant.now(),
        null,
        null,
        null
      )
    );

    verkuendungsImportService.processNormendokumentationspaket(
      new ProcessNormendokumentationspaketUseCase.ProcessOptions(processId)
    );

    var finishedProcess = verkuendungImportProcessesRepository.findById(processId);
    assertThat(finishedProcess).isPresent();
    assertThat(finishedProcess.get().getStatus()).isEqualTo(
      VerkuendungImportProcessDto.Status.ERROR
    );
    assertThat(finishedProcess.get().getDetails()).contains(
      "/errors/normendokumentationspaket-import-failed/signature-not-valid"
    );

    assertThat(dokumentRepository.findAll()).isEmpty();
    assertThat(binaryFileRepository.findAll()).isEmpty();
  }

  private void storeFolderAsNormendokumentationsPaket(UUID processId, String folderName)
    throws InvalidKeySpecException, IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
    storeFolderAsNormendokumentationsPaket(
      processId,
      folderName,
      loadPrivateKey(new ClassPathResource("certs/test-private-key.pem"))
    );
  }

  private void storeFolderAsNormendokumentationsPaket(
    UUID processId,
    String folderName,
    PrivateKey privateKey
  ) throws IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
    var processFolder = getEverkuendungPath().resolve(processId.toString());
    processFolder.toFile().mkdirs();

    var folder = new File(
      Fixtures.getResource(VerkuendungsImportServiceIntegrationTest.class, folderName).getFile()
    );

    var zipPath = processFolder.resolve("file.zip");
    var zipFileOutputStream = new FileOutputStream(zipPath.toFile().toString());
    var zipOutputStream = new ZipOutputStream(zipFileOutputStream);

    for (File file : Objects.requireNonNull(folder.listFiles())) {
      var zipEntry = new ZipEntry(file.getName());
      // setting size, compressed size, crc and method manually so they are actually included in the zip file
      // and ZipEntry::getSize works within our methods.
      zipEntry.setSize(file.length());
      zipEntry.setCompressedSize(file.length());
      var crc32 = new CRC32();
      var crc32FileInputStream = new FileInputStream(file);
      crc32.update(crc32FileInputStream.readAllBytes());
      crc32FileInputStream.close();
      zipEntry.setCrc(crc32.getValue());
      zipEntry.setMethod(ZipEntry.STORED);
      zipOutputStream.putNextEntry(zipEntry);

      var fileInputStream = new FileInputStream(file);
      byte[] bytes = new byte[1024];
      int length;
      while ((length = fileInputStream.read(bytes)) >= 0) {
        zipOutputStream.write(bytes, 0, length);
      }
      zipOutputStream.closeEntry();
      fileInputStream.close();
    }

    zipOutputStream.close();

    // Signature file
    var zipBytes = Files.readAllBytes(zipPath);
    var signature = Signature.getInstance("SHA256withRSA");
    signature.initSign(privateKey);
    signature.update(zipBytes);
    var signatureBytes = signature.sign();
    Files.write(processFolder.resolve("signature.sig"), signatureBytes);
  }

  private PrivateKey loadPrivateKey(Resource resource)
    throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    String key;
    try (InputStream is = resource.getInputStream()) {
      key = new String(is.readAllBytes(), StandardCharsets.UTF_8)
        .replaceAll("-----\\w+ PRIVATE KEY-----", "")
        .replaceAll("\\s+", "");
    }
    byte[] keyBytes = Base64.getDecoder().decode(key);
    final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
    final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    return keyFactory.generatePrivate(keySpec);
  }
}
