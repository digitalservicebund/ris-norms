package de.bund.digitalservice.ris.norms.adapter.output.database.service;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AnnouncementDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.NormManifestationDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.ReleaseDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AnnouncementMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.MigrationLogMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormManifestationMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.RegelungstextMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.ReleaseMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AnnouncementRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.DokumentRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.MigrationLogRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.ReleaseRepository;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentWorkEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    UpdateNormPort,
    UpdateAnnouncementPort,
    UpdateOrSaveNormPort,
    UpdateOrSaveAnnouncementPort,
    DeleteAnnouncementByNormEliPort,
    DeleteNormPort,
    SaveReleaseToAnnouncementPort,
    DeleteQueuedReleasesPort,
    LoadNormManifestationElisByPublishStatePort,
    LoadLastMigrationLogPort,
    LoadRegelungstextPort {

  private final AnnouncementRepository announcementRepository;
  private final DokumentRepository dokumentRepository;
  private final NormManifestationRepository normManifestationRepository;
  private final ReleaseRepository releaseRepository;
  private final MigrationLogRepository migrationLogRepository;

  public DBService(
    AnnouncementRepository announcementRepository,
    DokumentRepository dokumentRepository,
    NormManifestationRepository normManifestationRepository,
    ReleaseRepository releaseRepository,
    MigrationLogRepository migrationLogRepository
  ) {
    this.announcementRepository = announcementRepository;
    this.dokumentRepository = dokumentRepository;
    this.normManifestationRepository = normManifestationRepository;
    this.releaseRepository = releaseRepository;
    this.migrationLogRepository = migrationLogRepository;
  }

  @Override
  public Optional<Norm> loadNorm(LoadNormPort.Command command) {
    return switch (command.eli()) {
      case NormExpressionEli expressionEli -> normManifestationRepository
        .findFirstByExpressionEliOrderByManifestationEliDesc(expressionEli.toString())
        .map(NormManifestationMapper::mapToDomain);
      case NormManifestationEli manifestationEli -> {
        if (!manifestationEli.hasPointInTimeManifestation()) {
          // we can find the norm based on the expression eli as the point in time manifestation is the only additional identifying part of the eli
          yield this.loadNorm(new LoadNormPort.Command(manifestationEli.asExpressionEli()));
        }

        yield normManifestationRepository
          .findByManifestationEli(manifestationEli.toString())
          .map(NormManifestationMapper::mapToDomain);
      }
      case NormWorkEli ignored -> throw new IllegalArgumentException(
        "It's currently not possible to load a norm by it's work eli."
      );
    };
  }

  @Override
  public Optional<Regelungstext> loadRegelungstext(LoadRegelungstextPort.Command command) {
    return switch (command.eli()) {
      case DokumentExpressionEli expressionEli -> dokumentRepository
        .findFirstByEliDokumentExpressionOrderByEliDokumentManifestationDesc(
          expressionEli.toString()
        )
        .map(RegelungstextMapper::mapToDomain);
      case DokumentManifestationEli manifestationEli -> {
        if (!manifestationEli.hasPointInTimeManifestation()) {
          // we can find the regelungstext based on the expression eli as the point in time manifestation is the only additional identifying part of the eli
          yield this.loadRegelungstext(
              new LoadRegelungstextPort.Command(manifestationEli.asExpressionEli())
            );
        }

        yield dokumentRepository
          .findByEliDokumentManifestation(manifestationEli.toString())
          .map(RegelungstextMapper::mapToDomain);
      }
      case DokumentWorkEli ignored -> throw new IllegalArgumentException(
        "It's currently not possible to load a regelungstext by it's work eli."
      );
    };
  }

  @Override
  public Optional<Norm> loadNormByGuid(LoadNormByGuidPort.Command command) {
    return normManifestationRepository
      .findFirstByExpressionAktuelleVersionIdOrderByManifestationEli(command.guid())
      .map(NormManifestationMapper::mapToDomain);
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
    Optional<NormManifestationDto> normManifestationDto =
      normManifestationRepository.findByManifestationEli(
        command.norm().getManifestationEli().toString()
      );

    if (normManifestationDto.isEmpty()) {
      return Optional.empty();
    }

    var dokumentDtos = dokumentRepository.saveAll(
      command
        .norm()
        .getRegelungstexte()
        .stream()
        .map(regelungstext ->
          dokumentRepository
            .findByEliDokumentManifestation(regelungstext.getManifestationEli().toString())
            .map(dokumentDto -> {
              dokumentDto.setXml(XmlMapper.toString(regelungstext.getDocument()));
              return dokumentDto;
            })
        )
        .flatMap(Optional::stream)
        .collect(Collectors.toSet())
    );

    normManifestationDto.get().setDokumente(dokumentDtos);
    normManifestationDto.get().setPublishState(command.norm().getPublishState());

    return Optional.of(
      NormManifestationMapper.mapToDomain(
        normManifestationRepository.save(normManifestationDto.get())
      )
    );
  }

  @Override
  public Norm updateOrSave(UpdateOrSaveNormPort.Command command) {
    final Optional<Norm> updatedNorm = updateNorm(new UpdateNormPort.Command(command.norm()));
    if (updatedNorm.isEmpty()) {
      dokumentRepository.saveAllAndFlush(
        command
          .norm()
          .getRegelungstexte()
          .stream()
          .map(RegelungstextMapper::mapToDto)
          .collect(Collectors.toSet())
      );

      NormManifestationDto normManifestationDto = normManifestationRepository
        .findByManifestationEli(command.norm().getManifestationEli().toString())
        .orElseThrow();

      normManifestationDto.setPublishState(command.norm().getPublishState());

      return NormManifestationMapper.mapToDomain(
        normManifestationRepository.save(normManifestationDto)
      );
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
    var normDto = normManifestationRepository.findByManifestationEli(command.eli().toString());

    if (normDto.isEmpty()) {
      return;
    }

    if (!normDto.get().getPublishState().equals(command.publishState())) {
      return;
    }

    dokumentRepository.deleteAll(normDto.get().getDokumente());
    normManifestationRepository.delete(normDto.get());
  }

  @Override
  public Release saveReleaseToAnnouncement(SaveReleaseToAnnouncementPort.Command command) {
    final ReleaseDto releaseDto = ReleaseMapper.mapToDto(command.release());

    command
      .release()
      .getPublishedNorms()
      .forEach(norm ->
        normManifestationRepository
          .findByManifestationEli(norm.getManifestationEli().toString())
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
          .map(NormManifestationMapper::mapToDomain)
          .allMatch(norm -> norm.getPublishState() == NormPublishState.QUEUED_FOR_PUBLISH)
      )
      .toList();

    queuedReleaseDtos.forEach(releaseDto -> announcementDto.get().getReleases().remove(releaseDto));
    announcementRepository.save(announcementDto.get());

    releaseRepository.deleteAll(queuedReleaseDtos);

    return queuedReleaseDtos.stream().map(ReleaseMapper::mapToDomain).toList();
  }

  @Override
  public List<NormManifestationEli> loadNormManifestationElisByPublishState(
    LoadNormManifestationElisByPublishStatePort.Command command
  ) {
    return normManifestationRepository
      .findManifestationElisByPublishState(command.publishState())
      .stream()
      .map(NormManifestationEli::fromString)
      .toList();
  }

  @Override
  public Optional<MigrationLog> loadLastMigrationLog() {
    return migrationLogRepository
      .findFirstByOrderByCreatedAtDesc()
      .map(MigrationLogMapper::mapToDomain);
  }
}
