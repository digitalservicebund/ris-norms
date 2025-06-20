package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ReleaseResponseSchema;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Release;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReleaseResponseMapperTest {

  @Test
  void canMapVerkuendungAndEffectedNorms() {
    var release = Release.builder()
      .publishedNorms(
        List.of(
          Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"),
          Fixtures.loadNormFromDisk("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23")
        )
      )
      .build();

    // When
    final ReleaseResponseSchema result = ReleaseResponseMapper.fromRelease(release);

    // Then
    assertThat(result.getNorms())
      .hasSize(2)
      .contains(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
      )
      .contains(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-verkuendung-1.xml"
      );
  }
}
