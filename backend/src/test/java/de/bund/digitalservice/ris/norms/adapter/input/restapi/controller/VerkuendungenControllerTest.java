package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.VerkuendungNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
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

@SecurelessControllerTest(VerkuendungenController.class)
class VerkuendungenControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private LoadAllVerkuendungenUseCase loadAllVerkuendungenUseCase;

  @MockitoBean
  private LoadVerkuendungUseCase loadVerkuendungUseCase;

  @MockitoBean
  private LoadNormUseCase loadNormUseCase;

  @MockitoBean
  private CreateVerkuendungUseCase createVerkuendungUseCase;

  @MockitoBean
  private LoadNormExpressionsAffectedByVerkuendungUseCase loadNormExpressionsAffectedByVerkuendungUseCase;

  @MockitoBean
  private LoadZielnormenExpressionsUseCase loadZielnormenExpressionsUseCase;

  @MockitoBean
  private CreateZielnormenExpressionsUseCase createZielnormenExpressionsUseCase;

  @Nested
  class getAllVerkuendungen {

    @Test
    void itReturnsVerkuendungen() throws Exception {
      // Given
      var norm1 = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23"
      );
      var verkuendung1 = Verkuendung.builder()
        .eli(norm1.getExpressionEli())
        .importTimestamp(Instant.parse("2025-03-13T15:00:00Z"))
        .build();

      var norm2 = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/2024/10/2024-01-18/1/deu/2024-01-18");
      var verkuendung2 = Verkuendung.builder()
        .eli(norm2.getExpressionEli())
        .importTimestamp(Instant.parse("2025-03-13T16:00:00Z"))
        .build();

      // When
      when(loadAllVerkuendungenUseCase.loadAllVerkuendungen()).thenReturn(
        List.of(verkuendung1, verkuendung2)
      );
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

    @Test
    void itThrowsVerkuendungWithoutNorm() throws Exception {
      // Given
      var norm1 = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23"
      );
      var verkuendung1 = Verkuendung.builder()
        .eli(norm1.getExpressionEli())
        .importTimestamp(Instant.parse("2025-03-13T15:00:00Z"))
        .build();

      var norm2 = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/2024/10/2024-01-18/1/deu/2024-01-18");
      var verkuendung2 = Verkuendung.builder()
        .eli(norm2.getExpressionEli())
        .importTimestamp(Instant.parse("2025-03-13T16:00:00Z"))
        .build();

      // When
      when(loadAllVerkuendungenUseCase.loadAllVerkuendungen()).thenReturn(
        List.of(verkuendung1, verkuendung2)
      );
      when(loadNormUseCase.loadNorm(any()))
        .thenReturn(norm1)
        .thenThrow(new NormNotFoundException(verkuendung2.getEli()));

      // When // Then
      mockMvc
        .perform(get("/api/v1/verkuendungen").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity());
    }
  }

  @Nested
  class getVerkuendung {

    @Test
    void itReturnsVerkuendung() throws Exception {
      // Given
      var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
      var normEli = norm.getExpressionEli();
      var verkuendung = Verkuendung.builder()
        .eli(normEli)
        .importTimestamp(Instant.parse("2025-03-13T15:00:00Z"))
        .build();
      // When
      when(
        loadVerkuendungUseCase.loadVerkuendung(
          new LoadVerkuendungUseCase.Options(norm.getExpressionEli())
        )
      ).thenReturn(verkuendung);
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

      verify(loadVerkuendungUseCase, times(1)).loadVerkuendung(
        argThat(argument -> Objects.equals(argument.eli(), normEli))
      );
      verify(loadNormUseCase, times(1)).loadNorm(new LoadNormUseCase.EliOptions(normEli));
    }

    @Test
    void itReturns404() throws Exception {
      // Given
      var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");
      var normEli = norm.getExpressionEli();
      // When
      when(
        loadVerkuendungUseCase.loadVerkuendung(new LoadVerkuendungUseCase.Options(normEli))
      ).thenThrow(new VerkuendungNotFoundException(normEli.toString()));

      // When
      mockMvc
        .perform(
          get("/api/v1/verkuendungen/{eli}", normEli.toString()).accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isNotFound());

      verify(loadVerkuendungUseCase, times(1)).loadVerkuendung(
        argThat(argument -> Objects.equals(argument.eli(), normEli))
      );
      verify(loadNormUseCase, times(0)).loadNorm(any());
    }
  }

  @Nested
  class getVerkuendungsZielnormen {

    @Test
    void itReturnsZielnormen() throws Exception {
      // Given
      var zielnorm1 = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
      );
      when(
        loadNormExpressionsAffectedByVerkuendungUseCase.loadNormExpressionsAffectedByVerkuendung(
          any()
        )
      ).thenReturn(List.of(zielnorm1));

      // When
      mockMvc
        .perform(
          get("/api/v1/verkuendungen/eli/bund/bgbl-1/2025/2/2025-01-05/1/deu/zielnormen").accept(
            MediaType.APPLICATION_JSON
          )
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

      verify(
        loadNormExpressionsAffectedByVerkuendungUseCase,
        times(1)
      ).loadNormExpressionsAffectedByVerkuendung(
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
  class postVerkuendung {

    @Test
    void itCreatesAnVerkuendung() throws Exception {
      // Given
      var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23");
      var xmlContent = XmlMapper.toString(norm.getRegelungstext1().getDocument());
      final MockMultipartFile file = new MockMultipartFile(
        "file",
        "norm.txt",
        "text/plain",
        new ByteArrayInputStream(xmlContent.getBytes())
      );
      var verkuendung = Verkuendung.builder()
        .eli(norm.getExpressionEli())
        .importTimestamp(Instant.parse("2025-03-13T16:00:00Z"))
        .build();

      when(createVerkuendungUseCase.createVerkuendung(any())).thenReturn(verkuendung);
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
    void itCreatesAnVerkuendungWithForce() throws Exception {
      // Given
      var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23");
      var xmlContent = XmlMapper.toString(norm.getRegelungstext1().getDocument());
      final MockMultipartFile file = new MockMultipartFile(
        "file",
        "norm.txt",
        "text/plain",
        new ByteArrayInputStream(xmlContent.getBytes())
      );
      var verkuendung = Verkuendung.builder()
        .eli(norm.getExpressionEli())
        .importTimestamp(Instant.parse("2025-03-13T16:00:00Z"))
        .build();

      when(createVerkuendungUseCase.createVerkuendung(any())).thenReturn(verkuendung);
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
      var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23");
      var xmlContent = XmlMapper.toString(norm.getRegelungstext1().getDocument());
      final MockMultipartFile file = new MockMultipartFile(
        "file",
        "norm.txt",
        "text/plain",
        new ByteArrayInputStream(xmlContent.getBytes())
      );
      var verkuendung = Verkuendung.builder().eli(norm.getExpressionEli()).build();

      when(createVerkuendungUseCase.createVerkuendung(any())).thenReturn(verkuendung);
      when(loadNormUseCase.loadNorm(any())).thenThrow(
        new RuntimeException("Should not be visible in the output")
      );

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

  @Nested
  class getZielnormenPreview {

    @Test
    void itShouldReturnZielnormenPreview() throws Exception {
      // Given
      when(loadZielnormenExpressionsUseCase.loadZielnormExpressions(any())).thenReturn(
        List.of(
          new Zielnorm(
            NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"),
            "Gesetz zur Regelung des öffentlichen Vereinsrechts",
            "Vereinsgesetz",
            List.of(
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
            )
          )
        )
      );

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen/expressions/preview"
          ).accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].normWorkEli").value("eli/bund/bgbl-1/1964/s593"))
        .andExpect(
          jsonPath("$[0].title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts")
        )
        .andExpect(jsonPath("$[0].shortTitle").value("Vereinsgesetz"))
        .andExpect(
          jsonPath("$[0].expressions[0].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu"
          )
        )
        .andExpect(jsonPath("$[0].expressions[0].isGegenstandslos").value(true))
        .andExpect(jsonPath("$[0].expressions[0].isCreated").value(true))
        .andExpect(jsonPath("$[0].expressions[0].createdBy").value("andere Verkündung"))
        .andExpect(
          jsonPath("$[0].expressions[1].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2017-03-16/2/deu"
          )
        )
        .andExpect(jsonPath("$[0].expressions[1].isGegenstandslos").value(false))
        .andExpect(jsonPath("$[0].expressions[1].isCreated").value(false))
        .andExpect(jsonPath("$[0].expressions[1].createdBy").value("diese Verkündung"))
        .andExpect(
          jsonPath("$[0].expressions[2].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2017-04-16/2/deu"
          )
        )
        .andExpect(jsonPath("$[0].expressions[2].isGegenstandslos").value(true))
        .andExpect(jsonPath("$[0].expressions[2].isCreated").value(true))
        .andExpect(jsonPath("$[0].expressions[2].createdBy").value("andere Verkündung"))
        .andExpect(
          jsonPath("$[0].expressions[3].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2017-04-16/3/deu"
          )
        )
        .andExpect(jsonPath("$[0].expressions[3].isGegenstandslos").value(false))
        .andExpect(jsonPath("$[0].expressions[3].isCreated").value(false))
        .andExpect(jsonPath("$[0].expressions[3].createdBy").value("System"))
        .andExpect(jsonPath("$[0].expressions[4]").doesNotExist())
        .andExpect(jsonPath("$[1]").doesNotExist());

      verify(loadZielnormenExpressionsUseCase, times(1)).loadZielnormExpressions(
        new LoadZielnormenExpressionsUseCase.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu")
        )
      );
    }
  }

  @Nested
  class loadZielnormenExpressions {

    @Test
    void itShouldReturnZielnormenPreview() throws Exception {
      // Given
      when(loadZielnormenExpressionsUseCase.loadZielnormExpressions(any())).thenReturn(
        List.of(
          new Zielnorm(
            NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"),
            "Gesetz zur Regelung des öffentlichen Vereinsrechts",
            "Vereinsgesetz",
            List.of(
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
            )
          )
        )
      );

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen/expressions/preview"
          ).accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].normWorkEli").value("eli/bund/bgbl-1/1964/s593"))
        .andExpect(
          jsonPath("$[0].title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts")
        )
        .andExpect(jsonPath("$[0].shortTitle").value("Vereinsgesetz"))
        .andExpect(
          jsonPath("$[0].expressions[0].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu"
          )
        )
        .andExpect(jsonPath("$[0].expressions[0].isGegenstandslos").value(true))
        .andExpect(jsonPath("$[0].expressions[0].isCreated").value(true))
        .andExpect(jsonPath("$[0].expressions[0].createdBy").value("andere Verkündung"))
        .andExpect(
          jsonPath("$[0].expressions[1].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2017-03-16/2/deu"
          )
        )
        .andExpect(jsonPath("$[0].expressions[1].isGegenstandslos").value(false))
        .andExpect(jsonPath("$[0].expressions[1].isCreated").value(false))
        .andExpect(jsonPath("$[0].expressions[1].createdBy").value("diese Verkündung"))
        .andExpect(
          jsonPath("$[0].expressions[2].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2017-04-16/2/deu"
          )
        )
        .andExpect(jsonPath("$[0].expressions[2].isGegenstandslos").value(true))
        .andExpect(jsonPath("$[0].expressions[2].isCreated").value(true))
        .andExpect(jsonPath("$[0].expressions[2].createdBy").value("andere Verkündung"))
        .andExpect(
          jsonPath("$[0].expressions[3].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2017-04-16/3/deu"
          )
        )
        .andExpect(jsonPath("$[0].expressions[3].isGegenstandslos").value(false))
        .andExpect(jsonPath("$[0].expressions[3].isCreated").value(false))
        .andExpect(jsonPath("$[0].expressions[3].createdBy").value("System"))
        .andExpect(jsonPath("$[0].expressions[4]").doesNotExist())
        .andExpect(jsonPath("$[1]").doesNotExist());

      verify(loadZielnormenExpressionsUseCase, times(1)).loadZielnormExpressions(
        new LoadZielnormenExpressionsUseCase.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu")
        )
      );
    }
  }

  @Nested
  class createZielnormenExpressions {

    @Test
    void itShouldReturnCreatedZielnormen() throws Exception {
      // Given
      when(createZielnormenExpressionsUseCase.createZielnormExpressions(any())).thenReturn(
        new Zielnorm(
          NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"),
          "Gesetz zur Regelung des öffentlichen Vereinsrechts",
          "Vereinsgesetz",
          List.of(
            new Zielnorm.Expression(
              NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu"),
              true,
              true,
              Zielnorm.CreatedBy.OTHER_VERKUENDUNG
            ),
            new Zielnorm.Expression(
              NormExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/2017-03-16/2/deu"),
              false,
              true,
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
              true,
              Zielnorm.CreatedBy.SYSTEM
            )
          )
        )
      );

      // When
      mockMvc
        .perform(
          post(
            "/api/v1/verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen/eli/bund/bgbl-1/1964/s593/expressions/create"
          ).accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("normWorkEli").value("eli/bund/bgbl-1/1964/s593"))
        .andExpect(jsonPath("title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts"))
        .andExpect(jsonPath("shortTitle").value("Vereinsgesetz"))
        .andExpect(
          jsonPath("expressions[0].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu"
          )
        )
        .andExpect(jsonPath("expressions[0].isGegenstandslos").value(true))
        .andExpect(jsonPath("expressions[0].isCreated").value(true))
        .andExpect(jsonPath("expressions[0].createdBy").value("andere Verkündung"))
        .andExpect(
          jsonPath("expressions[1].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2017-03-16/2/deu"
          )
        )
        .andExpect(jsonPath("expressions[1].isGegenstandslos").value(false))
        .andExpect(jsonPath("expressions[1].isCreated").value(true))
        .andExpect(jsonPath("expressions[1].createdBy").value("diese Verkündung"))
        .andExpect(
          jsonPath("expressions[2].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2017-04-16/2/deu"
          )
        )
        .andExpect(jsonPath("expressions[2].isGegenstandslos").value(true))
        .andExpect(jsonPath("expressions[2].isCreated").value(true))
        .andExpect(jsonPath("expressions[2].createdBy").value("andere Verkündung"))
        .andExpect(
          jsonPath("expressions[3].normExpressionEli").value(
            "eli/bund/bgbl-1/1964/s593/2017-04-16/3/deu"
          )
        )
        .andExpect(jsonPath("expressions[3].isGegenstandslos").value(false))
        .andExpect(jsonPath("expressions[3].isCreated").value(true))
        .andExpect(jsonPath("expressions[3].createdBy").value("System"))
        .andExpect(jsonPath("expressions[4]").doesNotExist());

      verify(createZielnormenExpressionsUseCase, times(1)).createZielnormExpressions(
        new CreateZielnormenExpressionsUseCase.Options(
          NormExpressionEli.fromString("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu"),
          NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593")
        )
      );
    }
  }
}
