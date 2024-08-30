package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.apache.commons.io.IOUtils;

public class NormFixtures {

  public static Norm loadFromDisk(final String fileName) {
    return loadFromDisk(fileName, false);
  }

  public static Norm loadFromDisk(final String fileName, boolean validated) {
    return Norm.builder().document(XmlMapper.toDocument(loadNormFile(fileName), validated)).build();
  }

  private static String loadNormFile(final String fileName) {
    try {
      return IOUtils.toString(
        Objects.requireNonNull(NormFixtures.class.getResourceAsStream(fileName)),
        StandardCharsets.UTF_8
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
