package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import de.bund.digitalservice.ris.norms.application.exception.ImportProcessNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormendokumentationspacketProcessingStatusUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.ProcessNormendokumentationspaketUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormendokumentationspaketPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadVerkuendungImportProcessPort;
import de.bund.digitalservice.ris.norms.application.port.output.SaveNormendokumentationspaketPort;
import de.bund.digitalservice.ris.norms.application.port.output.SaveVerkuendungImportProcessPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveVerkuendungPort;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import de.bund.digitalservice.ris.norms.utils.ZipUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.tika.exception.TikaException;
import org.jetbrains.annotations.NotNull;
import org.jobrunr.scheduling.JobScheduler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

class VerkuendungsImportServiceTest {

  private final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  private final UpdateOrSaveNormPort updateOrSaveNormPort = mock(UpdateOrSaveNormPort.class);
  private final LoadNormendokumentationspaketPort loadNormendokumentationspaketPort = mock(
    LoadNormendokumentationspaketPort.class
  );
  private final SaveNormendokumentationspaketPort saveNormendokumentationspaketPort = mock(
    SaveNormendokumentationspaketPort.class
  );
  private final SaveVerkuendungImportProcessPort saveVerkuendungImportProcessPort = mock(
    SaveVerkuendungImportProcessPort.class
  );
  private final LoadVerkuendungImportProcessPort loadVerkuendungImportProcessPort = mock(
    LoadVerkuendungImportProcessPort.class
  );
  private final UpdateOrSaveVerkuendungPort updateOrSaveVerkuendungPort = mock(
    UpdateOrSaveVerkuendungPort.class
  );
  private final JobScheduler jobScheduler = mock(JobScheduler.class);

  private final VerkuendungsImportService verkuendungsImportService = new VerkuendungsImportService(
    loadNormPort,
    updateOrSaveNormPort,
    loadNormendokumentationspaketPort,
    saveNormendokumentationspaketPort,
    saveVerkuendungImportProcessPort,
    loadVerkuendungImportProcessPort,
    updateOrSaveVerkuendungPort,
    Fixtures.getLdmlDeValidator(),
    jobScheduler,
    new MediaTypeService()
  );

  VerkuendungsImportServiceTest() throws TikaException, IOException {}

  @BeforeAll
  static void beforeAll() {
    ((Logger) LoggerFactory.getLogger(VerkuendungsImportService.class)).setLevel(Level.DEBUG);
    ((Logger) LoggerFactory.getLogger(ZipUtils.class)).setLevel(Level.DEBUG);
  }

  @Test
  void getStatus() {
    // given
    UUID uuid = UUID.randomUUID();
    VerkuendungImportProcess verkuendungImportProcess = VerkuendungImportProcess
      .builder()
      .id(uuid)
      .status(VerkuendungImportProcess.Status.CREATED)
      .build();
    when(loadVerkuendungImportProcessPort.loadVerkuendungImportProcess(any()))
      .thenReturn(Optional.of(verkuendungImportProcess));

    //when
    var result = verkuendungsImportService.getStatus(
      new LoadNormendokumentationspacketProcessingStatusUseCase.Query(uuid)
    );

    //then
    verify(loadVerkuendungImportProcessPort).loadVerkuendungImportProcess(any());
    assertThat(result.getId()).isEqualTo(uuid);
    assertThat(result.getStatus()).isEqualTo(VerkuendungImportProcess.Status.CREATED);
    assertThat(result.getCreatedAt()).isNull();
    assertThat(result.getStartedAt()).isNull();
    assertThat(result.getFinishedAt()).isNull();
    assertThat(result.getDetail()).isNull();
  }

  @Test
  void getStatusThrowsException() {
    // given
    when(loadVerkuendungImportProcessPort.loadVerkuendungImportProcess(any()))
      .thenReturn(Optional.empty());

    // when
    LoadNormendokumentationspacketProcessingStatusUseCase.Query query =
      new LoadNormendokumentationspacketProcessingStatusUseCase.Query(UUID.randomUUID());
    assertThatThrownBy(() -> verkuendungsImportService.getStatus(query))
      // then
      .isInstanceOf(ImportProcessNotFoundException.class);
  }

