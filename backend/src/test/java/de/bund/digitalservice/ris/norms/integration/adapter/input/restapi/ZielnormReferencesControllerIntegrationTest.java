package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.hamcrest.Matchers.equalTo;
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
class ZielnormReferencesControllerIntegrationTest extends BaseIntegrationTest {

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
  class getReferences {

    @Test
    void itReturnsListOfZielnormReferences() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-verkuendung-1.xml"
          )
        )
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnorm-references"
          ).accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").exists())
        .andExpect(jsonPath("$[0].typ").value(equalTo("Änderungsvorschrift")))
        .andExpect(
          jsonPath("$[0].geltungszeit").value(equalTo("5e2f4f78-a0a1-4c55-9ef7-ad2821161915"))
        )
        .andExpect(
          jsonPath("$[0].eId").value(equalTo("hauptteil-1_art-1_abs-1_untergl-1_listenelem-1"))
        )
        .andExpect(jsonPath("$[0].zielnorm").value(equalTo("eli/bund/bgbl-1/1964/s593")));
    }
  }

  @Nested
  class updateReferences {

    @Test
    void itAddsAndUpdatesReferences() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-verkuendung-1.xml"
          )
        )
      );

      // When // Then
      mockMvc
        .perform(
          post("/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnorm-references")
            .content(
              """
              [
                {
                  "eId": "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
                  "zielnorm": "eli/bund/bgbl-1/2022/1",
                  "geltungszeit": "gz-2",
                  "typ": "Änderungsvorschrift"
                },
                {
                  "eId": "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2",
                  "zielnorm": "eli/bund/bgbl-1/2023/12",
                  "geltungszeit": "gz-1",
                  "typ": "Änderungsvorschrift"
                }
              ]
              """
            )
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").exists())
        .andExpect(jsonPath("$[0].typ").value(equalTo("Änderungsvorschrift")))
        .andExpect(jsonPath("$[0].geltungszeit").value(equalTo("gz-2")))
        .andExpect(
          jsonPath("$[0].eId").value(equalTo("hauptteil-1_art-1_abs-1_untergl-1_listenelem-1"))
        )
        .andExpect(jsonPath("$[0].zielnorm").value(equalTo("eli/bund/bgbl-1/2022/1")))
        .andExpect(jsonPath("$[1]").exists())
        .andExpect(jsonPath("$[1].typ").value(equalTo("Änderungsvorschrift")))
        .andExpect(jsonPath("$[1].geltungszeit").value(equalTo("gz-1")))
        .andExpect(
          jsonPath("$[1].eId").value(equalTo("hauptteil-1_art-1_abs-1_untergl-1_listenelem-2"))
        )
        .andExpect(jsonPath("$[1].zielnorm").value(equalTo("eli/bund/bgbl-1/2023/12")));
    }

    @Test
    void itAddsAReferenceWithoutChangingAnExitingOne() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-verkuendung-1.xml"
          )
        )
      );

      // When // Then
      mockMvc
        .perform(
          post("/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnorm-references")
            .content(
              """
              [
                {
                  "eId": "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2",
                  "zielnorm": "eli/bund/bgbl-1/2023/12",
                  "geltungszeit": "07fdc138-1509-4165-9ec7-f26f9d5c8cb8",
                  "typ": "Änderungsvorschrift"
                }
              ]
              """
            )
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").exists())
        .andExpect(jsonPath("$[0].typ").value(equalTo("Änderungsvorschrift")))
        .andExpect(
          jsonPath("$[0].geltungszeit").value(equalTo("5e2f4f78-a0a1-4c55-9ef7-ad2821161915"))
        )
        .andExpect(
          jsonPath("$[0].eId").value(equalTo("hauptteil-1_art-1_abs-1_untergl-1_listenelem-1"))
        )
        .andExpect(jsonPath("$[0].zielnorm").value(equalTo("eli/bund/bgbl-1/1964/s593")))
        .andExpect(jsonPath("$[1]").exists())
        .andExpect(jsonPath("$[1].typ").value(equalTo("Änderungsvorschrift")))
        .andExpect(
          jsonPath("$[1].geltungszeit").value(equalTo("07fdc138-1509-4165-9ec7-f26f9d5c8cb8"))
        )
        .andExpect(
          jsonPath("$[1].eId").value(equalTo("hauptteil-1_art-1_abs-1_untergl-1_listenelem-2"))
        )
        .andExpect(jsonPath("$[1].zielnorm").value(equalTo("eli/bund/bgbl-1/2023/12")));

      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnorm-references"
          ).accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").exists())
        .andExpect(jsonPath("$[0].typ").value(equalTo("Änderungsvorschrift")))
        .andExpect(
          jsonPath("$[0].geltungszeit").value(equalTo("5e2f4f78-a0a1-4c55-9ef7-ad2821161915"))
        )
        .andExpect(
          jsonPath("$[0].eId").value(equalTo("hauptteil-1_art-1_abs-1_untergl-1_listenelem-1"))
        )
        .andExpect(jsonPath("$[0].zielnorm").value(equalTo("eli/bund/bgbl-1/1964/s593")))
        .andExpect(jsonPath("$[1]").exists())
        .andExpect(jsonPath("$[1].typ").value(equalTo("Änderungsvorschrift")))
        .andExpect(
          jsonPath("$[1].geltungszeit").value(equalTo("07fdc138-1509-4165-9ec7-f26f9d5c8cb8"))
        )
        .andExpect(
          jsonPath("$[1].eId").value(equalTo("hauptteil-1_art-1_abs-1_untergl-1_listenelem-2"))
        )
        .andExpect(jsonPath("$[1].zielnorm").value(equalTo("eli/bund/bgbl-1/2023/12")));
    }
  }

  @Nested
  class deleteReferences {

    @Test
    void itDeletesAReferences() throws Exception {
      // Given
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-verkuendung-1.xml"
          )
        )
      );

      // When // Then
      mockMvc
        .perform(
          delete("/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnorm-references")
            .content(
              """
              [
                "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1"
              ]
              """
            )
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").doesNotExist());

      mockMvc
        .perform(
          get(
            "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnorm-references"
          ).accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").doesNotExist());
    }
  }
}
