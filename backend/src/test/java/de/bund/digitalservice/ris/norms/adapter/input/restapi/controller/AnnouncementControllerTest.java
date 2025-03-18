package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.bund.digitalservice.ris.norms.application.exception.AnnouncementNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.CreateAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAllAnnouncementsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SecurelessControllerTest(AnnouncementController.class)
class AnnouncementControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private LoadAllAnnouncementsUseCase loadAllAnnouncementsUseCase;

  @MockitoBean
  private LoadAnnouncementUseCase loadAnnouncementUseCase;

  @MockitoBean
  private LoadNormUseCase loadNormUseCase;

  @MockitoBean
  private CreateAnnouncementUseCase createAnnouncementUseCase;

  @Nested
  class getAllAnnouncements {

    @Test
    void itReturnsAnnouncements() throws Exception {
      // Given
      var norm1 = Norm
        .builder()
        .dokumente(
          Set.of(
            new Regelungstext(
              XmlMapper.toDocument(
                """
                    <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                           xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                               http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
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
                             <akn:block eId="einleitung-1_block-1" GUID="a0973d49-d628-42f7-a1da-b004bc980a44" name="attributsemantik-noch-undefiniert">
                                <akn:date eId="einleitung-1_block-1_datum-1" GUID="f20d437a-3058-4747-8b8b-9b1e06c17273" refersTo="ausfertigung-datum" date="2021-12-21">Vom
                                   29.12.2023</akn:date>
                             </akn:block>
                          </akn:preface>
                       </akn:act>
                    </akn:akomaNtoso>
                """
              )
            )
          )
        )
        .build();
      var announcement1 = Announcement
        .builder()
        .eli(norm1.getExpressionEli())
        .importTimestamp(Instant.parse("2025-03-13T15:00:00Z"))
        .build();

      var norm2 = Norm
        .builder()
        .dokumente(
          Set.of(
            new Regelungstext(
              XmlMapper.toDocument(
                """
                    <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                           xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                               http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
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
                             <akn:block eId="einleitung-1_block-1" GUID="a0973d49-d628-42f7-a1da-b004bc980a44" name="attributsemantik-noch-undefiniert">
                                <akn:date eId="einleitung-1_block-1_datum-1" GUID="f20d437a-3058-4747-8b8b-9b1e06c17273" refersTo="ausfertigung-datum" date="2023-12-30">Vom
                                   29.12.2023</akn:date>
                             </akn:block>
                          </akn:preface>
                       </akn:act>
                    </akn:akomaNtoso>
                """
              )
            )
          )
        )
        .build();
      var announcement2 = Announcement
        .builder()
        .eli(norm2.getExpressionEli())
        .importTimestamp(Instant.parse("2025-03-13T16:00:00Z"))
        .build();

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
        .andExpect(jsonPath("$[0].frbrDateVerkuendung", equalTo("1964-08-05")))
        .andExpect(jsonPath("$[0].dateAusfertigung", equalTo("2021-12-21")))
        .andExpect(jsonPath("$[0].importedAt", equalTo("2025-03-13T15:00:00Z")))
        .andExpect(
          jsonPath(
            "$[1].title",
            equalTo("Gesetz zum ersten Teil der Reform des Nachrichtendienstrechts")
          )
        )
        .andExpect(
          jsonPath("$[1].eli", equalTo("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1"))
        )
        .andExpect(jsonPath("$[1].frbrDateVerkuendung", equalTo("2023-12-29")))
        .andExpect(jsonPath("$[1].dateAusfertigung", equalTo("2023-12-30")))
        .andExpect(jsonPath("$[1].importedAt", equalTo("2025-03-13T16:00:00Z")));
    }
  }

  @Nested
  class getAnnouncement {

    @Test
    void itReturnsAnnouncement() throws Exception {
      // Given
      var norm = Fixtures.loadNormFromDisk("Vereinsgesetz.xml");
      var normEli = norm.getExpressionEli();
      var announcement = Announcement
        .builder()
        .eli(normEli)
        .importTimestamp(Instant.parse("2025-03-13T15:00:00Z"))
        .build();
      // When
      when(
        loadAnnouncementUseCase.loadAnnouncement(
          new LoadAnnouncementUseCase.Query(norm.getExpressionEli())
        )
      )
        .thenReturn(announcement);
      when(loadNormUseCase.loadNorm(any())).thenReturn(norm);

      // When
      mockMvc
        .perform(
          get("/api/v1/announcements/{eli}", normEli.toString()).accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isOk())
        .andExpect(
          jsonPath("eli").value("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
        )
        .andExpect(jsonPath("title").value("Gesetz zur Regelung des öffentlichen Vereinsrechts"))
        .andExpect(jsonPath("shortTitle").value("Vereinsgesetz"))
        .andExpect(jsonPath("fna").value("754-28-1"))
        .andExpect(jsonPath("frbrName").value("BGBl. I"))
        .andExpect(jsonPath("frbrNumber").value("s593"))
        .andExpect(jsonPath("frbrDateVerkuendung").value("1964-08-05"))
        .andExpect(jsonPath("dateAusfertigung").value("1964-08-05"))
        .andExpect(jsonPath("importedAt").value("2025-03-13T15:00:00Z"));

      verify(loadAnnouncementUseCase, times(1))
        .loadAnnouncement(argThat(argument -> Objects.equals(argument.eli(), normEli)));
      verify(loadNormUseCase, times(1))
        .loadNorm(argThat(argument -> Objects.equals(argument.eli(), normEli)));
    }

    @Test
    void itReturns404() throws Exception {
      // Given
      var norm = Fixtures.loadNormFromDisk("Vereinsgesetz.xml");
      var normEli = norm.getExpressionEli();
      // When
      when(loadAnnouncementUseCase.loadAnnouncement(new LoadAnnouncementUseCase.Query(normEli)))
        .thenThrow(new AnnouncementNotFoundException(normEli.toString()));

      // When
      mockMvc
        .perform(
          get("/api/v1/announcements/{eli}", normEli.toString()).accept(MediaType.APPLICATION_JSON)
        )
        // Then
        .andExpect(status().isNotFound());

      verify(loadAnnouncementUseCase, times(1))
        .loadAnnouncement(argThat(argument -> Objects.equals(argument.eli(), normEli)));
      verify(loadNormUseCase, times(0)).loadNorm(any());
    }
  }

  @Nested
  class postAnnouncement {

    @Test
    void itCreatesAnAnnouncement() throws Exception {
      // Given
      var norm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      var xmlContent = XmlMapper.toString(norm.getRegelungstext1().getDocument());
      final MockMultipartFile file = new MockMultipartFile(
        "file",
        "norm.txt",
        "text/plain",
        new ByteArrayInputStream(xmlContent.getBytes())
      );
      var announcement = Announcement
        .builder()
        .eli(norm.getExpressionEli())
        .importTimestamp(Instant.parse("2025-03-13T16:00:00Z"))
        .build();

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
        )
        .andExpect(jsonPath("importedAt", equalTo("2025-03-13T16:00:00Z")));
    }

    @Test
    void itCreatesAnAnnouncementWithForce() throws Exception {
      // Given
      var norm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      var xmlContent = XmlMapper.toString(norm.getRegelungstext1().getDocument());
      final MockMultipartFile file = new MockMultipartFile(
        "file",
        "norm.txt",
        "text/plain",
        new ByteArrayInputStream(xmlContent.getBytes())
      );
      var announcement = Announcement
        .builder()
        .eli(norm.getExpressionEli())
        .importTimestamp(Instant.parse("2025-03-13T16:00:00Z"))
        .build();

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
        )
        .andExpect(jsonPath("importedAt", equalTo("2025-03-13T16:00:00Z")));
    }

    @Test
    void itShouldNotExposeInternalInformationOnUnexpectedErrors() throws Exception {
      // Given
      var norm = Fixtures.loadNormFromDisk("NormWithMods.xml");
      var xmlContent = XmlMapper.toString(norm.getRegelungstext1().getDocument());
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
