package de.bund.digitalservice.ris.norms.utils;

import java.io.*;
import java.nio.file.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Helper class to create test ZIP files with various structures
 * for use in unit tests verifying ZIP extraction and validation.
 * <p>
 * Supports creating ZIPs with path traversal entries, folder entries,
 * and archives with too many files.
 */
public class TestZipHelper {

  /**
   * Creates a ZIP file containing a file with a path traversal in its name,
   * e.g., "../evil.sh", which should trigger path traversal detection.
   *
   * @param tempDir the directory to create the ZIP file in
   * @return the path to the created ZIP file
   */
  public static Path createZipWithPathTraversal(Path tempDir) throws IOException {
    return createZip(Map.of("../evil.sh", "malicious content"), tempDir);
  }

  /**
   * Creates a ZIP file containing an explicit folder entry ("folder/")
   * and a file inside that folder ("folder/file.txt").
   * This is used to test detection of actual folder entries in ZIP archives.
   *
   * @param tempDir the directory to create the ZIP file in
   * @return the path to the created ZIP file
   */
  public static Path createZipWithFolder(Path tempDir) throws IOException {
    Path zipPath = tempDir.resolve("folder-zip.zip");
    try (ZipOutputStream zipOut = new ZipOutputStream(Files.newOutputStream(zipPath))) {
      // Add a folder entry explicitly
      ZipEntry dirEntry = new ZipEntry("folder/");
      zipOut.putNextEntry(dirEntry);
      zipOut.closeEntry();

      // Add a file inside that folder
      ZipEntry fileEntry = new ZipEntry("folder/file.txt");
      zipOut.putNextEntry(fileEntry);
      zipOut.write("content".getBytes());
      zipOut.closeEntry();
    }
    return zipPath;
  }

  /**
   * Creates a ZIP file containing more than 1000 files, each named "file-{index}.txt" with minimal content,
   * used to test the max files extraction limit.
   *
   * @param tempDir the directory to create the ZIP file in
   * @return the path to the created ZIP file
   * @throws IOException if an I/O error occurs
   */
  public static Path createZipWithTooManyFiles(Path tempDir) throws IOException {
    var map = new LinkedHashMap<String, String>();
    for (int i = 0; i < 1001; i++) {
      map.put("file-" + i + ".txt", "x");
    }
    return createZip(map, tempDir);
  }

  /**
   * Create a zip with one file exceeding the single file max size.
   *
   * @param tempDir directory where zip will be created
   * @param sizeInMB file size in MB (should be > 200 to trigger)
   * @return path to zip file
   */
  public static Path createZipWithLargeFile(Path tempDir, int sizeInMB) throws IOException {
    Path zipPath = tempDir.resolve("large-file.zip");
    try (ZipOutputStream zipOut = new ZipOutputStream(Files.newOutputStream(zipPath))) {
      ZipEntry zipEntry = new ZipEntry("large-file.dat");
      zipOut.putNextEntry(zipEntry);

      // Write sizeInMB megabytes of zeros efficiently
      byte[] buffer = new byte[1024 * 1024]; // 1MB buffer
      for (int i = 0; i < sizeInMB; i++) {
        zipOut.write(buffer);
      }

      zipOut.closeEntry();
    }
    return zipPath;
  }

  /**
   * Create a zip with many files that total over the archive max size.
   * Each file will be 1MB so you can control how many files you add.
   *
   * @param tempDir directory where zip will be created
   * @param totalSizeMB total size in MB (> 200 to trigger)
   * @return path to zip file
   */
  public static Path createZipWithTotalSizeExceedingLimit(Path tempDir, int totalSizeMB)
    throws IOException {
    Path zipPath = tempDir.resolve("too-large-archive.zip");
    try (ZipOutputStream zipOut = new ZipOutputStream(Files.newOutputStream(zipPath))) {
      byte[] buffer = new byte[1024 * 1024]; // 1MB buffer filled with zeros

      for (int i = 0; i < totalSizeMB; i++) {
        ZipEntry zipEntry = new ZipEntry("file-" + i + ".dat");
        zipOut.putNextEntry(zipEntry);
        zipOut.write(buffer);
        zipOut.closeEntry();
      }
    }
    return zipPath;
  }

  private static Path createZip(Map<String, String> files, Path tempDir) throws IOException {
    Path zipPath = tempDir.resolve("test.zip");
    try (ZipOutputStream zipOut = new ZipOutputStream(Files.newOutputStream(zipPath))) {
      for (var entry : files.entrySet()) {
        ZipEntry zipEntry = new ZipEntry(entry.getKey());
        zipOut.putNextEntry(zipEntry);
        zipOut.write(entry.getValue().getBytes());
        zipOut.closeEntry();
      }
    }
    return zipPath;
  }
}
