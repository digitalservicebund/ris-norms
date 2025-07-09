package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadZeitgrenzenUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateZeitgrenzenUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.time.LocalDate;
import java.util.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ZeitgrenzeServiceTest {

  final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  final NormService normService = mock(NormService.class);

  final ZeitgrenzeService service = new ZeitgrenzeService(loadNormPort, normService);

  @Nested
  class loadZeitgrenzen {

    @Test
    void itThrowsNormNotFoundExceptionWhenNotFound() {
      // Given
      var eli = NormExpressionEli.fromString("eli/bund/bgbl-1/2020/s100/2020-06-10/1/deu");
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());

      // When / Then
      assertThatThrownBy(() -> service.loadZeitgrenzen(new LoadZeitgrenzenUseCase.Options(eli)))
        .isInstanceOf(NormNotFoundException.class)
        .hasMessageContaining(eli.toString());
    }

    @Test
    void itCallsLoadZeitgrenzenOfNormAndReturnsEmpty() {
      // Given
      var eli = NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu");

      var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      var zeitgrenzen = service.loadZeitgrenzen(new LoadZeitgrenzenUseCase.Options(eli));

      // Then
      verify(loadNormPort, times(1)).loadNorm(
        argThat(argument -> Objects.equals(argument.eli(), eli))
      );
      assertThat(zeitgrenzen).isEmpty();
    }

    @Test
    void itCallsLoadZeitgrenzenAndReturnsSingleZeitgrenze() {
      // Given
      var eli = NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu");

      var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23");
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      var zeitgrenzen = service.loadZeitgrenzen(new LoadZeitgrenzenUseCase.Options(eli));

      // Then
      verify(loadNormPort, times(1)).loadNorm(
        argThat(argument -> Objects.equals(argument.eli(), eli))
      );
      assertThat(zeitgrenzen)
        .hasSize(1)
        .extracting(Zeitgrenze::getId, Zeitgrenze::getDate, Zeitgrenze::getArt)
        .containsExactly(
          tuple(
            new Zeitgrenze.Id("5e2f4f78-a0a1-4c55-9ef7-ad2821161915"),
            LocalDate.parse("2017-03-16"),
            Zeitgrenze.Art.INKRAFT
          )
        );
    }

    @Test
    void itCallsLoadZeitgrenzenAndReturnsMultipleZeitgrenzen() {
      // Given
      var eli = NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu");

      var norm = Fixtures.loadNormFromDisk(
        ZeitgrenzeServiceTest.class,
        "norm-with-several-zeitgrenzen"
      );
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      var zeitgrenzen = service.loadZeitgrenzen(new LoadZeitgrenzenUseCase.Options(eli));

      // Then
      verify(loadNormPort, times(1)).loadNorm(
        argThat(argument -> Objects.equals(argument.eli(), eli))
      );
      assertThat(zeitgrenzen)
        .hasSize(4)
        .extracting(Zeitgrenze::getId, Zeitgrenze::getDate, Zeitgrenze::getArt)
        .containsExactlyInAnyOrder(
          tuple(new Zeitgrenze.Id("gz-1"), LocalDate.parse("2017-03-16"), Zeitgrenze.Art.INKRAFT),
          tuple(
            new Zeitgrenze.Id("gz-2"),
            LocalDate.parse("2018-04-21"),
            Zeitgrenze.Art.AUSSERKRAFT
          ),
          tuple(new Zeitgrenze.Id("gz-3"), LocalDate.parse("2019-12-29"), Zeitgrenze.Art.INKRAFT),
          tuple(
            new Zeitgrenze.Id("gz-4"),
            LocalDate.parse("2026-01-01"),
            Zeitgrenze.Art.AUSSERKRAFT
          )
        );
    }
  }

  @Nested
  class updateZeitgrenzenOfDokument {

    @Test
    void itThrowsRegelungstextNotFoundExceptionWhenRegelungstextNotFound() {
      // Given
      var eli = NormExpressionEli.fromString("eli/bund/bgbl-1/2020/s100/2020-06-10/1/deu");
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());

      // When / Then
      assertThatThrownBy(() ->
        service.updateZeitgrenzen(new UpdateZeitgrenzenUseCase.Options(eli, any()))
      )
        .isInstanceOf(NormNotFoundException.class)
        .hasMessageContaining(eli.toString());
      verify(normService, times(0)).updateNorm(any());
    }

    @Test
    void itUpdatesZeitgrenzen() {
      // Given
      var eli = NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu");

      var norm = Fixtures.loadNormFromDisk(
        ZeitgrenzeServiceTest.class,
        "norm-with-several-zeitgrenzen"
      );
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      final List<UpdateZeitgrenzenUseCase.ZeitgrenzenUpdateData> newZeitgrenzen = List.of(
        new UpdateZeitgrenzenUseCase.ZeitgrenzenUpdateData(
          LocalDate.parse("2025-02-20"),
          Zeitgrenze.Art.INKRAFT
        ),
        new UpdateZeitgrenzenUseCase.ZeitgrenzenUpdateData(
          LocalDate.parse("2023-05-01"),
          Zeitgrenze.Art.AUSSERKRAFT
        ),
        new UpdateZeitgrenzenUseCase.ZeitgrenzenUpdateData(
          LocalDate.parse("2017-03-16"),
          Zeitgrenze.Art.INKRAFT
        )
      );
      // When
      var updatedZeitgrenze = service.updateZeitgrenzen(
        new UpdateZeitgrenzenUseCase.Options(eli, newZeitgrenzen)
      );

      // Then
      verify(loadNormPort, times(1)).loadNorm(
        argThat(argument -> Objects.equals(argument.eli(), eli))
      );
      verify(normService, times(1)).updateNorm(any());
      assertThat(updatedZeitgrenze).hasSize(3);
      assertThat(updatedZeitgrenze.getFirst().getDate()).isEqualTo(LocalDate.parse("2017-03-16"));
      assertThat(updatedZeitgrenze.getFirst().getArt()).isEqualTo(Zeitgrenze.Art.INKRAFT);
      assertThat(updatedZeitgrenze.getFirst().getId()).isEqualTo(new Zeitgrenze.Id("gz-1")); // id is unchanged
      assertThat(updatedZeitgrenze.get(1).getDate()).isEqualTo(LocalDate.parse("2023-05-01"));
      assertThat(updatedZeitgrenze.get(1).getArt()).isEqualTo(Zeitgrenze.Art.AUSSERKRAFT);
      assertThat(updatedZeitgrenze.get(2).getDate()).isEqualTo(LocalDate.parse("2025-02-20"));
      assertThat(updatedZeitgrenze.get(2).getArt()).isEqualTo(Zeitgrenze.Art.INKRAFT);
    }

    @Test
    void itThrowsWhenUpdatingZeitgrenzeThatIsInUse() {
      // Given
      var eli = NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu");

      var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23");
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When / Then
      assertThatThrownBy(() ->
        service.updateZeitgrenzen(
          new UpdateZeitgrenzenUseCase.Options(
            eli,
            List.of(
              new UpdateZeitgrenzenUseCase.ZeitgrenzenUpdateData(
                LocalDate.parse("2017-03-16"),
                Zeitgrenze.Art.AUSSERKRAFT
              )
            )
          )
        )
      ).isInstanceOf(UpdateZeitgrenzenUseCase.ZeitgrenzeCanNotBeDeletedAsItIsUsedException.class);
      verify(normService, times(0)).updateNorm(any());
    }
  }
}
