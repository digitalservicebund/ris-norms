package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadAllAmendingLawsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAmendingLawUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAllAmendingLawsPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAmendingLawPort;
import de.bund.digitalservice.ris.norms.domain.entity.AmendingLaw;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class implementing the {@link LoadAmendingLawUseCase}. This class is responsible for
 * loading amending laws by delegating the operation to the injected {@link LoadAmendingLawPort}. It
 * is annotated with {@link Service} to indicate that it's a service component in the Spring
 * context.
 */
@Service
public class AmendingLawService implements LoadAmendingLawUseCase, LoadAllAmendingLawsUseCase {

  private final LoadAmendingLawPort loadAmendingLawPort;
  private final LoadAllAmendingLawsPort loadAllAmendingLawsPort;

  /**
   * Constructs a new {@link AmendingLawService} instance.
   *
   * @param loadAmendingLawPort The port for loading individual amending laws.
   * @param loadAllAmendingLawsPort The port for loading all amending laws.
   */
  public AmendingLawService(
      LoadAmendingLawPort loadAmendingLawPort, LoadAllAmendingLawsPort loadAllAmendingLawsPort) {
    this.loadAmendingLawPort = loadAmendingLawPort;
    this.loadAllAmendingLawsPort = loadAllAmendingLawsPort;
  }

  /** {@inheritDoc} */
  @Override
  public Optional<AmendingLaw> loadAmendingLaw(final Query query) {
    return loadAmendingLawPort.loadAmendingLawByEli(new LoadAmendingLawPort.Command(query.eli()));
  }

  @Override
  public List<AmendingLaw> loadAllAmendingLaws() {
    return loadAllAmendingLawsPort.loadAllAmendingLaws();
  }
}
