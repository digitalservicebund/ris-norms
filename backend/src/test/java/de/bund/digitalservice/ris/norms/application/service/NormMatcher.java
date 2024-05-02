package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import org.mockito.ArgumentMatcher;

public class NormMatcher implements ArgumentMatcher<UpdateNormPort.Command> {

  private final Norm left;

  // constructors
  public NormMatcher(Norm left) {
    this.left = left;
  }

  @Override
  public boolean matches(UpdateNormPort.Command right) {
    return left.equals(right.norm());
  }
}
