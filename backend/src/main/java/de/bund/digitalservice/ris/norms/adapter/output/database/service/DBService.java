package de.bund.digitalservice.ris.norms.adapter.output.database.service;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AnnouncementDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.NormDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.ReleaseDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AnnouncementMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.ReleaseMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AnnouncementRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.ReleaseRepository;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.Release;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.WorkEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
    DeleteQueuedReleasesPort {

  private final AnnouncementRepository announcementRepository;
  private final NormRepository normRepository;
  private final ReleaseRepository releaseRepository;

  public DBService(
    AnnouncementRepository announcementRepository,
    NormRepository normRepository,
    ReleaseRepository releaseRepository
  ) {
    this.announcementRepository = announcementRepository;
    this.normRepository = normRepository;
    this.releaseRepository = releaseRepository;
  }

  @Override
  public Optional<Norm> loadNorm(LoadNormPort.Command command) {
    return switch (command.eli()) {
      case ExpressionEli expressionEli -> normRepository
        .findFirstByEliExpressionOrderByEliManifestationDesc(expressionEli.toString())
        .map(NormMapper::mapToDomain);
      case ManifestationEli manifestationEli -> {
        if (!manifestationEli.hasPointInTimeManifestation()) {
          // we can find the norm based on the expression eli as the point in time manifestation is the only additional identifying part of the eli in our system (all norms are xmls)
          yield this.loadNorm(new LoadNormPort.Command(manifestationEli.asExpressionEli()));
        }

        yield normRepository
          .findByEliManifestation(manifestationEli.toString())
          .map(NormMapper::mapToDomain);
      }
      case WorkEli workEli -> throw new IllegalArgumentException(
        "It's currently not possible to load a norm by it's work eli."
      );
    };
  }

  @Override
  public Optional<Norm> loadNormByGuid(LoadNormByGuidPort.Command command) {
    return normRepository
      .findFirstByGuidOrderByEliManifestation(command.guid())
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
      .findByEliManifestation(command.norm().getManifestationEli().toString())
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
    announcementRepository.deleteByEli(command.eli().toString());
  }

  @Override
  @Transactional
  public void deleteNorm(DeleteNormPort.Command command) {
    normRepository.deleteByEliManifestationAndPublishState(
      command.eli().toString(),
      command.publishState()
    );
  }

  @Override
  @Transactional
  public void deleteQueuedForPublishNorms(DeleteQueuedNormsPort.Command command) {
    normRepository.deleteAllByEliWorkAndPublishState(
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
      .findFirst();
  }

  @Override
  public Release saveReleaseToAnnouncement(SaveReleaseToAnnouncementPort.Command command) {
    final ReleaseDto releaseDto = ReleaseMapper.mapToDto(command.release());

    command
      .release()
      .getPublishedNorms()
      .forEach(norm ->
        normRepository
          .findByEliManifestation(norm.getManifestationEli().toString())
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
}
