package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadElementFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

/**
 * Implements various operations related to getting generic elements from a {@link Norm}. Note that
 * this is necessarily vague because we allow to work with any element that can be identified by an
 * eId. This can be many different things. If you need something more specific (e.g. specifically
 * related to articles), you should use a more specific service.
 */
@Service
public class ElementService implements LoadElementFromNormUseCase {
  private final LoadNormPort loadNormPort;

  public ElementService(LoadNormPort loadNormPort) {
    this.loadNormPort = loadNormPort;
  }

  @Override
  public Optional<Node> loadElementFromNorm(Query query) {
    var xPath = String.format("//*[@eId='%s']", query.eid());

    return loadNormPort
        .loadNorm(new LoadNormPort.Command(query.eli()))
        .map(norm -> NodeParser.getNodeFromExpression(xPath, norm.getDocument()));
  }
}
