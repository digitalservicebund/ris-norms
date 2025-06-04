package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.port.input.LoadNormExpressionsWorkingCopiesUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import java.util.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ZielnormServiceTest {

  final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  final LoadNormExpressionElisPort loadNormExpressionElisPort = mock(
    LoadNormExpressionElisPort.class
  );
  final ZielnormService service = new ZielnormService(loadNormPort, loadNormExpressionElisPort);

  @Nested
  class loadZielnormWorkingCopies {

    @Test
    void itReturnsUnpublishedNormsWhenExpressionsExist() {
      // Given
      var workEli = NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593");
      var expressionEli1 = NormExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"
      );
      var expressionEli2 = NormExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/2022-01-01/1/deu"
      );

      var unpublishedNorm = Norm.builder().publishState(NormPublishState.UNPUBLISHED).build();
      var publishedNorm = Norm.builder().publishState(NormPublishState.PUBLISHED).build();

      when(loadNormExpressionElisPort.loadNormExpressionElis(any())).thenReturn(
        List.of(expressionEli1, expressionEli2)
      );

      when(
        loadNormPort.loadNorm(
          argThat(options -> options != null && options.eli().equals(expressionEli1))
        )
      ).thenReturn(Optional.of(unpublishedNorm));

      when(
        loadNormPort.loadNorm(
          argThat(options -> options != null && options.eli().equals(expressionEli2))
        )
      ).thenReturn(Optional.of(publishedNorm));

      // When
      var result = service.loadZielnormWorkingCopies(
        new LoadNormExpressionsWorkingCopiesUseCase.Options(workEli)
      );

      // Then
      assertThat(result).hasSize(1);
      assertThat(result.getFirst()).isEqualTo(unpublishedNorm);

      verify(loadNormExpressionElisPort, times(1)).loadNormExpressionElis(
        argThat(options -> options.eli().equals(workEli))
      );

      verify(loadNormPort, times(1)).loadNorm(
        argThat(options -> options.eli().equals(expressionEli1))
      );

      verify(loadNormPort, times(1)).loadNorm(
        argThat(options -> options.eli().equals(expressionEli2))
      );
    }

    @Test
    void itReturnsEmptyListWhenNoUnpublishedNormsExist() {
      // Given
      var workEli = NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593");
      var expressionEli1 = NormExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"
      );
      var expressionEli2 = NormExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/2022-01-01/1/deu"
      );

      var publishedNorm1 = Norm.builder().publishState(NormPublishState.PUBLISHED).build();
      var publishedNorm2 = Norm.builder().publishState(NormPublishState.PUBLISHED).build();

      when(loadNormExpressionElisPort.loadNormExpressionElis(any())).thenReturn(
        List.of(expressionEli1, expressionEli2)
      );

      when(
        loadNormPort.loadNorm(
          argThat(options -> options != null && options.eli().equals(expressionEli1))
        )
      ).thenReturn(Optional.of(publishedNorm1));

      when(
        loadNormPort.loadNorm(
          argThat(options -> options != null && options.eli().equals(expressionEli2))
        )
      ).thenReturn(Optional.of(publishedNorm2));

      // When
      var result = service.loadZielnormWorkingCopies(
        new LoadNormExpressionsWorkingCopiesUseCase.Options(workEli)
      );

      // Then
      assertThat(result).isEmpty();

      verify(loadNormExpressionElisPort, times(1)).loadNormExpressionElis(
        argThat(options -> options.eli().equals(workEli))
      );

      verify(loadNormPort, times(1)).loadNorm(
        argThat(options -> options.eli().equals(expressionEli1))
      );

      verify(loadNormPort, times(1)).loadNorm(
        argThat(options -> options.eli().equals(expressionEli2))
      );
    }

    @Test
    void itReturnsEmptyListWhenNoExpressionsExist() {
      // Given
      var workEli = NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593");

      when(loadNormExpressionElisPort.loadNormExpressionElis(any())).thenReturn(List.of());

      // When
      var result = service.loadZielnormWorkingCopies(
        new LoadNormExpressionsWorkingCopiesUseCase.Options(workEli)
      );

      // Then
      assertThat(result).isEmpty();

      verify(loadNormExpressionElisPort, times(1)).loadNormExpressionElis(
        argThat(options -> options.eli().equals(workEli))
      );

      verify(loadNormPort, never()).loadNorm(any());
    }

    @Test
    void itHandlesNormNotFound() {
      // Given
      var workEli = NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593");
      var expressionEli = NormExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"
      );

      when(loadNormExpressionElisPort.loadNormExpressionElis(any())).thenReturn(
        List.of(expressionEli)
      );

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());

      // When
      var result = service.loadZielnormWorkingCopies(
        new LoadNormExpressionsWorkingCopiesUseCase.Options(workEli)
      );

      // Then
      assertThat(result).isEmpty();

      verify(loadNormExpressionElisPort, times(1)).loadNormExpressionElis(
        argThat(options -> options.eli().equals(workEli))
      );

      verify(loadNormPort, times(1)).loadNorm(
        argThat(options -> options.eli().equals(expressionEli))
      );
    }

    @Test
    void itHandlesQueuedForPublishNorms() {
      // Given
      var workEli = NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593");
      var expressionEli1 = NormExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"
      );
      var expressionEli2 = NormExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/2022-01-01/1/deu"
      );
      var expressionEli3 = NormExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/2023-01-01/1/deu"
      );

      var unpublishedNorm = Norm.builder().publishState(NormPublishState.UNPUBLISHED).build();
      var queuedNorm = Norm.builder().publishState(NormPublishState.QUEUED_FOR_PUBLISH).build();
      var publishedNorm = Norm.builder().publishState(NormPublishState.PUBLISHED).build();

      when(loadNormExpressionElisPort.loadNormExpressionElis(any())).thenReturn(
        List.of(expressionEli1, expressionEli2, expressionEli3)
      );

      when(
        loadNormPort.loadNorm(
          argThat(options -> options != null && expressionEli1.equals(options.eli()))
        )
      ).thenReturn(Optional.of(unpublishedNorm));

      when(
        loadNormPort.loadNorm(
          argThat(options -> options != null && expressionEli2.equals(options.eli()))
        )
      ).thenReturn(Optional.of(queuedNorm));

      when(
        loadNormPort.loadNorm(
          argThat(options -> options != null && expressionEli3.equals(options.eli()))
        )
      ).thenReturn(Optional.of(publishedNorm));

      // When
      var result = service.loadZielnormWorkingCopies(
        new LoadNormExpressionsWorkingCopiesUseCase.Options(workEli)
      );

      // Then
      assertThat(result).hasSize(1);
      assertThat(result.getFirst()).isEqualTo(unpublishedNorm);

      verify(loadNormExpressionElisPort, times(1)).loadNormExpressionElis(
        argThat(options -> options.eli().equals(workEli))
      );

      verify(loadNormPort, times(1)).loadNorm(
        argThat(options -> options.eli().equals(expressionEli1))
      );

      verify(loadNormPort, times(1)).loadNorm(
        argThat(options -> options.eli().equals(expressionEli2))
      );

      verify(loadNormPort, times(1)).loadNorm(
        argThat(options -> options.eli().equals(expressionEli3))
      );
    }
  }
}
