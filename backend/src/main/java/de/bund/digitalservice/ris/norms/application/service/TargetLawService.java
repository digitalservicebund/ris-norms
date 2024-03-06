package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetLawUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetLawXmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadTargetLawPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadTargetLawXmlPort;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports related to target laws. It is annotated with {@link Service} to indicate that
 * it's a service component in the Spring context.
 */
@Service
public class TargetLawService implements LoadTargetLawUseCase, LoadTargetLawXmlUseCase {

  private final LoadTargetLawPort loadTargetLawPort;
  private final LoadTargetLawXmlPort loadTargetLawXmlPort;

  public TargetLawService(
      LoadTargetLawPort loadTargetLawPort, LoadTargetLawXmlPort loadTargetLawXmlPort) {
    this.loadTargetLawPort = loadTargetLawPort;
    this.loadTargetLawXmlPort = loadTargetLawXmlPort;
  }

  @Override
  public Optional<TargetLaw> loadTargetLaw(LoadTargetLawUseCase.Query query) {
    return loadTargetLawPort.loadTargetLawByEli(new LoadTargetLawPort.Command(query.eli()));
  }

  @Override
  public Optional<String> loadTargetLawXml(LoadTargetLawXmlUseCase.Query query) {
    return loadTargetLawXmlPort.loadTargetLawXmlByEli(
        new LoadTargetLawXmlPort.Command(query.eli()));
  }
}
