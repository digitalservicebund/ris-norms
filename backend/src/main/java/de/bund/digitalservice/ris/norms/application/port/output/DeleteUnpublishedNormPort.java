package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ManifestationEli;

/**
 * Interface representing a port for deleting a {@link Norm} based on the ELI (European Legislation
 * Identifier).
 */
public interface DeleteUnpublishedNormPort {
  /**
   * Delete a {@link Norm} based on the provided ELI specified in the command.
   *
   * @param command The command specifying the ELI to identify the norm to be deleted.
   */
  void deleteUnpublishedNorm(final Command command);

  /**
   * A record representing the command for loading a norm. The command includes the ELI (European
   * Legislation Identifier) to identify the norm.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the norm in the command.
   */
  record Command(ManifestationEli eli) {}
}
