package de.bund.digitalservice.ris.norms.adapter.output.database.service;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AnnouncementDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.NormDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.ReleaseDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AnnouncementMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.MigrationLogMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.ReleaseMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AnnouncementRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.MigrationLogRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.ReleaseRepository;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentWorkEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for interacting with the database. This class is annotated with {@link Service} to
 * indicate that it's a service component in the Spring context.
 */
@Service
public class DBService
  implements
    LoadNormPort,
    LoadNormByGuidPort,
    LoadAnnouncementByNormEliPort,
    LoadAllAnnouncementsPort,
    LoadLatestReleasePort,
    UpdateNormPort,
    UpdateAnnouncementPort,
    UpdateOrSaveNormPort,
    UpdateOrSaveAnnouncementPort,
    DeleteAnnouncementByNormEliPort,
    DeleteNormPort,
    DeleteQueuedNormsPort,
    SaveReleaseToAnnouncementPort,
    DeleteQueuedReleasesPort,
    LoadNormIdsByPublishStatePort,
    LoadNormByIdPort,
    LoadMigrationLogByDatePort {

  private final AnnouncementRepository announcementRepository;
  private final NormRepository normRepository;
  private final ReleaseRepository releaseRepository;
  private final MigrationLogRepository migrationLogRepository;

  public DBService(
    AnnouncementRepository announcementRepository,
    NormRepository normRepository,
    ReleaseRepository releaseRepository,
    MigrationLogRepository migrationLogRepository
  ) {
    this.announcementRepository = announcementRepository;
    this.normRepository = normRepository;
    this.releaseRepository = releaseRepository;
    this.migrationLogRepository = migrationLogRepository;
  }

  @Override
  public Optional<Norm> loadNorm(LoadNormPort.Command command) {
    return switch (command.eli()) {
      case DokumentExpressionEli expressionEli -> normRepository
        .findFirstByEliDokumentExpressionOrderByEliDokumentManifestationDesc(
          expressionEli.toString()
        )
        .map(NormMapper::mapToDomain);
      case DokumentManifestationEli manifestationEli -> {
        if (!manifestationEli.hasPointInTimeManifestation()) {
          // we can find the norm based on the expression eli as the point in time manifestation is the only additional identifying part of the eli in our system (all norms are xmls)
          yield this.loadNorm(new LoadNormPort.Command(manifestationEli.asExpressionEli()));
        }

        yield normRepository
          .findByEliDokumentManifestation(manifestationEli.toString())
          .map(NormMapper::mapToDomain);
      }
      case DokumentWorkEli workEli -> throw new IllegalArgumentException(
        "It's currently not possible to load a norm by it's work eli."
      );
    };
  }

  @Override
  public Optional<Norm> loadNormByGuid(LoadNormByGuidPort.Command command) {
    return normRepository
      .findFirstByGuidOrderByEliDokumentManifestation(command.guid())
      .map(NormMapper::mapToDomain);
  }

  @Override
  public Optional<Announcement> loadAnnouncementByNormEli(
    LoadAnnouncementByNormEliPort.Command command
  ) {
    return announcementRepository
      .findByEli(command.eli().toString())
      .map(AnnouncementMapper::mapToDomain);
  }

  @Override
  public List<Announcement> loadAllAnnouncements() {
    return announcementRepository
      .findAll()
      .stream()
      .map(AnnouncementMapper::mapToDomain)
      .sorted(
        Comparator
          .comparing((Announcement announcement) -> announcement.getEli().getPointInTime())
          .reversed()
      )
      .toList();
  }

  @Override
  public Optional<Norm> updateNorm(UpdateNormPort.Command command) {
    var normXml = XmlMapper.toString(command.norm().getDocument());
    return normRepository
      .findByEliDokumentManifestation(command.norm().getManifestationEli().toString())
      .map(normDto -> {
        normDto.setXml(normXml);
        normDto.setPublishState(command.norm().getPublishState());
        return NormMapper.mapToDomain(normRepository.save(normDto));
      });
  }

  @Override
  public Norm updateOrSave(UpdateOrSaveNormPort.Command command) {
    final Optional<Norm> updatedNorm = updateNorm(new UpdateNormPort.Command(command.norm()));
    if (updatedNorm.isEmpty()) {
      final NormDto normDto = NormMapper.mapToDto(command.norm());
      return NormMapper.mapToDomain(normRepository.save(normDto));
    } else {
      return updatedNorm.get();
    }
  }

  @Override
  public Optional<Announcement> updateAnnouncement(UpdateAnnouncementPort.Command command) {
    return announcementRepository
      .findByEli(command.announcement().getEli().toString())
      // There is no field in an announcement that can change, so we do nothing here
      .map(AnnouncementMapper::mapToDomain);
  }

  @Override
  public Announcement updateOrSaveAnnouncement(UpdateOrSaveAnnouncementPort.Command command) {
    final Optional<Announcement> updatedAnnouncement = updateAnnouncement(
      new UpdateAnnouncementPort.Command(command.announcement())
    );
    if (updatedAnnouncement.isEmpty()) {
      final AnnouncementDto announcementDto = AnnouncementMapper.mapToDto(command.announcement());
      return AnnouncementMapper.mapToDomain(announcementRepository.save(announcementDto));
    } else {
      return updatedAnnouncement.get();
    }
  }

  @Override
  @Transactional
  public void deleteAnnouncementByNormEli(DeleteAnnouncementByNormEliPort.Command command) {
    var announcementDto = announcementRepository.findByEli(command.eli().toString());
    announcementRepository.deleteByEli(command.eli().toString());
    announcementDto.ifPresent(dto -> releaseRepository.deleteAll(dto.getReleases()));
  }

  @Override
  @Transactional
  public void deleteNorm(DeleteNormPort.Command command) {
    normRepository.deleteByEliDokumentManifestationAndPublishState(
      command.eli().toString(),
      command.publishState()
    );
  }

  @Override
  @Transactional
  public void deleteQueuedForPublishNorms(DeleteQueuedNormsPort.Command command) {
    normRepository.deleteAllByEliDokumentWorkAndPublishState(
      command.eli().toString(),
      NormPublishState.QUEUED_FOR_PUBLISH
    );
  }

  @Override
  public Optional<Release> loadLatestRelease(LoadLatestReleasePort.Command command) {
    return announcementRepository
      .findByEli(command.eli().toString())
      .stream()
      .map(AnnouncementMapper::mapToDomain)
      .map(Announcement::getReleases)
      .flatMap(List::stream)
      .max(Comparator.comparing(Release::getReleasedAt));
  }

  @Override
  public Release saveReleaseToAnnouncement(SaveReleaseToAnnouncementPort.Command command) {
    final ReleaseDto releaseDto = ReleaseMapper.mapToDto(command.release());

    command
      .release()
      .getPublishedNorms()
      .forEach(norm ->
        normRepository
          .findByEliDokumentManifestation(norm.getManifestationEli().toString())
          .ifPresent(normDto -> releaseDto.getNorms().add(normDto))
      );

    var release = releaseRepository.save(releaseDto);

    announcementRepository
      .findByEli(command.announcement().getEli().toString())
      .ifPresent(announcementDto -> {
        announcementDto.getReleases().add(release);
        announcementRepository.save(announcementDto);
      });

    return ReleaseMapper.mapToDomain(release);
  }

  @Override
  public List<Release> deleteQueuedReleases(DeleteQueuedReleasesPort.Command command) {
    var announcementDto = announcementRepository.findByEli(command.eli().toString());

    if (announcementDto.isEmpty()) {
      return List.of();
    }

    var queuedReleaseDtos = announcementDto
      .get()
      .getReleases()
      .stream()
      .filter(releaseDto ->
        releaseDto
          .getNorms()
          .stream()
          .map(NormMapper::mapToDomain)
          .allMatch(norm -> norm.getPublishState() == NormPublishState.QUEUED_FOR_PUBLISH)
      )
      .toList();

    queuedReleaseDtos.forEach(releaseDto -> announcementDto.get().getReleases().remove(releaseDto));
    announcementRepository.save(announcementDto.get());

    releaseRepository.deleteAll(queuedReleaseDtos);

    return queuedReleaseDtos.stream().map(ReleaseMapper::mapToDomain).toList();
  }

  @Override
  public List<UUID> loadNormIdsByPublishState(LoadNormIdsByPublishStatePort.Command command) {
    return normRepository.findNormIdsByPublishState(command.publishState());
  }

  @Override
  public Optional<Norm> loadNormById(LoadNormByIdPort.Command command) {
    return normRepository.findById(command.id()).map(NormMapper::mapToDomain);
  }

  @Override
  public Optional<MigrationLog> loadMigrationLogByDate(LoadMigrationLogByDatePort.Command command) {
    return migrationLogRepository
      .findByCreatedAt(command.date())
      .map(MigrationLogMapper::mapToDomain);
  }
}
