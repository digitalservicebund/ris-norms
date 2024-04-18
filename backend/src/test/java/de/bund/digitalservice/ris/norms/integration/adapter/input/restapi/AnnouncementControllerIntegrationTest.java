package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AnnouncementMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AnnouncementRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

class AnnouncementControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private AnnouncementRepository announcementRepository;

  @AfterEach
  void cleanUp() {
    announcementRepository.deleteAll();
  }

  @Test
  void itReturnsAllAnnouncementsNorm() throws Exception {
    // Given
    var norm =
        Norm.builder()
            .document(
                XmlMapper.toDocument(
                    """
                        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                           xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                               http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                           <akn:act name="regelungstext">
                              <!-- Metadaten -->
                              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                                 <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                    <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
                                       <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="b82cc174-8fff-43bf-a434-5646de09e807" value="413" />
                                       <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" value="BGBl. I" />
                                       <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="2023-12-29" name="verkuendungsfassung" />
                                    </akn:FRBRWork>
                                    <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                                       <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1" />
                                       <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                                       <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                                       <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                                    </akn:FRBRExpression>
                                </akn:identification>
                              </akn:meta>

                              <akn:preface eId="einleitung-1" GUID="fc10e89f-fde4-44bf-aa98-b6bdea01f0ea">
                                 <akn:longTitle eId="einleitung-1_doktitel-1" GUID="abbb08de-e7e2-40ab-aba0-079ce786e6d6">
                                    <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="3e7c2134-d82c-44ba-b50d-bad9790375a0">
                                       <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="e08874b2-05a8-4d6e-9d78-7be24380c54b">Gesetz zum ersten Teil der Reform des Nachrichtendienstrechts</akn:docTitle>
                                    </akn:p>
                                 </akn:longTitle>
                              </akn:preface>
                           </akn:act>
                        </akn:akomaNtoso>
                        """))
            .build();
    var announcement = Announcement.builder().norm(norm).releasedByDocumentalistAt(null).build();
    announcementRepository.save(AnnouncementMapper.mapToDto(announcement));

    // When // Then
    mockMvc
        .perform(get("/api/v1/announcements").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").exists())
        .andExpect(jsonPath("$[1]").doesNotExist())
        .andExpect(
            jsonPath(
                "$[0].title",
                equalTo("Gesetz zum ersten Teil der Reform des Nachrichtendienstrechts")))
        .andExpect(
            jsonPath(
                "$[0].eli", equalTo("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1")));
  }
}
