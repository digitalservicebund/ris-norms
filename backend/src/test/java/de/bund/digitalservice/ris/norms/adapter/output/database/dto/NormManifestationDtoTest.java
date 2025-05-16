package de.bund.digitalservice.ris.norms.adapter.output.database.dto;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import java.util.List;
import org.junit.jupiter.api.Test;

class NormManifestationDtoTest {

  @Test
  void shouldBeUnpublishedByDefaultWhenCreatedUsingBuilder() {
    // Given
    final NormManifestationDto dokumentDto = NormManifestationDto.builder()
      .dokumente(List.of())
      .build();

    // Then
    assertThat(dokumentDto.getPublishState()).isEqualTo(NormPublishState.UNPUBLISHED);
  }
}
