package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetLawUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetLawXmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.TimeMachineUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateNormXmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateTargetLawUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadTargetLawPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadTargetLawXmlPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateTargetLawXmlPort;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports related to target laws. It is annotated with {@link Service} to indicate that
 * it's a service component in the Spring context.
 */
@Service
public class TargetLawService
    implements LoadTargetLawUseCase,
        LoadTargetLawXmlUseCase,
        TimeMachineUseCase,
        UpdateTargetLawUseCase {

  private final LoadTargetLawPort loadTargetLawPort;
  private final LoadTargetLawXmlPort loadTargetLawXmlPort;
  private final TimeMachineService timeMachineService;
  private final UpdateTargetLawXmlPort updateTargetLawXmlPort;
  private final UpdateNormXmlUseCase updateNormXmlUseCase;

  public TargetLawService(
      LoadTargetLawPort loadTargetLawPort,
      LoadTargetLawXmlPort loadTargetLawXmlPort,
      TimeMachineService timeMachineService,
      UpdateTargetLawXmlPort updateTargetLawXmlPort,
      UpdateNormXmlUseCase updateNormXmlUseCase) {
    this.loadTargetLawPort = loadTargetLawPort;
    this.loadTargetLawXmlPort = loadTargetLawXmlPort;
    this.timeMachineService = timeMachineService;
    this.updateTargetLawXmlPort = updateTargetLawXmlPort;
    this.updateNormXmlUseCase = updateNormXmlUseCase;
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

  @Override
  public Optional<String> applyTimeMachine(TimeMachineUseCase.Query query) {
    final Optional<String> targetLaw =
        loadTargetLawXmlPort.loadTargetLawXmlByEli(
            new LoadTargetLawXmlPort.Command(query.targetLawEli()));
    return targetLaw.map(
        targetLawString -> timeMachineService.apply(query.amendingLawXml(), targetLawString));
  }

  @Override
  public Optional<String> updateTargetLaw(UpdateTargetLawUseCase.Query query) {
    try {
      updateNormXmlUseCase.updateNormXml(
          new UpdateNormXmlUseCase.Query(query.eli(), query.newTargetLawXml()));
    } catch (UpdateNormXmlUseCase.InvalidUpdateException e) {
      throw new RuntimeException(e);
    }

    return updateTargetLawXmlPort.updateTargetLawXmlByEli(
        new UpdateTargetLawXmlPort.Command(query.eli(), query.newTargetLawXml()));
  }
}
