package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.apache.commons.io.IOUtils;

public class NormFixtures {
  static Norm simpleNorm() {
    return Norm.builder()
        .document(XmlMapper.toDocument(NormFixtures.loadNormFile("SimpleNorm.xml")))
        .build();
  }

  static Norm normWithPassiveModifications() {
    return Norm.builder()
        .document(
            XmlMapper.toDocument(NormFixtures.loadNormFile("NormWithPassiveModifications.xml")))
        .build();
  }

  static Norm normWithMods() {
    return Norm.builder()
        .document(XmlMapper.toDocument(NormFixtures.loadNormFile("NormWithMods.xml")))
        .build();
  }

  private static String loadNormFile(final String fileName) {
    try {
      return IOUtils.toString(
          Objects.requireNonNull(NormFixtures.class.getResourceAsStream(fileName)),
          StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
