package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Zielnorm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import java.util.List;

/**
 * Use case for creating the expressions of a {@link List} of Zielnormen (targeted legal norms) in response to changes introduced by a specified Verkündung (enactment).
 * <p>
 * A Verkündung contains new temporal boundaries (Zeitgrenzen) and references to Zielnormen it affects.
 * This use case updates these Zielnormen by creating new expressions or marking existing expressions as {@code gegenstandslos}
 * (obsolete/irrelevant) according to the new legal situation.
 */
public interface CreateZielnormenExpressionsUseCase {
  /**
   * Creates or sets to gegenstandslos the expressions of a Zielnorm according to the Verkündung that changes them.
   *
   * @param query Query used for identifying the Verkündung and the affected document,
   * @return a shortened form of the affected Zielnorms with the expressions that were created or set to gegenstandslos.
   */
  Zielnorm createZielnormExpressions(Query query);

  /**
   * Contains the parameters needed for identifying the Verkündung and the affected work.
   *
   * @param verkuendungEli The expression ELI used to identify the Verkündung
   * @param affectedWorkEli The work ELI of the affected norm
   */
  record Query(NormExpressionEli verkuendungEli, NormWorkEli affectedWorkEli) {}
}
