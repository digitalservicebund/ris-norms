package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.ZielnormReference;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.util.List;

/** Use case for getting the zielnorm references from a norm. */
public interface LoadZielnormReferencesUseCase {
  /**
   * Retrieves a list of all zielnorm references
   *
   * @param query Query used for identifying the norm
   * @return The zielnorm references
   */
  List<ZielnormReference> loadZielnormReferences(Query query);

  /**
   * Contains the parameters needed for loading the zielnorm references from a norm.
   *
   * @param eli The ELI used to identify the norm
   */
  record Query(NormExpressionEli eli) {}
}
