package de.bund.digitalservice.ris.norms.integration.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.application.service.PortalPrototypePublishService;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.integration.BaseS3MockIntegrationTest;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(
  properties = {
    "publish.portal-prototype.enabled=true", "publish.portal-prototype.cron=0 0 0 1 1 *",
  }
)
class PortalPrototypePublishServiceIntegrationTest extends BaseS3MockIntegrationTest {

  @Autowired
  private PortalPrototypePublishService portalPrototypePublishService;

  @Autowired
  private DokumentRepository dokumentRepository;

  @Autowired
  private NormManifestationRepository normManifestationRepository;

  @Test
  void itPublishesToTheCorrectBucket() {
    // Given
    final Norm norm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/2021/s818/2021-04-16/1/deu/2021-04-16"
    );
    dokumentRepository.save(DokumentMapper.mapToDto(norm.getRegelungstext1()));

    var normDto = normManifestationRepository
      .findByManifestationEli("eli/bund/bgbl-1/2021/s818/2021-04-16/1/deu/2021-04-16")
      .orElseThrow();
    normDto.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);
    normManifestationRepository.save(normDto);

    // When
    portalPrototypePublishService.publishNormsToPortalPrototype();

    // Then
    final Path filePath = getPortalPrototypePath(norm.getRegelungstext1());
    assertThat(Files.exists(filePath)).isTrue();
    // TODO: (Malte Laukötter, 2025-03-11) validate that the clean up works
  }

  @Test
  void itDoesntPublishNormsThatAreNotOnTheAllowList() {
    // Given
    final Norm norm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
    );
    dokumentRepository.save(DokumentMapper.mapToDto(norm.getRegelungstext1()));

    var normDto = normManifestationRepository
      .findByManifestationEli(norm.getManifestationEli().toString())
      .orElseThrow();
    normDto.setPublishState(NormPublishState.PUBLISHED);
    normManifestationRepository.save(normDto);

    // When
    portalPrototypePublishService.publishNormsToPortalPrototype();

    // Then
    final Path filePath = getPortalPrototypePath(norm.getRegelungstext1());
    assertThat(Files.exists(filePath)).isFalse();
  }
}
