package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.port.input.LoadRegelungstextXmlUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser
@WebMvcTest(
  controllers = NormManifestationController.class,
  excludeAutoConfiguration = OAuth2ClientAutoConfiguration.class
)
public class NormManifestationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private LoadRegelungstextXmlUseCase loadRegelungstextXmlUseCase;

  @Nested
  class getNormManifestationXml {

    @Test
    void itReturnsNorm() throws Exception {
      // Given
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml";

      var xml =
        """
          <?xml version="1.0" encoding="UTF-8"?>
          <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
          <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
            <akn:act name="regelungstext">
              <!-- Metadaten -->
              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                  <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                    <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                    <akn:FRBRalias GUID="af17d907-a88a-4081-a13a-fd4522cd5d1e" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="vorherige-version-id" value="49eec691-392b-4d77-abaf-23eb871132ad" />
                    <akn:FRBRalias GUID="9c086b80-be09-49e6-9230-4932cfe88c83" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="aktuelle-version-id" value="77167d15-511d-4927-adf3-3c8b0464423c" />
                    <akn:FRBRalias GUID="960b4c01-c81f-40b1-92c6-d0d223410a49" eId="meta-1_ident-1_frbrexpression-1_frbralias-3" name="nachfolgende-version-id" value="b0f315a1-620b-4eaf-922c-ea46a7d10c8b" />
                  </akn:FRBRExpression>
                  <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1" GUID="bd2375e5-3e81-435d-a4f8-159d8572c46b">
                    <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1" GUID="9dcc818e-3ed8-4414-b562-342bd5f405b3" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml" />
                    <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1" GUID="f3288a2a-3511-454e-ada1-9de8c33f6dbe" date="1964-08-05" name="generierung" />
                    <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1" GUID="634de2b4-5d14-4144-9e8e-80548da73b73" value="xml" />
                  </akn:FRBRManifestation>
                </akn:identification>
              </akn:meta>
            </akn:act>
          </akn:akomaNtoso>
        """;

      // When
      when(loadRegelungstextXmlUseCase.loadRegelungstextXml(any())).thenReturn(xml);

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms/{manifestationEli}", eli).accept(MediaType.APPLICATION_XML))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML));

      verify(loadRegelungstextXmlUseCase, times(1))
        .loadRegelungstextXml(
          new LoadRegelungstextXmlUseCase.Query(DokumentManifestationEli.fromString(eli))
        );
    }
  }
}
