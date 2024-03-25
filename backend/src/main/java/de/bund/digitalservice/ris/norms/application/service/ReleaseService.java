package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAmendingLawPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateAmendingLawPort;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import java.time.Instant;
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
  private final UpdateAmendingLawPort updateAmendingLawPort;

  public ReleaseService(
      LoadAmendingLawPort loadAmendingLawPort, UpdateAmendingLawPort updateAmendingLawPort) {
    this.loadAmendingLawPort = loadAmendingLawPort;
    this.updateAmendingLawPort = updateAmendingLawPort;
  }

  @Override
  public Optional<AmendingLaw> releaseAmendingLaw(Query query) {
    final Optional<AmendingLaw> amendingLawOptional =
        loadAmendingLawPort.loadAmendingLawByEli(new LoadAmendingLawPort.Command(query.eli()));
    return amendingLawOptional.flatMap(
        amendingLaw -> {
          amendingLaw.setReleasedAt(Instant.now());
          return updateAmendingLawPort.updateAmendingLaw(
              new UpdateAmendingLawPort.Command(amendingLaw));
        });
  }
}
