package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class NormFixtures {
  static Norm simpleNorm() {
    return Norm.builder()
        .document(XmlMapper.toDocument(
            NormFixtures.loadNormFile("SimpleNorm.xml")
        ))
        .build();
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
