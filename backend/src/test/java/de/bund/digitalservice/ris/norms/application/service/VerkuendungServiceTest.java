package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.exception.*;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormExpressionsAffectedByVerkuendungUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadVerkuendungUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class VerkuendungServiceTest {

  final LoadAllVerkuendungenPort loadAllVerkuendungenPort = mock(LoadAllVerkuendungenPort.class);
  final LoadVerkuendungByNormEliPort loadVerkuendungByNormEliPort = mock(
    LoadVerkuendungByNormEliPort.class
  );
  final LoadNormPort loadNormPort = mock(LoadNormPort.class);

  final VerkuendungService verkuendungService = new VerkuendungService(
    loadAllVerkuendungenPort,
    loadVerkuendungByNormEliPort,
    loadNormPort
  );

  @Nested
  class loadAllVerkuendungen {

    @Test
    void itReturnsVerkuendungen() {
      // Given
      var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
      var verkuendung = Verkuendung.builder().eli(norm.getExpressionEli()).build();
      when(loadAllVerkuendungenPort.loadAllVerkuendungen()).thenReturn(List.of(verkuendung));

      // When
      var loadedVerkuendungen = verkuendungService.loadAllVerkuendungen();

      // Then
      verify(loadAllVerkuendungenPort, times(1)).loadAllVerkuendungen();
      assertThat(loadedVerkuendungen).hasSize(1).containsExactly(verkuendung);
    }
  }

  @Nested
  class loadVerkuendung {

    @Test
    void itThrowsVerkuendungNotFoundException() {
      // given
      var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
      final var query = new LoadVerkuendungUseCase.Options(
        NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
      );

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));
      when(loadVerkuendungByNormEliPort.loadVerkuendungByNormEli(any())).thenReturn(
        Optional.empty()
      );

      // when
      assertThatThrownBy(() -> verkuendungService.loadVerkuendung(query)).isInstanceOf(
          // then
          VerkuendungNotFoundException.class
        );
    }

    @Test
    void itThrowsVerkuendungNotFoundExceptionIfNormDoesNotExist() {
      // given
      final var query = new LoadVerkuendungUseCase.Options(
        NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
      );

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());

      // when
      assertThatThrownBy(() -> verkuendungService.loadVerkuendung(query)).isInstanceOf(
          // then
          VerkuendungNotFoundException.class
        );
    }

    @Test
    void itReturnsVerkuendung() {
      // Given
      var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
      var verkuendung = Verkuendung.builder().eli(norm.getExpressionEli()).build();

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));
      when(loadVerkuendungByNormEliPort.loadVerkuendungByNormEli(any())).thenReturn(
        Optional.of(verkuendung)
      );

      // When
      var loadedVerkuendung = verkuendungService.loadVerkuendung(
        new LoadVerkuendungUseCase.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      // Then
      verify(loadVerkuendungByNormEliPort, times(1)).loadVerkuendungByNormEli(any());
      assertThat(loadedVerkuendung).hasToString(verkuendung.toString());
    }
  }

  @Nested
  class loadNormExpressionsAffectedByVerkuendung {

    @Test
    void itReturnsListOfNorms() {
      // Given
      var verkuendungsNorm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23"
      );
      var affectedNorm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu/2017-03-15"
      );

      when(loadNormPort.loadNorm(any()))
        .thenReturn(Optional.of(verkuendungsNorm))
        .thenReturn(Optional.of(affectedNorm));

      // When
      var norms = verkuendungService.loadNormExpressionsAffectedByVerkuendung(
        new LoadNormExpressionsAffectedByVerkuendungUseCase.Options(
          verkuendungsNorm.getExpressionEli()
        )
      );

      // Then
      assertThat(norms).hasSize(1).contains(affectedNorm);
      verify(loadNormPort, times(1)).loadNorm(
        new LoadNormPort.Options(verkuendungsNorm.getExpressionEli())
      );
      verify(loadNormPort, times(1)).loadNorm(
        new LoadNormPort.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu")
        )
      );
    }

    @Test
    void itDoesntReturnNonExistingNorms() {
      // Given
      var verkuendungsNorm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23"
      );

      when(loadNormPort.loadNorm(any()))
        .thenReturn(Optional.of(verkuendungsNorm))
        .thenReturn(Optional.empty());

      // When
      var norms = verkuendungService.loadNormExpressionsAffectedByVerkuendung(
        new LoadNormExpressionsAffectedByVerkuendungUseCase.Options(
          verkuendungsNorm.getExpressionEli()
        )
      );

      // Then
      assertThat(norms).isEmpty();
    }
  }
}
