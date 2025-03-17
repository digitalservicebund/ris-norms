package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AnnouncementMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AnnouncementRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
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
class AnnouncementControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AnnouncementRepository announcementRepository;

  @Autowired
  private DokumentRepository dokumentRepository;

  @Autowired
  private NormManifestationRepository normManifestationRepository;

  @AfterEach
  void cleanUp() {
    announcementRepository.deleteAll();
    dokumentRepository.deleteAll();
    normManifestationRepository.deleteAll();
  }

  @Nested
  class getAllAnnouncements {

    @Test
    void itReturnsEmptyListOfAnnouncements() throws Exception {
      // given no announcement in the DB

      // when
      mockMvc
        .perform(get("/api/v1/announcements").accept(MediaType.APPLICATION_JSON))
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").doesNotExist());
    }

    @Test
    void itReturnsAllAnnouncementsNorm() throws Exception {
      // Given
      var announcement = Announcement
        .builder()
        .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"))
        .build();
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("Vereinsgesetz.xml"))
      );
      announcementRepository.save(AnnouncementMapper.mapToDto(announcement));

      // When
      mockMvc
        .perform(get("/api/v1/announcements").accept(MediaType.APPLICATION_JSON))
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
            equalTo("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
          )
        );
    }
  }

  @Nested
  class postAnnouncement {

    @Test
    void itCreatesANewAnnouncement() throws Exception {
      // Given
      var xmlContent = Fixtures.loadTextFromDisk("NormWithMods.xml");
      var file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      // When // Then
      mockMvc
        .perform(multipart("/api/v1/announcements").file(file).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(
          jsonPath("eli", equalTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"))
        )
        .andExpect(jsonPath("importedAt").exists());

      // Assert norms was created
      assertThat(
        dokumentRepository.findByEliDokumentManifestation(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
        )
      )
        .isPresent();
    }

    @Test
    void itStoresTheImportTimestamp() throws Exception {
      // Given
      var xmlContent = Fixtures.loadTextFromDisk("NormWithMods.xml");
      var file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      // When / Then
      mockMvc
        .perform(multipart("/api/v1/announcements").file(file).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(
          jsonPath("eli", equalTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"))
        );

      // Then
      var announcement = announcementRepository.findByEliNormExpression(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"
      );
      assertThat(announcement.get().getImportTimestamp())
        .isCloseTo(Instant.now(), new TemporalUnitWithinOffset(5, ChronoUnit.MINUTES));
    }

    @Test
    void itFailsIfTheFileIsNotAnXmlFile() throws Exception {
      // Given
      var file = new MockMultipartFile(
        "file",
        "norm.txt",
        "text/plain",
        new ByteArrayInputStream("not-important".getBytes())
      );

      // When // Then
      mockMvc
        .perform(multipart("/api/v1/announcements").file(file).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/not-a-xml-file")))
        .andExpect(jsonPath("fileName", equalTo("norm.txt")))
        .andExpect(jsonPath("contentType", equalTo("text/plain")));
    }

    @Test
    void itFailsIfTheXmlIsNotLdmlDe() throws Exception {
      //Given
      var xmlContent =
        """
            <root>
              <child>Sample content</child>
            </root>
        """;
      var file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      // When // Then
      mockMvc
        .perform(multipart("/api/v1/announcements").file(file).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/not-a-ldml-de-xml-file")))
        .andExpect(jsonPath("fileName", equalTo("norm.xml")));
    }

    @Test
    void itFailsIfTheNormAlreadyExist() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithMods.xml"))
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithoutPassiveModifications.xml")
        )
      );

      var xmlContent = Fixtures.loadTextFromDisk("NormWithMods.xml");
      var file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      // When // Then
      mockMvc
        .perform(multipart("/api/v1/announcements").file(file).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/norm-with-eli-exists-already")));
    }

    @Test
    void itFailsIfANormWithSameGuidAlreadyExist() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithQuotedStructureMods.xml")
        )
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithoutPassiveModifications.xml")
        )
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithPassiveModsQuotedStructure.xml")
        )
      );

      var regelungstextWithSameGuid = Fixtures.loadRegelungstextFromDisk("NormWithMods.xml");
      regelungstextWithSameGuid
        .getMeta()
        .getFRBRExpression()
        .setFRBRaliasCurrentVersionId(UUID.fromString("c4166ebb-b6df-4f61-8ac1-1d6399cc80ef"));
      var xmlContent = XmlMapper.toString(regelungstextWithSameGuid.getDocument());
      var file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      // When // Then
      mockMvc
        .perform(multipart("/api/v1/announcements").file(file).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/norm-with-guid-exists-already")));
    }

    @Test
    void itFailsIfTheNormIsInvalid() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithoutPassiveModifications.xml")
        )
      );

      var xmlContent = Fixtures.loadTextFromDisk("NormWithModsXsdInvalid.xml");
      var file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      // When // Then
      mockMvc
        .perform(multipart("/api/v1/announcements").file(file).accept(MediaType.APPLICATION_JSON))
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
          Fixtures.loadRegelungstextFromDisk("NormWithoutPassiveModifications.xml")
        )
      );

      var xmlContent = Fixtures.loadTextFromDisk("NormWithModsSchematronInvalid.xml");
      var file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      // When // Then
      mockMvc
        .perform(multipart("/api/v1/announcements").file(file).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/ldml-de-not-schematron-valid")))
        .andExpect(
          jsonPath(
            "errors[0].type",
            equalTo("/errors/ldml-de-not-schematron-valid/failed-assert/SCH-00050-005")
          )
        )
        .andExpect(
          jsonPath(
            "errors[0].xPath",
            equalTo(
              "/Q{http://Inhaltsdaten.LegalDocML.de/1.7.2/}akomaNtoso[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7.2/}act[1]"
            )
          )
        )
        .andExpect(
          jsonPath(
            "errors[0].details",
            equalTo("Für ein Gesetz muss eine Eingangsformel verwendet werden.")
          )
        )
        .andExpect(jsonPath("errors[0].eId", equalTo("")))
        .andExpect(
          jsonPath(
            "errors[1].type",
            equalTo("/errors/ldml-de-not-schematron-valid/failed-assert/SCH-00460-000")
          )
        )
        .andExpect(jsonPath("errors[1].eId", equalTo("meta-1_geltzeiten-1")))
        .andExpect(
          jsonPath(
            "errors[2].type",
            equalTo("/errors/ldml-de-not-schematron-valid/failed-assert/SCH-00460-000")
          )
        )
        .andExpect(jsonPath("errors[2].eId", equalTo("meta-1_geltzeiten-1_geltungszeitgr-1")))
        .andExpect(
          jsonPath(
            "errors[3].type",
            equalTo(
              "/errors/ldml-de-not-schematron-valid/failed-assert/SCH-VERKF-hrefLiterals.expression.FRBRauthor"
            )
          )
        )
        .andExpect(
          jsonPath("errors[3].eId", equalTo("meta-1_ident-1_frbrexpression-1_frbrauthor-1"))
        );
    }

    @Test
    void ifCreatesAnnouncementWithForce() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithMods.xml"))
      );

      var announcement = Announcement
        .builder()
        .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"))
        .build();
      announcementRepository.save(AnnouncementMapper.mapToDto(announcement));

      var xmlContent = Fixtures.loadTextFromDisk("Vereinsgesetz_2017_s419_2017-03-15.xml");
      var file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      // When // Then
      mockMvc
        .perform(
          multipart("/api/v1/announcements?force=true")
            .file(file)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(
          jsonPath("eli", equalTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"))
        );

      assertThat(dokumentRepository.findAll()).hasSize(1);
      var dokumentDto = dokumentRepository.findByEliDokumentManifestation(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );
      assertThat(dokumentDto).isPresent();
      final Diff diff = DiffBuilder
        .compare(Input.from(dokumentDto.get().getXml()))
        .withTest(
          Input.from(
            Fixtures
              .loadRegelungstextFromDisk("Vereinsgesetz_2017_s419_2017-03-15.xml")
              .getDocument()
          )
        )
        .ignoreWhitespace()
        .build();
      assertThat(diff.hasDifferences()).isFalse();
    }
  }
}
