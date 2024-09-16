package de.bund.digitalservice.ris.norms.adapter.output.database.service;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AnnouncementDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.dto.NormDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AnnouncementMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AnnouncementRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormRepository;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
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
    UpdateNormPort,
    UpdateAnnouncementPort,
    UpdateOrSaveNormPort,
    UpdateOrSaveAnnouncementPort,
    DeleteAnnouncementByNormEliPort,
    DeleteNormByGuidPort {

  private final AnnouncementRepository announcementRepository;
  private final NormRepository normRepository;

  public DBService(AnnouncementRepository announcementRepository, NormRepository normRepository) {
    this.announcementRepository = announcementRepository;
    this.normRepository = normRepository;
  }

  @Override
  public Optional<Norm> loadNorm(LoadNormPort.Command command) {
    return normRepository.findByEli(command.eli()).map(NormMapper::mapToDomain);
  }

  @Override
  public Optional<Norm> loadNormByGuid(LoadNormByGuidPort.Command command) {
    return normRepository.findById(command.guid()).map(NormMapper::mapToDomain);
  }

  @Override
  public Optional<Announcement> loadAnnouncementByNormEli(
    LoadAnnouncementByNormEliPort.Command command
  ) {
    return announcementRepository
      .findByNormDtoEli(command.eli())
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
          .comparing((Announcement announcement) ->
            announcement.getNorm().getMeta().getFRBRWork().getFBRDate()
          )
          .reversed()
      )
      .toList();
  }

  @Override
  public Optional<Norm> updateNorm(UpdateNormPort.Command command) {
    var normXml = XmlMapper.toString(command.norm().getDocument());
    return normRepository
      .findByEli(command.norm().getEli())
      .map(normDto -> {
        normDto.setXml(normXml);
        // we do not update the GUID or ELI as they may not change
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
    var announcement = command.announcement();
    return announcementRepository
      .findByNormDtoEli(command.announcement().getNorm().getEli())
      .map(announcementDto -> {
        announcementDto.setReleasedByDocumentalistAt(announcement.getReleasedByDocumentalistAt());
        // It is not possible to change the norm associated with an announcement.
        // Therefore, we don't update that relationship.
        return AnnouncementMapper.mapToDomain(announcementRepository.save(announcementDto));
      });
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
    announcementRepository.deleteByNormDtoEli(command.eli());
  }

  @Override
  public void loadNormByGuid(DeleteNormByGuidPort.Command command) {
    normRepository.deleteById(command.guid());
  }
}
