package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.exception.AnnouncementNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.CreateAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAllAnnouncementsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormExpressionsAffectedByVerkuendungUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SecurelessControllerTest(AnnouncementController.class)
class AnnouncementControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private LoadAllAnnouncementsUseCase loadAllAnnouncementsUseCase;

  @MockitoBean
  private LoadAnnouncementUseCase loadAnnouncementUseCase;

  @MockitoBean
  private LoadNormUseCase loadNormUseCase;

  @MockitoBean
  private CreateAnnouncementUseCase createAnnouncementUseCase;

  @MockitoBean
  private LoadNormExpressionsAffectedByVerkuendungUseCase loadNormExpressionsAffectedByVerkuendungUseCase;

  @Nested
  class getAllAnnouncements {

    @Test
    void itReturnsAnnouncements() throws Exception {
      // Given
      var norm1 = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );
      var announcement1 = Announcement
        .builder()
        .eli(norm1.getExpressionEli())
        .importTimestamp(Instant.parse("2025-03-13T15:00:00Z"))
        .build();

      var norm2 = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2024/10/2024-01-18/1/deu/2024-01-18/regelungstext-1.xml"
      );
      var announcement2 = Announcement
        .builder()
        .eli(norm2.getExpressionEli())
        .importTimestamp(Instant.parse("2025-03-13T16:00:00Z"))
        .build();

      // When
      when(loadAllAnnouncementsUseCase.loadAllAnnouncements())
        .thenReturn(List.of(announcement1, announcement2));
      when(loadNormUseCase.loadNorm(any())).thenReturn(norm1).thenReturn(norm2);

      // When // Then
      mockMvc
        .perform(get("/api/v1/verkuendungen").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[1]").exists())
        .andExpect(jsonPath("$[2]").doesNotExist())
        .andExpect(
          jsonPath(
            "$[0].title",
            equalTo("Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes")
          )
        )
        .andExpect(
          jsonPath(
            "$[0].eli",
            equalTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1")
          )
        )
        .andExpect(jsonPath("$[0].frbrDateVerkuendung", equalTo("2017-03-15")))
        .andExpect(jsonPath("$[0].dateAusfertigung", equalTo("1900-01-01")))
        .andExpect(jsonPath("$[0].importedAt", equalTo("2025-03-13T15:00:00Z")))
        .andExpect(jsonPath("$[1].title", equalTo("Gesetz zur Änderung des Lobbyregistergesetzes")))
        .andExpect(
          jsonPath("$[1].eli", equalTo("eli/bund/bgbl-1/2024/10/2024-01-18/1/deu/regelungstext-1"))
        )
        .andExpect(jsonPath("$[1].frbrDateVerkuendung", equalTo("2024-01-18")))
        .andExpect(jsonPath("$[1].dateAusfertigung", equalTo("2024-01-15")))
        .andExpect(jsonPath("$[1].importedAt", equalTo("2025-03-13T16:00:00Z")));
    }
  }

  @Nested
  class getAnnouncement {

    @Test
    void itReturnsAnnouncement() throws Exception {
      // Given
      var norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      var normEli = norm.getExpressionEli();
      var announcement = Announcement
        .builder()
        .eli(normEli)
        .importTimestamp(Instant.parse("2025-03-13T15:00:00Z"))
        .build();
      // When
      when(
        loadAnnouncementUseCase.loadAnnouncement(
          new LoadAnnouncementUseCase.Query(norm.getExpressionEli())
        )
      )
        .thenReturn(announcement);
      when(loadNormUseCase.loadNorm(any())).thenReturn(norm);

      // When
      mockMvc
        .perform(
          get("/api/v1/verkuendungen/{eli}", normEli.toString()).accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
        )
        .andExpect(jsonPath("title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts"))
        .andExpect(jsonPath("shortTitle").value("Vereinsgesetz"))
        .andExpect(jsonPath("fna").value("754-28-1"))
        .andExpect(jsonPath("frbrName").value("BGBl. I"))
        .andExpect(jsonPath("frbrNumber").value("s593"))
        .andExpect(jsonPath("frbrDateVerkuendung").value("1964-08-05"))
        .andExpect(jsonPath("dateAusfertigung").value("1964-08-05"))
        .andExpect(jsonPath("importedAt").value("2025-03-13T15:00:00Z"));

      verify(loadAnnouncementUseCase, times(1))
        .loadAnnouncement(argThat(argument -> Objects.equals(argument.eli(), normEli)));
      verify(loadNormUseCase, times(1))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), normEli)));
    }

    @Test
    void itReturns404() throws Exception {
      // Given
      var norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      var normEli = norm.getExpressionEli();
      // When
      when(loadAnnouncementUseCase.loadAnnouncement(new LoadAnnouncementUseCase.Query(normEli)))
        .thenThrow(new AnnouncementNotFoundException(normEli.toString()));

      // When
      mockMvc
        .perform(
          get("/api/v1/verkuendungen/{eli}", normEli.toString()).accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isNotFound());

      verify(loadAnnouncementUseCase, times(1))
        .loadAnnouncement(argThat(argument -> Objects.equals(argument.eli(), normEli)));
      verify(loadNormUseCase, times(0)).loadNorm(any());
    }
  }

  @Nested
  class getVerkuendungsZielnormen {

    @Test
    void itReturnsZielnormen() throws Exception {
      // Given
      var zielnorm1 = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
      );
      when(
        loadNormExpressionsAffectedByVerkuendungUseCase.loadNormExpressionsAffectedByVerkuendung(
          any()
        )
      )
        .thenReturn(List.of(zielnorm1));

      // When
      mockMvc
        .perform(
          get("/api/v1/verkuendungen/eli/bund/bgbl-1/2025/2/2025-01-05/1/deu/zielnormen")
            .accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(
          jsonPath("$[0].eli").value("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
        )
        .andExpect(
          jsonPath("$[0].title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts")
        )
        .andExpect(jsonPath("$[0].shortTitle").value("Vereinsgesetz"))
        .andExpect(jsonPath("$[0].fna").value("754-28-1"))
        .andExpect(jsonPath("$[0].status").value("status-not-yet-implemented"));

      verify(loadNormExpressionsAffectedByVerkuendungUseCase, times(1))
        .loadNormExpressionsAffectedByVerkuendung(
          argThat(argument ->
            Objects.equals(
              argument.eli(),
              NormExpressionEli.fromString("eli/bund/bgbl-1/2025/2/2025-01-05/1/deu")
            )
          )
        );
    }
  }

  @Nested
  class postAnnouncement {

    @Test
    void itCreatesAnAnnouncement() throws Exception {
      // Given
      var norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );
      var xmlContent = XmlMapper.toString(norm.getRegelungstext1().getDocument());
      final MockMultipartFile file = new MockMultipartFile(
        "file",
        "norm.txt",
        "text/plain",
        new ByteArrayInputStream(xmlContent.getBytes())
      );
      var announcement = Announcement
        .builder()
        .eli(norm.getExpressionEli())
        .importTimestamp(Instant.parse("2025-03-13T16:00:00Z"))
        .build();

      when(createAnnouncementUseCase.createAnnouncement(any())).thenReturn(announcement);
      when(loadNormUseCase.loadNorm(any())).thenReturn(norm);

      // When // Then
      mockMvc
        .perform(
          multipart("/api/v1/verkuendungen")
            .file(file)
            .accept(MediaType.APPLICATION_JSON)
            .with(csrf())
        )
        .andExpect(status().isOk())
        .andExpect(
          jsonPath("eli", equalTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"))
        )
        .andExpect(jsonPath("importedAt", equalTo("2025-03-13T16:00:00Z")));
    }

    @Test
    void itCreatesAnAnnouncementWithForce() throws Exception {
      // Given
      var norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );
      var xmlContent = XmlMapper.toString(norm.getRegelungstext1().getDocument());
      final MockMultipartFile file = new MockMultipartFile(
        "file",
        "norm.txt",
        "text/plain",
        new ByteArrayInputStream(xmlContent.getBytes())
      );
      var announcement = Announcement
        .builder()
        .eli(norm.getExpressionEli())
        .importTimestamp(Instant.parse("2025-03-13T16:00:00Z"))
        .build();

      when(createAnnouncementUseCase.createAnnouncement(any())).thenReturn(announcement);
      when(loadNormUseCase.loadNorm(any())).thenReturn(norm);

      // When // Then
      mockMvc
        .perform(
          multipart("/api/v1/verkuendungen?force=true")
            .file(file)
            .accept(MediaType.APPLICATION_JSON)
            .with(csrf())
        )
        .andExpect(status().isOk())
        .andExpect(
          jsonPath("eli", equalTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"))
        )
        .andExpect(jsonPath("importedAt", equalTo("2025-03-13T16:00:00Z")));
    }

    @Test
    void itShouldNotExposeInternalInformationOnUnexpectedErrors() throws Exception {
      // Given
      var norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );
      var xmlContent = XmlMapper.toString(norm.getRegelungstext1().getDocument());
      final MockMultipartFile file = new MockMultipartFile(
        "file",
        "norm.txt",
        "text/plain",
        new ByteArrayInputStream(xmlContent.getBytes())
      );
      var announcement = Announcement.builder().eli(norm.getExpressionEli()).build();

      when(createAnnouncementUseCase.createAnnouncement(any())).thenReturn(announcement);
      when(loadNormUseCase.loadNorm(any()))
        .thenThrow(new RuntimeException("Should not be visible in the output"));

      // When // Then
      mockMvc
        .perform(
          multipart("/api/v1/verkuendungen")
            .file(file)
            .accept(MediaType.APPLICATION_JSON)
            .with(csrf())
        )
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("type", equalTo("/errors/internal-server-error")))
        .andExpect(jsonPath("title", equalTo("Internal Server Error")))
        .andExpect(jsonPath("detail", equalTo("An unexpected error has occurred")));
    }
  }
}
