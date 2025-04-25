package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.exception.InvalidUpdateException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.RegelungstextNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadRegelungstextPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NormServiceTest {

  final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  final UpdateNormPort updateNormPort = mock(UpdateNormPort.class);
  final LoadRegelungstextPort loadRegelungstextPort = mock(LoadRegelungstextPort.class);

  final NormService service = new NormService(loadNormPort, updateNormPort, loadRegelungstextPort);

  @Nested
  class loadNorm {

    @Test
    void itCallsLoadNormAndReturnsNorm() {
      // Given
      var eli = NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu");
      var norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      var returnedNorm = service.loadNorm(new LoadNormUseCase.Query(eli));

      // Then
      verify(loadNormPort, times(1))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
      assertThat(returnedNorm).isEqualTo(norm);
    }

    @Test
    void itThrowsWhenNormIsNotFound() {
      // Given
      var eli = NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu");
      var query = new LoadNormUseCase.Query(eli);
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());

      // When
      assertThatThrownBy(() -> service.loadNorm(query))
        // Then
        .isInstanceOf(NormNotFoundException.class);

      verify(loadNormPort, times(1))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli)));
    }
  }

  @Nested
  class loadRegelungstext {

    @Test
    void itReturnsRegelungstext() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );

      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.of(regelungstext));

      // When
      var returnedRegelungstext = service.loadRegelungstext(
        new LoadRegelungstextUseCase.Query(eli)
      );

      // Then
      verify(loadRegelungstextPort, times(1))
        .loadRegelungstext(argThat(argument -> Objects.equals(argument.eli(), eli)));
      assertThat(returnedRegelungstext).isEqualTo(regelungstext);
    }

    @Test
    void itThrowsWhenNotFound() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      var query = new LoadRegelungstextUseCase.Query(eli);
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.empty());

      // When
      assertThatThrownBy(() -> service.loadRegelungstext(query))
        // Then
        .isInstanceOf(RegelungstextNotFoundException.class);

      verify(loadRegelungstextPort, times(1))
        .loadRegelungstext(argThat(argument -> Objects.equals(argument.eli(), eli)));
    }
  }

  @Nested
  class loadRegelungstextXml {

    @Test
    void itCallsLoadRegelungstextAndReturnsXml() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.of(regelungstext));

      // When
      var xml = service.loadRegelungstextXml(new LoadRegelungstextXmlUseCase.Query(eli));

      // Then
      verify(loadRegelungstextPort, times(1))
        .loadRegelungstext(argThat(argument -> Objects.equals(argument.eli(), eli)));
      assertThat(xml).contains("eId=\"meta-1_ident-1_frbrexpression-1_frbrthis-1\"");
    }

    @Test
    void itCallsLoadRegelungstextAndThrowsNotFound() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.empty());
      var query = new LoadRegelungstextXmlUseCase.Query(eli);

      // When
      assertThatThrownBy(() -> service.loadRegelungstextXml(query))
        // then
        .isInstanceOf(RegelungstextNotFoundException.class);
    }
  }

  @Nested
  class updateRegelungstextXml {

    @Test
    void itUpdatesXml() throws InvalidUpdateException {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );

      var newXml = Fixtures.loadTextFromDisk(
        NormServiceTest.class,
        "vereinsgesetz-with-different-title.xml"
      );

      var oldNorm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      var newNorm = Norm
        .builder()
        .dokumente(Set.of(new Regelungstext(XmlMapper.toDocument(newXml))))
        .build();

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(oldNorm));
      when(updateNormPort.updateNorm(any())).thenReturn(Optional.of(newNorm));

      // When
      var result = service.updateRegelungstextXml(
        new UpdateRegelungstextXmlUseCase.Query(eli, newXml)
      );

      // Then
      verify(loadNormPort, times(1))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli.asNormEli())));
      verify(updateNormPort, times(1))
        .updateNorm(argThat(argument -> argument.norm().equals(newNorm)));
      assertThat(result).contains("Neuer Title");
    }

    @Test
    void itThrowsNormNotFoundIfNormDoesNotExist() throws InvalidUpdateException {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );

      var newXml = Fixtures.loadTextFromDisk(
        NormServiceTest.class,
        "vereinsgesetz-with-different-title.xml"
      );

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());
      var query = new UpdateRegelungstextXmlUseCase.Query(eli, newXml);

      // When
      assertThatThrownBy(() -> service.updateRegelungstextXml(query))
        // then
        .isInstanceOf(NormNotFoundException.class);

      verify(loadNormPort, times(1))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli.asNormEli())));
      verify(updateNormPort, times(0)).updateNorm(any());
    }

    @Test
    void itThrowsIfEliChanges() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );

      var newXml = Fixtures.loadTextFromDisk(
        "eli/bund/bgbl-1/2017/s593/2017-03-15/1/deu/2017-03-15/regelungstext-1.xml"
      );
      var oldNorm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(oldNorm));

      // When
      var thrown = catchThrowable(() ->
        service.updateRegelungstextXml(new UpdateRegelungstextXmlUseCase.Query(eli, newXml))
      );

      // Then
      verify(loadNormPort, times(1))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli.asNormEli())));
      verify(updateNormPort, times(0)).updateNorm(any());
      assertThat(thrown).isInstanceOf(InvalidUpdateException.class);
    }

    @Test
    void itThrowsIfGuidChanges() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );

      var newXml = Fixtures.loadTextFromDisk(
        NormServiceTest.class,
        "vereinsgesetz-with-different-guid.xml"
      );
      var oldNorm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(oldNorm));

      // When
      var thrown = catchThrowable(() ->
        service.updateRegelungstextXml(new UpdateRegelungstextXmlUseCase.Query(eli, newXml))
      );

      // Then
      verify(loadNormPort, times(1))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), eli.asNormEli())));
      verify(updateNormPort, times(0)).updateNorm(any());
      assertThat(thrown).isInstanceOf(InvalidUpdateException.class);
    }
  }

  @Nested
  class updateNorm {

    @Test
    void itSavesANorm() {
      // given
      Norm norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );

      when(updateNormPort.updateNorm(new UpdateNormPort.Command(norm)))
        .thenReturn(Optional.of(norm));

      // when
      service.updateNorm(norm);

      // then
      verify(updateNormPort, times(1))
        .updateNorm(
          argThat(argument -> Objects.equals(argument, new UpdateNormPort.Command(norm)))
        );
    }
  }

  @Test
  void loadZielnormReferences() {
    // given
    Norm norm = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
    );

    when(loadNormPort.loadNorm(new LoadNormPort.Command(any()))).thenReturn(Optional.of(norm));

    // when
    var zielnormReferences = service.loadZielnormReferences(
      new LoadZielnormReferencesUseCase.Query(
        NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu")
      )
    );

    // then
    assertThat(zielnormReferences).hasSize(1);
    assertThat(zielnormReferences.getFirst().getZielnorm())
      .hasToString("eli/bund/bgbl-1/1964/s593");
    assertThat(zielnormReferences.getFirst().getEId())
      .hasToString("hauptteil-1_art-1_abs-1_untergl-1_listenelem-1");
    assertThat(zielnormReferences.getFirst().getGeltungszeit()).isEqualTo("gz-1");
    assertThat(zielnormReferences.getFirst().getTyp()).isEqualTo("Änderungsvorschrift");
  }

  @Nested
  class updateZielnormReferences {

    @Test
    void updateOneAndCreateOne() {
      // given
      Norm norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );

      when(loadNormPort.loadNorm(new LoadNormPort.Command(any()))).thenReturn(Optional.of(norm));

      // when
      var zielnormReferences = service.updateZielnormReferences(
        new UpdateZielnormReferencesUseCase.Query(
          NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"),
          List.of(
            new UpdateZielnormReferencesUseCase.ZielnormReferenceUpdateData(
              "Änderungsvorschrift",
              "gz-2",
              new EId("hauptteil-1_art-1_abs-1_untergl-1_listenelem-1"),
              NormWorkEli.fromString("eli/bund/bgbl-1/2024/12")
            ),
            new UpdateZielnormReferencesUseCase.ZielnormReferenceUpdateData(
              "Änderungsvorschrift",
              "gz-1",
              new EId("hauptteil-1_art-1_abs-1_untergl-1_listenelem-2"),
              NormWorkEli.fromString("eli/bund/bgbl-1/2023/22")
            )
          )
        )
      );

      // then
      assertThat(zielnormReferences).hasSize(2);
      assertThat(zielnormReferences.getFirst().getZielnorm())
        .hasToString("eli/bund/bgbl-1/2024/12");
      assertThat(zielnormReferences.getFirst().getEId())
        .hasToString("hauptteil-1_art-1_abs-1_untergl-1_listenelem-1");
      assertThat(zielnormReferences.getFirst().getGeltungszeit()).isEqualTo("gz-2");
      assertThat(zielnormReferences.getFirst().getTyp()).isEqualTo("Änderungsvorschrift");
      assertThat(zielnormReferences.get(1).getZielnorm()).hasToString("eli/bund/bgbl-1/2023/22");
      assertThat(zielnormReferences.get(1).getEId())
        .hasToString("hauptteil-1_art-1_abs-1_untergl-1_listenelem-2");
      assertThat(zielnormReferences.get(1).getGeltungszeit()).isEqualTo("gz-1");
      assertThat(zielnormReferences.get(1).getTyp()).isEqualTo("Änderungsvorschrift");
    }

    @Test
    void addOne() {
      // given
      Norm norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );

      when(loadNormPort.loadNorm(new LoadNormPort.Command(any()))).thenReturn(Optional.of(norm));

      // when
      var zielnormReferences = service.updateZielnormReferences(
        new UpdateZielnormReferencesUseCase.Query(
          NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"),
          List.of(
            new UpdateZielnormReferencesUseCase.ZielnormReferenceUpdateData(
              "Änderungsvorschrift",
              "gz-1",
              new EId("hauptteil-1_art-1_abs-1_untergl-1_listenelem-2"),
              NormWorkEli.fromString("eli/bund/bgbl-1/2023/22")
            )
          )
        )
      );

      // then
      assertThat(zielnormReferences).hasSize(2);
      assertThat(zielnormReferences.getFirst().getZielnorm())
        .hasToString("eli/bund/bgbl-1/1964/s593");
      assertThat(zielnormReferences.getFirst().getEId())
        .hasToString("hauptteil-1_art-1_abs-1_untergl-1_listenelem-1");
      assertThat(zielnormReferences.getFirst().getGeltungszeit()).isEqualTo("gz-1");
      assertThat(zielnormReferences.getFirst().getTyp()).isEqualTo("Änderungsvorschrift");
      assertThat(zielnormReferences.get(1).getZielnorm()).hasToString("eli/bund/bgbl-1/2023/22");
      assertThat(zielnormReferences.get(1).getEId())
        .hasToString("hauptteil-1_art-1_abs-1_untergl-1_listenelem-2");
      assertThat(zielnormReferences.get(1).getGeltungszeit()).isEqualTo("gz-1");
      assertThat(zielnormReferences.get(1).getTyp()).isEqualTo("Änderungsvorschrift");
    }
  }
}
