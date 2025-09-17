package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.jspecify.annotations.NonNull;

/** UseCase for processing a Normendokumentationspaket */
public interface ProcessNormendokumentationspaketUseCase {
  /**
   * Process a Normendokumentationspaket. This includes validating it, adding it into our database and creating a new
   * {@link Verkuendung}
   *
   * @param options The options containing the processId for the Normendokumentationspaket
   * @return the newly created Verkündung
   */
  Verkuendung processNormendokumentationspaket(Options options) throws IOException;

  /**
   * The options for processing a Normendokumentationspaket.
   */
  sealed interface Options permits ProcessOptions, DirectProcessingOptions {}

  /**
   * The options for processing a Normendokumentationspaket that was previously uploaded into the bucket for the eVerkündung API.
   * This also validates the signature of the uploaded archive.
   *
   * @param processId the processId for the Normendokumentationspaket that should be processed
   */
  record ProcessOptions(UUID processId) implements Options {}

  /**
   * The options for directly processing an unsigned Normendokumentationspaket, without having it stored in the bucket for the eVerkündung API.
   *
   * @param zipFile the Normendokumentationspaket that should be processed
   * @param force in case a norm already exists, if set to true, the norm will be overwritten. Default: false
   */
  record DirectProcessingOptions(byte[] zipFile, boolean force) implements Options {
    public DirectProcessingOptions(byte[] zipFile) {
      this(zipFile, false);
    }

    @Override
    public boolean equals(Object o) {
      if (o == null || getClass() != o.getClass()) return false;
      DirectProcessingOptions that = (DirectProcessingOptions) o;
      return force == that.force && Objects.deepEquals(zipFile, that.zipFile);
    }

    @Override
    public int hashCode() {
      return Objects.hash(Arrays.hashCode(zipFile), force);
    }

    @NonNull
    @Override
    public String toString() {
      return "Options{" + "zipFile.length=" + zipFile.length + ", force=" + force + '}';
    }
  }

  /**
   * The import of a Normendokumentationspaket failed
   */
  abstract class NormendokumentationspaketImportFailedException
    extends RuntimeException
    implements NormsAppException {

    protected NormendokumentationspaketImportFailedException(String message) {
      super(message);
    }
  }

  /**
   * The uploaded file is not a zip file
   */
  class NotAZipFileException extends NormendokumentationspaketImportFailedException {

    public NotAZipFileException() {
      super("The uploaded file is not a zip file");
    }

    @Override
    public URI getType() {
      return URI.create("/errors/normendokumentationspaket-import-failed/not-a-zip-file");
    }

    @Override
    public String getTitle() {
      return "The uploaded file is not a zip file";
    }

    @Override
    public Map<String, Object> getProperties() {
      return Map.of();
    }
  }

  /**
   * The ZIP file does not contain a Rechtsetzungsdokument
   */
  class MissingRechtsetzungsdokumentException
    extends NormendokumentationspaketImportFailedException {

    public MissingRechtsetzungsdokumentException() {
      super("The ZIP file does not contain a Rechtsetzungsdokument");
    }

    @Override
    public URI getType() {
      return URI.create(
        "/errors/normendokumentationspaket-import-failed/missing-rechtsetzungsdokument"
      );
    }

    @Override
    public String getTitle() {
      return "The ZIP file does not contain a Rechtsetzungsdokument";
    }

    @Override
    public Map<String, Object> getProperties() {
      return Map.of();
    }
  }

  /**
   * A Dokument referenced by another Dokument is not part of the zip file
   */
  class MissingReferencedDokumentException extends NormendokumentationspaketImportFailedException {

    private final String dokumentName;

    public MissingReferencedDokumentException(String dokumentName) {
      super("Referenced Dokument " + dokumentName + " not found.");
      this.dokumentName = dokumentName;
    }

    @Override
    public URI getType() {
      return URI.create(
        "/errors/normendokumentationspaket-import-failed/missing-referenced-dokument"
      );
    }

    @Override
    public String getTitle() {
      return "A Dokument referenced by another Dokument is not part of the zip file";
    }

    @Override
    public Map<String, Object> getProperties() {
      return Map.of("dokumentName", dokumentName);
    }
  }

  /**
   * The structure of the zip file is not supported. E.g. it contains folders or is a zip bomb.
   */
  class InvalidStructureInZipFileException extends NormendokumentationspaketImportFailedException {

    public InvalidStructureInZipFileException(String message) {
      super(message);
    }

    @Override
    public URI getType() {
      return URI.create(
        "/errors/normendokumentationspaket-import-failed/invalid-structure-in-zip-file"
      );
    }

