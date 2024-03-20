package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports. It is annotated with {@link Service} to indicate that it's a service
 * component in the Spring context.
 */
@Service
public class ReleaseService implements ReleaseAmendingLawAndAllRelatedTargetLawsUseCase {

  @Override
  public List<TargetLaw> releaseAmendingLaw(Query query) {
    // TODO implement this. Now it's just for testing
    return List.of(
        TargetLaw.builder()
            .eli("eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1")
            .build());
  }
}
