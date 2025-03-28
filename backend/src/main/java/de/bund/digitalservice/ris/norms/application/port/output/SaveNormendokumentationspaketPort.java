package de.bund.digitalservice.ris.norms.application.port.output;

import java.io.IOException;
import java.util.UUID;
import org.springframework.core.io.Resource;

/**
 * Interface representing a port for saving the Normendokumentationspaket files.
 */
public interface SaveNormendokumentationspaketPort {
  /**
   * Saves a Normendokumentationspaket
   *
   * @param command with the Normendokumentationspaket files
   */
  void saveNormendokumentationspaket(final Command command) throws IOException;

  /**
   * A record representing the command for saving a Normendokumentationspaket.
   *
   * @param processId identifier for the upload process
   * @param file as zip
   * @param signature as sig
   */
  record Command(UUID processId, Resource file, Resource signature) {}
}
