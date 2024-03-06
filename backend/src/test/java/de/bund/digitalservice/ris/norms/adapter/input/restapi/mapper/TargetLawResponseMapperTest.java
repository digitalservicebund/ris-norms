package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.TargetLawResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import org.junit.jupiter.api.Test;

class TargetLawResponseMapperTest {

  @Test
  void canMapSimpleResponseSchema() {
    // Given
    final TargetLaw targetLaw1 =
        TargetLaw.builder()
            .eli("target-law-eli-1")
            .title("target law article 1")
            .xml("<target>1</target>")
            .build();

    // When
    final TargetLawResponseSchema resultTargetLaw =
        TargetLawResponseMapper.fromUseCaseData(targetLaw1);

    // Then
    assertThat(resultTargetLaw.getEli()).isEqualTo("target-law-eli-1");
    assertThat(resultTargetLaw.getTitle()).isEqualTo("target law article 1");
  }
}
