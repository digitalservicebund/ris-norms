package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;

/**
 * Interface representing a port for deleting an {@link Verkuendung} and its corresponding {@link Norm}.
 */
public interface DeleteVerkuendungByNormEliPort {
  /**
   * Deletes an {@link Verkuendung} with its corresponding {@link Norm}
   *
   * @param options The options specifying the Verkuendung to be deleted.
   */
  void deleteVerkuendungByNormEli(final Options options);

  /**
   * A record representing the options for deleting a Verkuendung.
   *
   * @param eli of the corresponding norm.
   */
  record Options(NormExpressionEli eli) {}
}
