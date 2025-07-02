package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import java.util.List;

/**
 * Port for loading information about all expressions of a work.
 */
public interface LoadExpressionsOfNormWorkPort {
  /**
   * Loads all {@link Norm} expressions of a work.
   *
   * @param options The options specifying the expressions to be loaded.
   * @return A List containing information about the loaded expressions.
   */
  List<Result> loadExpressionsOfNormWork(final Options options);

  /**
   * Options for loading norm expressions.
   *
   * @param eli the eli of the norm information
   */
  record Options(NormWorkEli eli) {}

  /**
   * Information about norm expressions
   * @param eli the expression eli
   * @param gegenstandslos is the expression gegenstandslos?
   */
  record Result(NormExpressionEli eli, Boolean gegenstandslos) {}
}
