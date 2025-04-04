package de.bund.digitalservice.ris.norms.adapter.output.database.service;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.ReleaseDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormManifestationMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.ReleaseMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.ReleaseRepository;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteQueuedReleasesPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadReleasesByNormExpressionEliPort;
import de.bund.digitalservice.ris.norms.application.port.output.SaveReleasePort;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import de.bund.digitalservice.ris.norms.domain.entity.Release;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * DBService for {@link Release} / {@link ReleaseDto}
 */
@Service
public class ReleaseDBService
  extends DBService
  implements SaveReleasePort, DeleteQueuedReleasesPort, LoadReleasesByNormExpressionEliPort {

  private final NormManifestationRepository normManifestationRepository;
  private final ReleaseRepository releaseRepository;

  public ReleaseDBService(
    NormManifestationRepository normManifestationRepository,
    ReleaseRepository releaseRepository
  ) {
    this.normManifestationRepository = normManifestationRepository;
    this.releaseRepository = releaseRepository;
  }

  @Override
  public Release saveRelease(SaveReleasePort.Command command) {
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
    return ReleaseMapper.mapToDomain(release);
  }

  @Override
  public List<Release> deleteQueuedReleases(DeleteQueuedReleasesPort.Command command) {
    var releases = releaseRepository.findAllByNormExpressionEli(command.eli().toString());

    var queuedReleaseDtos = releases
      .stream()
      .filter(releaseDto ->
        releaseDto
          .getNorms()
          .stream()
          .map(NormManifestationMapper::mapToDomain)
          .allMatch(norm -> norm.getPublishState() == NormPublishState.QUEUED_FOR_PUBLISH)
      )
      .toList();

    releaseRepository.deleteAll(queuedReleaseDtos);

    return queuedReleaseDtos.stream().map(ReleaseMapper::mapToDomain).toList();
  }

  @Override
  public List<Release> loadReleasesByNormExpressionEli(
    LoadReleasesByNormExpressionEliPort.Command command
  ) {
    return releaseRepository
      .findAllByNormExpressionEli(command.eli().toString())
      .stream()
      .map(ReleaseMapper::mapToDomain)
      .toList();
  }
}
