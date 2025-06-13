package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.NormManifestationDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormManifestationMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.VerkuendungMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.BinaryFileRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.VerkuendungRepository;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

@WithMockUser(roles = { Roles.NORMS_USER })
class VerkuendungenControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private VerkuendungRepository verkuendungRepository;

  @Autowired
  private DokumentRepository dokumentRepository;

  @Autowired
  private BinaryFileRepository binaryFileRepository;

  @Autowired
  private NormManifestationRepository normManifestationRepository;

  @AfterEach
  void cleanUp() {
    verkuendungRepository.deleteAll();
    dokumentRepository.deleteAll();
    binaryFileRepository.deleteAll();
    normManifestationRepository.deleteAll();
  }

  @Nested
  class getAllVerkuendungen {

    @Test
    void itReturnsEmptyListOfVerkuendungen() throws Exception {
      // given no Verkuendung in the DB

      // when
      mockMvc
        .perform(get("/api/v1/verkuendungen").accept(MediaType.APPLICATION_JSON))
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").doesNotExist());
    }

    @Test
    void itReturnsAllVerkuendungenNorm() throws Exception {
      // Given
      var verkuendung = Verkuendung.builder()
        .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"))
        .build();
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
        NormPublishState.UNPUBLISHED
      );
      verkuendungRepository.save(VerkuendungMapper.mapToDto(verkuendung));

      // When
      mockMvc
        .perform(get("/api/v1/verkuendungen").accept(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").exists())
        .andExpect(jsonPath("$[1]").doesNotExist())
        .andExpect(
          jsonPath("$[0].title", equalTo("Gesetz zur Regelung des öffentlichen Vereinsrechts"))
        )
        .andExpect(
          jsonPath(
            "$[0].eli",
            equalTo("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1")
          )
        )
        .andExpect(jsonPath("$[0].frbrDateVerkuendung", equalTo("1964-08-05")))
        .andExpect(jsonPath("$[0].dateAusfertigung", equalTo("1964-08-05")))
        .andExpect(jsonPath("$[0].importedAt").exists());
    }
  }

  @Nested
  class getVerkuendung {

    @Test
    void itReturnsVerkuendung() throws Exception {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
        NormPublishState.UNPUBLISHED
      );
      var verkuendung = Verkuendung.builder()
        .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"))
        .importTimestamp(Instant.parse("2025-03-13T15:00:00Z"))
        .build();

      verkuendungRepository.save(VerkuendungMapper.mapToDto(verkuendung));

      // When
      mockMvc
        .perform(
          get("/api/v1/verkuendungen/{eli}", "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu").accept(
            MediaType.APPLICATION_JSON
          )
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(
          jsonPath("eli").value(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
          )
        )
        .andExpect(jsonPath("title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts"))
        .andExpect(jsonPath("shortTitle").value("Vereinsgesetz"))
        .andExpect(jsonPath("fna").value("754-28-1"))
        .andExpect(jsonPath("frbrName").value("BGBl. I"))
        .andExpect(jsonPath("frbrNumber").value("s593"))
        .andExpect(jsonPath("frbrDateVerkuendung").value("1964-08-05"))
        .andExpect(jsonPath("dateAusfertigung").value("1964-08-05"))
        .andExpect(jsonPath("importedAt").exists());
    }

    @Test
    void itReturns404() throws Exception {
      // Given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu";
      // When
      mockMvc
        .perform(get("/api/v1/verkuendungen/{eli}", eli).accept(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isNotFound());
    }
  }

  @Nested
  class getVerkuendungsZielnormen {

    @Test
    void itReturnsAllZielnormen() throws Exception {
      // Given

      var verkuendung = Verkuendung.builder()
        .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"))
        .build();
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23",
        NormPublishState.UNPUBLISHED
      );
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/2017/s593/2017-03-15/1/deu/2017-03-15",
        NormPublishState.UNPUBLISHED
      );
      verkuendungRepository.save(VerkuendungMapper.mapToDto(verkuendung));

      // When
      mockMvc
        .perform(
          get("/api/v1/verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen").accept(
            MediaType.APPLICATION_JSON
          )
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").exists())
        .andExpect(jsonPath("$[1]").doesNotExist())
        .andExpect(
          jsonPath("$[0].title", equalTo("Gesetz zur Regelung des öffentlichen Vereinsrechts"))
        )
        .andExpect(
          jsonPath(
            "$[0].eli",
            equalTo("eli/bund/bgbl-1/2017/s593/2017-03-15/1/deu/regelungstext-verkuendung-1")
          )
        )
        .andExpect(jsonPath("$[0].shortTitle").value("Vereinsgesetz"))
        .andExpect(jsonPath("$[0].fna").value("754-28-1"))
        .andExpect(jsonPath("$[0].status").value("status-not-yet-implemented"));
    }
  }

  @Nested
  class postVerkuendung {

    @Test
    void itCreatesANewVerkuendung() throws Exception {
      // Given
      var file = new MockMultipartFile(
        "file",
        "norm.zip",
        "application/zip",
        loadFolderAsZip(
          Fixtures.getResource("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23")
        )
      );

      // When // Then
      mockMvc
        .perform(multipart("/api/v1/verkuendungen").file(file).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(
          jsonPath(
            "eli",
            equalTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-verkuendung-1")
          )
        )
        .andExpect(
          jsonPath(
            "title",
            equalTo("Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes")
          )
        )
        .andExpect(jsonPath("frbrDateVerkuendung", equalTo("2017-03-15")))
        .andExpect(jsonPath("dateAusfertigung", equalTo("1900-01-01")))
        .andExpect(jsonPath("importedAt").exists());

      // Assert norms was created
      assertThat(
        dokumentRepository.findByEliDokumentManifestation(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-verkuendung-1.xml"
        )
      ).isPresent();
    }

    @Test
    void itStoresTheImportTimestamp() throws Exception {
      // Given
      var file = new MockMultipartFile(
        "file",
        "norm.zip",
        "application/zip",
        loadFolderAsZip(
          Fixtures.getResource("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23")
        )
      );

      // When / Then
      mockMvc
        .perform(multipart("/api/v1/verkuendungen").file(file).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(
          jsonPath(
            "eli",
            equalTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-verkuendung-1")
          )
        );

      // Then
      var verkuendung = verkuendungRepository.findByEliNormExpression(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"
      );
      assertThat(verkuendung.get().getImportTimestamp()).isCloseTo(
        Instant.now(),
        new TemporalUnitWithinOffset(5, ChronoUnit.MINUTES)
      );
    }

    @Test
    void itFailsIfTheFileIsNotAnZipFile() throws Exception {
      // Given
      var file = new MockMultipartFile(
        "file",
        "norm.txt",
        "text/plain",
        new ByteArrayInputStream("not-important".getBytes())
      );

      // When // Then
      mockMvc
        .perform(multipart("/api/v1/verkuendungen").file(file).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(
          jsonPath(
            "type",
            equalTo("/errors/normendokumentationspaket-import-failed/not-a-zip-file")
          )
        );
    }

    @Test
    void itFailsIfTheXmlIsNotLdmlDe() throws Exception {
      //Given
      var file = new MockMultipartFile(
        "file",
        "norm.zip",
        "application/zip",
        loadFolderAsZip(
          Fixtures.getResource(VerkuendungenControllerIntegrationTest.class, "non-ldml-de-content")
        )
      );

      // When // Then
      mockMvc
        .perform(multipart("/api/v1/verkuendungen").file(file).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/ldml-de-not-valid")));
    }

    @Test
    void itFailsIfTheNormAlreadyExist() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-verkuendung-1.xml"
          )
        )
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
          )
        )
      );

      var file = new MockMultipartFile(
        "file",
        "norm.zip",
        "application/zip",
        loadFolderAsZip(
          Fixtures.getResource("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23")
        )
      );

      // When // Then
      mockMvc
        .perform(multipart("/api/v1/verkuendungen").file(file).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/norm-with-eli-exists-already")));
    }

    @Test
    void itFailsIfANormWithSameGuidAlreadyExist() throws Exception {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
        NormPublishState.UNPUBLISHED
      );

      var file = new MockMultipartFile(
        "file",
        "norm.zip",
        "application/zip",
        loadFolderAsZip(
          Fixtures.getResource(
            VerkuendungenControllerIntegrationTest.class,
            "vereinsgesetz-same-guid-different-eli"
          )
        )
      );

      // When // Then
      mockMvc
        .perform(multipart("/api/v1/verkuendungen").file(file).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/norm-with-guid-exists-already")));
    }

    @Test
    void itFailsIfTheNormIsInvalid() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
          )
        )
      );

      var file = new MockMultipartFile(
        "file",
        "norm.zip",
        "application/zip",
        loadFolderAsZip(
          Fixtures.getResource(
            VerkuendungenControllerIntegrationTest.class,
            "vereinsgesetz-xsd-invalid"
          )
        )
      );

      // When // Then
      mockMvc
        .perform(multipart("/api/v1/verkuendungen").file(file).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/ldml-de-not-valid")))
        .andExpect(
          jsonPath("errors[0].type", equalTo("/errors/ldml-de-not-valid/cvc-pattern-valid"))
        );
    }

    @Test
    void itFailsIfTheNormIsSchematronInvalid() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
          )
        )
      );

      var file = new MockMultipartFile(
        "file",
        "norm.zip",
        "application/zip",
        loadFolderAsZip(
          Fixtures.getResource(
            VerkuendungenControllerIntegrationTest.class,
            "vereinsgesetz-schematron-invalid"
          )
        )
      );

      // When // Then
      mockMvc
        .perform(multipart("/api/v1/verkuendungen").file(file).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/ldml-de-not-schematron-valid")))
        .andExpect(
          jsonPath(
            "errors[0].type",
            equalTo("/errors/ldml-de-not-schematron-valid/failed-assert/SCH-00460-000")
          )
        )
        .andExpect(jsonPath("errors[0].eId", equalTo("meta-n1_geltzeiten-n1")));
    }

    @Test
    void ifCreatesVerkuendungWithForce() throws Exception {
      // Given
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23",
        NormPublishState.UNPUBLISHED
      );

      var verkuendung = Verkuendung.builder()
        .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"))
        .build();
      verkuendungRepository.save(VerkuendungMapper.mapToDto(verkuendung));

      var file = new MockMultipartFile(
        "file",
        "norm.zip",
        "application/zip",
        loadFolderAsZip(
          Fixtures.getResource("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23")
        )
      );

      // When // Then
      mockMvc
        .perform(
          multipart("/api/v1/verkuendungen?force=true")
            .file(file)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(
          jsonPath(
            "eli",
            equalTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-verkuendung-1")
          )
        );

      assertThat(dokumentRepository.findAll()).hasSize(2);
      var regelungstextDto = dokumentRepository.findByEliDokumentManifestation(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-verkuendung-1.xml"
      );
      assertThat(regelungstextDto).isPresent();
      final Diff regelungstextDiff = DiffBuilder.compare(
        Input.from(regelungstextDto.get().getXml())
      )
        .withTest(
          Input.from(
            Fixtures.loadRegelungstextFromDisk(
              "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-verkuendung-1.xml"
            ).getDocument()
          )
        )
        .ignoreWhitespace()
        .build();
      assertThat(regelungstextDiff.hasDifferences()).isFalse();

      var rechtsetzungsDokumentDto = dokumentRepository.findByEliDokumentManifestation(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/rechtsetzungsdokument-1.xml"
      );
      assertThat(rechtsetzungsDokumentDto).isPresent();
      final Diff rechtsetzungsDokumentDiff = DiffBuilder.compare(
        Input.from(rechtsetzungsDokumentDto.get().getXml())
      )
        .withTest(
          Input.from(
            Fixtures.loadNormFromDisk("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23")
              .getRechtsetzungsdokument()
              .getDocument()
          )
        )
        .ignoreWhitespace()
        .build();
      assertThat(rechtsetzungsDokumentDiff.hasDifferences()).isFalse();
    }
  }

  @Nested
  class getZielnormenPreview {

    @Test
    void itShouldReturnZielnormenPreviewForNoExistingExpressions() throws Exception {
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23",
        NormPublishState.PUBLISHED
      );
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05",
        NormPublishState.PUBLISHED
      );

      mockMvc
        .perform(
          get(
            "/api/v1/verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen/expressions/preview"
          ).accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].normWorkEli").value("eli/bund/bgbl-1/1964/s593"))
        .andExpect(
          jsonPath("$[0].title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts")
        )
        .andExpect(jsonPath("$[0].shortTitle").value("Vereinsgesetz"))
        .andExpect(
          jsonPath("$[0].expressions[0].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu"
          )
        )
        .andExpect(jsonPath("$[0].expressions[0].isGegenstandslos").value(false))
        .andExpect(jsonPath("$[0].expressions[0].isCreated").value(false))
        .andExpect(jsonPath("$[0].expressions[0].createdBy").value("diese Verkündung"));
    }

    @Test
    void itShouldReturnZielnormenPreviewForExistingExpressions() throws Exception {
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-verkuendung-1.xml"
          )
        )
      );
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        VerkuendungenControllerIntegrationTest.class,
        "vereinsgesetz-2017-03-16-1",
        NormPublishState.PUBLISHED
      );
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        VerkuendungenControllerIntegrationTest.class,
        "vereinsgesetz-2017-03-21-1-gegenstandlos",
        NormPublishState.PUBLISHED
      );
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        VerkuendungenControllerIntegrationTest.class,
        "vereinsgesetz-2017-04-16-1-gegenstandlos",
        NormPublishState.PUBLISHED
      );
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        VerkuendungenControllerIntegrationTest.class,
        "vereinsgesetz-2017-04-16-2",
        NormPublishState.PUBLISHED
      );

      mockMvc
        .perform(
          get(
            "/api/v1/verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen/expressions/preview"
          ).accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].normWorkEli").value("eli/bund/bgbl-1/1964/s593"))
        .andExpect(
          jsonPath("$[0].title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts")
        )
        .andExpect(jsonPath("$[0].shortTitle").value("Vereinsgesetz"))
        .andExpect(
          jsonPath("$[0].expressions[0].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu"
          )
        )
        .andExpect(jsonPath("$[0].expressions[0].isGegenstandslos").value(true))
        .andExpect(jsonPath("$[0].expressions[0].isCreated").value(true))
        .andExpect(jsonPath("$[0].expressions[0].createdBy").value("andere Verkündung"))
        .andExpect(
          jsonPath("$[0].expressions[1].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2017-03-16/2/deu"
          )
        )
        .andExpect(jsonPath("$[0].expressions[1].isGegenstandslos").value(false))
        .andExpect(jsonPath("$[0].expressions[1].isCreated").value(false))
        .andExpect(jsonPath("$[0].expressions[1].createdBy").value("diese Verkündung"))
        .andExpect(
          jsonPath("$[0].expressions[2].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2017-04-16/2/deu"
          )
        )
        .andExpect(jsonPath("$[0].expressions[2].isGegenstandslos").value(true))
        .andExpect(jsonPath("$[0].expressions[2].isCreated").value(true))
        .andExpect(jsonPath("$[0].expressions[2].createdBy").value("andere Verkündung"))
        .andExpect(
          jsonPath("$[0].expressions[3].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2017-04-16/3/deu"
          )
        )
        .andExpect(jsonPath("$[0].expressions[3].isGegenstandslos").value(false))
        .andExpect(jsonPath("$[0].expressions[3].isCreated").value(false))
        .andExpect(jsonPath("$[0].expressions[3].createdBy").value("System"))
        .andExpect(jsonPath("$[0].expressions[4]").doesNotExist())
        .andExpect(jsonPath("$[1]").doesNotExist());
    }

    @Test
    void itShouldReturnZielnormenPreviewContainingOrphans() throws Exception {
      // amendingLaw
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        VerkuendungenControllerIntegrationTest.class,
        "amending-law-for-vereinsgesetz-with-two-orphans",
        NormPublishState.PUBLISHED
      );
      // targetLaw
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        VerkuendungenControllerIntegrationTest.class,
        "vereinsgesetz-original-expression",
        NormPublishState.PUBLISHED
      );
      // orphanBeforeFirstGeltungszeitregel
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        VerkuendungenControllerIntegrationTest.class,
        "vereinsgesetz-2017-03-16-1",
        NormPublishState.PUBLISHED
      );
      // orphanAfterFirstGeltungszeitregel
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        VerkuendungenControllerIntegrationTest.class,
        "vereinsgesetz-2024-05-30",
        NormPublishState.PUBLISHED
      );

      mockMvc
        .perform(
          get(
            "/api/v1/verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen/expressions/preview"
          ).accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].normWorkEli").value("eli/bund/bgbl-1/1964/s593"))
        .andExpect(
          jsonPath("$[0].title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts")
        )
        .andExpect(jsonPath("$[0].shortTitle").value("Vereinsgesetz"))
        .andExpect(
          jsonPath("$[0].expressions[0].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu"
          )
        )
        .andExpect(jsonPath("$[0].expressions[0].isGegenstandslos").value(false))
        .andExpect(jsonPath("$[0].expressions[0].isCreated").value(true))
        .andExpect(jsonPath("$[0].expressions[0].isOrphan").value(true))
        .andExpect(jsonPath("$[0].expressions[0].createdBy").value("diese Verkündung"))
        .andExpect(
          jsonPath("$[0].expressions[1].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2021-04-23/1/deu"
          )
        )
        .andExpect(jsonPath("$[0].expressions[1].isGegenstandslos").value(false))
        .andExpect(jsonPath("$[0].expressions[1].isCreated").value(false))
        .andExpect(jsonPath("$[0].expressions[1].isOrphan").value(false))
        .andExpect(jsonPath("$[0].expressions[1].createdBy").value("diese Verkündung"))
        .andExpect(
          jsonPath("$[0].expressions[2].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2024-05-30/1/deu"
          )
        )
        .andExpect(jsonPath("$[0].expressions[2].isGegenstandslos").value(false))
        .andExpect(jsonPath("$[0].expressions[2].isCreated").value(true))
        .andExpect(jsonPath("$[0].expressions[2].isOrphan").value(true))
        .andExpect(jsonPath("$[0].expressions[2].createdBy").value("diese Verkündung"))
        .andExpect(jsonPath("$[0].expressions[4]").doesNotExist())
        .andExpect(jsonPath("$[1]").doesNotExist());
    }
  }

  @Nested
  class createZielnormen {

    @Test
    void itShouldCreateNewZielnormen() throws Exception {
      var amendingNorm = Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        VerkuendungenControllerIntegrationTest.class,
        "amending-law-for-vereinsgesetz-several-zielnormen-references",
        NormPublishState.UNPUBLISHED
      );
      var targetNorm = Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        VerkuendungenControllerIntegrationTest.class,
        "vereinsgesetz-original-expression",
        NormPublishState.UNPUBLISHED
      );

      // Expressions to be created not yet existent
      final List<String> futureExpressionElis = List.of(
        "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu",
        "eli/bund/bgbl-1/1964/s593/2021-04-23/1/deu",
        "eli/bund/bgbl-1/1964/s593/2024-05-30/1/deu"
      );
      futureExpressionElis.forEach(expressionEli ->
        assertThat(
          normManifestationRepository.findFirstByExpressionEliOrderByManifestationEliDesc(
            expressionEli
          )
        ).isEmpty()
      );

      mockMvc
        .perform(
          post(
            String.format(
              "/api/v1/verkuendungen/%s/zielnormen/%s/expressions/create",
              amendingNorm.getExpressionEli(),
              targetNorm.getWorkEli()
            )
          ).accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("normWorkEli").value("eli/bund/bgbl-1/1964/s593"))
        .andExpect(jsonPath("title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts"))
        .andExpect(jsonPath("shortTitle").value("Vereinsgesetz"))
        .andExpect(
          jsonPath("expressions[0].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu"
          )
        )
        .andExpect(jsonPath("expressions[0].isGegenstandslos").value(false))
        .andExpect(jsonPath("expressions[0].isCreated").value(true))
        .andExpect(jsonPath("expressions[0].createdBy").value("diese Verkündung"))
        .andExpect(
          jsonPath("expressions[1].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2021-04-23/1/deu"
          )
        )
        .andExpect(jsonPath("expressions[1].isGegenstandslos").value(false))
        .andExpect(jsonPath("expressions[1].isCreated").value(true))
        .andExpect(jsonPath("expressions[1].createdBy").value("diese Verkündung"))
        .andExpect(
          jsonPath("expressions[2].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2024-05-30/1/deu"
          )
        )
        .andExpect(jsonPath("expressions[2].isGegenstandslos").value(false))
        .andExpect(jsonPath("expressions[2].isCreated").value(true))
        .andExpect(jsonPath("expressions[2].createdBy").value("diese Verkündung"));

      futureExpressionElis.forEach(expressionEli ->
        assertThat(
          normManifestationRepository.findFirstByExpressionEliOrderByManifestationEliDesc(
            expressionEli
          )
        ).isPresent()
      );
    }

    @Test
    void itShouldCreateNewZielnormenByRecreatingOne() throws Exception {
      var amendingNorm = Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        VerkuendungenControllerIntegrationTest.class,
        "amending-law-for-vereinsgesetz-several-zielnormen-references-and-one-created",
        NormPublishState.UNPUBLISHED
      );
      var targetNorm = Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        VerkuendungenControllerIntegrationTest.class,
        "vereinsgesetz-original-expression",
        NormPublishState.UNPUBLISHED
      );
      var alreadyExistingFutureExpressionToBeOverwritten = Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        VerkuendungenControllerIntegrationTest.class,
        "vereinsgesetz-2017-03-16-1",
        NormPublishState.UNPUBLISHED
      );

      // Expressions to be created not yet existent
      final List<String> futureExpressionElis = List.of(
        "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu",
        "eli/bund/bgbl-1/1964/s593/2021-04-23/1/deu",
        "eli/bund/bgbl-1/1964/s593/2024-05-30/1/deu"
      );
      assertThat(
        normManifestationRepository.findFirstByExpressionEliOrderByManifestationEliDesc(
          futureExpressionElis.get(0)
        )
      ).isPresent();
      assertThat(
        normManifestationRepository.findFirstByExpressionEliOrderByManifestationEliDesc(
          futureExpressionElis.get(1)
        )
      ).isEmpty();
      assertThat(
        normManifestationRepository.findFirstByExpressionEliOrderByManifestationEliDesc(
          futureExpressionElis.get(2)
        )
      ).isEmpty();

      final List<String> expressionElis = normManifestationRepository.findExpressionElisByWorkEli(
        "eli/bund/bgbl-1/1964/s593"
      );
      assertThat(expressionElis).hasSize(2);

      mockMvc
        .perform(
          post(
            String.format(
              "/api/v1/verkuendungen/%s/zielnormen/%s/expressions/create",
              amendingNorm.getExpressionEli(),
              targetNorm.getWorkEli()
            )
          ).accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("normWorkEli").value("eli/bund/bgbl-1/1964/s593"))
        .andExpect(jsonPath("title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts"))
        .andExpect(jsonPath("shortTitle").value("Vereinsgesetz"))
        .andExpect(
          jsonPath("expressions[0].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu"
          )
        )
        .andExpect(jsonPath("expressions[0].isGegenstandslos").value(false))
        .andExpect(jsonPath("expressions[0].isCreated").value(true))
        .andExpect(jsonPath("expressions[0].createdBy").value("diese Verkündung"))
        .andExpect(
          jsonPath("expressions[1].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2021-04-23/1/deu"
          )
        )
        .andExpect(jsonPath("expressions[1].isGegenstandslos").value(false))
        .andExpect(jsonPath("expressions[1].isCreated").value(true))
        .andExpect(jsonPath("expressions[1].createdBy").value("diese Verkündung"))
        .andExpect(
          jsonPath("expressions[2].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2024-05-30/1/deu"
          )
        )
        .andExpect(jsonPath("expressions[2].isGegenstandslos").value(false))
        .andExpect(jsonPath("expressions[2].isCreated").value(true))
        .andExpect(jsonPath("expressions[2].createdBy").value("diese Verkündung"));

      futureExpressionElis.forEach(expressionEli ->
        assertThat(
          normManifestationRepository.findFirstByExpressionEliOrderByManifestationEliDesc(
            expressionEli
          )
        ).isPresent()
      );

      // Recreated should have same current GUID but different point-in-time-manifestation
      final Optional<NormManifestationDto> recreated =
        normManifestationRepository.findFirstByExpressionEliOrderByManifestationEliDesc(
          "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu"
        );
      assertThat(recreated).isPresent();
      assertThat(
        NormManifestationEli.fromString(
          recreated.get().getManifestationEli()
        ).getPointInTimeManifestation()
      ).isAfter(
        alreadyExistingFutureExpressionToBeOverwritten
          .getManifestationEli()
          .getPointInTimeManifestation()
      );
      assertThat(recreated.get().getExpressionAktuelleVersionId()).isEqualTo(
        alreadyExistingFutureExpressionToBeOverwritten.getGuid()
      );

      final List<String> expressionsEliAfter =
        normManifestationRepository.findExpressionElisByWorkEli("eli/bund/bgbl-1/1964/s593");
      assertThat(expressionsEliAfter).hasSize(4);
    }

    @Test
    void itShouldCreateNewZielnormenAndSetGegenstandslos() throws Exception {
      var amendingNorm = Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        VerkuendungenControllerIntegrationTest.class,
        "amending-law-for-vereinsgesetz-several-zielnormen-references",
        NormPublishState.UNPUBLISHED
      );
      var targetNorm = Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        VerkuendungenControllerIntegrationTest.class,
        "vereinsgesetz-original-expression",
        NormPublishState.UNPUBLISHED
      );
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        VerkuendungenControllerIntegrationTest.class,
        "vereinsgesetz-2017-03-16-1",
        NormPublishState.UNPUBLISHED
      );

      // Expressions to be created not yet existent
      final List<String> futureExpressionElis = List.of(
        "eli/bund/bgbl-1/1964/s593/2017-03-16/2/deu",
        "eli/bund/bgbl-1/1964/s593/2021-04-23/1/deu",
        "eli/bund/bgbl-1/1964/s593/2024-05-30/1/deu"
      );
      futureExpressionElis.forEach(expressionEli ->
        assertThat(
          normManifestationRepository.findFirstByExpressionEliOrderByManifestationEliDesc(
            expressionEli
          )
        ).isEmpty()
      );

      mockMvc
        .perform(
          post(
            String.format(
              "/api/v1/verkuendungen/%s/zielnormen/%s/expressions/create",
              amendingNorm.getExpressionEli(),
              targetNorm.getWorkEli()
            )
          ).accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("normWorkEli").value("eli/bund/bgbl-1/1964/s593"))
        .andExpect(jsonPath("title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts"))
        .andExpect(jsonPath("shortTitle").value("Vereinsgesetz"))
        .andExpect(
          jsonPath("expressions[0].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu"
          )
        )
        .andExpect(jsonPath("expressions[0].isGegenstandslos").value(true))
        .andExpect(jsonPath("expressions[0].isCreated").value(true))
        .andExpect(jsonPath("expressions[0].createdBy").value("andere Verkündung"))
        .andExpect(
          jsonPath("expressions[1].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2017-03-16/2/deu"
          )
        )
        .andExpect(jsonPath("expressions[1].isGegenstandslos").value(false))
        .andExpect(jsonPath("expressions[1].isCreated").value(true))
        .andExpect(jsonPath("expressions[1].createdBy").value("diese Verkündung"))
        .andExpect(
          jsonPath("expressions[2].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2021-04-23/1/deu"
          )
        )
        .andExpect(jsonPath("expressions[2].isGegenstandslos").value(false))
        .andExpect(jsonPath("expressions[2].isCreated").value(true))
        .andExpect(jsonPath("expressions[2].createdBy").value("diese Verkündung"))
        .andExpect(
          jsonPath("expressions[3].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2024-05-30/1/deu"
          )
        )
        .andExpect(jsonPath("expressions[3].isGegenstandslos").value(false))
        .andExpect(jsonPath("expressions[3].isCreated").value(true))
        .andExpect(jsonPath("expressions[3].createdBy").value("diese Verkündung"));

      futureExpressionElis.forEach(expressionEli ->
        assertThat(
          normManifestationRepository.findFirstByExpressionEliOrderByManifestationEliDesc(
            expressionEli
          )
        ).isPresent()
      );

      final Optional<NormManifestationDto> gegendstandlosNormDto =
        normManifestationRepository.findFirstByExpressionEliOrderByManifestationEliDesc(
          "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu"
        );
      assertThat(gegendstandlosNormDto).isPresent();
      final Norm gegenstandslosNorm = NormManifestationMapper.mapToDomain(
        gegendstandlosNormDto.get()
      );
      assertThat(gegenstandslosNorm.isGegenstandlos()).isTrue();

      // Gegenstandlos and new replacing expression should have different current GUID
      final Optional<NormManifestationDto> replacingNewExpressionDto =
        normManifestationRepository.findFirstByExpressionEliOrderByManifestationEliDesc(
          "eli/bund/bgbl-1/1964/s593/2017-03-16/2/deu"
        );
      assertThat(replacingNewExpressionDto).isPresent();
      final Norm replacingNewExpression = NormManifestationMapper.mapToDomain(
        replacingNewExpressionDto.get()
      );
      assertThat(replacingNewExpression.isGegenstandlos()).isFalse();
      assertThat(replacingNewExpression.getGuid()).isNotEqualTo(gegenstandslosNorm.getGuid());

      // The new expression should have the previous id of the preceding expression (that can actually differ from the previous-id of the gegenstandslos)
      final Optional<NormManifestationDto> updatedPrecedingExpressionOptional =
        normManifestationRepository.findFirstByExpressionEliOrderByManifestationEliDesc(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"
        );
      assertThat(updatedPrecedingExpressionOptional).isPresent();
      final Norm updatedPrecedingExpression = NormManifestationMapper.mapToDomain(
        updatedPrecedingExpressionOptional.get()
      );
      assertThat(
        replacingNewExpression
          .getRegelungstext1()
          .getMeta()
          .getFRBRExpression()
          .getFRBRaliasPreviousVersionId()
      ).contains(
        updatedPrecedingExpression
          .getRegelungstext1()
          .getMeta()
          .getFRBRExpression()
          .getFRBRaliasCurrentVersionId()
      );

      // The preceding expression should have an updated next-id, set to the replacing expression
      assertThat(
        updatedPrecedingExpression
          .getRegelungstext1()
          .getMeta()
          .getFRBRExpression()
          .getFRBRaliasNextVersionId()
      ).contains(replacingNewExpression.getGuid());
    }

    @Test
    void itShouldRemoveOrphanExpressionBeforeFirstGeltungszeit() throws Exception {
      var amendingLaw = Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        VerkuendungenControllerIntegrationTest.class,
        "amending-law-for-vereinsgesetz-several-zielnormen-references-and-one-orphan-before-first-geltungszeit",
        NormPublishState.UNPUBLISHED
      );

      var targetLaw = Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        VerkuendungenControllerIntegrationTest.class,
        "vereinsgesetz-original-expression",
        NormPublishState.UNPUBLISHED
      );

      // alreadyExistingFutureExpressionToBeOverwritten
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        VerkuendungenControllerIntegrationTest.class,
        "vereinsgesetz-2017-03-16-1",
        NormPublishState.UNPUBLISHED
      );

      final String orphanExpressionEli = "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu";
      // Expressions to be created not yet existent
      final List<String> futureExpressionElis = List.of(
        "eli/bund/bgbl-1/1964/s593/2021-04-23/1/deu",
        "eli/bund/bgbl-1/1964/s593/2024-05-30/1/deu"
      );
      assertThat(
        normManifestationRepository.findFirstByExpressionEliOrderByManifestationEliDesc(
          orphanExpressionEli
        )
      ).isPresent();
      futureExpressionElis.forEach(f ->
        assertThat(
          normManifestationRepository.findFirstByExpressionEliOrderByManifestationEliDesc(f)
        ).isEmpty()
      );

      final List<String> expressionElis = normManifestationRepository.findExpressionElisByWorkEli(
        "eli/bund/bgbl-1/1964/s593"
      );
      assertThat(expressionElis).hasSize(2);

      mockMvc
        .perform(
          post(
            String.format(
              "/api/v1/verkuendungen/%s/zielnormen/%s/expressions/create",
              amendingLaw.getExpressionEli(),
              targetLaw.getWorkEli()
            )
          ).accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("normWorkEli").value("eli/bund/bgbl-1/1964/s593"))
        .andExpect(jsonPath("title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts"))
        .andExpect(jsonPath("shortTitle").value("Vereinsgesetz"))
        .andExpect(
          jsonPath("expressions[0].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2021-04-23/1/deu"
          )
        )
        .andExpect(jsonPath("expressions[0].isGegenstandslos").value(false))
        .andExpect(jsonPath("expressions[0].isCreated").value(true))
        .andExpect(jsonPath("expressions[0].createdBy").value("diese Verkündung"))
        .andExpect(
          jsonPath("expressions[1].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2024-05-30/1/deu"
          )
        )
        .andExpect(jsonPath("expressions[1].isGegenstandslos").value(false))
        .andExpect(jsonPath("expressions[1].isCreated").value(true))
        .andExpect(jsonPath("expressions[1].createdBy").value("diese Verkündung"));

      // Orphan was deleted
      assertThat(
        normManifestationRepository.findFirstByExpressionEliOrderByManifestationEliDesc(
          orphanExpressionEli
        )
      ).isEmpty();

      // New expressions created
      futureExpressionElis.forEach(f ->
        assertThat(
          normManifestationRepository.findFirstByExpressionEliOrderByManifestationEliDesc(f)
        ).isPresent()
      );

      final List<String> expressionsEliAfter =
        normManifestationRepository.findExpressionElisByWorkEli("eli/bund/bgbl-1/1964/s593");
      assertThat(expressionsEliAfter).hasSize(3);

      assertTimeLine(expressionsEliAfter);
    }

    @Test
    void itShouldRemoveOrphanExpressionAfterFirstGeltungszeit() throws Exception {
      var amendingLaw = Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        VerkuendungenControllerIntegrationTest.class,
        "amending-law-for-vereinsgesetz-several-zielnormen-references-and-one-orphan-after-first-geltungszeit",
        NormPublishState.UNPUBLISHED
      );

      var targetLaw = Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        VerkuendungenControllerIntegrationTest.class,
        "vereinsgesetz-original-expression",
        NormPublishState.UNPUBLISHED
      );

      // alreadyExistingFutureExpressionToBeOverwritten
      Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        VerkuendungenControllerIntegrationTest.class,
        "vereinsgesetz-2021-04-23",
        NormPublishState.UNPUBLISHED
      );

      final String orphanEli = "eli/bund/bgbl-1/1964/s593/2021-04-23/1/deu";
      // Expressions to be created not yet existent
      final List<String> futureExpressionElis = List.of(
        "eli/bund/bgbl-1/1964/s593/2016-03-16/1/deu",
        "eli/bund/bgbl-1/1964/s593/2024-05-30/1/deu"
      );
      assertThat(
        normManifestationRepository.findFirstByExpressionEliOrderByManifestationEliDesc(orphanEli)
      ).isPresent();
      futureExpressionElis.forEach(f ->
        assertThat(
          normManifestationRepository.findFirstByExpressionEliOrderByManifestationEliDesc(f)
        ).isEmpty()
      );

      final List<String> expressionElis = normManifestationRepository.findExpressionElisByWorkEli(
        "eli/bund/bgbl-1/1964/s593"
      );
      assertThat(expressionElis).hasSize(2);

      mockMvc
        .perform(
          post(
            String.format(
              "/api/v1/verkuendungen/%s/zielnormen/%s/expressions/create",
              amendingLaw.getExpressionEli(),
              targetLaw.getWorkEli()
            )
          ).accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("normWorkEli").value("eli/bund/bgbl-1/1964/s593"))
        .andExpect(jsonPath("title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts"))
        .andExpect(jsonPath("shortTitle").value("Vereinsgesetz"))
        .andExpect(
          jsonPath("expressions[0].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2016-03-16/1/deu"
          )
        )
        .andExpect(jsonPath("expressions[0].isGegenstandslos").value(false))
        .andExpect(jsonPath("expressions[0].isCreated").value(true))
        .andExpect(jsonPath("expressions[0].createdBy").value("diese Verkündung"))
        .andExpect(
          jsonPath("expressions[1].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2024-05-30/1/deu"
          )
        )
        .andExpect(jsonPath("expressions[1].isGegenstandslos").value(false))
        .andExpect(jsonPath("expressions[1].isCreated").value(true))
        .andExpect(jsonPath("expressions[1].createdBy").value("diese Verkündung"));

      // Orphan was deleted
      assertThat(
        normManifestationRepository.findFirstByExpressionEliOrderByManifestationEliDesc(orphanEli)
      ).isEmpty();

      // New expressions created
      futureExpressionElis.forEach(f ->
        assertThat(
          normManifestationRepository.findFirstByExpressionEliOrderByManifestationEliDesc(f)
        ).isPresent()
      );

      final List<String> expressionsEliAfter =
        normManifestationRepository.findExpressionElisByWorkEli("eli/bund/bgbl-1/1964/s593");
      assertThat(expressionsEliAfter).hasSize(3);

      assertTimeLine(expressionsEliAfter);
    }

    /**
     * This method tests the timeline for the given expressions ELIs (that should belong to the same work), meaning checks
     * if the previous/current/next GUIDs are properly set
     * @param expressionElis the list of the expression eli in string format
     */
    private void assertTimeLine(final List<String> expressionElis) {
      if (expressionElis.isEmpty()) {
        throw new IllegalArgumentException("List must not be empty");
      }
      final List<NormExpressionEli> normExpressionElis = expressionElis
        .stream()
        .map(NormExpressionEli::fromString)
        .toList();

      if (normExpressionElis.stream().map(NormExpressionEli::asWorkEli).distinct().count() > 1) {
        throw new IllegalArgumentException("Expressions do not belong to the same work");
      }

      final List<String> sortedElis = normExpressionElis
        .stream()
        .sorted()
        .map(NormExpressionEli::toString)
        .toList();

      final List<FRBRExpression> frbrExpressions = sortedElis
        .stream()
        .map(eli -> {
          final Optional<NormManifestationDto> optionalDto =
            normManifestationRepository.findFirstByExpressionEliOrderByManifestationEliDesc(eli);
          assertThat(optionalDto).isPresent();
          final Norm norm = NormManifestationMapper.mapToDomain(optionalDto.get());
          return norm.getRegelungstext1().getMeta().getFRBRExpression();
        })
        .toList();

      for (int i = 0; i < frbrExpressions.size(); i++) {
        FRBRExpression current = frbrExpressions.get(i);

        if (i == 0 && frbrExpressions.size() > 1) {
          // First entry
          FRBRExpression next = frbrExpressions.get(i + 1);
          assertThat(current.getFRBRaliasNextVersionId()).contains(
            next.getFRBRaliasCurrentVersionId()
          );
        } else if (i == frbrExpressions.size() - 1 && frbrExpressions.size() > 1) {
          // Last entry
          FRBRExpression previous = frbrExpressions.get(i - 1);
          assertThat(current.getFRBRaliasPreviousVersionId()).contains(
            previous.getFRBRaliasCurrentVersionId()
          );
        } else if (i > 0 && i < frbrExpressions.size() - 1) {
          // Middle entries
          FRBRExpression previous = frbrExpressions.get(i - 1);
          FRBRExpression next = frbrExpressions.get(i + 1);
          assertThat(current.getFRBRaliasPreviousVersionId()).contains(
            previous.getFRBRaliasCurrentVersionId()
          );
          assertThat(current.getFRBRaliasNextVersionId()).contains(
            next.getFRBRaliasCurrentVersionId()
          );
        }
      }
    }
  }

  private InputStream loadFolderAsZip(URL resource) throws IOException {
    var folder = new File(resource.getFile());

    var outputStream = new ByteArrayOutputStream();
    var zipOutputStream = new ZipOutputStream(outputStream);

    for (File file : Objects.requireNonNull(folder.listFiles())) {
      var zipEntry = new ZipEntry(file.getName());
      // setting size, compressed size, crc and method manually so they are actually included in the zip file
      // and ZipEntry::getSize works within our methods.
      zipEntry.setSize(file.length());
      zipEntry.setCompressedSize(file.length());
      var crc32 = new CRC32();
      var crc32FileInputStream = new FileInputStream(file);
      crc32.update(crc32FileInputStream.readAllBytes());
      crc32FileInputStream.close();
      zipEntry.setCrc(crc32.getValue());
      zipEntry.setMethod(ZipEntry.STORED);
      zipOutputStream.putNextEntry(zipEntry);

      var fileInputStream = new FileInputStream(file);
      byte[] bytes = new byte[1024];
      int length;
      while ((length = fileInputStream.read(bytes)) >= 0) {
        zipOutputStream.write(bytes, 0, length);
      }
      zipOutputStream.closeEntry();
      fileInputStream.close();
    }

    zipOutputStream.close();

    return new ByteArrayInputStream(outputStream.toByteArray());
  }
}
