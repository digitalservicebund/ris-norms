package de.bund.digitalservice.ris.norms.application.port.output;

import java.io.IOException;
import java.util.UUID;

/**
 * Interface representing a port for loading the Normendokumentationspaket files.
 */
public interface LoadNormendokumentationspaketPort {
  /**
   * Loads a Normendokumentationspaket
   *
   * @param command with the processId for the Normendokumentationspaket
   * @return the data about the Normendokumentationspaket
   */
  Result loadNormendokumentationspaket(final Command command) throws IOException;

  /**
   * A record representing the command for loading of a Normendokumentationspaket.
   *
   * @param processId identifier for the Normendokumentationspaket
   */
  record Command(UUID processId) {}

  /**
   * A record representing the files of the Normendokumentationspaket.
   *
   * @param file as zip
   * @param signature as sig
   */
  record Result(byte[] file, byte[] signature) {}
}
