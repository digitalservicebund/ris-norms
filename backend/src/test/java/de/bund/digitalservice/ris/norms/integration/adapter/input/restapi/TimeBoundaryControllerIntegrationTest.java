package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class TimeBoundaryControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private NormRepository normRepository;

  @AfterEach
  void cleanUp() {
    normRepository.deleteAll();
  }

  @Nested
  class GetTimeBoundaries {

    @Test
    void itCallsGetTimeBoundariesAndReturns404() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/thisEliDoesNotExist";

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms/{eli}/timeBoundaries", eli).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Norm with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/thisEliDoesNotExist does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/thisEliDoesNotExist/timeBoundaries"
            )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/thisEliDoesNotExist")
        );
    }

    @Test
    void itCallsGetTimeBoundariesAndReturnsJson() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      final String xml =
        """
          <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
          <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                 http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
             <akn:act name="regelungstext">
                <!-- Metadaten -->
                <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                  <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                     <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                        <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                        <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                        <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                        <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                     </akn:FRBRExpression>
                 </akn:identification>
                   <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29"
                          source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30"
                          source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-3" GUID="4539e3ee-3b35-4921-a249-93a98dbd7339" date="2024-01-01"
                          source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                   </akn:lifecycle>
                   <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                               <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                                  <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                               </akn:temporalGroup>
                               <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-2" GUID="fdfaeef0-0300-4e5b-9e8b-14d2162bfb00">
                                  <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1" GUID="8118030a-5fa4-4f9c-a880-b7ba19e5edfb" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-3" />
                               </akn:temporalGroup>
                   </akn:temporalData>
                </akn:meta>
             </akn:act>
          </akn:akomaNtoso>
        """.strip();

      // When
      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
      normRepository.save(NormMapper.mapToDto(norm));

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms/{eli}/timeBoundaries", eli).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].date", is("2023-12-30")))
        .andExpect(jsonPath("$[0].eventRefEid", is("meta-1_lebzykl-1_ereignis-2")))
        .andExpect(jsonPath("$[1].date", is("2024-01-01")))
        .andExpect(jsonPath("$[1].eventRefEid", is("meta-1_lebzykl-1_ereignis-3")));
    }
  }

  @Nested
  class GetTimeBoundariesAmendedBy {

    @Test
    void itCallsGetTimeBoundariesAmendedByAndReturns404() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/thisEliDoesNotExist";
      final String amendedBy = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/not-relevant";

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/norms/{eli}/timeBoundaries?amendedBy={amendedBy}", eli, amendedBy)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Norm with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/thisEliDoesNotExist does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/thisEliDoesNotExist/timeBoundaries"
            )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/thisEliDoesNotExist")
        );
    }

    @Test
    void itCallsGetTimeBoundariesAmendedByAndReturnsEmpty() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      var amendedBy = "eli/bund/bgbl-1/2024/81/2024-03-05/1/deu/non-in-norm";

      var norm = Norm
        .builder()
        .document(
          XmlMapper.toDocument(
            """
            <?xml version="1.0" encoding="UTF-8"?>
            <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                                                                        http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
               <akn:act name="regelungstext">
                  <!-- Metadaten -->
                  <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                     <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                       <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                          <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                          <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                          <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                          <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                       </akn:FRBRExpression>
                    </akn:identification>
                     <akn:analysis eId="meta-1_analysis-1" GUID="5a5d264e-431e-4dc1-b971-4bd81af8a0f4" source="attributsemantik-noch-undefiniert">
                        <akn:passiveModifications eId="meta-1_analysis-1_pasmod-1" GUID="77aae58f-06c9-4189-af80-a5f3ada6432c">
                           <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-1" GUID="06fb52c3-fce1-4be8-accc-3035452378ff" type="substitution">
                              <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-1_source-1" GUID="5384f580-110b-4f8a-8794-8b85c29aabdf" href="eli/bund/bgbl-1/2024/81/2024-03-05/1/deu/regelungstext-1/art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1.xml" />
                              <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-1_destination-1" GUID="2c26512f-fb04-45f2-8283-660274e52fdb" href="#para-9_abs-3" />
                              <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-1_gelzeitnachw-1" GUID="45331583-4386-4e3f-b68f-5af327347874" period="#meta-1_geltzeiten-1_geltungszeitgr-2" />
                           </akn:textualMod>
                           <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-2" GUID="26b091d0-1bb9-4c83-b940-f6788b2922f2" type="substitution">
                              <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-2_source-1" GUID="a5e43d31-65e1-4d99-a1aa-fb4695a94cf5" href="eli/bund/bgbl-1/2024/81/2024-03-05/1/deu/regelungstext-1/art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml" />
                              <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-2_destination-1" GUID="8c0418f1-b6fa-4110-8820-cf0db752c5bd" href="#para-20_abs-1/100-126" />
                              <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-2_gelzeitnachw-1" GUID="e5962d3b-9bb8-4eb0-8d8f-131a5114fddb" period="#meta-1_geltzeiten-1_geltungszeitgr-3" />
                           </akn:textualMod>
                           <!-- Passive mod from different amending law -->
                           <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-2" GUID="26b091d0-1bb9-4c83-b940-f6788b2922f2" type="substitution">
                              <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-2_source-1" GUID="a5e43d31-65e1-4d99-a1aa-fb4695a94cf5" href="eli/bund/bgbl-1/2024/120/2024-06-28/1/deu/regelungstext-1/art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml" />
                              <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-2_destination-1" GUID="8c0418f1-b6fa-4110-8820-cf0db752c5bd" href="#para-20_abs-1/100-126" />
                              <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-2_gelzeitnachw-1" GUID="e5962d3b-9bb8-4eb0-8d8f-131a5114fddb" period="#meta-1_geltzeiten-1_geltungszeitgr-4" />
                           </akn:textualMod>
                        </akn:passiveModifications>
                     </akn:analysis>
                     <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                        <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29" source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                        <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30" source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                        <akn:eventRef eId="meta-1_lebzykl-1_ereignis-3" GUID="4539e3ee-3b35-4921-a249-93a98dbd7339" date="2024-01-01" source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                        <akn:eventRef eId="meta-1_lebzykl-1_ereignis-4" GUID="4539e3ee-3b35-4921-a249-93a98dbd7339" date="2024-02-28" source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                     </akn:lifecycle>
                     <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                        <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                           <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                        </akn:temporalGroup>
                        <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-2" GUID="fdfaeef0-0300-4e5b-9e8b-14d2162bfb00">
                           <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1" GUID="8118030a-5fa4-4f9c-a880-b7ba19e5edfb" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-3" />
                        </akn:temporalGroup>
                        <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-3" GUID="fdfaeef0-0300-4e5b-9e8b-14d2162bfb00">
                           <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-3_gelzeitintervall-1" GUID="8118030a-5fa4-4f9c-a880-b7ba19e5edfb" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-4" />
                        </akn:temporalGroup>
                        <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-4" GUID="fdfaeef0-0300-4e5b-9e8b-14d2162bfb00">
                           <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-4_gelzeitintervall-1" GUID="8118030a-5fa4-4f9c-a880-b7ba19e5edfb" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-4" />
                        </akn:temporalGroup>
                     </akn:temporalData>
                  </akn:meta>
               </akn:act>
            </akn:akomaNtoso>
            """
          )
        )
        .build();
      normRepository.save(NormMapper.mapToDto(norm));

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/norms/{eli}/timeBoundaries?amendedBy={amendedBy}", eli, amendedBy)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void itCallsGetTimeBoundariesAmendedByAndReturnsJson() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      var amendedBy = "eli/bund/bgbl-1/2024/81/2024-03-05/1/deu/regelungstext-1";

      var norm = Norm
        .builder()
        .document(
          XmlMapper.toDocument(
            """
            <?xml version="1.0" encoding="UTF-8"?>
            <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                                                                        http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
               <akn:act name="regelungstext">
                  <!-- Metadaten -->
                  <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                     <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                       <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                          <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                          <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                          <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                          <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                       </akn:FRBRExpression>
                    </akn:identification>
                     <akn:analysis eId="meta-1_analysis-1" GUID="5a5d264e-431e-4dc1-b971-4bd81af8a0f4" source="attributsemantik-noch-undefiniert">
                        <akn:passiveModifications eId="meta-1_analysis-1_pasmod-1" GUID="77aae58f-06c9-4189-af80-a5f3ada6432c">
                           <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-1" GUID="06fb52c3-fce1-4be8-accc-3035452378ff" type="substitution">
                              <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-1_source-1" GUID="5384f580-110b-4f8a-8794-8b85c29aabdf" href="eli/bund/bgbl-1/2024/81/2024-03-05/1/deu/regelungstext-1/art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1.xml" />
                              <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-1_destination-1" GUID="2c26512f-fb04-45f2-8283-660274e52fdb" href="#para-9_abs-3" />
                              <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-1_gelzeitnachw-1" GUID="45331583-4386-4e3f-b68f-5af327347874" period="#meta-1_geltzeiten-1_geltungszeitgr-2" />
                           </akn:textualMod>
                           <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-2" GUID="26b091d0-1bb9-4c83-b940-f6788b2922f2" type="substitution">
                              <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-2_source-1" GUID="a5e43d31-65e1-4d99-a1aa-fb4695a94cf5" href="eli/bund/bgbl-1/2024/81/2024-03-05/1/deu/regelungstext-1/art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml" />
                              <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-2_destination-1" GUID="8c0418f1-b6fa-4110-8820-cf0db752c5bd" href="#para-20_abs-1/100-126" />
                              <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-2_gelzeitnachw-1" GUID="e5962d3b-9bb8-4eb0-8d8f-131a5114fddb" period="#meta-1_geltzeiten-1_geltungszeitgr-3" />
                           </akn:textualMod>
                           <!-- Passive mod from different amending law -->
                           <akn:textualMod eId="meta-1_analysis-1_pasmod-1_textualmod-2" GUID="26b091d0-1bb9-4c83-b940-f6788b2922f2" type="substitution">
                              <akn:source eId="meta-1_analysis-1_pasmod-1_textualmod-2_source-1" GUID="a5e43d31-65e1-4d99-a1aa-fb4695a94cf5" href="eli/bund/bgbl-1/2024/120/2024-06-28/1/deu/regelungstext-1/art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1.xml" />
                              <akn:destination eId="meta-1_analysis-1_pasmod-1_textualmod-2_destination-1" GUID="8c0418f1-b6fa-4110-8820-cf0db752c5bd" href="#para-20_abs-1/100-126" />
                              <akn:force eId="meta-1_analysis-1_pasmod-1_textualmod-2_gelzeitnachw-1" GUID="e5962d3b-9bb8-4eb0-8d8f-131a5114fddb" period="#meta-1_geltzeiten-1_geltungszeitgr-4" />
                           </akn:textualMod>
                        </akn:passiveModifications>
                     </akn:analysis>
                     <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                        <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29" source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                        <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30" source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                        <akn:eventRef eId="meta-1_lebzykl-1_ereignis-3" GUID="4539e3ee-3b35-4921-a249-93a98dbd7339" date="2024-01-01" source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                        <akn:eventRef eId="meta-1_lebzykl-1_ereignis-4" GUID="4539e3ee-3b35-4921-a249-93a98dbd7339" date="2024-02-28" source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                     </akn:lifecycle>
                     <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                        <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                           <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                        </akn:temporalGroup>
                        <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-2" GUID="fdfaeef0-0300-4e5b-9e8b-14d2162bfb00">
                           <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1" GUID="8118030a-5fa4-4f9c-a880-b7ba19e5edfb" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-3" />
                        </akn:temporalGroup>
                        <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-3" GUID="fdfaeef0-0300-4e5b-9e8b-14d2162bfb00">
                           <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-3_gelzeitintervall-1" GUID="8118030a-5fa4-4f9c-a880-b7ba19e5edfb" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-4" />
                        </akn:temporalGroup>
                        <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-4" GUID="fdfaeef0-0300-4e5b-9e8b-14d2162bfb00">
                           <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-4_gelzeitintervall-1" GUID="8118030a-5fa4-4f9c-a880-b7ba19e5edfb" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-4" />
                        </akn:temporalGroup>
                     </akn:temporalData>
                  </akn:meta>
               </akn:act>
            </akn:akomaNtoso>
            """
          )
        )
        .build();
      normRepository.save(NormMapper.mapToDto(norm));

      // When // Then
      mockMvc
        .perform(
          get("/api/v1/norms/{eli}/timeBoundaries?amendedBy={amendedBy}", eli, amendedBy)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].date", is("2024-01-01")))
        .andExpect(jsonPath("$[0].eventRefEid", is("meta-1_lebzykl-1_ereignis-3")))
        .andExpect(jsonPath("$[0].temporalGroupEid", is("meta-1_geltzeiten-1_geltungszeitgr-2")))
        .andExpect(jsonPath("$[1].date", is("2024-02-28")))
        .andExpect(jsonPath("$[1].eventRefEid", is("meta-1_lebzykl-1_ereignis-4")))
        .andExpect(jsonPath("$[1].temporalGroupEid", is("meta-1_geltzeiten-1_geltungszeitgr-3")));
    }
  }

  @Nested
  class UpdateTimeBoundaries {

    @Test
    void itCallsPutTimeBoundariesAndReturns404() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/thisEliDoesNotExist";

      // When // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/timeBoundaries", eli)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              "[{\"date\": \"2023-12-30\", \"eventRefEid\": \"meta-1_lebzykl-1_ereignis-2\"},{\"date\": \"2024-01-01\", \"eventRefEid\": null}]"
            )
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Norm with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/thisEliDoesNotExist does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/thisEliDoesNotExist/timeBoundaries"
            )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/thisEliDoesNotExist")
        );
    }

    @Test
    void itCallsUpdateTimeBoundaries() throws Exception {
      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      final String xml =
        """
          <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
          <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                 http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
             <akn:act name="regelungstext">
                <!-- Metadaten -->
                <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                  <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                     <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                        <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                        <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                        <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                        <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                     </akn:FRBRExpression>
                 </akn:identification>
                   <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29"
                          source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30"
                          source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                   </akn:lifecycle>
                   <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                               <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                                  <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                               </akn:temporalGroup>
                   </akn:temporalData>
                </akn:meta>
             </akn:act>
          </akn:akomaNtoso>
        """.strip();

      // When
      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
      normRepository.save(NormMapper.mapToDto(norm));

      // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/timeBoundaries", eli)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              "[{\"date\": \"2023-12-30\", \"eventRefEid\": \"meta-1_lebzykl-1_ereignis-2\"},{\"date\": \"2024-01-01\", \"eventRefEid\": null}]"
            )
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        // still there
        .andExpect(jsonPath("$[0].date", is("2023-12-30")))
        .andExpect(jsonPath("$[0].eventRefEid", is("meta-1_lebzykl-1_ereignis-2")))
        // expect new
        .andExpect(jsonPath("$[1].date", is("2024-01-01")))
        .andExpect(jsonPath("$[1].eventRefEid", is("meta-1_lebzykl-1_ereignis-3")));
    }

    @Test
    void itCallsUpdateTimeBoundariesDateNull() throws Exception {
      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      final String xml =
        """
          <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
          <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                 http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
             <akn:act name="regelungstext">
                <!-- Metadaten -->
                <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                   <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                     <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                        <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                        <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                        <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                        <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                     </akn:FRBRExpression>
                   </akn:identification>
                   <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29"
                          source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30"
                          source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                   </akn:lifecycle>
                   <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                       <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                          <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                       </akn:temporalGroup>
                   </akn:temporalData>
                </akn:meta>
             </akn:act>
          </akn:akomaNtoso>
        """.strip();

      // When
      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
      normRepository.save(NormMapper.mapToDto(norm));

      // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/timeBoundaries", eli)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("[{\"date\": null, \"eventRefEid\": null}]")
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("type").value("/errors/input-validation-error"))
        .andExpect(jsonPath("title").value("Input validation error"))
        .andExpect(jsonPath("status").value(400))
        .andExpect(jsonPath("detail").value("Date must not be null"))
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/timeBoundaries"
            )
        );
    }

    @Test
    void itCallsUpdateTimeBoundariesDateMalformed() throws Exception {
      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      final String xml =
        """
          <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
          <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                 http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
             <akn:act name="regelungstext">
                <!-- Metadaten -->
                <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                   <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                     <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                        <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                        <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                        <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                        <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                     </akn:FRBRExpression>
                   </akn:identification>
                   <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29"
                          source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30"
                          source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                   </akn:lifecycle>
                   <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                       <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                          <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                       </akn:temporalGroup>
                   </akn:temporalData>
                </akn:meta>
             </akn:act>
          </akn:akomaNtoso>
        """.strip();

      // When
      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
      normRepository.save(NormMapper.mapToDto(norm));

      // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/timeBoundaries", eli)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("[{\"date\": \"THISISNODATE\", \"eventRefEid\": null}]")
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("type").value("/errors/http-message-not-readable-exception"))
        .andExpect(jsonPath("title").value("Bad Request"))
        .andExpect(jsonPath("status").value(400))
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/timeBoundaries"
            )
        );
    }

    @Test
    void itCallsUpdateTimeBoundariesMultipleSameDates() throws Exception {
      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      final String xml =
        """
          <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
          <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                 http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
             <akn:act name="regelungstext">
                <!-- Metadaten -->
                <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                  <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                     <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                        <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                        <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                        <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                        <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                     </akn:FRBRExpression>
                 </akn:identification>
                   <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29"
                          source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30"
                          source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                   </akn:lifecycle>
                   <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                               <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                                  <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                               </akn:temporalGroup>
                   </akn:temporalData>
                </akn:meta>
             </akn:act>
          </akn:akomaNtoso>
        """.strip();

      // When
      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
      normRepository.save(NormMapper.mapToDto(norm));

      // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/timeBoundaries", eli)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              "[" +
              "{\"date\": \"2023-12-30\", \"eventRefEid\": \"meta-1_lebzykl-1_ereignis-2\"}," +
              "{\"date\": \"2024-01-01\", \"eventRefEid\": null}," +
              "{\"date\": \"2024-01-01\", \"eventRefEid\": null}" +
              "]"
            )
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("type").value("/errors/input-validation-error"))
        .andExpect(jsonPath("title").value("Input validation error"))
        .andExpect(jsonPath("status").value(400))
        .andExpect(jsonPath("detail").value("All dates must be unique."))
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/timeBoundaries"
            )
        );
    }

    @Test
    void itCallsUpdateTimeBoundariesDelete() throws Exception {
      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      final String xml =
        """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                               http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
           <akn:act name="regelungstext">
              <!-- Metadaten -->
              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                   <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                      <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                      <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                      <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                      <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                   </akn:FRBRExpression>
               </akn:identification>
                 <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-3" GUID="860d4338-04a0-419f-a3d1-e7dbefd36d8b" date="2024-12-01"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                 </akn:lifecycle>
                 <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                     <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                        <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                     </akn:temporalGroup>
                     <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-2" GUID="f49f536c-57e9-4c5e-9edd-4b827340279e">
                        <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1" GUID="63eedd45-6a2c-4213-bbc4-27596255c1b7" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-3" />
                     </akn:temporalGroup>
                 </akn:temporalData>
              </akn:meta>
           </akn:act>
        </akn:akomaNtoso>
        """.strip();

      // When
      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
      normRepository.save(NormMapper.mapToDto(norm));

      // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/timeBoundaries", eli)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              "[{\"date\": \"2023-12-30\", \"eventRefEid\": \"meta-1_lebzykl-1_ereignis-2\"}]"
            )
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        // still there
        .andExpect(jsonPath("$[0].date", is("2023-12-30")))
        .andExpect(jsonPath("$[0].eventRefEid", is("meta-1_lebzykl-1_ereignis-2")));
    }

    @Test
    void itCallsUpdateTimeBoundariesAddAndDelete() throws Exception {
      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      final String xml =
        """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                               http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
           <akn:act name="regelungstext">
              <!-- Metadaten -->
              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                   <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                      <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                      <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                      <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                      <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                   </akn:FRBRExpression>
               </akn:identification>
                 <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                 </akn:lifecycle>
                 <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                     <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                        <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                     </akn:temporalGroup>
                 </akn:temporalData>
              </akn:meta>
           </akn:act>
        </akn:akomaNtoso>
        """.strip();

      // When
      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
      normRepository.save(NormMapper.mapToDto(norm));

      // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/timeBoundaries", eli)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("[{\"date\": \"2024-01-01\", \"eventRefEid\": null}]")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        // expect new
        .andExpect(jsonPath("$[0].date", is("2024-01-01")))
        .andExpect(jsonPath("$[0].eventRefEid", is("meta-1_lebzykl-1_ereignis-2")));
    }

    @Test
    void itCallsUpdateTimeBoundariesAddAndDeleteWithTwo() throws Exception {
      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      final String xml =
        """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                               http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
           <akn:act name="regelungstext">
              <!-- Metadaten -->
              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                   <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                      <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                      <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                      <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                      <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                   </akn:FRBRExpression>
               </akn:identification>
                 <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-3" GUID="860d4338-04a0-419f-a3d1-e7dbefd36d8b" date="2024-12-01"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                 </akn:lifecycle>
                 <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                     <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                        <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                     </akn:temporalGroup>
                     <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-2" GUID="f49f536c-57e9-4c5e-9edd-4b827340279e">
                        <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1" GUID="63eedd45-6a2c-4213-bbc4-27596255c1b7" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-3" />
                     </akn:temporalGroup>
                 </akn:temporalData>
              </akn:meta>
           </akn:act>
        </akn:akomaNtoso>
        """.strip();

      // When
      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
      normRepository.save(NormMapper.mapToDto(norm));

      // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/timeBoundaries", eli)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              "[{\"date\": \"2023-12-30\", \"eventRefEid\": \"meta-1_lebzykl-1_ereignis-2\"},{\"date\": \"2024-01-01\", \"eventRefEid\": null}]"
            )
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        // still there
        .andExpect(jsonPath("$[0].date", is("2023-12-30")))
        .andExpect(jsonPath("$[0].eventRefEid", is("meta-1_lebzykl-1_ereignis-2")))
        // expect new
        .andExpect(jsonPath("$[1].date", is("2024-01-01")))
        .andExpect(jsonPath("$[1].eventRefEid", is("meta-1_lebzykl-1_ereignis-3")));
    }

    @Test
    void itCallsUpdateTimeBoundariesAddAndDeleteAndEdit() throws Exception {
      // the existing ones:
      // meta-1_lebzykl-1_ereignis-1: 2019-01-01 (ignored ausfertigung)
      // meta-1_lebzykl-1_ereignis-2: 2020-01-01 (nothing should change)
      // meta-1_lebzykl-1_ereignis-3: 2021-01-01 (will be deleted)
      // meta-1_lebzykl-1_ereignis-4: 2022-01-01 (nothing should change)
      // meta-1_lebzykl-1_ereignis-5: 2023-01-01 (will be edited)
      // meta-1_lebzykl-1_ereignis-6: 2024-01-01 (will be deleted)
      // meta-1_lebzykl-1_ereignis-7: 2025-01-01 (will be edited)

      // the request:
      // meta-1_lebzykl-1_ereignis-2: 2020-01-01 (nothing changed)
      // meta-1_lebzykl-1_ereignis-4: 2022-01-01 (nothing changed)
      // null                       : 2024-01-01 (new)
      // meta-1_lebzykl-1_ereignis-5: 2023-06-06 (edit)
      // null                       : 2025-01-01 (new)
      // meta-1_lebzykl-1_ereignis-7: 2025-09-09 (edit)

      // expected outcome:
      // meta-1_lebzykl-1_ereignis-1: 2019-01-01 (ignored ausfertigung)
      // meta-1_lebzykl-1_ereignis-2: 2020-01-01 (nothing changed)
      // meta-1_lebzykl-1_ereignis-3: 2022-01-01 (nothing changed)
      // meta-1_lebzykl-1_ereignis-4: 2023-06-06 (edited)
      // meta-1_lebzykl-1_ereignis-5: 2025-09-09 (edited)
      // meta-1_lebzykl-1_ereignis-6: 2024-01-01 (new)
      // meta-1_lebzykl-1_ereignis-7: 2025-01-01 (new)

      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      final String xml =
        """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                               http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
           <akn:act name="regelungstext">
              <!-- Metadaten -->
              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                   <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                      <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                      <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                      <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                      <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                   </akn:FRBRExpression>
               </akn:identification>
                 <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2019-01-01"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2020-01-01"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-3" GUID="860d4338-04a0-419f-a3d1-e7dbefd36d8b" date="2021-01-01"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-4" GUID="fb4a8755-3b1d-4f51-8eba-334a79928af0" date="2022-01-01"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-5" GUID="1c2d67f6-8cc3-4b26-b75d-fc6ae884544f" date="2023-01-01"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-6" GUID="c528e88a-e95e-4e14-ac4e-0b40bebb9372" date="2024-01-01"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                    <akn:eventRef eId="meta-1_lebzykl-1_ereignis-7" GUID="e2dc04db-efce-4df8-90f4-e346a9ff86f5" date="2025-01-01"
                        source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                 </akn:lifecycle>
                 <akn:temporalData eId="meta-1_geltzeiten-1" GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
                     <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
                        <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2" />
                     </akn:temporalGroup>
                     <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-2" GUID="f49f536c-57e9-4c5e-9edd-4b827340279e">
                        <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1" GUID="63eedd45-6a2c-4213-bbc4-27596255c1b7" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-3" />
                     </akn:temporalGroup>
                     <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-3" GUID="5825f7f8-f37b-4c82-878a-765524bb8e90">
                        <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-3_gelzeitintervall-1" GUID="4c42fb23-c6a7-417b-a796-e60e2074b899" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-4" />
                     </akn:temporalGroup>
                     <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-4" GUID="c64c7836-a11b-4ed7-950b-194c66c2fa7a">
                        <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-4_gelzeitintervall-1" GUID="0c88efce-9d07-441e-935d-464981634675" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-5" />
                     </akn:temporalGroup>
                     <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-5" GUID="80ff1429-f8b5-4415-894b-38e342ea889e">
                        <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-5_gelzeitintervall-1" GUID="5f3af344-137b-41a1-835f-2d939e258860" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-6" />
                     </akn:temporalGroup>
                     <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-6" GUID="81e4dc31-7279-41f8-8be0-3d5e2507a7d6">
                        <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-6_gelzeitintervall-1" GUID="5fae86fb-4e8a-458d-a3b7-66c3971e4ec2" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-7" />
                     </akn:temporalGroup>
                 </akn:temporalData>
              </akn:meta>
           </akn:act>
        </akn:akomaNtoso>
        """.strip();

      // When
      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
      normRepository.save(NormMapper.mapToDto(norm));

      // Then
      mockMvc
        .perform(
          put("/api/v1/norms/{eli}/timeBoundaries", eli)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              "[" +
              "{\"date\": \"2020-01-01\", \"eventRefEid\": \"meta-1_lebzykl-1_ereignis-2\"}," +
              "{\"date\": \"2022-01-01\", \"eventRefEid\": \"meta-1_lebzykl-1_ereignis-4\"}," +
              "{\"date\": \"2024-01-01\", \"eventRefEid\": null}," +
              "{\"date\": \"2023-06-06\", \"eventRefEid\": \"meta-1_lebzykl-1_ereignis-5\"}," +
              "{\"date\": \"2025-01-01\", \"eventRefEid\": null}," +
              "{\"date\": \"2025-09-09\", \"eventRefEid\": \"meta-1_lebzykl-1_ereignis-7\"}" +
              "]"
            )
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(6)))
        // still there
        .andExpect(jsonPath("$[0].date", is("2020-01-01")))
        .andExpect(jsonPath("$[0].eventRefEid", is("meta-1_lebzykl-1_ereignis-2")))
        // still there
        .andExpect(jsonPath("$[1].date", is("2022-01-01")))
        .andExpect(jsonPath("$[1].eventRefEid", is("meta-1_lebzykl-1_ereignis-3")))
        // expect new
        .andExpect(jsonPath("$[2].date", is("2023-06-06")))
        .andExpect(jsonPath("$[2].eventRefEid", is("meta-1_lebzykl-1_ereignis-4")))
        // expect new
        .andExpect(jsonPath("$[3].date", is("2025-09-09")))
        .andExpect(jsonPath("$[3].eventRefEid", is("meta-1_lebzykl-1_ereignis-5")))
        // expect new
        .andExpect(jsonPath("$[4].date", is("2024-01-01")))
        .andExpect(jsonPath("$[4].eventRefEid", is("meta-1_lebzykl-1_ereignis-6")))
        // expect new
        .andExpect(jsonPath("$[5].date", is("2025-01-01")))
        .andExpect(jsonPath("$[5].eventRefEid", is("meta-1_lebzykl-1_ereignis-7")));
    }
  }
}