    @Override
    public String getTitle() {
      return "The structure of the zip file is not supported";
    }

    @Override
    public Map<String, Object> getProperties() {
      return Map.of();
    }
  }

  /**
   * The filename of a Dokument is not the same as its subtype
   */
  class MismatchBetweenFilenameAndSubtypeException
    extends NormendokumentationspaketImportFailedException {

    private final String fileName;
    private final String expectedSubtype;
    private final String subtype;

    public MismatchBetweenFilenameAndSubtypeException(
      String fileName,
      DokumentManifestationEli eli
    ) {
      super(
        "The subtype of the manifestation eli for " +
          fileName +
          " does not match the file name (Expected: " +
          fileName.split("\\.")[0] +
          ", Found: " +
          eli.getSubtype() +
          ")."
      );
      this.fileName = fileName;
      this.expectedSubtype = fileName.split("\\.")[0];
      this.subtype = eli.getSubtype();
    }

    @Override
    public URI getType() {
      return URI.create(
        "/errors/normendokumentationspaket-import-failed/filetype-and-subtype-mismatch"
      );
    }

    @Override
    public String getTitle() {
      return "Filename and Subtype are not matching";
    }

    @Override
    public Map<String, Object> getProperties() {
      return Map.of("fileName", fileName, "expectedSubtype", expectedSubtype, "subtype", subtype);
    }
  }

  /**
   * The Rechtsetzungsdokument does not reference any Regelungstext or Bekanntmachung
   */
  class NoRegelungstextOrBekanntmachungstextException
    extends NormendokumentationspaketImportFailedException {

    public NoRegelungstextOrBekanntmachungstextException() {
      super(
        "The normendokumentationspaket contains neither a regelungstext nor a beanntmachungstext"
      );
    }

    @Override
    public URI getType() {
      return URI.create(
        "/errors/normendokumentationspaket-import-failed/no-regelungstext-or-bekanntmachungstext"
      );
    }

    @Override
    public String getTitle() {
      return "The normendokumentationspaket contains neither a regelungstext nor a beanntmachungstext";
    }

    @Override
    public Map<String, Object> getProperties() {
      return Map.of();
    }
  }

  /**
   * Different Dokumente within the zip archive use different norm manifestation elis
   */
  class InconsistentEliException extends NormendokumentationspaketImportFailedException {

    private final String fileName;
    private final String expectedEli;
    private final String actualEli;

    public InconsistentEliException(
      String fileName,
      NormManifestationEli expectedEli,
      NormManifestationEli actualEli
    ) {
      super(
        "The Dokument %s uses a different norm manifestation eli (%s) than the rechtsetzungsdokument (%s)".formatted(
          fileName,
          actualEli,
          expectedEli
        )
      );
      this.fileName = fileName;
      this.expectedEli = expectedEli.toString();
      this.actualEli = actualEli.toString();
    }

    @Override
    public URI getType() {
      return URI.create("/errors/normendokumentationspaket-import-failed/inconsistent-eli");
    }

    @Override
    public String getTitle() {
      return "Dokumente use different norm manifestation elis";
    }

    @Override
    public Map<String, Object> getProperties() {
      return Map.of("fileName", fileName, "expectedEli", expectedEli, "actualEli", actualEli);
    }
  }

  /**
   * A file in the zip archive is of an unsupported file type
   */
  class UnsupportedFileTypeException extends NormendokumentationspaketImportFailedException {

    private final String fileName;

    public UnsupportedFileTypeException(String fileName) {
      super(
        "BinaryFile " +
          fileName +
          " is of an unsupported file type. Supported: XML, PDF, JPG, PNG, GIF"
      );
      this.fileName = fileName;
    }

    @Override
    public URI getType() {
      return URI.create("/errors/normendokumentationspaket-import-failed/unsupported-file-type");
    }

    @Override
    public String getTitle() {
      return "A file uses an unsupported file type";
    }

    @Override
    public Map<String, Object> getProperties() {
      return Map.of("file", fileName, "supportedTypes", List.of("XML", "PDF", "JPG", "PNG", "GIF"));
    }
  }

  /**
   * An internal error happened
   */
  class InternalErrorException extends NormendokumentationspaketImportFailedException {

    public InternalErrorException() {
      super("An internal error happened. Please contact the norms team.");
    }

    @Override
    public URI getType() {
      return URI.create("/errors/normendokumentationspaket-import-failed/internal-error");
    }

    @Override
    public String getTitle() {
      return "Internal Error";
    }

    @Override
    public Map<String, Object> getProperties() {
      return Map.of();
    }
  }
}
