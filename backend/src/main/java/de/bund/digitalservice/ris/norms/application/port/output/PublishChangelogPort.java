package de.bund.digitalservice.ris.norms.application.port.output;

/**
 * Interface representing the output port for publishing a changelog to storage
 */
public interface PublishChangelogPort {
  /**
   * Publishes the changelog.
   * @param command the command for the port
   */
  void publishChangelogs(final Command command);

  /**
   * Command for publishing a changelog
   *
   * @param allChanged if the publish process should just send a reset signal in the changelog
   */
  record Command(boolean allChanged) {}
}
