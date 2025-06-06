package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.util.List;

/**
 * UseCase for loading a list of norms that are affected by a Verkündung
 */
public interface LoadNormExpressionsAffectedByVerkuendungUseCase {
  /**
   * Retrieves a list of norms that are affected by the Verkündung identified by the provided options.
   * This list includes the latest manifestations of all expressions that are changed due to the Verkündung.
   *
   * @param options Options used for identifying the Verkündung
   * @return The list of norms
   */
  List<Norm> loadNormExpressionsAffectedByVerkuendung(Options options);

  /**
   * The query for loading a list of norms that are affected by the Verkündung.
   *
   * @param eli The ELI used to identify the Verkündung
   */
  record Options(NormExpressionEli eli) {}
}
