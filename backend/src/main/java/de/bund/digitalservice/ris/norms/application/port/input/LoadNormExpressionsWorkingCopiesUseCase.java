package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import java.util.List;

/**
 * UseCase for loading a list of Working copies of a norm
 */
public interface LoadNormExpressionsWorkingCopiesUseCase {
  /**
   * Retrieves a list of working copies of a Zielnorm that are created due to a Verkündung.
   *
   * @param options Options used for identifying the Zielnorm
   * @return The list of Expressions
   */
  List<Norm> loadZielnormWorkingCopies(Options options);

  /**
   * The query for loading a list of working copies.
   *
   * @param eli The ELI used to identify the Zielnorm
   */
  record Options(NormWorkEli eli) {}
}
