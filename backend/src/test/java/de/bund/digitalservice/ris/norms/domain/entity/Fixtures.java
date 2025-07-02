package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.BinaryFileMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormManifestationMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.BinaryFileRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.application.service.LdmlDeValidator;
import de.bund.digitalservice.ris.norms.application.service.XsdSchemaService;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.InvalidDokumentTypeException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.UrlResource;

/**
 * Loader for norm fixtures.
 */
public class Fixtures {

  private static final String LDMLDE_RESOURCE_FOLDER = "/LegalDocML.de/1.8.1";

  private static final String LDMLDE_EXTENSION_RESOURCE_FOLDER =
    "/LegalDocML.de/ris-norms-ldml-schema-extensions/1.8.1";

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
    ),
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource(
          LDMLDE_RESOURCE_FOLDER + "/schema/legalDocML.de-metadaten-bundesregierung.xsd"
        )
      )
    ),
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource(
          LDMLDE_RESOURCE_FOLDER + "/schema/legalDocML.de-metadaten-regelungstext.xsd"
        )
      )
    ),
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource(
          LDMLDE_RESOURCE_FOLDER + "/schema/legalDocML.de-metadaten-rechtsetzungsdokument.xsd"
        )
      )
    ),
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource(
          LDMLDE_EXTENSION_RESOURCE_FOLDER + "/legalDocML.de-metadaten-ris.xsd"
        )
      )
    ),
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource(
          LDMLDE_EXTENSION_RESOURCE_FOLDER + "/norms-application-only-metadata.xsd"
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

  public static Norm loadNormFromDisk(final String folderName) {
    return loadNormFromDisk(folderName, false);
  }

  public static Norm loadNormFromDisk(final Class<?> clazz, final String folderName) {
    return loadNormFromDisk(clazz, folderName, false);
  }

  public static Norm loadNormFromDisk(final String folderName, boolean validated) {
    return loadNormFromDisk(getResource(folderName), validated);
  }

  public static Norm loadNormFromDisk(
    final Class<?> clazz,
    final String folderName,
    boolean validated
  ) {
    return loadNormFromDisk(getResource(clazz, folderName), validated);
  }

  private static Norm loadNormFromDisk(final URL folderName, boolean validated) {
    File folder = new File(folderName.getPath());

    Set<Dokument> dokumente = new HashSet<>();
    Set<BinaryFile> binaryFiles = new HashSet<>();
    NormManifestationEli normManifestationEli = null;

    for (File file : Objects.requireNonNull(folder.listFiles())) {
      if (file.isDirectory()) {
        continue;
      }

      try {
        switch (DokumentType.getByFileName(file.getName())) {
          case REGELUNGSTEXT_VERKUENDUNG:
            try {
              dokumente.add(loadRegelungstextFromDisk(file.toURI().toURL(), validated));
            } catch (MalformedURLException e) {
              throw new RuntimeException(e);
            }
            break;
          case ANLAGE_REGELUNGSTEXT:
            try {
              dokumente.add(loadOffeneStrukturFromDisk(file.toURI().toURL(), validated));
            } catch (MalformedURLException e) {
              throw new RuntimeException(e);
            }
            break;
          case RECHTSETZUNGSDOKUMENT:
            try {
              var rechtsetzungsdokument = loadRechtsetzungsdokumentFromDisk(
                file.toURI().toURL(),
                validated
              );
              dokumente.add(rechtsetzungsdokument);
              normManifestationEli = rechtsetzungsdokument.getManifestationEli().asNormEli();
            } catch (MalformedURLException e) {
              throw new RuntimeException(e);
            }
            break;
          case BEKANNTMACHUNGSTEXT:
            try {
              dokumente.add(loadBekanntmachungFromDisk(file.toURI().toURL(), validated));
            } catch (MalformedURLException e) {
              throw new RuntimeException(e);
            }
            break;
        }
      } catch (InvalidDokumentTypeException ignored) {
        try {
          assert normManifestationEli != null;
          binaryFiles.add(
            loadBinaryFileFromDisk(
              file.toURI().toURL(),
              DokumentManifestationEli.fromNormEli(
                normManifestationEli,
                file.getName().split("\\.")[0],
                file.getName().split("\\.")[1]
              )
            )
          );
        } catch (MalformedURLException e) {
          throw new RuntimeException(e);
        }
      }
    }

    return Norm.builder().dokumente(dokumente).binaryFiles(binaryFiles).build();
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

  private static Bekanntmachung loadBekanntmachungFromDisk(
    final URL fileName,
    final boolean validated
  ) {
    if (validated) {
      return ldmlDeValidator.parseAndValidateBekanntmachung(loadTextFromDisk(fileName));
    }

    return new Bekanntmachung(XmlMapper.toDocument(loadTextFromDisk(fileName)));
  }

  private static Rechtsetzungsdokument loadRechtsetzungsdokumentFromDisk(
    final URL fileName,
    final boolean validated
  ) {
    if (validated) {
      return ldmlDeValidator.parseAndValidateRechtsetzungsdokument(loadTextFromDisk(fileName));
    }

    return new Rechtsetzungsdokument(XmlMapper.toDocument(loadTextFromDisk(fileName)));
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
    try (var resourceStream = fileName.openStream()) {
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
    return Optional.ofNullable(
      Fixtures.class.getResource(FIXTURES_RESOURCE_FOLDER + "/" + fileName)
    ).orElseThrow(() -> new RuntimeException("Could not find fixture " + fileName));
  }

  public static URL getResource(Class<?> clazz, String fileName) {
    return Optional.ofNullable(
      clazz.getResource(clazz.getSimpleName() + "/" + fileName)
    ).orElseThrow(() ->
      new RuntimeException(
        "Could not find fixture " + fileName + " in test resources for class " + clazz.getName()
      )
    );
  }

  public static Norm loadAndSaveNormFixture(
    DokumentRepository dokumentRepository,
    BinaryFileRepository binaryFileRepository,
    NormManifestationRepository normManifestationRepository,
    String folderName,
    NormPublishState publishState
  ) {
    final Norm norm = Fixtures.loadNormFromDisk(folderName);
    return saveNormFixture(
      dokumentRepository,
      binaryFileRepository,
      normManifestationRepository,
      norm,
      publishState
    );
  }

  public static Norm loadAndSaveNormFixture(
    DokumentRepository dokumentRepository,
    BinaryFileRepository binaryFileRepository,
    NormManifestationRepository normManifestationRepository,
    Class<?> clazz,
    String folderName,
    NormPublishState publishState
  ) {
    final Norm norm = Fixtures.loadNormFromDisk(clazz, folderName);
    return saveNormFixture(
      dokumentRepository,
      binaryFileRepository,
      normManifestationRepository,
      norm,
      publishState
    );
  }

  private static Norm saveNormFixture(
    DokumentRepository dokumentRepository,
    BinaryFileRepository binaryFileRepository,
    NormManifestationRepository normManifestationRepository,
    Norm norm,
    NormPublishState publishState
  ) {
    norm
      .getDokumente()
      .forEach(dokument -> {
        dokumentRepository.save(DokumentMapper.mapToDto(dokument));
      });
    norm
      .getBinaryFiles()
      .forEach(binaryFile -> {
        binaryFileRepository.save(BinaryFileMapper.mapToDto(binaryFile));
      });
    var normDto = normManifestationRepository
      .findByManifestationEli(norm.getManifestationEli().toString())
      .orElseThrow();
    normDto.setPublishState(publishState);
    var savedNormDto = normManifestationRepository.save(normDto);
    return NormManifestationMapper.mapToDomain(savedNormDto);
  }
}