  @Nested
  class processNormendokumentationspaket {

    @Test
    void itProcessesAValidPaket() throws IOException {
      // Given
      var processId = UUID.randomUUID();
      var process = VerkuendungImportProcess
        .builder()
        .id(processId)
        .createdAt(Instant.now())
        .build();
      when(loadVerkuendungImportProcessPort.loadVerkuendungImportProcess(any()))
        .thenReturn(Optional.of(process));
      when(saveVerkuendungImportProcessPort.saveOrUpdateVerkuendungImportProcess(any()))
        .thenReturn(process);

      when(loadNormendokumentationspaketPort.loadNormendokumentationspaket(any()))
        .thenReturn(
          new LoadNormendokumentationspaketPort.Result(
            loadFolderAsZipResource("verkuendung-valid"),
            null
          )
        );

      // When
      verkuendungsImportService.processNormendokumentationspaket(
        new ProcessNormendokumentationspaketUseCase.Query(processId)
      );

      // Then
      verify(saveVerkuendungImportProcessPort, times(1))
        .saveOrUpdateVerkuendungImportProcess(
          argThat(command -> command.status().equals(VerkuendungImportProcess.Status.PROCESSING))
        );
      verify(saveVerkuendungImportProcessPort, times(1))
        .saveOrUpdateVerkuendungImportProcess(
          argThat(command -> command.status().equals(VerkuendungImportProcess.Status.SUCCESS))
        );
      verify(updateOrSaveNormPort, times(1))
        .updateOrSave(
          assertArg(command -> {
            assertThat(command.norm().getManifestationEli())
              .hasToString("eli/bund/bgbl-1/2024/107/2024-03-27/1/deu/2024-03-27");
            assertThat(command.norm().getDokumente()).hasSize(2);
            assertThat(command.norm().getBinaryFiles()).isEmpty();
          })
        );

      verify(updateOrSaveVerkuendungPort, times(1))
        .updateOrSaveVerkuendung(
          assertArg(command -> {
            assertThat(command.verkuendung().getEli())
              .hasToString("eli/bund/bgbl-1/2024/107/2024-03-27/1/deu");
          })
        );
    }

    @Test
    void itFailsForSchematronInvalidContent() throws IOException {
      // Given
      var processId = UUID.randomUUID();
      var process = VerkuendungImportProcess
        .builder()
        .id(processId)
        .createdAt(Instant.now())
        .build();
      when(loadVerkuendungImportProcessPort.loadVerkuendungImportProcess(any()))
        .thenReturn(Optional.of(process));
      when(saveVerkuendungImportProcessPort.saveOrUpdateVerkuendungImportProcess(any()))
        .thenReturn(process);

      when(loadNormendokumentationspaketPort.loadNormendokumentationspaket(any()))
        .thenReturn(
          new LoadNormendokumentationspaketPort.Result(
            loadFolderAsZipResource("verkuendung-invalid-schematron"),
            null
          )
        );

      // When
      verkuendungsImportService.processNormendokumentationspaket(
        new ProcessNormendokumentationspaketUseCase.Query(processId)
      );

      // Then
      verify(saveVerkuendungImportProcessPort, times(1))
        .saveOrUpdateVerkuendungImportProcess(
          argThat(command -> command.status().equals(VerkuendungImportProcess.Status.PROCESSING))
        );
      verify(saveVerkuendungImportProcessPort, times(1))
        .saveOrUpdateVerkuendungImportProcess(
          argThat(command ->
            command.status().equals(VerkuendungImportProcess.Status.ERROR) &&
            command.details().getType().getPath().equals("/errors/ldml-de-not-schematron-valid")
          )
        );
      verify(updateOrSaveNormPort, times(0)).updateOrSave(any());
    }

