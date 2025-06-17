package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.ImportProcessNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.LdmlDeNotValidException;
import de.bund.digitalservice.ris.norms.application.exception.LdmlDeSchematronException;
import de.bund.digitalservice.ris.norms.application.exception.NormExistsAlreadyException;
import de.bund.digitalservice.ris.norms.application.exception.NormWithGuidAlreadyExistsException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormendokumentationspacketProcessingStatusUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.ProcessNormendokumentationspaketUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.StoreNormendokumentationspaketUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormByGuidPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormendokumentationspaketPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadVerkuendungImportProcessPort;
import de.bund.digitalservice.ris.norms.application.port.output.SaveNormendokumentationspaketPort;
import de.bund.digitalservice.ris.norms.application.port.output.SaveVerkuendungImportProcessPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveVerkuendungPort;
import de.bund.digitalservice.ris.norms.domain.entity.BinaryFile;
import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import de.bund.digitalservice.ris.norms.utils.ZipUtils;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.scheduling.JobBuilder;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

/**
 * Service for importing normendokumentationspakete as new verkündungen.
 */
@Service
@Slf4j
public class VerkuendungsImportService
  implements
    StoreNormendokumentationspaketUseCase,
    LoadNormendokumentationspacketProcessingStatusUseCase,
    ProcessNormendokumentationspaketUseCase {

  private final LoadNormPort loadNormPort;
  private final UpdateOrSaveNormPort updateOrSaveNormPort;
  private final LoadNormendokumentationspaketPort loadNormendokumentationspaketPort;
  private final SaveNormendokumentationspaketPort saveNormendokumentationspaketPort;
  private final SaveVerkuendungImportProcessPort saveVerkuendungImportProcessPort;
  private final LoadVerkuendungImportProcessPort loadVerkuendungImportProcessPort;
  private final UpdateOrSaveVerkuendungPort updateOrSaveVerkuendungPort;
  private final LdmlDeValidator ldmlDeValidator;
  private final JobScheduler jobScheduler;
  private final MediaTypeService mediaTypeService;
  private final SignatureValidator signatureValidator;

  static final List<MediaType> SUPPORTED_MEDIA_TYPES = List.of(
    MediaType.APPLICATION_XML,
    MediaType.APPLICATION_PDF,
    MediaType.IMAGE_JPEG,
    MediaType.IMAGE_PNG,
    MediaType.IMAGE_GIF
  );

  static final String RECHTSETZUNGSDOKUMENT_FILENAME = "rechtsetzungsdokument-1.xml";
  private final LoadNormByGuidPort loadNormByGuidPort;

  public VerkuendungsImportService(
    LoadNormPort loadNormPort,
    UpdateOrSaveNormPort updateOrSaveNormPort,
    LoadNormendokumentationspaketPort loadNormendokumentationspaketPort,
    SaveNormendokumentationspaketPort saveNormendokumentationspaketPort,
    SaveVerkuendungImportProcessPort saveVerkuendungImportProcessPort,
    LoadVerkuendungImportProcessPort loadVerkuendungImportProcessPort,
    UpdateOrSaveVerkuendungPort updateOrSaveVerkuendungPort,
    LdmlDeValidator ldmlDeValidator,
    JobScheduler jobScheduler,
    MediaTypeService mediaTypeService,
    SignatureValidator signatureValidator,
    LoadNormByGuidPort loadNormByGuidPort
  ) {
    this.loadNormPort = loadNormPort;
    this.updateOrSaveNormPort = updateOrSaveNormPort;
    this.loadNormendokumentationspaketPort = loadNormendokumentationspaketPort;
    this.saveNormendokumentationspaketPort = saveNormendokumentationspaketPort;
    this.saveVerkuendungImportProcessPort = saveVerkuendungImportProcessPort;
    this.loadVerkuendungImportProcessPort = loadVerkuendungImportProcessPort;
    this.updateOrSaveVerkuendungPort = updateOrSaveVerkuendungPort;
    this.ldmlDeValidator = ldmlDeValidator;
    this.jobScheduler = jobScheduler;
    this.mediaTypeService = mediaTypeService;
    this.signatureValidator = signatureValidator;
    this.loadNormByGuidPort = loadNormByGuidPort;
  }

  @Override
  public UUID storeNormendokumentationspaket(StoreNormendokumentationspaketUseCase.Options options)
    throws IOException {
    final UUID processId = UUID.randomUUID();
    saveNormendokumentationspaketPort.saveNormendokumentationspaket(
      new SaveNormendokumentationspaketPort.Options(processId, options.file(), options.signature())
    );

    saveVerkuendungImportProcessPort.saveOrUpdateVerkuendungImportProcess(
      new SaveVerkuendungImportProcessPort.Options(
        processId,
        VerkuendungImportProcess.Status.CREATED
      )
    );

    jobScheduler.create(
      JobBuilder.aJob()
        .withId(processId)
        .withName("Process Normendokumentationspaket")
        .<ProcessNormendokumentationspaketUseCase>withDetails(service ->
          service.processNormendokumentationspaket(
            new ProcessNormendokumentationspaketUseCase.ProcessOptions(processId)
          )
        )
    );
    return processId;
  }

  @Override
  public VerkuendungImportProcess getStatus(
    LoadNormendokumentationspacketProcessingStatusUseCase.Options options
  ) {
    return loadVerkuendungImportProcessPort
      .loadVerkuendungImportProcess(
        new LoadVerkuendungImportProcessPort.Options(options.processingId())
      )
      .orElseThrow(() -> new ImportProcessNotFoundException(options.processingId()));
  }

  @Override
  public Verkuendung processNormendokumentationspaket(
    ProcessNormendokumentationspaketUseCase.Options options
  ) throws IOException {
    return switch (options) {
      case DirectProcessingOptions directProcessingOptions -> processNormendokumentationspaket(
        directProcessingOptions
      );
      case ProcessOptions processOptions -> processNormendokumentationspaket(processOptions);
    };
  }

  private Verkuendung processNormendokumentationspaket(
    ProcessNormendokumentationspaketUseCase.ProcessOptions options
  ) {
    log.info("Start processing Normendokumentationspaket: {}", options.processId());

    var process = loadVerkuendungImportProcessPort
      .loadVerkuendungImportProcess(
        new LoadVerkuendungImportProcessPort.Options(options.processId())
      )
      .orElseThrow(() -> new RuntimeException("Could not load verkuendung import process"));

    process = saveVerkuendungImportProcessPort.saveOrUpdateVerkuendungImportProcess(
      new SaveVerkuendungImportProcessPort.Options(
        process.getId(),
        VerkuendungImportProcess.Status.PROCESSING
      )
    );

    Verkuendung savedVerkuendung = null;

    try {
      var files = loadNormendokumentationspaketPort.loadNormendokumentationspaket(
        new LoadNormendokumentationspaketPort.Options(options.processId())
      );
      var zipFile = files.file();
      var signatureFile = files.signature();

      signatureValidator.validate(zipFile, signatureFile);

      Norm norm = parseAndValidate(zipFile, false);
      updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Options(norm));

      Verkuendung verkuendung = Verkuendung.builder().eli(norm.getExpressionEli()).build();
      savedVerkuendung = updateOrSaveVerkuendungPort.updateOrSaveVerkuendung(
        new UpdateOrSaveVerkuendungPort.Options(verkuendung)
      );

      saveVerkuendungImportProcessPort.saveOrUpdateVerkuendungImportProcess(
        new SaveVerkuendungImportProcessPort.Options(
          process.getId(),
          VerkuendungImportProcess.Status.SUCCESS
        )
      );
    } catch (Exception e) {
      if (e instanceof NormsAppException normsAppException) {
        log.warn(
          "Exception during processing of Normendokumentationspaket: {}",
          options.processId(),
          e
        );
        saveVerkuendungImportProcessPort.saveOrUpdateVerkuendungImportProcess(
          new SaveVerkuendungImportProcessPort.Options(
            process.getId(),
            VerkuendungImportProcess.Status.ERROR,
            normsAppException
          )
        );
      } else {
        log.error(
          "Unexpected exception during processing of Normendokumentationspaket: {}",
          options.processId(),
          e
        );
        saveVerkuendungImportProcessPort.saveOrUpdateVerkuendungImportProcess(
          new SaveVerkuendungImportProcessPort.Options(
            process.getId(),
            VerkuendungImportProcess.Status.ERROR,
            new InternalErrorException()
          )
        );
      }
    }

    log.info("Finished processing Normendokumentationspaket: {}", options.processId());

    return savedVerkuendung;
  }

  private Verkuendung processNormendokumentationspaket(
    ProcessNormendokumentationspaketUseCase.DirectProcessingOptions options
  ) throws IOException {
    Norm norm = parseAndValidate(options.zipFile(), options.force());
    updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Options(norm));

    Verkuendung verkuendung = Verkuendung.builder().eli(norm.getExpressionEli()).build();
    return updateOrSaveVerkuendungPort.updateOrSaveVerkuendung(
      new UpdateOrSaveVerkuendungPort.Options(verkuendung)
    );
  }

  private Norm parseAndValidate(byte[] zipFile, boolean ignoreExistingNorm)
    throws IOException, NormendokumentationspaketImportFailedException, LdmlDeNotValidException, LdmlDeSchematronException {
    try (ByteArrayInputStream zipInputStream = new ByteArrayInputStream(zipFile)) {
      validateFileIsZipArchive(zipInputStream);
      log.debug("File is zip archive.");
      Map<String, byte[]> files;
      try {
        files = ZipUtils.unzipFileWithoutDirectories(zipInputStream);
      } catch (IllegalArgumentException e) {
        throw new InvalidStructureInZipFileException(e.getMessage());
      }

      Norm norm = findParseAndValidateFilesAsNorm(files);
      ldmlDeValidator.validateSchematron(norm);

      if (norm.getRegelungstexte().isEmpty() && norm.getBekanntmachungen().isEmpty()) {
        throw new NoRegelungstextOrBekanntmachungstextException();
      }

      if (
        !ignoreExistingNorm &&
        loadNormPort.loadNorm(new LoadNormPort.Options(norm.getWorkEli())).isPresent()
      ) {
        throw new NormExistsAlreadyException(norm.getWorkEli().toString());
      }

      if (
        !ignoreExistingNorm &&
        loadNormByGuidPort
          .loadNormByGuid(new LoadNormByGuidPort.Options(norm.getGuid()))
          .isPresent()
      ) {
        throw new NormWithGuidAlreadyExistsException(norm.getGuid());
      }

      log.info(
        "Verified new norm from import: {} with {} Dokumenten and {} Binary files",
        norm.getManifestationEli(),
        norm.getDokumente().size(),
        norm.getBinaryFiles().size()
      );

      return norm;
    }
  }

  private void validateFileIsZipArchive(InputStream fileStream) throws IOException {
    var mediaType = mediaTypeService.detectMediaType(fileStream);

    if (
      mediaType.isEmpty() ||
      !mediaType.get().isCompatibleWith(MediaType.parseMediaType("application/zip"))
    ) {
      throw new NotAZipFileException();
    }
  }

  private Norm findParseAndValidateFilesAsNorm(Map<String, byte[]> files) throws IOException {
    if (!files.containsKey(RECHTSETZUNGSDOKUMENT_FILENAME)) {
      throw new MissingRechtsetzungsdokumentException();
    }

    NormManifestationEli normManifestationEli = null;
    Map<String, Dokument> dokumente = new HashMap<>();

    Queue<String> dokumenteToProcess = new LinkedList<>();
    Queue<String> binaryFilesToProcess = new LinkedList<>();
    dokumenteToProcess.add(RECHTSETZUNGSDOKUMENT_FILENAME);

    while (!dokumenteToProcess.isEmpty()) {
      String dokumentName = dokumenteToProcess.poll();
      log.debug("Processing Dokument: {}", dokumentName);
      var dokument = parseAndValidateDokument(
        normManifestationEli,
        dokumentName,
        files.get(dokumentName)
      );
      log.debug("Parsed & validated Dokument: {}", dokument.getManifestationEli());
      dokumente.put(dokumentName, dokument);

      if (normManifestationEli == null) {
        normManifestationEli = dokument.getManifestationEli().asNormEli();
      }

      dokument
        .getReferencedDokumentAndBinaryFileFileNames()
        .stream()
        // check if we already know about the referenced dokument
        .filter(Predicate.not(dokumente::containsKey))
        .filter(Predicate.not(binaryFilesToProcess::contains))
        .filter(Predicate.not(dokumenteToProcess::contains))
        .filter(referencedDokumentName -> {
          if (files.containsKey(referencedDokumentName)) {
            return true;
          }
          throw new MissingReferencedDokumentException(referencedDokumentName);
        })
        .forEach(referencedDokumentName -> {
          log.debug("Found new referenced dokument: {}", referencedDokumentName);
          if (isXmlFile(files.get(referencedDokumentName))) {
            dokumenteToProcess.add(referencedDokumentName);
          } else {
            binaryFilesToProcess.add(referencedDokumentName);
          }
        });
    }

    Map<String, BinaryFile> binaryFiles = new HashMap<>();

    while (!binaryFilesToProcess.isEmpty()) {
      String binaryFileName = binaryFilesToProcess.poll();
      log.debug("Processing BinaryFile: {}", binaryFileName);
      var binaryFile = parseAndValidateBinaryFile(
        normManifestationEli,
        binaryFileName,
        files.get(binaryFileName)
      );
      log.debug("Parsed & validated BinaryFile: {}", binaryFileName);
      binaryFiles.put(binaryFileName, binaryFile);
    }

    files
      .keySet()
      .stream()
      .filter(fileName -> !dokumente.containsKey(fileName) && !binaryFiles.containsKey(fileName))
      .forEach(fileName -> log.debug("Ignored file: {}", fileName));

    return new Norm(
      NormPublishState.UNPUBLISHED,
      new HashSet<>(dokumente.values()),
      new HashSet<>(binaryFiles.values())
    );
  }

  private BinaryFile parseAndValidateBinaryFile(
    NormManifestationEli normManifestationEli,
    String binaryFileName,
    byte[] file
  ) throws IOException {
    var mediaType = mediaTypeService
      .detectMediaType(new ByteArrayInputStream(file))
      .orElseThrow(() -> new UnsupportedFileTypeException(binaryFileName));

    if (SUPPORTED_MEDIA_TYPES.stream().noneMatch(mediaType::isCompatibleWith)) {
      throw new UnsupportedFileTypeException(binaryFileName);
    }

    return new BinaryFile(
      DokumentManifestationEli.fromNormEli(
        normManifestationEli,
        binaryFileName.split("\\.")[0],
        binaryFileName.split("\\.")[1]
      ),
      file
    );
  }

  private Dokument parseAndValidateDokument(
    @Nullable NormManifestationEli normManifestationEli,
    String dokumentName,
    byte[] file
  ) {
    var dokument = ldmlDeValidator.parseAndValidateDokument(dokumentName, new String(file));

    if (!dokument.getManifestationEli().getSubtype().equals(dokumentName.split("\\.")[0])) {
      throw new MismatchBetweenFilenameAndSubtypeException(
        dokumentName,
        dokument.getManifestationEli()
      );
    }

    if (
      normManifestationEli != null &&
      !dokument.getManifestationEli().asNormEli().equals(normManifestationEli)
    ) {
      throw new InconsistentEliException(
        dokumentName,
        normManifestationEli,
        dokument.getManifestationEli().asNormEli()
      );
    }

    return dokument;
  }

  private boolean isXmlFile(byte[] dokument) {
    try {
      return mediaTypeService
        .detectMediaType(new ByteArrayInputStream(dokument))
        .map(type -> type.equalsTypeAndSubtype(MediaType.APPLICATION_XML))
        .orElse(false);
    } catch (IOException e) {
      return false;
    }
  }
}
