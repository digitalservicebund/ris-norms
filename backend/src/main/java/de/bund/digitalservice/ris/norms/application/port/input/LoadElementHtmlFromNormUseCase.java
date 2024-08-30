package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/** Use case for getting a single element as an HTML string from a {@link Norm}. */
public interface LoadElementHtmlFromNormUseCase {
  /**
   * Retrieves an element inside a norm and renders its HTML preview.
   *
   * @param query Query used for identifying the element
   * @return The HTML string
   */
  String loadElementHtmlFromNorm(Query query);

  /**
   * Contains the parameters needed for loading an element from a norm.
   *
   * @param eli The ELI used to identify the norm
   * @param eid The eId of the element inside the norm
   */
  record Query(String eli, String eid) {}
}
