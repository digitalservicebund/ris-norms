package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentWorkEli;

/**
 * Interface representing a port for deleting a {@link de.bund.digitalservice.ris.norms.domain.entity.NormPublishState#QUEUED_FOR_PUBLISH} {@link Norm} based on the ELI (European Legislation
 * Identifier).
 */
public interface DeleteQueuedNormsPort {
  /**
   * Delete all {@link Norm}s in the {@link de.bund.digitalservice.ris.norms.domain.entity.NormPublishState#QUEUED_FOR_PUBLISH} state that have the ELI specified in the command.
   *
   * @param command The command specifying the ELI to identify the norm(s) to be deleted.
   */
  void deleteQueuedForPublishNorms(final Command command);

  /**
   * A record representing the command for deleting {@link de.bund.digitalservice.ris.norms.domain.entity.NormPublishState#QUEUED_FOR_PUBLISH} norms. The command includes the ELI (European
   * Legislation Identifier) to identify the norm.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the norms in the command.
   */
  record Command(DokumentWorkEli eli) {}
}
