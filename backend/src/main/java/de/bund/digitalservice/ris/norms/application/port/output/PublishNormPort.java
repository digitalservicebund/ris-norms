package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/**
 * Interface representing the output port for publishing a {@link Norm} entity to a storage.
 * This port provides the necessary method to upload the norm to a storage location, facilitating the application's outbound communication requirements.
 */
public interface PublishNormPort {
  /**
   * Publishes the specified {@link Norm} entity to a designated storage location.
   * This method initiates the process of storing the norm, using the
   * {@link Command} object to encapsulate the specific norm to be published.
   *
   * @param command a {@link Command} object containing the {@link Norm} entity to be stored.
   */
  void publishNorm(final Command command);

  /**
   * A record representing the command used for publishing a {@link Norm}.
   * This {@link Command} record encapsulates the norm data required for publication, enabling
   * clients to specify the {@link Norm} entity intended storage.
   *
   * @param norm the {@link Norm} entity to be published to the storage location.
   */
  record Command(Norm norm) {}
}
