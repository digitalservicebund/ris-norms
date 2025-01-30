package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.exception.*;
import de.bund.digitalservice.ris.norms.application.port.input.CreateAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAnnouncementByNormEliUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
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

class AnnouncementServiceTest {

  final LoadAllAnnouncementsPort loadAllAnnouncementsPort = mock(LoadAllAnnouncementsPort.class);
  final LoadAnnouncementByNormEliPort loadAnnouncementByNormEliPort = mock(
    LoadAnnouncementByNormEliPort.class
  );
  final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  final LoadNormByGuidPort loadNormByGuidPort = mock(LoadNormByGuidPort.class);
  final CreateZf0Service loadZf0Service = mock(CreateZf0Service.class);
  final BillToActService billToActService = mock(BillToActService.class);
  final LdmlDeValidator ldmlDeValidator = mock(LdmlDeValidator.class);
  final UpdateOrSaveAnnouncementPort updateOrSaveAnnouncementPort = mock(
    UpdateOrSaveAnnouncementPort.class
  );
  final DeleteAnnouncementByNormEliPort deleteAnnouncementByNormEliPort = mock(
    DeleteAnnouncementByNormEliPort.class
  );
  final DeleteNormPort deleteNormPort = mock(DeleteNormPort.class);
  final ReferenceService referenceService = mock(ReferenceService.class);
  private final UpdateOrSaveNormPort updateOrSaveNormPort = mock(UpdateOrSaveNormPort.class);

  final AnnouncementService announcementService = new AnnouncementService(
    loadAllAnnouncementsPort,
    loadAnnouncementByNormEliPort,
    loadNormPort,
    loadNormByGuidPort,
    loadZf0Service,
    updateOrSaveAnnouncementPort,
    billToActService,
    ldmlDeValidator,
    deleteAnnouncementByNormEliPort,
    deleteNormPort,
    referenceService,
    updateOrSaveNormPort
  );

  @Nested
  class loadAllAnnouncements {

    @Test
    void itReturnsAnnouncements() {
      // Given
      var norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
      var announcement = Announcement.builder().eli(norm.getNormExpressionEli()).build();
      when(loadAllAnnouncementsPort.loadAllAnnouncements()).thenReturn(List.of(announcement));

      // When
      var loadedAnnouncements = announcementService.loadAllAnnouncements();

      // Then
      verify(loadAllAnnouncementsPort, times(1)).loadAllAnnouncements();
      assertThat(loadedAnnouncements).hasSize(1).containsExactly(announcement);
    }
  }

  @Nested
  class loadAnnouncement {

    @Test
    void itThrowsAnnouncementNotFoundException() {
      // given
      var norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
      final var query = new LoadAnnouncementByNormEliUseCase.Query(
        NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
      );

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));
      when(loadAnnouncementByNormEliPort.loadAnnouncementByNormEli(any()))
        .thenReturn(Optional.empty());

