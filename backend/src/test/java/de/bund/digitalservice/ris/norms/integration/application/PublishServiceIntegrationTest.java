package de.bund.digitalservice.ris.norms.integration.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.NormDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormRepository;
import de.bund.digitalservice.ris.norms.application.service.PublishService;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.integration.BaseS3MockIntegrationTest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PublishServiceIntegrationTest extends BaseS3MockIntegrationTest {

  @Autowired
  private PublishService publishService;

  @Autowired
  private NormRepository normRepository;

  @Test
  void processQueuedFilesForPublish() {
    // Given
    final Norm norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
    final NormDto normDto = NormMapper.mapToDto(norm);
    normDto.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);
    normRepository.save(normDto);

    // When
    publishService.processQueuedFilesForPublish();

    // Then
    final Optional<NormDto> loaded = normRepository.findByEliDokumentManifestation(
      norm.getManifestationEli().toString()
    );
    assertThat(loaded)
      .isPresent()
      .hasValueSatisfying(loadedNormDto ->
        assertThat(loadedNormDto.getPublishState()).isEqualTo(NormPublishState.PUBLISHED)
      );
    final Path publicFilePath = getPublicPath(norm);
    assertThat(Files.exists(publicFilePath)).isTrue();
    final Path privateFilePath = getPublicPath(norm);
    assertThat(Files.exists(privateFilePath)).isTrue();
  }
}
