package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import java.util.List;

/**
 * Use case for loading a list of all expressions of a work
 */
public interface LoadExpressionsOfNormWorkUseCase {
  /**
   * Loads all the expressions of a {@link Norm} work.
   *
   * @param options The options specifying expressions to be loaded.
   * @return A list containing the laoded expressions.
   */
  List<Result> loadExpressionsOfNormWork(Options options);

  /**
   * Options for loading expressions.
   *
   * @param eli eli of the work whose expressions should be loaded
   */
  record Options(NormWorkEli eli) {}

  /**
   * Information about expressions
   * @param eli the expression eli
   * @param gegenstandslos is the expression gegenstandslos?
   */
  record Result(NormExpressionEli eli, boolean gegenstandslos) {}
}
