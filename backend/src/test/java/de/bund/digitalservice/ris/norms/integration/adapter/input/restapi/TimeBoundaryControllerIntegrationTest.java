package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.DokumentMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser
public class TimeBoundaryControllerIntegrationTest extends BaseIntegrationTest {

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
  class GetTimeBoundaries {

    @Test
    void itCallsGetTimeBoundariesAndReturns404() throws Exception {
      // Given
      final String eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/thisEliDoesNotExist";

      // When // Then
      mockMvc
        .perform(get("/api/v1/norms/{eli}/timeBoundaries", eli).accept(MediaType.APPLICATION_JSON))
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
      dokumentRepository.save(
        DokumentMapper.mapToDto(
          Fixtures.loadRegelungstextFromDisk("NormWithPassiveModifications.xml")
        )
      );

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/norms/{eli}/timeBoundaries",
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(4)))
        .andExpect(jsonPath("$[0].date", is("1964-09-21")))
        .andExpect(jsonPath("$[0].eventRefEid", is("meta-1_lebzykl-1_ereignis-2")))
        .andExpect(jsonPath("$[1].date", is("2017-03-23")))
        .andExpect(jsonPath("$[1].eventRefEid", is("meta-1_lebzykl-1_ereignis-4")))
        .andExpect(jsonPath("$[2].date", is("2019-01-01")))
        .andExpect(jsonPath("$[2].eventRefEid", is("meta-1_lebzykl-1_ereignis-5")))
        .andExpect(jsonPath("$[3].date", is("2017-03-01")))
        .andExpect(jsonPath("$[3].eventRefEid", is("meta-1_lebzykl-1_ereignis-6")));
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
    void itCallsUpdateTimeBoundariesDateNull() throws Exception {
      // When
      var eli = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1";
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml"))
      );

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

      // When
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml"))
      );

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

      // When
      dokumentRepository.save(
        DokumentMapper.mapToDto(Fixtures.loadRegelungstextFromDisk("SimpleNorm.xml"))
      );

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
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                   http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
           <akn:act name="regelungstext">
              <!-- Metadaten -->
              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                   <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8a">
                      <akn:FRBRthis eId="meta-1_ident-1_frbrwork-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c7a" value="eli/bund/bgbl-1/1964/s593/regelungstext-1" />
                      <akn:FRBRuri eId="meta-1_ident-1_frbrwork-1_frbruri-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c7b" value="eli/bund/bgbl-1/1964/s593" />
                   </akn:FRBRWork>
                   <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                      <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                      <akn:FRBRuri eId="meta-1_ident-1_frbrexpression-1_frbruri-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c79" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu" />
                      <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                      <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                      <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                   </akn:FRBRExpression>
                   <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1" GUID="ea61dfec-d89c-442a-9f6d-cb65d8ed2dc3">
                      <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1" GUID="d74e4be8-c15d-4a9f-8ae6-781e522dc7a4" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23/regelungstext-1.xml"/>
                      <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1" GUID="d74e4be8-c15d-4a9f-8ae6-781e522dc7a6" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23/regelungstext-1.xml"/>
                      <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1" GUID="791a8124-d12e-45e1-9c80-5f0438e4d046" date="2022-08-23" name="generierung"/>
                   </akn:FRBRManifestation>
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
      dokumentRepository.save(
        DokumentMapper.mapToDto(new Regelungstext(XmlMapper.toDocument(xml)))
      );

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
