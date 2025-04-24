package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.util.UUID;

/**
 * Port for saving the progress information for the background processing
 * of a new Verkündung.
 */
public interface SaveVerkuendungImportProcessPort {
  /**
   * Save the {@link VerkuendungImportProcess} object
   *
   * @param command containing the object of {@link VerkuendungImportProcess}
   * @return The saved/updated {@link VerkuendungImportProcess}
   */
  VerkuendungImportProcess saveOrUpdateVerkuendungImportProcess(final Command command);

  /**
   * Parameter for saving/updating a {@link VerkuendungImportProcess} object
   *
   * @param id of the job
   * @param status of the file import
   * @param details of what went wrong
   */
  record Command(UUID id, VerkuendungImportProcess.Status status, NormsAppException details) {
    /**
     * Parameter for saving/updating a {@link VerkuendungImportProcess} object
     *
     * @param id of the job
     * @param status of the file import
     */
    public Command(UUID id, VerkuendungImportProcess.Status status) {
      this(id, status, null);
    }
  }
}
