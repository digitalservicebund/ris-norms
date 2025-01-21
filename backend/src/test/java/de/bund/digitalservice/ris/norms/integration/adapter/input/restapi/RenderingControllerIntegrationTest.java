package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser
class RenderingControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private NormRepository normRepository;

  @AfterEach
  void cleanUp() {
    normRepository.deleteAll();
  }

  @Nested
  class getHtmlPreviewWithCustomRegelungstexte {

    @Test
    void itReturnsRender() throws Exception {
      // Given
      var jsonPayload = new JsonObject();
      jsonPayload.addProperty(
        "regelungstext",
        Fixtures.loadTextFromDisk("NormWithPassiveModifications.xml")
      );
      var customRegelungstexteJson = new JsonArray();
      customRegelungstexteJson.add(Fixtures.loadTextFromDisk("NormWithMods.xml"));
      jsonPayload.add("customRegelungstexte", customRegelungstexteJson);

      // When // Then
      mockMvc
        .perform(
          post("/api/v1/renderings?atIsoDate=2024-01-01T00:00:00.0Z")
            .accept(MediaType.TEXT_HTML)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonPayload.toString())
        )
        .andExpect(status().isOk())
        .andExpect(
          xpath("//*[@data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1']")
            .string(containsString("§ 9 Absatz 1 Satz 2, Absatz 2 oder 3"))
        );
    }

    @Test
    void itReturnsRenderOfQuotedStructure() throws Exception {
      // Given
      var jsonPayload = new JsonObject();
      jsonPayload.addProperty(
        "regelungstext",
        Fixtures.loadTextFromDisk("NormWithPassiveModsQuotedStructure.xml")
      );
      var customRegelungstexteJson = new JsonArray();
      customRegelungstexteJson.add(Fixtures.loadTextFromDisk("NormWithQuotedStructureMods.xml"));
      jsonPayload.add("customRegelungstexte", customRegelungstexteJson);

      // When // Then
      mockMvc
        .perform(
          post("/api/v1/renderings?atIsoDate=1002-11-01T00:00:00.0Z")
            .accept(MediaType.TEXT_HTML)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonPayload.toString())
        )
        .andExpect(status().isOk())
        .andExpect(
          xpath("//*[@data-eId='einleitung-1_doktitel-1_text-1_doctitel-1']")
            .string(
              containsString(
                "Fiktives Beispielgesetz für das Ersetzen von Strukturen und Gliederungseinheiten mit\n" +
                "                  Änderungsbefehlen"
              )
            )
        )
        .andExpect(
          xpath("//*[@data-eId='preambel-1_ernormen-1_ernorm-1_text-1']")
            .string(
              containsString(
                "Aufgrund der Spezifikation von Änderungsbefehlen können Strukturen und Gliederungseinheiten\n" +
                "                  ersetzt werden."
              )
            )
        )
        .andExpect(
          xpath("//*[@data-eId='preambel-1_präambeln-1_präambelinh-1_text-1']")
            .string(
              containsString(
                "Im Bewusstsein der Herausforderungen bei der Umsetzung von Änderungsbefehlen hat der\n" +
                "                  Deutsche Bundestag das nachstehende Gesetz beschlossen."
              )
            )
        )
        .andExpect(
          xpath("//*[@data-eId='preambel-1_blockcontainer-1_inhuebs-1_eintrag-1_span-1']")
            .string(containsString("Neue Inhaltsübersicht."))
        )
        .andExpect(
          xpath("//*[@data-eId='hauptteil-1_art-2_abs-1_inhalt-1_text-1']")
            .string(
              containsString(
                "Dieses Gesetz findet Anwendung auf fast alle definierten Struktur und Gliederungsebenen."
              )
            )
        )
        .andExpect(
          xpath(
            "//*[@data-eId='hauptteil-1_art-2_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-1_inhalt-1_text-1']"
          )
            .string(containsString("Zeilen,"))
        )
        .andExpect(
          xpath(
            "//*[@data-eId='hauptteil-1_art-2_abs-3_untergl-1_listenelem-2_untergl-1_listenelem-2_inhalt-1_text-1']"
          )
            .string(containsString("oder Zellen."))
        );
    }
  }
}
