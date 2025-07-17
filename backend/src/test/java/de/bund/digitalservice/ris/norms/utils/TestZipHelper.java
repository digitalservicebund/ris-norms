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
