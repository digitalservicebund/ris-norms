package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import java.time.Instant;

/** Use case for getting a single element as an HTML string from a {@link Norm}. */
public interface LoadElementHtmlAtDateFromNormUseCase {
  /**
   * Generates the version of a norm at a specific date by applying all pending modifications up
   * until that date; then retrieves an element inside the norm and renders its HTML preview. The
   * date can't be in the past.
   *
   * @param query Query used for identifying the element
   * @return The HTML string
   */
  String loadElementHtmlAtDateFromNorm(LoadElementHtmlAtDateFromNormUseCase.Query query);

  /**
   * Contains the parameters needed for loading an element from a norm at a specific date.
   *
   * @param eli The ELI used to identify the norm
   * @param eid The eId of the element inside the norm
   * @param atDate Apply all modifications until and including that date
   */
  record Query(DokumentExpressionEli eli, String eid, Instant atDate) {}
}
