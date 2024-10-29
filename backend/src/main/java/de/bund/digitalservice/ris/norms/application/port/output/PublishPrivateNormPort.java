package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/**
 * Interface representing the output port for publishing a {@link Norm} entity to a storage location designated
 * for private data. This port defines the operation required to upload the norm to a secure, private storage,
 * supporting the application's outbound communication requirements for privately shared norms.
 */
public interface PublishPrivateNormPort {
  /**
   * Publishes the specified {@link Norm} entity to a designated private storage location.
   * This method initiates the process of storing the norm in a secure private location,
   * utilizing the {@link Command} object to encapsulate the specific norm to be published.
   *
   * @param command a {@link Command} object containing the {@link Norm} entity to be stored
   *                in the private storage location.
   * @return if the publish process succeeded.
   */
  boolean publishPrivateNorm(final Command command);

  /**
   * A record representing the command used for publishing a {@link Norm} to the private storage location.
   * This {@link Command} record encapsulates the norm data required for publication, enabling clients
   * to specify the {@link Norm} entity intended for private storage.
   *
   * @param norm the {@link Norm} entity to be published to the private storage location.
   */
  record Command(Norm norm) {}
}
