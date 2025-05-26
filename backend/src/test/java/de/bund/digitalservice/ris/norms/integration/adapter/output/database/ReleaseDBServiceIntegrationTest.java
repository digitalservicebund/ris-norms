package de.bund.digitalservice.ris.norms.integration.adapter.output.database;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.ReleaseMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.ReleaseRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.service.ReleaseDBService;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteQueuedReleasesPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadReleasesByNormExpressionEliPort;
import de.bund.digitalservice.ris.norms.application.port.output.SaveReleasePort;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.Release;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ReleaseDBServiceIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private ReleaseDBService releaseDBService;

  @Autowired
  private DokumentRepository dokumentRepository;

  @Autowired
  private NormManifestationRepository normManifestationRepository;

  @Autowired
  private ReleaseRepository releaseRepository;

  @AfterEach
  void cleanUp() {
    releaseRepository.deleteAll();
    dokumentRepository.deleteAll();
    normManifestationRepository.deleteAll();
  }

  @Nested
  class saveRelease {

    @Test
    void itSavesRelease() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );

      var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
      var release = Release.builder().publishedNorms(List.of(norm)).build();

      // When
      releaseDBService.saveRelease(new SaveReleasePort.Options(release));

      var releases = releaseRepository.findAll();

      assertThat(releases).hasSize(1);
      assertThat(releases.getFirst().getNorms()).hasSize(1);
      assertThat(releases.getFirst().getNorms().getFirst().getManifestationEli()).isEqualTo(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
      );
    }
  }

  @Nested
  class deleteQueuedReleases {

    @Test
    void itDeletesQueuedReleases() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );
      var normDto = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
        .orElseThrow();
      normDto.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);
      normDto = normManifestationRepository.save(normDto);

      var release = Release.builder()
        .publishedNorms(
          List.of(
            Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
          )
        )
        .build();

      var releaseDto = ReleaseMapper.mapToDto(release);
      releaseDto.setNorms(List.of(normDto));
      releaseRepository.save(releaseDto);

      // When
      releaseDBService.deleteQueuedReleases(
        new DeleteQueuedReleasesPort.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      // Then
      var savedReleases = releaseRepository.findAllByNormExpressionEli(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"
      );
      assertThat(savedReleases).isEmpty();

      var savedNorm = dokumentRepository.findByEliDokumentManifestation(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      assertThat(savedNorm).isPresent();
    }
  }

  @Nested
  class loadReleasesByNormExpressionEli {

    @Test
    void itLoadsRelease() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );
      var normDto = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
        .orElseThrow();
      normDto.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);
      normDto = normManifestationRepository.save(normDto);

      var release = Release.builder()
        .publishedNorms(
          List.of(
            Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
          )
        )
        .build();

      var releaseDto = ReleaseMapper.mapToDto(release);
      releaseDto.setNorms(List.of(normDto));
      releaseRepository.save(releaseDto);
      releaseRepository.save(
        ReleaseMapper.mapToDto(Release.builder().releasedAt(Instant.now()).build())
      );

      // When
      var releases = releaseDBService.loadReleasesByNormExpressionEli(
        new LoadReleasesByNormExpressionEliPort.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      assertThat(releases).hasSize(1);
      assertThat(releases.getFirst().getPublishedNorms()).hasSize(1);
      assertThat(
        releases.getFirst().getPublishedNorms().getFirst().getManifestationEli()
      ).isEqualTo(
        NormManifestationEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
      );
    }

    @Test
    void itLoadsReleases() {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );
      var normDto = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
        .orElseThrow();
      normDto.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);
      normDto = normManifestationRepository.save(normDto);
      var release = Release.builder()
        .publishedNorms(
          List.of(
            Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
          )
        )
        .build();
      var releaseDto = ReleaseMapper.mapToDto(release);
      releaseDto.setNorms(List.of(normDto));
      releaseRepository.save(releaseDto);

      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2017-03-15/regelungstext-1.xml"
          )
        )
      );
      var normDto2 = normManifestationRepository
        .findByManifestationEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2017-03-15")
        .orElseThrow();
      var release2 = Release.builder()
        .publishedNorms(
          List.of(
            Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
          )
        )
        .build();
      var releaseDto2 = ReleaseMapper.mapToDto(release2);
      releaseDto2.setNorms(List.of(normDto2));
      releaseRepository.save(releaseDto2);

      releaseRepository.save(
        ReleaseMapper.mapToDto(Release.builder().releasedAt(Instant.now()).build())
      );

      // When
      var releases = releaseDBService.loadReleasesByNormExpressionEli(
        new LoadReleasesByNormExpressionEliPort.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      assertThat(releases).hasSize(2);
      assertThat(releases.getFirst().getPublishedNorms()).hasSize(1);
      assertThat(
        releases.getFirst().getPublishedNorms().getFirst().getManifestationEli()
      ).isEqualTo(
        NormManifestationEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
      );
      assertThat(releases.get(1).getPublishedNorms()).hasSize(1);
      assertThat(releases.get(1).getPublishedNorms().getFirst().getManifestationEli()).isEqualTo(
        NormManifestationEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2017-03-15")
      );
    }
  }
}
