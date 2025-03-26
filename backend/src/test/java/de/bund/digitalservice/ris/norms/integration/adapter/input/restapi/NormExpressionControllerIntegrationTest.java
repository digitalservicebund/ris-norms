package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static de.bund.digitalservice.ris.norms.XPathMatcher.hasXPath;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Roles;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser(roles = { Roles.NORMS_USER })
class NormExpressionControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DokumentRepository dokumentRepository;

  @Autowired
  private NormManifestationRepository normManifestationRepository;

  @AfterEach
  void cleanUp() {
    dokumentRepository.deleteAll();
    normManifestationRepository.deleteAll();
  }

  @Nested
  class GetNormByEli {

    // Note: Error case missing

    @Test
    void itCallsNormsServiceAndReturnsNorm() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
        )
        .andExpect(jsonPath("title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts"))
        .andExpect(jsonPath("frbrName").value("BGBl. I"))
        .andExpect(jsonPath("frbrNumber").value("s593"))
        .andExpect(jsonPath("frbrDateVerkuendung").value("1964-08-05"));
    }

    @Test
    void itCallsNormsServiceAndReturnsNormXml() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
            .accept(MediaType.APPLICATION_XML)
        )
        .andExpect(status().isOk())
        .andExpect(
          xpath("//*[@eId='einleitung-1_doktitel-1_text-1_doctitel-1']")
            .string("Gesetz zur Regelung des öffentlichen Vereinsrechts")
        );
    }

    @Test
    void itCallsNormServiceAndReturnsNormRender() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
          )
        )
      );

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1")
            .accept(MediaType.TEXT_HTML)
        )
        .andExpect(status().isOk())
        .andExpect(
          content()
            .node(
              hasXPath(
                "//h1//*[@data-eId=\"einleitung-1_doktitel-1_text-1_doctitel-1\"]",
                equalTo("Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes")
              )
            )
        );
    }
  }

  @Nested
  class GetNormRenderWithQueryParameters {

    // Note: Error case missing

    @Test
    void itCallsNormServiceAndReturnsNormRenderWithMetadata() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
          )
        )
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1?showMetadata=true"
          )
            .accept(MediaType.TEXT_HTML)
        )
        .andExpect(status().isOk())
        .andExpect(
          content()
            .node(
              hasXPath(
                "//h1//*[@data-eId=\"einleitung-1_doktitel-1_text-1_doctitel-1\"]",
                equalTo("Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes")
              )
            )
        )
        .andExpect(
          content()
            .node(
              hasXPath(
                "//section[@class=\"metadata\"]//td[text()=\"Amtliche Langüberschrift\"]/following-sibling::td/text()",
                equalTo("Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes")
              )
            )
        );
    }
  }

  @Nested
  class PutNormByEli {

    @Test
    void itReturnsNotFound() throws Exception {
      // Given no norm in database
      final String eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1";
      final String xml =
        "<akn:doc xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.7.2/\">new</akn:doc>";

      // When
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}", eli)
            .accept(MediaType.APPLICATION_XML)
            .contentType(MediaType.APPLICATION_XML)
            .content(xml)
        )
        // Then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value("Norm with eli eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu does not exist")
        )
        .andExpect(
          jsonPath("instance")
            .value("/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1")
        )
        .andExpect(jsonPath("eli").value("eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu"));
    }

    @Test
    void itThrowsInvalidUpdateExceptionBecauseEliChanged() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );
      var newXml = Fixtures.loadTextFromDisk(
        "eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/2017-03-15/regelungstext-1.xml"
      );

      // When // Then
      mockMvc
        .perform(
          put("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
            .accept(MediaType.APPLICATION_XML)
            .contentType(MediaType.APPLICATION_XML)
            .content(newXml)
        )
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type").value("/errors/invalidate-update"))
        .andExpect(jsonPath("title").value("Invalid update operation"))
        .andExpect(jsonPath("status").value(422))
        .andExpect(jsonPath("detail").value("Changing the ELI is not supported."))
        .andExpect(
          jsonPath("instance")
            .value("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
        );
    }

    @Test
    void itThrowsInvalidUpdateExceptionBecauseGuidChanged() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );
      var newXml = Fixtures.loadTextFromDisk(
        NormExpressionControllerIntegrationTest.class,
        "vereinsgesetz-with-different-guid.xml"
      );

      // When // Then
      mockMvc
        .perform(
          put("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
            .accept(MediaType.APPLICATION_XML)
            .contentType(MediaType.APPLICATION_XML)
            .content(newXml)
        )
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type").value("/errors/invalidate-update"))
        .andExpect(jsonPath("title").value("Invalid update operation"))
        .andExpect(jsonPath("status").value(422))
        .andExpect(jsonPath("detail").value("Changing the GUID is not supported."))
        .andExpect(
          jsonPath("instance")
            .value("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
        );
    }

    @Test
    void itCallsNormServiceAndUpdatesNorm() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
          )
        )
      );
      var newXml = Fixtures.loadTextFromDisk(
        NormExpressionControllerIntegrationTest.class,
        "vereinsgesetz-with-different-title.xml"
      );

      // When // Then
      mockMvc
        .perform(
          put("/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
            .accept(MediaType.APPLICATION_XML)
            .contentType(MediaType.APPLICATION_XML)
            .content(newXml)
        )
        .andExpect(status().isOk())
        .andExpect(
          content()
            .node(
              hasXPath(
                "//*[@eId=\"einleitung-1_doktitel-1_text-1_doctitel-1\"]",
                equalTo("Neuer Title")
              )
            )
        );
    }
  }

  @Nested
  class GetNormTimeBoundaries {

    // Note: Error case missing

    @Test
    void itExtractsAndReturnsTimeBoundariesFromNorm() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
          )
        )
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/timeBoundaries"
          )
            .accept(MediaType.APPLICATION_JSON_VALUE)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].date", is("2017-03-16")));
    }

    @Test
    void itExtractsAndReturnsTimeBoundariesFromNormWhenDateIsEmpty() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            NormExpressionControllerIntegrationTest.class,
            "vereinsgesetz-with-empty-inkrafttreten-date.xml"
          )
        )
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/timeBoundaries"
          )
            .accept(MediaType.APPLICATION_JSON_VALUE)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].date", is(nullValue())));
    }
  }
}
