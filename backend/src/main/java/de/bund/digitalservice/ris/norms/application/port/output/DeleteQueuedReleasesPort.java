package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Release;
import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormEli;
import java.util.List;

/**
 * Interface representing a port for deleting all queued {@link Release}s of an {@link Verkuendung}.
 */
public interface DeleteQueuedReleasesPort {
  /**
   * Deleting all queued {@link Release}s of an {@link Verkuendung}.
   *
   * @param command The command specifying the ELI to identify the {@link Verkuendung} whose queued {@link Release}s should be deleted.
   * @return A list of the deleted releases.
   */
  List<Release> deleteQueuedReleases(final Command command);

  /**
   * A record representing the command for deleting all queued {@link Release}s of an {@link Verkuendung}. The command includes the ELI (European
   * Legislation Identifier) to identify the {@link Verkuendung}.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the {@link Verkuendung} in the command.
   */
  record Command(NormEli eli) {}
}
