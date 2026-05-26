package de.bund.digitalservice.ris.norms.adapter.output.database.service;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.NormManifestationDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.NormManifestationMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.NormManifestationRepository;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormManifestationElisByPublishStatePort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPublishStatePort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * DBService for {@link Norm} / {@link NormManifestationDto}
 */
@Service
public class NormDBService
  extends DBService
  implements LoadNormPort, UpdateNormPublishStatePort, LoadNormManifestationElisByPublishStatePort {

  private final NormManifestationRepository normManifestationRepository;

  public NormDBService(NormManifestationRepository normManifestationRepository) {
    this.normManifestationRepository = normManifestationRepository;
  }

  @Override
  public Optional<Norm> loadNorm(LoadNormPort.Options options) {
    return switch (options.eli()) {
      case NormExpressionEli expressionEli -> normManifestationRepository
        .findFirstByExpressionEliOrderByManifestationEliDesc(expressionEli.toString())
        .map(NormManifestationMapper::mapToDomain);
      case NormManifestationEli manifestationEli -> {
        if (!manifestationEli.hasPointInTimeManifestation()) {
          // we can find the norm based on the expression eli as the point in time
          // manifestation is the only additional identifying part of the eli
          yield this.loadNorm(new LoadNormPort.Options(manifestationEli.asExpressionEli()));
        }

        yield normManifestationRepository
          .findByManifestationEli(manifestationEli.toString())
          .map(NormManifestationMapper::mapToDomain);
      }
      case NormWorkEli workEli -> normManifestationRepository
        .findFirstByWorkEliOrderByManifestationEliDesc(workEli.toString())
        .map(NormManifestationMapper::mapToDomain);
    };
  }

  @Override
  public void updateNormPublishState(UpdateNormPublishStatePort.Options options) {
    normManifestationRepository.updatePublishStateByManifestationEli(
      options.normManifestationEli().toString(),
      options.publishState()
    );
  }

  @Override
  public List<NormManifestationEli> loadNormManifestationElisByPublishState(
    LoadNormManifestationElisByPublishStatePort.Options options
  ) {
    return normManifestationRepository
      .findManifestationElisByPublishState(options.publishState())
      .stream()
      .map(NormManifestationEli::fromString)
      .toList();
  }
}
