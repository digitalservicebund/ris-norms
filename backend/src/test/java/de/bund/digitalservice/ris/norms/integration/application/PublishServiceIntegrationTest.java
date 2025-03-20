package de.bund.digitalservice.ris.norms.integration.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.application.service.PublishService;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.integration.BaseS3MockIntegrationTest;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PublishServiceIntegrationTest extends BaseS3MockIntegrationTest {

  @Autowired
  private PublishService publishService;

  @Autowired
  private DokumentRepository dokumentRepository;

  @Autowired
  private NormManifestationRepository normManifestationRepository;

  @Test
  void processQueuedFilesForPublish() {
    // Given
    final Norm norm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );
    dokumentRepository.save(DokumentMapper.mapToDto(norm.getRegelungstext1()));

    var normDto = normManifestationRepository
      .findByManifestationEli("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
      .orElseThrow();
    normDto.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);
    normManifestationRepository.save(normDto);

    // When
    publishService.processQueuedFilesForPublish();

    // Then
    var loaded = normManifestationRepository.findByManifestationEli(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
    );
    assertThat(loaded)
      .hasValueSatisfying(loadedNormDto ->
        assertThat(loadedNormDto.getPublishState()).isEqualTo(NormPublishState.PUBLISHED)
      );
    final Path publicFilePath = getPublicPath(norm.getRegelungstext1());
    assertThat(Files.exists(publicFilePath)).isTrue();
    final Path privateFilePath = getPrivatePath(norm.getRegelungstext1());
    assertThat(Files.exists(privateFilePath)).isTrue();
  }
}