    @Test
    void itFailsForMissingRechtsetzungsdokument() throws IOException {
      // Given
      var processId = UUID.randomUUID();
      var process = VerkuendungImportProcess
        .builder()
        .id(processId)
        .createdAt(Instant.now())
        .build();
      when(loadVerkuendungImportProcessPort.loadVerkuendungImportProcess(any()))
        .thenReturn(Optional.of(process));
      when(saveVerkuendungImportProcessPort.saveOrUpdateVerkuendungImportProcess(any()))
        .thenReturn(process);

      when(loadNormendokumentationspaketPort.loadNormendokumentationspaket(any()))
        .thenReturn(
          new LoadNormendokumentationspaketPort.Result(
            loadFolderAsZipResource("verkuendung-without-rechtsetzungsdokument"),
            null
          )
        );

      // When
      verkuendungsImportService.processNormendokumentationspaket(
        new ProcessNormendokumentationspaketUseCase.Query(processId)
      );

      // Then
      verify(saveVerkuendungImportProcessPort, times(1))
        .saveOrUpdateVerkuendungImportProcess(
          argThat(command -> command.status().equals(VerkuendungImportProcess.Status.PROCESSING))
        );
      verify(saveVerkuendungImportProcessPort, times(1))
        .saveOrUpdateVerkuendungImportProcess(
          argThat(command ->
            command.status().equals(VerkuendungImportProcess.Status.ERROR) &&
            command
              .details()
              .getType()
              .getPath()
              .equals(
                "/errors/normendokumentationspaket-import-failed/missing-rechtsetzungsdokument"
              )
          )
        );
      verify(updateOrSaveNormPort, times(0)).updateOrSave(any());
    }

    @Test
    void itFailsIfNormAlreadyExists() throws IOException {
      // Given
      var processId = UUID.randomUUID();
      var process = VerkuendungImportProcess
        .builder()
        .id(processId)
        .createdAt(Instant.now())
        .build();
      when(loadVerkuendungImportProcessPort.loadVerkuendungImportProcess(any()))
        .thenReturn(Optional.of(process));
      when(saveVerkuendungImportProcessPort.saveOrUpdateVerkuendungImportProcess(any()))
        .thenReturn(process);

      when(loadNormPort.loadNorm(any()))
        .thenReturn(
          Optional.of(
            Fixtures.loadNormFromDisk(
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
            )
          )
        );

      when(loadNormendokumentationspaketPort.loadNormendokumentationspaket(any()))
        .thenReturn(
          new LoadNormendokumentationspaketPort.Result(
            loadFolderAsZipResource("verkuendung-valid"),
            null
          )
        );

      // When
      verkuendungsImportService.processNormendokumentationspaket(
        new ProcessNormendokumentationspaketUseCase.Query(processId)
      );

      // Then
      verify(saveVerkuendungImportProcessPort, times(1))
        .saveOrUpdateVerkuendungImportProcess(
          argThat(command -> command.status().equals(VerkuendungImportProcess.Status.PROCESSING))
        );
      verify(saveVerkuendungImportProcessPort, times(1))
        .saveOrUpdateVerkuendungImportProcess(
          argThat(command ->
            command.status().equals(VerkuendungImportProcess.Status.ERROR) &&
            command.details().getType().getPath().equals("/errors/norm-with-eli-exists-already")
          )
        );
      verify(updateOrSaveNormPort, times(0)).updateOrSave(any());
      verify(loadNormPort)
        .loadNorm(new LoadNormPort.Command(NormWorkEli.fromString("eli/bund/bgbl-1/2024/107")));
    }

