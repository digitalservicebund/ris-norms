package de.bund.digitalservice.ris.norms.adapter.output.database.service;

import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AnnouncementMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.AnnouncementRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormRepository;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class for interacting with the database. This class is annotated with {@link Service} to
 * indicate that it's a service component in the Spring context.
 */
@Service
public class DBService
    implements LoadNormPort,
        LoadNormByGuidPort,
        LoadAnnouncementPort,
        LoadAllAnnouncementsPort,
        UpdateNormPort,
        UpdateAnnouncementPort {

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
  public Optional<Announcement> loadAnnouncement(LoadAnnouncementPort.Command command) {
    return announcementRepository
        .findByNormDtoEli(command.eli())
        .map(AnnouncementMapper::mapToDomain);
  }

  @Override
  public List<Announcement> loadAllAnnouncements() {
    return announcementRepository.findAll().stream()
        .map(AnnouncementMapper::mapToDomain)
        .sorted(
            Comparator.comparing(
                    (Announcement announcement) ->
                        announcement.getNorm().getFBRDateVerkuendung().orElse(LocalDate.MIN))
                .reversed())
        .toList();
  }

  @Override
  public Optional<Norm> updateNorm(UpdateNormPort.Command command) {
    var normXml = XmlMapper.toString(command.norm().getDocument());
    return command
        .norm()
        .getEli()
        .flatMap(
            eli ->
                normRepository
                    .findByEli(eli)
                    .map(
                        normDto -> {
                          normDto.setXml(normXml);
                          // we do not update the GUID or ELI as they may not change
                          return NormMapper.mapToDomain(normRepository.save(normDto));
                        }));
  }

  @Override
  public Optional<Announcement> updateAnnouncement(UpdateAnnouncementPort.Command command) {
    var announcement = command.announcement();
    return command
        .announcement()
        .getNorm()
        .getEli()
        .flatMap(
            eli ->
                announcementRepository
                    .findByNormDtoEli(eli)
                    .map(
                        announcementDto -> {
                          announcementDto.setReleasedByDocumentalistAt(
                              announcement.getReleasedByDocumentalistAt());
                          // It is not possible to change the norm associated with an announcement.
                          // Therefore, we don't update that relationship.
                          return AnnouncementMapper.mapToDomain(
                              announcementRepository.save(announcementDto));
                        }));
  }
}
