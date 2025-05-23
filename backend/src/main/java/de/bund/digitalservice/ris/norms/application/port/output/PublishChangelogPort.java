package de.bund.digitalservice.ris.norms.application.port.output;

/**
 * Interface representing the output port for publishing a changelog to storage
 */
public interface PublishChangelogPort {
  /**
   * Publishes the changelog.
   * @param options the options for the port
   */
  void publishChangelogs(final Options options);

  /**
   * Options for publishing a changelog
   *
   * @param allChanged if the publish process should just send a reset signal in the changelog
   */
  record Options(boolean allChanged) {}
}
