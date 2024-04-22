package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.port.input.ReleaseAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAnnouncementPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateAnnouncementPort;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class ReleaseServiceTest {
  private final LoadAnnouncementPort loadAnnouncementPort = mock(LoadAnnouncementPort.class);
  private final UpdateAnnouncementPort updateAnnouncementPort = mock(UpdateAnnouncementPort.class);
  private final ReleaseService releaseService =
      new ReleaseService(loadAnnouncementPort, updateAnnouncementPort);

  @Test
  void itShouldReleaseAnnouncement() {
    // Given
    var announcement =
        Announcement.builder()
            .norm(
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
                                              <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                                                 <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                                              </akn:FRBRExpression>
                                          </akn:identification>
                                        </akn:meta>
                                     </akn:act>
                                  </akn:akomaNtoso>
                                """))
                    .build())
            .releasedByDocumentalistAt(null)
            .build();
    when(loadAnnouncementPort.loadAnnouncement(any())).thenReturn(Optional.of(announcement));

    // When
    var instantBeforeRelease = Instant.now();
    releaseService.releaseAnnouncement(
        new ReleaseAnnouncementUseCase.Query(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"));

    // Then
    verify(loadAnnouncementPort, times(1))
        .loadAnnouncement(
            argThat(
                argument ->
                    Objects.equals(
                        argument.eli(),
                        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")));

    var commandCaptor = ArgumentCaptor.forClass(UpdateAnnouncementPort.Command.class);
    verify(updateAnnouncementPort, times(1)).updateAnnouncement(commandCaptor.capture());
    assertThat(commandCaptor.getValue().announcement().getNorm()).isEqualTo(announcement.getNorm());
    assertThat(commandCaptor.getValue().announcement().getReleasedByDocumentalistAt())
        .isAfter(instantBeforeRelease);
  }

  @Test
  void itShouldDoNothingForUnknownAnnouncement() {
    // Given
    when(loadAnnouncementPort.loadAnnouncement(any())).thenReturn(Optional.empty());

    // When
    Optional<Announcement> result =
        releaseService.releaseAnnouncement(
            new ReleaseAnnouncementUseCase.Query(
                "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"));

    // Then
    verify(loadAnnouncementPort, times(1))
        .loadAnnouncement(
            argThat(
                argument ->
                    Objects.equals(
                        argument.eli(),
                        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")));
    verify(updateAnnouncementPort, times(0)).updateAnnouncement(any());

    assertThat(result).isEmpty();
  }
}
