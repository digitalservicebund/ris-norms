package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bund.digitalservice.ris.norms.application.port.input.LoadNormExpressionsWorkingCopiesUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadReleasesByNormExpressionEliUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.ReleaseAllNormExpressionsUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Release;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SecurelessControllerTest(ReleaseController.class)
class ReleaseControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private LoadReleasesByNormExpressionEliUseCase loadReleasesByNormExpressionEliUseCase;

  @MockitoBean
  private ReleaseAllNormExpressionsUseCase releaseAllNormExpressionsUseCase;

  @MockitoBean
  private LoadNormExpressionsWorkingCopiesUseCase loadNormExpressionsWorkingCopiesUseCase;

  @MockitoBean
  private LoadNormUseCase loadNormUseCase;

  @Nested
  class getRelease {

    @Test
    void itReturnsRelease() throws Exception {
      // Given
      var norm1 = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23"
      );
      var norm2 = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
      );
      List<Release> releases = List.of(
        Release.builder()
          .releasedAt(Instant.parse("2024-01-02T10:20:30.0Z"))
          .publishedNorms(List.of(norm1, norm2))
          .build()
      );

      when(
        loadReleasesByNormExpressionEliUseCase.loadReleasesByNormExpressionEli(any())
      ).thenReturn(releases);

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/releases").accept(
            MediaType.APPLICATION_JSON
          )
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("[0].releaseAt", equalTo("2024-01-02T10:20:30Z")))
        .andExpect(jsonPath("[0].norms[0]").exists())
        .andExpect(jsonPath("[0].norms[2]").doesNotExist())
        .andExpect(
          jsonPath(
            "[0].norms[0]",
            equalTo(
              "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-verkuendung-1.xml"
            )
          )
        )
        .andExpect(
          jsonPath(
            "[0].norms[1]",
            equalTo(
              "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-verkuendung-1.xml"
            )
          )
        );
    }
  }

  @Nested
  class postRelease {

    @Test
    void itReleaseANormExpression() throws Exception {
      // Given
      var norm1 = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23"
      );
      var norm2 = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05"
      );
      var release = Release.builder()
        .releasedAt(Instant.parse("2024-01-02T10:20:30.0Z"))
        .publishedNorms(List.of(norm1, norm2))
        .build();

      when(releaseAllNormExpressionsUseCase.release(any())).thenReturn(release);

      // When // Then
      mockMvc
        .perform(
          post("/api/v1/eli/bund/bgbl-1/2023/413/releases")
            .content(
              """
              {"releasetype": "praetext"}
              """
            )
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .with(csrf())
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("normWorkEli", equalTo("eli/bund/bgbl-1/2017/s419")))
        .andExpect(
          jsonPath(
            "title",
            equalTo("Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes")
          )
        )
        .andExpect(jsonPath("shortTitle", equalTo("")))
        .andExpect(jsonPath("expressions", hasSize(2)))
        .andExpect(
          jsonPath(
            "expressions[0].normExpressionEli",
            equalTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu")
          )
        )
        .andExpect(jsonPath("expressions[0].isGegenstandslos", equalTo(false)))
        .andExpect(jsonPath("expressions[0].currentStatus", equalTo("NOT_RELEASED")))
        .andExpect(
          jsonPath(
            "expressions[1].normExpressionEli",
            equalTo("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu")
          )
        )
        .andExpect(jsonPath("expressions[1].isGegenstandslos", equalTo(false)))
        .andExpect(jsonPath("expressions[1].currentStatus", equalTo("PRAETEXT_RELEASED")));
    }
  }
}
