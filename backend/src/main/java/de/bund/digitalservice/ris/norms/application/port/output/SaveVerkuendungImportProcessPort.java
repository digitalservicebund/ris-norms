package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcessDetail;
import java.util.List;
import java.util.Objects;
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
  record Command(
    UUID id,
    VerkuendungImportProcess.Status status,
    List<VerkuendungImportProcessDetail> details
  ) {
    /**
     * Parameter for saving/updating a {@link VerkuendungImportProcess} object
     *
     * @param id of the job
     * @param status of the file import
     * @param details of what went wrong
     */
    public Command {
      Objects.requireNonNull(id, "id cannot be null");
      Objects.requireNonNull(status, "status cannot be null");
      Objects.requireNonNull(
        details,
        "Details cannot be null. Use the other constructor or provide an empty list."
      );
    }
    /**
     * Parameter for saving/updating a {@link VerkuendungImportProcess} object
     *
     * @param id of the job
     * @param status of the file import
     */
    public Command(UUID id, VerkuendungImportProcess.Status status) {
      this(id, status, List.of());
    }
  }
}
