package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/**
 * Interface representing the output port for deleting a {@link Norm} entity from a storage location designated
 * for private data.
 */
public interface DeletePrivateNormPort {
  /**
   * Deletes the specified {@link Norm} entity from a designated private storage location.
   *
   * @param command a {@link Command} object containing the {@link Norm} entity to be deleted
   *                from the private storage location.
   */
  void deletePrivateNorm(final Command command);

  /**
   * A record representing the command used for deleting a {@link Norm} from the private storage location.
   *
   * @param norm the {@link Norm} entity to be published to the private storage location.
   */
  record Command(Norm norm) {}
}
