package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.port.input.CreateAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAllAnnouncementsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAnnouncementByNormEliUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetNormsAffectedByAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.ReleaseAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.config.SecurityConfig;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Not using SpringBootTest annotation to avoid needing a database connection. Using @Import to load
 * the {@link SecurityConfig} in order to avoid http 401 Unauthorised
 */
@WithMockUser
@WebMvcTest(
  controllers = AnnouncementController.class,
  excludeAutoConfiguration = OAuth2ClientAutoConfiguration.class
)
class AnnouncementControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private LoadAllAnnouncementsUseCase loadAllAnnouncementsUseCase;

  @MockitoBean
  private LoadAnnouncementByNormEliUseCase loadAnnouncementByNormEliUseCase;

  @MockitoBean
  private LoadNormUseCase loadNormUseCase;

  @MockitoBean
  private LoadTargetNormsAffectedByAnnouncementUseCase loadTargetNormsAffectedByAnnouncementUseCase;

  @MockitoBean
  private ReleaseAnnouncementUseCase releaseAnnouncementUseCase;

  @MockitoBean
  private CreateAnnouncementUseCase createAnnouncementUseCase;

  @Nested
  class getAllAnnouncements {

    @Test
    void itReturnsAnnouncements() throws Exception {
      // Given
      var norm1 = Norm
        .builder()
        .regelungstexte(
          Set.of(
            new Regelungstext(
              XmlMapper.toDocument(
                """
                    <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                           xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.1/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                               http://Inhaltsdaten.LegalDocML.de/1.7.1/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                       <akn:act name="regelungstext">
                          <!-- Metadaten -->
                          <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                             <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                <akn:FRBRWork eId="meta-1_ident-1_frbrwork-1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
                                   <akn:FRBRnumber eId="meta-1_ident-1_frbrwork-1_frbrnumber-1" GUID="b82cc174-8fff-43bf-a434-5646de09e807" value="s593" />
                                   <akn:FRBRname eId="meta-1_ident-1_frbrwork-1_frbrname-1" GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" value="BGBl. I" />
                                   <akn:FRBRdate eId="meta-1_ident-1_frbrwork-1_frbrdate-1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung" />
                                </akn:FRBRWork>
                                <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                                   <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                                   <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
                                   <akn:FRBRalias GUID="6c99101d-6bca-41ae-9794-250bd096fead" eId="meta-1_ident-1_frbrexpression-1_frbralias-1" name="aktuelle-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01"/>
                                   <akn:FRBRalias GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" eId="meta-1_ident-1_frbrexpression-1_frbralias-2" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
                                </akn:FRBRExpression>
                            </akn:identification>
                          </akn:meta>

                          <akn:preface eId="einleitung-1" GUID="fc10e89f-fde4-44bf-aa98-b6bdea01f0ea">
                             <akn:longTitle eId="einleitung-1_doktitel-1" GUID="abbb08de-e7e2-40ab-aba0-079ce786e6d6">
                                <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="3e7c2134-d82c-44ba-b50d-bad9790375a0">
                                   <akn:docTitle
                                      eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="8c4eabab-9893-455e-b83b-c46f2453f2fb">Gesetz zur Regelung des öffentlichen Vereinsrechts</akn:docTitle>
                                </akn:p>
                             </akn:longTitle>
                          </akn:preface>
                       </akn:act>
                    </akn:akomaNtoso>
                """
              )
            )
          )
        )
        .build();
      var announcement1 = Announcement.builder().eli(norm1.getExpressionEli()).build();

      var norm2 = Norm
        .builder()
        .regelungstexte(
          Set.of(
            new Regelungstext(
              XmlMapper.toDocument(
                """
                    <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                           xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.1/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                               http://Inhaltsdaten.LegalDocML.de/1.7.1/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
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
                """
              )
            )
          )
        )
        .build();
      var announcement2 = Announcement.builder().eli(norm2.getExpressionEli()).build();

      // When
      when(loadAllAnnouncementsUseCase.loadAllAnnouncements())
        .thenReturn(List.of(announcement1, announcement2));
      when(loadNormUseCase.loadNorm(any())).thenReturn(norm1).thenReturn(norm2);

      // When // Then
      mockMvc
        .perform(get("/api/v1/announcements").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[1]").exists())
        .andExpect(jsonPath("$[2]").doesNotExist())
        .andExpect(
          jsonPath("$[0].title", equalTo("Gesetz zur Regelung des öffentlichen Vereinsrechts"))
        )
        .andExpect(
          jsonPath(
            "$[0].eli",
            equalTo("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
          )
        )
        .andExpect(
          jsonPath(
            "$[1].title",
            equalTo("Gesetz zum ersten Teil der Reform des Nachrichtendienstrechts")
          )
        )
        .andExpect(
          jsonPath("$[1].eli", equalTo("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1"))
        );
    }
  }

  @Nested
  class getRelease {

    @Test
    void itReturnsRelease() throws Exception {
      // Given
      var amendingNorm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      var affectedNormZf0 = Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml");
      var announcement = Announcement
        .builder()
        .eli(amendingNorm.getExpressionEli())
        .releases(
          List.of(
            Release
              .builder()
              .releasedAt(Instant.parse("2024-01-02T10:20:30.0Z"))
              .publishedNorms(List.of(amendingNorm, affectedNormZf0))
              .build()
          )
        )
        .build();

      when(loadAnnouncementByNormEliUseCase.loadAnnouncementByNormEli(any()))
        .thenReturn(announcement);

      // When // Then
      mockMvc
        .perform(
          get(
            "/api/v1/announcements/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/releases"
          )
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("[0].releaseAt", equalTo("2024-01-02T10:20:30Z")))
        .andExpect(jsonPath("[0].norms[0]").exists())
        .andExpect(jsonPath("[0].norms[2]").doesNotExist())
        .andExpect(
          jsonPath(
            "[0].norms[0]",
            equalTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml")
          )
        )
        .andExpect(
          jsonPath(
            "[0].norms[1]",
            equalTo("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23/regelungstext-1.xml")
          )
        );
    }
  }

  @Nested
  class putRelease {

    @Test
    void itReleaseAnAnnouncement() throws Exception {
      // Given
      var amendingNorm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      var affectedNormZf0 = Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml");
      var announcement = Announcement
        .builder()
        .eli(amendingNorm.getExpressionEli())
        .releases(
          List.of(
            Release
              .builder()
              .releasedAt(Instant.parse("2024-01-02T10:20:30.0Z"))
              .publishedNorms(List.of(amendingNorm, affectedNormZf0))
              .build()
          )
        )
        .build();

      when(releaseAnnouncementUseCase.releaseAnnouncement(any())).thenReturn(announcement);

      // When // Then
      mockMvc
        .perform(
          post(
            "/api/v1/announcements/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/releases"
          )
            .accept(MediaType.APPLICATION_JSON)
            .with(csrf())
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("releaseAt", equalTo("2024-01-02T10:20:30Z")))
        .andExpect(jsonPath("norms[2]").doesNotExist())
        .andExpect(
          jsonPath(
            "norms[0]",
            equalTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml")
          )
        )
        .andExpect(
          jsonPath(
            "norms[1]",
            equalTo("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23/regelungstext-1.xml")
          )
        );
    }
  }

  @Nested
  class postAnnouncement {

    @Test
    void itCreatesAnAnnouncement() throws Exception {
      // Given
      var norm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      var xmlContent = XmlMapper.toString(norm.getDocument());
      final MockMultipartFile file = new MockMultipartFile(
        "file",
        "norm.txt",
        "text/plain",
        new ByteArrayInputStream(xmlContent.getBytes())
      );
      var announcement = Announcement.builder().eli(norm.getExpressionEli()).build();

      when(createAnnouncementUseCase.createAnnouncement(any())).thenReturn(announcement);
      when(loadNormUseCase.loadNorm(any())).thenReturn(norm);

      // When // Then
      mockMvc
        .perform(
          multipart("/api/v1/announcements")
            .file(file)
            .accept(MediaType.APPLICATION_JSON)
            .with(csrf())
        )
        .andExpect(status().isOk())
        .andExpect(
          jsonPath("eli", equalTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"))
        );
    }

    @Test
    void itCreatesAnAnnouncementWithForce() throws Exception {
      // Given
      var norm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      var xmlContent = XmlMapper.toString(norm.getDocument());
      final MockMultipartFile file = new MockMultipartFile(
        "file",
        "norm.txt",
        "text/plain",
        new ByteArrayInputStream(xmlContent.getBytes())
      );
      var announcement = Announcement.builder().eli(norm.getExpressionEli()).build();

      when(createAnnouncementUseCase.createAnnouncement(any())).thenReturn(announcement);
      when(loadNormUseCase.loadNorm(any())).thenReturn(norm);

      // When // Then
      mockMvc
        .perform(
          multipart("/api/v1/announcements?force=true")
            .file(file)
            .accept(MediaType.APPLICATION_JSON)
            .with(csrf())
        )
        .andExpect(status().isOk())
        .andExpect(
          jsonPath("eli", equalTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"))
        );
    }

    @Test
    void itShouldNotExposeInternalInformationOnUnexpectedErrors() throws Exception {
      // Given
      var norm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      var xmlContent = XmlMapper.toString(norm.getDocument());
      final MockMultipartFile file = new MockMultipartFile(
        "file",
        "norm.txt",
        "text/plain",
        new ByteArrayInputStream(xmlContent.getBytes())
      );
      var announcement = Announcement.builder().eli(norm.getExpressionEli()).build();

      when(createAnnouncementUseCase.createAnnouncement(any())).thenReturn(announcement);
      when(loadNormUseCase.loadNorm(any()))
        .thenThrow(new RuntimeException("Should not be visible in the output"));

      // When // Then
      mockMvc
        .perform(
          multipart("/api/v1/announcements")
            .file(file)
            .accept(MediaType.APPLICATION_JSON)
            .with(csrf())
        )
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("type", equalTo("/errors/internal-server-error")))
        .andExpect(jsonPath("title", equalTo("Internal Server Error")))
        .andExpect(jsonPath("detail", equalTo("An unexpected error has occurred")));
    }
  }
}
