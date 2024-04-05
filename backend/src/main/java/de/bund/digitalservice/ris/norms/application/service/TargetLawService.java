package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetLawUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetLawXmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.TimeMachineUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateTargetLawUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadTargetLawPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadTargetLawXmlPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateTargetLawXmlPort;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

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

  public TargetLawService(
      LoadTargetLawPort loadTargetLawPort,
      LoadTargetLawXmlPort loadTargetLawXmlPort,
      TimeMachineService timeMachineService,
      UpdateTargetLawXmlPort updateTargetLawXmlPort) {
    this.loadTargetLawPort = loadTargetLawPort;
    this.loadTargetLawXmlPort = loadTargetLawXmlPort;
    this.timeMachineService = timeMachineService;
    this.updateTargetLawXmlPort = updateTargetLawXmlPort;
  }

  @Override
  public Optional<TargetLaw> loadTargetLaw(LoadTargetLawUseCase.Query query) {
    return loadTargetLawPort.loadTargetLawByEli(new LoadTargetLawPort.Command(query.eli()));
  }

  @Override
  public Optional<Document> loadTargetLawXml(LoadTargetLawXmlUseCase.Query query) {
    return loadTargetLawXmlPort.loadTargetLawXmlByEli(
        new LoadTargetLawXmlPort.Command(query.eli()));
  }

  @Override
  public Optional<String> applyTimeMachine(TimeMachineUseCase.Query query) {
    final Optional<Document> targetLaw =
        loadTargetLawXmlPort.loadTargetLawXmlByEli(
            new LoadTargetLawXmlPort.Command(query.targetLawEli()));
    return targetLaw.map(
        // TODO: Switch timeMachineService.apply() to work on Documents, not Strings
        targetLawDocument -> timeMachineService.apply(timeMachineService.convertDocumentToString(query.amendingLawXml()), timeMachineService.convertDocumentToString(targetLawDocument)));
  }

  @Override
  public Optional<Document> updateTargetLaw(UpdateTargetLawUseCase.Query query) {
    return updateTargetLawXmlPort.updateTargetLawXmlByEli(
        new UpdateTargetLawXmlPort.Command(query.eli(), query.newTargetLawXml()));
  }
}
