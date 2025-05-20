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
import de.bund.digitalservice.ris.norms.application.port.output.UpdateDokumentPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import java.time.LocalDate;
import java.util.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ZeitgrenzeServiceTest {

  final LoadRegelungstextPort loadRegelungstextPort = mock(LoadRegelungstextPort.class);
  final UpdateDokumentPort updateDokumentPort = mock(UpdateDokumentPort.class);

  final ZeitgrenzeService service = new ZeitgrenzeService(
    loadRegelungstextPort,
    updateDokumentPort
  );

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
        service.loadZeitgrenzenFromDokument(new LoadZeitgrenzenUseCase.Options(eli))
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
      var zeitgrenzen = service.loadZeitgrenzenFromDokument(
        new LoadZeitgrenzenUseCase.Options(eli)
      );

      // Then
      verify(loadRegelungstextPort, times(1)).loadRegelungstext(
        argThat(argument -> Objects.equals(argument.eli(), eli))
      );
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
      var zeitgrenzen = service.loadZeitgrenzenFromDokument(
        new LoadZeitgrenzenUseCase.Options(eli)
      );

      // Then
      verify(loadRegelungstextPort, times(1)).loadRegelungstext(
        argThat(argument -> Objects.equals(argument.eli(), eli))
      );
      assertThat(zeitgrenzen)
        .hasSize(1)
        .extracting(Zeitgrenze::getId, Zeitgrenze::getDate, Zeitgrenze::getArt)
        .containsExactly(
          tuple(new Zeitgrenze.Id("gz-1"), LocalDate.parse("2017-03-16"), Zeitgrenze.Art.INKRAFT)
        );
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
      var zeitgrenzen = service.loadZeitgrenzenFromDokument(
        new LoadZeitgrenzenUseCase.Options(eli)
      );

      // Then
      verify(loadRegelungstextPort, times(1)).loadRegelungstext(
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
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2020/s100/2020-06-10/1/deu/regelungstext-2"
      );
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.empty());

      // When / Then
      assertThatThrownBy(() ->
        service.updateZeitgrenzenOfDokument(new UpdateZeitgrenzenUseCase.Options(eli, any()))
      )
        .isInstanceOf(RegelungstextNotFoundException.class)
        .hasMessageContaining(eli.toString());
      verify(updateDokumentPort, times(0)).updateDokument(any());
    }

    @Test
    void itUpdatesZeitgrenzen() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
      );

      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        ZeitgrenzeServiceTest.class,
        "norm-with-several-zeitgrenzen.xml"
      );
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.of(regelungstext));

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
      var updatedZeitgrenze = service.updateZeitgrenzenOfDokument(
        new UpdateZeitgrenzenUseCase.Options(eli, newZeitgrenzen)
      );

      // Then
      verify(loadRegelungstextPort, times(1)).loadRegelungstext(
        argThat(argument -> Objects.equals(argument.eli(), eli))
      );
      verify(updateDokumentPort, times(1)).updateDokument(any());
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
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
      );

      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.of(regelungstext));

      // When / Then
      assertThatThrownBy(() ->
        service.updateZeitgrenzenOfDokument(
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
      verify(updateDokumentPort, times(0)).updateDokument(any());
    }
  }
}
