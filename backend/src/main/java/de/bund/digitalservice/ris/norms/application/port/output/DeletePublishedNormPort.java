package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/**
 * Interface representing the output port for deleting a {@link Norm} entity from a storage location.
 */
public interface DeletePublishedNormPort {
  /**
   * Deletes the specified {@link Norm} entity from a designated storage location.
   *
   * @param options a {@link Options} object containing the {@link Norm} entity to be deleted
   *                from the storage location.
   */
  void deletePublishedNorm(final Options options);

  /**
   * A record representing the command used for deleting a {@link Norm} from the storage location.
   *
   * @param norm the {@link Norm} entity to be published to the storage location.
   */
  record Options(Norm norm) {}
}
