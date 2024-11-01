package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/**
 * Interface representing the output port for deleting a {@link Norm} entity from a storage location designated
 * for public data.
 */
public interface DeletePublicNormPort {
  /**
   * Deletes the specified {@link Norm} entity from a designated public storage location.
   *
   * @param command a {@link Command} object containing the {@link Norm} entity to be deleted
   *                from the public storage location.
   */
  void deletePublicNorm(final Command command);

  /**
   * A record representing the command used for deleting a {@link Norm} from the public storage location.
   *
   * @param norm the {@link Norm} entity to be published to the public storage location.
   */
  record Command(Norm norm) {}
}
