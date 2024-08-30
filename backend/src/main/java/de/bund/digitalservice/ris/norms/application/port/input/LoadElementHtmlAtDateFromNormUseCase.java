package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.time.Instant;
import java.util.Optional;

/** Use case for getting a single element as an HTML string from a {@link Norm}. */
public interface LoadElementHtmlAtDateFromNormUseCase {
  /**
   * Generates the version of a norm at a specific date by applying all pending modifications up
   * until that date; then retrieves an element inside the norm and renders its HTML preview. The
   * date can't be in the past.
   *
   * @param query Query used for identifying the element
   * @return An {@link Optional} containing the HTML string if found, or empty if either the norm or
   *     the element don't exist.
   */
  Optional<String> loadElementHtmlAtDateFromNorm(LoadElementHtmlAtDateFromNormUseCase.Query query)
    throws IllegalArgumentException;

  /**
   * Contains the parameters needed for loading an element from a norm at a specific date.
   *
   * @param eli The ELI used to identify the norm
   * @param eid The eId of the element inside the norm
   * @param atDate Apply all modifications until and including that date
   */
  record Query(String eli, String eid, Instant atDate) {}
}
