package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Roles;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
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

  @AfterEach
  void cleanUp() {
    dokumentRepository.deleteAll();
  }

  @Nested
  class getZeitgrenzen {

    @Test
    void itCallsGetTimeBoundariesAndReturns404() throws Exception {
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
          jsonPath("detail")
            .value(
              "Regelungstext with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/thisEliDoesNotExist does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
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
        .andExpect(jsonPath("$[0].id", is("gz-1")))
        .andExpect(jsonPath("$[0].date", is("2017-03-16")))
        .andExpect(jsonPath("$[0].art", is("INKRAFT")));
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
          jsonPath("detail")
            .value(
              "Regelungstext with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/thisEliDoesNotExist does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
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
          jsonPath("instance")
            .value(
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
          jsonPath("instance")
            .value(
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
          jsonPath("instance")
            .value(
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
          jsonPath("instance")
            .value(
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
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/zeitgrenzen"
            )
        );
    }

    @Test
    void itCallsUpdateTimeBoundariesAddAndDeleteAndEdit() throws Exception {
      // Given
      var eli = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
      );

      var regelungstext = Fixtures.loadRegelungstextFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
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
              "{\"date\": \"2023-12-30\", \"art\": \"AUSSERKRAFT\"}," +
              "{\"date\": \"2025-01-01\", \"art\": \"INKRAFT\"}," +
              "{\"date\": \"2024-01-01\", \"art\": \"AUSSERKRAFT\"}" +
              "]"
            )
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].id", is("gz-1")))
        .andExpect(jsonPath("$[0].date", is("2023-12-30")))
        .andExpect(jsonPath("$[0].art", is("AUSSERKRAFT")))
        .andExpect(jsonPath("$[1].id", is("gz-2")))
        .andExpect(jsonPath("$[1].date", is("2024-01-01")))
        .andExpect(jsonPath("$[1].art", is("AUSSERKRAFT")))
        .andExpect(jsonPath("$[2].id", is("gz-3")))
        .andExpect(jsonPath("$[2].date", is("2025-01-01")))
        .andExpect(jsonPath("$[2].art", is("INKRAFT")));
    }
  }
}
