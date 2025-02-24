package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AnnouncementDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.ReleaseDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AnnouncementMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AnnouncementRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.ReleaseRepository;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser
class AnnouncementControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AnnouncementRepository announcementRepository;

  @Autowired
  private DokumentRepository dokumentRepository;

  @Autowired
  private NormManifestationRepository normManifestationRepository;

  @Autowired
  private ReleaseRepository releaseRepository;

  @AfterEach
  void cleanUp() {
    announcementRepository.deleteAll();
    releaseRepository.deleteAll();
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
  class getReleases {

    @Test
    void itDoesNotReturnReleaseBecauseAmendingLawNotFound() throws Exception {
      // given no announcement is stored in the database

      // when
      mockMvc
        .perform(
          get("/api/v1/announcements/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/releases")
            .accept(MediaType.APPLICATION_JSON)
        )
        // then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/announcement-not-found"))
        .andExpect(jsonPath("title").value("Announcement not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Announcement for norm with eli eli/bund/bgbl-1/2023/413/2023-12-29/1/deu does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value("/api/v1/announcements/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/releases")
        )
        .andExpect(jsonPath("eli").value("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu"));
    }

    @Test
    void itDoesReturnNoReleasesIfNoneFound() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml"))
      );

      var announcement = Announcement
        .builder()
        .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"))
        .build();
      announcementRepository.save(AnnouncementMapper.mapToDto(announcement));

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/announcements/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/releases")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("[0]").doesNotExist());
    }

    @Test
    void itReturnsRelease() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithMods.xml"))
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithoutPassiveModifications.xml")
        )
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithPassiveModifications.xml")
        )
      );

      var amendingNorm = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23")
        .orElseThrow();
      var affectedNorm = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
        .orElseThrow();
      var affectedNormZf0 = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23")
        .orElseThrow();

      var releaseDto = releaseRepository.save(
        ReleaseDto
          .builder()
          .releasedAt(Instant.parse("2024-01-02T10:20:30.0Z"))
          .norms(List.of(amendingNorm, affectedNorm, affectedNormZf0))
          .build()
      );
      announcementRepository.save(
        AnnouncementDto
          .builder()
          .eliNormExpression("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu")
          .releases(List.of(releaseDto))
          .build()
      );

      // When
      mockMvc
        .perform(
          get("/api/v1/announcements/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/releases")
            .accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("[0]").exists())
        .andExpect(jsonPath("[1]").doesNotExist())
        .andExpect(jsonPath("[0].releaseAt", equalTo("2024-01-02T10:20:30Z")))
        .andExpect(jsonPath("[0].norms[3]").doesNotExist())
        .andExpect(
          jsonPath(
            "[0].norms",
            containsInAnyOrder(
              "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml",
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml",
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23/regelungstext-1.xml"
            )
          )
        );
    }
  }

  @Nested
  class postReleases {

    @Test
    void itDoesNotReleaseBecauseAmendingLawNotFound() throws Exception {
      // given no announcement is stored in the database

      // when
      mockMvc
        .perform(
          post("/api/v1/announcements/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/releases")
            .accept(MediaType.APPLICATION_JSON)
        )
        // then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/announcement-not-found"))
        .andExpect(jsonPath("title").value("Announcement not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Announcement for norm with eli eli/bund/bgbl-1/2023/413/2023-12-29/1/deu does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value("/api/v1/announcements/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/releases")
        )
        .andExpect(jsonPath("eli").value("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu"));
    }

    @Test
    void itReleaseAnnouncement() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithMods.xml"))
      );

      var announcement = Announcement
        .builder()
        .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"))
        .build();
      announcementRepository.save(AnnouncementMapper.mapToDto(announcement));

      // When // Then
      mockMvc
        .perform(
          post("/api/v1/announcements/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/releases")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("releaseAt").exists())
        .andExpect(jsonPath("norms[1]").doesNotExist())
        .andExpect(
          jsonPath(
            "norms",
            containsInAnyOrder(
              "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/%s/regelungstext-1.xml".formatted(
                  LocalDate.now().toString()
                )
            )
          )
        );

      var publishedManifestationOfAmendingNorm = normManifestationRepository.findByManifestationEli(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/%s".formatted(LocalDate.now().toString())
      );
      assertThat(publishedManifestationOfAmendingNorm).isPresent();
      assertThat(publishedManifestationOfAmendingNorm.get().getPublishState())
        .isEqualTo(NormPublishState.QUEUED_FOR_PUBLISH);

      var newUnpublishedManifestationOfAmendingNorm =
        normManifestationRepository.findByManifestationEli(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/%s".formatted(
              LocalDate.now().plusDays(1).toString()
            )
        );
      assertThat(newUnpublishedManifestationOfAmendingNorm).isPresent();
      assertThat(newUnpublishedManifestationOfAmendingNorm.get().getPublishState())
        .isEqualTo(NormPublishState.UNPUBLISHED);

      // 1 queued for publish norms + 1 newly created manifestations for further work. The original amending norm should no longer exist
      assertThat(dokumentRepository.findAll()).hasSize(2);
      assertThat(normManifestationRepository.findAll()).hasSize(2);
    }

    @Test
    void releasingAnAnnouncementASecondTimeCreatesTheSameFilesAndCleansUpOldRelease()
      throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithMods.xml"))
      );

      var announcement = Announcement
        .builder()
        .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"))
        .build();
      announcementRepository.save(AnnouncementMapper.mapToDto(announcement));

      // When // Then
      mockMvc
        .perform(
          post("/api/v1/announcements/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/releases")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());

      // release norm a second time
      mockMvc
        .perform(
          post("/api/v1/announcements/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/releases")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());

      var publishedManifestationOfAmendingNorm = normManifestationRepository.findByManifestationEli(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/%s".formatted(LocalDate.now().toString())
      );
      assertThat(publishedManifestationOfAmendingNorm).isPresent();
      assertThat(publishedManifestationOfAmendingNorm.get().getPublishState())
        .isEqualTo(NormPublishState.QUEUED_FOR_PUBLISH);

      var newUnpublishedManifestationOfAmendingNorm =
        normManifestationRepository.findByManifestationEli(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/%s".formatted(
              LocalDate.now().plusDays(1).toString()
            )
        );
      assertThat(newUnpublishedManifestationOfAmendingNorm).isPresent();
      assertThat(newUnpublishedManifestationOfAmendingNorm.get().getPublishState())
        .isEqualTo(NormPublishState.UNPUBLISHED);

      // 1 queued for publish norms + 1 newly created manifestations for further work. The original amending norm should no longer exist
      assertThat(dokumentRepository.findAll()).hasSize(2);
    }

    @Test
    void failsWhenTryingToReleaseAnXsdInvalidNorm() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("NormWithModsXsdInvalid.xml"))
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithoutPassiveModifications.xml")
        )
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithPassiveModifications.xml")
        )
      );

      var affectedNormZf0 = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23")
        .orElseThrow();
      affectedNormZf0.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);
      normManifestationRepository.save(affectedNormZf0);

      var announcement = Announcement
        .builder()
        .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"))
        .build();
      announcementRepository.save(AnnouncementMapper.mapToDto(announcement));

      // When // Then
      // Request is refused
      mockMvc
        .perform(
          post("/api/v1/announcements/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/releases")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/ldml-de-not-valid")));

      // Content of the DB should be unchanged = 3 sample norms, all unpublished
      assertThat(dokumentRepository.findAll()).hasSize(3);
      assertThat(
        dokumentRepository.findByEliDokumentManifestation(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
        )
      )
        .isNotEmpty();
      assertThat(
        dokumentRepository.findByEliDokumentManifestation(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
        )
      )
        .isNotEmpty();

      var originalNormWithPassiveMods = normManifestationRepository.findByManifestationEli(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23"
      );

      assertThat(originalNormWithPassiveMods).isNotEmpty();
      assertThat(originalNormWithPassiveMods.get().getPublishState())
        .isEqualTo(NormPublishState.QUEUED_FOR_PUBLISH);
    }

    @Test
    void failsWhenTryingToReleaseASchematronInvalidNorm() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithModsSchematronInvalid.xml")
        )
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithoutPassiveModifications.xml")
        )
      );
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithPassiveModifications.xml")
        )
      );

      var announcement = Announcement
        .builder()
        .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"))
        .build();

      announcementRepository.save(AnnouncementMapper.mapToDto(announcement));

      // When // Then
      // Request is refused
      mockMvc
        .perform(
          post("/api/v1/announcements/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/releases")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/ldml-de-not-schematron-valid")));

      // Content of the DB should be unchanged = 3 sample norms, all unpublished
      assertThat(dokumentRepository.findAll()).hasSize(3);
      assertThat(
        dokumentRepository.findByEliDokumentManifestation(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
        )
      )
        .isNotEmpty();
      assertThat(
        dokumentRepository.findByEliDokumentManifestation(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
        )
      )
        .isNotEmpty();
      assertThat(
        dokumentRepository.findByEliDokumentManifestation(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23/regelungstext-1.xml"
        )
      )
        .isNotEmpty();
    }
  }

  @Nested
  class postAnnouncement {

    @Test
    void itCreatesANewAnnouncement() throws Exception {
      // Given
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
        .andExpect(status().isOk())
        .andExpect(
          jsonPath("eli", equalTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"))
        );

      // Assert ZF0 was created
      assertThat(
        dokumentRepository.findByEliDokumentManifestation(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/%s/regelungstext-1.xml".formatted(
              LocalDate.now().toString()
            )
        )
      )
        .isPresent();
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
    void itFailsIfTheAffectedNormDoesNotExist() throws Exception {
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
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/active-mod/destination/norm-not-found")))
        .andExpect(jsonPath("eli", equalTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu")))
        .andExpect(
          jsonPath(
            "destinationEli",
            equalTo("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
          )
        );
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
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithoutPassiveModifications.xml")
        )
      ); // eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithPassiveModifications.xml")
        )
      ); // the one for the existing amending norm; eli/bund/bgbl-1/1964/s593/2022-08-23/1/deu/regelungstext-1"

      var announcement = Announcement
        .builder()
        .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"))
        .build();
      announcementRepository.save(AnnouncementMapper.mapToDto(announcement));

      var xmlContent = Fixtures.loadTextFromDisk("NormWithMods.xml");
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

      // Assert ZF0 was created
      assertThat(
        dokumentRepository.findByEliDokumentManifestation(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/%s/regelungstext-1.xml".formatted(
              LocalDate.now().toString()
            )
        )
      )
        .isPresent();

      // Assert old ZF0 was deleted
      assertThat(
        dokumentRepository.findByEliDokumentManifestation(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23/regelungstext-1.xml"
        )
      )
        .isEmpty();
    }
  }
}