      // when
      assertThatThrownBy(() -> announcementService.loadAnnouncementByNormEli(query))
        // then
        .isInstanceOf(AnnouncementNotFoundException.class);
    }

    @Test
    void itThrowsAnnouncementNotFoundExceptionIfNormDoesNotExist() {
      // given
      final var query = new LoadAnnouncementByNormEliUseCase.Query(
        NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
      );

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.empty());

      // when
      assertThatThrownBy(() -> announcementService.loadAnnouncementByNormEli(query))
        // then
        .isInstanceOf(AnnouncementNotFoundException.class);
    }

    @Test
    void itReturnsAnnouncement() {
      // Given
      var norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
      var announcement = Announcement.builder().eli(norm.getNormExpressionEli()).build();

      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(norm));
      when(loadAnnouncementByNormEliPort.loadAnnouncementByNormEli(any()))
        .thenReturn(Optional.of(announcement));

      // When
      var loadedAnnouncement = announcementService.loadAnnouncementByNormEli(
        new LoadAnnouncementByNormEliUseCase.Query(
          NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
        )
      );

      // Then
      verify(loadAnnouncementByNormEliPort, times(1)).loadAnnouncementByNormEli(any());
      assertThat(loadedAnnouncement).hasToString(announcement.toString());
    }
  }

  @Nested
  class createAnnouncement {

    @Test
    void itCreatesANewAnnouncement() throws IOException {
      // Given
      var xmlContent = XmlMapper.toString(
        Fixtures.loadNormFromDisk("NormWithMods.xml").getDocument()
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
      )
        .thenReturn(Optional.of(Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml")));
      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Command(
            NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu")
          )
        )
      )
        .thenReturn(Optional.empty());

      // When
      var announcement = announcementService.createAnnouncement(
        new CreateAnnouncementUseCase.Query(file, false)
      );

      // Then
      verify(updateOrSaveAnnouncementPort, times(1)).updateOrSaveAnnouncement(any());
      assertThat(announcement.getEli()).hasToString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu");
    }

    @Test
    void itThrowsWhenTheFileIsNotXML() throws IOException {
      // Given
      var xmlContent = XmlMapper.toString(
        Fixtures.loadNormFromDisk("NormWithMods.xml").getDocument()
      );
      final MultipartFile file = new MockMultipartFile(
        "file",
        "norm.txt",
        "text/plain",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      // When // Then
      var query = new CreateAnnouncementUseCase.Query(file, false);
      assertThatThrownBy(() -> announcementService.createAnnouncement(query))
        .isInstanceOf(NotAXmlFileException.class);
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
      var query = new CreateAnnouncementUseCase.Query(file, false);
      assertThatThrownBy(() -> announcementService.createAnnouncement(query))
        .isInstanceOf(NotLdmlDeXmlFileException.class);
    }

    @Test
    void itThrowsWhenADestinationEliDoesNotExist() throws IOException {
      // Given
      var xmlContent = XmlMapper.toString(
        Fixtures.loadNormFromDisk("NormWithMods.xml").getDocument()
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
            DokumentExpressionEli.fromString(
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
            )
          )
        )
      )
        .thenReturn(Optional.empty());

      // When // Then
      var query = new CreateAnnouncementUseCase.Query(file, false);
      assertThatThrownBy(() -> announcementService.createAnnouncement(query))
        .isInstanceOf(ActiveModDestinationNormNotFoundException.class);
    }

    @Test
    void itThrowsWhenAnEliOfTheSameEliExists() throws IOException {
      // Given
      var xmlContent = XmlMapper.toString(
        Fixtures.loadNormFromDisk("NormWithMods.xml").getDocument()
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
            DokumentExpressionEli.fromString(
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
            )
          )
        )
      )
        .thenReturn(Optional.of(Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml")));
      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Command(
            DokumentExpressionEli.fromString(
              "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
            )
          )
        )
      )
        .thenReturn(Optional.of(Fixtures.loadNormFromDisk("NormWithMods.xml")));

      // When // Then
      var query = new CreateAnnouncementUseCase.Query(file, false);
      assertThatThrownBy(() -> announcementService.createAnnouncement(query))
        .isInstanceOf(NormExistsAlreadyException.class);
    }

    @Test
    void itThrowsWhenANormWithSameGuidExists() throws IOException {
      // Given
      var norm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      var xmlContent = XmlMapper.toString(norm.getDocument());
      final MultipartFile file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Command(
            DokumentExpressionEli.fromString(
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
            )
          )
        )
      )
        .thenReturn(Optional.of(Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml")));
      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Command(
            DokumentExpressionEli.fromString(
              "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
            )
          )
        )
      )
        .thenReturn(Optional.empty());
      when(
        loadNormByGuidPort.loadNormByGuid(
          new LoadNormByGuidPort.Command(UUID.fromString("ba44d2ae-0e73-44ba-850a-932ab2fa553f"))
        )
      )
        .thenReturn(Optional.of(norm));

      // When // Then
      var query = new CreateAnnouncementUseCase.Query(file, false);
      assertThatThrownBy(() -> announcementService.createAnnouncement(query))
        .isInstanceOf(NormWithGuidAlreadyExistsException.class);
    }

    @Test
    void itThrowsWhenTheNormIsNotXsdValid() throws IOException {
      // Given
      var xmlContent = XmlMapper.toString(
        Fixtures.loadNormFromDisk("NormWithModsXsdInvalid.xml").getDocument()
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
            DokumentExpressionEli.fromString(
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
            )
          )
        )
      )
        .thenReturn(Optional.of(Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml")));
      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Command(
            DokumentExpressionEli.fromString(
              "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
            )
          )
        )
      )
        .thenReturn(Optional.empty());
      when(ldmlDeValidator.parseAndValidate(any()))
        .thenThrow(new LdmlDeNotValidException(List.of()));

      // When // Then
      var query = new CreateAnnouncementUseCase.Query(file, false);
      assertThatThrownBy(() -> announcementService.createAnnouncement(query))
        .isInstanceOf(LdmlDeNotValidException.class);
    }

    @Test
    void itThrowsWhenTheNormIsNotSchematronValid() throws IOException {
      // Given
      var regelungstext = Fixtures.loadRegelungstextFromDisk("NormWithModsSchematronInvalid.xml");
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
            DokumentExpressionEli.fromString(
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
            )
          )
        )
      )
        .thenReturn(Optional.of(Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml")));
      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Command(
            DokumentExpressionEli.fromString(
              "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
            )
          )
        )
      )
        .thenReturn(Optional.empty());
      when(ldmlDeValidator.parseAndValidate(any())).thenReturn(regelungstext);
      doThrow(new LdmlDeSchematronException(List.of()))
        .when(ldmlDeValidator)
        .validateSchematron(regelungstext);

      // When // Then
      var query = new CreateAnnouncementUseCase.Query(file, false);
      assertThatThrownBy(() -> announcementService.createAnnouncement(query))
        .isInstanceOf(LdmlDeSchematronException.class);
    }

    @Test
    void itCreatesANewAnnouncementWithForce() throws IOException {
      // Given
      var xmlContent = XmlMapper.toString(
        Fixtures.loadNormFromDisk("NormWithMods.xml").getDocument()
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
      )
        .thenReturn(Optional.of(Fixtures.loadNormFromDisk("NormWithoutPassiveModifications.xml")));
      when(
        loadNormPort.loadNorm(
          new LoadNormPort.Command(
            NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu")
          )
        )
      )
        .thenReturn(Optional.of(Fixtures.loadNormFromDisk("NormWithMods.xml")));

      var announcement = announcementService.createAnnouncement(
        new CreateAnnouncementUseCase.Query(file, true)
      );

      // Then
      verify(updateOrSaveAnnouncementPort, times(1)).updateOrSaveAnnouncement(any());
      assertThat(announcement.getEli()).hasToString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu");
    }
  }
}
