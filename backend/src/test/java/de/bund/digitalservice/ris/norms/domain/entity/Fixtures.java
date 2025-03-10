package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.application.service.LdmlDeValidator;
import de.bund.digitalservice.ris.norms.application.service.XsdSchemaService;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.UrlResource;

public class Fixtures {

  private static final XsdSchemaService xsdSchemaService = new XsdSchemaService(
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource("/LegalDocML.de/1.7.2/schema/legalDocML.de-baukasten.xsd")
      )
    ),
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource("/LegalDocML.de/1.7.2/schema/legalDocML.de-metadaten.xsd")
      )
    ),
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource(
            "/LegalDocML.de/1.7.2/schema/legalDocML.de-regelungstextverkuendungsfassung.xsd"
          )
      )
    ),
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource(
            "/LegalDocML.de/1.7.2/legalDocML.de-risnorms-regelungstextverkuendungsfassung.xsd"
          )
      )
    ),
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource(
            "/LegalDocML.de/1.7.2/legalDocML.de-risnorms-offenestruktur.xsd"
          )
      )
    )
  );

  private static final LdmlDeValidator ldmlDeValidator = new LdmlDeValidator(
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource("/LegalDocML.de/1.7.2/schema/legalDocML.de.xsl")
      )
    ),
    xsdSchemaService
  );

  public static XsdSchemaService getXsdSchemaService() {
    return xsdSchemaService;
  }

  public static Norm loadNormFromDisk(final String fileName) {
    return loadNormFromDisk(fileName, false);
  }

  public static Norm loadNormFromDisk(final String fileName, boolean validated) {
    return Norm.builder().dokumente(Set.of(loadRegelungstextFromDisk(fileName, validated))).build();
  }

  public static Regelungstext loadRegelungstextFromDisk(final String fileName) {
    return loadRegelungstextFromDisk(fileName, false);
  }

  public static Regelungstext loadRegelungstextFromDisk(
    final String fileName,
    final boolean validated
  ) {
    if (validated) {
      return ldmlDeValidator.parseAndValidateRegelungstext(loadFile(fileName));
    }

    return new Regelungstext(XmlMapper.toDocument(loadFile(fileName)));
  }

  public static OffeneStruktur loadOffeneStrukturFromDisk(final String fileName) {
    return loadOffeneStrukturFromDisk(fileName, false);
  }

  public static OffeneStruktur loadOffeneStrukturFromDisk(
    final String fileName,
    final boolean validated
  ) {
    if (validated) {
      return ldmlDeValidator.parseAndValidateOffeneStruktur(loadFile(fileName));
    }

    return new OffeneStruktur(XmlMapper.toDocument(loadFile(fileName)));
  }

  public static BinaryFile loadBinaryFileFromDisk(
    final String fileName,
    DokumentManifestationEli dokumentManifestationEli
  ) {
    try (var resourceStream = Fixtures.class.getResourceAsStream(fileName)) {
      return new BinaryFile(
        dokumentManifestationEli,
        Objects.requireNonNull(resourceStream).readAllBytes()
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
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
