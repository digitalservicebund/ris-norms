package de.bund.digitalservice.ris.norms.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.core.io.UrlResource;

/**
 * Unit tests for ZipUtils.
 *
 * <p>Tests use JUnit 5's {@link TempDir} to create temporary directories for generating ZIP files dynamically. This ensures:
 * <ul>
 *   <li>Tests do not rely on hardcoded files or commit large binary files in the repo.</li>
 *   <li>Temporary files are cleaned up automatically after each test.</li>
 *   <li>Safe isolation between test runs and no pollution of the workspace.</li>
 * </ul>
 */
class ZipUtilsTest {

  @Nested
  class unzipFileWithoutDirectories {

    @Test
    void itUnpacksASimpleArchive() throws IOException {
      try (
        InputStream inputStream = new UrlResource(
          Objects.requireNonNull(
            ZipUtilsTest.class.getResource(
              ZipUtilsTest.class.getSimpleName() + "/valid-archive.zip"
            )
          )
        ).getInputStream();
      ) {
        var files = ZipUtils.unzipFileWithoutDirectories(inputStream);

        assertThat(files)
          .hasSize(2)
          .containsKeys("rechtsetzungsdokument.xml", "regelungstext-1.xml");
      }
    }

    @Test
    void itFailsToUnpackArchiveWithFolder(@TempDir Path tempDir) throws IOException {
      Path zipPath = TestZipHelper.createZipWithFolder(tempDir);
      try (InputStream in = Files.newInputStream(zipPath)) {
        assertThatThrownBy(() -> ZipUtils.unzipFileWithoutDirectories(in))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("Zip contains folder");
      }
    }

    @Test
    void itFailsToUnpackArchiveWithPathTraversal(@TempDir Path tempDir) throws IOException {
      Path zipPath = TestZipHelper.createZipWithPathTraversal(tempDir);
      try (InputStream in = Files.newInputStream(zipPath)) {
        assertThatThrownBy(() -> ZipUtils.unzipFileWithoutDirectories(in))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("Zip contains path traversals");
      }
    }

    @Test
    void itFailsWhenTooManyFilesAreIncluded(@TempDir Path tempDir) throws IOException {
      Path zipPath = TestZipHelper.createZipWithTooManyFiles(tempDir);
      try (InputStream in = Files.newInputStream(zipPath)) {
        assertThatThrownBy(() -> ZipUtils.unzipFileWithoutDirectories(in))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("Too many files to be extracted");
      }
    }

    @Test
    void itFailsWhenSingleFileIsTooLarge(@TempDir Path tempDir) throws Exception {
      Path zipPath = TestZipHelper.createZipWithLargeFile(tempDir, 201); // 201 MB
      try (var in = Files.newInputStream(zipPath)) {
        assertThatThrownBy(() -> ZipUtils.unzipFileWithoutDirectories(in))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("File large-file.dat is too large to be extracted");
      }
    }

    @Test
    void itFailsWhenArchiveIsTooLarge(@TempDir Path tempDir) throws Exception {
      // 201 files * 1MB each = 201MB > 200MB limit
      Path zipPath = TestZipHelper.createZipWithTotalSizeExceedingLimit(tempDir, 201);
      try (var in = Files.newInputStream(zipPath)) {
        assertThatThrownBy(() -> ZipUtils.unzipFileWithoutDirectories(in))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("Archive too large to be extracted");
      }
    }
  }
}
