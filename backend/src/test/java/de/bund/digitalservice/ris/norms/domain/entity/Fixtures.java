package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.application.service.LdmlDeValidator;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.UrlResource;

public class Fixtures {

  private static final LdmlDeValidator ldmlDeValidator = new LdmlDeValidator(
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource("/LegalDocML.de/1.7.2/schema/legalDocML.de.xsl")
      )
    ),
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource(
            "/LegalDocML.de/1.7.2/legalDocML.de-risnorms-regelungstextverkuendungsfassung.xsd"
          )
      )
    )
  );

  public static Norm loadNormFromDisk(final String fileName) {
    return loadNormFromDisk(fileName, false);
  }

  public static Norm loadNormFromDisk(final String fileName, boolean validated) {
    if (validated) {
      return ldmlDeValidator.parseAndValidate(loadFile(fileName));
    }

    return Norm.builder().regelungstexte(Set.of(loadRegelungstextFromDisk(fileName))).build();
  }

  public static Regelungstext loadRegelungstextFromDisk(final String fileName) {
    return new Regelungstext(XmlMapper.toDocument(loadFile(fileName)));
  }

  public static String loadTextFromDisk(final String fileName) {
    return loadFile(fileName);
  }

  private static String loadFile(final String fileName) {
    try {
      return IOUtils.toString(
        Objects.requireNonNull(Fixtures.class.getResourceAsStream(fileName)),
        StandardCharsets.UTF_8
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
