package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ExpressionsStatusResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ExpressionsStatusResponseSchema.Expression;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ExpressionsStatusResponseSchema.Expression.Status;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.List;
import org.junit.jupiter.api.Test;

class ExpressionsStatusResponseMapperTest {

  @Test
  void canMapEmptyList() {
    // Given
    List<Norm> norms = List.of();

    // When
    final ExpressionsStatusResponseSchema result = ExpressionsStatusResponseMapper.fromNorms(norms);

    // Then
    assertThat(result).isNull();
  }

  @Test
  void canMapSingleNorm() {
    // Given
    var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");

    // When
    final ExpressionsStatusResponseSchema result = ExpressionsStatusResponseMapper.fromNorms(
      List.of(norm)
    );

    // Then
    assertThat(result.normWorkEli()).isEqualTo("eli/bund/bgbl-1/1964/s593");
    assertThat(result.title()).isEqualTo("Gesetz zur Regelung des öffentlichen Vereinsrechts");
    assertThat(result.shortTitle()).isEqualTo("Vereinsgesetz");
    assertThat(result.expressions()).hasSize(1);

    Expression expression = result.expressions().getFirst();
    assertThat(expression.normExpressionEli()).isEqualTo(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"
    );
    assertThat(expression.isGegenstandslos()).isEqualTo(norm.isGegenstandlos());
    assertThat(expression.currentStatus()).isEqualTo(Status.PRAETEXT_RELEASED);
  }
}
