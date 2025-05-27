package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.DokumentDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.BinaryFileRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.Roles;
import de.bund.digitalservice.ris.norms.domain.entity.Zeitgrenze;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser(roles = { Roles.NORMS_USER })
class ZeitgrenzeControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DokumentRepository dokumentRepository;

  @Autowired
  private BinaryFileRepository binaryFileRepository;

  @Autowired
  private NormManifestationRepository normManifestationRepository;

  @AfterEach
  void cleanUp() {
    dokumentRepository.deleteAll();
    binaryFileRepository.deleteAll();
    normManifestationRepository.deleteAll();
  }

  @Nested
  class getZeitgrenzen {

    @Test
    void itCallsGetZeitgrenzenAndReturns404() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/thisEliDoesNotExist";

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms/{eli}/zeitgrenzen", eli).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/regelungstext-not-found"))
        .andExpect(jsonPath("title").value("Regelungstext not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail").value(
            "Regelungstext with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/thisEliDoesNotExist does not exist"
          )
        )
        .andExpect(
          jsonPath("instance").value(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/thisEliDoesNotExist/zeitgrenzen"
          )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/thisEliDoesNotExist")
        );
    }

    @Test
    void itCallsGetZeitgrenzeAndReturnsJson() throws Exception {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
      );

      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );
      dokumentRepository.save(DokumentMapper.mapToDto(regelungstext));

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms/{eli}/zeitgrenzen", eli).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", is("5e2f4f78-a0a1-4c55-9ef7-ad2821161915")))
        .andExpect(jsonPath("$[0].date", is("2017-03-16")))
        .andExpect(jsonPath("$[0].art", is("INKRAFT")))
        .andExpect(jsonPath("$[0].inUse", is(true)));
    }
  }

  @Nested
  class updateZeitgrenzen {

    @Test
    void itCallsUpdateZeitgrenzenAndReturns404() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/thisEliDoesNotExist";

      // When // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/zeitgrenzen", eli)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              "[{\"date\": \"2023-12-30\", \"art\": \"INKRAFT\"},{\"date\": \"2024-01-01\", \"art\": \"AUSSERKRAFT\"}]"
            )
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/regelungstext-not-found"))
        .andExpect(jsonPath("title").value("Regelungstext not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail").value(
            "Regelungstext with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/thisEliDoesNotExist does not exist"
          )
        )
        .andExpect(
          jsonPath("instance").value(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/thisEliDoesNotExist/zeitgrenzen"
          )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/thisEliDoesNotExist")
        );
    }

    @Test
    void itCallsUpdateZeitgrenzenDateNull() throws Exception {
      // When
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );

      // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/zeitgrenzen", eli)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("[{\"date\": null, \"art\": \"INKRAFT\"}]")
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("type").value("/errors/input-validation-error"))
        .andExpect(jsonPath("title").value("Input validation error"))
        .andExpect(jsonPath("status").value(400))
        .andExpect(jsonPath("detail").value("Date must not be null"))
        .andExpect(
          jsonPath("instance").value(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/zeitgrenzen"
          )
        );
    }

    @Test
    void itCallsUpdateZeitgrenzenArtNull() throws Exception {
      // When
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );

      // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/zeitgrenzen", eli)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("[{\"date\": \"2023-12-30\", \"art\": null}]")
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("type").value("/errors/input-validation-error"))
        .andExpect(jsonPath("title").value("Input validation error"))
        .andExpect(jsonPath("status").value(400))
        .andExpect(jsonPath("detail").value("Art must not be null"))
        .andExpect(
          jsonPath("instance").value(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/zeitgrenzen"
          )
        );
    }

    @Test
    void itCallsUpdateZeitgrenzenDateMalformed() throws Exception {
      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";

      // When
      // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/zeitgrenzen", eli)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("[{\"date\": \"THISISNODATE\", \"art\": \"INKRAFT\"}]")
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("type").value("/errors/http-message-not-readable-exception"))
        .andExpect(jsonPath("title").value("Bad Request"))
        .andExpect(jsonPath("status").value(400))
        .andExpect(
          jsonPath("instance").value(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/zeitgrenzen"
          )
        );
    }

    @Test
    void itCallsUpdateZeitgrenzenArtMalformed() throws Exception {
      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";

      // When
      // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/zeitgrenzen", eli)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("[{\"date\": \"2020-01-01\", \"art\": \"NONEXISTENT\"}]")
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("type").value("/errors/http-message-not-readable-exception"))
        .andExpect(jsonPath("title").value("Bad Request"))
        .andExpect(jsonPath("status").value(400))
        .andExpect(
          jsonPath("instance").value(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/zeitgrenzen"
          )
        );
    }

    @Test
    void itCallsUpdateZeitgrenzenMultipleSameDateArtCombinations() throws Exception {
      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";

      // When
      // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/zeitgrenzen", eli)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              "[" +
              "{\"date\": \"2023-12-30\", \"art\": \"INKRAFT\"}," +
              "{\"date\": \"2024-01-01\", \"art\": \"AUSSERKRAFT\"}," +
              "{\"date\": \"2024-01-01\", \"art\": \"AUSSERKRAFT\"}" +
              "]"
            )
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("type").value("/errors/input-validation-error"))
        .andExpect(jsonPath("title").value("Input validation error"))
        .andExpect(jsonPath("status").value(400))
        .andExpect(jsonPath("detail").value("Not all combinations of date + art are unique."))
        .andExpect(
          jsonPath("instance").value(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/zeitgrenzen"
          )
        );
    }

    @Test
    void itCallsUpdateZeitgrenzenWithMoreThanTheAllowedNumberOfItems() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";

      LocalDate currentDate = LocalDate.parse("2023-12-30");
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      StringBuilder payload = new StringBuilder();
      payload.append("[");
      for (int i = 0; i < 101; i++) {
        if (i > 0) payload.append(",");
        payload.append(
          "{\"date\": \"%s\", \"art\": \"INKRAFT\"}".formatted(currentDate.format(formatter))
        );
        currentDate = currentDate.plusDays(1);
      }
      payload.append("]");

      // When
      // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/zeitgrenzen", eli)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload.toString())
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("type").value("/errors/input-validation-error"))
        .andExpect(jsonPath("title").value("Input validation error"))
        .andExpect(jsonPath("status").value(400))
        .andExpect(jsonPath("detail").value("A maximum of 100 time boundaries is supported"))
        .andExpect(
          jsonPath("instance").value(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/zeitgrenzen"
          )
        );
    }

    @Test
    void itCallsUpdateZeitgrenzenAdd() throws Exception {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
      );

      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
      );
      assertThat(regelungstext.getZeitgrenzen())
        .hasSize(1)
        .extracting(Zeitgrenze::getId, Zeitgrenze::getDate, Zeitgrenze::getArt)
        .containsExactlyInAnyOrder(
          tuple(
            new Zeitgrenze.Id("5e2f4f78-a0a1-4c55-9ef7-ad2821161915"),
            LocalDate.parse("2017-03-16"),
            Zeitgrenze.Art.INKRAFT
          )
        );
      dokumentRepository.save(DokumentMapper.mapToDto(regelungstext));

      // When
      // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/zeitgrenzen", eli)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              "[" +
              "{\"date\": \"2017-03-16\", \"art\": \"INKRAFT\"}," +
              "{\"date\": \"2025-01-01\", \"art\": \"INKRAFT\"}," +
              "{\"date\": \"2024-01-01\", \"art\": \"AUSSERKRAFT\"}" +
              "]"
            )
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].id", is("5e2f4f78-a0a1-4c55-9ef7-ad2821161915")))
        .andExpect(jsonPath("$[0].date", is("2017-03-16")))
        .andExpect(jsonPath("$[0].art", is("INKRAFT")))
        .andExpect(jsonPath("$[1].date", is("2024-01-01")))
        .andExpect(jsonPath("$[1].art", is("AUSSERKRAFT")))
        .andExpect(jsonPath("$[2].date", is("2025-01-01")))
        .andExpect(jsonPath("$[2].art", is("INKRAFT")));

      final Optional<DokumentDto> loadedFromDb =
        dokumentRepository.findFirstByEliDokumentExpressionOrderByEliDokumentManifestationDesc(
          eli.toString()
        );
      assertThat(loadedFromDb).isPresent();

      final List<Zeitgrenze> zeitgrenzen = DokumentMapper.mapToDomain(
        loadedFromDb.get()
      ).getZeitgrenzen();
      assertThat(zeitgrenzen).hasSize(3);
      assertThat(zeitgrenzen.getFirst().getDate()).isEqualTo(LocalDate.parse("2017-03-16"));
      assertThat(zeitgrenzen.getFirst().getArt()).isEqualTo(Zeitgrenze.Art.INKRAFT);
      assertThat(zeitgrenzen.getFirst().getId()).isEqualTo(
        new Zeitgrenze.Id("5e2f4f78-a0a1-4c55-9ef7-ad2821161915")
      );
      assertThat(zeitgrenzen.get(1).getDate()).isEqualTo(LocalDate.parse("2024-01-01"));
      assertThat(zeitgrenzen.get(1).getArt()).isEqualTo(Zeitgrenze.Art.AUSSERKRAFT);
      assertThat(zeitgrenzen.get(2).getDate()).isEqualTo(LocalDate.parse("2025-01-01"));
      assertThat(zeitgrenzen.get(2).getArt()).isEqualTo(Zeitgrenze.Art.INKRAFT);
    }

    @Test
    void itCallsUpdateZeitgrenzenWithEmptyList() throws Exception {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
      );

      var norm = Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        ZeitgrenzeControllerIntegrationTest.class,
        "norm-with-only-unused-geltungszeit",
        NormPublishState.UNPUBLISHED
      );
      assertThat(norm.getRegelungstext1().getZeitgrenzen())
        .hasSize(1)
        .extracting(Zeitgrenze::getId, Zeitgrenze::getDate, Zeitgrenze::getArt)
        .containsExactlyInAnyOrder(
          tuple(new Zeitgrenze.Id("gz-1"), LocalDate.parse("2017-03-16"), Zeitgrenze.Art.INKRAFT)
        );

      // When
      // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/zeitgrenzen", eli)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("[]")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));

      final Optional<DokumentDto> loadedFromDb =
        dokumentRepository.findFirstByEliDokumentExpressionOrderByEliDokumentManifestationDesc(
          eli.toString()
        );
      assertThat(loadedFromDb).isPresent();
      final Dokument dokument = DokumentMapper.mapToDomain(loadedFromDb.get());
      assertThat(dokument.getZeitgrenzen()).isEmpty();
    }

    @Test
    void itCallsUpdateZeitgrenzenOnXMLWithoutModsMetadata() throws Exception {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
      );

      var norm = Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        ZeitgrenzeControllerIntegrationTest.class,
        "norm-without-mods-metadata",
        NormPublishState.UNPUBLISHED
      );
      assertThat(norm.getRegelungstext1().getZeitgrenzen()).isEmpty();

      // When
      // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/zeitgrenzen", eli)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("[{\"date\": \"2023-12-30\", \"art\": \"AUSSERKRAFT\"}]")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", isA(String.class)))
        .andExpect(jsonPath("$[0].date", is("2023-12-30")))
        .andExpect(jsonPath("$[0].art", is("AUSSERKRAFT")));

      final Optional<DokumentDto> loadedFromDb =
        dokumentRepository.findFirstByEliDokumentExpressionOrderByEliDokumentManifestationDesc(
          eli.toString()
        );
      assertThat(loadedFromDb).isPresent();

      final List<Zeitgrenze> zeitgrenzen = DokumentMapper.mapToDomain(
        loadedFromDb.get()
      ).getZeitgrenzen();
      assertThat(zeitgrenzen).hasSize(1);
      assertThat(zeitgrenzen.getFirst().getDate()).isEqualTo(LocalDate.parse("2023-12-30"));
      assertThat(zeitgrenzen.getFirst().getArt()).isEqualTo(Zeitgrenze.Art.AUSSERKRAFT);
    }

    @Test
    void itCallsUpdateZeitgrenzenOnXMLWithoutRisMetadata() throws Exception {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
      );

      var norm = Fixtures.loadAndSaveNormFixture(
        dokumentRepository,
        binaryFileRepository,
        normManifestationRepository,
        ZeitgrenzeControllerIntegrationTest.class,
        "norm-without-ris-metadata",
        NormPublishState.UNPUBLISHED
      );
      assertThat(norm.getRegelungstext1().getZeitgrenzen()).isEmpty();

      // When
      // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/zeitgrenzen", eli)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("[{\"date\": \"2023-12-30\", \"art\": \"AUSSERKRAFT\"}]")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].date", is("2023-12-30")))
        .andExpect(jsonPath("$[0].art", is("AUSSERKRAFT")));

      final Optional<DokumentDto> loadedFromDb =
        dokumentRepository.findFirstByEliDokumentExpressionOrderByEliDokumentManifestationDesc(
          eli.toString()
        );
      assertThat(loadedFromDb).isPresent();
      final Dokument dokument = DokumentMapper.mapToDomain(loadedFromDb.get());
      assertThat(dokument.getZeitgrenzen())
        .hasSize(1)
        .extracting(Zeitgrenze::getDate, Zeitgrenze::getArt)
        .containsExactlyInAnyOrder(
          tuple(LocalDate.parse("2023-12-30"), Zeitgrenze.Art.AUSSERKRAFT)
        );
    }
  }
}
