package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.application.service.LdmlDeValidator;
import de.bund.digitalservice.ris.norms.application.service.XsdSchemaService;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.UrlResource;

/**
 * Loader for norm fixtures.
 */
public class Fixtures {

  private static final String LDMLDE_RESOURCE_FOLDER = "/LegalDocML.de/1.7.2";

  private static final String FIXTURES_RESOURCE_FOLDER = LDMLDE_RESOURCE_FOLDER + "/fixtures";

  private static final XsdSchemaService xsdSchemaService = new XsdSchemaService(
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource(
            LDMLDE_RESOURCE_FOLDER + "/schema/legalDocML.de-baukasten.xsd"
          )
      )
    ),
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource(
            LDMLDE_RESOURCE_FOLDER + "/schema/legalDocML.de-metadaten.xsd"
          )
      )
    ),
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource(
            LDMLDE_RESOURCE_FOLDER + "/schema/legalDocML.de-regelungstextverkuendungsfassung.xsd"
          )
      )
    ),
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource(
            LDMLDE_RESOURCE_FOLDER + "/legalDocML.de-risnorms-regelungstextverkuendungsfassung.xsd"
          )
      )
    ),
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource(
            LDMLDE_RESOURCE_FOLDER + "/legalDocML.de-risnorms-bekanntmachung.xsd"
          )
      )
    ),
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource(
            LDMLDE_RESOURCE_FOLDER + "/legalDocML.de-risnorms-offenestruktur.xsd"
          )
      )
    ),
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource(
            LDMLDE_RESOURCE_FOLDER + "/legalDocML.de-risnorms-rechtsetzungsdokument.xsd"
          )
      )
    )
  );

  private static final LdmlDeValidator ldmlDeValidator = new LdmlDeValidator(
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource(LDMLDE_RESOURCE_FOLDER + "/schema/legalDocML.de.xsl")
      )
    ),
    xsdSchemaService
  );

  public static XsdSchemaService getXsdSchemaService() {
    return xsdSchemaService;
  }

  public static LdmlDeValidator getLdmlDeValidator() {
    return ldmlDeValidator;
  }

  public static Norm loadNormFromDisk(final String fileName) {
    return loadNormFromDisk(fileName, false);
  }

  public static Norm loadNormFromDisk(final Class<?> clazz, final String fileName) {
    return loadNormFromDisk(clazz, fileName, false);
  }

  public static Norm loadNormFromDisk(final String fileName, boolean validated) {
    return loadNormFromDisk(getResource(fileName), validated);
  }

  public static Norm loadNormFromDisk(
    final Class<?> clazz,
    final String fileName,
    boolean validated
  ) {
    return loadNormFromDisk(getResource(clazz, fileName), validated);
  }

  private static Norm loadNormFromDisk(final URL fileName, boolean validated) {
    return Norm.builder().dokumente(Set.of(loadRegelungstextFromDisk(fileName, validated))).build();
  }

  public static Regelungstext loadRegelungstextFromDisk(final String fileName) {
    return loadRegelungstextFromDisk(fileName, false);
  }

  public static Regelungstext loadRegelungstextFromDisk(
    final Class<?> clazz,
    final String fileName
  ) {
    return loadRegelungstextFromDisk(clazz, fileName, false);
  }

  public static Regelungstext loadRegelungstextFromDisk(
    final String fileName,
    final boolean validated
  ) {
    return loadRegelungstextFromDisk(getResource(fileName), validated);
  }

  public static Regelungstext loadRegelungstextFromDisk(
    final Class<?> clazz,
    final String fileName,
    final boolean validated
  ) {
    return loadRegelungstextFromDisk(getResource(clazz, fileName), validated);
  }

  private static Regelungstext loadRegelungstextFromDisk(
    final URL fileName,
    final boolean validated
  ) {
    if (validated) {
      return ldmlDeValidator.parseAndValidateRegelungstext(loadTextFromDisk(fileName));
    }

    return new Regelungstext(XmlMapper.toDocument(loadTextFromDisk(fileName)));
  }

  public static OffeneStruktur loadOffeneStrukturFromDisk(final String fileName) {
    return loadOffeneStrukturFromDisk(fileName, false);
  }

  public static OffeneStruktur loadOffeneStrukturFromDisk(
    final String fileName,
    final boolean validated
  ) {
    return loadOffeneStrukturFromDisk(getResource(fileName), validated);
  }

  public static OffeneStruktur loadOffeneStrukturFromDisk(
    final Class<?> clazz,
    final String fileName
  ) {
    return loadOffeneStrukturFromDisk(clazz, fileName, false);
  }

  public static OffeneStruktur loadOffeneStrukturFromDisk(
    final Class<?> clazz,
    final String fileName,
    final boolean validated
  ) {
    return loadOffeneStrukturFromDisk(getResource(clazz, fileName), validated);
  }

  private static OffeneStruktur loadOffeneStrukturFromDisk(
    final URL fileName,
    final boolean validated
  ) {
    if (validated) {
      return ldmlDeValidator.parseAndValidateOffeneStruktur(loadTextFromDisk(fileName));
    }

    return new OffeneStruktur(XmlMapper.toDocument(loadTextFromDisk(fileName)));
  }

  public static BinaryFile loadBinaryFileFromDisk(
    final String fileName,
    DokumentManifestationEli dokumentManifestationEli
  ) {
    return loadBinaryFileFromDisk(getResource(fileName), dokumentManifestationEli);
  }

  public static BinaryFile loadBinaryFileFromDisk(
    final Class<?> clazz,
    final String fileName,
    DokumentManifestationEli dokumentManifestationEli
  ) {
    return loadBinaryFileFromDisk(getResource(clazz, fileName), dokumentManifestationEli);
  }

  private static BinaryFile loadBinaryFileFromDisk(
    final URL fileName,
    DokumentManifestationEli dokumentManifestationEli
  ) {
    try (var resourceStream = fileName.openStream();) {
      return new BinaryFile(
        dokumentManifestationEli,
        Objects.requireNonNull(resourceStream).readAllBytes()
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static String loadTextFromDisk(Class<?> testClass, final String fileName) {
    return loadTextFromDisk(getResource(testClass, fileName));
  }

  public static String loadTextFromDisk(final String fileName) {
    return loadTextFromDisk(getResource(fileName));
  }

  private static String loadTextFromDisk(final URL fileName) {
    try {
      return IOUtils.toString(
        Objects.requireNonNull(fileName.openStream()),
        StandardCharsets.UTF_8
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static URL getResource(String fileName) {
    return Optional
      .ofNullable(Fixtures.class.getResource(FIXTURES_RESOURCE_FOLDER + "/" + fileName))
      .orElseThrow(() -> new RuntimeException("Could not find fixture " + fileName));
  }

  public static URL getResource(Class<?> clazz, String fileName) {
    return Optional
      .ofNullable(clazz.getResource(clazz.getSimpleName() + "/" + fileName))
      .orElseThrow(() ->
        new RuntimeException(
          "Could not find fixture " + fileName + " in test resources for class " + clazz.getName()
        )
      );
  }
}
