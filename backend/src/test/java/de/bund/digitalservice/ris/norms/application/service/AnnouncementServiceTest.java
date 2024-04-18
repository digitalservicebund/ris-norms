package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.port.output.LoadAllAnnouncementsPort;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AnnouncementServiceTest {

  final LoadAllAnnouncementsPort loadAllAnnouncementsPort = mock(LoadAllAnnouncementsPort.class);

  final AnnouncementService service = new AnnouncementService(loadAllAnnouncementsPort);

  @Nested
  class loadAllAnnouncements {

    @Test
    void itReturnsAnnouncements() {
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
              .releasedByDocumentalistAt(Instant.now())
              .build();
      when(loadAllAnnouncementsPort.loadAllAnnouncements()).thenReturn(List.of(announcement));

      // When
      var loadedAnnouncements = service.loadAllAnnouncements();

      // Then
      verify(loadAllAnnouncementsPort, times(1)).loadAllAnnouncements();
      assertThat(loadedAnnouncements).hasSize(1).containsExactly(announcement);
    }
  }
}
