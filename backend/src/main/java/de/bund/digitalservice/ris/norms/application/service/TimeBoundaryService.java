package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.TimeBoundary;
import de.bund.digitalservice.ris.norms.domain.entity.TimeBoundaryChangeData;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports. It is annotated with {@link Service} to indicate that it's a service
 * component in the Spring context.
 */
@Service
public class TimeBoundaryService implements LoadTimeBoundariesUseCase, UpdateTimeBoundariesUseCase {
  private final LoadNormPort loadNormPort;
  private final UpdateNormPort updateNormPort;

  public TimeBoundaryService(LoadNormPort loadNormPort, UpdateNormPort updateNormPort) {
    this.loadNormPort = loadNormPort;
    this.updateNormPort = updateNormPort;
  }

  @Override
  public List<TimeBoundary> loadTimeBoundariesOfNorm(LoadTimeBoundariesUseCase.Query query) {
    return loadNormPort
        .loadNorm(new LoadNormPort.Command(query.eli()))
        .map(Norm::getTimeBoundaries)
        .orElse(List.of());
  }

  @Override
  public List<TimeBoundary> updateTimeBoundariesOfNorm(UpdateTimeBoundariesUseCase.Query query) {
    Optional<Norm> norm = loadNormPort.loadNorm(new LoadNormPort.Command(query.eli()));
    if (norm.isPresent()) {
      deleteTimeBoundaries(query.timeBoundaries(), norm.get());
      addTimeBoundaries(query.timeBoundaries(), norm.get());
      changeTimeBoundaries(query.timeBoundaries(), norm.get());
    }

    return norm.map(Norm::getTimeBoundaries).orElse(List.of());
  }

  private void changeTimeBoundaries(
      List<TimeBoundaryChangeData> timeBoundaryChangeData, Norm norm) {
    throw new UnsupportedOperationException();
  }

  private void addTimeBoundaries(List<TimeBoundaryChangeData> timeBoundaryChangeData, Norm norm) {
    throw new UnsupportedOperationException();
  }

  private void deleteTimeBoundaries(
      List<TimeBoundaryChangeData> timeBoundaryChangeData, Norm norm) {
    throw new UnsupportedOperationException();
  }
}
