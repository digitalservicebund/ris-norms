package de.bund.digitalservice.ris.norms.adapter.output.database.service;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AnnouncementDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AnnouncementMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AnnouncementRepository;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteAnnouncementByNormEliPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAllAnnouncementsPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAnnouncementByNormEliPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateAnnouncementPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveAnnouncementPort;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * DBService for {@link Announcement} / {@link AnnouncementDto}
 */
@Service
public class AnnouncementDBService
  extends DBService
  implements
    LoadAnnouncementByNormEliPort,
    LoadAllAnnouncementsPort,
    UpdateAnnouncementPort,
    UpdateOrSaveAnnouncementPort,
    DeleteAnnouncementByNormEliPort {

  private final AnnouncementRepository announcementRepository;

  public AnnouncementDBService(AnnouncementRepository announcementRepository) {
    this.announcementRepository = announcementRepository;
  }

  @Override
  public Optional<Announcement> loadAnnouncementByNormEli(
    LoadAnnouncementByNormEliPort.Command command
  ) {
    return announcementRepository
      .findByEliNormExpression(command.eli().toString())
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
  public Optional<Announcement> updateAnnouncement(UpdateAnnouncementPort.Command command) {
    return announcementRepository
      .findByEliNormExpression(command.announcement().getEli().toString())
      // There is no field in an announcement that can change, so we do nothing here
      .map(AnnouncementMapper::mapToDomain);
  }

  @Override
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
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
  public void deleteAnnouncementByNormEli(DeleteAnnouncementByNormEliPort.Command command) {
    announcementRepository.deleteByEliNormExpression(command.eli().toString());
  }
}
