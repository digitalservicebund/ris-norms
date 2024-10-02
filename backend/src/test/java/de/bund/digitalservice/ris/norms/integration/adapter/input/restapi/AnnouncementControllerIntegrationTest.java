package de.bund.digitalservice.ris.norms.integration.adapter.input.restapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.NormDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AnnouncementMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AnnouncementRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormRepository;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

class AnnouncementControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AnnouncementRepository announcementRepository;

  @Autowired
  private NormRepository normRepository;

  @AfterEach
  void cleanUp() {
    announcementRepository.deleteAll();
    normRepository.deleteAll();
  }

  @Nested
  class getAllAnnouncements {

    @Test
    void itReturnsEmptyListOfAnnouncements() throws Exception {
      // given no announcement in the DB

      // when
      mockMvc
        .perform(get("/api/v1/announcements").accept(MediaType.APPLICATION_JSON))
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").doesNotExist());
    }

    @Test
    void itReturnsAllAnnouncementsNorm() throws Exception {
      // Given
      var norm = Norm
        .builder()
        .document(
          XmlMapper.toDocument(
            """
                            <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                                   xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                                       http://Inhaltsdaten.LegalDocML.de/1.7/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
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
                                            <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1"
                                                                   GUID="ea61dfec-d89c-442a-9f6d-cb65d8ed2dc3">
                                               <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"
                                                             GUID="d74e4be8-c15d-4a9f-8ae6-781e522dc7a4"
                                                                 value="eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/2022-08-23/regelungstext-1.xml"/>
                                               <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"
                                                            GUID="6e12c94c-f206-4144-bedf-dcab30867f4c"
                                                                value="eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/2022-08-23/regelungstext-1.xml"/>
                                               <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"
                                                             GUID="791a8124-d12e-45e1-9c80-5f0438e4d046"
                                                             date="2022-08-23"
                                                             name="generierung"/>
                                               <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"
                                                               GUID="f9d34cba-d819-4468-b6a7-4a3d76046a26"
                                                               href="recht.bund.de"/>
                                               <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"
                                                               GUID="dcf3aa47-de13-4ef6-9dce-1325a121fb4d"
                                                               value="xml"/>
                                            </akn:FRBRManifestation>
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
            """
          )
        )
        .build();
      var announcement = Announcement.builder().norm(norm).releasedByDocumentalistAt(null).build();
      announcementRepository.save(AnnouncementMapper.mapToDto(announcement));

      // When
      mockMvc
        .perform(get("/api/v1/announcements").accept(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").exists())
        .andExpect(jsonPath("$[1]").doesNotExist())
        .andExpect(
          jsonPath(
            "$[0].title",
            equalTo("Gesetz zum ersten Teil der Reform des Nachrichtendienstrechts")
          )
        )
        .andExpect(
          jsonPath("$[0].eli", equalTo("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1"))
        );
    }
  }

  @Nested
  class getRelease {

    @Test
    void itDoesNotReturnReleaseBecauseAmendingLawNotFound() throws Exception {
      // given no announcement is stored in the database

      // when
      mockMvc
        .perform(
          get(
            "/api/v1/announcements/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/release"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        // then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/announcement-not-found"))
        .andExpect(jsonPath("title").value("Announcement not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Announcement for norm with eli eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1 does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/announcements/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/release"
            )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1")
        );
    }

    @Test
    void itDoesNotReturnReleaseButReturnsNotFoundIfTargetLawNotFound() throws Exception {
      // Given
      var amendingNorm = Norm
        .builder()
        .document(
          XmlMapper.toDocument(
            """
                 <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                       http://Inhaltsdaten.LegalDocML.de/1.7/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
               <akn:act name="regelungstext">

                  <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                     <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                        <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                           <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="f3805314-bbb6-4def-b82b-8b7f0b126197" value="eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1" />
                           <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                           <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                        </akn:FRBRExpression>
                            <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1"
                                                   GUID="ea61dfec-d89c-442a-9f6d-cb65d8ed2dc3">
                               <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"
                                             GUID="d74e4be8-c15d-4a9f-8ae6-781e522dc7a4"
                                                 value="eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/2022-08-23/regelungstext-1.xml"/>
                               <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"
                                            GUID="6e12c94c-f206-4144-bedf-dcab30867f4c"
                                                value="eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/2022-08-23/regelungstext-1.xml"/>
                               <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"
                                             GUID="791a8124-d12e-45e1-9c80-5f0438e4d046"
                                             date="2022-08-23"
                                             name="generierung"/>
                               <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"
                                               GUID="f9d34cba-d819-4468-b6a7-4a3d76046a26"
                                               href="recht.bund.de"/>
                               <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"
                                               GUID="dcf3aa47-de13-4ef6-9dce-1325a121fb4d"
                                               value="xml"/>
                            </akn:FRBRManifestation>
                     </akn:identification>
                  </akn:meta>
                  <akn:body eId="hauptteil-1" GUID="0B4A8E1F-65EF-4B7C-9E22-E83BA6B73CD8">
                     <!-- Artikel 1 : Hauptänderung -->
                     <akn:article eId="hauptteil-1_art-1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#geltungszeitgr-1" refersTo="hauptaenderung">
                        <akn:num eId="hauptteil-1_art-1_bezeichnung-1" GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">
                               Artikel 1</akn:num>
                        <akn:heading eId="hauptteil-1_art-1_überschrift-1" GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes</akn:heading>
                        <!-- Absatz (1) -->
                        <akn:paragraph eId="hauptteil-1_art-1_abs-1" GUID="48ead358-f901-41d3-a135-e372d5ef97b1">
                           <akn:num eId="hauptteil-1_art-1_abs-1_bezeichnung-1" GUID="ef3a32d2-df20-4978-914b-cd6288872208">

                           </akn:num>
                           <akn:list eId="hauptteil-1_art-1_abs-1_untergl-1" GUID="41675622-ed62-46e3-869f-94d99908b010">
                              <akn:intro eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1" GUID="5d6fc824-7926-43b4-b243-b0096da183f9">
                                 <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1" GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5"> Das <akn:affectedDocument eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1" GUID="88b3b9f3-e4a8-49c6-9320-b5b42150bcc5"
                                       href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1">Vereinsgesetz vom 5. August 1964 (BGBl. I S. 593), das zuletzt durch … geändert worden ist</akn:affectedDocument>, wird wie folgt geändert:</akn:p>
                              </akn:intro>
                           </akn:list>
                        </akn:paragraph>
                     </akn:article>
                      <!-- Artikel 3: Geltungszeitregel-->
                      <akn:article eId="hauptteil-1_art-3" GUID="aaae12b5-0c74-4e51-a286-d6051ff5d21b" period="#geltungszeitgr-1" refersTo="geltungszeitregel">
                         <akn:num eId="hauptteil-1_art-3_bezeichnung-1" GUID="1bc12642-f00c-4b55-8388-5e8870e6e706">
                                Artikel 3</akn:num>
                         <akn:heading eId="hauptteil-1_art-3_überschrift-1" GUID="59a7dc28-e095-4da6-ba78-278a0d69a3fd">Inkrafttreten</akn:heading>
                         <!-- Absatz (1) -->
                         <akn:paragraph eId="hauptteil-1_art-3_abs-1" GUID="0b1590b0-8945-44a0-bf44-ebfb7d8c3bd8">
                            <akn:num eId="hauptteil-1_art-3_abs-1_bezeichnung-1" GUID="c46a1cbc-f823-4a18-9b0c-0f131244a58e">

                            </akn:num>
                            <akn:content eId="hauptteil-1_art-3_abs-1_inhalt-1" GUID="3e004e1f-f1bc-42a7-8042-2c1d77df81aa">
                               <akn:p eId="hauptteil-1_art-3_abs-1_inhalt-1_text-1" GUID="52406e40-b866-410c-b097-af69e6248f58"> Dieses Gesetz tritt <akn:date eId="hauptteil-1_art-3_abs-1_inhalt-1_text-1_datum-1" GUID="2ee89811-5368-46e0-acf8-a598506cc956" date="2017-03-16" refersTo="inkrafttreten-datum">am Tag
                                  nach der Verkündung</akn:date> in Kraft. </akn:p>
                            </akn:content>
                         </akn:paragraph>
                      </akn:article>
                  </akn:body>
               </akn:act>
            </akn:akomaNtoso>
            """
          )
        )
        .build();

      var announcement = Announcement
        .builder()
        .norm(amendingNorm)
        .releasedByDocumentalistAt(null)
        .build();
      announcementRepository.save(AnnouncementMapper.mapToDto(announcement));

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/announcements/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/release"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Norm with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1 does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/announcements/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/release"
            )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
        );
    }

    @Test
    void itReturnsRelease() throws Exception {
      // Given
      var amendingNorm = Norm
        .builder()
        .document(
          XmlMapper.toDocument(
            """
                 <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                       http://Inhaltsdaten.LegalDocML.de/1.7/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
               <akn:act name="regelungstext">

                  <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                     <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                        <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                           <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="f3805314-bbb6-4def-b82b-8b7f0b126197" value="eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1" />
                           <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                           <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                        </akn:FRBRExpression>
                            <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1"
                                                   GUID="ea61dfec-d89c-442a-9f6d-cb65d8ed2dc3">
                               <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"
                                             GUID="d74e4be8-c15d-4a9f-8ae6-781e522dc7a4"
                                                 value="eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/2022-08-23/regelungstext-1.xml"/>
                               <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"
                                            GUID="6e12c94c-f206-4144-bedf-dcab30867f4c"
                                                value="eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/2022-08-23/regelungstext-1.xml"/>
                               <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"
                                             GUID="791a8124-d12e-45e1-9c80-5f0438e4d046"
                                             date="2022-08-23"
                                             name="generierung"/>
                               <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"
                                               GUID="f9d34cba-d819-4468-b6a7-4a3d76046a26"
                                               href="recht.bund.de"/>
                               <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"
                                               GUID="dcf3aa47-de13-4ef6-9dce-1325a121fb4d"
                                               value="xml"/>
                            </akn:FRBRManifestation>
                     </akn:identification>
                  </akn:meta>
                  <akn:body eId="hauptteil-1" GUID="0B4A8E1F-65EF-4B7C-9E22-E83BA6B73CD8">
                     <!-- Artikel 1 : Hauptänderung -->
                     <akn:article eId="hauptteil-1_art-1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#geltungszeitgr-1" refersTo="hauptaenderung">
                        <akn:num eId="hauptteil-1_art-1_bezeichnung-1" GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">
                               Artikel 1</akn:num>
                        <akn:heading eId="hauptteil-1_art-1_überschrift-1" GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes</akn:heading>
                        <!-- Absatz (1) -->
                        <akn:paragraph eId="hauptteil-1_art-1_abs-1" GUID="48ead358-f901-41d3-a135-e372d5ef97b1">
                           <akn:num eId="hauptteil-1_art-1_abs-1_bezeichnung-1" GUID="ef3a32d2-df20-4978-914b-cd6288872208">

                           </akn:num>
                           <akn:list eId="hauptteil-1_art-1_abs-1_untergl-1" GUID="41675622-ed62-46e3-869f-94d99908b010">
                              <akn:intro eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1" GUID="5d6fc824-7926-43b4-b243-b0096da183f9">
                                 <akn:p eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1" GUID="04879ca1-994b-4eb2-b59b-032e528d9ce5"> Das <akn:affectedDocument eId="hauptteil-1_art-1_abs-1_untergl-1_intro-1_text-1_bezugsdoc-1" GUID="88b3b9f3-e4a8-49c6-9320-b5b42150bcc5"
                                       href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1">Vereinsgesetz vom 5. August 1964 (BGBl. I S. 593), das zuletzt durch … geändert worden ist</akn:affectedDocument>, wird wie folgt geändert:</akn:p>
                              </akn:intro>
                           </akn:list>
                        </akn:paragraph>
                     </akn:article>
                      <!-- Artikel 3: Geltungszeitregel-->
                      <akn:article eId="hauptteil-1_art-3" GUID="aaae12b5-0c74-4e51-a286-d6051ff5d21b" period="#geltungszeitgr-1" refersTo="geltungszeitregel">
                         <akn:num eId="hauptteil-1_art-3_bezeichnung-1" GUID="1bc12642-f00c-4b55-8388-5e8870e6e706">
                                Artikel 3</akn:num>
                         <akn:heading eId="hauptteil-1_art-3_überschrift-1" GUID="59a7dc28-e095-4da6-ba78-278a0d69a3fd">Inkrafttreten</akn:heading>
                         <!-- Absatz (1) -->
                         <akn:paragraph eId="hauptteil-1_art-3_abs-1" GUID="0b1590b0-8945-44a0-bf44-ebfb7d8c3bd8">
                            <akn:num eId="hauptteil-1_art-3_abs-1_bezeichnung-1" GUID="c46a1cbc-f823-4a18-9b0c-0f131244a58e">

                            </akn:num>
                            <akn:content eId="hauptteil-1_art-3_abs-1_inhalt-1" GUID="3e004e1f-f1bc-42a7-8042-2c1d77df81aa">
                               <akn:p eId="hauptteil-1_art-3_abs-1_inhalt-1_text-1" GUID="52406e40-b866-410c-b097-af69e6248f58"> Dieses Gesetz tritt <akn:date eId="hauptteil-1_art-3_abs-1_inhalt-1_text-1_datum-1" GUID="2ee89811-5368-46e0-acf8-a598506cc956" date="2017-03-16" refersTo="inkrafttreten-datum">am Tag
                                  nach der Verkündung</akn:date> in Kraft. </akn:p>
                            </akn:content>
                         </akn:paragraph>
                      </akn:article>
                  </akn:body>
               </akn:act>
            </akn:akomaNtoso>
            """
          )
        )
        .build();
      var affectedNorm = Norm
        .builder()
        .document(
          XmlMapper.toDocument(
            """
              <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                  <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                     xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                         http://Inhaltsdaten.LegalDocML.de/1.7/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                 <akn:act name="regelungstext">
                    <!-- Metadaten -->
                    <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                       <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                          <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                             <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                             <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="af17d907-a88a-4081-a13a-fd4522cd5d1e" name="vorherige-version-id" value="49eec691-392b-4d77-abaf-23eb871132ad" />
                             <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="9c086b80-be09-49e6-9230-4932cfe88c83" name="aktuelle-version-id" value="77167d15-511d-4927-adf3-3c8b0464423c" />
                             <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-3" GUID="960b4c01-c81f-40b1-92c6-d0d223410a49" name="nachfolgende-version-id" value="b0f315a1-620b-4eaf-922c-ea46a7d10c8b" />
                          </akn:FRBRExpression>
                              <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1"
                                                     GUID="ea61dfec-d89c-442a-9f6d-cb65d8ed2dc3">
                                 <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"
                                               GUID="d74e4be8-c15d-4a9f-8ae6-781e522dc7a4"
                                                   value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23/regelungstext-1.xml"/>
                                 <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"
                                              GUID="6e12c94c-f206-4144-bedf-dcab30867f4c"
                                                  value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23/regelungstext-1.xml"/>
                                 <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"
                                               GUID="791a8124-d12e-45e1-9c80-5f0438e4d046"
                                               date="2022-08-23"
                                               name="generierung"/>
                                 <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"
                                                 GUID="f9d34cba-d819-4468-b6a7-4a3d76046a26"
                                                 href="recht.bund.de"/>
                                 <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"
                                                 GUID="dcf3aa47-de13-4ef6-9dce-1325a121fb4d"
                                                 value="xml"/>
                              </akn:FRBRManifestation>
                      </akn:identification>
                    </akn:meta>
                 </akn:act>
              </akn:akomaNtoso>
            """
          )
        )
        .build();
      var affectedNormZf0 = Norm
        .builder()
        .document(
          XmlMapper.toDocument(
            """
              <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                  <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                     xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                         http://Inhaltsdaten.LegalDocML.de/1.7/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                 <akn:act name="regelungstext">
                    <!-- Metadaten -->
                    <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                       <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                          <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                             <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/2023-12-29/1/deu/regelungstext-1" />
                             <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="af17d907-a88a-4081-a13a-fd4522cd5d1e" name="vorherige-version-id" value="77167d15-511d-4927-adf3-3c8b0464423c" />
                             <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="9c086b80-be09-49e6-9230-4932cfe88c83" name="aktuelle-version-id" value="b0f315a1-620b-4eaf-922c-ea46a7d10c8b" />
                             <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-3" GUID="960b4c01-c81f-40b1-92c6-d0d223410a49" name="nachfolgende-version-id" value="960b4c01-c81f-40b1-92c6-ea46a7d10c8b" />
                          </akn:FRBRExpression>
                              <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1"
                                                     GUID="ea61dfec-d89c-442a-9f6d-cb65d8ed2dc3">
                                 <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1"
                                               GUID="d74e4be8-c15d-4a9f-8ae6-781e522dc7a4"
                                                   value="eli/bund/bgbl-1/1964/s593/2023-12-29/1/deu/2022-08-23/regelungstext-1.xml"/>
                                 <akn:FRBRuri eId="meta-1_ident-1_frbrmanifestation-1_frbruri-1"
                                              GUID="6e12c94c-f206-4144-bedf-dcab30867f4c"
                                                  value="eli/bund/bgbl-1/1964/s593/2023-12-29/1/deu/2022-08-23/regelungstext-1.xml"/>
                                 <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1"
                                               GUID="791a8124-d12e-45e1-9c80-5f0438e4d046"
                                               date="2022-08-23"
                                               name="generierung"/>
                                 <akn:FRBRauthor eId="meta-1_ident-1_frbrmanifestation-1_frbrauthor-1"
                                                 GUID="f9d34cba-d819-4468-b6a7-4a3d76046a26"
                                                 href="recht.bund.de"/>
                                 <akn:FRBRformat eId="meta-1_ident-1_frbrmanifestation-1_frbrformat-1"
                                                 GUID="dcf3aa47-de13-4ef6-9dce-1325a121fb4d"
                                                 value="xml"/>
                              </akn:FRBRManifestation>
                      </akn:identification>
                    </akn:meta>
                 </akn:act>
              </akn:akomaNtoso>
            """
          )
        )
        .build();
      var announcement = Announcement
        .builder()
        .norm(amendingNorm)
        .releasedByDocumentalistAt(Instant.parse("2024-01-02T10:20:30.0Z"))
        .build();
      announcementRepository.save(AnnouncementMapper.mapToDto(announcement));
      normRepository.save(NormMapper.mapToDto(affectedNorm));
      normRepository.save(NormMapper.mapToDto(affectedNormZf0));

      // When
      mockMvc
        .perform(
          get(
            "/api/v1/announcements/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/release"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("releaseAt", equalTo("2024-01-02T10:20:30Z")))
        .andExpect(
          jsonPath(
            "amendingLawEli",
            equalTo("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1")
          )
        )
        .andExpect(jsonPath("zf0Elis[0]").exists())
        .andExpect(jsonPath("zf0Elis[1]").doesNotExist())
        .andExpect(
          jsonPath(
            "zf0Elis[0]",
            equalTo("eli/bund/bgbl-1/1964/s593/2023-12-29/1/deu/regelungstext-1")
          )
        );
    }
  }

  @Nested
  class putRelease {

    @Test
    void itDoesNotReleaseBecauseAmendingLawNotFound() throws Exception {
      // given no announcement is stored in the database

      // when
      mockMvc
        .perform(
          put(
            "/api/v1/announcements/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/release"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        // then
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/announcement-not-found"))
        .andExpect(jsonPath("title").value("Announcement not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Announcement for norm with eli eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1 does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/announcements/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/release"
            )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1")
        );
    }

    @Test
    void itDoesNotReleaseButReturnsNotFoundIfTargetLawNotFound() throws Exception {
      // Given
      var amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      var announcement = Announcement
        .builder()
        .norm(amendingNorm)
        .releasedByDocumentalistAt(null)
        .build();
      announcementRepository.save(AnnouncementMapper.mapToDto(announcement));

      // When // Then
      mockMvc
        .perform(
          put(
            "/api/v1/announcements/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/release"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("type").value("/errors/norm-not-found"))
        .andExpect(jsonPath("title").value("Norm not found"))
        .andExpect(jsonPath("status").value(404))
        .andExpect(
          jsonPath("detail")
            .value(
              "Norm with eli eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1 does not exist"
            )
        )
        .andExpect(
          jsonPath("instance")
            .value(
              "/api/v1/announcements/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/release"
            )
        )
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
        );
    }

    @Test
    void itReleaseAnnouncement() throws Exception {
      // Given
      var amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
      var affectedNorm = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");
      var affectedNormZf0 = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
      var announcement = Announcement
        .builder()
        .norm(amendingNorm)
        .releasedByDocumentalistAt(null)
        .build();

      announcementRepository.save(AnnouncementMapper.mapToDto(announcement));
      normRepository.save(NormMapper.mapToDto(affectedNorm));
      normRepository.save(NormMapper.mapToDto(affectedNormZf0));

      // When // Then
      mockMvc
        .perform(
          put(
            "/api/v1/announcements/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/release"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("releaseAt").exists())
        .andExpect(
          jsonPath(
            "amendingLawEli",
            equalTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1")
          )
        )
        .andExpect(jsonPath("zf0Elis[0]").exists())
        .andExpect(jsonPath("zf0Elis[1]").doesNotExist())
        .andExpect(
          jsonPath(
            "zf0Elis[0]",
            equalTo("eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1")
          )
        );
    }
  }

  @Nested
  class postAnnouncement {

    @Test
    void itCreatesANewAnnouncement() throws Exception {
      // Given
      var norm = NormFixtures.loadFromDisk("NormWithMods.xml");
      var affectedNorm = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");

      normRepository.save(NormMapper.mapToDto(affectedNorm));

      var xmlContent = XmlMapper.toString(norm.getDocument());
      var file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      // When // Then
      mockMvc
        .perform(multipart("/api/v1/announcements").file(file).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(
          jsonPath("eli", equalTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"))
        );

      // Assert ZF0 was created
      assertThat(
        normRepository.findFirstByEliExpressionOrderByEliManifestation(
          "eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1"
        )
      )
        .isPresent();

      // Assert that target law has now next-version-guid
      final Optional<NormDto> targetNormDto =
        normRepository.findFirstByEliExpressionOrderByEliManifestation(
          affectedNorm.getExpressionEli().toString()
        );
      assertThat(targetNormDto).isPresent();
      final Norm targetNorm = NormMapper.mapToDomain(targetNormDto.get());
      assertThat(targetNorm.getMeta().getFRBRExpression().getFRBRaliasNextVersionId()).isNotEmpty();
    }

    @Test
    void itFailsIfTheFileIsNotAnXmlFile() throws Exception {
      // Given
      var file = new MockMultipartFile(
        "file",
        "norm.txt",
        "text/plain",
        new ByteArrayInputStream("not-important".getBytes())
      );

      // When // Then
      mockMvc
        .perform(multipart("/api/v1/announcements").file(file).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/not-a-xml-file")))
        .andExpect(jsonPath("fileName", equalTo("norm.txt")))
        .andExpect(jsonPath("contentType", equalTo("text/plain")));
    }

    @Test
    void itFailsIfTheXmlIsNotLdmlDe() throws Exception {
      //Given
      var xmlContent =
        """
            <root>
              <child>Sample content</child>
            </root>
        """;
      var file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      // When // Then
      mockMvc
        .perform(multipart("/api/v1/announcements").file(file).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/not-a-ldml-de-xml-file")))
        .andExpect(jsonPath("fileName", equalTo("norm.xml")));
    }

    @Test
    void itFailsIfTheAffectedNormDoesNotExist() throws Exception {
      // Given
      var norm = NormFixtures.loadFromDisk("NormWithMods.xml");

      var xmlContent = XmlMapper.toString(norm.getDocument());
      var file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      // When // Then
      mockMvc
        .perform(multipart("/api/v1/announcements").file(file).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/active-mod/destination/norm-not-found")))
        .andExpect(
          jsonPath("eli", equalTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"))
        )
        .andExpect(
          jsonPath(
            "destinationEli",
            equalTo("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
          )
        );
    }

    @Test
    void itFailsIfTheNormAlreadyExist() throws Exception {
      // Given
      var norm = NormFixtures.loadFromDisk("NormWithMods.xml");
      var affectedNorm = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");

      normRepository.save(NormMapper.mapToDto(affectedNorm));
      normRepository.save(NormMapper.mapToDto(norm));

      var xmlContent = XmlMapper.toString(norm.getDocument());
      var file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      // When // Then
      mockMvc
        .perform(multipart("/api/v1/announcements").file(file).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/norm-with-eli-exists-already")));
    }

    @Test
    void itFailsIfANormWithSameGuidAlreadyExist() throws Exception {
      // Given
      var normWithSameGuid = NormFixtures.loadFromDisk("NormWithMods.xml");
      var affectedNormForNormWithSameGuid = NormFixtures.loadFromDisk(
        "NormWithoutPassiveModifications.xml"
      );
      var norm = NormFixtures.loadFromDisk("NormWithQuotedStructureMods.xml");
      var affectedNorm = NormFixtures.loadFromDisk("NormWithPassiveModsQuotedStructure.xml");

      normRepository.save(NormMapper.mapToDto(affectedNormForNormWithSameGuid));
      normRepository.save(NormMapper.mapToDto(affectedNorm));
      normRepository.save(NormMapper.mapToDto(norm));

      normWithSameGuid.getMeta().getFRBRExpression().setFRBRaliasCurrentVersionId(norm.getGuid());
      var xmlContent = XmlMapper.toString(normWithSameGuid.getDocument());
      var file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      // When // Then
      mockMvc
        .perform(multipart("/api/v1/announcements").file(file).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/norm-with-guid-exists-already")));
    }

    @Test
    void itFailsIfTheNormIsInvalid() throws Exception {
      // Given
      var norm = NormFixtures.loadFromDisk("NormWithModsXsdInvalid.xml");
      var affectedNorm = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");

      normRepository.save(NormMapper.mapToDto(affectedNorm));

      var xmlContent = XmlMapper.toString(norm.getDocument());
      var file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      // When // Then
      mockMvc
        .perform(multipart("/api/v1/announcements").file(file).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/ldml-de-not-valid")))
        .andExpect(
          jsonPath("errors[0].type", equalTo("/errors/ldml-de-not-valid/cvc-complex-type.4"))
        );
    }

    @Test
    void itFailsIfTheNormIsSchematronInvalid() throws Exception {
      // Given
      var norm = NormFixtures.loadFromDisk("NormWithModsSchematronInvalid.xml");
      var affectedNorm = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml");

      normRepository.save(NormMapper.mapToDto(affectedNorm));

      var xmlContent = XmlMapper.toString(norm.getDocument());
      var file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      // When // Then
      mockMvc
        .perform(multipart("/api/v1/announcements").file(file).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("type", equalTo("/errors/ldml-de-not-schematron-valid")))
        .andExpect(
          jsonPath(
            "errors[0].type",
            equalTo("/errors/ldml-de-not-schematron-valid/failed-assert/SCH-00050-005")
          )
        )
        .andExpect(
          jsonPath(
            "errors[0].xPath",
            equalTo(
              "/Q{http://Inhaltsdaten.LegalDocML.de/1.7/}akomaNtoso[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7/}act[1]"
            )
          )
        )
        .andExpect(
          jsonPath(
            "errors[0].details",
            equalTo("Für ein Gesetz muss eine Eingangsformel verwendet werden.")
          )
        )
        .andExpect(jsonPath("errors[0].eId", equalTo("")))
        .andExpect(
          jsonPath(
            "errors[1].type",
            equalTo("/errors/ldml-de-not-schematron-valid/failed-assert/SCH-00460-000")
          )
        )
        .andExpect(jsonPath("errors[1].eId", equalTo("meta-1_geltzeiten-1")))
        .andExpect(
          jsonPath(
            "errors[2].type",
            equalTo("/errors/ldml-de-not-schematron-valid/failed-assert/SCH-00460-000")
          )
        )
        .andExpect(jsonPath("errors[2].eId", equalTo("meta-1_geltzeiten-1_geltungszeitgr-1")))
        .andExpect(
          jsonPath(
            "errors[3].type",
            equalTo(
              "/errors/ldml-de-not-schematron-valid/failed-assert/SCH-VERKF-hrefLiterals.expression.FRBRauthor"
            )
          )
        )
        .andExpect(
          jsonPath("errors[3].eId", equalTo("meta-1_ident-1_frbrexpression-1_frbrauthor-1"))
        );
    }

    @Test
    void ifCreatesAnnouncementWithForce() throws Exception {
      // Given
      var norm = NormFixtures.loadFromDisk("NormWithMods.xml");
      var announcement = Announcement.builder().norm(norm).build();
      var affectedNorm = NormFixtures.loadFromDisk("NormWithoutPassiveModifications.xml"); // eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"

      normRepository.save(NormMapper.mapToDto(affectedNorm));
      announcementRepository.save(AnnouncementMapper.mapToDto(announcement));

      var xmlContent = XmlMapper.toString(norm.getDocument());
      var file = new MockMultipartFile(
        "file",
        "norm.xml",
        "text/xml",
        new ByteArrayInputStream(xmlContent.getBytes())
      );

      // When // Then
      mockMvc
        .perform(
          multipart("/api/v1/announcements?force=true")
            .file(file)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(
          jsonPath("eli", equalTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"))
        );

      // Assert ZF0 was created
      assertThat(
        normRepository.findFirstByEliExpressionOrderByEliManifestation(
          "eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1"
        )
      )
        .isPresent();

      // Assert that target law has now next-version-guid
      final Optional<NormDto> targetNormDto =
        normRepository.findFirstByEliExpressionOrderByEliManifestation(
          affectedNorm.getExpressionEli().toString()
        );
      assertThat(targetNormDto).isPresent();
      final Norm targetNorm = NormMapper.mapToDomain(targetNormDto.get());
      assertThat(targetNorm.getMeta().getFRBRExpression().getFRBRaliasNextVersionId()).isNotEmpty();
    }
  }
}