    @Test
    void itFailsIfItHasAnUnsupportedFileType() throws IOException {
      // Given
      var processId = UUID.randomUUID();
      var process = VerkuendungImportProcess
        .builder()
        .id(processId)
        .createdAt(Instant.now())
        .build();
      when(loadVerkuendungImportProcessPort.loadVerkuendungImportProcess(any()))
        .thenReturn(Optional.of(process));
      when(saveVerkuendungImportProcessPort.saveOrUpdateVerkuendungImportProcess(any()))
        .thenReturn(process);

      when(loadNormendokumentationspaketPort.loadNormendokumentationspaket(any()))
        .thenReturn(
          new LoadNormendokumentationspaketPort.Result(
            loadFolderAsZipResource("verkuendung-with-unsupported-filetype"),
            null
          )
        );

      // When
      verkuendungsImportService.processNormendokumentationspaket(
        new ProcessNormendokumentationspaketUseCase.Query(processId)
      );

      // Then
      verify(saveVerkuendungImportProcessPort, times(1))
        .saveOrUpdateVerkuendungImportProcess(
          argThat(command -> command.status().equals(VerkuendungImportProcess.Status.PROCESSING))
        );
      verify(saveVerkuendungImportProcessPort, times(1))
        .saveOrUpdateVerkuendungImportProcess(
          argThat(command ->
            command.status().equals(VerkuendungImportProcess.Status.ERROR) &&
            command
              .details()
              .getType()
              .getPath()
              .equals("/errors/normendokumentationspaket-import-failed/unsupported-file-type")
          )
        );
      verify(updateOrSaveNormPort, times(0)).updateOrSave(any());
    }

    @Test
    void itFailsIfItHasNoRegelungstext() throws IOException {
      // Given
      var processId = UUID.randomUUID();
      var process = VerkuendungImportProcess
        .builder()
        .id(processId)
        .createdAt(Instant.now())
        .build();
      when(loadVerkuendungImportProcessPort.loadVerkuendungImportProcess(any()))
        .thenReturn(Optional.of(process));
      when(saveVerkuendungImportProcessPort.saveOrUpdateVerkuendungImportProcess(any()))
        .thenReturn(process);

      when(loadNormendokumentationspaketPort.loadNormendokumentationspaket(any()))
        .thenReturn(
          new LoadNormendokumentationspaketPort.Result(
            loadFolderAsZipResource("verkuendung-without-regelungstext"),
            null
          )
        );

      // When
      verkuendungsImportService.processNormendokumentationspaket(
        new ProcessNormendokumentationspaketUseCase.Query(processId)
      );

      // Then
      verify(saveVerkuendungImportProcessPort, times(1))
        .saveOrUpdateVerkuendungImportProcess(
          argThat(command -> command.status().equals(VerkuendungImportProcess.Status.PROCESSING))
        );
      verify(saveVerkuendungImportProcessPort, times(1))
        .saveOrUpdateVerkuendungImportProcess(
          argThat(command ->
            command.status().equals(VerkuendungImportProcess.Status.ERROR) &&
            command
              .details()
              .getType()
              .getPath()
              .equals(
                "/errors/normendokumentationspaket-import-failed/no-regelungstext-or-bekanntmachungstext"
              )
          )
        );
      verify(updateOrSaveNormPort, times(0)).updateOrSave(any());
    }
  }

  private Resource loadFolderAsZipResource(String folderName) throws IOException {
    var folder = new File(
      Fixtures.getResource(VerkuendungsImportServiceTest.class, folderName).getFile()
    );

    var byteArrayOutputStream = new ByteArrayOutputStream();
    var zipOutputStream = new ZipOutputStream(byteArrayOutputStream);

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

    var byteArray = byteArrayOutputStream.toByteArray();
    var byteArrayResource = new ByteArrayResource(byteArray);

    // Create a new resource class so we are able to implement getFilename which is not implemented by ByteArrayResource
    return new AbstractResource() {
      @Override
      public @NotNull String getDescription() {
        return byteArrayResource.getDescription();
      }

      @Override
      public @NotNull InputStream getInputStream() throws IOException {
        return byteArrayResource.getInputStream();
      }

      @Override
      public String getFilename() {
        return folderName + ".zip";
      }
    };
  }
}
