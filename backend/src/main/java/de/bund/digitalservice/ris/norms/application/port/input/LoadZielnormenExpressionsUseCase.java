package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Zielnorm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.util.List;

/**
 * Use case for calculating the expressions of a {@link List} of Zielnormen (targeted legal norms) in response to changes introduced by a specified Verkündung (enactment).
 * <p>
 * A Verkündung contains new temporal boundaries (Zeitgrenzen) and references to Zielnormen it affects.
 * This use case previews these Zielnormen by calculating new expressions and identifying existing expressions as {@code gegenstandslos}
 * (obsolete/irrelevant) according to the new legal situation.
 */
public interface LoadZielnormenExpressionsUseCase {
  /**
   * Creates or sets to gegenstandslos the expressions of a Zielnorm according to the Verkündung that changes them.
   *
   * @param query Query used for identifying the Verkündung
   * @return a shortened form of the affected Zielnorms with the expressions that would be created or set to gegenstandslos.
   */
  List<Zielnorm> loadZielnormExpressions(Query query);

  /**
   * Contains the parameters needed for identifying the Verkündung
   *
   * @param verkuendungEli The expression ELI used to identify the Verkündung
   */
  record Query(NormExpressionEli verkuendungEli) {}
}
