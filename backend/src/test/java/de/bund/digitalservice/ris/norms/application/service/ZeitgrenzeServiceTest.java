package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.exception.RegelungstextNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadZeitgrenzenUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateZeitgrenzenUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadRegelungstextPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import java.time.LocalDate;
import java.util.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ZeitgrenzeServiceTest {

  final LoadRegelungstextPort loadRegelungstextPort = mock(LoadRegelungstextPort.class);

  final ZeitgrenzeService service = new ZeitgrenzeService(loadRegelungstextPort);

  @Nested
  class loadZeizgrenzenFromDokument {

    @Test
    void itThrowsRegelungstextNotFoundExceptionWhenRegelungstextNotFound() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2020/s100/2020-06-10/1/deu/regelungstext-2"
      );
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.empty());

      // When / Then
      assertThatThrownBy(() ->
          service.loadZeitgrenzenFromDokument(new LoadZeitgrenzenUseCase.Query(eli))
        )
        .isInstanceOf(RegelungstextNotFoundException.class)
        .hasMessageContaining(eli.toString());
    }

    @Test
    void itCallsLoadZeitgrenzenOfNormAndReturnsEmpty() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );

      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.of(regelungstext));

      // When
      var zeitgrenzen = service.loadZeitgrenzenFromDokument(new LoadZeitgrenzenUseCase.Query(eli));

      // Then
      verify(loadRegelungstextPort, times(1))
        .loadRegelungstext(argThat(argument -> Objects.equals(argument.eli(), eli)));
      assertThat(zeitgrenzen).isEmpty();
    }

    @Test
    void itCallsLoadZeitgrenzenAndReturnsSingleZeitgrenze() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
      );

      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.of(regelungstext));

      // When
      var zeitgrenzen = service.loadZeitgrenzenFromDokument(new LoadZeitgrenzenUseCase.Query(eli));

      // Then
      verify(loadRegelungstextPort, times(1))
        .loadRegelungstext(argThat(argument -> Objects.equals(argument.eli(), eli)));
      assertThat(zeitgrenzen)
        .hasSize(1)
        .extracting(Zeitgrenze::getId, Zeitgrenze::getDate, Zeitgrenze::getArt)
        .containsExactly(tuple("gz-1", LocalDate.parse("2017-03-16"), Zeitgrenze.Art.INKRAFT));
    }

    @Test
    void itCallsLoadZeitgrenzenAndReturnsMultipleZeitgrenzen() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
      );

      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        ZeitgrenzeServiceTest.class,
        "norm-with-several-zeitgrenzen.xml"
      );
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.of(regelungstext));

      // When
      var zeitgrenzen = service.loadZeitgrenzenFromDokument(new LoadZeitgrenzenUseCase.Query(eli));

      // Then
      verify(loadRegelungstextPort, times(1))
        .loadRegelungstext(argThat(argument -> Objects.equals(argument.eli(), eli)));
      assertThat(zeitgrenzen)
        .hasSize(4)
        .extracting(Zeitgrenze::getId, Zeitgrenze::getDate, Zeitgrenze::getArt)
        .containsExactlyInAnyOrder(
          tuple("gz-1", LocalDate.parse("2017-03-16"), Zeitgrenze.Art.INKRAFT),
          tuple("gz-2", LocalDate.parse("2018-04-21"), Zeitgrenze.Art.AUSSERKRAFT),
          tuple("gz-3", LocalDate.parse("2019-12-29"), Zeitgrenze.Art.INKRAFT),
          tuple("gz-4", LocalDate.parse("2026-01-01"), Zeitgrenze.Art.AUSSERKRAFT)
        );
    }
  }

  @Nested
  class updateZeitgrenzenOfDokument {

    @Test
    void itThrowsRegelungstextNotFoundExceptionWhenRegelungstextNotFound() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2020/s100/2020-06-10/1/deu/regelungstext-2"
      );
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.empty());

      // When / Then
      assertThatThrownBy(() ->
          service.updateZeitgrenzenOfDokument(new UpdateZeitgrenzenUseCase.Query(eli, any()))
        )
        .isInstanceOf(RegelungstextNotFoundException.class)
        .hasMessageContaining(eli.toString());
    }

    @Test
    void itCallsLoadZeitgrenzenAndReturnsMultipleZeitgrenzen() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
      );

      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        ZeitgrenzeServiceTest.class,
        "norm-with-several-zeitgrenzen.xml"
      );
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.of(regelungstext));

      final List<Zeitgrenze> newZeitgrenzen = List.of(
        Zeitgrenze
          .builder()
          .art(Zeitgrenze.Art.INKRAFT)
          .date(LocalDate.parse("2025-02-20"))
          .build(),
        Zeitgrenze
          .builder()
          .art(Zeitgrenze.Art.AUSSERKRAFT)
          .date(LocalDate.parse("2023-05-01"))
          .build(),
        Zeitgrenze.builder().art(Zeitgrenze.Art.INKRAFT).date(LocalDate.parse("2024-06-15")).build()
      );
      // When
      var updatedZeitgrenze = service.updateZeitgrenzenOfDokument(
        new UpdateZeitgrenzenUseCase.Query(eli, newZeitgrenzen)
      );

      // Then
      verify(loadRegelungstextPort, times(1))
        .loadRegelungstext(argThat(argument -> Objects.equals(argument.eli(), eli)));
      assertThat(updatedZeitgrenze)
        .hasSize(3)
        .extracting(Zeitgrenze::getId, Zeitgrenze::getDate, Zeitgrenze::getArt)
        .containsExactly(
          tuple("gz-1", LocalDate.parse("2023-05-01"), Zeitgrenze.Art.AUSSERKRAFT),
          tuple("gz-2", LocalDate.parse("2024-06-15"), Zeitgrenze.Art.INKRAFT),
          tuple("gz-3", LocalDate.parse("2025-02-20"), Zeitgrenze.Art.INKRAFT)
        );
    }
  }
}
