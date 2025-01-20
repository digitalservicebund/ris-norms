package de.bund.digitalservice.ris.norms.integration.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.DokumentDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.RegelungstextMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
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
  private DokumentRepository dokumentRepository;

  @Test
  void processQueuedFilesForPublish() {
    // Given
    final Norm norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
    final DokumentDto dokumentDto = RegelungstextMapper.mapToDto(
      norm.getRegelungstext1(),
      NormPublishState.QUEUED_FOR_PUBLISH
    );
    dokumentRepository.save(dokumentDto);

    // When
    publishService.processQueuedFilesForPublish();

    // Then
    final Optional<DokumentDto> loaded = dokumentRepository.findByEliDokumentManifestation(
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
