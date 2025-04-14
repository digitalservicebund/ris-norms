package de.bund.digitalservice.ris.norms.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for unpacking zip archives
 */
@Slf4j
public class ZipUtils {

  private ZipUtils() {}

  private static final int MAX_FILES_TO_EXTRACT = 1000;
  private static final int MAX_UNCOMPRESSED_SIZE = 200 * 1024 * 1024; // 200 MB
  private static final int MAX_SINGLE_FILE_UNCOMPRESSED_SIZE = 200 * 1024 * 1024; // 200 MB

  /**
   * Extracts a zip file that does not contain any folders into a map that contains the filenames and uncompressed
   * content of the files.
   * It tries to protect against zip bombs by having a maximal amount of files to extract as well as a maximal filesize
   * for both individual files within the archive and for the whole archive. It also checks that the uncompressed
   * filesize for entries provided by the zip archive is correct.
   *
   * @param zipFile zip file to extract (may not contain any folders)
   * @return a map between the filenames and the extracted content of those files
   * @throws IOException thrown during problems reading the zip file
   */
  public static Map<String, byte[]> unzipFileWithoutDirectories(InputStream zipFile)
    throws IOException {
    Map<String, byte[]> files = new HashMap<>();

    try (ZipInputStream zipInputStream = new ZipInputStream(zipFile)) {
      var zipEntryCount = 0;
      var zipArchiveSize = 0;

      for (ZipEntry entry; (entry = zipInputStream.getNextEntry()) != null;) {
        zipEntryCount++;

        if (entry.isDirectory()) {
          throw new IllegalArgumentException(
            "Zip contains folder (\"%s\"). This is not supported.".formatted(entry.getName())
          );
        }

        if (entry.getSize() > MAX_SINGLE_FILE_UNCOMPRESSED_SIZE) {
          throw new IllegalArgumentException(
            "File %s is too large to be extracted".formatted(entry.getName())
          );
        }

        byte[] entryBytes = new byte[(int) entry.getSize()];
        int readBytes = zipInputStream.readNBytes(entryBytes, 0, (int) entry.getSize());

        // We expect the stream to have read the whole file and only have one single byte left that is indicating the end of the stream.
        // Otherwise, the zip file was modified and the entry size is not correct so it could be a zip-bomb.
        if (zipInputStream.available() != 1) {
          throw new IllegalArgumentException(
            "Expected uncompressed filesize (%s) differs from actual file size for %s".formatted(
                entry.getSize(),
                entry.getName()
              )
          );
        }

        zipArchiveSize += readBytes;

        log.debug("Extracted {} (Size: {} bytes)", entry.getName(), readBytes);
        files.put(entry.getName(), entryBytes);

        if (zipArchiveSize > MAX_UNCOMPRESSED_SIZE) {
          throw new IllegalArgumentException("Archive too large to be extracted");
        }

        if (zipEntryCount > MAX_FILES_TO_EXTRACT) {
          throw new IllegalArgumentException("Too many files to be extracted");
        }
      }
    }

    return files;
  }
}
