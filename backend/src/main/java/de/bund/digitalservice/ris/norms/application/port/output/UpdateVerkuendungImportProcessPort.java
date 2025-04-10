package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;

/**
 * Port for updating the progress information for the background processing
 * of a new Verkündung.
 */
public interface UpdateVerkuendungImportProcessPort {
  /**
   * Updates the progress information with the infos from the command.
   *
   * @param command Information identifying the updated data
   * @return The progress information
   */
  VerkuendungImportProcess updateVerkuendungImportProcess(final Command command);

  /**
   * Parameters for updating the progress information.
   *
   * @param verkuendungImportProcess The information to update
   */
  record Command(VerkuendungImportProcess verkuendungImportProcess) {}
}
