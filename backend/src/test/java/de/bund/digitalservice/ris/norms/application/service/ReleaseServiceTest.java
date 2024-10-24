package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.port.input.LoadAnnouncementByNormEliUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.ReleaseAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateAnnouncementPort;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ExpressionEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class ReleaseServiceTest {

  private final LoadAnnouncementByNormEliUseCase loadAnnouncementByNormEliUseCase = mock(
    LoadAnnouncementByNormEliUseCase.class
  );
  private final UpdateAnnouncementPort updateAnnouncementPort = mock(UpdateAnnouncementPort.class);
  private final ReleaseService releaseService = new ReleaseService(
    loadAnnouncementByNormEliUseCase,
    updateAnnouncementPort
  );

  @Test
  void itShouldReleaseAnnouncement() {
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
                        <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                           <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                        </akn:FRBRExpression>
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
      .eli(norm.getExpressionEli())
      .releasedByDocumentalistAt(null)
      .build();
    when(loadAnnouncementByNormEliUseCase.loadAnnouncementByNormEli(any()))
      .thenReturn(announcement);

    // When
    var instantBeforeRelease = Instant.now();
    releaseService.releaseAnnouncement(
      new ReleaseAnnouncementUseCase.Query(
        ExpressionEli.fromString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")
      )
    );

    // Then
    verify(loadAnnouncementByNormEliUseCase, times(1))
      .loadAnnouncementByNormEli(
        argThat(argument ->
          Objects.equals(
            argument.eli().toString(),
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
          )
        )
      );

    var commandCaptor = ArgumentCaptor.forClass(UpdateAnnouncementPort.Command.class);
    verify(updateAnnouncementPort, times(1)).updateAnnouncement(commandCaptor.capture());
    assertThat(commandCaptor.getValue().announcement().getEli()).isEqualTo(announcement.getEli());
    assertThat(commandCaptor.getValue().announcement().getReleasedByDocumentalistAt())
      .isAfter(instantBeforeRelease);
  }

  @Nested
  class prepareForRelease {

    @Test
    void removesOrganisationsEinheitFromMetadata() {
      // Given
      var norm = NormFixtures.loadFromDisk("NormToBeReleased.xml");
      var proprietary = norm.getMeta().getProprietary().get();

      assertThat(proprietary.getOrganisationsEinheit(LocalDate.of(2005, 1, 1)))
        .contains("Aktuelle Organisationseinheit");
      assertThat(proprietary.getOrganisationsEinheit(LocalDate.of(2028, 6, 1)))
        .contains("Nächste Organisationseinheit");
      assertThat(proprietary.getOrganisationsEinheit(LocalDate.of(2029, 6, 1)))
        .contains("Übernächste Organisationseinheit");

      // When
      releaseService.prepareForRelease(norm);

      // Then
      assertThat(proprietary.getOrganisationsEinheit(LocalDate.of(2005, 1, 1))).isEmpty();
      assertThat(proprietary.getOrganisationsEinheit(LocalDate.of(2028, 6, 1))).isEmpty();
      assertThat(proprietary.getOrganisationsEinheit(LocalDate.of(2029, 6, 1))).isEmpty();
    }
  }
}
