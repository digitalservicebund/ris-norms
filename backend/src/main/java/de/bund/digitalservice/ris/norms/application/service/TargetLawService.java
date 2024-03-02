package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetLawUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadTargetLawPort;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports related to target laws. It is annotated with {@link Service} to indicate that
 * it's a service component in the Spring context.
 */
@Service
public class TargetLawService implements LoadTargetLawUseCase {

  private final LoadTargetLawPort loadTargetLawPort;

  /**
   * Constructs a new {@link TargetLawService} instance.
   *
   * @param loadTargetLawPort The port for loading individual target law.
   */
  public TargetLawService(LoadTargetLawPort loadTargetLawPort) {
    this.loadTargetLawPort = loadTargetLawPort;
  }

  @Override
  public Optional<TargetLaw> loadTargetLaw(Query query) {
    return loadTargetLawPort.loadTargetLawByEli(new LoadTargetLawPort.Command(query.eli()));
  }
}
