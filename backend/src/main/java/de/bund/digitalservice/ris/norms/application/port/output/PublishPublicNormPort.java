package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/**
 * Interface representing the output port for publishing a {@link Norm} entity to a storage for public data.
 * This port provides the necessary method to upload the norm to a storage location containing public data,
 * facilitating the application's outbound communication requirements for publicly shared norms.
 */
public interface PublishPublicNormPort {
  /**
   * Publishes the specified {@link Norm} entity to a designated public storage location.
   * This method initiates the process of storing the norm in a public location, using the
   * {@link Command} object to encapsulate the specific norm to be published.
   *
   * @param command a {@link Command} object containing the {@link Norm} entity to be stored
   *                in the public storage location.
   */
  void publishPublicNorm(final Command command);

  /**
   * A record representing the command used for publishing a {@link Norm} to the public storage location.
   * This {@link Command} record encapsulates the norm data required for publication, enabling
   * clients to specify the {@link Norm} entity intended for public storage.
   *
   * @param norm the {@link Norm} entity to be published to the public storage location.
   */
  record Command(Norm norm) {}
}
