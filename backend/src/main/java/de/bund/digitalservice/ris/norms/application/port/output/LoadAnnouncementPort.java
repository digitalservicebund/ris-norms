package de.bund.digitalservice.ris.norms.application.port.output;

import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import java.util.Optional;

/**
 * Interface representing a port for loading a {@link Announcement} based on the ELI (European
 * Legislation Identifier) of its {@link de.bund.digitalservice.ris.norms.domain.entity.Norm}.
 * Implementations of this interface should provide functionality to load a announcement using the
 * specified command.
 */
public interface LoadAnnouncementPort {

  /**
   * Loads a {@link Announcement} based on the provided ELI specified in the command.
   *
   * @param command The command specifying the ELI to identify the norm of the announcement to be
   *     loaded.
   * @return An {@link Optional} containing the loaded {@link Announcement} if found, or empty if
   *     not found.
   */
  Optional<Announcement> loadAnnouncement(final Command command);

  /**
   * A record representing the command for loading an announcement. The command includes the ELI
   * (European Legislation Identifier) to identify the announcement.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the announcement in the
   *     command.
   */
  record Command(String eli) {}
}
