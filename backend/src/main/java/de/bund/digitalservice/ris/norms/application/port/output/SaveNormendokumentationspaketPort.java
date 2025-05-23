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
   * @param options with the Normendokumentationspaket files
   */
  void saveNormendokumentationspaket(final Options options) throws IOException;

  /**
   * A record representing the options for saving a Normendokumentationspaket.
   *
   * @param processId identifier for the upload process
   * @param file as zip
   * @param signature as sig
   */
  record Options(UUID processId, Resource file, Resource signature) {}
}
