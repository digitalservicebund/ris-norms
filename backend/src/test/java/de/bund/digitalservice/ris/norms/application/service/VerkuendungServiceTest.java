package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.exception.*;
import de.bund.digitalservice.ris.norms.application.port.input.CreateVerkuendungUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormExpressionsAffectedByVerkuendungUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadVerkuendungUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

class VerkuendungServiceTest {

  final LoadAllVerkuendungenPort loadAllVerkuendungenPort = mock(LoadAllVerkuendungenPort.class);
  final LoadVerkuendungByNormEliPort loadVerkuendungByNormEliPort = mock(
    LoadVerkuendungByNormEliPort.class
  );
  final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  final LoadNormByGuidPort loadNormByGuidPort = mock(LoadNormByGuidPort.class);
  final LdmlDeValidator ldmlDeValidator = mock(LdmlDeValidator.class);
  final UpdateOrSaveVerkuendungPort updateOrSaveVerkuendungPort = mock(
    UpdateOrSaveVerkuendungPort.class
  );
  final DeleteVerkuendungByNormEliPort deleteVerkuendungByNormEliPort = mock(
    DeleteVerkuendungByNormEliPort.class
  );
  private final UpdateOrSaveNormPort updateOrSaveNormPort = mock(UpdateOrSaveNormPort.class);

  final VerkuendungService verkuendungService = new VerkuendungService(
    loadAllVerkuendungenPort,
    loadVerkuendungByNormEliPort,
    loadNormPort,
    loadNormByGuidPort,
    updateOrSaveVerkuendungPort,
    ldmlDeValidator,
    deleteVerkuendungByNormEliPort,
    updateOrSaveNormPort
  );

  @Nested
  class loadAllVerkuendungen {

    @Test
    void itReturnsVerkuendungen() {
      // Given
      var norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
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
      var norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
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
      var norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
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
  class createVerkuendung {

    @Test
    void itCreatesANewVerkuendung() throws IOException {
      // Given
      var xmlContent = Fixtures.loadTextFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );
      final MultipartFile file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Command(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
          )
        )
      ).thenReturn(
        Optional.of(
          Fixtures.loadNormFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );
      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Command(
            NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu")
          )
        )
      ).thenReturn(Optional.empty());

      when(
        updateOrSaveVerkuendungPort.updateOrSaveVerkuendung(
          new UpdateOrSaveVerkuendungPort.Command(any())
        )
      ).thenReturn(
        Verkuendung.builder()
          .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"))
          .build()
      );

      // When
      var verkuendung = verkuendungService.createVerkuendung(
        new CreateVerkuendungUseCase.Options(file, false)
      );

