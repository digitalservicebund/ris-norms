package de.bund.digitalservice.ris.norms.application.port.output;

/**
 * Interface representing the output port for publishing changelogs to public and private storages
 */
public interface PublishChangelogsPort {
  /**
   * Publishes the changelogs to designated public and private locations.
   * @param command the command for the port
   */
  void publishChangelogs(final Command command);

  /**
   * Command for publishing change logs
   *
   * @param allChanged if the publish process should just send a reset signal in the changelog
   */
  record Command(boolean allChanged) {}
}
