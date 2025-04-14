package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.ImportProcessNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.NormExistsAlreadyException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormendokumentationspacketProcessingStatusUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.ProcessNormendokumentationspaketUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.StoreNormendokumentationspaketUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadVerkuendungImportProcessPort;
import de.bund.digitalservice.ris.norms.application.port.output.SaveNormendokumentationspaketPort;
import de.bund.digitalservice.ris.norms.application.port.output.SaveVerkuendungImportProcessPort;
import de.bund.digitalservice.ris.norms.domain.entity.BinaryFile;
import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import de.bund.digitalservice.ris.norms.utils.ZipUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.scheduling.JobBuilder;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;

@Service
@Slf4j
class VerkuendungsImportService
  implements
    StoreNormendokumentationspaketUseCase,
    LoadNormendokumentationspacketProcessingStatusUseCase,
    ProcessNormendokumentationspaketUseCase {

  private final LoadNormPort loadNormPort;
  private final SaveNormendokumentationspaketPort saveNormendokumentationspaketPort;
  private final SaveVerkuendungImportProcessPort saveVerkuendungImportProcessPort;
  private final LoadVerkuendungImportProcessPort loadVerkuendungImportProcessPort;
  private final LdmlDeValidator ldmlDeValidator;
  private final JobScheduler jobScheduler;

  static final List<MimeType> SUPPORTED_MIME_TYPES = List.of(
    MediaType.APPLICATION_XML,
    MediaType.APPLICATION_PDF,
    MediaType.IMAGE_JPEG,
    MediaType.IMAGE_PNG,
    MediaType.IMAGE_GIF
  );

  static final String RECHTSETZUNGSDOKUMENT_FILENAME = "rechtsetzungsdokument-1.xml";

  public VerkuendungsImportService(
    SaveNormendokumentationspaketPort saveNormendokumentationspaketPort,
    SaveVerkuendungImportProcessPort saveVerkuendungImportProcessPort,
    LoadVerkuendungImportProcessPort loadVerkuendungImportProcessPort,
    LdmlDeValidator ldmlDeValidator,
    LoadNormPort loadNormPort,
    JobScheduler jobScheduler
  ) {
    this.saveNormendokumentationspaketPort = saveNormendokumentationspaketPort;
    this.saveVerkuendungImportProcessPort = saveVerkuendungImportProcessPort;
    this.loadVerkuendungImportProcessPort = loadVerkuendungImportProcessPort;
    this.ldmlDeValidator = ldmlDeValidator;
    this.loadNormPort = loadNormPort;
    this.jobScheduler = jobScheduler;
  }

  @Override
  public UUID storeNormendokumentationspaket(StoreNormendokumentationspaketUseCase.Query query)
    throws IOException {
    final UUID processId = UUID.randomUUID();
    saveNormendokumentationspaketPort.saveNormendokumentationspaket(
      new SaveNormendokumentationspaketPort.Command(processId, query.file(), query.signature())
    );

    saveVerkuendungImportProcessPort.saveOrUpdateVerkuendungImportProcess(
      new SaveVerkuendungImportProcessPort.Command(
        processId,
        VerkuendungImportProcess.Status.CREATED
      )
    );

    jobScheduler.create(
      JobBuilder
        .aJob()
        .withId(processId)
        .withName("Process Normendokumentationspaket")
        .<ProcessNormendokumentationspaketUseCase>withDetails(service ->
          service.processNormendokumentationspaket(
            new ProcessNormendokumentationspaketUseCase.Query(processId)
          )
        )
    );
    return processId;
  }

  @Override
  public VerkuendungImportProcess getStatus(
    LoadNormendokumentationspacketProcessingStatusUseCase.Query query
  ) {
    return loadVerkuendungImportProcessPort
      .loadVerkuendungImportProcess(
        new LoadVerkuendungImportProcessPort.Command(query.processingId())
      )
      .orElseThrow(() -> new ImportProcessNotFoundException(query.processingId()));
  }

  @Override
  public void processNormendokumentationspaket(
    ProcessNormendokumentationspaketUseCase.Query query
  ) {
    // TODO: (Malte Laukötter, 2025-04-08) call parseAndValidate with the zip file resource

    throw new RuntimeException("Not implemented yet");
  }

  // TODO: (Malte Laukötter, 2025-04-08) make private once integrated in processNormendokumentationspaket
  public Norm parseAndValidate(Resource zipFile)
    throws IOException, NormendokumentationspaketImportFailedException {
    validateFileIsZipArchive(zipFile);
    log.debug("File is zip archive.");

    Map<String, byte[]> files;
    try {
      files = ZipUtils.unzipFileWithoutDirectories(zipFile.getInputStream());
    } catch (IllegalArgumentException e) {
      throw new InvalidStructureInZipFileException(e.getMessage());
    }

    Set<Dokument> dokumente = findParseAndValidateDokumente(files);

    var manifestationEli = dokumente
      .stream()
      .findFirst()
      .orElseThrow()
      .getManifestationEli()
      .asNormEli();

    var binaryFiles = files
      .entrySet()
      .stream()
      .filter(entry ->
        dokumente
          .stream()
          .noneMatch(dokument -> dokument.getManifestationEli().getFileName().equals(entry.getKey())
          )
      )
      .map(entry -> {
        log.debug("Found binary file: {}", entry.getKey());

        var mimeType = MediaTypeFactory.getMediaType(entry.getKey()).orElseThrow();

        if (SUPPORTED_MIME_TYPES.stream().noneMatch(mimeType::isCompatibleWith)) {
          throw new UnsupportedFileTypeException(entry.getKey());
        }

        return new BinaryFile(
          DokumentManifestationEli.fromNormEli(
            manifestationEli,
            entry.getKey().split("\\.")[0],
            entry.getKey().split("\\.")[1]
          ),
          entry.getValue()
        );
      })
      .collect(Collectors.toSet());

    var norm = new Norm(NormPublishState.UNPUBLISHED, dokumente, binaryFiles);

    ldmlDeValidator.validateSchematron(norm);

    if (
      !norm
        .getRechtsetzungsdokument()
        .orElseThrow(MissingRechtsetzungsdokumentException::new)
        .isVerkuendungsfassung()
    ) {
      throw new RechtsetzungsdokumentNotAVerkuendungsfassungException();
    }

    if (norm.getRegelungstexte().isEmpty() && norm.getBekanntmachungen().isEmpty()) {
      throw new NoRegelungstextOrBekanntmachungstextException();
    }

    if (loadNormPort.loadNorm(new LoadNormPort.Command(norm.getWorkEli())).isPresent()) {
      throw new NormExistsAlreadyException(norm.getWorkEli().toString());
    }

    log.info(
      "Verified new norm from import: {} with {} Dokumenten and {} Binary files",
      norm.getManifestationEli(),
      norm.getDokumente().size(),
      norm.getBinaryFiles().size()
    );

    return norm;
  }

  private void validateFileIsZipArchive(Resource file) {
    var mediaType = MediaTypeFactory
      .getMediaType(file)
      .orElseThrow(() -> new NotAZipFileException("Invalid filetype: " + file.getFilename()));

    if (!mediaType.includes(MimeType.valueOf("application/zip"))) {
      throw new NotAZipFileException("Invalid filetype: " + file.getFilename());
    }
  }

  private Set<Dokument> findParseAndValidateDokumente(Map<String, byte[]> files) {
    if (!files.containsKey(RECHTSETZUNGSDOKUMENT_FILENAME)) {
      throw new MissingRechtsetzungsdokumentException();
    }

    NormManifestationEli normManifestationEli = null;

    Map<String, Dokument> dokumente = new HashMap<>();
    Queue<String> dokumenteToProcess = new LinkedList<>();
    dokumenteToProcess.add(RECHTSETZUNGSDOKUMENT_FILENAME);

    while (!dokumenteToProcess.isEmpty()) {
      String dokumentName = dokumenteToProcess.poll();
      log.debug("Processing Dokument {}", dokumentName);
      var dokument = parseAndValidateDokument(dokumentName, files.get(dokumentName));
      dokumente.put(dokumentName, dokument);

      if (!dokument.getManifestationEli().getSubtype().equals(dokumentName.split("\\.")[0])) {
        throw new MismatchBetweenFilenameAndSubtypeException(
          dokumentName,
          dokument.getManifestationEli()
        );
      }

      if (normManifestationEli == null) {
        normManifestationEli = dokument.getManifestationEli().asNormEli();
      }

      if (!dokument.getManifestationEli().asNormEli().equals(normManifestationEli)) {
        throw new InconsistentEliException(
          dokumentName,
          normManifestationEli,
          dokument.getManifestationEli().asNormEli()
        );
      }

      log.debug(
        "Parsed & validated Dokument {} now looking for referenced dokuments in it",
        dokument.getManifestationEli()
      );

      dokumenteToProcess.addAll(
        dokument
          .getReferencedDokumenteNames()
          .stream()
          // check if we already know about the referenced dokument
          .filter(Predicate.not(dokumente::containsKey))
          .filter(Predicate.not(dokumenteToProcess::contains))
          .filter(referencedDokumentName -> {
            if (isXmlFile(referencedDokumentName)) {
              return true;
            }
            log.debug(
              "Ignore referenced dokument {} as it is not an xml document.",
              referencedDokumentName
            );
            return false;
          })
          .filter(referencedDokumentName -> {
            if (files.containsKey(referencedDokumentName)) {
              return true;
            }
            throw new MissingReferencedDokumentException(referencedDokumentName);
          })
          .peek(referencedDokumentName ->
            log.debug("Found new referenced dokument {}", referencedDokumentName)
          )
          .collect(Collectors.toSet())
      );
    }

    return new HashSet<>(dokumente.values());
  }

  private Dokument parseAndValidateDokument(String dokumentName, byte[] file) {
    return switch (dokumentName.split("-")[0]) {
      case "rechtsetzungsdokument" -> ldmlDeValidator.parseAndValidateRechtsetzungsdokument(
        new String(file)
      );
      case "regelungstext" -> ldmlDeValidator.parseAndValidateRegelungstext(new String(file));
      case "offenestruktur" -> ldmlDeValidator.parseAndValidateOffeneStruktur(new String(file));
      case "bekanntmachungstext" -> ldmlDeValidator.parseAndValidateBekanntmachung(
        new String(file)
      );
      default -> throw new InvalidDokumentTypeException(dokumentName);
    };
  }

  private boolean isXmlFile(String dokumentName) {
    return MediaTypeFactory
      .getMediaType(dokumentName)
      .map(type -> type.equalsTypeAndSubtype(MediaType.APPLICATION_XML))
      .orElse(false);
  }
}
