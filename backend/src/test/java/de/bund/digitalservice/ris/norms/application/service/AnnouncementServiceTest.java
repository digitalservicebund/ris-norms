package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.application.exception.AnnouncementNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAnnouncementByNormEliUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetNormsAffectedByAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAllAnnouncementsPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAnnouncementByNormEliPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AnnouncementServiceTest {

  final LoadAllAnnouncementsPort loadAllAnnouncementsPort = mock(LoadAllAnnouncementsPort.class);
  final LoadAnnouncementByNormEliPort loadAnnouncementByNormEliPort =
      mock(LoadAnnouncementByNormEliPort.class);
  final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  final LoadZf0Service loadZf0Service = mock(LoadZf0Service.class);

  final AnnouncementService announcementService =
      new AnnouncementService(
          loadAllAnnouncementsPort, loadAnnouncementByNormEliPort, loadNormPort, loadZf0Service);

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
      var loadedAnnouncements = announcementService.loadAllAnnouncements();

      // Then
      verify(loadAllAnnouncementsPort, times(1)).loadAllAnnouncements();
      assertThat(loadedAnnouncements).hasSize(1).containsExactly(announcement);
    }
  }

  @Nested
  class loadAnnouncement {

    @Test
    void itThrowsAnnouncementNotFoundException() {
      // given
      when(loadAnnouncementByNormEliPort.loadAnnouncementByNormEli(any()))
          .thenReturn(Optional.empty());

      // when
      assertThatThrownBy(
              () ->
                  announcementService.loadAnnouncementByNormEli(
                      new LoadAnnouncementByNormEliUseCase.Query(
                          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1")))
          // then
          .isInstanceOf(AnnouncementNotFoundException.class);
    }

    @Test
    void itReturnsAnnouncement() {
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
      when(loadAnnouncementByNormEliPort.loadAnnouncementByNormEli(any()))
          .thenReturn(Optional.of(announcement));

      // When
      var loadedAnnouncement =
          announcementService.loadAnnouncementByNormEli(
              new LoadAnnouncementByNormEliUseCase.Query(
                  "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"));

      // Then
      verify(loadAnnouncementByNormEliPort, times(1)).loadAnnouncementByNormEli(any());
      assertEquals(loadedAnnouncement.toString(), announcement.toString());
    }
  }

  @Nested
  class loadTargetNormsAffectedByAnnouncement {

    @Test
    void itReturnsNorms() {
      // Given
      var amendingNorm =
          Norm.builder()
              .document(
                  XmlMapper.toDocument(
                      """
                               <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                          <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                             xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                                 http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
                             <akn:act name="regelungstext">

                                <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                                   <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                      <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                                         <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="f3805314-bbb6-4def-b82b-8b7f0b126197" value="eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1" />
                                         <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                                         <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                                      </akn:FRBRExpression>
                                   </akn:identification>
                                </akn:meta>
                                <akn:body eId="hauptteil-1" GUID="0B4A8E1F-65EF-4B7C-9E22-E83BA6B73CD8">
                                   <!-- Artikel 1 : Hauptänderung -->
                                   <akn:article eId="hauptteil-1_art-1" GUID="cdbfc728-a070-42d9-ba2f-357945afef06" period="#geltungszeitgr-1" refersTo="hauptaenderung">
                                      <akn:num eId="hauptteil-1_art-1_bezeichnung-1" GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">
                                         <akn:marker eId="hauptteil-1_art-1_bezeichnung-1_zaehlbez-1" GUID="81c9c481-9427-4f03-9f51-099aa9b2201e" name="1" />Artikel 1</akn:num>
                                      <akn:heading eId="hauptteil-1_art-1_überschrift-1" GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes</akn:heading>
                                      <!-- Absatz (1) -->
                                      <akn:paragraph eId="hauptteil-1_art-1_abs-1" GUID="48ead358-f901-41d3-a135-e372d5ef97b1">
                                         <akn:num eId="hauptteil-1_art-1_abs-1_bezeichnung-1" GUID="ef3a32d2-df20-4978-914b-cd6288872208">
                                            <akn:marker eId="hauptteil-1_art-1_abs-1_bezeichnung-1_zaehlbez-1" GUID="eab5a7e7-b649-4c23-b495-648b8ec71843" name="1" />
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
                                          <akn:marker eId="hauptteil-1_art-3_bezeichnung-1_zaehlbez-1" GUID="7bbcdd71-a27b-4932-91b7-6c18356ed3e5" name="3" />Artikel 3</akn:num>
                                       <akn:heading eId="hauptteil-1_art-3_überschrift-1" GUID="59a7dc28-e095-4da6-ba78-278a0d69a3fd">Inkrafttreten</akn:heading>
                                       <!-- Absatz (1) -->
                                       <akn:paragraph eId="hauptteil-1_art-3_abs-1" GUID="0b1590b0-8945-44a0-bf44-ebfb7d8c3bd8">
                                          <akn:num eId="hauptteil-1_art-3_abs-1_bezeichnung-1" GUID="c46a1cbc-f823-4a18-9b0c-0f131244a58e">
                                             <akn:marker eId="hauptteil-1_art-3_abs-1_bezeichnung-1_zaehlbez-1" GUID="1d41fa26-e36a-4d03-8f4a-4790d3184944" name="1" />
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
                          """))
              .build();
      var affectedNormZf0 =
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
                                           <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/2023-12-29/1/deu/regelungstext-1" />
                                           <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="af17d907-a88a-4081-a13a-fd4522cd5d1e" name="vorherige-version-id" value="77167d15-511d-4927-adf3-3c8b0464423c" />
                                           <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="9c086b80-be09-49e6-9230-4932cfe88c83" name="aktuelle-version-id" value="b0f315a1-620b-4eaf-922c-ea46a7d10c8b" />
                                           <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-3" GUID="960b4c01-c81f-40b1-92c6-d0d223410a49" name="nachfolgende-version-id" value="960b4c01-c81f-40b1-92c6-ea46a7d10c8b" />
                                        </akn:FRBRExpression>
                                    </akn:identification>
                                  </akn:meta>
                               </akn:act>
                            </akn:akomaNtoso>
                          """))
              .build();

      var announcement =
          Announcement.builder()
              .norm(amendingNorm)
              .releasedByDocumentalistAt(Instant.now())
              .build();
      when(loadAnnouncementByNormEliPort.loadAnnouncementByNormEli(any()))
          .thenReturn(Optional.of(announcement));
      // Should return target law but we dont care what is returned, since we also mock the loadZf0
      when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(amendingNorm));
      when(loadZf0Service.loadOrCreateZf0(
              argThat(argument -> argument.amendingLaw().equals(amendingNorm))))
          .thenReturn(affectedNormZf0);

      // When
      var norms =
          announcementService.loadTargetNormsAffectedByAnnouncement(
              new LoadTargetNormsAffectedByAnnouncementUseCase.Query(
                  "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1"));

      // Then
      verify(loadAnnouncementByNormEliPort, times(1)).loadAnnouncementByNormEli(any());
      assertThat(norms).hasSize(1).containsExactly(affectedNormZf0);
    }
  }
}
