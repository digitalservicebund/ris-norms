package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.application.service.LdmlDeValidator;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.UrlResource;

public class NormFixtures {
  private static final LdmlDeValidator ldmlDeValidator =
      new LdmlDeValidator(
          new UrlResource(
              Objects.requireNonNull(
                  LdmlDeValidator.class.getResource("/schema/fixtures/legalDocML.de.xsl"))),
          new UrlResource(
              Objects.requireNonNull(
                  LdmlDeValidator.class.getResource(
                      "/schema/fixtures/ldml1.6_ds_regelungstext.xsd"))));

  public static Norm loadFromDisk(final String fileName) {
    return loadFromDisk(fileName, false);
  }

  public static String loadTextFromDisk(final String fileName) {
    return loadNormFile(fileName);
  }

  public static Norm loadFromDisk(final String fileName, boolean validated) {
    if (validated) {
      return ldmlDeValidator.parseAndValidate(loadNormFile(fileName));
    }

    return Norm.builder().document(XmlMapper.toDocument(loadNormFile(fileName))).build();
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
