package de.bund.digitalservice.ris.norms.integration.adapter.output.database;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AnnouncementDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.DokumentDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.ReleaseDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AnnouncementMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.MigrationLogMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.ReleaseMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AnnouncementRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.MigrationLogRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.ReleaseRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.service.DBService;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.integration.BaseIntegrationTest;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DBServiceIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private DBService dbService;

  @Autowired
  private AnnouncementRepository announcementRepository;

  @Autowired
  private DokumentRepository dokumentRepository;

  @Autowired
  private ReleaseRepository releaseRepository;

  @Autowired
  private MigrationLogRepository migrationLogRepository;

  @AfterEach
  void cleanUp() {
    announcementRepository.deleteAll();
    releaseRepository.deleteAll();
    dokumentRepository.deleteAll();
    migrationLogRepository.deleteAll();
  }

  @Test
  void itFindsNormOnDB() {
    // Given
    var norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
    dokumentRepository.saveAll(NormMapper.mapToDtos(norm));

    // When
    final Optional<Norm> normOptional = dbService.loadNorm(
      new LoadNormPort.Command(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
        )
      )
    );

    // Then
    assertThat(normOptional).isPresent().satisfies(normDb -> assertThat(normDb).contains(norm));
  }

  @Test
  void itFindsNormByManifestationEliWithoutPointInTimeManifestationOnDB() {
    // Given
    var norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
    dokumentRepository.saveAll(NormMapper.mapToDtos(norm));

    // When
    final Optional<Norm> normOptional = dbService.loadNorm(
      new LoadNormPort.Command(
        DokumentManifestationEli.fromString(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1.xml"
        )
      )
    );

    // Then
    assertThat(normOptional).isPresent().satisfies(normDb -> assertThat(normDb).contains(norm));
  }

  @Test
  void itFindsNormByManifestationEli() {
    // Given
    var norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
    dokumentRepository.saveAll(NormMapper.mapToDtos(norm));

    // When
    final Optional<Norm> normOptional = dbService.loadNorm(
      new LoadNormPort.Command(
        DokumentManifestationEli.fromString(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
        )
      )
    );

    // Then
    assertThat(normOptional).isPresent().satisfies(normDb -> assertThat(normDb).contains(norm));
  }

  @Test
  void itFindsNewestManifestationOfNorm() {
    // Given
    dokumentRepository.saveAll(
      NormMapper.mapToDtos(Fixtures.loadNormFromDisk("NormWithoutPassiveModifications.xml"))
    );
    var norm = Fixtures.loadNormFromDisk("NormWithPassiveModifications.xml");
    dokumentRepository.saveAll(NormMapper.mapToDtos(norm));

    // When
    final Optional<Norm> normOptional = dbService.loadNorm(
      new LoadNormPort.Command(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
        )
      )
    );

    // Then
    assertThat(normOptional).isPresent().satisfies(normDb -> assertThat(normDb).contains(norm));
  }

  @Test
  void itFindsNormByGuidOnDB() {
    // When
    var norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
    dokumentRepository.saveAll(NormMapper.mapToDtos(norm));

    // When
    final Optional<Norm> normOptional = dbService.loadNormByGuid(
      new LoadNormByGuidPort.Command(UUID.fromString("77167d15-511d-4927-adf3-3c8b0464423c"))
    );

    // Then
    assertThat(normOptional).isPresent().contains(norm);
  }

  @Test
  void itFindsAnnouncementOnDB() {
    // Given
    var norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
    var announcement = Announcement.builder().eli(norm.getExpressionEli()).build();
    announcementRepository.save(AnnouncementMapper.mapToDto(announcement));

    // When
    final Optional<Announcement> announcementOptional = dbService.loadAnnouncementByNormEli(
      new LoadAnnouncementByNormEliPort.Command(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
        )
      )
    );

    // Then
    assertThat(announcementOptional).isPresent().contains(announcement);
  }

  @Test
  void itLoadsAllAnnouncementsFromDB() {
    // Given
    final String xml1 =
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
                       <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                       <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                       <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="91238a23-4321-31ac-34ad-87ad62e89f01" />
                    </akn:FRBRExpression>
                        <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1" GUID="ea61dfec-d89c-442a-9f6d-cb65d8ed2dc3">
                               <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1" GUID="d74e4be8-c15d-4a9f-8ae6-781e522dc7a4" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23/regelungstext-1.xml"/>
                           <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1" GUID="791a8124-d12e-45e1-9c80-5f0438e4d046" date="2022-08-23" name="generierung"/>
                        </akn:FRBRManifestation>
                </akn:identification>
              </akn:meta>
           </akn:act>
        </akn:akomaNtoso>
      """;

    final String xml2 =
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
                       <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="f3805314-bbb6-4def-b82b-8b7f0b126197" value="eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1" />
                       <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                       <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                       <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                    </akn:FRBRExpression>
                        <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1" GUID="ea61dfec-d89c-442a-9f6d-cb65d8ed2dc3">
                               <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1" GUID="d74e4be8-c15d-4a9f-8ae6-781e522dc7a4" value="eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/2022-08-23/regelungstext-1.xml"/>
                           <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1" GUID="791a8124-d12e-45e1-9c80-5f0438e4d046" date="2022-08-23" name="generierung"/>
                        </akn:FRBRManifestation>
                </akn:identification>
              </akn:meta>
           </akn:act>
        </akn:akomaNtoso>
      """;

    // When
    var norm1 = Norm
      .builder()
      .regelungstexte(Set.of(new Regelungstext(XmlMapper.toDocument(xml1))))
      .build();
    var announcement1 = Announcement.builder().eli(norm1.getExpressionEli()).build();
    announcementRepository.save(AnnouncementMapper.mapToDto(announcement1));
    var norm2 = Norm
      .builder()
      .regelungstexte(Set.of(new Regelungstext(XmlMapper.toDocument(xml2))))
      .build();
    var announcement2 = Announcement.builder().eli(norm2.getExpressionEli()).build();
    announcementRepository.save(AnnouncementMapper.mapToDto(announcement2));

    // When
    final List<Announcement> announcements = dbService.loadAllAnnouncements();

    // Then
    assertThat(announcements).containsExactly(announcement2, announcement1);
  }

  @Test
  void itUpdatesNorm() {
    // Given
    var oldNorm = Fixtures.loadNormFromDisk("NormWithAppliedQuotedStructure.xml");
    oldNorm.setPublishState(NormPublishState.UNPUBLISHED);
    dokumentRepository.saveAll(NormMapper.mapToDtos(oldNorm));

    var newNorm = new Norm(oldNorm);
    newNorm.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);

    // When
    var normFromDatabase = dbService.updateNorm(new UpdateNormPort.Command(newNorm));

    // Then
    assertThat(dokumentRepository.findAll()).hasSize(1);
    assertThat(normFromDatabase).contains(newNorm);
  }

  @Test
  void itCreatesNewAnnouncement() {
    // Given
    final String xml =
      """
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
            <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.1/ ../../../Grammatiken/legalDocML.de-metadaten.xsd
                                   http://Inhaltsdaten.LegalDocML.de/1.7.1/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
           <akn:act name="regelungstext">
              <!-- Metadaten -->
              <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                 <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                    <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                       <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                       <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                       <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                       <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                    </akn:FRBRExpression>
                        <akn:FRBRManifestation eId="meta-1_ident-1_frbrmanifestation-1" GUID="ea61dfec-d89c-442a-9f6d-cb65d8ed2dc3">
                               <akn:FRBRthis eId="meta-1_ident-1_frbrmanifestation-1_frbrthis-1" GUID="d74e4be8-c15d-4a9f-8ae6-781e522dc7a4" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2022-08-23/regelungstext-1.xml"/>
                           <akn:FRBRdate eId="meta-1_ident-1_frbrmanifestation-1_frbrdate-1" GUID="791a8124-d12e-45e1-9c80-5f0438e4d046" date="2022-08-23" name="generierung"/>
                        </akn:FRBRManifestation>
                </akn:identification>
              </akn:meta>

              <akn:preface eId="einleitung-1" GUID="4554f060-e4ef-43a3-b71f-f30aa25769d6">
                 <akn:longTitle eId="einleitung-1_doktitel-1" GUID="185fcdbe-04f8-4b17-ac7c-2208c7f2f9df">
                    <akn:p eId="einleitung-1_doktitel-1_text-1" GUID="a9694e02-330d-40e3-b0d1-50b2059f020c">
                       <akn:docStage eId="einleitung-1_doktitel-1_text-1_docstadium-1" GUID="884b29f7-584f-41e2-9329-d8780d33a3d7">Verkündungsfassung</akn:docStage>
                       <akn:docTitle eId="einleitung-1_doktitel-1_text-1_doctitel-1" GUID="e08874b2-05a8-4d6e-9d78-7be24380c54b">Gesetz zum ersten Teil der
                          Reform des Nachrichtendienstrechts</akn:docTitle>
                    </akn:p>
                 </akn:longTitle>
                 <akn:block eId="einleitung-1_block-1" GUID="a0973d49-d628-42f7-a1da-b004bc980a44" name="attributsemantik-noch-undefiniert">
                    <akn:date eId="einleitung-1_block-1_datum-1" GUID="f20d437a-3058-4747-8b8b-9b1e06c17273" refersTo="ausfertigung-datum" date="2023-12-29">Vom
                       29.12.2023</akn:date>
                 </akn:block>
              </akn:preface>
           </akn:act>
        </akn:akomaNtoso>
      """;
    var norm = Norm
      .builder()
      .regelungstexte(Set.of(new Regelungstext(XmlMapper.toDocument(xml))))
      .build();
    var announcement = Announcement.builder().eli(norm.getExpressionEli()).build();

    // When
    var announcementFromDatabase = dbService.updateOrSaveAnnouncement(
      new UpdateOrSaveAnnouncementPort.Command(announcement)
    );

    // Then
    assertThat(announcementFromDatabase).isEqualTo(announcement);
  }

  @Test
  void itFindsReleaseOnDB() {
    // Given
    var norm1 = Fixtures.loadNormFromDisk("SimpleNorm.xml");
    var norm1Dtos = dokumentRepository.saveAll(NormMapper.mapToDtos(norm1));
    var norm2Dtos = dokumentRepository.saveAll(
      NormMapper.mapToDtos(Fixtures.loadNormFromDisk("NormWithMods.xml"))
    );
    var release1 = releaseRepository.save(
      ReleaseDto
        .builder()
        .releasedAt(Instant.parse("2024-01-01T00:00:00Z"))
        .norms(norm2Dtos)
        .build()
    );
    var release2 = releaseRepository.save(
      ReleaseDto
        .builder()
        .releasedAt(Instant.parse("2024-02-01T00:00:00Z"))
        .norms(norm1Dtos)
        .build()
    );
    announcementRepository.save(
      AnnouncementDto
        .builder()
        .eli(norm1.getExpressionEli().toString())
        .releases(List.of(release1, release2))
        .build()
    );

    // When
    final Optional<Release> releaseOptional = dbService.loadLatestRelease(
      new LoadLatestReleasePort.Command(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
        )
      )
    );

    // Then
    assertThat(releaseOptional).isPresent();
    assertThat(releaseOptional.get().getReleasedAt()).isEqualTo("2024-02-01T00:00:00Z");
    assertThat(releaseOptional.get().getPublishedNorms()).hasSize(1);
    assertThat(
      releaseOptional.get().getPublishedNorms().stream().findFirst().get().getManifestationEli()
    )
      .isEqualTo(
        DokumentManifestationEli.fromString(
          "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
        )
      );
  }

  @Nested
  class saveReleaseToAnnouncement {

    @Test
    void itUpdatesAnnouncementAndSavesRelease() {
      // Given
      var norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
      var announcement = Announcement.builder().eli(norm.getExpressionEli()).build();
      var release = Release.builder().publishedNorms(List.of(norm)).build();

      dokumentRepository.saveAll(NormMapper.mapToDtos(norm));
      announcementRepository.save(AnnouncementMapper.mapToDto(announcement));

      // When
      dbService.saveReleaseToAnnouncement(
        new SaveReleaseToAnnouncementPort.Command(release, announcement)
      );

      var savedAnnouncement = announcementRepository.findByEli(norm.getExpressionEli().toString());

      assertThat(savedAnnouncement).isPresent();
      assertThat(savedAnnouncement.get().getReleases()).hasSize(1);
      assertThat(savedAnnouncement.get().getReleases().getFirst().getNorms()).hasSize(1);
      assertThat(
        savedAnnouncement
          .get()
          .getReleases()
          .getFirst()
          .getNorms()
          .getFirst()
          .getEliDokumentManifestation()
      )
        .isEqualTo(norm.getManifestationEli().toString());
    }
  }

  @Nested
  class deleteQueuedReleases {

    @Test
    void itDeletesQueuedReleases() {
      // Given
      var norm = Fixtures.loadNormFromDisk("SimpleNorm.xml");
      norm.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);
      var announcement = Announcement.builder().eli(norm.getExpressionEli()).build();
      var release = Release.builder().publishedNorms(List.of(norm)).build();

      var normDto = dokumentRepository.saveAll(NormMapper.mapToDtos(norm));

      var releaseDto = ReleaseMapper.mapToDto(release);
      releaseDto.setNorms(normDto);
      releaseRepository.save(releaseDto);

      var announcementDto = AnnouncementMapper.mapToDto(announcement);
      announcementDto.setReleases(List.of(releaseDto));
      announcementRepository.save(announcementDto);

      // When
      dbService.deleteQueuedReleases(new DeleteQueuedReleasesPort.Command(announcement.getEli()));

      // Then
      var savedAnnouncement = announcementRepository.findByEli(norm.getExpressionEli().toString());
      assertThat(savedAnnouncement).isPresent();
      assertThat(savedAnnouncement.get().getReleases()).isEmpty();

      var savedNorm = dokumentRepository.findByEliDokumentManifestation(
        norm.getManifestationEli().toString()
      );
      assertThat(savedNorm).isPresent();
    }
  }

  @Nested
  class loadNormsByPublishState {

    @Test
    void itLoadsNormIdByPublishState() {
      // Given
      var normQueued = Fixtures.loadNormFromDisk("SimpleNorm.xml");
      normQueued.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);

      var normPublished = Fixtures.loadNormFromDisk("NormToBeReleased.xml");
      normPublished.setPublishState(NormPublishState.PUBLISHED);

      dokumentRepository.saveAll(NormMapper.mapToDtos(normQueued));
      dokumentRepository.saveAll(NormMapper.mapToDtos(normPublished));

      // When
      final List<UUID> publishedNorms = dbService.loadNormIdsByPublishState(
        new LoadNormIdsByPublishStatePort.Command(NormPublishState.QUEUED_FOR_PUBLISH)
      );

      // Then
      assertThat(publishedNorms).isNotEmpty().hasSize(1);
    }

    @Test
    void itLoadsNormById() {
      // Given
      var normQueued = Fixtures.loadNormFromDisk("SimpleNorm.xml");
      normQueued.setPublishState(NormPublishState.QUEUED_FOR_PUBLISH);

      DokumentDto norm = dokumentRepository.save(
        NormMapper.mapToDtos(normQueued).stream().findFirst().get()
      );
      UUID normId = norm.getId();

      // When
      final Optional<Norm> publishedNorm = dbService.loadNormById(
        new LoadNormByIdPort.Command(normId)
      );

      // Then
      assertThat(publishedNorm).isNotEmpty();
      assertThat(XmlMapper.toString(publishedNorm.get().getDocument())).isEqualTo(norm.getXml());
    }
  }

  @Nested
  class loadsMigrationLog {

    @Test
    void itLoadsMigrationLogByDate() {
      // Given
      var date1 = LocalDate.parse("2024-11-06");
      var migrationLog1 = new MigrationLog(5, date1.atStartOfDay().toInstant(ZoneOffset.UTC));

      var date2 = LocalDate.parse("2024-11-05");
      var migrationLog2 = new MigrationLog(12, date2.atStartOfDay().toInstant(ZoneOffset.UTC));

      migrationLogRepository.save(MigrationLogMapper.mapToDto(migrationLog1));
      migrationLogRepository.save(MigrationLogMapper.mapToDto(migrationLog2));

      // When
      final Optional<MigrationLog> migrationLogOptional = dbService.loadMigrationLogByDate(
        new LoadMigrationLogByDatePort.Command(date1)
      );

      // Then
      assertThat(migrationLogOptional)
        .isPresent()
        .satisfies(log -> assertThat(log).contains(migrationLog1));
    }
  }
}
