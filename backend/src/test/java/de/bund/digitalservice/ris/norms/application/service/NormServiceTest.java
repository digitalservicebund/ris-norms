package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.exception.InvalidUpdateException;
import de.bund.digitalservice.ris.norms.application.exception.LdmlDeNotValidException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.RegelungstextNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import java.util.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;

class NormServiceTest {

  final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  final LoadNormByGuidPort loadNormByGuidPort = mock(LoadNormByGuidPort.class);
  final LoadNormWorksPort loadNormWorksPort = mock(LoadNormWorksPort.class);
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
  final LoadExpressionsOfNormWorkPort loadExpressionsOfNormWorkPort = mock(
    LoadExpressionsOfNormWorkPort.class
  );
  final LdmlDeElementSorter ldmlDeElementSorter = mock(LdmlDeElementSorter.class);
  final LdmlDeEmptyElementRemover ldmlDeEmptyElementRemover = new LdmlDeEmptyElementRemover();
  final LdmlDeValidator ldmlDeValidator = mock(LdmlDeValidator.class);
  final CreateNewWorkService createNewWorkService = mock(CreateNewWorkService.class);

  final NormService service = new NormService(
    loadNormPort,
    loadNormByGuidPort,
    loadRegelungstextPort,
    loadNormExpressionElisPort,
    eliService,
    createNewVersionOfNormService,
    updateOrSaveNormPort,
    deleteNormPort,
    loadNormWorksPort,
    loadExpressionsOfNormWorkPort,
    ldmlDeElementSorter,
    ldmlDeEmptyElementRemover,
    ldmlDeValidator,
    createNewWorkService
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
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
      );

      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
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
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
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
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
      );
      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
      );
      when(loadRegelungstextPort.loadRegelungstext(any())).thenReturn(Optional.of(regelungstext));

      // When
      var xml = service.loadRegelungstextXml(new LoadRegelungstextXmlUseCase.Options(eli));

      // Then
      verify(loadRegelungstextPort, times(1)).loadRegelungstext(
        argThat(argument -> Objects.equals(argument.eli(), eli))
      );
      assertThat(xml).contains("eId=\"meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1\"");
    }

    @Test
    void itCallsLoadRegelungstextAndThrowsNotFound() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
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
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
      );

      var newXml = Fixtures.loadTextFromDisk(
        NormServiceTest.class,
        "vereinsgesetz-with-different-title/regelungstext-verkuendung-1.xml"
      );

      var oldNorm = Norm.builder()
        .dokumente(
          Set.of(
            Fixtures.loadRegelungstextFromDisk(
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
            )
          )
        )
        .build();
      var newNorm = Norm.builder()
        .dokumente(Set.of(new Regelungstext(XmlMapper.toDocument(newXml))))
        .build();
      newNorm.setManifestationDateTo(Norm.WORKING_COPY_DATE);

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(oldNorm));
      when(updateOrSaveNormPort.updateOrSave(any())).thenReturn(newNorm);
      when(createNewVersionOfNormService.createNewManifestation(any(), any())).thenReturn(newNorm);

      // When
      var result = service.updateRegelungstextXml(
        new UpdateRegelungstextXmlUseCase.Options(eli, newXml)
      );

      // Then
      verify(loadNormPort, times(1)).loadNorm(
        argThat(argument -> Objects.equals(argument.eli(), eli.asNormEli()))
      );
      verify(updateOrSaveNormPort, times(1)).updateOrSave(
        argThat(argument ->
          argument.norm().getManifestationEli().equals(newNorm.getManifestationEli())
        )
      );
      assertThat(result).contains("Neuer Title");
    }

    @Test
    void itThrowsNormNotFoundIfNormDoesNotExist() throws InvalidUpdateException {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
      );

      var newXml = Fixtures.loadTextFromDisk(
        NormServiceTest.class,
        "vereinsgesetz-with-different-title/regelungstext-verkuendung-1.xml"
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
      verify(updateOrSaveNormPort, times(0)).updateOrSave(any());
    }

    @Test
    void itThrowsIfEliChanges() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
      );

      var newXml = Fixtures.loadTextFromDisk(
        "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu/2017-03-15/regelungstext-verkuendung-1.xml"
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
      verify(updateOrSaveNormPort, times(0)).updateOrSave(any());
      assertThat(thrown).isInstanceOf(InvalidUpdateException.class);
    }

    @Test
    void itThrowsIfGuidChanges() {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
      );

      var newXml = Fixtures.loadTextFromDisk(
        NormServiceTest.class,
        "vereinsgesetz-with-different-guid/regelungstext-verkuendung-1.xml"
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
      verify(updateOrSaveNormPort, times(0)).updateOrSave(any());
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

      when(createNewVersionOfNormService.createNewManifestation(any(), any())).thenReturn(norm);
      when(updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Options(norm))).thenReturn(
        norm
      );

      // when
      service.updateNorm(norm);

      // then
      verify(createNewVersionOfNormService, times(1)).createNewManifestation(
        any(),
        eq(Norm.WORKING_COPY_DATE)
      );
      verify(updateOrSaveNormPort, times(1)).updateOrSave(
        argThat(argument -> Objects.equals(argument, new UpdateOrSaveNormPort.Options(norm)))
      );
      verify(ldmlDeElementSorter, times(2)).sortElements(any()); // once for regelungstext and once for rechtsetzungsdokument
      verify(ldmlDeValidator, times(1)).validateXSDSchema(any(Norm.class));
    }

    @Test
    void itDoesNotSaveNormIfValidationFails() {
      // given
      Norm norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23"
      );

      when(createNewVersionOfNormService.createNewManifestation(any(), any())).thenReturn(norm);
      doThrow(new LdmlDeNotValidException(List.of()))
        .when(ldmlDeValidator)
        .validateXSDSchema(any(Norm.class));

      // when
      assertThatThrownBy(() -> service.updateNorm(norm)).isInstanceOf(
        LdmlDeNotValidException.class
      );

      // then
      verify(updateOrSaveNormPort, times(0)).updateOrSave(any());
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
      "art-z1_abs-z_untergl-n1_listenelem-n1"
    );
    assertThat(zielnormReferences.getFirst().getGeltungszeit()).hasToString(
      "5e2f4f78-a0a1-4c55-9ef7-ad2821161915"
    );
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
      when(updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Options(any()))).thenReturn(
        norm
      );
      when(createNewVersionOfNormService.createNewManifestation(any(), any())).thenReturn(norm);

      // when
      var zielnormReferences = service.updateZielnormReferences(
        new UpdateZielnormReferencesUseCase.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"),
          List.of(
            new UpdateZielnormReferencesUseCase.ZielnormReferenceUpdateData(
              "Änderungsvorschrift",
              new Zeitgrenze.Id("f82ab983-5498-49ab-918f-5cf5e730e5ec"),
              new EId("art-z1_abs-z_untergl-n1_listenelem-n1"),
              NormWorkEli.fromString("eli/bund/bgbl-1/2024/12"),
              false
            ),
            new UpdateZielnormReferencesUseCase.ZielnormReferenceUpdateData(
              "Änderungsvorschrift",
              new Zeitgrenze.Id("3ea901c6-4cc3-484d-aa19-5557ab2420e7"),
              new EId("art-z2"),
              NormWorkEli.fromString("eli/bund/bgbl-1/2017/s419-1"),
              true
            ),
            new UpdateZielnormReferencesUseCase.ZielnormReferenceUpdateData(
              "Änderungsvorschrift",
              new Zeitgrenze.Id("6aa3a7ca-f30a-43b6-950b-b1e942fd1842"),
              new EId("art-z1_abs-z_untergl-n1_listenelem-n2"),
              NormWorkEli.fromString("eli/bund/bgbl-1/2023/22"),
              false
            )
          )
        )
      );

      // then
      assertThat(zielnormReferences).hasSize(3);
      assertThat(zielnormReferences.getFirst().getZielnorm()).hasToString(
        "eli/bund/bgbl-1/2024/12"
      );
      assertThat(zielnormReferences.getFirst().getEId()).hasToString(
        "art-z1_abs-z_untergl-n1_listenelem-n1"
      );
      assertThat(zielnormReferences.getFirst().getGeltungszeit()).hasToString(
        "f82ab983-5498-49ab-918f-5cf5e730e5ec"
      );
      assertThat(zielnormReferences.getFirst().getTyp()).isEqualTo("Änderungsvorschrift");

      assertThat(zielnormReferences.get(1).getZielnorm()).hasToString(
        "eli/bund/bgbl-1/2017/s419-1"
      );
      assertThat(zielnormReferences.get(1).getEId()).hasToString("art-z2");
      assertThat(zielnormReferences.get(1).getGeltungszeit()).hasToString(
        "3ea901c6-4cc3-484d-aa19-5557ab2420e7"
      );
      assertThat(zielnormReferences.get(1).getTyp()).isEqualTo("Änderungsvorschrift");

      assertThat(zielnormReferences.get(2).getZielnorm()).hasToString("eli/bund/bgbl-1/2023/22");
      assertThat(zielnormReferences.get(2).getEId()).hasToString(
        "art-z1_abs-z_untergl-n1_listenelem-n2"
      );
      assertThat(zielnormReferences.get(2).getGeltungszeit()).hasToString(
        "6aa3a7ca-f30a-43b6-950b-b1e942fd1842"
      );
      assertThat(zielnormReferences.get(2).getTyp()).isEqualTo("Änderungsvorschrift");

      verify(updateOrSaveNormPort, times(1)).updateOrSave(any());
    }

    @Test
    void addOne() {
      // given
      Norm norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23"
      );

      when(loadNormPort.loadNorm(new LoadNormPort.Options(any()))).thenReturn(Optional.of(norm));
      when(updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Options(any()))).thenReturn(
        norm
      );
      when(createNewVersionOfNormService.createNewManifestation(any(), any())).thenReturn(norm);

      // when
      var zielnormReferences = service.updateZielnormReferences(
        new UpdateZielnormReferencesUseCase.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"),
          List.of(
            new UpdateZielnormReferencesUseCase.ZielnormReferenceUpdateData(
              "Änderungsvorschrift",
              new Zeitgrenze.Id("07fdc138-1509-4165-9ec7-f26f9d5c8cb8"),
              new EId("art-z1_abs-z_untergl-n1_listenelem-n2"),
              NormWorkEli.fromString("eli/bund/bgbl-1/2023/22"),
              false
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
        "art-z1_abs-z_untergl-n1_listenelem-n1"
      );
      assertThat(zielnormReferences.getFirst().getGeltungszeit()).hasToString(
        "5e2f4f78-a0a1-4c55-9ef7-ad2821161915"
      );
      assertThat(zielnormReferences.getFirst().getTyp()).isEqualTo("Änderungsvorschrift");
      assertThat(zielnormReferences.get(1).getZielnorm()).hasToString("eli/bund/bgbl-1/2023/22");
      assertThat(zielnormReferences.get(1).getEId()).hasToString(
        "art-z1_abs-z_untergl-n1_listenelem-n2"
      );
      assertThat(zielnormReferences.get(1).getGeltungszeit()).hasToString(
        "07fdc138-1509-4165-9ec7-f26f9d5c8cb8"
      );
      assertThat(zielnormReferences.get(1).getTyp()).isEqualTo("Änderungsvorschrift");

      verify(updateOrSaveNormPort, times(1)).updateOrSave(any());
    }
  }

  @Test
  void deleteZielnormReferences() {
    // given
    Norm norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23");

    when(loadNormPort.loadNorm(new LoadNormPort.Options(any()))).thenReturn(Optional.of(norm));
    when(updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Options(any()))).thenReturn(
      norm
    );
    when(createNewVersionOfNormService.createNewManifestation(any(), any())).thenReturn(norm);

    // when
    var zielnormReferences = service.deleteZielnormReferences(
      new DeleteZielnormReferencesUseCase.Options(
        NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"),
        List.of(new EId("art-z1_abs-z_untergl-n1_listenelem-n1"))
      )
    );

    // then
    assertThat(zielnormReferences).isEmpty();
    verify(updateOrSaveNormPort, times(1)).updateOrSave(any());
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
      when(eliService.findNextExpressionEli(any(), any(), any())).thenReturn(
        NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-04-16/3/deu")
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
        .hasSize(3)
        .containsExactly(
          new Zielnorm.Expression(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu"),
            false,
            true,
            false,
            Zielnorm.CreatedBy.THIS_VERKUENDUNG
          ),
          new Zielnorm.Expression(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-04-16/2/deu"),
            true,
            true,
            false,
            Zielnorm.CreatedBy.OTHER_VERKUENDUNG
          ),
          new Zielnorm.Expression(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-04-16/3/deu"),
            false,
            false,
            false,
            Zielnorm.CreatedBy.SYSTEM
          )
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
    void itExtractsElisFromEingebundeneStammform() {
      //given
      Norm normVerkuendung = Fixtures.loadNormFromDisk(
        NormServiceTest.class,
        "gleichstellung_bundeswehr_with_eingebundeneStammform"
      );

      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Options(
            NormExpressionEli.fromString("eli/bund/bgbl-1/2024/17/2024-01-24/1/deu")
          )
        )
      ).thenReturn(Optional.of(normVerkuendung));

      //when
      var preview = service.loadZielnormExpressions(
        new LoadZielnormenExpressionsUseCase.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/2024/17/2024-01-24/1/deu")
        )
      );

      //than
      assertThat(preview).hasSize(1);
      assertThat(preview.getFirst().normWorkEli()).hasToString("eli/bund/bgbl-1/2024/17-1");
      assertThat(preview.getFirst().title()).hasToString(
        "Soldatinnen- und Soldatengleichstellungsgesetz"
      );
      assertThat(preview.getFirst().shortTitle()).hasToString("(SGleiG)");
      assertThat(preview.getFirst().expressions())
        .hasSize(1)
        .containsExactly(
          new Zielnorm.Expression(
            NormExpressionEli.fromString("eli/bund/bgbl-1/2024/17-1/2017-03-16/1/deu"),
            false,
            false,
            false,
            Zielnorm.CreatedBy.THIS_VERKUENDUNG
          )
        );

      verify(loadNormPort, times(1)).loadNorm(
        new LoadNormPort.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/2024/17/2024-01-24/1/deu")
        )
      );
    }

    @Test
    void itExtractsElisFromEingebundeneStammformWhenPreviouslySavedInDatabase() {
      //given
      Norm normVerkuendung = Fixtures.loadNormFromDisk(
        NormServiceTest.class,
        "gleichstellung_bundeswehr_with_eingebundeneStammform"
      );

      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Options(
            NormExpressionEli.fromString("eli/bund/bgbl-1/2024/17/2024-01-24/1/deu")
          )
        )
      ).thenReturn(Optional.of(normVerkuendung));

      Dokument regelungstextEingebundeneStammform = normVerkuendung
        .getDokumente()
        .stream()
        .filter(d ->
          d
            .getManifestationEli()
            .equals(
              DokumentManifestationEli.fromString(
                "eli/bund/bgbl-1/2024/17/2024-01-24/1/deu/2024-01-24/regelungstext-verkuendung-2.xml"
              )
            )
        )
        .findFirst()
        .get();
      Norm eingebundeneStammform = Norm.builder()
        .dokumente(Set.of(regelungstextEingebundeneStammform))
        .build();
      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Options(NormWorkEli.fromString("eli/bund/bgbl-1/2024/17-1"))
        )
      ).thenReturn(Optional.of(eingebundeneStammform));

      //when
      var preview = service.loadZielnormExpressions(
        new LoadZielnormenExpressionsUseCase.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/2024/17/2024-01-24/1/deu")
        )
      );

      //than
      assertThat(preview).hasSize(1);
      assertThat(preview.getFirst().normWorkEli()).hasToString("eli/bund/bgbl-1/2024/17-1");
      assertThat(preview.getFirst().title()).hasToString(
        "Soldatinnen- und Soldatengleichstellungsgesetz"
      );
      assertThat(preview.getFirst().shortTitle()).hasToString("(SGleiG)");
      assertThat(preview.getFirst().expressions())
        .hasSize(1)
        .containsExactly(
          new Zielnorm.Expression(
            NormExpressionEli.fromString("eli/bund/bgbl-1/2024/17/2024-01-24/1/deu"),
            false,
            true,
            false,
            Zielnorm.CreatedBy.THIS_VERKUENDUNG
          )
        );

      verify(loadNormPort, times(1)).loadNorm(
        new LoadNormPort.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/2024/17/2024-01-24/1/deu")
        )
      );
    }

    @Test
    void itReturnsEmptyListWhenNoZielnormReferencesExist() {
      // given
      Norm noZielnorm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
      );

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(noZielnorm));

      // when
      var result = service.loadZielnormExpressions(
        new LoadZielnormenExpressionsUseCase.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      // then
      assertThat(result).isEmpty();

      verify(loadNormPort, times(1)).loadNorm(any());
    }

    @Test
    void itFailsWhenEingebundeneStammformArticleIsMissing() {
      // given
      Norm invalidNorm = Fixtures.loadNormFromDisk(
        NormServiceTest.class,
        "gleichstellung_bundeswehr_with_eingebundeneStammform"
      );
      Element zielnorm = invalidNorm
        .getRegelungstext1()
        .getMeta()
        .getProprietary()
        .flatMap(Proprietary::getCustomModsMetadata)
        .flatMap(CustomModsMetadata::getZielnormenReferences)
        .get()
        .stream()
        .findFirst()
        .get()
        .getElement();
      NodeParser.getElementFromExpression("./eid", zielnorm)
        .get()
        .setTextContent("eli/bund/bgbl-1/2222/s222/2222-02-22/2/deu");

      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Options(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
          )
        )
      ).thenReturn(Optional.of(invalidNorm));
      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Options(
            NormExpressionEli.fromString("eli/bund/bgbl-1/2222/s222/2222-02-22/2/deu")
          )
        )
      ).thenReturn(Optional.empty());

      // when/then
      LoadZielnormenExpressionsUseCase.Options options =
        new LoadZielnormenExpressionsUseCase.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        );

      assertThatThrownBy(() -> service.loadZielnormExpressions(options))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining(
          "Reference was wrong: No article found for EId eli/bund/bgbl-1/2222/s222/2222-02-22/2/deu"
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
            false,
            Zielnorm.CreatedBy.THIS_VERKUENDUNG
          ),
          new Zielnorm.Expression(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2018-04-17/1/deu"),
            false,
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
    void itCreatesZielNorm() {
      final Norm amendingLaw = Fixtures.loadNormFromDisk(
        NormServiceTest.class,
        "norm-with-amended-expressions"
      );
      final Norm targetLaw = Fixtures.loadNormFromDisk(
        NormServiceTest.class,
        "vereinsgesetz-original-expression"
      );
      final Norm amendedExpression = Fixtures.loadNormFromDisk(
        NormServiceTest.class,
        "vereinsgesetz-2017-03-16-1"
      );

      final List<Zielnorm.Expression> expressionen = new ArrayList<>();
      expressionen.add(
        new Zielnorm.Expression(
          amendedExpression.getExpressionEli(),
          false,
          false,
          false,
          Zielnorm.CreatedBy.THIS_VERKUENDUNG
        )
      );
      final List<Zielnorm> zielNormen = new ArrayList<>();
      final NormWorkEli zielWorkEli = NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593");
      final Zielnorm zielnorm = new Zielnorm(
        zielWorkEli,
        "Gesetz zur Regelung des öffentlichen Vereinsrechts",
        "Vereinsgesetz",
        expressionen
      );
      zielNormen.add(zielnorm);
      NormService spiedService = spy(service);
      doReturn(zielNormen).when(spiedService).loadZielnormExpressions(any());

      // Mock load amending law
      when(
        loadNormPort.loadNorm(new LoadNormPort.Options(amendingLaw.getExpressionEli()))
      ).thenReturn(Optional.of(amendingLaw));

      // Mock get closest previous expression
      when(loadNormExpressionElisPort.loadNormExpressionElis(any())).thenReturn(
        List.of(NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"))
      );
      when(
        loadNormPort.loadNorm(new LoadNormPort.Options(targetLaw.getExpressionEli()))
      ).thenReturn(Optional.of(targetLaw));

      // Mock creating new expression (and new previous manifestation)
      var result = new CreateNewVersionOfNormService.CreateNewExpressionResult(
        amendedExpression,
        targetLaw
      );
      when(
        createNewVersionOfNormService.createNewExpression(
          targetLaw,
          amendedExpression.getExpressionEli().getPointInTime()
        )
      ).thenReturn(result);

      // Mock saving new expression (and new previous manifestation)
      when(updateOrSaveNormPort.updateOrSave(any())).thenReturn(mock(Norm.class));
      when(updateOrSaveNormPort.updateOrSave(any())).thenReturn(mock(Norm.class));

      // Mock saving updated amending law
      when(createNewVersionOfNormService.createNewManifestation(any(), any())).thenReturn(
        amendingLaw
      );
      when(updateOrSaveNormPort.updateOrSave(any())).thenReturn(mock(Norm.class));

      var createdExpressions = spiedService.createZielnormExpressions(
        new CreateZielnormenExpressionsUseCase.Options(amendingLaw.getExpressionEli(), zielWorkEli)
      );

      assertThat(createdExpressions.expressions()).hasSize(1);
      assertThat(createdExpressions.expressions().getFirst().normExpressionEli()).isEqualTo(
        amendedExpression.getExpressionEli()
      );
      assertThat(createdExpressions.expressions().getFirst().isGegenstandslos()).isFalse();
      assertThat(createdExpressions.expressions().getFirst().isCreated()).isTrue();
      assertThat(createdExpressions.expressions().getFirst().createdBy()).isEqualTo(
        Zielnorm.CreatedBy.THIS_VERKUENDUNG
      );
    }

    @Test
    void itCreatesNewWork() {
      final Norm amendingLawWithEs = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2024/17/2024-01-24/1/deu/2024-01-24"
      );
      final List<Zielnorm.Expression> expressionen = new ArrayList<>();
      expressionen.add(
        new Zielnorm.Expression(
          NormExpressionEli.fromString("eli/bund/bgbl-1/2024/17-1/2024-01-24/1/deu"),
          false,
          false,
          false,
          Zielnorm.CreatedBy.THIS_VERKUENDUNG
        )
      );
      final List<Zielnorm> zielNormen = new ArrayList<>();
      final NormWorkEli zielWorkEli = NormWorkEli.fromString("eli/bund/bgbl-1/2024/17-1");
      final Zielnorm zielnorm = new Zielnorm(
        zielWorkEli,
        "Soldatinnen- und Soldatengleichstellungsgesetz",
        "(SGleiG)",
        expressionen
      );
      zielNormen.add(zielnorm);
      NormService spiedService = spy(service);
      doReturn(zielNormen).when(spiedService).loadZielnormExpressions(any());

      // Mock load amending law
      when(
        loadNormPort.loadNorm(new LoadNormPort.Options(amendingLawWithEs.getExpressionEli()))
      ).thenReturn(Optional.of(amendingLawWithEs));

      // Mock saving new work
      when(createNewVersionOfNormService.createNewManifestation(any(), any())).thenReturn(
        amendingLawWithEs
      );
      when(updateOrSaveNormPort.updateOrSave(any())).thenReturn(mock(Norm.class));

      // Mock saving updated amending law
      when(createNewVersionOfNormService.createNewManifestation(any(), any())).thenReturn(
        amendingLawWithEs
      );
      when(updateOrSaveNormPort.updateOrSave(any())).thenReturn(mock(Norm.class));

      var createdExpressions = spiedService.createZielnormExpressions(
        new CreateZielnormenExpressionsUseCase.Options(
          amendingLawWithEs.getExpressionEli(),
          zielWorkEli
        )
      );

      assertThat(createdExpressions.expressions()).hasSize(1);
      assertThat(createdExpressions.expressions().getFirst().normExpressionEli()).hasToString(
        "eli/bund/bgbl-1/2024/17-1/2024-01-24/1/deu"
      );
      assertThat(createdExpressions.expressions().getFirst().isGegenstandslos()).isFalse();
      assertThat(createdExpressions.expressions().getFirst().isCreated()).isTrue();
      assertThat(createdExpressions.expressions().getFirst().createdBy()).isEqualTo(
        Zielnorm.CreatedBy.THIS_VERKUENDUNG
      );
    }

    @Test
    void itThrowsWhenExpressionOfWorkAlreadyExists() {
      // Given
      final Norm amendingLawWithEs = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2024/17/2024-01-24/1/deu/2024-01-24"
      );
      final List<Zielnorm.Expression> expressionen = new ArrayList<>();
      expressionen.add(
        new Zielnorm.Expression(
          NormExpressionEli.fromString("eli/bund/bgbl-1/2024/17-1/2024-01-24/1/deu"),
          false,
          true,
          false,
          Zielnorm.CreatedBy.THIS_VERKUENDUNG
        )
      );
      final List<Zielnorm> zielNormen = new ArrayList<>();
      final NormWorkEli zielWorkEli = NormWorkEli.fromString("eli/bund/bgbl-1/2024/17-1");
      final Zielnorm zielnorm = new Zielnorm(
        zielWorkEli,
        "Soldatinnen- und Soldatengleichstellungsgesetz",
        "(SGleiG)",
        expressionen
      );
      zielNormen.add(zielnorm);

      NormService spiedService = spy(service);
      doReturn(zielNormen).when(spiedService).loadZielnormExpressions(any());
      // Mock load amending law
      when(
        loadNormPort.loadNorm(new LoadNormPort.Options(amendingLawWithEs.getExpressionEli()))
      ).thenReturn(Optional.of(amendingLawWithEs));

      // When // Then
      assertThatThrownBy(() ->
        spiedService.createZielnormExpressions(
          new CreateZielnormenExpressionsUseCase.Options(
            amendingLawWithEs.getExpressionEli(),
            zielWorkEli
          )
        )
      ).isInstanceOf(
        CreateZielnormenExpressionsUseCase.ExpressionOfNewWorkAlreadyExistsException.class
      ); // Then
    }
  }
}
