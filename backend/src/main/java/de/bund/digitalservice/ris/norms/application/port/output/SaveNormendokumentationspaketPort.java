package de.bund.digitalservice.ris.norms.application.port.output;

import org.springframework.core.io.Resource;

/**
 * Interface representing a port for saving the Normendokumentationspaket files.
 */
public interface SaveNormendokumentationspaketPort {
  /**
   * Saves a Normendokumentationspaket
   *
   * @param command with the Normendokumentationspaket files
   * @return location of stored file
   */
  String saveNormendokumentationspaket(final Command command);

  /**
   * A record representing the command for saving a Normendokumentationspaket.
   *
   * @param file as zip
   * @param signature as sig
   */
  record Command(Resource file, Resource signature) {}
}
