package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import java.util.Optional;
import java.util.UUID;

/**
 * Port for loading the progress information for the background processing
 * of a new Verkündung.
 */
public interface LoadVerkuendungImportProcessPort {
  /**
   * Loads the progress information identified by the options.
   *
   * @param options Information identifying the requested data
   * @return The requested progress information
   */
  Optional<VerkuendungImportProcess> loadVerkuendungImportProcess(final Options options);

  /**
   * Parameters for loading the progress information.
   *
   * @param id ID of the process for which information should be loaded
   */
  record Options(UUID id) {}
}
