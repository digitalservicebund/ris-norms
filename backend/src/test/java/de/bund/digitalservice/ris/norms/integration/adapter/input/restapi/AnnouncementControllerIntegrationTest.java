package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AnnouncementDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.ReleaseDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AnnouncementMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AnnouncementRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.ReleaseRepository;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
  private ReleaseRepository releaseRepository;

  @AfterEach
  void cleanUp() {
    announcementRepository.deleteAll();
    releaseRepository.deleteAll();
    dokumentRepository.deleteAll();
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
      var norm = Fixtures.loadNormFromDisk("Vereinsgesetz.xml");
      var announcement = Announcement.builder().eli(norm.getNormExpressionEli()).build();
      dokumentRepository.saveAll(NormMapper.mapToDtos(norm));
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
          get(
            "/api/v1/announcements/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/releases"
          )
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
              "Announcement for norm with eli eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1 does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/announcements/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/releases"
            )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1")
        );
    }

    @Test
    void itDoesReturnNoReleasesIfNoneFound() throws Exception {
      // Given
      var amendingNorm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
      dokumentRepository.saveAll(NormMapper.mapToDtos(amendingNorm));

      var announcement = Announcement.builder().eli(amendingNorm.getNormExpressionEli()).build();
      announcementRepository.save(AnnouncementMapper.mapToDto(announcement));

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/announcements/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/releases"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("[0]").doesNotExist());
    }

    @Test
    void itReturnsRelease() throws Exception {
      // Given
      var amendingNorm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      var affectedNorm = Fixtures.loadNormFromDisk("NormWithoutPassiveModifications.xml");
      var affectedNormZf0 = Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml");

      var dokumentDtos = dokumentRepository.saveAll(
        Stream
          .of(amendingNorm, affectedNorm, affectedNormZf0)
          .map(NormMapper::mapToDtos)
          .flatMap(Set::stream)
          .collect(Collectors.toSet())
      );

      var releaseDto = releaseRepository.save(
        ReleaseDto
          .builder()
          .releasedAt(Instant.parse("2024-01-02T10:20:30.0Z"))
          .norms(dokumentDtos)
          .build()
      );
      announcementRepository.save(
        AnnouncementDto
          .builder()
          .eli(amendingNorm.getNormExpressionEli().toString())
          .releases(List.of(releaseDto))
          .build()
      );

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/announcements/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/releases"
          )
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
          post(
            "/api/v1/announcements/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/releases"
          )
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
              "Announcement for norm with eli eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1 does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/announcements/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/releases"
            )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1")
        );
    }

    @Test
    void itDoesNotReleaseButReturnsNotFoundIfTargetLawNotFound() throws Exception {
      // Given
      var amendingNorm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      var announcement = Announcement.builder().eli(amendingNorm.getNormExpressionEli()).build();
      dokumentRepository.saveAll(NormMapper.mapToDtos(amendingNorm));
      announcementRepository.save(AnnouncementMapper.mapToDto(announcement));

      // When // Then
      mockMvc
        .perform(
          post(
            "/api/v1/announcements/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/releases"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value("Norm with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu does not exist")
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/announcements/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/releases"
            )
        )
        .andExpect(jsonPath("eli").value("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"));
    }

    @Test
    void itReleaseAnnouncement() throws Exception {
      // Given
      var amendingNorm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      var affectedNorm = Fixtures.loadNormFromDisk("NormWithoutPassiveModifications.xml");
      var affectedNormZf0 = Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml");
      var announcement = Announcement.builder().eli(amendingNorm.getNormExpressionEli()).build();

      dokumentRepository.saveAll(NormMapper.mapToDtos(amendingNorm));
      announcementRepository.save(AnnouncementMapper.mapToDto(announcement));
      dokumentRepository.saveAll(NormMapper.mapToDtos(affectedNorm));
      dokumentRepository.saveAll(NormMapper.mapToDtos(affectedNormZf0));

      // When // Then
      mockMvc
        .perform(
          post(
            "/api/v1/announcements/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/releases"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("releaseAt").exists())
        .andExpect(jsonPath("norms[3]").doesNotExist())
        .andExpect(
          jsonPath(
            "norms",
            containsInAnyOrder(
              "eli/bund/bgbl-1/1964/s593/2017-03-23/1/deu/%s/regelungstext-1.xml".formatted(
                  LocalDate.now().toString()
                ),
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/%s/regelungstext-1.xml".formatted(
                  LocalDate.now().toString()
                ),
              "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/%s/regelungstext-1.xml".formatted(
                  LocalDate.now().toString()
                )
            )
          )
        );

      var publishedManifestationOfAmendingNorm = dokumentRepository.findByEliDokumentManifestation(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/%s/regelungstext-1.xml".formatted(
            LocalDate.now().toString()
          )
      );
      assertThat(publishedManifestationOfAmendingNorm).isPresent();
      assertThat(publishedManifestationOfAmendingNorm.get().getPublishState())
        .isEqualTo(NormPublishState.QUEUED_FOR_PUBLISH);

      var publishedZf0ManifestationOfTargetNorm = dokumentRepository.findByEliDokumentManifestation(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/%s/regelungstext-1.xml".formatted(
            LocalDate.now().toString()
          )
      );
      assertThat(publishedZf0ManifestationOfTargetNorm).isPresent();
      assertThat(publishedZf0ManifestationOfTargetNorm.get().getPublishState())
        .isEqualTo(NormPublishState.QUEUED_FOR_PUBLISH);

      var publishedManifestationOfTargetNormAtFirstTimeBoundary =
        dokumentRepository.findByEliDokumentManifestation(
          "eli/bund/bgbl-1/1964/s593/2017-03-23/1/deu/%s/regelungstext-1.xml".formatted(
              LocalDate.now().toString()
            )
        );
      assertThat(publishedManifestationOfTargetNormAtFirstTimeBoundary).isPresent();
      assertThat(publishedManifestationOfTargetNormAtFirstTimeBoundary.get().getPublishState())
        .isEqualTo(NormPublishState.QUEUED_FOR_PUBLISH);

      var newUnpublishedManifestationOfAmendingNorm =
        dokumentRepository.findByEliDokumentManifestation(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/%s/regelungstext-1.xml".formatted(
              LocalDate.now().plusDays(1).toString()
            )
        );
      assertThat(newUnpublishedManifestationOfAmendingNorm).isPresent();
      assertThat(newUnpublishedManifestationOfAmendingNorm.get().getPublishState())
        .isEqualTo(NormPublishState.UNPUBLISHED);

      var newUnpublishedManifestationOfTargetNorm =
        dokumentRepository.findByEliDokumentManifestation(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/%s/regelungstext-1.xml".formatted(
              LocalDate.now().plusDays(1).toString()
            )
        );
      assertThat(newUnpublishedManifestationOfTargetNorm).isPresent();
      assertThat(newUnpublishedManifestationOfTargetNorm.get().getPublishState())
        .isEqualTo(NormPublishState.UNPUBLISHED);

      // original target norm + 3 queued for publish norms + 2 newly created manifestations for further work. The original amending norm and zf0 norm should no longer exist
      assertThat(dokumentRepository.findAll()).hasSize(6);
    }

    @Test
    void releasingAnAnnouncementASecondTimeCreatesTheSameFilesAndCleansUpOldRelease()
      throws Exception {
      // Given
      var amendingNorm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      var affectedNorm = Fixtures.loadNormFromDisk("NormWithoutPassiveModifications.xml");
      affectedNorm.setPublishState(NormPublishState.PUBLISHED);
      var affectedNormZf0 = Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml");
      var announcement = Announcement.builder().eli(amendingNorm.getNormExpressionEli()).build();

      dokumentRepository.saveAll(NormMapper.mapToDtos(amendingNorm));
      announcementRepository.save(AnnouncementMapper.mapToDto(announcement));
      dokumentRepository.saveAll(NormMapper.mapToDtos(affectedNorm));
      dokumentRepository.saveAll(NormMapper.mapToDtos(affectedNormZf0));

      // When // Then
      mockMvc
        .perform(
          post(
            "/api/v1/announcements/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/releases"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());

      // release norm a second time
      mockMvc
        .perform(
          post(
            "/api/v1/announcements/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/releases"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());

      var publishedManifestationOfAmendingNorm = dokumentRepository.findByEliDokumentManifestation(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/%s/regelungstext-1.xml".formatted(
            LocalDate.now().toString()
          )
      );
      assertThat(publishedManifestationOfAmendingNorm).isPresent();
      assertThat(publishedManifestationOfAmendingNorm.get().getPublishState())
        .isEqualTo(NormPublishState.QUEUED_FOR_PUBLISH);

      var publishedZf0ManifestationOfTargetNorm = dokumentRepository.findByEliDokumentManifestation(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/%s/regelungstext-1.xml".formatted(
            LocalDate.now().toString()
          )
      );
      assertThat(publishedZf0ManifestationOfTargetNorm).isPresent();
      assertThat(publishedZf0ManifestationOfTargetNorm.get().getPublishState())
        .isEqualTo(NormPublishState.QUEUED_FOR_PUBLISH);

      var publishedManifestationOfTargetNormAtFirstTimeBoundary =
        dokumentRepository.findByEliDokumentManifestation(
          "eli/bund/bgbl-1/1964/s593/2017-03-23/1/deu/%s/regelungstext-1.xml".formatted(
              LocalDate.now().toString()
            )
        );
      assertThat(publishedManifestationOfTargetNormAtFirstTimeBoundary).isPresent();
      assertThat(publishedManifestationOfTargetNormAtFirstTimeBoundary.get().getPublishState())
        .isEqualTo(NormPublishState.QUEUED_FOR_PUBLISH);

      var newUnpublishedManifestationOfAmendingNorm =
        dokumentRepository.findByEliDokumentManifestation(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/%s/regelungstext-1.xml".formatted(
              LocalDate.now().plusDays(1).toString()
            )
        );
      assertThat(newUnpublishedManifestationOfAmendingNorm).isPresent();
      assertThat(newUnpublishedManifestationOfAmendingNorm.get().getPublishState())
        .isEqualTo(NormPublishState.UNPUBLISHED);

      var newUnpublishedManifestationOfTargetNorm =
        dokumentRepository.findByEliDokumentManifestation(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/%s/regelungstext-1.xml".formatted(
              LocalDate.now().plusDays(1).toString()
            )
        );
      assertThat(newUnpublishedManifestationOfTargetNorm).isPresent();
      assertThat(newUnpublishedManifestationOfTargetNorm.get().getPublishState())
        .isEqualTo(NormPublishState.UNPUBLISHED);

      // original target norm + 3 queued for publish norms + 2 newly created manifestations for further work. The original amending norm and zf0 norm should no longer exist
      assertThat(dokumentRepository.findAll()).hasSize(6);
    }

    @Test
    void failsWhenTryingToReleaseAnXsdInvalidNorm() throws Exception {
      // Given
      var amendingNorm = Fixtures.loadNormFromDisk("NormWithModsXsdInvalid.xml");
      var affectedNorm = Fixtures.loadNormFromDisk("NormWithoutPassiveModifications.xml");
      var affectedNormZf0 = Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml");
      affectedNormZf0.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);

      var announcement = Announcement.builder().eli(amendingNorm.getNormExpressionEli()).build();

      dokumentRepository.saveAll(NormMapper.mapToDtos(amendingNorm));
      announcementRepository.save(AnnouncementMapper.mapToDto(announcement));
      dokumentRepository.saveAll(NormMapper.mapToDtos(affectedNorm));
      dokumentRepository.saveAll(NormMapper.mapToDtos(affectedNormZf0));

      // When // Then
      // Request is refused
      mockMvc
        .perform(
          post(
            "/api/v1/announcements/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/releases"
          )
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

      var originalNormWithPassiveMods = dokumentRepository.findByEliDokumentManifestation(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23/regelungstext-1.xml"
      );

      assertThat(originalNormWithPassiveMods).isNotEmpty();
      assertThat(originalNormWithPassiveMods.get().getPublishState())
        .isEqualTo(NormPublishState.QUEUED_FOR_PUBLISH);
    }

    @Test
    void failsWhenTryingToReleaseASchematronInvalidNorm() throws Exception {
      // Given
      var amendingNorm = Fixtures.loadNormFromDisk("NormWithModsSchematronInvalid.xml");
      var affectedNorm = Fixtures.loadNormFromDisk("NormWithoutPassiveModifications.xml");
      var affectedNormZf0 = Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml");
      var announcement = Announcement.builder().eli(amendingNorm.getNormExpressionEli()).build();

      dokumentRepository.saveAll(NormMapper.mapToDtos(amendingNorm));
      announcementRepository.save(AnnouncementMapper.mapToDto(announcement));
      dokumentRepository.saveAll(NormMapper.mapToDtos(affectedNorm));
      dokumentRepository.saveAll(NormMapper.mapToDtos(affectedNormZf0));

      // When // Then
      // Request is refused
      mockMvc
        .perform(
          post(
            "/api/v1/announcements/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/releases"
          )
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
      var norm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      var affectedNorm = Fixtures.loadNormFromDisk("NormWithoutPassiveModifications.xml");

      dokumentRepository.saveAll(NormMapper.mapToDtos(affectedNorm));

      var xmlContent = XmlMapper.toString(norm.getDocument());
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
      var norm = Fixtures.loadNormFromDisk("NormWithMods.xml");

      var xmlContent = XmlMapper.toString(norm.getDocument());
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
        .andExpect(
          jsonPath("eli", equalTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"))
        )
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
      var norm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      var affectedNorm = Fixtures.loadNormFromDisk("NormWithoutPassiveModifications.xml");

      dokumentRepository.saveAll(NormMapper.mapToDtos(affectedNorm));
      dokumentRepository.saveAll(NormMapper.mapToDtos(norm));

      var xmlContent = XmlMapper.toString(norm.getDocument());
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
      var normWithSameGuid = Fixtures.loadNormFromDisk("NormWithMods.xml");
      var affectedNormForNormWithSameGuid = Fixtures.loadNormFromDisk(
        "NormWithoutPassiveModifications.xml"
      );
      var norm = Fixtures.loadNormFromDisk("NormWithQuotedStructureMods.xml");
      var affectedNorm = Fixtures.loadNormFromDisk("NormWithPassiveModsQuotedStructure.xml");

      dokumentRepository.saveAll(NormMapper.mapToDtos(affectedNormForNormWithSameGuid));
      dokumentRepository.saveAll(NormMapper.mapToDtos(affectedNorm));
      dokumentRepository.saveAll(NormMapper.mapToDtos(norm));

      normWithSameGuid.getMeta().getFRBRExpression().setFRBRaliasCurrentVersionId(norm.getGuid());
      var xmlContent = XmlMapper.toString(normWithSameGuid.getDocument());
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
      var norm = Fixtures.loadNormFromDisk("NormWithModsXsdInvalid.xml");
      var affectedNorm = Fixtures.loadNormFromDisk("NormWithoutPassiveModifications.xml");

      dokumentRepository.saveAll(NormMapper.mapToDtos(affectedNorm));

      var xmlContent = XmlMapper.toString(norm.getDocument());
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
      var norm = Fixtures.loadNormFromDisk("NormWithModsSchematronInvalid.xml");
      var affectedNorm = Fixtures.loadNormFromDisk("NormWithoutPassiveModifications.xml");

      dokumentRepository.saveAll(NormMapper.mapToDtos(affectedNorm));

      var xmlContent = XmlMapper.toString(norm.getDocument());
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
      var norm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      var announcement = Announcement.builder().eli(norm.getNormExpressionEli()).build();
      var affectedNorm = Fixtures.loadNormFromDisk("NormWithoutPassiveModifications.xml"); // eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      var affectedNormZf0 = Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml"); // the one for the existing amending norm; eli/bund/bgbl-1/1964/s593/2022-08-23/1/deu/regelungstext-1"

      dokumentRepository.saveAll(NormMapper.mapToDtos(norm));
      dokumentRepository.saveAll(NormMapper.mapToDtos(affectedNorm));
      dokumentRepository.saveAll(NormMapper.mapToDtos(affectedNormZf0));
      announcementRepository.save(AnnouncementMapper.mapToDto(announcement));

      var xmlContent = XmlMapper.toString(norm.getDocument());
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
