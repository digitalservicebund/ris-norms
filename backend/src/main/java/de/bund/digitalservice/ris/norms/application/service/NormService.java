package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadNormXmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports. It is annotated with {@link Service} to indicate that it's a service
 * component in the Spring context.
 */
@Service
public class NormService implements LoadNormXmlUseCase {
  private final LoadNormPort loadNormPort;

  public NormService(LoadNormPort loadNormPort) {
    this.loadNormPort = loadNormPort;
  }

  @Override
  public Optional<String> loadNormXml(final LoadNormXmlUseCase.Query query) {
    return loadNormPort
        .loadNorm(new LoadNormPort.Command(query.eli()))
        .map(Norm::getDocument)
        .map(XmlMapper::toString);
  }
}
