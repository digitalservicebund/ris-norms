package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.AmendingLawDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.AmendingLawMapper;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAmendingLawPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadTargetLawsForAmendingLawPort;
import de.bund.digitalservice.ris.norms.application.port.output.SaveAmendingLawPort;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports. It is annotated with {@link Service} to indicate that it's a service
 * component in the Spring context.
 */
@Service
public class ReleaseService implements ReleaseAmendingLawAndAllRelatedTargetLawsUseCase {
  private final LoadAmendingLawPort loadAmendingLawPort;
  private final SaveAmendingLawPort saveAmendingLawPort;
  private final LoadTargetLawsForAmendingLawPort loadTargetLawsForAmendingLawPort;

  public ReleaseService(
      LoadAmendingLawPort loadAmendingLawPort,
      SaveAmendingLawPort saveAmendingLawPort,
      LoadTargetLawsForAmendingLawPort loadTargetLawsForAmendingLawPort) {
    this.loadAmendingLawPort = loadAmendingLawPort;
    this.saveAmendingLawPort = saveAmendingLawPort;
    this.loadTargetLawsForAmendingLawPort = loadTargetLawsForAmendingLawPort;
  }

  @Override
  public List<TargetLaw> releaseAmendingLaw(Query query) {
    Optional<AmendingLaw> amendingLawOptional =
        loadAmendingLawPort.loadAmendingLawByEli(new LoadAmendingLawPort.Command(query.eli()));
    if (amendingLawOptional.isEmpty()) return Collections.emptyList();

    AmendingLaw amendingLaw = amendingLawOptional.get();
    final Instant timestampNow = Instant.now();
    amendingLaw.setReleasedAt(timestampNow);
    AmendingLawDto amendingLawDto = AmendingLawMapper.mapToDto(amendingLaw);

    AmendingLaw updatedAmendingLaw =
        saveAmendingLawPort.saveAmendingLawByEli(new SaveAmendingLawPort.Command(amendingLawDto));

    return loadTargetLawsForAmendingLawPort.loadTargetsLawByAmendingLawEli(
        new LoadTargetLawsForAmendingLawPort.Command(updatedAmendingLaw.getEli()));
  }
}
