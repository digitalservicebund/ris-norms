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

  @Autowired private MockMvc mockMvc;

  @Autowired private NormRepository normRepository;

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
          .perform(
              get("/api/v1/norms/{eli}/timeBoundaries", eli).accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isNotFound());
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
                    """
              .strip();

      // When
      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
      normRepository.save(NormMapper.mapToDto(norm));

      // When // Then
      mockMvc
          .perform(
              get("/api/v1/norms/{eli}/timeBoundaries", eli).accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(2)))
          .andExpect(jsonPath("$[0].date", is("2023-12-30")))
          .andExpect(jsonPath("$[0].eid", is("meta-1_lebzykl-1_ereignis-2")))
          .andExpect(jsonPath("$[1].date", is("2024-01-01")))
          .andExpect(jsonPath("$[1].eid", is("meta-1_lebzykl-1_ereignis-3")));
    }
  }

  @Nested
  class UpdateTimeBoundaries {
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
                    """
              .strip();

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
                      "[{\"date\": \"2023-12-30\", \"eid\": \"meta-1_lebzykl-1_ereignis-2\"},{\"date\": \"2024-01-01\", \"eid\": null}]"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(2)))

          // still there
          .andExpect(jsonPath("$[0].date", is("2023-12-30")))
          .andExpect(jsonPath("$[0].eid", is("meta-1_lebzykl-1_ereignis-2")))

          // expect new
          .andExpect(jsonPath("$[1].date", is("2024-01-01")))
          .andExpect(jsonPath("$[1].eid", is("meta-1_lebzykl-1_ereignis-3")));
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
                    """
              .strip();

      // When
      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
      normRepository.save(NormMapper.mapToDto(norm));

      // Then
      mockMvc
          .perform(
              put("/api/v1/norms/{eli}/timeBoundaries", eli)
                  .accept(MediaType.APPLICATION_JSON)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("[{\"date\": null, \"eid\": null}]"))
          .andExpect(status().isBadRequest());
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
                    """
              .strip();

      // When
      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
      normRepository.save(NormMapper.mapToDto(norm));

      // Then
      mockMvc
          .perform(
              put("/api/v1/norms/{eli}/timeBoundaries", eli)
                  .accept(MediaType.APPLICATION_JSON)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("[{\"date\": \"THISISNODATE\", \"eid\": null}]"))
          .andExpect(status().isBadRequest());
    }

    //    @Test
    //    void itCallsUpdateTimeBoundariesDelete() throws Exception {
    //      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
    //      final String xml =
    //          """
    //                          <?xml-model href="../../../Grammatiken/legalDocML.de.sch"
    // schematypens="http://purl.oclc.org/dsdl/schematron"?>
    //                          <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/"
    // xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    //                             xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/
    // ../../../Grammatiken/legalDocML.de-metadaten.xsd
    //                                                 http://Inhaltsdaten.LegalDocML.de/1.6/
    // ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
    //                             <akn:act name="regelungstext">
    //                                <!-- Metadaten -->
    //                                <akn:meta eId="meta-1"
    // GUID="82a65581-0ea7-4525-9190-35ff86c977af">
    //                                  <akn:identification eId="meta-1_ident-1"
    // GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
    //                                     <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1"
    // GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
    //                                        <akn:FRBRthis
    // eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78"
    // value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
    //                                        <akn:FRBRalias
    // GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2"
    // name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
    //                                        <akn:FRBRalias
    // GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1"
    // name="aktuelle-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
    //                                        <akn:FRBRalias
    // GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2"
    // name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
    //                                     </akn:FRBRExpression>
    //                                 </akn:identification>
    //                                   <akn:lifecycle eId="meta-1_lebzykl-1"
    // GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
    //                                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1"
    // GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29"
    //                                          source="attributsemantik-noch-undefiniert"
    // type="generation" refersTo="ausfertigung" />
    //                                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2"
    // GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30"
    //                                          source="attributsemantik-noch-undefiniert"
    // type="generation" refersTo="inkrafttreten" />
    //                                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-3"
    // GUID="860d4338-04a0-419f-a3d1-e7dbefd36d8b" date="2024-12-01"
    //                                          source="attributsemantik-noch-undefiniert"
    // type="generation" refersTo="inkrafttreten" />
    //                                   </akn:lifecycle>
    //                                   <akn:temporalData eId="meta-1_geltzeiten-1"
    // GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
    //                                       <akn:temporalGroup
    // eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
    //                                          <akn:timeInterval
    // eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1"
    // GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit"
    // start="#meta-1_lebzykl-1_ereignis-2" />
    //                                       </akn:temporalGroup>
    //                                       <akn:temporalGroup
    // eId="meta-1_geltzeiten-1_geltungszeitgr-2" GUID="f49f536c-57e9-4c5e-9edd-4b827340279e">
    //                                          <akn:timeInterval
    // eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1"
    // GUID="63eedd45-6a2c-4213-bbc4-27596255c1b7" refersTo="geltungszeit"
    // start="#meta-1_lebzykl-1_ereignis-3" />
    //                                       </akn:temporalGroup>
    //                                   </akn:temporalData>
    //                                </akn:meta>
    //                             </akn:act>
    //                          </akn:akomaNtoso>
    //                        """
    //              .strip();
    //
    //      // When
    //      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
    //      normRepository.save(NormMapper.mapToDto(norm));
    //
    //      // Then
    //      mockMvc
    //          .perform(
    //              put("/api/v1/norms/{eli}/timeBoundaries", eli)
    //                  .accept(MediaType.APPLICATION_JSON)
    //                  .contentType(MediaType.APPLICATION_JSON)
    //                  .content(
    //                      "[{\"date\": \"2023-12-30\", \"eid\":
    // \"meta-1_lebzykl-1_ereignis-2\"}]"))
    //          .andExpect(status().isOk())
    //          .andExpect(jsonPath("$", hasSize(1)))
    //
    //          // still there
    //          .andExpect(jsonPath("$[0].date", is("2023-12-30")))
    //          .andExpect(jsonPath("$[0].eid", is("meta-1_lebzykl-1_ereignis-2")));
    //    }
    //
    //    @Test
    //    void itCallsUpdateTimeBoundariesAddAndDelete() throws Exception {
    //      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
    //      final String xml =
    //          """
    //                          <?xml-model href="../../../Grammatiken/legalDocML.de.sch"
    // schematypens="http://purl.oclc.org/dsdl/schematron"?>
    //                          <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/"
    // xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    //                             xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/
    // ../../../Grammatiken/legalDocML.de-metadaten.xsd
    //                                                 http://Inhaltsdaten.LegalDocML.de/1.6/
    // ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
    //                             <akn:act name="regelungstext">
    //                                <!-- Metadaten -->
    //                                <akn:meta eId="meta-1"
    // GUID="82a65581-0ea7-4525-9190-35ff86c977af">
    //                                  <akn:identification eId="meta-1_ident-1"
    // GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
    //                                     <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1"
    // GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
    //                                        <akn:FRBRthis
    // eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78"
    // value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
    //                                        <akn:FRBRalias
    // GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2"
    // name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
    //                                        <akn:FRBRalias
    // GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1"
    // name="aktuelle-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
    //                                        <akn:FRBRalias
    // GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2"
    // name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
    //                                     </akn:FRBRExpression>
    //                                 </akn:identification>
    //                                   <akn:lifecycle eId="meta-1_lebzykl-1"
    // GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
    //                                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1"
    // GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29"
    //                                          source="attributsemantik-noch-undefiniert"
    // type="generation" refersTo="ausfertigung" />
    //                                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2"
    // GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30"
    //                                          source="attributsemantik-noch-undefiniert"
    // type="generation" refersTo="inkrafttreten" />
    //                                   </akn:lifecycle>
    //                                   <akn:temporalData eId="meta-1_geltzeiten-1"
    // GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
    //                                       <akn:temporalGroup
    // eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
    //                                          <akn:timeInterval
    // eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1"
    // GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit"
    // start="#meta-1_lebzykl-1_ereignis-2" />
    //                                       </akn:temporalGroup>
    //                                   </akn:temporalData>
    //                                </akn:meta>
    //                             </akn:act>
    //                          </akn:akomaNtoso>
    //                        """
    //              .strip();
    //
    //      // When
    //      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
    //      normRepository.save(NormMapper.mapToDto(norm));
    //
    //      // Then
    //      mockMvc
    //          .perform(
    //              put("/api/v1/norms/{eli}/timeBoundaries", eli)
    //                  .accept(MediaType.APPLICATION_JSON)
    //                  .contentType(MediaType.APPLICATION_JSON)
    //                  .content("[{\"date\": \"2024-01-01\", \"eid\": null}]"))
    //          .andExpect(status().isOk())
    //          .andExpect(jsonPath("$", hasSize(1)))
    //
    //          // expect new
    //          .andExpect(jsonPath("$[0].date", is("2024-01-01")))
    //          .andExpect(jsonPath("$[0].eid", is("meta-1_lebzykl-1_ereignis-3")));
    //    }
    //
    //    @Test
    //    void itCallsUpdateTimeBoundariesAddAndDeleteWithTwo() throws Exception {
    //      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
    //      final String xml =
    //          """
    //                          <?xml-model href="../../../Grammatiken/legalDocML.de.sch"
    // schematypens="http://purl.oclc.org/dsdl/schematron"?>
    //                          <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/"
    // xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    //                             xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/
    // ../../../Grammatiken/legalDocML.de-metadaten.xsd
    //                                                 http://Inhaltsdaten.LegalDocML.de/1.6/
    // ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
    //                             <akn:act name="regelungstext">
    //                                <!-- Metadaten -->
    //                                <akn:meta eId="meta-1"
    // GUID="82a65581-0ea7-4525-9190-35ff86c977af">
    //                                  <akn:identification eId="meta-1_ident-1"
    // GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
    //                                     <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1"
    // GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
    //                                        <akn:FRBRthis
    // eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78"
    // value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
    //                                        <akn:FRBRalias
    // GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2"
    // name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
    //                                        <akn:FRBRalias
    // GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1"
    // name="aktuelle-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
    //                                        <akn:FRBRalias
    // GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2"
    // name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
    //                                     </akn:FRBRExpression>
    //                                 </akn:identification>
    //                                   <akn:lifecycle eId="meta-1_lebzykl-1"
    // GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
    //                                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1"
    // GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29"
    //                                          source="attributsemantik-noch-undefiniert"
    // type="generation" refersTo="ausfertigung" />
    //                                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2"
    // GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30"
    //                                          source="attributsemantik-noch-undefiniert"
    // type="generation" refersTo="inkrafttreten" />
    //                                      <akn:eventRef eId="meta-1_lebzykl-1_ereignis-3"
    // GUID="860d4338-04a0-419f-a3d1-e7dbefd36d8b" date="2024-12-01"
    //                                          source="attributsemantik-noch-undefiniert"
    // type="generation" refersTo="inkrafttreten" />
    //                                   </akn:lifecycle>
    //                                   <akn:temporalData eId="meta-1_geltzeiten-1"
    // GUID="82854d32-d922-43d7-ac8c-612c07219336" source="attributsemantik-noch-undefiniert">
    //                                       <akn:temporalGroup
    // eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396">
    //                                          <akn:timeInterval
    // eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1"
    // GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" refersTo="geltungszeit"
    // start="#meta-1_lebzykl-1_ereignis-2" />
    //                                       </akn:temporalGroup>
    //                                       <akn:temporalGroup
    // eId="meta-1_geltzeiten-1_geltungszeitgr-2" GUID="f49f536c-57e9-4c5e-9edd-4b827340279e">
    //                                          <akn:timeInterval
    // eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1"
    // GUID="63eedd45-6a2c-4213-bbc4-27596255c1b7" refersTo="geltungszeit"
    // start="#meta-1_lebzykl-1_ereignis-3" />
    //                                       </akn:temporalGroup>
    //                                   </akn:temporalData>
    //                                </akn:meta>
    //                             </akn:act>
    //                          </akn:akomaNtoso>
    //                        """
    //              .strip();
    //
    //      // When
    //      var norm = Norm.builder().document(XmlMapper.toDocument(xml)).build();
    //      normRepository.save(NormMapper.mapToDto(norm));
    //
    //      // Then
    //      mockMvc
    //          .perform(
    //              put("/api/v1/norms/{eli}/timeBoundaries", eli)
    //                  .accept(MediaType.APPLICATION_JSON)
    //                  .contentType(MediaType.APPLICATION_JSON)
    //                  .content(
    //                      "[{\"date\": \"2023-12-30\", \"eid\":
    // \"meta-1_lebzykl-1_ereignis-2\"},{\"date\": \"2024-01-01\", \"eid\": null}]"))
    //          .andExpect(status().isOk())
    //          .andExpect(jsonPath("$", hasSize(2)))
    //
    //          // still there
    //          .andExpect(jsonPath("$[0].date", is("2023-12-30")))
    //          .andExpect(jsonPath("$[0].eid", is("meta-1_lebzykl-1_ereignis-2")))
    //
    //          // expect new
    //          .andExpect(jsonPath("$[1].date", is("2024-01-01")))
    //          .andExpect(jsonPath("$[1].eid", is("meta-1_lebzykl-1_ereignis-4")));
    //    }
  }
}