      // Then
      verify(updateOrSaveVerkuendungPort, times(1)).updateOrSaveVerkuendung(any());
      assertThat(verkuendung.getEli()).hasToString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu");
    }

    @Test
    void itThrowsWhenTheFileIsNotXML() throws IOException {
      // Given
      var xmlContent = Fixtures.loadTextFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );
      final MultipartFile file = new MockMultipartFile(
        "file",
        "norm.txt",
        "text/plain",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      // When // Then
      var query = new CreateVerkuendungUseCase.Options(file, false);
      assertThatThrownBy(() -> verkuendungService.createVerkuendung(query)).isInstanceOf(
        NotAXmlFileException.class
      );
    }

    @Test
    void itThrowsWhenXmlNotLdmlDe() throws IOException {
      // Given
      var xmlContent =
        """
            <root>
              <child>Sample content</child>
            </root>
        """;
      final MultipartFile file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      // When // Then
      var query = new CreateVerkuendungUseCase.Options(file, false);
      assertThatThrownBy(() -> verkuendungService.createVerkuendung(query)).isInstanceOf(
        CreateVerkuendungUseCase.NotLdmlDeXmlFileException.class
      );
    }

    @Test
    void itThrowsWhenAnEliOfTheSameEliExists() throws IOException {
      // Given
      var xmlContent = Fixtures.loadTextFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );
      final MultipartFile file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      doReturn(
        Optional.of(
          Fixtures.loadNormFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      )
        .when(loadNormPort)
        .loadNorm(
          argThat(argument ->
            argument
              .eli()
              .equals(NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"))
          )
        );
      doReturn(
        Optional.of(
          Fixtures.loadNormFromDisk(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
          )
        )
      )
        .when(loadNormPort)
        .loadNorm(
          argThat(argument ->
            argument
              .eli()
              .equals(NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"))
          )
        );

      // When // Then
      var query = new CreateVerkuendungUseCase.Options(file, false);
      assertThatThrownBy(() -> verkuendungService.createVerkuendung(query)).isInstanceOf(
        NormExistsAlreadyException.class
      );
    }

    @Test
    void itThrowsWhenANormWithSameGuidExists() throws IOException {
      // Given
      var norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );
      var xmlContent = XmlMapper.toString(norm.getRegelungstext1().getDocument());
      final MultipartFile file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Command(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
          )
        )
      ).thenReturn(
        Optional.of(
          Fixtures.loadNormFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );
      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Command(
            NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu")
          )
        )
      ).thenReturn(Optional.empty());
      when(
        loadNormByGuidPort.loadNormByGuid(
          new LoadNormByGuidPort.Command(UUID.fromString("e47a5106-c153-4da4-8d94-8cc2ebf9b232"))
        )
      ).thenReturn(Optional.of(norm));

      // When // Then
      var query = new CreateVerkuendungUseCase.Options(file, false);
      assertThatThrownBy(() -> verkuendungService.createVerkuendung(query)).isInstanceOf(
        NormWithGuidAlreadyExistsException.class
      );
    }

    @Test
    void itThrowsWhenTheNormIsNotXsdValid() throws IOException {
      // Given
      var xmlContent = Fixtures.loadTextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      final MultipartFile file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      when(ldmlDeValidator.parseAndValidateRegelungstext(any())).thenThrow(
        new LdmlDeNotValidException(List.of())
      );

      // When // Then
      var query = new CreateVerkuendungUseCase.Options(file, false);
      assertThatThrownBy(() -> verkuendungService.createVerkuendung(query)).isInstanceOf(
        LdmlDeNotValidException.class
      );
    }

    @Test
    void itThrowsWhenTheNormIsNotSchematronValid() throws IOException {
      // Given
      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      var xmlContent = XmlMapper.toString(regelungstext.getDocument());
      final MultipartFile file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Command(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
          )
        )
      ).thenReturn(
        Optional.of(
          Fixtures.loadNormFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );
      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Command(
            NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu")
          )
        )
      ).thenReturn(Optional.empty());
      when(ldmlDeValidator.parseAndValidateRegelungstext(any())).thenReturn(regelungstext);
      doThrow(new LdmlDeSchematronException(List.of()))
        .when(ldmlDeValidator)
        .validateSchematron(regelungstext);

      // When // Then
      var query = new CreateVerkuendungUseCase.Options(file, false);
      assertThatThrownBy(() -> verkuendungService.createVerkuendung(query)).isInstanceOf(
        LdmlDeSchematronException.class
      );
    }

    @Test
    void itCreatesANewVerkuendungWithForce() throws IOException {
      // Given
      var xmlContent = Fixtures.loadTextFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );
      final MultipartFile file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      // When
      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Command(
            NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
          )
        )
      ).thenReturn(
        Optional.of(
          Fixtures.loadNormFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );
      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Command(
            NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu")
          )
        )
      ).thenReturn(
        Optional.of(
          Fixtures.loadNormFromDisk(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
          )
        )
      );

      when(
        updateOrSaveVerkuendungPort.updateOrSaveVerkuendung(
          new UpdateOrSaveVerkuendungPort.Command(any())
        )
      ).thenReturn(
        Verkuendung.builder()
          .eli(NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"))
          .build()
      );

      var verkuendung = verkuendungService.createVerkuendung(
        new CreateVerkuendungUseCase.Options(file, true)
      );

      // Then
      verify(updateOrSaveVerkuendungPort, times(1)).updateOrSaveVerkuendung(any());
      assertThat(verkuendung.getEli()).hasToString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu");
    }
  }

  @Nested
  class loadNormExpressionsAffectedByVerkuendung {

    @Test
    void itReturnsListOfNorms() {
      // Given
      var verkuendungsNorm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );
      var affectedNorm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s593/2017-03-15/1/deu/2017-03-15/regelungstext-1.xml"
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
        new LoadNormPort.Command(verkuendungsNorm.getExpressionEli())
      );
      verify(loadNormPort, times(1)).loadNorm(
        new LoadNormPort.Command(
          NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s593/2017-03-15/1/deu")
        )
      );
    }

    @Test
    void itDoesntReturnNonExistingNorms() {
      // Given
      var verkuendungsNorm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
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
