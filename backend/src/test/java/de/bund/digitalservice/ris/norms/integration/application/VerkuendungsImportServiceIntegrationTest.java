package de.bund.digitalservice.ris.norms.integration.application;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungImportProcessDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.BinaryFileRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.VerkuendungImportProcessesRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.VerkuendungRepository;
import de.bund.digitalservice.ris.norms.application.port.input.ProcessNormendokumentationspaketUseCase;
import de.bund.digitalservice.ris.norms.application.service.VerkuendungsImportService;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.integration.BaseS3MockIntegrationTest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

  @Test
  void processNormendokumentationspaket() throws IOException {
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
      new ProcessNormendokumentationspaketUseCase.Query(processId)
    );

    var finishedProcess = verkuendungImportProcessesRepository.findById(processId);
    assertThat(finishedProcess).isPresent();
    assertThat(finishedProcess.get().getStatus())
      .isEqualTo(VerkuendungImportProcessDto.Status.SUCCESS);

    assertThat(dokumentRepository.findAll()).hasSize(2);
    assertThat(binaryFileRepository.findAll()).hasSize(1);
    assertThat(verkuendungRepository.findAll())
      .hasSize(1)
      .anySatisfy(verkuendungDto ->
        assertThat(verkuendungDto.getEliNormExpression())
          .isEqualTo("eli/bund/bgbl-1/2024/107/2024-03-27/1/deu")
      );
  }

  @Test
  void processNormendokumentationspaketWithInvalidSchematron() throws IOException {
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
      new ProcessNormendokumentationspaketUseCase.Query(processId)
    );

    var finishedProcess = verkuendungImportProcessesRepository.findById(processId);
    assertThat(finishedProcess).isPresent();
    assertThat(finishedProcess.get().getStatus())
      .isEqualTo(VerkuendungImportProcessDto.Status.ERROR);
    assertThat(finishedProcess.get().getDetail()).hasSize(1);
    assertThat(finishedProcess.get().getDetail().getFirst().getType())
      .isEqualTo("/errors/ldml-de-not-schematron-valid");
    assertThat(finishedProcess.get().getDetail().getFirst().getDetail())
      .contains("/errors/ldml-de-not-schematron-valid/failed-assert/SCH-00071-005");

    assertThat(dokumentRepository.findAll()).isEmpty();
    assertThat(binaryFileRepository.findAll()).isEmpty();
    assertThat(verkuendungRepository.findAll()).isEmpty();
  }

  @Test
  void processNormendokumentationspaketWithUnsupportedFiletype() throws IOException {
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
      new ProcessNormendokumentationspaketUseCase.Query(processId)
    );

    var finishedProcess = verkuendungImportProcessesRepository.findById(processId);
    assertThat(finishedProcess).isPresent();
    assertThat(finishedProcess.get().getStatus())
      .isEqualTo(VerkuendungImportProcessDto.Status.ERROR);
    assertThat(finishedProcess.get().getDetail()).hasSize(1);
    assertThat(finishedProcess.get().getDetail().getFirst().getType())
      .isEqualTo("/errors/normendokumentationspaket-import-failed/missing-referenced-dokument");
    assertThat(finishedProcess.get().getDetail().getFirst().getDetail())
      .isEqualTo(
        """
        {"dokumentName":"regelungstext-1.xml"}"""
      );

    assertThat(dokumentRepository.findAll()).isEmpty();
    assertThat(binaryFileRepository.findAll()).isEmpty();
  }

  private void storeFolderAsNormendokumentationsPaket(UUID processId, String folderName)
    throws IOException {
    var processFolder = getEverkuendungPath().resolve(processId.toString());
    processFolder.toFile().mkdirs();

    var folder = new File(
      Fixtures.getResource(VerkuendungsImportServiceIntegrationTest.class, folderName).getFile()
    );

    var zipFileOutputStream = new FileOutputStream(processFolder.resolve("file.zip").toString());
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

    var signatureFileOutputStream = new FileOutputStream(
      processFolder.resolve("signature.sig").toString()
    );
    signatureFileOutputStream.write("signature".getBytes());
    signatureFileOutputStream.close();
  }
}
