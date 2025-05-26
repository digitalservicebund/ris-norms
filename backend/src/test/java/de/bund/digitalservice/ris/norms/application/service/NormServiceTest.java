package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.exception.InvalidUpdateException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.RegelungstextNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NormServiceTest {

  final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  final LoadNormByGuidPort loadNormByGuidPort = mock(LoadNormByGuidPort.class);
  final UpdateNormPort updateNormPort = mock(UpdateNormPort.class);
  final LoadRegelungstextPort loadRegelungstextPort = mock(LoadRegelungstextPort.class);
  final LoadNormExpressionElisPort loadNormExpressionElisPort = mock(
    LoadNormExpressionElisPort.class
  );
  final EliService eliService = mock(EliService.class);
  final CreateNewVersionOfNormService createNewVersionOfNormService = mock(
    CreateNewVersionOfNormService.class
  );
  final UpdateOrSaveNormPort updateOrSaveNormPort = mock(UpdateOrSaveNormPort.class);
  final DeleteNormPort deleteNormPort = mock(DeleteNormPort.class);

  final NormService service = new NormService(
    loadNormPort,
    loadNormByGuidPort,
    updateNormPort,
    loadRegelungstextPort,
    loadNormExpressionElisPort,
    eliService,
    createNewVersionOfNormService,
    updateOrSaveNormPort,
    deleteNormPort
  );

  @Nested
  class loadNorm {

    @Test
    void itCallsLoadNormAndReturnsNorm() {
      // Given
      var eli = NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu");
      var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));

      // When
      var returnedNorm = service.loadNorm(new LoadNormUseCase.EliOptions(eli));

      // Then
      verify(loadNormPort, times(1)).loadNorm(
        argThat(argument -> Objects.equals(argument.eli(), eli))
      );
      assertThat(returnedNorm).isEqualTo(norm);
    }

    @Test
    void itCallsLoadNormByGuidAndReturnsNorm() {
      // Given
      var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
      when(loadNormByGuidPort.loadNormByGuid(any())).thenReturn(Optional.of(norm));

      // When
      var returnedNorm = service.loadNorm(
        new LoadNormUseCase.GuidOptions(UUID.fromString("d04791fc-dcdc-47e6-aefb-bc2f7aaee151"))
      );

      // Then
      verify(loadNormByGuidPort, times(1)).loadNormByGuid(
        argThat(argument ->
          Objects.equals(argument.guid(), UUID.fromString("d04791fc-dcdc-47e6-aefb-bc2f7aaee151"))
        )
      );
      assertThat(returnedNorm).isEqualTo(norm);
    }

    @Test
    void itThrowsWhenNormIsNotFound() {
      // Given
      var eli = NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu");
      var query = new LoadNormUseCase.EliOptions(eli);
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());

      // When
      assertThatThrownBy(() -> service.loadNorm(query)).isInstanceOf(NormNotFoundException.class); // Then

      verify(loadNormPort, times(1)).loadNorm(
        argThat(argument -> Objects.equals(argument.eli(), eli))
      );
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
        new LoadRegelungstextUseCase.Options(eli)
      );

      // Then
      verify(loadRegelungstextPort, times(1)).loadRegelungstext(
        argThat(argument -> Objects.equals(argument.eli(), eli))
      );
      assertThat(returnedRegelungstext).isEqualTo(regelungstext);
    }

    @Test
    void itThrowsWhenNotFound() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      var query = new LoadRegelungstextUseCase.Options(eli);
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.empty());

      // When
      assertThatThrownBy(() -> service.loadRegelungstext(query)).isInstanceOf(
        // Then
        RegelungstextNotFoundException.class
      );

      verify(loadRegelungstextPort, times(1)).loadRegelungstext(
        argThat(argument -> Objects.equals(argument.eli(), eli))
      );
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
      var xml = service.loadRegelungstextXml(new LoadRegelungstextXmlUseCase.Options(eli));

      // Then
      verify(loadRegelungstextPort, times(1)).loadRegelungstext(
        argThat(argument -> Objects.equals(argument.eli(), eli))
      );
      assertThat(xml).contains("eId=\"meta-1_ident-1_frbrexpression-1_frbrthis-1\"");
    }

    @Test
    void itCallsLoadRegelungstextAndThrowsNotFound() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
      );
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.empty());
      var query = new LoadRegelungstextXmlUseCase.Options(eli);

      // When
      assertThatThrownBy(() -> service.loadRegelungstextXml(query)).isInstanceOf(
        // then
        RegelungstextNotFoundException.class
      );
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
        "vereinsgesetz-with-different-title/regelungstext-1.xml"
      );

      var oldNorm = Norm.builder()
        .dokumente(
          Set.of(
            Fixtures.loadRegelungstextFromDisk(
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
            )
          )
        )
        .build();
      var newNorm = Norm.builder()
        .dokumente(Set.of(new Regelungstext(XmlMapper.toDocument(newXml))))
        .build();

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(oldNorm));
      when(updateNormPort.updateNorm(any())).thenReturn(Optional.of(newNorm));

      // When
      var result = service.updateRegelungstextXml(
        new UpdateRegelungstextXmlUseCase.Options(eli, newXml)
      );

      // Then
      verify(loadNormPort, times(1)).loadNorm(
        argThat(argument -> Objects.equals(argument.eli(), eli.asNormEli()))
      );
      verify(updateNormPort, times(1)).updateNorm(
        argThat(argument -> argument.norm().equals(newNorm))
      );
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
        "vereinsgesetz-with-different-title/regelungstext-1.xml"
      );

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());
      var query = new UpdateRegelungstextXmlUseCase.Options(eli, newXml);

      // When
      assertThatThrownBy(() -> service.updateRegelungstextXml(query)).isInstanceOf(
        // then
        NormNotFoundException.class
      );

      verify(loadNormPort, times(1)).loadNorm(
        argThat(argument -> Objects.equals(argument.eli(), eli.asNormEli()))
      );
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
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
      );

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(oldNorm));

      // When
      var thrown = catchThrowable(() ->
        service.updateRegelungstextXml(new UpdateRegelungstextXmlUseCase.Options(eli, newXml))
      );

      // Then
      verify(loadNormPort, times(1)).loadNorm(
        argThat(argument -> Objects.equals(argument.eli(), eli.asNormEli()))
      );
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
        "vereinsgesetz-with-different-guid/regelungstext-1.xml"
      );
      var oldNorm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
      );

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(oldNorm));

      // When
      var thrown = catchThrowable(() ->
        service.updateRegelungstextXml(new UpdateRegelungstextXmlUseCase.Options(eli, newXml))
      );

      // Then
      verify(loadNormPort, times(1)).loadNorm(
        argThat(argument -> Objects.equals(argument.eli(), eli.asNormEli()))
      );
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
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23"
      );

      when(updateNormPort.updateNorm(new UpdateNormPort.Options(norm))).thenReturn(
        Optional.of(norm)
      );

      // when
      service.updateNorm(norm);

      // then
      verify(updateNormPort, times(1)).updateNorm(
        argThat(argument -> Objects.equals(argument, new UpdateNormPort.Options(norm)))
      );
    }
  }

  @Test
  void loadZielnormReferences() {
    // given
    Norm norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23");

    when(loadNormPort.loadNorm(new LoadNormPort.Options(any()))).thenReturn(Optional.of(norm));

    // when
    var zielnormReferences = service.loadZielnormReferences(
      new LoadZielnormReferencesUseCase.Options(
        NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu")
      )
    );

    // then
    assertThat(zielnormReferences).hasSize(1);
    assertThat(zielnormReferences.getFirst().getZielnorm()).hasToString(
      "eli/bund/bgbl-1/1964/s593"
    );
    assertThat(zielnormReferences.getFirst().getEId()).hasToString(
      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1"
    );
    assertThat(zielnormReferences.getFirst().getGeltungszeit()).hasToString("gz-1");
    assertThat(zielnormReferences.getFirst().getTyp()).isEqualTo("Änderungsvorschrift");
  }

  @Nested
  class updateZielnormReferences {

    @Test
    void updateOneAndCreateOne() {
      // given
      Norm norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23"
      );

      when(loadNormPort.loadNorm(new LoadNormPort.Options(any()))).thenReturn(Optional.of(norm));
      when(updateNormPort.updateNorm(new UpdateNormPort.Options(any()))).thenReturn(
        Optional.of(norm)
      );

      // when
      var zielnormReferences = service.updateZielnormReferences(
        new UpdateZielnormReferencesUseCase.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"),
          List.of(
            new UpdateZielnormReferencesUseCase.ZielnormReferenceUpdateData(
              "Änderungsvorschrift",
              new Zeitgrenze.Id("gz-2"),
              new EId("hauptteil-1_art-1_abs-1_untergl-1_listenelem-1"),
              NormWorkEli.fromString("eli/bund/bgbl-1/2024/12")
            ),
            new UpdateZielnormReferencesUseCase.ZielnormReferenceUpdateData(
              "Änderungsvorschrift",
              new Zeitgrenze.Id("gz-1"),
              new EId("hauptteil-1_art-1_abs-1_untergl-1_listenelem-2"),
              NormWorkEli.fromString("eli/bund/bgbl-1/2023/22")
            )
          )
        )
      );

      // then
      assertThat(zielnormReferences).hasSize(2);
      assertThat(zielnormReferences.getFirst().getZielnorm()).hasToString(
        "eli/bund/bgbl-1/2024/12"
      );
      assertThat(zielnormReferences.getFirst().getEId()).hasToString(
        "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1"
      );
      assertThat(zielnormReferences.getFirst().getGeltungszeit()).hasToString("gz-2");
      assertThat(zielnormReferences.getFirst().getTyp()).isEqualTo("Änderungsvorschrift");
      assertThat(zielnormReferences.get(1).getZielnorm()).hasToString("eli/bund/bgbl-1/2023/22");
      assertThat(zielnormReferences.get(1).getEId()).hasToString(
        "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2"
      );
      assertThat(zielnormReferences.get(1).getGeltungszeit()).hasToString("gz-1");
      assertThat(zielnormReferences.get(1).getTyp()).isEqualTo("Änderungsvorschrift");

      verify(updateNormPort, times(1)).updateNorm(any());
    }

    @Test
    void addOne() {
      // given
      Norm norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23"
      );

      when(loadNormPort.loadNorm(new LoadNormPort.Options(any()))).thenReturn(Optional.of(norm));
      when(updateNormPort.updateNorm(new UpdateNormPort.Options(any()))).thenReturn(
        Optional.of(norm)
      );

      // when
      var zielnormReferences = service.updateZielnormReferences(
        new UpdateZielnormReferencesUseCase.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"),
          List.of(
            new UpdateZielnormReferencesUseCase.ZielnormReferenceUpdateData(
              "Änderungsvorschrift",
              new Zeitgrenze.Id("gz-1"),
              new EId("hauptteil-1_art-1_abs-1_untergl-1_listenelem-2"),
              NormWorkEli.fromString("eli/bund/bgbl-1/2023/22")
            )
          )
        )
      );

      // then
      assertThat(zielnormReferences).hasSize(2);
      assertThat(zielnormReferences.getFirst().getZielnorm()).hasToString(
        "eli/bund/bgbl-1/1964/s593"
      );
      assertThat(zielnormReferences.getFirst().getEId()).hasToString(
        "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1"
      );
      assertThat(zielnormReferences.getFirst().getGeltungszeit()).hasToString("gz-1");
      assertThat(zielnormReferences.getFirst().getTyp()).isEqualTo("Änderungsvorschrift");
      assertThat(zielnormReferences.get(1).getZielnorm()).hasToString("eli/bund/bgbl-1/2023/22");
      assertThat(zielnormReferences.get(1).getEId()).hasToString(
        "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2"
      );
      assertThat(zielnormReferences.get(1).getGeltungszeit()).hasToString("gz-1");
      assertThat(zielnormReferences.get(1).getTyp()).isEqualTo("Änderungsvorschrift");

      verify(updateNormPort, times(1)).updateNorm(any());
    }
  }

  @Test
  void deleteZielnormReferences() {
    // given
    Norm norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23");

    when(loadNormPort.loadNorm(new LoadNormPort.Options(any()))).thenReturn(Optional.of(norm));
    when(updateNormPort.updateNorm(new UpdateNormPort.Options(any()))).thenReturn(
      Optional.of(norm)
    );

    // when
    var zielnormReferences = service.deleteZielnormReferences(
      new DeleteZielnormReferencesUseCase.Options(
        NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"),
        List.of(new EId("hauptteil-1_art-1_abs-1_untergl-1_listenelem-1"))
      )
    );

    // then
    assertThat(zielnormReferences).isEmpty();
    verify(updateNormPort, times(1)).updateNorm(any());
  }

  @Nested
  class loadZielnormen {

    @Test
    void itShouldGenerateCorrectElisForNoExistingExpressions() {
      Norm norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23"
      );
      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Options(
            NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu")
          )
        )
      ).thenReturn(Optional.of(norm));
      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Options(NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"))
        )
      ).thenReturn(
        Optional.of(
          Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
        )
      );
      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Options(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
          )
        )
      ).thenReturn(
        Optional.of(
          Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05")
        )
      );
      when(loadNormExpressionElisPort.loadNormExpressionElis(any())).thenReturn(List.of());
      when(eliService.findNextExpressionEli(any(), any(), any())).thenReturn(
        NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu")
      );

      var preview = service.loadZielnormExpressions(
        new LoadZielnormenExpressionsUseCase.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu")
        )
      );

      assertThat(preview).hasSize(1);
      assertThat(preview.getFirst().normWorkEli()).hasToString("eli/bund/bgbl-1/1964/s593");
      assertThat(preview.getFirst().title()).hasToString(
        "Gesetz zur Regelung des öffentlichen Vereinsrechts"
      );
      assertThat(preview.getFirst().shortTitle()).hasToString("Vereinsgesetz");
      assertThat(preview.getFirst().expressions())
        .hasSize(1)
        .containsExactly(
          new Zielnorm.Expression(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu"),
            false,
            false,
            Zielnorm.CreatedBy.THIS_VERKUENDUNG
          )
        );

      verify(eliService, times(1)).findNextExpressionEli(
        NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"),
        LocalDate.parse("2017-03-16"),
        "deu"
      );
      verify(loadNormPort, times(1)).loadNorm(
        new LoadNormPort.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu")
        )
      );
      verify(loadNormPort, times(1)).loadNorm(
        new LoadNormPort.Options(NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"))
      );
      verify(loadNormExpressionElisPort, times(1)).loadNormExpressionElis(
        new LoadNormExpressionElisPort.Options(NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"))
      );
    }

    @Test
    void itShouldGenerateCorrectElisForExistingExpressions() {
      Norm norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23"
      );
      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Options(
            NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu")
          )
        )
      ).thenReturn(Optional.of(norm));
      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Options(NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"))
        )
      ).thenReturn(
        Optional.of(Fixtures.loadNormFromDisk(NormServiceTest.class, "vereinsgesetz-2017-04-16-2"))
      );
      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Options(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-04-16/2/deu")
          )
        )
      ).thenReturn(
        Optional.of(Fixtures.loadNormFromDisk(NormServiceTest.class, "vereinsgesetz-2017-04-16-2"))
      );
      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Options(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu")
          )
        )
      ).thenReturn(
        Optional.of(Fixtures.loadNormFromDisk(NormServiceTest.class, "vereinsgesetz-2017-03-16-1"))
      );
      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Options(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-03-21/1/deu")
          )
        )
      ).thenReturn(
        Optional.of(
          Fixtures.loadNormFromDisk(
            NormServiceTest.class,
            "vereinsgesetz-2017-03-21-1-gegenstandlos"
          )
        )
      );
      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Options(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-04-16/1/deu")
          )
        )
      ).thenReturn(
        Optional.of(
          Fixtures.loadNormFromDisk(
            NormServiceTest.class,
            "vereinsgesetz-2017-04-16-1-gegenstandlos"
          )
        )
      );
      when(loadNormExpressionElisPort.loadNormExpressionElis(any())).thenReturn(
        List.of(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu"), // a new expression for this date should be created
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-03-21/1/deu"), // should be ignored as it is gegenstandlos
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-04-16/1/deu"), // should be ignored as it is gegenstandlos
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-04-16/2/deu") // a new expression for this date should be created
        )
      );
      when(eliService.findNextExpressionEli(any(), any(), any()))
        .thenReturn(NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-03-16/2/deu"))
        .thenReturn(NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-04-16/3/deu"));

      var preview = service.loadZielnormExpressions(
        new LoadZielnormenExpressionsUseCase.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu")
        )
      );

      assertThat(preview).hasSize(1);
      assertThat(preview.getFirst().normWorkEli()).hasToString("eli/bund/bgbl-1/1964/s593");
      assertThat(preview.getFirst().title()).hasToString(
        "Gesetz zur Regelung des öffentlichen Vereinsrechts"
      );
      assertThat(preview.getFirst().shortTitle()).hasToString("Vereinsgesetz");
      assertThat(preview.getFirst().expressions())
        .hasSize(4)
        .containsExactly(
          new Zielnorm.Expression(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu"),
            true,
            true,
            Zielnorm.CreatedBy.OTHER_VERKUENDUNG
          ),
          new Zielnorm.Expression(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-03-16/2/deu"),
            false,
            false,
            Zielnorm.CreatedBy.THIS_VERKUENDUNG
          ),
          new Zielnorm.Expression(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-04-16/2/deu"),
            true,
            true,
            Zielnorm.CreatedBy.OTHER_VERKUENDUNG
          ),
          new Zielnorm.Expression(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-04-16/3/deu"),
            false,
            false,
            Zielnorm.CreatedBy.SYSTEM
          )
        );

      verify(eliService, times(1)).findNextExpressionEli(
        NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"),
        LocalDate.parse("2017-03-16"),
        "deu"
      );
      verify(eliService, times(1)).findNextExpressionEli(
        NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"),
        LocalDate.parse("2017-04-16"),
        "deu"
      );
      verify(loadNormPort, times(1)).loadNorm(
        new LoadNormPort.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu")
        )
      );
      verify(loadNormPort, times(1)).loadNorm(
        new LoadNormPort.Options(NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"))
      );
      verify(loadNormPort, times(1)).loadNorm(
        new LoadNormPort.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu")
        )
      );
      verify(loadNormPort, times(1)).loadNorm(
        new LoadNormPort.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-03-21/1/deu")
        )
      );
      verify(loadNormPort, times(1)).loadNorm(
        new LoadNormPort.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-04-16/1/deu")
        )
      );
      verify(loadNormPort, times(1)).loadNorm(
        new LoadNormPort.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-04-16/2/deu")
        )
      );
      verify(loadNormExpressionElisPort, times(1)).loadNormExpressionElis(
        new LoadNormExpressionElisPort.Options(NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"))
      );
    }

    @Test
    void itShouldGenerateCorrectElisForAlreadyCreatedExpressions() {
      Norm norm = Fixtures.loadNormFromDisk(NormServiceTest.class, "norm-with-amended-expressions");
      when(loadNormPort.loadNorm(new LoadNormPort.Options(norm.getExpressionEli()))).thenReturn(
        Optional.of(norm)
      );

      Norm amendedExpression = Fixtures.loadNormFromDisk(
        NormServiceTest.class,
        "vereinsgesetz-2017-03-16-1"
      );
      when(
        loadNormPort.loadNorm(new LoadNormPort.Options(amendedExpression.getWorkEli()))
      ).thenReturn(Optional.of(amendedExpression));

      when(loadNormExpressionElisPort.loadNormExpressionElis(any())).thenReturn(
        List.of(NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu"))
      );

      when(
        loadNormPort.loadNorm(new LoadNormPort.Options(amendedExpression.getExpressionEli()))
      ).thenReturn(Optional.of(amendedExpression));

      when(eliService.findNextExpressionEli(any(), any(), any())).thenReturn(
        NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2018-04-17/1/deu")
      );

      var preview = service.loadZielnormExpressions(
        new LoadZielnormenExpressionsUseCase.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu")
        )
      );

      assertThat(preview).hasSize(1);
      assertThat(preview.getFirst().normWorkEli()).hasToString("eli/bund/bgbl-1/1964/s593");
      assertThat(preview.getFirst().title()).hasToString(
        "Gesetz zur Regelung des öffentlichen Vereinsrechts"
      );
      assertThat(preview.getFirst().shortTitle()).hasToString("Vereinsgesetz");
      assertThat(preview.getFirst().expressions())
        .hasSize(2)
        .containsExactly(
          new Zielnorm.Expression(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu"),
            false,
            true,
            Zielnorm.CreatedBy.THIS_VERKUENDUNG
          ),
          new Zielnorm.Expression(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2018-04-17/1/deu"),
            false,
            false,
            Zielnorm.CreatedBy.THIS_VERKUENDUNG
          )
        );

      verify(eliService, times(1)).findNextExpressionEli(
        NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"),
        LocalDate.parse("2018-04-17"),
        "deu"
      );

      verify(loadNormPort, times(1)).loadNorm(
        new LoadNormPort.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu")
        )
      );
      verify(loadNormPort, times(1)).loadNorm(
        new LoadNormPort.Options(NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"))
      );
      verify(loadNormPort, times(1)).loadNorm(
        new LoadNormPort.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu")
        )
      );

      verify(loadNormExpressionElisPort, times(1)).loadNormExpressionElis(
        new LoadNormExpressionElisPort.Options(NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"))
      );
    }
  }

  @Nested
  class createZielnormen {

    @Test
    @Disabled("Being implemented")
    void itShouldRunloadAndSaveZielnormen() {
      CreateZielnormenExpressionsUseCase.Options options =
        new CreateZielnormenExpressionsUseCase.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"),
          NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593")
        );

      assertThatThrownBy(() -> service.createZielnormExpressions(options))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Not yet implemented");
    }
  }
}
