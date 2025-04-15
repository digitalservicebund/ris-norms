package de.bund.digitalservice.ris.norms.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.UrlResource;

class ZipUtilsTest {

  private InputStream loadResource(String name) throws IOException {
    return new UrlResource(
      Objects.requireNonNull(
        ZipUtilsTest.class.getResource(ZipUtilsTest.class.getSimpleName() + "/" + name)
      )
    )
      .getInputStream();
  }

  @Nested
  class unzipFileWithoutDirectories {

    @Test
    void itUnpacksASimpleArchive() throws IOException {
      var files = ZipUtils.unzipFileWithoutDirectories(loadResource("valid-archive.zip"));

      assertThat(files).hasSize(2).containsKeys("rechtsetzungsdokument.xml", "regelungstext-1.xml");
    }

    @Test
    void itFailsToUnpackArchiveWithFolder() throws IOException {
      var inputStream = loadResource("archive-with-folder.zip");
      assertThatThrownBy(() -> ZipUtils.unzipFileWithoutDirectories(inputStream))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Zip contains folder (\"folder/\"). This is not supported.");
    }
  }
}
